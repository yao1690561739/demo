package com.yao.system.controller;

import cn.hutool.core.convert.Convert;
import com.yao.common.controller.BaseController;
import com.yao.common.enums.ResultEnum;
import com.yao.common.util.ResultUtils;
import com.yao.system.config.actionLog.annotation.Operation;
import com.yao.system.config.file.properties.UploadProjectProperties;
import com.yao.system.config.file.util.UploadUtils;
import com.yao.system.dto.UploadDto;
import com.yao.system.entity.Upload;
import com.yao.system.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YL
 * @date 2020/11/12 10:58
 * @description 文件服务控制层
 */
@RestController
@Api(tags = "文件服务接口")
@RequestMapping("/system/file")
@Slf4j
public class UploadController extends BaseController {

    @Resource
    private UploadService uploadService;

    @Resource
    private UploadProjectProperties uploadProjectProperties;

    /**
     * 根据md5值查询文件信息
     * @param md5 文件md5值
     * @return 文件信息
     */
    @GetMapping("/get/{md5}")
    @ApiOperation("根据md5值查询文件信息")
    @ApiImplicitParam(name = "md5",value = "文件md5值",required = true)
    @Operation(name = "根据md5值查询文件信息")
    public ResultUtils get(@PathVariable("md5") String md5){
        return result(uploadService.getFileByMd5(md5));
    }

    /**
     * 查询文件信息列表
     * @param uploadDto 文件信息
     * @return 文件列表
     */
    @GetMapping("/list")
    @ApiOperation("查询文件信息列表")
    @Operation(name = "查询文件信息列表")
    public ResultUtils list(UploadDto uploadDto){
        startPage();
        return result(uploadService.getFiles(uploadDto));
    }

    /**
     * 上传文件
     * @param files 文件
     * @return 结果
     * @throws IOException 文件流异常
     * @throws NoSuchAlgorithmException 算法异常
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传接口")
    @Operation(name = "上传文件")
    public ResultUtils upload(MultipartFile[] files) throws IOException, NoSuchAlgorithmException {
        int len=files.length;
        int count=0;
        //创建map存储返回信息
        Map<String, Object> map=new HashMap<>(16);
        //存储上传成功的文件信息
        List<Upload> uploads=new ArrayList<>();
        //存储上传失败的文件名
        List<String> failname=new ArrayList<>();
        //判断文件为空
        if (len==0){
            return ResultUtils.error(ResultEnum.NO_FILE_NULL);
        }
        for (MultipartFile file:files){
            //文件上传目录
            String path;
            //判断是否支持的图片类型
            if(UploadUtils.isContentType(file,uploadProjectProperties.getPhotoTypes())){
                path=uploadProjectProperties.getFilePath()+"photo"+UploadUtils.genDateMkdir("yyyyMMdd");
            }else if (UploadUtils.isContentType(file,uploadProjectProperties.getDocTypes())){
                path=uploadProjectProperties.getFilePath()+"doc"+UploadUtils.genDateMkdir("yyyyMMdd");
            }else if (UploadUtils.isContentType(file,uploadProjectProperties.getPackageTypes())){
                path=uploadProjectProperties.getFilePath()+"package"+UploadUtils.genDateMkdir("yyyyMMdd");
            }else {
                return ResultUtils.error(ResultEnum.NO_FILE_TYPE);
            }
            //判断目录是否存在
            String realPath=UploadUtils.getFilePath()+path;
            File dataDir = new File(realPath);
            if (!dataDir.exists()){
                //不存在则生成目录
                dataDir.mkdirs();
            }
            //获取文件MD5值和哈希值
            byte[] buffer = new byte[4096];
            InputStream fis = file.getInputStream();
            MessageDigest md5x = MessageDigest.getInstance("MD5");
            MessageDigest sha1x = MessageDigest.getInstance("SHA1");
            int length = 0;
            while ((length = fis.read(buffer)) != -1) {
                md5x.update(buffer, 0, length);
                sha1x.update(buffer, 0, length);
            }
            BigInteger md5Bi = new BigInteger(1, md5x.digest());
            BigInteger sha1Bi = new BigInteger(1, sha1x.digest());
            String md5=md5Bi.toString(16);
            String sha1=sha1Bi.toString(16);
            //判断文件是否存在
            Upload uploadMd5 = uploadService.getFileByMd5(md5);
            if (uploadMd5 != null) {
                //存在的话直接显示文件上传成功
                uploads.add(uploadMd5);
            } else {
                //不存在则继续执行上传
                //生成新的文件名称
                String newFileName = UploadUtils.genNewName(file);
                // 创建文件对象
                Upload upload = new Upload();
                //处理文件上传
                try {
                    file.transferTo(new File(dataDir,newFileName));
                    //获取文件的原始名称
                    String oldFileName = file.getOriginalFilename();
                    //获取文件的后缀
                    String extension = UploadUtils.getExtension(file);
                    //获取文件大小
                    long size = file.getSize();
                    upload.setRealname(oldFileName);
                    upload.setNewname(newFileName);
                    upload.setPath(path);
                    upload.setType(extension);
                    upload.setSize(size);
                    upload.setMd5(md5);
                    upload.setSha1(sha1);
                    upload.setCounts(0);
                    // 将文件信息保存到数据库中
                    uploadService.addFile(upload);
                    uploads.add(upload);
                }catch (Exception e){
                    failname.add(file.getOriginalFilename());
                    log.error(e.getMessage());
                }
            }
            count++;
            log.info("上传文件成功:[{}]",file.getOriginalFilename());
        }
        String uploadInfo="上传"+len+"个文件，成功"+count+"个，失败"+(len-count)+"个!";
        map.put("summary",uploadInfo);
        map.put("data",uploads);
        map.put("failname",failname);
        log.info(uploadInfo);
        return ResultUtils.ok(map);
    }


    /**
     * 文件下载
     * @param md5 md5值
     * @throws IOException 文件异常
     */
    @GetMapping("/download/{md5}")
    @ApiOperation("文件下载接口")
    @Operation(name = "下载文件")
    public void download(@PathVariable("md5") String md5, HttpServletResponse response) throws IOException {
        //根据md5查询文件信息
        Upload uploadMd5 = uploadService.getFileByMd5(md5);
        //文件路径
        String realpath = UploadUtils.getFilePath()+uploadMd5.getPath();
        //获取文件输入流
        FileInputStream is = new FileInputStream(new File(realpath, uploadMd5.getNewname()));
        //设置文件
        //设置强制下载不打开
        response.setContentType("application/force-download");
        //设置文件名
        response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(uploadMd5.getRealname(),"UTF-8"));
        //获取响应输出流
        ServletOutputStream os = response.getOutputStream();
        //文件拷贝
        try {
            IOUtils.copy(is,os);
        }catch (ClientAbortException e){
            log.info(e.getMessage());
            log.info("使用第三方软件下载！");
        }catch (Exception e){
            log.error(e.getMessage());
        }
        log.info("文件下载成功！");
        //更新下载次数
        Upload upload=new Upload();
        upload.setId(uploadMd5.getId());
        upload.setCounts(uploadMd5.getCounts()+1);
        uploadService.editFile(upload);
        //关闭文件输入输出流，不需要catch
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }

    /**
     * 删除文件
     * @param md5 md5值
     * @return 结果
     * @throws IOException 文件异常
     */
    @DeleteMapping("/delete/{md5}")
    @ApiOperation("文件删除接口")
    @Operation(name = "删除文件")
    public ResultUtils delete(@PathVariable("md5") String md5) throws IOException {
        //根据md5查询文件信息
        Upload uploadMd5 = uploadService.getFileByMd5(md5);
        //删除文件
        String realpath = UploadUtils.getFilePath()+uploadMd5.getPath();
        File file = new File(realpath, uploadMd5.getNewname());
        if (file.exists()){
            try {
                //执行垃圾回收后删除文件，防止文件被占用时删除不掉
                System.gc();
                log.info("执行垃圾回收！");
                file.delete();
                log.info("删除文件成功！");
            }catch (Exception e){
                log.error(e.getMessage());
                ResultUtils.error("删除文件失败！");
            }
        }
        //删除数据库中的记录
        uploadService.removeFiles(Convert.toStr(uploadMd5.getId()));
        return ResultUtils.ok();
    }

}

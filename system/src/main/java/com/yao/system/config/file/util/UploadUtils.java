package com.yao.system.config.file.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author YL
 * @date 2020/11/12 10:16
 * @description 文件处理工具
 */
@Configuration
public class UploadUtils {

    /**
     * 获取文件后缀名
     */
    public static String getExtension(MultipartFile file){
        //获取文件名
        String fileName=file.getOriginalFilename();
        return FilenameUtils.getExtension(fileName);
    }

    /**
     * 判断文件是否为支持的格式
     * @param file MultipartFile对象
     */
    public static boolean isContentType(MultipartFile file,String[] filesTypes){
        List<String> typeList = Arrays.asList(filesTypes);
        return typeList.contains(getExtension(file));
    }

    /**
     * 生成随机且唯一的文件名
     */
    public static String genNewName(MultipartFile file){
        //获取文件的后缀
        String extension = getExtension(file);
        //20位UUID+原后缀
        return UUID.randomUUID().toString().replace("-","").substring(0,20) + "."+extension;
    }

    /**
     * 获取文件上传保存路径
     */
    public static String getFilePath() throws IOException {
        return new File("").getCanonicalPath() + "/";
    }

    /**
     * 生成指定日期格式的目录名称
     */
    public static String genDateMkdir(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return "/" + sdf.format(new Date()) + "/";
    }

    /**
     * 获取文件的MD5值
     */
    public static String getFileMd5(String filePath) throws IOException {
        return DigestUtils.md5Hex(new FileInputStream(filePath));
    }

    /**
     * 获取文件的SHA1值
     */
    public static String getFileSha1(String filePath) throws IOException {
        return DigestUtils.sha1Hex(new FileInputStream(filePath));
    }

}


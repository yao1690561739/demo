package com.yao.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.yao.system.config.redis.annotation.RedisCache;
import com.yao.system.config.redis.annotation.RedisEvict;
import com.yao.system.dao.UploadDao;
import com.yao.system.dto.UploadDto;
import com.yao.system.entity.Upload;
import com.yao.system.service.UploadService;
import com.yao.system.vo.UploadVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author YL
 * @date 2020/11/11 16:37
 * @description 文件业务接口实现
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    @Resource
    private UploadDao uploadDao;

    /**
     * 根据MD5值获取文件信息
     *
     * @param md5 文件MD5值
     * @return 文件信息
     */
    @Override
    public UploadVo getFileByMd5(String md5) {
        log.info("查询文件信息！");
        return uploadDao.getFileByMd5(md5);
    }

    /**
     * 按条件获取文件信息
     *
     * @param uploadDto 实体对象
     * @return 文件信息
     */
    @Override
    public List<UploadVo> getFiles(UploadDto uploadDto) {
        log.info("查询文件列表！");
        return uploadDao.getFiles(uploadDto);
    }

    /**
     * 添加文件信息
     *
     * @param upload 实体对象
     */
    @Override
    public void addFile(Upload upload) {
        log.info("添加文件信息！");
        uploadDao.addFile(upload);
    }

    /**
     * 修改文件信息
     *
     * @param upload 实体对象
     */
    @Override
    public void editFile(Upload upload) {
        log.info("更新文件信息！");
        uploadDao.editFile(upload);
    }

    /**
     * 批量删除文件
     *
     * @param ids 文件ID
     */
    @Override
    public void removeFiles(String ids) {
        log.info("删除文件信息！");
        uploadDao.removeFiles(Convert.toStrArray(ids));
    }


}

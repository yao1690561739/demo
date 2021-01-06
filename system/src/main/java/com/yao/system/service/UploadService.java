package com.yao.system.service;

import com.yao.system.dto.UploadDto;
import com.yao.system.entity.Upload;
import com.yao.system.vo.UploadVo;

import java.util.List;

/**
 * @author YL
 * @date 2020/11/11 16:31
 * @description 文件业务接口
 */
public interface UploadService {
    /**
     * 根据MD5值获取文件信息
     * @param md5 文件MD5值
     * @return 文件信息
     */
    UploadVo getFileByMd5(String md5);

    /**
     * 按条件获取文件信息
     * @param uploadDto 实体对象
     * @return 文件信息
     */
    List<UploadVo> getFiles(UploadDto uploadDto);

    /**
     * 添加文件信息
     * @param upload 实体对象
     */
    void addFile(Upload upload);

    /**
     * 修改文件信息
     * @param upload 实体对象
     */
    void editFile(Upload upload);

    /**
     * 批量删除文件
     * @param ids 文件ID
     */
    void removeFiles(String ids);
}

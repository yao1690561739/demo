package com.yao.system.dao;

import com.yao.system.dto.UploadDto;
import com.yao.system.entity.Upload;
import com.yao.system.vo.UploadVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YL
 * @date 2020/11/11 11:31
 * @description 文件服务接口
 */
@Mapper
public interface UploadDao {
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
     * @return 影响行数
     */
    int addFile(Upload upload);

    /**
     * 修改文件信息
     * @param upload 实体对象
     * @return 影响行数
     */
    int editFile(Upload upload);

    /**
     * 批量删除文件
     * @param ids 文件ID
     * @return 影响行数
     */
    int removeFiles(String[] ids);
}

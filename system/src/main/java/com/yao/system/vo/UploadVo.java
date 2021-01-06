package com.yao.system.vo;

import com.yao.system.entity.Upload;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YL
 * @date 2020/11/11 14:05
 * @description 文件信息展示
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UploadVo extends Upload {
    /** 上传者名称 */
    private String createName;

    /** 更新者名称 */
    private String updateName;
}

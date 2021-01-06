package com.yao.system.config.file.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author YL
 * @date 2020/11/13 18:47
 * @description 文件上传配置项
 */
@Data
@Component
@ConfigurationProperties(prefix = "project.upload")
public class UploadProjectProperties {

    /** 上传文件路径 */
    private String filePath;

    /** 支持上传的图片类型 */
    private String[] photoTypes;

    /** 支持上传的文档类型 */
    private String[] docTypes;

    /** 支持上传的包类型 */
    private String[] packageTypes;

}

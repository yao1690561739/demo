package com.yao.system.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YL
 * @date 2020/11/10 16:46
 * @description 文件信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("文件实体类对象")
public class Upload extends BaseEntity{
    /** 文件ID */
    @ApiModelProperty(value = "文件ID")
    private Integer id;

    /** 原文件名 */
    @ApiModelProperty(value = "原文件名")
    private String realname;

    /** 新文件名 */
    @ApiModelProperty(value = "新文件名")
    private String newname;

    /** 文件路径 */
    @ApiModelProperty(value = "文件路径")
    private String path;

    /** 文件类型 */
    @ApiModelProperty(value = "文件类型")
    private String type;

    /** 文件大小 */
    @ApiModelProperty(value = "文件大小")
    private Long size;

    /** MD5值 */
    @ApiModelProperty(value = "MD5值")
    private String md5;

    /** 哈希值 */
    @ApiModelProperty(value = "哈希值")
    private String sha1;

    /** 下载次数 */
    @ApiModelProperty(value = "下载次数")
    private Integer counts;

}

package com.yao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YL
 * @date 2020/9/29 16:49
 * @description 文件列表查询数据传输对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("文件查询数据传输对象")
public class UploadDto extends PageDto{

    /** 用户名 */
    @ApiModelProperty(value = "用户名")
    private String realname;

    /** 昵称 */
    @ApiModelProperty(value = "用户昵称")
    private String createName;

}

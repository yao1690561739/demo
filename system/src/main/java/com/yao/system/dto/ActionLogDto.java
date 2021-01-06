package com.yao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YL
 * @date 2020/11/24 20:55
 * @description 操作日志查询数据传输对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("操作日志查询数据传输对象")
public class ActionLogDto extends PageDto{
    /** 日志名称 */
    @ApiModelProperty(value = "日志名称")
    private String name;
    /** 产生日志的用户 */
    @ApiModelProperty(value = "产生日志的用户")
    private String operBy;
}

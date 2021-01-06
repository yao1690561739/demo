package com.yao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YL
 * @date 2020/11/24 20:57
 * @description 登录记录查询数据传输对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("登录记录查询数据传输对象")
public class LoginInfoDto extends PageDto{
    /** 登录账号 */
    @ApiModelProperty(value = "登录账号")
    private String loginAccount;
    /** 登录名 */
    @ApiModelProperty(value = "登录名")
    private String loginName;
    /** 浏览器类型 */
    @ApiModelProperty(value = "浏览器类型")
    private String browser;
    /** 操作系统 */
    @ApiModelProperty(value = "操作系统")
    private String os;
    /** 登录状态 */
    @ApiModelProperty(value = "登录状态")
    private String status;
}

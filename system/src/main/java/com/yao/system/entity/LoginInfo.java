package com.yao.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author YL
 * @date 2020/11/24 9:26
 * @description 登录记录实体类
 */
@Data
public class LoginInfo {

    @ApiModelProperty(value = "登录记录ID")
    private Integer id;
    /** 登录账号 */
    @ApiModelProperty(value = "登录账号")
    private String loginAccount;
    /** 登录名 */
    @ApiModelProperty(value = "登录名")
    private String loginName;
    /** 登录IP */
    @ApiModelProperty(value = "登录IP")
    private String ipaddr;
    /** 浏览器类型 */
    @ApiModelProperty(value = "浏览器类型")
    private String browser;
    /** 操作系统 */
    @ApiModelProperty(value = "操作系统")
    private String os;
    /** 登录状态 */
    @ApiModelProperty(value = "登录状态")
    private String status;
    /** 提示消息 */
    @ApiModelProperty(value = "提示消息")
    private String msg;
    /** 登录时间 */
    @ApiModelProperty(value = "登录时间")
    private String loginTime;
}

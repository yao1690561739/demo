package com.yao.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YL
 * @date 2020/9/24 14:02
 * @description 用户表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户实体类对象")
public class User extends BaseEntity{

    @ApiModelProperty(value = "用户ID")
    private Integer id;

    /** 用户名 */
    @ApiModelProperty(value = "用户名")
    private String username;

    /** 昵称 */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /** 密码 */
    @ApiModelProperty(value = "密码")
    private String password;

    /** 随机盐 */
    @ApiModelProperty(hidden = true)
    private String salt;

    /** 部门ID */
    @ApiModelProperty(value = "部门ID")
    private Integer deptId;

    /** 头像 */
    @ApiModelProperty(value = "头像")
    private String picture;

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private Integer sex;

    /** 手机号 */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /** 邮箱 */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /** 状态 */
    @ApiModelProperty(value = "状态")
    private Integer status;

}

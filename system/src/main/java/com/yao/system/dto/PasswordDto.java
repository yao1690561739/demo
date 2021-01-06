package com.yao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author YL
 * @date 2020/9/29 8:25
 * @description 修改用户密码数据传输对象
 */
@Data
@ApiModel("密码修改数据传输对象")
public class PasswordDto {

    @ApiModelProperty(value = "用户id")
    private Integer id;

    /** 旧密码 */
    @ApiModelProperty(value = "旧密码")
    private String oldPwd;

    /** 新密码 */
    @ApiModelProperty(value = "新密码")
    private String newPwd;

    /** 确认密码 */
    @ApiModelProperty(value = "确认密码")
    private String confirmPwd;

    /** 随机盐 */
    @ApiModelProperty(hidden = true)
    private String salt;
}

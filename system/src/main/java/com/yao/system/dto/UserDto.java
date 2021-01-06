package com.yao.system.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YL
 * @date 2020/9/29 16:49
 * @description 用户列表查询数据传输对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户查询数据传输对象")
public class UserDto extends PageDto{
    /** 用户名 */
    @ApiModelProperty(value = "用户名")
    private String username;

    /** 昵称 */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /** 性别 */
    @ApiModelProperty(value = "性别")
    private Integer sex;

    /** 状态 */
    @ApiModelProperty(value = "状态")
    private Integer status;

}

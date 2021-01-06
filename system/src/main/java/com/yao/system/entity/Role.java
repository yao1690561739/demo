package com.yao.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YL
 * @date 2020/11/3 11:30
 * @description 角色表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("角色实体类对象")
public class Role extends BaseEntity{

    @ApiModelProperty(value = "角色ID")
    private Integer id;

    /** 角色名称 */
    @ApiModelProperty(value = "角色名称")
    private String title;

    /** 角色标识 */
    @ApiModelProperty(value = "角色标识")
    private String name;

    /** 状态 */
    @ApiModelProperty(value = "状态")
    private Integer status;
}

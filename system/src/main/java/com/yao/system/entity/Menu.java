package com.yao.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author YL
 * @date 2020/11/3 16:23
 * @description 菜单权限
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("菜单权限实体类对象")
public class Menu extends BaseEntity{

    @ApiModelProperty(value = "菜单ID")
    private Integer id;

    /** 菜单名称 */
    @ApiModelProperty(value = "菜单名称")
    private String title;

    /** 父级编号 */
    @ApiModelProperty(value = "父级编号")
    private Integer pid;

    /** 所有父级编号 */
    @ApiModelProperty(value = "所有父级编号")
    private String pids;

    /** URL地址 */
    @ApiModelProperty(value = "URL地址")
    private String url;

    /** 权限标识 */
    @ApiModelProperty(value = "权限标识")
    private String perms;

    /** 图标 */
    @ApiModelProperty(value = "图标")
    private String icon;

    /** 类型 */
    @ApiModelProperty(value = "类型")
    private Integer type;

    /** 排序 */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /** 状态 */
    @ApiModelProperty(value = "状态")
    private Integer status;

}

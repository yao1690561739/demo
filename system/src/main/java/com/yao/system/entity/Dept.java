package com.yao.system.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

/**
 * @author YL
 * @date 2020/9/24 15:37
 * @description 部门表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("部门实体类对象")
public class Dept extends BaseEntity{

    @ApiModelProperty(value = "部门ID")
    private Integer deptId;

    /** 部门名称 */
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    /** 父级编号 */
    @ApiModelProperty(value = "父级编号")
    private Integer pid;

    /** 所有父级编号 */
    @ApiModelProperty(value = "所有父级编号")
    private String pids;

    /** 排序 */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /** 状态 */
    @ApiModelProperty(value = "状态")
    private Integer status;

}

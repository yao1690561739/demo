package com.yao.system.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author YL
 * @date 2020/11/3 16:33
 * @description 基础实体类
 */
@Data
public class BaseEntity {

    /** 备注 */
    @ApiModelProperty(value = "备注")
    private String remark;

    /** 创建者 */
    @ApiModelProperty(hidden = true)
    private Integer createBy;

    /** 创建时间 */
    @ApiModelProperty(hidden = true)
    private String createTime;

    /** 更新者 */
    @ApiModelProperty(hidden = true)
    private Integer updateBy;

    /** 更新时间 */
    @ApiModelProperty(hidden = true)
    private String updateTime;
}

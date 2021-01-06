package com.yao.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author YL
 * @date 2020/11/11 15:54
 * @description 分页查询数据传输对象
 */
@Data
public class PageDto {
    /** 页码 */
    @ApiModelProperty(value = "页码",example = "1")
    private Integer pageNum;

    /** 每页数量 */
    @ApiModelProperty(value = "每页数量",example = "10")
    private Integer pageSize;

    /** 排序列 */
    @ApiModelProperty(value = "排序列")
    private String sortField;

    /** 排序的方向"desc"或者"asc" */
    @ApiModelProperty(value = "排序方向")
    private String sortOrder;
}

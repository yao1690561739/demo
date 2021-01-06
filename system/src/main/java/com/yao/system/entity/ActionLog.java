package com.yao.system.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author YL
 * @date 2020/11/23 9:43
 * @description 操作日志实体类
 */
@Data
public class ActionLog {
    @ExcelIgnore
    @ApiModelProperty(value = "操作日志ID")
    private Integer id;
    /** 日志名称 */
    @ExcelProperty(value="日志名称",index = 0)
    @ApiModelProperty(value = "日志名称")
    private String name;
    /** IP地址 */
    @ExcelProperty(value="IP地址",index = 1)
    @ApiModelProperty(value = "IP地址")
    private String ipaddr;
    /** 产生日志的类 */
    @ExcelProperty(value="产生日志的类",index = 2)
    @ApiModelProperty(value = "产生日志的类")
    private String clazz;
    /** 产生日志的方法 */
    @ExcelProperty(value="产生日志的方法",index = 3)
    @ApiModelProperty(value = "产生日志的方法")
    private String method;
    /** 日志消息 */
    @ExcelProperty(value="日志消息",index = 4)
    @ApiModelProperty(value = "日志消息")
    private String message;
    /** 记录时间 */
    @ExcelProperty(value="记录时间",index = 5)
    @ApiModelProperty(value = "记录时间")
    private String createTime;
    /** 产生日志的用户 */
    @ExcelProperty(value="产生日志的用户",index = 6)
    @ApiModelProperty(value = "产生日志的用户")
    private String operBy;
}

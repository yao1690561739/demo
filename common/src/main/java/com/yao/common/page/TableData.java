package com.yao.common.page;

import lombok.Data;

import java.util.List;

/**
 * @author YL
 * @date 2020/9/28 13:45
 * @description 表格分页数据对象
 */
@Data
public class TableData {
    /** 总记录数 */
    private long total;
    /** 列表数据 */
    private List<?> data;
    /** 消息状态码 */
    private int code;

    /**
     * 表格数据对象
     */
    public TableData() {
    }

    /**
     * 分页
     *
     * @param list 列表数据
     * @param total 总记录数
     */
    public TableData(List<?> list, int total) {
        this.data = list;
        this.total = total;
    }
}

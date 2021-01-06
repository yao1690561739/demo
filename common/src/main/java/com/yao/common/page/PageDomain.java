package com.yao.common.page;

import com.yao.common.util.StringUtils;
import lombok.Data;

/**
 * @author YL
 * @date 2020/9/28 11:04
 * @description 分页数据对象
 */
@Data
public class PageDomain {
    /** 当前记录起始索引 */
    private Integer pageNum;
    /** 每页显示记录数 */
    private Integer pageSize;
    /** 排序列 */
    private String orderByColumn;
    /** 排序的方向 "desc" 或者 "asc". */
    private String isAsc;

    public String getOrderBy()
    {
        if (StringUtils.isEmpty(orderByColumn))
        {
            return "";
        }
        return StringUtils.toUnderScoreCase(orderByColumn) + " " + isAsc;
    }
}

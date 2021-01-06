package com.yao.common.page;

import com.yao.common.constant.Constants;
import com.yao.common.util.ServletUtils;

/**
 * @author YL
 * @date 2020/9/28 11:17
 * @description 表格数据处理
 */
public class TableSupport {
    /**
     * 封装分页对象
     */
    public static PageDomain getPage()
    {
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPageNum(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
        pageDomain.setIsAsc(ServletUtils.getParameter(Constants.IS_ASC));
        return pageDomain;
    }

    public static PageDomain buildPageRequest()
    {
        return getPage();
    }
}

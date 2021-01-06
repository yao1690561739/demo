package com.yao.common.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yao.common.constant.Constants;
import com.yao.common.page.*;
import com.yao.common.util.ResultUtils;
import com.yao.common.util.ServletUtils;
import com.yao.common.util.SqlUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author YL
 * @date 2020/9/28 10:59
 * @description web通用数据处理
 */
public class BaseController {

    /**
     * 设置请求分页数据
     */
    protected void startPage(){
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (null != pageNum && null != pageSize){
            String orderBy = SqlUtils.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    //分页post
    /**
     * 设置请求分页数据
     */
    protected void startPage(Integer pageNum,Integer pageSize,String sortField ,String sortOrder) {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        pageNum = pageDomain.getPageNum();
        pageSize = pageDomain.getPageSize();
        if (null != pageNum && null != pageSize) {
            pageDomain.setOrderByColumn(sortField);
            pageDomain.setIsAsc(sortOrder);;
            String orderBy = SqlUtils.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 获取request
     */
    public HttpServletRequest getRequest() {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse() {
        return ServletUtils.getResponse();
    }

    /**
     * 获取session
     */
    public HttpSession getSession() {
        return getRequest().getSession();
    }

    public Integer getCurrentUserId() {
        String currentId = getRequest().getHeader(Constants.CURRENT_ID);
        if (StringUtils.isNotBlank(currentId)) {
            return Integer.parseInt(currentId);
        }
        return 0;
    }

    public String getLoginName() {
        return getRequest().getHeader(Constants.CURRENT_USERNAME);
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected TableData getDataTable(List<?> list) {
        TableData rspData = new TableData();
        rspData.setCode(0);
        rspData.setData(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected ResultUtils result(List<?> list) {
        PageInfo<?> pageInfo = new PageInfo(list);
        Map<String, Object> m = new HashMap<>(16);
        m.put("data", list);
        m.put("pageNum", pageInfo.getPageNum());
        m.put("total", pageInfo.getTotal());
        return ResultUtils.ok(m);
    }

    protected ResultUtils result(Object data) {
        Map<String, Object> m = new HashMap<>(16);
        m.put("data", data);
        return ResultUtils.ok(m);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected ResultUtils toAjax(int rows) {
        return rows > 0 ? ResultUtils.ok() : ResultUtils.error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected ResultUtils toAjax(boolean result) {
        return result ? ResultUtils.ok() : ResultUtils.error();
    }
}

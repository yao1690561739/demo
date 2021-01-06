package com.yao.system.controller;

import com.alibaba.excel.EasyExcel;
import com.yao.common.controller.BaseController;
import com.yao.common.util.ResultUtils;
import com.yao.system.config.actionLog.annotation.Operation;
import com.yao.system.dto.ActionLogDto;
import com.yao.system.entity.ActionLog;
import com.yao.system.service.ActionLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author YL
 * @date 2020/11/23 14:49
 * @description 操作日志控制层接口
 */
@RestController
@RequestMapping("/system/action")
@Slf4j
@Api(tags = "操作日志接口")
public class ActionLogController extends BaseController {

    @Resource
    private ActionLogService actionLogService;

    /**
     * 根据ID查询操作日志信息
     * @param id 日志ID
     * @return 日志信息
     */
    @GetMapping("/get/{id}")
    @ApiOperation("根据ID查询操作日志信息")
    @ApiImplicitParam(name = "id",value = "日志ID",defaultValue = "1",required = true)
    @Operation(name = "根据ID查询操作日志信息")
    public ResultUtils get(@PathVariable("id") Integer id){
        return result(actionLogService.getActionLogById(id));
    }

    /**
     * 获取操作日志列表
     * @param actionLogDto 日志信息
     * @return 日志列表
     */
    @GetMapping("/list")
    @ApiOperation("查询操作日志信息列表")
    @Operation(name = "查询操作日志信息列表")
    public ResultUtils list(ActionLogDto actionLogDto){
        startPage();
        return result(actionLogService.getActionLogs(actionLogDto));
    }

    /**
     * 删除操作日志信息
     * @param ids 日志ID
     * @return 影响行数
     */
    @DeleteMapping("delete")
    @ApiOperation("删除操作日志信息")
    @Operation(name = "删除操作日志信息")
    public ResultUtils delete(String ids){
        return toAjax(actionLogService.removeActionLogs(ids));
    }

    /**
     * 删除操作日志信息
     * @return 影响行数
     */
    @DeleteMapping("deleteAll")
    @ApiOperation("清空操作日志信息")
    @Operation(name = "清空操作日志信息")
    public ResultUtils deleteAll(){
        return toAjax(actionLogService.removeAllActionLogs());
    }

    /**
     * 导出表格
     * @param response 响应
     * @param actionLogDto 操作日志筛选条件
     * @throws IOException IO流异常
     */
    @GetMapping("export")
    @ApiOperation("导出操作日志信息")
    @Operation(name = "导出操作日志信息")
    public void export(HttpServletResponse response, ActionLogDto actionLogDto) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("操作日志表", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        List<ActionLog> actionLogs=actionLogService.getActionLogs(actionLogDto);
        EasyExcel.write(response.getOutputStream(), ActionLog.class).sheet("操作日志表").doWrite(actionLogs);
        log.info("导出操作日志表！");
    }
}

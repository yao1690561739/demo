package com.yao.system.controller;

import com.yao.common.controller.BaseController;
import com.yao.common.util.ResultUtils;
import com.yao.system.config.actionLog.annotation.Operation;
import com.yao.system.dto.LoginInfoDto;
import com.yao.system.service.LoginInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author YL
 * @date 2020/11/24 10:14
 * @description 登录记录控制层接口
 */
@RestController
@RequestMapping("/system/logininfo")
@Api(tags = "登录记录接口")
public class LoginInfoController extends BaseController {

    @Resource
    private LoginInfoService loginInfoService;

    /**
     * 根据ID查询登录记录信息
     * @param id 登录记录ID
     * @return 登录记录信息
     */
    @GetMapping("/get/{id}")
    @ApiOperation("根据ID查询登录记录信息")
    @ApiImplicitParam(name = "id",value = "登录记录ID",defaultValue = "1",required = true)
    @Operation(name = "根据ID查询登录记录信息")
    public ResultUtils get(@PathVariable("id") Integer id){
        return result(loginInfoService.getLoginInfoById(id));
    }

    /**
     * 获取登录记录列表
     * @param loginInfoDto 登录记录信息
     * @return 登录记录列表
     */
    @GetMapping("/list")
    @ApiOperation("查询登录记录信息列表")
    @Operation(name = "查询登录记录信息列表")
    public ResultUtils list(LoginInfoDto loginInfoDto){
        startPage();
        return result(loginInfoService.getLoginInfos(loginInfoDto));
    }

    /**
     * 删除登录记录信息
     * @param ids 登录记录ID
     * @return 影响行数
     */
    @DeleteMapping("delete")
    @ApiOperation("删除登录记录信息")
    @Operation(name = "删除登录记录信息")
    public ResultUtils delete(String ids){
        return toAjax(loginInfoService.removeLoginInfos(ids));
    }

    /**
     * 删除登录记录信息
     * @return 影响行数
     */
    @DeleteMapping("deleteAll")
    @ApiOperation("清空登录记录信息")
    @Operation(name = "清空登录记录信息")
    public ResultUtils deleteAll(){
        int row=loginInfoService.removeAllLoginInfos();
        if (row>0){
            return toAjax(row);
        } else {
            return ResultUtils.error("登录记录信息已被清空！");
        }
    }

}

package com.yao.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author YL
 * @date 2020/9/30 15:31
 * @description 页面跳转
 */
@Controller
@Api(tags = "页面跳转汇总接口")
@Slf4j
public class PageController {

    /**
     * 不输入详细路径时默认跳转到登陆页面
     * @return 登陆页面
     */
    @GetMapping("/")
    @ApiOperation("默认跳转登录页面")
    public String defaults(){
        log.info("------默认跳转登录页面------");
        return "login";
    }

    /**
     * 跳转登录页面
     * @return  登录页面
     */
    @GetMapping("login")
    @ApiOperation("用户登录页面")
    public String login(){
        log.info("------用户登录页面------");
        return "login";
    }

    /**
     * 注销
     * @return 重定向到登录页面
     */
    @GetMapping("/logout")
    @ApiOperation("用户注销")
    public String logout() {
        SecurityUtils.getSubject().logout();
        log.info("------用户已注销------");
        return "redirect:/login";
    }

    /**
     * 跳转注册页面
     * @return 注册页面
     */
    @GetMapping("register")
    @ApiOperation("用户注册页面")
    public String register(){
        log.info("------用户注册页面------");
        return "register";
    }

    /**
     * 权限不足页面
     */
    @GetMapping("/noauth")
    @ApiOperation("权限不足页面")
    public String noAuth() {
        log.info("------权限不足页面------");
        return "noauth";
    }

}

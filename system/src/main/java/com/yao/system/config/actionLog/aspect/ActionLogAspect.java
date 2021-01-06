package com.yao.system.config.actionLog.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yao.common.util.ServletUtils;
import com.yao.common.util.StringUtils;
import com.yao.system.config.actionLog.annotation.Operation;
import com.yao.system.config.shiro.util.ShiroUtils;
import com.yao.system.entity.ActionLog;
import com.yao.system.service.ActionLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author YL
 * @date 2020/11/23 15:36
 * @description 操作日志切面处理类
 */
@Aspect
@Component
@Slf4j
public class ActionLogAspect {
    @Resource
    private ActionLogService actionLogService;

    /** 正常操作的返回值信息 */
    private static final String SUCCESS ="success";

    /**
     * 定义切点 @Pointcut
     * 在注解的位置切入代码
     */
    @Pointcut("@annotation( com.yao.system.config.actionLog.annotation.Operation)")
    public void logPoinCut() {
    }

    /**
     * 正常日志
     * 切面 配置通知
     */
    @AfterReturning( pointcut = "logPoinCut()",returning = "obj")
    public void saveNormalLog(JoinPoint joinPoint,Object obj) {
        log.info("=====正常操作日志切面处理=====");
        //保存日志
        ActionLog actionLog=new ActionLog();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取操作
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null) {
            //保存获取的日志名称
            String name = operation.name();
            actionLog.setName(name);
            //保存获取的日志消息
            if (StringUtils.isNull(obj)){
                //没有返回值代表接口返回类型为void
                actionLog.setMessage(name+"成功");
            } else {
                String str = JSON.toJSONString(obj);
                JSONObject jsonObject = JSONObject.parseObject(str);
                String message=jsonObject.getString("msg");
                if (SUCCESS.equals(message)){
                    actionLog.setMessage(name+"成功");
                }else {
                    actionLog.setMessage(name+"失败："+message);
                }
            }
        }
        //保存请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        actionLog.setClazz(className);
        //保存请求的方法名
        String methodName = method.getName();
        actionLog.setMethod(methodName);
        //获取用户ip地址
        String ipaddr = ServletUtils.getIp();
        actionLog.setIpaddr(ipaddr);
        //未登录时和放行的操作获取不到用户对象
        if (StringUtils.isNotNull(ShiroUtils.getSubject())){
            //获取用户名
            String operBy= ShiroUtils.getSubject().getNickname();
            actionLog.setOperBy(operBy);
        }
        //调用service保存SysLog实体类到数据库
        actionLogService.addActionLog(actionLog);
    }

    /**
     * 异常日志
     * 切面 配置通知
     */
    @AfterThrowing( pointcut = "logPoinCut()",throwing = "throwable")
    public void saveNormalLog(JoinPoint joinPoint,Throwable throwable) {
        log.info("=====异常操作日志切面处理=====");
        //保存日志
        ActionLog actionLog=new ActionLog();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        //获取操作
        Operation operation = method.getAnnotation(Operation.class);
        if (operation != null) {
            //保存获取的日志名称
            String name = operation.name();
            actionLog.setName(name);
        }
        //保存获取的日志消息
        actionLog.setMessage(throwable.getClass().getName()+":"+throwable.getMessage());
        //保存请求的类名
        String className = joinPoint.getTarget().getClass().getName();
        actionLog.setClazz(className);
        //保存请求的方法名
        String methodName = method.getName();
        actionLog.setMethod(methodName);
        //获取用户ip地址
        String ipaddr = ServletUtils.getIp();
        actionLog.setIpaddr(ipaddr);
        //未登录时和放行的操作获取不到用户对象
        if (StringUtils.isNotNull(ShiroUtils.getSubject())){
            //获取用户名
            String operBy= ShiroUtils.getSubject().getNickname();
            actionLog.setOperBy(operBy);
        }
        //调用service保存SysLog实体类到数据库
        actionLogService.addActionLog(actionLog);
    }

}

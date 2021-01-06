package com.yao.system.config.actionLog.annotation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * @author YL
 * @date 2020/11/23 15:30
 * @description 自定义操作日志注解类
 */
@Target({METHOD, TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Operation {
    // 日志名称
    String name() default "";
}

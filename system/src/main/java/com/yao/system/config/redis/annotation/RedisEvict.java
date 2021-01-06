package com.yao.system.config.redis.annotation;

import java.lang.annotation.*;

/**
 * redis删除缓存注解
 * @author ruoyi
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisEvict
{
    String key();

    String fieldKey();
}
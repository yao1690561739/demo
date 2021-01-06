package com.yao.system.config.shiro.util;

import com.yao.system.entity.User;
import org.apache.shiro.SecurityUtils;

import java.util.Random;

/**
 * @author YL
 * @date 2020/11/23 16:56
 * @description shiro工具类
 */
public class ShiroUtils {

    /**
     * 生成随机盐
     */
    public static String getSalt(int n){
        char[] chars="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()".toCharArray();
        StringBuilder sb=new StringBuilder();
        for (int i = 0; i < n; i++) {
            int x=new Random().nextInt(chars.length);
            char aChar=chars[x];
            sb.append(aChar);
        }
        return sb.toString();
    }

    /**
     * 获取当前用户对象
     */
    public static User getSubject() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }
}

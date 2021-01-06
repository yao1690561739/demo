package com.yao.system.config.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author YL
 * @date 2020/11/19 9:48
 * @description JWT token类，用于存放token
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

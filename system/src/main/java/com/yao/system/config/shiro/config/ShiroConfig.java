package com.yao.system.config.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.yao.system.config.shiro.UserRealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author YL
 * @date 2020/9/30 10:43
 * @description shiro配置
 */
@Configuration
public class ShiroConfig {

    /**
     * 1.创建SHiroFilterFactoryBean
     * 用于拦截需要安全控制的请求进行处理
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //设置Shiro内置过滤器
        Map<String, String> filterMap = new LinkedHashMap<>();
        //无需认证直接放行
        filterMap.put("/login", "anon");
        filterMap.put("/system/user/login", "anon");
        filterMap.put("/register", "anon");
        filterMap.put("/system/user/register", "anon");
        filterMap.put("/system/user/captcha", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/js/**", "anon");
        filterMap.put("/img/**", "anon");
        filterMap.put("/lib/**", "anon");
        //放行swagger
        filterMap.put("/swagger-ui.html", "anon");
        filterMap.put("/swagger/**", "anon");
        filterMap.put("/swagger-resources/**", "anon");
        filterMap.put("/v2/**", "anon");
        filterMap.put("/webjars/**", "anon");
        //授权过滤器
        //认证过滤器
        //!!!!注意，根路径必须放在最下边，否则下面的过滤器不会生效!!!!
        filterMap.put("/**", "authc");
        //添加配置到内置过滤器
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        //设置跳转的登录页面，否则默认login.jsp
        shiroFilterFactoryBean.setLoginUrl("/login");
        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");
        return shiroFilterFactoryBean;
    }

    /**
     * 2.创建安全管理器
     */
    @Bean("defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联realm
        securityManager.setRealm(userRealm);
        //设置会话
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm() {
        UserRealm userRealm=new UserRealm();
        //设置密码匹配方式
        userRealm.setCredentialsMatcher(credentialsMatcher());
        return userRealm;
    }

    /**
     * 修改凭证校验器
     */
    @Bean
    public CredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher hashedMatcher = new HashedCredentialsMatcher();
        //使用MD5散列算法
        hashedMatcher.setHashAlgorithmName("md5");
        //散列次数
        hashedMatcher.setHashIterations(1024);
        //storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        hashedMatcher.setStoredCredentialsHexEncoded(true);
        return hashedMatcher;
    }

    /**
     * 配置ShiroDialect
     * 用于thymeleaf和Shiro标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    /**
     * Session Manager：会话管理
     * 即用户登录后就是一次会话，在没有退出之前，它的所有信息都在会话中；
     * 会话可以是普通JavaSE环境的，也可以是如Web环境的；
     */
    @Bean("sessionManager")
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置会话过期时间
        //默认半小时
        sessionManager.setGlobalSessionTimeout(60*60*1000);
        //默认自定调用SessionDAO的delete方法删除会话
        sessionManager.setDeleteInvalidSessions(true);
        //设置会话定时检查
        //默认一小时
        sessionManager.setSessionValidationInterval(60*60*1000);
        sessionManager.setSessionValidationSchedulerEnabled(false);
        //重定向时不显示JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    /**
     * 启用shrio授权注解拦截方式，AOP式方法级权限检查
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor =
                new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}

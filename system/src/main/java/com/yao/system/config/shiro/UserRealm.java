package com.yao.system.config.shiro;

import com.yao.common.constant.StatusConst;
import com.yao.common.util.StringUtils;
import com.yao.system.entity.Menu;
import com.yao.system.entity.Role;
import com.yao.system.entity.User;
import com.yao.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Set;

/**
 * @author YL
 * @date 2020/9/30 8:24
 * @description 自定义realm
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     * doGetAuthorizationInfo
     * 执行授权逻辑
     * 获取权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("执行授权逻辑，获取权限信息！");
        //给资源授权
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        //获取当前用户
        User user=(User) principalCollection.getPrimaryPrincipal();
        //添加用户角色
        Set<Role> roles=userService.getUserById(user.getId()).getRoles();
        roles.forEach(role -> {
            String name=role.getName();
            if(!StringUtils.isEmpty(name)){
                info.addRoles(Collections.singleton(name));
            }
        });
        //添加资源的授权字符串
        Set<Menu> menus=userService.getUserById(user.getId()).getMenus();
        menus.forEach(menu -> {
            String perms=menu.getPerms();
            if(!StringUtils.isEmpty(perms)){
                info.addStringPermissions(Collections.singleton(perms));
            }
        });
        return info;
    }

    /**
     * doGetAuthenticationInfo
     * 执行认证逻辑
     * 获取认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("执行认证逻辑，获取认证信息！");
        //编写Shiro判断逻辑，判断用户名和密码
        //1.判断用户是否存在
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        User user=userService.getUserByName((String) token.getPrincipal());
        if (user==null){
            //用户名不存在
            return null;
        }
        if (user.getStatus().equals(StatusConst.FREEZED)){
            //账号被冻结
            throw new LockedAccountException();
        }
        if (user.getStatus().equals(StatusConst.DELETE)){
            //账号被禁用
            throw new DisabledAccountException();
        }
        //2.与数据库中用户名、密码和生成的realm名匹对
        AuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getSalt()),this.getName());
        log.info("该用户信息为：[{}]",authenticationInfo);
        return authenticationInfo;
    }
}

package com.yao.system.vo;

import com.yao.system.entity.Menu;
import com.yao.system.entity.Role;
import com.yao.system.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * @author YL
 * @date 2020/9/25 16:10
 * @description 用户信息视图
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVo extends User {

    /** 部门名称 */
    private String deptName;

    /** 创建者名称 */
    private String createName;

    /** 更新者名称 */
    private String updateName;

    /** 用户角色 */
    private Set<Role> roles;

    /** 用户菜单权限 */
    private Set<Menu> menus;
}

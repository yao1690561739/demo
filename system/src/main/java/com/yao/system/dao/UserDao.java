package com.yao.system.dao;

import com.yao.system.dto.PasswordDto;
import com.yao.system.dto.UserDto;
import com.yao.system.entity.Menu;
import com.yao.system.entity.Role;
import com.yao.system.entity.User;
import com.yao.system.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author YL
 * @date 2020/9/25 9:13
 * @description 用户数据接口
 */
@Mapper
public interface UserDao {

    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    UserVo getUserById(Integer id);

    /**
     * 根据用户ID获取密码
     * @param id 用户ID
     * @return 密码
     */
    String getPwdById(Integer id);

    /**
     * 根据用户ID获取用户角色
     * @param id 用户ID
     * @return 角色
     */
    Set<Role> getRoleByUserId(Integer id);

    /**
     * 根据用户ID获取用户菜单权限
     * @param id 用户ID
     * @return 菜单权限
     */
    Set<Menu> getMenuByUserId(Integer id);

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return 用户信息
     */
    User getUserByName(String username);

    /**
     * 按条件获取用户信息
     * @param userDto 实体对象
     * @return 用户信息
     */
    List<UserVo> getUsers(UserDto userDto);

    /**
     * 添加用户信息
     * @param user 实体对象
     * @return 影响行数
     */
    int addUser(User user);

    /**
     * 修改除密码外的用户信息
     * @param user 实体对象
     * @return 影响行数
     */
    int editUser(User user);

    /**
     * 修改密码
     * @param passwordDto 密码信息
     * @return 影响行数
     */
    int editPwd(PasswordDto passwordDto);

    /**
     * 批量处理用户状态
     * @param status 用户状态
     * @param ids 用户ID集合
     * @return 影响行数
     */
    int updateStatus(Integer status,String ids);
}

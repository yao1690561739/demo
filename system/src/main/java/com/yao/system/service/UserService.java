package com.yao.system.service;

import com.yao.common.enums.StatusEnum;
import com.yao.system.dto.UserDto;
import com.yao.system.entity.User;
import com.yao.system.dto.PasswordDto;
import com.yao.system.vo.UserVo;

import java.util.List;

/**
 * @author YL
 * @date 2020/9/25 14:42
 * @description 用户业务接口
 */
public interface UserService {
    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    UserVo getUserById(Integer id);

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
     * 修改用户信息
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
     * @param statusEnum 用户状态码
     * @param ids 用户ID集合
     * @return 影响行数
     */
    int updateStatus(StatusEnum statusEnum, String ids);
}

package com.yao.system.service.impl;

import com.yao.common.enums.StatusEnum;
import com.yao.system.config.redis.annotation.RedisCache;
import com.yao.system.config.redis.annotation.RedisEvict;
import com.yao.system.config.shiro.util.ShiroUtils;
import com.yao.system.dao.UserDao;
import com.yao.system.dto.PasswordDto;
import com.yao.system.dto.UserDto;
import com.yao.system.entity.Menu;
import com.yao.system.entity.Role;
import com.yao.system.entity.User;
import com.yao.system.service.UserService;
import com.yao.system.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author YL
 * @date 2020/9/25 14:43
 * @description 用户业务接口实现
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
     * 根据ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    @Override
    @RedisCache(key = "user", fieldKey = "#id")
    public UserVo getUserById(Integer id) {
        log.info("获取用户信息！");
        UserVo userVo=userDao.getUserById(id);
        log.info("获取用户角色信息！");
        Set<Role> roles=userDao.getRoleByUserId(id);
        log.info("获取用户菜单权限信息！");
        Set<Menu> menus=userDao.getMenuByUserId(id);
        userVo.setRoles(roles);
        userVo.setMenus(menus);
        log.info("根据ID查询用户信息！");
        return userVo;
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return 用户信息
     */
    @Override
    public User getUserByName(String username) {
        log.info("根据用户名查询用户信息！");
        return userDao.getUserByName(username);
    }

    /**
     * 按条件获取用户信息
     *
     * @param userDto 实体对象
     * @return 用户信息
     */
    @Override
    public List<UserVo> getUsers(UserDto userDto) {
        log.info("查询用户信息列表！");
        return userDao.getUsers(userDto);
    }

    /**
     * 添加用户信息
     *
     * @param user 实体对象
     * @return 影响行数
     */
    @Override
    public int addUser(User user) {
        //添加用户默认为正常状态
        user.setStatus(0);
        //用户密码加密过程
        //1.生成随机盐
        String salt= ShiroUtils.getSalt(8);
        //2.将随机盐保存到数据
        user.setSalt(salt);
        //3.对明文密码进行md5+salt+hash散列
        Md5Hash md5Hash=new Md5Hash(user.getPassword(),salt,1024);
        //4.将加密密码存入数据
        user.setPassword(md5Hash.toHex());
        log.info("添加用户信息！");
        return userDao.addUser(user);
    }

    /**
     * 修改用户信息
     *
     * @param user 实体对象
     * @return 影响行数
     */
    @Override
    @RedisEvict(key = "user", fieldKey = "#user.id")
    public int editUser(User user) {
        log.info("修改用户信息！");
        return userDao.editUser(user);
    }

    /**
     * 修改密码
     *
     * @param passwordDto 密码信息
     * @return 影响行数
     */
    @Override
    public int editPwd(PasswordDto passwordDto) {
        //用户密码加密过程
        //1.生成随机盐
        String salt= ShiroUtils.getSalt(8);
        //2.将随机盐保存到数据
        passwordDto.setSalt(salt);
        //3.对明文密码进行md5+salt+hash散列
        Md5Hash md5Hash=new Md5Hash(passwordDto.getNewPwd(),salt,1024);
        //4.将加密密码存入数据
        passwordDto.setNewPwd(md5Hash.toHex());
        log.info("修改密码！");
        return userDao.editPwd(passwordDto);
    }

    /**
     * 批量处理用户状态
     *
     * @param statusEnum 用户状态码
     * @param ids        用户ID集合
     * @return 影响行数
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    @RedisEvict(key = "user", fieldKey = "#ids")
    public int updateStatus(StatusEnum statusEnum, String ids) {
        log.info("修改用户状态！");
        return userDao.updateStatus(statusEnum.getCode(),ids);
    }
}

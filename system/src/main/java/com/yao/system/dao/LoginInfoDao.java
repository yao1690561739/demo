package com.yao.system.dao;

import com.yao.system.dto.LoginInfoDto;
import com.yao.system.entity.LoginInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author YL
 * @date 2020/11/24 9:54
 * @description 登录记录接口
 */
@Mapper
public interface LoginInfoDao {
    /**
     * 根据ID获取登录记录
     * @param id 登录记录ID
     * @return 登录记录信息
     */
    LoginInfo getLoginInfoById(Integer id);
    /**
     * 按条件获取登录记录信息列表
     * @param loginInfoDto 实体对象
     * @return 登录记录信息
     */
    List<LoginInfo> getLoginInfos(LoginInfoDto loginInfoDto);
    /**
     * 添加登录记录信息
     * @param loginInfo 实体对象
     * @return 影响行数
     */
    int addLoginInfo(LoginInfo loginInfo);
    /**
     * 批量删除登录记录信息
     * @param ids 登录记录ID
     * @return 影响行数
     */
    int removeLoginInfos(String[] ids);
    /**
     * 清空登录记录信息
     * @return 影响行数
     */
    int removeAllLoginInfos();
}

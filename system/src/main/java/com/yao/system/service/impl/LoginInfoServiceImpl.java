package com.yao.system.service.impl;

import cn.hutool.core.convert.Convert;
import com.yao.common.enums.ResultEnum;
import com.yao.common.util.ServletUtils;
import com.yao.common.util.StringUtils;
import com.yao.system.dao.LoginInfoDao;
import com.yao.system.dao.UserDao;
import com.yao.system.dto.LoginInfoDto;
import com.yao.system.entity.LoginInfo;
import com.yao.system.service.LoginInfoService;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author YL
 * @date 2020/11/24 10:08
 * @description 登录记录实现类
 */
@Service
@Slf4j
public class LoginInfoServiceImpl implements LoginInfoService {

    @Resource
    private LoginInfoDao loginInfoDao;
    @Resource
    private UserDao userDao;
    /**
     * 根据ID获取登录记录
     *
     * @param id 登录记录ID
     * @return 登录记录信息
     */
    @Override
    public LoginInfo getLoginInfoById(Integer id) {
        log.info("查询登录记录信息！");
        return loginInfoDao.getLoginInfoById(id);
    }

    /**
     * 按条件获取登录记录信息列表
     *
     * @param loginInfoDto 实体对象
     * @return 登录记录信息
     */
    @Override
    public List<LoginInfo> getLoginInfos(LoginInfoDto loginInfoDto) {
        log.info("查询登录记录列表！");
        return loginInfoDao.getLoginInfos(loginInfoDto);
    }

    /**
     * 添加登录记录信息
     *
     * @param loginInfo 实体对象
     * @return 影响行数
     */
    @Override
    public int addLoginInfo(LoginInfo loginInfo) {
        //根据用户账号获取用户名
        String loginAccount=loginInfo.getLoginAccount();
        if (!StringUtils.isEmpty(loginAccount)){
            String loginName=userDao.getUserByName(loginAccount).getNickname();
            loginInfo.setLoginName(loginName);
        }
        //获取用户登录状态
        String msg=loginInfo.getMsg();
        if (ResultEnum.USER_SUCCESS.getMessage().equals(msg)){
            loginInfo.setStatus(ResultEnum.SUCCESS.getMessage());
        }else {
            loginInfo.setStatus(ResultEnum.ERROR.getMessage());
        }
        HttpServletRequest request = ServletUtils.getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        loginInfo.setOs(os);
        // 获取客户端浏览器
        Browser browser = userAgent.getBrowser();
        loginInfo.setBrowser(browser.getName());
        //获取IP地址
        String ipaddr = ServletUtils.getIp();
        loginInfo.setIpaddr(ipaddr);
        log.info("添加登录记录信息！");
        return loginInfoDao.addLoginInfo(loginInfo);
    }

    /**
     * 批量删除登录记录信息
     *
     * @param ids 登录记录ID
     * @return 影响行数
     */
    @Override
    public int removeLoginInfos(String ids) {
        log.info("批量删除登录记录信息！");
        return loginInfoDao.removeLoginInfos(Convert.toStrArray(ids));
    }

    /**
     * 清空登录记录信息
     *
     * @return 影响行数
     */
    @Override
    public int removeAllLoginInfos() {
        log.info("清空登录记录信息！");
        return loginInfoDao.removeAllLoginInfos();
    }
}

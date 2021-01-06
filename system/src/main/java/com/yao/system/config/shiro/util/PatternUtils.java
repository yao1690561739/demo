package com.yao.system.config.shiro.util;

import com.yao.common.enums.ResultEnum;
import com.yao.common.util.ResultUtils;
import com.yao.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @author YL
 * @date 2020/9/29 9:52
 * @description 用户名、密码校验工具
 */
@Component
public class PatternUtils {

    @Value("${register.rule.username:true}")
    private boolean registerUsername;
    @Value("${register.rule.nickname:true}")
    private boolean registerNickname;
    @Value("${register.rule.password:true}")
    private boolean registerPassword;
    @Value("${register.rule.email:true}")
    private boolean registerEmail;
    @Value("${register.rule.phone:true}")
    private boolean registerPhone;

    /**
     * 用户名规则,只能包含字母、数字、下划线
     */
    private static final Pattern USERNAME_PATTERN =Pattern.compile("[a-zA-Z0-9_]+");
    /**
     * 用户名最小长度
     */
    private static final int USER_LENGTH_MIN=4;
    /**
     * 用户名最大长度
     */
    private static final int USER_LENGTH_MAX=16;
    /**
     * 中文昵称规则
     */
    private static final Pattern NICKNAME_ZH_PATTERN=Pattern.compile("^(?:[\\u4e00-\\u9fa5·]{2,16})$");
    /**
     * 英文昵称规则
     */
    private static final Pattern NICKNAME_EN_PATTERN=Pattern.compile("(^[a-zA-Z][a-zA-Z\\s]{0,20}[a-zA-Z]$)");
    /**
     * 密码规则,必须同时包含大小写字母、数字、_或&符号
     */
    private static final Pattern PASSWORD_PATTERN =Pattern.compile("(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[_&])[A-Za-z0-9_&]+");
    /**
     * 密码最小长度
     */
    private static final int PWD_LENGTH_MIN=8;
    /**
     * 密码最大长度
     */
    private static final int PWD_LENGTH_MAX=100;
    /**
     * 邮箱规则
     */
    private static final Pattern EMAIL_PATTERN=Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z]+)+$");

    /**
     * 手机号规则，13-19开头，长度为11位
     */
    private static final Pattern PHONE_PATTERN=Pattern.compile("^(?:(?:\\+|00)86)?1[3-9]\\d{9}$");

    /**
     * 用户名规则校验
     * @param username 用户名
     * @return 校验结果
     */
    public ResultUtils verifyUser(String username){

        //判断用户名是否符合规则
        if (StringUtils.isEmpty(username)||StringUtils.isNull(username)){
            //用户名不能为空
            return ResultUtils.error(ResultEnum.USER_NAME_NULL);
        }
        if (registerUsername){
            if (!USERNAME_PATTERN .matcher(username).matches()){
                //用户名只能由字母、数字、下划线组成
                return ResultUtils.error(ResultEnum.USER_NAME_ERROR);
            }
            if (username.length()<USER_LENGTH_MIN||username.length()>USER_LENGTH_MAX){
                //用户名长度要求在4到16位之间
                return ResultUtils.error(ResultEnum.USER_NAME_LENGTH);
            }
        }
        return null;
    }

    /**
     * 昵称校验
     * @param nickname 昵称
     * @return 校验结果
     */
    public ResultUtils verifyNick(String nickname){
        if (StringUtils.isEmpty(nickname)||StringUtils.isNull(nickname)){
            //判断昵称是否为空
            return ResultUtils.error(ResultEnum.USER_NICK_NULL);
        }
        if (registerNickname){
            if (!NICKNAME_ZH_PATTERN.matcher(nickname).matches()&&!NICKNAME_EN_PATTERN.matcher(nickname).matches()){
                //判断昵称是否符合规则
                return ResultUtils.error(ResultEnum.USER_NICK_ERROR);
            }
        }
        return null;
    }

    /**
     * 密码规则校验
     * @param password 密码
     * @return 校验结果
     */
    public ResultUtils verifyPwd(String password){

        //判断密码是否符合规则
        if (StringUtils.isEmpty(password)||StringUtils.isNull(password)){
            //判断密码是否为空
            return ResultUtils.error(ResultEnum.USER_PWD_NULL);
        }
        if (registerPassword){
            if (!PASSWORD_PATTERN.matcher(password).matches()){
                //密码必须包含大小写字母、数字、下划线或&符号
                return ResultUtils.error(ResultEnum.USER_PWD_ERROR);
            }
            if (password.length()<PWD_LENGTH_MIN){
                //密码长度不得少于8位
                return ResultUtils.error(ResultEnum.USER_PWD_LENGTH_MIN);
            }
            if (password.length()>PWD_LENGTH_MAX){
                //密码长度不得多于50位
                return ResultUtils.error(ResultEnum.USER_PWD_LENGTH_MAX);
            }
        }
        return null;
    }

    /**
     * 邮箱规则校验
     * @param email 邮箱地址
     * @return 校验结果
     */
    public ResultUtils verifyEmail(String email){
        if (StringUtils.isEmpty(email)||StringUtils.isNull(email)){
            //邮箱地址不能为空
            return ResultUtils.error(ResultEnum.USER_EMAIL_NULL);
        }
        if (registerEmail){
            if (!EMAIL_PATTERN.matcher(email).matches()){
                //判断邮箱地址是否符合规则
                return ResultUtils.error(ResultEnum.USER_EMAIL_ERROR);
            }
        }
        return null;
    }

    /**
     * 手机号规则校验
     * @param phone 手机号
     * @return 校验结果
     */
    public ResultUtils verifyPhone(String phone){
        if (StringUtils.isEmpty(phone)||StringUtils.isNull(phone)){
            //手机号不能为空
            return ResultUtils.error(ResultEnum.USER_PHONE_NULL);
        }
        if (registerPhone){
            if (!PHONE_PATTERN.matcher(phone).matches()){
                //判断手机号是符合规则
                return ResultUtils.error(ResultEnum.USER_PHONE_ERROR);
            }
        }
        return null;
    }
}

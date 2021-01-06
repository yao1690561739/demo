package com.yao.system.controller;

import com.yao.common.constant.AdminConst;
import com.yao.common.controller.BaseController;
import com.yao.common.enums.ResultEnum;
import com.yao.common.enums.StatusEnum;
import com.yao.common.util.ResultUtils;
import com.yao.common.util.StringUtils;
import com.yao.common.util.VerifyCodeUtils;
import com.yao.system.config.actionLog.annotation.Operation;
import com.yao.system.config.shiro.util.PatternUtils;
import com.yao.system.dao.UserDao;
import com.yao.system.dto.PasswordDto;
import com.yao.system.dto.UserDto;
import com.yao.system.entity.LoginInfo;
import com.yao.system.entity.User;
import com.yao.system.service.LoginInfoService;
import com.yao.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author YL
 * @date 2020/9/25 15:10
 * @description 用户操作控制层
 */
@RestController
@Api(tags = "用户管理接口")
@RequestMapping("/system/user")
@Slf4j
public class UserController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private UserDao userDao;
    @Resource
    private LoginInfoService loginInfoService;
    @Resource
    private PatternUtils patternUtils;
    /**
     * 用户登录
     * @param user 用户信息
     * @return 结果
     */
    @PostMapping("/login")
    @ApiOperation("用户登录接口")
    @Operation(name = "用户登录")
    public ResultUtils login(@RequestBody User user) {
        //输入的用户名和密码
        String username=user.getUsername();
        String password=user.getPassword();
        //保存到登陆记录
        LoginInfo loginInfo=new LoginInfo();
        loginInfo.setLoginAccount(username);
        //用户名为空
        if (StringUtils.isEmpty(username)){
            log.error(ResultEnum.USER_NAME_NULL.getMessage());
            loginInfo.setMsg(ResultEnum.USER_NAME_NULL.getMessage());
            //添加到登录记录表中
            loginInfoService.addLoginInfo(loginInfo);
            return ResultUtils.error(ResultEnum.USER_NAME_NULL);
        }
        //密码为空
        if (StringUtils.isEmpty(password)){
            log.error(ResultEnum.USER_PWD_NULL.getMessage());
            loginInfo.setMsg(ResultEnum.USER_PWD_NULL.getMessage());
            //添加到登录记录表中
            loginInfoService.addLoginInfo(loginInfo);
            return ResultUtils.error(ResultEnum.USER_PWD_NULL);
        }
        //1.获取Subject，当前用户
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //3.执行登录方法
        try {
            log.info("开始登录校验，登录用户为：[{}]",user.getUsername());
            subject.login(token);
            log.info(ResultEnum.USER_SUCCESS.getMessage());
            loginInfo.setMsg(ResultEnum.USER_SUCCESS.getMessage());
            return ResultUtils.ok();
        } catch (UnknownAccountException e) {
            //用户不存在
            log.error(ResultEnum.USER_NOT_EXIST.getMessage());
            loginInfo.setMsg(ResultEnum.USER_NOT_EXIST.getMessage());
            return ResultUtils.error(ResultEnum.USER_NOT_EXIST);
        } catch (LockedAccountException e) {
            //用户被冻结
            log.error(ResultEnum.USER_FREEZED.getMessage());
            loginInfo.setMsg(ResultEnum.USER_FREEZED.getMessage());
            return ResultUtils.error(ResultEnum.USER_FREEZED);
        } catch (DisabledAccountException e) {
            //用户被禁用
            log.error(ResultEnum.USER_FORBIDDEN.getMessage());
            loginInfo.setMsg(ResultEnum.USER_FORBIDDEN.getMessage());
            return ResultUtils.error(ResultEnum.USER_FORBIDDEN);
        } catch (IncorrectCredentialsException e) {
            //密码错误
            log.error(ResultEnum.USER_PWD_NOT_MATCH.getMessage());
            loginInfo.setMsg(ResultEnum.USER_PWD_NOT_MATCH.getMessage());
            return ResultUtils.error(ResultEnum.USER_PWD_NOT_MATCH);
        } finally {
            //添加到登录记录表中
            loginInfoService.addLoginInfo(loginInfo);
        }
    }

    /**
     * 生成验证码图片
     * @param request 请求
     * @return 结果
     * @throws IOException 文件流异常
     */
    @GetMapping("captcha")
    @ApiOperation("生成验证码")
    public String getImageCode(HttpServletRequest request) throws IOException {
        //1.生成工具类生成4位验证码
        String code = VerifyCodeUtils.generateVerifyCode(4);
        //2.将验证码放入session对象作用域
        HttpSession session = request.getSession();
        //设置会话超时时间
        session.setMaxInactiveInterval(600);
        session.setAttribute("code",code);
        //3.将图片转为base64
        //创建一个字节数组缓冲区,所有发送到输出流的数据保存在该字节数组缓冲区中
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        //将验证码生成图片
        VerifyCodeUtils.outputImage(233,50,byteArrayOutputStream,code);
        //将图片转为base64字节码并返回
        return "data:image/png;base64,"+ Base64Utils.encodeToString(byteArrayOutputStream.toByteArray());
    }

    /**
     * 用户注册
     * @param user 用户信息
     * @param code 验证码
     * @param request 请求
     * @return 结果
     */
    @PostMapping("/register")
    @ApiOperation("用户注册接口")
    @Operation(name = "用户注册")
    public ResultUtils register(@RequestBody User user, String code, HttpServletRequest request){
        //获取验证码
        String key= (String) request.getSession().getAttribute("code");
        //输入的用户名和密码等信息
        String username=user.getUsername();
        String password=user.getPassword();
        String nickname=user.getNickname();
        String email=user.getEmail();
        String phone=user.getPhone();
        //用户名已存在
        if (userDao.getUserByName(username)!=null){
            log.error(ResultEnum.USER_EXIST.getMessage());
            return ResultUtils.error(ResultEnum.USER_EXIST);
        }
        //用户名校验
        ResultUtils rUser=patternUtils.verifyUser(username);
        if (StringUtils.isNotNull(rUser)){
            log.error("用户名不符合规则");
            return rUser;
        }
        //昵称校验
        ResultUtils rNick=patternUtils.verifyNick(nickname);
        if (StringUtils.isNotNull(rNick)){
            log.error("昵称不符合规则");
            return rNick;
        }
        //密码校验
        ResultUtils rPwd=patternUtils.verifyPwd(password);
        if (StringUtils.isNotNull(rPwd)){
            log.error("密码不符合规则");
            return rPwd;
        }
        //邮箱地址校验
        ResultUtils rEmail=patternUtils.verifyEmail(email);
        if (StringUtils.isNotNull(rEmail)){
            log.error("邮箱不符合规则");
            return rEmail;
        }
        //手机号校验
        ResultUtils rPhone=patternUtils.verifyPhone(phone);
        if (StringUtils.isNotNull(rPhone)){
            log.error("手机号不符合规则");
            return rPhone;
        }
        //验证码校验
        if (StringUtils.isNull(key)){
            //验证码超时
            log.error(ResultEnum.USER_CAPTCHA_OUT.getMessage());
            return ResultUtils.error(ResultEnum.USER_CAPTCHA_OUT);
        } else if (!key.equals(code)){
            //验证码错误
            log.error(ResultEnum.USER_CAPTCHA_ERROR.getMessage());
            return ResultUtils.error(ResultEnum.USER_CAPTCHA_ERROR);
        }
        log.info("用户注册成功！");
        return toAjax(userService.addUser(user));
    }

    /**
     * 根据ID获取用户信息
     * @param id 用户ID
     * @return 用户信息
     */
    @GetMapping("/get/{id}")
    @ApiOperation("根据ID查询用户信息")
    @ApiImplicitParam(name = "id",value = "用户ID",defaultValue = "1",required = true)
    @Operation(name = "根据ID查询用户信息")
    public ResultUtils get(@PathVariable("id") Integer id){
        return result(userService.getUserById(id));
    }

    /**
     * 获取用户列表
     * @param userDto 用户信息
     * @return 用户列表
     */
    @GetMapping("/list")
    @ApiOperation("查询用户信息列表")
    @Operation(name = "查询用户信息列表")
    public ResultUtils list(UserDto userDto){
        startPage();
        return result(userService.getUsers(userDto));
    }

    /**
     * 添加用户信息
     * @param user 用户信息
     * @return 影响行数
     */
    @PostMapping("/add")
    @ApiOperation("添加用户信息")
    @Operation(name = "添加用户信息")
    public ResultUtils add(@RequestBody User user){
        //输入的用户名和密码等信息
        String username=user.getUsername();
        String password=user.getPassword();
        String nickname=user.getNickname();
        String email=user.getEmail();
        String phone=user.getPhone();
        //用户名已存在
        if (userDao.getUserByName(username)!=null){
            log.error("用户名已存在");
            return ResultUtils.error(ResultEnum.USER_EXIST);
        }
        //用户名校验
        ResultUtils rUser=patternUtils.verifyUser(username);
        if (StringUtils.isNotNull(rUser)){
            log.error("用户名不符合规则");
            return rUser;
        }
        //密码校验
        ResultUtils rPwd=patternUtils.verifyPwd(password);
        if (StringUtils.isNotNull(rPwd)){
            log.error("密码不符合规则");
            return rPwd;
        }
        //昵称校验
        ResultUtils rNick=patternUtils.verifyNick(nickname);
        if (StringUtils.isNotNull(rNick)){
            log.error("昵称不符合规则");
            return rNick;
        }
        //邮箱地址校验
        ResultUtils rEmail=patternUtils.verifyEmail(email);
        if (StringUtils.isNotNull(rEmail)){
            log.error("邮箱不符合规则");
            return rEmail;
        }
        //手机号校验
        ResultUtils rPhone=patternUtils.verifyPhone(phone);
        if (StringUtils.isNotNull(rPhone)){
            log.error("手机号不符合规则");
            return rPhone;
        }
        return toAjax(userService.addUser(user));
    }

    /**
     * 修改用户信息
     * @param user 用户信息
     * @return 影响行数
     */
    @PutMapping("/edit")
    @ApiOperation("修改用户信息")
    @Operation(name = "修改用户信息")
    public ResultUtils edit(@RequestBody User user){
        //输入的用户名等信息
        String username=user.getUsername();
        String nickname=user.getNickname();
        String email=user.getEmail();
        String phone=user.getPhone();
        if (StringUtils.isNotEmpty(username)){
            //修改时的用户名
            String name=userService.getUserById(user.getId()).getUsername();
            if (!username.equals(name) && userDao.getUserByName(username)!=null){
                //用户名已存在
                log.error(ResultEnum.USER_EXIST.getMessage());
                return ResultUtils.error(ResultEnum.USER_EXIST);
            }
            //用户名校验
            ResultUtils rUser=patternUtils.verifyUser(username);
            if (StringUtils.isNotNull(rUser)){
                log.error("用户名不符合规则");
                return rUser;
            }
        }
        if (StringUtils.isNotEmpty(nickname)){
            //昵称校验
            ResultUtils rNick=patternUtils.verifyNick(nickname);
            if (StringUtils.isNotNull(rNick)){
                log.error("昵称不符合规则");
                return rNick;
            }
        }
        if (StringUtils.isNotEmpty(email)){
            //邮箱地址校验
            ResultUtils rEmail=patternUtils.verifyEmail(email);
            if (StringUtils.isNotNull(rEmail)){
                log.error("邮箱不符合规则");
                return rEmail;
            }
        }
        if (StringUtils.isNotEmpty(phone)){
            //手机号校验
            ResultUtils rPhone=patternUtils.verifyPhone(phone);
            if (StringUtils.isNotNull(rPhone)){
                log.error("手机号不符合规则");
                return rPhone;
            }
        }
        return toAjax(userService.editUser(user));
    }

    /**
     * 修改个人密码
     * @param passwordDto 密码信息
     * @return 结果
     */
    @PutMapping("selfpwd")
    @ApiOperation("修改个人密码")
    @Operation(name = "修改用户密码")
    public ResultUtils selfpwd(@RequestBody PasswordDto passwordDto){
        //当前用户ID
        Integer id= passwordDto.getId();
        //当前用户密码
        String password=userDao.getPwdById(id);
        //输入旧密码
        String oldPwd= passwordDto.getOldPwd();
        //输入新密码
        String newPwd= passwordDto.getNewPwd();
        //输入确认密码
        String confirmPwd= passwordDto.getConfirmPwd();
        //输入原密码不能为空
        if (StringUtils.isEmpty(oldPwd)){
            log.error(ResultEnum.USER_PWD_NULL.getMessage());
            return ResultUtils.error(ResultEnum.USER_PWD_NULL);
        }
        //原密码不正确
        if (!password.equals(oldPwd)){
            log.error(ResultEnum.USER_OLD_PWD_ERROR.getMessage());
            return ResultUtils.error(ResultEnum.USER_OLD_PWD_ERROR);
        }
        //对新密码进行规则校验
        ResultUtils rPwd=patternUtils.verifyPwd(newPwd);
        if (StringUtils.isNotNull(rPwd)){
            log.error("新密码不符合规则");
            return rPwd;
        }
        //确认密码是否一致
        if (!newPwd.equals(confirmPwd)){
            //两次密码不一致
            log.error(ResultEnum.USER_INEQUALITY.getMessage());
            return ResultUtils.error(ResultEnum.USER_INEQUALITY);
        }
        return toAjax(userService.editPwd(passwordDto));
    }

    /**
     * 修改其他人密码
     * @param passwordDto 密码信息
     * @return 结果
     */
    @PutMapping("otherpwd")
    @ApiOperation("修改其他人密码")
    @Operation(name = "修改用户密码")
    public ResultUtils otherpwd(@RequestBody PasswordDto passwordDto){
        //输入新密码
        String newPwd= passwordDto.getNewPwd();
        //输入确认密码
        String confirmPwd= passwordDto.getConfirmPwd();
        //对新密码进行规则校验
        ResultUtils rPwd=patternUtils.verifyPwd(newPwd);
        if (StringUtils.isNotNull(rPwd)){
            log.error("新密码不符合规则");
            return rPwd;
        }
        //确认密码是否一致
        if (!newPwd.equals(confirmPwd)){
            //两次密码不一致
            log.error(ResultEnum.USER_INEQUALITY.getMessage());
            return ResultUtils.error(ResultEnum.USER_INEQUALITY);
        }
        return toAjax(userService.editPwd(passwordDto));
    }

    /**
     * 批量修改用户状态
     * @param status 状态码
     * @param ids 用户ID
     * @return 结果
     */
    @PutMapping("/status/{status}")
    @ApiOperation("批量修改用户状态")
    @Operation(name = "修改用户状态")
    public ResultUtils status(
            @PathVariable("status") String status,
            @RequestParam("ids") String ids){
        //当前用户为管理员
        //此处应修改为根据角色来判断
        if(StringUtils.stringToList(ids).contains(AdminConst.ADMIN_ID)){
            log.error(ResultEnum.NO_ADMIN_STATUS.getMessage());
            return ResultUtils.error(ResultEnum.NO_ADMIN_STATUS);
        }
        //更新状态
        StatusEnum statusEnum=StatusEnum.valueOf(status.toUpperCase());
        return toAjax(userService.updateStatus(statusEnum,ids));
    }
}

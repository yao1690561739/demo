package com.yao.common.enums;

import lombok.Getter;

/**
 * @author YL
 * @date 2020/9/28 16:07
 * @description 后台返回结果集枚举
 */
@Getter
public enum ResultEnum{

    /** 成功 */
    SUCCESS(200, "成功"),
    /** 失败 */
    ERROR(300, "失败"),


    /** 用户登陆成功 */
    USER_SUCCESS(200, "用户登陆成功"),
    /** 用户名已存在 */
    USER_EXIST(301, "用户名已存在"),
    /** 用户名不存在 */
    USER_NOT_EXIST(302, "用户名不存在"),
    /** 该用户被冻结 */
    USER_FREEZED(303, "该用户被冻结"),
    /** 该用户被禁用 */
    USER_FORBIDDEN(304, "该用户被禁用"),
    /** 用户名不能为空 */
    USER_NAME_NULL(305, "用户名不能为空"),
    /** 用户名只能由字母、数字、下划线组成 */
    USER_NAME_ERROR(306, "用户名只能由字母、数字、下划线组成"),
    /** 用户名长度要求在4到16位之间 */
    USER_NAME_LENGTH(307, "用户名长度要求在4到16位之间"),
    /** 昵称不能为空 */
    USER_NICK_NULL(308,"昵称不能为空"),
    /** 仅允许中文昵称或英文昵称，不允许中英文混用 */
    USER_NICK_ERROR(309,"仅允许中文昵称或英文昵称，不允许中英文混用"),
    /** 密码不能为空 */
    USER_PWD_NULL(310, "密码不能为空"),
    /** 密码输入错误 */
    USER_PWD_NOT_MATCH(311, "密码输入错误"),
    /** 两次输入的密码不一致 */
    USER_INEQUALITY(312, "两次输入的密码不一致"),
    /** 原来密码不正确 */
    USER_OLD_PWD_ERROR(313, "原来密码不正确"),
    /** 密码必须包含大小写字母、数字、下划线或&符号 */
    USER_PWD_ERROR(314, "密码必须包含大小写字母、数字、下划线或&符号"),
    /** 密码长度不得少于8位 */
    USER_PWD_LENGTH_MIN(315, "密码长度不得少于8位"),
    /** 虽然输入得很辛苦，但是密码长度不得多于50位 */
    USER_PWD_LENGTH_MAX(316, "虽然输入得很辛苦，但是密码长度不得多于50位"),
    /** 邮箱地址不能为空 */
    USER_EMAIL_NULL(317,"邮箱地址不能为空"),
    /** 请输入正确格式的邮箱地址 */
    USER_EMAIL_ERROR(318,"请输入正确格式的邮箱地址"),
    /** 手机号不能为空 */
    USER_PHONE_NULL(319,"手机号不能为空"),
    /** 请输入正确格式的手机号 */
    USER_PHONE_ERROR(320,"请输入正确格式的手机号"),
    /** 验证码错误 */
    USER_CAPTCHA_ERROR(321, "验证码错误"),
    /** 验证码已失效 */
    USER_CAPTCHA_OUT(322, "验证码已失效"),


    /** 该角色标识已经存在，不允许重复 */
    ROLE_EXIST(301, "该角色标识已经存在，不允许重复"),


    /** 部门存在用户，无法删除 */
    DEPT_EXIST_USER(301, "部门存在用户，无法删除"),


    /** 该字典标识已经存在，不允许重复 */
    DICT_EXIST(301, "该字典标识已经存在，不允许重复"),


    /** 非法操作：状态有误 */
    STATUS_ERROR(301, "非法操作：状态有误"),


    /** 权限不足 */
    NO_PERMISSIONS(501, "权限不足"),
    /** 不允许操作超级管理员 */
    NO_ADMIN_AUTH(502, "不允许操作超级管理员"),
    /** 不能修改超级管理员状态 */
    NO_ADMIN_STATUS(503, "不能修改超级管理员状态"),
    /** 不允许操作管理员角色 */
    NO_ADMINROLE_AUTH(504, "不允许操作管理员角色"),
    /** 不能修改管理员角色状态 */
    NO_ADMINROLE_STATUS(505, "不能修改管理员角色状态"),


    /** 文件不能为空 */
    NO_FILE_NULL(401, "文件不能为空"),
    /** 不支持该文件类型 */
    NO_FILE_TYPE(402, "不支持该文件类型"),

    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

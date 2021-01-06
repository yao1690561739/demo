package com.yao.system.handler;

import com.yao.common.enums.ResultEnum;
import com.yao.common.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import java.io.FileNotFoundException;

/**
 * @author YL
 * @date 2020/11/5 16:52
 * @description 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AuthorizationException.class)
    public ResultUtils handleAuthorizationException() {
        return ResultUtils.error(ResultEnum.NO_PERMISSIONS);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResultUtils handleNullPointerException() {
        log.error("空指针异常!请确保输入的参数正确!");
        return ResultUtils.error("空指针异常!请确保输入的参数正确!");
    }

    @ExceptionHandler(value = FileNotFoundException.class)
    public ResultUtils handleFileNotFoundException() {
        log.error("找不到指定的文件!");
        return ResultUtils.error("找不到指定的文件!");
    }

    @ExceptionHandler(value = MultipartException.class)
    public ResultUtils handleMultipartException() {
        log.error("上传文件的大小超出限制!");
        return ResultUtils.error("上传文件的大小超出限制!");
    }

}

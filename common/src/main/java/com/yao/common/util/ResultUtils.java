package com.yao.common.util;

import com.yao.common.enums.ResultEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YL
 * @date 2020/9/28 9:56
 * @description 返回结果数据处理工具
 */
public class ResultUtils extends HashMap<String, Object> {

    public ResultUtils() {
        put("code", 200);
        put("msg", "success");
    }

    public static ResultUtils error() {
        return error(500, "操作的数据不存在");
    }

    public static ResultUtils error(String msg) {
        return error(500, msg);
    }

    public static ResultUtils error(int code, String msg) {
        ResultUtils r = new ResultUtils();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static ResultUtils error(ResultEnum resultEnum){
        return error(resultEnum.getCode(),resultEnum.getMessage());
    }

    public static ResultUtils ok(String msg) {
        ResultUtils r = new ResultUtils();
        r.put("msg", msg);
        return r;
    }

    public static ResultUtils ok(Object data) {
        ResultUtils r = new ResultUtils();
        r.put("data", data);
        return r;
    }

    public static ResultUtils ok(Map<String, Object> map) {
        ResultUtils r = new ResultUtils();
        r.putAll(map);
        return r;
    }

    public static ResultUtils ok() {
        return new ResultUtils();
    }

    @Override
    public ResultUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}

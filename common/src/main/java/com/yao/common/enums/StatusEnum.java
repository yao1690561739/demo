package com.yao.common.enums;

import com.yao.common.constant.StatusConst;
import lombok.Getter;

/**
 * @author YL
 * @date 2020/9/25 9:30
 * @description 数据状态枚举-用于逻辑删除控制
 */
@Getter
public enum StatusEnum {

    /**
     * 正常的数据
     */
    OK(StatusConst.OK, "正常"),
    /**
     * 被冻结的数据，不可用
     */
    FREEZED(StatusConst.FREEZED, "冻结"),
    /**
     * 数据已被删除
     */
    DELETE(StatusConst.DELETE, "删除");

    private Integer code;

    private String message;

    StatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
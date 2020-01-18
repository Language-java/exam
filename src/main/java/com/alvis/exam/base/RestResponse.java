package com.alvis.exam.base;

import com.alvis.exam.domain.Chapter;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 响应的状态
 *
 * @param <T>
 */
@Getter
@Setter
public class RestResponse<T> {
    private int code;  //代表了编号
    private String message;  //错误信息
    private T response;

    public RestResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestResponse(int code, String message, T response) {
        this.code = code;
        this.message = message;
        this.response = response;
    }

    public static RestResponse fail(Integer code, String msg) {
        return new RestResponse<>(code, msg);
    }

    public static RestResponse ok() {
        SystemCode systemCode = SystemCode.OK;
        return new RestResponse<>(systemCode.getCode(), systemCode.getMessage());
    }

    public static <F> RestResponse<F> ok(F response) {
        SystemCode systemCode = SystemCode.OK;
        return new RestResponse<>(systemCode.getCode(), systemCode.getMessage(), response);
    }


}

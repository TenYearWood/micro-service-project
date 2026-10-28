package com.csii.common.vo;

import lombok.Data;

@Data
public class Result<T> {

    private int code;
    private String message;
    private T data;

    public Result(T data) {
        this.code = 200;
        this.message = "操作成功";
        this.data = data;
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return Result.ok(null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<T>(data);
    }

    public static <T> Result<T> failed(int code, String message) {
        return failed(code, message, null);
    }

    public static <T> Result<T> failed(int code, String message, T data) {
        Result<T> result = new Result<>(data);
        result.code = code;
        result.message = message;
        return result;
    }
}

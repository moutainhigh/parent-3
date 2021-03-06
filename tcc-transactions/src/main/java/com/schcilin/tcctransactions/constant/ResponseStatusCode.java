package com.schcilin.tcctransactions.constant;

import com.google.common.collect.ImmutableMap;

public enum ResponseStatusCode implements RestStatus {
    OK(20000, "请求成功"),

    // 40xxx 客户端不合法的请求
    INVALID_MODEL_FIELDS(40001, "字段校验非法"),

    /**
     * 参数类型非法，常见于SpringMVC中String无法找到对应的enum而抛出的异常
     */
    INVALID_PARAMS_CONVERSION(40002, "参数类型非法"),

    // 41xxx 请求方式出错
    /**
     * http media type not supported
     */
    HTTP_MESSAGE_NOT_READABLE(41001, "HTTP消息不可读"),

    /**
     * 请求方式非法
     */
    REQUEST_METHOD_NOT_SUPPORTED(41002, "不支持的HTTP请求方法"),

    // 成功接收请求, 但是处理失败

    /**
     * Duplicate Key
     */
    DUPLICATE_KEY(42001, "操作过快, 请稍后再试"),

    /**
     * service is registering with eureka
     */
    SERVICE_INITIALIZING(42002, "服务注册中, 请稍后再试"),

    /**
     * reservation conflict
     */
    RESERVATION_CONFLICT(42003, "预留资源确认冲突"),

    // 50xxx 服务端异常
    /**
     * 用于处理未知的服务端错误
     */
    SERVER_UNKNOWN_ERROR(50001, "服务端异常, 请稍后再试");

    private final int code;

    private final String message;

    private static final ImmutableMap<Integer, ResponseStatusCode> CACHE;

    static {
        final ImmutableMap.Builder<Integer, ResponseStatusCode> builder = ImmutableMap.builder();
        for (ResponseStatusCode statusCode : values()) {
            builder.put(statusCode.code(), statusCode);
        }
        CACHE = builder.build();
    }

    ResponseStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}

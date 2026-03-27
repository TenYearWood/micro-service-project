package com.csii.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description ClientToken响应参数data
 * @author: chengyu
 * @date: 2026/1/22 14:35
 */
@Getter
@Setter
@ToString
public class ClientTokenResponseData {

    /**
     * 接口调用凭证
     */
    private String access_token;

    /**
     * 描述
     */
    private String description;

    /**
     * 错误码
     */
    private int error_code;

    /**
     * 接口调用凭证超时时间，单位（秒）
     */
    private int expires_in;
}

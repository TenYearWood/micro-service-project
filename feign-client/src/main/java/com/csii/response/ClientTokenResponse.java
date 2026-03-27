package com.csii.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description ClientToken响应参数
 * @author: chengyu
 * @date: 2026/1/22 14:34
 */
@Getter
@Setter
@ToString
public class ClientTokenResponse {

    /**
     * 响应参数data
     */
    private ClientTokenResponseData data;

    /**
     * 请求响应信息
     */
    private String message;
}

package com.csii.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @description xx
 * @author: chengyu
 * @date: 2026/3/27 10:29
 */
@Getter
@Setter
@ToString
public class ClientTokenRequest {

    private String client_key;

    private String client_secret;

    private String grant_type;

}

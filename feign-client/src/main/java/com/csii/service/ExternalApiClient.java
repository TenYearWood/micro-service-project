package com.csii.service;

import com.csii.request.ClientTokenRequest;
import com.csii.response.ClientTokenResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 利用feignClient请求一个http接口
 */
@FeignClient(name = "externalApi", url = "https://open.douyin.com")
public interface ExternalApiClient {


    /**
     * 模拟抖音来客的一个获取clientToken的接口
     */
    @PostMapping("/oauth/client_token/")
    @Headers("Content-Type: application/json")
    //@PostMapping(value = "/oauth/client_token/", headers = {"Content-Type=application/json"})
    ClientTokenResponse getClientToken(@RequestBody ClientTokenRequest clientTokenRequest);

}

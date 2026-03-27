package com.csii.controller;

import com.csii.request.ClientTokenRequest;
import com.csii.response.ClientTokenResponse;
import com.csii.service.ExternalApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description xx
 * @author: chengyu
 * @date: 2026/3/27 10:14
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class TestFeignClientController {

    private final ExternalApiClient externalApiClient;

    @GetMapping("/testFeignClient1")
    public ClientTokenResponse testFeignClient1() {
        ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
        clientTokenRequest.setClient_key("aw4rxnssabp8xxxx");
        clientTokenRequest.setClient_secret("afc12a4c0ab0cd0d0dc3c2623b78xxxx");
        clientTokenRequest.setGrant_type("client_credential");
        ClientTokenResponse clientToken = externalApiClient.getClientToken(clientTokenRequest);
        log.info("clientToken返回值: {}", clientToken);
        //clientToken返回值: ClientTokenResponse(data=ClientTokenResponseData(access_token=clt.a027a0197049642a8e5f590625c16ce5jw0Bg3pTI2KDgyv57v1iVbhtgCby_lf, description=, error_code=0, expires_in=7200), message=success)
        //clientToken返回值: ClientTokenResponse(data=ClientTokenResponseData(access_token=null, description=配置无效, error_code=10003, expires_in=0), message=error)
        return clientToken;
    }
}

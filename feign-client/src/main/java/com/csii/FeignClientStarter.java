package com.csii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @description EurekaStarter
 * @author: chengyu
 * @date: 2026/3/5 11:37
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class FeignClientStarter {

    public static void main(String[] args) {
        SpringApplication.run(FeignClientStarter.class, args);
    }
}

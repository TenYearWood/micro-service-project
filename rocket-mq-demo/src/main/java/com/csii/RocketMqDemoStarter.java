package com.csii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @description RocketMqDemoStarter
 * @author: chengyu
 * @date: 2026/3/5 11:37
 */
@SpringBootApplication
@EnableEurekaClient
public class RocketMqDemoStarter {

    public static void main(String[] args) {
        SpringApplication.run(RocketMqDemoStarter.class, args);
    }
}

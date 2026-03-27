package com.csii;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @description EurekaStarter
 * @author: chengyu
 * @date: 2026/3/5 11:37
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaStarter {

    public static void main(String[] args) {
        SpringApplication.run(EurekaStarter.class, args);
    }
}

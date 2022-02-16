package com.ares;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @description:
 * @author: Young
 * @date: 2022/2/14
 * @see: com.ares.flowable.FlowableApplication.java
 **/
@MapperScan("com.ares.**.dao")
@SpringBootApplication
@EnableAsync
@EnableDiscoveryClient
@EnableFeignClients
public class AresFlowableApplication {

    public static void main(String[] args) {
        SpringApplication.run(AresFlowableApplication.class,args);
    }
}

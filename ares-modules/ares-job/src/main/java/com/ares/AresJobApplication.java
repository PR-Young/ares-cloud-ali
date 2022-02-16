package com.ares;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @description:
 * @author: Young
 * @date: 2022/2/14
 * @see: com.ares.quartz.JobApplication.java
 **/
@MapperScan("com.ares.**.dao")
@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class AresJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(AresJobApplication.class,args);
    }
}

package com.ares;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Young
 * @date: 2022/2/14
 * @see: com.ares.gateway.GatewayApplication.java
 **/
@RestController
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DruidDataSourceAutoConfigure.class})
public class AresGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(AresGatewayApplication.class,args);
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello,this is gateway";
    }
}

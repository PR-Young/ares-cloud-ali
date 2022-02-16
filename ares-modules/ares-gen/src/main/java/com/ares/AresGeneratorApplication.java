package com.ares;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @description:
 * @author: Young
 * @date: 2022/2/14
 * @see: com.ares.generator.GeneratorApplication.java
 **/
@MapperScan("com.ares.**.dao")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})

public class AresGeneratorApplication {
    public static void main(String[] args) {
        SpringApplication.run(AresGeneratorApplication.class,args);
    }
}

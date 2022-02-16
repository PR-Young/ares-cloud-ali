package com.ares;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author young
 */
@MapperScan("com.ares.**.dao")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableAsync
public class AresSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AresSystemApplication.class, args);
        print();
    }

    private static void print(){
        System.out.println("         ______                                     __    __ ");
        System.out.println("		/      \\                                   /  |  /  |");
        System.out.println("       /$$$$$$  |  ______   _____  ____    ______  $$ | _$$ |_     _____");
        System.out.println("       $$ |  $$/  /      \\ /     \\/   \\  /     \\   $$ |/ $$   |   /     \\");
        System.out.println("       $$ |      /$$$$$$  |$$$$$$ $$$$  |/$$$$$$  |$$ |$$$$$$/   /$$$$$$ |");
        System.out.println("	   $$ |   __ $$ |  $$ |$$ | $$ | $$ |$$ |  $$ |$$ |  $$ | __ $$    $$|");
        System.out.println("       $$ \\__/  |$$ \\__$$ |$$ | $$ | $$ |$$ |__$$ |$$ |  $$ |/  |$$$$$$$$/");
        System.out.println("	   $$    $$/ $$    $$/ $$ | $$ | $$ |$$    $$/ $$ |  $$  $$/ $$      |");
        System.out.println("	   $$$$$$/    $$$$$$/  $$/  $$/  $$/ $$$$$$$/  $$/    $$$$/   $$$$$$$/");
        System.out.println("				                         $$ |");
        System.out.println("		                                 $$ |");
        System.out.println("		                                 $$/");

    }

}

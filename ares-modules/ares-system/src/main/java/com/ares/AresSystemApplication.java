/*
 *
 *  *  ******************************************************************************
 *  *  * Copyright (c) 2021 - 9999, ARES
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *  *****************************************************************************
 *
 */

package com.ares;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author young
 */
@MapperScan("com.ares.system")
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableDubbo(scanBasePackages = {"com.ares.system.provider", "com.ares.api"})
public class AresSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AresSystemApplication.class, args);
        print();
    }

    private static void print() {
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

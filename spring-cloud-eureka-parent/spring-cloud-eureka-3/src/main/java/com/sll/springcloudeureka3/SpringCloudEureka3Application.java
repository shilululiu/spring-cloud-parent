package com.sll.springcloudeureka3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer//注册中心需要加上EnableEurekaServer注解
public class SpringCloudEureka3Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEureka3Application.class, args);
    }

}

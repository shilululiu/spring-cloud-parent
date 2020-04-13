package com.sll.springcloudeureka2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer//注册中心需要加上EnableEurekaServer注解
public class SpringCloudEureka2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEureka2Application.class, args);
    }

}

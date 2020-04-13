package com.sll.springcloudconfig3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class SpringCloudConfig3Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfig3Application.class, args);
    }

}

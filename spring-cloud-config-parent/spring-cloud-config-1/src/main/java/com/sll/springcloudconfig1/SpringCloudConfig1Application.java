package com.sll.springcloudconfig1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class SpringCloudConfig1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfig1Application.class, args);
    }

}

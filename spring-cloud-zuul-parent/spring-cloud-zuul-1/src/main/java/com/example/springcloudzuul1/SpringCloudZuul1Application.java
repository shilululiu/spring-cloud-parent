package com.example.springcloudzuul1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy  //网关服务
@EnableEurekaClient //注册到Eureka
public class SpringCloudZuul1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZuul1Application.class, args);
    }

}

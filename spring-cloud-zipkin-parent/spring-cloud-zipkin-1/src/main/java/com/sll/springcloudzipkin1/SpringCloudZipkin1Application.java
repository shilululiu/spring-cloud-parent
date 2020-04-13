package com.sll.springcloudzipkin1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import zipkin2.server.internal.EnableZipkinServer;

@SpringBootApplication
@EnableZipkinServer
public class SpringCloudZipkin1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZipkin1Application.class, args);
    }

}

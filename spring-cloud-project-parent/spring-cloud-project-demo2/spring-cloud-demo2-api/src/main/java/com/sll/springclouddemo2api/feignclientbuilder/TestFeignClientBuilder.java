package com.sll.springclouddemo2api.feignclientbuilder;

import com.sll.springclouddemo2api.api.TestControllerApi;
import feign.Feign;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class TestFeignClientBuilder  {




    @Bean
    @LoadBalanced
    public TestControllerApi  get(){

        FeignClientsConfiguration feignClientsConfiguration = new FeignClientsConfiguration();


        return  Feign.builder().contract(new SpringMvcContract()).target(TestControllerApi.class,"http://SPRINGCLOUD-DEMO2");
    }


}

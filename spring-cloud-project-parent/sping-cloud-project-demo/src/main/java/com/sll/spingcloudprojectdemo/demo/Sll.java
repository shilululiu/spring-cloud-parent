package com.sll.spingcloudprojectdemo.demo;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RefreshScope
public class Sll {

    @Value("${name}")
    private String form;

    @GetMapping("name")
    public String getForm(){
        return form;
    }

    @Autowired
    HelloService helloService;


    @RequestMapping("/test8")
    @ResponseBody
    public void test8() throws ExecutionException, InterruptedException {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        Future<Long> f1 = helloService.test10(1l);
        Future<Long> f2 = helloService.test10(2l);
        Future<Long> f3 = helloService.test10(3l);
        Long b1 = f1.get();
        Long b2 = f2.get();
        Long b3 = f3.get();
        Thread.sleep(3000);
        Future<Long> f4 = helloService.test10(4l);
        Long  b4 = f4.get();
        System.out.println("b1>>>"+b1);
        System.out.println("b2>>>"+b2);
        System.out.println("b3>>>"+b3);
        System.out.println("b4>>>"+b4);
        context.close();
    }

}

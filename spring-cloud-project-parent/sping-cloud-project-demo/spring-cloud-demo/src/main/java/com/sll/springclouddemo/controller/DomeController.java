package com.sll.springclouddemo.controller;






import com.sll.springclouddemo.server.TestControllerApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Service
public class DomeController {

 /*  @Autowired
   TestFeignClientBuilder feignClientBuilder;*/
    @Autowired
    TestControllerApi testControllerApi;

    @GetMapping("/test")
    public String test(){
        return testControllerApi.test();
    }


}

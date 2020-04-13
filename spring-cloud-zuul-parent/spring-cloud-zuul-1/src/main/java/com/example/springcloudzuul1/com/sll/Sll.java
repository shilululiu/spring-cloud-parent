package com.example.springcloudzuul1.com.sll;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class Sll {



    @GetMapping("name")
    public String getForm(){
        return "ddd";
    }

}

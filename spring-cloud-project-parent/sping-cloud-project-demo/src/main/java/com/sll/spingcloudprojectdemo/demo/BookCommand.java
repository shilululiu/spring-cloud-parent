package com.sll.spingcloudprojectdemo.demo;


import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class BookCommand extends HystrixCommand {


    protected BookCommand(HystrixCommandGroupKey group) {
        super(group);
    }

    @Override
    protected String getFallback() {
        return  "4";
    }


    @Override
    protected String run() throws Exception {
        return "5" ;
    }




}

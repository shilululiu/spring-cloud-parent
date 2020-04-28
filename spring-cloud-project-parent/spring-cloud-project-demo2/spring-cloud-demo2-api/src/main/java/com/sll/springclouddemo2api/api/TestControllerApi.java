package com.sll.springclouddemo2api.api;




import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;




@FeignClient("SPRINGCLOUD-DEMO2")
public interface TestControllerApi {

  @RequestMapping(value = "/test",method = RequestMethod.GET)
  String test();


}

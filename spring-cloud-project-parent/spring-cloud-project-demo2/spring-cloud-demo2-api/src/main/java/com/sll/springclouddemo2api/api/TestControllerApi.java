package com.sll.springclouddemo2api.api;

import com.sll.springclouddemo2api.feignclientbuilder.TestControllerApiF;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "SPRINGCLOUD-DEMO2",fallback = TestControllerApiF.class  )
public interface TestControllerApi {

  //@RequestMapping(value = "/test",method = RequestMethod.GET)
  String test();

}

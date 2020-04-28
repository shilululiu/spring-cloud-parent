package com.sll.springclouddemo.config;

import feign.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 该类为Feign的配置类
 * 注意：该类不应该在主应用程序上下文的@CompantScan中
 */
@Configuration
public class FeignConfiguration {

    /**
     * 用feign.Contract.Default替换SpringMvcContract契约
     *
     * @return
     */

}

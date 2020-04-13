package com.sll.springcloudeureka3.com.sll.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override  //关闭CSRF保护
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }
}

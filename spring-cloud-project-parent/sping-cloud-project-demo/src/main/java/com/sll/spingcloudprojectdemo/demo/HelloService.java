package com.sll.spingcloudprojectdemo.demo;

import com.google.common.collect.Lists;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL;

@Service
public class HelloService {



    @CacheResult   //@CacheResult方法可以用在我们之前的Service方法上，表示给该方法开启缓存，默认情况下方法的所有参数都将作为缓存的key
    //ignoreExceptions  返回异常不进行降级
    @HystrixCommand(fallbackMethod = "error",ignoreExceptions = ArithmeticException.class) //断路器   失败会执行 fallbackMethod 配置的 方法
    public String hello() {
        return "成功";
    }

    public String error() {
        return "error";
    }

    @CacheResult(cacheKeyMethod = "getCacheKey2")//@CacheResult中添加cacheKeyMethod属性来指定返回缓存key的方法
    @HystrixCommand
    public String test6(Integer id) {
        return "";
    }
    public String getCacheKey2(Integer id) {
        return String.valueOf(id);
    }


    @CacheResult
    @HystrixCommand//@CacheKey注解指明了缓存的key为id，和aa这个参数无关，此时只要id相同就认为是同一个请求，而aa参数的值则不会作为判断缓存的依据
    public String test6(@CacheKey Integer id, String aa) {
        return "";
    }




    @CacheRemove(commandKey = "test6") //注意这里必须指定commandKey，commandKey的值就为缓存的位置，配置了commandKey属性的值，Hystrix才能找到请求命令缓存的位置.为方法名
    @HystrixCommand
    public Book test7(@CacheKey Integer id) {
        return null;
    }


    @HystrixCommand
    public static   Future<String> test3() {
        return new AsyncResult<String>() {
            @Override
            public String invoke() {
                return "";
            }
        };
    }

    @RequestMapping("/test1")//异步调用
    public Book test1() throws ExecutionException, InterruptedException {

        BookCommand bookCommand = new BookCommand(HystrixCommandGroupKey.Factory.asKey(""));
        //同步调用
        //Book book1 = bookCommand.execute();
        //异步调用
        Future<Book> queue = bookCommand.queue();
        Book book = queue.get();
        return book;
    }


    @RequestMapping("/test3") //异步调用
    public String test4() throws ExecutionException, InterruptedException {
        Future<String> bookFuture = HelloService.test3();
        //调用get方法时也可以设置超时时长
        return bookFuture.get();
    }














    //****************************************  请求合并



    @HystrixCollapser(batchMethod = "test11", scope = GLOBAL, collapserProperties = {
            @HystrixProperty(name = "timerDelayInMilliseconds", value = "50"),
            @HystrixProperty(name = "maxRequestsInBatch", value = "200"),
    })
    public Future<Long> test10(Long id) {
        return null;
    }

    @HystrixCommand
    public List<Long> test11(List<Long> ids) {
        System.out.println("test9---------"+ids+"Thread.currentThread().getName():" + Thread.currentThread().getName());

        List<Long> longs = new ArrayList<>();
        longs.add(2l);


        ArrayList<Object> objects = new ArrayList<>();

        return Lists.newArrayList(longs) ;
    }







}

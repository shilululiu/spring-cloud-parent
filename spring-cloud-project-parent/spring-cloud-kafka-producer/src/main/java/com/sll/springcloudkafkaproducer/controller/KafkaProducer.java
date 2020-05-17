package com.sll.springcloudkafkaproducer.controller;


import org.apache.logging.log4j.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.core.ProducerFactoryUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class KafkaProducer {


    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @GetMapping("/sendKafka")
    @Transactional
    public void sendKafka( String s)  {

        kafkaTemplate.send("sll",s);

        // throw new RuntimeException("fail");
        //Thread.sleep(1000);
    }





}

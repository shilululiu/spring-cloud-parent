package com.sll.springcloudkafkaconsumer.controller;


import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.sound.midi.Soundbank;
import java.util.List;

@Component
public class KafkaConsumer {

    @KafkaListener(id = "sll",clientIdPrefix ="sll",topics = {"sll"},containerFactory = "MyKafkaConsumerListener")
    public void kafkaLListener(List<String> data){
        for (String datum : data) {
            System.out.println(datum);
        }
    }
}

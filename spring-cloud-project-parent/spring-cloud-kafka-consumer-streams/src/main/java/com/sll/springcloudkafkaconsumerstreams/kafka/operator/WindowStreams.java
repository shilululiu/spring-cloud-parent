package com.sll.springcloudkafkaconsumerstreams.kafka.operator;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import org.apache.kafka.streams.StreamsConfig;

import java.util.Properties;

/*******************************************************************************
 * @date 2017-12-26 下午 6:32
 * @author: <a href=mailto:>黄跃然</a>
 * @Description: 滑动窗口 window Join
 ******************************************************************************/
public class WindowStreams {

    // Bean
    static public class RegionClicks {
        public long clicks;
        public String region;
    }

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka.streams.1");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
      //  props.put(StreamsConfig.ZOOKEEPER_CONNECT_CONFIG, "localhost:2181");
        props.put(StreamsConfig.STATE_DIR_CONFIG, "/tmp/kafka-streams-windowedjoin");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    }

}

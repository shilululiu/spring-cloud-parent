package com.sll.springcloudkafkaconsumerstreams.kafka.operator;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/*******************************************************************************
 * @date 2017-12-28 下午 5:28
 * @author: <a href=mailto:>黄跃然</a>
 * @Description: Branch 将stream按照规则进行切分多个stream。
 ******************************************************************************/
public class BranchStreams2 {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-stream-processing-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // 制定K-V 格式
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass()); // Serdes : Data Types and Serialization
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass()); // Serdes : Data Types and Serialization
        StreamsConfig config = new StreamsConfig(props);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, String> sll = builder.stream("sll");


        KStream<String, String>[] sllstreams  =  sll.branch((k, v) -> v.startsWith("A"), (k, v) -> v.startsWith("B"), (k, v) -> true);
        sllstreams[0].foreach((k,v)-> System.out.println(k+" |" +v)); //遍历以A开头
        sllstreams[1].foreach((k,v)-> System.out.println(k+": "+v)); //遍历以B开头
        sllstreams[2].foreach((k,v)-> System.out.println(k+"||"+v)); //遍历其他


        final KafkaStreams streams = new KafkaStreams(builder.build(), props);

        final CountDownLatch latch = new CountDownLatch(1);


        Runtime.getRuntime().addShutdownHook(new Thread("streams-temperature-shutdown-hook") {
            @Override
            public void run() {
                streams.close();
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);



    }

}

package com.sll.springcloudkafkaconsumerstreams.kafka.operator;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.kstream.internals.WindowedSerializer;
import org.apache.kafka.streams.state.KeyValueStore;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;


/*******************************************************************************
 * @date 2017-12-26 下午 4:59
 * @author: <a href=mailto:>黄跃然</a>
 * @Description: aggregate 聚合
 ******************************************************************************/
public class AggregateStreams {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-stream-processing-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // 制定K-V 格式
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);

        StreamsConfig config = new StreamsConfig(props);

        StreamsBuilder streamsBuilder = new StreamsBuilder();

        KStream<String, String> sll = streamsBuilder.stream("AggregateStreams");

                sll.flatMapValues(value -> {
                    System.out.println("**"+value);
                            return Arrays.asList(value.split(" "));
                        }
                ).groupBy((k,v)->{
                    System.out.println(k+"***"+v+"****");
                    return k;
                });
                //.groupByKey()
                // 第一参数：聚合的初始值  第二参数：聚合逻辑  第三个参数：【必须】指定状态存储的KV数据类型
               // .aggregate(
                      //  ()-> 0L,
                     //   (k,v,agg) ->{

                         //   long l = Long.valueOf(v) + agg;
                         //   System.out.println(k+"***"+v.toString()+"**********"+l+"********"+agg+"********");
                        //   return l;},
                      //  Materialized.<String,Long, KeyValueStore<Bytes,byte[]>>as("c151")
                              //  .withKeySerde(Serdes.String())
                             //   .withValueSerde(Serdes.Long()));



        //发送到其他主题  该主题要提前创建
       // kTable.toStream().to("AggregateStreams2",Produced.with(Serdes.String(), Serdes.Long()));

        final KafkaStreams streams = new KafkaStreams(streamsBuilder.build(), props);

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

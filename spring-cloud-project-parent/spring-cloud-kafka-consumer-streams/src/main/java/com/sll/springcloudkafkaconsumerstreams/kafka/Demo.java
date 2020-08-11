package com.sll.springcloudkafkaconsumerstreams.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.*;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.ValueMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class Demo {


    //将一个topic写入另一个topic
    public static void main(String[] args) {
        Properties prop = new Properties();
        //程序的唯一标识符以区别于其他应用程序与同一Kafka集群通信
        prop.put(StreamsConfig.APPLICATION_ID_CONFIG,"zj");
        //用于建立与Kafka集群的初始连接的主机/端口对的列表
        prop.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.56.137:9092");
        //记录键值对的默认序列化和反序列化库
        prop.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        prop.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        //定义Streams应用程序的计算逻辑,计算逻辑被定义为topology连接的处理器节点之一,构建流构建工具
        final StreamsBuilder builder = new StreamsBuilder();
        //将demo3写入另一个Kafka toptic(test2) 类似于算子组成的图模型
        builder.stream("demo3").to("test2");
        //构建Topology对象
        final Topology topo = builder.build();
        //构建 kafka流 API实例 将算子以及操作的服务器配置到kafka流
        final KafkaStreams stream = new KafkaStreams(topo,prop);
        final CountDownLatch latch = new CountDownLatch(1);
        // 附加关闭处理程序来捕获control-c
        Runtime.getRuntime().addShutdownHook(new Thread("zj01"){
            @Override
            public void run(){
                stream.close();
                latch.countDown();
            }
        });
        try {
            stream.start();
            latch.await();
        }catch (InterruptedException e){
            //是非正常退出，就是说无论程序正在执行与否，都退出
            System.exit(1);
        }
        //正常退出，程序正常执行结束退出
        System.exit(0);
    }


    //Line Split

    public static void main1(String[] args) {
        Properties prop = new Properties();
        //程序的唯一标识符以区别于其他应用程序与同一Kafka集群通信
        prop.put(StreamsConfig.APPLICATION_ID_CONFIG,"zj");
        //用于建立与Kafka集群的初始连接的主机/端口对的列表
        prop.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.56.137:9092");
        //记录键值对的默认序列化和反序列化库
        prop.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        prop.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        //定义Streams应用程序的计算逻辑,计算逻辑被定义为topology连接的处理器节点之一,构建流构建工具
        final StreamsBuilder builder = new StreamsBuilder();
        builder.stream("demo3").flatMapValues(new ValueMapper<Object, Iterable<Object>>() {
            @Override
            public Iterable<Object> apply(Object s) {
                return Arrays.asList(s.toString().split(","));
            }
        });
        //构建Topology对象
        final Topology topo = builder.build();
        //打印算子结果
        System.out.println(topo.describe().toString());
        //构建 kafka流 API实例 将算子以及操作的服务器配置到kafka流
        final KafkaStreams stream = new KafkaStreams(topo,prop);
        final CountDownLatch latch = new CountDownLatch(1);
        // 附加关闭处理程序来捕获control-c
        Runtime.getRuntime().addShutdownHook(new Thread("zj01"){
            @Override
            public void run(){
                stream.close();
                latch.countDown();
            }
        });
        try {
            stream.start();
            latch.await();
        }catch (InterruptedException e){
            //是非正常退出，就是说无论程序正在执行与否，都退出
            System.exit(1);
        }
        //正常退出，程序正常执行结束退出
        System.exit(0);
    }



    //单行映射成多行
    public static void main2(String[] args) {
        Properties prop = new Properties();
        //程序的唯一标识符以区别于其他应用程序与同一Kafka集群通信
        prop.put(StreamsConfig.APPLICATION_ID_CONFIG,"zj");
        //用于建立与Kafka集群的初始连接的主机/端口对的列表
        prop.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.56.122:9092");
        //记录键值对的默认序列化和反序列化库
        prop.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        prop.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        //定义Streams应用程序的计算逻辑,计算逻辑被定义为topology连接的处理器节点之一,构建流构建工具
        final StreamsBuilder builder = new StreamsBuilder();
        builder.stream("demo4").filter((k,v)->v.toString().split(",").length==2)
                .flatMap((k,v)->{
                    List<KeyValue<String,String>> keyValues = new ArrayList<>();
                    String[] info = v.toString().split(",");
                    String[] friends = info[1].split(" ");
                    for (String friend:friends){
                        keyValues.add(new KeyValue<String, String>(info[0].toString(),friend));
                    }
                    return keyValues;
                }).foreach(((k,v)-> System.out.println(k+"======="+v)));

        //构建Topology对象
        final Topology topo = builder.build();
        //打印算子结构
        // System.out.println(topo.describe().toString());
        //构建 kafka流 API实例 将算子以及操作的服务器配置到kafka流
        final KafkaStreams stream = new KafkaStreams(topo,prop);
        final CountDownLatch latch = new CountDownLatch(1);
        // 附加关闭处理程序来捕获
        Runtime.getRuntime().addShutdownHook(new Thread("zj01"){
            @Override
            public void run(){
                stream.close();
                latch.countDown();
            }
        });
        try {
            stream.start();
            latch.await();
        }catch (InterruptedException e){
            //是非正常退出，就是说无论程序正在执行与否，都退出
            System.exit(1);
        }
        //正常退出，程序正常执行结束退出
        System.exit(0);
    }


    //






}

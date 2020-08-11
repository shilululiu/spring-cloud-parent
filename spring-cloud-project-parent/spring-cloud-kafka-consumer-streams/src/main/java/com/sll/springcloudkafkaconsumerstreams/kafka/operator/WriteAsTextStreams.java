package com.sll.springcloudkafkaconsumerstreams.kafka.operator;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;

import java.util.HashMap;
import java.util.Map;

/*******************************************************************************
 * @date 2017-12-29 下午 4:25
 * @author: <a href=mailto:>黄跃然</a>
 * @Description: WriteAsText Write the records to a file. 将记录写入到文件
 ******************************************************************************/
public class WriteAsTextStreams {

    public static void main(String[] args) {
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-stream-processing-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        // 制定K-V 格式
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass()); // Serdes : Data Types and Serialization
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass()); // Serdes : Data Types and Serialization
        StreamsConfig config = new StreamsConfig(props);


    }

}

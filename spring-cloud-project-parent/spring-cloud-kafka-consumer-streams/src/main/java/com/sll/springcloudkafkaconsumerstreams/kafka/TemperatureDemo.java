package com.sll.springcloudkafkaconsumerstreams.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * / **
 *  *使用高级KStream DSL演示如何实现IoT演示应用程序
 *  *会提取温度值，并在最近的TEMPERATURE_WINDOW_SIZE秒内处理最大值
 *  *是5秒），如果超过TEMPERATURE_THRESHOLD（即20），则发送新消息
 *  *
 *  *在此示例中，输入流从名为“ iot-temperature”的主题读取，其中消息的值
 * *代表温度值；使用TEMPERATURE_WINDOW_SIZE秒的“滚动”窗口，将处理最大值，并且
 *  *如果超过TEMPERATURE_THRESHOLD，则发送到名为“ iot-temperature-max”的主题。
 *  *
 *  *在运行此示例之前，必须按以下方式创建温度值的输入主题：
 *  *
 *  * bin / kafka-topics.sh-创建--zookeeper本地主机：2181-复制因子1-分区1-主题物联网温度
 *  *
 *  *，同时是过滤值的输出主题：
 *  *
 *  * bin / kafka-topics.sh --create --zookeeper localhost：2181-复制因子1-分区1 --topic iot-temperature-max
 *  *
 *  *之后，可以启动控制台使用者，以便从“ iot-temperature-max”主题中读取过滤后的值：
 *  *
 *  * bin / kafka-console-consumer.sh --bootstrap-server localhost：9092 --topic iot-temperature-max --from-beginning
 *  *
 *  *另一方面，控制台生产者可以用于发送温度值（该值必须为整数）
 *  *在控制台上输入“ iot-temperature”：
 *  *
 *  * bin / kafka-console-producer.sh --broker-list localhost：9092 --topic物联网温度
 *  *> 10
 *  *> 15
 *  *> 22
 *  * /
 */
public class TemperatureDemo {

    // threshold used for filtering max temperature values
    private static final int TEMPERATURE_THRESHOLD = 20;
    // window size within which the filtering is applied
    private static final int TEMPERATURE_WINDOW_SIZE = 5;

    public static void main(String[] args) {

        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-temperature");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, 0);

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> source = builder.stream("iot-temperature");
        source.branch();

        KStream<Windowed<String>, String> max = source
                // temperature values are sent without a key (null), so in order
                // to group and reduce them, a key is needed ("temp" has been chosen)
                .selectKey((key, value) -> "temp")
                .groupByKey()
                .windowedBy(TimeWindows.of(TimeUnit.SECONDS.toMillis(TEMPERATURE_WINDOW_SIZE)))
                .reduce((value1, value2) -> {
                    if (Integer.parseInt(value1) > Integer.parseInt(value2))
                        return value1;
                    else
                        return value2;
                })
                .toStream()
                .filter((key, value) -> Integer.parseInt(value) > TEMPERATURE_THRESHOLD);

        Serde<Windowed<String>> windowedSerde = WindowedSerdes.timeWindowedSerdeFrom(String.class);

        // need to override key serde to Windowed<String> type
        max.to("iot-temperature-max", Produced.with(windowedSerde, Serdes.String()));

        final KafkaStreams streams = new KafkaStreams(builder.build(), props);
        final CountDownLatch latch = new CountDownLatch(1);

        // attach shutdown handler to catch control-c
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

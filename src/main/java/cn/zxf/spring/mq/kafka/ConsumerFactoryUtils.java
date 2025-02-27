package cn.zxf.spring.mq.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * 消费者工厂帮助类
 * <p/>
 * ZXF 创建于 2025/2/27
 */
public class ConsumerFactoryUtils {

    /**
     * 消费者监听器工厂
     */
    // 只是对下面 2 个方法的封装
    public static KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> consumerFactory(
            String servers
    ) {
        ConsumerFactory<String, String> cf = buildConsumerFactory(servers);

        ConcurrentKafkaListenerContainerFactory<String, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);

        setupDef(factory);

        return factory;
    }


    // ---------------------------

    /**
     * 构建消费者工厂
     */
    public static ConsumerFactory<String, String> buildConsumerFactory(String servers) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "xx-consumer"); // 设置消费组 (默认都用同一个)
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
        properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, 10000);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        return new DefaultKafkaConsumerFactory<>(properties);
    }

    /**
     * 设置默认配置。
     * <p>
     * Kafka 单条消费，手动确认
     */
    public static void setupDef(ConcurrentKafkaListenerContainerFactory<String, String> factory) {
        /**
         * 表示每台机器创建几个监听实例。每个实例分配一个分区进行消费
         * 尽量保证 concurrency * 服务集群数 <= topic 消息的分区数，如此消息则会分发到各机器上的各个 listener 并发消费
         * 消息量不大时，没必要设置 concurrency 很大，多监听器空转浪费资源
         */
        factory.setConcurrency(1);
        factory.setBatchListener(false);    // 设置单条消息消费
        factory.getContainerProperties().setPollTimeout(3000);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL); // 设置手动确认
    }


    // ---------------------------

    /**
     * 设置默认配置。
     * <p>
     * Kafka 单条消费，手动确认
     */
    public static void setupDef2(ConcurrentKafkaListenerContainerFactory<String, String> factory) {
        /**
         * 表示每台机器创建几个监听实例。每个实例分配一个分区进行消费
         * 尽量保证 concurrency * 服务集群数 <= topic 消息的分区数，如此消息则会分发到各机器上的各个 listener 并发消费
         * 消息量不大时，没必要设置 concurrency 很大，多监听器空转浪费资源
         */
        factory.setConcurrency(2);
        factory.setBatchListener(true);     // 设置批量消息消费
        factory.getContainerProperties().setPollTimeout(3000);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL); // 设置手动确认
    }

}

package cn.zxf.spring.mq.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 生产者工厂帮助类
 * <p/>
 * ZXF 创建于 2025/2/27
 */
public class ProducerFactoryUtils {


    /**
     * 获取工厂
     */
    public static ProducerFactory<String, String> producerFactory(String servers) {
        return new DefaultKafkaProducerFactory<>(producerConfigs(servers));
    }


    /**
     * 生产者配置
     */
    public static Map<String, Object> producerConfigs(String servers) {
        Map<String, Object> props = new LinkedHashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        // 发生错误后，消息重发的次数
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        // 当有多个消息需要被发送到同一个分区时，生产者会把它们放在同一个批次里。该参数指定了一个批次可以使用的内存大小，按照字节数计算
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 4096);
        // 批量发送，延迟为 100 毫秒，启用该功能能有效减少生产者发送消息次数，从而提高并发量
        props.put(ProducerConfig.LINGER_MS_CONFIG, 100);
        // 设置生产者内存缓冲区的大小
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 40960);
        // 键的序列化方式
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // 值的序列化方式
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, 10000);
        return props;
    }

}

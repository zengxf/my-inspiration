package cn.zxf.spring.mq.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

/**
 * Kafka 配置示例
 * <p/>
 * ZXF 创建于 2025/2/27
 */
@Configuration
public class DemoKafkaConfig {


    /**
     * 对方服务
     */
    @Value("${spring.kafka.xx.bootstrap-servers}")
    private String servers;


    /**
     * Kafka 单条消费，手动确认
     */
    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    demoKafkaFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.consumer());

        ConsumerFactoryUtils.setupDef(factory);

        return factory;
    }

    /**
     * Kafka 批量消费，手动确认
     */
    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    demo2KafkaFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(this.consumer());

        ConsumerFactoryUtils.setupDef2(factory);

        return factory;
    }


    private ConsumerFactory<String, String> consumer() {
        return ConsumerFactoryUtils.buildConsumerFactory(this.servers);
    }


}

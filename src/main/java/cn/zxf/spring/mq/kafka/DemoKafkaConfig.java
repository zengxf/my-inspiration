package cn.zxf.spring.mq.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

/**
 * Kafka 配置示例
 * <p/>
 * ZXF 创建于 2025/2/27
 */
@Configuration
public class DemoKafkaConfig {


    /**
     * 要监听的服务
     */
    @Value("${spring.kafka.xx.bootstrap-servers}")
    private String fromServers;

    /**
     * 要发送消息的目标服务
     */
    @Value("${spring.kafka.xx.bootstrap-servers}")
    private String toServers;


    // ------------------------------------

    /**
     * Kafka 生产者-发送模板
     */
    @Bean
    KafkaTemplate<String, String> demoKafkaTemplate() {
        ProducerFactory<String, String> producerFactory = ProducerFactoryUtils.producerFactory(toServers);
        return new KafkaTemplate<>(producerFactory);
    }


    // ------------------------------------

    /**
     * Kafka 消费者工厂
     * <p/>
     * 单条消费，手动确认
     */
    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>>
    demoKafkaFactory() {
        // ConcurrentKafkaListenerContainerFactory<String, String> factory
        //         = new ConcurrentKafkaListenerContainerFactory<>();
        // factory.setConsumerFactory(this.consumer());
        //
        // ConsumerFactoryUtils.setupDef(factory);
        //
        // return factory;
        return ConsumerFactoryUtils.consumerFactory(fromServers);
    }

    /**
     * Kafka 消费者工厂
     * <p/>
     * 批量消费，手动确认
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
        return ConsumerFactoryUtils.buildConsumerFactory(this.fromServers);
    }


}

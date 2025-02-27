package cn.zxf.spring.mq.kafka;

import cn.hutool.core.util.StrUtil;
import cn.zxf.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * Kafka 监听示例
 * <p/>
 * ZXF 创建于 2025/2/27
 */
@Component
@Slf4j
public class DemoListener {


    @KafkaListener(
            topics = "xx_topic",
            containerFactory = "demoKafkaFactory",          /** 配置参考：{@link DemoKafkaConfig#demoKafkaFactory} */
            id = "xx-DemoListener",
            idIsGroup = false
    )
    public void consume(
            ConsumerRecord<String, String> record, // 配置是一次消息一条记录
            Acknowledgment ack
    ) {
        if (Objects.isNull(record) || StrUtil.isBlank(record.value())) {
            return;
        }

        log.info("开始消费，分区：[{}], Offset: [{}]", record.partition(), record.offset());

        // 执行消息消费
        try {
            String msg = record.value();

            Object msgVO = JsonUtils.serializable(msg, Object.class);
            this.handle(msgVO);

            ack.acknowledge();
        } catch (Exception e) {
            log.error("消费 Kafka 消息时出错", e);
        }
    }

    @KafkaListener(
            topics = "xx_topic",
            containerFactory = "demo2KafkaFactory",         /** 配置参考：{@link DemoKafkaConfig#demo2KafkaFactory} */
            id = "xx-Demo2Listener",
            idIsGroup = false
    )
    public void consumer2(List<ConsumerRecord<String, String>> consumerRecords, Acknowledgment ack) {
        log.info("消息记录条数：[{}]", consumerRecords.size());
        try {
            for (ConsumerRecord<String, String> record : consumerRecords) {
                log.debug("开始消费，分区：[{}], offset: [{}]", record.partition(), record.offset());

                String value = record.value();
                if (StrUtil.isEmpty(value)) {
                    log.info("消息为空");
                    continue;
                }

                this.handle(value);
            }
            ack.acknowledge(); // 无错才 ack，服务端可重复消费
        } catch (Exception e) {
            log.error("消费 Kafka 消息时出错", e);
        }
    }

    /**
     * 处理
     */
    public void handle(Object msgVO) {
        if (msgVO == null) {
            return;
        }

        // ...
        log.info("{}", msgVO);
    }

}

package cn.zxf.spring.mq.kafka;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import cn.zxf.utils.JsonUtils;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Kafka 发送消息示例
 * <p/>
 * ZXF 创建于 2025/2/27
 */
@Component
@Slf4j
public class DemoProducer {


    @Autowired
    private KafkaTemplate<String, String> demoKafkaTemplate;


    /**
     * 发送消息
     */
    public void send(String topic, String key, Object msg) {
        if (Objects.isNull(msg)) {
            return;
        }

        String msgJson = JsonUtils.deserializer(msg);

        // 1. 推送消息
        CompletableFuture<SendResult<String, String>> future;
        if (StrUtil.isEmpty(key)) {
            future = this.demoKafkaTemplate.send(topic, msgJson);
        } else {
            future = this.demoKafkaTemplate.send(topic, key, msgJson);
        }

        // 2. 回调处理
        this.callbackHandle(future);
    }


    /**
     * 回调处理
     */
    private void callbackHandle(CompletableFuture<SendResult<String, String>> future) {
        // 设置回调处理
        future.handleAsync((result, err) -> {
            if (err != null) {
                log.error("=====> 发送出错！", err);
                return 0;
            }

            RecordMetadata metadata = result.getRecordMetadata();
            log.info("=====> 发送成功。topic: [{}], Offset: [{}]", metadata.topic(), metadata.offset());
            return 1;
        });
    }

}

package com.yearns.kafka.util;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * 消费者 spring-kafka 2.0 + 依赖JDK8
 * @author Abbott
 */
@Component
@Slf4j
public class KafkaConsumer {
    /**
     * 监听yearns主题,有消息就读取
     * @param message
     */
    @KafkaListener(topics = {"yearns"})
    public void receiveMessage(String message){
        log.info("------- Kafka接收到的消息开始:" + message+"---------");

    }
}
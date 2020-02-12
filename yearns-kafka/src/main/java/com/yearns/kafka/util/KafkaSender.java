package com.yearns.kafka.util;


import com.yearns.kafka.model.OrderEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import javax.annotation.Resource;
import java.util.Date;


/**
 * 生产者
 * @author Abbott
 */
@Component
public class KafkaSender {
    @Resource
    private KafkaTemplate kafkaTemplate;


    /**
     * 发送消息到kafka
     */
    public ListenableFuture<SendResult> sendChannelMess(String channel, String message){

        OrderEntity orderEntity=new OrderEntity();
        orderEntity.setUserId("kafka123");
        orderEntity.setCreateTime(new Date());
        orderEntity.setMessage(message);

        ListenableFuture<SendResult> sendResultListenableFuture = kafkaTemplate.send(channel, orderEntity.getUserId(), orderEntity.toString());
        return sendResultListenableFuture;
    }
    /**
     * 发送带时间戳的消息到kafka
     */
    public ListenableFuture<SendResult> sendTimestampMess(String channel,Integer partition,Long timestamp,String key,String message){
        ListenableFuture<SendResult> sendResultListenableFuture = kafkaTemplate.send(channel,partition,timestamp,key,message);
        return sendResultListenableFuture;
    }

}

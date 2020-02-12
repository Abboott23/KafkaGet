package com.yearns.kafka;

import com.yearns.kafka.util.KafkaSendResultHandler;
import com.yearns.kafka.util.KafkaSender;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class KafkaApplicationTests {

    @Autowired
    private KafkaSender kafkaSender;
    @Test
    public void contextSend()  throws InterruptedException {
        for (int j=0;j<101;j++){
            try {
                SendResult sendResult = kafkaSender.sendChannelMess("yearns","yearns"+j).get();
                log.info("耗时:"+(System.currentTimeMillis()-sendResult.getRecordMetadata().timestamp()));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void contextLoads()  throws InterruptedException {
        for (int i=0;i<101;i++){
            Long time = System.currentTimeMillis();
            try {

                SendResult sendResult = kafkaSender.sendTimestampMess("yearns",0,time,"0","yearns"+i).get();
                //SendResult sendResult = kafkaSender.sendChannelMess("yearns","test"+i).get();
                System.out.println(sendResult.getRecordMetadata().partition());
                System.out.println("耗时:"+(System.currentTimeMillis()-sendResult.getRecordMetadata().timestamp()));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }


        }
    }
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private KafkaSendResultHandler kafkaSendResultHandler;
    @Test
    public void kafkaThird(){
        for (int i=0;i<100;i++){
            Long startTime = System.currentTimeMillis();
            kafkaTemplate.setProducerListener(kafkaSendResultHandler);
            kafkaTemplate.send("yearns",0,null,"yearns"+i);
            Long endTime = System.currentTimeMillis();
            System.out.println("耗时："+(endTime-startTime)+"毫秒");

        }
    }
    @Test
    public void kafkaSecond(){
        for (int i=0;i<100;i++){
            Long startTime = System.currentTimeMillis();
            ListenableFuture<SendResult> sendResultListenableFuture =kafkaTemplate.send("yearns",0,null,"yearns"+i);
            try {
               SendResult sendResult = sendResultListenableFuture.get();
               String topic= sendResult.getRecordMetadata().topic();
               int partition= sendResult.getRecordMetadata().partition();
               Long endTime = System.currentTimeMillis();
               System.out.println("topic:"+topic+",partition:"+partition+",耗时："+(endTime-startTime)+"毫秒");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }
    @Test
    public void kafkaFirst(){
        for (int i=0;i<101;i++){
            Long startTime = System.currentTimeMillis();
            kafkaTemplate.send("yearns",0,null,"yearns"+i);
            Long endTime = System.currentTimeMillis();
            System.out.println("耗时："+(endTime-startTime)+"毫秒");
        }
    }
}

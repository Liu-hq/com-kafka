package com.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Created by Administrator on 2018/7/16.
 */
//@Component
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @KafkaListener(topics = "WordsWithCountsTopic1")
    public void testTopic(ConsumerRecord<String, String> record) {
        try{
            logger.info("WordsWithCountsTopic 接受消息");
            Optional<String> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {
                Object message = kafkaMessage.get();
                logger.info("Listener [WordsWithCountsTopic1] 接受到的消息内容为: {}",message);
                if (!StringUtils.isEmpty(message)) {


                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

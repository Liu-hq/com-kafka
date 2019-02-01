package com.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Created by Administrator on 2018/7/16.
 */
@Component
public class KafkaService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaService.class);

    @Autowired
    private TransactionDemo transactionDemo;

    @KafkaListener(topics = "WordsWithCountsTopic")
    public void testTopic(ConsumerRecord<String, byte[]> record) {
        try{
            logger.info("WordsWithCountsTopic 接受消息");
            Optional<byte[]> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {
                Object message = new String(kafkaMessage.get(),"utf-8");
                logger.info("Listener [WordsWithCountsTopic] 接受到的消息内容为: {} {}",record.key(),message);
                if (!StringUtils.isEmpty(message)) {


                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * stream sink streams-wordcount-processor-output
     * 有sink
     * @param record
     */
    @KafkaListener(topics = "streams-wordcount-processor-output")
    public void outputTopic(ConsumerRecord<String, byte[]> record) {
        try{
            logger.info("streams-wordcount-processor-output 接受消息");
            Optional<byte[]> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {
                Object message = new String(kafkaMessage.get(),"utf-8");
                logger.info("Listener [streams-wordcount-processor-output] 接受到的消息内容为: {} {}",record.key(),message);
                if (!StringUtils.isEmpty(message)) {

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @KafkaListener(topics = "trTopic")
    public void trTopic(ConsumerRecord<String, byte[]> record) {
        try{
            logger.info("trTopic 接受消息");
            Optional<byte[]> kafkaMessage = Optional.ofNullable(record.value());
            if (kafkaMessage.isPresent()) {
                Object message = new String(kafkaMessage.get(),"utf-8");
                transactionDemo.test();
                logger.info("Listener [streams-wordcount-processor-output] 接受到的消息内容为: {} {}",record.key(),message);
                if (!StringUtils.isEmpty(message)) {

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

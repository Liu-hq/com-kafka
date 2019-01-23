package com.kafka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * kafka
 * Created by Administrator on 2018/11/6.
 */
@RestController
@RequestMapping("/stream")
public class KafkaStreamController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @RequestMapping(value="/send")
    public String send(String name) {
        try{
            System.out.println("aa");
            kafkaTemplate.send("streams-plaintext-input",name.getBytes("utf-8"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return "succeed";
    }

}

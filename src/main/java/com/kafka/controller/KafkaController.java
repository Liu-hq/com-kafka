package com.kafka.controller;

import com.alibaba.fastjson.JSONArray;
import com.kafka.config.KafkaConsumerConfig;
import com.kafka.utils.Constants;
import com.kafka.utils.ReturnMessage;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.PartitionInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * kafka
 * Created by Administrator on 2018/11/6.
 */
@RestController
@RequestMapping("/kafka")
public class KafkaController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaConsumerConfig config;

    /**
     * kafka topic list
     * @return
     */
    @RequestMapping(value = "/topic/list/api")
    public Map listApi() {
        Map result;
        try {
            Properties props = new Properties();
            props.put("bootstrap.servers", config.getBootstrapServers());
            props.put("group.id", config.getGroupId());
            props.put("enable.auto.commit", String.valueOf(config.isEnableAutoCommit()));
            props.put("key.deserializer", config.getKeyDeserializer());
            props.put("value.deserializer",config.getValueDeserializer());
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
            Map<String, List<PartitionInfo>> list = consumer.listTopics();
            Map<String, String> str = new HashMap<>();
            for (Map.Entry<String, List<PartitionInfo>> entry : list.entrySet()) {
                Set<String> map =new HashSet<>();
                entry.getValue().forEach( item ->
                    map.add(item.toString())
                );
                str.put(entry.getKey(),JSONArray.toJSONString(map));
            }
            result = ReturnMessage.jsonData(true, str, Constants.SUCCESS_CODE, "查询成功");
        }catch (Exception e){
            logger.error("kafka topic list 异常",e);
            result = ReturnMessage.jsonData(true, "", Constants.FAIL_CODE, e.getMessage());
        }
        return result;
    }

}

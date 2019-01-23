package com.kafka.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Created by Administrator on 2019/1/8.
 */
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    public String bootstrapServers;
    @Value("${spring.kafka.consumer.group-id}")
    public String groupId;
    @Value("${spring.kafka.consumer.auto-offset-reset}")
    public  String autoOffsetReset;
    @Value("${spring.kafka.consumer.enable-auto-commit}")
    public boolean enableAutoCommit;
    @Value("${spring.kafka.consumer.key-deserializer}")
    public  String keyDeserializer;
    @Value("${spring.kafka.consumer.value-deserializer}")
    public  String valueDeserializer;

    @Value("${spring.kafka.listener.poll-timeout}")
    public  String pollTimeout;

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public void setBootstrapServers(String bootstrapServers) {
        this.bootstrapServers = bootstrapServers;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public boolean isEnableAutoCommit() {
        return enableAutoCommit;
    }

    public void setEnableAutoCommit(boolean enableAutoCommit) {
        this.enableAutoCommit = enableAutoCommit;
    }

    public String getAutoOffsetReset() {
        return autoOffsetReset;
    }

    public void setAutoOffsetReset(String autoOffsetReset) {
        this.autoOffsetReset = autoOffsetReset;
    }

    public String getKeyDeserializer() {
        return keyDeserializer;
    }

    public void setKeyDeserializer(String keyDeserializer) {
        this.keyDeserializer = keyDeserializer;
    }

    public String getValueDeserializer() {
        return valueDeserializer;
    }

    public void setValueDeserializer(String valueDeserializer) {
        this.valueDeserializer = valueDeserializer;
    }

    public String getPollTimeout() {
        return pollTimeout;
    }

    public void setPollTimeout(String pollTimeout) {
        this.pollTimeout = pollTimeout;
    }
}

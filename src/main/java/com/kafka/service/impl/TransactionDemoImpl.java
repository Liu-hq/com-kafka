package com.kafka.service.impl;

import com.kafka.service.TransactionDemo;
import com.kafka.utils.SpringUtils;
import org.apache.kafka.clients.producer.internals.TransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2019/2/1.
 */
@Service
@Transactional
public class TransactionDemoImpl implements TransactionDemo{

    private TransactionManager transactionManager;

    @Override
    public String test(String param){
        String trId =  transactionManager.transactionalId();
        return param+"   事务id"+trId;
    }
}

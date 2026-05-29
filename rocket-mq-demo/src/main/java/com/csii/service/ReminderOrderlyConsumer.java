package com.csii.service;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @description xx
 * @author: chengyu
 * @date: 2026/5/28 15:14
 */
@Service
@RocketMQMessageListener(topic = "MyLocalOrderlyTopic", consumerGroup = "${rocketmq.consumer.groupB}", consumeMode = ConsumeMode.ORDERLY)
public class ReminderOrderlyConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("Received orderly reminder: " + message);
    }
}

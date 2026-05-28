package com.csii.service;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @description xx
 * @author: chengyu
 * @date: 2026/5/28 15:14
 */
@Service
@RocketMQMessageListener(topic = "MyLocalReminderTopic", consumerGroup = "local-consumer-group", messageModel = MessageModel.CLUSTERING)
public class ReminderConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        System.out.println("Received reminder: " + message);
        // 在这里处理接收到的提醒消息
    }
}

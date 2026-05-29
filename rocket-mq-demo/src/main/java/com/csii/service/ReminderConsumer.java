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
@RocketMQMessageListener(topic = "MyLocalReminderTopic", consumerGroup = "${rocketmq.consumer.groupA}", messageModel = MessageModel.CLUSTERING)
public class ReminderConsumer implements RocketMQListener<String> {

    /**
     * 接收并处理消息（消息类型与生产者发送的一致，这里是String）
     */
    @Override
    public void onMessage(String message) {
        // 模拟消息处理（实际项目中替换为业务逻辑，如扣减库存、发送短信等）
        System.out.println("Received reminder: " + message);
        // 入门阶段暂不添加复杂业务逻辑，后续可扩展异常处理、消息确认等
    }
}

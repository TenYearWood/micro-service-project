package com.csii.service;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description xx
 * @author: chengyu
 * @date: 2026/5/28 15:10
 */
@Service
public class ReminderProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public void sendReminder(String message) {
        rocketMQTemplate.convertAndSend("MyLocalReminderTopic", message);
    }
}

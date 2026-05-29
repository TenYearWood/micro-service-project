package com.csii.controller;

import com.csii.service.ReminderProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description xx
 * @author: chengyu
 * @date: 2026/3/27 10:14
 */
@RestController
@Slf4j
public class TestRocketMqController {

    @Autowired
    private ReminderProducer reminderProducer;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    private static final String TOPIC_NAME = "MyLocalReminderTopic";

    private static final String ORDERLY_TOPIC_NAME = "MyLocalOrderlyTopic";

    @GetMapping("/sendReminder")
    public String sendReminder(@RequestParam String message) {
        reminderProducer.sendReminder(message);
        return "Reminder sent: " + message;
    }

    /**
     * 发送同步消息（可靠发送，等待Broker确认）
     */
    @GetMapping("/send/sync/{msg}")
    public String sendSyncMessage(@PathVariable String msg) {
        String message = "【RocketMQ 同步消息】" + msg;
        // 同步发送，返回发送结果（可判断是否发送成功）
        SendResult sendResult = rocketMQTemplate.syncSend(TOPIC_NAME, message);
        // 判断发送状态（SUCCESS 表示发送成功）
        if (sendResult.getSendStatus() == SendStatus.SEND_OK) {
            return "同步消息发送成功！发送内容：" + message + "，消息ID：" + sendResult.getMsgId();
        } else {
            return "同步消息发送失败！";
        }
    }

    /**
     * 发送异步消息
     */
    @GetMapping("/send/async/{msg}")
    public String sendAsyncMsg(@PathVariable String msg) {
        String message = "【RocketMQ 异步消息】" + msg;
        rocketMQTemplate.asyncSend(TOPIC_NAME, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("异步消息发送成功! 消息ID：" + sendResult.getMsgId());
            }
            @Override
            public void onException(Throwable e) {
                System.out.println("异步消息发送失败! 原因:" + e.getMessage());
            }
        });
        return "异步消息发送中，请查看控制台回调结果！发送内容：" + message;
    }

    /**
     * 发送顺序消息，生产者：发送多条消息时，使用相同的hashKey（如订单ID）
     */
    @GetMapping("/send/orderly/{msg}")
    public String sendOrderlyMsg(@PathVariable String msg) {
        String orderId = "ORDER_001";
        String message = "【RocketMQ 顺序消息】" + msg;
        rocketMQTemplate.syncSendOrderly(ORDERLY_TOPIC_NAME, message, orderId);
        return "发送顺序消息成功! 发送内容：" + message;
    }


}

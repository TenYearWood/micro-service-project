package com.csii.service;

import com.csii.model.SystemMsgPojo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @description xx
 * @author: chengyu
 * @date: 2026/5/29 9:54
 */
@Service
@RocketMQMessageListener(topic = "${rocketmq.topic}", selectorExpression = "${rocketmq.consumer.tag-system}", consumerGroup = "${rocketmq.consumer.group-system}")
@Slf4j
public class SystemMessageService implements RocketMQListener<SystemMsgPojo> {


    @Override
    public void onMessage(SystemMsgPojo systemMsgPojo) {
        log.info("系统消息service监听，接收到消息:{}", systemMsgPojo);
    }
}

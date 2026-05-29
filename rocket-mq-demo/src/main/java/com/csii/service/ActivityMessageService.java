package com.csii.service;

import com.csii.model.ActivityMsgPojo;
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
@RocketMQMessageListener(topic = "${rocketmq.topic}", selectorExpression = "${rocketmq.consumer.tag-activity}", consumerGroup = "${rocketmq.consumer.group-activity}")
@Slf4j
public class ActivityMessageService implements RocketMQListener<ActivityMsgPojo> {


    @Override
    public void onMessage(ActivityMsgPojo activityMsgPojo) {
        log.info("活动消息service监听，接收到消息:{}", activityMsgPojo);
    }
}

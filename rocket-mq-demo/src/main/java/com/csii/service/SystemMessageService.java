package com.csii.service;

import com.alibaba.fastjson.JSON;
import com.csii.model.SystemMsgPojo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @description xx
 * @author: chengyu
 * @date: 2026/5/29 9:54
 */
@Service
@RocketMQMessageListener(topic = "${rocketmq.topic}", selectorExpression = "${rocketmq.consumer.tag-system}", consumerGroup = "${rocketmq.consumer.group-system}")
@Slf4j
public class SystemMessageService implements RocketMQListener<MessageExt> {


    @Override
    public void onMessage(MessageExt messageExt) {
        log.info("系统消息service监听，接收到消息id:{}", messageExt.getMsgId());
        log.info("系统消息service监听，接收到消息:{}", messageExt);
        byte[] body = messageExt.getBody();
        String str = new String(body, StandardCharsets.UTF_8);
        SystemMsgPojo systemMsgPojo = JSON.parseObject(str, SystemMsgPojo.class);
        log.info("系统消息service监听，解析后消息:{}", systemMsgPojo);
    }
}

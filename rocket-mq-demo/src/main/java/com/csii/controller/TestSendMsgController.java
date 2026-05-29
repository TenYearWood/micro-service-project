package com.csii.controller;

import com.csii.model.ActivityMsgPojo;
import com.csii.model.SystemMsgPojo;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description xx
 * @author: chengyu
 * @date: 2026/3/27 10:14
 */
@RestController
@Slf4j
public class TestSendMsgController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${rocketmq.topic}")
    private String topicName;

    @Value("${rocketmq.consumer.tag-activity}")
    private String activityTag;

    @Value("${rocketmq.consumer.tag-system}")
    private String systemTag;

    /**
     * 发送活动消息
     */
    @GetMapping("/sendActivityMsg")
    public String sendActivityMsg() {
        String destination = topicName + ":" + activityTag;
        ActivityMsgPojo activityMsgPojo = new ActivityMsgPojo();
        activityMsgPojo.setTemplateCode("ACTIVITY_LUCKY");
        activityMsgPojo.setActivityId("A001");
        activityMsgPojo.setTitle("幸运转盘抽奖");
        activityMsgPojo.setContent("抽奖好礼赢取不停!");

        rocketMQTemplate.asyncSend(destination, activityMsgPojo, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("活动消息发送成功! 消息ID：" + sendResult.getMsgId());
            }
            @Override
            public void onException(Throwable e) {
                System.out.println("活动消息发送失败! 原因:" + e.getMessage());
            }
        });
        return "活动消息正在发送... 内容：" + activityMsgPojo;
    }

    /**
     * 发送系统消息
     */
    @GetMapping("/sendSystemMsg")
    public String sendSystemMsg() {
        String destination = topicName + ":" + systemTag;
        SystemMsgPojo systemMsgPojo = new SystemMsgPojo();
        systemMsgPojo.setTemplateCode("APP_VERSION_UPDATE");
        systemMsgPojo.setTitle("版本更新");
        systemMsgPojo.setContent("Ver.1.1已上线，修复已知问题，点击立即更新!");

        rocketMQTemplate.asyncSend(destination, systemMsgPojo, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("系统消息发送成功! 消息ID：" + sendResult.getMsgId());
            }
            @Override
            public void onException(Throwable e) {
                System.out.println("系统消息发送失败! 原因:" + e.getMessage());
            }
        });
        return "系统消息正在发送... 内容：" + systemMsgPojo;
    }


}

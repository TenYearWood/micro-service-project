package com.csii.controller;

import com.csii.service.ReminderProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/sendReminder")
    public String sendReminder(@RequestParam String message) {
        reminderProducer.sendReminder(message);
        return "Reminder sent: " + message;
    }
}

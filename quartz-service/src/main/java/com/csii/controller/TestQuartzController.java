package com.csii.controller;

import com.csii.job.jobmanager.EmailRemindJobManager;
import com.csii.job.jobs.EmailRemindTaskJob;
import com.csii.vo.EmailRemindData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestQuartzController {

    @Autowired
    private EmailRemindJobManager emailRemindJobManager;

    @GetMapping("/testAddEmailRemindJob")
    public String testAddEmailRemindJob() {
        EmailRemindData emailRemindData = new EmailRemindData();
        emailRemindData.setId(1);
        emailRemindData.setAddress("123456@qq.com");
        emailRemindData.setUsername("张三");
        emailRemindData.setTitle("给你的信");
        emailRemindData.setContent("请注意收信了，最近好吗，你过得怎么样，巴拉巴拉...");
        emailRemindData.setCronExpression("0 13 15 08 11 ? 2026-2026");
        emailRemindJobManager.addJob(EmailRemindTaskJob.class, emailRemindData);
        return "success";
    }
}

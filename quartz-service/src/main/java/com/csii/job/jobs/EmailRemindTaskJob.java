package com.csii.job.jobs;

import com.csii.vo.EmailRemindData;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailRemindTaskJob implements Job {

    private EmailRemindData emailRemindData;

    public void setEmailRemindData(EmailRemindData emailRemindData) {
        this.emailRemindData = emailRemindData;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始发送邮件，地址:{},收件人:{},标题:{},内容:{}", emailRemindData.getAddress(),
                emailRemindData.getUsername(), emailRemindData.getTitle(), emailRemindData.getContent());
    }
}

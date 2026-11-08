package com.csii.job.jobmanager;

import com.csii.vo.EmailRemindData;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailRemindJobManager {

    @Autowired
    private Scheduler scheduler;

    public void addJob(Class<? extends Job> cls, EmailRemindData emailRemindData) {
        String jobName = "emailJob:" + emailRemindData.getId();
        String jobGroupName = "emailJobGroup";
        String triggerGroupName = "emailTriggerGroupName";
        try {
            scheduler.start();      //多余，可以不写，Spring 生命周期已经启动过它

            JobKey jobKey = JobKey.jobKey(jobName, jobGroupName);
            if (scheduler.checkExists(jobKey)) {
                throw new RuntimeException("该任务已存在!");
            }

            JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(jobName, jobGroupName).build();
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(emailRemindData.getCronExpression());
            JobDataMap jobDataMap = new JobDataMap();
            jobDataMap.put("emailRemindData", emailRemindData);

            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName, triggerGroupName)
                    .usingJobData(jobDataMap)
                    .withSchedule(scheduleBuilder)
                    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            log.error("EmailRemindJobManager addJob error", e);
            throw new RuntimeException("邮件提醒任务管理器添加Job失败!");
        }
    }
}

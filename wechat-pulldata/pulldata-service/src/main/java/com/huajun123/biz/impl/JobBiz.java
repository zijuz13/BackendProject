package com.huajun123.biz.impl;

import com.huajun123.biz.IJobBiz;
import com.huajun123.domain.FetchNewsJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobBiz implements IJobBiz {
    @Autowired
    private Scheduler scheduler;
    private static final Logger LOGGER= LoggerFactory.getLogger(JobBiz.class);
    @Override
    public void startFetchingNewsJob() {
        try {
            LOGGER.info("STARTED");
            JobDetail jobDetail = JobBuilder.newJob(FetchNewsJob.class).withIdentity("1", "hujkc123").build();
            CronScheduleBuilder builder = CronScheduleBuilder.cronSchedule("0 /9 * * * ? ");
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("1", "hujkc123").withSchedule(builder).build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        }catch (Exception e){
            LOGGER.info("went wrong that is {}",e.getMessage());
        }
    }
}

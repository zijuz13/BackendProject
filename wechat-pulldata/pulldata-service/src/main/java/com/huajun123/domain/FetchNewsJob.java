package com.huajun123.domain;

import com.huajun123.biz.IPullBiz;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//用来执行定时fetchnews任务
public class FetchNewsJob implements Job {
    private static final Logger LOGGER= LoggerFactory.getLogger(FetchNewsJob.class);
    @Autowired
    private IPullBiz biz;
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if(LOGGER.isInfoEnabled()){
            LOGGER.info("The intermittent job has been triggered!");
        }
        biz.saveToRedis();
    }
}

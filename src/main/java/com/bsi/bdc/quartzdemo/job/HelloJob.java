package com.bsi.bdc.quartzdemo.job;

import com.bsi.bdc.quartzdemo.service.JobService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
* DESCRIPTION:
* hello job test
* @author zouyan
* @create 2019/11/11-下午4:49
* created by fuck~
**/
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class HelloJob implements Job {
    private static Logger logger = LogManager.getLogger(HelloJob.class);

    // 如果没有自定义改写 JobFactory，这里会注入失败
    @Autowired
    private JobService jobService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("{} start -- {}", context.getJobDetail().getKey().getName(), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}

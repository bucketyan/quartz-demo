package com.bsi.bdc.quartzdemo.listener;

import com.bsi.bdc.quartzdemo.exception.ServiceException;
import com.bsi.bdc.quartzdemo.pojo.ScheduleJob;
import com.bsi.bdc.quartzdemo.service.JobService;
import com.bsi.bdc.quartzdemo.util.ScheduleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import java.util.List;

/**
* DESCRIPTION:
* Listener注入该类会自动读取自定义job表，
* job不存在于quartz中会自动创建，存在会重新更新一次。
* (若quartz任务在应用停止前并未设置暂停状态等
* 而自定义job表中在应用停止后设置了任务状态为暂停等状态可以在应用启动时自动变更quartz中job任务状态)
*
* @author zouyan
* @create 2019/11/7-下午5:01
* created by fuck~
**/
//@Component
public class ApplicationListener implements CommandLineRunner {

    private static Logger logger = LogManager.getLogger(ApplicationListener.class);

    @Autowired
    private JobService jobService;

    @Autowired
    private Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {
        //应用启动之后执行所有可执行的任务
        List<ScheduleJob> scheduleJobList = jobService.getAllEnableJob();
        for(ScheduleJob scheduleJob : scheduleJobList){
            try{
                CronTrigger cronTrigger = ScheduleUtil.getCronTrigger(scheduler, scheduleJob);
                if(cronTrigger == null){
                    ScheduleUtil.createScheduleJob(scheduler, scheduleJob);
                }else {
                    ScheduleUtil.updateScheduleJob(scheduler, scheduleJob);
                }
                logger.info("Startup {}-{} success", scheduleJob.getJobGroup(), scheduleJob.getJobName());
            }catch (ServiceException e){
                logger.error("ApplicationListener error", e);
            }
        }
    }
}

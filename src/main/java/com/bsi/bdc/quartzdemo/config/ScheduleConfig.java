package com.bsi.bdc.quartzdemo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.util.ResourceUtils;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
* DESCRIPTION:
* 
* @author zouyan
* @create 2019/11/06-上午10:29
* created by fuck~ 
**/
@Configuration
public class ScheduleConfig {

    @Autowired
    private ScheduleJobFactory scheduleJobFactory;

    @Value("${custom.quartz-config}")
    private String quartzConfig;

    @Bean
    @Qualifier("scheduleBean")
    public SchedulerFactoryBean schedulerFactoryBean(DruidDataSource druidDataSource) throws IOException {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //延迟10秒启动
        schedulerFactoryBean.setStartupDelay(10);
        schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setAutoStartup(true);
        schedulerFactoryBean.setDataSource(druidDataSource);
        Properties quartzProperties = new Properties();
        quartzProperties.load(ResourceUtils.getURL(quartzConfig).openStream());
        schedulerFactoryBean.setQuartzProperties(quartzProperties);
        //将JobFactory改为自定义的，否则在Job中注入Bean会失败
        schedulerFactoryBean.setJobFactory(scheduleJobFactory);
        return schedulerFactoryBean;
    }
}

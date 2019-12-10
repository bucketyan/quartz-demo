package com.bsi.bdc.quartzdemo.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
* DESCRIPTION:
* ScheduleJob工厂类，为了将Job管理注入到Spring中
* @author zouyan
* @create 2019/11/06-上午10:48
* created by fuck~
**/
@Component
public class ScheduleJobFactory extends AdaptableJobFactory {

    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        autowireCapableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}

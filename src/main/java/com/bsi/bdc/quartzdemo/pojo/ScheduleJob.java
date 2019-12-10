package com.bsi.bdc.quartzdemo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;

/**
* DESCRIPTION:
* quartz pojo
* @author zouyan
* @create 2019/11/11-下午4:50
* created by fuck~
**/
public class ScheduleJob implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键id
     */
    private Long id;
    /**
     * 运行的job类路径，例：com.bsi.bdc.quartzdemo.job.HelloJob
     */
    private String className;
    /**
     * 运行的job类路径，例：com.bsi.bdc.quartzdemo.job.HelloJob
     */
    private String cronExpression;
    /**
     * job名称
     */
    private String jobName;
    /**
     * job分组
     */
    private String jobGroup;
    /**
     * 调度名称
     */
    private String triggerName;
    /**
     * 调度分组
     */
    private String triggerGroup;
    /**
     * 是否暂停，默认false，即tinyint 0
     */
    private Boolean pause;
    /**
     * 是否允许，默认true，即tinyint 1
     */
    private Boolean enable;
    /**
     * job描述
     */
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public Boolean getPause() {
        return pause;
    }

    public void setPause(Boolean pause) {
        this.pause = pause;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}

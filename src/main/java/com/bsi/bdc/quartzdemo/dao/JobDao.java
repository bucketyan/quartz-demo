package com.bsi.bdc.quartzdemo.dao;

import com.bsi.bdc.quartzdemo.pojo.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* DESCRIPTION:
*
* @author zouyan
* @create 2019/11/12-下午1:45
* created by fuck~
**/
@Mapper
public interface JobDao {

    /**
     * 根据主键id查询schedule_job
     * @param id
     * @return
     */
    ScheduleJob select(@Param("id") long id);

    /**
     * 根据主键id更新schedule_job
     * @param scheduleJob
     * @return
     */
    Integer update(ScheduleJob scheduleJob);

    /**
     * 新增schedule_job
     * @param scheduleJob
     * @return
     */
    Integer insert(ScheduleJob scheduleJob);

    /**
     * 根据主键id物理删除schedule_job
     * @param jobId
     * @return
     */
    Integer delete(Long jobId);

    /**
     * 查询所有schedule_job
     * @return
     */
    List<ScheduleJob> getAllJob();

    /**
     * 查询所有schedule_job
     * @return
     */
    List<ScheduleJob> getAllEnableJob();
}

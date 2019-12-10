package com.bsi.bdc.quartzdemo.service;

import com.bsi.bdc.quartzdemo.dao.JobDao;
import com.bsi.bdc.quartzdemo.exception.ServiceException;
import com.bsi.bdc.quartzdemo.pojo.ScheduleJob;
import com.bsi.bdc.quartzdemo.util.ScheduleUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.Scheduler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* DESCRIPTION:
* 定时任务Service
* @author zouyan
* @create 2019/11/11-下午5:50
* created by fuck~
**/
@Service
public class JobService {

    private static Logger logger = LogManager.getLogger(JobService.class);

    @Autowired
    private JobDao jobDao;

    @Autowired
    private Scheduler scheduler;

    public List<ScheduleJob> getAllEnableJob() {
        return jobDao.getAllEnableJob();
    }

    /**
     * 根据主键id查询schedule_job
     * @param jobId
     * @return
     */
    public ScheduleJob select(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = jobDao.select(jobId);
        if (scheduleJob == null) {
            logger.info("根据jobId:{} 未查询到相关job", jobId);
            throw new ServiceException("ScheduleJob:" + jobId + " not found");
        }
        return scheduleJob;
    }

    /**
     * 根据主键id更新schedule_job,以及更新quartz中job
     * @param scheduleJob
     * @return
     */
    @Transactional(rollbackFor = DataAccessException.class)
    public void update(Long jobId, ScheduleJob scheduleJob) throws ServiceException {
        scheduleJob.setId(jobId);
        ScheduleJob oldScheduleJob = jobDao.select(jobId);
        BeanUtils.copyProperties(oldScheduleJob, scheduleJob, getNullPropertyNames(scheduleJob));
        if (jobDao.update(scheduleJob) <= 0) {
            logger.info("根据jobId:{} 更新job未进行任何变更, scheduleJob:{}", jobId, scheduleJob);
            throw new ServiceException("Update product:" + jobId + "failed");
        }
        ScheduleUtil.updateScheduleJob(scheduler, scheduleJob);
    }

    /**
     * @Title: getNullPropertyNames
     * @Description: 获取值为空的属性名称
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue != null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    /**
     * 新增schedule_job,并加入到quartz中
     * @param scheduleJob
     * @return
     * @throws ServiceException
     */
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean add(ScheduleJob scheduleJob) throws ServiceException {
        Integer num = jobDao.insert(scheduleJob);
        if (num <= 0) {
            logger.info("新增job失败, scheduleJob:{}", scheduleJob);
            throw new ServiceException("Add product failed");
        }
        ScheduleUtil.createScheduleJob(scheduler, scheduleJob);
        return true;
    }

    /**
     * 根据主键id物理删除schedule_job,以及删除quartz中job
     * @param jobId
     * @return
     * @throws ServiceException
     */
    @Transactional(rollbackFor = DataAccessException.class)
    public boolean delete(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = select(jobId);
        Integer num = jobDao.delete(jobId);
        if (num <= 0) {
            logger.info("根据jobId:{} 删除job未删除", jobId);
            throw new ServiceException("Delete product:" + jobId + "failed");
        }
        ScheduleUtil.deleteJob(scheduler, scheduleJob);
        return true;
    }

    /**
     * 查询所有schedule_job
     * @return
     */
    public List<ScheduleJob> getAllJob() {
        return jobDao.getAllJob();
    }

    /**
     * 根据jobId设置schedule_job isPause为false,恢复job执行
     * @param jobId
     * @return
     * @throws ServiceException
     */
    public boolean resume(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = updateScheduleJobStatus(jobId, false);
        ScheduleUtil.resumeJob(scheduler, scheduleJob);
        return true;
    }

    /**
     * 根据jobId设置schedule_job isPause为true,暂停job执行
     * @param jobId
     * @return
     * @throws ServiceException
     */
    public boolean pause(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = updateScheduleJobStatus(jobId, true);
        ScheduleUtil.pauseJob(scheduler, scheduleJob);
        return true;
    }

    /**
     * 根据jobId设置schedule_job isPause为false,并开启job执行
     * @param jobId
     * @return
     * @throws ServiceException
     */
    public boolean run(Long jobId) throws ServiceException {
        ScheduleJob scheduleJob = updateScheduleJobStatus(jobId, false);
        ScheduleUtil.run(scheduler, scheduleJob);
        return true;
    }


    private ScheduleJob updateScheduleJobStatus(Long jobId, Boolean isPause) throws ServiceException {
        ScheduleJob scheduleJob = select(jobId);
        scheduleJob.setPause(isPause);
        update(scheduleJob.getId(), scheduleJob);
        return scheduleJob;
    }
}

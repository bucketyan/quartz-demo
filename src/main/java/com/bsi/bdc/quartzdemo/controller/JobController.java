package com.bsi.bdc.quartzdemo.controller;

import com.bsi.bdc.quartzdemo.exception.ServiceException;
import com.bsi.bdc.quartzdemo.pojo.ScheduleJob;
import com.bsi.bdc.quartzdemo.service.JobService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
* DESCRIPTION:
* Job API接口
* @author zouyan
* @create 2019/11/11-上午10:50
* created by fuck~
**/
@RestController
@RequestMapping("/job")
public class JobController {

    private static Logger logger = LogManager.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    /**
     * 查询所有schedule_job
     * @return
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllJob(){
        List<ScheduleJob> scheduleJobList = jobService.getAllJob();
        logger.info("JobController getAllJob scheduleJobList.size:{}", scheduleJobList.size());
        return new ResponseEntity<>(scheduleJobList, HttpStatus.OK);

    }

    /**
     * 根据主键id查询schedule_job
     * @param jobId
     * @return
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<?> getJob(@PathVariable("id") Long jobId) {
        try {
            ScheduleJob scheduleJob = jobService.select(jobId);
            return new ResponseEntity<>(scheduleJob, HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>("获取job失败, " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据主键id更新schedule_job,以及更新quartz中job
     * @param jobId
     * @param newScheduleJob
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJob(@PathVariable("id") Long jobId,
                            @RequestBody ScheduleJob newScheduleJob) {
        try {
            jobService.update(jobId, newScheduleJob);
            return new ResponseEntity<>("更新job成功", HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>("更新job失败, " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据主键id物理删除schedule_job,以及删除quartz中job
     * @param jobId
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable("id") Long jobId) {
        try {
            jobService.delete(jobId);
            return new ResponseEntity<>("删除job成功", HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>("删除job失败, " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 新增schedule_job,并加入到quartz中
     * @param newScheduleJob
     * @return
     */
    @PostMapping("/save")
    public ResponseEntity<?> saveJob(@RequestBody ScheduleJob newScheduleJob) {
        try {
            jobService.add(newScheduleJob);
            return new ResponseEntity<>("新增job成功", HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>("新增job失败, " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据jobId设置schedule_job isPause为false,并开启job执行
     * @param jobId
     * @return
     */
    @GetMapping("/run/{id}")
    public ResponseEntity<?> runJob(@PathVariable("id") Long jobId) {
        try {
            jobService.run(jobId);
            return new ResponseEntity<>("开启job执行成功", HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>("开启job执行失败, " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据jobId设置schedule_job isPause为true,暂停job执行
     * @param jobId
     * @return
     */
    @GetMapping("/pause/{id}")
    public ResponseEntity<?> pauseJob(@PathVariable("id") Long jobId) {
        try {
            jobService.pause(jobId);
            return new ResponseEntity<>("暂停job成功", HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>("暂停job失败, " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据jobId设置schedule_job isPause为false,恢复job执行
     * @param jobId
     * @return
     */
    @GetMapping("/resume/{id}")
    public ResponseEntity<?> resumeJob(@PathVariable("id") Long jobId) {
        try {
            jobService.resume(jobId);
            return new ResponseEntity<>("恢复job成功", HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<>("恢复job失败, " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

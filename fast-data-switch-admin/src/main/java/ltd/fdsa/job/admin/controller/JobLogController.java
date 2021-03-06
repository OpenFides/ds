package ltd.fdsa.job.admin.controller;

import ltd.fdsa.job.admin.entity.User;
import ltd.fdsa.job.admin.repository.JobGroupRepository;
import ltd.fdsa.job.admin.repository.JobInfoRepository;
import ltd.fdsa.job.admin.repository.JobLogRepository;
import ltd.fdsa.job.admin.scheduler.JobScheduler;
import ltd.fdsa.job.admin.entity.JobGroup;
import ltd.fdsa.job.admin.entity.JobInfo;
import ltd.fdsa.job.admin.entity.JobLog;

import ltd.fdsa.ds.core.exception.FastDataSwitchException;
import ltd.fdsa.ds.core.job.enums.HttpCode;
import ltd.fdsa.ds.core.job.executor.Executor;
import ltd.fdsa.ds.core.job.model.LogResult;
import ltd.fdsa.ds.core.model.Result;
import ltd.fdsa.ds.core.util.DateUtil;
import ltd.fdsa.ds.core.util.I18nUtil;

import ltd.fdsa.job.admin.service.impl.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * index controller
 */
@Controller
@RequestMapping("/joblog")
public class JobLogController {
    private static Logger logger = LoggerFactory.getLogger(JobLogController.class);
    @Resource
    private JobGroupRepository jobGroupService;

    @Resource
    public JobInfoRepository JobInfoDao;
    @Resource
    public JobLogRepository jobLogRepository;

    @Resource
    private SystemUserService userService;

    List<JobGroup> filterJobGroupByRole(List<JobGroup> jobGroupList_all) {
        List<JobGroup> jobGroupList = new ArrayList<>();
        if (jobGroupList_all != null && jobGroupList_all.size() > 0) {
            User loginUser = this.userService.checkLogin();
            if (loginUser.getType() == 1) {
                jobGroupList = jobGroupList_all;
            } else {
                List<String> groupIdStrs = new ArrayList<>();

                for (JobGroup groupItem : jobGroupList_all) {
                    if (groupIdStrs.contains(String.valueOf(groupItem.getId()))) {
                        jobGroupList.add(groupItem);
                    }
                }
            }
        }
        return jobGroupList;
    }

    @RequestMapping
    public String index(Model model, @RequestParam(required = false, defaultValue = "0") Integer jobId) {

        // ???????????????
        List<JobGroup> jobGroupList_all = this.jobGroupService.findAll();

        // filter group
        List<JobGroup> jobGroupList = filterJobGroupByRole(jobGroupList_all);
        if (jobGroupList == null || jobGroupList.size() == 0) {
            throw new FastDataSwitchException(I18nUtil.getInstance("").getString("jobgroup_empty"));
        }

        model.addAttribute("JobGroupList", jobGroupList);

        // ??????
        if (jobId > 0) {
            JobInfo jobInfo = JobInfoDao.findById(jobId).get();
            if (jobInfo == null) {
                throw new RuntimeException(
                        I18nUtil.getInstance("").getString("jobinfo_field_id") + I18nUtil.getInstance("").getString("system_invalid"));
            }

            model.addAttribute("jobInfo", jobInfo);
        }

        return "joblog/joblog.index";
    }

    @RequestMapping("/getJobsByGroup")
    @ResponseBody
    public Result<List<JobInfo>> getJobsByGroup(int jobGroup) {
        List<JobInfo> list = JobInfoDao.findAll().stream().filter(m -> m.getGroupId() == jobGroup).collect(Collectors.toList());
        return Result.success(list);
    }

    @RequestMapping("/pageList")
    @ResponseBody
    public Map<String, Object> pageList(@RequestParam(required = false, defaultValue = "0") int start, @RequestParam(required = false, defaultValue = "10") int length,
                                        int jobGroup,
                                        int jobId,
                                        int logStatus,
                                        String filterTime) {
        // parse param
        Date triggerTimeStart = null;
        Date triggerTimeEnd = null;
        if (filterTime != null && filterTime.trim().length() > 0) {
            String[] temp = filterTime.split(" - ");
            if (temp.length == 2) {
                triggerTimeStart = DateUtil.parseDateTime(temp[0]);
                triggerTimeEnd = DateUtil.parseDateTime(temp[1]);
            }
        }

//        // page query
//        List<JobLog> list =
//                JobLogDao.pageList(
//                        start, length, jobGroup, jobId, triggerTimeStart, triggerTimeEnd, logStatus);
//        int list_count =
//                JobLogDao.pageListCount(
//                        start, length, jobGroup, jobId, triggerTimeStart, triggerTimeEnd, logStatus);

        // package result
        Map<String, Object> maps = new HashMap<String, Object>();
//        maps.put("recordsTotal", list_count); // ????????????
//        maps.put("recordsFiltered", list_count); // ????????????????????????
//        maps.put("data", list); // ????????????
        return maps;
    }

    @RequestMapping("/logDetailPage")
    public String logDetailPage(int id, Model model) {

        // base check
        Result<String> logStatue = Result.success();
        JobLog jobLog = jobLogRepository.findById(id).get();
        if (jobLog == null) {
            throw new RuntimeException(I18nUtil.getInstance("").getString("joblog_logid_invalid"));
        }

        model.addAttribute("triggerCode", jobLog.getTriggerCode());
        model.addAttribute("handleCode", jobLog.getHandleCode());
        model.addAttribute("executorAddress", jobLog.getExecutorAddress());
        model.addAttribute("triggerTime", jobLog.getTriggerTime().getTime());
        model.addAttribute("logId", jobLog.getId());
        return "joblog/joblog.detail";
    }

    @RequestMapping("/logDetailCat")
    @ResponseBody
    public Result<LogResult> logDetailCat(
            String executorAddress, long triggerTime, int logId, String fromLineNum) {
        try {
            Executor executorBiz = JobScheduler.getExecutorClient(executorAddress);
            Result<LogResult> logResult = executorBiz.log(logId, fromLineNum);

            // is end
            if (logResult.getData() != null
                    && logResult.getData().getFromLineNum() > logResult.getData().getToLineNum()) {
                JobLog jobLog = jobLogRepository.findById(logId).get();
                if (jobLog.getHandleCode() > 0) {
                    logResult.getData().setEnd(true);
                }
            }

            return logResult;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Result.fail(500, e.getMessage());
        }
    }

    @RequestMapping("/logKill")
    @ResponseBody
    public Result<String> logKill(int id) {
        // base check
        JobLog log = jobLogRepository.findById(id).get();
        JobInfo jobInfo = JobInfoDao.findById(log.getJobId()).get();
        if (jobInfo == null) {
            return Result.fail(500, I18nUtil.getInstance("").getString("jobinfo_glue_jobid_invalid"));
        }
        if (Result.success().getCode() != log.getTriggerCode()) {
            return Result.fail(500, I18nUtil.getInstance("").getString("joblog_kill_log_limit"));
        }

        // request of kill
        Result<String> runResult = null;
        try {
            Executor executorBiz = JobScheduler.getExecutorClient(log.getExecutorAddress());
            runResult = executorBiz.stop(jobInfo.getId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            runResult = Result.fail(500, e.getMessage());
        }

        if (Result.success().getCode() == runResult.getCode()) {
            log.setHandleCode(HttpCode.INTERNAL_SERVER_ERROR.getCode());
            log.setHandleMsg(
                    I18nUtil.getInstance("").getString("joblog_kill_log_byman")
                            + ":"
                            + (runResult.getMessage() != null ? runResult.getMessage() : ""));
            log.setHandleTime(new Date());
            jobLogRepository.save(log);
            return Result.success(runResult.getMessage());
        } else {
            return Result.fail(500, runResult.getMessage());
        }
    }

    @RequestMapping("/clearLog")
    @ResponseBody
    public Result<String> clearLog(int jobGroup, int jobId, int type) {

        Date clearBeforeTime = null;
        int clearBeforeNum = 0;
        if (type == 1) {
            clearBeforeTime = DateUtil.addMonths(new Date(), -1); // ?????????????????????????????????
        } else if (type == 2) {
            clearBeforeTime = DateUtil.addMonths(new Date(), -3); // ?????????????????????????????????
        } else if (type == 3) {
            clearBeforeTime = DateUtil.addMonths(new Date(), -6); // ?????????????????????????????????
        } else if (type == 4) {
            clearBeforeTime = DateUtil.addYears(new Date(), -1); // ??????????????????????????????
        } else if (type == 5) {
            clearBeforeNum = 1000; // ?????????????????????????????????
        } else if (type == 6) {
            clearBeforeNum = 10000; // ?????????????????????????????????
        } else if (type == 7) {
            clearBeforeNum = 30000; // ?????????????????????????????????
        } else if (type == 8) {
            clearBeforeNum = 100000; // ?????????????????????????????????
        } else if (type == 9) {
            clearBeforeNum = 0; // ????????????????????????
        } else {
            return Result.fail(500, I18nUtil.getInstance("").getString("joblog_clean_type_invalid"));
        }

//        List<Long> logIds = null;
//        do {
//            logIds = JobLogDao.findClearLogIds(jobGroup, jobId, clearBeforeTime, clearBeforeNum, 1000);
//            if (logIds != null && logIds.size() > 0) {
//                JobLogDao.deleteAll(logIds.toArray(Integer[]::new));
//            }
//        } while (logIds != null && logIds.size() > 0);

        return Result.success();
    }
}

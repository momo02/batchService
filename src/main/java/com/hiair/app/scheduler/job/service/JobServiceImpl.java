package com.hiair.app.scheduler.job.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.hiair.app.scheduler.QuartzJobLauncher;
import com.hiair.app.scheduler.job.model.QrtzJob;


@Service
public class JobServiceImpl implements JobService {

	private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
	
	private Scheduler scheduler;

	public JobServiceImpl(SchedulerFactoryBean schedulerFactoryBean) {
		scheduler = schedulerFactoryBean.getScheduler();
	}
	
	/**
	 * 모든 Job List 조회
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<QrtzJob> list() throws SchedulerException {
		List<QrtzJob> qrtzJobList = new ArrayList<QrtzJob>();
		
		for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.anyJobGroup())) {
			logger.debug("Found job identified by: " + jobKey);
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			qrtzJobList.add(jobDetailToQrtzJob(jobDetail));
		}
		return qrtzJobList;
	}

	/**
	 * 특정 Job Group의 Job List 조회
	 * 
	 * @param JobGroup
	 * @return
	 * @throws SchedulerException
	 */
	public List<QrtzJob> listByGroup(String JobGroup) throws SchedulerException {
		List<QrtzJob> qrtzJobList = new ArrayList<QrtzJob>();
		
		for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.groupEquals(JobGroup))) {
			logger.debug("Found job identified by: " + jobKey);
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			qrtzJobList.add(jobDetailToQrtzJob(jobDetail));
		}
		return qrtzJobList;
	}

	/**
	 * 새 Job 저장 (추후 사용하기 위함. 트리거 없이 존재)
	 * 
	 * @param qrtzJob
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SchedulerException
	 */
	public void add(String jobName, String jobGroup, String description) throws ClassNotFoundException, SchedulerException {
//		Class<?> jobClass = Class.forName(qrtzJob.getJobClassName());

//		// Job 파라미터
//		JobDataMap jobDataMap;
//		if (null == qrtzJob.getJobDataMap()) {
//			jobDataMap = new JobDataMap();
//		} else {
//			jobDataMap = qrtzJob.getJobDataMap();
//		}
//		jobDataMap.put("EXECUTION_COUNT", "0"); // 실행 횟수 저장 (공통)

		// Define a durable job instance (durable jobs can exist without triggers)
		//JobBuilder.newJob((Class<? extends Job>) jobClass)
		JobDetail jobDetail = JobBuilder.newJob(QuartzJobLauncher.class)
				.withIdentity(new JobKey(jobName, jobGroup))
				.requestRecovery(true) // '복구' 또는 '실패' 상황(shutdown)이 발생한 경우 작업을 다시 실행해야 하는지 여부
				.storeDurably()
				.withDescription(description).
//				usingJobData(jobDataMap).
				build();
		
		logger.debug("=================== scheduleJob ===================");
		logger.debug("::::::: JobClassName : " + jobDetail.getJobClass().getName());
		logger.debug("::::::: JobGroup : " + jobDetail.getKey().getGroup());
		logger.debug("::::::: JobName : " + jobDetail.getKey().getName());
		logger.debug("::::::: Description : " + jobDetail.getDescription());
		logger.debug("================================================");

		// Add the the job to the scheduler's store
		scheduler.addJob(jobDetail, true); 
		
		// true => 동일 job존재할경우 에러없이 replace
		// true => 중복되는 job or trigger key가 이미 존재할 경우 교체
		// (cf.. false 일경우 중복 시 에러 발생.
		// org.quartz.ObjectAlreadyExistsException: Unable to store Job :
		// 'IBE.job1', because one already exists with this identification.)
	}

	/**
	 * 특정 Job 삭제 - 해당 Job의 trigger들도 모두 삭제 (Delete the identified Job from the
	 * Scheduler - and anyassociated Triggers)
	 */
	public void delete(String jobName, String jobGroup) throws SchedulerException {
		JobKey jobKey = new JobKey(jobName, jobGroup);
		logger.debug("=================== deleteJob ===================");
		logger.debug("::::: Job Key : " + jobKey);
		logger.debug("::::: 연관 trigger list : ");
		for (Trigger trigger : scheduler.getTriggersOfJob(jobKey)) {
			logger.debug("=> " + trigger.getKey().toString());
		}
		logger.debug("================================================");
		
		scheduler.deleteJob(jobKey);
	}

	/**
	 * 모든 Job 삭제
	 */
	public void deleteAll() throws SchedulerException {
		for (String groupName : scheduler.getJobGroupNames()) {
			logger.debug("##### groupName : " + groupName);
			// 특정 그룹의 jobkey 리스트를 얻는다.
			Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(groupName));
			for (JobKey jobKey : jobKeys) {
				logger.debug("##### jobKey : " + jobKey);
				scheduler.deleteJob(jobKey);
			}
		}
		logger.debug("=================== deleteJobAll ===================");
	}

	/**
	 * 특정 Job 중지 - 해당 Job의 현재 실행중인 Trigger들도 중지
	 */
	public void pause(String jobName, String jobGroup) throws SchedulerException {

		JobKey jobKey = new JobKey(jobName, jobGroup);
		scheduler.pauseJob(jobKey);

		logger.debug("=================== pauseJob ===================");
		logger.debug("::::: Job Key : " + jobKey);
		logger.debug("::::: 연관 trigger list : ");
		for (Trigger trigger : scheduler.getTriggersOfJob(jobKey)) {
			logger.debug("=> " + trigger.getKey().toString());
		}
		logger.debug("================================================");
	}

	/**
	 * 특정 Job 재시작 - 해당 Job의 중지되있던 Trigger들을 재시작 - 중지되어 실행되지않았던 1번 이상의 작업실행?이
	 * 발생됨. (If any of the Job'sTrigger s missed one or more fire-times, then
	 * the Trigger's misfireinstruction will be applied.)
	 */
	public void resume(String jobName, String jobGroup) throws SchedulerException {

		JobKey jobKey = new JobKey(jobName, jobGroup);
		scheduler.resumeJob(jobKey);

		logger.debug("=================== resumeJob ===================");
		logger.debug("::::: Job Key : " + jobKey);
		logger.debug("::::: 연관 trigger list : ");
		for (Trigger trigger : scheduler.getTriggersOfJob(jobKey)) {
			logger.debug("=> " + trigger.getKey().toString());
		}
		logger.debug("================================================");
	}


	public QrtzJob jobDetailToQrtzJob(JobDetail jobDetail) {
		QrtzJob qrtzJob = new QrtzJob();
		qrtzJob.setJobName(jobDetail.getKey().getName());
		qrtzJob.setJobGroup(jobDetail.getKey().getGroup());
//		qrtzJob.setJobClassName(jobDetail.getJobClass().getName());
		qrtzJob.setDescription(jobDetail.getDescription());
		return qrtzJob;
	}

}

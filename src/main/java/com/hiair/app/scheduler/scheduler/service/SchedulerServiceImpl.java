package com.hiair.app.scheduler.scheduler.service;

import java.util.Set;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.hiair.cmm.util.CmmJsonUtils;

@Service
public class SchedulerServiceImpl implements SchedulerService {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);
	
	public static Scheduler scheduler;

	public SchedulerServiceImpl(SchedulerFactoryBean schedulerFactoryBean) {
		scheduler = schedulerFactoryBean.getScheduler();
	}

	/**
	 * 스케쥴 시작
	 */
	public void start() throws SchedulerException {
		scheduler.start();
		logger.debug("=================== scheduler start ===================");
		logger.debug("::::: getSchedulerName : " + scheduler.getSchedulerName());
	}

	/**
	 * 스케쥴 중지
	 */
	public void stop() throws SchedulerException {
		// 모든 트리거의 실행을 일시적으로 중단.
		// (Temporarily halts the Scheduler's firing of Triggers.)
		scheduler.standby();
		logger.debug("=================== scheduler stop ===================");
	}

	/**
	 * 스케쥴 종료
	 * 
	 * @throws SchedulerException
	 */
	public void shutdown() throws SchedulerException {
		// 스케줄러의 트리거 실행을 중지하고 스케줄러와 관련된 모든 리소스를 정리
		scheduler.shutdown(true); // true -> 모든 현재 실행중인 job들이 완료될때까지 기다림.
		logger.debug("=================== scheduler shutdown ===================");
	}

	/**
	 * 모든 Trigger들을 중지
	 */
	public void pauseAll() throws SchedulerException {
		scheduler.pauseAll();
		logger.debug("=================== pauseAll ===================");
	}

	/**
	 * 모든 Trigger 재시작
	 */
	public void resumeAll() throws SchedulerException {
		scheduler.resumeAll();
		logger.debug("=================== resumeAll ===================");
	}


	/**
	 * 특정 그룹의 스케쥴(jobs & triggers) 정보 조회
	 * 
	 * @param group
	 * @throws SchedulerException
	 */
	void selectScheduleByGroup(String group) throws SchedulerException {

		// 특정 그룹의 triggerKey 리스트를 얻는다.
		Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(GroupMatcher.groupEquals(group));

		logger.debug("===== GROUP [" + group + " ] Trigger List =====");
		logger.debug(CmmJsonUtils.println(triggerKeys));

		// 특정 그룹의 jobkey 리스트를 얻는다.
		Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.groupEquals(group));

		for (JobKey jobKey : jobKeys) {
			// JobDetail jobDetail = scheduler.getJobDetail(jobKey);
			// logger.debug( ">>>>> jobDetail : " + jobDetail);

			logger.debug("==============================================");
			logger.debug("::::: [ " + jobKey + " ]의 Triggers : " + scheduler.getTriggersOfJob(jobKey));
			for (Trigger trgr : scheduler.getTriggersOfJob(jobKey)) {
				logger.debug(">>>>> [ " + trgr.getKey() + " ] <<<<<");
				logger.debug("::::: Class(trigger type) : " + trgr.getClass());
				logger.debug("::::: Description: " + trgr.getDescription());
				logger.debug("::::: PreviousFireTime : " + trgr.getPreviousFireTime());
				logger.debug("::::: NextFireTime : " + trgr.getNextFireTime());
			}
			logger.debug("==============================================");
		}
	}


	/**
	 * 스케쥴러의 모든 스케쥴(jobs & triggers) 정보 조회
	 * 
	 * @throws SchedulerException
	 */
	public void selectScheduleAll() throws SchedulerException {
		if (scheduler.getJobGroupNames().size() == 0) {
			logger.debug("NO SCHEDULE..");
		}
		for (String groupName : scheduler.getJobGroupNames()) {
			selectScheduleByGroup(groupName);
		}
	}



}

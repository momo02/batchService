package com.hiair.app.scheduler.trigger.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.quartz.CronTrigger;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.hiair.app.scheduler.trigger.model.QrtzTrigger;

@Service
public class TriggerServiceImpl implements TriggerService {

	private static final Logger logger = LoggerFactory.getLogger(TriggerServiceImpl.class);

	private Scheduler scheduler;
	
	public TriggerServiceImpl(SchedulerFactoryBean schedulerFactoryBean) {
		scheduler = schedulerFactoryBean.getScheduler();
		System.out.println(">>>>>" + scheduler);
	}

	/**
	 * 특정 Job의 Trigger List 조회
	 * 
	 * @param vo
	 * @return
	 * @throws SchedulerException
	 */
	public List<QrtzTrigger> list(String jobName, String jobGroup) throws SchedulerException {
		List<QrtzTrigger> triggerList = new ArrayList<QrtzTrigger>();
		for (Trigger trigger : scheduler.getTriggersOfJob(new JobKey(jobName, jobGroup))) {
			TriggerState trgrState = scheduler.getTriggerState(trigger.getKey());

			triggerList.add(triggerToTriggerModel((CronTrigger)trigger, trgrState));
		}
		return triggerList;
	}
	
	/**
	 * 해당 Trigger Detail
	 * 
	 * @param QrtzTrigger
	 * @throws SchedulerException
	 */
	public QrtzTrigger detail(String triggerName, String triggerGroup) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
		TriggerState trgrState = scheduler.getTriggerState(triggerKey);
		
		return triggerToTriggerModel(trigger, trgrState);
	}
	
	/**
	 * 기존에 저장된 Job에 Trigger 추가 (Scheduling an already stored job)
	 * 
	 * @param vo
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void add(QrtzTrigger trigger) throws SchedulerException {
		logger.debug("=================== ADD OR UPDATE ===================");
		logger.debug("::::: Trigger Key: " + trigger.getTriggerKey());
		logger.debug("====================================================");
		
		//이미 트리거가 존재한다면 수정
		if(scheduler.checkExists(trigger.getTriggerKey())){
			logger.debug("##### 해당 트리거 키 존재 - 트리거 수정 #####");
			update(trigger);
		} else {
			// Schedule the trigger
			scheduler.scheduleJob(newTrigger(trigger));
		}
	
	}


	/**
	 * 특정 Trigger 중지 - QRTZ_TRIGGERS > TRIGGER_STATE : 'PAUSED'
	 */
	public void pause(String triggerName, String triggerGroup) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		scheduler.pauseTrigger(triggerKey);
		logger.debug("=================== pauseTrigger ===================");
		logger.debug("::::: Trigger Group : " + triggerKey.getGroup());
		logger.debug("::::: Trigger Name : " + triggerKey.getName());
		logger.debug("====================================================");
	}

	/**
	 * 특정 Trigger 재시작
	 */
	public void resume(String triggerName, String triggerGroup) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		scheduler.resumeTrigger(triggerKey);
		logger.debug("=================== resumeTrigger ===================");
		logger.debug("::::: Trigger Group : " + triggerKey.getGroup());
		logger.debug("::::: Trigger Name : " + triggerKey.getName());
		logger.debug("====================================================");
	}
	
	/**
	 * 특정 Trigger 삭제
	 */
	public void delete(String triggerName, String triggerGroup) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		scheduler.unscheduleJob(triggerKey);

		logger.debug("=================== deleteTrigger ===================");
		logger.debug("::::: Trigger Group : " + triggerKey.getGroup());
		logger.debug("::::: Trigger Name : " + triggerKey.getName());
		logger.debug("====================================================");
	}

	/**
	 * 새 Trigger 생성
	 * 
	 * @param qrtzTrigger
	 * @return
	 */
	public Trigger newTrigger(QrtzTrigger qrtzTrigger) {
		ScheduleBuilder<?> sceduleBuilder = null;
		
		sceduleBuilder = cronSchedule(qrtzTrigger.getCronExpression())
				.inTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
//				.inTimeZone(vo.getTIMEZONE());
		
		TriggerBuilder<?> triggerBuilder = TriggerBuilder.newTrigger()
				.withIdentity(qrtzTrigger.getTriggerName(), qrtzTrigger.getTriggerGroup())
				.startAt(qrtzTrigger.getStartTime()) // 값 없을 경우 "now"
				.withSchedule(sceduleBuilder) // 계속 반복
				.endAt(qrtzTrigger.getEndTime())
				.withDescription(qrtzTrigger.getDescription())
				.withPriority(qrtzTrigger.getPriority());
//				.modifiedByCalendar(null); //예외 처리 캘린더

		if ((null != qrtzTrigger.getJobGroup()) && (null != qrtzTrigger.getJobName())) {
			triggerBuilder.forJob(qrtzTrigger.getJobName(), qrtzTrigger.getJobGroup());
		}

//		if (null != qrtzTrigger.getJobDataMap()) {
//			triggerBuilder.usingJobData(qrtzTrigger.getJobDataMap());
//		}

		return triggerBuilder.build();
	}

	public void update(QrtzTrigger orgTrigger) throws SchedulerException {
//		기존 트리거
		Trigger oldTrigger = scheduler.getTrigger(orgTrigger.getTriggerKey());
		
		/*
		 * 새로운 trigger를 define. ==> 이때, 새로운 trigger의 name, group은 이전과
		 * 동일하게 하고, schedule(실행 조건), description(스케쥴 설명) 만 변경.
		 */
		//변경할 트리거
		Trigger newTrigger = newTrigger(orgTrigger);
		
		/*
		 * trigger.getKey() => 주어진 키로 이전 트리거를 제거하도록 스케줄러에 지시하고,
		 * newTrigger => 새 키를 그 자리에 놓는다.
		 */
		// tell the scheduler to remove the old trigger with the given key, and put the new one in its place
		scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
	}
	
	private QrtzTrigger triggerToTriggerModel(CronTrigger trigger, TriggerState triggerState) throws SchedulerException {
		QrtzTrigger qrtzTrigger = new QrtzTrigger();
		
		qrtzTrigger.setTriggerName(trigger.getJobKey().getName());
		qrtzTrigger.setTriggerGroup(trigger.getJobKey().getGroup());
		qrtzTrigger.setJobName(trigger.getJobKey().getName());
		qrtzTrigger.setJobGroup(trigger.getJobKey().getGroup());
		qrtzTrigger.setCronExpression(trigger.getCronExpression());
		qrtzTrigger.setDescription(trigger.getDescription());
		qrtzTrigger.setPriority(trigger.getPriority());
		qrtzTrigger.setTriggerState(triggerState.toString());
		qrtzTrigger.setStartTime(trigger.getStartTime());
		qrtzTrigger.setEndTime(trigger.getEndTime());
		qrtzTrigger.setNextFireTime(trigger.getNextFireTime());
		qrtzTrigger.setPrevFireTime(trigger.getPreviousFireTime());
		qrtzTrigger.setMisfireInstr(Integer.toString(trigger.getMisfireInstruction()));
		qrtzTrigger.setCalendarName(trigger.getCalendarName());
		
		logger.debug("==============================================");
		logger.debug(">>>>> [ " + trigger.getKey() + " ] <<<<<");
		logger.debug("::::: Class(trigger type) : " + trigger.getClass());
		logger.debug("::::: Cron Expression : " + trigger.getCronExpression());
		logger.debug("::::: Description: " + trigger.getDescription());
		logger.debug("::::: Priority: " + trigger.getPriority());
		logger.debug("::::: Start Time: " + trigger.getStartTime());
		logger.debug("::::: End Time: " + trigger.getEndTime());
		logger.debug("::::: PreviousFireTime : " + trigger.getPreviousFireTime());
		logger.debug("::::: NextFireTime : " + trigger.getNextFireTime());
		logger.debug("::::: CalendarName : " + trigger.getCalendarName());
		logger.debug("==============================================");
		
		return qrtzTrigger;
	}
	
	//미사용
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

}

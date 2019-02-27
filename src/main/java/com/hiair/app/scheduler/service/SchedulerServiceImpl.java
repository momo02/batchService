package com.hiair.app.scheduler.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.calendar.CronCalendar;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.hiair.app.scheduler.model.QrtzCalendar;
import com.hiair.app.scheduler.model.QrtzJob;
import com.hiair.app.scheduler.model.QrtzSchedulerJob;
import com.hiair.app.scheduler.model.QrtzTrigger;
import com.hiair.cmm.util.CmmJsonUtils;

@Service
public class SchedulerServiceImpl implements SchedulerService {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);

	// @Autowired
	// private SchedulerFactoryBean schedulerFactoryBean;
	
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
	 * 스케쥴 작업 등록 (Job & Trigger 등록)
	 * 
	 * @throws ClassNotFoundException
	 */
	public void scheduleJob(QrtzSchedulerJob vo) throws SchedulerException, ClassNotFoundException {
		QrtzJob job = vo.getJob();
		Set<QrtzTrigger> triggers = vo.getTriggers();

		// true => 중복되는 job or trigger key가 이미 존재할 경우 교체
		// (cf.. false 일경우 중복 시 에러 발생.
		// org.quartz.ObjectAlreadyExistsException: Unable to store Job :
		// 'IBE.job1', because one already exists with this identification.)
		scheduler.scheduleJob(newJob(job), newTriggers(triggers), false);

		logger.debug("=================== scheduleJob ===================");
		logger.debug("::::::: JobClassName : " + job.getJobClassName());
		logger.debug("::::::: JobGroup : " + job.getJobGroup());
		logger.debug("::::::: JobName : " + job.getJobName());
		logger.debug("::::::: 연관 trigger list : ");
		for (Trigger trigger : scheduler.getTriggersOfJob(job.getJobKey())) {
			logger.debug("=> " + trigger.getKey().toString());
		}
		logger.debug("================================================");
	}

	/**
	 * 스케쥴 작업 업데이트
	 * 
	 * @throws ParseException
	 */
	public void rescheduleJob(QrtzTrigger orgTrigger) throws SchedulerException, ParseException {
		/*
		 * ========================================================= 1. group &
		 * trigger명(=> Key) 으로 기존의 Trigger를 get
		 * =========================================================
		 */
		Trigger trigger = (Trigger) scheduler.getTrigger(orgTrigger.getTriggerKey());
		logger.debug("##### org trigger : " + trigger);

		if (trigger != null) {
			/*
			 * ========================================================= 2. 새로운
			 * 스케쥴 정보로 Trigger 교체
			 * =========================================================
			 */
			Trigger newTrigger = null;

			String orgTriggerName = trigger.getKey().getName();
			String orgTriggerGroup = trigger.getKey().getGroup();

			logger.debug(">>>>> orgTriggerName : " + orgTriggerName);
			logger.debug(">>>>> orgTriggerGroup : " + orgTriggerGroup);
			logger.debug(">>>>> orgTriggerClass(type) : " + trigger.getClass());
			logger.debug(">>>>> orgDescription : " + trigger.getDescription());

			/*
			 * 2-1. 새로운 trigger를 define. ==> 이때, 새로운 trigger의 name, group은 이전과
			 * 동일하게 하고, schedule(실행 조건), description(스케쥴 설명) 만 변경.
			 */
			newTrigger = newTrigger(orgTrigger);
			/*
			 * 2-2. trigger.getKey() => 주어진 키로 이전 트리거를 제거하도록 스케줄러에 지시하고,
			 * newTrigger => 새 키를 그 자리에 놓는다.
			 */
//			scheduler.rescheduleJob(trigger.getKey(), newTrigger);
//
//			String calendarCronExpression = orgTrigger.getCalendarCronExpression();
//			if (null != calendarCronExpression || ("").equals(calendarCronExpression)) {
//				// CronCalendar 객체 생성
//				CronCalendar cronCalendar = new CronCalendar(orgTrigger.getCalendarCronExpression());
//				// Scheduler에 Calendar 추가
//				scheduler.addCalendar(orgTrigger.getCalendarName(), cronCalendar, true, true);
//			}

			logger.debug("================= reschedule 후 schedule 정보 확인 =================");
			// reschedule 후 현재 스케쥴 상태 확인
			selectScheduleAll();
		}
	}

	/**
	 * 새 Job 저장 (추후 사용하기 위함. 트리거 없이 존재)
	 * 
	 * @param vo
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SchedulerException
	 */
	public void addJob(QrtzJob vo) throws ClassNotFoundException, SchedulerException {
		Class<?> jobClass = Class.forName(vo.getJobClassName());

		// Job 파라미터
		JobDataMap jobDataMap;
		if (null == vo.getJobDataMap()) {
			jobDataMap = new JobDataMap();
		} else {
			jobDataMap = vo.getJobDataMap();
		}
		jobDataMap.put("EXECUTION_COUNT", "0"); // 실행 횟수 저장 (공통)

		// Define a durable job instance (durable jobs can exist without triggers)
		JobDetail job = JobBuilder.newJob((Class<? extends Job>) jobClass)
				.withIdentity(vo.getJobKey())
				.requestRecovery(true) // '복구' 또는 '실패' 상황(shutdown)이 발생한 경우 작업을 다시 실행해야 하는지 여부
				.storeDurably()
				.withDescription(vo.getDescription()).
				usingJobData(jobDataMap).
				build();

		// Add the the job to the scheduler's store
		scheduler.addJob(job, true); // true => 동일 job존재할경우 에러없이 replace
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
	public QrtzTrigger addTrigger(QrtzTrigger trigger) throws SchedulerException, ParseException {
		QrtzTrigger vo = null;
		
		//이미 트리거가 존재한다면 수정
		if(scheduler.checkExists(trigger.getTriggerKey())){
			logger.debug("##### 해당 트리거 키 존재 - 트리거 수정 #####");
			vo = updateTrigger(trigger);
			
			return vo;
		}

		Trigger trgr = newTrigger(trigger);
		
		// Schedule the trigger
		scheduler.scheduleJob(trgr);

		vo = qrtzTriggerToTriggerModel(trgr, scheduler.getTriggerState(trgr.getKey()));
		
		return vo;
	}

	/**
	 * 특정 Job 삭제 - 해당 Job의 trigger들도 모두 삭제 (Delete the identified Job from the
	 * Scheduler - and anyassociated Triggers)
	 */
	public void deleteJob(String jobName, String jobGroup) throws SchedulerException {
		
		JobKey jobKey = new JobKey(jobName, jobGroup);
		scheduler.deleteJob(jobKey);
		logger.debug("=================== deleteJob ===================");
		logger.debug("::::: Job Key : " + jobKey);
		logger.debug("::::: 연관 trigger list : ");
		for (Trigger trigger : scheduler.getTriggersOfJob(jobKey)) {
			logger.debug("=> " + trigger.getKey().toString());
		}
		logger.debug("================================================");
	}

	/**
	 * 모든 Job 삭제
	 */
	public void deleteJobAll() throws SchedulerException {
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
	public void pauseJob(String jobName, String jobGroup) throws SchedulerException {

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
	 * 특정 Trigger 또는 Trigger 그룹 중지 QRTZ_TRIGGERS > TRIGGER_STATE : 'PAUSED'
	 */
	public void pauseTrigger(String triggerName, String triggerGroup) throws SchedulerException {

		// if(null == vo.getTriggerGroup()) {
		// String triggerGroup = vo.getTriggerGroup();
		// scheduler.pauseTriggers(GroupMatcher.groupEquals(triggerGroup));
		// logger.debug("=================== pauseTriggers in [" + triggerGroup
		// + "]===================");
		// }else {
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		scheduler.pauseTrigger(triggerKey);
		logger.debug("=================== pauseTrigger ===================");
		logger.debug("::::: Trigger Group : " + triggerKey.getGroup());
		logger.debug("::::: Trigger Name : " + triggerKey.getName());
		logger.debug("====================================================");
		// }
	}

	/**
	 * 모든 Trigger들을 중지
	 */
	public void pauseAll() throws SchedulerException {
		scheduler.pauseAll();
		logger.debug("=================== pauseAll ===================");
	}

	/**
	 * 특정 Job 재시작 - 해당 Job의 중지되있던 Trigger들을 재시작 - 중지되어 실행되지않았던 1번 이상의 작업실행?이
	 * 발생됨. (If any of the Job'sTrigger s missed one or more fire-times, then
	 * the Trigger's misfireinstruction will be applied.)
	 */
	public void resumeJob(String jobName, String jobGroup) throws SchedulerException {

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

	/**
	 * 특정 Trigger 또는 Trigger 그룹 재시작
	 */
	public void resumeTrigger(String triggerName, String triggerGroup) throws SchedulerException {

		// if(null == vo.getTriggerName()) {
		// String triggerGroup = vo.getTriggerGroup();
		// scheduler.resumeTriggers(GroupMatcher.groupEquals(triggerGroup));
		// logger.debug("=================== resumeTriggers in [" + triggerGroup
		// + "]===================");
		// }else {
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		scheduler.resumeTrigger(triggerKey);

		logger.debug("=================== resumeTrigger ===================");
		logger.debug("::::: Trigger Group : " + triggerKey.getGroup());
		logger.debug("::::: Trigger Name : " + triggerKey.getName());
		logger.debug("====================================================");
		// }
	}

	/**
	 * 특정 Trigger 삭제
	 */
	public void deleteTrigger(String triggerName, String triggerGroup) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		scheduler.unscheduleJob(triggerKey);

		logger.debug("=================== deleteTrigger ===================");
		logger.debug("::::: Trigger Group : " + triggerKey.getGroup());
		logger.debug("::::: Trigger Name : " + triggerKey.getName());
		logger.debug("====================================================");
	}

	/**
	 * 모든 Trigger 재시작
	 */
	public void resumeAll() throws SchedulerException {
		scheduler.resumeAll();
		logger.debug("=================== resumeAll ===================");
	}

	/**
	 * 새 Job 생성
	 * 
	 * @param vo
	 * @return
	 * @throws ClassNotFoundException
	 */
	public JobDetail newJob(QrtzJob vo) throws ClassNotFoundException {
		Class<?> jobClass = Class.forName(vo.getJobClassName());
		// Job 파라미터
		JobDataMap jobDataMap;
		if (null == vo.getJobDataMap()) {
			jobDataMap = new JobDataMap();
		} else {
			jobDataMap = vo.getJobDataMap();
		}
		jobDataMap.put("EXECUTION_COUNT", "0"); // 실행 횟수 저장 (공통)

		return JobBuilder.newJob((Class<? extends Job>) jobClass)
				// .withIdentity(vo.getJobName(), vo.getJobGroup())
				.withIdentity(vo.getJobKey()).requestRecovery(true) // '복구' 또는 '실패' 상황(shutdown)이 발생한 경우 작업을 다시 실행해야 하는지 여부
				.withDescription(vo.getDescription())
				.usingJobData(jobDataMap)
				.build();
	}

	/**
	 * 새 Trigger 생성
	 * 
	 * @param vo
	 * @return
	 */
	public Trigger newTrigger(QrtzTrigger vo) {
		ScheduleBuilder<?> sceduleBuilder = null;
		
//		String triggerType = vo.getTriggerType();

//		if ("SIMPLE".equals(triggerType)) {
//			SimpleTriggerVO trigger = (SimpleTriggerVO) vo;
//			int repeatCount = trigger.getRepeatCount();
//
//			if (repeatCount > 0) {
//				sceduleBuilder = simpleSchedule()
//						// .withIntervalInMilliseconds(Long.parseLong("3000"))
//						.withIntervalInMilliseconds(((SimpleTriggerVO) vo).getRepeatInterval())
//						// .withMisfireHandlingInstructionFireNow() //TODOJ
//						// misfire
//						.withRepeatCount(repeatCount);
//
//			} else {
//				sceduleBuilder = simpleSchedule().withIntervalInMilliseconds(((SimpleTriggerVO) vo).getRepeatInterval())
//						.repeatForever(); // 계속 반복
//			}
//
//		} else if ("CRON".equals(triggerType)) {
//			sceduleBuilder = cronSchedule(vo.getCronExpression())
//					.inTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
//		}

		sceduleBuilder = cronSchedule(vo.getCronExpression())
				.inTimeZone(vo.getTIMEZONE());
		
		TriggerBuilder<?> triggerBuilder = TriggerBuilder.newTrigger()
				.withIdentity(vo.getTriggerName(), vo.getTriggerGroup())
				.startAt(vo.getStartTime()) // 값 없을 경우 "now"
				.withSchedule(sceduleBuilder) // 계속 반복
				.endAt(vo.getEndTime())
				.withDescription(vo.getDescription())
				.withPriority(vo.getPriority());
//				.modifiedByCalendar(null); //예외 처리 캘린더

		if ((null != vo.getJobGroup()) && (null != vo.getJobName())) {
			triggerBuilder.forJob(vo.getJobName(), vo.getJobGroup());
		}

		if (null != vo.getJobDataMap()) {
			triggerBuilder.usingJobData(vo.getJobDataMap());
		}

		return triggerBuilder.build();
	}

	/**
	 * 새 Trigger 생성
	 * 
	 * @param vo
	 * @return
	 */
	public Set<Trigger> newTriggers(Set<QrtzTrigger> triggers) {
		Set<Trigger> newTriggers = new HashSet<>();
		for (QrtzTrigger trigger : triggers) {
			newTriggers.add(newTrigger(trigger));
		}
		return newTriggers;
	}

	// TODOJ
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
	 * 해당 Trigger Detail
	 * 
	 * @param QrtzTrigger
	 * @throws SchedulerException
	 */
	public QrtzTrigger getTriggerDetail(String triggerName, String triggerGroup) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		
		Trigger trgr = scheduler.getTrigger(triggerKey);
		TriggerState trgrState = scheduler.getTriggerState(triggerKey);
		
		logger.debug("==============================================");
		logger.debug(">>>>> [ " + trgr.getKey() + " ] <<<<<");
		logger.debug("::::: Class(trigger type) : " + trgr.getClass());
		if (trgr instanceof CronTrigger) {
			CronTrigger cronTrigger = (CronTrigger) trgr;
			logger.debug("::::: Cron Expression : " + cronTrigger.getCronExpression());
		}
		logger.debug("::::: Description: " + trgr.getDescription());
		logger.debug("::::: Priority: " + trgr.getPriority());
		logger.debug("::::: Start Time: " + trgr.getStartTime());
		logger.debug("::::: End Time: " + trgr.getEndTime());
		logger.debug("::::: PreviousFireTime : " + trgr.getPreviousFireTime());
		logger.debug("::::: NextFireTime : " + trgr.getNextFireTime());
		logger.debug("::::: CalendarName : " + trgr.getCalendarName());

//		org.quartz.Calendar calendar = scheduler.getCalendar(trgr.getCalendarName());
//		logger.debug("::::: CalendarName : " + trgr.getCalendarName());
//		if (calendar instanceof CronCalendar) {
//			CronExpression cronExpression = ((CronCalendar) calendar).getCronExpression();
//			logger.debug("::::: Calendar Cron Expression : " + cronExpression.toString());
//		}
		logger.debug("==============================================");
		
		return qrtzTriggerToTriggerModel(trgr, trgrState);
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

	/**
	 * 모든 Job List 조회
	 * 
	 * @return
	 * @throws SchedulerException
	 */
	public List<QrtzJob> getJobAll() throws SchedulerException {
		List<QrtzJob> jobList = new ArrayList<QrtzJob>();
		for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.anyJobGroup())) {
			logger.debug("Found job identified by: " + jobKey);
			JobDetail qrtzJob = scheduler.getJobDetail(jobKey);
			jobList.add(qrtzJobToJobModel(qrtzJob));
		}
		return jobList;
	}

	/**
	 * 특정 Job Group의 Job List 조회
	 * 
	 * @param JobGroup
	 * @return
	 * @throws SchedulerException
	 */
	public List<QrtzJob> getJobsByGroup(String JobGroup) throws SchedulerException {
		List<QrtzJob> jobList = new ArrayList<QrtzJob>();
		for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.groupEquals(JobGroup))) {
			logger.debug("Found job identified by: " + jobKey);
			JobDetail qrtzJob = scheduler.getJobDetail(jobKey);
			jobList.add(qrtzJobToJobModel(qrtzJob));
		}
		return jobList;
	}

	/**
	 * 특정 Job의 Trigger List 조회
	 * 
	 * @param vo
	 * @return
	 * @throws SchedulerException
	 */
	public List<QrtzTrigger> getTriggersOfJob(String jobName, String jobGroup) throws SchedulerException {
		List<QrtzTrigger> triggerList = new ArrayList<QrtzTrigger>();
		for (Trigger qrtzTrigger : scheduler.getTriggersOfJob(new JobKey(jobName, jobGroup))) {
			
			TriggerState trgrState = scheduler.getTriggerState(qrtzTrigger.getKey());
//			org.quartz.Calendar calendar = scheduler.getCalendar(qrtzTrigger.getCalendarName());
			
			triggerList.add(qrtzTriggerToTriggerModel(qrtzTrigger, trgrState));
		}
		return triggerList;
	}

	public QrtzJob qrtzJobToJobModel(JobDetail qrtzJob) {
		
		QrtzJob job = new QrtzJob();
		job.setJobGroup(qrtzJob.getKey().getGroup());
		job.setJobName(qrtzJob.getKey().getName());
		job.setJobClassName(qrtzJob.getJobClass().getName());
		job.setDescription(qrtzJob.getDescription());
		return job;
	}

	public QrtzTrigger qrtzTriggerToTriggerModel(Trigger trigger, TriggerState triggerState) throws SchedulerException {
		QrtzTrigger qrtzTrigger = new QrtzTrigger();
		
		qrtzTrigger.setTriggerName(trigger.getJobKey().getName());
		qrtzTrigger.setTriggerGroup(trigger.getJobKey().getGroup());
		qrtzTrigger.setJobName(trigger.getJobKey().getName());
		qrtzTrigger.setJobGroup(trigger.getJobKey().getGroup());
		qrtzTrigger.setDescription(trigger.getDescription());
		qrtzTrigger.setPriority(trigger.getPriority());
		qrtzTrigger.setTriggerState(triggerState.toString());
		qrtzTrigger.setStartTime(trigger.getStartTime());
		qrtzTrigger.setEndTime(trigger.getEndTime());
		qrtzTrigger.setNextFireTime(trigger.getNextFireTime());
		qrtzTrigger.setPrevFireTime(trigger.getPreviousFireTime());
		qrtzTrigger.setMisfireInstr(Integer.toString(trigger.getMisfireInstruction()));
		qrtzTrigger.setCalendarName(trigger.getCalendarName());
		
		return qrtzTrigger;

	}
	
	public QrtzTrigger updateTrigger(QrtzTrigger orgTrigger) throws SchedulerException {
		//기존 트리거
		Trigger oldTrigger = (Trigger) scheduler.getTrigger(orgTrigger.getTriggerKey());
		
		//변경할 트리거
		Trigger newTrigger = newTrigger(orgTrigger);
		
		// tell the scheduler to remove the old trigger with the given key, and put the new one in its place
		scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
		
		QrtzTrigger qrtzTrigger = qrtzTriggerToTriggerModel(newTrigger, scheduler.getTriggerState(newTrigger.getKey()));
		
		return qrtzTrigger;
		
	}

	public List<QrtzCalendar> getCalendarList() throws SchedulerException {
		List<QrtzCalendar> calendarList = new ArrayList<QrtzCalendar>();
		
		for (String calendarNames : scheduler.getCalendarNames()) {
			calendarList.add(qrtzCalendarToCalendarModel(calendarNames, (CronCalendar)scheduler.getCalendar(calendarNames)));
		}
		
		return calendarList;
		
	}
	
	public QrtzCalendar qrtzCalendarToCalendarModel(String calendarNames, CronCalendar qrtzCalendar) throws SchedulerException {
		QrtzCalendar calendar = new QrtzCalendar();
		
		calendar.setCalendarName(calendarNames);
		calendar.setCalendarCronExpression(qrtzCalendar.getCronExpression().toString());
		calendar.setCalendarDescription(qrtzCalendar.getDescription());

		return calendar;

	}

	public void addCalendar(QrtzCalendar calendarVO) throws ParseException, SchedulerException {
		CronCalendar cronCalendar = new CronCalendar(calendarVO.getCalendarCronExpression());
		cronCalendar.setDescription(calendarVO.getCalendarDescription());
		cronCalendar.setTimeZone(calendarVO.getTIMEZONE());
		
		scheduler.addCalendar(calendarVO.getCalendarName(), cronCalendar, true, true);
	}
	
	public void deleteCalendar(String name) throws SchedulerException{
		scheduler.deleteCalendar(name);
		
	}
	
	public QrtzCalendar getCalendarDetail(String name) throws SchedulerException {
		QrtzCalendar calendarVO = qrtzCalendarToCalendarModel(name, (CronCalendar)scheduler.getCalendar(name));
		return calendarVO;
	}
	
	public void addCalendarToTrigger(String calendarName, String triggerName, String triggerGroup) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		Trigger trigger = scheduler.getTrigger(triggerKey);
		Trigger newTrigger = trigger.getTriggerBuilder().modifiedByCalendar(calendarName).build();
		
		scheduler.rescheduleJob(triggerKey, newTrigger);
	}
	
	public void removeCalendarFromTrigger(String triggerName, String triggerGroup) throws SchedulerException {
		TriggerKey triggerKey = new TriggerKey(triggerName, triggerGroup);
		Trigger trigger = scheduler.getTrigger(triggerKey);
		Trigger newTrigger = trigger.getTriggerBuilder().modifiedByCalendar(null).build();
		
		scheduler.rescheduleJob(triggerKey, newTrigger);
	}
	

}

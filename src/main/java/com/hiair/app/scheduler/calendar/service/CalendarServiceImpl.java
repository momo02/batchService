package com.hiair.app.scheduler.calendar.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.quartz.impl.calendar.CronCalendar;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.hiair.app.scheduler.calendar.model.QrtzCalendar;
import com.hiair.app.scheduler.trigger.model.QrtzTrigger;
import com.hiair.cmm.util.CmmStringUtil;

@Service
public class CalendarServiceImpl implements CalendarService {

	private static final Logger logger = LoggerFactory.getLogger(CalendarServiceImpl.class);

	public static Scheduler scheduler;

	public CalendarServiceImpl(SchedulerFactoryBean schedulerFactoryBean) {
		scheduler = schedulerFactoryBean.getScheduler();
	}
	
	public List<QrtzCalendar> list() throws SchedulerException {
		List<QrtzCalendar> calendarList = new ArrayList<QrtzCalendar>();
		
		for (String calendarNames : scheduler.getCalendarNames()) {
			calendarList.add(calendarToCalendarModel(calendarNames, (CronCalendar)scheduler.getCalendar(calendarNames)));
		}
		
		return calendarList;
		
	}
	
	public QrtzCalendar detail(String name) throws SchedulerException {
		QrtzCalendar qrtzCalendar = calendarToCalendarModel(name, (CronCalendar)scheduler.getCalendar(name));
		return qrtzCalendar;
	}
	
	public void add(String calendarCronExpression, String calendarDescription) throws ParseException, SchedulerException {
			CronCalendar cronCalendar = new CronCalendar(calendarCronExpression);
			cronCalendar.setDescription(calendarDescription);
			cronCalendar.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));

			scheduler.addCalendar("EX_" + CmmStringUtil.getTimeStamp(), cronCalendar, true, true);
	}

	public void delete(String calendarName) throws SchedulerException {
		Set<TriggerKey> triggerKeys = scheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup()); 
		
		for (TriggerKey triggerKey : triggerKeys) {
			Trigger trigger = scheduler.getTrigger(triggerKey);

			if (null != trigger.getCalendarName()) {
				if (trigger.getCalendarName().equals(calendarName)) {
					deleteFromTrigger(triggerKey);
				}
			}
		}
		
		scheduler.deleteCalendar(calendarName);
	}
	
	public List<QrtzTrigger> triggerList(String calendarName) throws SchedulerException {
		
		Set<TriggerKey> triggerKey = scheduler.getTriggerKeys(GroupMatcher.anyTriggerGroup()); 
		List<QrtzTrigger> triggerList = new ArrayList<QrtzTrigger>();
		
		for (TriggerKey triggerKeys : triggerKey) {
			
			CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKeys);
			TriggerState triggerState = scheduler.getTriggerState(triggerKeys);
			
			triggerList.add(triggerToTriggerModel(cronTrigger, triggerState, calendarName));
		}
		
		return triggerList;
	}
	
	public void addToTrigger(QrtzTrigger qrtzTrigger) throws SchedulerException {
		Trigger trigger = scheduler.getTrigger(qrtzTrigger.getTriggerKey());
		Trigger newTrigger = trigger.getTriggerBuilder().modifiedByCalendar(qrtzTrigger.getCalendarName()).build();
		
		scheduler.rescheduleJob(qrtzTrigger.getTriggerKey(), newTrigger);
	}
	
	public void deleteFromTrigger(TriggerKey triggerKey) throws SchedulerException {
		Trigger trigger = scheduler.getTrigger(triggerKey);
		Trigger newTrigger = trigger.getTriggerBuilder().modifiedByCalendar(null).build();
		
		scheduler.rescheduleJob(triggerKey, newTrigger);
	}

	public QrtzCalendar calendarToCalendarModel(String calendarName, CronCalendar qrtzCalendar) throws SchedulerException {
		QrtzCalendar calendar = new QrtzCalendar();
		
		calendar.setCalendarName(calendarName);
		calendar.setCalendarCronExpression(qrtzCalendar.getCronExpression().toString());
		calendar.setCalendarDescription(qrtzCalendar.getDescription());

		logger.debug("==============================================");
		logger.debug("::::: Calendar Name : " + calendarName);
		logger.debug("::::: CronExpression : " + qrtzCalendar.getCronExpression().toString());
		logger.debug("::::: Description : " + qrtzCalendar.getDescription());
		logger.debug("==============================================");
		
		return calendar;

	}
	
	public QrtzTrigger triggerToTriggerModel(CronTrigger cronTrigger, TriggerState triggerState, String targetCalendarName) throws SchedulerException {
		QrtzTrigger qrtzTrigger = new QrtzTrigger();
		
		qrtzTrigger.setTriggerName(cronTrigger.getJobKey().getName());
		qrtzTrigger.setTriggerGroup(cronTrigger.getJobKey().getGroup());
		qrtzTrigger.setJobName(cronTrigger.getJobKey().getName());
		qrtzTrigger.setJobGroup(cronTrigger.getJobKey().getGroup());
		qrtzTrigger.setCronExpression(cronTrigger.getCronExpression());
		qrtzTrigger.setDescription(cronTrigger.getDescription());
		qrtzTrigger.setPriority(cronTrigger.getPriority());
		qrtzTrigger.setTriggerState(triggerState.toString());
		qrtzTrigger.setStartTime(cronTrigger.getStartTime());
		qrtzTrigger.setEndTime(cronTrigger.getEndTime());
		qrtzTrigger.setNextFireTime(cronTrigger.getNextFireTime());
		qrtzTrigger.setPrevFireTime(cronTrigger.getPreviousFireTime());
		qrtzTrigger.setMisfireInstr(Integer.toString(cronTrigger.getMisfireInstruction()));
		qrtzTrigger.setCalendarName(targetCalendarName);
		
		logger.debug("==============================================");
		logger.debug(">>>>> [ " + cronTrigger.getKey() + " ] <<<<<");
		logger.debug("::::: Class(trigger type) : " + cronTrigger.getClass());
		logger.debug("::::: Cron Expression : " + cronTrigger.getCronExpression());
		logger.debug("::::: Description: " + cronTrigger.getDescription());
		logger.debug("::::: Priority: " + cronTrigger.getPriority());
		logger.debug("::::: Start Time: " + cronTrigger.getStartTime());
		logger.debug("::::: End Time: " + cronTrigger.getEndTime());
		logger.debug("::::: PreviousFireTime : " + cronTrigger.getPreviousFireTime());
		logger.debug("::::: NextFireTime : " + cronTrigger.getNextFireTime());
		if(null != cronTrigger.getCalendarName()){
			if (cronTrigger.getCalendarName().equals(targetCalendarName)) qrtzTrigger.setTargetCalendar(true);
		}
		logger.debug("::::: isTargetCalendar : " + qrtzTrigger.isTargetCalendar());
		logger.debug("==============================================");
		
		return qrtzTrigger;
		
	}

}

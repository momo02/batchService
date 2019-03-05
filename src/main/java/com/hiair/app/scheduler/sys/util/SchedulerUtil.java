package com.hiair.app.scheduler.sys.util;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class SchedulerUtil {

	private static final Logger logger = LoggerFactory.getLogger(SchedulerUtil.class);
	
	public static Scheduler scheduler;

	public SchedulerUtil(SchedulerFactoryBean schedulerFactoryBean) {
		scheduler = schedulerFactoryBean.getScheduler();
		System.out.println(">>>>>" + scheduler);
	}
	
	public static boolean checkJobExists(String jobName, String jobGroup) throws SchedulerException {
		return scheduler.checkExists(new JobKey(jobName, jobGroup));
	}	
	
	public static boolean checkTriggerExists(String triggerName, String triggerGroup) throws SchedulerException {
		return scheduler.checkExists(new TriggerKey(triggerName, triggerGroup));
	}
	
	public static boolean checkCalendarExists(String calendarName) throws SchedulerException {
		return scheduler.getCalendarNames().contains(calendarName);
	}
	
}

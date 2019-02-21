package com.hiair.app.schedule.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component
@DisallowConcurrentExecution /* 작업의 여러 인스턴스가 동시 실행되지 않도록 (IS_NONCONCURRENT=ture)*/
@PersistJobDataAfterExecution /* to prevent race-conditions on saved data. (IS_UPDATE_DATA=ture)*/
public class CalendarShopJob implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(CalendarShopJob.class); 
	
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		
		JobKey jobKey = jobContext.getJobDetail().getKey();
		TriggerKey triggerKey = jobContext.getTrigger().getKey();
		
		logger.debug("--------------------------------------------------------------------");
		logger.debug(":::::::::::::: CalendarShopJob ::::::::::::::");
		
		JobDataMap dataMap = jobContext.getJobDetail().getJobDataMap();
		//실행 횟수 증가
		int excCount = Integer.parseInt(dataMap.getString("EXECUTION_COUNT"));
		dataMap.put("EXECUTION_COUNT", Integer.toString(++excCount));
		
		String depart = dataMap.getString("depart");
		String arrival = dataMap.getString("arrival");
		String month = dataMap.getString("month");
		logger.debug(">>>>> depart : " + depart);
		logger.debug(">>>>> arrival : " + arrival);
		logger.debug(">>>>> month : " + month);
		
		logger.debug("- 실행 횟수 : " + excCount); 
		logger.debug("- 실행 시간 : " + jobContext.getFireTime()); 
		logger.debug("- 다음 실행 시간 : " + jobContext.getNextFireTime()); 
		logger.debug("- thread 명 : " + Thread.currentThread().getName());
		
		logger.debug("- jobKey : " + jobKey);
		logger.debug("- jobDesc : " + jobContext.getJobDetail().getDescription());
		
		logger.debug("- triggerKey : " + triggerKey);
		logger.debug("- TriggerDesc : " + jobContext.getTrigger().getDescription());
		logger.debug("--------------------------------------------------------------------");
	}
}


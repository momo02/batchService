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

@Component
@PersistJobDataAfterExecution 
@DisallowConcurrentExecution
public class Job5 implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(Job5.class); 
	private static int count;
	
	private String jobData;
	
	public String getJobData() {
		return jobData;
	}

	public void setJobData(String jobData) {
		this.jobData = jobData;
	}

	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		
		JobKey jobKey = jobContext.getJobDetail().getKey();
		TriggerKey triggerKey = jobContext.getTrigger().getKey();
		
		System.out.println("--------------------------------------------------------------------");
		System.out.println("Job5 start: " + jobContext.getFireTime());
		count++;
		System.out.println("Job count " + count);		
		System.out.println("Job5 next scheduled time: " + jobContext.getNextFireTime());
		System.out.println("Job's thread name is: " + Thread.currentThread().getName());
		
		logger.debug("jobKey : " + jobKey);
		logger.debug("triggerKey : " + triggerKey);
		logger.debug("TriggerDesc : " + jobContext.getTrigger().getDescription());
		
//		JobDataMap dataMap = jobContext.getJobDetail().getJobDataMap();
//		String jobData = dataMap.getString("jobData");
//		logger.debug("jobData : " + jobData);
		
		JobDataMap dataMap = jobContext.getMergedJobDataMap(); 
		
		logger.debug("test : " + dataMap.getString("test"));
		logger.debug("test2 : " + dataMap.getString("test2"));
		
		System.out.println("Job end");
		System.out.println("--------------------------------------------------------------------");
		try {
			
			//cf.. 해당 실행이 복구 된 job의 실행일 경우에 true. 
			//(job 생성 시, requestRecovery(true) 설정을 해야함)
			logger.debug("isRecovering : " + jobContext.isRecovering());
			Thread.sleep(30000); //10초실행.....
			logger.debug("실행끝!!!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			JobExecutionException e2 = new JobExecutionException(e);
        	// 즉시 트리거 재실행 요청 (this job will refire immediately )
        	e2.refireImmediately();
        	throw e2;
			
			// 이 작업과 관련된 모든 트리거의 일정을 자동으로 해제하여 다시 실행되지 않도록 함
			// (Quartz will automatically unschedule all triggers associated with this job so that it does not run again)
//        	e2.setUnscheduleAllTriggers(true);
		}
	}

}

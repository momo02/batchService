package com.hiair.app.schedule.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
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
public class Job3 implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(Job1.class); 
	private static int count;
	
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		
		count++;
		//System.out.println("Job3 getRefireCount : " +  jobContext.getRefireCount());
		
		JobKey jobKey = jobContext.getJobDetail().getKey();
		TriggerKey triggerKey = jobContext.getTrigger().getKey();
		
		System.out.println("--------------------------------------------------------------------");
		System.out.println("Job3 start: " + jobContext.getFireTime());
		
		System.out.println("Job count " + count);		
		System.out.println("Job3 next scheduled time: " + jobContext.getNextFireTime());
		System.out.println("Job's thread name is: " + Thread.currentThread().getName());
		
		logger.debug("jobKey : " + jobKey);
		logger.debug("triggerKey : " + triggerKey);
		logger.debug("TriggerDesc : " + jobContext.getTrigger().getDescription());
		
		System.out.println("Job end");
		System.out.println("--------------------------------------------------------------------");
		try {
			
			//Thread.sleep(20000);
			int a = 1/0; //에러 발생 시킴 
			
		} catch (Exception e) {
			e.printStackTrace();
			JobExecutionException e2 = new JobExecutionException(e);
			
        	//1) 즉시 트리거 재실행 요청 (this job will refire immediately )
			e2.refireImmediately();
			throw e2;
			
    		//2) 이 작업과 관련된 모든 트리거의 일정을 자동으로 해제하여 다시 실행되지 않도록 함
			//(Quartz will automatically unschedule all triggers associated with this job so that it does not run again)
        	//e2.setUnscheduleAllTriggers(true);
		}
	}

}

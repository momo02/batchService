package com.hiair.app.scheduler.job;

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
public class Job2 implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(Job1.class); 
	
	private static int count;
	
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		
		JobKey jobKey = jobContext.getJobDetail().getKey();
		TriggerKey triggerKey = jobContext.getTrigger().getKey();
		
		System.out.println("--------------------------------------------------------------------");
		System.out.println("Job2 start: " + jobContext.getFireTime());
		count++;
		System.out.println("Job count " + count);		
		System.out.println("Job2 next scheduled time: " + jobContext.getNextFireTime());
		System.out.println("Job's thread name is: " + Thread.currentThread().getName());
		
		logger.debug("jobKey : " + jobKey);
		logger.debug("triggerKey : " + triggerKey);
		logger.debug("TriggerDesc : " + jobContext.getTrigger().getDescription());
		
		System.out.println("Job end");
		System.out.println("--------------------------------------------------------------------");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

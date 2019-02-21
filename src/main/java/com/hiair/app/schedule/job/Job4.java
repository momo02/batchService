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
public class Job4 implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(Job4.class); 
	private static int count;
	
	public static final String NUM_EXECUTIONS = "NumExecutions";
    public static final String EXECUTION_DELAY = "ExecutionDelay";
	
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		
		JobKey jobKey = jobContext.getJobDetail().getKey();
		TriggerKey triggerKey = jobContext.getTrigger().getKey();
		
		System.out.println("--------------------------------------------------------------------");
		System.out.println("Job4 start: " + jobContext.getFireTime());
		count++;
		System.out.println("Job count " + count);		
		System.out.println("Job4 next scheduled time: " + jobContext.getNextFireTime());
		System.out.println("Job's thread name is: " + Thread.currentThread().getName());
		
		logger.debug("jobKey : " + jobKey);
		logger.debug("triggerKey : " + triggerKey);
		logger.debug("TriggerDesc : " + jobContext.getTrigger().getDescription());
		
		System.out.println("Job end");
		System.out.println("--------------------------------------------------------------------");
		
		//////////////////////////////
		JobDataMap map = jobContext.getJobDetail().getJobDataMap();

        int executeCount = 0;
        if (map.containsKey(NUM_EXECUTIONS)) {
            executeCount = map.getInt(NUM_EXECUTIONS);
        }
        executeCount++;
        logger.debug(">>>>>>>> executeCount >>>>>>>>> : " + executeCount);
        
        map.put(NUM_EXECUTIONS, executeCount);
		
        long delay = 2000l;  //EXECUTION_DELAY 값이전달되지 않으면 작업의 대기 시간은 디폴트 5초
        if (map.containsKey(EXECUTION_DELAY)) {
            delay = map.getLong(EXECUTION_DELAY);
        }

        try {
        	Thread.sleep(delay);
            
        } catch (Exception e) {
        	
			e.printStackTrace();
			JobExecutionException e2 = new JobExecutionException(e);
        	// 즉시 트리거 재실행 요청 (this job will refire immediately )
        	e2.refireImmediately();
        	throw e2;
			
			// 이 작업과 관련된 모든 트리거의 일정을 자동으로 해제하여 다시 실행되지 않도록 함
			// (Quartz will automatically unschedule all triggers associated with this job so that it does not run again)
        	// e2.setUnscheduleAllTriggers(true);
        }

	}

}

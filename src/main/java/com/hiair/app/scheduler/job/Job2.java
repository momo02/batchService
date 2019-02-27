package com.hiair.app.scheduler.job;

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
@PersistJobDataAfterExecution /* to prevent race-conditions on saved data. (IS_UPDATE_DATA=ture)*/
@DisallowConcurrentExecution /* 작업의 여러 인스턴스가 동시 실행되지 않도록 (IS_NONCONCURRENT=ture)*/
public class Job2 implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(Job2.class); 
	
	private static int count;
	
	private int count2 = 0; // 클래스 멤버변수 
	//cf..  Quartz는 각 실행 중에 항상 클래스의 새 인스턴스를 생성 (멤버 변수가 상태를 유지하는 데 사용되는 것을 방지) 
	//      이 값은 항상 1로 표시 

	//실행 로직 구현
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		
		JobKey jobKey = jobContext.getJobDetail().getKey();
		TriggerKey triggerKey = jobContext.getTrigger().getKey();
		
//		this.jobkey = jobContext.getJobDetail().getKey();          
//		this.triggerKey = jobContext.getTrigger().getKey();    
		
		logger.debug("--------------------------------------------------------------------");
		logger.debug("Job1 start: " + jobContext.getFireTime()); //작업 실행 시간
		count++;
		count2++;
		//logger.debug("Job count " + count);		
		//logger.debug("Job1 next scheduled time: " + jobContext.getNextFireTime());
		//logger.debug("Job's thread name is: " + Thread.currentThread().getName());
		
		logger.debug("all job instance execute count :" + count);		
		logger.debug("member variable count : " + count2);		
		
		JobDataMap dataMap = jobContext.getJobDetail().getJobDataMap();
		
		if(dataMap.containsKey("EXECUTION_COUNT")) {
			int excCount = Integer.parseInt( dataMap.getString("EXECUTION_COUNT"));
			excCount++;
			logger.debug("-current job instance execute count : " + excCount);
			dataMap.put("EXECUTION_COUNT", Integer.toString(excCount));
		}
		logger.debug("- jobKey : " + jobKey);
		logger.debug("- triggerKey : " + triggerKey);
		//logger.debug("paramData1 : " + paramData1);
		//logger.debug("paramData2 : " + paramData2);
		logger.debug("- TriggerDesc : " + jobContext.getTrigger().getDescription());
		
//		try {
//			//Thread.sleep(60000);//1분 실행...
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		logger.debug("Job end");
		logger.debug("--------------------------------------------------------------------");
		
	}

}

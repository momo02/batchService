package com.hiair.app.scheduler.job;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.hiair.app.sample.test.service.SampleQueueService;
import com.hiair.app.sample.test.vo.SampleQueueVO;
//TODOJ DemandTicketJob
//@Component
@PersistJobDataAfterExecution 
@DisallowConcurrentExecution 
public class DemandTicketJob implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(DemandTicketJob.class); 
	
	@Autowired
	private SampleQueueService sampleQueueService;

	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		
		JobKey jobKey = jobContext.getJobDetail().getKey();
		TriggerKey triggerKey = jobContext.getTrigger().getKey();
		
		logger.debug("--------------------------------------------------------------------");
		logger.debug("::::::::::::::::::::: DemandTicketJob start :::::::::::::::::::::");
//		logger.debug("- fire time: " + jobContext.getNextFireTime()); //작업 실행 시간
//		logger.debug("- next scheduled time: " + jobContext.getNextFireTime()); //다음 실행 시간
//		logger.debug("- thread name is: " + Thread.currentThread().getName());
//		logger.debug("- jobKey : " + jobKey);
//		logger.debug("- jobDesc : " + jobContext.getJobDetail().getDescription());
//		logger.debug("- triggerKey : " + triggerKey);
//		logger.debug("- TriggerDesc : " + jobContext.getTrigger().getDescription());
		
		try {
			
			// 1. 전체 발권 대상 조회 
		    // (처리상태가 FAIL 또는 NULL 이고, 재시도 횟수가 10보다 작은 데이터) 
			List<SampleQueueVO> qList = null;
			qList = sampleQueueService.select();
			logger.debug("::::: 처리할 큐 list size : " + qList.size());
			
			// 2. for문을 돌리면서 하나씩 발권처리
			for(SampleQueueVO q : qList) {
				
				try {
					
				    /////////////////// 발권처리 ///////////////////
					
					logger.debug("::::: 처리 seq : " + q.getSeq());
					
					//테스트 에러발생 (seq 1,4,10) 
					if(q.getSeq() == 1 || q.getSeq() == 4 || q.getSeq() == 10) {
						int a = 1/0;
					} 
					
					logger.debug("::::: 처리 SUCCESS ");
					q.setPrcsCd("SUCCESS");
					sampleQueueService.update(q);
					
				}catch(Exception e) {
					//db 변경 내용이있다면 롤백 처리..
					
					logger.debug("::::: 처리 FAIL ");
					q.setPrcsCd("FAIL");
					q.setDetail(makeStackTrace(e));
					q.setRtryCnt(q.getRtryCnt() + 1);
					
					try {
						sampleQueueService.update(q);
					
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
				
			}
		logger.debug("::::::::::::::::::::: DemandTicketJob end :::::::::::::::::::::");	
		logger.debug("--------------------------------------------------------------------");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			JobExecutionException e2 = new JobExecutionException(e);
        	// 즉시 트리거 재실행 요청 (this job will refire immediately )
        	e2.refireImmediately();
        	throw e2;
			// 이 작업과 관련된 모든 트리거의 일정을 자동으로 해제하여 다시 실행되지 않도록 함
			// (Quartz will automatically unschedule all triggers associated with this job so that it does not run again)
        	//e2.setUnscheduleAllTriggers(true);
		}
	}

	
	public String makeStackTrace(Throwable t) {
		if (t == null)
			return "";
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			t.printStackTrace(new PrintStream(bout));
			bout.flush();
			return new String(bout.toByteArray());//.substring(0, 4000);
		} catch (Exception ex) {
			return "";
		}
	}	
}

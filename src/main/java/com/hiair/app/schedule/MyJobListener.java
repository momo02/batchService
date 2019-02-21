package com.hiair.app.schedule;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class MyJobListener implements JobListener {
	 //JobDetail이 실행될 떄 스케줄러에 의해서 호출되는 메서드
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
		System.out.println("Job to be exected: " + context.getFireInstanceId() + ", job listener: " + getName());
	}
	
	 // JobDetail이 실행될 때 TriggerListener가 실행
	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
	}
	
	// Job 실행이 완료된 후 호출되는 메서드, JOB 실행후 처리할 로직을 여기에 구현
	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		System.out.println("Job was exected: " + context.getFireInstanceId() + ", job listener: " + getName());
	}
	@Override
	public String getName() {
		return "MyJobListener";
	}
}

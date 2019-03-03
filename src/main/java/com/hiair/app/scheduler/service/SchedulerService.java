package com.hiair.app.scheduler.service;

import org.quartz.SchedulerException;

public interface SchedulerService {

	//스케쥴 시작
	public void start() throws SchedulerException;
	
	//스케쥴 중지
	public void stop() throws SchedulerException;
	
	//스케쥴 종료 
	public void shutdown() throws SchedulerException;
	
	//모든 Trigger 중지
	public void pauseAll() throws SchedulerException;
	
	//모든 Trigger 재시작
	public void resumeAll() throws SchedulerException;
	
	//스케쥴러의 모든 스케쥴 정보 조회
	public void selectScheduleAll() throws SchedulerException;
	
}

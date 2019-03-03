package com.hiair.app.scheduler.job.service;

import java.util.List;

import org.quartz.SchedulerException;

import com.hiair.app.scheduler.job.model.QrtzJob;

public interface JobService {

	//모든 Job List 조회 
	public List<QrtzJob> list() throws SchedulerException;
		
	//특정 Job Group의 Job List 조회 
	public List<QrtzJob> listByGroup(String JobGroup) throws SchedulerException;
		
	//특정 Job 저장
	public void add(String jobName, String jobGroup, String description) throws SchedulerException, ClassNotFoundException;
	
	//특정 Job 삭제
	public void delete(String jobName, String jobGroup) throws SchedulerException;
	
	//모든 Job 삭제 - 미사용
	public void deleteAll() throws SchedulerException;
	
	//특정 Job 중지 => 해당 Job의 모든 Trigger를 중지 - 미사용
	public void pause(String jobName, String jobGroup) throws SchedulerException;
	
	//특정 Job 재시작 => 해당 Job의 모든 Trigger를 재시작 - 미사용
	public void resume(String jobName, String jobGroup) throws SchedulerException;
	
}

package com.hiair.app.scheduler.trigger.service;

import java.text.ParseException;
import java.util.List;

import org.quartz.SchedulerException;

import com.hiair.app.scheduler.trigger.model.QrtzTrigger;


public interface TriggerService {

	//특정 Job의 Trigger List 조회 
	public List<QrtzTrigger> list(String jobName, String jobGroup) throws SchedulerException;
	
	//특정 Trigger Detail
	public QrtzTrigger detail(String triggerName, String triggerGroup) throws SchedulerException;
	
	//특정 Job에 Trigger 추가
	public void add(QrtzTrigger qrtzTrigger) throws SchedulerException, ParseException;
	
	//특정 Trigger 중지  
	public void pause(String triggerName, String triggerGroup) throws SchedulerException;
	
	//특정 Trigger 재시작
	public void resume(String triggerName, String triggerGroup) throws SchedulerException;
	
	//특정 Trigger 삭제
	public void delete(String triggerName, String triggerGroup) throws SchedulerException;
	
	//특정 Trigger Update
	public void update(QrtzTrigger trigger) throws SchedulerException, ParseException;
	
	//모든 Trigger 중지 - 미사용
	public void pauseAll() throws SchedulerException;
	
	//모든 Trigger 재시작 - 미사용
	public void resumeAll() throws SchedulerException;

}

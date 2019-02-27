package com.hiair.app.scheduler.service;

import java.text.ParseException;
import java.util.List;

import org.quartz.SchedulerException;

import com.hiair.app.scheduler.model.QrtzCalendar;
import com.hiair.app.scheduler.model.QrtzJob;
import com.hiair.app.scheduler.model.QrtzSchedulerJob;
import com.hiair.app.scheduler.model.QrtzTrigger;

public interface SchedulerService {

	//스케쥴 시작
	public void start() throws SchedulerException;
	
	//스케쥴 중지
	public void stop() throws SchedulerException;
	
	//스케쥴 종료 
	public void shutdown() throws SchedulerException;
	
	//스케쥴 등록 (Job & Trigger 등록)
	public void scheduleJob(QrtzSchedulerJob vo) throws SchedulerException, ClassNotFoundException;
	
	//스케쥴 업데이트
	public void rescheduleJob(QrtzTrigger vo) throws SchedulerException, ParseException;
	
	//특정 Job 저장
	public void addJob(QrtzJob vo) throws SchedulerException, ClassNotFoundException;
	
	//특정 Job 삭제
	public void deleteJob(String jobName, String jobGroup) throws SchedulerException;
	
	//모든 Job 삭제
	public void deleteJobAll() throws SchedulerException;
	
	//특정 Job 중지 => 해당 Job의 모든 Trigger를 중지
	public void pauseJob(String jobName, String jobGroup) throws SchedulerException;
	
	//특정 Job 재시작 => 해당 Job의 모든 Trigger를 재시작
	public void resumeJob(String jobName, String jobGroup) throws SchedulerException;

	//특정 Job에 Trigger 추가
	public QrtzTrigger addTrigger(QrtzTrigger vo) throws SchedulerException, ParseException;
	
	//특정 Trigger 또는 Trigger 그룹 중지  
	public void pauseTrigger(String triggerName, String triggerGroup) throws SchedulerException;
	
	//특정 Trigger 또는 Trigger 그룹 재시작
	public void resumeTrigger(String triggerName, String triggerGroup) throws SchedulerException;
	
	//특정 Trigger 삭제
	public void deleteTrigger(String triggerName, String triggerGroup) throws SchedulerException;
	
	//모든 Trigger 중지
	public void pauseAll() throws SchedulerException;
	
	//모든 Trigger 재시작
	public void resumeAll() throws SchedulerException;
	
	//스케쥴러의 모든 스케쥴 정보 조회
	public void selectScheduleAll() throws SchedulerException;
	
	//모든 Job List 조회 
	public List<QrtzJob> getJobAll() throws SchedulerException;
	
	//특정 Job Group의 Job List 조회 
	public List<QrtzJob> getJobsByGroup(String JobGroup) throws SchedulerException;
	
	//특정 Job의 Trigger List 조회 
	public List<QrtzTrigger> getTriggersOfJob(String jobName, String jobGroup) throws SchedulerException;
	
	//특정 Trigger Detail
	public QrtzTrigger getTriggerDetail(String triggerName, String triggerGroup) throws SchedulerException;

	//특정 Trigger Update
	public QrtzTrigger updateTrigger(QrtzTrigger trigger) throws SchedulerException, ParseException;

	//예외 일자 리스트
	public List<QrtzCalendar> getCalendarList() throws SchedulerException;
	
	//예외 일자 디테일
	public QrtzCalendar getCalendarDetail(String name) throws SchedulerException;

	public void addCalendar(QrtzCalendar calendarVO) throws ParseException, SchedulerException;

	public void deleteCalendar(String calendarName) throws SchedulerException;

	public void addCalendarToTrigger(String calendarName, String triggerName, String triggerGroup) throws SchedulerException;

	public void removeCalendarFromTrigger(String triggerName, String triggerGroup) throws SchedulerException;
	
	
}

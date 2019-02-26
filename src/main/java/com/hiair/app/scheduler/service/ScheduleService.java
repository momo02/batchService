package com.hiair.app.scheduler.service;

import java.text.ParseException;
import java.util.List;
import java.util.Set;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;

import com.hiair.app.scheduler.vo.JobDetailsVO;
import com.hiair.app.scheduler.vo.ScheduleJobVO;
import com.hiair.app.scheduler.vo.TriggerVO;

public interface ScheduleService {

//	public Scheduler getScheduler() throws SchedulerException;
	
	//스케쥴 시작
	public void start() throws SchedulerException;
	
	//스케쥴 중지
	public void stop() throws SchedulerException;
	
	//스케쥴 종료 
	public void shutdown() throws SchedulerException;
	
	//스케쥴 등록 (Job & Trigger 등록)
	public void scheduleJob(ScheduleJobVO vo) throws SchedulerException, ClassNotFoundException;
	
	//스케쥴 업데이트
	public void rescheduleJob(TriggerVO vo) throws SchedulerException, ParseException;
	
	//특정 Job 저장
	public void addJob(JobDetailsVO vo) throws SchedulerException, ClassNotFoundException;
	
	//특정 Job 삭제
	public void deleteJob(JobDetailsVO vo) throws SchedulerException;
	
	//모든 Job 삭제
	public void deleteJobAll() throws SchedulerException;
	
	//특정 Job 중지 => 해당 Job의 모든 Trigger를 중지
	public void pauseJob(JobDetailsVO vo) throws SchedulerException;
	
	//특정 Job 재시작 => 해당 Job의 모든 Trigger를 재시작
	public void resumeJob(JobDetailsVO vo) throws SchedulerException;

	//특정 Job에 Trigger 추가
	public TriggerVO addTrigger(TriggerVO vo) throws SchedulerException, ParseException;
	
	//특정 Trigger 또는 Trigger 그룹 중지  
	public void pauseTrigger(TriggerVO vo) throws SchedulerException;
	
	//특정 Trigger 또는 Trigger 그룹 재시작
	public void resumeTrigger(TriggerVO vo) throws SchedulerException;
	
	//모든 Trigger 중지
	public void pauseAll() throws SchedulerException;
	
	//모든 Trigger 재시작
	public void resumeAll() throws SchedulerException;
	
	//스케쥴러의 모든 스케쥴 정보 조회
	public void selectScheduleAll() throws SchedulerException;
	
	//모든 Job List 조회 
	public List<JobDetailsVO> getJobAll() throws SchedulerException;
	
	//특정 Job Group의 Job List 조회 
	public List<JobDetailsVO> getJobsByGroup(String JobGroup) throws SchedulerException;
	
	//특정 Job의 Trigger List 조회 
	public List<TriggerVO> getTriggersOfJob(JobDetailsVO vo) throws SchedulerException;
	
	//특정 Trigger 삭제
	public void deleteTrigger(TriggerVO vo) throws SchedulerException;
	
	//특정 Trigger Detail
	public <T extends TriggerVO> T getTriggerDetail(TriggerVO trigger) throws SchedulerException;

	//특정 Trigger Update
	public TriggerVO updateTrigger(TriggerVO trigger) throws SchedulerException;
}

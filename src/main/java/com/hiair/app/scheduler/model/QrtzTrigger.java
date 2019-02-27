package com.hiair.app.scheduler.model;

import java.util.Date;
import java.util.TimeZone;

import org.quartz.JobDataMap;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class QrtzTrigger {

	@ApiModelProperty(notes = "트리거 명", required = true, example = "job1_Trigger")
	private String triggerName;

	@ApiModelProperty(notes = "트리거 그룹", required = true, example = "IBE")
	private String triggerGroup;

	@ApiModelProperty(notes = "작업 명", required = true, example = "job1")
	private String jobName;

	@ApiModelProperty(notes = "작업 그룹명", required = true, example ="IBE")
	private String jobGroup;

	@ApiModelProperty(notes = "트리거 설명", example = "트리거 - 30초마다 실행")
	private String description;

	@ApiModelProperty(hidden = true)
	private int priority = Trigger.DEFAULT_PRIORITY;
	@ApiModelProperty(hidden = true)
	private String triggerState;

	@ApiModelProperty(notes = "트리거 타입, Default = CRON", required = true, example = "cron")
	private String triggerType = "CRON";
	@ApiModelProperty(notes = "크론 표현식", required = true, example = "0/30 * * * * ?")
	private String cronExpression;
	
	@ApiModelProperty(notes = "타임 존 : ASIA/SEOUL", hidden = true)
	private final TimeZone TIMEZONE = TimeZone.getTimeZone("Asia/Seoul");

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	@ApiModelProperty(notes = "시작 일시 : yyyy-MM-dd hh:mm:ss, Default = new Date()")
	private Date startTime = new Date();

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	@ApiModelProperty(notes = "종료 일시 : yyyy-MM-dd hh:mm:ss")
	private Date endTime;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	@ApiModelProperty(hidden = true)
	private Date nextFireTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	@ApiModelProperty(hidden = true)
	private Date prevFireTime;

	@ApiModelProperty(hidden = true)
	private String misfireInstr;
	
	@ApiModelProperty(hidden = true)
	private String calendarName;
	
	@ApiModelProperty(notes = "작업 데이터", hidden = true)
	private JobDataMap jobDataMap;

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getTriggerState() {
		return triggerState;
	}

	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public Date getPrevFireTime() {
		return prevFireTime;
	}

	public void setPrevFireTime(Date prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	public String getMisfireInstr() {
		return misfireInstr;
	}

	public void setMisfireInstr(String misfireInstr) {
		this.misfireInstr = misfireInstr;
	}

	public JobDataMap getJobDataMap() {
		return jobDataMap;
	}

	public void setJobDataMap(JobDataMap jobDataMap) {
		this.jobDataMap = jobDataMap;
	}
	
	public TriggerKey getTriggerKey() {
		return TriggerKey.triggerKey(this.triggerName, this.triggerGroup);
	}

	public String getCalendarName() {
		return calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	public TimeZone getTIMEZONE() {
		return TIMEZONE;
	}

	@Override
	public String toString() {
		return "TriggerVO [triggerName=" + triggerName + ", triggerGroup=" + triggerGroup + ", jobName=" + jobName
				+ ", jobGroup=" + jobGroup + ", description=" + description + ", priority=" + priority
				+ ", triggerState=" + triggerState + ", triggerType=" + triggerType + ", cronExpression="
				+ cronExpression + ", TIMEZONE=" + TIMEZONE + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", nextFireTime=" + nextFireTime + ", prevFireTime=" + prevFireTime + ", misfireInstr=" + misfireInstr
				+ ", calendarName=" + calendarName + ", jobDataMap=" + jobDataMap + "]";
	}

	
	
}

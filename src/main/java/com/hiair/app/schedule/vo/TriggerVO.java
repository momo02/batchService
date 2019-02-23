package com.hiair.app.schedule.vo;

import java.util.Date;

import org.quartz.JobDataMap;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import io.swagger.annotations.ApiModelProperty;

@JsonTypeInfo(
        use= JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "triggerType",
        visible = true,
        defaultImpl = TriggerVO.class
)
@JsonSubTypes({
	@Type(value = SimpleTriggerVO.class, name = "SIMPLE"),
	@Type(value = CronTriggerVO.class, name = "CRON"),
})

public class TriggerVO {
	
	private String schedName;
	
	@ApiModelProperty(notes = "트리거 명")
	private String triggerName;
	
	@ApiModelProperty(notes = "트리거 그룹")
	private String triggerGroup;
	
	@ApiModelProperty(notes = "작업 명")
	private String jobName;
	
	@ApiModelProperty(notes = "작업 그룹")
	private String jobGroup;
	
	@ApiModelProperty(notes = "트리거 설명")
	private String description;
	
	private int priority = Trigger.DEFAULT_PRIORITY;
	private String triggerState;
	
	@ApiModelProperty(notes = "트리거 타입, deafult = CRON")
	private String triggerType = "CRON";
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	@ApiModelProperty(notes = "시작 일시, Deafult = new Date()")
	private Date startTime = new Date();
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	@ApiModelProperty(notes = "종료 일시")
	private Date endTime;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	private Date nextFireTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	private Date prevFireTime;
	
	private String calendarName;
	private String misfireInstr;
	private JobDataMap jobDataMap;
	
	private String calendarCronExpression;
	
	public String getSchedName() {
		return schedName;
	}
	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}
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
	public String getCalendarName() {
		return calendarName;
	}
	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
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
	public String getCalendarCronExpression() {
		return calendarCronExpression;
	}
	public void setCalendarCronExpression(String calendarCronExpression) {
		this.calendarCronExpression = calendarCronExpression;
	}
	
	public TriggerKey getTriggerKey() {
		return TriggerKey.triggerKey(this.triggerName, this.triggerGroup);
	}
	@Override
	public String toString() {
		return "TriggerVO [schedName=" + schedName + ", triggerName=" + triggerName + ", triggerGroup=" + triggerGroup
				+ ", jobName=" + jobName + ", jobGroup=" + jobGroup + ", description=" + description + ", nextFireTime="
				+ nextFireTime + ", prevFireTime=" + prevFireTime + ", priority=" + priority + ", triggerState="
				+ triggerState + ", triggerType=" + triggerType + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", calendarName=" + calendarName + ", misfireInstr=" + misfireInstr + ", jobDataMap=" + jobDataMap
				+ ", calendarCronExpression=" + calendarCronExpression + "]";
	}
	
}

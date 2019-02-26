package com.hiair.app.scheduler.vo;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("CRON")
public class CronTriggerVO extends TriggerVO {
	
	private String cronExpression;
	private String timeZoneId;
	
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getTimeZoneId() {
		return timeZoneId;
	}
	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}
	@Override
	public String toString() {
		return "CronTriggerVO [cronExpression=" + cronExpression + ", timeZoneId=" + timeZoneId + ", getSchedName()="
				+ getSchedName() + ", getTriggerName()=" + getTriggerName() + ", getTriggerGroup()=" + getTriggerGroup()
				+ ", getJobName()=" + getJobName() + ", getJobGroup()=" + getJobGroup() + ", getDescription()="
				+ getDescription() + ", getNextFireTime()=" + getNextFireTime() + ", getPrevFireTime()="
				+ getPrevFireTime() + ", getPriority()=" + getPriority() + ", getTriggerState()=" + getTriggerState()
				+ ", getTriggerType()=" + getTriggerType() + ", getStartTime()=" + getStartTime() + ", getEndTime()="
				+ getEndTime() + ", getCalendarName()=" + getCalendarName() + ", getMisfireInstr()=" + getMisfireInstr()
				+ ", getJobDataMap()=" + getJobDataMap() + ", getCalendarCronExpression()="
				+ getCalendarCronExpression() + ", getTriggerKey()=" + getTriggerKey() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}

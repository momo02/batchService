package com.hiair.app.scheduler.vo;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("SIMPLE")
public class SimpleTriggerVO extends TriggerVO {
	
	private int repeatCount;     // 반복 횟수
	private Long repeatInterval; // 반복 간격(ms)
	private Long timesTriggered;  
	
	public int getRepeatCount() {
		return repeatCount;
	}
	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}
	public Long getRepeatInterval() {
		return repeatInterval;
	}
	public void setRepeatInterval(Long repeatInterval) {
		this.repeatInterval = repeatInterval;
	}
	public Long getTimesTriggered() {
		return timesTriggered;
	}
	public void setTimesTriggered(Long timesTriggered) {
		this.timesTriggered = timesTriggered;
	}
	@Override
	public String toString() {
		return "SimpleTriggerVO [repeatCount=" + repeatCount + ", repeatInterval=" + repeatInterval
				+ ", timesTriggered=" + timesTriggered + ", getSchedName()=" + getSchedName() + ", getTriggerName()="
				+ getTriggerName() + ", getTriggerGroup()=" + getTriggerGroup() + ", getJobName()=" + getJobName()
				+ ", getJobGroup()=" + getJobGroup() + ", getDescription()=" + getDescription() + ", getNextFireTime()="
				+ getNextFireTime() + ", getPrevFireTime()=" + getPrevFireTime() + ", getPriority()=" + getPriority()
				+ ", getTriggerState()=" + getTriggerState() + ", getTriggerType()=" + getTriggerType()
				+ ", getStartTime()=" + getStartTime() + ", getEndTime()=" + getEndTime() + ", getCalendarName()="
				+ getCalendarName() + ", getMisfireInstr()=" + getMisfireInstr() + ", getJobDataMap()="
				+ getJobDataMap() + ", getCalendarCronExpression()=" + getCalendarCronExpression()
				+ ", getTriggerKey()=" + getTriggerKey() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
}

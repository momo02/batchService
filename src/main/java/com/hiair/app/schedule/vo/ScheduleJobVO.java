package com.hiair.app.schedule.vo;

import java.util.Set;

public class ScheduleJobVO {
	private JobDetailsVO job;
	private Set<TriggerVO> triggers;
	
	public JobDetailsVO getJob() {
		return job;
	}
	public void setJob(JobDetailsVO job) {
		this.job = job;
	}
	public Set<TriggerVO> getTriggers() {
		return triggers;
	}
	public void setTriggers(Set<TriggerVO> triggers) {
//		for(TriggerVO trigger: triggers) {
//			trigger.setJobGroup(this.job.getJobGroup());
//			trigger.setJobName(this.job.getJobName());
//		}
		this.triggers = triggers;
	}
}

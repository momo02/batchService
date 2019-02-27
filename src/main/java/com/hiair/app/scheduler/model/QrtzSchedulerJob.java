package com.hiair.app.scheduler.model;

import java.util.Set;

public class QrtzSchedulerJob {
	private QrtzJob job;
	private Set<QrtzTrigger> triggers;
	
	public QrtzJob getJob() {
		return job;
	}
	public void setJob(QrtzJob job) {
		this.job = job;
	}
	public Set<QrtzTrigger> getTriggers() {
		return triggers;
	}
	public void setTriggers(Set<QrtzTrigger> triggers) {
//		for(TriggerVO trigger: triggers) {
//			trigger.setJobGroup(this.job.getJobGroup());
//			trigger.setJobName(this.job.getJobName());
//		}
		this.triggers = triggers;
	}
}

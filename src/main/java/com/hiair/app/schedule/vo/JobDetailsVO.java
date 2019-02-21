package com.hiair.app.schedule.vo;

import org.quartz.JobDataMap;
import org.quartz.JobKey;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hiair.app.schedule.ScheduleConfig;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class JobDetailsVO {
	
	private String schedName;
	private String jobName;
	private String jobGroup;
	private String description;
	private String jobClassName;
	private String isDurable;
	private String isNonconcurrent;
	private String isUpdateData;
	private String requestsRecovery;
	private JobDataMap jobDataMap;
	private JobKey jobKey;
	
	public String getSchedName() {
		return schedName;
	}
	public void setSchedName(String schedName) {
		this.schedName = schedName;
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
	public String getJobClassName() {
		return ScheduleConfig.JOB_BASE_PACKAGE + jobClassName;
	}
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
	public String getIsDurable() {
		return isDurable;
	}
	public void setIsDurable(String isDurable) {
		this.isDurable = isDurable;
	}
	public String getIsNonconcurrent() {
		return isNonconcurrent;
	}
	public void setIsNonconcurrent(String isNonconcurrent) {
		this.isNonconcurrent = isNonconcurrent;
	}
	public String getIsUpdateData() {
		return isUpdateData;
	}
	public void setIsUpdateData(String isUpdateData) {
		this.isUpdateData = isUpdateData;
	}
	public String getRequestsRecovery() {
		return requestsRecovery;
	}
	public void setRequestsRecovery(String requestsRecovery) {
		this.requestsRecovery = requestsRecovery;
	}
	public JobDataMap getJobDataMap() {
		return jobDataMap;
	}
	public void setJobDataMap(JobDataMap jobDataMap) {
		this.jobDataMap = jobDataMap;
	}
	public JobKey getJobKey() {
		return JobKey.jobKey(this.jobName, this.jobGroup);
	}
	@Override
	public String toString() {
		return "JobDetailsVO [schedName=" + schedName + ", jobName=" + jobName + ", jobGroup=" + jobGroup
				+ ", description=" + description + ", jobClassName=" + jobClassName + ", isDurable=" + isDurable
				+ ", isNonconcurrent=" + isNonconcurrent + ", isUpdateData=" + isUpdateData + ", requestsRecovery="
				+ requestsRecovery + ", jobDataMap=" + jobDataMap + ", jobKey=" + jobKey + "]";
	}

}

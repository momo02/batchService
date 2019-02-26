package com.hiair.app.scheduler.vo;

import org.quartz.JobDataMap;
import org.quartz.JobKey;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class JobDetailsVO {
	//작업 클래스 BASE PACKAGE 명
	public static final String JOB_BASE_PACKAGE = "com.hiair.app.schedule.job.";
	
	@ApiModelProperty(notes = "스케줄명")
	private String schedName;
	
	@ApiModelProperty(notes = "작업명")
	private String jobName;
	
	@ApiModelProperty(notes = "작업그룹")
	private String jobGroup;
	
	@ApiModelProperty(notes = "작업설명")
	private String description;
	private String jobClassName;
	private String isDurable;
	private String isNonconcurrent;
	private String isUpdateData;
	private String requestsRecovery;
	
	@ApiModelProperty(notes = "작업 데이터")
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
		return JOB_BASE_PACKAGE + jobClassName;
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

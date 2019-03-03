package com.hiair.app.scheduler.job.model;

import org.quartz.JobDataMap;
import org.quartz.JobKey;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL) 
public class QrtzJob {
	//작업 클래스 BASE PACKAGE 명
//	public static final String JOB_BASE_PACKAGE = "com.hiair.app.scheduler.job.";
	
	@ApiModelProperty(notes = "작업 명", required = true, example = "job1")
	private String jobName;
	
	@ApiModelProperty(notes = "작업 그룹명", required = true, example ="IBE")
	private String jobGroup;
	
	@ApiModelProperty(notes = "작업 설명", example = "쿼츠 작업 : job1")
	private String description;
	
//	@ApiModelProperty(notes = "작업 클래스명", required = true, example = "Job1")
//	private String jobClassName;
	
	@ApiModelProperty(hidden = true) 
	private String isDurable;
	@ApiModelProperty(hidden = true)
	private String isNonconcurrent;
	@ApiModelProperty(hidden = true)
	private String isUpdateData;
	@ApiModelProperty(hidden = true)
	private String requestsRecovery;
	
	@ApiModelProperty(notes = "작업 데이터", hidden = true)
	private JobDataMap jobDataMap;
	
//	@ApiModelProperty(hidden = true)
//	private JobKey jobKey;
	
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
//	public String getJobClassName() {
//		return JOB_BASE_PACKAGE + jobClassName;
//	}
//	public void setJobClassName(String jobClassName) {
//		this.jobClassName = jobClassName;
//	}
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
//	public JobKey getJobKey() {
//		return JobKey.jobKey(this.jobName, this.jobGroup);
//	}
	
	@Override
	public String toString() {
		return "QrtzJob [jobName=" + jobName + ", jobGroup=" + jobGroup + ", description=" + description
				+ ", isDurable=" + isDurable + ", isNonconcurrent=" + isNonconcurrent + ", isUpdateData=" + isUpdateData
				+ ", requestsRecovery=" + requestsRecovery + ", jobDataMap=" + jobDataMap + "]";
	}
	

	
	
}

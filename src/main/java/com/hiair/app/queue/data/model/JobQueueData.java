package com.hiair.app.queue.data.model;

import org.apache.ibatis.type.Alias;

/**
 * 작업 큐 정보
 * @author jung
 */
@Alias("jobQueueData")
public class JobQueueData {
	
	private int queueDataNo;		  /* 큐 정보 번호 */
	private String jobGroup;		  /* 작업 그룹 명 */
	private String jobName;			  /* 작업 명 */
	private String queueClass;		  /* 큐 구분 */
	private int processCount;		  /* 처리 횟수 */
	private String recentProcessDate; /* 최근 처리 일시 */
	private String recentProcessCode; /* 최근 처리 코드 */
	private String recentRunServerIp; /* 최근 실행 서버 IP */
	private String resetDate;		  /* 초기화 일시 */
	private String resetUserId;		  /* 초기화 사용자 ID */
	
	public int getQueueDataNo() {
		return queueDataNo;
	}
	public void setQueueDataNo(int queueDataNo) {
		this.queueDataNo = queueDataNo;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getQueueClass() {
		return queueClass;
	}
	public void setQueueClass(String queueClass) {
		this.queueClass = queueClass;
	}
	public int getProcessCount() {
		return processCount;
	}
	public void setProcessCount(int processCount) {
		this.processCount = processCount;
	}
	public String getRecentProcessDate() {
		return recentProcessDate;
	}
	public void setRecentProcessDate(String recentProcessDate) {
		this.recentProcessDate = recentProcessDate;
	}
	public String getRecentProcessCode() {
		return recentProcessCode;
	}
	public void setRecentProcessCode(String recentProcessCode) {
		this.recentProcessCode = recentProcessCode;
	}
	public String getRecentRunServerIp() {
		return recentRunServerIp;
	}
	public void setRecentRunServerIp(String recentRunServerIp) {
		this.recentRunServerIp = recentRunServerIp;
	}
	public String getResetDate() {
		return resetDate;
	}
	public void setResetDate(String resetDate) {
		this.resetDate = resetDate;
	}
	public String getResetUserId() {
		return resetUserId;
	}
	public void setResetUserId(String resetUserId) {
		this.resetUserId = resetUserId;
	}
}

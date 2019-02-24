package com.hiair.app.batch.job.queue.group.model;

import org.apache.ibatis.type.Alias;

/**
 * 작업 큐 그룹(마스터) 정보
 * @author jung
 */
@Alias("jobQueueGroup")
public class JobQueueGroup {
	private String jobName;			/* 작업명 */
	private int retryCount;			/* 재시도 수 */
	private String produceUserId;	/* 생성 사용자 ID */
	private int threadCount;		/* 쓰레드 수 */
	private String queueSaveFlag;	/* 큐 저장 여부 */
	private String remarks;			/* 비고 */
	private String produceDate;		/* 생성 일시 */
	private String modifiedUserId;	/* 수정 사용자 ID */
	private String modifiedDate;	/* 수정 일시 */
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public int getRetryCount() {
		return retryCount;
	}
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}
	public String getProduceUserId() {
		return produceUserId;
	}
	public void setProduceUserId(String produceUserId) {
		this.produceUserId = produceUserId;
	}
	public int getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	public String getQueueSaveFlag() {
		return queueSaveFlag;
	}
	public void setQueueSaveFlag(String queueSaveFlag) {
		this.queueSaveFlag = queueSaveFlag;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getProduceDate() {
		return produceDate;
	}
	public void setProduceDate(String produceDate) {
		this.produceDate = produceDate;
	}
	public String getModifiedUserId() {
		return modifiedUserId;
	}
	public void setModifiedUserId(String modifiedUserId) {
		this.modifiedUserId = modifiedUserId;
	}
	public String getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}

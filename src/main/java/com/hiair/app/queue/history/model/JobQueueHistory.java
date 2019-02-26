package com.hiair.app.queue.history.model;

import org.apache.ibatis.type.Alias;

/**
 * 작업 큐 이력 정보
 * @author jung
 */
@Alias("jobQueueHistory")
public class JobQueueHistory {
	private int queueHistoryNo;		/* 큐 이력 번호 */
	private int queueDataNo;		/* 큐 정보 번호 */
	private String processCode;  	/* 처리 코드 */
	private String processContent;	/* 처리 내용 */
	private String processDate;		/* 처리 일시 */
	private String processServerIp;	/* 처리 서버 IP */
	
	public int getQueueHistoryNo() {
		return queueHistoryNo;
	}
	public void setQueueHistoryNo(int queueHistoryNo) {
		this.queueHistoryNo = queueHistoryNo;
	}
	public int getQueueDataNo() {
		return queueDataNo;
	}
	public void setQueueDataNo(int queueDataNo) {
		this.queueDataNo = queueDataNo;
	}
	public String getProcessCode() {
		return processCode;
	}
	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}
	public String getProcessContent() {
		return processContent;
	}
	public void setProcessContent(String processContent) {
		this.processContent = processContent;
	}
	public String getProcessDate() {
		return processDate;
	}
	public void setProcessDate(String processDate) {
		this.processDate = processDate;
	}
	public String getProcessServerIp() {
		return processServerIp;
	}
	public void setProcessServerIp(String processServerIp) {
		this.processServerIp = processServerIp;
	}
}

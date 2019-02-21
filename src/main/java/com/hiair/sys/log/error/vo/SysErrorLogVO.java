package com.hiair.sys.log.error.vo;

import org.apache.ibatis.type.Alias;

@Alias("sysErrorLog")
public class SysErrorLogVO {
	
	private String errorLogNo;
	private String systemCode;
	private String occurYmd;
	private String occurHms;
	private String processServerIp;
	private String javaName;
	private String methodName;
	private String userId;
	private String processUrl;
	private String errorSimpleContent;
	private String errorDetailContent;
	private String requestContent;
	
	public String getErrorLogNo() {
		return errorLogNo;
	}
	public void setErrorLogNo(String errorLogNo) {
		this.errorLogNo = errorLogNo;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	public String getOccurYmd() {
		return occurYmd;
	}
	public void setOccurYmd(String occurYmd) {
		this.occurYmd = occurYmd;
	}
	public String getOccurHms() {
		return occurHms;
	}
	public void setOccurHms(String occurHms) {
		this.occurHms = occurHms;
	}
	public String getProcessServerIp() {
		return processServerIp;
	}
	public void setProcessServerIp(String processServerIp) {
		this.processServerIp = processServerIp;
	}
	public String getJavaName() {
		return javaName;
	}
	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getProcessUrl() {
		return processUrl;
	}
	public void setProcessUrl(String processUrl) {
		this.processUrl = processUrl;
	}
	public String getErrorSimpleContent() {
		return errorSimpleContent;
	}
	public void setErrorSimpleContent(String errorSimpleContent) {
		this.errorSimpleContent = errorSimpleContent;
	}
	public String getErrorDetailContent() {
		return errorDetailContent;
	}
	public void setErrorDetailContent(String errorDetailContent) {
		this.errorDetailContent = errorDetailContent;
	}
	public String getRequestContent() {
		return requestContent;
	}
	public void setRequestContent(String requestContent) {
		this.requestContent = requestContent;
	}
}

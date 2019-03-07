package com.hiair.cmm.model;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class RestResponse {
	
	@ApiModelProperty(notes = "현재 API에 버전을 표출", required = true, example="1.0")
	private String version = "1.0";
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone = "Asia/Seoul")
	private Date dateTime = new Date();
	
	@ApiModelProperty(notes = "Error에 대한 Message 표출", required = true)
	private String errorMessage;
	
	@ApiModelProperty(notes = "1이상은 정상, 음수 부터 비정상", required = true)
	private String errorCode;
	
	@ApiModelProperty(notes = "아이템", required = false)
	private Map<String,Object> items;
	
	@ApiModelProperty(notes = "처리 개수", required = false)
	private int proCnt;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public int getProCnt() {
		return proCnt;
	}
	public void setProCnt(int proCnt) {
		this.proCnt = proCnt;
	}
	public Map<String, Object> getItems() {
		return items;
	}
	public void setItems(Map<String, Object> items) {
		this.items = items;
	}
	
	
}

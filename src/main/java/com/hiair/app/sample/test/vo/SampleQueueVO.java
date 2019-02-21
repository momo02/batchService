package com.hiair.app.sample.test.vo;

import org.apache.ibatis.type.Alias;

@Alias("sampleQueue")
public class SampleQueueVO {
	private int seq;
	private String regDate;
	private String prcsDate;
	private String prcsCd;
	private String detail;
	private int rtryCnt;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public String getPrcsDate() {
		return prcsDate;
	}
	public void setPrcsDate(String prcsDate) {
		this.prcsDate = prcsDate;
	}
	public String getPrcsCd() {
		return prcsCd;
	}
	public void setPrcsCd(String prcsCd) {
		this.prcsCd = prcsCd;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getRtryCnt() {
		return rtryCnt;
	}
	public void setRtryCnt(int rtryCnt) {
		this.rtryCnt = rtryCnt;
	}
	@Override
	public String toString() {
		return "SampleQueueVO [seq=" + seq + ", regDate=" + regDate + ", prcsDate=" + prcsDate + ", prcsCd=" + prcsCd
				+ ", detail=" + detail + ", rtryCnt=" + rtryCnt + "]";
	}
	
	

}

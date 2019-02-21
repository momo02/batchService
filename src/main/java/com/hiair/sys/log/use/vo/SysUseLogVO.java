/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : SysAccessVO.java
 * @Description   : 
 * @Author        : 지대한
 * @Version       : v1.0
 * Copyright(c) 2019 PALNETWORKS All rights reserved
 *------------------------------------------------------------------------------
 *                  변         경         사         항                       
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                      DESCRIPTION                        
 * ----------  ------  --------------------------------------------------------- 
 * 2019-01-11   지대한    신규생성                                     
 *------------------------------------------------------------------------------
 */
package com.hiair.sys.log.use.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * <pre>
 * com.hiair.sys.interceptor.access.vo
 * _SysAccessVO.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-11
 * @Version : 1.0
 */
@Alias("sysAccess")
public class SysUseLogVO {
	
	private String uri;
	private int userId;
	private String userName;
	private Date insertDate;
	
	public SysUseLogVO() {
	}
	
	public SysUseLogVO(String uri) {
		this.uri = uri;
	}
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
	@Override
	public String toString() {
		return "SysAccessVO [uri=" + uri + ", userId=" + userId + ", userName=" + userName + ", insertDate="
				+ insertDate + "]";
	}
	
}

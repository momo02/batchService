/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : CmmUserVO.java
 * @Description   : 
 * @Author        : 지대한
 * @Version       : v1.0
 * Copyright(c) 2019 PALNETWORKS All rights reserved
 *------------------------------------------------------------------------------
 *                  변         경         사         항                       
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                      DESCRIPTION                        
 * ----------  ------  --------------------------------------------------------- 
 * 2019-01-10   지대한    신규생성                                     
 *------------------------------------------------------------------------------
 */
package com.hiair.app.cmm.user.vo;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.hiair.app.cmm.vo.KendoDefaultVO;

/**
 * <pre>
 * com.hiair.app.cmm.user.vo
 * _CmmUserVO.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-10
 * @Version : 1.0
 */
@Alias("cmmUser")
public class CmmUserVO extends KendoDefaultVO implements Serializable{

	private static final long serialVersionUID = -404176003261690797L;
	
	private int id;
	private String userId;
	private String userName;
	private String password;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "CmmUserVO [id=" + id + ", userId=" + userId + ", userName=" + userName + ", password=" + password
				+ "]";
	}

	
}

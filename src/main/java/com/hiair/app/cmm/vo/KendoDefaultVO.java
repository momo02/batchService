/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : KendoDefaultVO.java
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
package com.hiair.app.cmm.vo;

import org.apache.ibatis.type.Alias;

/**
 * <pre>
 * com.hiair.app.cmm.vo
 * _KendoDefaultVO.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-10
 * @Version : 1.0
 */
@Alias("kendoDefault")
public class KendoDefaultVO {
	
	/* kendo dataSource uid */
	private String uid;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "KendoDefaultVO [uid=" + uid + "]";
	}
	
	
}

/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : SysAccessService.java
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
package com.hiair.sys.log.use.service;

import com.hiair.sys.log.use.vo.SysUseLogVO;

/**
 * <pre>
 * com.hiair.sys.interceptor.access.service
 * _SysAccessService.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-11
 * @Version : 1.0
 */
public interface SysUseLogService {

	public int insertLog(SysUseLogVO vo);
	
}

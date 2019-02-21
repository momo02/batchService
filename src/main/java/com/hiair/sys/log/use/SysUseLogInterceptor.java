/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : SysInterceptorAccess.java
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
package com.hiair.sys.log.use;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hiair.sys.log.use.service.SysUseLogService;
import com.hiair.sys.log.use.vo.SysUseLogVO;

/**
 * <pre>
 * com.hiair.sys.interceptor.access
 * _SysInterceptorAccess.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-10
 * @Version : 1.0
 */
public class SysUseLogInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	SysUseLogService service;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		SysUseLogVO vo = new SysUseLogVO(request.getRequestURI());
		int proc = service.insertLog(vo);
		
		return super.preHandle(request, response, handler);
	}
	
	
}

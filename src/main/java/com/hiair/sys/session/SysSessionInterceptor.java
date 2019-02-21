/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : SysInterceptorSession.java
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
package com.hiair.sys.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hiair.app.cmm.user.vo.CmmUserVO;
import com.hiair.cmm.util.CmmUserInfoManagemenet;

/**
 * <pre>
 * com.hiair.sys.interceptor.session
 * _SysInterceptorSession.java
 * </pre>
 * 
 * Desc : 세션 체크
 * @Company : PALNETWORKS
 * @Date 2019-01-10
 * @Version : 1.0
 */
public class SysSessionInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 세션에 유저정보가 있는지 확인후 없으면 로그인화면으로 리다이렉트 함.
		CmmUserVO user = CmmUserInfoManagemenet.getUserInfo();
		if(user == null || user.getId() == 0) {
			response.sendRedirect("/cmm/user/viewUserLogin.do");
			return false;
		}
		return super.preHandle(request, response, handler);
	}	
	
	
}
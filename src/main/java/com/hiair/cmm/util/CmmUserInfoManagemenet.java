package com.hiair.cmm.util;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.hiair.app.cmm.user.vo.CmmUserVO;


public class CmmUserInfoManagemenet {
	
	public static CmmUserVO getUserInfo() {
		if(RequestContextHolder.getRequestAttributes() == null) return null;
		return (CmmUserVO) RequestContextHolder.getRequestAttributes().getAttribute("cmmUserVO", RequestAttributes.SCOPE_SESSION);
	}
	
	public static void setUserInfo(CmmUserVO vo) {
		if(RequestContextHolder.getRequestAttributes() == null) return;
		RequestContextHolder.getRequestAttributes().setAttribute("cmmUserVO", vo, RequestAttributes.SCOPE_SESSION);
	}
	
	public static Boolean isUserInfo() {
		if(RequestContextHolder.getRequestAttributes() == null) return false;
		if(RequestContextHolder.getRequestAttributes().getAttribute("cmmUserVO", RequestAttributes.SCOPE_SESSION) == null) return false;
		return true;
	}
}

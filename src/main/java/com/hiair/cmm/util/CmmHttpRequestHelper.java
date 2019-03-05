package com.hiair.cmm.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class CmmHttpRequestHelper {
	public static boolean isHttpRequest() {
		try {
			getCurrentRequest();
		}catch(IllegalStateException ise){
			return false;
		}
		return true;
	}
	
	public static HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return sra.getRequest();
	}
	
	public static String getRequestIp() {
		HttpServletRequest req = getCurrentRequest();
		String ip = req.getHeader("X-FORWARDED-FOR");
		if(ip == null) {
			ip = req.getRemoteAddr();
		}
		return ip;
	}
	
	public static String getRequestURI() {
		return getCurrentRequest().getRequestURI();
	}
	
	public static HttpSession getCurrentSession() {
		return getCurrentRequest().getSession();
	}
	
	public static String getFirstUriPath() {
		String firstPath = null;
		String uri = getCurrentRequest().getRequestURI();
		String[] uriPath = uri.split("/");
		for(int i=0;i<uriPath.length;i++) {
			if(uriPath[i] != null && !"".equals(uriPath[i])) {
				firstPath = uriPath[i];
				break;
			}
		}
		return firstPath;
	}
	
	
	/**
	 * 클라이언트(Client)의 웹브라우저 종류를 조회하는 기능
	 * @param HttpServletRequest request Request객체
	 * @return String webKind 웹브라우저 종류
	 * @exception Exception
	*/
	public static String getClntWebKind(HttpServletRequest request) throws Exception {
		
		String user_agent = request.getHeader("user-agent");
		
		// 웹브라우저 종류 조회
		String webKind = "";
		if (user_agent.toUpperCase().indexOf("GECKO") != -1) {
			if (user_agent.toUpperCase().indexOf("NESCAPE") != -1) {
				webKind = "Netscape (Gecko/Netscape)";
			} else if (user_agent.toUpperCase().indexOf("FIREFOX") != -1) {
				webKind = "Mozilla Firefox (Gecko/Firefox)";
			} else {
				webKind = "Mozilla (Gecko/Mozilla)";
			}
		} else if (user_agent.toUpperCase().indexOf("MSIE") != -1) {
			if (user_agent.toUpperCase().indexOf("OPERA") != -1) {
				webKind = "Opera (MSIE/Opera/Compatible)";
			} else {
				webKind = "Internet Explorer (MSIE/Compatible)";
			}
		} else if (user_agent.toUpperCase().indexOf("SAFARI") != -1) {
			if (user_agent.toUpperCase().indexOf("CHROME") != -1) {
				webKind = "Google Chrome";
			} else {
				webKind = "Safari";
			}
		} else if (user_agent.toUpperCase().indexOf("THUNDERBIRD") != -1) {
			webKind = "Thunderbird";
		} else {
			webKind = "Other Web Browsers";
		}
		return webKind;
	}
	
	/**
	 * 클라이언트(Client)의 웹브라우저 버전을 조회하는 기능
	 * @param HttpServletRequest request Request객체
	 * @return String webVer 웹브라우저 버전
	 * @exception Exception
	*/
	public static String getClntWebVer(HttpServletRequest request) throws Exception {
		
		String user_agent = request.getHeader("user-agent");
		
		// 웹브라우저 버전 조회
		String webVer = "";
		String [] arr = {"MSIE", "OPERA", "NETSCAPE", "FIREFOX", "SAFARI"};
		for (int i = 0; i < arr.length; i++) {
			int s_loc = user_agent.toUpperCase().indexOf(arr[i]);
			if (s_loc != -1) {
				int f_loc = s_loc + arr[i].length();
				webVer = user_agent.toUpperCase().substring(f_loc, f_loc+5);
				webVer = webVer.replaceAll("/", "").replaceAll(";", "").replaceAll("^", "").replaceAll(",", "").replaceAll("//.", "");
			}
		}
		return webVer;
	}
	
}

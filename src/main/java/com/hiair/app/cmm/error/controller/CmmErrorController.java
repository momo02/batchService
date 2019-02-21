/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : CmmErrorController.java
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
package com.hiair.app.cmm.error.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 * com.hiair.app.cmm.error.controller
 * _CmmErrorController.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-10
 * @Version : 1.0
 */
@Controller
@RequestMapping("/cmm/error")
public class CmmErrorController {
	
	@RequestMapping("/error404.do")
	public String viewError404() {
		return "/cmm/error/error404.df";
	}
	
	@RequestMapping("/error500.do")
	public String viewError500() {
		return "/cmm/error/error500.df";
	}
	
	// 파일이 없을 경우
	@RequestMapping("/error9000.do")
	public String viewError9000() {
		return "/cmm/error/error9000.df";
	}
}

/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : CmmUserController.java
 * @Description   : 
 * @Author        : 지대한
 * @Version       : v1.0
 * Copyright(c) 2019 PALNETWORKS All rights reserved
 *------------------------------------------------------------------------------
 *                  변         경         사         항                       
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                      DESCRIPTION                        
 * ----------  ------  --------------------------------------------------------- 
 * 2019-01-14   지대한    신규생성                                     
 *------------------------------------------------------------------------------
 */
package com.hiair.app.cmm.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiair.app.cmm.user.vo.CmmUserVO;
import com.hiair.cmm.util.CmmUserInfoManagemenet;

/**
 * <pre>
 * com.hiair.app.cmm.user.controller
 * _CmmUserController.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-14
 * @Version : 1.0
 */
@Controller
@RequestMapping("/cmm/user")
public class CmmUserController {
	
	@RequestMapping("/viewUserLogin.do")
	public String viewUserLogin() {
		return "/cmm/user/userLogin.df";
	}
	
	@RequestMapping("/userLogin.do")
	@ResponseBody
	public Map<String, Object> userLogin(CmmUserVO bin){
		// TODO Login 인증 처리
		System.out.println("### user login");
		bin.setUserName("테스터");
		bin.setId(99);
		
		CmmUserInfoManagemenet.setUserInfo(bin);
		
		// 성공 메시지
		Map<String,Object> map = new HashMap<>();
		map.put("flag", "success");
		return map;
	}
}

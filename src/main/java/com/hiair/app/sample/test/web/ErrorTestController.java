package com.hiair.app.sample.test.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hiair.app.cmm.user.vo.CmmUserVO;
import com.hiair.app.sample.test.service.ErrorTestService;
import com.hiair.cmm.util.CmmUserInfoManagemenet;

@Controller
public class ErrorTestController {
	//TEST CLASS
	public static class TestVO {
		public String name = "박정하";
		public int age = 27;
	}
	
	@Autowired
	private ErrorTestService service;
	

	@RequestMapping(value="/errorTest.do")
	public void errorTest(HttpServletRequest req, String testParam) throws Exception {
		
		testParam = "testParam";
		service.errorMethod(testParam);
	}
	
	@RequestMapping(value="/errorTest2.do")
	public void errorTest2(HttpServletRequest req, String testParam) throws Exception {
		//로그인 했다 치고.. 
		CmmUserVO userVO = new CmmUserVO();
		userVO.setUserId("jungha");
		userVO.setPassword("1234");
		CmmUserInfoManagemenet.setUserInfo(userVO);
		
		TestVO vo = new TestVO();
		service.errorMethod2(vo);
	}
}

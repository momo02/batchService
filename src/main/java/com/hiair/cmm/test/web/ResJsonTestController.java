package com.hiair.cmm.test.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hiair.cmm.test.vo.UserVO;
import com.hiair.cmm.util.CmmJsonUtils;
/**
 * 
 * @RestController 어노테이션은@Controller를 상속받아 @Controller + @ResponseBody와 같은 의미로써 
 * Restful웹서비스를 구현할 때  응답은 항상 응답바디(response body)에 보내져야 하는데 이를위해 스프링4.0에서 특별히 @ResrController를 제공한다. 
 * 도메인객체를 Web Service로 노출 가능하며 각각의 @RequestMapping method에 @ResponseBody할 필요가 없어진다. 
 * 그러므로 Spring MVC에서 @ReponseBody를 이용하여  JSON or XML 포맷으로 데이터를 넘길 수 있다.
 */
@RestController
@RequestMapping(value="/test")
public class ResJsonTestController {
	
	@RequestMapping(value = "/json.do", method = RequestMethod.GET)
	//@ResponseBody //이 애노테이션이 붙은 파라미터에는 HTTP 요청의 본문body 부분이 그대로 전달된다.
	public List<UserVO> jsonResTest(HttpServletResponse res) throws JsonProcessingException {
		
		List<UserVO> userList = new ArrayList<UserVO>();
		
		UserVO user1 = new UserVO();
		user1.setId("test111");
		user1.setEmail("aaa@naver.com");
		user1.setName("김개똥이");
		user1.setPassword("1111");
		user1.setRole("사용자");
		userList.add(user1);
		
		UserVO user2 = new UserVO();
		user2.setId("test222");
		user2.setEmail("bbb@naver.com");
		user2.setName("김소똥이");
		user2.setPassword("2222");
		user2.setRole("관리자");
		userList.add(user2);
		
		CmmJsonUtils.println(userList);
		
		return userList;
	}
	
	@RequestMapping(value = "/json2.do", method = RequestMethod.GET)
	//@ResponseBody
	public UserVO jsonResTest2(HttpServletResponse res) {
		
		UserVO user1 = new UserVO();
		user1.setId("test111");
		user1.setEmail("aaa@naver.com");
		user1.setName("김개똥이");
		user1.setPassword("1111");
		user1.setRole("사용자");
		
		return user1;
	}
	

	@RequestMapping(value = "/json3.do", method = RequestMethod.POST)
	//@ResponseBody
	public Map<String,Object> jsonResTest3(HttpServletResponse res) {
		
		Map<String,Object> rs = new HashMap<String,Object>();
		rs.put("result", "success");
		return rs;
	}
	
}

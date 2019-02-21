package com.hiair.cmm.test.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hiair.cmm.test.vo.FormVO;


@Controller
@RequestMapping(value="/test")
public class ReqJsonTestController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReqJsonTestController.class); 

	@RequestMapping(value = "/sendReqJsonView.do", method = RequestMethod.GET)
	public String view(HttpServletResponse res) {
		return "test/sendReqJsonView";
	}
	
	/**
	 * 클라이언트로부터 전달받은 Json형식의 Request를 Java Object에 mapping.
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/sendReqJson.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> reqJsonTest(@RequestBody FormVO vo) {
		
		logger.debug("##### FormVO : " + vo.toString());
		logger.debug("##### vo.getTestTags() : " + vo.getTestTags());
		logger.debug("##### vo.getTestTags().get(0) : " + vo.getTestTags().get(0));
		
		Map<String,Object> rs = new HashMap<String,Object>();
		rs.put("result", "success");
		rs.put("data", vo);
		
		return rs;
	}
	
}


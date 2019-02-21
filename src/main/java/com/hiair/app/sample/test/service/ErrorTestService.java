package com.hiair.app.sample.test.service;

import com.hiair.app.sample.test.web.ErrorTestController.TestVO;


public interface ErrorTestService {
	public void errorMethod(String testParam) throws Exception;
	public void errorMethod2(TestVO vo) throws Exception;
}

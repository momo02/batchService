package com.hiair.app.sample.test.service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.hiair.app.sample.test.web.ErrorTestController.TestVO;

@Service
public class ErrorTestServiceImpl implements ErrorTestService{
	
	public void errorMethod(String testParam) throws Exception {
		if(true) {
			throw new SQLException("에러를 던집니당... ");
		}
	}
	
	public void errorMethod2(TestVO vo) throws Exception {
		if(true) {
			throw new IndexOutOfBoundsException("에러를 던집니당... ");
		}
	}
}

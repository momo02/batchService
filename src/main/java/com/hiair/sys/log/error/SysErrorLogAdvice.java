package com.hiair.sys.log.error;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hiair.cmm.util.CmmHttpRequestHelper;
import com.hiair.cmm.util.CmmJsonUtils;
import com.hiair.cmm.util.CmmUserInfoManagemenet;
import com.hiair.sys.log.error.service.SysErrorLogService;
import com.hiair.sys.log.error.vo.SysErrorLogVO;

@Service
@Aspect
public class SysErrorLogAdvice {
	
	private static final Logger logger = LoggerFactory.getLogger(SysErrorLogAdvice.class);
	private static final int MAX_ERROR_STACK_SIZE = 2000;
	private static final String SYSTEM_CODE = "BATCH"; 
	
	@Autowired
	private SysErrorLogService sysErrorLogService; 
	
	@Pointcut("execution(* com.hiair..service.*Service.*(..)) && !@annotation(com.hiair.sys.annotation.NoLogging)")
	public void pointcut() {}
	
	/**
	 * 
	 * @param jp
	 * @param exceptObj : 호출 메소드에서 발생한 예외객체
	 * @throws JsonProcessingException 
	 */
	@AfterThrowing(pointcut="pointcut()", throwing="exceptObj")
	public void exceptionLog(JoinPoint jp, Exception exceptObj){
		try {
			//cf) jp.getSignature() : 호출한 메소드의 시그니처(return타입, 이름, 매개변수 등) 정보가 저장된 Signature 객체 리턴
			//	  jp.getTarget() : 호출한 비즈니스 메소드를 포함하는 비즈니스 객체 리턴 
			//	  jp.getArgs() : 메소드 호출 시 넘겨준 인자 목록을 Object 배열로 리턴 
			Signature signature = jp.getSignature();
			Class<?> targetClass = jp.getTarget().getClass();
			String errorClassFullNm = targetClass.getName();
			String errorClassNm = targetClass.getSimpleName();
			String errorMethodNm = signature.getName();
			Object[] reqParam = jp.getArgs();
			String stackTrace = makeStackTrace(exceptObj);
			String userId = null;
			if(null != CmmUserInfoManagemenet.getUserInfo()) {
				userId = CmmUserInfoManagemenet.getUserInfo().getUserId();
			}
			
			logger.debug("##### 에러 발생 메소드 Signature : " + signature);
			logger.debug("##### 에러 발생 Class명(패키지명 포함) : " + errorClassFullNm);
			logger.debug("##### 에러 발생 Class명(패키지명 포함X) : " + errorClassNm);
			logger.debug("##### 에러 발생 Method명 : " + errorMethodNm);
			logger.debug("##### 요청 URI : " + CmmHttpRequestHelper.getRequestURI());
			logger.debug("##### 요청 Param (JSON): " + CmmJsonUtils.println(reqParam));
			logger.debug("##### Exception : " + exceptObj.toString());
			logger.debug("##### Exception Msg : " + exceptObj.getMessage());
			logger.debug("##### Exception StackTrace : " + stackTrace);
			logger.debug("##### UserId : " + userId);
			
			SysErrorLogVO errorLogVO = new SysErrorLogVO();
			errorLogVO.setSystemCode(SYSTEM_CODE);
			errorLogVO.setProcessServerIp(CmmHttpRequestHelper.getRequestIp());
			errorLogVO.setJavaName(errorClassNm);
			errorLogVO.setMethodName(errorMethodNm);
			errorLogVO.setUserId(userId);
			errorLogVO.setProcessUrl(CmmHttpRequestHelper.getRequestURI());
			errorLogVO.setErrorSimpleContent(exceptObj.toString());
			errorLogVO.setErrorDetailContent(stackTrace);
			errorLogVO.setRequestContent(CmmJsonUtils.print(reqParam));
			//System.out.println(errorLogVO.toString());
			sysErrorLogService.insert(errorLogVO);
			
		}catch(Exception e) {
			logger.error("ERROR LOG INSERT FAIL");
			e.printStackTrace();
		}
	}
	
	public String makeStackTrace(Throwable t) {
		if (t == null)
			return "";
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			t.printStackTrace(new PrintStream(bout));
			bout.flush();
			return new String(bout.toByteArray()).substring(0, MAX_ERROR_STACK_SIZE);
		} catch (Exception ex) {
			return "";
		}
	}	
}
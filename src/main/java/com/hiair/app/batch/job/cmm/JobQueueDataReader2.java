package com.hiair.app.batch.job.cmm;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.hiair.app.queue.data.model.JobQueueData;
import com.hiair.app.queue.data.service.JobQueueDataService;
import com.hiair.cmm.util.CmmJsonUtils;


public class JobQueueDataReader2 extends MyBatisPagingItemReader<JobQueueData> {
	
	private static final Logger logger = LoggerFactory.getLogger(JobQueueDataReader2.class);
	
	//private static Queue<JobQueueData> tempQueue = new LinkedList<JobQueueData>();
	
//	private static Queue<JobQueueData> tempQueue = new LinkedList<JobQueueData>();
	
//	private static Object lock = new Object();
	
	@Autowired
	private JobQueueDataService service;
	@Autowired	
	private SqlSessionFactory sqlSessionFactory;
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	private Object lock = new Object();
	
	private PlatformTransactionManager transactionManger;
	
	
	public JobQueueDataReader2() {
		// TODOJ Auto-generated constructor stub
		logger.debug("=========== new Reader 인스턴스 생성 ================");
	}
                                                             
	@Override
	protected void doReadPage() {
		// TODOJ Auto-generated method stub
		super.doReadPage();
		logger.debug("2222222222222222222222222222222222222222222222222222222");
		logger.debug("10개 읽은 데이터>>>>>>" + CmmJsonUtils.println(results) );
		logger.debug("2222222222222222222222222222222222222222222222222222222");
		
		try {
			
			for(JobQueueData result : results) {
//				JobQueueData result11 = new JobQueueData();                
//				result11.setQueueDataNo(result.getQueueDataNo());          
//				result11.setProcessCode("PROCESSING");                     
//				service.update(result11);                                  
				
				
				DefaultTransactionDefinition txDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
				TransactionStatus txStatus = transactionManger.getTransaction(txDefinition);
				
				JobQueueData result11 = new JobQueueData();             
				result11.setQueueDataNo(result.getQueueDataNo());       
				result11.setProcessCode("PROCESSING");                  
				service.update(result11);                               
				
//				
//				transactionManger.createNativeQuery(UPDATE_QREFUND).setParameter(1, prcsCode)
//				.setParameter(2, message).setParameter(3, m.get("QREFUND_SN")).executeUpdate();
//				
//				
//				 sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);          
//				 String queryId_updatePrcsCd = "com.hiair.app.queue.data.service.JobQueueDataMapper.update";  
//				                                                                                              
//				 Map<String, Object> parameters = new HashMap<String, Object>();                              
//				 parameters.put("processCode", "PROCESSING");                                                 
//				 parameters.put("queueDataNo", result.getQueueDataNo());                                      
//				 sqlSessionTemplate.update(queryId_updatePrcsCd, parameters);
				 
				 transactionManger.commit(txStatus);
				 
			}
			
		}catch (Exception e) {
			e.printStackTrace();// TODOJ: handle exception
		}
		
	}
	
	
//	@Override
//	protected JobQueueData doRead() throws Exception {
//		// TODOJ Auto-generated method stub
////		try {
////			
////		synchronized (lock) {
////			
////			JobQueueData result = super.doRead();
////			logger.debug("11111111111111111111111111111111111");
////			logger.debug(">>>>> " +  result);
////			logger.debug("11111111111111111111111111111111111");
////			
////			
//////			JobQueueData result11 = new JobQueueData();
//////			result11.setQueueDataNo(result.getQueueDataNo());
//////			result11.setProcessCode("PROCESSING");
//////			service.update(result11);
////			
////		    sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
//////		    String query_updatePrcsCd = " UPDATE QUEUE_DATA \r\n" + 
//////								    	" SET    \r\n" + 
//////								    	" RECENT_PROCESS_CODE = #{processCode} \r\n" + 
//////								    	" WHERE  QUEUE_DATA_NO = #{queueDataNo}; ";
//////		    
////		    String queryId_updatePrcsCd = "com.hiair.app.queue.data.service.JobQueueDataMapper.update";
////		    
////		    Map<String, Object> parameters = new HashMap<String, Object>();
////		    parameters.put("processCode", "PROCESSING");
////		    parameters.put("queueDataNo", result.getQueueDataNo());
////		    sqlSessionTemplate.update(queryId_updatePrcsCd, parameters);
////			
////			return result;
////		}
////		
////		}catch (Exception e) {
////			e.printStackTrace();
////			// TODOJ: handle exception
////		}
////		return null;
//			return super.doRead();
//	}
	
//	@PostConstruct
//	public void initialize() {
//		logger.info("================ 새로운 잡인스턴스 생성(새로운 스텝 생성)때마다 호출??"); //아니면 딱 한번 호출되는지...
//		logger.info("================ init JobQueueDataReader ================");
//		
////		logger.info("scanning file directory: {}.", inputDirectoryLocation);
////
////		File inputDirectory = new File(inputDirectoryLocation);
////		if (!inputDirectory.exists()) {
////			logger.warn("Input directory {} does not exist, creating it.", inputDirectoryLocation);
////			inputDirectory.mkdirs();
////		}
////
////		Arrays.stream(inputDirectory.listFiles()).forEach(file -> attemptQueue.add(new Attempt(file)));
////
////		logger.info("{} attempts queued.", attemptQueue.size());
//		
//		
//	}
//	
////	////생성자
////	public JobQueueDataReader(JobQueueDataService service) {
////		super();
////		this.service = service;
////		
////		
////		
////	}
//	
	
//	///// 모든 조회 대상 데이터를 Queue<JobQueueData>에다가 저장해둔다. 
//	@Override
//	public synchronized JobQueueData read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//		// TODOJ Auto-generated method stub
//		
//		//static 큐 변수에서 한개씩 꺼내서
//		//큐 데이터 상태를 PROCESSING으로 바꾼다, 
//		
//		synchronized( lock )
//		{	
//			logger.debug("");	
//		   ///statement;      // theObject 가 동기화된다.
//
//		}
//		
//		
//		return null;
//	}

	

}

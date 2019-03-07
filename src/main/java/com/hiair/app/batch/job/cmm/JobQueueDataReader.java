package com.hiair.app.batch.job.cmm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;

import com.hiair.app.queue.data.model.JobQueueData;
import com.hiair.app.queue.data.service.JobQueueDataService;


//@Component
public class JobQueueDataReader implements ItemReader<JobQueueData> {
	
	private static final Logger logger = LoggerFactory.getLogger(JobQueueDataReader.class);
	
	private static Queue<JobQueueData> tempQueue = new LinkedList<JobQueueData>();
	
//	@Autowired
//	private JobQueueDataService service;
	
	@Autowired	
	private SqlSessionFactory sqlSessionFactory;
	
	private SqlSessionTemplate sqlSessionTemplate;
	
	//새로운 Job instance가 생성될때마다 호출
	@PostConstruct
	public void initialize() {
		
		logger.debug(":::::::::::::::::::: init JobQueueDataReader ::::::::::::::::::::");
		logger.debug(":::::::::::::::::::: read job queue data ::::::::::::::::::::");
		/////////////////// 모든 처리 대상 데이터를 Queue<JobQueueData>에 저장해둔다. ///////////////////
		
//		JobQueueData param = new JobQueueData();
//		param.setJobGroup("TESTGR");
//		param.setJobName("testJob");
//		service.list(param);
		
		// MyBatisBatchItemWriter 를 사용하면 내부적으로 SqlSessionTemplate이 생성될 때 ExecutorType이 BATCH로 지정됨
		// Reader 내에서 service를 통해 select 쿼리를 날리고자한다면, 기본적으로 ExecutorType이 SIMPLE이기 때문에 문제 (Mybatis 기본 설정에서는 default ExecutorType이 SIMPLE)
		// => org.springframework.dao.TransientDataAccessResourceException: Cannot change the ExecutorType when there is an existing transaction
		// 동일한 Transaction내에서 ExecutorType이 변경될 수 없다는 에러 발생됨 
		// 따라서 select시에도, 동일한 ExecutorType(ExecutorType.BATCH)을 가진 SqlSessionTemplate을 사용하도록 설정!
		
		sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);         
		String queryId = "com.hiair.app.queue.data.service.JobQueueDataMapper.list"; 
		                                                                                            
		Map<String, Object> parameters = new HashMap<String, Object>();                             
		parameters.put("jobGroup", "TESTGR");                                                
		parameters.put("jobName", "testJob");           
		
		List<JobQueueData> dataList = sqlSessionTemplate.selectList(queryId, parameters);
		
		for(JobQueueData  data : dataList ) {           
			tempQueue.add(data);                        
		}                                               
		logger.debug(">>>>> 처리할 JobQueueData size : " + tempQueue.size());
	}
	
	@Override
	public synchronized JobQueueData read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		JobQueueData queueData = null;
		
		logger.debug("Temp Queue size {}.", tempQueue.size());
		if (tempQueue.size() > 0) {
			queueData = tempQueue.remove(); //저장해둔 큐 데이터를 하나씩 꺼낸다.
			logger.debug(">>>>> queueData to read : " + queueData.toString());
		}
		return queueData;
	}
	
}

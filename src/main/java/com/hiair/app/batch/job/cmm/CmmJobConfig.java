package com.hiair.app.batch.job.cmm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


@Configuration
public class CmmJobConfig {
	  
	  private static final Logger logger = LoggerFactory.getLogger(CmmJobConfig.class);
	  private static final String READ_QUERY = "com.hiair.app.queue.data.service.JobQueueDataMapper.list";
	  private static final String WRITE_QUERY = "com.hiair.app.queue.data.service.JobQueueDataMapper.update";
	  private static final String WRITE_QUERY2 = "com.hiair.app.queue.history.service.JobQueueHistoryMapper.insert";
	  
	  @Autowired
	  private SqlSessionFactory sqlSessionFactory;
	  
	  @Bean
	  @StepScope
	  public TaskExecutor taskExecutor() {
		  ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		  taskExecutor.setCorePoolSize(1);
		  taskExecutor.setMaxPoolSize(10);
		  taskExecutor.afterPropertiesSet();
		  return taskExecutor;
	  }
	  
//	  @Bean
//	  @StepScope
//	  public Map<String, Object> jobParamMap(@Value("#{jobParameters['jobGroup']}") String jobGroup
//			  								 ,@Value("#{jobParameters['jobName']}") String jobName) {
//		  Map<String, Object> map = new HashMap<>();
//		  map.put("jobGroup", jobGroup);
//		  map.put("jobName", jobName);
//		  return map;
//	  }
	  
	  
	  @Bean(name="queueReader")
	  @StepScope //Step의 실행시점에 컴포넌트를 spring baen으로 생성. 
	  // 즉 singleton이 아니라, 각각의 step에서 별도의 ItemReader가 생성된다.
	  // (job에서 해당 reader를 사용하지 않을 경우에도 무조건 생성)
	  public ItemReader<?> reader(/*@Value("#{jobParameters['readQuery']}") String query*/
			  	  @Value("#{jobParameters['jobGroup']}") String jobGroup
				 ,@Value("#{jobParameters['jobName']}") String jobName
				 ,@Value("#{jobParameters['retryCount']}") String retryCount
			  ) {
		  MyBatisPagingItemReader<?> itemReader = new MyBatisPagingItemReader<Object>();
		  itemReader.setSqlSessionFactory((SqlSessionFactory) sqlSessionFactory);
		  itemReader.setQueryId(READ_QUERY);
		  itemReader.setPageSize(10);
		  
		  Map<String, Object> map = new HashMap<>();
		  map.put("jobGroup", jobGroup);
		  map.put("jobName", jobName);
		  map.put("retryCount", retryCount);
		  
		  logger.debug(">>>>> jobGroup : " + jobGroup);
		  logger.debug(">>>>> jobName : " + jobName);
		  logger.debug(">>>>> retryCount : " + retryCount);
		  
		  itemReader.setParameterValues(map);
		  
		  return itemReader;
	  }
	  
//	  @Bean(name="queueWriter")
//	  @StepScope
//	  public ItemWriter<?> writer(/*@Value("#{jobParameters['jobName']}") String jobName,*//*확인용*/
//			  					  /*@Value("#{jobParameters['runDate']}") Date runDate,*//*확인용*/	
//			  					  /*@Value("#{jobParameters['writeQuery']}") String query*/) {
//		  MyBatisBatchItemWriter<?> itemWriter = new MyBatisBatchItemWriter<Object>();
//		  itemWriter.setSqlSessionFactory((SqlSessionFactory) sqlSessionFactory);
//		  
//		  logger.debug("=====================================================");
//		  logger.debug("write!!!!");
//		  //logger.debug("jobName    >>>>>> " + jobName);
//		  //logger.debug("runDate    >>>>>> " + runDate);
//		  //logger.debug("writeQuery >>>>>> " + query);
//		  logger.debug("=====================================================");
//	
//		  itemWriter.setStatementId(WRITE_QUERY);
//		  return itemWriter;
//	  }
//	  
//	  
//	  @Bean(name="queueHistoryWriter")
//	  @StepScope
//	  public ItemWriter<?> writer2(/*@Value("#{jobParameters['jobName']}") String jobName,*//*확인용*/
//			  					  /*@Value("#{jobParameters['runDate']}") Date runDate,*//*확인용*/	
//			  					  /*@Value("#{jobParameters['writeQuery']}") String query*/) {
//		  MyBatisBatchItemWriter<?> itemWriter = new MyBatisBatchItemWriter<Object>();
//		  itemWriter.setSqlSessionFactory((SqlSessionFactory) sqlSessionFactory);
//		  
//		  logger.debug("=====================================================");
//		  logger.debug("write!!!!");
//		  //logger.debug("jobName    >>>>>> " + jobName);
//		  //logger.debug("runDate    >>>>>> " + runDate);
//		  //logger.debug("writeQuery >>>>>> " + query);
//		  logger.debug("=====================================================");
//	
//		  itemWriter.setStatementId(WRITE_QUERY2);
//		  return itemWriter;
//	  }
	  
	  @Bean
	  @StepScope
	  public ItemWriter<?> writer2() {
		  
		  CompositeItemWriter<Object> cWriter = new CompositeItemWriter<>();   
		  
		  MyBatisBatchItemWriter<Object> itemWriter1 = new MyBatisBatchItemWriter<Object>();
		  itemWriter1.setSqlSessionFactory((SqlSessionFactory) sqlSessionFactory);
		  itemWriter1.setStatementId(WRITE_QUERY);
		  itemWriter1.setAssertUpdates(false); //This will prevent Spring Batch from verifying that only one update was made per item.
		  
		  MyBatisBatchItemWriter<Object> itemWriter2 = new MyBatisBatchItemWriter<Object>();
		  itemWriter2.setSqlSessionFactory((SqlSessionFactory) sqlSessionFactory);
		  itemWriter2.setStatementId(WRITE_QUERY2);
		  itemWriter1.setAssertUpdates(false);
		  
		  List<ItemWriter<? super Object>> mWriter = new ArrayList<ItemWriter<? super Object>>();
		  mWriter.add((ItemWriter<? super Object>) itemWriter1); // **Comment this line and the code works fine**
		  mWriter.add((ItemWriter<? super Object>) itemWriter2);
		  
		  cWriter.setDelegates(mWriter);
		  
		  return cWriter;
	  }
	  
	  
	  @Bean
	  @StepScope
	  public ItemProcessor<?,?> processor(){
		  return new CmmJobProcessor();
	  }
}

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
	  
	  private static final String SELECT_QUEUE_DATA = "com.hiair.app.queue.data.service.JobQueueDataMapper.listForReader";
	  private static final String UPDATE_QUEUE_DATA = "com.hiair.app.queue.data.service.JobQueueDataMapper.updateForWriter";
	  private static final String INSERT_QUEUE_HISTORY = "com.hiair.app.queue.history.service.JobQueueHistoryMapper.insert";
	  
	  @Autowired
	  private SqlSessionFactory sqlSessionFactory;
	  
	  @Bean
	  @StepScope
	  public TaskExecutor taskExecutor(@Value("#{jobParameters['threadCount']}") int threadCount) {
		  
		  System.err.println(">>>>>> threadCount : " + threadCount); 
		  ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		  taskExecutor.setCorePoolSize(threadCount);
		  taskExecutor.setMaxPoolSize(Integer.MAX_VALUE); //TODOJ Max Thread Pool Size는 몇으로 할지..
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
	  
	  @Bean
	  @StepScope //Step의 실행시점에 컴포넌트를 spring baen으로 생성. 
	  // 즉 singleton이 아니라, 각각의 step에서 별도의 ItemReader가 생성된다.
	  // (job에서 해당 reader를 사용하지 않을 경우에도 무조건 생성)
	  public ItemReader<?> reader(/*@Value("#{jobParameters['readQuery']}") String query*/
			  	  @Value("#{jobParameters['jobGroup']}") String jobGroup
				 ,@Value("#{jobParameters['jobName']}") String jobName
				 ,@Value("#{jobParameters['retryCount']}") String retryCount
			  ) {
//		  MyBatisPagingItemReader<?> itemReader = new MyBatisPagingItemReader<Object>();
		  
		  MyBatisPagingItemReader<?> itemReader = new JobQueueDataReader2();
		  
		  itemReader.setSqlSessionFactory((SqlSessionFactory) sqlSessionFactory);
		  itemReader.setQueryId(SELECT_QUEUE_DATA);
		  itemReader.setPageSize(10); // 한번에 조회할 Item의 양, Page 단위로 끊어서 조회
		  //cf.PageSize가 10이고, ChunkSize가 50이라면 ItemReader에서 Page 조회가 5번 일어나면 1번 의 트랜잭션이 발생하여 Chunk가 처리
		  //한번의 트랜잭션 처리를 위해 5번의 쿼리 조회가 발생하기 때문에 성능상 이슈가 발생할 수 있음.
		  //Setting a fairly large page size and using a commit interval that matches the page size should provide better performance. 
		  //(상당히 큰 페이지 크기를 설정하고 페이지 크기와 일치하는 커미트 간격을 사용하면 성능이 향상됨)
		  
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
	  
	  @Bean
	  @StepScope
	  public ItemWriter<?> writer2() {
		  
		  CompositeItemWriter<Object> cWriter = new CompositeItemWriter<>();   
		  
		  MyBatisBatchItemWriter<Object> itemWriter1 = new MyBatisBatchItemWriter<Object>();
		  itemWriter1.setSqlSessionFactory((SqlSessionFactory) sqlSessionFactory);
		  itemWriter1.setStatementId(UPDATE_QUEUE_DATA);
		  itemWriter1.setAssertUpdates(false); //This will prevent Spring Batch from verifying that only one update was made per item.
		  
		  MyBatisBatchItemWriter<Object> itemWriter2 = new MyBatisBatchItemWriter<Object>();
		  itemWriter2.setSqlSessionFactory((SqlSessionFactory) sqlSessionFactory);
		  itemWriter2.setStatementId(INSERT_QUEUE_HISTORY);
		  itemWriter1.setAssertUpdates(false);
		  
		  List<ItemWriter<? super Object>> mWriter = new ArrayList<ItemWriter<? super Object>>();
		  mWriter.add((ItemWriter<? super Object>) itemWriter1); 
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

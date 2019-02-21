package com.hiair.app.batch;

import java.util.Date;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class BatchConfig {
	  
	  private static final Logger logger = LoggerFactory.getLogger(BatchConfig.class);
	  @Autowired
	  private SqlSessionFactory sqlSessionFactory;
	  
	  @Bean
	  public TaskExecutor taskExecutor() {
		  ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		  taskExecutor.setCorePoolSize(10);
		  taskExecutor.setMaxPoolSize(10);
		  taskExecutor.afterPropertiesSet();
		  return taskExecutor;
	  }
	  
	  @Bean
	  @StepScope
	  public ItemReader<?> reader(@Value("#{jobParameters['readQuery']}") String query) {
		  MyBatisPagingItemReader<?> itemReader = new MyBatisPagingItemReader<Object>();
		  itemReader.setSqlSessionFactory((SqlSessionFactory) sqlSessionFactory);
		  itemReader.setQueryId(query);
		  itemReader.setPageSize(10);
		  return itemReader;
	  }
	  
	  @Bean
	  @StepScope
	  public ItemWriter<?> writer(@Value("#{jobParameters['jobName']}") String jobName,/*확인용*/
			  					  @Value("#{jobParameters['runDate']}") Date runDate,/*확인용*/	
			  					  @Value("#{jobParameters['writeQuery']}") String query) {
		  MyBatisBatchItemWriter<?> itemReader = new MyBatisBatchItemWriter<Object>();
		  itemReader.setSqlSessionFactory((SqlSessionFactory) sqlSessionFactory);
		  
		  logger.debug("=====================================================");
		  logger.debug("jobName    >>>>>> " + jobName);
		  logger.debug("runDate    >>>>>> " + runDate);
		  logger.debug("writeQuery >>>>>> " + query);
		  logger.debug("=====================================================");
		  
		  itemReader.setStatementId(query);
		  return itemReader;
	  }
}

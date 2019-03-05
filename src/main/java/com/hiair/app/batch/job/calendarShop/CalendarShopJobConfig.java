package com.hiair.app.batch.job.calendarShop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import com.hiair.app.batch.job.refundTicket.RefundTicektJobConfig;

@Configuration
@EnableBatchProcessing
public class CalendarShopJobConfig {
	
		private static final Logger logger = LoggerFactory.getLogger(CalendarShopJobConfig.class);
	
		private static final String JOB_NM = "CalendarShopJob";
		
		private static final String STEP_NM = "CalendarShopJob_Step";
		private static final String STEP_NM2 = "CalendarShopJob_Step2";
		
		
		
		@Autowired
		private JobBuilderFactory jobBuilderFactory;
		@Autowired
		private StepBuilderFactory stepBuilderFactory;
		@Autowired
		private ItemReader<?> reader;
		@Autowired
		private ItemWriter<?> writer;
		@Autowired
		private ItemProcessor<?, ?> processor;
		@Autowired
		private TaskExecutor taskExecutor;
		
//		@Bean
//		public AttemptReader processAttemptReader() {
//			return new AttemptReader();
//		}
//
//		@Bean
//		public AttemptProcessor processAttemptProcessor() {
//			return new AttemptProcessor();
//		}
//
//		@Bean
//		public AttemptWriter processAttemptWriter() {
//			return new AttemptWriter();
//		}
		
		@Bean(name = JOB_NM)
		public Job sampleJob1() {
			return jobBuilderFactory.get(JOB_NM)
//					.start(step_main2())
//					.next(step_main())
					.start(step_main())
					.build();
		}
	
		// 작업 큐에서 데이터 read & write (QUEUE_DATA)
		@Bean(name = STEP_NM)
		public Step step_main() {
			return stepBuilderFactory.get(STEP_NM)
					.chunk(10)
					.reader(reader)
					.processor((ItemProcessor<? super Object, ? extends Object>) processor)
					.writer((ItemWriter<? super Object>) writer)
					.taskExecutor(taskExecutor)
					.throttleLimit(5)  //maximium number of concurrent tasklet executions allowed
					.build();
		}
		
//		@Bean(name = STEP_NM2)
//		public Step step_main2() {
//			return stepBuilderFactory.get(STEP_NM2)
//					.chunk(1)
//					.reader(reader)
//					.processor((ItemProcessor<? super Object, ? extends Object>) processor)
//					.writer((ItemWriter<? super Object>) writer)
//					.taskExecutor(taskExecutor)
//					.throttleLimit(1)  //maximium number of concurrent tasklet executions allowed
//					.build();
//		}
	  
}

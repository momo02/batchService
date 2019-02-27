package com.hiair.app.batch.job.refundTicket;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
public class RefundTicektJobConfig {
	  
	private static final Logger logger = LoggerFactory.getLogger(RefundTicektJobConfig.class);
	
	  private static final String JOB_NM = "RefundTicektJob";
	  
	  private static final String STEP_NM = "RefundTicektJob_Step";
	  private static final String STEP_NM2 = "RefundTicektJob_Step2";
	  private static final String STEP_NM3 = "RefundTicektJob_Step3";
	  private static final String STEP_NM4 = "RefundTicektJob_Step4";
	  
	  @Autowired
	  private JobBuilderFactory jobBuilderFactory;
	  @Autowired
	  private StepBuilderFactory stepBuilderFactory;
	  @Autowired
	  private ItemReader<?> reader;
//	  
//	  @Resource(name = "queueWriter")
//	  private ItemWriter<?> writer;
//	  @Resource(name = "queueHistoryWriter")
//	  private ItemWriter<?> writer2;
	  
	  @Autowired
	  private ItemWriter<?> writer;
	  
	  @Autowired
	  private ItemProcessor<?,?> processor;
	  @Autowired
	  private TaskExecutor taskExecutor;
	  
	  
	  @Bean(name=JOB_NM)
	  public Job sampleJob1() {
		  return jobBuilderFactory.get(JOB_NM)
	                .start(step_main())
	                .build();
//		              	.on("COMPLETED")
////		                .to(writeQueueHistory())
////		                .next(afterSuccess())
//		              	.to(afterSuccess())
//		               
//		               // .on("*") // afterSuccess()의 결과 관계 없이 
//		               // .end() // Job 종료
//		            .from(step_main())
//		              .on("FAILED")
////		                .to(writeQueueHistory())
////		                .next(afterFail())
//		              	.to(afterFail())
//		                
//		              //  .on("*") // afterFail()의 결과 관계 없이 
//		              //  .end() // Job 종료
////		            .from(step_main())
////		              .on("*") //FAILED 외에 모든 경우
////		              .to(writeQueueHistory())
//		              //.end()
//		            .end()
//		            .build();
		                
	  }
	  
	  //작업 큐에서 데이터 read & write (QUEUE_DATA)
	  @Bean(name=STEP_NM)
	  public Step step_main() {
	      return stepBuilderFactory.get(STEP_NM)
	              .chunk(100)
	              .reader(reader)
	    		  .processor((ItemProcessor<? super Object, ? extends Object>) processor)
	    		  .writer((ItemWriter<? super Object>) writer)
	              .taskExecutor(taskExecutor)
	              .throttleLimit(5) //maximium number of concurrent tasklet executions allowed
	              .build();
	  }
	  
	  
//	  //작업 큐 이력 write (QUEUE_HISTORY)
//	  @Bean(name=STEP_NM2) //test..
//	  public Step writeQueueHistory() {
//	      return stepBuilderFactory.get(STEP_NM2)
//	    		  .chunk(100)
//	              //.reader(reader)
//	    		  //.processor((ItemProcessor<? super Object, ? extends Object>) processor)
//	    		  .writer((ItemWriter<? super Object>) writer2)
//	              .taskExecutor(taskExecutor)
//	              //.throttleLimit(5) //maximium number of concurrent tasklet executions allowed
//	              .build();
//	  }
//	  
	  
	  @Bean(name=STEP_NM3)
	  public Step afterSuccess() {
	      return stepBuilderFactory.get(STEP_NM3)
	    		  .tasklet((contribution, chunkContext) -> {
	            	  //TEST
	        		  // Step은 batch job생성 후 1번만 실행되는지 
	        		  // reader & writer는 step 수행마다 새로 생성되는지 
//	            	  System.out.println(">>>>> This is Step2");
//	            	  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
//	        		  System.out.println("step >>>>>>>>>>>>>>>>>>>>> " + STEP_NM );
//	        		  System.out.println("reader >>>>>>>>>>>>>>>>>>>>> " + reader ); 
////	        		  System.out.println("writer >>>>>>>>>>>>>>>>>>>>> " + writer ); 
//	        		  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	                  
	    			  System.err.println("================= 성공 이후 로직 =================");
	    			  contribution.setExitStatus(ExitStatus.COMPLETED);
	    			  
	                  return RepeatStatus.FINISHED;
	              })
	              .build();
	  }
	  
	  
	  @Bean(name=STEP_NM4)
	  public Step afterFail() {
		  return stepBuilderFactory.get(STEP_NM4)
	    		  .tasklet((contribution, chunkContext) -> {
	    			  System.err.println("================= 실패 이후 로직 =================");
	                  return RepeatStatus.FINISHED;
	              })
	              .build();
	  }


}

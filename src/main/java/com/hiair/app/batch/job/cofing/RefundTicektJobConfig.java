package com.hiair.app.batch.job.cofing;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
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
	  
	  private static final String JOB_NM = "RefundTicektJob";
	  private static final String STEP_NM = "RefundTicektJob_Step2";
	  @Autowired
	  private JobBuilderFactory jobBuilderFactory;
	  @Autowired
	  private StepBuilderFactory stepBuilderFactory;
	  @Autowired
	  private ItemReader<?> reader;
	  @Autowired
	  private ItemWriter<?> writer;
	  @Autowired
	  private TaskExecutor taskExecutor;
	  
	  
	  @Bean(name=JOB_NM)
	  public Job sampleJob1() {
		  return jobBuilderFactory.get(JOB_NM)
	                .start(simpleStep1())
	                .build();
	  }
	  
	  @Bean(name=STEP_NM)
	  public Step simpleStep1() {
	      return stepBuilderFactory.get(STEP_NM)
	              //.chunk(100)
	              //.reader(reader)
//	    		  .processor(processor())
	    		  //.writer((ItemWriter<? super Object>) writer)
	              //.taskExecutor(taskExecutor)
	              //.throttleLimit(5) //maximium number of concurrent tasklet executions allowed
//	    		  .tasklet((contribution, chunkContext) -> {
//	                  System.out.println(">>>>> This is Step1");
//	                  return RepeatStatus.FINISHED;
//	              })
	    		  
	    		  .tasklet((contribution, chunkContext) -> {
	            	  //TEST
	        		  // Step은 batch job생성 후 1번만 실행되는지 
	        		  // reader & writer는 step 수행마다 새로 생성되는지 
	            	  System.out.println(">>>>> This is Step2");
	            	  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	        		  System.out.println("step >>>>>>>>>>>>>>>>>>>>> " + STEP_NM );
	        		  System.out.println("reader >>>>>>>>>>>>>>>>>>>>> " + reader ); 
	        		  System.out.println("writer >>>>>>>>>>>>>>>>>>>>> " + writer ); 
	        		  System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	                  
	                  return RepeatStatus.FINISHED;
	              })
	    		  
	              .build();
	  }

//processor 보류..
//	  @Bean
//	  public ItemProcessor<Object,Object> processor(){
//		return new CustomProcessor();
//	  }
}

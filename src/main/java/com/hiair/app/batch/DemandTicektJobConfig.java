package com.hiair.app.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing //배치Job 생성에 필요한 bean들을 자동으로 가져온다.
public class DemandTicektJobConfig {
	  
	  private static final String JOB_NM = "DemandTicektJob";
	  private static final String STEP_NM = "DemandTicektJob_Step1";
	
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
	              .chunk(100)
	              .reader(reader)
//	    		  .processor(processor())
	    		  .writer((ItemWriter<? super Object>) writer)
	              .taskExecutor(taskExecutor)
	              .throttleLimit(5) //maximium number of concurrent tasklet executions allowed
//	    		  .tasklet((contribution, chunkContext) -> {
//	                  System.out.println(">>>>> This is Step1");
//	                  return RepeatStatus.FINISHED;
//	              })
	              .build();
	  }

//processor 보류..
//	  @Bean
//	  public ItemProcessor<Object,Object> processor(){
//		return new CustomProcessor();
//	  }
}

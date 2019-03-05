package com.hiair.app.scheduler;

import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hiair.app.queue.data.model.JobQueueData;
import com.hiair.app.queue.data.service.JobQueueDataService;
import com.hiair.app.queue.group.model.JobQueueGroup;
import com.hiair.app.queue.group.service.JobQueueGroupService;

@Component
@PersistJobDataAfterExecution /* to prevent race-conditions on saved data. (IS_UPDATE_DATA=ture)*/
@DisallowConcurrentExecution /* 작업의 여러 인스턴스가 동시 실행되지 않도록 (IS_NONCONCURRENT=ture)*/
public class QuartzJobLauncher implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(QuartzJobLauncher.class);
	
	static final String JOB_NAME = "jobName";
	
	@Autowired
	private JobLocator jobLocator;
	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private JobQueueGroupService jobQueueGroupService;
	@Autowired
	private JobQueueDataService jobQueueDataService;
	
	@Override
	public void execute(JobExecutionContext context) {
		
		try {

			logger.debug("=============== main job execute ===============");
			logger.debug(">>>>> jobLocator : " + jobLocator);
			logger.debug(">>>>> jobLauncher : " + jobLauncher);

			// 작업 큐 그룹(마스터) 정보 조회
			JobQueueGroup jobQueueGroup = getJobQueueGroupInfo(context.getJobDetail());
			
			if(null == jobQueueGroup) {
				throw new Exception("No jobQueueGroup data"); //TODOJ 에러메시지 추후 다시 정리..
				// 다른 작업은 별개로 실행되어야함.. 
			}else {
				logger.debug(">>>>> jobQueueGroup : " + jobQueueGroup.toString());
			}
			
			String batchJobName = jobQueueGroup.getBatchJobName();

			// queueSaveFlag(큐 정보 저장) 여부가 false일 경우 (특정시간마다 한번씩만 수행되는 작업),
			// 배치 작업 전 1건의 큐 정보를 미리 저장해둔다.
			if ("N".equals(jobQueueGroup.getQueueSaveFlag())) {
				JobQueueData jobQueueData = new JobQueueData();
				jobQueueData.setJobGroup(jobQueueGroup.getJobGroup());
				jobQueueData.setJobName(jobQueueGroup.getJobName());
				jobQueueData.setQueueClass("test"); ////// ??? TODOJ 
				jobQueueData.setProcessCode("WAITING");
				jobQueueDataService.insert(jobQueueData);
			}

			// 작업 큐 그룹 정보를 통해 jobParameter 생성
			JobParameters jobParameters = getJobParamFromJobQueueGroupInfo(jobQueueGroup);
			logger.debug(">>>>> jobParameters : " + jobParameters);

			System.out.println(jobLocator.getJob(batchJobName));

			// 배치 작업 시작
			jobLauncher.run(jobLocator.getJob(batchJobName), jobParameters);

			
		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchJobException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	private JobQueueGroup getJobQueueGroupInfo(JobDetail jobDetail) {
		String jobGroup = jobDetail.getKey().getGroup();
		String jobName = jobDetail.getKey().getName();
		
		JobQueueGroup param = new JobQueueGroup(jobGroup,jobName);
		return jobQueueGroupService.detail(param); // 큐 그룹(마스터) 정보 조회
	}
	
	private JobParameters getJobParamFromJobQueueGroupInfo(JobQueueGroup info){
		
		JobParametersBuilder builder = new JobParametersBuilder();
		
		//need unique job parameter to rerun the same job
		builder.addDate("runDate", new Date());
		
		builder.addString("jobGroup", info.getJobGroup());
		builder.addString("jobName", info.getJobName());
		builder.addString("batchJobName", info.getBatchJobName()); //배치 작업 명
		builder.addLong("retryCount", (long)info.getRetryCount()); //재시도 수  
		builder.addLong("threadCount",(long)(info.getThreadCount())); //스레드 수 
//		builder.addString("queueSaveFlag", String.valueOf(info.getQueueSaveFlag())); //큐 정보 저장 여부
		builder.addString("jobData", String.valueOf(info.getJobData())); //개별 job마다 추가적으로 전달할 job (pram) 데이터
		
		return builder.toJobParameters();
	}

}

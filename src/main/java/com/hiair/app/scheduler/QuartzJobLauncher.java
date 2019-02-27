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
	
	@Override
	public void execute(JobExecutionContext context) {
		
		try {
		
		logger.debug("=============== main job execute ===============");
		logger.debug("jobLocator >>>>>" + jobLocator);
		logger.debug("jobLauncher >>>>>" + jobLauncher);
		
	//quartz job Data로 부터 파라미터를 가져오는 방식. 사용 X
	//	Map<String, Object> jobDataMap = context.getMergedJobDataMap();
	//	String jobName = (String) jobDataMap.get(JOB_NAME);
	//	logger.debug("jobName >>>>>" + jobName);
	//	JobParameters jobParameters = getJobParametersFromJobMap(jobDataMap);
		
	//	System.out.println(jobLocator.getJob(jobName));
	//	System.out.println(jobParameters);
	//	
	//	jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
		
		
		//QUEUE_GROUP 테이블 정보를 통해 jobParameter를 생성
		JobParameters jobParameters = getJobParametersFromQueueGroupInfo(context.getJobDetail());
	    logger.debug(">>>>> jobParameters : " + jobParameters);
		
	    String batchJobName = jobParameters.getString("jobName");
	    System.out.println(jobLocator.getJob(batchJobName));
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

	private JobParameters getJobParametersFromQueueGroupInfo(JobDetail jobDetail){
		
		String jobGroup = jobDetail.getKey().getGroup();
		String jobName = jobDetail.getKey().getName();
		
		JobParametersBuilder builder = new JobParametersBuilder();
		
		JobQueueGroup paramInfo = new JobQueueGroup(jobGroup,jobName);
		JobQueueGroup getInfo = jobQueueGroupService.detail(paramInfo); // 큐 그룹 정보 조회
		
		//need unique job parameter to rerun the same job
		builder.addDate("runDate", new Date());
		
		builder.addString("jobGroup", getInfo.getJobGroup());
		builder.addString("jobName", getInfo.getJobName());
		builder.addString("batchJobName", getInfo.getBatchJobName()); //배치 작업 명
		builder.addString("retryCount", String.valueOf(getInfo.getRetryCount())); //재시도 수  
		builder.addString("threadCount", String.valueOf(getInfo.getThreadCount())); //스레드 수 
		builder.addString("queueSaveFlag", String.valueOf(getInfo.getQueueSaveFlag())); //큐 정보 저장 여부
		builder.addString("jobData", String.valueOf(getInfo.getJobData())); //개별 job마다 추가적으로 전달할 job (pram) 데이터
		
		return builder.toJobParameters();
	}
	
	
	
//	//get params from jobDataAsMap property, job-quartz.xml
//	private JobParameters getJobParametersFromJobMap(Map<String, Object> jobDataMap) {
//
//		JobParametersBuilder builder = new JobParametersBuilder();
//
//		for (Entry<String, Object> entry : jobDataMap.entrySet()) {
//			String key = entry.getKey();
//			Object value = entry.getValue();
//			if (value instanceof String && !key.equals(JOB_NAME)) {
//				builder.addString(key, (String) value);
//			} else if (value instanceof Float || value instanceof Double) {
//				builder.addDouble(key, ((Number) value).doubleValue());
//			} else if (value instanceof Integer || value instanceof Long) {
//				builder.addLong(key, ((Number) value).longValue());
//			} else if (value instanceof Date) {
//				builder.addDate(key, (Date) value);
//			} else {
//				// JobDataMap contains values which are not job parameters
//				// (ignoring)
//			}
//		}
//		//need unique job parameter to rerun the same job
//		builder.addDate("runDate", new Date());
//		
//		//////// TEST ////////
//		builder.addString("readQuery", "com.hiair.app.sample.test.service.SampleQueueMapper.select");
//		
//		String jobName = (String)jobDataMap.get(JOB_NAME); 
//		logger.debug(">>>>>>>>>>>>> Job Name >>>>>>>>>>>>>" + jobName);
//		builder.addString("jobName", jobName);
//		if("DemandTicektJob".equals(jobName)) {
//			builder.addString("writeQuery", "com.hiair.app.sample.test.service.SampleQueueMapper.update1");
//		}else if("RefundTicektJob".equals(jobName)) {
//			builder.addString("writeQuery", "com.hiair.app.sample.test.service.SampleQueueMapper.update2");
//		}
//		return builder.toJobParameters();
//	}
}

package com.hiair.app.scheduler.job;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
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

@Component
@PersistJobDataAfterExecution 
@DisallowConcurrentExecution 
public class MainJob implements Job {
	
	private static final Logger logger = LoggerFactory.getLogger(MainJob.class);
	
	static final String JOB_NAME = "jobName";
	
	@Autowired
	private JobLocator jobLocator;
	@Autowired
	private JobLauncher jobLauncher;
	
	@Override
	public void execute(JobExecutionContext context) {
		
		try {
		
		logger.debug("=============== main job execute ===============");
		logger.debug("jobLocator >>>>>" + jobLocator);
		logger.debug("jobLauncher >>>>>" + jobLauncher);
		
		
		Map<String, Object> jobDataMap = context.getMergedJobDataMap();

		String jobName = (String) jobDataMap.get(JOB_NAME);
		logger.debug("jobName >>>>>" + jobName);
		
		JobParameters jobParameters = getJobParametersFromJobMap(jobDataMap);
		logger.debug("jobParameters >>>>>" + jobParameters);
		
		System.out.println(jobLocator.getJob(jobName));
		System.out.println(jobParameters);
		
		jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
		
		
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

	
	//get params from jobDataAsMap property, job-quartz.xml
	private JobParameters getJobParametersFromJobMap(Map<String, Object> jobDataMap) {

		JobParametersBuilder builder = new JobParametersBuilder();

		for (Entry<String, Object> entry : jobDataMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof String && !key.equals(JOB_NAME)) {
				builder.addString(key, (String) value);
			} else if (value instanceof Float || value instanceof Double) {
				builder.addDouble(key, ((Number) value).doubleValue());
			} else if (value instanceof Integer || value instanceof Long) {
				builder.addLong(key, ((Number) value).longValue());
			} else if (value instanceof Date) {
				builder.addDate(key, (Date) value);
			} else {
				// JobDataMap contains values which are not job parameters
				// (ignoring)
			}
		}
		//need unique job parameter to rerun the same job
		builder.addDate("runDate", new Date());
		
		//////// TEST ////////
		builder.addString("readQuery", "com.hiair.app.sample.test.service.SampleQueueMapper.select");
		
		String jobName = (String)jobDataMap.get(JOB_NAME); 
		logger.debug(">>>>>>>>>>>>> Job Name >>>>>>>>>>>>>" + jobName);
		builder.addString("jobName", jobName);
		if("DemandTicektJob".equals(jobName)) {
			builder.addString("writeQuery", "com.hiair.app.sample.test.service.SampleQueueMapper.update1");
		}else if("RefundTicektJob".equals(jobName)) {
			builder.addString("writeQuery", "com.hiair.app.sample.test.service.SampleQueueMapper.update2");
		}
		
		return builder.toJobParameters();

	}
}

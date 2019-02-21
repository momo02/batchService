package com.hiair.app.schedule.job;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
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
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

//QuartzJobBean -> 전달된 job data map을 빈의 속성값으로 적용 
@Component
public class JobLauncherDetails extends QuartzJobBean {

	static final String JOB_NAME = "jobName";
	
	@Autowired
	private JobLocator jobLocator;
	@Autowired
	private JobLauncher jobLauncher;
	
//	public void setJobLocator(JobLocator jobLocator) {
//		this.jobLocator = jobLocator;
//	}
//
//	public void setJobLauncher(JobLauncher jobLauncher) {
//		this.jobLauncher = jobLauncher;
//	}

	protected void executeInternal(JobExecutionContext context) {
		
		try {
			
//			SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
			System.err.println("///////// 111 //////////////");
			
			Map<String, Object> jobDataMap = context.getMergedJobDataMap();

			String jobName = (String) jobDataMap.get(JOB_NAME);

			JobParameters jobParameters = getJobParametersFromJobMap(jobDataMap);
			
			System.err.println("jobLocator >>>>>" + jobLocator);
			System.err.println("jobParameters >>>>>" + jobParameters);
			
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
		builder.addDate("run date", new Date());
		
		return builder.toJobParameters();

	}

}
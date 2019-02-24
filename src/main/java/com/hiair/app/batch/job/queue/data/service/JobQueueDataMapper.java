package com.hiair.app.batch.job.queue.data.service;

import java.util.List;

import com.hiair.app.batch.job.queue.data.model.JobQueueData;

public interface JobQueueDataMapper {
	
	public List<JobQueueData> list();
	
	public int insert(JobQueueData model);
	public int update(JobQueueData model);
	public int delete(JobQueueData model);
}

package com.hiair.app.batch.job.queue.group.service;

import java.util.List;

import com.hiair.app.batch.job.queue.group.model.JobQueueGroup;


public interface JobQueueGroupService {
	
	public List<JobQueueGroup> list();
	
	public int insert(JobQueueGroup model);
	
	public int update(JobQueueGroup model);
	
	public int delete(JobQueueGroup model);
	 
}

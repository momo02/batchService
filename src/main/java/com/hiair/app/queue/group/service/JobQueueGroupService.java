package com.hiair.app.queue.group.service;

import java.util.List;

import com.hiair.app.queue.group.model.JobQueueGroup;


public interface JobQueueGroupService {
	
	public List<JobQueueGroup> list();
	public JobQueueGroup detail(JobQueueGroup model);
	
	public int insert(JobQueueGroup model);
	public int update(JobQueueGroup model);
	public int delete(JobQueueGroup model);
	 
}

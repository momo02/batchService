package com.hiair.app.queue.history.service;

import java.util.List;

import com.hiair.app.queue.history.model.JobQueueHistory;


public interface JobQueueHistoryService {
	
	public List<JobQueueHistory> list();
	public JobQueueHistory detail(JobQueueHistory model);
	
	public int insert(JobQueueHistory model);
	public int update(JobQueueHistory model);
	public int delete(JobQueueHistory model);
	public int deleteAll();
}

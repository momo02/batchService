package com.hiair.app.batch.job.queue.history.service;

import java.util.List;

import com.hiair.app.batch.job.queue.history.model.JobQueueHistory;

public interface JobQueueHistoryMapper {
	
	public List<JobQueueHistory> list();
	
	public int insert(JobQueueHistory model);
	public int update(JobQueueHistory model);
	public int delete(JobQueueHistory model);
}

package com.hiair.app.queue.data.service;

import java.util.List;

import com.hiair.app.queue.data.model.JobQueueData;

public interface JobQueueDataService {
	
	public List<JobQueueData> list(JobQueueData param);
	public JobQueueData detail(JobQueueData model);
	
	public int insert(JobQueueData model);
	public int update(JobQueueData model);
	public int delete(JobQueueData model);
	public int deleteAll(); 
	
	// spring batch의 reader & writer 객체에 의해 사용
	public List<JobQueueData> listForReader(JobQueueData param);
	public int updateForWriter(JobQueueData model);
}

package com.hiair.app.queue.data.service;

import java.util.List;

import com.hiair.app.queue.data.model.JobQueueData;
import com.hiair.sys.annotation.Mapper;

@Mapper
public interface JobQueueDataMapper {
	
	public List<JobQueueData> list(JobQueueData model);
	public JobQueueData detail(JobQueueData model);
	
	public int insert(JobQueueData model);
	public int update(JobQueueData model);
	public int delete(JobQueueData model);
	public int deleteAll();
	
	// spring batch의 reader & writer 객체에 의해 사용
	public List<JobQueueData> listForReader(JobQueueData model);
	public int updateForWriter(JobQueueData model);
}

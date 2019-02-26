package com.hiair.app.queue.group.service;

import java.util.List;

import com.hiair.app.queue.group.model.JobQueueGroup;
import com.hiair.sys.annotation.Mapper;

@Mapper
public interface JobQueueGroupMapper {
	
	public List<JobQueueGroup> list();
	public JobQueueGroup detail(JobQueueGroup model);
	
	public int insert(JobQueueGroup model);
	public int update(JobQueueGroup model);
	public int delete(JobQueueGroup model);
}

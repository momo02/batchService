package com.hiair.app.queue.group.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiair.app.queue.group.model.JobQueueGroup;

@Service
public class JobQueueGroupServiceImpl implements JobQueueGroupService{
	
	private static final Logger logger = LoggerFactory.getLogger(JobQueueGroupServiceImpl.class);
	
	@Autowired
	private JobQueueGroupMapper mapper;

	public List<JobQueueGroup> list(JobQueueGroup model) {
		return mapper.list(model);
	}
	
	public JobQueueGroup detail(JobQueueGroup model) {
		return  mapper.detail(model);
	}
	
	public int insert(JobQueueGroup model) {
		return mapper.insert(model);
	}

	public int update(JobQueueGroup model) {
		return mapper.update(model);
	}

	public int delete(JobQueueGroup model) {
		return mapper.delete(model);
	}

	public int deleteAll() {
		return mapper.deleteAll();
	}

}

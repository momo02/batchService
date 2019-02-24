package com.hiair.app.batch.job.queue.group.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiair.app.batch.job.queue.group.model.JobQueueGroup;

@Service
public class JobQueueGroupServiceImpl implements JobQueueGroupService{
	
	private static final Logger logger = LoggerFactory.getLogger(JobQueueGroupServiceImpl.class);
	
	@Autowired
	private JobQueueGroupMapper mapper;

	@Override
	public List<JobQueueGroup> list() {
		return mapper.list();
	}

	@Override
	public int insert(JobQueueGroup model) {
		return mapper.insert(model);
	}

	@Override
	public int update(JobQueueGroup model) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(JobQueueGroup model) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

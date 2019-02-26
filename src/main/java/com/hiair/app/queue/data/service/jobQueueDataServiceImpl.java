package com.hiair.app.queue.data.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiair.app.queue.data.model.JobQueueData;

@Service
public class jobQueueDataServiceImpl implements JobQueueDataService{
	
	private static final Logger logger = LoggerFactory.getLogger(jobQueueDataServiceImpl.class);
	
	@Autowired
	private JobQueueDataMapper mapper;
	
	public List<JobQueueData> list() {
		return mapper.list();
	}
	
	public JobQueueData detail(JobQueueData model) {
		return mapper.detail(model);
	}

	public int insert(JobQueueData model) {
		return mapper.insert(model);
	}

	public int update(JobQueueData model) {
		return mapper.update(model);
	}

	public int delete(JobQueueData model) {
		return mapper.delete(model);
	}
}

package com.hiair.app.queue.history.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiair.app.queue.history.model.JobQueueHistory;

@Service
public class JobQueueHistoryServiceImpl implements JobQueueHistoryService{
	
	private static final Logger logger = LoggerFactory.getLogger(JobQueueHistoryServiceImpl.class);
	
	@Autowired
	private JobQueueHistoryMapper mapper;
	
	public List<JobQueueHistory> list() {
		return mapper.list();
	}

	public JobQueueHistory detail(JobQueueHistory model) {
		return mapper.detail(model);
	}
	
	public int insert(JobQueueHistory model) {
		return mapper.insert(model);
	}

	public int update(JobQueueHistory model) {
		return mapper.update(model);
	}

	public int delete(JobQueueHistory model) {
		return mapper.delete(model);
	}
}

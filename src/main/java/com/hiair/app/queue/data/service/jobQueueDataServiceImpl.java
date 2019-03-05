package com.hiair.app.queue.data.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiair.app.queue.data.model.JobQueueData;

@Service
public class JobQueueDataServiceImpl implements JobQueueDataService{
	
	private static final Logger logger = LoggerFactory.getLogger(JobQueueDataServiceImpl.class);
	
	@Autowired
	private JobQueueDataMapper mapper;
	
	public List<JobQueueData> list(JobQueueData model) {
		return mapper.list(model);
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

	public int deleteAll() {
		return mapper.deleteAll();
	}
	
	public List<JobQueueData> listForReader(JobQueueData model) {
		return mapper.listForReader(model);
	}

	public int updateForWriter(JobQueueData model) {
		return mapper.updateForWriter(model);
	}
	
}

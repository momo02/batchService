package com.hiair.app.batch.job.cmm;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hiair.app.queue.data.model.JobQueueData;
import com.hiair.app.queue.data.service.JobQueueDataService;


//@Component
public class JobQueueDataReader implements ItemReader<JobQueueData> {
	
	private static final Logger logger = LoggerFactory.getLogger(JobQueueDataReader.class);
	
	//private static Queue<JobQueueData> tempQueue = new LinkedList<JobQueueData>();
	
	private static Queue<JobQueueData> tempQueue = new LinkedList<JobQueueData>();
	
	private static Object lock = new Object();
	
	@Autowired
	private JobQueueDataService service;
	
	@PostConstruct
	public void initialize() {
		logger.info("================ 새로운 잡인스턴스 생성(새로운 스텝 생성)때마다 호출??"); //아니면 딱 한번 호출되는지...
		logger.info("================ init JobQueueDataReader ================");
		
//		logger.info("scanning file directory: {}.", inputDirectoryLocation);
//
//		File inputDirectory = new File(inputDirectoryLocation);
//		if (!inputDirectory.exists()) {
//			logger.warn("Input directory {} does not exist, creating it.", inputDirectoryLocation);
//			inputDirectory.mkdirs();
//		}
//
//		Arrays.stream(inputDirectory.listFiles()).forEach(file -> attemptQueue.add(new Attempt(file)));
//
//		logger.info("{} attempts queued.", attemptQueue.size());
		
		
	}
	
//	////생성자
//	public JobQueueDataReader(JobQueueDataService service) {
//		super();
//		this.service = service;
//		
//		
//		
//	}
	
	
	///// 모든 조회 대상 데이터를 Queue<JobQueueData>에다가 저장해둔다. 
	@Override
	public synchronized JobQueueData read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODOJ Auto-generated method stub
		
		//static 큐 변수에서 한개씩 꺼내서
		//큐 데이터 상태를 PROCESSING으로 바꾼다, 
		
		synchronized( lock )
		{	
			logger.debug("");	
		   ///statement;      // theObject 가 동기화된다.

		}
		
		
		return null;
	}

	

}

package com.hiair.app.batch.job.cmm;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.hiair.app.queue.data.model.JobQueueData;

//ItemProcessor : ItemReader로부터 읽어들인 Item을 DB에 Write하기 전에 필요한 로직을 처리.
//null을 반환하면 Writer에 전달되지 X
@Component
public class CmmJobProcessor implements ItemProcessor<JobQueueData, JobQueueData> {
	
	private static final Logger logger = LoggerFactory.getLogger(CmmJobProcessor.class);
	
	@Override
	public JobQueueData process(JobQueueData item) throws Exception {
		
		logger.debug(">>>>> param items (before) : " + item.toString());
		
		
		////// 각 서비스 처리 로직 ///////////
		///// 서비스 수행 중 에러 테스트 
		try {
			
			//// 성공/실패 random test
			boolean isSuccess = new Random().nextBoolean();
			
			if(isSuccess) {
				item.setProcessCode("SUCCESS");
				item.setProcessContent("성공입니당! ^-^"); //history
			}else {
				throw new Exception("test");
			}
			
		}catch(Exception e) {
			item.setProcessCode("FAIL");
			item.setProcessContent("실패입니다..ㅠㅠ"); //history
		}

		item.setProcessCount(item.getProcessCount() + 1);
		//item.setProcessServerIp("testServerIp");
		//스레드 확인하기 위해서 임시로 스레드 정보 저장...
		item.setProcessServerIp(Thread.currentThread().getName());
		
		logger.debug(">>>>> param items (after) : " + item.toString());
		return item;
	}

}

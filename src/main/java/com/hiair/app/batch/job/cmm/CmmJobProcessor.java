package com.hiair.app.batch.job.cmm;

import java.util.Random;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.hiair.app.queue.data.model.JobQueueData;

//ItemProcessor : ItemReader로부터 읽어들인 Item을 DB에 Write하기 전에 필요한 로직을 처리.
@Component
public class CmmJobProcessor implements ItemProcessor<JobQueueData, JobQueueData> {
	
//	public boolean getRandomBoolean() {
//	    Random random = new Random();
//	    return random.nextBoolean();
//	}
	
	@Override
	public JobQueueData process(JobQueueData item) throws Exception {
		
		System.err.println(">>>>> param items (before) : " + item.toString());
		
		////// 각 서비스 처리 로직 ///////////
		
		//// 성공/실패 random test
		boolean isSuccess = new Random().nextBoolean();
		
		if(isSuccess) {
			item.setProcessCode("SUCCESS");
			item.setProcessContent("성공입니당! ^-^"); //history
		}else {
			item.setProcessCode("FAIL");
			item.setProcessContent("실패입니다..ㅠㅠ"); //history
		}
		item.setProcessCount(item.getProcessCount() + 1);
		//item.setProcessServerIp("testServerIp");
		//스레드 확인하기 위해서 임시로 스레드 정보 저장...
		item.setProcessServerIp(Thread.currentThread().getName());
		
		System.err.println(">>>>> param items (after) : " + item.toString());
		return item;
	}

}

package com.hiair.app.queue.data.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hiair.app.queue.data.model.JobQueueData;
import com.hiair.app.queue.data.service.JobQueueDataService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:config/spring/test-main-context.xml"})
@WebAppConfiguration
@Transactional 
public class JobQueueDataServiceTest {
	
	@Autowired
	private JobQueueDataService service;
	private JobQueueData model;
	private JobQueueData model2;
	
	@Before
	public void setUp() {
		model = new JobQueueData();
		model.setJobGroup("IBE");
		model.setJobName("RefundTicektJob");
		model.setQueueClass("test");
//		model.setProcessCount(0);
		
//		model.setRecentProcessDate(null);
//		model.setRecentProcessCode("WAITING");
//		model.setRecentRunServerIp(null);
		
		model.setProcessCode("WAITING");
		model.setProcessServerIp("test");
		
		model.setResetDate(null);
		model.setResetUserId("testUser");
		
		model2 = new JobQueueData();
		model2.setJobGroup("ADM");
		model2.setJobName("RefundTicektJob");
		model2.setQueueClass("큐구분2");
//		model2.setProcessCount(0);
		
//		model2.setRecentProcessDate(null);
//		model2.setRecentProcessCode("WAITING");
//		model2.setRecentRunServerIp(null);
		
		model2.setProcessCode("WAITING");
		model2.setProcessServerIp("test");
		
		model2.setResetDate(null);
		model2.setResetUserId("testUser");
	}
	
	@Test
	public void testList() {
		JobQueueData param = new JobQueueData();
		param.setJobGroup("ADM");
		param.setJobName("RefundTicektJob");
//임시주석,,		
//		List<JobQueueData> getList = service.list(param);
//		CmmJsonUtils.println(getList);
//		System.out.println(">>>>> list count : " + getList.size());
	}

	@Test
	public void testDetail() {
		
	}

	@Test
	//@Rollback(false)
	public void testInsert() {
		int runCnt = 0;
		while(runCnt < 100) {
			int result = service.insert(model);
			//assertThat(result, is(1));
			runCnt++;
		}
		//System.out.println(result);
	}

	@Test
	public void testUpdate() {
	}

	@Test
	public void testDelete() {
	}
	

	@Test
	//@Rollback(false)
	public void testDeleteAll() {
		int deleteCnt = service.deleteAll();
		System.out.println(">>>>> delete count : " + deleteCnt);
	}

}

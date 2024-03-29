package com.hiair.app.queue.history.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hiair.app.queue.history.model.JobQueueHistory;
import com.hiair.app.queue.history.service.JobQueueHistoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:config/spring/test-main-context.xml"})
@WebAppConfiguration
@Transactional 
public class JobQueueHistoryServiceTest {
	
	@Autowired
	private JobQueueHistoryService service;
	private JobQueueHistory model;
	
	@Before
	public void setUp() {
		model = new JobQueueHistory();
		model.setQueueDataNo(3); //큐 정보 번호 
		model.setProcessCode("SUCCESS"); //처리 코드
		model.setProcessContent("잘처리됬슴용"); //처리 내용
		model.setProcessServerIp("일단test로"); //처리 서버 IP
	}
	
	@Test
	public void testList() {
	}

	@Test
	public void testDetail() {
	}

	@Test
	//@Rollback(false)
	public void testInsert() {
		int result = service.insert(model);
		assertThat(result, is(1));
		
		System.out.println(result);
	}

	@Test
	public void testUpdate() {
	}

	@Test
	public void testDelete() {
	}

}

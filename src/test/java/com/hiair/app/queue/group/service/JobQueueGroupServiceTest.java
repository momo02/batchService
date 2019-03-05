package com.hiair.app.queue.group.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hiair.app.queue.group.model.JobQueueGroup;
import com.hiair.app.queue.group.service.JobQueueGroupService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:config/spring/test-main-context.xml"})
@WebAppConfiguration
@Transactional
public class JobQueueGroupServiceTest {
	
	@Autowired
	private JobQueueGroupService service;
	private JobQueueGroup model;
	
	@Before
	public void setUp() {
		model = new JobQueueGroup();
		model.setJobGroup("IBE");
		model.setJobName("TestJob");
		model.setBatchJobName("RefundTicektJob");
		model.setRetryCount(10);
		model.setProduceUserId("testUser");
		model.setThreadCount(2);
		model.setQueueSaveFlag("Y");
		model.setJobData("test");
		model.setModifiedUserId("testUser");
	}
	
	@Test
	public void testList() {
		JobQueueGroup param = new JobQueueGroup();
		param.setJobGroup("IBE");

//임시주석		
//		List<JobQueueGroup> getList = service.list(param);
//		CmmJsonUtils.println(getList);
//		System.out.println(">>>>> list count : " + getList.size());
	}
		
	@Test
	//@Rollback(false) //rollback 예외처리
	public void testDetail() throws SQLException {
		//1. 저장
		int result = service.insert(model);
		assertThat(result, is(1));

//임시주석		
		//2. 저장한 값으로 조회
//		JobQueueGroup getModel = service.detail(model);
//		System.out.println(getModel);
//		
//		assertEquals(getModel.getJobName(),model.getJobName());
	}
	
	
	@Test
	//@Rollback(false) //transaction rollback 예외처리
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
		//service.delete(model)
	}

	@Test
	public void testDeleteAll() {
		int deleteCnt = service.deleteAll();
		System.out.println(">>>>> delete count : " + deleteCnt);
	}	
	
}

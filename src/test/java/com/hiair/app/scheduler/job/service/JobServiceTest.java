package com.hiair.app.scheduler.job.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hiair.cmm.util.CmmJsonUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:config/spring/test-main-context.xml"})
@WebAppConfiguration
@Transactional
public class JobServiceTest {

	@Autowired
	private JobService jobService;
	
	private static final String TEST_JOB_GROUP = "TESTGR";
	private static final String TEST_JOB_NAME = "testJob";
	
	@Test
	public void testList() {
		try {
			CmmJsonUtils.println(jobService.list());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testListByGroup() {
	}

	@Test
	public void testAdd() {
		try {
			jobService.add(TEST_JOB_NAME, TEST_JOB_GROUP, "테스트 Job");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDelete() {
		try {
			jobService.delete(TEST_JOB_NAME, TEST_JOB_GROUP);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}	
	}

	@Test
	public void testDeleteAll() {
	}

	@Test
	public void testPause() {
	}

	@Test
	public void testResume() {
	}

}

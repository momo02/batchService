package com.hiair.app.scheduler.trigger.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.hiair.app.scheduler.trigger.model.QrtzTrigger;

public class TriggerServiceTest {
	
	private static final String TEST_JOB_GROUP = "TESTGR";
	private static final String TEST_JOB_NAME = "testJob";
	private static final String TEST_TRIGGER_NAME = "testTrigger1";
	
	@Test
	public void testList() {
	}

	@Test
	public void testDetail() {
	}

	@Test
	public void testAdd() {
		QrtzTrigger qrtzTrigger = new QrtzTrigger();
		qrtzTrigger.setTriggerGroup(TEST_JOB_GROUP);
		qrtzTrigger.setTriggerName(TEST_TRIGGER_NAME);
		qrtzTrigger.setJobGroup(TEST_JOB_GROUP);
		qrtzTrigger.setJobName(TEST_JOB_NAME);
		qrtzTrigger.setCronExpression("0/30 * * * * ?");
		qrtzTrigger.setDescription("트리거 - 30초마다 실행");
	}

	@Test
	public void testPause() {
	}

	@Test
	public void testResume() {
	}

	@Test
	public void testDelete() {
	}

	@Test
	public void testUpdate() {
	}

	@Test
	public void testPauseAll() {
	}

	@Test
	public void testResumeAll() {
	}

}

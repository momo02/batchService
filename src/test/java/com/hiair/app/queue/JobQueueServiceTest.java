package com.hiair.app.queue;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.hiair.app.queue.data.model.JobQueueData;
import com.hiair.app.queue.data.service.JobQueueDataService;
import com.hiair.app.queue.group.model.JobQueueGroup;
import com.hiair.app.queue.group.service.JobQueueGroupService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:config/spring/test-main-context.xml"})
@WebAppConfiguration
@Transactional
public class JobQueueServiceTest {
	
	@Autowired
	private JobQueueGroupService jobQueueGroupService;
	@Autowired
	private JobQueueDataService jobQueueDataService;
	
	private JobQueueData jobQueueData;
	private JobQueueGroup jobQueueGroup;
	
	private static final String TEST_JOB_GROUP = "TESTGR";
	private static final String TEST_JOB_NAME = "testJob";
//	private static final String TEST_BATCH_JOB_NAME = "TestJob";
	private static final String TEST_BATCH_JOB_NAME = "CalendarShopJob";
	
	private JdbcTemplate jdbcTemplate;
	
	// dataSource 설정
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Before
	public void setUp() {
		///// 테스트 
		jobQueueGroup = new JobQueueGroup();
		jobQueueGroup.setJobGroup(TEST_JOB_GROUP);
		jobQueueGroup.setJobName(TEST_JOB_NAME);
		jobQueueGroup.setBatchJobName(TEST_BATCH_JOB_NAME);
		jobQueueGroup.setRetryCount(10);
		jobQueueGroup.setProduceUserId("testUser");
		jobQueueGroup.setThreadCount(5);
		jobQueueGroup.setQueueSaveFlag("Y");
		jobQueueGroup.setJobData("test");
		jobQueueGroup.setModifiedUserId("testUser");
		
		
		jobQueueData = new JobQueueData();
		jobQueueData.setJobGroup(TEST_JOB_GROUP);
		jobQueueData.setJobName(TEST_JOB_NAME);
		jobQueueData.setQueueClass("test");
		jobQueueData.setProcessCode("WAITING");
	}
	
	
	@Test
	@Rollback(false)
	public void 테스트_데이터_삭제() {
		//기존에 동일한 테스트 데이터 존재하면 삭제 
		deleteExistingSameData();
	}
	
	
	@Test
	@Rollback(false)
	public void 큐그룹과_큐정보_테이블에_데이터_추가() {
		//기존에 동일한 테스트 데이터 존재하면 삭제 
		deleteExistingSameData();
		
		//작업 큐 그룹 추가
		jobQueueGroupService.insert(jobQueueGroup);
		//작업 큐 정보 추가,, 동일데이터 1000건 
		jobQueueDataInsertRows(200);
	}
	
	
	@Test
	@Rollback(false)
	public void 큐그룹_테이블에만_데이터_추가() { //큐 정보 테이블에 데이터가 없는 단건 작업
		//기존에 동일한 테스트 데이터 존재하면 삭제  
		deleteExistingSameData();
		
		jobQueueGroup.setQueueSaveFlag("N"); //큐 정보 저장 여부 false
		//작업 큐 그룹 추가
		jobQueueGroupService.insert(jobQueueGroup);
	}
	
	
	/**
	 * rowCnt 만큼 작업 큐 정보 추가
	 * @param rowCnt
	 */
	public void jobQueueDataInsertRows(int rowCnt) {
		for(int i = 0; i < rowCnt; i++ ) {
			jobQueueDataService.insert(jobQueueData);
		}
	}
	
	/**
	 * 기존에 동일한 테스트 데이터 존재하면 삭제 (test insert 롤백없이 진행했을 경우)
	 */
	public void deleteExistingSameData() {
		//STEP 1
		//큐 히스토리 테이블에서 삭제
		String sql_delQueueHistory = "DELETE FROM QUEUE_HISOTRY\r\n" + 
				"WHERE QUEUE_DATA_NO IN ( \r\n" + 
				"	SELECT QUEUE_DATA_NO FROM QUEUE_DATA \r\n" + 
				"	WHERE JOB_NAME= ? AND JOB_GROUP= ? \r\n" + 
				")\r\n";
		this.jdbcTemplate.update(sql_delQueueHistory,TEST_JOB_GROUP,TEST_JOB_NAME);
		
		//큐 히스토리 테이블 auto_increment 초기화 
		String sql_initAutoIncrmntQueueHistory = "ALTER TABLE QUEUE_HISOTRY AUTO_INCREMENT = 0";  
		this.jdbcTemplate.update(sql_initAutoIncrmntQueueHistory);
		
		//STEP 2
		//큐 정보 테이블에서 삭제
		String sql_delQueueData = "DELETE FROM QUEUE_DATA"
							+ " WHERE JOB_GROUP = ? " 
							+ " AND JOB_NAME = ? ";
		this.jdbcTemplate.update(sql_delQueueData,TEST_JOB_GROUP,TEST_JOB_NAME);
		
		//큐 정보 테이블 auto_increment 초기화 
		String sql_initAutoIncrmntQueueData = "ALTER TABLE QUEUE_DATA AUTO_INCREMENT = 0";  
		this.jdbcTemplate.update(sql_initAutoIncrmntQueueData);
		
		//STEP 3
		//큐 그룹 테이블에서 삭제 
		String sql_delQueueGroup = "DELETE FROM QUEUE_GROUP"
							 + " WHERE JOB_GROUP = ? "
							 + " AND JOB_NAME = ? ";
		this.jdbcTemplate.update(sql_delQueueGroup,TEST_JOB_GROUP,TEST_JOB_NAME);
	}
	
}

package com.hiair.app.batch.sys.config;

import javax.annotation.PostConstruct;

import org.springframework.batch.core.configuration.BatchConfigurationException;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.MapJobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 
 * Custom {@link BatchConfigurer} for creating components needed by Spring Batch system. 
 *   => org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer(기본)를 사용하지않고 
 *      커스터마이징한 SpringBatchConfig를 사용한다.
 */
@Component      
public class SpringBatchConfigurer implements BatchConfigurer {
	
	private PlatformTransactionManager transactionManager;

	private JobRepository jobRepository;

	private JobLauncher jobLauncher;

	private JobExplorer jobExplorer;

	SpringBatchConfigurer() {
	}

	/**
	 * Registers {@link JobRepository} bean.
	 */
	@Override
	public JobRepository getJobRepository() {
		return this.jobRepository;
	}

	/**
	 * Registers {@link PlatformTransactionManager} bean.
	 */
	@Override
	public PlatformTransactionManager getTransactionManager() {
		return this.transactionManager;
	}

	/**
	 * Registers {@link JobLauncher} bean.
	 */
	@Override
	public JobLauncher getJobLauncher() {
		return this.jobLauncher;
	}

	/**
	 * Registers {@link JobExplorer} bean. This bean is actually created in {@link CmmJobConfig}.
	 */
	@Override
	public JobExplorer getJobExplorer() throws Exception {
		return this.jobExplorer;
	}

	/**
	 * Initializes Spring Batch components.
	 */
	@PostConstruct
	public void initialize() {
		try {
			// 아무 작업도 하지 않는 transactionManager
			this.transactionManager = new ResourcelessTransactionManager();
			
			// cf) Job Repository :현재 실행 중인 프로세스의 meta data를 저장
			//     ==> spring batch의 jobExecution과 stepExecution과 같은 domain 오브젝트를 저장하기 위한 CRUD 기능을 위해 사용
			
			// DataSource & DataSourceTransactionManager를 참조하여 DB Repository(DB에 메타데이터 저장)를 사용할수도 있지만, (=> DefaultBatchConfigurer 참고)
			// (이 경우엔 spring batch 제공 schema를 database에 생성해주어야함)  
			// 현 프로젝트는 db에 메타데이터를 관리할 필요가 없기때문에 In-Memory Repository (메모리에 메타데이터 저장)를 사용! 
			MapJobRepositoryFactoryBean jobRepositoryFactory = new MapJobRepositoryFactoryBean(this.transactionManager);
			jobRepositoryFactory.afterPropertiesSet();
			this.jobRepository = jobRepositoryFactory.getObject();

			MapJobExplorerFactoryBean jobExplorerFactory = new MapJobExplorerFactoryBean(jobRepositoryFactory);
			jobExplorerFactory.afterPropertiesSet();
			this.jobExplorer = jobExplorerFactory.getObject();

			this.jobLauncher = createJobLauncher();
		} catch (Exception e) {
			throw new BatchConfigurationException(e);
		}
	}
	
	private JobLauncher createJobLauncher() throws Exception {
		SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
		jobLauncher.setJobRepository(getJobRepository());
		jobLauncher.afterPropertiesSet();
		return jobLauncher;
	}
}

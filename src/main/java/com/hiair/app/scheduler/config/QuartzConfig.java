package com.hiair.app.scheduler.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;


@Configuration
public class QuartzConfig {
	 
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	public SchedulerFactoryBean quartzScheduler() {
	    
		SchedulerFactoryBean quartzScheduler = null; 
		
		try {
			quartzScheduler = new SchedulerFactoryBean();
		    // custom job factory of spring with DI support for @Autowired!
		    AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		    jobFactory.setApplicationContext(applicationContext);
		    
		    Resource configLocation = new PathMatchingResourcePatternResolver()
		    							  .getResource("classpath:config/properties/config-quartz.properties");
		    
		    quartzScheduler.setConfigLocation(configLocation);
		    quartzScheduler.setJobFactory(jobFactory);
	    
		}catch (Exception e) {
			e.printStackTrace();
		}
	    
	    return quartzScheduler;
	}

}

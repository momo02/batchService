package com.hiair.app.scheduler.sys.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

//SpringBeanJobFactory을 이용해 Quartz 객체들의 auto-wiring을 지원
public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {
		
	private transient AutowireCapableBeanFactory beanFactory;
	
	@Override
	public void setApplicationContext(final ApplicationContext context) {
	    beanFactory = context.getAutowireCapableBeanFactory();
	}
	
	@Override
	protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
	    final Object job = super.createJobInstance(bundle);
	    beanFactory.autowireBean(job);
	    return job;
	}
}
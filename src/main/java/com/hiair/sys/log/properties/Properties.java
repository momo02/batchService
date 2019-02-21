package com.hiair.sys.log.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class Properties implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger logger = LoggerFactory.getLogger(Properties.class);

	@Autowired
	private Environment env;
	static boolean isStart = false;
	
	/* 우선순위 
	1. Context parameter in web.xml
	2. WebApplicationInitializer
	3. JVM System parameter
	4. Environment variable
	5. Maven profile
	*/

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (!isStart) {
			
			logger.debug("================= PROFILE =================");

			String[] profiles = env.getActiveProfiles();
			for (String profile : profiles) {
				logger.debug("ACTIVE PROFILE : " + profile);
			}

			isStart = true;
		}
	}
}

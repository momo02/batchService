package com.hiair.app.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
@ContextConfiguration(locations = {"classpath:config/*.xml"})
@TestPropertySource("classpath:config/properties/config-dev.properties")
public class PropertiesLoadTest {

	@Value("${test}")
	private String test;
	@Value("${db}")
	private String db;
	
	@Test
	public void propertiesLoadTest() {
		System.out.println("test :: "+test);
		System.out.println("db :: "+db);
	}
}

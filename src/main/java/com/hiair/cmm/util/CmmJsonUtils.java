package com.hiair.cmm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CmmJsonUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(CmmJsonUtils.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	
	public static String print(Object object){
		try {
			return mapper.writeValueAsString(object);
		}catch (JsonProcessingException e) {
			 e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}
	
	public static String println(Object object){
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		}catch (JsonProcessingException e) {
			 e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}	
}

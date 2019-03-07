package com.hiair.app.batch.job.cmm.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.listener.ChunkListenerSupport;
import org.springframework.batch.core.scope.context.ChunkContext;

public class ChunkExecutionListener extends ChunkListenerSupport{
	
	private static final Logger logger = LoggerFactory.getLogger(ChunkExecutionListener.class);
	
	@Override
	public void beforeChunk(ChunkContext context) {
		context.attributeNames();
		logger.debug("======================= Before chunk =======================");
		super.beforeChunk(context);
	}
	
	@Override
	public void afterChunk(ChunkContext context) {
		logger.debug("======================= After chunk =======================");
		super.afterChunk(context);
	}
}
package com.hiair.app.batch;

import org.mybatis.spring.batch.MyBatisPagingItemReader;

public class MyItemReader extends MyBatisPagingItemReader{
	
	  public void setQueryId(String queryId) {
		    super.setQueryId(queryId);
	  }
}

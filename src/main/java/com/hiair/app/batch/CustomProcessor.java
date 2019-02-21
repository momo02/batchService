package com.hiair.app.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

import com.hiair.app.sample.test.vo.SampleQueueVO;


//ItemProcessor : ItemReader로부터 읽어들인 Item을 DB에 Write하기 전에 필요한 로직을 처리.
@Component
public class CustomProcessor implements ItemProcessor<Object, Object> {

	@Override
	public Object process(Object item) throws Exception {
	
		System.err.println("process param items >>>>> " + item);
	
		SampleQueueVO s = ((SampleQueueVO)item);
		
		System.out.print(s.toString());
		
		s.setPrcsCd("SUCCESS");
		s.setRtryCnt(0);
		
		return item;
	}
}

package com.hiair.app.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;


//ItemReader : 한 Step안에서 FlatFile, XML, DB 등 여러 input에서 Item을 읽어 들임.
@Component
public class CustomReader implements ItemReader<String> {

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		// TODO Auto-generated method stub
		
		System.out.println("read !!!!!!!!!!!!!!!");
		
		return "test";
	}
}

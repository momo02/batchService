package com.hiair.app.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;


//ItemWriter : ItemReader로부터 읽어 들인 Item을 Insert, Update 처리.
@Component
public class CustomWriter implements ItemWriter<String> {

	@Override
	public void write(List<? extends String> items) throws Exception {
		// TODO Auto-generated method stub
		
		
		System.err.println("items size >>>>> " + items.size());
		System.err.println("write param items >>>>> " + items);
		
		System.out.println("write !!!!!!!!!!!!!!!");
	}


}

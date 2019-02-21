package com.hiair.app.sample.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiair.app.sample.test.vo.SampleQueueVO;

@Service("sampleQueueService")
public class SampleQueueService{
	
	@Autowired
	private SampleQueueMapper mapper;
	
	public List<SampleQueueVO> select(){
		return mapper.select();
	}
	
	public int update(SampleQueueVO vo){
		return mapper.update(vo);
	}
}

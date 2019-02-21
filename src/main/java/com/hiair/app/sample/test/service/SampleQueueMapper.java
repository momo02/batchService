package com.hiair.app.sample.test.service;

import java.util.List;

import com.hiair.app.sample.test.vo.SampleQueueVO;
import com.hiair.sys.annotation.Mapper;

@Mapper
public interface SampleQueueMapper {

	public List<SampleQueueVO> select();
	public int update(SampleQueueVO vo);
	
	public int update1(SampleQueueVO vo);
	public int update2(SampleQueueVO vo);
}

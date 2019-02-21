package com.hiair.sys.log.error.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiair.sys.annotation.NoLogging;
import com.hiair.sys.log.error.mapper.SysErrorLogMapper;
import com.hiair.sys.log.error.vo.SysErrorLogVO;

@Service
public class SysErrorLogServiceImpl implements SysErrorLogService {
	@Autowired
	private SysErrorLogMapper mapper; 
	
	//에러 로그 저장
	@NoLogging
	public int insert(SysErrorLogVO vo) {
		return mapper.insert(vo);
	}
}

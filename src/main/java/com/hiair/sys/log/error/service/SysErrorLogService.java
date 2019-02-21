package com.hiair.sys.log.error.service;

import com.hiair.sys.log.error.vo.SysErrorLogVO;

public interface SysErrorLogService {
	//에러 로그 저장
	public int insert(SysErrorLogVO vo);
}

package com.hiair.sys.log.error.mapper;

import com.hiair.sys.annotation.Mapper;
import com.hiair.sys.log.error.vo.SysErrorLogVO;

@Mapper
public interface SysErrorLogMapper {
	//에러 로그 저장
	public int insert(SysErrorLogVO vo);
}

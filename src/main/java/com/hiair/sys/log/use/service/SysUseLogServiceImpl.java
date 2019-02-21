/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : SysAccessServiceImpl.java
 * @Description   : 
 * @Author        : 지대한
 * @Version       : v1.0
 * Copyright(c) 2019 PALNETWORKS All rights reserved
 *------------------------------------------------------------------------------
 *                  변         경         사         항                       
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                      DESCRIPTION                        
 * ----------  ------  --------------------------------------------------------- 
 * 2019-01-11   지대한    신규생성                                     
 *------------------------------------------------------------------------------
 */
package com.hiair.sys.log.use.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiair.cmm.util.CmmUserInfoManagemenet;
import com.hiair.sys.log.use.mapper.SysUseLogMapper;
import com.hiair.sys.log.use.vo.SysUseLogVO;

/**
 * <pre>
 * com.hiair.sys.interceptor.access.service
 * _SysAccessServiceImpl.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-11
 * @Version : 1.0
 */
@Service
public class SysUseLogServiceImpl implements SysUseLogService {

	@Autowired
	SysUseLogMapper mapper;
	
	@Override
	public int insertLog(SysUseLogVO vo) {
		if(CmmUserInfoManagemenet.isUserInfo()) {
			// TODO login 정보에 따라 변경
			vo.setUserName(CmmUserInfoManagemenet.getUserInfo().getUserName());
		}
		return mapper.insertLog(vo);
	}

}

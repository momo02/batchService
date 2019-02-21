/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : CmmFileMapper.java
 * @Description   : 
 * @Author        : 지대한
 * @Version       : v1.0
 * Copyright(c) 2019 PALNETWORKS All rights reserved
 *------------------------------------------------------------------------------
 *                  변         경         사         항                       
 *------------------------------------------------------------------------------
 *    DATE     AUTHOR                      DESCRIPTION                        
 * ----------  ------  --------------------------------------------------------- 
 * 2019-01-10   지대한    신규생성                                     
 *------------------------------------------------------------------------------
 */
package com.hiair.app.cmm.file.mapper;

import com.hiair.app.cmm.file.vo.CmmFileVO;
import com.hiair.sys.annotation.Mapper;

/**
 * <pre>
 * com.hiair.app.cmm.file.mapper
 * _CmmFileMapper.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-10
 * @Version : 1.0
 */
@Mapper
public interface CmmFileMapper {
	
	void insert(CmmFileVO vo);

	CmmFileVO selectByFileId(int fileId);

	int deleteByFileId(int fileId);

}

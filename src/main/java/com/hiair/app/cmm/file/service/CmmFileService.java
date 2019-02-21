/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : CmmFileService.java
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
package com.hiair.app.cmm.file.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.hiair.app.cmm.file.vo.CmmFileVO;

/**
 * <pre>
 * com.hiair.app.cmm.file.service
 * _CmmFileService.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-10
 * @Version : 1.0
 */
public interface CmmFileService {

	// 파일 저장
	public CmmFileVO fileSave(List<MultipartFile> files, CmmFileVO metaVO) throws Exception;
	
	// 파일 DB 저장
	public int insert(CmmFileVO vo);
	
	// 파일 제거
	public int removeFile(int fileId);
	
	// 파일 DB 제거
	public int deleteByFileId(int fileId);
	
	
	// TODO 임시 폴더에서 저장 폴더로 이동하는 메서드
	// TODO 일정기간동안 임시폴더에 있는 파일 제거
	
	// 파일 정보 select
	public CmmFileVO selectByFileId(int fileId);
	
	// TODO 파일 다운로드
	public int fileDownload(HttpServletResponse response, int fileId) throws Exception;
	
}

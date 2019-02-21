/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : CmmFileServiceImpl.java
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiair.app.cmm.file.mapper.CmmFileMapper;
import com.hiair.app.cmm.file.vo.CmmFileVO;

/**
 * <pre>
 * com.hiair.app.cmm.file.service
 * _CmmFileServiceImpl.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-10
 * @Version : 1.0
 */
@Service("cmmFileService")
public class CmmFileServiceImpl implements CmmFileService {

	private final static Logger logger = LoggerFactory.getLogger(CmmFileServiceImpl.class);

	// DB에 파일이 없을 경우
	private final int FILE_INFO_EMPTY = -1;
	// 저장소에 파일이 없을 경우
	private final int FILE_NOT_FOUND = -2;
	
	@Value("${file.path.save}")
	private String saveRootPath;
	
	@Value("${file.path.temp}")
	private String tempRootPath;
	
	@Autowired
	private CmmFileMapper mapper;
	
	// 실제 저장경로 추출
	private String getSavePath() {
		LocalDate ld = LocalDate.now();
		String tempPath = ld.getYear()+File.separator+ld.getMonthValue()+File.separator+ld.getDayOfMonth();
		return tempPath;
	}
	
	// 임시 저장경로 추출
	private String getTempPath() {
		LocalDate ld = LocalDate.now();
		String tempPath = ld.getYear()+File.separator+ld.getMonthValue()+File.separator+ld.getDayOfMonth();
		return tempPath; 
	}
	
	// 저장할 파일 이름 추출
	private String getSaveFileName(String fileName) {
		String saveFileName = UUID.randomUUID().toString()+"_"+fileName;
		return saveFileName;
	}
	
	/* (non-Javadoc)
	 * @see com.hiair.app.cmm.file.service.CmmFileService#fileSave(java.util.List, com.hiair.sys.file.vo.CmmFileVO)
	 */
	@Override
	public CmmFileVO fileSave(List<MultipartFile> files, CmmFileVO metaVO) throws Exception {
		
		ObjectMapper mapper = new ObjectMapper();
		
		boolean uploadedFlag = metaVO.getTotalChunks() - 1 <= metaVO.getChunkIndex();
		
		CmmFileVO result = null;
		
		String fileName = metaVO.getFileName();
		String uploadUid = metaVO.getUploadUid();
		int fileId = -1;
		
		if(uploadedFlag) {
			
			// 저장 경로
			String path = getTempPath();
			
			
			int charInx = fileName.lastIndexOf(".");
			String originalFileName = fileName.substring(0, charInx);
			String fileExt = fileName.substring(charInx+1);
			
			String storageFileName = getSaveFileName(originalFileName);
			logger.debug("### saveFileName : "+storageFileName);
			
			
			for (MultipartFile file : files) {
				logger.debug(file.getName());
				byte[] bytes = file.getBytes();
				File dir = new File(tempRootPath+File.separator+path);
				if (!dir.exists()) dir.mkdirs();
				
				// 파일이름.....
				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + storageFileName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile, true));
				stream.write(bytes);
				stream.close();
				
				result = new CmmFileVO();
				
				result.setFileExt(fileExt);
				result.setOriginalFileName(originalFileName);
				result.setStorageFileName(storageFileName);
				result.setFilePath(path);
				result.setFileSize((int)file.getSize());
//				result.setFileGroupId(1);
//				result.setFileType(1);
				fileId = insert(result);
			}
		}
		
		if(result == null) {
			result = new CmmFileVO();
		}
		if(fileId > -1) {
			result.setFileId(fileId);
		}
		result.setUploaded(uploadedFlag);
		result.setFileUid(uploadUid);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.hiair.app.cmm.file.service.CmmFileService#insert(com.hiair.sys.file.vo.CmmFileVO)
	 */
	@Override
	public int insert(CmmFileVO vo) {
		mapper.insert(vo);
		logger.debug("### : "+vo.toString());
		logger.debug("### fileid : "+vo.getFileId());
		return vo.getFileId();
	}

	/* (non-Javadoc)
	 * @see com.hiair.app.cmm.file.service.CmmFileService#removeFile(int)
	 */
	@Override
	public int removeFile(int fileId) {
		CmmFileVO vo = selectByFileId(fileId);
		if(vo == null) {
			return FILE_INFO_EMPTY;
		}
		
		String storageFileName = vo.getStorageFileName();
		String path = tempRootPath+File.separator+vo.getFilePath();
		
		File file = new File(path+File.separator+storageFileName);
		if(!file.exists()) {
			// TODO 파일이 존재하지 않을 경우
            return FILE_NOT_FOUND;
		}
		
		if(file.delete()) {
			deleteByFileId(fileId);
		}
		
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.hiair.app.cmm.file.service.CmmFileService#deleteByFileId(int)
	 */
	@Override
	public int deleteByFileId(int fileId) {
		return mapper.deleteByFileId(fileId);
	}

	/* (non-Javadoc)
	 * @see com.hiair.app.cmm.file.service.CmmFileService#selectByFileId(int)
	 */
	@Override
	public CmmFileVO selectByFileId(int fileId) {
		return mapper.selectByFileId(fileId);
	}

	/* (non-Javadoc)
	 * @see com.hiair.app.cmm.file.service.CmmFileService#fileDownload(javax.servlet.http.HttpServletResponse, int)
	 */
	@Override
	public int fileDownload(HttpServletResponse response, int fileId) throws Exception {
		CmmFileVO vo = selectByFileId(fileId);
		
		if(vo == null) {
			throw new Exception("not file infomation");
//			return FILE_INFO_EMPTY;
		}
		
		String originalFileName = vo.getOriginalFileName() + "." + vo.getFileExt();
		String storageFileName = vo.getStorageFileName();
		String path = tempRootPath+File.separator+vo.getFilePath();
		
		File file = new File(path+File.separator+storageFileName);
		
		System.out.println(file.getName());
		
        if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            System.out.println(errorMessage);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return FILE_NOT_FOUND;
        }
         
        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null){
            System.out.println("mimetype is not detectable, will take default");
            mimeType = "application/octet-stream";
        }
         
        System.out.println("mimetype : "+mimeType);
         
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + originalFileName +"\""));
        response.setContentLength((int)file.length());
        response.setHeader("Content-Transfer-Encoding", "binary");
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
		return 1;
	}

}

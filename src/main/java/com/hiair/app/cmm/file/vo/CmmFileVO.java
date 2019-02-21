/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : CmmFileVO.java
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
package com.hiair.app.cmm.file.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * <pre>
 * com.hiair.app.cmm.file.vo
 * _CmmFileVO.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-10
 * @Version : 1.0
 */
@Alias("cmmFile")
public class CmmFileVO extends KendoFileVO{
	
	private int fileId;
	private int fileGroupId;
	private String originalFileName;
	private String storageFileName;
	private String fileExt;
	private String filePath;
	private int fileSize;
	private int fileType;
	private Date insertDate;
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public int getFileGroupId() {
		return fileGroupId;
	}
	public void setFileGroupId(int fileGroupId) {
		this.fileGroupId = fileGroupId;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getStorageFileName() {
		return storageFileName;
	}
	public void setStorageFileName(String storageFileName) {
		this.storageFileName = storageFileName;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getFileSize() {
		return fileSize;
	}
	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}
	public int getFileType() {
		return fileType;
	}
	public void setFileType(int fileType) {
		this.fileType = fileType;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
	@Override
	public String toString() {
		return "CmmFileVO [fileId=" + fileId + ", fileGroupId=" + fileGroupId + ", originalFileName=" + originalFileName
				+ ", storageFileName=" + storageFileName + ", fileExt=" + fileExt + ", filePath=" + filePath
				+ ", fileSize=" + fileSize + ", fileType=" + fileType + ", insertDate=" + insertDate + "]";
	}
	
}

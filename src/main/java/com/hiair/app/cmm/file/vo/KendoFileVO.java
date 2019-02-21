/*
 * ------------------------------------------------------------------------------
 * @Project       : HIAIR
 * @Source        : KendoFileVO.java
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

import com.hiair.app.cmm.vo.DefaultVO;

/**
 * <pre>
 * com.hiair.app.cmm.file.vo
 * _KendoFileVO.java
 * </pre>
 * 
 * Desc : 
 * @Company : PALNETWORKS
 * @Date 2019-01-10
 * @Version : 1.0
 */
@Alias("kendoFile")
public class KendoFileVO extends DefaultVO{
	
	private boolean uploaded;
	private String fileUid;
	private String fileNames;
	private int chunkIndex;
	private String contentType;
	private String fileName;
	private String relativePath;
	private long totalFileSize;
	private int totalChunks;
	private String uploadUid;
	private Date insertDate;
	
	public boolean isUploaded() {
		return uploaded;
	}
	public void setUploaded(boolean uploaded) {
		this.uploaded = uploaded;
	}
	public String getFileUid() {
		return fileUid;
	}
	public void setFileUid(String fileUid) {
		this.fileUid = fileUid;
	}
	public String getFileNames() {
		return fileNames;
	}
	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}
	public int getChunkIndex() {
		return chunkIndex;
	}
	public void setChunkIndex(int chunkIndex) {
		this.chunkIndex = chunkIndex;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getRelativePath() {
		return relativePath;
	}
	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}
	public long getTotalFileSize() {
		return totalFileSize;
	}
	public void setTotalFileSize(long totalFileSize) {
		this.totalFileSize = totalFileSize;
	}
	public int getTotalChunks() {
		return totalChunks;
	}
	public void setTotalChunks(int totalChunks) {
		this.totalChunks = totalChunks;
	}
	public String getUploadUid() {
		return uploadUid;
	}
	public void setUploadUid(String uploadUid) {
		this.uploadUid = uploadUid;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}
	
	@Override
	public String toString() {
		return "KendoFileVO [uploaded=" + uploaded + ", fileUid=" + fileUid + ", fileNames=" + fileNames
				+ ", chunkIndex=" + chunkIndex + ", contentType=" + contentType + ", fileName=" + fileName
				+ ", relativePath=" + relativePath + ", totalFileSize=" + totalFileSize + ", totalChunks=" + totalChunks
				+ ", uploadUid=" + uploadUid + ", insertDate=" + insertDate + "]";
	}
	
}

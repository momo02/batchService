package com.hiair.app.sample.fileupload.vo;

import org.apache.ibatis.type.Alias;

import com.hiair.app.cmm.file.vo.KendoFileVO;

@Alias("file")
public class SampleFileVO extends KendoFileVO{
	
	private int fileId;
	private int fileGroupId;
	private String originalFileName;
	private String storageFileName;
	private String fileExt;
	private String filePath;
	private int fileSize;
	private int fileType;
	
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
	@Override
	public String toString() {
		return "FileVO [fileId=" + fileId + ", fileGroupId=" + fileGroupId + ", originalFileName=" + originalFileName
				+ ", storageFileName=" + storageFileName + ", fileExt=" + fileExt + ", filePath=" + filePath
				+ ", fileSize=" + fileSize + ", fileType=" + fileType + ", toString()=" + super.toString() + "]";
	}
	
	
}

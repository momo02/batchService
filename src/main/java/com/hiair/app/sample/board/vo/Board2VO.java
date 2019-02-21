package com.hiair.app.sample.board.vo;

import org.apache.ibatis.type.Alias;

import com.hiair.app.cmm.vo.DefaultVO;

@Alias("board2")
public class Board2VO extends DefaultVO{
	
	private int boardId;
	private String boardTitle;
	private String boardContent;
	private int fileGroupId;
	private int imgFileGroupId;
	private String insertDate;
	private String updateDate; 
	private int insertUserId;
	private String insertUserName;
	private int boardType;
	
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public int getFileGroupId() {
		return fileGroupId;
	}
	public void setFileGroupId(int fileGroupId) {
		this.fileGroupId = fileGroupId;
	}
	public int getImgFileGroupId() {
		return imgFileGroupId;
	}
	public void setImgFileGroupId(int imgFileGroupId) {
		this.imgFileGroupId = imgFileGroupId;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public int getInsertUserId() {
		return insertUserId;
	}
	public void setInsertUserId(int insertUserId) {
		this.insertUserId = insertUserId;
	}
	public String getInsertUserName() {
		return insertUserName;
	}
	public void setInsertUserName(String insertUserName) {
		this.insertUserName = insertUserName;
	}
	public int getBoardType() {
		return boardType;
	}
	public void setBoardType(int boardType) {
		this.boardType = boardType;
	}
	@Override
	public String toString() {
		return "Board2VO [boardId=" + boardId + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", fileGroupId=" + fileGroupId + ", imgFileGroupId=" + imgFileGroupId + ", insertDate=" + insertDate
				+ ", updateDate=" + updateDate + ", insertUserId=" + insertUserId + ", insertUserName=" + insertUserName
				+ ", boardType=" + boardType + "]";
	}
	
	
}

package com.hiair.app.sample.board.service;

import java.util.List;

import com.hiair.app.sample.board.vo.Board2VO;
import com.hiair.app.sample.board.vo.BoardVO;

public interface BoardService {

	public List<BoardVO> select(BoardVO vo);

	public int insert(List<BoardVO> voList) throws Exception;
	public int update(List<BoardVO> voList);
	public int delete(List<BoardVO> voList);
	
	//////// vo date type >> string type
	
	public List<Board2VO> select2(Board2VO vo);
	
	public int insert2(List<Board2VO> voList) throws Exception;
	public int update2(List<Board2VO> voList);
	public int delete2(List<Board2VO> voList);

}

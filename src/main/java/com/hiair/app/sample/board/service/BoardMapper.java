package com.hiair.app.sample.board.service;

import java.util.List;

import com.hiair.app.sample.board.vo.Board2VO;
import com.hiair.app.sample.board.vo.BoardVO;
import com.hiair.sys.annotation.Mapper;

@Mapper
public interface BoardMapper {

	public List<BoardVO> select(BoardVO vo);
	public int insert(BoardVO vo);
	public int update(BoardVO vo);
	public int delete(BoardVO vo);
	
	public List<Board2VO> select2(Board2VO vo);
	public int insert2(Board2VO vo);
	public int update2(Board2VO vo);
	public int delete2(Board2VO vo);
	
}

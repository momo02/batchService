package com.hiair.app.sample.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hiair.app.sample.board.vo.Board2VO;
import com.hiair.app.sample.board.vo.BoardVO;
import com.hiair.cmm.util.CmmUserInfoManagemenet;

@Service("boardService")
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardMapper mapper;

	public List<BoardVO> select(BoardVO vo) {
		return mapper.select(vo);
	}

	@Override
	public int insert(List<BoardVO> voList) throws Exception {
		int proc = 0;
		for(BoardVO vo : voList) {
			proc += mapper.insert(vo);
		}
		return proc;
	}
	
	@Override
	public int update(List<BoardVO> voList) {
		int proc = 0;
		for(BoardVO vo : voList) {
			System.out.println(vo.getBoardId() +" :: "+vo.getInsertDate());
			proc += mapper.update(vo);
		}
		return proc;
	}
	
	@Override
	public int delete(List<BoardVO> voList) {
		int proc = 0;
		for(BoardVO vo : voList) {
			proc += mapper.delete(vo);
		}
		return proc;
	}
	
	//////// vo date type >> string type
	@Override
	public List<Board2VO> select2(Board2VO vo) {
		return mapper.select2(vo);
	}
	@Override
	public int insert2(List<Board2VO> voList) throws Exception {
		int proc = 0;
		// 세션 정보 가져오기
		String userName = CmmUserInfoManagemenet.getUserInfo().getUserName();
		int userId = CmmUserInfoManagemenet.getUserInfo().getId();
		int cnt = 0;
		for(Board2VO vo : voList) {
			// 
			vo.setInsertUserName(userName);
			vo.setInsertUserId(userId);
			proc += mapper.insert2(vo);
			cnt++;
			System.out.println("### cnt : "+cnt);
			if(cnt >= 3) {
				throw new Exception("transation test...");
			}
		}
		return proc;
	}
	@Override
	public int update2(List<Board2VO> voList) {
		int proc = 0;
		for(Board2VO vo : voList) {
			System.out.println(vo.getBoardId() +" :: "+vo.getInsertDate());
			proc += mapper.update2(vo);
		}
		return proc;
	}
	@Override
	public int delete2(List<Board2VO> voList) {
		int proc = 0;
		for(Board2VO vo : voList) {
			proc += mapper.delete2(vo);
		}
		return proc;
	}

}

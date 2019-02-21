package com.hiair.app.sample.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiair.app.sample.board.service.BoardService;
import com.hiair.app.sample.board.vo.Board2VO;
import com.hiair.app.sample.board.vo.BoardVO;

@Controller
@RequestMapping(value="/sample/board")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class); 
	
	@Resource(name="boardService")
	private BoardService service;
	
	@RequestMapping(value="/board.do")
	public String viewBoard(BoardVO vo, Model model) {
		return "/sample/board/board.dash";
	}
	@RequestMapping(value="/board2.do")
	public String viewBoard2(BoardVO vo, Model model) {
		return "/sample/board/board2.dash";
	}
	@RequestMapping(value="/boardList.do")
	public String viewBoardList(BoardVO vo, Model model) {
		return "/sample/board/boardList.dash";
	}
	@RequestMapping(value="/boardDetail.do")
	public String viewBoardDetail(BoardVO vo, Model model) {
		model.addAttribute("vo", vo);
		return "/sample/board/boardDetail";
	}
	
	
	
	@RequestMapping(value="/select.do")
	@ResponseBody
	public List<BoardVO> selectList(BoardVO vo){
		List<BoardVO> list = service.select(vo);
		return list;
	}
	
	@RequestMapping(value="/insert.do")
	@ResponseBody
	public Map<String,Object> insert(@RequestBody List<BoardVO> voList) throws Exception {
		int proc = service.insert(voList);
		Map<String, Object> map = new HashMap<>();
		map.put("success", "ok");
		map.put("proc",proc);
		return map;
	}
	
	@RequestMapping(value="/update.do")
	@ResponseBody
	public Map<String,Object> update(@RequestBody List<BoardVO> voList) {
		int proc = service.update(voList);
		Map<String, Object> map = new HashMap<>();
		map.put("success", "ok");
		map.put("proc",proc);
		return map;
	}
	
	@RequestMapping(value="/delete.do")
	@ResponseBody
	public Map<String,Object> delete(@RequestBody List<BoardVO> voList) {
		int proc = service.delete(voList);
		Map<String, Object> map = new HashMap<>();
		map.put("success", "ok");
		map.put("proc",proc);
		return map;
	}
	
	@RequestMapping(value="/select2.do")
	@ResponseBody
	public List<Board2VO> selectList2(Board2VO vo){
		List<Board2VO> list = service.select2(vo);
		return list;
	}
	
	@RequestMapping(value="/save2.do")
	@ResponseBody
	public Map<String,Object> save(@RequestBody List<Board2VO> voList, String status) throws Exception {
		int proc = 0;
		switch(status) {
		case "insert":
			proc = service.insert2(voList);
			
			break;
		case "update": 
			proc = service.update2(voList);
			
			break;
		case "delete": 
			proc = service.delete2(voList);
			
			break;
		default:
			System.out.println("other");
			
			
			// TODO 에러 처리
//			throw new Exception("saveFlag is ohter type");
			break;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("success", "ok");
//		map.put("proc",proc);
		return map;
	}
	
}

package com.dictation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dictation.service.BoardService;
import com.dictation.vo.BoardVO;


@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
    //insert user
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void execWrite(@RequestBody BoardVO board) {
		boardService.insert(board);
	}


      //according to id delete
	@GetMapping(value="/delete/{seq_no}")
	public void delete(@PathVariable("seq_no") String seq_no) {
		boardService.delete(seq_no);
	}
	//modify
	//seq_no는 같아야 함
	@PostMapping(value="/update")
	public void update(@RequestBody BoardVO board) {
		boardService.update(board);
	}

	//according to id Query students
	@GetMapping(value="/get/{seq_no}")
	public BoardVO getById(@PathVariable("seq_no") String seq_no) {
		BoardVO board = boardService.getById(seq_no);
		return board;
	}
	
	//All queries
	@PostMapping(value="/list")
	public List<BoardVO> list(){
		return boardService.list();
	}	
}

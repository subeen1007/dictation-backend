package com.dictation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
	public void insert(@RequestBody BoardVO board,HttpServletRequest request) {
		
		//lecture_no
		HttpSession session = request.getSession();
		board.setLecture_no((int)session.getAttribute("lecture_no"));
		
		
		//board_cd
		board.setDae_b("006");
		if(board.getBoard_cd().equals("001")) {//프론트에서 공지사항이면 001로 데이터 값을 넘김  
			board.setSo_b("001");
		}else if(board.getBoard_cd().equals("002")) {//프론트에서 학습자료이면 002로 데이터 값을 넘김
			board.setSo_b("002");
		}else if(board.getBoard_cd().equals("002")) {////프론트에서 Q&A이면 003로 데이터 값을 넘김
			board.setSo_b("003");
		}
		
		
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

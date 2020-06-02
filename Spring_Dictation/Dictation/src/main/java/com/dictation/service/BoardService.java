package com.dictation.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.BoardMapper;
import com.dictation.vo.BoardVO;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(BoardVO board) {
		boardMapper.insert(board);
	}

	//according to id delete
	public void delete(String seq_no) {
		boardMapper.delete(seq_no);
	}

	//according to user Of id modify
	public void update(BoardVO board) {
		boardMapper.update(board);
	}

	//according to id query
	public BoardVO getById(String seq_no) {
		return boardMapper.getById(seq_no);
	}

	//All queries
	public List<BoardVO> list(){
		return boardMapper.list();
	}
	

}

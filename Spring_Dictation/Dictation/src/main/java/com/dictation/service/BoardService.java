package com.dictation.service;

import java.util.HashMap;
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
	public void delete(HashMap<String, Object> map) {
		boardMapper.delete(map);
	}
	
	//update after delete
	
	public void after_delete(HashMap<String, Object> map) { 
		boardMapper.after_delete(map); 
	}
	
	

	//according to user Of id modify
	public void update(BoardVO board) {
		boardMapper.update(board);
	}

	//according to id query
	public BoardVO getById(HashMap<String, Object> map) {
		return boardMapper.getById(map);
	}

	//All queries
	public List<BoardVO> list(){
		return boardMapper.list();
	}
	
	//search file_nm for file download
	public String getFileNm(String save_file_nm) {
		return boardMapper.getFileNm(save_file_nm);
	}
	

}

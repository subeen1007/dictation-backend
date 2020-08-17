package com.dictation.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.CommentMapper;
import com.dictation.vo.CommentVO;

@Service
public class CommentService {
	@Autowired
	private CommentMapper commentMapper;
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(CommentVO comment) {
		commentMapper.insert(comment);
	}

	//delete
	public void delete(HashMap<String, Object> map) {
		commentMapper.delete(map);
	}
	
	//´ñ±Û ¼öÁ¤
	public void update(CommentVO comment) {
		commentMapper.update(comment);
	}
	
	public CommentVO getById(CommentVO comment) {
		return commentMapper.getById(comment);
	}

	//All queries
	public List<CommentVO> list(CommentVO comment){
		return commentMapper.list(comment);
	}

}

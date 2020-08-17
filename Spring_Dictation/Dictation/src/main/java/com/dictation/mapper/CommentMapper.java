package com.dictation.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.dictation.vo.CommentVO;

@Repository
@Mapper
public interface CommentMapper {

	//insert
	public void insert(CommentVO comment);

	//delete
	public void delete(HashMap<String, Object> map);
	
	public void update(CommentVO comment);

	//according to id query
	public CommentVO getById(CommentVO comment);

	//All queries
	public List<CommentVO> list(CommentVO comment);
	
}

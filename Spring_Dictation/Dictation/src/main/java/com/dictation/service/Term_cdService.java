package com.dictation.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.Term_cdMapper;
import com.dictation.vo.Term_cdVO;


@Service
public class Term_cdService {
	
	@Autowired
	private Term_cdMapper term_cdMapper;
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(Term_cdVO term_cd) {
		term_cdMapper.insert(term_cd);
	}

	//according to id delete
	public void delete(String year, String term) {
		term_cdMapper.delete(year,term);
	}

	//according to user Of id modify
	public void update(Term_cdVO term_cd) {
		term_cdMapper.update(term_cd);
	}

	//according to id query
	public Term_cdVO getById(String year, String term) {
		return term_cdMapper.getById(year,term);
	}

	//All queries
	public List<Term_cdVO> list(){
		return term_cdMapper.list();
	}
	

}

package com.dictation.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.So_cdMapper;
import com.dictation.vo.So_cdVO;



@Service
public class So_cdService {
	
	@Autowired
	private So_cdMapper so_cdMapper;
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(So_cdVO so_cd) {
		so_cdMapper.insert(so_cd);
	}

	//according to id delete
	public void delete(String so_cd) {
		so_cdMapper.delete(so_cd);
	}

	//according to user Of id modify
	public void update(So_cdVO so_cd) {
		so_cdMapper.update(so_cd);
	}

	//according to id query
	public So_cdVO getById(String so_cd) {
		return so_cdMapper.getById(so_cd);
	}

	//All queries
	public List<So_cdVO> list(){
		return so_cdMapper.list();
	}
	

}

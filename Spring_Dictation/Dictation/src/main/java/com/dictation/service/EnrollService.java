package com.dictation.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.EnrollMapper;
import com.dictation.vo.EnrollVO;

@Service
public class EnrollService {
	
	@Autowired
	private EnrollMapper enrollMapper;
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(EnrollVO enroll) {
		enrollMapper.insert(enroll);
	}

	//according to id delete
	public void delete(String user_id) {
		enrollMapper.delete(user_id);
	}

	//according to user Of id modify
	public void update(EnrollVO enroll) {
		enrollMapper.update(enroll);
	}

	//according to id query
	public EnrollVO getById(String user_id) {
		return enrollMapper.getById(user_id);
	}

	//All queries
	public List<EnrollVO> list(){
		return enrollMapper.list();
	}
	

}

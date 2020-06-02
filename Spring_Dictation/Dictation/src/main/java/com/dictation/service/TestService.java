package com.dictation.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.TestMapper;
import com.dictation.vo.TestVO;

@Service
public class TestService {
	
	@Autowired
	private TestMapper testMapper;
	
	public List<TestVO> select() {
		
		return testMapper.selectTest();
	}
	
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(TestVO user) {
		testMapper.insert(user);
	}

	//according to id delete
	public void delete(String user_id) {
		testMapper.delete(user_id);
	}

	//according to user Of id modify
	public void update(TestVO user) {
		testMapper.update(user);
	}

	//according to id query
	public TestVO getById(String user_id) {
		return testMapper.getById(user_id);
	}

	//All queries
	public List<TestVO> list(){
		return testMapper.list();
	}
	

}

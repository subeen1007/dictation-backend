package com.dictation.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.UserMapper;
import com.dictation.vo.UserVO;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(UserVO user) {
		userMapper.insert(user);
	}

	//according to id delete
	public void delete(String user_id) {
		userMapper.delete(user_id);
	}

	//according to user Of id modify
	public void update(UserVO user) {
		userMapper.update(user);
	}

	//according to id query
	public UserVO getById(String user_id) {
		return userMapper.getById(user_id);
	}

	//All queries
	public List<UserVO> list(){
		return userMapper.list();
	}
	

}

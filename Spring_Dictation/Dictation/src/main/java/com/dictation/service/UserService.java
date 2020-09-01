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
	
	//user_id 생성할때 중복되는 user_id가 있는지 검사하는코드(선생님 엑셀파일 업로드에 사용)
	public String userid_no_search(String user_id) {
		return userMapper.userid_no_search(user_id);
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

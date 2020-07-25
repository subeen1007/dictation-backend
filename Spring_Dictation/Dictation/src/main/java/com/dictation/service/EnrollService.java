package com.dictation.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.EnrollMapper;
import com.dictation.vo.EnrollVO;
import com.dictation.vo.UserVO;

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
	public void delete(EnrollVO enroll) {
		enrollMapper.delete(enroll);
	}

	//according to user Of id modify
	public void update(EnrollVO enroll) {		
		enrollMapper.update(enroll);
	}
	
	//선생님이 학생을 승인시켜줌
	public void update_request(int lecture_no, String user_id) {
		EnrollVO enroll = new EnrollVO();
		enroll.setLecture_no(lecture_no);
		enroll.setUser_id(user_id);
		
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
	
	//신청현황 리스트
	public List<UserVO> list_request(int lecture_no){
		return enrollMapper.list_request(lecture_no);
	}
	

}

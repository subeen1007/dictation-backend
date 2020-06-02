package com.dictation.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.LectureMapper;
import com.dictation.vo.LectureVO;

@Service
public class LectureService {
	
	@Autowired
	private LectureMapper lectureMapper;
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(LectureVO lecture) {
		lectureMapper.insert(lecture);
	}

	//according to id delete
	public void delete(String lecture_no) {
		lectureMapper.delete(lecture_no);
	}

	//according to user Of id modify
	public void update(LectureVO lecture) {
		lectureMapper.update(lecture);
	}

	//according to id query
	public LectureVO getById(String lecture_no) {
		return lectureMapper.getById(lecture_no);
	}

	//All queries
	public List<LectureVO> list(){
		return lectureMapper.list();
	}
	

}

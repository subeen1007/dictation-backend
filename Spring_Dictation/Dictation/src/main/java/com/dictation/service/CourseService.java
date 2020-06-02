package com.dictation.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.CourseMapper;
import com.dictation.vo.CourseVO;

@Service
public class CourseService {
	
	@Autowired
	private CourseMapper courseMapper;
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(CourseVO course) {
		courseMapper.insert(course);
	}

	//according to id delete
	public void delete(String lecture_no) {
		courseMapper.delete(lecture_no);
	}

	//according to user Of id modify
	public void update(CourseVO course) {
		courseMapper.update(course);
	}

	//according to id query
	public CourseVO getById(String lecture_no) {
		return courseMapper.getById(lecture_no);
	}

	//All queries
	public List<CourseVO> list(){
		return courseMapper.list();
	}
	

}

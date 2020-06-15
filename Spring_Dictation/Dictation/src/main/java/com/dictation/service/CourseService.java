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
	public void delete(CourseVO course) {
		courseMapper.delete(course);
	}

	//according to user Of id modify
	public void update(CourseVO course) {
		courseMapper.update(course);
	}

	//according to id query
	public CourseVO getById(CourseVO course) {
		return courseMapper.getById(course);
	}

	//All queries
	public List<CourseVO> list(){
		return courseMapper.list();
	}
	
	//search file_nm for file download
	public String getFileNm(String save_file_nm) {
		return courseMapper.getFileNm(save_file_nm);
	}
	

}

package com.dictation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dictation.service.CourseService;
import com.dictation.vo.CourseVO;


@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/course")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	
    //insert user
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void insert(@RequestBody CourseVO course,HttpServletRequest request) {
		
		//lecture_no
		HttpSession session = request.getSession();
		course.setLecture_no((int)session.getAttribute("lecture_no"));
		
		
		courseService.insert(course);
	}


      //according to id delete
	@GetMapping(value="/delete/{lecture_no}")
	public void delete(@PathVariable("lecture_no") String lecture_no) {
		courseService.delete(lecture_no);
	}
	//modify
	//lecture_no는 같아야 함
	@PostMapping(value="/update")
	public void update(@RequestBody CourseVO course) {
		courseService.update(course);
	}

	//according to id Query students
	@GetMapping(value="/get/{lecture_no}")
	public CourseVO getById(@PathVariable("lecture_no") String lecture_no) {
		CourseVO course = courseService.getById(lecture_no);
		return course;
	}
	
	//All queries
	@PostMapping(value="/list")
	public List<CourseVO> list(){
		return courseService.list();
	}	
}

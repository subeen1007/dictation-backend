package com.dictation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dictation.service.LectureService;
import com.dictation.vo.LectureVO;


@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/lecture")
public class LectureController {
	
	@Autowired
	private LectureService lectureService;
	
	
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void execWrite(@RequestBody LectureVO lecture) {
		lectureService.insert(lecture);
	}


      //according to id delete
	@GetMapping(value="/delete/{lecture_no}")
	public void delete(@PathVariable("lecture_no") String lecture_no) {
		lectureService.delete(lecture_no);
	}
	//modify
	//lecture_no는 같아야 함
	@PostMapping(value="/update")
	public void update(@RequestBody LectureVO lecture) {
		lectureService.update(lecture);
	}

	//according to id Query students
	@GetMapping(value="/get/{lecture_no}")
	public LectureVO getById(@PathVariable("lecture_no") String lecture_no) {
		LectureVO lecture = lectureService.getById(lecture_no);
		return lecture;
	}
	
	//All queries
	@PostMapping(value="/list")
	public List<LectureVO> list(){
		return lectureService.list();
	}	
}

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

import com.dictation.service.TestService;
import com.dictation.vo.TestVO;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/test")
public class TestController {
	
	@Autowired
	private TestService testService;
	
	//전체리스트 가져옴
	@GetMapping(value="/test")
	public List<TestVO> select() {
		return testService.select();
	}
	
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void execWrite(@RequestBody TestVO user) {
		testService.insert(user);
	}
	
      //according to id delete
	@GetMapping(value="/delete/{user_id}")
	public void delete(@PathVariable("user_id") String user_id) {
		testService.delete(user_id);
	}
	//modify
	@PostMapping(value="/update")
	public void update(@RequestBody TestVO user) {
		testService.update(user);
	}

	//according to id Query students
	@GetMapping(value="/get/{user_id}")
	public TestVO getById(@PathVariable("user_id") String user_id) {
		TestVO user = testService.getById(user_id);
		return user;
	}
	
	//All queries
	@GetMapping(value="/list")
	public List<TestVO> list(){
		return testService.list();
	}	
}

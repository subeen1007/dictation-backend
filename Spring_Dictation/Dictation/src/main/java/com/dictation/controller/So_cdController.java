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

import com.dictation.service.So_cdService;
import com.dictation.vo.So_cdVO;



@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/so_cd")
public class So_cdController {
	
	@Autowired
	private So_cdService so_cdService;
	
	
    //insert user
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void insert(@RequestBody So_cdVO so_cd) {
		so_cdService.insert(so_cd);
	}


      //according to id delete
	@GetMapping(value="/delete/{so_cd}")
	public void delete(@PathVariable("so_cd") String so_cd) {
		so_cdService.delete(so_cd);
	}
	//modify
	//so_cd는 같아야 함
	@PostMapping(value="/update")
	public void update(@RequestBody So_cdVO so_cd) {
		so_cdService.update(so_cd);
	}

	//according to id Query students
	@GetMapping(value="/get/{so_cd}")
	public So_cdVO getById(@PathVariable("so_cd") String so_cd) {
		So_cdVO so_cd1 = so_cdService.getById(so_cd);
		return so_cd1;
	}
	
	//All queries
	@PostMapping(value="/list")
	public List<So_cdVO> list(){
		return so_cdService.list();
	}	
}

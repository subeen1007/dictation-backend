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

import com.dictation.service.Dae_cdService;
import com.dictation.vo.Dae_cdVO;


@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/dae_cd")
public class Dae_cdController {
	
	@Autowired
	private Dae_cdService dae_cdService;
	
	
    //insert
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void insert(@RequestBody Dae_cdVO dae_cd) {
		dae_cdService.insert(dae_cd);
	}


      //delete
	@GetMapping(value="/delete/{dae_cd}")
	public void delete(@PathVariable("dae_cd") String dae_cd) {
		dae_cdService.delete(dae_cd);
	}
	//modify
	//dae_cd는 같아야 함
	@PostMapping(value="/update")
	public void update(@RequestBody Dae_cdVO dae_cd) {
		dae_cdService.update(dae_cd);
	}

	//getbyid
	@GetMapping(value="/get/{dae_cd}")
	public Dae_cdVO getById(@PathVariable("dae_cd") String dae_cd) {
		Dae_cdVO dae_cd2 = dae_cdService.getById(dae_cd);
		return dae_cd2;
	}
	
	//All queries
	@PostMapping(value="/list")
	public List<Dae_cdVO> list(){
		return dae_cdService.list();
	}	
}

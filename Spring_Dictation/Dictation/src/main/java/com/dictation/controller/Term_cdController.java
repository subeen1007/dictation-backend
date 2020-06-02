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

import com.dictation.service.Term_cdService;
import com.dictation.vo.Term_cdVO;




@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/term_cd")
public class Term_cdController {
	
	@Autowired
	private Term_cdService term_cdService;
	
	
    //insert user
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void insert(@RequestBody Term_cdVO term_cd) {
		term_cdService.insert(term_cd);
	}


      //according to id delete
	@GetMapping(value="/delete/{year}/{term}")//아직 미완성(test오류)
	public void delete(@PathVariable("year") String year, @PathVariable("term") String term) {
		term_cdService.delete(year,term);
	}
	//modify
	//year,term는 같아야 함
	@PostMapping(value="/update")
	public void update(@RequestBody Term_cdVO term_cd) {
		term_cdService.update(term_cd);
	}

	//according to id Query students
	@GetMapping(value="/get/{year&term}")//아직 미완성(test오류)
	public Term_cdVO getById(@PathVariable("year") String year, @PathVariable("term") String term) {
		Term_cdVO term_cd = term_cdService.getById(year,term);
		return term_cd;
	}
	
	//All queries
	@PostMapping(value="/list")
	public List<Term_cdVO> list(){
		return term_cdService.list();
	}	
}

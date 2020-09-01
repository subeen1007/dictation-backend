package com.dictation.controller;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.dictation.service.Dae_cdService;
import com.dictation.service.So_cdService;
import com.dictation.service.Term_cdService;
import com.dictation.service.UserService;
import com.dictation.vo.Dae_cdVO;
import com.dictation.vo.So_cdVO;
import com.dictation.vo.Term_cdVO;




@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/admin")
public class AdminController {//관리자 컨트롤러

	@Autowired
	private Term_cdService term_cdService;
	@Autowired
	private UserService userService;
	@Autowired
	private Dae_cdService dae_cdService;
	@Autowired
	private So_cdService so_cdService;
	
    //insert user
	@PostMapping(produces = "application/json;charset=UTF-8", value="/term_cd")
	public void term_insert(@RequestBody Term_cdVO term_cd) {
		term_cdService.insert(term_cd);
	}
	
	//according to id delete
	@GetMapping(value="/term_cd/delete/{year}/{term}")//아직 미완성(test오류)
	public void term_delete(@PathVariable("year") String year, @PathVariable("term") String term) {
		term_cdService.delete(year,term);
	}
	//modify
	//year,term는 같아야 함
	@PostMapping(value="/term_cd/update")
	public void term_update(@RequestBody Term_cdVO term_cd) {
		term_cdService.update(term_cd);
	}

	//according to id Query students
	@GetMapping(value="/term_cd/get/{year&term}")//아직 미완성(test오류)
	public Term_cdVO term_getById(@PathVariable("year") String year, @PathVariable("term") String term) {
		Term_cdVO term_cd = term_cdService.getById(year,term);
		return term_cd;
	}
	
	//All queries
	@PostMapping(value="/term_cd/list")
	public List<Term_cdVO> term_list(){
		return term_cdService.list();
	}	
	
	
	//insert
	@PostMapping(produces = "application/json;charset=UTF-8", value="/dae_cd")
	public void dae_insert(@RequestBody Dae_cdVO dae_cd) {
		dae_cdService.insert(dae_cd);
	}


    //delete
	@GetMapping(value="/dae_cd/delete/{dae_cd}")
	public void dae_delete(@PathVariable("dae_cd") String dae_cd) {
		dae_cdService.delete(dae_cd);
	}
	//modify
	//dae_cd는 같아야 함
	@PostMapping(value="/dae_cd/update")
	public void dae_update(@RequestBody Dae_cdVO dae_cd) {
		dae_cdService.update(dae_cd);
	}

	//getbyid
	@GetMapping(value="/dae_cd/get/{dae_cd}")
	public Dae_cdVO dae_getById(@PathVariable("dae_cd") String dae_cd) {
		Dae_cdVO dae_cd2 = dae_cdService.getById(dae_cd);
		return dae_cd2;
	}
	
	//All queries
	@PostMapping(value="/dae_cd/list")
	public List<Dae_cdVO> dae_list(){
		return dae_cdService.list();
	}
	
	//insert user
	@PostMapping(produces = "application/json;charset=UTF-8", value="/so_cd")
	public void so_insert(@RequestBody So_cdVO so_cd) {
		so_cdService.insert(so_cd);
	}


      //according to id delete
	@GetMapping(value="/so_cd/delete/{so_cd}")
	public void so_delete(@PathVariable("so_cd") String so_cd) {
		so_cdService.delete(so_cd);
	}
	//modify
	//so_cd는 같아야 함
	@PostMapping(value="/so_cd/update")
	public void so_update(@RequestBody So_cdVO so_cd) {
		so_cdService.update(so_cd);
	}

	//according to id Query students
	@GetMapping(value="/so_cd/get/{so_cd}")
	public So_cdVO so_getById(@PathVariable("so_cd") String so_cd) {
		So_cdVO so_cd1 = so_cdService.getById(so_cd);
		return so_cd1;
	}
	
	//All queries
	@PostMapping(value="/so_cd/list")
	public List<So_cdVO> so_list(){
		return so_cdService.list();
	}
}

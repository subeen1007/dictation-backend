package com.dictation.controller;

import java.util.Enumeration;
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
import org.springframework.web.bind.support.SessionStatus;

import com.dictation.service.LectureService;
import com.dictation.vo.LectureVO;


@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/lecture")
public class LectureController {
	
	@Autowired
	private LectureService lectureService;

	
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void insert(@RequestBody LectureVO lecture) {
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

	//lecture_no변수 int형으로 바꿀것
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
	
	//강좌들어갈때, 수강신청 눌렀을때 lecture_no 세션값 생성
	//나중에는 post로 lecture_no 값 줄것
	@GetMapping(value = "/lecture_no/{lecture_no}")
	public String lecture_no(@PathVariable("lecture_no") int lecture_no, HttpServletRequest request) throws Exception {
		
		HttpSession session = request.getSession();
		session.setAttribute("lecture_no", lecture_no);
		int lecture_session=(int)session.getAttribute("lecture_no");
		System.out.println("lecture_no 세션값 :" +lecture_session);

	    return "lecture_no";
	}
	
	
	//세션값 확인후 지우는 메소드(test용)
	@GetMapping(value = "/session")
	public String session(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();

	    System.out.println("lecture_no 세션값 :" +(int)session.getAttribute("lecture_no"));
	    
	    //모든 세션값 확인
	    Enumeration se = session.getAttributeNames();
	    while(se.hasMoreElements()){
	    	String getse = se.nextElement()+"";
	    	System.out.println("@@@@@@@ session : "+getse+" : "+session.getAttribute(getse));
	    }


	    // 세션에서 지운다.
	    //session.invalidate();
	    //System.out.println("지운후 user_id 세션값 :" +session.getAttribute("user_id"));
	    //System.out.println("지운후 lecture_no 세션값 :" +session.getAttribute("lecture_no"));
	    return "login/user_id&lecture_no";
	}
		
}

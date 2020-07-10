package com.dictation.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.dictation.service.CourseService;
import com.dictation.service.LectureService;
import com.dictation.vo.CourseVO;
import com.dictation.vo.LectureVO;


@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/lecture")
public class LectureController {
	
	@Autowired
	private LectureService lectureService;
	@Autowired
	private CourseService courseService;

	//강좌개설(강좌정보 + 받아쓰기정보 받아옴)
	//강좌정보는 lecture테이블에 넣고, 받아쓰기정보는 배열로 받아와서 course테이블에 넣음
	@CrossOrigin("*")
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void insert_lec_cour(@RequestBody LectureVO lecture, @RequestBody CourseVO[] course) {
		//프론트엔드 lecture_nm, grade, ban받아옴
		//백엔드 lecture_no, school_cd 추가(enroll_st_dt는 추후에 맵퍼에서 추가)

		int lecture_no=rand(7); //DB에 넣을 lecture_no칼럼값 생성(7자리 난수생성)
		Object db_lec_no = lectureService.lecture_no_search(lecture_no); //생성한난수가 디비에 이미있는지 검사(없으면 null, 있으면 lecture_no값)
		
		//lecture_no중복되지 않는 값으로 설정
		while(db_lec_no!=null) {
			lecture_no=rand(7);
			db_lec_no = lectureService.lecture_no_search(lecture_no);
		}
		
		lecture.setLecture_no(lecture_no);		
		lectureService.insert(lecture);
		
		//받아쓰기 정보들 DB에 저장
		for(int i=0; i<course.length; i++) {
			course[i].setLecture_no(lecture_no);
			
			//음성파일 추가해야함
			
			courseService.insert(course[i]);
		
		}
	}
	
	
	public int rand(int num) {//num자리번호 난수생성
		Random random = new Random();
		String numStr="";//num자리번호 문자형
		int numInt=0;//num자리번호 정수형
		
		for(int i=0; i<num; i++) {
			String ran=Integer.toString(random.nextInt(10));
			numStr += ran;
		}
		numInt=Integer.parseInt(numStr);
		return numInt;
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
	public LectureVO getById(@PathVariable("lecture_no") int lecture_no) {
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

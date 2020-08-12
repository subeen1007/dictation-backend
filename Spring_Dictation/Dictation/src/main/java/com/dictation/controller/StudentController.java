package com.dictation.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.dictation.service.CourseService;
import com.dictation.service.EnrollService;
import com.dictation.service.LectureService;
import com.dictation.vo.CourseVO;
import com.dictation.vo.EnrollVO;
import com.dictation.vo.LectureVO;
import com.dictation.vo.UserVO;


@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/student")
public class StudentController {//학생 컨트롤러

	@Autowired
	private EnrollService enrollService;
	@Autowired
	private LectureService lectureService;
	@Autowired
	private CourseService courseService;

	
	//학생들이 수강신청버튼 눌렀을때
	//세션값user_id받아와서  enroll테이블에 insert
	//lecture_no, user_id만 있으면 됨
	@GetMapping(value="/enroll/insert/{lecture_no}")
	public void insert_student(@PathVariable("lecture_no") int lecture_no, HttpServletRequest request) {
		EnrollVO enroll = new EnrollVO();
		enroll.setLecture_no(lecture_no);
		enroll.setApproval_cd("미승인");
		
		//user_id
		HttpSession session = request.getSession();
		UserVO user_session=(UserVO)session.getAttribute("user");
		enroll.setUser_id(user_session.getUser_id());
		
		enrollService.insert(enroll);
	}
	
	//학생화면-수강신청 취소
	//enroll테이블에서 delete
	//세션값 user_id가져와서 delete
	@GetMapping(value="/enroll/delete/{lecture_no}")
	public void delete_student(@PathVariable("lecture_no") int lecture_no, HttpServletRequest request) {
		EnrollVO enroll = new EnrollVO();
		enroll.setLecture_no(lecture_no);
		
		HttpSession session = request.getSession();
		UserVO user_session=(UserVO)session.getAttribute("user");
		enroll.setUser_id(user_session.getUser_id());
		
		enrollService.delete(enroll);
	}
	
	//학생화면 전체강좌 리스트에 강좌신청여부
	@RequestMapping(value="/lecture/student_lec_list")
	public List<LectureVO> student_lec_list(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		UserVO user_session=(UserVO)session.getAttribute("user");
	
		System.out.println("student_lec_list에서 user_id 세션값 : "+user_session.getUser_id());
				
		return lectureService.student_lec_list(user_session.getUser_id());
	}
	//학생 본인이 수강신청해서 승인된 강좌목록 띄우기 위한 코드
	@RequestMapping(value="/lecture/student_mylec")
	public List<LectureVO> student_mylec(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		UserVO user_session=(UserVO)session.getAttribute("user");
	
		System.out.println("student_mylec에서 user_id 세션값 : "+user_session.getUser_id());
				
		return lectureService.student_mylec(user_session.getUser_id());
	}
	
	//학생이 통과한 단계번호 반환
	@GetMapping(value="/enroll/what_pass_course")
	public Integer what_pass_course(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user_session = (UserVO)session.getAttribute("user");
		int lecture_session=(int)session.getAttribute("lecture_no");
		
		EnrollVO enroll=new EnrollVO();
		enroll.setUser_id(user_session.getUser_id());
		enroll.setLecture_no(lecture_session);
		
		return enrollService.what_pass_course(enroll);
	}
	
	//학생이 통과한 단계번호 업데이트
	@GetMapping(value="/enroll/update/{course_step}")
	public void enroll_update(@PathVariable("course_step") int course_step, HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserVO user_session = (UserVO)session.getAttribute("user");
		int lecture_session=(int)session.getAttribute("lecture_no");
		Integer pass_course;//통과한 단계번호
		
		EnrollVO enroll=new EnrollVO();
		enroll.setUser_id(user_session.getUser_id());
		enroll.setLecture_no(lecture_session);
		pass_course=enrollService.what_pass_course(enroll);
		
		//통과한 단계번호 update
		if(pass_course<course_step || pass_course==null) {
			enroll.setPass_course_no(course_step);
			enrollService.update(enroll);
		}
	}
	

}

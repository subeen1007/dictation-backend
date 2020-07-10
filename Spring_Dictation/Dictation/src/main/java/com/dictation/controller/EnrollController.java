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
import com.dictation.service.EnrollService;
import com.dictation.vo.CourseVO;
import com.dictation.vo.EnrollVO;
import com.dictation.vo.UserVO;


@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/enroll")
public class EnrollController {
	
	@Autowired
	private EnrollService enrollService;
	@Autowired
	private CourseService courseService;
	
	
    //insert user
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void insert(@RequestBody EnrollVO enroll, HttpServletRequest request) {
		
		//lecture_no
		HttpSession session = request.getSession();
		enroll.setLecture_no((int)session.getAttribute("lecture_no"));
				
		enrollService.insert(enroll);
	}


      //according to id delete
	@GetMapping(value="/delete/{user_id}")
	public void delete(@PathVariable("user_id") String user_id) {
		enrollService.delete(user_id);
	}
	//modify
	//user_id는 같아야 함
	@PostMapping(value="/update")
	public void update(@RequestBody EnrollVO enroll) { //user_id, lecture_no값 필수
		enrollService.update(enroll);
	}

	//according to id Query students
	@GetMapping(value="/get/{user_id}")
	public EnrollVO getById(@PathVariable("user_id") String user_id) {
		EnrollVO enroll = enrollService.getById(user_id);
		return enroll;
	}
	
	//All queries
	@PostMapping(value="/list")
	public List<EnrollVO> list(){
		return enrollService.list();
	}	
	
	//정답비교(학생답을 매개변수로 넣음)
	@PostMapping(value="/answer")
	public boolean[] answer(@RequestBody CourseVO[] courseList, HttpServletRequest request) throws Exception {
		//quesion, lecture_no, course_no, quesion_no가져와야 함
		//원래 lecture_no, user_id는 세션값으로 가져와야함(일단 user_id는 임의로 만듬)

		//세션값에서 학생아이디 가져옴
		HttpSession session = request.getSession();
		UserVO user_session=(UserVO)session.getAttribute("user");
		String student_id = user_session.getUser_id();
		
		
		String question;
		CourseVO course;
		boolean[] answer=new boolean[courseList.length];

		for(int i=0; i<courseList.length; i++) {
			question=courseList[i].getQuestion();
			course=courseService.getById(courseList[i]);
			if(question.equals(course.getQuestion())) {
				answer[i]=true;
			}else {
				answer[i]=false;
			}
		}
		
		System.out.println("학생아이디 : "+student_id);
		
		EnrollVO enroll=new EnrollVO();
		enroll.setLecture_no(courseList[1].getLecture_no());
		enroll.setUser_id(student_id);
		
		//점수(추후에 점수표기가 아닌 단계표기할때 삭제할 예정)
		int score=0;
		for(int i=0; i<answer.length; i++) {
			if(answer[i]==true) {
				score+=10;
			}
		}
		enroll.setPass_course_no(score);//일단은 점수로 표기(추후에 단계로 표시할 예정)
		
		//enroll.setUser_id("vv");//임시 아이디(user_id가 기존 enroll에 있어야함)
		enrollService.update(enroll);
		return answer;
		
	}
}

package com.dictation.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dictation.service.UserService;
import com.dictation.vo.UserVO;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
    //insert user
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void insert(@RequestBody UserVO user) {
		
		//position_cd
		user.setDae_p("003");
		if(user.getPosition_cd().equals("관리자")) {  
			user.setSo_p("001");
		}else if(user.getPosition_cd().equals("선생님")) {//프론트에서 선생님이면 "선생님"으로 데이터 값을 넘김
			user.setSo_p("002");
		}else if(user.getPosition_cd().equals("학생")) {//프론트에서 학생이면 "학생"으로 데이터 값을 넘김
			user.setSo_p("003");
		}
		
		//gender_cd
		user.setDae_g("002");
		System.out.println(user.getPosition_cd());
		if(user.getGender_cd().equals("G01")) {//프론트에서 남자이면 "G01"으로 데이터 값을 넘김  
			user.setSo_g("001");
		}else if(user.getGender_cd().equals("G02")) {//프론트에서 여자이면 "G02"으로 데이터 값을 넘김
			user.setSo_g("002");
		}
		
		userService.insert(user);
	}


      //according to id delete
	@GetMapping(value="/delete/{user_id}")
	public void delete(@PathVariable("user_id") String user_id) {
		userService.delete(user_id);
	}
	//modify
	//user_id는 같아야 함
	@PostMapping(value="/update")
	public void update(@RequestBody UserVO user) {
		userService.update(user);
	}

	//according to id Query students
	@GetMapping(value="/get/{user_id}")
	public UserVO getById(@PathVariable("user_id") String user_id) {
		UserVO user = userService.getById(user_id);
		return user;
	}
	
	//All queries
	@GetMapping(value="/list")
	public List<UserVO> list(){
		return userService.list();
	}	
	
	//로그인(성공시 position_cd값 반환)
	@GetMapping(value = "/login/{user_id}&{pw}")
	public String login(@PathVariable("user_id") String user_id,@PathVariable("pw") String pw, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		
		if(getById(user_id).getPw().equals(pw)) {//로그인성공 &세션값 줌
			session.setAttribute("user_id", user_id);
			System.out.println("세션값 :" +session.getAttribute("user_id"));
			return getById(user_id).getPosition_cd();
		}
	    return "";
	}
	
	//세션값 생성 메소드
	//login할때 세션값 생성
	//나중에는 post로 user_id 값 줄것
	@GetMapping(value = "/user_id/{user_id}")
	public String user_session(@PathVariable("user_id") String user_id, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		session.setAttribute("user_id", user_id);
	
	    System.out.println("세션값 :" +session.getAttribute("user_id"));

	    return "login/user_id";
	}
	
	//세션값 확인후 지우는 메소드(test용)
	@GetMapping(value = "/session")
	public String session(HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		
	    System.out.println("user_id 세션값 :" +session.getAttribute("user_id"));
	    System.out.println("lecture_no 세션값 :" +session.getAttribute("lecture_no"));

	    // 세션에서 지운다.
	    session.invalidate();
	    System.out.println("지운후 user_id 세션값 :" +session.getAttribute("user_id"));
	    System.out.println("지운후 lecture_no 세션값 :" +session.getAttribute("lecture_no"));
	    return "login/user_id&lecture_no";
	}

	/*
	세션값 생성  session.setAttribute("이름", "값");
	가져오기  session.getAttribute("이름");
	한개삭제  session.removeAttribute("이름");
	초기화    session.invalidate();
	*/
	
	

	
	
}

package com.dictation.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dictation.service.BoardService;
import com.dictation.service.CourseService;
import com.dictation.service.EnrollService;
import com.dictation.service.LectureService;
import com.dictation.service.UserService;
import com.dictation.vo.CourseVO;
import com.dictation.vo.EnrollVO;
import com.dictation.vo.LectureVO;
import com.dictation.vo.UserVO;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/common")
public class CommonController {//공통컨트롤러
	@Autowired
	private CourseService courseService;
	@Autowired
	private EnrollService enrollService;
	@Autowired
	private LectureService lectureService;
	@Autowired
	private UserService userService;
	@Autowired
	private BoardService boardService;
	
	//회원가입
	@PostMapping(produces = "application/json;charset=UTF-8",value="/signup")
	public void insert(@RequestBody UserVO user) {
		
		//position_cd
		if(user.getPosition_cd().equals("관리자")) {  
			user.setPosition_cd("003001");
		}else if(user.getPosition_cd().equals("선생님")) {//프론트에서 선생님이면 "선생님"으로 데이터 값을 넘김
			user.setPosition_cd("003002");
		}else if(user.getPosition_cd().equals("학생")) {//프론트에서 학생이면 "학생"으로 데이터 값을 넘김
			user.setPosition_cd("003003");
		}
		
		userService.insert(user);
		
	}
	
	//로그인(성공시 UserVO값 세션값으로 저장하고, position_cd값 반환)
	//나중에는 post로 user_id 값 줄것
	@GetMapping(value = "login/{user_id}&{pw}")
	public UserVO login(@PathVariable("user_id") String user_id,@PathVariable("pw") String pw, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		UserVO user = userService.getById(user_id);
		
		if(user.equals(null) || user == null) {
		    user.setLoginYn("0");
		    return user;
		}else if(user.getPw().equals(pw)) {//로그인성공 &세션값 줌
			//if() 관리자코드, 선생님코드 로그인시 세션확인값 생성 필요
			
			session.setAttribute("user", user);//세션에 UserVO값줌
			
			UserVO user_session=(UserVO)session.getAttribute("user");
			System.out.println("아이디 세션값 :" +user_session.getUser_id());
			System.out.println("비밀번호 세션값 :" +user_session.getPw());
			System.out.println("신분코드 세션값 :" +user_session.getPosition_cd());
			user.setLoginYn("1");
			return user;
		}else {
			System.out.println("리턴값확인완료");
			session.setAttribute("login_fail", pw);
			user.setLoginYn("0");
			return user;
		}
	}
	
	//mypage(회원정보를 반환)
	@GetMapping(value = "/user/get")
	public UserVO user_getById(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		UserVO user_session=(UserVO)session.getAttribute("user");
		
		UserVO user = userService.getById(user_session.getUser_id());
		return user;
	}
	
	//회원정보 수정(mypage)
	@PostMapping(value = "/user/update")
	public void user_update(@RequestBody UserVO user) throws Exception {
		System.out.println("this is common/user/update");
		//gender_cd
		if(user.getGender_cd().equals("남자")) {//프론트에서 남자이면 "002001"으로 데이터 값을 넘김  
			user.setGender_cd("002001");
		}else if(user.getGender_cd().equals("여자")) {//프론트에서 여자이면 "002002"으로 데이터 값을 넘김
			user.setGender_cd("002002");
		}
		
		userService.update(user);
	}
	
    //according to id delete
	@GetMapping(value="/course/delete/{lecture_no}&{course_no}&{question_no}")
	public void delete(@PathVariable("lecture_no") int lecture_no, @PathVariable("course_no") int course_no, @PathVariable("question_no") int question_no) {
		CourseVO course=new CourseVO();
		course.setLecture_no(lecture_no);
		course.setCourse_no(course_no);
		course.setQuestion_no(question_no);
		courseService.delete(course);
	}
	
	//modify
	//lecture_no는 같아야 함
	@PostMapping(value="/course/update")
	public void update(@RequestBody CourseVO course) {
		courseService.update(course);
	}

	//according to id Query students
	@GetMapping(value="/course/get/{lecture_no}&{course_no}&{question_no}")
	public CourseVO getById(@PathVariable("lecture_no") int lecture_no, @PathVariable("course_no") int course_no, @PathVariable("question_no") int question_no) {
		CourseVO course2=new CourseVO();
		course2.setLecture_no(lecture_no);
		course2.setCourse_no(course_no);
		course2.setQuestion_no(question_no);
		
		CourseVO course = courseService.getById(course2);
		return course;
	}
	
	//All queries
	@PostMapping(value="/course/list")
	public List<CourseVO> list(){
		return courseService.list();
	}	
	
	
	//파일 업로드를 위함(1개의 파일)
	@CrossOrigin("*")
	@PostMapping(value="/course/fileupload")
	//@ResponseStatus(HttpStatus.CREATED)//@RequestParam("file") 
	public String upload(HttpServletRequest request, @RequestPart MultipartFile file) throws Exception {
		
		if(file.isEmpty()){ //업로드할 파일이 없을 시
            System.out.println("파일없음");
            return "";
        }else {
        	System.out.println("file 실행 !!");
    		
    		//파일 이름가져옴(FILE_NM)
    		String originalfileName = file.getOriginalFilename();
    	
    		/*
    		String fileUrl=ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(originalfileName)
                    .toUriString();
            */
    		
    			
    		//SAVE_FILE_NM
    		UUID uuid =UUID.randomUUID();
    		String save_file_nm=uuid.toString() +"_" +originalfileName;
    				
    		//파일 지정한 경로로 저장(save_file_nm 파일이름으로 저장)
    		File dest = new File("C:/Temp/" + save_file_nm);
    		file.transferTo(dest);
    		
    		System.out.println("파일이름 : "+originalfileName);
    		System.out.println("새로운 파일이름 : "+save_file_nm);
    		//System.out.println("파일경로 : "+fileUrl);
    		
    		return save_file_nm;

        }
	}
	
	
	//파일 업로드를 위함(여러개의 파일)
	@CrossOrigin("*")
	@PostMapping(value="/course/fileupload_list")
	//@ResponseStatus(HttpStatus.CREATED)//@RequestParam("file") 
	public String upload_list(HttpServletRequest request, @RequestPart List<MultipartFile> file) throws Exception {
		
		if(file.isEmpty()){ //업로드할 파일이 없을 시
            System.out.println("파일없음");
            return "";
        }else {
        	
        	for(int i=0; i<file.size(); i++) {
        		System.out.println("file 실행 !!");
	    		
	    		//파일 이름가져옴(FILE_NM)
	    		String originalfileName = file.get(i).getOriginalFilename();
	    	
	    		/*
	    		String fileUrl=ServletUriComponentsBuilder.fromCurrentContextPath()
	                    .path("/downloadFile/")
	                    .path(originalfileName)
	                    .toUriString();
	            */
	    		
	    			
	    		//SAVE_FILE_NM
	    		UUID uuid =UUID.randomUUID();
	    		String save_file_nm=uuid.toString() +"_" +originalfileName;
	    				
	    		//파일 지정한 경로로 저장(save_file_nm 파일이름으로 저장)
	    		File dest = new File("C:/Temp/" + save_file_nm);
	    		file.get(i).transferTo(dest);
	    		
	    		System.out.println("파일이름 : "+originalfileName);
	    		System.out.println("새로운 파일이름 : "+save_file_nm);
	    		//System.out.println("파일경로 : "+fileUrl);
	    		

        	}
        	
        }
		
		return "성공";
	}
	
	//modify
	//user_id는 같아야 함
	@PostMapping(value="/enroll/update")
	public void update(@RequestBody EnrollVO enroll) { //user_id, lecture_no값 필수
		enrollService.update(enroll);
	}

	
	//according to id Query students
	@GetMapping(value="/enroll/get/{user_id}")
	public EnrollVO getById(@PathVariable("user_id") String user_id) {
		EnrollVO enroll = enrollService.getById(user_id);
		return enroll;
	}
	
	//All queries
	@PostMapping(value="/enroll/list")
	public List<EnrollVO> en_list(){
		return enrollService.list();
	}
	
	//according to id delete
	//lecture를 지우면 DB에 해당lecture_no이 존재하는 모든 데이터를 지워야함(board, course, enroll, study, lecture) 
	@GetMapping(value="/lecture/delete/{lecture_no}")
	public void delete(@PathVariable("lecture_no") int lecture_no) {

		boardService.lecture_delete(lecture_no);
		enrollService.lecture_delete(lecture_no);
		courseService.lecture_delete(lecture_no);
		lectureService.delete(lecture_no);
	}
	//modify
	//lecture_no는 같아야 함
	@PostMapping(value="/lecture/update")
	public void update(@RequestBody LectureVO lecture) {
		lectureService.update(lecture);
	}

	//according to id Query students
	@GetMapping(value="/lecture/get/{lecture_no}")
	public LectureVO getById_nosession(@PathVariable("lecture_no") int lecture_no) {

		LectureVO lecture = lectureService.getById(lecture_no);
		return lecture;
	}
	
	@GetMapping(value="/lecture/get")
	public LectureVO getById(HttpServletRequest request) {
		HttpSession session = request.getSession();
		int lecture_session=(int)session.getAttribute("lecture_no");
		
		LectureVO lecture = lectureService.getById(lecture_session);
		return lecture;
	}
	
	
	//All queries
	@RequestMapping(value="/lecture/list")
	public List<LectureVO> lec_list(){
		return lectureService.list();
	}
	
	//강좌들어갈때 lecture_no 세션값 생성
	//나중에는 post로 lecture_no 값 줄것
	@GetMapping(value = "/lecture/lecture_no/{lecture_no}")
	public String lecture_no(@PathVariable("lecture_no") int lecture_no, HttpServletRequest request) throws Exception {
		
		System.out.println("lecture_no에 대한 세션값을 줌");
		
		HttpSession session = request.getSession();
		session.setAttribute("lecture_no", lecture_no);
		int lecture_session=(int)session.getAttribute("lecture_no");
		System.out.println("lecture_no 세션값 :" +lecture_session);

	    return "lecture_no";
	}
	//세션값 확인후 지우는 메소드(test용)
	@GetMapping(value = "/lecture/session")
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
	/*
	세션값 생성  session.setAttribute("이름", "값");
	가져오기  session.getAttribute("이름");
	한개삭제  session.removeAttribute("이름");
	초기화    session.invalidate();
	*/
	

}

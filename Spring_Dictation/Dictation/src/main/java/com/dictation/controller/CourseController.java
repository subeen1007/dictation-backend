package com.dictation.controller;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dictation.service.CourseService;
import com.dictation.vo.BoardVO;
import com.dictation.vo.CourseVO;
import com.dictation.vo.EnrollVO;
import com.dictation.vo.LectureVO;
import com.dictation.vo.UserVO;

import ch.qos.logback.core.pattern.parser.Parser;


@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/course")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	private LectureController lectureController;
	
	//단계번호, 문항번호, 정답은 vue에서 가져옴(CourseVO 1개씩만 insert)
    //insert user
	//선생님 화면-받아쓰기 등록버튼
	@RequestMapping(produces = "application/json;charset=UTF-8")
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void insert(@RequestParam Map<String, Object> map,@Param(value = "file") MultipartFile file, HttpServletRequest request) throws Exception{
		//프론트엔드에서 course_no, question_no, question 가져오기
		
		
		//+db에 file_nm, save_file_nm저장하기
		//course.setFile_nm(originalfileName);
		//course.setSave_file_nm(save_file_nm);
		
		
		//lecture_no을 세션값에서 가져와서 저장
		HttpSession session = request.getSession();
		int lecture_session=(int)session.getAttribute("lecture_no");
		
		int course_no=Integer.parseInt((String)map.get("course_no"));
		int question_no=Integer.parseInt((String)map.get("question_no"));
		String question=(String)map.get("question");
		String originalfileName = null;
		String save_file_nm=null;
		
		CourseVO course = new CourseVO();
		course.setLecture_no(lecture_session);
		course.setCourse_no(course_no);
		course.setQuestion_no(question_no);
		course.setQuestion(question);
		
		if(file.isEmpty()){ //업로드할 파일이 없을 시
            System.out.println("파일없음");
        }else {
        	System.out.println("file 실행 !!");
    		
    		//파일 이름가져옴(FILE_NM)
    		originalfileName = file.getOriginalFilename();
    	
    		/*
    		String fileUrl=ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/downloadFile/")
                    .path(originalfileName)
                    .toUriString();
            */
    		
    			
    		//SAVE_FILE_NM
    		UUID uuid =UUID.randomUUID();
    		save_file_nm=uuid.toString() +"_" +originalfileName;
    				
    		//파일 지정한 경로로 저장(save_file_nm 파일이름으로 저장)
    		File dest = new File("C:/Temp/" + save_file_nm);
    		file.transferTo(dest);
    		
    		System.out.println("파일이름 : "+originalfileName);
    		System.out.println("새로운 파일이름 : "+save_file_nm);
    		//System.out.println("파일경로 : "+fileUrl);
        }
		
		course.setFile_nm(originalfileName);
		course.setSave_file_nm(save_file_nm);		
		
		courseService.insert(course);
	}
	
	//선생님화면- 받아쓰기 수정버튼
	@PostMapping(value="/dic_modify")
	public void dic_modify(@RequestParam Map<String, Object> map,@Param(value = "file_nm") MultipartFile file_nm, HttpServletRequest request) throws Exception{
		//프론트엔드에서 course_no, question_no, question, file_nm, change_file 가져오기
		
		//lecture_no을 세션값에서 가져와서 저장
		HttpSession session = request.getSession();
		int lecture_session=(int)session.getAttribute("lecture_no");
		
		System.out.println("111111");
		int course_no=Integer.parseInt((String)map.get("course_no"));
		int question_no=Integer.parseInt((String)map.get("question_no"));
		String question=(String)map.get("question");
		boolean change_file2=Boolean.parseBoolean((String)map.get("change_file"));
		String originalfileName = null;
		String save_file_nm=null;
		
		CourseVO course1 = new CourseVO();
		course1.setLecture_no(lecture_session);
		course1.setCourse_no(course_no);
		course1.setQuestion_no(question_no);
		course1.setQuestion(question);
		
		System.out.println("22222");
		courseService.dic_modify_question(course1);
		System.out.println("33333");
		if(change_file2==true) {//파일이 수정됐을떄
			if(file_nm.isEmpty()){ //업로드할 파일이 없을 시
	            System.out.println("파일없음");
	        }else {
	        	System.out.println("file 실행 !!");
	    		
	    		//파일 이름가져옴(FILE_NM)
	    		originalfileName = file_nm.getOriginalFilename();
	    			
	    		//SAVE_FILE_NM
	    		UUID uuid =UUID.randomUUID();
	    		save_file_nm=uuid.toString() +"_" +originalfileName;
	    				
	    		//파일 지정한 경로로 저장(save_file_nm 파일이름으로 저장)
	    		File dest = new File("C:/Temp/" + save_file_nm);
	    		file_nm.transferTo(dest);
	    		
	    		System.out.println("파일이름 : "+originalfileName);
	    		System.out.println("새로운 파일이름 : "+save_file_nm);
	    		//System.out.println("파일경로 : "+fileUrl);
	        }
			CourseVO course2 = new CourseVO();
			course2.setLecture_no(lecture_session);
			course2.setCourse_no(course_no);
			course2.setQuestion_no(question_no);
			course2.setFile_nm(originalfileName);
			course2.setSave_file_nm(save_file_nm);
			courseService.dic_modify_file(course2);
		}
	}


    //according to id delete
	@GetMapping(value="/delete/{lecture_no}&{course_no}&{question_no}")
	public void delete(@PathVariable("lecture_no") int lecture_no, @PathVariable("course_no") int course_no, @PathVariable("question_no") int question_no) {
		CourseVO course=new CourseVO();
		course.setLecture_no(lecture_no);
		course.setCourse_no(course_no);
		course.setQuestion_no(question_no);
		courseService.delete(course);
	}
	
	//modify
	//lecture_no는 같아야 함
	@PostMapping(value="/update")
	public void update(@RequestBody CourseVO course) {
		courseService.update(course);
	}

	//according to id Query students
	@GetMapping(value="/get/{lecture_no}&{course_no}&{question_no}")
	public CourseVO getById(@PathVariable("lecture_no") int lecture_no, @PathVariable("course_no") int course_no, @PathVariable("question_no") int question_no) {
		CourseVO course2=new CourseVO();
		course2.setLecture_no(lecture_no);
		course2.setCourse_no(course_no);
		course2.setQuestion_no(question_no);
		
		CourseVO course = courseService.getById(course2);
		return course;
	}
	
	//All queries
	@PostMapping(value="/list")
	public List<CourseVO> list(){
		return courseService.list();
	}	
	
	
	//정답비교(학생답을 매개변수로 넣음)
	@PostMapping(value="/answer")
	public boolean answer(@RequestBody CourseVO course) {
		//원래는 세션값에서 no, seq_no가져와야 함
		String question = course.getQuestion();
		CourseVO course2 =courseService.getById(course);
		
		if(question.equals(course2.getQuestion())) {
			return true;
		}else {
			return false;
		}
		
	}
	
	
	//파일 업로드를 위함(1개의 파일)
	@CrossOrigin("*")
	@PostMapping(value="/fileupload")
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
	
	//학생이 받아쓰기를 진행할때 음성파일 클릭시 음성나오도록 구현
	//.wav에서 test성공, .mp3에선 작동안함(+post로 경로값 받아올것) 
	@GetMapping(value="/sound")
	public void soundPlay() {
		File file = new File("C:\\Users\\subin\\Desktop\\딕테이션_프로젝트\\각종문서\\(남성)흐흐흐흐.wav");
		AudioInputStream audioInputStream =null;
		SourceDataLine auline =null;
		
		try {
			audioInputStream=AudioSystem.getAudioInputStream(file);
		}catch(UnsupportedAudioFileException e1) {
			e1.printStackTrace();
			return;
		}catch(IOException e1) {
			e1.printStackTrace();
			return;
		}
		AudioFormat format = audioInputStream.getFormat();
		DataLine.Info info= new DataLine.Info(SourceDataLine.class, format);
		try {
			auline=(SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		}catch(LineUnavailableException e) {
			e.printStackTrace();
			return;
		}catch(Exception e) {
			e.printStackTrace();
			return;
		}
		auline.start();
		int nBytesRead=0;
		final int EXTERNAL_BUFFER_SIZE=524288;
		byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
		
		try {
			while(nBytesRead !=-1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if(nBytesRead >=0) {
					auline.write(abData, 0, nBytesRead);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
			return;
		}finally {
			auline.drain();
			auline.close();
		}
	}
	
	
	
	/*
	//음성파일 바로 들려줄때 쓰기    
    @GetMapping(path = "/download/{save_file_nm}")
    public ResponseEntity download_file(@PathVariable("save_file_nm") String save_file_nm) { 
    	String file_path="C:/Temp/";
	    String file_name= save_file_nm;
	    
	    
        File file = new File(file_path+File.separator+file_name);
        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+file_name);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = null;

        try {
            resource = new ByteArrayResource(Files.readAllBytes(path));
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        return ResponseEntity.ok()
            .headers(header)
            .contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream"))
            .body(resource);
    }*/

	
	/*
	@PostMapping("/upload")
	public ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path path = Paths.get(fileBasePath + fileName);
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/files/download/")
				.path(fileName)
				.toUriString();
		return ResponseEntity.ok(fileDownloadUri);
	}

	*/
	
	
	
	/*
	@PostMapping(value="/fileupload")
	@ResponseStatus(HttpStatus.CREATED)
	public List<String> upload(@RequestPart List<MultipartFile> files) throws Exception {
		System.out.println("file 실행 !!");
		List<String> list = new ArrayList<>();
		for (MultipartFile file : files) {
			String originalfileName = file.getOriginalFilename();
			File dest = new File("C:/Image/" + originalfileName);
			file.transferTo(dest);

		}
		return list;
	}
	*/
	
	
	
	//파일 업로드를 위함(여러개의 파일)
	@CrossOrigin("*")
	@PostMapping(value="/fileupload_list")
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
	
	//강좌에 대한 받아쓰기가 등록되어 있는지 여부를 알려줌
	//선생님 화면-받아쓰기 등록: 디비에 강좌가 있으면 수정버튼이 뜨고, 강좌가 없으면 등록버튼이 뜨게하기 위함
	//존재하면 1, 존재안하면 0
	@GetMapping(value="/dic_empty/{course_no}")
	public Integer dic_empty(@PathVariable("course_no") int course_no, HttpServletRequest request) {
		CourseVO course2=new CourseVO();
		
		HttpSession session = request.getSession();
		int lecture_session=(int)session.getAttribute("lecture_no");
		course2.setLecture_no(lecture_session);
		course2.setCourse_no(course_no);

		Integer course = courseService.dic_empty(course2);
		return course;
	}
	
	//선생님- 받아쓰기 정답 가져옴
	@GetMapping(value="/dic_answers/{course_no}")
	public List<CourseVO> dic_answers(@PathVariable("course_no") int course_no, HttpServletRequest request) {
		CourseVO course2=new CourseVO();
		
		HttpSession session = request.getSession();
		int lecture_session=(int)session.getAttribute("lecture_no");
		course2.setLecture_no(lecture_session);
		course2.setCourse_no(course_no);

		return courseService.dic_answers(course2);
	}
	

}

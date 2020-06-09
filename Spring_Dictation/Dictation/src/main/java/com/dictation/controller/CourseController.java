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
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dictation.service.CourseService;
import com.dictation.vo.CourseVO;


@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/course")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	private LectureController lectureController;
	
	//단계번호, 문항번호는 vue에서 가져옴
    //insert user
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void insert(@RequestBody CourseVO course,HttpServletRequest request) {
		
		//lecture_no
		HttpSession session = request.getSession();
		int lecture_session=(int)session.getAttribute("lecture_no");		
		course.setLecture_no(lecture_session);
		
		courseService.insert(course);
	}


      //according to id delete
	@GetMapping(value="/delete/{lecture_no}&{course_no}&{question_no}")
	public void delete(@PathVariable("course") CourseVO course) {
		courseService.delete(course);
	}
	//modify
	//lecture_no는 같아야 함
	@PostMapping(value="/update")
	public void update(@RequestBody CourseVO course) {
		courseService.update(course);
	}

	//according to id Query students
	@GetMapping(value="/get/{lecture_no}")
	public CourseVO getById(@PathVariable("lecture_no") int lecture_no) {
		CourseVO course = courseService.getById(lecture_no);
		return course;
	}
	
	//All queries
	@PostMapping(value="/list")
	public List<CourseVO> list(){
		return courseService.list();
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
	
	//.wav에서 test성공, .mp3에선 작동안함(+post로 경로값 받아올것) 
	@GetMapping(value="/sound")
	public void soundPlay() {
		File file = new File("C:\\Users\\subin\\Desktop\\Spring_소리파일\\(남성)흐흐흐흐.wav");
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
}

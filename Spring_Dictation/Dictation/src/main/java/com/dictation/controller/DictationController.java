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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.dictation.service.CourseService;
import com.dictation.service.EnrollService;
import com.dictation.service.StudyService;
import com.dictation.vo.CourseVO;
import com.dictation.vo.EnrollVO;
import com.dictation.vo.LectureVO;
import com.dictation.vo.StudyVO;
import com.dictation.vo.UserVO;

@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/dictation")
public class DictationController {//받아쓰기 컨트롤러
   @Autowired
   private CourseService courseService;
   @Autowired
   private EnrollService enrollService;
   @Autowired
   private StudyService studyService;

   //선생님화면 받아쓰기 완료버튼 누름
   @GetMapping(value="/course/finish_yes/{course_no}")
   public void finish_yes(@PathVariable("course_no") int course_no, HttpServletRequest request) {
      CourseVO course = new CourseVO();
      course.setCourse_no(course_no);
      
      HttpSession session = request.getSession();
      int lecture_session=(int)session.getAttribute("lecture_no");
      course.setLecture_no(lecture_session);
      course.setFinish_yn("1");
      
      courseService.finish_yes(course);
   }
   
   //받아쓰기 정답비교(학생답을 매개변수로 넣음)
   @PostMapping(value="/enroll/answer")
   public boolean[] answer(@RequestBody CourseVO[] courseList, HttpServletRequest request) throws Exception {
      //quesion, lecture_no, course_no, quesion_no가져와야 함
      //원래 lecture_no, user_id는 세션값으로 가져와야함(일단 user_id는 임의로 만듬)

      //세션값에서 학생아이디 가져옴
      HttpSession session = request.getSession();
      UserVO user_session=(UserVO)session.getAttribute("user");
      String student_id = user_session.getUser_id();
      int lecture_session=(int)session.getAttribute("lecture_no");
      
      String question;
      CourseVO course;
      boolean[] answer=new boolean[courseList.length];

      for(int i=0; i<courseList.length; i++) {
         question=courseList[i].getQuestion();
         courseList[i].setLecture_no(lecture_session);
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

      int score=0;
      for(int i=0; i<answer.length; i++) {
         if(answer[i]==true) {
            score+=10;
         }
      }
   
      //enroll.setPass_course_no(score);//일단은 점수로 표기(추후에 단계로 표시할 예정)
      
      //enroll.setUser_id("vv");//임시 아이디(user_id가 기존 enroll에 있어야함)
      //enrollService.update(enroll);
      return answer;
      
   }
   
   //강좌에 대해 받아쓰기 완료처리된 단계들만 반환 
   @GetMapping(value="/course/finish_yes_cl")
   public List<Integer> finish_yes_cl(HttpServletRequest request) {
      
      HttpSession session = request.getSession();
	  int lecture_session=(int)session.getAttribute("lecture_no");
      
      return courseService.finish_yes_cl(lecture_session);
   }
   
   //학생의 받아쓰기 데이터를 Study테이블에 insert
   @PostMapping(value="/study/insert")
   public void study_insert(@RequestBody StudyVO study, HttpServletRequest request) {   
      HttpSession session = request.getSession();
      int lecture_session=(int)session.getAttribute("lecture_no");
      studyService.insert(study);
   }
   
   //학생이 받아쓰기 진행할때 음성파일 재생을 위한 음성파일url
   @PostMapping(value="/course/audio")
   public String audio(@Param(value = "course_no") int course_no, @Param(value = "question_no") int question_no,
         HttpServletRequest request) {
   
      HttpSession session = request.getSession();
      int lecture_session=(int)session.getAttribute("lecture_no");
      
      CourseVO course=new CourseVO();
      course.setLecture_no(lecture_session);
      course.setCourse_no(course_no);
      course.setQuestion_no(question_no);
      
      String savefile_nm=courseService.getById(course).getSave_file_nm();
      String url="C:/Temp/"+savefile_nm; // "C:/Temp/"은 추후에 서버에 파일저장 경로로 바꿀것
      //System.out.println("url주소??"+url);
      return url;
   }
   
   //학생이 받아쓰기를 진행할때 음성파일 클릭시 음성나오도록 구현
   //.wav에서 test성공, .mp3에선 작동안함(+post로 경로값 받아올것) 
   @GetMapping(value="/course/sound")
   public void soundPlay() {
      File file = new File("C:\\Users\\subin\\Desktop\\딕테이션_프로젝트\\각종문서\\(남성)흐흐흐흐1.wav");
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
    @GetMapping(path = "/course/download/{save_file_nm}")
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

}
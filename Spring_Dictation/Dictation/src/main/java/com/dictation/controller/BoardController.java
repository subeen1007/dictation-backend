package com.dictation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dictation.service.BoardService;
import com.dictation.vo.BoardVO;


@CrossOrigin("*")
@RestController
@RequestMapping(value="/api/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	
    //insert user
	@PostMapping(produces = "application/json;charset=UTF-8")
	public void insert(@RequestBody BoardVO board,HttpServletRequest request) {
		int lecture_no;
		String so_b = null;
		String no = null;
		
		//lecture_no
		//HttpSession session = request.getSession();
		//lecture_no=(int)session.getAttribute("lecture_no");
		//board.setLecture_no(lecture_no);
		lecture_no=board.getLecture_no();//임시
		
		//board_cd, no
		board.setDae_b("006");
		if(board.getBoard_cd().equals("001")) {//프론트에서 공지사항이면 001로 데이터 값을 넘김  
			so_b="001";
			no=lecture_no+"001";
		}else if(board.getBoard_cd().equals("002")) {//프론트에서 학습자료이면 002로 데이터 값을 넘김
			so_b="002";
			no=lecture_no+"002";
		}else if(board.getBoard_cd().equals("002")) {////프론트에서 Q&A이면 003로 데이터 값을 넘김
			so_b="003";
			no=lecture_no+"003";
		}
		board.setSo_b(so_b);
		board.setNo(Long.valueOf(no));
		board.setSeq_no(Integer.valueOf(no));
		
		boardService.insert(board);
	}


      //according to id delete
	@GetMapping(value="/delete/{no}/{seq_no}")
	public void delete(@PathVariable("no") long no, @PathVariable("seq_no") int seq_no) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("seq_no", seq_no);
		boardService.delete(map);
		boardService.after_delete(map);
	}
	
	//modify
	//seq_no는 같아야 함
	@PostMapping(value="/update")
	public void update(@RequestBody BoardVO board) {
		boardService.update(board);
	}

	//according to id Query students
	@GetMapping(value="/get/{no}&{seq_no}")
	public BoardVO getById(@PathVariable("no") long no, @PathVariable("seq_no") int seq_no) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("no", no);
		map.put("seq_no", seq_no);		
		BoardVO board = boardService.getById(map);
		return board;
	}
	
	//All queries
	@PostMapping(value="/list")
	public List<BoardVO> list(){
		return boardService.list();
	}
	

	@PostMapping("/upload")
	public String uploadToLocalFileSystem(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception{
		//FILE_NM
		String originalfileName = file.getOriginalFilename();
		//SAVE_FILE_NM
		UUID uuid =UUID.randomUUID();
		String save_file_nm=uuid.toString() +"_" +originalfileName;
				


		 
		// 실제 구동할때(서버에 파일저장????)
		// http://localhost:3003/files/download/a.txt처럼 서버에 저장할때 사용
		/*String fileDownloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/files/download/")
				.toUriString();
		*/
		
		
		/*
		//fileDownloadUrl
		String root_path = request.getSession().getServletContext().getRealPath("/");  
		String attach_path = "resources/upload/";
		String fileDownloadUrl=root_path + attach_path;
		*/
		
		String fileDownloadUrl="C:/Temp/";
		System.out.println(fileDownloadUrl);

		File dest = new File(fileDownloadUrl + save_file_nm);
		file.transferTo(dest);
		
		
		return save_file_nm;
		//return ResponseEntity.ok(fileDownloadUri);
	}
	

	@GetMapping(path = "/download/{save_file_nm}")
    public void download_file(@PathVariable("save_file_nm") String save_file_nm, HttpServletRequest request,HttpServletResponse response) throws IOException { 
    	String file_path="C:/Temp/";
    	/*
		String file_path = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/files/download/")
				.toUriString();
		*/
		
	    String file_name= save_file_nm; //저장된 파일이름
	    String fileUrl=file_path+file_name; //저장된 파일 url
	    
	    File file = new File(fileUrl);
	    if (!file.exists()) {
            return;
        }
	    
	    String origin_file_nm; //파일 원래이름
	    System.out.println(file_name);
	    if((origin_file_nm=boardService.getFileNm(file_name))==null) { //디비에 원래 파일명이 없을때
	    	origin_file_nm=file_name;
	    }
	    System.out.println(origin_file_nm);
	    
	    
	    response.setContentType("application/octer-stream");
        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + origin_file_nm + "\"");
        try {
            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
 
            int ncount = 0;
            byte[] bytes = new byte[512];
 
            while ((ncount = fis.read(bytes)) != -1 ) {
                os.write(bytes, 0, ncount);
            }
            fis.close();
            os.close();
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
        } catch (IOException ex) {
            System.out.println("IOException");
        }
    
    }
	
	
	
	/*
	@PostMapping("/multi-upload")
	public ResponseEntity multiUpload(@RequestParam("files") MultipartFile[] files) {
		List<Object> fileDownloadUrls = new ArrayList<>();
		Arrays.asList(files)
				.stream()
				.forEach(file -> fileDownloadUrls.add(uploadToLocalFileSystem(file).getBody()));
		return ResponseEntity.ok(fileDownloadUrls);
	}
	*/

}

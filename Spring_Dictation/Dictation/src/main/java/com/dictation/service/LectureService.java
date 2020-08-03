package com.dictation.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.LectureMapper;
import com.dictation.vo.LectureVO;

@Service
public class LectureService {
	
	@Autowired
	private LectureMapper lectureMapper;
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(LectureVO lecture) {
		lectureMapper.insert(lecture);
	}

	//according to id delete
	public void delete(int lecture_no) {
		lectureMapper.delete(lecture_no);
	}

	//according to user Of id modify
	public void update(LectureVO lecture) {
		lectureMapper.update(lecture);
	}
	
	public Object lecture_no_search(int lecture_no) {
		return lectureMapper.lecture_no_search(lecture_no);
	}
	
	//선생님 본인이 개설한 강좌목록 띄우기 위한 코드
	public List<LectureVO> teacher_mylec(String user_id) {
		return lectureMapper.teacher_mylec(user_id);
	}
	
	//학생화면 전체강좌 리스트에 강좌신청여부
	public List<LectureVO> student_lec_list(String user_id) {
		return lectureMapper.student_lec_list(user_id);
	}
	
	//학생 본인이 수강신청해서 승인된 강좌목록 띄우기 위한 코드
	public List<LectureVO> student_mylec(String user_id){
		return lectureMapper.student_mylec(user_id);
	}

	//according to id query
	public LectureVO getById(int lecture_no) {
		return lectureMapper.getById(lecture_no);
	}

	//All queries
	public List<LectureVO> list(){
		return lectureMapper.list();
	}
	

}

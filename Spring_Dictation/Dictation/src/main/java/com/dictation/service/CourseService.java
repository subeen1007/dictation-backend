package com.dictation.service;

import java.util.HashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.CourseMapper;
import com.dictation.vo.CourseVO;

@Service
public class CourseService {
	
	@Autowired
	private CourseMapper courseMapper;
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(CourseVO course) {
		courseMapper.insert(course);
	}

	//according to id delete
	public void delete(CourseVO course) {
		courseMapper.delete(course);
	}

	//선생님이 강좌삭제했을때
	public void lecture_delete(int lecture_no) {
		courseMapper.lecture_delete(lecture_no);
	}
	
	//according to user Of id modify
	public void update(CourseVO course) {
		courseMapper.update(course);
	}
	
	//받아쓰기 정답 수정
	public void dic_modify_question(CourseVO course) {
		courseMapper.dic_modify_question(course);
	}
	
	//받아쓰기 파일 수정
	public void dic_modify_file(CourseVO course) {
		courseMapper.dic_modify_file(course);
	}

	//선생님 받아쓰기 완료버튼
	public void finish_yes(CourseVO course) {
		courseMapper.finish_yes(course);
	}
	
	//according to id query
	public CourseVO getById(CourseVO course) {
		return courseMapper.getById(course);
	}

	//All queries
	public List<CourseVO> list(){
		return courseMapper.list();
	}
	
	//강좌에 대해 받아쓰기 완료처리된 단계들만 반환 
	public List<Integer> finish_yes_cl(int lecture_no){
		return courseMapper.finish_yes_cl(lecture_no);
	}
	
	//search file_nm for file download
	public String getFileNm(String save_file_nm) {
		return courseMapper.getFileNm(save_file_nm);
	}
	
	//선생님화면 등록한 받아쓰기 최대단계
	public int max_dic_course(int lecture_no) {
		return courseMapper.max_dic_course(lecture_no);
	}
	
	//선생님- 받아쓰기 정답 가져옴
	public List<CourseVO> dic_answers(CourseVO course){
		return courseMapper.dic_answers(course);
	}
	

}

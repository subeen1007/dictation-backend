package com.dictation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.dictation.vo.LectureVO;

@Repository
@Mapper
public interface LectureMapper {	//lectureMapper.xml에서 이름,명령어 지정	
	
	//insert
	public void insert(LectureVO lecture);

	//according to id delete
	public void delete(int lecture_no);	

	//according to user Of id modify
	public void update(LectureVO lecture);

	//강좌번호 생성할때 중복되는 강좌가 있는지 검사하는코드
	public Object lecture_no_search(int lecture_no);
	
	//선생님 본인이 개설한 강좌목록 띄우기 위한 코드
	public List<LectureVO> teacher_mylec(String user_id);
	
	//학생화면 전체강좌 리스트에 강좌신청여부
	public List<LectureVO> student_lec_list(String user_id);
	
	//학생 본인이 수강신청해서 승인된 강좌목록 띄우기 위한 코드
	public List<LectureVO> student_mylec(String user_id);
	
	//according to id query
	public LectureVO getById(int lecture_no);

	//All queries
	public List<LectureVO> list();

	
}

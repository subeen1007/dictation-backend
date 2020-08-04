package com.dictation.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.dictation.vo.CourseVO;

@Repository
@Mapper
public interface CourseMapper {	//courseMapper.xml에서 이름,명령어 지정	
	
	//insert
	public void insert(CourseVO course);

	//according to id delete
	public void delete(CourseVO course);	//일단은 lecture_no을 통해 삭제가능하도록 했음.
	
	//선생님이 강좌삭제했을때
	public void lecture_delete(int lecture_no);

	//according to user Of id modify
	public void update(CourseVO course);
	
	//받아쓰기 정답 수정
	public void dic_modify_question(CourseVO course);
	//받아쓰기 파일 수정
	public void dic_modify_file(CourseVO course);

	//according to id query
	public CourseVO getById(CourseVO course);	//일단은 lecture_no으로

	//All queries
	public List<CourseVO> list();
	
	//search file_nm for file download
	public String getFileNm(String save_file_nm);

	//강좌에 대한 받아쓰기가 등록되어 있는지 여부를 알려줌
	public Integer dic_empty(CourseVO course);
	
	//선생님- 받아쓰기 정답 가져옴
	public List<CourseVO> dic_answers(CourseVO course);
	
}

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
	public void delete(String lecture_no);	

	//according to user Of id modify
	public void update(LectureVO lecture);

	//according to id query
	public LectureVO getById(String lecture_no);

	//All queries
	public List<LectureVO> list();

	
}

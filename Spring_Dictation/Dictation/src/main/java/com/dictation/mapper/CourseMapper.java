package com.dictation.mapper;

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

	//according to user Of id modify
	public void update(CourseVO course);

	//according to id query
	public CourseVO getById(int lecture_no);	//일단은 lecture_no으로

	//All queries
	public List<CourseVO> list();
	
	//search file_nm for file download
	public String getFileNm(String save_file_nm);

	
}

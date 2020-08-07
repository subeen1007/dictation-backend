package com.dictation.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.dictation.vo.StudyVO;

@Repository
@Mapper
public interface StudyMapper {	//studyMapper.xml에서 이름,명령어 지정	
	
	//insert
	public void insert(StudyVO study);
	
	//according to user Of id modify
	public void update(StudyVO study);

	//according to id query
	public StudyVO getById(int seq_no);

	//All queries
	public List<StudyVO> list();
		
}

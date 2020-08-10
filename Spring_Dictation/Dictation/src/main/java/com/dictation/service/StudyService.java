package com.dictation.service;

import java.util.HashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.StudyMapper;
import com.dictation.vo.StudyVO;

@Service
public class StudyService {
	
	@Autowired
	private StudyMapper studyMapper;
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(StudyVO study) {
		studyMapper.insert(study);
	}	

	//according to user Of id modify
	public void update(StudyVO study) {
		studyMapper.update(study);
	}

	//according to id query
	public StudyVO getById(int seq_no) {
		return studyMapper.getById(seq_no);
	}

	//All queries
	public List<StudyVO> list(){
		return studyMapper.list();
	}

}

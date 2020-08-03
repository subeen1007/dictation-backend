package com.dictation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.dictation.vo.EnrollVO;
import com.dictation.vo.UserVO;

@Repository
@Mapper
public interface EnrollMapper {	//enrollMapper.xml에서 이름,명령어 지정	
	
	//insert
	public void insert(EnrollVO enroll);

	//according to id delete
	public void delete(EnrollVO enroll);	//일단은 user_id 넣음.
	
	//선생님이 강좌삭제했을때
	public void lecture_delete(int lecture_no);

	//according to user Of id modify
	public void update(EnrollVO enroll);
	
	//선생님이 학생을 승인시켜줌
	public void update_request(EnrollVO enroll);

	//according to id query
	public EnrollVO getById(String user_id);	//일단은 user_id 넣음.

	//All queries
	public List<EnrollVO> list();
	
	//신청현황 리스트
	public List<UserVO> list_request(int lecture_no);

	
}

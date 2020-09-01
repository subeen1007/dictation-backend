package com.dictation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.dictation.vo.UserVO;

@Repository
@Mapper
public interface UserMapper {	//userMapper.xml에서 이름,명령어 지정	
	
	//insert
	public void insert(UserVO user);

	//according to id delete
	public void delete(String user_id);

	//according to user Of id modify
	public void update(UserVO user);

	//user_id 생성할때 중복되는 user_id가 있는지 검사하는코드(선생님 엑셀파일 업로드에 사용)
	public String userid_no_search(String user_id);
	
	//according to id query
	public UserVO getById(String user_id);

	//All queries
	public List<UserVO> list();

	
}

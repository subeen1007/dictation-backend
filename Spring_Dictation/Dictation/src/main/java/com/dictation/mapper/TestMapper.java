package com.dictation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.dictation.vo.TestVO;

@Repository
@Mapper
public interface TestMapper {	//testMapper.xml에서 이름,명령어 지정
	public List<TestVO> selectTest();
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(TestVO user);

	//according to id delete
	public void delete(String user_id);

	//according to user Of id modify
	public void update(TestVO user);

	//according to id query
	public TestVO getById(String user_id);

	//All queries
	public List<TestVO> list();

	
}

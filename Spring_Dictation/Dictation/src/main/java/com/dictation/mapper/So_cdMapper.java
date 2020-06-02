package com.dictation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.dictation.vo.So_cdVO;



@Repository
@Mapper
public interface So_cdMapper {	//userMapper.xml에서 이름,명령어 지정	
	
	//insert
	public void insert(So_cdVO so_cd);

	//according to id delete
	public void delete(String so_cd);	//일단은 하위 일련번호를 넣음. 아마 일련번호, 강좌번호를 추가할 수도 있음

	//according to user Of id modify
	public void update(So_cdVO so_cd);

	//according to id query
	public So_cdVO getById(String so_cd);	//일단 하위일련번호로

	//All queries
	public List<So_cdVO> list();

	
}

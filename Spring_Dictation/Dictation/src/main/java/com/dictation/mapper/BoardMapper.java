package com.dictation.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.dictation.vo.BoardVO;

@Repository
@Mapper
public interface BoardMapper {	//boardMapper.xml에서 이름,명령어 지정	
	
	//insert
	public void insert(BoardVO board);

	//according to id delete
	public void delete(String seq_no);	//일단은 하위 일련번호를 넣음. 아마 일련번호, 강좌번호를 추가할 수도 있음

	//according to user Of id modify
	public void update(BoardVO board);

	//according to id query
	public BoardVO getById(String seq_no);	//일단 하위일련번호로

	//All queries
	public List<BoardVO> list();

	
}

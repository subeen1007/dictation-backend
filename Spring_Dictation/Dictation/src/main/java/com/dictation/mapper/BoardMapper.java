package com.dictation.mapper;

import java.util.HashMap;
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
	public void delete(HashMap<String, Object> map);

	//update after delete
	public void after_delete(HashMap<String, Object> map);
	
	//according to user Of id modify
	public void update(BoardVO board);

	//according to id query
	public BoardVO getById(HashMap<String, Object> map);

	//All queries
	public List<BoardVO> list();
	
	//search file_nm for file download
	public String getFileNm(String save_file_nm);

	
}

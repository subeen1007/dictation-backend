package com.dictation.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dictation.mapper.Dae_cdMapper;
import com.dictation.vo.Dae_cdVO;


@Service
public class Dae_cdService {
	
	@Autowired
	private Dae_cdMapper dae_cdMapper;
	
	
	//##insert,delete,update,getById,list##
	//insert
	public void insert(Dae_cdVO dae_cd) {
		dae_cdMapper.insert(dae_cd);
	}

	//according to id delete
	public void delete(String dae_cd) {
		dae_cdMapper.delete(dae_cd);
	}

	//according to user Of id modify
	public void update(Dae_cdVO dae_cd) {
		dae_cdMapper.update(dae_cd);
	}

	//according to id query
	public Dae_cdVO getById(String dae_cd) {
		return dae_cdMapper.getById(dae_cd);
	}

	//All queries
	public List<Dae_cdVO> list(){
		return dae_cdMapper.list();
	}
	

}

package com.dictation.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVO {
	

	
	private String user_id;
	private String pw;
	private String school_cd;
	private String position_cd;
	private String kor_nm;
	private String end_nm;
	private int grade;
	private String ban;
	private String birth_dt;
	private String cel_phone_no;
	private String hom_phone_no;
	private String gender_cd;
	private String email;
	
	//position_cd 에서 dae_cd와 so_cd값을 합치기 위한 변수
	private String dae_p;
	private String so_p;
	//gender_cd 에서 dae_cd와 so_cd값을 합치기 위한 변수
	private String dae_g;
	private String so_g;
		
}

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
	private String input_id;
	private Date input_date;
	private String update_id;
	private Date update_date;
	private String loginYn;
	
	//신청현황 출력할때 필요한 변수(enroll변수)
	private String register_dt;
	private String approval_cd;
	private String approval_dt;
	private int pass_course_no;
	private int study_time;
			
}

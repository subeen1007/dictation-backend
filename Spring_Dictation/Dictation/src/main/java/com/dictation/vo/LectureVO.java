package com.dictation.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LectureVO {
	private String year;
	private String term;
	private int lecture_no;
	private String lecture_nm;
	private String school_cd;
	private int grade;
	private String ban;
	private String enroll_st_dt;
	private String enroll_ed_dt;
	private int max_cnt;
	private String teacher_id;
	private String input_id;
	private Date input_date;
	private String update_id;
	private Date update_date;
	
}

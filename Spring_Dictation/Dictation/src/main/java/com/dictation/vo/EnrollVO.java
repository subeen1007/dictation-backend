package com.dictation.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollVO {
	private String year;
	private String term;
	private int lecture_no;
	private String user_id;
	private String register_dt;
	private String approval_cd;
	private String approval_dt;
	private int pass_course_no;
	private int study_time;
	private String input_id;
	private Date input_date;
	private String update_id;
	private Date update_date;
	
}

package com.dictation.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyVO {
	private String year;
	private String term;
	private int lecture_no;
	private String user_id;
	private int course_no;
	private int question_no;
	private int seq_no;
	private String answer;
	private Date system_time;
	private String correct_yn;
	private String update_id;
	private Date update_date;
	
	
}

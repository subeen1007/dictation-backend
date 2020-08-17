package com.dictation.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentVO {
	private String year;
	private String term;
	private int lecture_no;
	private String board_cd;
	private long no;
	private int seq_no;
	private String user_id;
	private String content;
	private String input_id;
	private Date input_date;
	private String update_id;
	private Date update_date;
}

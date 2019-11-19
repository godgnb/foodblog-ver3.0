package com.food.domain;

import java.util.Date;

import lombok.Data;

@Data
public class TipBoardVO {
	private int num;
	private String id;
	private String passwd;
	private String subject;
	private String content;
	private int readcount;
	private int commcount;
	private String ip;
	private Date regDate;
	
} // TipBoardVO

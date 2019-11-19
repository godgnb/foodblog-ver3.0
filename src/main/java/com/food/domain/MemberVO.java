package com.food.domain;

import java.util.Date;

import lombok.Data;

@Data
public class MemberVO {
	private String id;
	private String passwd;
	private String name;
	private String phone;
	private String email;
	private Date regDate;
	
} // // MemberVO

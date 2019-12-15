package com.food.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.MemberVO;
import com.food.mapper.MemberMapper;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private JavaMailSender mailSender;
	
	// 회원가입 메소드
	public int insertMember(MemberVO memberVO) {
		return memberMapper.insertMember(memberVO);
	} // insertMember method
	
	
	// 해당 유저 비밀번호 확인 메소드
	public int userCheck(String id, String passwd) {
		int check = -1;
		
		// 회원 정보 가져오기
		MemberVO memberVO = memberMapper.getMember(id);
		if (memberVO != null) {
			if (passwd.equals(memberVO.getPasswd())) {
				check = 1;
			} else {
				check = 0;
			}
		} else {
			check = -1;
		}
		return check;
	} // userCheck
	
	
	// 아이디 중복여부 확인 메소드
	public boolean isIdDupCheck(String id) {
		boolean isIdDupCheck = false;
		// 아이디중복 확인
		int count = memberMapper.isIdDupCheck(id);
		if (count > 0) {
			isIdDupCheck = true;
		}
		return isIdDupCheck;
	} // isIdDupCheck method
	
	
	// 이메일 인증번호 보내는 메소드
	public void sendEmail(String from, String to, String subject, String text) {
		MimeMessage message = mailSender.createMimeMessage();
		
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			
			messageHelper.setFrom(from);
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(text);
			
			mailSender.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	} // sendEmail method
	
	
	// 이메일 인증번호 확인 메소드
	public boolean emailCheck(String inputNumber, String authCode) {
		boolean result = false;
		
		if(inputNumber.equals(authCode)) {
			result = true;
		}
		
		return result;
	} // emailCheck method
	
	
	// 회원정보 불러오는 메소드 호출
	public MemberVO getMember(String id) {
		// 회원 정보 가져오기
		MemberVO memberVO = memberMapper.getMember(id);
		
		return memberVO;
	} // getMember method
	
	
	// 회원정보 삭제하기 메소드
	public void deleteMember(String id) {
		// 회원 정보 삭제
		memberMapper.deleteMember(id);
	} // deleteMember method
	
	
	// 다중회원정보 삭제하기 메소드
	public void deleteMembers(List<String> rowCheckList) {
		System.out.println("rowCheckList S: " + rowCheckList);
		// 다중 회원 정보 삭제
		memberMapper.deleteMembers(rowCheckList);
	} // deleteMembers method
	
	
	// 회원정보 수정하기 메소드
	public void updateMember(MemberVO memberVO) {
		// 회원 정보 수정
		memberMapper.updateMember(memberVO);
	} // updateMember method
	
	
	// 전체회원 목록 가져오기 메소드
	public List<MemberVO> getMembers(int startRow, int pageSize, String search) {
		// 전체 회원 목록 가져오기
		List<MemberVO> list = memberMapper.getMembers(startRow, pageSize, search);
		
		return list;
	} // getMembers method
	
	
	// 전체 회원수 가져오기 메소드
	public int memberCount(String search) {
		int result = 0;
		
		// 전체 회원수 가져오기
		result = memberMapper.memberCount(search);
		
		return result;
	} // memberCount method
	
	
	
} // MemberService class

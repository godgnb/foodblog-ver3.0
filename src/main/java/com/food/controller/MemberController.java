package com.food.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.food.domain.MemberVO;
import com.food.service.MemberService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/member/*")
@Log4j
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/loginForm") // 로그인 페이지이동
	public String loginForm() {
		return "member/login";
	} // loginForm get
	
	
	@GetMapping("/login") // 로그인 진행
	@ResponseBody
	public int login(@RequestParam("id") String id,@RequestParam("passwd") String passwd, HttpSession session) {
		// 사용자 확인 메소드 호출
		int check = memberService.userCheck(id, passwd);
		
		if (check != 1) { // 로그인 실패시
			return check;
		} else { // 로그인 성공시
			// id 세션값 저장
			session.setAttribute("id", id);
			return check;
		}
	} // login post
	
	
	@GetMapping("/registerForm") // 회원가입 페이지 이동
	public String registerForm() {
		return "member/register";
	} // registerForm get
	
	
	@GetMapping("/registerIdDupCheck") // 중복아이디 확인
	@ResponseBody
	public boolean registerIdDupCheck(@RequestParam("id") String id) {
		// 아이디 중복확인 메소드 호출
		boolean isIdDup = memberService.isIdDupCheck(id);
		
		return isIdDup;
	} // registerIdDupCheck
	
	
	@PostMapping("/register") // 회원가입 진행
	public String register(MemberVO memberVO) {
		// 회원가입(추가) 메소드 호출
		memberService.insertMember(memberVO);
		
		// 로그인 페이지로 이동하는 정보 생성해서 리턴
		return "redirect:/member/loginForm";
	} // register post
	
	
	@GetMapping("/logout") // 로그아웃 진행
	public ResponseEntity<String> logout(HttpSession session, HttpServletRequest request) {
		// 세션값 초기화
		session.invalidate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("alert('로그아웃 되었습니다.');");
		sb.append("location.href = '/';");
		sb.append("</script>");
		
		return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);
	} // logout get
	
	
	@GetMapping("/myAccountForm") // 회원정보 페이지 이동
	public String myAccountForm(HttpSession session, Model model) {
		
		String id =(String) session.getAttribute("id");
		
		// 회원정보 가져오는 메소드 호출
		MemberVO memberVO = memberService.getMember(id);
		
		model.addAttribute("member", memberVO);
		
		return "member/myAccount";
	} // myAccountForm get
	
	
	@GetMapping("/accountDeleteForm") // 회원탈퇴 페이지 이동
	public String accountDeleteForm() {
		return "member/myAccountDelete";
	} // accountDeleteForm get
	
	
	@PostMapping("/accountDelete") // 회원탈퇴 진행
	public ResponseEntity<String> accountDelete(HttpSession session, @RequestParam("passwd") String passwd) {
		String id =(String) session.getAttribute("id");
		System.out.println("id: " + id);
		System.out.println("passwd: " + passwd);
		
		// 패스워드 일치 메소드 호출
		int check = memberService.userCheck(id, passwd);
		
		if (check != 1) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "text/html; charset=UTF-8");
			
			StringBuilder sb = new StringBuilder();
			sb.append("<script>");
			sb.append("alert('패스워드가 일치하지 않습니다');");
			sb.append("history.back();");
			sb.append("</script>");
			
			return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);
		}
		
		// 회원정보 삭제하기 메소드 호출
		memberService.deleteMember(id);
		
		// 세션 초기화
		session.invalidate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("alert('회원정보를 삭제하였습니다');");
		sb.append("location.href='/'");
		sb.append("</script>");
		
		return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);
	} // accountDelete post
	
	
	@PostMapping("/accountUpdate") // 회원수정 진행
	public ResponseEntity<String> accountUpdate(MemberVO memberVO) {
		// 패스워드 일치 메소드 호출
		int check = memberService.userCheck(memberVO.getId(), memberVO.getPasswd());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");
		
		StringBuilder sb = new StringBuilder();
		if (check != 1) {
			
			sb.append("<script>");
			sb.append("alert('패스워드가 일치하지 않습니다');");
			sb.append("history.back();");
			sb.append("</script>");
			
			return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);
		}
		
		// 회원정보 수정하기 메소드 호출
		memberService.updateMember(memberVO);
		
		sb.append("<script>");
		sb.append("alert('회원정보를 수정하였습니다');");
		sb.append("location.href='/'");
		sb.append("</script>");
		
		return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);
	} // accountUpdate post
	
	
	@GetMapping("/adminForm")
	public String adminForm() {
		return "member/admin";
	} // adminForm get
	
	
	@GetMapping("/allMemberForm")
	public String allMemberForm(
			@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "", required = false) String search,
			Model model) {
		// ===========================================
		// 한 페이지에 해당하는 회원정보목록 구하기 작업
		
		// 한페이지에 보여줄 회원정보 개수
		int pageSize = 10;
	
		// 시작행번호 구하기
		int startRow = (pageNum - 1) * pageSize;
		
		// 전체회원정보 가져오기 메소드 호출
		List<MemberVO> memberList = memberService.getMembers(startRow, pageSize, search);
		log.info("memberList: " + memberList);
		// ===========================================
		// 페이지 블록 관련정보 구하기 작업
		
		// 전체회원 수 가져오기 메소드 호출
		int count = memberService.memberCount(search);
		
		//전체 회원수 / 한페이지당 회원정보개수 (+1 : 나머지 있을때)
    	int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
    	
    	// 페이지블록 수 설정
    	int pageBlock = 10;
    	
    	// 시작페이지번호 startPage 구하기
    	int startPage = ((pageNum - 1) / pageBlock) * pageBlock + 1;
    	
    	// 끝페이지번호 endPage 구하기
    	int endPage = startPage + pageBlock - 1;
    	if (endPage > pageCount) {
    		endPage = pageCount;
    	}
		
    	// 페이지블록 관련정보를 Map 또는 VO 객체로 준비
    	Map<String, Integer> pageInfo = new HashMap<String, Integer>();
    	pageInfo.put("count", count);
    	pageInfo.put("pageCount", pageCount);
    	pageInfo.put("pageBlock", pageBlock);
    	pageInfo.put("startPage", startPage);
    	pageInfo.put("endPage", endPage);
    	
    	
		// 뷰(jsp)에 사용할 데이터를 request 영역개체에 저장
		model.addAttribute("memberList", memberList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("search", search);
		
		return "member/allMember";
	} // allMemberForm get
	
	
	@GetMapping("/allMemberDeleteForm")
	public String allMemberDeleteForm(@RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "", required = false) String search,
			Model model) {
		// ===========================================
		// 한 페이지에 해당하는 회원정보목록 구하기 작업
		
		// 한페이지에 보여줄 회원정보 개수
		int pageSize = 10;
	
		// 시작행번호 구하기
		int startRow = (pageNum - 1) * pageSize;
		
		// 전체회원정보 가져오기 메소드 호출
		List<MemberVO> memberList = memberService.getMembers(startRow, pageSize, search);
		
		// ===========================================
		// 페이지 블록 관련정보 구하기 작업
		
		// 전체회원 수 가져오기 메소드 호출
		int count = memberService.memberCount(search);
		
		//전체 회원수 / 한페이지당 회원정보개수 (+1 : 나머지 있을때)
    	int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
    	
    	// 페이지블록 수 설정
    	int pageBlock = 10;
    	
    	// 시작페이지번호 startPage 구하기
    	int startPage = ((pageNum - 1) / pageBlock) * pageBlock + 1;
    	
    	// 끝페이지번호 endPage 구하기
    	int endPage = startPage + pageBlock - 1;
    	if (endPage > pageCount) {
    		endPage = pageCount;
    	}
		
    	// 페이지블록 관련정보를 Map 또는 VO 객체로 준비
    	Map<String, Integer> pageInfo = new HashMap<String, Integer>();
    	pageInfo.put("count", count);
    	pageInfo.put("pageCount", pageCount);
    	pageInfo.put("pageBlock", pageBlock);
    	pageInfo.put("startPage", startPage);
    	pageInfo.put("endPage", endPage);
    	
    	
		// 뷰(jsp)에 사용할 데이터를 request 영역개체에 저장
		model.addAttribute("memberList", memberList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("search", search);
		
		return "member/allMemberDelete";

	} // allMemberDeleteForm get
	
	
	@PostMapping("/allMemberDelete")
	public ResponseEntity<String> allMemberDelete(@RequestParam("rowCheck") List<String> rowCheckList) {
		log.info("rowCheckList: " + rowCheckList);
		log.info(rowCheckList.size());
		
		if (!rowCheckList.isEmpty()) {
			log.info("rowCheckList22222: " + rowCheckList);
			memberService.deleteMembers(rowCheckList);
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "text/html; charset=UTF-8");

			StringBuilder sb = new StringBuilder();
			sb.append("<script>");
			sb.append("alert('회원정보를 삭제하였습니다');");
			sb.append("location.href='/member/allMemberDeleteForm'");
			sb.append("</script>");

			return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);
		}
		  
		return null;
		
	} // allMemberDelete post
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
} // MemberController class

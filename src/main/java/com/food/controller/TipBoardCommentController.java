package com.food.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.domain.TipBoardCommentVO;
import com.food.service.TipBoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@RestController
@RequestMapping("/tipboard/*")
@Log4j
@AllArgsConstructor
public class TipBoardCommentController {

	private TipBoardService tipBoardService;
	
	
	@PostMapping("/commentWrite")
	public String commentWrite(@RequestBody TipBoardCommentVO tipBoardCommentVO, int num, int pageNum, int commentPageNum, Model model){
		// =========== 댓글 등록 ===============
		// 댓글 번호 생성하는 메소드 호출
		int reNum = tipBoardService.nextCommentNum();
		
		tipBoardCommentVO.setReNum(reNum);
		
		// 댓글 한개 등록하는 메소드 호출
		tipBoardService.insertComment(tipBoardCommentVO);
		
		
		//========= 댓글 카운트 올리기 작업 ==============
		tipBoardService.commCountUp(num);
		
		
		//========= 해당 게시글 댓글정보 구하기 작업 ==============
		// 한페이지에 보여줄 글 개수
		int pageSize = 5;

		// 시작행번호 구하기
		int startRow = (pageNum - 1) * pageSize;
		
		// 글번호에 해당하는 댓글정보 가져오기
		 List<TipBoardCommentVO> commentList = tipBoardService.getComments(num, startRow, pageSize);
		
		// request 영역객체에 저장
		model.addAttribute("commentList", commentList);
		
		return "redirect:/tipboard/contentForm?num=" + num + "&pageNum=" + pageNum + "&commentPageNum=" + commentPageNum;
	} // commentWrite post
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
} // TipBoardCommentController class

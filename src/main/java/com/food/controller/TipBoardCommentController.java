package com.food.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.food.domain.TipBoardAttachVO;
import com.food.domain.TipBoardCommentVO;
import com.food.domain.TipBoardVO;
import com.food.service.TipBoardAttachService;
import com.food.service.TipBoardService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/tipboardcomment/*")
@Log4j
public class TipBoardCommentController {
	
	@Autowired
	private TipBoardService tipBoardService;
	
	@Autowired
	private TipBoardAttachService TipBoardAttachService;
	
	@PostMapping("/commentWrite")
	public String commentWrite(TipBoardCommentVO tipBoardCommentVO, int num, int pageNum, int commentPageNum, Model model){
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
	
	
	@GetMapping("/commentDelete")
	public String commentDelete(int num, int pageNum, int reNum) {
		//========= 댓글 삭제 ==============
		// 댓글 한개 삭제하는 메소드 호출
		tipBoardService.deleteComment(reNum);
		
		//========= 댓글 카운트 내리는 작업 ==============
		tipBoardService.commCountDown(num);
		
		return "redirect:/tipboard/contentForm?num=" + num + "&pageNum=" + pageNum;
	} // commentDelete post
	
	
	@GetMapping("/commentUpdateForm")
	public String commentUpdateForm(int num, int pageNum, @RequestParam("commentPageNum") int commentPageNum, int reNum, Model model) {
		//========= 수정할 글 정보 가져오기 ==============
		// 수정할 글 글번호로 가져오기
		TipBoardVO tipBoardVO = tipBoardService.getboardTip(num);
		
		// ========== 해당 게시글 첨부이미지 구하기 작업 ===============
		// 글번호에 해당하는 첨부파일정보 가져오기
		TipBoardAttachVO tipBoardAttachVO = TipBoardAttachService.getAttach(num);
		
		//========= 수정할 댓글 정보 가져오기 ==============
		// 수정할 댓글 댓글번호로 가져오기
		TipBoardCommentVO tipBoardCommentVO = tipBoardService.getComment(reNum);
		
		
		// ========== 해당 게시글 댓글정보 구하기 작업 ===============
		// 한페이지에 보여줄 글 개수
		int pageSize = 5;

		// 시작행번호 구하기
		int startRow = (pageNum - 1) * pageSize;
		
		// 글번호에 해당하는 댓글정보 가져오기
		List<TipBoardCommentVO> commentList = tipBoardService.getComments(num, startRow, pageSize);
		
		
		// =========== 페이지 블록 관련정보 구하기 작업 ===============
		// 해당게시글 전체댓글개수 가져오기 메소드
		int count = tipBoardService.getCommentCount(num);

		//전체 글 개수 / 한페이지당 글개수 (+1 : 나머지 있을때)
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);

		// 페이지블록 수 설정
		int pageBlock = 5;

		// 시작페이지번호 startPage 구하기
		int startPage = ((commentPageNum - 1) / pageBlock) * pageBlock + 1;

		// 끝페이지번호 endPage 구하기
		int endPage = startPage + pageBlock - 1;
		if (endPage > pageCount) {
			endPage = pageCount;
		}
		
		// 페이지블록 관련정보를 Map 또는 VO 객체로 준비
		Map<String, Integer> pageInfo = new HashMap<String, Integer>();
		pageInfo.put("commentPageNum", commentPageNum);
		pageInfo.put("count", count);
		pageInfo.put("pageCount", pageCount);
		pageInfo.put("pageBlock", pageBlock);
		pageInfo.put("startPage", startPage);
		pageInfo.put("endPage", endPage);
		
		// request 영역개체에 저장
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("tipBoard", tipBoardVO);
		model.addAttribute("attach", tipBoardAttachVO);
		model.addAttribute("commentList", commentList);
		model.addAttribute("tipboardcomment", tipBoardCommentVO);
		model.addAttribute("pageInfo", pageInfo);
		
		return "cookingTip/commentUpdate";
	} // commentUpdateForm get
	
	
	
	@PostMapping("/commentUpdate")
	public String commentUpdate(int num, int pageNum, TipBoardCommentVO tipBoardCommentVO, Model model) {
		// =========== 댓글 수정 작업 ===============
		// 댓글 한개 수정하는 메소드 호출
		tipBoardService.updateComment(tipBoardCommentVO);
		
		//========= 해당 게시글 댓글정보 구하기 작업 ==============
		// 한페이지에 보여줄 글 개수
		int pageSize = 5;

		// 시작행번호 구하기
		int startRow = (pageNum - 1) * pageSize;
		
		// 글번호에 해당하는 댓글정보 가져오기
		List<TipBoardCommentVO> commentList = tipBoardService.getComments(num, startRow, pageSize);
		
		// request 영역객체에 저장
		model.addAttribute("commentList", commentList);
		
		
		return "redirect:/tipboard/contentForm?num=" + num + "&pageNum=" + pageNum;
	} // commentUpdate post
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
} // TipBoardCommentController class

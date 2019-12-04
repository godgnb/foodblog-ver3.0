package com.food.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.TipBoardAttachVO;
import com.food.domain.TipBoardCommentVO;
import com.food.domain.TipBoardVO;
import com.food.mapper.TipboardAttachMapper;
import com.food.mapper.TipboardMapper;

@Service
@Transactional
public class TipBoardService {

	@Autowired
	private TipboardMapper tipboardMapper;
	
	@Autowired
	private TipboardAttachMapper tipboardAttachMapper;
	
	// insert할 레코드의 번호 생성 메소드
	public int NextTipNum() {
		int bnum = tipboardMapper.NextTipNum();
		
		return bnum;
	} // NextTipNum method
	
	
	// 게시글과 첨부파일 한개 등록하는 메소드
	public void insertTipAndAttach(TipBoardVO tipboardVO, TipBoardAttachVO tipBoardAttachVO) {
		// 파일게시판 주글 등록
		tipboardMapper.insertTip(tipboardVO);
		
		// 첨부파일 등록
		if (tipBoardAttachVO != null) { // 첨부파일 정보 있으면
			tipboardAttachMapper.insertAttach(tipBoardAttachVO); // 첨부파일 등록
		}
	} // insertTip method
	
	
	// 검색어로 검색된 행의 시작행번호부터 갯수만큼 가져오기(페이징)
	public List<TipBoardVO> getBoards(int startRow, int pageSize, String search) {
		List<TipBoardVO> list = tipboardMapper.getBoards(startRow, pageSize, search);
		
		return list;
	} // getBoards method
	
	
	// 검색어로 게시판(board) 테이블 레코드 개수 가져오기 메소드
	public int getBoardCount(String search) {
		int count = tipboardMapper.getBoardCount(search);
		
		return count;
	} // getBoardCount method
	
	
	// 특정 레코드의 조회수를 1증가시키는 메소드
	public void updateReadCount(int num) {
		tipboardMapper.updateReadCount(num);
	} // updateReadCount method
	
	
	// 게시글 한개를 가져오는 메소드
	public TipBoardVO getboardTip(int num) {
		TipBoardVO tipBoardVO = tipboardMapper.getboardTip(num);
		
		return tipBoardVO;
	} // getboardTip method
	
	
	// 게시글 패스워드 비교 메소드
	public boolean isPasswdEqual(int num, String passwd) {
		boolean isPasswdEqual = false;
		
		int count = tipboardMapper.isPasswdEqual(num, passwd);
		if (count == 1) {
			isPasswdEqual = true;
		} else {
			isPasswdEqual = false;
		}
		
		return isPasswdEqual;
	} // isPasswdEqual method
	
	
	// 게시글 삭제하기 메소드
	public void deleteBoard(int num) {
		tipboardMapper.deleteBoard(num);
	} // deleteBoard method
	
	
	// 게시글 수정하기 메소드
	public void updateBoard(TipBoardVO tipBoardVO) {
		tipboardMapper.updateBoard(tipBoardVO);
	} // updateBoard method
	
	
	//=================== 게시글 댓글 작업 ================
	// 게시글 댓글 출력하는 메소드
	public List<TipBoardCommentVO> getComments(int num, int startRow, int pageSize) {
		List<TipBoardCommentVO> list = tipboardMapper.getComments(num, startRow, pageSize);
		
		return list;
	} // getComments method
	
	
	// 댓글 한개를 가져오는 메소드
	public TipBoardCommentVO getComment(int reNum) {
		TipBoardCommentVO tipBoardCommentVO = tipboardMapper.getComment(reNum);
		
		return tipBoardCommentVO;
	} // getComment method
	
	
	// 게시글 전체 삭제하기 메소드
	public void deleteComments(int num) {
		tipboardMapper.deleteComments(num);
	} // deleteComments method
	
	
	// 댓글 삭제하기 메소드
	public void deleteComment(int reNum) {
		tipboardMapper.deleteComment(reNum);
	} // deleteComment method
	
	
	// 특정 게시글 댓글 개수 가져오기 메소드
	public int getCommentCount(int num) {
		int count = tipboardMapper.getCommentCount(num);
		
		return count;
	} // getCommentCount method
	
	
	// insert할 게시물의 댓글번호 생성 메소드
	public int nextCommentNum() {
		int bnum = tipboardMapper.nextCommentNum();
		
		return bnum;
	} // nextCommentNum method
	
	
	// 게시글에 댓글 한개 등록하는 메소드
	public void insertComment(TipBoardCommentVO tipboardcommentVO) {
		tipboardMapper.insertComment(tipboardcommentVO);
	} // insertComment method
	
	
	// 특정 레코드의 댓글수를 1증가시키는 메소드
	public void commCountUp(int num) {
		tipboardMapper.commCountUp(num);
	} // commCountUp method
	
	
	// 특정 레코드의 댓글수를 1감소시키는 메소드
	public void commCountDown(int num) {
		tipboardMapper.commCountDown(num);
	} // commCountDown method
	
	
	// 댓글 수정하기 메소드
	public void updateComment(TipBoardCommentVO tipBoardCommentVO) {
		tipboardMapper.updateComment(tipBoardCommentVO);
	} // updateComment method
	
	
} // TipBoardService class

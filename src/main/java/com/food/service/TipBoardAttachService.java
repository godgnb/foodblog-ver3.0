package com.food.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.domain.TipBoardAttachVO;
import com.food.mapper.TipboardAttachMapper;

@Service
@Transactional
public class TipBoardAttachService {

	@Autowired
	private TipboardAttachMapper tipboardAttachMapper;
	
	
	// 첨부파일 한개 등록하는 메소드
	public void insertAttach(TipBoardAttachVO tipBoardAttachVO) {
		tipboardAttachMapper.insertAttach(tipBoardAttachVO);
	} // insertAttach method
	
	// 글번호에 해당하는 첨부파일정보 가져오기
	public TipBoardAttachVO getAttach(int bno) {
		TipBoardAttachVO tipBoardAttachVO = tipboardAttachMapper.getAttach(bno);
		
		return tipBoardAttachVO;
	} // getAttach method
	
	
	// 파일 삭제하기 메소드 기준 bno
	public void deleteAttachByBno(int bno) {
		tipboardAttachMapper.deleteAttachByBno(bno);
	} // deleteAttach method
	
	// 파일 삭제하기 메소드 호출 기준 uuid
	public void deleteAttachByUuid(String uuid) {
		tipboardAttachMapper.deleteAttachByUuid(uuid);
	} // deleteAttachByUuid method
	
	
	
	
	
	
	
	
	
} // TipBoardAttachService class

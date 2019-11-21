package com.food.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.executor.ReuseExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.food.domain.TipBoardAttachVO;
import com.food.domain.TipBoardCommentVO;
import com.food.domain.TipBoardVO;
import com.food.service.TipBoardAttachService;
import com.food.service.TipBoardService;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/tipboard/*")
@Log4j
public class TipBoardController {

	@Autowired
	private TipBoardService tipBoardService;

	@Autowired
	private TipBoardAttachService tipBoardAttachService;


	@GetMapping("/tipboardForm")
	public String tipboardForm (@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") int pageNum,
			Model model) {

		// =========== 한 페이지에 해당하는 글목록 구하기 작업 ===============
		// 한페이지에 보여줄 글 개수
		int pageSize = 10;

		// 시작행번호 구하기
		int startRow = (pageNum - 1) * pageSize;

		// 글목록 가져오기 메소드 호출
		List<TipBoardVO> tipboardList = tipBoardService.getBoards(startRow, pageSize, search);


		// =========== 페이지 블록 관련정보 구하기 작업 ===============
		// tipboard테이블 전체글개수 가져오기 메소드
		int count = tipBoardService.getBoardCount(search);

		//전체 글 개수 / 한페이지당 글개수 (+1 : 나머지 있을때)
		int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);

		// 페이지블록 수 설정
		int pageBlock = 5;

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
		model.addAttribute("tipboardList", tipboardList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("search", search);

		return "cookingTip/tipBoard";
	} // tipboardForm get


	@GetMapping("/writeForm")
	public String writeForm() {

		return "cookingTip/write";
	} // writeForm get


	@PostMapping("/write")
	public String write(MultipartFile file, TipBoardVO tipBoardVO, HttpServletRequest request) throws Exception {
		// =========== 게시판 글 등록준비 ===============
		// IP주소 값 저장
		tipBoardVO.setIp(request.getRemoteAddr());

		// 게시글 번호 생성하는 메소드 호출
		int num = tipBoardService.NextTipNum();

		tipBoardVO.setNum(num);
		tipBoardVO.setReadcount(0);
		tipBoardVO.setCommcount(0);


		// =========== 첨부파일 등록준비 ===============
		// 업로드할 경로
		ServletContext application = request.getServletContext();
		String realPath = application.getRealPath("/resources/upload");

		// 업로드한 원본 파일이름
		String uploadFileName = file.getOriginalFilename();

		// 파일업로드
		UUID uuid = UUID.randomUUID();
		uploadFileName = uuid.toString() + "_" + uploadFileName;

		File saveFile = new File(realPath, uploadFileName);

		file.transferTo(saveFile); // 파일 업로드 수행

		// TipBoardAttach 테이블에 insert할 tipBoardAttachVO 준비하기
		TipBoardAttachVO tipBoardAttachVO = new TipBoardAttachVO();
		tipBoardAttachVO.setBno(tipBoardVO.getNum());
		tipBoardAttachVO.setUuid(uuid.toString());
		tipBoardAttachVO.setUploadpath(realPath);
		tipBoardAttachVO.setFilename(uploadFileName);


		// =========== 게시글 등록 ===============
		// 테이블 insert : board테이블과 attach테이블 트랜잭션으로 insert
		tipBoardService.insertTipAndAttach(tipBoardVO, tipBoardAttachVO);

		return "redirect:/tipboard/tipboardForm";
	} // write post


	@GetMapping("/contentForm")
	public String contentForm(int pageNum, int num, Model model,
			@RequestParam(defaultValue = "1") int commentPageNum) {
		// ========== 해당 게시글 구하기 작업 ===============
		// 조회수 1 증가 메소드 호출
		tipBoardService.updateReadCount(num);

		// 글번호에 해당하는 레코드 한개 가져오기
		TipBoardVO tipBoardVO = tipBoardService.getboardTip(num);

		// ========== 해당 게시글 첨부이미지 구하기 작업 ===============
		// 글번호에 해당하는 첨부파일정보 가져오기
		TipBoardAttachVO tipBoardAttachVO = tipBoardAttachService.getAttach(num);

		// ========== 해당 게시글 댓글정보 구하기 작업 ===============
		// 한페이지에 보여줄 글 개수
		int pageSize = 5;

		// 시작행번호 구하기
		int startRow = (commentPageNum - 1) * pageSize;

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


		// request 영역객체에 저장
		model.addAttribute("num", num);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("tipBoard", tipBoardVO);
		model.addAttribute("attach", tipBoardAttachVO);
		model.addAttribute("commentList", commentList);
		model.addAttribute("pageInfo", pageInfo);

		return "cookingTip/content";
	} // contentForm get


	@GetMapping("/deleteForm")
	public String deleteForm(@ModelAttribute("pageNum") String pageNum,@ModelAttribute("num") int num, HttpSession session) {
		// 로그인 안한 사용자면 글목록으로 이동시키기
		String id = (String) session.getAttribute("id");

		if (id == null) {
			return "redirect:/tipboard/tipboardForm";
		}

		return "cookingTip/delete";
	} // deleteForm get


	@PostMapping("/delete")
	public ResponseEntity<String> delete(String pageNum, int num, String passwd, HttpServletRequest request) {
		// 패스워드가 다를때는 "글패스워드 다름" 뒤로가기
		if (!tipBoardService.isPasswdEqual(num, passwd)) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "text/html; charset=UTF-8");

			StringBuilder sb = new StringBuilder();
			sb.append("<script>");
			sb.append("alert('글 패스워드가 다릅니다.');");
			sb.append("history.back();");
			sb.append("</script>");
			return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);
		}

		// ========== 해당 게시글 삭제 작업 ===============
		// 게시글 한개 삭제하기 메소드 호출
		tipBoardService.deleteBoard(num);


		// ========== 해당 게시글 첨부이미지 삭제 작업 ===============
		// 게시판 글번호에 해당하는 첨부파일정보 가져오기
		TipBoardAttachVO tipBoardAttachVO = tipBoardAttachService.getAttach(num);

		// application 객체 참조 구하기
		ServletContext appliaction = request.getServletContext();	

		// 첨부파일정보가 있으면 해당 실제파일 삭제하기
		if (tipBoardAttachVO != null) {
			// 삭제할 첨부파일경로 가져오기
			String realpath = appliaction.getRealPath("/resources/upload");

			// 파일 삭제를 위한 File 객체 준비
			File file = new File(realpath, tipBoardAttachVO.getFilename());

			if (file.exists()) {
				file.delete();
			}
		}

		// 첨부파일 삭제 메소드
		tipBoardAttachService.deleteAttachByBno(num);

		// ========== 해당 게시글 댓글정보 삭제 작업 ===============
		// 댓글 전체 삭제하기 메소드 호출
		tipBoardService.deleteComments(num);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Location", "/tipboard/tipboardForm?pageNum=" + pageNum);
		return new ResponseEntity<String>(headers, HttpStatus.FOUND);
	} // delete post


	@GetMapping("/updateForm")
	public String updateForm(HttpSession session, @ModelAttribute("num") int num, 
			@ModelAttribute("pageNum") String pageNum, Model model) {
		// 로그인 안한 사용자면 글목록으로 이동시키기
		String id = (String) session.getAttribute("id");

		if (id == null) {
			return "redirect:/tipboard/tipboardForm";
		}

		// ========== 수정할 게시글 정보 가져오기 작업 ===============
		// 수정할 글 글번호로 가져오기
		TipBoardVO tipBoardVO = tipBoardService.getboardTip(num);

		// 글번호에 해당하는 첨부파일 정보가져오기
		TipBoardAttachVO tipBoardAttachVO = tipBoardAttachService.getAttach(num);

		// model 영역개체에 저장
		model.addAttribute("tipBoard", tipBoardVO);
		model.addAttribute("attach", tipBoardAttachVO);

		return "cookingTip/update";
	} // updateForm get


	@PostMapping("/update")
	public ResponseEntity<String> update(MultipartFile file, TipBoardVO tipBoardVO, HttpServletRequest request, String pageNum,
			@RequestParam(value = "delFile", defaultValue = "", required = false) String delFile) throws Exception {
		//=============== 게시글 수정 처리 시작 =================
		// 게시글 수정하는 메소드 호출
		tipBoardService.updateBoard(tipBoardVO);

		//=============== 첨부파일 수정 처리 시작 =================
		if(!delFile.equals("")) {
			// 업로드할 경로
			ServletContext application = request.getServletContext();
			String realPath = application.getRealPath("/resources/upload");

			// 업로드한 원본 파일이름
			String uploadFileName = file.getOriginalFilename();

			// 파일업로드
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;

			File saveFile = new File(realPath, uploadFileName);

			file.transferTo(saveFile); // 파일 업로드 수행

			// TipBoardAttach 테이블에 insert할 tipBoardAttachVO 준비하기
			TipBoardAttachVO tipBoardAttachVO = new TipBoardAttachVO();
			tipBoardAttachVO.setBno(tipBoardVO.getNum());
			tipBoardAttachVO.setUuid(uuid.toString());
			tipBoardAttachVO.setUploadpath(realPath);
			tipBoardAttachVO.setFilename(uploadFileName);

			// 첨부파일정보 한개 등록하는 메소드 호출
			tipBoardAttachService.insertAttach(tipBoardAttachVO);


			//=============== 삭제파일 삭제작업 시작 =================
			// 삭제할 파일정보 파라미터 가져오기
			String[] strArr = delFile.split("_");
			String delUuid = strArr[0];
			String delFilename = strArr[1];
			log.info("delUuid : " + uuid);
			log.info("delFilename : " + delFilename);

			// 파일 삭제하기
			File DelFile = new File(realPath, delFile);
			if (DelFile.exists()) {
				DelFile.delete();
			}

			// uuid레코드 삭제 메소드 호출
			tipBoardAttachService.deleteAttachByUuid(delUuid);
		}

		//=============== 삭제파일 삭제작업 시작 =================
		// 페이지 이동
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "text/html; charset=UTF-8");

		StringBuilder sb = new StringBuilder();
		sb.append("<script>");
		sb.append("alert('글 수정 성공!');");
		sb.append("location.href = '/tipboard/contentForm?num=" + tipBoardVO.getNum() + "&pageNum=" + pageNum + "';");
		sb.append("</script>");

		return new ResponseEntity<String>(sb.toString(), headers, HttpStatus.OK);
	} // update post

} // TipBoardController class

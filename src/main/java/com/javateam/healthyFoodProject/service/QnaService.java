package com.javateam.healthyFoodProject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.javateam.healthyFoodProject.dao.PhotoDAO;
import com.javateam.healthyFoodProject.dao.QnaDAO;
import com.javateam.healthyFoodProject.domain.PhotoVO;
import com.javateam.healthyFoodProject.domain.QnaVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class QnaService {
	
	@Autowired
	QnaDAO qnaDAO;
	
	@Transactional(rollbackFor = Exception.class)
	public QnaVO insertQna(QnaVO qnaVO) {
		
		return qnaDAO.save(qnaVO);
	}
	
	@Transactional(readOnly = true)
	public int selectQnasCount() {
		
		return (int)qnaDAO.count();
	} //

	@Transactional(readOnly = true)
	public List<QnaVO> selectQnasByPaging(int currPage, int limit) {
				
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "qnaDate"));
		return qnaDAO.findAll(pageable).getContent();
	} //

	@Transactional(readOnly = true)
	public QnaVO selectQna(int qnaCode) {
		
		return qnaDAO.findById(qnaCode);
	}

	@Transactional(readOnly = true)
	public int selectQnasCountBySearching(String searchKey, String searchWord) {

		// return searchKey.equals("qna_title") ? qnaQnaDAO.countByQnaSubjectLike("%"+searchWord+"%") : 
		return searchKey.equals("QNA_TITLE") ? qnaDAO.countByQnaTitleContaining(searchWord) :
			   searchKey.equals("QNA_CONTENT") ? qnaDAO.countByQnaContentContaining(searchWord): 
			   qnaDAO.countByMemberEmailContaining(searchWord);//QnaWriter->MemberEmail 수정 song 0412	
		
	}

	@Transactional(readOnly = true)
	public List<QnaVO> selectQnasBySearching(int currPage, int limit, String searchKey, String searchWord) {
		
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "qnaCode"));
		
		// return searchKey.equals("board_subject") ? qnaDAO.findByQnaSubjectLike("%"+searchWord+"%", pageable).getContent() : 
		return searchKey.equals("QNA_TITLE") ? qnaDAO.findByQnaTitleContaining(searchWord, pageable).getContent() :
			   searchKey.equals("QNA_CONTENT") ? qnaDAO.findByQnaContentContaining(searchWord, pageable).getContent() : 
			   qnaDAO.findByMemberEmailContaining(searchWord, pageable).getContent();//song 0412 QnaWriter->MemberEmail로 수정 
	}
	
	// imgUploadPath = /qna/image/  //song 0412 board->qna로 경로 수정
	public List<Integer> getImageList(String str, String imgUploadPath) {

		log.info("QnaService.getImageList");
		List<Integer> imgList = new ArrayList<>(); // upload_file_tbl 테이블의 PK(기본키)
		
		if (str.contains(imgUploadPath) == false) { // 이미지 미포함
			
			log.info("이미지가 전혀 포함되어 있지 않습니다.");
			
		} else {

			// 포함된 전체 이미지 수 : 이 한계량 만큼 검색  => 카운터에 반영
			int imgLen = StringUtils.countOccurrencesOf(str, imgUploadPath);
			
			log.info("imgLen : " + imgLen);
			
			// 이미지 검색 카운터 설정 : 이미지 검색할 횟수
			int count = 0;  
			
			int initPos = str.indexOf(imgUploadPath);
			log.info("첫 발견 위치 : " + initPos);
			
			// 추출된 문자열 : 반복문에서 사용
			String subStr = str;
			
			while (count < imgLen) {
				
				initPos = subStr.indexOf(imgUploadPath);
				
				// 이미지 파일만 추출 (첫번째)
				// "/board/image/".length()
				initPos += imgUploadPath.length();
				log.info("이미지 파일 시작 위치 : " + initPos);
				
				// 추출된 문자열
				// ex) 41 (.../board/image/41" : upload_file_tbl 테이블의 삽입 이미지 PK(기본키))
				subStr = subStr.substring(initPos);
				
				log.info("subStr : " + subStr);
				
				// 첫번째 " (큰 따옴표) 위치 검색하여 순수한 숫자(PK)만 추출
				int quotMarkPos = subStr.indexOf("\"");
				
				// 이미지 파일 끝 검색하여 이미지 파일명/확장자 추출
				// 이미지 끝 검색 : 검색어(" )
				int imgFileNum = Integer.parseInt(subStr.substring(0, quotMarkPos));
				
				log.info("이미지 파일 테이블 PK(기본기) : " + imgFileNum);
				
				count++; // 이미지 추출되었으므로 카운터 증가
				
				imgList.add(imgFileNum); // 리스트에 추가
				
				log.info("----------------------------------------");
			
			} //  while
		
		} // if

		return imgList;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public QnaVO updateQna(QnaVO qnaVO) {
		
		return qnaDAO.save(qnaVO);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<QnaVO> selectReplysById(int qnaCode) {
		
//		return qnaDAO.findByQnaReRefOrderByQnaDateAsc(qnaCode);
		return qnaDAO.findByQnaReSeq(qnaCode);
	}
	
	@Transactional(readOnly = true)
	public int selectQnasCountWithoutReplies() {
		
		return (int)qnaDAO.countByQnaReSeq(0); // (댓글 아닌)원글만 추출 : board_re_ref = 0
	} //

	@Transactional(readOnly = true)
	public List<QnaVO> selectQnasByPagingWithoutReplies(int currPage, int limit) {
				
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "qnaCode"));
		return qnaDAO.findByQnaReSeq(0, pageable).getContent(); // (댓글 아닌)원글만 추출 : board_re_ref = 0
	} //

	@Transactional(rollbackFor = Exception.class)
	public boolean deleteReplysById(int qnaCode) {
		
		boolean result = false;
		
		try {
			qnaDAO.deleteById(qnaCode);
			result = true;
		} catch (Exception e) {
			log.error("deleteReplyById error : {}", e);
			result = false;
		}
		
		return result;
	}
	
	// 댓글 수량 조회
	@Transactional(readOnly = true)
	public int selectQnasCountWithReplies(int qnaCode) {
		
		return (int)qnaDAO.countByQnaReSeq(qnaCode); // 댓글의 갯수 추출 : board_re_ref = qnaCode
	} //
	
	
	// 게시글(원글) 삭제
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteById(int qnaCode) {
		
		boolean result = false;
		
		try {
			qnaDAO.deleteById(qnaCode);
			result = true;
		} catch (Exception e) {
			log.error("deleteById error : {}", e);
			result = false;
		}
		
		return result;
	}
	
//	// 게시글 조회수 갱신
	@Transactional(rollbackFor = Exception.class)
	public boolean updateQnaReadcountByQnaCode(int qnaCode) {
		
		boolean result = false;
		
		try {
			qnaDAO.updateQnaReadCountByQnaCode(qnaCode);
			result = true;
		} catch (Exception e) {
			log.error("updateQnaReadCountByQnaCode error : {}", e);
			result = false;
		}
		
		return result;
	}
	
}

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
import com.javateam.healthyFoodProject.domain.PhotoVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PhotoService {
	
	@Autowired
	PhotoDAO photoDAO;
	
	@Transactional(rollbackFor = Exception.class)
	public PhotoVO insertBoard(PhotoVO photoVO) {
		
		return photoDAO.save(photoVO);
	}
	
	@Transactional(readOnly = true)
	public int selectBoardsCount() {
		
		return (int)photoDAO.count();
	} //
	
	//0411 leee 추가 수정. 
	@Transactional(readOnly = true)
	public List<PhotoVO> selectPhotoAndFileName(int boardNum) {
	    return photoDAO.selectPhotoAndFileName(boardNum );
	}
	
	
	@Transactional(readOnly = true)
	public List<String> selectBoardsImg(int boardNum) {
		log.info("보드 번호 : " + boardNum);
		return photoDAO.selectBoardImg(boardNum + 1); // 0409 leee 여기서 0으로 들어가서 안나왔음. +1해줌. 
	} //

	@Transactional(readOnly = true)
	public List<PhotoVO> selectBoardsByPaging(int currPage, int limit) {
				
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "boardDate"));
		return photoDAO.findAll(pageable).getContent();
	} //

	@Transactional(readOnly = true)
	public PhotoVO selectBoard(int boardNum) {
		
		return photoDAO.findById(boardNum);
	}

	@Transactional(readOnly = true)
	public int selectBoardsCountBySearching(String searchKey, String searchWord) {

		// return searchKey.equals("board_subject") ? photoBoardDAO.countByBoardSubjectLike("%"+searchWord+"%") : 
		return searchKey.equals("board_subject") ? photoDAO.countByBoardSubjectContaining(searchWord) :
			   searchKey.equals("board_content") ? photoDAO.countByBoardContentContaining(searchWord) : 
			   photoDAO.countByBoardWriterContaining(searchWord);	
		
	}

	@Transactional(readOnly = true)
	public List<PhotoVO> selectBoardsBySearching(int currPage, int limit, String searchKey, String searchWord) {
		
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "boardNum"));
		
		// return searchKey.equals("board_subject") ? photoBoardDAO.findByBoardSubjectLike("%"+searchWord+"%", pageable).getContent() : 
		return searchKey.equals("board_subject") ? photoDAO.findByBoardSubjectContaining(searchWord, pageable).getContent() :
			   searchKey.equals("board_content") ? photoDAO.findByBoardContentContaining(searchWord, pageable).getContent() : 
			   photoDAO.findByBoardWriterContaining(searchWord, pageable).getContent();
	}
	
	// imgUploadPath = /board/image/
	public List<Integer> getImageList(String str, String imgUploadPath) {

		log.info("BoardService.getImageList");
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
	public PhotoVO updateBoard(PhotoVO boardVO) {
		
		return photoDAO.save(boardVO);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<PhotoVO> selectReplysById(int boardNum) {
		
		return photoDAO.findByBoardReRefOrderByBoardDateAsc(boardNum);
	}
	
	@Transactional(readOnly = true)
	public int selectBoardsCountWithoutReplies() {
		
		return (int)photoDAO.countByBoardReRef(0); // (댓글 아닌)원글만 추출 : board_re_ref = 0
	} //

	@Transactional(readOnly = true)
	public List<PhotoVO> selectBoardsByPagingWithoutReplies(int currPage, int limit) {
				
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "boardNum"));//Page request [number: 0, size 10, sort: boardNum: DESC]
		log.info( "pageable 서비스 >> " + pageable );
		return photoDAO.findByBoardReRef(0, pageable).getContent(); // (댓글 아닌)원글만 추출 : board_re_ref = 0
	} //

	@Transactional(rollbackFor = Exception.class)
	public boolean deleteReplysById(int boardNum) {
		
		boolean result = false;
		
		try {
			photoDAO.deleteById(boardNum);
			result = true;
		} catch (Exception e) {
			log.error("deleteReplyById error : {}", e);
			result = false;
		}
		
		return result;
	}
	
	// 댓글 수량 조회
	@Transactional(readOnly = true)
	public int selectBoardsCountWithReplies(int boardNum) {
		
		return (int)photoDAO.countByBoardReRef(boardNum); // 댓글의 갯수 추출 : board_re_ref = boardNum
	} //
	
	
	// 게시글(원글) 삭제
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteById(int boardNum) {
		
		boolean result = false;
		
		try {
			photoDAO.deleteById(boardNum);
			result = true;
		} catch (Exception e) {
			log.error("deleteById error : {}", e);
			result = false;
		}
		
		return result;
	}
	
	// 게시글 조회수 갱신
	@Transactional(rollbackFor = Exception.class)
	public boolean updateBoardReadcountByBoardNum(int boardNum) {
		
		boolean result = false;
		
		try {
			photoDAO.updateBoardReadcountByBoardNum(boardNum);
			result = true;
		} catch (Exception e) {
			log.error("updateBoardReadcountByBoardNum error : {}", e);
			result = false;
		}
		
		return result;
	}
	
}

package com.javateam.healthyFoodProject.service;

import java.util.ArrayList;
import java.util.List;

//import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.javateam.healthyFoodProject.dao.FoodDAO;
import com.javateam.healthyFoodProject.domain.FoodVO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FoodService {

	@Autowired
	FoodDAO foodDAO;
	
	@Transactional(rollbackFor = Exception.class)
	public FoodVO insertFood(FoodVO foodVO) {
		
		return foodDAO.save(foodVO);
	}
	
	public List<FoodVO> findAllByOrderByFoodCode(){
		return foodDAO.findAllByOrderByFoodCodeDesc();
	}
	
	// 0423 leee 추가함. 
	public List<FoodVO> findAllByFoodIngredientMainInside(String sasang){
		log.info("sasang 서비스 이름 확인 : " + sasang);
		return foodDAO.findAllByFoodIngredientMainInside(sasang);
		
	}

	// 0423 leee 추가함. 
	public List<FoodVO> findSasangGoodIngredientMainBySasangNameResult(String sasang){
		log.info("sasang 서비스 이름 확인 1 : " + sasang);
		return foodDAO.findSasangGoodIngredientMainBySasangNameResult(sasang);
		
	}

	// 0501 사상체질 별 음식 보여주기 .
	public List<FoodVO> findSasangFoodByTaeumin(String sasang){
		return foodDAO.findSasangGoodIngredientMainBySasangName(sasang);
	}

	// 0501 사상체질 별 음식 보여주기 .
	public int countSasangFoodByTaeumin(String sasang){
		return foodDAO.countSasangGoodIngredientMainBySasangName(sasang);
	}

	// 0501 음식 유형 에 따라 보여주기. 
	public List<FoodVO> findByFoodTypeAndFoodCateCode(int page, int limit, int foodType){
		
		if(foodType == 0) {
			Pageable pageable = PageRequest.of(page-1, limit, Sort.by(Direction.DESC, "foodCode"));
			return foodDAO.findAll(pageable).getContent(); 
		}else {
			return foodDAO.findByFoodTypeAndFoodCateCode(page, limit, foodType);
		}
	}
	
	//0508 음식유형 이름만 가져옴. 
	public String findByFoodTypeName(int foodType){
		return foodDAO.findByFoodTypeName(foodType);
	}

	// 0501 - PAGING 사상체질 별 음식 보여주기 .
	public List<FoodVO> findSasangGoodIngredientMainBySasangNameAndPaging(int page, int limit, String sasang){
		return foodDAO.findSasangGoodIngredientMainBySasangNameAndPaging(page, limit, sasang);
	}
	
	// 0423 leee 추가함. 
	public List<FoodVO> findByFoodIngredientMainInsideIn(List<String> sasang){
		log.info("sasang 서비스 이름 확인 2: " + sasang);
		return foodDAO.findByFoodIngredientMainInsideIn(sasang);
	}
	
	// 0424 leee 추가함. 쉼표로 컬럼 내용이 많이 들어간 애들을 다 비교해서 가져옴  
	public List<FoodVO> findByFoodIngredientMainInsideLike(String sasang){
		log.info("sasang find 서비스 재료 확인 >>: " + sasang);
		return foodDAO.findByFoodName(sasang);
		
	}
	
	@Transactional(readOnly = true)
	public int selectFoodsCount() {
		
		return (int)foodDAO.count();
	} //
	
	@Transactional(readOnly = true)
	public List<FoodVO> selectFoodsByPaging(int currPage, int limit) {
				
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "foodDate"));
		return foodDAO.findAll(pageable).getContent();
	} //

	@Transactional(readOnly = true)
	public FoodVO selectFood(int foodCode) {
		
		return foodDAO.findById(foodCode);
	}

	@Transactional(readOnly = true)
	public int selectFoodsCountBySearching(String searchKey, String searchWord, int foodType) {
		
		if(foodType == 0) {//전체검색
			
			if(searchKey.equals("foodName")) {// 레시피명 검색
				return foodDAO.countByFoodNameContainingFoodTypeAll(searchWord);
			} else { // 재료명 검색
				return foodDAO.countByFoodIngredientContainingFoodTypeAll(searchWord);
			}

		} else {// 카테고리별 검색

			if(searchKey.equals("foodName")) {// 레시피명 검색
				return foodDAO.countByFoodNameContainingAndFoodType(searchWord, foodType);
			} else { // 재료명 검색
				return foodDAO.countByFoodIngredientContainingAndFoodType(searchWord, foodType);
			}
			
		}		
		
	}
	// 체질 검색 카운트
	@Transactional(readOnly = true)
	public int selectSasangFoodsCountBySearching(String searchKey, String searchWord, int foodType, String sasangType) {
		
		if(foodType == 0) {//전체검색
			
			if(searchKey.equals("foodName")) {// 레시피명 검색
				return foodDAO.countBySasangFoodNameContainingFoodTypeAll(searchWord,sasangType);
			} else { // 재료명 검색
				return foodDAO.countBySasangFoodIngredientContainingFoodTypeAll(searchWord,sasangType);
			}
			
		} else {// 카테고리별 검색
			
			if(searchKey.equals("foodName")) {// 레시피명 검색
				return foodDAO.countBySasangFoodNameContainingAndFoodType(searchWord, sasangType, foodType);
			} else { // 재료명 검색
				return foodDAO.countBySasangFoodIngredientContainingAndFoodType(searchWord, sasangType, foodType);
			}
			
		}		
		
	}

	@Transactional(readOnly = true)
	public List<FoodVO> selectFoodsBySearching(int currPage, int limit, String searchKey, String searchWord) {
		
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "foodCode"));
		
		return searchKey.equals("FOOD_NAME") ? foodDAO.findByfoodNameContaining(searchWord, pageable).getContent() :
			   foodDAO.findByfoodIngredientMainViewContaining(searchWord, pageable).getContent(); // 0415 leee 수정
	}
	
//	//0501 건강식 레시피 검색 
//	@Transactional(readOnly = true)
//	public List<FoodVO> findBySeachingAndPaging(int currPage, int limit, String searchKey, String searchWord) {
//		
//		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "foodCode"));
//		
//		return searchKey.equals("FOOD_NAME") ? foodDAO.findBySeachingAndPaging(currPage, limit, searchKey, searchWord):
//			foodDAO.findByfoodIngredientMainViewContaining(searchWord, pageable).getContent(); // 0415 leee 수정
//	}
	
	//0501 건강식 레시피 검색 
	@Transactional(readOnly = true)
	public List<FoodVO> findBySeachingAndPaging(int currPage, int limit, String searchKey, String searchWord, int foodType) {
		
			if(foodType == 0) {//전체검색
			
				if(searchKey.equals("foodName")) {// 레시피명 검색
					return foodDAO.findBySeachingFoodNameAndPaging(currPage, limit, searchWord);
				} else { // 재료명 검색
					return foodDAO.findBySeachingFoodIngredientAndPaging(currPage, limit, searchWord);
				}
		
			} else {// 카테고리별 검색
		
				if(searchKey.equals("foodName")) {// 레시피명 검색
					return foodDAO.findBySeachingFoodNameAndFoodTypePaging(currPage, limit, foodType, searchWord);
				} else { // 재료명 검색
					return foodDAO.findBySeachingFoodIngredientAndFoodTypePaging(currPage, limit, foodType, searchWord);
				}
				
			}		
	}
	
	// 사상 레시피 검색 유형+재료
	@Transactional(readOnly = true)
	public List<FoodVO> findSearchSasangGoodIngredientMainBySasangIngredientAndPaging(int page, int limit, String searchKey, String searchWord, int foodType, String sasangType) {
		
		if(foodType == 0) {//전체검색
			
			if(searchKey.equals("foodName")) {// 레시피명 검색
				return foodDAO.findSearchSasangGoodIngredientMainBySasangNameAndAllPaging(page, limit, searchWord, sasangType);
			} else { // 재료명 검색
				return foodDAO.findSearchSasangGoodIngredientMainBySasangIngredientAndAllPaging(page, limit, searchWord, sasangType);
			}
			
		} else {// 카테고리별 검색
			
			if(searchKey.equals("foodName")) {// 레시피명 검색
				return foodDAO.findSearchSasangGoodIngredientMainBySasangNameAndPaging(page, limit, searchWord, foodType, sasangType);
			} else { // 재료명 검색
				return foodDAO.findSearchSasangGoodIngredientMainBySasangIngredientAndPaging(page, limit, searchWord, foodType, sasangType);
			}
			
		}		
	}
	
	// imgUploadPath = /food/image/
	public List<Integer> getImageList(String str, String imgUploadPath) {

		log.info("FoodService.getImageList");
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
				// "/food/image/".length()
				initPos += imgUploadPath.length();
				log.info("이미지 파일 시작 위치 : " + initPos);
				
				// 추출된 문자열
				// ex) 41 (.../food/image/41" : upload_file_tbl 테이블의 삽입 이미지 PK(기본키))
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
	public FoodVO updateFood(FoodVO foodVO) {
		
		return foodDAO.save(foodVO);
	}
	
//	@Transactional(rollbackFor = Exception.class)
//	public List<FoodVO> selectReplysById(int foodCode) {
//		
//		return foodDAO.findByFoodReSeq(foodCode);
//	}
	
	@Transactional(readOnly = true)
	public int selectFoodsCountWithoutReplies() {
		
//		return (int)foodDAO.findByFoodCode(0); // (댓글 아닌)원글만 추출 : food_re_ref = 0
		return (int) foodDAO.countBy();
	} //

	//0501 음식 유형별
	@Transactional(readOnly = true)
	public int countByFoodType(int foodType) {
		
		if(foodType == 0) {
			return (int) foodDAO.count();
		}else {
			return (int) foodDAO.countByFoodType(foodType);
		} 
	} //

	//0501 체질별 레시피 추천 페이징
	@Transactional(readOnly = true)
	public int countByfoodNameLike(String foodName) {
		
//		return (int)foodDAO.findByFoodCode(0); // (댓글 아닌)원글만 추출 : food_re_ref = 0
		return foodDAO.countByfoodNameLike(foodName);
	} //
	
	@Transactional(readOnly = true)
	public List<FoodVO> selectFoodsByPagingWithoutReplies(int currPage, int limit) {
				
		Pageable pageable = PageRequest.of(currPage-1, limit, Sort.by(Direction.DESC, "foodCode"));
		// return foodDAO.findAll(pageable).getContent();
		return foodDAO.findAll(pageable).getContent(); // 0415 leee findAll로 수정함. 
	} //

	@Transactional(rollbackFor = Exception.class)
	public boolean deleteReplysById(int foodCode) {
		
		boolean result = false;
		
		try {
			foodDAO.deleteById(foodCode);
			result = true;
		} catch (Exception e) {
			log.error("deleteReplyById error : {}", e);
			result = false;
		}
		
		return result;
	}
	
//	// 댓글 수량 조회
//	@Transactional(readOnly = true)
//	public int selectFoodsCountWithReplies(int foodCode) {
//		
//		return (int)foodDAO.countByFoodReSeq(foodCode); // 댓글의 갯수 추출 : food_re_ref = foodCode
//	} //
	
	
	// 게시글(원글) 삭제
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteById(int foodCode) {
		
		boolean result = false;
		
		try {
			foodDAO.deleteById(foodCode);
			result = true;
		} catch (Exception e) {
			log.error("deleteById error : {}", e);
			result = false;
		}
		
		return result;
	}
	
	// 게시글 조회수 갱신
	@Transactional(rollbackFor = Exception.class)
	public boolean updateFoodReadcountByFoodCode(int foodCode) {
		
		boolean result = false;
		
		try {
			foodDAO.updateFoodReadcountByFoodCode(foodCode);
			result = true;
		} catch (Exception e) {
			log.error("updateFoodReadcountByfoodCode error : {}", e);
			result = false;
		}
		
		return result;
	}
	
}
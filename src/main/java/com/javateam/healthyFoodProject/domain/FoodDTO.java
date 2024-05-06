package com.javateam.healthyFoodProject.domain;

import java.sql.Date;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FoodDTO {
	
	private int foodCode; // 게시글 번호
	private String foodName; // 게시글 작성자 이메일아이디
	private String foodImg; // 게시글 작성자 별명
	private String foodImgOrigin; // 게시글 작성자 이미지
	private String foodIntroduce; // 원게시글 번호
	private String foodIngredientMainInside; //게시글 답글 순서
	private String foodIngredientSubInside; // 게시글 제목
	private String foodIngredientMainView; // 게시글 내용
	private String foodIngredientSubView; // 게시글 썸네일
	private String foodRecipe; // 게시글 썸네일 원본
	private Date foodDate; // 게시글 등록일
	private int foodDisplay; // 활성화 여부
//	private int boardReadCount = 0 ; // 게시글 조회수
//	private int enabled; // 활성화 여부
	private String textMulti = "text"; // 텍스트 모드(text:기본값) / 멀티미디어 모드(multi)
	
	@Override
	public String toString() {
		return "FoodDTO [foodCode=" + foodCode + ", foodName=" + foodName + ", foodImg=" + foodImg + ", foodImgOrigin="
				+ foodImgOrigin + ", foodIntroduce=" + foodIntroduce + ", foodIngredientMainInside="
				+ foodIngredientMainInside + ", foodIngredientSubInside=" + foodIngredientSubInside
				+ ", foodIngredientMainView=" + foodIngredientMainView + ", foodIngredientSubView="
				+ foodIngredientSubView + ", foodRecipe=" + foodRecipe + ", foodDate=" + foodDate + ", foodDisplay="
				+ foodDisplay + ", textMulti=" + textMulti + "]";
	}
	
	
	
	

}
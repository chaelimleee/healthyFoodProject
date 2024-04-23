/**
 * 
 */
package com.javateam.healthyFoodProject.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
//import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javateam.healthyFoodProject.util.FileUploadUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name="food_tbl")
@Slf4j
public class FoodVO implements Serializable { // 10.25 (sesssion으로 변환할 경우 에러 방지)
	
	/**
	 *  FOOD_CODE                   음식 번호
		FOOD_NAME                   음식 이름
		FOOD_IMG                    음식 썸네일
		FOOD_IMG_ORIGIN             음식 썸네일 원본
		FOOD_INTRODUCE              음식 소개
		FOOD_INGREDIENT_MAIN_INSIDE 음식 주재료 검색
		FOOD_INGREDIENT_SUB_INSIDE  음식 부재료 검색
		FOOD_INGREDIENT_MAIN_VIEW   음식 주재료
		FOOD_INGREDIENT_SUB_VIEW    음식 부재료
		FOOD_RECIPE                 음식 조리법
		FOOD_DATE                   음식 등록일
		FOOD_DISPLAY                음식 활성화
	 */
	private static final Long serialVersionUID = 1L;

	/** 음식 번호 */
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		    generator = "FOOD_SEQ_GENERATOR")
			@SequenceGenerator(
			name = "FOOD_SEQ_GENERATOR",
			sequenceName = "food_seq",
			initialValue = 1,
			allocationSize = 1)
	@Column(name = "FOOD_CODE") 
	private int foodCode; 
	
	/** 음식 이름 */
	@Column(name = "FOOD_NAME") 
	private String foodName; 
	
	/** 음식 썸네일*/
	@Column(name = "FOOD_IMG") 
	private String foodImg; 

	/** 음식 썸네일 원본 */
	@Column(name = "FOOD_IMG_ORIGIN") 
	private String foodImgOrigin; 
	
	/** 음식 소개 */
	@Column(name = "FOOD_INTRODUCE") 
	private String foodIntroduce; 
	
	/** 음식 주재료 검색 */
	@Column(name = "FOOD_INGREDIENT_MAIN_INSIDE") 
	private String foodIngredientMainInside; 
	
	/** 음식 부재료 검색 */
	@Column(name = "FOOD_INGREDIENT_SUB_INSIDE") 
	private String foodIngredientSubInside; 
	
	/** 음식 주재료  */
	@Column(name = "FOOD_INGREDIENT_MAIN_VIEW") 
	private String foodIngredientMainView; 
	
	/** 음식 부재료 */
	@Column(name = "FOOD_INGREDIENT_SUB_VIEW") 
	private String foodIngredientSubView; 
	
	/** 음식 조리법 */
	@Column(name = "FOOD_RECIPE") 
	private String foodRecipe; 
	
	/** 음식 등록일 */
	//@CreationTimestamp // 작성 날짜(기본값) 생성
	@Column(name = "FOOD_DATE")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul") // JSON 변환시 "년월일 및 시분초"까지 모두 출력 
	private Date foodDate; 
	
	/** 음식 활성화 */
	@Column(name = "FOOD_DISPLAY") 
	private int foodDisplay; 
	
//	/** 게시글 조회수 */
//	private int boardReadCount; 

//	/** 활성화 여부 */
//	@Column(name = "ENABLED")
//	private int enabled; 
	
	public FoodVO() {
		
	}
	
	public FoodVO(FoodDTO food) {
		super();
		this.foodCode = food.getFoodCode();
		this.foodName = food.getFoodName();
		this.foodImg = food.getFoodImg();
		this.foodImgOrigin = food.getFoodImgOrigin();
		this.foodIntroduce = food.getFoodIntroduce();
		this.foodIngredientMainInside = food.getFoodIngredientMainInside();
		this.foodIngredientSubInside = food.getFoodIngredientSubInside();
		this.foodIngredientMainView = food.getFoodIngredientMainView();
		this.foodIngredientSubView = food.getFoodIngredientSubView();
		this.foodRecipe = food.getFoodRecipe();
		this.foodDisplay = food.getFoodDisplay();
		this.foodDate = food.getFoodDate();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FoodVO [foodCode=").append(foodCode)
				.append(", foodName=").append(foodName)
				.append(", foodImg=").append(foodImg)
				.append(", foodImgOrigin=").append(foodImgOrigin)
				.append(", foodIntroduce=").append(foodIntroduce)
				.append(", foodIngredientMainInside=").append(foodIngredientMainInside)
				.append(", foodIngredientSubInside=").append(foodIngredientSubInside)
				.append(", foodIngredientMainView=").append(foodIngredientMainView)
				.append(", foodIngredientSubView=").append(foodIngredientSubView)
				.append(", foodRecipe=").append(foodRecipe)
				.append(", foodDisplay=").append(foodDisplay)
				.append(", foodDate=").append(foodDate)
				.append("]");
		return builder.toString();
	}
	
	
	

//	public int getEnabled() {
//		return enabled;
//	}
//
//	public void setEnabled(int enabled) {
//		this.enabled = enabled;
//	}

	public int getFoodCode() {
		return foodCode;
	}

	public void setFoodCode(int foodCode) {
		this.foodCode = foodCode;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public String getFoodImg() {
		return foodImg;
	}

	public void setFoodImg(String foodImg) {
		this.foodImg = foodImg;
	}

	public String getFoodImgOrigin() {
		return foodImgOrigin;
	}

	public void setFoodImgOrigin(String foodImgOrigin) {
		this.foodImgOrigin = foodImgOrigin;
	}

	public String getFoodIntroduce() {
		return foodIntroduce;
	}

	public void setFoodIntroduce(String foodIntroduce) {
		this.foodIntroduce = foodIntroduce;
	}

	public String getFoodIngredientMainInside() {
		return foodIngredientMainInside;
	}

	public void setFoodIngredientMainInside(String foodIngredientMainInside) {
		this.foodIngredientMainInside = foodIngredientMainInside;
	}

	public String getFoodIngredientSubInside() {
		return foodIngredientSubInside;
	}

	public void setFoodIngredientSubInside(String foodIngredientSubInside) {
		this.foodIngredientSubInside = foodIngredientSubInside;
	}

	public String getFoodIngredientMainView() {
		return foodIngredientMainView;
	}

	public void setFoodIngredientMainView(String foodIngredientMainView) {
		this.foodIngredientMainView = foodIngredientMainView;
	}

	public String getFoodIngredientSubView() {
		return foodIngredientSubView;
	}

	public void setFoodIngredientSubView(String foodIngredientSubView) {
		this.foodIngredientSubView = foodIngredientSubView;
	}

	public String getFoodRecipe() {
		return foodRecipe;
	}

	public void setFoodRecipe(String foodRecipe) {
		this.foodRecipe = foodRecipe;
	}

	public Date getFoodDate() {
		return foodDate;
	}

	public void setFoodDate(Date foodDate) {
		this.foodDate = foodDate;
	}

	public int getFoodDisplay() {
		return foodDisplay;
	}

	public void setFoodDisplay(int foodDisplay) {
		this.foodDisplay = foodDisplay;
	}

}
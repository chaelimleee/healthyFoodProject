package com.javateam.healthyFoodProject.dao;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.healthyFoodProject.domain.FoodVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class FindAllByFoodIngredientMainInsideTest {

	@Autowired
	FoodDAO foodDAO;
	
	@Test
	public void test() {

		log.info("테스트 >>");
		
		//FoodVO foodVO = foodDAO.findById(22);
		List<FoodVO> list = foodDAO.findBySasangName("태음인");
		
		log.info("list 확인 >> " + list.get(0));
		
//		List<FoodVO> list = foodDAO.findAllByFoodIngredientMainInside("태음인");
//		
//		log.info("list get(0) >> " + list.get(0));
	}
}

package com.javateam.healthyFoodProject.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.healthyFoodProject.domain.FoodVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class FindByFoodIngredientMainInsideInTest {

	@Autowired
	FoodDAO foodDAO;
	
	@Test
	public void test() {

		log.info("테스트 >>");
		
		//List<String> list3 = foodDAO.findSasangGoodIngredientMainBySasangName("태음인");
		
//		List<String> list = new ArrayList<>();
//		list.addAll(Arrays.asList(new String[]{"감자","고구마","갈치"}));
		//List<FoodVO> list2 = foodDAO.findByFoodIngredientMainInsideIn(list3);
		
		//log.info("list 확인 >> " + list2.get(0));
		
	}
}

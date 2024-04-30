package com.javateam.healthyFoodProject.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.healthyFoodProject.domain.BoardVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class FindByBoardContentTest {
	
	@Autowired
	BoardDAO boardDAO;
	
	@Test
	public void test() {
	
//		List<BoardVO> list = boardDAO.findByBoardContentContaining("비싸다",1, 10);
		List<BoardVO> list = boardDAO.findByBoardContentContaining(";;",1, 10);
		log.info("List 크기 : {}", list.size());
		assertEquals(1,list.size());
		
		assertEquals("쏭쏭쏭",list.get(0).getMemberNick());
	}

}
	

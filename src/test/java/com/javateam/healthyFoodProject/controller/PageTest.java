package com.javateam.healthyFoodProject.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.healthyFoodProject.domain.PageVO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class PageTest {
	
//	@Test
//	public void test(){
//		
//		for(int i = 21; i <= 30; i++) {
//			assertEquals(21, PageVO.getStartPage(i,20/2));
//		}
//	}

//	@Test
//	public void test2(){
//		
//		for(int i = 11; i <= 20; i++) {
//			assertEquals(20, PageVO.getEndPage(i,20/2));
//		}
//	}
	
//	@Test
//	public void test3(){
//		
//		assertEquals(1, PageVO.getStartPage(2,20/2));
//		assertEquals(10, PageVO.getEndPage(2,20/2));
//		
//	}

	@Test
	public void test4(){
		
		int currPage = 16;
		int limit = 20;
		int endPage = PageVO.getEndPage(currPage, limit/2);
		int maxPage = 17;
//		int nextPage = currPage + 1 > endPage ? endPage : currPage + 1 ;
		int nextPage = currPage + 1 > maxPage ? maxPage : currPage + 1 ;
		
		assertEquals(17, nextPage);
		
	}
}

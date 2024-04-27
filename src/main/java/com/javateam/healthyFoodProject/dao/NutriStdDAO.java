package com.javateam.healthyFoodProject.dao;

import org.springframework.data.repository.CrudRepository;

import org.springframework.data.repository.CrudRepository;

import com.javateam.healthyFoodProject.domain.NutriStdVO;


public interface NutriStdDAO extends CrudRepository<NutriStdVO, Integer> {

	public NutriStdVO findByGenderAndAge(char gender, String ageRange);
	
}
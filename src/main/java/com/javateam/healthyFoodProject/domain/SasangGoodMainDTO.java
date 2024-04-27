package com.javateam.healthyFoodProject.domain;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j
public class SasangGoodMainDTO {
	/** 
	 * 
	 * 체질별 좋은 주재료
	 * 	SASANG_NAME	  체질 명
		SASANG_GOOD_INGREDIENT_MAIN	 해당 재료
	 */
	
	/** 1. 체질명 */
	private String sasangName;
	
	/** 1. 해당 재료 */
	private String sasangGoodIngredientMain;
	
	
	// DTO => MultiValueMap
	public static MultiValueMap<String, String> toMap(SasangGoodMainDTO memberDTO) 
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		Field[] fields = memberDTO.getClass().getDeclaredFields();
				
		for (Field fld : fields) {
			
			// 로그 객체(log)는 제외
			if (fld.getName().equals("log") == false) {
				
				Method method = memberDTO.getClass().getDeclaredMethod("get"+StringUtils.capitalize(fld.getName()));
				
				if (fld.getName().equals("memberDate")) {
				
					map.put(fld.getName(), Arrays.asList(new SimpleDateFormat("yyyy-MM-dd").format(method.invoke(memberDTO))));
					
				} else {
					map.put(fld.getName(), Arrays.asList(method.invoke(memberDTO).toString()));
				} //
				
			} // 로그 객체는 제외
			
		} // for
		
		return map;		
	}

	public SasangGoodMainDTO(Map<String, Object> requestMap) {
		
		Set<String> set = requestMap.keySet();
		Iterator<String> it = set.iterator();
		Field field; // reflection 정보 활용
		
		while (it.hasNext()) {
			
			 String fldName = it.next();
		
			 try {
		    		// DTO와 1:1 대응되는 필드들 처리 
			    	try {
							field = this.getClass().getDeclaredField(fldName);
							field.setAccessible(true);
							
							if (!fldName.equals("memberDate")) {
								field.set(this, requestMap.get(fldName));
							}
							
					} catch (NoSuchFieldException e) {
						
						// 만약 VO와 1:1 대응되지 않는 인자일 경우는 이 부분에서 입력처리합니다.
						log.info("인자와 필드가 일치하지 않습니다."); 
						
					} // try
					
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) { 
				e.printStackTrace();
			} // try
			 
		} // while	 
		
	} //
	
}	

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
public class MemberDTO {
	/** 
	 * leee
	 * 0401 수정 완.
	 * 0402 생일 수정 완. 
	 * 		셀렉트 박스 사용으로 생일을 3개로 나눠서 받아 온 후 , 매퍼에서 합쳐서 저장. 
	 * 
	 * 14개 컬럼
	 * 	MEMBER_EMAIL	  회원 이메일
		MEMBER_PW	      회원 비밀번호
		MEMBER_NAME	      회원 이름
		MEMBER_NICK	      회원 별명
		MEMBER_MOBILE	  회원 휴대전화
		MEMBER_BIRTH	  회원 생일
		MEMBER_ZIP    	  회원 우편번호
		MEMBER_ADDRESS1	  회원 기본주소
		MEMBER_ADDRESS2	  회원 상세주소
		MERBER_IMG	      회원 이미지
		MERBER_IMG_ORIGIN 회원 이미지 원본
		MERBER_DATE	      회원 가입일
		MEMBER_ROLE	      회원 권한
		MEMBER_DISPLAY	  회원 활성화
	 */
	
	
	/** 1. 회원 이메일 아이디 */
	private String memberEmail;
	
	/** 2. 회원 패쓰워드 */
	private String memberPw;
	
	/** 3. 회원 이름 */
	private String memberName;
	
	/** 4. 회원 별명 */
	private String memberNick;
	
	/** 5. 회원 휴대전화 */
	private String memberMobile;

	/** 6. 회원 생년월일 */
	private Date memberBirth;
	
	/** 6. 회원 생일 년도 */ // 추가수정
	private String memberYear;
	
	/** 6. 회원 생일 월 */  // 추가수정
	private String memberMonth;
	
	/** 6. 회원 생일 일 */  // 추가수정
	private String memberDay;
	
	/** 6. 회원 생일 더함.  */  // 추가수정
	private String memberBirthAll;
	
	/** 7. 회원 우편번호 */
	private String memberZip;
	
	/** 8. 회원 기본 도로명 주소 */
	private String memberAddress1;
	
	/** 9. 회원 상세 주소 */
	private String memberAddress2;

	/** 10. 회원 이미지 */
	private String memberImg;
	
	/** 11. 회원 이미지 원본 */
	private String memberImgOrigin;
	
	/** 12. 회원 가입일 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date memberDate;
	
	/** 13. 회원 롤 */
	private String memberRole;
	
	/** 14. 회원 활성화 여부 */
	private int memberDisplay;
	
	public static String formatBirthAll(String memberYear, String memberMonth, String memberDay) {
		return memberYear + "-" + memberMonth +"-" + memberDay ;
	}
	
	// DTO => MultiValueMap
	public static MultiValueMap<String, String> toMap(MemberDTO memberDTO) 
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

	public MemberDTO(Map<String, Object> requestMap) {
		
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

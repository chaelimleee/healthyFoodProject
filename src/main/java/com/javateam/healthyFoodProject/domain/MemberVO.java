/**
 * 
 */
package com.javateam.healthyFoodProject.domain;

import java.io.Serializable;
import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name="member_tbl")
public class MemberVO implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "MEMBER_EMAIL") 
	private String memberEmail; 
	
	/** 게시글 작성자 이메일 아이디*/
	@Column(name = "MEMBER_PW")
	private String memberPw;

	/** 게시글 작성자 별명*/
	@Column(name = "MEMBER_NAME")
	private String memberName; 
	
	/** 게시글 작성자 별명*/
	@Column(name = "MEMBER_NICK")
	private String memberNick; 

	/** 게시글 작성자 이미지*/
	@Column(name = "MEMBER_MOBILE")
	private String memberMobile; 
	
	/** 원게시글 번호 */
	@Column(name = "MEMBER_BIRTH")
	private Date memberBirth; 
	
	/** 원게시글 번호 */
	@Column(name = "MEMBER_YEAR")
	private String memberYear; 

	/** 원게시글 번호 */
	@Column(name = "MEMBER_MONTH")
	private String memberMonth;
	
	/** 원게시글 번호 */
	@Column(name = "MEMBER_DAY")
	private String memberDay;
	
	/** 원게시글 번호 */
	private String memberBirthAll;
	
	/** 게시글 답글 순서 */
	@Column(name = "MEMBER_ZIP")
	private String memberZip; 
	
	/** 게시글 제목 */
	@Column(name = "MEMBER_ADDRESS1")
	private String memberAddress1; 

	/** 게시글 제목 */
	@Column(name = "MEMBER_ADDRESS2")
	private String memberAddress2;
	
	/** 게시글 제목 */
	@Column(name = "MEMBER_IMG")
	private String memberImg; 
	
	/** 게시글 제목 */
	@Column(name = "MEMBER_IMG_ORIGIN")
	private String memberImgOrigin; 
	
	/** 12. 회원 가입일 */
	@Column(name = "MEMBER_DATE")
	private Date memberDate;
	
	/** 12. 회원 가입일 */
	@Column(name = "MEMBER_ROLE")
	private Date memberRole;
	
	/** 12. 회원 가입일 */
	@Column(name = "ENABLED")
	private int enabled;
	
}
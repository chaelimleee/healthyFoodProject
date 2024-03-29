package com.javateam.healthyFoodProject.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateDTO extends MemberDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 회원 패쓰워드(수정) */
	private String password1;

	/** 회원 패쓰워드(확인) */
	private String password2;

	public MemberUpdateDTO(MemberDTO memberDTO) {

		this.setMemberEmail(memberDTO.getMemberEmail());
		this.setMemberPw(memberDTO.getMemberPw());
		this.setMemberNick(memberDTO.getMemberNick());
		this.setMemberMobile(memberDTO.getMemberMobile());
		this.setMemberZip(memberDTO.getMemberZip());
		this.setMemberAddress1(memberDTO.getMemberAddress1());
		this.setMemberAddress2(memberDTO.getMemberAddress2());
		this.setRegDate(memberDTO.getRegDate());
		this.setMemberRole(memberDTO.getMemberRole());
		this.setMemberDisplay(memberDTO.getMemberDisplay());
	}

	

}
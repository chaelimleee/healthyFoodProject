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
	 * 0401 leee 수정 완.
	 */
	private static final long serialVersionUID = 1L;

	/** 회원 패쓰워드(수정) */
	private String password1;

	/** 회원 패쓰워드(확인) */
	private String password2;

	public MemberUpdateDTO(MemberDTO memberDTO) {

		this.setMemberEmail(memberDTO.getMemberEmail());
		this.setMemberPw(memberDTO.getMemberPw());
		this.setMemberName(memberDTO.getMemberName());
		this.setMemberNick(memberDTO.getMemberNick());
		this.setMemberMobile(memberDTO.getMemberMobile());
		this.setMemberBirth(memberDTO.getMemberBirth());
		this.setMemberZip(memberDTO.getMemberZip());
		this.setMemberAddress1(memberDTO.getMemberAddress1());
		this.setMemberAddress2(memberDTO.getMemberAddress2());
		this.setMemberImg(memberDTO.getMemberImg());
		this.setMemberImgOrigin(memberDTO.getMemberImgOrigin());
		this.setRegDate(memberDTO.getRegDate());
		this.setMemberRole(memberDTO.getMemberRole());
		this.setMemberDisplay(memberDTO.getMemberDisplay());
	}

	@Override
	public String toString() {
		return "MemberUpdateDTO [password1=" + password1 + ", password2=" + password2 + ", getMemberEmail()="
				+ getMemberEmail() + ", getMemberPw()=" + getMemberPw() + ", getMemberName()=" + getMemberName()
				+ ", getMemberNick()=" + getMemberNick() + ", getMemberMobile()=" + getMemberMobile()
				+ ", getMemberBirth()=" + getMemberBirth() + ", getMemberZip()=" + getMemberZip()
				+ ", getMemberAddress1()=" + getMemberAddress1() + ", getMemberAddress2()=" + getMemberAddress2()
				+ ", getMemberImg()=" + getMemberImg() + ", getMemberImgOrigin()=" + getMemberImgOrigin()
				+ ", getRegDate()=" + getRegDate() + ", getMemberRole()=" + getMemberRole() + ", getMemberDisplay()="
				+ getMemberDisplay() + ", getClass()=" + getClass() + "]";
	}

	

}
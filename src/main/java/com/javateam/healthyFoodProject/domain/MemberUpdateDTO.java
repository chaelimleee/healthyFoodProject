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

		this.setEmail(memberDTO.getEmail());
		this.setPassword(memberDTO.getPassword());
		this.setNick(memberDTO.getNick());
		this.setMobile(memberDTO.getMobile());
		this.setZip(memberDTO.getZip());
		this.setRoadAddress(memberDTO.getRoadAddress());
		this.setDetailAddress(memberDTO.getDetailAddress());
		this.setRegDate(memberDTO.getRegDate());
		this.setDisplay(memberDTO.getDisplay());
	}

	@Override
	public String toString() {
		return "MemberUpdateDTO [password1=" + password1 + ", password2=" + password2 + ", getEmail()=" + getEmail()
				+ ", getPassword()=" + getPassword() + ", getNick()=" + getNick() + ", getMobile()=" + getMobile()
				+ ", getZip()=" + getZip() + ", getRoadAddress()=" + getRoadAddress() + "getDetailAddress()="
				+ getDetailAddress() + ", getRegDate()=" + getRegDate() + ", getDisplay()=" + getDisplay() + "]";
	}

}
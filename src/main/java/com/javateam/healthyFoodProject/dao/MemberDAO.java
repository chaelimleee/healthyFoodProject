package com.javateam.healthyFoodProject.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.DateUtils;

import com.javateam.healthyFoodProject.domain.MemberDTO;
import com.javateam.healthyFoodProject.domain.MemberUpdateDTO;
import com.javateam.healthyFoodProject.domain.Role;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MemberDAO {

	@Autowired
	SqlSession sqlSession;
	
//	public void insertMemberBirth(MemberDTO memberDTO) {
//
//		sqlSession.insert("mapper.Member.insertMember", memberDTO);
//	}
	
	public void insertMember(MemberDTO memberDTO) throws ParseException {
		String memberBirth = MemberDTO.formatBirthAll(memberDTO.getMemberYear(), memberDTO.getMemberMonth(), memberDTO.getMemberDay());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date memberD = new Date();
		
		memberD = format.parse(memberBirth);
		log.info("memberBirth ==>" + memberBirth);
		log.info("memberD ==>" + memberD);
		memberDTO.setMemberBirthAll(memberD);
		
		sqlSession.insert("mapper.Member.insertMember", memberDTO);
	}
	
	public void insertRole(Role role) {
		sqlSession.insert("mapper.Member.insertRole", role);
	}
	
//	public void insertMemberSasang(String sasang) {
//		sqlSession.insert("mapper.Member.insertMemberSasang", sasang);
//	}
	
	//0516 사상체질 dB저장 수정
	public void updateMemberSasang(MemberDTO memberDTO) {
		sqlSession.insert("mapper.Member.updateMemberSasang", memberDTO);
	}
	
	public boolean hasFld(String fld, String val) {
		
		Map<String, String> map = new HashMap<>();
		map.put("fld", fld);
		map.put("val", val);
		
		return (int)sqlSession.selectOne("mapper.Member.hasFld", map) == 1 ? true : false;
	} //
	
	public MemberDTO selectMember(String id) {
		
		return sqlSession.selectOne("mapper.Member.selectMember", id); 
	}
	
	public boolean hasFldForUpdate(String id, String fld, String val) {
		
		Map<String, String> map = new HashMap<>();
		map.put("id", id);
		map.put("fld", fld);
		map.put("val", val);
		
		return (int)sqlSession.selectOne("mapper.Member.hasFldForUpdate", map) == 1 ? true : false;
	} //
	
	public void updateMember(MemberDTO memberDTO) {
		log.info("memberDAO.updateMember memberDTO =>" + memberDTO);
		log.info("memberDAO.updateMember pw =>" + (memberDTO.getMemberPw() == null));
		sqlSession.update("mapper.Member.updateMember", memberDTO);
	}
	
	public int selectMembersCount() {
		return sqlSession.selectOne("mapper.Member.selectMembersCount");
	}
	
	public List<MemberDTO> selectMembersByPaging(int page, int limit) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("page",  page);
		map.put("limit", limit);
		
		return sqlSession.selectList("mapper.Member.selectMembersByPaging", map);
	}
	
	public List<Map<String, Object>> selectMembersWithRolesByPaging(int page, int limit) {
	
		Map<String, Integer> map = new HashMap<>();
		map.put("page",  page);
		map.put("limit", limit);
		
		return sqlSession.selectList("mapper.Member.selectMembersWithRolesByPaging", map);
	} //

	public List<String> selectRolesById(String id) {
		
		return sqlSession.selectList("mapper.Member.selectRolesById", id);
	}
	
	public void deleteRolesByEmail(String id) {
		
		sqlSession.delete("mapper.Member.deleteRolesByEmail", id);
	}
	
	public List<Map<String, Object>> selectMembersWithRolesBySearching(int page, int limit, String searchKey, String searchWord) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("page",  page);
		map.put("limit", limit);
		map.put("searchKey", searchKey);
		map.put("searchWord", searchWord);
		
		return sqlSession.selectList("mapper.Member.selectMembersWithRolesBySearching", map);
	} //
	
	public int selectMembersCountBySearching(String searchKey, String searchWord) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("searchKey", searchKey);
		map.put("searchWord", searchWord);
		
		return (int)sqlSession.selectOne("mapper.Member.selectMembersCountBySearching", map);
	}
	
	public void changeEnabled(String id, int enabled) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("id", id);
		map.put("enabled", enabled);
		
		log.info("상태 정보 : {}", enabled);
		
		sqlSession.update("mapper.Member.changeEnabled", map);
	}

//	public void deleteMemberById(String id) {
//		
//		sqlSession.delete("mapper.Member.deleteMemberById", id);
//	}
	
	// 회원 탈퇴 email
	public void deleteMemberByEmail(String memberEmail) {
		sqlSession.delete("mapper.Member.deleteMemberByEmail", memberEmail);

	}
	
	
	
}
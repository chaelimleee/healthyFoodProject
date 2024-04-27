package com.javateam.healthyFoodProject.dao;

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
public class SasangGoodMainDAO {

	@Autowired
	SqlSession sqlSession;
	
	public MemberDTO selectMember(String id) {
		
		return sqlSession.selectOne("mapper.Member.selectMember", id); 
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

	
}
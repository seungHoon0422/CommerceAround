package com.commerce.model.dao;

import java.sql.SQLException;

import com.commerce.model.MemberDto;
import com.commerce.model.util.exception.NotFoundEntityException;

public interface MemberDao {
	
	/** 로그인 */
	MemberDto login(String id, String pass) throws SQLException;//추후에 Exception은 Invalid~~예외로 바꿔주기
	
	/** 해당 id 중복체크 */
	int loginCheck(String id) throws SQLException;
	
	/** 회원가입  */
	void registMember(MemberDto memberDto) throws SQLException;
	
	/** 회원정보 수정 */
	void updateMemberInfo(MemberDto memberDto) throws SQLException, NotFoundEntityException;
	
}

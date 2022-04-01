package com.commerce.model.service;

import java.sql.SQLException;

import com.commerce.model.MemberDto;
import com.commerce.model.util.exception.DuplicatedEntityException;
import com.commerce.model.util.exception.InvalidFormException;
import com.commerce.model.util.exception.NotFoundEntityException;

public interface MemberService {
	
	/** 로그인 */
	MemberDto login(String id, String pass) throws SQLException, InvalidFormException;
	
	/** 해당 id 중복체크 */
	int loginCheck(String id) throws SQLException, InvalidFormException;
	
	/** 회원가입  */
	void registMember(MemberDto memberDto) throws SQLException, InvalidFormException, DuplicatedEntityException;
	
	/** 회원정보 수정 */
	void updateMemberInfo(MemberDto memberDto)
			throws SQLException, InvalidFormException, NotFoundEntityException;
}

/*
 *  * 
	 * 	만약 DB에 겹치는 회원 정보 있다면 Exception발생
	 * 	(추후 사용자정의 예외(duplicateIdException으로 변경)
	 * 	https://withseungryu.tistory.com/94
 * */

package com.commerce.model.service;

import java.sql.SQLException;
import java.util.List;

import com.commerce.model.InterestedDto;
import com.commerce.model.util.exception.DuplicatedEntityException;
import com.commerce.model.util.exception.InvalidFormException;
import com.commerce.model.util.exception.NotFoundEntityException;

public interface InterestedService {
	
	/** 관심지역 리스트 */
	List<InterestedDto> getInterestedRegionList(String id)
			throws SQLException, NotFoundEntityException, InvalidFormException;
	
	/** 해당 관심지역 */
	public InterestedDto getInterestedRegion(String id, String dongCode, String middleCode)
			throws SQLException, NotFoundEntityException, InvalidFormException;
	
	/** 관심지역 등록 */
	void registRegion(String id, String dongCode, String middleCode) throws SQLException, InvalidFormException, DuplicatedEntityException;
	
	/** 관심지역 삭제 */
	void deleteInterestedRegion(String id, String dongCode, String middleCode)
			throws SQLException, InvalidFormException, NotFoundEntityException;
}

/*
 *  * 
	 * 	만약 DB에 겹치는 회원 정보 있다면 Exception발생
	 * 	(추후 사용자정의 예외(duplicateIdException으로 변경)
	 * 	https://withseungryu.tistory.com/94
 * */

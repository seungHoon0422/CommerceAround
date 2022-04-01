package com.commerce.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.commerce.model.InterestedDto;
import com.commerce.model.util.exception.DuplicatedEntityException;
import com.commerce.model.util.exception.NotFoundEntityException;

public interface InterestedDao {
	
	/** 관심지역 리스트 */
	List<InterestedDto> getInterestedRegionList(String id) throws SQLException;
	
	/** 해당 관심지역 */
	public InterestedDto getInterestedRegion(InterestedDto interestedDto)
			throws SQLException, NotFoundEntityException;
	
	/** 관심지역 등록 */
	void registRegion(InterestedDto interestedDto) throws SQLException, DuplicatedEntityException;
	
	/** 관심지역 삭제 */
	void deleteInterestedRegion(InterestedDto interestedDto) throws SQLException;
}

package com.commerce.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.commerce.model.CategoryDto;
import com.commerce.model.CommerceDto;
import com.commerce.model.ListParamDto;
import com.commerce.model.RegionDto;

public interface CommerceDao {
	
	/** 시도리스트 */
	List<RegionDto> getSidoList() throws SQLException;
	
	/** 구군리스트 */
	List<RegionDto> getGugunList(String sidoCode) throws SQLException;
	
	/** 동리스트 */
	List<RegionDto> getDongList(String gugunCode) throws SQLException;
	
	/** 대분류 리스트 */
	List<CategoryDto> getLargeList() throws SQLException;
	
	/** 중분류 리스트 */
	List<CategoryDto> getMiddleList(String largeCode) throws SQLException;
	
	/** 상권 리스트 */
	List<CommerceDto> getCommerceList(String dongName, String dongCode, String middleCode,
			ListParamDto listParamDto) throws SQLException;
}

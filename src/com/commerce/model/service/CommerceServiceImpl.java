package com.commerce.model.service;

import java.sql.SQLException;
import java.util.List;

import com.commerce.model.CategoryDto;
import com.commerce.model.CommerceDto;
import com.commerce.model.ListParamDto;
import com.commerce.model.RegionDto;
import com.commerce.model.dao.CommerceDao;
import com.commerce.model.dao.CommerceDaoImpl;

public class CommerceServiceImpl implements CommerceService {

	private final int COUNTPERPAGE = 15;

	/** commerceDao 싱글톤 객체 저장 */
	private CommerceDao commerceDao = CommerceDaoImpl.getCommerceDao();
	
	/** 싱글톤패턴 적용 */
	private static CommerceServiceImpl instance = new CommerceServiceImpl();
	
	private CommerceServiceImpl() {}
	
	public static CommerceServiceImpl getInstance() {
		return instance;
	}
	
	@Override
	public List<RegionDto> getSidoList() throws SQLException{
		//모든 시도 리스트를 받아오기
		return commerceDao.getSidoList();
	}
	
	@Override
	public List<RegionDto> getGugunList(String sidoCode) throws SQLException {
		return commerceDao.getGugunList(sidoCode);
	}
	
	@Override
	public List<RegionDto> getDongList(String gugunCode) throws SQLException {
		return commerceDao.getDongList(gugunCode);
	}
	
	@Override
	public List<CategoryDto> getLargeList() throws SQLException {
		return commerceDao.getLargeList();
	}
	
	@Override
	public List<CategoryDto> getMiddleList(String largeCode) throws SQLException {
		return commerceDao.getMiddleList(largeCode);
	}
	
	@Override
	public List<CommerceDto> getCommerceList(String dongName, String dongCode, String middleCode, String pg,
			String key, String word) throws SQLException {
		//key~~ 유효성체크, dto, dao ->
		int pgno = pg.isEmpty() ? 1 : Integer.parseInt(pg);
		int countPerPage = COUNTPERPAGE;
		
		key = key == null ? "" : key.trim();
		word = word == null ? "" : word.trim();
		//검색값에 따라서 페이지수 계산, dao로 넘기기
		ListParamDto listParamDto = new ListParamDto();
		listParamDto.setKey(key);
		listParamDto.setWord(word);
		listParamDto.setStart((pgno - 1) / countPerPage);
		listParamDto.setCountPerPage(countPerPage);
		
		return commerceDao.getCommerceList(dongName, dongCode, middleCode, listParamDto);
	}
}

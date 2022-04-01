package com.commerce.model.service;

import java.sql.SQLException;
import java.util.List;

import com.commerce.model.InterestedDto;
import com.commerce.model.dao.InterestedDao;
import com.commerce.model.dao.InterestedDaoImpl;
import com.commerce.model.util.exception.DuplicatedEntityException;
import com.commerce.model.util.exception.InvalidFormException;
import com.commerce.model.util.exception.NotFoundEntityException;

public class InterestedServiceImpl implements InterestedService {

	InterestedDao interestedDao = InterestedDaoImpl.getInstance();
	
	private static InterestedService instance = new InterestedServiceImpl();
	
	private InterestedServiceImpl() {}
	
	public static InterestedService getInstance() {
		return instance;
	}
	
	@Override
	public List<InterestedDto> getInterestedRegionList(String id)
			throws SQLException, NotFoundEntityException, InvalidFormException {
		
		if (!checkId(id)) {
			throw new InvalidFormException();
		}
		
		List<InterestedDto> list = interestedDao.getInterestedRegionList(id);
		if (list == null)//관심지역 등록 안한 경우
			throw new NotFoundEntityException();
		return list;
	}
	
	@Override
	public InterestedDto getInterestedRegion(String id, String dongCode, String largeCode)
			throws SQLException, NotFoundEntityException, InvalidFormException {
		
		if (!checkId(id)) {
			throw new InvalidFormException();
		} else if (dongCode == null || dongCode.equals("")) {
			throw new InvalidFormException();
		} else if (largeCode == null || largeCode.equals("")) {
			throw new InvalidFormException();
		}
		
		InterestedDto interestedDto = new InterestedDto();
		interestedDto.setId(id);
		interestedDto.setDongCode(dongCode);
		interestedDto.setLargeCode(largeCode);
		
		InterestedDto ret = interestedDao.getInterestedRegion(interestedDto);
		if (ret == null)//관심지역 등록 안한 경우
			throw new NotFoundEntityException();
		return ret;
	}

	@Override
	public void registRegion(String id, String dongCode, String largeCode)
			throws SQLException, InvalidFormException, DuplicatedEntityException {
		//동코드, 미들코드 널인지, 아이디 유효한지 정도 체크
		if (!checkId(id)) {
			throw new InvalidFormException();
		} else if (dongCode == null || dongCode.equals("")) {
			throw new InvalidFormException();
		} else if (largeCode == null || largeCode.equals("")) {
			throw new InvalidFormException();
		}
		
		InterestedDto interestedDto = new InterestedDto();
		interestedDto.setId(id);
		interestedDto.setDongCode(dongCode);
		interestedDto.setLargeCode(largeCode);
		
		interestedDao.registRegion(interestedDto);
	}

	@Override
	public void deleteInterestedRegion(String id, String dongCode, String largeCode)
			throws SQLException, InvalidFormException, NotFoundEntityException {
		
		if (!checkId(id)) {
			throw new InvalidFormException();
		} else if (dongCode == null || dongCode.equals("")) {
			throw new InvalidFormException();
		} else if (largeCode == null || largeCode.equals("")) {
			throw new InvalidFormException();
		}
		
		InterestedDto interestedDto = new InterestedDto();
		interestedDto.setId(id);
		interestedDto.setDongCode(dongCode);
		interestedDto.setLargeCode(largeCode);
		
		interestedDao.deleteInterestedRegion(interestedDto);
	}

	private boolean checkId(String id) {
		//id : 소문자와 숫자 조합만 허용, 소문자가 적어도 1개, 맨 첫글자는 무조건 소문자,
		//     길이 5자 이상, not null and ""
		if (id == null || "".equals(id) || id.length() < 5)
			return false;
		char[] id_ = id.toCharArray();
		for (int i = 0; i < id_.length; i++) {
			if (i == 0 && !isLowerCase(id_[i]))
				return false;
			if (!isLowerCase(id_[i]) && !isNumber(id_[i]))
				return false;
		}
		return true;
	}
	
	private boolean isLowerCase(char c) {
		if (c >='a' && c <= 'z')
			return true;
		return false;
	}
	
	private boolean isNumber(char c) {
		if (c >='0' && c <= '9')
			return true;
		return false;
	}

}

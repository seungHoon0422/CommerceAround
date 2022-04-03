package com.commerce.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.commerce.model.CategoryDto;
import com.commerce.model.CommerceDto;
import com.commerce.model.ListParamDto;
import com.commerce.model.RegionDto;
import com.commerce.model.util.DBUtil;

public class CommerceDaoImpl implements CommerceDao {

	private DBUtil dbutil = DBUtil.getInstance();//드라이버 연결
	
	//싱글톤 패턴 적용
	private static CommerceDao instance = new CommerceDaoImpl();
	
	private CommerceDaoImpl() {}
	
	public static CommerceDao getCommerceDao() {
		return instance;
	}

	@Override
	public List<RegionDto> getSidoList() throws SQLException {
		
		List<RegionDto> list = new ArrayList<RegionDto>();
		
		String sql = "SELECT * from sido";
		
		try(Connection conn = dbutil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery()) {
			
			while (rs.next()) {
				RegionDto regionDto = new RegionDto();
				regionDto.setSidoCode(rs.getString("sidoCode"));
				regionDto.setSidoName(rs.getString("sidoName"));
				list.add(regionDto);
			}
			
		}
		return list;
	}

	@Override
	public List<RegionDto> getGugunList(String sidoCode) throws SQLException {
		List<RegionDto> list = new ArrayList<RegionDto>();
		
		String sql = "SELECT * from gugun where sidoCode = ?";
		
		try(Connection conn = dbutil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, sidoCode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RegionDto regionDto = new RegionDto();
				regionDto.setSidoCode(sidoCode);
				regionDto.setGugunCode(rs.getString("gugunCode"));
				regionDto.setGugunName(rs.getString("gugunName"));
				list.add(regionDto);
			}
			rs.close();
		}
		return list;
	}

	@Override
	public List<RegionDto> getDongList(String gugunCode) throws SQLException {
		List<RegionDto> list = new ArrayList<RegionDto>();
		
		String sql = "SELECT * FROM dong WHERE gugunCode = ? "
				+ "GROUP BY dongName ";
		
		try(Connection conn = dbutil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setString(1, gugunCode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				RegionDto regionDto = new RegionDto();
				regionDto.setGugunCode(gugunCode);
				regionDto.setDongCode(rs.getString("dongCode"));
				regionDto.setDongName(rs.getString("dongName"));
				list.add(regionDto);
			}
			rs.close();
		} 
		return list;
	}

	@Override
	public List<CategoryDto> getLargeList() throws SQLException {
		List<CategoryDto> list = new ArrayList<CategoryDto>();
		
		String sql = "SELECT * from largesector";
		
		try(Connection conn = dbutil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();) {
			
			while (rs.next()) {
				CategoryDto categoryDto = new CategoryDto();
				categoryDto.setLargeCode(rs.getString("code"));
				categoryDto.setLargeName(rs.getString("name"));
				list.add(categoryDto);
			}
		} 
		return list;
	}

	@Override
	public List<CategoryDto> getMiddleList(String largeCode) throws SQLException {
		List<CategoryDto> list = new ArrayList<CategoryDto>();
		
		String sql = "SELECT * from middlesector where largeCode = ?";
		
		try(Connection conn = dbutil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);) {
			
			pstmt.setString(1, largeCode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				CategoryDto categoryDto = new CategoryDto();
				categoryDto.setLargeCode(largeCode);
				categoryDto.setMiddleCode(rs.getString("code"));
				categoryDto.setMiddleName(rs.getString("name"));
				list.add(categoryDto);
			}
			rs.close();
		}
		return list;
	}

	@Override
	public List<CommerceDto> getCommerceList(String dongName, String dongCode, String middleCode, 
			ListParamDto listParamDto) throws SQLException {
		List<CommerceDto> list = new ArrayList<CommerceDto>();
		
		String key = listParamDto.getKey();
		String word = listParamDto.getWord();
		
		StringBuilder sql = new StringBuilder();
		//dongCode와 앞의 5자리가 똑같고, dongName과 이름이 같은 지역 select!!
		//dong테이블에서 dongName으로 검색 -> dongCodeList가 나옴 -> dongCode가 여기 속해야함
		sql.append("SELECT * FROM store WHERE middleCode = ? ")
		.append(" AND dongCode IN ")
		.append("(SELECT dongCode FROM dong WHERE dongName = ? AND dongCode LIKE concat(?, '%'))");
		//wort null -> 그대로, 아니면 쿼리 추가 ?에 key, word채워주기, limit도
		try(Connection conn = dbutil.getConnection()) {
			if (!word.isEmpty()) {
				sql.append("and ").append(key).append(" like concat('%', ?, '%') \n");
			}
			int idx = 1;
			try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())){
				pstmt.setString(idx++, middleCode);
				pstmt.setString(idx++, dongName);
				pstmt.setString(idx++, dongCode.substring(0, 5));
				if (!word.isEmpty()) {
					pstmt.setString(idx++, word);
				}
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					CommerceDto commerceDto = new CommerceDto();
					commerceDto.setName(rs.getString("name"));
					commerceDto.setAddress(rs.getString("address"));
					commerceDto.setFloor(rs.getString("floor"));
					commerceDto.setSmallName(rs.getString("smallName"));
					commerceDto.setLon(rs.getString("lon"));
					commerceDto.setLat(rs.getString("lat"));
					list.add(commerceDto);
				}
				rs.close();
			}
		} 
		return list;
	}
	
}

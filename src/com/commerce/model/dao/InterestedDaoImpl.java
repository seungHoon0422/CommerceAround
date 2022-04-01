package com.commerce.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.commerce.model.InterestedDto;
import com.commerce.model.util.DBUtil;
import com.commerce.model.util.exception.DuplicatedEntityException;
import com.commerce.model.util.exception.NotFoundEntityException;

public class InterestedDaoImpl implements InterestedDao {
	
	/** mysql 드라이버 로딩 객체  */
	private DBUtil dbUtil = DBUtil.getInstance();
	
	/** 싱글톤 패턴 */
	private static InterestedDao instance = new InterestedDaoImpl();
	
	private InterestedDaoImpl() {}
	
	public static InterestedDao getInstance() {
		return instance;
	}
	
	@Override
	public List<InterestedDto> getInterestedRegionList(String id) throws SQLException {
		
		List<InterestedDto> list = null;
		
		String sql = "SELECT * FROM interested "
				+ "WHERE id=?";
		
		try(Connection conn = dbUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<InterestedDto>();
				InterestedDto interestedDto = new InterestedDto();
				interestedDto.setId(rs.getString("id"));
				interestedDto.setDongCode(rs.getString("dongCode"));
				interestedDto.setMiddleCode(rs.getString("middleCode"));
				list.add(interestedDto);
			}
			rs.close();
		}
		return list;
	}
	
	@Override
	public InterestedDto getInterestedRegion(InterestedDto interestedDto) throws SQLException, NotFoundEntityException {
		InterestedDto ret = null;
		
		String sql = "SELECT * FROM interested "
				+ "WHERE id=? AND dongCode=? AND middleCode=?";
		
		try(Connection conn = dbUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, interestedDto.getId());
			pstmt.setString(2, interestedDto.getDongCode());
			pstmt.setString(3, interestedDto.getMiddleCode());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ret = new InterestedDto();
				ret.setId(rs.getString("id"));
				ret.setDongCode(rs.getString("dongCode"));
				ret.setMiddleCode(rs.getString("middleCode"));
			} else {
				throw new NotFoundEntityException();
			}
			rs.close();
		}
		return ret;
	}

	@Override
	public void registRegion(InterestedDto interestedDto)
			throws SQLException, DuplicatedEntityException {
		
		int ret = 0;
		String sql = "INSERT INTO interested (id, dongCode, middleCode) \n"
				+ " SELECT ?, ?, ? FROM DUAL WHERE NOT EXISTS \n"
				+ " (SELECT id, dongCode, middleCode FROM interested \n" 
				+ " WHERE id=? AND dongCode=? AND middleCode=?)";
		
		try(Connection conn = dbUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, interestedDto.getId());
			pstmt.setString(2, interestedDto.getDongCode());
			pstmt.setString(3, interestedDto.getMiddleCode());
			pstmt.setString(4, interestedDto.getId());
			pstmt.setString(5, interestedDto.getDongCode());
			pstmt.setString(6, interestedDto.getMiddleCode());
			
			ret = pstmt.executeUpdate();
		}
		if (ret == 0)
			throw new DuplicatedEntityException();
	}

	@Override
	public void deleteInterestedRegion(InterestedDto interestedDto) throws SQLException {

		String sql = "DELETE FROM interested WHERE id=? AND dongCode=? "
				+ " AND middleCode=?";
		
		try(Connection conn = dbUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, interestedDto.getId());
			pstmt.setString(2, interestedDto.getDongCode());
			pstmt.setString(3, interestedDto.getMiddleCode());
			
			pstmt.executeUpdate();
		}
	}
	
}

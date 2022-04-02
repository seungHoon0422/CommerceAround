package com.commerce.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.commerce.model.InterestedDto;
import com.commerce.model.InterestedVo;
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
	public List<InterestedVo> getInterestedRegionList(String id) throws SQLException {
		
		List<InterestedVo> list = null;
		
		String sql = "SELECT dongName, name largeName, i.dongCode, i.largeCode "
				+ " FROM interested i join dong d "
				+ " on d.dongCode = i.dongCode "
				+ " join largesector l "
				+ " on l.code = i.largeCode "
				+ " where i.id = ?";
		
		try(Connection conn = dbUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				if (list == null)
					list = new ArrayList<InterestedVo>();
				InterestedVo interestedVo = new InterestedVo();
				interestedVo.setDongName(rs.getString("dongName"));
				interestedVo.setLargeName(rs.getString("largeName"));
				interestedVo.setDongCode(rs.getString("dongCode"));
				interestedVo.setLargeCode(rs.getString("largeCode"));
				list.add(interestedVo);
			}
			rs.close();
		}
		return list;
	}
	
	@Override
	public InterestedDto getInterestedRegion(InterestedDto interestedDto) throws SQLException, NotFoundEntityException {
		InterestedDto ret = null;
		
		String sql = "SELECT * FROM interested "
				+ "WHERE id=? AND dongCode=? AND largeCode=?";
		
		try(Connection conn = dbUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, interestedDto.getId());
			pstmt.setString(2, interestedDto.getDongCode());
			pstmt.setString(3, interestedDto.getLargeCode());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				ret = new InterestedDto();
				ret.setId(rs.getString("id"));
				ret.setDongCode(rs.getString("dongCode"));
				ret.setLargeCode(rs.getString("largeCode"));
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
		String sql = "INSERT INTO interested (id, dongCode, largeCode) \n"
				+ " SELECT ?, ?, ? FROM DUAL WHERE NOT EXISTS \n"
				+ " (SELECT id, dongCode, largeCode FROM interested \n" 
				+ " WHERE id=? AND dongCode=? AND largeCode=?)";
		
		try(Connection conn = dbUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, interestedDto.getId());
			pstmt.setString(2, interestedDto.getDongCode());
			pstmt.setString(3, interestedDto.getLargeCode());
			pstmt.setString(4, interestedDto.getId());
			pstmt.setString(5, interestedDto.getDongCode());
			pstmt.setString(6, interestedDto.getLargeCode());
			
			ret = pstmt.executeUpdate();
		}
		if (ret == 0)
			throw new DuplicatedEntityException();
	}

	@Override
	public void deleteInterestedRegion(InterestedDto interestedDto) throws SQLException {

		String sql = "DELETE FROM interested WHERE id=? AND dongCode=? "
				+ " AND largeCode=?";
		
		try(Connection conn = dbUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, interestedDto.getId());
			pstmt.setString(2, interestedDto.getDongCode());
			pstmt.setString(3, interestedDto.getLargeCode());
			
			pstmt.executeUpdate();
		}
	}
	
}

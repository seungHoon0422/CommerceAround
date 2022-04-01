package com.commerce.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.commerce.model.MemberDto;
import com.commerce.model.util.DBUtil;
import com.commerce.model.util.exception.NotFoundEntityException;

public class MemberDaoImpl implements MemberDao {
	
	/** mysql 드라이버 로딩 객체  */
	private DBUtil dbUtil = DBUtil.getInstance();
	
	/** 싱글톤 패턴 */
	private static MemberDao instance = new MemberDaoImpl();
	
	private MemberDaoImpl() {}
	
	public static MemberDao getInstance() {
		return instance;
	}
	
	@Override
	public MemberDto login(String id, String pass) throws SQLException {
		
		MemberDto memberDto = null;
		String sql = "select * from user where id = ?";
		
		try(Connection conn = dbUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("id").equals(id) && rs.getString("pass").equals(pass)) {
					System.out.println("FInd member");
					memberDto = new MemberDto();
					memberDto.setId(id);
					memberDto.setPass(pass);
					memberDto.setName(rs.getString("name"));
					memberDto.setAddress(rs.getString("address"));
					memberDto.setPhoneNum (rs.getString("phoneNum"));
					memberDto.setQuestion(rs.getString("question"));
					memberDto.setAnswer(rs.getString("answer"));
					memberDto.setRegDate(rs.getString("regDate"));
				}
			}
			//그런 사용자 없으면 null값 리턴
			rs.close();
		} 
		//아이디 비번 일치하지 않으면 null리턴
		return memberDto;
	}

	@Override
	public int loginCheck(String id) throws SQLException {
		
		int ret = 0;
		String sql = "select id from user where id = ?";
		
		try(Connection conn = dbUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { //Dup예외 던지는게 통일성있지 않은지?
				ret = 1;
			}
			rs.close();
		}
		//중복값 없으면 0리턴
		return ret;
	}

	@Override
	public void registMember(MemberDto memberDto) throws SQLException {
		
		String sql = "insert into user (id, pass, name, address, phoneNum, question, answer) "
				+ "values (?, ?, ?, ?, ?, ?, ?)";
		
		try(Connection conn = dbUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, memberDto.getId());
			pstmt.setString(2, memberDto.getPass());
			pstmt.setString(3, memberDto.getName());
			pstmt.setString(4, memberDto.getAddress());
			pstmt.setString(5, memberDto.getPhoneNum());
			pstmt.setString(6, memberDto.getQuestion());
			pstmt.setString(7, memberDto.getAnswer());
			
			pstmt.executeUpdate();
		}
	}

	@Override
	public void updateMemberInfo(MemberDto memberDto) throws SQLException, NotFoundEntityException {
		//이미 값 체크 service에서 했으므로 그냥 넣으면 됨
		int ret = 1;
		String sql = "UPDATE user SET pass = ?, address = ?, phoneNum = ?, question = ?, answer = ?"; 
		sql += " WHERE id = ?";
		try(Connection conn = dbUtil.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			pstmt.setString(1, memberDto.getPass());
			pstmt.setString(2, memberDto.getAddress());
			pstmt.setString(3, memberDto.getPhoneNum());
			pstmt.setString(4, memberDto.getQuestion());
			pstmt.setString(5, memberDto.getAnswer());
			pstmt.setString(6, memberDto.getId());
			
			ret = pstmt.executeUpdate();
			
		}
		if (ret == 0)
			throw new NotFoundEntityException();
	}
	
}

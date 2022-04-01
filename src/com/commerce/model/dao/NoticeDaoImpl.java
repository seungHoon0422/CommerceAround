package com.commerce.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.commerce.model.ListParamDto;
import com.commerce.model.NoticeDto;
import com.commerce.model.util.DBUtil;

public class NoticeDaoImpl implements NoticeDao {

	
	private DBUtil dbUtil = DBUtil.getInstance();
	
	private static NoticeDao noticeDao = new NoticeDaoImpl();
	
	private NoticeDaoImpl() {}

	public static NoticeDao getNoticeDao() {
		return noticeDao;
	}
	
	
	
	
	
	@Override
	public void registerNotice(NoticeDto noticeDto) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder registerArticle = new StringBuilder();
			registerArticle.append("insert into notice (userId, subject, content, regTime) ");
			registerArticle.append("values (?, ?, ?, now())");
			pstmt = conn.prepareStatement(registerArticle.toString());
			pstmt.setString(1, noticeDto.getUserId());
			pstmt.setString(2, noticeDto.getSubject());
			pstmt.setString(3, noticeDto.getContent());
			pstmt.executeUpdate();
		} finally {
			dbUtil.close(pstmt, conn);
		}

	}

	@Override
	public List<NoticeDto> listNotice(ListParamDto listParameterDto) throws SQLException {
		
		List<NoticeDto> list = new ArrayList<NoticeDto>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = dbUtil.getConnection();
			StringBuilder listArticle = new StringBuilder();
			listArticle.append("select n.articleNo, n.userId, n.subject, n.content, m.name, ");
			listArticle.append(" 		case when date_format(n.regtime, '%y%m%d') = date_format(now(), '%y%m%d') ");
			listArticle.append("			then date_format(n.regtime, '%H:%i:%d') ");
			listArticle.append("			else date_format(n.regtime, '%y.%m.%d') ");
			listArticle.append("		end regtime ");
			listArticle.append("from notice n, user m ");
			listArticle.append("where n.userId = m.id ");
			String key = listParameterDto.getKey();
			String word = listParameterDto.getWord();
			if(!word.isEmpty()) {
				if(key.equals("subject")) {
					listArticle.append("and n.subject like concat('%', ?, '%') \n");
				} else {
					listArticle.append("and n." + key + " = ? \n");
				}
			}
			listArticle.append("order by n.articleNo desc limit ?, ? \n");
			pstmt = conn.prepareStatement(listArticle.toString());
			int idx = 0;
			if(!word.isEmpty()) {
				pstmt.setString(++idx, word);
			}
			pstmt.setInt(++idx, listParameterDto.getStart());
			pstmt.setInt(++idx, listParameterDto.getCountPerPage());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				NoticeDto noticeDto = new NoticeDto();
				noticeDto.setArticleNo(rs.getInt("articleNo"));
				noticeDto.setUserId(rs.getString("userId"));
				noticeDto.setUserName(rs.getString("name"));
				noticeDto.setSubject(rs.getString("subject").replace("<", "&lt;"));
				noticeDto.setContent(rs.getString("content"));
				noticeDto.setRegTime(rs.getString("regTime"));
				
				list.add(noticeDto);
			}
		} finally {
			dbUtil.close(rs, pstmt, conn);
		}
		return list;
	}

	@Override
	public int getTotalCount(ListParamDto listParameterDto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public NoticeDto getArticle(int articleNo) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateArticle(NoticeDto guestBookDto) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteArticle(int articleNo) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}

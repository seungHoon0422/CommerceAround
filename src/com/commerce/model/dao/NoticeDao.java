package com.commerce.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.commerce.model.ListParamDto;
import com.commerce.model.NoticeDto;

public interface NoticeDao {

	void registerNotice(NoticeDto noticeDto) throws SQLException;
	List<NoticeDto> listNotice(ListParamDto listParameterDto) throws SQLException;
	int getTotalCount(ListParamDto listParameterDto) throws SQLException;

	NoticeDto getArticle(int articleNo) throws SQLException;
	void updateArticle(NoticeDto guestBookDto) throws SQLException;
	void deleteArticle(int articleNo) throws SQLException;

}

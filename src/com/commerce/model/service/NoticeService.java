package com.commerce.model.service;

import java.util.List;

import com.commerce.model.NoticeDto;
import com.commerce.model.util.PageNavigation;

public interface NoticeService {

	void registerArticle(NoticeDto noticeDto) throws Exception;
	List<NoticeDto> listArticle(String pg, String key, String word) throws Exception;
	PageNavigation makePageNavigation(String pg, String key, String word) throws Exception;
	
//	구현해 보세요!!!
	NoticeDto getArticle(int articleNo) throws Exception;
	void updateArticle(NoticeDto noticeDto) throws Exception;
	void deleteArticle(int articleNo) throws Exception;

}

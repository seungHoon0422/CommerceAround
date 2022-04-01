package com.commerce.model.service;

import java.util.List;

import com.commerce.model.ListParamDto;
import com.commerce.model.NoticeDto;
import com.commerce.model.dao.NoticeDao;
import com.commerce.model.dao.NoticeDaoImpl;
import com.commerce.model.util.PageNavigation;

public class NoticeServiceImpl implements NoticeService{


	private NoticeDao noticeDao = NoticeDaoImpl.getNoticeDao();
	
	
	private static NoticeService noticeService = new NoticeServiceImpl();
	
	private NoticeServiceImpl() {}

	public static NoticeService getNoticeService() {
		return noticeService;
	}

	
	
	
	@Override
	public void registerArticle(NoticeDto noticeDto) throws Exception {
		noticeDao.registerNotice(noticeDto);
		
	}
	@Override
	public List<NoticeDto> listArticle(String pg, String key, String word) throws Exception {
		int pgno = pg != null ? Integer.parseInt(pg) : 1;
		int countPerPage = 10;
		int start= (pgno - 1) * countPerPage;
		
		ListParamDto listParameterDto = new ListParamDto();
		listParameterDto.setKey(key == null ? "" : key.trim());
		listParameterDto.setWord(word == null ? "" : word.trim());
		listParameterDto.setStart(start);
//		listParameterDto.setCurrentPerPage(countPerPage);
		listParameterDto.setCountPerPage(5);
		return noticeDao.listNotice(listParameterDto);
	}

	@Override
	public PageNavigation makePageNavigation(String pg, String key, String word) throws Exception {
		return null;
	}

	@Override
	public NoticeDto getArticle(int articleNo) throws Exception {
		return null;
	}

	@Override
	public void updateArticle(NoticeDto noticeDto) throws Exception {
		
	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		
	}

}

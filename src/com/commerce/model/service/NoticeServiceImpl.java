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
		listParameterDto.setCountPerPage(countPerPage);
		return noticeDao.listNotice(listParameterDto);
	}

	@Override
	public PageNavigation makePageNavigation(String pg, String key, String word) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();
		int currentPage = pg != null ? Integer.parseInt(pg) : 1;
		int naviSize = 10;
		int countPerPage = 10;

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setCountPerPage(countPerPage);
		pageNavigation.setNaviSize(naviSize);
		
		ListParamDto listParameterDto = new ListParamDto();
		listParameterDto.setKey(key == null ? "" : key.trim());
		listParameterDto.setWord(word == null ? "" : word.trim());
		
		int totalCount = noticeDao.getTotalCount(listParameterDto);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / countPerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		pageNavigation.setStartRange(currentPage <= naviSize);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}

	@Override
	public NoticeDto getArticle(int articleNo) throws Exception {
		return noticeDao.getArticle(articleNo);
	}

	@Override
	public void updateArticle(NoticeDto noticeDto) throws Exception {
		noticeDao.updateArticle(noticeDto);
	}

	@Override
	public void deleteArticle(int articleNo) throws Exception {
		noticeDao.deleteArticle(articleNo);
		
	}

}

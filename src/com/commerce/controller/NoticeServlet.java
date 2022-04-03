package com.commerce.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.commerce.model.MemberDto;
import com.commerce.model.NoticeDto;
import com.commerce.model.service.NoticeService;
import com.commerce.model.service.NoticeServiceImpl;
import com.commerce.model.util.PageNavigation;

@WebServlet("/notice")
public class NoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private NoticeService noticeService = NoticeServiceImpl.getNoticeService();
	
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		
		System.out.println("Notice mapping");
		
		String action = request.getParameter("action");
		if (action == null)
			action = "movenotice";
		String root = request.getContextPath();
		String path="/Index.jsp";
		
		switch (action) {
		case "movenotice":
			path = moveList(request, response);
			request.getRequestDispatcher(path).forward(request, response);
			break;
		case "shownotice":
			path = showArticle(request, response);
			request.getRequestDispatcher(path).forward(request, response);
			break;
		case "moveregister":
			path = moveRegister(request);
			response.sendRedirect(root + path);
			break;
		case "register":
			path = registerNotice(request);
//			response.sendRedirect(request.getContextPath() + path);
			request.getRequestDispatcher(path).forward(request, response);
			break;
		case "mvmodify":
			path = getArticle(request, response);
			request.getRequestDispatcher(path).forward(request, response);
			break;
		case "modify":
			path = modifyArticle(request, response);
			response.sendRedirect(request.getContextPath() + path);
			break;
		case "delete":
			path = deleteArticle(request, response);
			response.sendRedirect(request.getContextPath() + path);
			break;
		default:
			response.sendRedirect(path);
			break;
		}
		
		
	}

	private String showArticle(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		MemberDto memberDto = (MemberDto) session.getAttribute("memberInfo");
//		if(memberDto != null) {
			NoticeDto noticeDto = new NoticeDto();
			try {
				noticeDto = noticeService.getArticle(Integer.parseInt(request.getParameter("articleno")));
				request.setAttribute("article", noticeDto);
				return "/notice/shownotice.jsp";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글 수정중 에러가 발생했습니다.");
				return "/error/error.jsp";
			}
//		} else {			
//			return "/member?action=mvlogin";
//		}
	}

	private String deleteArticle(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("memberInfo");
		if(memberDto != null) {
			int articleNo = Integer.parseInt(request.getParameter("articleno"));
			
			try {
				noticeService.deleteArticle(articleNo);
				return "/notice?action=movenotice";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글 얻기중 에러가 발생했습니다.");
				return "/error/error.jsp";
			}
		} else {			
			return "/member?action=mvlogin";
		}

	}

	private String modifyArticle(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("memberInfo");
		if(memberDto != null) {
			NoticeDto noticeDto = new NoticeDto();
			noticeDto.setArticleNo(Integer.parseInt(request.getParameter("articleno")));
			noticeDto.setSubject(request.getParameter("subject"));
			noticeDto.setContent(request.getParameter("content"));
			try {
				noticeService.updateArticle(noticeDto);
				return "/notice/writesuccess.jsp";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글 수정중 에러가 발생했습니다.");
				return "/error/error.jsp";
			}
		} else {			
			return "/member?action=mvlogin";
		}
	}

	private String getArticle(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("memberInfo");
		if(memberDto != null) {
			int articleNo = Integer.parseInt(request.getParameter("articleno"));
			System.out.println("article NO : " +articleNo);
			try {
				
				 NoticeDto noticeDto = noticeService.getArticle(articleNo);
				request.setAttribute("article", noticeDto);
				return "/notice/modify.jsp";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글 얻기중 에러가 발생했습니다.");
				return "/error/error.jsp";
			}
		} else {			
			return "/member?action=mvlogin";
		}
	}

	private String moveRegister(HttpServletRequest request) {
		return "/notice/register.jsp";
	}

	private String registerNotice(HttpServletRequest request) {
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("memberInfo");
		System.out.println("register : "+memberDto.toString());
		if(memberDto != null) {
			NoticeDto noticeDto = new NoticeDto();
			noticeDto.setUserId(memberDto.getId());
			noticeDto.setSubject(request.getParameter("subject"));
			noticeDto.setContent(request.getParameter("content"));
			System.out.println(noticeDto.toString());
			
			try {
				noticeService.registerArticle(noticeDto);
				return "/notice/writesuccess.jsp";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글 작성중 에러가 발생했습니다.");
				return "/error/error.jsp";
			}
		} else {			
			return "/member?action=mvlogin";
		}

	}

	private String moveList(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Move List");
//		HttpSession session = request.getSession();
//		MemberDto memberDto = (MemberDto) session.getAttribute("memberInfo");
//		if(memberDto != null) {
			String pg = request.getParameter("pg");
			String key = request.getParameter("key");
			String word = request.getParameter("word");
			try {
				List<NoticeDto> list = noticeService.listArticle(pg, key, word);
				PageNavigation navigation = noticeService.makePageNavigation(pg, key, word);
				request.setAttribute("articles", list);
				request.setAttribute("navi", navigation);
				request.setAttribute("key", key);
				request.setAttribute("word", word);
				
				System.out.println("path : "+"/notice/notice.jsp");
				return "/notice/notice.jsp";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "글목록 얻기중 에러가 발생했습니다.");
				return "/error/error.jsp";
			}
//		} else {			
//			return "/member?act=mvlogin";
//		}
	}
}
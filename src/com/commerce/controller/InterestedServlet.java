package com.commerce.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.commerce.model.InterestedVo;
import com.commerce.model.MemberDto;
import com.commerce.model.service.InterestedService;
import com.commerce.model.service.InterestedServiceImpl;
import com.commerce.model.util.exception.DuplicatedEntityException;
import com.commerce.model.util.exception.NotFoundEntityException;
import com.google.gson.Gson;

@WebServlet("/interested")
public class InterestedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private InterestedService interestedService = InterestedServiceImpl.getInstance();
	
	private String root;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		
		System.out.println("Interested mapping");
		
		HttpSession session = request.getSession();
		MemberDto memberDto = (MemberDto) session.getAttribute("memberInfo");
		if (memberDto == null) {
			request.setAttribute("msg", "로그인이 필요합니다.");
			request.getRequestDispatcher("/member?action=movelogin").forward(request, response);
			return;
		}
		
		String id = memberDto.getId();
		String action = request.getParameter("action");
		if (action == null)
			action = "";
		root = request.getContextPath();
		String path="/Index.jsp";//요청 실패 경로임. 추후 수정하기!
		
		switch (action) {
		case "regist":
			registRegion(id, request, response);
			break;
		case "check":
			//중복체크
			break;
		case "delete":
			deleteRegion(id, request, response);
			break;
		case "list":
			listRegion(id, request, response);
			break;
		default:
			response.sendRedirect(path);
			break;
		}
	}
	
//	중분류로 관심지역 등록하던 코드
//	private void registRegion(String id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//		String dongCode = request.getParameter("dongCode");
//		String middleCode = request.getParameter("middleCode");
//		String path = "/commerce?action=main" + "&dong=" + dongCode 
//				+ "&large=" + middleCode.charAt(0);
//		try {
//			interestedService.registRegion(id, dongCode, middleCode);
//		} catch (DuplicatedEntityException e) {
//			e.printStackTrace();
//			request.setAttribute("msg", "중복된 지역이 존재합니다.");
//		} catch (Exception e) {
//			e.printStackTrace();
//			request.setAttribute("msg", "관심지역 등록 중 에러가 발생했습니다.");
//			path = "/error/error.jsp";
//		}
//		System.out.println("path: " + path);
//		request.getRequestDispatcher(path).forward(request, response);
//	}

	//대분류로 관심지역 등록
	private void registRegion(String id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String dongCode = request.getParameter("dongCode");
		String largeCode = request.getParameter("largeCode");
		String path = "/commerce?action=main" + "&dong=" + dongCode 
				+ "&large=" + largeCode;

		try {
			interestedService.registRegion(id, dongCode, largeCode);
		} catch (DuplicatedEntityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "중복된 지역이 존재합니다.");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "관심지역 등록 중 에러가 발생했습니다.");
			path = "/error/error.jsp";
		}
		System.out.println("path: " + path);
		request.getRequestDispatcher(path).forward(request, response);
	}
	
	private void deleteRegion(String id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String dongCode = request.getParameter("dongCode");
		String largeCode = request.getParameter("largeCode");
		String path = "/commerce?action=main&dongCode=" + dongCode + "&largeCode=" + largeCode;
		
		try {
			interestedService.deleteInterestedRegion(id, dongCode, largeCode);
			response.sendRedirect(root + path);
		} catch (NotFoundEntityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "삭제할 지역이 없습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "관심지역 등록 중 에러가 발생했습니다.");
			path = "/error/error.jsp";
		}
		request.getRequestDispatcher(path).forward(request, response);
	}

	private void listRegion(String id, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String dongCode = request.getParameter("dongCode");
		String largeCode = request.getParameter("largeCode");
		String path = "/commerce?action=main" + "&dong=" + dongCode 
				+ "&large=" + largeCode;
		
		try {
			List<InterestedVo> list = interestedService.getInterestedRegionList(id);
			request.setAttribute("interestedList", list);
			Gson gson = new Gson();
			String listJson = gson.toJson(list, List.class).toString();
			response.getWriter().append(listJson);
			return;
		} catch (NotFoundEntityException e) {
			e.printStackTrace();
			request.setAttribute("msg", "관심 지역이 없습니다.");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "에러가 발생했습니다.");
			path = "/error/error.jsp";
		}
		request.getRequestDispatcher(path).forward(request, response);
	}
}
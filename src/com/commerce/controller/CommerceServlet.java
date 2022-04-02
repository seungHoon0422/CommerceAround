package com.commerce.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.commerce.model.CategoryDto;
import com.commerce.model.CommerceDto;
import com.commerce.model.RegionDto;
import com.commerce.model.service.CommerceService;
import com.commerce.model.service.CommerceServiceImpl;
import com.google.gson.Gson;

@WebServlet("/commerce")
public class CommerceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private CommerceService commerceService = CommerceServiceImpl.getInstance();
	
	public CommerceServlet() {
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		doGet(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		
		String action = request.getParameter("action");
		if (action == null)
			action = "";
		String root = request.getContextPath();
		String path = root + "/commerce/main.jsp";
		
		switch (action) {
		case "main":
			path = moveMiddle(request);
			request.getRequestDispatcher(path).forward(request, response);
			break;
		case "map":
			String mapJson = getMap(request);
			// 그에 맞는 상권 리스트를 json으로 main.jsp에 response출력
			if (mapJson != null)
				response.getWriter().append(mapJson);
			break;
		case "sido":
			String sidoJson = getSidoList(request);
			// 그에 맞는 상권 리스트를 json으로 main.jsp에 response출력
			if (sidoJson != null)
				response.getWriter().append(sidoJson);
			break;
		case "gugun":
			String gugunJson = getGugunList(request);
			// 그에 맞는 상권 리스트를 json으로 main.jsp에 response출력
			if (gugunJson != null)
				response.getWriter().append(gugunJson);
			break;
		case "dong":
			String dongJson = getDongList(request);
			// 그에 맞는 상권 리스트를 json으로 main.jsp에 response출력
			if (dongJson != null)
				response.getWriter().append(dongJson);
			break;
		case "largeCode" :
			String largeJson = getLargeList(request);
			if (largeJson != null)
				response.getWriter().append(largeJson);
			break;
		default:
			response.sendRedirect(path);
			break;
		}
	}
	
	private String moveMiddle(HttpServletRequest request) {
		
		request.setAttribute("dongCode", request.getParameter("dong"));
		String largeCode = request.getParameter("large");
		request.setAttribute("largeCode", largeCode);
		
		try {
			List<CategoryDto> middleList = commerceService.getMiddleList(largeCode);
			request.setAttribute("middleList", middleList);
			return "/commerce/main.jsp";
			
		} catch (SQLException e) {
			// middle list 못받아온 경우
			e.printStackTrace();
		}
		return null;
	}

	private String getLargeList(HttpServletRequest request) {
		
		try {
			List<CategoryDto> list = commerceService.getLargeList();
			Gson gson = new Gson();//자바객체를 JSON으로 변경해주는 객체(외부 jar파일 받았음)
			String listJson = gson.toJson(list, List.class).toString();
			return listJson;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getSidoList(HttpServletRequest request) {
		
		try {
			List<RegionDto> list = commerceService.getSidoList();
			
			Gson gson = new Gson();//자바객체를 JSON으로 변경해주는 객체(외부 jar파일 받았음)
			String listJson = gson.toJson(list, List.class).toString();
			return listJson;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getGugunList(HttpServletRequest request) {
		try {
			String regCode = request.getParameter("code");
			List<RegionDto> list = commerceService.getGugunList(regCode);
			
			Gson gson = new Gson();//자바객체를 JSON으로 변경해주는 객체(외부 jar파일 받았음)
			String listJson = gson.toJson(list, List.class).toString();
			return listJson;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getDongList(HttpServletRequest request) {
		try {
			List<RegionDto> list = commerceService.getDongList(request.getParameter("code"));
			Gson gson = new Gson();//자바객체를 JSON으로 변경해주는 객체(외부 jar파일 받았음)
			String listJson = gson.toJson(list, List.class).toString();
			return listJson;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected String getMap(HttpServletRequest request) {
		
		String dongCode = request.getParameter("dongCode");
		String middleCode = request.getParameter("middleCode");
		String pg = request.getParameter("pg");
		String key = request.getParameter("key");
		String word = request.getParameter("word");
		
		try {
			//리스트 받아오는 서비스 객체
			//페이지, key, word
			List<CommerceDto> list = commerceService.getCommerceList(dongCode, middleCode, pg, key, word);
			Gson gson = new Gson();//자바객체를 JSON으로 변경해주는 객체(외부 jar파일 받았음)
			String listJson = gson.toJson(list, List.class).toString();
			return listJson;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DB에러");
			return null;
		}
	}
}

package com.commerce.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.commerce.model.MemberDto;
import com.commerce.model.service.CommerceService;
import com.commerce.model.service.CommerceServiceImpl;
import com.commerce.model.service.MemberService;
import com.commerce.model.service.MemberServiceImpl;
import com.commerce.model.util.exception.DuplicatedEntityException;
import com.commerce.model.util.exception.InvalidFormException;
import com.commerce.model.util.exception.NotFoundEntityException;

@WebServlet("/member")
public class MemberServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private MemberService memberService = MemberServiceImpl.getInstance();
    private CommerceService commerceService = CommerceServiceImpl.getInstance();

    public MemberServlet() {
        super();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        doGet(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    String action = request.getParameter("action");
	    String root = request.getContextPath();
	    String path = "/index.jsp";
	    if("brand".equals(action)) {
	        response.sendRedirect(request.getContextPath()+"/index.jsp");
	    } else if("checkid".equals(action)) {
	        // 아이디 유효성 체크
	        int cnt = idCheck(request, response);
	        response.getWriter().append(cnt + "");
	        System.out.println("id check : "+cnt);
//	        response.sendRedirect(root+path);
	    } else if("movelogin".equals(action)) {
	        // 로그인 화면으로 이동
	        System.out.println("Move LoginPage");
	        response.sendRedirect(root+"/member/login.jsp");
	    } else if("login".equals(action)) {
	        // 로그인 버튼 클릭
	        path = loginUser(request, response);
	        response.sendRedirect(root+path);
	    } else if("logout".equals(action)) {
	        path = logout(request, response);
	        response.sendRedirect(root+path);
	    } else if("movesignup".equals(action)) {
	        System.out.println("move sing up page");
	        response.sendRedirect(root+"/member/regist.jsp");
	        // 회원가입 화면으로 이동
	    } else if("register".equals(action)) {
	        path=registerUser(request, response);
	        response.sendRedirect(root+"/member/login.jsp");
	        // user information 으로 이동
	    } else if("showinfo".equals(action)) {
	        path=showInfo(request, response);
	        response.sendRedirect(root+path);
	        // user information 으로 이동
	    } else if("fixinfo".equals(action)) {
	        path=fixInfo(request, response);
	        response.sendRedirect(root+path);
	        // user information 으로 이동
	    } else {
	    	//시데이터 넣어주고 forward?
	    	response.sendRedirect(root + path);
	    }
    
}


	private String fixInfo(HttpServletRequest request, HttpServletResponse response) {
	
		MemberDto memberDto = new MemberDto();
		memberDto.setId(request.getParameter("id"));
		memberDto.setName(request.getParameter("name"));
		memberDto.setPass(request.getParameter("pass"));//[파라미터 name값 변경하기]
		memberDto.setAddress(request.getParameter("address"));
		memberDto.setPhoneNum(request.getParameter("phoneNum"));
		memberDto.setQuestion(request.getParameter("question"));
		memberDto.setAnswer(request.getParameter("answer"));
	    try {
	        // 1. db update
	        // ## 회원정보를 수정한 회원의 dto객체를 리턴
	        // ## userService => fixInfo interface 추가
	        memberService.updateMemberInfo(memberDto);
	        
	        // 2. session update
	        // ## session 정보 수정
	        HttpSession session = request.getSession();
	        session.setAttribute("memberInfo", memberDto);//[jsp에서 memberInfo를 받을것]
	        request.setAttribute("msg", "회원정보가 수정되었습니다.");
	        return "/index.jsp";
	    } catch (InvalidFormException e) {
	    	//[입력값이 유효하지 않을 때 처리]
	        request.setAttribute("msg", "형식에 맞춰서 입력해주세요");
	    	
	    	return "member/showinfo.jsp";
	    } catch (NotFoundEntityException e) {
	    	//[입력값이 이미 DB에 있을 때 처리]
	    	request.setAttribute("msg", "수정된 정보가 없습니다.");
	    	return "/index.jsp";
	    } catch (SQLException e) {
	        e.printStackTrace();
	        request.setAttribute("msg", "회원 수정 중 문제가 발생했습니다.");
	        return "/error/error.jsp";
	    }
	}
	
	private String showInfo(HttpServletRequest request, HttpServletResponse response) {
		
		
	    return "/member/showInfo.jsp";
	}
	private String logout(HttpServletRequest request, HttpServletResponse response) {
	    HttpSession session = request.getSession();
//        session.setAttribute("userInfo", null);
//        session.removeAttribute("userInfo");
        session.invalidate();
        return "/index.jsp";
    }


    private String loginUser(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        String password = request.getParameter("pass");
        MemberDto memberDto = null;
        try {
            memberDto = memberService.login(id, password);
            if(memberDto != null) System.out.println(memberDto.toString());
            HttpSession session = request.getSession();
            
            session.setAttribute("memberInfo", memberDto);
            System.out.println(session.getAttribute("memberInfo").toString());
            String idsv = request.getParameter("idsave");
            if("saveok".equals(idsv)) { // 아이디 저장 체크
//                Cookie setting
                Cookie cookie = new Cookie("loginid", id);
                cookie.setMaxAge(60*60*24*365*20);//20년
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
            } else { // 아이디 저장 체크 X
                Cookie[] cookies = request.getCookies();
                if(cookies != null) {
                    for(int i=0;i<cookies.length;i++) {
                        if(cookies[i].getName().equals("loginid")) {
                            cookies[i].setMaxAge(0);
                            response.addCookie(cookies[i]);
                            break;
                        }
                    }
                }
            }
            // 로그인 성공한 경우
            System.out.println("Login Success");
            return "/index.jsp";

        } catch (InvalidFormException e) {
        	//[입력값이 유효하지 않을 때 처리]
        	System.out.println("invalid exception");
        	return "/index.jsp";
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Login Fail");
            return "/index.jsp";
        }
    }


    private String registerUser(HttpServletRequest request, HttpServletResponse response) {

        MemberDto memberDto = new MemberDto();
        memberDto.setId(request.getParameter("id")); //[jsp파일에서 input태그의 id, name값 바꾸기]
        memberDto.setPass(request.getParameter("pass"));
        memberDto.setName(request.getParameter("name"));
        memberDto.setPhoneNum(request.getParameter("phoneNum"));
        memberDto.setAddress(request.getParameter("address"));
        memberDto.setQuestion(request.getParameter("question"));
        memberDto.setAnswer(request.getParameter("answer"));

        try {
            memberService.registMember(memberDto);
            return "/member/login.jsp";
        } catch (InvalidFormException e) {
        	//[입력값이 유효하지 않을 때 처리]
        	System.out.println("Invalid Exception Error");
        	return "/index.jsp";
        } catch (DuplicatedEntityException e) {
        	//[입력값이 이미 DB에 있을 때 처리]
        	// ## 이미 회원가입한 아이디인 경우 login page로 이동
        	System.out.println("Duplicated Member Exception Error");
        	return "/member/login.jsp";
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException");
            request.setAttribute("msg", "회원 가입 중 문제가 발생했습니다.");
            return "/error/error.jsp";
        }

    }
    private int idCheck(HttpServletRequest request, HttpServletResponse response) {
        int count = 1;
        String id = request.getParameter("ckid");
        try {
			count = memberService.loginCheck(id);
		} catch (InvalidFormException e) {
        	//[입력값이 유효하지 않을 때 처리]
        	
        	return 0;
        } catch (SQLException e) {
        	//[DB에러 날 때]
            e.printStackTrace();
            request.setAttribute("msg", "회원 가입 중 문제가 발생했습니다.");
            return 0;
        }
        return count;
    }

}
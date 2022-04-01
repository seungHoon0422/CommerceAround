package com.commerce.model.service;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.commerce.model.MemberDto;
import com.commerce.model.dao.MemberDao;
import com.commerce.model.dao.MemberDaoImpl;
import com.commerce.model.util.exception.DuplicatedEntityException;
import com.commerce.model.util.exception.InvalidFormException;
import com.commerce.model.util.exception.NotFoundEntityException;
import com.mysql.cj.exceptions.MysqlErrorNumbers;

public class MemberServiceImpl implements MemberService {

	/** DAO객체 저장 */
	private MemberDao memberDao = MemberDaoImpl.getInstance();
	
	/** 싱글톤 패턴 */
	private static MemberServiceImpl instance = new MemberServiceImpl();
	
	private MemberServiceImpl() {}
	
	public static MemberServiceImpl getInstance() {
		return instance;
	}
	
	/** 로그인 */
	@Override
	public MemberDto login(String id, String pass) throws SQLException, InvalidFormException {
		//id, pass유효성 체크 후 객체 만들어서 DAO로 넘김
		//유효하지 않으면 서비스에서 예외처리
		if (!checkId(id) || !checkPass(pass)) {
			throw new InvalidFormException();//추후에 InvalidFormException으로 변경
		}
		return memberDao.login(id, pass);
		
	}

	/** 로그인 id 중복체크 */
	@Override
	public int loginCheck(String id) throws SQLException, InvalidFormException {
		if (!checkId(id))
			throw new InvalidFormException();//추후에 InvalidFormException으로 변경
		return memberDao.loginCheck(id);
	}

	/** 회원가입 */
	@Override
	public void registMember(MemberDto memberDto) throws SQLException, InvalidFormException, DuplicatedEntityException {

		
		String id = memberDto.getId();
		String pass = memberDto.getPass();
	    String name = memberDto.getName();
	    String address = memberDto.getAddress(); 
	    String phoneNum = memberDto.getPhoneNum(); 
	    String question = memberDto.getQuestion();
	    String answer = memberDto.getAnswer();
	    
	    //유효성 체크
	    if (!checkId(id) || !checkPass(pass)) //id, pw유효성체크
	    	throw new InvalidFormException();//invalid로 변경
	    if (name == null || "".equals(name) || name.length() > 15) //null,empty체크 15자 이하인지
	    	throw new InvalidFormException();
	    if (address == null || "".equals(address) || address.length() > 100) //null,empty체크 100자 이하인지
	    	throw new InvalidFormException();
	    if (phoneNum == null || "".equals(phoneNum) || !isValidPhoneNumber(phoneNum))//null,empty체크, 전화번호형식인지
	    	throw new InvalidFormException();
	    if (question == null || "".equals(question) || question.length() > 45)//null,empty체크 45자 이하인지
	    	throw new InvalidFormException();
	    if (answer == null || "".equals(answer) || answer.length() > 45)//null,empty체크 45자 이하인지
	    	throw new InvalidFormException();
	   
	    try {
	    	memberDao.registMember(memberDto);
		} catch (SQLException e) {
			if (e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY)//entry가 겹칠 때 나는 SQL ErrorCode
				throw new DuplicatedEntityException();
			throw e;
		}
	}

	/** 회원정보 수정 */
	@Override
	public void updateMemberInfo(MemberDto memberDto) throws SQLException, InvalidFormException, NotFoundEntityException {
		String id = memberDto.getId();
		String pass = memberDto.getPass();
	    String address = memberDto.getAddress(); 
	    String phoneNum = memberDto.getPhoneNum(); 
	    String question = memberDto.getQuestion();
	    String answer = memberDto.getAnswer();
		//유효성 확인 -> null이 아니면 유효해야 함
	    if (!checkId(id))
	    	throw new InvalidFormException();
		if (pass != null && !checkPass(pass))
	    	throw new InvalidFormException();
	    if (address != null && ("".equals(address) || address.length() > 100)) //null,empty체크 100자 이하인지
	    	throw new InvalidFormException();
	    if (phoneNum != null && ("".equals(phoneNum) || !isValidPhoneNumber(phoneNum)))//null,empty체크, 전화번호형식인지
	    	throw new InvalidFormException();
	    if (question != null && ("".equals(question) || question.length() > 45))//null,empty체크 45자 이하인지
	    	throw new InvalidFormException();
	    if (answer != null && ("".equals(answer) || answer.length() > 45))//null,empty체크 45자 이하인지
	    	throw new InvalidFormException();
	    try {
	    	memberDao.updateMemberInfo(memberDto);
		} catch (SQLException | NotFoundEntityException e) {
			throw e;
		}
	}

	/** 추가 함수 */
	private boolean checkId(String id) {
		//id : 소문자와 숫자 조합만 허용, 소문자가 적어도 1개, 맨 첫글자는 무조건 소문자,
		//     길이 5자 이상, not null and ""
		if (id == null || "".equals(id) || id.length() < 5)
			return false;
		char[] id_ = id.toCharArray();
		for (int i = 0; i < id_.length; i++) {
			if (i == 0 && !isLowerCase(id_[i]))
				return false;
			if (!isLowerCase(id_[i]) && !isNumber(id_[i]))
				return false;
		}
		return true;
	}
	
	private boolean isLowerCase(char c) {
		if (c >='a' && c <= 'z')
			return true;
		return false;
	}
	
	private boolean isNumber(char c) {
		if (c >='0' && c <= '9')
			return true;
		return false;
	}

	private boolean checkPass(String pass) {
		//pass : 길이 제한 5자 이상, 15자 이하, whitespace는 안됨
		if (pass == null || "".equals(pass) || pass.length() < 5 || pass.length() > 15)
			return false;
		
		for (int i = 0; i < pass.length(); i++) {
			if (Character.isWhitespace(pass.charAt(i)))
				return false;
		}
		return true;
	}
	
	/** 전화번호 형식 확인 */
	private boolean isValidPhoneNumber(String phoneNum) {
		for (int i = 0; i < phoneNum.length(); i++) {//숫자나 '-'로 이루어졌는지 확인
			char c = phoneNum.charAt(i);
			if (!isNumber(c) && c != '-')
				return false;
		}
		
        Pattern pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");//형태가 xxx-xxxx-xxxx인지 확인
        Matcher matcher = pattern.matcher(phoneNum);
        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
	}
	
}

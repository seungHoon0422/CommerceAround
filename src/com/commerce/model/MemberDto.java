package com.commerce.model;

public class MemberDto {
	private String id;
    private String pass;
    private String name;
    private String address;
    private String phoneNum;
    private String question;
    private String answer;
    private String regDate;
    
    public MemberDto() {
	}
    
	public MemberDto(String id, String pass, String name, String address, String phoneNum, String question,
			String answer, String regDate) {
		super();
		this.id = id;
		this.pass = pass;
		this.name = name;
		this.address = address;
		this.phoneNum = phoneNum;
		this.question = question;
		this.answer = answer;
		this.regDate = regDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", pass=" + pass + ", name=" + name + ", address=" + address + ", phoneNum="
				+ phoneNum + ", question=" + question + ", answer=" + answer + ", regDate=" + regDate + "]";
	}
}

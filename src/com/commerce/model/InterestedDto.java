package com.commerce.model;

public class InterestedDto {

	private String id;
	private String dongCode;
	private String largeCode;
	
	public InterestedDto() {
	}
	
	public InterestedDto(String id, String dongCode, String largeCode) {
		super();
		this.id = id;
		this.dongCode = dongCode;
		this.largeCode = largeCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDongCode() {
		return dongCode;
	}

	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}

	public String getLargeCode() {
		return largeCode;
	}

	public void setLargeCode(String largeCode) {
		this.largeCode = largeCode;
	}

	@Override
	public String toString() {
		return "InterestedDto [id=" + id + ", dongCode=" + dongCode + ", largeCode=" + largeCode + "]";
	}
	
}

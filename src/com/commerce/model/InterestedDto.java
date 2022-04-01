package com.commerce.model;

public class InterestedDto {

	private String id;
	private String dongCode;
	private String middleCode;
	
	public InterestedDto() {
	}
	
	public InterestedDto(String id, String dongCode, String middleCode) {
		super();
		this.id = id;
		this.dongCode = dongCode;
		this.middleCode = middleCode;
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

	public String getMiddleCode() {
		return middleCode;
	}

	public void setMiddleCode(String middleCode) {
		this.middleCode = middleCode;
	}

	@Override
	public String toString() {
		return "InterestedDto [id=" + id + ", dongCode=" + dongCode + ", middleCode=" + middleCode + "]";
	}
	
}

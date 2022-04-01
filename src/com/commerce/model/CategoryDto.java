package com.commerce.model;

public class CategoryDto {
	private String largeCode;
	private String largeName;
	private String middleCode;
	private String middleName;
	
	public CategoryDto() {
	}

	public CategoryDto(String largeCode, String largeName, String middleCode, String middleName) {
		super();
		this.largeCode = largeCode;
		this.largeName = largeName;
		this.middleCode = middleCode;
		this.middleName = middleName;
	}

	public String getLargeCode() {
		return largeCode;
	}

	public void setLargeCode(String largeCode) {
		this.largeCode = largeCode;
	}

	public String getLargeName() {
		return largeName;
	}

	public void setLargeName(String largeName) {
		this.largeName = largeName;
	}

	public String getMiddleCode() {
		return middleCode;
	}

	public void setMiddleCode(String middleCode) {
		this.middleCode = middleCode;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Override
	public String toString() {
		return "CategoryDto [largeCode=" + largeCode + ", largeName=" + largeName + ", middleCode=" + middleCode
				+ ", middleName=" + middleName + "]";
	}
}

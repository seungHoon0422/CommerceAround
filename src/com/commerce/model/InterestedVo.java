package com.commerce.model;

public class InterestedVo {

	private String dongName;
	private String largeName;
	private String dongCode;
	private String largeCode;
	
	public InterestedVo() {
	}

	public String getDongName() {
		return dongName;
	}

	public void setDongName(String dongName) {
		this.dongName = dongName;
	}

	public String getLargeName() {
		return largeName;
	}

	public void setLargeName(String largeName) {
		this.largeName = largeName;
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
		return "InterestedVo [dongName=" + dongName + ", largeName=" + largeName + "]";
	}
	
}

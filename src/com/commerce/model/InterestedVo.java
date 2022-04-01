package com.commerce.model;

public class InterestedVo {

	private String dongName;
	private String largeName;
	
	public InterestedVo() {
	}

	public InterestedVo(String dongName, String largeName) {
		super();
		this.dongName = dongName;
		this.largeName = largeName;
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

	@Override
	public String toString() {
		return "InterestedVo [dongName=" + dongName + ", largeName=" + largeName + "]";
	}
	
}

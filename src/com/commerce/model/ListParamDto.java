package com.commerce.model;

public class ListParamDto {

	private String key;
	private String word;
	private int start;
	private int countPerPage;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getCountPerPage() {
		return countPerPage;
	}

	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}

	@Override
	public String toString() {
		return "ListParamDto [key=" + key + ", word=" + word + ", start=" + start + ", currentPerPage=" + countPerPage
				+ "]";
	}

}

package com.commerce.model;

public class CommerceDto {
	private String name;
	private String address;
	private String floor;
	private String smallName;
	private String lon;
	private String lat;//위도
	
	public CommerceDto() {
	}

	public CommerceDto(String name, String address, String floor, String smallName, String lon, String lat) {
		super();
		this.name = name;
		this.address = address;
		this.floor = floor;
		this.smallName = smallName;
		this.lon = lon;
		this.lat = lat;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public void setSmallName(String smallName) {
		this.smallName = smallName;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	@Override
	public String toString() {
		return "CommerceDto [name=" + name + ", address=" + address + ", floor=" + floor + ", smallName=" + smallName
				+ ", lon=" + lon + ", lat=" + lat + "]";
	}
}

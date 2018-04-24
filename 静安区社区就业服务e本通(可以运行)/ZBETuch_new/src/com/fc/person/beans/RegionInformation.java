package com.fc.person.beans;

public class RegionInformation {
	private String regionId;
	private String regionName;
	private String cityId;

	public RegionInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RegionInformation(String regionId, String regionName, String cityId) {
		super();
		this.regionId = regionId;
		this.regionName = regionName;
		this.cityId = cityId;
	}

	public String getRegioId() {
		return regionId;
	}

	public void setRegioId(String regioId) {
		this.regionId = regioId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	@Override
	public String toString() {
		return regionName;
	}

}

package com.fc.person.beans;

import java.util.ArrayList;
import java.util.List;

public class StreetInformation {
	private String streetId;
	private String streetName;
	private String regionId;

	public StreetInformation() {
		super();
	}

	public StreetInformation(String streetId, String streetName, String regionId) {
		super();
		this.streetId = streetId;
		this.streetName = streetName;
		this.regionId = regionId;
	}

	public StreetInformation(String streetId, String streetName) {
		super();
		this.streetId = streetId;
		this.streetName = streetName;
	}

	public String getStreetId() {
		return streetId;
	}

	public void setStreetId(String streetId) {
		this.streetId = streetId;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	@Override
	public String toString() {
		return streetName;
	}

	public List<StreetInformation> GetStreetList() {
		List<StreetInformation> street_list = new ArrayList<StreetInformation>();
		street_list.add(new StreetInformation("0", "请选择"));
		street_list.add(new StreetInformation("8001", "天目西路街"));
		street_list.add(new StreetInformation("8006", "北站街道"));
		street_list.add(new StreetInformation("8007", "宝山路街道"));
		street_list.add(new StreetInformation("8012", "共和新路街道"));
		street_list.add(new StreetInformation("8013", "大宁路街道"));
		street_list.add(new StreetInformation("8014", "彭浦新村街道"));
		street_list.add(new StreetInformation("8015", "临汾路街道"));
		street_list.add(new StreetInformation("8016", "芷江西路街道"));
		street_list.add(new StreetInformation("8101", "彭浦镇街道"));
		return street_list;

	}

}

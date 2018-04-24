package com.fc.first.beans;

import java.io.Serializable;

public class LocationInformation implements Serializable {
	private String id;
	private String staff;
	private String mark;
	private double longitude;
	private double latitude;
	private double height;
	private String create_date;

	public LocationInformation() {
		super();

	}

	public LocationInformation(String id, String staff, String mark,
			double longitude, double latitude, double height, String create_date) {
		super();
		this.id = id;
		this.staff = staff;
		this.mark = mark;
		this.longitude = longitude;
		this.latitude = latitude;
		this.height = height;
		this.create_date = create_date;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	@Override
	public String toString() {
		return "LocationInformation [id=" + id + ", staff=" + staff + ", mark="
				+ mark + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", height=" + height + ", create_date=" + create_date + "]";
	}

}

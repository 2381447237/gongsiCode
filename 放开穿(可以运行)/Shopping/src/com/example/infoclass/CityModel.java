package com.example.infoclass;

import java.io.Serializable;
import java.util.List;

public class CityModel implements Serializable{
	public String $id;
	public Refrence tbl_Province_Dict;
	public List<DistrictModel> tbl_District_Dict;
	public String CityID;
	public String CityName;
	public String ProvinceID;
	public String get$id() {
		return $id;
	}
	public void set$id(String $id) {
		this.$id = $id;
	}
	
	public List<DistrictModel> getTbl_District_Dict() {
		return tbl_District_Dict;
	}
	public void setTbl_District_Dict(List<DistrictModel> tbl_District_Dict) {
		this.tbl_District_Dict = tbl_District_Dict;
	}
	public String getCityID() {
		return CityID;
	}
	public void setCityID(String cityID) {
		CityID = cityID;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}
	public String getProvinceID() {
		return ProvinceID;
	}
	public void setProvinceID(String provinceID) {
		ProvinceID = provinceID;
	}
	
	
}

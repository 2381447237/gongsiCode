package com.example.infoclass;

import java.io.Serializable;
import java.util.List;

public class DistrictModel implements Serializable{
	public String $id;
	public Refrence tbl_City_Dict;
	public String DistrictID;
	public String DistrictName;
	public String CityID;
	public String ProvinceID;
	
	public DistrictModel(String DistrictName,String DistrictID){
		this.DistrictName=DistrictName;
	}
	public DistrictModel(){
	}
	public String get$id() {
		return $id;
	}
	public void set$id(String $id) {
		this.$id = $id;
	}
	public String getDistrictID() {
		return DistrictID;
	}
	public void setDistrictID(String districtID) {
		DistrictID = districtID;
	}
	public String getDistrictName() {
		return DistrictName;
	}
	public void setDistrictName(String districtName) {
		DistrictName = districtName;
	}
	public String getCityID() {
		return CityID;
	}
	public void setCityID(String cityID) {
		CityID = cityID;
	}
	public String getProvinceID() {
		return ProvinceID;
	}
	public void setProvinceID(String provinceID) {
		ProvinceID = provinceID;
	}
	
	
}

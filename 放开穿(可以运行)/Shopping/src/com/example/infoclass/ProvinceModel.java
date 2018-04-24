package com.example.infoclass;

import java.io.Serializable;
import java.util.List;

public class ProvinceModel implements Serializable{
	public String $id;
	public List<CityModel> tbl_City_Dict;
	public String ProvinceID;
	public String ProvinceName;
	public String get$id() {
		return $id;
	}
	public void set$id(String $id) {
		this.$id = $id;
	}
	public List<CityModel> getTbl_City_Dict() {
		return tbl_City_Dict;
	}
	public void setTbl_City_Dict(List<CityModel> tbl_City_Dict) {
		this.tbl_City_Dict = tbl_City_Dict;
	}
	public String getProvinceID() {
		return ProvinceID;
	}
	public void setProvinceID(String provinceID) {
		ProvinceID = provinceID;
	}
	public String getProvinceName() {
		return ProvinceName;
	}
	public void setProvinceName(String provinceName) {
		ProvinceName = provinceName;
	}
	
	
}

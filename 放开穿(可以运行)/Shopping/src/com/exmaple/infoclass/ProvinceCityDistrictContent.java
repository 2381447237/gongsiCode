package com.exmaple.infoclass;

import java.util.List;

public class ProvinceCityDistrictContent {

	public String $id;
	public int ProvinceID;//省ID
	public String ProvinceName;//省名  
	public List<TblCityDictEntity> tbl_City_Dict;
	
	public static class TblCityDictEntity{		
		public String $id;
		public TblProvinceDictEntity tbl_Province_Dict;
		public int CityID;//市ID
		public String CityName;//市名	
		public int ProvinceID;
		public List<TblDistrictDictEntity> tbl_District_Dict;
		
		public static class TblProvinceDictEntity{			
			public String $ref;
			
		}
		
		public static class TblDistrictDictEntity{		
			public String $id;
			public TblCityDict tbl_City_Dict;
			public int DistrictID;//区ID
			public String DistrictName;//区名	
			public int CityID;
			public int ProvinceID;
			
			public TblDistrictDictEntity(String districtName,int districtID) {
				super();
				DistrictName = districtName;
				DistrictID=districtID;
			}



			public static class TblCityDict{
				
				public String $ref;
			}
			
		}
		
	}
	
}
 // [{"$id":"1","tbl_City_Dict":[{"$id":"2","tbl_Province_Dict":{"$ref":"1"},"tbl_District_Dict":
	// [{"$id":"3","tbl_City_Dict":{"$ref":"2"},"DistrictID":5,"DistrictName":"静安区","CityID":1,"ProvinceID":1},
	// {"$id":"4","tbl_City_Dict":{"$ref":"2"},"DistrictID":7,"DistrictName":"闸北区","CityID":1,"ProvinceID":1},
	// {"$id":"5","tbl_City_Dict":{"$ref":"2"},"DistrictID":8,"DistrictName":"虹口区","CityID":1,"ProvinceID":1},
	// {"$id":"6","tbl_City_Dict":{"$ref":"2"},"DistrictID":9,"DistrictName":"杨浦区","CityID":1,"ProvinceID":1},
	// {"$id":"7","tbl_City_Dict":{"$ref":"2"},"DistrictID":10,"DistrictName":"闵行区","CityID":1,"ProvinceID":1},
	// {"$id":"8","tbl_City_Dict":{"$ref":"2"},"DistrictID":11,"DistrictName":"宝山区","CityID":1,"ProvinceID":1},
	// {"$id":"9","tbl_City_Dict":{"$ref":"2"},"DistrictID":12,"DistrictName":"嘉定区","CityID":1,"ProvinceID":1},
	// {"$id":"10","tbl_City_Dict":{"$ref":"2"},"DistrictID":13,"DistrictName":"浦东新区","CityID":1,"ProvinceID":1},
	// {"$id":"11","tbl_City_Dict":{"$ref":"2"},"DistrictID":14,"DistrictName":"金山区","CityID":1,"ProvinceID":1},
	// {"$id":"12","tbl_City_Dict":{"$ref":"2"},"DistrictID":15,"DistrictName":"松江区","CityID":1,"ProvinceID":1},
	// {"$id":"13","tbl_City_Dict":{"$ref":"2"},"DistrictID":16,"DistrictName":"青浦区","CityID":1,"ProvinceID":1},
	// {"$id":"14","tbl_City_Dict":{"$ref":"2"},"DistrictID":17,"DistrictName":"奉贤区","CityID":1,"ProvinceID":1},
	// {"$id":"15","tbl_City_Dict":{"$ref":"2"},"DistrictID":18,"DistrictName":"崇明县","CityID":1,"ProvinceID":1},
	// {"$id":"16","tbl_City_Dict":{"$ref":"2"},"DistrictID":20,"DistrictName":"宝山区","CityID":1,"ProvinceID":1},
	// {"$id":"17","tbl_City_Dict":{"$ref":"2"},"DistrictID":23,"DistrictName":"黄埔区","CityID":1,"ProvinceID":1}],
	// "CityID":1,"CityName":"上海市","ProvinceID":1}],"ProvinceID":1,"ProvinceName":"上海"}]
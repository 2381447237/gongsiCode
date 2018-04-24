package com.fc.main.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.fc.main.tools.HttpUtil;

public class GuanLiDao {
	
	/**
	 * 上传会议管理
	 * @return 
	 */
	public String setMeetGuanLi(Map<String, String> data) {
		String url = "/Json/Set_Meeting_Master.aspx";
		System.out.println(data.get("data"));
			String value = HttpUtil.postJson(url, data.get("data"),null);
			return value;
	}

}

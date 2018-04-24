package com.fc.main.dao;

import java.util.Map;

import com.fc.main.tools.HttpUtil;

public class MeetGuanLiDao {

	/**
	 * 上传会议管理
	 * 
	 * @return
	 */
	public String setMeetGuanLi(Map<String, String> data) {
		String url = "/Json/Set_Meeting_Master.aspx";
		System.out.println(data.get("data"));
		String value = HttpUtil.postJson(url, data.get("data"));
		System.out.println("++++++++++++++++" + value);
		return value;

	}

}
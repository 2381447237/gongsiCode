package com.fc.meetguanli.beans;

import java.util.Map;

import com.fc.main.tools.HttpUtil;

public class GuanLiDao {

	/**
	 * 上传会议管理
	 * 
	 * @return
	 */
	public String setMeetGuanLi(Map<String, String> data) {
		String url = "/Json/Set_Meeting_Master.aspx";
		System.out.println(data.get("json"));
		String value = HttpUtil.postJson(url, data.get("json"));
		System.out.println(value);
		return value;

	}

	public String getMeetGuanLiNum(Map<String, String> data) {
		String url = "/Json/Set_Meeting_Master.aspx";
		System.out.println(data.get("json"));
		String value = HttpUtil.postJson(url, data.get("json"));
		System.out.println(value);
		return value;

	}

}

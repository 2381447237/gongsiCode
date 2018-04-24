package com.fc.main.dao;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class ERecordDao {

	public String getERecordInfo(Map<String, String> data) {
		String url = "/Json/Get_TB_Staff_Pad_File.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}

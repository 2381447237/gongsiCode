package com.fc.main.dao;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class ReportDao {
	/**
	 * 得到报表更新时间
	 * 
	 * @param data
	 * @return
	 */
	public String getUpdateTime() {
		String url = "/Json/Get_Update_Time.aspx";

		try {
			String value = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}

package com.fc.main.dao;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class PersonPolicyDao {
	
	/**
	 * 得到政策分类列表
	 * @return
	 */
	public String getPolicyLineLevel(Map<String, String> data) {
		String url = "/Json/Get_Policy_Tree.aspx";
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

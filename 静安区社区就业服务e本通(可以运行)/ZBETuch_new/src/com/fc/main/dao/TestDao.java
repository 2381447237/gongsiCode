package com.fc.main.dao;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class TestDao {
	/**
	 * 得到政策分类列表
	 * 
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

	public String getAnswerUserful(Map<String, String> data) {
		String url = "/Json/Get_Check_Policy_Question_Answer.aspx";
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

	public String getFileUserful(Map<String, String> data) {
		String url = "/Json/Get_Check_Policy_File_New.aspx";
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

	public String getCaseUserful(Map<String, String> data) {
		String url = "/Json/Get_Check_Policy_Case.aspx";
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

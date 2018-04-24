package com.fc.main.dao;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class PolicyDao {

	/**
	 * 得到政策答案
	 * 
	 * @param type
	 * @param keyWord
	 * @return
	 */
	public String getPolicy(String type, String keyWord) {

		try {
			String url = "/Json/Get_QUESTION_ANSWER.aspx?type="
					+ URLEncoder.encode(type, "UTF-8") + "&code="
					+ URLEncoder.encode(keyWord, "UTF-8");
			String valueString = HttpUtil.getRequest(url);
			return valueString;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 
	 * 提交问题
	 * 
	 * @param value
	 * @return
	 */
	public String setAsk(Map<String, String> data) {
		String url = "/Json/Set_QUESTION_ANSWER_User.aspx";

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

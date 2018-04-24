package com.fc.main.dao;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class SetPwdDao {

	/**
	 * 修改个人密码
	 * @param data
	 * @return
	 */
	public String setPwd(Map<String, String> data){
		String url ="/Json/Set_Pwd.aspx";
		try {
			String valueString  = HttpUtil.postRequest(url, data);
			return valueString;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}

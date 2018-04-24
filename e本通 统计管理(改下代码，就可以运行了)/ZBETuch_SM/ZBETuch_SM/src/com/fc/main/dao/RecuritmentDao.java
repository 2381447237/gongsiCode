package com.fc.main.dao;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class RecuritmentDao {

	/**
	 * 招聘会获取列表信息
	 * @return 
	 */
	public String getRecuritmentList(Map<String, String> data) {
		String url = "/Json/GetJobFairMaster.aspx";
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
	
	/**
	 * 获取招聘会下岗位列表
	 * @return 
	 */
	public String getRecuritmentDetailList(Map<String, String> data) {
		String url = "/Json/GetJobFairDetail.aspx";
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
	
	/**
	 * 返回是否已读按钮状态
	 * @param master_id 招聘会的id
	 * @return yd 已读 wd 未读 gq 过期
	 */
	public String getJobFairCheck(Map<String, String> data) {
		String url = "/Json/Get_JobFairCheck.aspx";
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
	
	/**
	 * 设置已读
	 * @param master_id 招聘会的id
	 * @return true false
	 */
	public String setJobFairCheck(Map<String, String> data) {
		String url = "/Json/Set_JobFairCheck.aspx";
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

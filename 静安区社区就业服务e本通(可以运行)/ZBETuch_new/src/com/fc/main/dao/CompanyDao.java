package com.fc.main.dao;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class CompanyDao {

	/**
	 * 根据页数查询工作列表
	 * 
	 * @param index
	 * @return
	 */
	public String getJobListByPage(int index) {
		String url = "/Json/GetJobs.aspx?page=" + index + "&rows=15";
		try {
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
	 * @param index
	 * @param data
	 * @return
	 */
	public String getJobListByPage(int index, Map<String, String> data) {
		String url = "/Json/GetJobs.aspx?page=" + index + "&rows=15&ComId="
				+ data.get("ComId");
		String value = "";
		try {
			value = HttpUtil.getRequest(url);

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return value;
	}

	public String getJobListByPage(Map<String, String> data) {
		String url = "/Json/GetJobs_Search.aspx";
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
	 * 根据身份证查询岗位信息
	 * 
	 * @param data
	 * @return
	 */
	public String getJobListByPeople(Map<String, String> data) {
		String url = "/Json/Get_JobsInfo.aspx";
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
	 * 查询招聘详细信息
	 * 
	 * @param id
	 * @return
	 */
	public String getJobDetail(String jobno) {
		String url = "/Json/GetJobDetail.aspx?JobNo=" + jobno;
		try {
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
	 * 得到联系人信息
	 * 
	 * @param jobno
	 * @return
	 */
	public String getLinkMan(String jobno) {
		String url = "/Json/Get_Career_Counselor.aspx?JobNo=" + jobno;
		try {
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
	 * 得到企业列表
	 * 
	 * @param data
	 * @return
	 */
	public String getCompanyList(Map<String, String> data) {
		String url = "/Json/Get_Company_Infos.aspx";

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
	 * 得到职位列表
	 * 
	 * @return
	 */
	public String getLineLevel(Map<String, String> data) {
		String url = "/Json/Get_Line_level.aspx";
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
	 * 根据职务id查询人员信息
	 * 
	 * @param data
	 * @return
	 */
	public String getPersonByLevel(Map<String, String> data) {
		String url = "/Json/Get_Line_Staff.aspx";

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

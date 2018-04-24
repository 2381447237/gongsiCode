package com.fc.main.dao;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import android.util.Log;

import com.fc.main.tools.HttpUtil;

public class GradeateDao {

	/**
	 * 获取应届毕业生 单条记录 参数 sfz page rows
	 * 
	 * @param data
	 * @return
	 */
	public String getGradeate(Map<String, String> data) {
		
		 Log.i("2017-2-21","***获取应届毕业生 单条记录 参数***");
		
		String url = "/Json/Get_Graduate_Master.aspx";
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
	 * 保存毕业生信息 返回id
	 * 
	 * @param data
	 * @return
	 */
	public String saveGradeate(Map<String, String> data) {
		String url = "/Json/Set_Graduate_Master.aspx";
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
	 * 获取个人求职意愿 单条记录 参数master_id
	 * 
	 * @param data
	 * @return
	 */
	public String getGradeateJobIntent(Map<String, String> data) {
		String url = "/Json/Get_JobIntent.aspx";
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
	 * 保存个人求职意愿
	 * 
	 * @param data
	 * @return
	 */
	public String saveGradeateJobIntent(Map<String, String> data) {
		String url = "/Json/Set_JobIntent.aspx";
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
	 * 保存走访情况 删除添加参数 delete = ture,false
	 * 
	 * @param data
	 * @return
	 */
	public String saveWorkMark(Map<String, String> data) {
		String url = "/Json/Set_JobMarks.aspx";

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
	 * 获取走访情况 参数master_id
	 * 
	 * @param data
	 * @return
	 */
	public String getJobMarkList(Map<String, String> data) {
		String url = "/Json/Get_JobMarks.aspx";

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
	 * 保存职业指导情况 删除添加参数 delete = ture,false
	 * 
	 * @param data
	 * @return
	 */
	public String saveJobGride(Map<String, String> data) {
		String url = "/Json/Set_JobGuide.aspx";

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
	 * 获取职业指导情况 参数master_id
	 * 
	 * @param data
	 * @return
	 */
	public String getJobGrideList(Map<String, String> data) {
		String url = "/Json/Get_JobGuide.aspx";

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
	 * 获取此街道下的操作人信息
	 * 
	 * @param data
	 * @return
	 */
	public String getCommitteeStaffList(Map<String, String> data) {
		String url = "/Json/Get_COMMITTEE_Staff.aspx";

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

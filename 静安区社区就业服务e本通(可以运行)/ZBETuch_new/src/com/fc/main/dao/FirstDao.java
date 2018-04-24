package com.fc.main.dao;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class FirstDao {

	/**
	 * 得到待办事宜
	 * 
	 * @param data
	 * @return
	 */
	public String getPendWordList(Map<String, String> data) {
		try {
			String url = "/Json/Get_PendingWork.aspx?page=" + data.get("page")
					+ "&rows=" + data.get("rows") + "" + "&type="
					+ URLEncoder.encode(data.get("type"), "UTF-8");
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
	 * 查询下属待办事宜
	 * 
	 * @param data
	 * @return
	 */
	public String getStaffPendWorkList(Map<String, String> data) {
		String url = "/Json/GetStaffPedingWork.aspx";

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
	 * 查询近期热点
	 * 
	 * @param data
	 * @return
	 */
	public String getNewsList(Map<String, String> data) {
		String url = "/Json/Get_News.aspx?page=" + data.get("page") + "&rows="
				+ data.get("rows");
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
	 * 查询留言板
	 * 
	 * @param data
	 * @return
	 */
	public String getMsgboardList(Map<String, String> data) {
		String url = "/Json/Get_MsgBoard_Master.aspx?page=" + data.get("page")
				+ "&rows=" + data.get("rows");
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
	 * 获取工作列表
	 * 
	 * @param data
	 * @return
	 */
	public String getJobList(Map<String, String> data) {
		String url = "/Json/GetJobs.aspx?page=" + data.get("page") + "&rows="
				+ data.get("rows");
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
	 * 获取登录人员信息
	 * 
	 * @return
	 */
	public String getStaff() {
		String url = "/Json/Get_Staff.aspx";
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
	 * 获取登录人员照片
	 * 
	 * @return
	 */
	public byte[] getStaffImg() {
		String url = "/Json/GetStaffPic.aspx";
		try {
			byte[] data = HttpUtil.getImage(url);
			return data;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改待办事宜为已完成
	 * 
	 * @param data
	 * @return
	 */
	public String updatePendWork(Map<String, String> data) {
		String url = "/Json/Set_PendingWork.aspx";
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
	 * 得到回帖信息
	 * 
	 * @param data
	 * @return
	 */
	public String getMsgBoardDetail(Map<String, String> data) {
		String url = "/Json/Get_MsgBoard_Detile.aspx";
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
	 * 回帖操作
	 * 
	 * @param data
	 * @return
	 */
	public String setMsgBoardDetail(Map<String, String> data) {
		String url = "/Json/Set_MsgBoard_Detile.aspx";
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

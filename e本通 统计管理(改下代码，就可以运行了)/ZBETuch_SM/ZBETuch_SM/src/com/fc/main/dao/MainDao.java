package com.fc.main.dao;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class MainDao {

	/**
	 * 得到单位性质
	 * @return
	 */
	public String getCompanyProperty(){
		String url = "/Json/Get_CompanyProperty.aspx";
		try {
			String value  = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 得到工种信息
	 * @return
	 */
	public String getTypeOfWork(){
		return "";
	}
	
	/**
	 * 得到文化程度
	 * @return
	 */
	public String getEdu(){
		String url = "/Json/Get_Edu.aspx";
		try {
			String value  = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 得到职业分类大类
	 * @return
	 */
	public String getZyfl(){
		String url = "/Json/Get_Zyfl.aspx";
		try {
			String value  = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 得到职业分类小类
	 * @return
	 */
	public String getZyflChild(int parent_id){
		String url = "/Json/Get_Zyfl_Child.aspx?parent_id="+parent_id;
		try {
			String value  = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 得到行业分类大类
	 * @return
	 */
	public String getIndustryClass(){
		String url = "/Json/Get_Industry_Class.aspx";
		try {
			String value  = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 得到行业分类小类
	 * @return
	 */
	public String getIndustryChild(int parentid){
		String url = "/Json/Get_Industry_Class_Child.aspx?parent_code="+parentid;
		try {
			String value  = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 得到工作性质
	 * @return
	 */
	public String getGzxz(){
		String url = "/Json/Get_Gzxz.aspx";
		try {
			String value  = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 得到工作班时
	 * @return
	 */
	public String getGzbs(){
		String url = "/Json/Get_Gzbs.aspx";
		try {
			String value  = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 得到工作地区
	 * @return
	 */
	public String getAreaInfo(){
		String url = "/Json/Get_Area_Info.aspx";
		try {
			String value  = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 得到政策文件类型
	 * @return
	 */
	public String getPolicyInfo(){
		String url = "/Json/Get_policy_Type.aspx";
		try {
			String value  = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 得到联系人信息
	 * @return
	 */
	public String getLinkMan(){
		String url="/Json/Get_Career_Counselor.aspx";
		
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
	
	/**
	 * 全局获取推送信息
	 * @param content,staff
	 */
	public String postMainNum(Map<String, String>data){
		
//		String url = "/comet_broadcast.asyn";
		String url = "/Msg.GetMsg.bspx";
		
		try {
			String value = HttpUtil.postLongRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return "";		
	}
	
	
	
	
	
	
	
	
	
	
	
}

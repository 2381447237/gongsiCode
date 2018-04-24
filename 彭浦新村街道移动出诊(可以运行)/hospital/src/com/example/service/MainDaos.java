package com.example.service;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.example.httpurl.HttpTool;

import android.util.Log;



public class MainDaos {
	/**
	 * 登录
	 * @param content,staff
	 */
	public String getLoginNum(String user,String password){
		
	//	String url = "/Json/Get_MsgBoard_Detile.aspx";
		String url = "/Login.aspx?";
		try {
			String value = HttpTool.getValue(url,user,password);
			Log.i("qwj", "value=="+value);
			return value;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";		
	}
	//待诊病人列表
	public String getUnide(){
		String url="/Json/usp_get_UnVisitedInfo.aspx";
		
		try {
			String value = HttpTool.getRequest(url);
			Log.i("qwj", "aaaaa=="+value);
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
	//根据输入码获取诊断
	public String postDiag(Map<String, String> data){
		String url="/json/vw_DiagnosisInfo.aspx";
		
		try {
			String value = HttpTool.postRequest(url, data);
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
	//根据输入码获取收费项目
	public String postDrug(Map<String, String> data){
		String url="/json/vw_ItemsInfo.aspx";
		
		try {
			String value = HttpTool.postRequest(url, data);
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
	//频率
	public String getFrequency(){
		String url="/json/vw_FrequencyInfo.aspx";
		
		try {
			String value = HttpTool.getRequest(url);
			//Log.i("qwj", "value=="+value);
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
	//用法
	public String getUsage(){
		String url="/json/vw_UsageInfo.aspx";
		
		try {
			String value = HttpTool.getRequest(url);
			//Log.i("qwj", "value=="+value);
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
	
	static int count=0;
	//保存诊断
	public String postDiagnosis(Map<String, String> data){
		String url="/json/usp_do_HomeVisitDiagnosis.aspx";
		
		try {
			String value = HttpTool.postRequest(url, data);
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
	//保存新下达处方
	public String postPrescribe(Map<String, String> data){
		String url="/json/usp_do_HomeVisitPrescribe.aspx";
		
		try {
			String value = HttpTool.postRequest(url, data);
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
	//处方列表
	public String postNumInfo(Map<String, String> data){
		String url="/json/usp_get_HomeVisitPrescribeInfo_Result.aspx";
		
		try {
			String value = HttpTool.postRequest(url, data);
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
	
	public String postHomeVisitDiagnosis(Map<String, String> data){
		String url="/json/usp_do_HomeVisitDiagnosis.aspx";
		
		try {
			String value = HttpTool.postRequest(url, data);
			Log.i("qwj", "value=="+value);
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
	//获取历史处方信息列表
	public String postVisitedDiagnosis(Map<String, String> data){
		String url="/json/vw_VisitedInfo.aspx";
		
		try {
			String value = HttpTool.postRequest(url, data);
			Log.i("qwj", "xxx=="+value);
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
	public String deleteHomeVisitescribeInfo(Map<String, String> data){
		String url="/json/usp_do_delHomeVisitPrescribe.aspx";
		try {
			String value = HttpTool.postRequest(url, data);
			Log.i("aaa", "xxx=="+value);
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
	public String postHomeVisitescribeInfo(Map<String, String> data){
		String url="/json/usp_get_HomeVisitPrescribedetailsInfo_Result.aspx";
		
		try {
			String value = HttpTool.postRequest(url, data);
			Log.i("aaa", "xxx=="+value);
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

package com.example.httpurl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.http.client.ClientProtocolException;

import com.example.hospital.LoginActivity;


import android.content.SharedPreferences;
import android.util.Log;


public class HttpUrl {
	public static final String HttpURL = "http://192.168.11.11:86";//服务器
	//public static final String HttpURL = "http://www.pphealth.cn:8088";//彭浦，账号密码：73，73
	//public static final String HttpURL = "http://192.168.191.1:80";//服务器连猎豹WiFi
	/*HTTPMAIN("http://192.168.11.11:86/Login.aspx?"),//登录
	HTTPPOST("http://192.168.11.11:86/Login.aspx/Json/usp_get_UnVisitedInfo.aspx"),//待诊病人列表
	UnVisitedIfno("http://192.168.11.11:86/json/vw_FrequencyInfo.aspx"),//频率
	vw_UsageInfo("http://192.168.11.11:86/json/vw_UsageInfo.aspx");//用法
	private String url;
	private HttpUrl(String url) {
		this.url=url;
	}
	public String getUrl(){
		return url;
	}*/
}

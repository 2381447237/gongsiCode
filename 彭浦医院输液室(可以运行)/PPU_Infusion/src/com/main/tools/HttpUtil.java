package com.main.tools;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import android.util.Base64;


public class HttpUtil {
	private static HttpUrls_ instance = null;
	public static String staffName="";

	public static HttpURLConnection GetConnection(String urllogin)
	{
		HttpURLConnection conn=null;
		try
		{
			URL url = new URL(urllogin);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			if(staffName!="")
				conn.setRequestProperty("cookie", staffName);
			//Log.i("staffName----12321321",staffName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return conn;
	}

	public static synchronized HttpUrls_ getInstance() {
		if (instance == null) {
			instance = new HttpUrls_();
		}
		return instance;
	}
	public static String getValue(String murl, String user,String password) {
		URL url;
		try {
			
			url = new URL(HttpUrls_.HttpURL + murl);
		//	Log.i("qwj", "url=="+HttpUrls_.HttpURL + murl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			
			
			// 设置是否向connection输出，因为这个是post请求，参数要放在
			// http正文内，因此�?要设为true
			 connection.setDoOutput(true);
			// // Read from the connection. Default is true.
			// connection.setDoInput(true);
			// 默认�? GET方式
			connection.setRequestMethod("POST");

			// Post 请求不能使用缓存
			connection.setUseCaches(false);
			connection.setConnectTimeout(10000);
			connection.setInstanceFollowRedirects(false);
			// connection.setRequestProperty("cookie", staffName);
			// 配置本次连接的Content-type，配置为application/x-www-form-urlencoded�?
			// 意�?�是正文是urlencoded编码过的form参数，下面我们可以看到我们对正文内容使用URLEncoder.encode
			// 进行编码
			/*connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");*/
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成�?
			// 要注意的是connection.getOutputStream会隐含的进行connect�?
			connection.connect();
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			// The URL-encoded contend
			// 正文，正文内容其实跟get的URL�? '? '后的参数字符串一�?
			String content = "StaffNo=" +user;
			content += "&Pwd="+ password;
			// tv_md5.setText(toHexString(encrypt("9999")).toUpperCase());

			// DataOutputStream.writeBytes将字符串中的16位的unicode字符�?8位的字符形式写到流里�?
			out.writeBytes(content);
			 String cookieval = connection.getHeaderField("set-cookie");
			// Log.i("cookieval=======>", cookieval + "");
			 if (cookieval != null) {
			 cookieval = cookieval.substring(0, cookieval.indexOf(";"));
			// Log.i("cook----3232323", cookieval);
			 } else {
			 cookieval = "null";
			 }
			
			 staffName = cookieval;
			// Log.i("cook----32323ttgf23", staffName);

			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			StringBuffer sb = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			connection.disconnect();
			return sb.toString();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
		

	}
	private static final int timeout=15*1000;
	public static String getRequest(String url) throws ClientProtocolException,
	IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(HttpUrls_.HttpURL + url);
		//Log.i("qwj", "get=="+HttpUrls_.HttpURL + url);
		String value = "";
		try {
			if (!staffName.trim().equals("")) {
				get.setHeader("cookie",staffName);
			}
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			HttpConnectionParams.setSoTimeout(httpParams, timeout);
			 
			HttpResponse response = client.execute(get);
		//	Log.i("qwj", "==response=="+response);
			if (response.getStatusLine().getStatusCode() == 200) {
				
				value = EntityUtils.toString(response.getEntity(), "UTF-8");
				//Log.i("qwj", "==value=="+value);
			//	System.out.println("returnvalue=====>" + value);
			}
		}
		
		
		finally {
			get.abort();
		}
		
		return value;

	}
	
	public static String postJson(String url,String jsonString,Map<String, String> data){
		HttpClient client=new DefaultHttpClient();
		String strhttp=HttpUrls_.HttpURL + url;
		if (data != null && data.size() > 0) {
			strhttp+="?Personnel_id="+data.get("Personnel_id")+"+&mark="+data.get("mark");
			//System.out.println(data.get("mark")+"=======");
		}

		HttpPost post=new HttpPost(strhttp);
		//System.out.println(HttpUrls_.HttpURL + url);
		try {
			if (!staffName.trim().equals("")) {
				post.setHeader("cookie", staffName);
			}
			if (!"".equals(jsonString)&&null!=jsonString) {
				byte[] json=jsonString.getBytes("utf-8");
				String str=Base64.encodeToString(json, Base64.DEFAULT);
				//System.out.println("str====="+str);
				StringEntity stringEntity=new StringEntity(str);
				stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
				stringEntity.setContentEncoding(new BasicHeader(HTTP.CONTENT_ENCODING, HTTP.UTF_8));
				post.setEntity(stringEntity);
			}
			HttpResponse response=client.execute(post);
			//System.out.println("response.getStatusLine().getStatusCode()=="+response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode()==200) {
				return EntityUtils.toString(response.getEntity(),HTTP.UTF_8);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			post.abort();
		}
		return "";
	}
	public static String postRequest(String url, Map<String, String> data)
			throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(HttpUrls_.HttpURL + url);
	//	Log.i("qwj", "==HttpUrls_.HttpURL + url=="+HttpUrls_.HttpURL + url);
		try {
			if (!staffName.trim().equals("")) {
				post.setHeader("cookie", staffName);
			}
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			HttpConnectionParams.setSoTimeout(httpParams, timeout);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if (data != null && data.size() > 0) {
				for (String key : data.keySet()) {
					params.add(new BasicNameValuePair(key, data.get(key)));
				}
			}

			//System.out.println("params==>" + params);
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = client.execute(post);
			//System.out.println("==================="+response.getStatusLine().getStatusCode());
          // Log.i("2017/1/6","响应码="+response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				String value = EntityUtils.toString(response.getEntity());
				//System.out.println("returnvalue===>" + value);
				return value;
			}
		} finally {
			post.abort();
		}
		return "";
	}
	
	
}

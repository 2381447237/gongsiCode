package com.fc.main.tools;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import android.util.Base64;

import android.text.Html;
import android.util.Log;

public class HttpUtil {
	// 超时时间设置
	private static final int timeout = 20 * 1000;

	// public static HttpClient client = new DefaultHttpClient();

	public static String getRequest(String url) throws ClientProtocolException,
			IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(HttpUrls_.HttpURL + url);
		System.out.println("HttpUrls_.HttpURL + url=====>" + HttpUrls_.HttpURL + url);
		String value = "";
		try {
			if (!HttpUrls_.staffName.trim().equals("")) {
				get.setHeader("cookie", HttpUrls_.staffName);
                 Log.i("2017/3/6","cookie="+HttpUrls_.staffName);
			}
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			HttpConnectionParams.setSoTimeout(httpParams, timeout);

			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				value = EntityUtils.toString(response.getEntity(), "UTF-8");
				System.out.println("returnvalue=====>" + value);
			}
		} finally {
			get.abort();
		}
		return value;

	}

	public static String postRequest(String url, Map<String, String> data)
			throws ClientProtocolException, IOException {
		
		Log.e("2017/10/24","url=="+(HttpUrls_.HttpURL + url));
		Log.e("2017/12/6","data=="+data);
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(HttpUrls_.HttpURL + url);
		try {
			if (!HttpUrls_.staffName.trim().equals("")) {
				post.setHeader("cookie", HttpUrls_.staffName);
			}
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			HttpConnectionParams.setSoTimeout(httpParams, timeout);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			Log.i("2017/3/10","9999999999");
			if (data != null && data.size() > 0) {
				for (String key : data.keySet()) {
					params.add(new BasicNameValuePair(key, data.get(key)));
				}
			}

			System.out.println("params==>" + params);
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

			HttpResponse response = client.execute(post);
			System.out.println("====="
					+ response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				String value = EntityUtils.toString(response.getEntity());
				System.out.println("returnvalue===>" + value);
				return value;
			}
		} finally {
			post.abort();
		}
		return "";
	}

	public static String upLoadImage(String url, String filePath,
			Map<String, String> data) throws ClientProtocolException,
			IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(HttpUrls_.HttpURL + url);
		try {
			if (!HttpUrls_.staffName.trim().equals("")) {
				post.setHeader("cookie", HttpUrls_.staffName);
			}
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			HttpConnectionParams.setSoTimeout(httpParams, timeout);
			MultipartEntity entity = new MultipartEntity();
			if (data != null) {
				for (String key : data.keySet()) {
					StringBody stringBody = new StringBody(data.get(key));
					entity.addPart(key, stringBody);
				}
			}

			if (filePath != null && filePath.trim().length() > 0) {
				FileBody fileBody = new FileBody(new File(filePath));
				entity.addPart("FILE", fileBody);
			}

			post.setEntity(entity);

			System.out.println("params==>" + entity);
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String value = EntityUtils.toString(response.getEntity());
				System.out.println("returnvalue===>" + value);
				return value;
			}
		} finally {
			post.abort();
		}
		return "";
	}

	/**
	 * �õ�ͼƬ
	 * 
	 * @param url
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static byte[] getImage(String url) throws ClientProtocolException,
			IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(HttpUrls_.HttpURL + url);
		try {
			if (!HttpUrls_.staffName.trim().equals("")) {
				get.setHeader("cookie", HttpUrls_.staffName);
			}
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			HttpConnectionParams.setSoTimeout(httpParams, timeout);

			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				InputStream inStream = response.getEntity().getContent();
				byte[] data = IOUtil.getBytesByStream(inStream);
				return data;
			}
		} finally {
			get.abort();
		}
		return null;
	}

	// public synchronized static void saveImage(String url,String iconName)
	// throws ClientProtocolException, IOException{
	// File file = new File(iconName);
	// if(!file.exists()){
	// HttpPost post = new HttpPost(HttpUrls_.HttpURL+url);
	// HttpParams httpParams = client.getParams();
	// HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
	// HttpConnectionParams.setSoTimeout(httpParams, 5000);
	// List<NameValuePair> params = new ArrayList<NameValuePair>();
	//
	// params.add(new BasicNameValuePair("iconName", iconName));
	//
	// post.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
	// HttpResponse response = client.execute(post);
	// if(response!=null&&response.getStatusLine().getStatusCode()==200){
	// FileOutputStream outputStream = new FileOutputStream(file);
	// InputStream inStream = response.getEntity().getContent();
	// byte[] buffer = new byte[1024];
	// int len=0;
	// while( (len=inStream.read(buffer))!=-1 ){
	// outputStream.write(buffer,0,len);
	// }
	// inStream.close();
	// outputStream.close();
	// }
	// }
	// }
	//

	public static String postJson(String url, String jsonString) {
		
		Log.e("2017/9/14","要上传的=="+jsonString);
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(HttpUrls_.HttpURL + url);
		System.out.println(HttpUrls_.HttpURL + url);
		try {
			if (!HttpUrls_.staffName.trim().equals("")) {
				post.setHeader("cookie", HttpUrls_.staffName);
			}
			byte[] json = jsonString.getBytes("utf-8");
			String str = Base64.encodeToString(json, Base64.DEFAULT);
			StringEntity stringEntity = new StringEntity(str);
			stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
					"application/json"));
			post.setEntity(stringEntity);
			HttpResponse response = client.execute(post);
			System.out.println(response.getStatusLine().getStatusCode()
					+ "============");
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			post.abort();
		}
		return "";
	}

	public static String postJson_GXWenJuan(String url, String jsonString,
		Map<String, String> data) {
		
		Log.e("2018-01-16", "url==========="+url);
		
		HttpClient client = new DefaultHttpClient();
		String strhttp = HttpUrls_.HttpURL + url;
		if (data != null && data.size() > 0) {
			strhttp += "?Personnel_id=" + data.get("Personnel_id") + "&mark="
					+ data.get("mark")+"&Phone="+ data.get("Phone");
			if(data.containsKey("Receiv_id"))
			{
				strhttp+="&Receiv_id="+ data.get("Receiv_id");
			}
			if(data.containsKey("del"))
			{
				strhttp+="&del="+ data.get("del");
			}
			System.out.println(data.get("mark") + "=======");
		}
		
		Log.i("qwj", "HttpURL:"+strhttp);
		HttpPost post = new HttpPost(strhttp);
		
		Log.e("2018-01-16", "strhttp========"+strhttp);
		
		System.out.println(HttpUrls_.HttpURL + url);
		try {
			if (!HttpUrls_.staffName.trim().equals("")) {
				post.setHeader("cookie", HttpUrls_.staffName);
			}
			if (!"".equals(jsonString) && null != jsonString) {
				byte[] json = jsonString.getBytes("utf-8");
				String str = Base64.encodeToString(json, Base64.DEFAULT);
				System.out.println("str=====" + str);
				StringEntity stringEntity = new StringEntity(str);
				stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				stringEntity.setContentEncoding(new BasicHeader(
						HTTP.CONTENT_ENCODING, HTTP.UTF_8));
				post.setEntity(stringEntity);
				
				
			}
			HttpResponse response = client.execute(post);
			System.out.println("response.getStatusLine().getStatusCode()=="
					+ response.getStatusLine().getStatusCode());
			if (response.getStatusLine().getStatusCode() == 200) {
				
				String a=EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

                 Log.e("2018-1-22", "EntityUtils.toString(response.getEntity(), HTTP.UTF_8)" + a);
				
                 return a;
                 
				//return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			post.abort();
		}
		return "";
	}
	
	public static String postJson_WenJuan(String url,Map<String, String> data) {
		
		Log.i("2017/3/7","postJson_WenJuan==data=="+data);
			HttpClient client = new DefaultHttpClient();
			String strhttp = HttpUrls_.HttpURL + url;
			Log.i("2017/3/7", "===HttpURL==="+strhttp);
			HttpPost post = new HttpPost(strhttp);
			System.out.println(HttpUrls_.HttpURL + url);
			try {
				if (!HttpUrls_.staffName.trim().equals("")) {
					post.setHeader("cookie", HttpUrls_.staffName);
				}
			
					if (data!=null) {
				
						org.json.JSONObject jo=new org.json.JSONObject(data);
					byte[] json=jo.toString().getBytes("utf-8");
					String str = Base64.encodeToString(json, Base64.DEFAULT);
					Log.i("2017/3/7", "===str==="+str);
					StringEntity stringEntity = new StringEntity(str);
					stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
							"application/json"));
					stringEntity.setContentEncoding(new BasicHeader(
							HTTP.CONTENT_ENCODING, HTTP.UTF_8));
					post.setEntity(stringEntity);
				}
				HttpResponse response = client.execute(post);
				System.out.println("response.getStatusLine().getStatusCode()=="
						+ response.getStatusLine().getStatusCode());
				if (response.getStatusLine().getStatusCode() == 200) {
					return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				post.abort();
			}
			return "";
		}
}

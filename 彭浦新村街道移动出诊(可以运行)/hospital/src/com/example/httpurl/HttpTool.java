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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.example.httpurl.HttpUrl;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HttpTool {
	private static HttpUrl instance = null;
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
			Log.i("staffName----12321321",staffName);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return conn;
	}

	public static synchronized HttpUrl getInstance() {
		if (instance == null) {
			instance = new HttpUrl();
		}
		return instance;
	}
	public static String getValue(String murl, String user,String password) {
		URL url;
		try {
			
			url = new URL(HttpUrl.HttpURL + murl);
			Log.i("qwj", "url=="+HttpUrl.HttpURL + murl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			
			
			// 设置是否向connection输出，因为这个是post请求，参数要放在
			// http正文内，因此�?要设为true
			// connection.setDoOutput(true);
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
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 连接，从postUrl.openConnection()至此的配置必须要在connect之前完成�?
			// 要注意的是connection.getOutputStream会隐含的进行connect�?
			connection.connect();
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			// The URL-encoded contend
			// 正文，正文内容其实跟get的URL�? '? '后的参数字符串一�?
			String content = "StaffNo=" + URLEncoder.encode(user, "UTF-8");
			content += "&Password="
					+ URLEncoder.encode(
							toHexString(encrypt(password)).toUpperCase(), "UTF-8");
			// tv_md5.setText(toHexString(encrypt("9999")).toUpperCase());

			// DataOutputStream.writeBytes将字符串中的16位的unicode字符�?8位的字符形式写到流里�?
			out.writeBytes(content);
			 String cookieval = connection.getHeaderField("set-cookie");
			 Log.i("cookieval=======>", cookieval + "");
			 if (cookieval != null) {
			 cookieval = cookieval.substring(0, cookieval.indexOf(";"));
			 Log.i("cook----3232323", cookieval);
			 } else {
			 cookieval = "null";
			 }
			
			 staffName = cookieval;
			 Log.i("cook----32323ttgf23", staffName);

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

	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}

		return hexString.toString();
	}

	public static byte[] encrypt(String message) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(MD5("36546201").substring(0, 8)
				.getBytes());

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(MD5("36546201")
				.substring(0, 8).getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return cipher.doFinal(message.getBytes());
	}

	public final static String MD5(String s) {
		// 16进制下数字到字符的映射数�?
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		try {
			byte[] strTemp = s.getBytes();
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	private static final int timeout=15*1000;
	public static String getRequest(String url) throws ClientProtocolException,
	IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(HttpUrl.HttpURL + url);
		Log.i("qwj", "get=="+HttpUrl.HttpURL + url);
		String value = "";
		try {
			if (!staffName.trim().equals("")) {
				get.setHeader("cookie", staffName);
			}
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			HttpConnectionParams.setSoTimeout(httpParams, timeout);
			 
			HttpResponse response = client.execute(get);
			Log.i("qwj", "==response=="+response);
			if (response.getStatusLine().getStatusCode() == 200) {
				
				value = EntityUtils.toString(response.getEntity(), "UTF-8");
				//Log.i("qwj", "==value=="+value);
				System.out.println("returnvalue=====>" + value);
			}
		}
		
		
		finally {
			get.abort();
		}
		
		return value;

	}
	public static String postRequest(String url, Map<String, String> data)
			throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(HttpUrl.HttpURL + url);
		Log.i("qwj", "==HttpUrls_.HttpURL + url=="+HttpUrl.HttpURL + url+data);
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

			System.out.println("params==>" + params);
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse response = client.execute(post);
			System.out.println("==================="+response.getStatusLine().getStatusCode());
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
}

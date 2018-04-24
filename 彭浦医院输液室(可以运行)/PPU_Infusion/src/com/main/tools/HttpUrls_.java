package com.main.tools;

public class HttpUrls_ {
	//public static final String HttpURL = "http://192.168.0.83:8088";//彭浦医院 账号密码:103,103
	//public static final String HttpURL = "http://192.168.11.11:88";
	//public static final String HttpURL="http://www.youli.pw:8088";
	//public static final String HttpURL="http://192.168.0.230:83";//彭浦，账号密码：103，103
	//public static final String HttpURL = "http://192.168.11.11:89";
	public static final String HttpURL = "http://192.168.11.11:8088";//服务器//公司，账号密码:9999，123456
	//public static final String HttpURL = "http://192.168.23.1:8088";//服务器
	/*private static HttpUrls_ instance = null;
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

	public static synchronized HttpUrls_ getInstance() {
		if (instance == null) {
			instance = new HttpUrls_();
		}
		return instance;
	}
*/
}

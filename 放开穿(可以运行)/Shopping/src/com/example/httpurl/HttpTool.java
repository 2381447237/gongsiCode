package com.example.httpurl;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import android.graphics.Bitmap;
import android.util.Log;

public class HttpTool {
	private static HttpUrl instance = null;
	public static String staffName = "";

	public static HttpURLConnection GetConnection(String urllogin) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(urllogin);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			if (staffName != "")
				conn.setRequestProperty("cookie", staffName);
		//	Log.i("staffName----12321321", staffName);
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

	public static String getValue(String murl, String user, String password) {
		URL url;
		try {

			url = new URL(HttpUrl.HttpURL + murl);
			//Log.i("qwj", "url==" + HttpUrl.HttpURL + murl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();

			// 璁剧疆鏄惁鍚慶onnection杈撳嚭锛屽洜涓鸿繖涓槸post璇锋眰锛屽弬鏁拌鏀惧湪
			// http姝ｆ枃鍐咃紝鍥犳锟�?瑕佽涓簍rue
			// connection.setDoOutput(true);
			// // Read from the connection. Default is true.
			// connection.setDoInput(true);
			// 榛樿锟�? GET鏂瑰紡
			connection.setRequestMethod("POST");

			// Post 璇锋眰涓嶈兘浣跨敤缂撳瓨
			connection.setUseCaches(false);
			connection.setConnectTimeout(10000);
			connection.setInstanceFollowRedirects(false);
			// connection.setRequestProperty("cookie", staffName);
			// 閰嶇疆鏈杩炴帴鐨凜ontent-type锛岄厤缃负application/x-www-form-urlencoded锟�?
			// 鎰忥拷?锟芥槸姝ｆ枃鏄痷rlencoded缂栫爜杩囩殑form鍙傛暟锛屼笅闈㈡垜浠彲浠ョ湅鍒版垜浠姝ｆ枃鍐呭浣跨敤URLEncoder.encode
			// 杩涜缂栫爜
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// 杩炴帴锛屼粠postUrl.openConnection()鑷虫鐨勯厤缃繀椤昏鍦╟onnect涔嬪墠瀹屾垚锟�?
			// 瑕佹敞鎰忕殑鏄痗onnection.getOutputStream浼氶殣鍚殑杩涜connect锟�?
			connection.connect();
			DataOutputStream out = new DataOutputStream(
					connection.getOutputStream());
			// The URL-encoded contend
			// 姝ｆ枃锛屾鏂囧唴瀹瑰叾瀹炶窡get鐨刄RL锟�? '? '鍚庣殑鍙傛暟瀛楃涓蹭竴锟�?
			String content = "StaffNo=" + URLEncoder.encode(user, "UTF-8");
			content += "&Password="
					+ URLEncoder.encode(toHexString(encrypt(password))
							.toUpperCase(), "UTF-8");
			// tv_md5.setText(toHexString(encrypt("9999")).toUpperCase());

			// DataOutputStream.writeBytes灏嗗瓧绗︿覆涓殑16浣嶇殑unicode瀛楃锟�?8浣嶇殑瀛楃褰㈠紡鍐欏埌娴侀噷锟�?
			out.writeBytes(content);
			String cookieval = connection.getHeaderField("set-cookie");
			//Log.i("cookieval=======>", cookieval + "");
			if (cookieval != null) {
				cookieval = cookieval.substring(0, cookieval.indexOf(";"));
				//Log.i("cook----3232323", cookieval);
			} else {
				cookieval = "null";
			}

			staffName = cookieval;
			//Log.i("cook----32323ttgf23", staffName);

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
		// 16杩涘埗涓嬫暟瀛楀埌瀛楃鐨勬槧灏勬暟锟�?
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

	private static final int timeout = 15 * 1000;

	public static String getRequest(String url) throws ClientProtocolException,
			IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(HttpUrl.HttpURL + url);
		//Log.i("a", "get==" + HttpUrl.HttpURL + url);
		String value = "";
		try {
			if (!staffName.trim().equals("")) {
				get.setHeader("cookie", staffName);
			}
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
			HttpConnectionParams.setSoTimeout(httpParams, timeout);

			HttpResponse response = client.execute(get);
			//Log.i("aa", "==response==" + response);
			if (response.getStatusLine().getStatusCode() == 200) {

				value = EntityUtils.toString(response.getEntity(), "UTF-8");
				// Log.i("aaa", "打印的结果==value=="+value);
				//System.out.println("打印的结果returnvalue=====>" + value);
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
		//Log.i("qwj", "==HttpUrls_.HttpURL + url==" + HttpUrl.HttpURL + url);
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
//			System.out.println("==================="
//					+ response.getStatusLine().getStatusCode());
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

	public static byte[] getImage(String url) throws ClientProtocolException,
			IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(HttpUrl.HttpURL + url);
		try {
//			if (!HttpUrl.staffName.trim().equals("")) {
//				get.setHeader("cookie", HttpUrl.staffName);
//			}
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
	
	
}

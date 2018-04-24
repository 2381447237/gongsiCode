package com.fc.main.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.fc.first.beans.Center;
import com.fc.first.beans.CommitteeInformation;
import com.fc.first.beans.GetJobs;
import com.fc.first.beans.GetMsgBoardDetile;
import com.fc.first.beans.GetMsgBoardMaster;
import com.fc.first.beans.GetNews;
import com.fc.first.beans.GetUsrInformation;
import com.fc.first.beans.LocationInformation;
import com.fc.first.beans.LostJobInformation;
import com.fc.first.beans.PendingWorkInformation;
import com.fc.first.views.FirstPageActivity;
import com.fc.first.views.LoadActivity;
import com.fc.first.views.LoginActivity;
import com.fc.person.beans.PersonMark;
import com.fc.person.beans.PersonResume;
import com.fc.person.beans.PersonUpdataImg;
import com.fc.person.beans.PersonalBaseInformation;
import com.fc.person.beans.PersonalInformation;

public class HttpUrls_ {
	// private static final String HttpURL = "http://10.10.10.100:89";

//	public static final String HttpURL = "http://192.168.1.157:89";

//	public static final String HttpURL = "http://www.fcxx.net.cn:89";
	public static final String HttpURL = "http://192.168.4.11:89"; //3G
//	public static final String HttpURL = "http://192.168.11.143:89"; //本机
//	public static final String HttpURL = "http://192.168.11.99:89";//老大机器
//	public static final String HttpURL = "http://192.168.11.11:89"; //服务器
//	public static final String HttpURL = "http://192.168.11.104:89";//老大机器
//	public static final String HttpURL = "http://192.168.1.157:89";//老大机器 就促
	
//	public static final String HttpURL="http://192.168.191.1:89";
	

 

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

	/**
	 * 
	 * @param context
	 * @param userName
	 * @param userPwd
	 * @return 要发送审核的信息
	 */
	public static String login(final LoginActivity context, String userName,
			String userPwd) {
		String urllogin = HttpURL + "/login.aspx";
		String msgLogin = "";
		SharedPreferences preferences = context.getSharedPreferences("msg", 0);
		String msgkey = preferences.getString("key", null);
		// Log.i("key********", msgkey);
		// Log.i("url-----------", urllogin);
		String query;
		try {
			String name = Fc_Helper.EncryptDES(userName, msgkey);
			String Text = Fc_Helper.EncryptDES(userPwd, msgkey);

			// url中的“+”会自动转换为“%2B”在密码中会有问题，必须转化为全角的“+”
//
			query = "?username=" + name.replace("\n", "").replace("+", "|||")
					+ "&password=" + Text.replace("\n", "").replace("+", "|||");
//			query = "?username=" + URLEncoder.encode( name,"UTF-8")
//					+ "&password=" +URLEncoder.encode(Text, "UTF-8");
			urllogin += query;
			// query = "?username=" + userName.replace("\n", "").replace("+",
			// "＋")
			// + "&password=" + userPwd.replace("\n", "").replace("+", "＋");
			// urllogin += query;

			Log.i("url+++++++++++++", urllogin);
			URL url = new URL(urllogin);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String cookieval = conn.getHeaderField("set-cookie"); 
			Log.i("cookieval=======>",cookieval + "");
			if(cookieval != null){
				cookieval = cookieval.substring(0,cookieval.indexOf(";"));
				Log.i("cook----3232323",cookieval);
			}else{
				cookieval = "null";
			}
			



			staffName = cookieval;
			Log.i("cook----3232323",cookieval);
			conn.setConnectTimeout(5000);
			System.out.println(conn.getResponseCode()+"+===============");

			//if (conn.getResponseCode() == 200) {
				InputStream in = conn.getInputStream();
				byte[] buffer = new byte[in.available()];
				in.read(buffer);
				msgLogin = new String(buffer, "utf-8");
				Log.i("发送的信息。。。。...", msgLogin);
				in.close();
				conn.disconnect();
			//}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return msgLogin;

	}

	/**
	 * 
	 * @param context
	 * @return 得到的私钥
	 */
	public static String getDes(final LoadActivity context) {
		String urlStr = HttpURL + "/getdes.aspx";
		Log.i("url-----------", urlStr);
		String msgDes = "";
		try {
			//			URL url = new URL(urlStr);
			//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			HttpURLConnection conn = GetConnection(urlStr);
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				// 获得输入流
				InputStream in = conn.getInputStream();
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgDes = new String(buffer, "utf-8");
				// 得到的数据存入到sharedPrefences中
				SharedPreferences.Editor preferences = context
						.getSharedPreferences("msg", 0).edit();
				preferences.putString("key", msgDes);
				preferences.commit();

				Log.i("得到的信息。。。。", msgDes);
				in.close();// 关闭数据流
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msgDes;
	}

	
	/**
	 * 
	 * @param context
	 * @return
	 * @throws JSONException
	 */
	public static ArrayList<String> getPolicyJson(Context context)
			throws JSONException {
		String urlStr = HttpURL + "/Json/Get_policy_Type.aspx";
		ArrayList<String> list_policy = new ArrayList<String>();
		HashMap<String, String> map_policy = new HashMap<String, String>();

		Log.i("url-----------", urlStr);
		String msgJson = "";
		String Json = "";
		try {
			//			URL url = new URL(urlStr);
			//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			HttpURLConnection conn = GetConnection(urlStr);
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				// 获得输入流
				InputStream in = conn.getInputStream();
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgJson = new String(buffer, "utf-8");
				JSONArray jsonarray = new JSONArray(msgJson);
				int len = jsonarray.length();
				for (int i = 0; i < len; i++) {
					JSONObject object = jsonarray.getJSONObject(i);
					String ploicy_id = object.getString("ID");
					String policy_type = object.getString("TYPE_NAME");
					list_policy.add(policy_type);
					map_policy.put(policy_type, ploicy_id);
				}
				Log.i("得到的信息。。。。", msgJson);
				in.close();// 关闭数据流
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list_policy;
	}



	public static ArrayList<CommitteeInformation> getCommitteeJson(
			Context context, String street)
					throws JSONException {
		String CommitteeId = street;
		String urlStr = HttpURL + "/Json/Get_Area.aspx?COMMITTEE="
				+ CommitteeId;
		ArrayList<CommitteeInformation> committeeInfo_list = new ArrayList<CommitteeInformation>();
		Log.i("url-----------", urlStr);
		String msgJson = "";
		try {
			//			URL url = new URL(urlStr);
			//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			HttpURLConnection conn = GetConnection(urlStr);
			conn.setConnectTimeout(5 * 1000);
			if (conn.getResponseCode() == 200) {
				// 获得输入流
				InputStream in = conn.getInputStream();
				Thread.sleep(500);
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgJson = new String(buffer, "utf-8");
				Log.i("得到的信息。。。。", msgJson);
				JSONArray jsonarray = new JSONArray(msgJson);
				int len = jsonarray.length();
				for (int i = 0; i < len; i++) {
					JSONObject object = jsonarray.getJSONObject(i);
					String committee_id = object.getString("ID");
					String committee_type = object.getString("NAME");
					String committee_street = object.getString("STREETID");

					CommitteeInformation committeeInfo = new CommitteeInformation();
					committeeInfo.setCommitteeId(committee_id);
					committeeInfo.setCommitteeName(committee_type);
					committeeInfo.setStreetId(committee_street);
					committeeInfo_list.add(committeeInfo);

				}
				in.close();// 关闭数据流
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return committeeInfo_list;
	}

	/**
	 * 发送需要查询的信息给服务器，并返回详细信息 把对象转化为json数据，并发送
	 * 
	 * @param list
	 * @return
	 */
	public static String postJson(ArrayList<PersonalInformation> list) {
		StringBuilder builder = new StringBuilder();
		String urlPost = HttpURL + "/Json/Seach_Personal_Info.aspx?";
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		JSONArray jsonarray = new JSONArray();
		for (PersonalInformation personalInfo : list) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("CardId", personalInfo.getPersonCard());
				jsonObject.put("CardNum", personalInfo.getPersonNum());
				jsonObject.put("PersonName", personalInfo.getPersonName());
				jsonObject.put("PersonType", personalInfo.getPersonType());
				jsonObject.put("HouseType", personalInfo.getPersonHuji());
				jsonObject.put("Education", personalInfo.getPersonDegree());
				jsonObject.put("AgeLow", personalInfo.getAgelow());
				jsonObject.put("AgeUp", personalInfo.getAgeup());
				jsonObject.put("ProvinceId", personalInfo.getProvince());
				jsonObject.put("CityId", personalInfo.getCity());
				jsonObject.put("RegionId", personalInfo.getCounty());
				jsonObject.put("StreetId", personalInfo.getStreet());
				jsonObject.put("CommitteeId", personalInfo.getCommittee());
				jsonObject.put("Road", personalInfo.getRoad());
				jsonObject.put("Nong", personalInfo.getNong());
				jsonObject.put("Number", personalInfo.getNumber());
				jsonObject.put("Room", personalInfo.getRoom());
				jsonObject.put("IsChecka", personalInfo.getIsCheck_a());
				jsonObject.put("IsCheckb", personalInfo.getIsCheck_b());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsonarray.put(jsonObject);
			Log.i("jsonxinxi??????????????", jsonarray.toString());
		}
		nameValuePair.add(new BasicNameValuePair("jsonString", jsonarray
				.toString()));
		try {
			// 建立服务器的连接，以post方式发送到服务器。
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(urlPost);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
			Log.i("json?????????%%%%%%%%%%%", nameValuePair.toString());
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Log.i("返回的呢？？？？？？？", httpResponse.toString());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();

	}

	/**
	 * 根据身份证获得个人基本信息
	 * 
	 * @param personal_Activity
	 * @param IdCard
	 * @return
	 * @throws JSONException
	 * @throws InterruptedException
	 */
	public static ArrayList<PersonalBaseInformation> getPersoninfoJson(
			final Context addperson, String cardId) {
		String urlStr = HttpURL + "/Json/Get_BASIC_INFORMATION.aspx?sfz="
				+ cardId;
		ArrayList<PersonalBaseInformation> personbaseInfo_list = new ArrayList<PersonalBaseInformation>();
		// PersonalBaseInformation personbaseInfo = null;
		Center center = new Center();
		Log.i("url-----------", urlStr);
		String msgJson = "";
		String Json = "";
		try {
			//			URL url = new URL(urlStr);
			//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			HttpURLConnection conn = GetConnection(urlStr);
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				// 获得输入流
				InputStream in = conn.getInputStream();
				Thread.sleep(1000);
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgJson = new String(buffer, "utf-8");
				Log.i("得到的信息。。。。", msgJson);
				JSONArray jsonarray = new JSONArray(msgJson);
				int len = jsonarray.length();
				for (int i = 0; i < len; i++) {
					JSONObject object = jsonarray.getJSONObject(i);
					PersonalBaseInformation personbaseInfo = new PersonalBaseInformation();
					String personIdCard = object.getString("SFZ");
					String personName = object.getString("NAME");
					String personNational = object.getString("NATIONS");
					String personSex = object.getString("SEX");
					String personMobilePhone = object.getString("CONTACT_NUMBER");
					String personNative = object.getString("NATIVE");
					String personLevelMsg = object.getString("LevelMsg");
					String personBorn = object.getString("BIRTH_DATE");
					String personBeizhu =object.getString("Remark");
					String type = object.getString("TYPE");
					String road = object.getString("ROAD");
					String nong = object.getString("LANE");
					String number = object.getString("NO");
					String room = object.getString("ROOM");
					String nowroad = object.getString("NOW_ROAD");
					String nownong = object.getString("NOW_LANE");
					String nownumber = object.getString("NOW_NO");
					String nowroom = object.getString("NOW_ROOM");
					// 转化时间格式
					String substring = personBorn.substring(0,
							personBorn.indexOf("T"));

					// Date persondate = new Date(substring);
					// SimpleDateFormat sdf = new
					// SimpleDateFormat("yyyy年MM月dd日");
					// String personbornDate = sdf.format(persondate);

					String personEducation = object.getString("CULTURAL_CODE");
					
					String status = object.getString("Current_situation");
					String intention = object.getString("Current_intent");
					JSONObject jsonCenter = object.getJSONObject("Center");
//					String personNational1 = jsonCenter.getString("Q民族");
					String cardtype = jsonCenter.getString("Q证件类型");
					String cardnum = jsonCenter.getString("Q证件号码");
					String personstreet = jsonCenter.getString("Q户口所属街道");
					String personjuwei = jsonCenter.getString("Q居委会");
					String status1 = jsonCenter.getString("Q目前摸底状况");
					String intention1 = jsonCenter.getString("Q当前意向");
					String address = jsonCenter.getString("Q户口地址");
					center.setQcardtype(cardtype);
					center.setQaddress(nowroad+nownong+nownumber+nowroom);
					center.setQcurretnStatus(status1);
					center.setQcurrentintention(intention1);
					center.setQcardnum(cardnum);
					center.setQstreet(personstreet);
					center.setQjuweihui(personjuwei);
					center.setQhujidizhi(road+nong+number+room);
//					center.setQnational(personNational1);
					personbaseInfo.setCenter(center);
					personbaseInfo.setPersonName(personName);
					personbaseInfo.setPersonCardId(personIdCard);
					personbaseInfo.setPersonBorn(substring);
					personbaseInfo.setPersonSex(personSex);
					personbaseInfo.setPersonType(type);
					personbaseInfo.setPersonEducation(personEducation);
					personbaseInfo.setPersonMobilePhone(personMobilePhone);
					personbaseInfo.setPersonNativePlace(personNative);
					personbaseInfo.setPersonLevelmsg(personLevelMsg);
					personbaseInfo.setPersonNational(personNational);
					personbaseInfo.setPersonBeizhu(personBeizhu);
					personbaseInfo.setPersonRoad(road);
					personbaseInfo.setPersonNong(nong);
					personbaseInfo.setPersonNumber(number);
					personbaseInfo.setPersonRoom(room);
					personbaseInfo.setPersonNowRoad(nowroad);
					personbaseInfo.setPersonNowNong(nownong);
					personbaseInfo.setPersonNowNumber(nownumber);
					personbaseInfo.setPersonNowRoom(nowroom);
					personbaseInfo.setPersonIntention(intention);
					personbaseInfo.setPersonCurrentStatus(status);
					String time = jsonCenter.getString("CREATE_DATE");
					String [] times = time.split("T");					
					personbaseInfo.setUpdateTime(times[0]+" "+times[1].substring(0, times[1].indexOf('.')>0?times[1].indexOf('.'):times[1].length()));
					
					Log.i("????????!!!!!!!!!!!!!######$$$$$$$$$",
							personbaseInfo.toString());
					personbaseInfo_list.add(personbaseInfo);

				}
				in.close();// 关闭数据流
				conn.disconnect();
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return personbaseInfo_list;
	}


	/**
	 * 得到人员的标识信息(根据身份证)
	 * 
	 * @param addpersoninfo
	 * @param IdCard
	 * @return
	 * @throws JSONException
	 * @throws InterruptedException
	 */
	public static ArrayList<PersonMark> getPersonMarkJson(
			final Context addpersoninfo, String IdCard)
					throws JSONException, InterruptedException {
		String urlStr = HttpURL + "/Json/Get_Tb_Mark.aspx?sfz=" + IdCard;
		ArrayList<PersonMark> personMark_list = new ArrayList<PersonMark>();
		Log.i("url-----------", urlStr);
		String msgJson = "";
		try {
			//			URL url = new URL(urlStr);
			//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			HttpURLConnection conn = GetConnection(urlStr);
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				// 获得输入流
				InputStream in = conn.getInputStream();
				Thread.sleep(3000);
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgJson = new String(buffer, "utf-8");
				Log.i("得到的信息。。。。", msgJson);
				JSONArray jsonarray = new JSONArray(msgJson);
				int len = jsonarray.length();
				for (int i = 0; i < len; i++) {
					JSONObject object = jsonarray.getJSONObject(i);
					String personMarkId = object.getString("ID");
					String personSFZ = object.getString("SFZ");
					String personMarkName = object.getString("MARK");
					String personMarkCreatdate = object
							.getString("CREATE_DATE");
					String subdate = personMarkCreatdate.substring(0, personMarkCreatdate.indexOf("T"));
					String personMarkSource = object.getString("SOURCE");
					PersonMark personmark = new PersonMark();
					personmark.setPersonMarkId(personMarkId);
					personmark.setPersonSFZ(personSFZ);
					personmark.setPersonMarkName(personMarkName);
					personmark.setPersonMarkCreatdate(subdate);
					personmark.setPersonMarkSoure(personMarkSource);
					personMark_list.add(personmark);
				}
				in.close();// 关闭数据流
				conn.disconnect();
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return personMark_list;
	}

	/**
	 * 对话框显示
	 * 
	 * @param msg
	 * @param context
	 */
	public static void showDialog(String msg, final Context context) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setMessage(msg).setCancelable(true)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		AlertDialog alert = dialog.create();
		alert.show();

	}

	public static String postJson1(ArrayList<LocationInformation> list) {

		return "";
		/*
		StringBuilder builder = new StringBuilder();
		String urlPost = HttpURL + "/Json/Get_Gps_Info.aspx";
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		JSONArray jsonarray = new JSONArray();
		for (LocationInformation locationInfo : list) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("ID", locationInfo.getId());
				jsonObject.put("STAFF", locationInfo.getStaff());
				jsonObject.put("MARK", locationInfo.getMark());
				jsonObject.put("LONGITUDE", locationInfo.getLongitude());
				jsonObject.put("LATITUDE", locationInfo.getLatitude());
				jsonObject.put("HEIGHT", locationInfo.getHeight());
				jsonObject.put("CREATE_DATE", locationInfo.getCreate_date());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsonarray.put(jsonObject);
		}
		nameValuePair.add(new BasicNameValuePair("jsonString", jsonarray
				.toString()));
		try {
			// 建立服务器的连接，以post方式发送到服务器。
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(urlPost);
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
			Log.i("json", nameValuePair.toString());
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Log.i("？？？？？？？", httpResponse.toString());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
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
		}
		return builder.toString();
		 */
	}

	public static ArrayList<LostJobInformation> getJson(
			final FirstPageActivity context) throws JSONException {
		String urlStr = HttpURL + "/Json/GetPadMain.aspx";
		ArrayList<LostJobInformation> lostjob_list = new ArrayList<LostJobInformation>();
		Log.i("url-----------", urlStr);
		String msgJson = "";

		try {
			//			URL url = new URL(urlStr);
			//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			HttpURLConnection conn = GetConnection(urlStr);
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				// 获得输入流
				InputStream in = conn.getInputStream();
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgJson = new String(buffer, "utf-8");
				Log.i("得到的信息。。。。", msgJson);
				JSONArray jsonarray = new JSONArray(msgJson);
				int len = jsonarray.length();
				for (int i = 0; i < len; i++) {
					JSONObject object = jsonarray.getJSONObject(i);
					String lostJobNewTime = object.getString("Time");
					String lostJobMale = object.getString("Man");
					String lostJobFeMale = object.getString("Woman");
					//转化时间格式
					String substring = lostJobNewTime.substring(0, lostJobNewTime.indexOf("T"));

					LostJobInformation lostjobInfo = new LostJobInformation();
					lostjobInfo.setTime(lostJobNewTime);
					lostjobInfo.setMan(lostJobMale);
					lostjobInfo.setWoman(lostJobFeMale);
					lostjob_list.add(lostjobInfo);

				}
				in.close();// 关闭数据流
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return lostjob_list;
	}


	public static GetUsrInformation getUserInfoJson(
			final FirstPageActivity context) throws JSONException {
		String urlStr = HttpURL + "/Json/Get_Staff.aspx";
		GetUsrInformation userinfo = null ;
		Log.i("url-----------", urlStr);
		String msgJson = "";

		try {
			//URL url = new URL(urlStr);
			//HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			HttpURLConnection conn = GetConnection(urlStr);
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				// 获得输入流
				InputStream in = conn.getInputStream();
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgJson = new String(buffer, "utf-8");
				Log.i("得到的信息。。。。", msgJson);


				JSONObject object = new JSONObject(msgJson);

				String id = object.getString("ID");
				String name = object.getString("NAME");
				String input_code = object.getString("INPUT_CODE");
				String pwd = object.getString("PWD");
				String phone = object.getString("PHONE");
				String email = object.getString("EMAIL");
				String photo = object.getString("PHOTO");
				String create_date = object.getString("CREATE_DATE");
				String create_staff = object.getString("CREATE_STAFF");
				String update_date = object.getString("UPDATE_DATE");
				String update_staff = object.getString("UPDATE_STAFF");
				String stop = object.getString("STOP");
				String enable = object.getString("Enable");

				userinfo = new GetUsrInformation();
				userinfo.setId(id);
				userinfo.setName(name);
				userinfo.setInput_Code(input_code);
				userinfo.setPwd(pwd);
				userinfo.setPhone(phone);
				userinfo.setEmail(email);
				userinfo.setPhoto(photo);
				userinfo.setCreate_Date(create_date);
				userinfo.setCreate_Staff(create_staff);
				userinfo.setUpdate_Date(update_date);
				userinfo.setUpdate_Staff(update_staff);
				userinfo.setStop(stop);
				userinfo.setEnable(enable);


				in.close();// 关闭数据流
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userinfo;
	}
	public static String postImgJson(ArrayList<PersonUpdataImg> list_updataImg,ArrayList<LocationInformation> list_location) {
		StringBuilder builder = new StringBuilder();
		String urlPost = HttpURL + "/Json/Set_Photo.aspx";
		Log.i("uuuurl?????", urlPost);
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		JSONArray jsonarray = new JSONArray();
		for (PersonUpdataImg personupdate : list_updataImg) {
			JSONObject jsonObject = new JSONObject();
			try {

				//				byte[] tmp = personupdate.getPersonheadImg();

				jsonObject.put("json",personupdate);//Base64.encode(tmp,Base64.DEFAULT));
				//jsonObject.put("Sfz", personupdate.getPersonSfz());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsonarray.put(jsonObject);
			Log.i("jsonxinxi??????????????", jsonarray.toString());
		}
		/*for(LocationInformation location: list_location){
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("jingdu", location.getLongitude());
				jsonObject.put("weidu", location.getLatitude());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jsonarray.put(jsonObject);
		}*/
		nameValuePair.add(new BasicNameValuePair("jsonString", jsonarray
				.toString()));
		try {
			// 建立服务器的连接，以post方式发送到服务器。
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(urlPost);
			if(staffName!=""){

				httpPost.setHeader("cookie",staffName);
			}

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));



			Log.i("json?????????%%%%%%%%%%%", nameValuePair.toString());
			HttpResponse httpResponse = httpClient.execute(httpPost);

			Log.i("返回的呢？？？？？？？", httpResponse.toString());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();

	}

	/**
	 * 工作日志
	 * @param context
	 * @return
	 * @throws JSONException
	 * @throws UnsupportedEncodingException
	 */
	
	public static ArrayList<PendingWorkInformation> getPendingWorkJson(
			Context context,int page,int rows,String typecd) throws JSONException, UnsupportedEncodingException {

		String typecode = URLEncoder.encode(typecd,"utf-8");
		//page=0，rows=2主mian日志显示，page=0，rows=15：三星显示，page=0，rows=
		String urlStr = HttpURL + "/Json/Get_PendingWork.aspx?page="+page+"&rows="+rows+""+"&type="+typecode;
		ArrayList<PendingWorkInformation> pendwork_list = new ArrayList<PendingWorkInformation>();
		Log.i("url-----------", urlStr);
		String msgJson = "";

		try {
			HttpURLConnection conn = GetConnection(urlStr);
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				Thread.sleep(2000);
				// 获得输入流
				InputStream in = conn.getInputStream();
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgJson = new String(buffer, "utf-8");
				Log.i("得到的信息。。。。", msgJson);
				JSONArray jsonarray = new JSONArray(msgJson);
				int len = jsonarray.length();
				for (int i = 0; i < len; i++) {
					JSONObject object = jsonarray.getJSONObject(i);
					String id = object.getString("ID");
					String start_Time = object.getString("START_TIME");
					String end_Time = object.getString("END_TIME");
					String title = object.getString("TITLE");
					String doc = object.getString("DOC");
					String work_Staff = object.getString("WORK_STAFF");
					String create_Staff = object.getString("CREATE_STAFF");
					String create_Time = object.getString("CREATE_TIME");
					String type = object.getString("TYPE");
					String update_Time = object.getString("UPDATE_TIME");
					String max = object.getString("Max");
					PendingWorkInformation pendworkInfo = new PendingWorkInformation();
					pendworkInfo.setId(id);
					pendworkInfo.setStart_Time(start_Time);
					pendworkInfo.setEnd_Time(end_Time);
					pendworkInfo.setTitle(title);
					pendworkInfo.setDoc(doc);
					pendworkInfo.setWork_Staff(work_Staff);
					pendworkInfo.setCreate_Staff(create_Staff);
					pendworkInfo.setCreate_Time(create_Time);
					pendworkInfo.setType(type);
					pendworkInfo.setUpdate_time(update_Time);
					pendworkInfo.setMax(max);
					pendwork_list.add(pendworkInfo);
				}
				in.close();// 关闭数据流
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pendwork_list;
	}
	public static Bitmap getPersonImage(Context context,String Idcard){
		String urlstr = HttpURL+"/Web/Personal/windows/ShowPic.aspx?sfz="+Idcard;
		Bitmap bitmap = null;
		HttpURLConnection conn = GetConnection(urlstr);
		conn.setConnectTimeout(5000);
		try {
			if (conn.getResponseCode() == 200) {
				Thread.sleep(2000);
				// 获得输入流
				InputStream in = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(in);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;

	}

	public static String postPersonJson(ArrayList<PersonalBaseInformation> list_update) {
		StringBuilder builder = new StringBuilder();
		String urlPost = HttpURL + "/Json/Set_BASIC_INFORMATION.aspx";
		Log.i("uuuurl?????", urlPost);
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		JSONArray jsonarray = new JSONArray();
		for (PersonalBaseInformation personupdate : list_update) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("NAME",personupdate.getPersonName());
				jsonObject.put("SEX", personupdate.getPersonSex());
				jsonObject.put("BIRTH_DATE",personupdate.getPersonBorn());
				jsonObject.put("NATIONS",personupdate.getPersonNational());
				jsonObject.put("NATIVE", personupdate.getPersonNativePlace());
				jsonObject.put("TYPE",personupdate.getPersonType());
				jsonObject.put("CULTURAL_CODE", personupdate.getPersonEducation());
				jsonObject.put("SFZ", personupdate.getPersonCardId());
				jsonObject.put("CONTACT_NUMBER", personupdate.getPersonMobilePhone());
				jsonObject.put("GPS", FirstPageActivity.GetGpsInfo());
				jsonObject.put("ROAD", personupdate.getPersonRoad());
				jsonObject.put("LANE", personupdate.getPersonNong());
				jsonObject.put("NO", personupdate.getPersonNumber());
				jsonObject.put("ROOM", personupdate.getPersonRoom());
				jsonObject.put("NOW_ROAD", personupdate.getPersonNowRoad());
				jsonObject.put("NOW_LANE", personupdate.getPersonNowNong());
				jsonObject.put("NOW_NO", personupdate.getPersonNowNumber());
				jsonObject.put("NOW_ROOM", personupdate.getPersonNowRoom());
				jsonObject.put("Remark", personupdate.getPersonBeizhu());
				jsonObject.put("Current_situation", personupdate.getPersonCurrentStatus());
				jsonObject.put("Current_intent", personupdate.getPersonIntention());
				JSONObject jsoncenter = new JSONObject();
				Center center= personupdate.getCenter();
//				jsoncenter.put("Q当前意向", center.getQcurrentintention());
//				jsoncenter.put("Q目前摸底状况", center.getQcurretnStatus());
				jsoncenter.put("Q户口所属街道", center.getQstreet());
				jsoncenter.put("Q居委会", center.getQjuweihui());
				jsoncenter.put("Q户口地址", center.getQaddress());
				jsonObject.put("Center", jsoncenter);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsonarray.put(jsonObject);
			Log.i("jsonxinxi??????????????", jsonarray.toString());
		}
		nameValuePair.add(new BasicNameValuePair("jsonString", jsonarray
				.toString()));
		try {
			// 建立服务器的连接，以post方式发送到服务器。
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(urlPost);
			if(staffName!=""){
				httpPost.setHeader("cookie",staffName);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
			Log.i("json?????????%%%%%%%%%%%", nameValuePair+"");
			HttpResponse httpResponse = httpClient.execute(httpPost);

			Log.i("返回的呢？？？？？？？", httpResponse.toString());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();

	}
	public static HttpResponse GetHttpResponse(String Url,List<NameValuePair> nameValuePair)
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(Url);
		HttpResponse httpResponse = null;
		if(staffName!=""){
			httpPost.setHeader("cookie",staffName);
		}

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
			httpResponse = httpClient.execute(httpPost);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.i("json?????????%%%%%%%%%%%", nameValuePair+"");
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return httpResponse;

	}


	public static String PendWorkPostJson(ArrayList<PendingWorkInformation> list) {
		final StringBuilder builder = new StringBuilder();
		//String urlPost = HttpURL + "/Json/Set_PendingWork.aspx?";
		//		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		JSONArray jsonarray = new JSONArray();
		for (PendingWorkInformation pendworkInfo : list) {
		
			JSONObject jsonObject = new JSONObject();
			try {
				
				jsonObject.put("ID", pendworkInfo.getId());
				jsonObject.put("START_TIME", pendworkInfo.getStart_Time());
				jsonObject.put("END_TIME", pendworkInfo.getEnd_Time());
				jsonObject.put("TITLE", pendworkInfo.getTitle());
				jsonObject.put("DOC", pendworkInfo.getDoc());
				jsonObject.put("WORK_STAFF", pendworkInfo.getWork_Staff());
				jsonObject.put("CREATE_STAFF", pendworkInfo.getCreate_Staff());
				jsonObject.put("CREATE_TIME", pendworkInfo.getCreate_Time());
				jsonObject.put("TYPE", pendworkInfo.getType());
				jsonObject.put("UPDATE_TIME", pendworkInfo.getUpdate_time());
				jsonObject.put("Max",pendworkInfo.getMax());


			} catch (JSONException e) {
				e.printStackTrace();
			} 
			jsonarray.put(jsonObject);
			Log.i("jsonxinxi??????????????", jsonarray.toString());
		}
		//		nameValuePair.add(new BasicNameValuePair("jsonString", jsonarray
		//				.toString()));

		// 建立服务器的连接，以post方式发送到服务器。

		final HashMap<String, String> map = new HashMap<String, String>();
		map.put("json", jsonarray.toString().trim());

		new Thread(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					builder.append(HttpUtil.postRequest("/Json/Set_PendingWork.aspx", map));
					System.out.println(map);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

		//			
		//
		//			HttpResponse httpResponse = GetHttpResponse(urlPost,nameValuePair);
		//			Log.i("？？？？？？？", httpResponse.toString());
		//			Log.i("json?????????%%%%%%%%%%%", nameValuePair.toString());
		//			BufferedReader reader = new BufferedReader(new InputStreamReader(
		//					httpResponse.getEntity().getContent()));
		//			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
		//				builder.append(s);
		//			}

		Log.i("fanhui", builder.toString());
		return builder.toString();
	}

	public static ArrayList<GetNews> getNewsJson(
			Context context,int page,int rows) throws JSONException, UnsupportedEncodingException {

		//page=0，rows=2主mian日志显示，page=0，rows=15：三星显示，page=0，rows=
		String urlStr = HttpURL + "/Json/Get_News.aspx?page="+page+"&rows="+rows;
		ArrayList<GetNews> news_list = new ArrayList<GetNews>();
		Log.i("url-----------", urlStr);
		String msgJson = "";

		try {
			HttpURLConnection conn = GetConnection(urlStr);
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				Thread.sleep(2000);
				// 获得输入流
				InputStream in = conn.getInputStream();
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgJson = new String(buffer, "utf-8");
				Log.i("得到的信息。。。。", msgJson);
				JSONArray jsonarray = new JSONArray(msgJson);
				int len = jsonarray.length();
				for (int i = 0; i < len; i++) {
					JSONObject object = jsonarray.getJSONObject(i);
					String id = object.getString("ID");
					String title = object.getString("TITLE");
					String doc = object.getString("DOC");
					String create_Time = object.getString("CREATE_TIME");
					String create_Staff = object.getString("CREATE_STAFF");
					String recordCount = object.getString("RecordCount");
					GetNews newsInfo = new GetNews();
					newsInfo.setId(id);
					newsInfo.setTitle(title);
					newsInfo.setDoc(doc);
					newsInfo.setCreate_Time(create_Time);
					newsInfo.setCreate_Staff(create_Staff);
					newsInfo.setRecordCount(recordCount);
					news_list.add(newsInfo);
				}
				in.close();// 关闭数据流
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return news_list;
	}

	public static ArrayList<GetMsgBoardMaster> getmsgboardmasterJson(
			Context context,int page,int rows) throws JSONException, UnsupportedEncodingException {

		//page=0，rows=2主mian日志显示，page=0，rows=15：三星显示，page=0，rows=
		String urlStr = HttpURL + "/Json/Get_MsgBoard_Master.aspx?page=" + page + "&rows=" + rows;
		ArrayList<GetMsgBoardMaster> msgboardmaster_list = new ArrayList<GetMsgBoardMaster>();
		Log.i("url-----------", urlStr);
		String msgJson = "";

		try {
			HttpURLConnection conn = GetConnection(urlStr);
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				Thread.sleep(2000);
				// 获得输入流
				InputStream in = conn.getInputStream();
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgJson = new String(buffer, "utf-8");
				Log.i("得到的信息。。。。", msgJson);
				JSONArray jsonarray = new JSONArray(msgJson);
				int len = jsonarray.length();
				for (int i = 0; i < len; i++) {
					JSONObject object = jsonarray.getJSONObject(i);
					String id = object.getString("ID");
					String title = object.getString("TITLE");
					String create_Time = object.getString("CREATE_TIME");
					String create_Staff = object.getString("CREATE_STAFF");
					String update_Time = object.getString("UPDATE_TIME");
					String update_Staff = object.getString("UPDATE_STAFF");
					String staff = object.getString("Staff");
					GetMsgBoardMaster msgboardmasterInfo = new GetMsgBoardMaster();
					msgboardmasterInfo.setId(id);
					msgboardmasterInfo.setTitle(title);
					msgboardmasterInfo.setCreate_time(create_Time);
					msgboardmasterInfo.setCreate_staff(create_Staff);
					msgboardmasterInfo.setUpdate_time(update_Time);
					msgboardmasterInfo.setUpdate_staff(update_Staff);
					msgboardmasterInfo.setStaff(staff);
					msgboardmaster_list.add(msgboardmasterInfo);
				}
				in.close();// 关闭数据流
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msgboardmaster_list;
	}

	public static ArrayList<GetMsgBoardDetile> getmsgboarddetileJson(
			Context context,int masterid) throws JSONException, UnsupportedEncodingException {

		//page=0，rows=2主mian日志显示，page=0，rows=15：三星显示，page=0，rows=
		String urlStr = HttpURL + "/Json/Get_MsgBoard_Detile.aspx?" + "master_id=" + masterid;
		ArrayList<GetMsgBoardDetile> msgboarddetile_list = new ArrayList<GetMsgBoardDetile>();
		Log.i("url-----------", urlStr);
		String msgJson = "";

		try {
			HttpURLConnection conn = GetConnection(urlStr);
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				Thread.sleep(2000);
				// 获得输入流
				InputStream in = conn.getInputStream();
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgJson = new String(buffer, "utf-8");
				Log.i("得到的信息。。。。", msgJson);
				JSONArray jsonarray = new JSONArray(msgJson);
				int len = jsonarray.length();
				for (int i = 0; i < len; i++) {
					JSONObject object = jsonarray.getJSONObject(i);
					String id = object.getString("ID");
					String master_id = object.getString("MASTER_ID");
					String create_staff = object.getString("CREATE_STAFF");
					String update_date = object.getString("UPDATE_DATE");
					String doc = object.getString("DOC");
					String staff = object.getString("Staff");
					GetMsgBoardDetile msgboarddetileInfo = new GetMsgBoardDetile();
					msgboarddetileInfo.setId(id);
					msgboarddetileInfo.setMaster_id(master_id);
					msgboarddetileInfo.setCreate_staff(create_staff);
					msgboarddetileInfo.setUpdate_date(update_date);
					msgboarddetileInfo.setDoc(doc);
					msgboarddetileInfo.setStaff(staff);
					msgboarddetile_list.add(msgboarddetileInfo);
				}
				in.close();// 关闭数据流
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msgboarddetile_list;
	}

	/**
	 * @param context
	 * @param page
	 * @param rows
	 * @return
	 * @throws JSONException
	 * @throws UnsupportedEncodingException
	 */
	public static ArrayList<GetJobs> getjobsJson(
			Context context,int page,int rows) throws JSONException, UnsupportedEncodingException {

		//page=0，rows=2主mian日志显示，page=0，rows=15：三星显示，page=0，rows=
		String urlStr = HttpURL + "/Json/GetJobs.aspx?"+"page=" + page + "&rows=" + rows; 
		ArrayList<GetJobs> jobs_list = new ArrayList<GetJobs>();
		Log.i("url-----------", urlStr);
		String msgJson = "";

		try {
			HttpURLConnection conn = GetConnection(urlStr);
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				// 获得输入流
				InputStream in = conn.getInputStream();
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgJson = new String(buffer, "utf-8");
				Log.i("得到的信息。。。。", msgJson);
				JSONArray jsonarray = new JSONArray(msgJson);
				int len = jsonarray.length();
				for (int i = 0; i < len; i++) {
					JSONObject object = jsonarray.getJSONObject(i);
					String jobid = object.getString("jobid");
					String comname = object.getString("comname");
					String jobname = object.getString("jobname");
					String jobno = object.getString("jobno");
					String eduname = object.getString("eduname");
					String startage = object.getString("startage");
					String endage = object.getString("endage");
					String recruitnums = object.getString("recruitnums");
					String modifydate = object.getString("modifydate");
					String startsalary = object.getString("startsalary");
					String endsalary = object.getString("endsalary");
					String max_row = object.getString("max_row");
					GetJobs jobsInfo = new GetJobs();
					jobsInfo.setJobid(jobid);
					jobsInfo.setComname(comname);
					jobsInfo.setJobname(jobname);
					jobsInfo.setJobno(jobno);
					jobsInfo.setEduname(eduname);
					jobsInfo.setStartage(startage);
					jobsInfo.setEndage(endage);
					jobsInfo.setRecruitnums(recruitnums);
					jobsInfo.setModifydate(modifydate);
					jobsInfo.setStartsalary(startsalary);
					jobsInfo.setEndsalary(endsalary);
					jobsInfo.setMax_row(max_row);
					jobs_list.add(jobsInfo);
				}
				in.close();// 关闭数据流
				conn.disconnect();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jobs_list;
	}

	public static String CheckPersoninfo(String titleName){
		String urlStr="";
		try {
			urlStr = HttpURL + "/Json/GetCheckLineLevel.aspx?module_name="+ URLEncoder.encode(titleName,"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String msgJson = "" ;
		//		http://192.168.11.118:89/Json/GetCheckLineLevel.aspx?module_name=

		try {
			HttpURLConnection conn = GetConnection(urlStr);
			Log.i("json check  ....", urlStr);
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				//Thread.sleep(2000);
				// 获得输入流
				InputStream in = conn.getInputStream();
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgJson = new String(buffer, "utf-8");
				Log.i("json check  ....", msgJson);
				in.close();
				conn.disconnect();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return msgJson;

	}
	public static String postResumeJson(ArrayList<PersonResume> list_resume) {
		StringBuilder builder = new StringBuilder();
		String urlPost = HttpURL + "/Json/Set_Resumes_Info.aspx";
		Log.i("uuuurl?????", urlPost);
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		JSONArray jsonarray = new JSONArray();
		for (PersonResume personresume : list_resume) {
			JSONObject jsonObject = new JSONObject();
			try {	
				jsonObject.put("IDNO", personresume.getPersonSFZ());
				jsonObject.put("COMPUTERLEVELID", personresume.getPersonComputerSkills());
				jsonObject.put("COMPUTERCERT", personresume.getPersonComputerZhengshu());
				jsonObject.put("LANGUAGEID_1", personresume.getPersonForeignLanguages1());
				jsonObject.put("LANGUAGEID_2", personresume.getPersonForeignLanguages2());
				jsonObject.put("STARTSALARY", personresume.getPersonExpectedSalarylow());
				jsonObject.put("ENDSALARY", personresume.getPersonExpectedSalaryUp());
				jsonObject.put("ZYFLID_1", personresume.getPersonFuGangwei1());
				jsonObject.put("ZYFLID_2", personresume.getPersonFuGangwei2());
				jsonObject.put("OTHERZYFL", personresume.getPersonGanwei());
				jsonObject.put("GZXZID", personresume.getPersonNatureEmployment());
				jsonObject.put("LANGUAGECERT", personresume.getPersonForeignZhengshu());
				jsonObject.put("OTHERCERTS", personresume.getPersonQitazhengshu());
				jsonObject.put("LANGUAGEPROFICIENCYID_1", personresume.getPersonShunian1());
				jsonObject.put("LANGUAGEPROFICIENCYID_2", personresume.getPersonShunian2());
				jsonObject.put("AREAID_1", personresume.getPersonWorkaddress1());
				jsonObject.put("AREAID_2", personresume.getPersonWorkaddress2());
				jsonObject.put("AREAID_3", personresume.getPersonWorkaddress3());
				jsonObject.put("GZBSID", personresume.getPersonWorktime());
//				jsonObject.put("WORKYEARS", personresume.getPersonworkyears());
				jsonObject.put("ZYFLCHILDID_1", personresume.getPersonZigangwei1());
				jsonObject.put("ZYFLCHILDID_2", personresume.getPersonZigangwei2());
				jsonObject.put("SELFEVALUATION", personresume.getPersonAssessment());
				jsonObject.put("GPS", FirstPageActivity.GetGpsInfo());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsonarray.put(jsonObject);
			Log.i("jsonjianli??????????????", jsonarray.toString());
		}
		nameValuePair.add(new BasicNameValuePair("jsonString", jsonarray
				.toString()));
		try {
			// 建立服务器的连接，以post方式发送到服务器。
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(urlPost);
			if(staffName!=""){
				httpPost.setHeader("cookie",staffName);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
			Log.i("json?????????%%%%%%%%%%%", nameValuePair+"");
			HttpResponse httpResponse = httpClient.execute(httpPost);

			Log.i("返回的呢？？？？？？？", httpResponse.toString());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	public static ArrayList<PersonResume> getPersonResumeJson(
			final Context context, String cardId) {
		String urlStr = HttpURL + "/Json/Get_Resumes_Info.aspx?sfz=" + cardId;
		ArrayList<PersonResume> personresume_list = new ArrayList<PersonResume>();
		Log.i("url-----------", urlStr);
		String msgJson = "";
		try {
			HttpURLConnection conn = GetConnection(urlStr);
			conn.setConnectTimeout(5000);
			if (conn.getResponseCode() == 200) {
				// 获得输入流
				InputStream in = conn.getInputStream();
				Thread.sleep(1000);
				// 创建一个缓冲字节数
				byte[] buffer = new byte[in.available()];
				// 在输入流中读取数据并存放到缓冲字节数组中
				in.read(buffer);
				// 将字节转换成字符串
				msgJson = new String(buffer, "utf-8");
				Log.i("得到的信息。。。。", msgJson);
				JSONArray jsonarray = new JSONArray(msgJson);
				int len = jsonarray.length();
				for (int i = 0; i < len; i++) {
					JSONObject object = jsonarray.getJSONObject(i);
					String personcomputerskills = object.getString("COMPUTERLEVELID");
					String personcomputercert = object.getString("COMPUTERCERT");
					String personlanguage1 = object.getString("LANGUAGEID_1");
					String personlanguage2 = object.getString("LANGUAGEID_2");
					String personchendu1 = object.getString("LANGUAGEPROFICIENCYID_1");
					String personchendu2 = object.getString("LANGUAGEPROFICIENCYID_2");
					String personwaiyuzhengshu = object.getString("LANGUAGECERT");
					String personotherzhengshu = object.getString("OTHERCERTS");
					String personselfpingjia = object.getString("SELFEVALUATION");
					String personfugangwei1 = object.getString("ZYFLID_1");
					String personzigangwei1 = object.getString("ZYFLCHILDID_1");
					String personfugangwei2 = object.getString("ZYFLID_2");
					String personzigangwei2 = object.getString("ZYFLCHILDID_2");
					String personothergangwei = object.getString("OTHERZYFL");
					String personxinzilow = object.getString("STARTSALARY");
					String personxinziup = object.getString("ENDSALARY");
					String persongzxz = object.getString("GZXZNAME");
					String persongzbs = object.getString("GZBSNAME");
					String personaddress1 = object.getString("AREAID_1");
					String personaddress2 = object.getString("AREAID_2");
					String personaddress3 = object.getString("AREAID_3");
					String personworkyear = object.getString("WORKYEARS");
					PersonResume resume = new PersonResume();
					resume.setPersonAssessment(personselfpingjia);
					resume.setPersonComputerSkills(personcomputerskills);
					resume.setPersonComputerZhengshu(personcomputercert);
					resume.setPersonExpectedSalarylow(personxinzilow);
					resume.setPersonExpectedSalaryUp(personxinziup);
					resume.setPersonForeignLanguages1(personlanguage1);
					resume.setPersonForeignLanguages2(personlanguage2);
					resume.setPersonForeignZhengshu(personwaiyuzhengshu);
					resume.setPersonFuGangwei1(personfugangwei1);
					resume.setPersonFuGangwei2(personfugangwei2);
					resume.setPersonGanwei(personothergangwei);
					resume.setPersonNatureEmployment(persongzxz);
					resume.setPersonQitazhengshu(personotherzhengshu);
					resume.setPersonShunian1(personchendu1);
					resume.setPersonShunian2(personchendu2);
					resume.setPersonWorkaddress1(personaddress1);
					resume.setPersonWorkaddress2(personaddress2);
					resume.setPersonWorkaddress3(personaddress3);
					resume.setPersonworkyears(personworkyear);
					resume.setPersonWorktime(persongzbs);
					resume.setPersonZigangwei1(personzigangwei1);
					resume.setPersonZigangwei2(personzigangwei2);
					personresume_list.add(resume);

				}
				in.close();// 关闭数据流
				conn.disconnect();
			}
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (JSONException e) {

			e.printStackTrace();
		}
		return personresume_list;
	}
	public static Bitmap getadminImage(Context context){
		String urlstr = HttpURL+"/Json/GetStaffPic.aspx";
		Bitmap bitmap = null;
		HttpURLConnection conn = GetConnection(urlstr);
		conn.setConnectTimeout(5000);
		try {
			if (conn.getResponseCode() == 200) {
				Thread.sleep(2000);
				// 获得输入流
				InputStream in = conn.getInputStream();
				bitmap = BitmapFactory.decodeStream(in);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;

	}
	public static String postMarJson(ArrayList<PersonMark> list_mark) {
		StringBuilder builder = new StringBuilder();
		String urlPost = HttpURL + "/Json/Set_Tb_Mark.aspx";
		Log.i("uuuurl?????", urlPost);
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		JSONArray jsonarray = new JSONArray();

		for (PersonMark personmark: list_mark) {
			JSONObject jsonObject = new JSONObject();
			try {
				if(personmark.getPersonMarkId()!=null && personmark.getPersonMarkId().equals("-1")){
					jsonObject.put("ID", "-1");
					jsonObject.put("SFZ",personmark.getPersonSFZ());
					list_mark.clear();
				}else {
					jsonObject.put("MARK", personmark.getPersonMarkName());
					jsonObject.put("SOURCE",personmark.getPersonMarkSoure());
					jsonObject.put("CREATE_DATE",personmark.getPersonMarkCreatdate());
					jsonObject.put("SFZ",personmark.getPersonSFZ());
					jsonObject.put("GPS", FirstPageActivity.GetGpsInfo());
				}				

			} catch (JSONException e) {
				e.printStackTrace();
			}
			jsonarray.put(jsonObject);
			Log.i("jsonjianli001", jsonarray.toString());
		}
		nameValuePair.add(new BasicNameValuePair("jsonString", jsonarray
				.toString()));
		try {
			// 建立服务器的连接，以post方式发送到服务器。
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(urlPost);
			if(staffName!=""){
				httpPost.setHeader("cookie",staffName);
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
			Log.i("json?????????%%%%%%%%%%%", nameValuePair+"");
			
			System.out.println("postparams====>"+nameValuePair);
			HttpResponse httpResponse = httpClient.execute(httpPost);

			Log.i("返回的呢？？？？？？？", httpResponse.toString());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					httpResponse.getEntity().getContent()));
			for (String s = reader.readLine(); s != null; s = reader.readLine()) {
				builder.append(s);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
}


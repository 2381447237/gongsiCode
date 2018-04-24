package com.fc.meetdoc.tools;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fc.meetdoc.R;
import com.fc.meetdoc.entity.ShareMeeting;
import com.fc.meetdoc.entity.Task;
import com.fc.meetdoc.face.Const;
import com.fc.meetdoc.face.IActivity;
import com.fc.meetdoc.service.MainService;
import com.fc.meetdoc.views.SetIP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainTools {
	public static ShareMeeting sharemeet;
	private static Message msg;

	// public static Handler handler=new Handler(){
	//
	// public void handleMessage(Message msg) {
	// Activity activity=null;
	// activity = (Activity) MainService.getActivityByName("SetIP");
	// switch(msg.arg1){
	// case Task.REMAIND:
	// Toast.makeText((Context)msg.obj, "已是最新列表", 1).show();
	// break;
	// case Task.REFRESHIPLIST:
	// ((SetIP) activity).display();
	// break;
	//
	// }
	// };
	// };

	public static void getshare(Context context) {
		sharemeet = new ShareMeeting(context);
	}

	public static Intent openFile(String filePath) {

		File file = new File(filePath);

		if ((file == null) || !file.exists() || file.isDirectory())
			return null;

		/* 取得扩展名 */
		String end = file
				.getName()
				.substring(file.getName().lastIndexOf(".") + 1,
						file.getName().length()).toLowerCase();
		/* 依扩展名的类型决定MimeType */
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			return getAudioFileIntent(filePath);
		} else if (end.equals("3gp") || end.equals("mp4")) {
			return getAudioFileIntent(filePath);
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			return getImageFileIntent(filePath);
		} else if (end.equals("apk")) {
			return getApkFileIntent(filePath);
		} else if (end.equals("ppt") || end.equals("pptx")) {
			return getPptFileIntent(filePath);
		} else if (end.equals("xls") || end.equals("xlsx")) {
			// excell文件
			return getExcelFileIntent(filePath);
		} else if (end.equals("doc") || end.equals("docx")) {
			// word文件
			return getWordFileIntent(filePath);
		} else if (end.equals("pdf")) {
			return getPdfFileIntent(filePath);
		} else if (end.equals("chm")) {
			return getChmFileIntent(filePath);
		} else if (end.equals("txt")) {
			return getTextFileIntent(filePath, false);
		} else {
			return getAllIntent(filePath);
		}
	}

	// Android获取一个用于打开APK文件的intent
	public static Intent getAllIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "*/*");
		return intent;
	}

	// Android获取一个用于打开APK文件的intent
	public static Intent getApkFileIntent(String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		return intent;
	}

	// Android获取一个用于打开VIDEO文件的intent
	public static Intent getVideoFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	// Android获取一个用于打开AUDIO文件的intent
	public static Intent getAudioFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	// Android获取一个用于打开Html文件的intent
	public static Intent getHtmlFileIntent(String param) {

		Uri uri = Uri.parse(param).buildUpon()
				.encodedAuthority("com.android.htmlfileprovider")
				.scheme("content").encodedPath(param).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	// Android获取一个用于打开图片文件的intent
	public static Intent getImageFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	// Android获取一个用于打开PPT文件的intent
	public static Intent getPptFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	// Android获取一个用于打开Excel文件的intent
	public static Intent getExcelFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	// Android获取一个用于打开Word文件的intent
	public static Intent getWordFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	// Android获取一个用于打开CHM文件的intent
	public static Intent getChmFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/x-chm");
		return intent;
	}

	// Android获取一个用于打开文本文件的intent
	public static Intent getTextFileIntent(String param, boolean paramBoolean) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (paramBoolean) {
			Uri uri1 = Uri.parse(param);
			intent.setDataAndType(uri1, "text/plain");
		} else {
			Uri uri2 = Uri.fromFile(new File(param));
			intent.setDataAndType(uri2, "text/plain");
		}
		return intent;
	}

	// Android获取一个用于打开PDF文件的intent
	public static Intent getPdfFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

	/**
	 * 将String转为指定长度的byte[]
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static byte[] parseStrToByte(String str, int length) {
		try {
			byte[] value = str.getBytes("utf-8");
			return Arrays.copyOf(value, length);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字节数组拼接
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public static byte[] joinArray(byte[] first, byte[] second) {
		byte[] data3 = new byte[first.length + second.length];
		System.arraycopy(first, 0, data3, 0, first.length);
		System.arraycopy(second, 0, data3, first.length, second.length);
		return data3;
	}

	/**
	 * 得到本机IP
	 */
	public static String getRealIp() {
		// 注释部分方法可能会得到ipv6地址
		// String localip = null;// 本地IP，如果没有配置外网IP则返回它
		// String netip = null;// 外网IP
		// try {
		// Enumeration<NetworkInterface> netInterfaces = NetworkInterface
		// .getNetworkInterfaces();
		// InetAddress ip = null;
		// boolean finded = false;// 是否找到外网IP
		// while (netInterfaces.hasMoreElements() && !finded) {
		// NetworkInterface ni = netInterfaces.nextElement();
		// Enumeration<InetAddress> address = ni.getInetAddresses();
		// while (address.hasMoreElements()) {
		// ip = address.nextElement();
		// if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
		// && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
		// netip = ip.getHostAddress();
		// finded = true;
		// break;
		// } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
		// && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
		// localip = ip.getHostAddress();
		// }
		// }
		// }
		// } catch (SocketException e) {
		// e.printStackTrace();
		// }
		//
		// if (netip != null && !"".equals(netip)) {
		// return netip;
		// } else {
		// return localip;
		// }

		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& inetAddress instanceof Inet4Address) {
						// if (!inetAddress.isLoopbackAddress() && inetAddress
						// instanceof Inet6Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
		}
		return "";
	}

	/**
	 * 将字符串转为map
	 * 
	 * @param value
	 * @return
	 */
	// fileName=朱海林.xls,fileSize=15360,hostIP=192.168.11.127,filePath=/sdcard/MEETDOCS/朱海林.xls
	public static Map<String, String> fretchStrToMap(String value) {
		Map<String, String> data = new HashMap<String, String>();
		String[] infos = value.split(",");
		Log.e("split", "split=" + Arrays.toString(infos));
		for (String info : infos) {
			if (info != null && info.length() > 0) {
				String[] values = info.split("=");
				if (values.length > 1) {
					data.put(values[0], values[1]);
				} else {
					data.put(values[0], "");
				}
			}
		}

		return data;
	}

	// 获取文件所有IP
	/**
	 * 取出字符串转化成数组
	 */
	public static String[] getAllIP(Context context) {
		getshare(context);
		if (TextUtils.isEmpty(sharemeet.getIP())) {
			return null;
		} else {
			String IP = sharemeet.getIP();
			return IP.split(";");
		}
	}

	/**
	 * IP匹配
	 */
	public static void pick(Context context, String accept[]) {
		getshare(context);
		getAllIP(context);

		if (accept != null) {
			// StringBuffer buffer=new StringBuffer();
			String buf_pick;
			// 接收的IP数据中不能包含本机IP
			for (int i = 0; i < accept.length; i++) {
				if (accept[i].equals(MainTools.getRealIp())) {
					Arrays.fill(accept, i, i + 1, null);
				}
			}

			if (getAllIP(context) != null) {
				for (int i = 0; i < accept.length; i++) {
					// 匹配IP不能与列表中IP冲突
					for (int j = 0; j < getAllIP(context).length; j++) {
						if (getAllIP(context)[j].equals(accept[i])) {
							Arrays.fill(accept, i, i + 1, null);
						}
					}
				}
				Log.e("accept", "accept=" + Arrays.toString(accept));
				// 将接收数据中筛选出的IP添加到本地列表
				buf_pick = store(accept);
				Log.e("buf_pick", "buf_pick=" + buf_pick);
				// 存进文件
				if (TextUtils.isEmpty(buf_pick)) {
					msg = new Message();
					msg.arg1 = Task.REMAIND;
					MainService.handler.sendMessage(msg);
					return;
				} else {
					sharemeet.putIP(sharemeet.getIP() + ";" + buf_pick);
				}
				Log.e("Share", "Share=" + sharemeet.getIP() + ";" + buf_pick);
				// 通知列表更新
			} else {
				buf_pick = store(accept);
				// 存进文件
				if (TextUtils.isEmpty(buf_pick)) {
					msg = new Message();
					msg.arg1 = Task.REMAIND;
					MainService.handler.sendMessage(msg);
					return;
				} else {
					sharemeet.putIP(buf_pick);
				}
			}
			Log.e("Task.REFRESHIPLIST", "Task.REFRESHIPLIST="
					+ Task.REFRESHIPLIST);
			msg = new Message();
			msg.arg1 = Task.REFRESHIPLIST;
			MainService.handler.sendMessage(msg);
			Log.e("Task.REFRESHIPLIST", "+++++++++++" + Task.REFRESHIPLIST);
		}
	}

	/**
	 * 数组转化成字符串存储
	 */
	public static String store(String arr[]) {
		StringBuffer np = new StringBuffer();
		String p = null;
		int k = 0;
		if (arr != null && arr.length > 0) {
			for (int j = 0; j < arr.length; j++) {
				if (TextUtils.isEmpty(arr[j])) {
					k++;
				}
			}
			if (k == arr.length) {
				return null;
			}
			for (int i = 0; i < arr.length; i++) {
				if (!(TextUtils.isEmpty(arr[i]))) {
					np.append(arr[i] + ";");
					p = np.toString();
					p = p.substring(0, p.length() - 1);
				}
			}
			return p;
		}
		return null;
	}

	/**
	 * 判断IP格式//Ipv4
	 */
	public static boolean IPformat(String s) {
		// String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
		//
		// + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
		//
		// + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
		//
		// + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

		String ip = "^(1\\d{2}|2[0-4]\\d|25[0-5]|0{0,1}[1-9]\\d|0{0,2}[1-9])\\."

				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|0{0,1}[1-9]\\d|0{0,2}\\d)\\."

				+ "(1\\d{2}|2[0-4]\\d|25[0-5]|0{0,1}[1-9]\\d|0{0,2}\\d)\\."

				+ "(1\\d{2}|2[0-4]\\d|25[0-4]|0{0,1}[1-9]\\d|0{0,2}\\d)$";

		Pattern pattern = Pattern.compile(ip);

		Matcher matcher = pattern.matcher(s);

		return matcher.matches();
	}

	/*
	 * 字符串转化成数字
	 */
	public static int getStringtoInt(String s) {
		return Integer.valueOf(s);
	}

	/**
	 * 获得SharedPreferences
	 * 
	 * @param context
	 * @return
	 */
	public static SharedPreferences getPreferences(Context context) {
		return context.getSharedPreferences(Const.PREFERENCES_NAME,
				Context.MODE_PRIVATE);
	}

	/**
	 * 保存SharedPreferences
	 * 
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void savePreference(Context context, String key, String value) {
		Editor edit = getPreferences(context).edit();
		edit.putString(key, value);
		edit.commit();
	}

	/**
	 * 从SharedPreferences取值
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static String loadPreference(Context context, String key) {
		return getPreferences(context).getString(key, "");
	}

	public static String getSaveNewName(String name) {
		UUID uuid = UUID.randomUUID();
		String newName = name.substring(0, name.lastIndexOf(".")) + "[@@]"
				+ uuid.toString() + name.substring(name.lastIndexOf("."));
		return newName;
	}
	
	public static String getDisplayName(String name){
		int index = name.indexOf("[@@]")==-1?name.lastIndexOf("."):name.indexOf("[@@]");
		String displayName = name.substring(0, index)+name.substring(name.lastIndexOf(".")) ;
		return displayName;
	}

}

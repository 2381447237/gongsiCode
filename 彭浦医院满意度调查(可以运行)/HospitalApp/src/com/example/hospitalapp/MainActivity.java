package com.example.hospitalapp;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import okhttp3.Call;

import com.example.hospitalapp.entity.YGContent;
import com.example.hospitalapp.nonetwork.entity.YGNonetWorkContent;
import com.example.hospitalapp.utils.LongPressView;
import com.example.hospitalapp.utils.UpdateManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
		OnLongClickListener {

	private Button btn;
	public static String httpStr = "http://";
	//public static String url = "192.168.11.11:115"; �ɴ����
	public static String url = "192.168.11.11:89";//�ɰ汾������
	private EditText et_userName, et_passWord;
	private String dengluUrl;
	private String userNameStr, passWordStr, changeAddressStr;
	private int YGID, YHID;
	private String YHXM, YGXM;
	private SharedPreferences sp, sp2, spxm;
	public static String myJson;

	private boolean isCheckAccount = false;

	private com.example.hospitalapp.utils.LongPressView longPressView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(this);
		et_userName = (EditText) findViewById(R.id.et_username);
		et_passWord = (EditText) findViewById(R.id.et_password);

		longPressView = (LongPressView) findViewById(R.id.longPressView);
		longPressView.setOnLongClickListener(this);

        sp=getSharedPreferences("YG",Context.MODE_PRIVATE);
		
		url=sp.getString("setIP",url);
		
		// ����apk
		UpdateManager manager = new UpdateManager(MainActivity.this);
		manager.checkUpdate();

		int versionCode = getVersionCode(this);
		//Log.i("2016-11-28", "��ǰ�汾��ΪversionCode===============" + versionCode);
	}

	@Override
	protected void onResume() {
		// ǿ�ƺ���
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		super.onResume();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn:
			isCheckAccount = false;
			userNameStr = et_userName.getText().toString().trim();
			passWordStr = et_passWord.getText().toString().trim();

			if (TextUtils.isEmpty(userNameStr)
					|| TextUtils.isEmpty(passWordStr)) {

				Toast.makeText(this, "�û��������벻��Ϊ��", 0).show();

			} else {
				// �ж��Ƿ�����
				if (isNetworkAvailable(MainActivity.this)) {
					denglu();
				} else {
					dengluNoNetWork();
				}

			}

			break;

		default:
			break;
		}

	}

	// û�����ӷ������ĵ�¼
	private void dengluNoNetWork() {

		File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/myAnswerData.txt");

		if (!file.exists()) {

			Toast.makeText(MainActivity.this, "�����ӷ�����������", 0).show();

		} else {

			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					FormList.readFileSdcard(Environment
							.getExternalStorageDirectory().getAbsolutePath()
							+ "/myAnswerData.txt");

				}
			});

			getData();
			checkAccount();

		}
	}

	// ƥ���˺ź������Ƿ���ȷ
	private void checkAccount() {

		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				Gson gson = new Gson();

				Type listType = new TypeToken<LinkedList<YGNonetWorkContent>>() {
				}.getType();

				LinkedList<YGNonetWorkContent> ygContent = gson.fromJson(
						myJson, listType);

				for (int i = 0; i < ygContent.size(); i++) {

					if (ygContent.get(i).YHDLZH.equals(userNameStr)
							&& (ygContent.get(i).DLMM.equals(getMD5(passWordStr
									+ ygContent.get(i).YHID
									+ ygContent.get(i).YHXM)))) {
						// ���ܷ�ʽ:����+Ա��ID+Ա������
						YHID = ygContent.get(i).YHID;

						YHXM = ygContent.get(i).YHXM;

						isCheckAccount = true;

						sp2 = getSharedPreferences("YG2", Context.MODE_PRIVATE);

						Editor editor = sp2.edit();

						editor.putInt("YHID", YHID);

						editor.commit();

						sp = getSharedPreferences("YG", Context.MODE_PRIVATE);

						Editor editor2 = sp.edit();

						editor2.putString("YGID", String.valueOf(0));

						editor2.commit();

						spxm = getSharedPreferences("YGXM",
								Context.MODE_PRIVATE);

						Editor edxm = spxm.edit();

						edxm.putString("YGXM", null);

						edxm.commit();

						Toast.makeText(MainActivity.this, "��¼�ɹ�", 0).show();
						Intent intent = new Intent(MainActivity.this,
								FormList.class);
						startActivity(intent);
					}
				}

				if (!isCheckAccount) {

					Toast.makeText(MainActivity.this, "�û������������", 0).show();

				}

			}
		});

	}

	// ��ñ����˺ţ������json����
	private void getData() {

		if (FormList.downLoadData != null) {

			int num1 = FormList.downLoadData.indexOf("[");
			int num2 = FormList.downLoadData.indexOf("]");
			myJson = FormList.downLoadData.substring(num1, num2 + 1);

		}

	}

	// ���ӷ������ĵ�¼
	private void denglu() {

//		sp=getSharedPreferences("YG",Context.MODE_PRIVATE);
//		
//		url=sp.getString("setIP","192.168.11.11:89");
		
		dengluUrl = httpStr + url + "/Json/Login.aspx";

		// Toast.makeText(this,"��¼��ַ"+dengluUrl,0).show();

		Log.i("2016/11/24", "��¼��ַ" + dengluUrl);

		OkHttpUtils.post().url(dengluUrl).addParams("UserNo", userNameStr)
				.addParams("UserPwd", passWordStr).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(String arg0) {

						if (!arg0.equals("�û������������")) {
							Intent intent = new Intent(MainActivity.this,
									FormList.class);
							startActivity(intent);
							Toast.makeText(MainActivity.this, "��¼�ɹ�", 0).show();

							Gson gson = new Gson();

							YGContent ygc = gson
									.fromJson(arg0, YGContent.class);

							YGID = ygc.YGID;

							sp = getSharedPreferences("YG",
									Context.MODE_PRIVATE);

							Editor editor = sp.edit();

							editor.putString("YGID", String.valueOf(YGID));

							editor.commit();

							YGXM = ygc.YGXM;

							spxm = getSharedPreferences("YGXM",
									Context.MODE_PRIVATE);

							Editor edxm = spxm.edit();

							edxm.putString("YGXM", YGXM);

							edxm.commit();

							sp2 = getSharedPreferences("YG2",
									Context.MODE_PRIVATE);

							Editor editor2 = sp2.edit();

							editor2.putInt("YHID", 0);

							editor2.commit();

						} else {
							Toast.makeText(MainActivity.this, "�û��������벻��ȷ", 0)
									.show();
						}
					}

					@Override
					public void onError(Call arg0, Exception arg1) {

						Toast.makeText(MainActivity.this, "���������������ķ�������ַ", 0)
								.show();

					}
				});

	}

	// ��鵱ǰ�����Ƿ����
	private boolean isNetworkAvailable(Activity activity) {

		Context context = activity.getApplicationContext();

		// ��ȡ�ֻ��������ӹ�����󣨰�����wi-fi,net�����ӵĹ���
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {

			return false;

		} else {

			// ��ȡNetworkInfo����
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {

				for (int i = 0; i < networkInfo.length; i++) {
					/*
					 * System.out.println(i + "===״̬===" +
					 * networkInfo[i].getState()); System.out.println(i +
					 * "===����===" + networkInfo[i].getTypeName());
					 */
					// �жϵ�ǰ����״̬�Ƿ�Ϊ����״̬
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {

						// �жϵ�ǰ��wifi��ip�ͷ�������ip�Ƿ���ͬ
						// //=======================================================================
						// ��ȡwifi����
						WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
						// �ж�wifi�Ƿ���
						if (!wifiManager.isWifiEnabled()) {
							wifiManager.setWifiEnabled(true);
						}
						WifiInfo wifiInfo = wifiManager.getConnectionInfo();
						int ipAddress = wifiInfo.getIpAddress();
						String ip = intToIp(ipAddress);
						// Toast.makeText(this, "��ǰipΪ" + ip, 0).show();
						// �жϵ�ǰ��wifi��ip�ͷ�������ip�Ƿ���ͬ

						// if(!ip.equals(changeAddressStr)){
						// return false;
						// }

						// //=======================================================================

						return true;
					}
				}
			}
		}
		return false;
	}

	// �õ���ǰ��wifiIP��ַ
	private String intToIp(int i) {

		return (i & 0Xff) + "." + ((i >> 8) & 0Xff) + "." + ((i >> 16) & 0xff)
				+ "." + (i >> 24 & 0xff);

	}

	// ������MD5���ܣ���ĸ��д��
	public String getMD5(String info) {

		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(info.getBytes("UTF-8"));
			byte[] encryption = md5.digest();
			StringBuffer strBuf = new StringBuffer();
			for (int i = 0; i < encryption.length; i++) {
				if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
					strBuf.append("0").append(
							Integer.toHexString(0xff & encryption[i]));
				} else {
					strBuf.append(Integer.toHexString(0xff & encryption[i]));
				}
			}

			return strBuf.toString().toUpperCase();// ��ĸ��д

		} catch (UnsupportedEncodingException e) {

			return "";
		}

		catch (NoSuchAlgorithmException e) {

			return "";
		}

	}

	@Override
	public boolean onLongClick(View v) {

		switch (v.getId()) {

		case R.id.longPressView:

			showChangeIpDialog();

			break;

		default:
			break;
		}

		return false;
	}

	private void showChangeIpDialog() {

		final EditText change_et;
		Button change_btn;

		final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
				.create();

		View view = LayoutInflater.from(MainActivity.this).inflate(
				R.layout.dialog_change, null);

		dialog.setView(view);
       // dialog.setCancelable(false);
		dialog.show();
		Window window = dialog.getWindow();
		// WindowManager.LayoutParams lp=window.getAttributes();
		// window.setGravity(Gravity.CENTER);
		// window.setAttributes(lp);
		window.setContentView(R.layout.dialog_change);

		change_et = (EditText) window.findViewById(R.id.change_et);

		change_btn = (Button) window.findViewById(R.id.change_btn);
		change_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!TextUtils.isEmpty(change_et.getText().toString().trim())) {

					changeAddressStr = change_et.getText().toString().trim();

					url = changeAddressStr;

					sp=getSharedPreferences("YG",Context.MODE_PRIVATE);
					
					Editor editor=sp.edit();
					
					editor.putString("setIP",changeAddressStr);
					
					editor.commit();
					
					Toast.makeText(MainActivity.this, "��������ַ�޸�Ϊ:" + url, 0)
							.show();
					
					// ����apk
					UpdateManager manager = new UpdateManager(MainActivity.this);
					manager.checkUpdate();
					
				} else {

					Toast.makeText(MainActivity.this, "�����ַ����Ϊ��", 0).show();

					return;
				}

				dialog.dismiss();

			}
		});
	}

	/**
	 * ��ȡ����汾��
	 * 
	 * @param context
	 * @return
	 */
	private int getVersionCode(Context context) {
		int versionCode = 0;
		try {
			// ��ȡ����汾�ţ���ӦAndroidManifest.xml��android:versionCode
			versionCode = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionCode;
	}

}

package com.example.hospitalapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.apache.http.util.EncodingUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

import com.example.hospitalapp.adapter.FormListAdapter;
import com.example.hospitalapp.entity.FormListContent;
import com.example.hospitalapp.entity.RecordContent;
import com.example.hospitalapp.nonetwork.adapter.FormListNonetWorkAdapter;
import com.example.hospitalapp.nonetwork.entity.AnswerNonetWorkContent;
import com.example.hospitalapp.nonetwork.entity.FormListNonetWorkContent;
import com.example.hospitalapp.nonetwork.entity.FormatDetailedQuestionModel;
import com.example.hospitalapp.nonetwork.entity.FormatDetailedQuestionModel.ParentModel;
import com.example.hospitalapp.receiver.ConnectionChangeReceiver;
import com.example.hospitalapp.sqlite.PersonDao;
import com.example.hospitalapp.sqlite.RecordNonetWorkContent;
import com.example.hospitalapp.utils.Json2ModelAsyncNonetWork;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.media.ImageReader.OnImageAvailableListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Xml.Encoding;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FormList extends Activity implements OnItemClickListener,
		OnClickListener, SwipeRefreshLayout.OnRefreshListener {

	protected static final int ERROR = 111111;
	protected static final int GETNONETDATA = 222222;
	protected static final int SUCCESS = 333333;
	protected static final int FAIL = 444444;
	private FormListAdapter flAdapter;
	public ListView lv;

	public static List<FormListContent> data = new ArrayList<FormListContent>();
	private String listUrl = MainActivity.httpStr+MainActivity.url + "/Json/Get_Question_List.aspx";
	private String downLoadUrl = MainActivity.httpStr+MainActivity.url + "/Json/Get_Down_Info.aspx";
	private String uploadUrl = MainActivity.httpStr+MainActivity.url
			+ "/Json/Get_Question_Stream_Info.aspx";
	// http://192.168.11.11:115/Json/Get_Question_Stream_Info.aspx
	private List<Integer> shunxu = new ArrayList<Integer>();
	private List<Integer> shunxunoNet = new ArrayList<Integer>();
	public static int myMasterId;
	public static int mynoNetMasterId;
	private RelativeLayout rl_caidan;
	public static String downLoadData;
	private String downLoadJson;
	private String[] temp = null;
	private String downLoadPath = Environment.getExternalStorageDirectory().getAbsolutePath();

	public static List<FormListNonetWorkContent> noNetData = new ArrayList<FormListNonetWorkContent>();
	public ListView noNetlv;
	private FormListNonetWorkAdapter noNetAdapter;

	// private boolean isData = false;

	private ArrayList<FormatDetailedQuestionModel.ParentModel> mList = new ArrayList<>();
	private ArrayList<FormatDetailedQuestionModel.ParentModel> itemList1 = new ArrayList<>();
	private PersonDao pDao;

	private ConnectionChangeReceiver myReceiver;
	private int requestCode;

	public SwipeRefreshLayout srl, noNetSrl;
	public TextView tv_advise;
	public  File file;
	
	private Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			switch (msg.what) {
			case GETNONETDATA:
				noNetData = (List<FormListNonetWorkContent>) msg.obj;

				// if (isData == false) {

				noNetAdapter = new FormListNonetWorkAdapter(noNetData,FormList.this);

				noNetlv.setAdapter(noNetAdapter);
				noNetSrl.setRefreshing(false);
				// isData = true;

				// }

				noNetAdapter.notifyDataSetChanged();

				break;

			case ERROR:

				Toast.makeText(FormList.this, "上传失败，请检查网络", 0).show();

				break;
			case FAIL:

				Toast.makeText(FormList.this, "答案上传失败，请重新上传", 0).show();

				break;
			case SUCCESS:

				Toast.makeText(FormList.this, "答案上传成功", 0).show();

				break;

			default:
				break;
			}

		};

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_formlist);

		rl_caidan = (RelativeLayout) findViewById(R.id.caidan_rl);
		rl_caidan.setOnClickListener(this);

		srl = (SwipeRefreshLayout) findViewById(R.id.swipe_ly);
		srl.setOnRefreshListener(this);
		srl.setColorScheme(android.R.color.holo_green_dark,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		noNetSrl = (SwipeRefreshLayout) findViewById(R.id.swipe_ly_noNetwork);
		noNetSrl.setOnRefreshListener(this);
		noNetSrl.setColorScheme(android.R.color.holo_green_dark,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		lv = (ListView) findViewById(R.id.lv_formlist);
		lv.setOnItemClickListener(this);

		noNetlv = (ListView) findViewById(R.id.lv_formlist_noNetwork);
		noNetlv.setOnItemClickListener(this);
		
		// getListData();
		pDao = new PersonDao(this);
		
		tv_advise=(TextView) findViewById(R.id.tvAdvise);
		tv_advise.setVisibility(View.GONE);
		
		registerReceiver();
		
	}

	// 注册Receiver
	private void registerReceiver() {

		IntentFilter filter = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);

		myReceiver = new ConnectionChangeReceiver();

		this.registerReceiver(myReceiver, filter);

	}

	// 注销Receiver
	private void unregisterReceiver() {

		this.unregisterReceiver(myReceiver);

	}

	private void getListData() {

		OkHttpUtils.post().url(listUrl).build().execute(new StringCallback() {

			@Override
			public void onResponse(final String str) {

				runOnUiThread(new Runnable() {

					@Override
					public void run() {

						Gson gson = new Gson();

						Type listType = new TypeToken<LinkedList<FormListContent>>() {
						}.getType();

						LinkedList<FormListContent> flc = gson.fromJson(str,
								listType);

						data.clear();
						shunxu.clear();
						for (Iterator iterator = flc.iterator(); iterator
								.hasNext();) {

							FormListContent content = (FormListContent) iterator
									.next();

							data.add(content);
							shunxu.add(content.ID);
						}

						flAdapter = new FormListAdapter(data, FormList.this);

						lv.setAdapter(flAdapter);
						srl.setRefreshing(false);
					}
				});

			}

			@Override
			public void onError(Call arg0, Exception arg1) {
				// Toast.makeText(FormList.this, "请检查您的网络", 0).show();
			}
		});

	}

	@Override
	protected void onResume() {
		// 强制横屏
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}

		super.onResume();

		getListData();

		file = new File(downLoadPath + "/myAnswerData.txt");

		if (file.exists()) {

			runOnUiThread(new Runnable() {

				@Override
				public void run() {

					readFileSdcard(downLoadPath + "/myAnswerData.txt");

				}
			});

		} else {

//			if (isNetworkAvailable(FormList.this) == false) {
//				// if (isNetwork == false) {
//
//				Toast.makeText(this, "文件不存在", 0).show();
////				noNetSrl.setVisibility(View.GONE);
////				noNetlv.setVisibility(View.GONE);
////				tv_advise.setVisibility(View.VISIBLE);
//			}
		}

		if (downLoadData != null) {

			temp = downLoadData.split("]");

			downLoadJson = temp[1] + "]";

			getNonetData(downLoadJson);

		}

	}

	private void getNonetData(final String str) {

		new Thread() {

			public void run() {

				Gson gson = new Gson();

				Type listType = new TypeToken<LinkedList<FormListNonetWorkContent>>() {
				}.getType();

				LinkedList<FormListNonetWorkContent> nwc = gson.fromJson(str,
						listType);

				noNetData.clear();
				shunxunoNet.clear();
				for (FormListNonetWorkContent f : nwc) {

					noNetData.add(f);

					shunxunoNet.add(f.ID);

				}
				Message msg = Message.obtain();

				msg.obj = noNetData;

				msg.what = GETNONETDATA;

				mHandler.sendMessage(msg);

			};

		}.start();

		new Json2ModelAsyncNonetWork(FormList.this) {

			@Override
			public void onPostExecute(ArrayList<ParentModel> parentList) {

				mList = parentList;

			}
		}.execute();

	}

	// 检查当前网络是否可用

	private boolean isNetworkAvailable(Activity activity) {

		Context context = activity.getApplicationContext();

		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {

			return false;

		} else {

			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					/*
					 * System.out.println(i + "===状态===" +
					 * networkInfo[i].getState()); System.out.println(i +
					 * "===类型===" + networkInfo[i].getTypeName());
					 */
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {

						// 判断当前的wifi的ip和服务器的ip是否相同
						// //=======================================================================

						// 获取wifi服务
						WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
						// 判断wifi是否开启
						if (!wifiManager.isWifiEnabled()) {
							wifiManager.setWifiEnabled(true);
						}

						WifiInfo wifiInfo = wifiManager.getConnectionInfo();
						int ipAddress = wifiInfo.getIpAddress();
						String ip = intToIp(ipAddress);
						// Toast.makeText(this, "当前ip为" + ip, 0).show();
						// 判断当前的wifi的ip和服务器的ip是否相同
						// if (!ip.equals("0.0.0.0")) {
						// if
						// (!ip.equals("10.0.3.15")||!ip.equals("192.168.11.116"))
						// {
         //						if (!ip.equals("10.0.3.15")
         //								&& !ip.equals(MainActivity.myIpAddress)) {
         //
         //							return false;
         //						}

						// //=======================================================================
					
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {

		switch (parent.getId()) {

		case R.id.lv_formlist:
			myMasterId = shunxu.get(position);
			Intent intent = new Intent(this, DetailedQuestion.class);

			for (int i = 0; i < data.size(); i++) {

				if (data.get(i).ID == myMasterId) {

					intent.putExtra("title_tv", data.get(i).TITLE);
					intent.putExtra("header_tv", data.get(i).NOTE);

				}

			}

			startActivity(intent);

			break;

		case R.id.lv_formlist_noNetwork:

			mynoNetMasterId = shunxunoNet.get(position);

			if (view instanceof LinearLayout) {

				View view1 = ((LinearLayout) view).getChildAt(0);
				noNetWorkGroupList((Integer) view1.getTag());
				Intent intent2 = new Intent(this,
						DetailedQuestionNonetWork.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable(DetailedQuestionNonetWork.DATA_LIST,
						itemList1);
				intent2.putExtra(DetailedQuestionNonetWork.LIST, bundle);
				for (int i = 0; i < noNetData.size(); i++) {

					if (noNetData.get(i).ID == mynoNetMasterId) {

						intent2.putExtra("title_tv", noNetData.get(i).TITLE);
						intent2.putExtra("header_tv", noNetData.get(i).NOTE);
					}

				}
				startActivity(intent2);

			}
			break;

		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.caidan_rl:
			showDialog();
			break;

		default:
			break;
		}
	}

	private void downLoad() {

		OkHttpUtils
				.get()
				.url(downLoadUrl)
				.build()
				.execute(
						new FileCallBack(Environment
								.getExternalStorageDirectory()
								.getAbsolutePath(), "myAnswerData.txt") {

							@Override
							public void onResponse(File arg0) {

								Toast.makeText(FormList.this,
										"下载完成", 0).show();

								runOnUiThread(new Runnable() {

									@Override
									public void run() {

										readFileSdcard(downLoadPath
												+ "/myAnswerData.txt");

									}
								});

							}

							@Override
							public void onError(Call arg0, Exception arg1) {

								Toast.makeText(FormList.this,
										"下载失败，请检查网络", 0).show();
							}

							@Override
							public void inProgress(float arg0, long arg1) {

							}
						});

	}

	public static String readFileSdcard(String fileName) {

		try {
			FileInputStream fin = new FileInputStream(fileName);

			int length = fin.available();

			byte[] buffer = new byte[length];

			while (fin.read(buffer) != -1) {

			}

			downLoadData = EncodingUtils.getString(buffer, "UTF-8");

			fin.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return downLoadData;

	}

	private String intToIp(int i) {

		return (i & 0Xff) + "." + ((i >> 8) & 0Xff) + "." + ((i >> 16) & 0xff)
				+ "." + (i >> 24 & 0xff);

	}

	// 显示对话框
	private void showDialog() {

		LinearLayout dialog_download, dialog_upload;

		final AlertDialog dialog = new AlertDialog.Builder(FormList.this)
				.create();

		View view = LayoutInflater.from(FormList.this).inflate(
				R.layout.dialog_download, null);

		dialog.setView(view);
		dialog.show();
		Window window = dialog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setGravity(Gravity.RIGHT | Gravity.TOP);
		lp.x = 30;
		lp.y = 70;
		window.setAttributes(lp);
		window.setContentView(R.layout.dialog_download);

		dialog_download = (LinearLayout) window.findViewById(R.id.ll_download);
		dialog_download.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String status = Environment.getExternalStorageState();

				if (!Environment.MEDIA_MOUNTED.equals(status)) {
					// 表示SD卡未挂载

					Toast.makeText(FormList.this, "请检查手机存储设备的状态", 0).show();

					return;
				}

				if (!isNetworkAvailable(FormList.this)) {

					Toast.makeText(FormList.this, "请连接网络...", 0).show();

					requestCode = 1111;
					Intent wifiSettingIntent = new Intent(
							"android.settings.WIFI_SETTINGS");
					startActivityForResult(wifiSettingIntent, requestCode);

				} else {

					downLoad();

					Toast.makeText(FormList.this, "开始下载文件", 0).show();

				}

				dialog.dismiss();
			}
		});

		dialog_upload = (LinearLayout) window.findViewById(R.id.ll_upload);
		dialog_upload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (!isNetworkAvailable(FormList.this)) {

					Toast.makeText(FormList.this, "请连接网络...", 0).show();

					requestCode = 2222;
					Intent wifiSettingIntent = new Intent(
							"android.settings.WIFI_SETTINGS");
					startActivityForResult(wifiSettingIntent, requestCode);
				} else {
					
					uploadAnswer();

				}

				dialog.dismiss();
			}
		});
	}

	// 上传本地数据库的数据给服务器
	private void uploadAnswer() {

		Gson gsonB = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();

		List<RecordNonetWorkContent> userList = pDao.getAllUsers();
		for (RecordNonetWorkContent recordNonetWorkContent : userList) {
			List<AnswerNonetWorkContent> answerList = pDao
					.getAllAnswersByUserId(recordNonetWorkContent.Id + "");
			recordNonetWorkContent.MyAnswerList = answerList;
		}
		final String bb = gsonB.toJson(userList);

		Log.i("2016/11/4", "=上传的答案=" + bb);

		if (TextUtils.equals("[]", bb)) {

			Toast.makeText(this, "本地数据为空，请先答题", 0).show();

			return;
		}else{
			Toast.makeText(FormList.this, "上传答案中...", 0).show();
		}

		new Thread(

		new Runnable() {
			public void run() {
				
				OutputStream outStrm = null;
				
				try {
                    //byte[](字节数组)就是数据流
					byte[] myData = bb.getBytes();

					URL url = new URL(uploadUrl);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();

					conn.setConnectTimeout(5000);
					conn.setRequestMethod("POST");
					conn.setDoInput(true);
					conn.setDoOutput(true);
					conn.setUseCaches(false);

					conn.setRequestProperty("Content-Type",
							"application/x-www-form-urlencoded");
					conn.setRequestProperty("Content-Length",
							String.valueOf(myData.length));
					outStrm = conn.getOutputStream();
					outStrm.write(myData);
					outStrm.flush();		

					int code = conn.getResponseCode();

					if (200 == code) {
						
						Log.i("2016/11/9", "连接成功");

						if (TextUtils.equals(
								"true",
								changeInputStream(conn.getInputStream(),
										"utf-8"))) {

							Message msg = Message.obtain();

							msg.what = SUCCESS;

							mHandler.sendMessage(msg);

							// setSign(bb);
							// 删除表
							pDao.dropPinfo();
							pDao.dropAinfo();
							// 创建表
							pDao.createPinfo();
							pDao.createAinfo();
						} else {

							Message msg = Message.obtain();

							msg.what = FAIL;

							mHandler.sendMessage(msg);

						}
					} else {

						Message msg = Message.obtain();

						msg.what = FAIL;

						mHandler.sendMessage(msg);

					}

				} catch (Exception e) {

					e.printStackTrace();

					Message msg = Message.obtain();

					msg.what = ERROR;

					mHandler.sendMessage(msg);
				} finally{
					
					try {
						outStrm.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}

		}

		).start();

	}

	private String changeInputStream(InputStream inputStream, String encode) {
		// 内存流
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		String result = null;
		if (inputStream != null) {
			try {
				while ((len = inputStream.read(data)) != -1) {
					byteArrayOutputStream.write(data, 0, len);
				}
				result = new String(byteArrayOutputStream.toByteArray(), encode);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		Log.i("2016/11/10", "result=" + result);
		// result如果返回false就不用管
		// 返回true就要清除数据
		return result;
	}

	public void noNetWorkGroupList(int masterId) {
		itemList1.clear();

		for (int i = 0; i < mList.size(); i++) {
			FormatDetailedQuestionModel.ParentModel parentModel = mList.get(i);
			if (parentModel.getMasterId() == masterId) {
				itemList1.add(parentModel);
			}
		}
		for (int i = 0; i < itemList1.size(); i++) {
			itemList1.get(i).setDataIndex(i);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver();
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case 1111:

			downLoad();

			//Toast.makeText(FormList.this, "开始下载文件", 0).show();

			break;

		case 2222:

			uploadAnswer();

			break;

		default:
			break;
		}

	}

	@Override
	public void onRefresh() {
		flAdapter = null;
		getListData();
		noNetAdapter = null;

		if (downLoadData != null) {

			temp = downLoadData.split("]");

			downLoadJson = temp[1] + "]";

			getNonetData(downLoadJson);

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			
			showBackDialog();
			
		}
		
		return super.onKeyDown(keyCode, event);
	}

	private void showBackDialog() {
		
		final AlertDialog dialog=new AlertDialog.Builder(this).create();
		
		View view=LayoutInflater.from(this).inflate(R.layout.back_dialog_layout,null);
		dialog.setView(view);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		Window window=dialog.getWindow();
		window.setContentView(R.layout.back_dialog_layout);
		TextView tv_dialog=(TextView) window.findViewById(R.id.back_tv_dialog);
		tv_dialog.setText("您要确定返回吗？");
		Button btnOk=(Button) window.findViewById(R.id.back_btn_ok);
		btnOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				finish();
			}
		});
		Button btnUndo=(Button) window.findViewById(R.id.back_btn_undo);
		btnUndo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}
	
}

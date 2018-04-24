package com.fc.first.views;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.ZoomDensity;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fc.company.beans.CompanyTask;
import com.fc.first.beans.FirstMeetingAdapter;
import com.fc.first.beans.FirstWorkTongZhiAdapter;
import com.fc.first.beans.GetJobs;
import com.fc.first.beans.GetMsgBoardMaster;
import com.fc.first.beans.GetNews;
import com.fc.first.beans.GetNewsAdapter;
import com.fc.first.beans.GetUsrInformation;
import com.fc.first.beans.JobsAdapter;
import com.fc.first.beans.LocationInformation;
import com.fc.first.beans.MsgBoardMasterAdapter;
import com.fc.first.beans.PendWorkAdapter;
import com.fc.first.beans.PendingWorkInformation;
import com.fc.invite.views.InviteJobDetailActivity;
import com.fc.invite.views.InviteJobListActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.fc.main.service.MainService;
import com.fc.main.service.PersonService;
import com.fc.main.tools.CrashApplication;
import com.fc.main.tools.HttpUrls_;
import com.fc.main.views.MainPageActivity;
import com.test.zbetuch_news.R;
import com.fc.meetingpost.beans.MeetingInfo;
import com.fc.meetingpost.views.MeetingListActivity;
import com.fc.meetingpost.views.MeetingListDetailActivity;
import com.fc.person.beans.PersonTask;
import com.fc.work.bean.WorkTongzhiBean;
import com.fc.work.views.WorkTongZhiActivity;
import com.fc.work.views.WorkTongzhiDetail;

public class FirstPageActivity extends Activity implements OnClickListener,
		IActivity {

	public static double GPSLOCATION_LAT = 0;
	public static double GPSLOCATION_LNG = 0;
	public static double GPSLOCATION_ALT = 0;
	
	public static String DiaoChaYuan;
	// /** Handler What加载数据完毕 **/
	// private static final int WHAT_DID_LOAD_DATA = 0;
	// /** Handler What更新数据完毕 **/
	// private static final int WHAT_DID_REFRESH = 1;
	// /** Handler What更多数据完毕 **/
	// private static final int WHAT_DID_MORE = 2;
	public static Date gps_date = new Date();
	private Button bt_personal_message, btn_mainimage;
	private Button tv_firstpage_more1, tv_firstpage_more2, tv_firstpage_more3,
			tv_firstpage_more4;
	ListView pendwork_list, getnews_list, msgboardmaster_list, jobs_list;
	TextView tv_jobnumberinput, tv_officername;

	LocationManager locationManager;
	Location location;
	TextView tv_nowlocation, latLocationText, lngLocationText, altLocationText;
	private PendWorkAdapter pendworkadapter;
	private GetNewsAdapter getnewsadapter;
	private MsgBoardMasterAdapter msgboardmasteradapter;
	private JobsAdapter jobsadapter;
	private FirstMeetingAdapter meetingAdapter;
	int page = 0;
	int rows = 0;
	Bitmap adminpic;
	ImageView imgvw_ownphoto;
	private ArrayList<LocationInformation> locationinfoJson = new ArrayList<LocationInformation>();
	private ArrayList<PendingWorkInformation> pendworklist = new ArrayList<PendingWorkInformation>();
	private ArrayList<GetMsgBoardMaster> msgboardmasterlist = new ArrayList<GetMsgBoardMaster>();
	private ArrayList<GetNews> newslist = new ArrayList<GetNews>();
	private ArrayList<GetJobs> jobslist = new ArrayList<GetJobs>();
	private ArrayList<MeetingInfo> meetlist = new ArrayList<MeetingInfo>();

	private List<WorkTongzhiBean> workInfoList = new ArrayList<WorkTongzhiBean>();
	private ProgressDialog progressDialog;

	private FirstWorkTongZhiAdapter workaAdapter;

	public static final int REFRESH_PENDWORK = 1;
	public static final int REFRESH_NEWSINFO = 2;
	public static final int REFRESH_MSGBOARD = 3;
	public static final int REFRESH_JOBS = 4;
	public static final int REFRESH_STAFF = 5;
	public static final int REFRESH_STAFFIMG = 6;
	public static final int REFRESH_MEETING = 7;
	public static final int REFRESH_WORK = 8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		showDialog(FirstPageActivity.this);
		setContentView(R.layout.activity_first);

		init();
		initView();
		initViewListener();
		initData();
		initListener();

		// 获取GPS定位经纬高信息
		GPSLocation();
		// 按钮监听进入工作目录
		bt_personal_message.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FirstPageActivity.this,
						MainPageActivity.class);
				startActivity(intent);
				// finish();
			}
		});

		// 发送本机app和film
		CompanyTask task = new CompanyTask(
				CompanyTask.SECURITY_FIRSTPAGEACTIVITY_SENDAPP, null);
		CompanyService.newTask(task);
	}

	private void initView() {
		bt_personal_message = (Button) findViewById(R.id.bt_personal_message);
		latLocationText = (TextView) findViewById(R.id.tv_latlocation);
		lngLocationText = (TextView) findViewById(R.id.tv_lnglocation);
		altLocationText = (TextView) findViewById(R.id.tv_altlocation);
		imgvw_ownphoto = (ImageView) findViewById(R.id.imgvw_ownphoto);

		tv_firstpage_more1 = (Button) findViewById(R.id.tv_firstpage_more1);
		tv_firstpage_more2 = (Button) findViewById(R.id.tv_firstpage_more2);
		tv_firstpage_more3 = (Button) findViewById(R.id.tv_firstpage_more3);
		tv_firstpage_more4 = (Button) findViewById(R.id.tv_firstpage_more4);
		btn_mainimage = (Button) findViewById(R.id.btn_mainimage);

		pendwork_list = (ListView) findViewById(R.id.listView1);
		getnews_list = (ListView) findViewById(R.id.listView3);
		msgboardmaster_list = (ListView) findViewById(R.id.listView2);
		jobs_list = (ListView) findViewById(R.id.listView4);

		tv_jobnumberinput = (TextView) findViewById(R.id.tv_jobnumberinput);
		tv_officername = (TextView) findViewById(R.id.tv_officername);
	}

	private void initViewListener() {
		tv_firstpage_more1.setOnClickListener(this);
		tv_firstpage_more2.setOnClickListener(this);
		tv_firstpage_more3.setOnClickListener(this);
		tv_firstpage_more4.setOnClickListener(this);
		btn_mainimage.setOnClickListener(this);
	}

	private void initData() {
		// 得到登录人员信息
		CompanyTask task5 = new CompanyTask(
				CompanyTask.FIRSTPAGEACTIVITY_GET_STAFF, null);
		CompanyService.newTask(task5);

		// 刷新会议通知
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("State", "true");
		data.put("page", "0");
		data.put("rows", "3");
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.FIRST_FIRSTPAGEACTIVITY_GET_MEETING_MASTER, params);
		CompanyService.newTask(task);

		// 刷新工作通知
		Map<String, Object> params3 = new HashMap<String, Object>();
		Map<String, String> data3 = new HashMap<String, String>();
		data3.put("page", "0");
		data3.put("rows", "3");
		params3.put("data", data3);
		PersonTask task3 = new PersonTask(
				PersonTask.FIRST_FIRSTPAGEACTIVITY_GET_WORK, params3);
		PersonService.newTask(task3);

		// // 刷新留言板
		// Map<String, Object> params7 = new HashMap<String, Object>();
		// Map<String, String> data7 = new HashMap<String, String>();
		// data7.put("page", "" + 0);
		// data7.put("rows", "" + 3);
		// params3.put("data", data7);
		// CompanyTask task7 = new CompanyTask(
		// CompanyTask.FIRSTPAGEACTIVITY_GET_MSGBOARD, params7);
		// CompanyService.newTask(task7);

		// 刷新工作列表
		Map<String, Object> params4 = new HashMap<String, Object>();
		Map<String, String> data4 = new HashMap<String, String>();
		data4.put("page", "" + 0);
		data4.put("rows", "" + 3);
		params4.put("data", data4);
		CompanyTask task4 = new CompanyTask(
				CompanyTask.FIRSTPAGEACTIVITY_GET_JOBS, params4);
		CompanyService.newTask(task4);

		// 刷新近期热点

		Map<String, Object> params2 = new HashMap<String, Object>();
		Map<String, String> data2 = new HashMap<String, String>();
		data2.put("page", "" + 0);
		data2.put("rows", "" + 3);
		params2.put("data", data2);
		CompanyTask task2 = new CompanyTask(
				CompanyTask.FIRSTPAGEACTIVITY_GET_NEWSINFO, params2);
		CompanyService.newTask(task2);

		// 刷新登录人员照片
		CompanyTask task6 = new CompanyTask(
				CompanyTask.FIRSTPAGEACTIVITY_GET_STAFFIMG, null);
		CompanyService.newTask(task6);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_firstpage_more1:
			Intent intent1 = new Intent(FirstPageActivity.this,
					MeetingListActivity.class);
			startActivity(intent1);
			break;

		case R.id.tv_firstpage_more2:
			Intent intent2 = new Intent(FirstPageActivity.this,
					WorkTongZhiActivity.class);
			startActivity(intent2);
			break;
		case R.id.tv_firstpage_more3:
			Intent intent3 = new Intent(FirstPageActivity.this,
					GetNewsListActivity.class);
			startActivity(intent3);
			break;
		case R.id.tv_firstpage_more4:
			Intent intent4 = new Intent(FirstPageActivity.this,
					InviteJobListActivity.class);
			intent4.putExtra("flag", 10);
			startActivity(intent4);
			break;
		case R.id.btn_mainimage:
			Intent intent5 = new Intent(FirstPageActivity.this,
					MainPageActivity.class);
			startActivity(intent5);
		default:

			break;
		}
	}

	/*
	 * 功能：GPS定位服务
	 * 
	 * @author clf
	 * 
	 * @since 2013/5/21
	 */
	public void GPSLocation() {

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(true);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String provider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider);

		updateView(location);

		// 判断GPS是否可用
		// System.out.println("state="+locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
		// 一秒为 1*1000
		locationManager.requestLocationUpdates(provider, 120 * 1000, 10,
				new LocationListener() {

					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						// location为变化完的新位置，更新显示
						updateView(location);
					}

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub
						updateView(locationManager
								.getLastKnownLocation(provider));

					}

					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub
						updateView(null);

					}

				});
	}

	public static String GetGpsInfo() {
		Date now = new Date();
		Date after = gps_date;
		after.setHours(after.getHours() + 1);
		// after.setMinutes((after.getMinutes()+5));
		String Gps = "";
		if ((after.after(now))) {
			Gps = String.valueOf(GPSLOCATION_LNG) + ","
					+ String.valueOf(GPSLOCATION_LAT);
			// Gps=GPSLOCATION_LAT.

		}
		return Gps;
	}

	private void updateView(Location location) {
		// TODO Auto-generated method stub
		String latString;
		String lngString;
		String altString;
		LocationInformation locationInfo = new LocationInformation();

		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			double alt = location.getAltitude();
			double hgt = alt / 3.2808398950131;
			BigDecimal b = new BigDecimal(hgt);
			double hgt1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			GPSLOCATION_LAT = lat;
			GPSLOCATION_LNG = lng;
			GPSLOCATION_ALT = hgt1;

			gps_date = new Date();

			latString = "经度：" + lng;
			lngString = "纬度：" + lat;
			altString = "高度：" + hgt1 + "米";

			latLocationText.setText(latString);
			lngLocationText.setText(lngString);
			altLocationText.setText(altString);
			locationInfo.setLongitude(lng);
			locationInfo.setLatitude(lat);
			locationInfo.setHeight(hgt1);
			locationinfoJson.add(locationInfo);
			String postJson = HttpUrls_.postJson1(locationinfoJson);
			Log.i("发送的json信息。。。。", postJson);

		} else {
			lngString = "无法获取地理信息";
			latLocationText.setText(lngString);
		}
	}

	// 提示框消失处理
	public void DismissDialog() {
		if (FirstPageActivity.this != null
				&& !FirstPageActivity.this.isFinishing()
				&& progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	private void initListener() {
		pendwork_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// LayoutInflater inflater = LayoutInflater
				// .from(FirstPageActivity.this);
				// // 加载数据
				// PendingWorkInformation list_position_data = pendworklist
				// .get(position);
				// // 加载要加载的布局
				// View inflater_view = inflater.inflate(
				// R.layout.activity_pendwork, null);
				// Builder builder = new AlertDialog.Builder(
				// FirstPageActivity.this);
				// builder.setView(inflater_view);
				// System.out.println(pendworklist.get(position));
				// // 标题
				// TextView tv_pendwork_title = (TextView) inflater_view
				// .findViewById(R.id.tv_pendwork_title);
				// // 开始时间-----至结束时间
				// TextView tv_pendwork_starttime = (TextView) inflater_view
				// .findViewById(R.id.tv_pendwork_starttime);
				// TextView tv_pendwork_endtime = (TextView) inflater_view
				// .findViewById(R.id.tv_pendwork_endtime);
				// // 显示日志内容
				// // TextView tv_pendwork_doc = (TextView) inflater_view
				// // .findViewById(R.id.tv_pendwork_doc);
				// WebView tv_pendwork_doc = (WebView) inflater_view
				// .findViewById(R.id.tv_pendwork_doc);
				// initWebView(tv_pendwork_doc);
				// tv_pendwork_doc.loadUrl(HttpUrls_.HttpURL
				// + "/Json/Get_PendingWork_ID.aspx?ID="
				// + list_position_data.getId());
				// // 当前时间
				// TextView tv_pendwork_currentlytime = (TextView) inflater_view
				// .findViewById(R.id.tv_pendwork_currentlytime);
				// // pendwork_title标题
				// String pendwork_title = list_position_data.getTitle();
				// // 开始时间
				// String start_time = list_position_data.getStart_Time();
				// String end_time = list_position_data.getEnd_Time();
				//
				// // 转换时间
				// SimpleDateFormat format = new SimpleDateFormat(
				// "yyyy-MM-dd HH:mm:ss");
				// // 获取系统当前时间
				// String nowTime = format.format(new Date());
				// start_time = start_time.replaceFirst("T", " ");
				// end_time = end_time.replace("T", " ");
				// // 转换开始时间
				// // 内容
				// String pendwork_content = list_position_data.getDoc();
				// // 设值
				// // tv_pendwork_doc.setText(pendwork_content);
				// tv_pendwork_title.setText(pendwork_title);
				// tv_pendwork_currentlytime.setText(nowTime);
				// tv_pendwork_starttime.setText(start_time);
				// tv_pendwork_endtime.setText(end_time);
				//
				// builder.show();

				Intent intent = new Intent(FirstPageActivity.this,
						MeetingListDetailActivity.class);
				intent.putExtra("meetInfo", meetlist.get(position));
				startActivity(intent);

			}

		});

		getnews_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				LayoutInflater inflater = LayoutInflater
						.from(FirstPageActivity.this);
				// 加载数据
				GetNews list_position_data = newslist.get(position);
				View inflater_view = inflater.inflate(
						R.layout.activity_getnews, null);
				// 加载要加载的布局
				Builder builder = new AlertDialog.Builder(
						FirstPageActivity.this);
				builder.setView(inflater_view);
				System.out.println(newslist.get(position));
				// 标题
				TextView tv_getnews_title = (TextView) inflater_view
						.findViewById(R.id.tv_getnews_title);
				// 显示日志内容
				TextView tv_getnews_doc = (TextView) inflater_view
						.findViewById(R.id.tv_getnews_doc);
				// 当前时间
				TextView tv_getnews_currentlytime = (TextView) inflater_view
						.findViewById(R.id.tv_getnews_currentlytime);
				// pendwork_title标题
				String pendwork_title = list_position_data.getTitle();
				// 转换时间
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				// 获取系统当前时间
				String nowTime = format.format(new Date());
				// 内容
				String pendwork_content = list_position_data.getDoc();
				// 设值
				tv_getnews_doc.setText(pendwork_content);
				tv_getnews_title.setText(pendwork_title);
				tv_getnews_currentlytime.setText(nowTime);
				builder.show();
			}
		});

		msgboardmaster_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(FirstPageActivity.this,
						WorkTongzhiDetail.class);
				intent.putExtra("WorkTongzhiBean",
						(Serializable) workInfoList.get(position));
				startActivity(intent);
				// Intent intent = new Intent(FirstPageActivity.this,
				// MsgboarddetitleActivity.class);
				// String masterid = msgboardmasterlist.get(position).getId();
				// intent.putExtra("masterid", masterid);
				// Intent intentid = new Intent(FirstPageActivity.this,
				// TrendsActivity.class);
				// String postid = msgboardmasterlist.get(position).getId();
				// intentid.putExtra("id", postid);
				// startActivity(intent);
			}

		});

		jobs_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String jobno;
				jobno = jobslist.get(position).getJobno();
				Intent intent = new Intent(FirstPageActivity.this,
						InviteJobDetailActivity.class);
				intent.putExtra("id", jobno);
				startActivity(intent);
			}

		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			// if (System.currentTimeMillis() - exittime > 2000) {
			// Toast.makeText(FirstPageActivity.this, " 再按一次退出！",
			// Toast.LENGTH_SHORT).show();
			// exittime = System.currentTimeMillis();
			// } else {
			// Intent service = new Intent("com.fc.main.service.MainService");
			// stopService(service);
			// Intent service2 = new Intent(
			// "com.fc.person.service.PersonService");
			// stopService(service2);
			// Intent service3 = new Intent(
			// "com.fc.company.service.CompanyService");
			// stopService(service3);
			// finish();
			// System.exit(0);
			//
			// }

			AlertDialog.Builder builder = new Builder(FirstPageActivity.this);
			builder.setTitle("关闭提示")
					.setMessage("您确定要关闭此程序吗?")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setCancelable(true)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
									Intent service2 = new Intent(
											"com.fc.company.newservice.CompanyService");
									stopService(service2);
									Intent service3 = new Intent(
											"com.fc.person.newservice.PersonService");
									stopService(service3);
									Intent service = new Intent(
											"com.fc.main.newservice.MainService");
									stopService(service);
									manager.restartPackage("com.fc.main.service.CompanyService");
									manager.restartPackage("com.fc.main.service.PersonService");
									manager.restartPackage("com.fc.main.service.MainService");
									finish();
									System.exit(0);
								}
							}).setNegativeButton("取消", null).show();
		}
		return false;
	}

	public void showDialog(Context context) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后!");

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		return super.onCreateDialog(id);
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
		PersonService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		DismissDialog();
		switch (Integer.valueOf(params[0].toString().trim())) {
		//
		case FirstPageActivity.REFRESH_MEETING:
			if (params[1] != null && !"".equals(params[1].toString().trim())) {
				String pendworkStr = params[1].toString();
				parseJsonToMeetList(pendworkStr);
			}
			break;
		// case FirstPageActivity.REFRESH_PENDWORK:
		// if (params[1] != null) {
		// String pendworkStr = params[1].toString();
		// // refreshPendworkList(pendworkStr);
		// }
		// break;

		//
		case FirstPageActivity.REFRESH_NEWSINFO:
			if (params[1].toString().trim() != null
					&& !"".equals(params[1].toString().trim())) {
				String newsStr = params[1].toString();
				refreshNewsList(newsStr);
			}
			break;
		// case FirstPageActivity.REFRESH_MSGBOARD:
		// if (params[1] != null) {
		// String msgboardStr = params[1].toString();
		// refreshMsgboardList(msgboardStr);
		// }
		// break;

		//
		case FirstPageActivity.REFRESH_JOBS:
			if (params[1] != null && !"".equals(params[1].toString().trim())) {
				String jobsStr = params[1].toString().trim();
				refreshJobsList(jobsStr);
			}
			break;

		//
		case FirstPageActivity.REFRESH_STAFF:
			if (params[1].toString().trim() != null
					&& !"".equals(params[1].toString().trim())) {
				String staffStr = params[1].toString();
				refreshStaff(staffStr);
			}
			break;

		//
		case FirstPageActivity.REFRESH_WORK:
			if (params[1].toString().trim() != null
					&& !"".equals(params[1].toString().trim())) {
				String msgboardStr = params[1].toString().trim();
				parseJsonToWorkList(msgboardStr);
			}
			break;
		case FirstPageActivity.REFRESH_STAFFIMG:
			if(params[1]!=null){
			if (params[1].toString().trim() != null) {
				byte[] data = (byte[]) params[1];
				if (data.length > 0) {
					Bitmap map = BitmapFactory.decodeByteArray(data, 0,
							data.length);
					imgvw_ownphoto.setImageBitmap(map);
				}
			}
			break;
		}
		}
	}

	/**
	 * 
	 * @param 会议通知JSON转化为MeetingInfo集合
	 * @return
	 */
	private void parseJsonToMeetList(String meetStr) {
		meetlist.clear();
		JSONArray jsonarray;
		try {
			jsonarray = new JSONArray(meetStr);
			int len = jsonarray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonarray.getJSONObject(i);
				MeetingInfo info = new MeetingInfo();
				info.setId(Integer.valueOf(object.getString("ID")));
				info.setTitle(object.getString("TITLE"));
				info.setDoc(object.getString("DOC"));
				info.setMeetingTime(object.getString("MEETING_TIME"));
				info.setMeetingAdd(object.getString("MEETING_ADD"));
				info.setCreateStaff(Integer.valueOf(object
						.getString("CREATE_STAFF")));
				info.setCreateDate(object.getString("CREATE_DATE"));
				info.setRecordCount(Integer.valueOf(object
						.getString("RecordCount")));
				info.setCreateStaffName(object.getString("CREATE_STAFF_NAME"));
				info.setMeetingStaff(object.getString("METTING_STAFFS"));
				meetlist.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		meetingAdapter = new FirstMeetingAdapter(this, meetlist);
		pendwork_list.setAdapter(meetingAdapter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
		PersonService.allActivity.remove(this);
	}

	// /**
	// * 刷新未完成工作列表
	// *
	// * @param pendworkStr
	// */
	// private void refreshPendworkList(String pendworkStr) {
	// pendworklist.clear();
	// try {
	// JSONArray jsonarray = new JSONArray(pendworkStr);
	// int len = jsonarray.length();
	// for (int i = 0; i < len; i++) {
	// JSONObject object = jsonarray.getJSONObject(i);
	// String id = object.getString("ID");
	// String start_Time = object.getString("START_TIME");
	// String end_Time = object.getString("END_TIME");
	// String title = object.getString("TITLE");
	// String doc = object.getString("DOC");
	// String work_Staff = object.getString("WORK_STAFF");
	// String create_Staff = object.getString("CREATE_STAFF");
	// String create_Time = object.getString("CREATE_TIME");
	// String type = object.getString("TYPE");
	// String update_Time = object.getString("UPDATE_TIME");
	// String max = object.getString("Max");
	// PendingWorkInformation pendworkInfo = new PendingWorkInformation();
	// pendworkInfo.setId(id);
	// pendworkInfo.setStart_Time(start_Time);
	// pendworkInfo.setEnd_Time(end_Time);
	// pendworkInfo.setTitle(title);
	// pendworkInfo.setDoc(doc);
	// pendworkInfo.setWork_Staff(work_Staff);
	// pendworkInfo.setCreate_Staff(create_Staff);
	// pendworkInfo.setCreate_Time(create_Time);
	// pendworkInfo.setType(type);
	// pendworkInfo.setUpdate_time(update_Time);
	// pendworkInfo.setMax(max);
	// pendworklist.add(pendworkInfo);
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// pendworkadapter = new PendWorkAdapter(this, pendworklist);
	// pendwork_list.setAdapter(pendworkadapter);
	// }

	/**
	 * 刷新近期热点
	 * 
	 * @param newsStr
	 */
	private void refreshNewsList(String newsStr) {
		newslist.clear();
		try {
			JSONArray jsonarray = new JSONArray(newsStr);
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
				newslist.add(newsInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		getnewsadapter = new GetNewsAdapter(this, newslist);
		getnews_list.setAdapter(getnewsadapter);

	}

	// /**
	// * 刷新留言板
	// *
	// * @param msgboardStr
	// */
	// private void refreshMsgboardList(String msgboardStr) {
	// msgboardmasterlist.clear();
	// try {
	// JSONArray jsonarray = new JSONArray(msgboardStr);
	// int len = jsonarray.length();
	// for (int i = 0; i < len; i++) {
	// JSONObject object = jsonarray.getJSONObject(i);
	// String id = object.getString("ID");
	// String title = object.getString("TITLE");
	// String create_Time = object.getString("CREATE_TIME");
	// String create_Staff = object.getString("CREATE_STAFF");
	// String update_Time = object.getString("UPDATE_TIME");
	// String update_Staff = object.getString("UPDATE_STAFF");
	// String staff = object.getString("Staff");
	// GetMsgBoardMaster msgboardmasterInfo = new GetMsgBoardMaster();
	// msgboardmasterInfo.setId(id);
	// msgboardmasterInfo.setTitle(title);
	// msgboardmasterInfo.setCreate_time(create_Time);
	// msgboardmasterInfo.setCreate_staff(create_Staff);
	// msgboardmasterInfo.setUpdate_time(update_Time);
	// msgboardmasterInfo.setUpdate_staff(update_Staff);
	// msgboardmasterInfo.setStaff(staff);
	// msgboardmasterlist.add(msgboardmasterInfo);
	// }
	// } catch (JSONException e) {
	// e.printStackTrace();
	// }
	// msgboardmasteradapter = new MsgBoardMasterAdapter(
	// FirstPageActivity.this, msgboardmasterlist);
	// msgboardmaster_list.setAdapter(msgboardmasteradapter);
	// }

	/**
	 * 刷新工作列表
	 * 
	 * @param jobsStr
	 */
	private void refreshJobsList(String jobsStr) {
		jobslist.clear();
		try {
			JSONArray jsonarray = new JSONArray(jobsStr);
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
				jobslist.add(jobsInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		jobsadapter = new JobsAdapter(FirstPageActivity.this, jobslist);
		jobs_list.setAdapter(jobsadapter);
	}

	/**
	 * 刷新登录人员信息
	 * 
	 * @param staffStr
	 */
	private void refreshStaff(String staffStr) {

		try {
			JSONObject object = new JSONObject(staffStr);

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
			String dept = object.getString("DEPT");

			MainService.STAFFID = id;
			MainService.STAFFNAME = name;

			GetUsrInformation userinfo = new GetUsrInformation();
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
			userinfo.setDept(dept);

			tv_officername.setText(userinfo.getDept());
			tv_jobnumberinput.setText(userinfo.getName());

			DiaoChaYuan=tv_jobnumberinput.getText().toString().trim();
			
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

	}

	/**
	 * 刷新工作通知
	 * 
	 * @param str
	 * @return
	 */
	private void parseJsonToWorkList(String str) {
		workInfoList.clear();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				WorkTongzhiBean bean = new WorkTongzhiBean();
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				bean.setID(jsonObject.getInt("ID"));
				bean.setTITLE(jsonObject.getString("TITLE"));
				bean.setDOC(jsonObject.getString("DOC"));
				bean.setNOTICE_TIME(jsonObject.getString("NOTICE_TIME"));
				bean.setCREATE_STAFF(jsonObject.getInt("CREATE_STAFF"));
				bean.setCREATE_DATE(jsonObject.getString("CREATE_DATE"));
				bean.setUPDATE_STAFF(jsonObject.getInt("UPDATE_STAFF"));
				bean.setUPDATE_DATE(jsonObject.getString("UPDATE_DATE"));
				bean.setRecordCount(jsonObject.getInt("RecordCount"));
				bean.setCREATE_STAFF_NAME(jsonObject
						.getString("CREATE_STAFF_NAME"));
				workInfoList.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		workaAdapter = new FirstWorkTongZhiAdapter(FirstPageActivity.this,
				workInfoList);
		msgboardmaster_list.setAdapter(workaAdapter);
	}

	private void initWebView(WebView view) {
		view.getSettings().setJavaScriptEnabled(true);
		view.getSettings().setAllowFileAccess(true);
		view.getSettings().setPluginsEnabled(true);
		view.getSettings().setPluginState(PluginState.ON);
		view.getSettings().setSupportZoom(true);
		view.getSettings().setBuiltInZoomControls(true);
		view.getSettings().setDefaultZoom(ZoomDensity.MEDIUM);
	}
}

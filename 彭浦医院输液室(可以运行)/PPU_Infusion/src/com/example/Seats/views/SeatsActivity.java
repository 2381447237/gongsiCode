package com.example.Seats.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.Seats.beans.SeatsAdapter;
import com.example.Seats.beans.SeatsInfo;
import com.example.companytask.CompanyTask;
import com.example.helper.IActivity;
import com.example.ppu_infusion.LoginActivity;
import com.example.ppu_infusion.PeiyaoDetailActivity;
import com.example.ppu_infusion.R;
import com.example.services.MainService;
import com.main.tools.VibratorUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;

//这个页面的url:http://192.168.11.11:8088/json/vw_SeatsInfo.aspx?STAFFID=1

//http://192.168.11.11:8088/json/GetQrCode.aspx?QRCODE=20170103000008

//个人信息 ：http://192.168.0.83:8088/json/vw_InfusionOrdersInfo.aspx?STAFFID=1&CARDNO=20170607000028
//个人信息 ：http://192.168.11.11:8088/json/vw_InfusionOrdersInfo.aspx?STAFFID=1&CARDNO=H0208773X

//输液列表： http://192.168.11.11:8088/json/vw_SeatsInfo.aspx?STAFFID=1

//获取输液信息: http://192.168.0.83:8088/json/GetStart.aspx?STAFFID=1&PATIENTID=186

//http://192.168.11.11:8088/json/GetStart.aspx?STAFFID=1&PATIENTID=2

public class SeatsActivity extends Activity implements IActivity,
		OnItemClickListener, OnClickListener {
	public static final int SEATS = 1;
	private int STAFFID;
	private GridView gridView;
	private SeatsAdapter adapter;
	private List<SeatsInfo> infos = new ArrayList<SeatsInfo>();
	private DisplayMetrics dm;
	private Handler mHandler;
	private ProgressDialog dialog;
	private Context context = this;
	private Button btn,btn_peiyao;

	public static List<SeatsInfo> promptlist = new ArrayList<SeatsInfo>();

	private int nNo;// 发送通知的次数
//	private SharedPreferences sp;
//	private Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置屏幕无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置屏幕全屏
		hideStatusBar();
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_seats);
		Intent intent = getIntent();
		STAFFID = intent.getIntExtra("STAFFID", -1);

		init();
		initview();
		//sp = getSharedPreferences("count", Context.MODE_PRIVATE);
		// postMsg();
		showDialog();
		mHandler = new Handler();
		mHandler.post(r);
	}

	Runnable r = new Runnable() {

		@Override
		public void run() {
			postMsg();
			mHandler.postDelayed(this, 5000);
		}
	};

	public void initview() {
		gridView = (GridView) findViewById(R.id.gridview);
		btn = (Button) findViewById(R.id.bt_scanning);
		btn_peiyao=(Button) findViewById(R.id.bt_peiyao);
		gridView.setOnItemClickListener(this);
		btn_peiyao.setOnClickListener(this);
		btn.setOnClickListener(this);
	}

	// 输液列表
	public void postMsg() {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("STAFFID", STAFFID + "");
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.SEATSINFO, params);
		MainService.newTask(task);

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		MainService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		
		//Log.i("2017/6/8","refresh(Object... params)");
		
		// TODO Auto-generated method stub
		switch (Integer.valueOf(params[0].toString().trim())) {
		case SeatsActivity.SEATS:
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (params[1] != null) {
				String value = params[1].toString().trim();
				parseJsonList(value);

			}
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();	
		intent.putExtra("STAFFID", STAFFID);
		intent.putExtra("SeatsInfo", infos.get(position));
		intent.setClass(SeatsActivity.this, InfusionActivity.class);
		startActivityForResult(intent, 200);
	}

	@Override
	public void onClick(View v) {
	
		switch (v.getId()) {
		case R.id.bt_scanning:
			Intent intent = new Intent();
			intent.putExtra("STAFFID", STAFFID);
			intent.putExtra("Tag","SeatsActivity");
			intent.setClass(SeatsActivity.this, ScanActivity.class);
			// startActivityForResult(intent, 100);
			startActivity(intent);
			break;

		case R.id.bt_peiyao:
			
			Intent intent2=new Intent();
			intent2.putExtra("STAFFID", STAFFID);
			intent2.setClass(SeatsActivity.this,PeiyaoDetailActivity.class);
			startActivity(intent2);
			break;
			
		default:
			break;
		}
		
		
	}

	private void parseJsonList(String mtStr) {
       //  Log.i("2017/6/8","解析数据=============");
		infos.clear();
		// List<UnideInfo> list=new ArrayList<UnideInfo>();
		try {
			JSONArray jsonArray = new JSONArray(mtStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				SeatsInfo info = new SeatsInfo();
				info.setSEATNO(object.getString("SEATNO"));
				info.setSEATSTATUS(object.getString("SEATSTATUS"));
				info.setINFUSIONID(object.getString("INFUSIONID"));
				info.setPATIENTID(object.getString("PATIENTID"));
				info.setCARDNO(object.getString("CARDNO"));
				info.setPATIENTNAME(object.getString("PATIENTNAME"));
				info.setGENDER(object.getString("GENDER"));
				info.setAGE(object.getString("AGE"));
				info.setBAGCNTS(object.getString("BAGCNTS"));
				info.setCOMPLETEDBAGCNTS(object.getString("COMPLETEDBAGCNTS"));
				info.setTOTALDURATION(object.getString("TOTALDURATION"));
				info.setTOTALVOLUME(object.getString("TOTALVOLUME"));
				info.setINFUSIONGROUPNO(object.getInt("INFUSIONGROUPNO"));
				// info.setINFUSIONDETAILID(object.getString("INFUSIONDETAILID"));
				info.setREMAINDERRATE(object.getString("REMAINDERRATE"));
				info.setDRIPCNTSPERMINUTE(object.getString("DRIPCNTSPERMINUTE"));
				info.setTUBEID(object.getString("TUBEID"));
				info.setDRIPCNTSPERML(object.getString("DRIPCNTSPERML"));
				info.setTUBENAME(object.getString("TUBENAME"));
				info.setINFUSIONSTATUS(object.getString("INFUSIONSTATUS"));
				info.setINFUSIONSTATUSNAME(object.getString("INFUSIONSTATUSNAME"));
				info.setREMAINDERVOLUME(object.getString("REMAINDERVOLUME"));
				info.setREMAINDERTIME(object.getString("REMAINDERTIME"));

				if (info.getSEATSTATUS().equals("1")) {
					infos.add(info);	
			
					if(Integer.valueOf(info.getREMAINDERVOLUME())<10&&Integer.valueOf(info.getREMAINDERVOLUME())>-1){
						
					if (!promptlist.contains(info)) {
						promptlist.add(new SeatsInfo(info.getSEATNO(), info
								.getPATIENTID(), info.getCARDNO(), info
								.getPATIENTNAME(), info.getAGE()));
					}
	
					for (int a = nNo; a < promptlist.size(); a++) {
						
						showStatusBar();
						sendNotification(a);
					}
				}				
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		adapter = new SeatsAdapter(this, infos);
		gridView.setAdapter(adapter);
		
	}

	private void showDialog() {
		dialog = new ProgressDialog(context);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("正在查询，请稍等...");
		dialog.show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100) {// 扫描返回的结果
			if (resultCode == RESULT_OK) {
				String getValue = data.getStringExtra("extra");
				// Log.i("aaa", "getValue==" + getValue);
			}
		}
		if (requestCode == 200) {
			if (resultCode == RESULT_OK) {
				postMsg();
			}
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = new Builder(SeatsActivity.this);
			builder.setTitle("关闭提示")
					.setMessage("您确定要关闭此程序吗?")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setCancelable(true)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									mHandler.removeCallbacks(r);
									Intent intent = new Intent(
											"com.example.services.MainService");
									context.stopService(intent);
									finish();
								}
							}).setNegativeButton("取消", null).show();

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();

		hideStatusBar();

		if (LoginActivity.isPad(this)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}

	// 发送通知和震动
	private void sendNotification(int a) {
		//Log.i("2017/6/8","发送通知和震动+++++++++++++++");
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		if (LoginActivity.isPad(this)) {

			Notification notification = new NotificationCompat.Builder(this)
					.setContentTitle(
							"姓名:" + promptlist.get(a).getPATIENTNAME() + "，座位:"
									+ promptlist.get(a).getSEATNO())
					.setContentText(
							"姓名:" + promptlist.get(a).getPATIENTNAME() + "，座位:"
									+ promptlist.get(a).getSEATNO()
									+ "，即将完成输液!")
					.setWhen(System.currentTimeMillis())
					 .setSmallIcon(R.drawable.apknamem)
					.setDefaults(Notification.DEFAULT_ALL)
					.setPriority(NotificationCompat.PRIORITY_MAX).build();
			manager.notify(nNo, notification);

		} else {

			Notification notification = new NotificationCompat.Builder(this)
					.setContentTitle(
							"姓名:" + promptlist.get(a).getPATIENTNAME() + "，座位:"
									+ promptlist.get(a).getSEATNO())
					.setContentText(
							"姓名:" + promptlist.get(a).getPATIENTNAME() + "，座位:"
									+ promptlist.get(a).getSEATNO()
									+ "，即将完成输液!")
					.setWhen(System.currentTimeMillis())
					.setSmallIcon(R.drawable.apkname)
					.setLargeIcon(
							BitmapFactory.decodeResource(getResources(),
									R.drawable.apkname))
					.setDefaults(Notification.DEFAULT_ALL)
					.setPriority(NotificationCompat.PRIORITY_MAX).build();
			manager.notify(nNo, notification);
		}
		VibratorUtil.Vibrate(this, 1000);// 震动1000ms
		nNo++;

	}

	// 隐藏状态栏
	private void hideStatusBar() {
		WindowManager.LayoutParams attrs =getWindow().getAttributes();
		attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().setAttributes(attrs);
	}

	// 显示状态栏
	private void showStatusBar() {
		WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().setAttributes(attrs);
	}

}

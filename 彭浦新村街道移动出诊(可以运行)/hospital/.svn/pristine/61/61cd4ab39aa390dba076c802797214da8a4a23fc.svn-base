package com.fc.has.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.companytask.CompanyTask;
import com.example.hospital.R;
import com.example.service.MainService;
import com.fc.has.beans.HomeVisitNumInfo;
import com.fc.has.beans.HomeVisitNumInfoAdapter;
import com.fc.helper.IActivity;
import com.fc.unidentified.beans.UnideInfo;
import com.fc.unidentified.beans.UnidentifiedAdapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomeVisitNumInfoActivity extends Activity implements IActivity,OnItemClickListener{

	public static final int VISITNUMINFO_REFRESH=1;
	private Context context = this;
	private ProgressDialog dialog;
	private TextView tv_liebiao;
	private ListView lv_liebiao;
	private int mResId;
	private String PatientName;
	private HomeVisitNumInfoAdapter adapter;
	private ArrayList<HomeVisitNumInfo> list=new ArrayList<HomeVisitNumInfo>();
	public static HomeVisitNumInfoActivity _instance=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_visit_num_info);
		_instance=this;
		showDialog();
		Intent intent=getIntent();
		mResId=intent.getIntExtra("visit_info", -1);
		PatientName=intent.getStringExtra("PatientName");
		init();
		initView();
		initData();
	}
	
	private void initData() {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("mRegId",mResId+"");
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.VISITNUMINFO, params);
		MainService.newTask(task);
	}

	private void initView() {
		tv_liebiao=(TextView)findViewById(R.id.btn_chufangliebiao);
		lv_liebiao=(ListView)findViewById(R.id.lv_liebiao);
		lv_liebiao.setOnItemClickListener(this);
		tv_liebiao.setText(PatientName+"——处方列表");
	}

	@Override
	public void init() {
		MainService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString().trim())) {
		case HomeVisitNumInfoActivity.VISITNUMINFO_REFRESH:
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			String data="";
			if (params[1] != null) {
				data = params[1].toString().trim();
				parseJsonList(data);
			}
		}
	}

	private void parseJsonList(String data) {
		try {
			JSONArray array = new JSONArray(data);
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				HomeVisitNumInfo info=new HomeVisitNumInfo();
				info.setPrescribeId(object.getInt("PrescribeId"));
				info.setRegId(object.getInt("RegId"));
				info.setPatientId(object.getInt("PatientId"));
				info.setPrescribeDoctorId(object.getInt("PrescribeDoctorId"));
				info.setPrescribeDoctorName(object.getString("PrescribeDoctorName"));
				info.setPrescribeDate(object.getString("PrescribeDate"));
				info.setTotalCosts(object.getDouble("TotalCosts"));
				info.setIsPayed(object.getBoolean("IsPayed"));
				info.setIsPayedName(object.getString("IsPayedName"));
				info.setCardNo(object.getString("CardNo"));
				info.setPatientName(object.getString("PatientName"));
				info.setGender(object.getString("Gender"));
				info.setBirthDate(object.getString("BirthDate"));
				info.setPatientAddr(object.getString("PatientAddr"));
				info.setPatientAge(object.getInt("PatientAge"));
				list.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		adapter=new HomeVisitNumInfoAdapter(list, this);
		lv_liebiao.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent=new Intent(HomeVisitNumInfoActivity.this, HomeVisitPrescribeInfoActivity.class);
		intent.putExtra("mPrescribeId", list.get(position).getPrescribeId());
		intent.putExtra("PatientName", PatientName);
		intent.putExtra("mResId", mResId);
		startActivity(intent);
	}
	
	private void showDialog() {
		dialog = new ProgressDialog(context);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("正在查询，请稍等...");
		dialog.show();

	}
	
	public static  int getbackground1(int position) {
		
		if (position % 2 == 0) {
			return R.drawable.btn_selector1;// listview偶数项
		} else {
			return R.drawable.btn_selector2;
		}
	}
//	public static  int getbackground1(int position) {
//		
//		if (position % 2 == 0) {
//			return 0x6699ff;// listview偶数项
//		} else {
//			return 0xffffff;
//		}
//	}
}
//package com.fc.has.views;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import com.example.companytask.CompanyTask;
//import com.example.hospital.R;
//import com.example.service.MainService;
//import com.fc.has.beans.HomeVisitNumInfo;
//import com.fc.has.beans.HomeVisitNumInfoAdapter;
//import com.fc.has.beans.RefreshListView;
//import com.fc.helper.IActivity;
//import com.fc.unidentified.beans.UnideInfo;
//import com.fc.unidentified.beans.UnidentifiedAdapter;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.AdapterView.OnItemClickListener;
//
//public class HomeVisitNumInfoActivity extends Activity implements IActivity,OnItemClickListener{
//	
//	public static final int VISITNUMINFO_REFRESH=1;
//	private Context context = this;
//	private ProgressDialog dialog;
//	private TextView tv_liebiao;
//	private ListView lv_liebiao;
//	private int mResId;
//	private String PatientName;
//	private HomeVisitNumInfoAdapter adapter;
//	private ArrayList<HomeVisitNumInfo> list=new ArrayList<HomeVisitNumInfo>();
//	public static HomeVisitNumInfoActivity _instance=null;
//	private RefreshListView mPullListView;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_home_visit_num_info);
//		_instance=this;
//		showDialog();
//		Intent intent=getIntent();
//		mResId=intent.getIntExtra("visit_info", -1);
//		PatientName=intent.getStringExtra("PatientName");
//		init();
//		initView();
//		initData();
//	}
//	
//	private void initData() {
//		Map<String, String> data = new HashMap<String, String>();
//		Map<String, Object> params = new HashMap<String, Object>();
//		data.put("mRegId",mResId+"");
//		params.put("data", data);
//		CompanyTask task = new CompanyTask(CompanyTask.VISITNUMINFO, params);
//		MainService.newTask(task);
//	}
//	
//	private void initView() {
//		tv_liebiao=(TextView)findViewById(R.id.btn_chufangliebiao);
//		lv_liebiao=(ListView)findViewById(R.id.lv_liebiao);
//		lv_liebiao.setOnItemClickListener(this);
//		tv_liebiao.setText(PatientName+"——处方列表");
//	}
//	
//	@Override
//	public void init() {
//		MainService.addActivity(this);
//	}
//	
//	@Override
//	public void refresh(Object... params) {
//		switch (Integer.valueOf(params[0].toString().trim())) {
//		case HomeVisitNumInfoActivity.VISITNUMINFO_REFRESH:
//			if (dialog != null && dialog.isShowing()) {
//				dialog.dismiss();
//			}
//			String data="";
//			if (params[1] != null) {
//				data = params[1].toString().trim();
//				parseJsonList(data);
//			}
//		}
//	}
//	
//	private void parseJsonList(String data) {
//		try {
//			JSONArray array = new JSONArray(data);
//			for (int i = 0; i < array.length(); i++) {
//				JSONObject object = array.getJSONObject(i);
//				HomeVisitNumInfo info=new HomeVisitNumInfo();
//				info.setPrescribeId(object.getInt("PrescribeId"));
//				info.setRegId(object.getInt("RegId"));
//				info.setPatientId(object.getInt("PatientId"));
//				info.setPrescribeDoctorId(object.getInt("PrescribeDoctorId"));
//				info.setPrescribeDoctorName(object.getString("PrescribeDoctorName"));
//				info.setPrescribeDate(object.getString("PrescribeDate"));
//				info.setTotalCosts(object.getDouble("TotalCosts"));
//				info.setIsPayed(object.getBoolean("IsPayed"));
//				info.setIsPayedName(object.getString("IsPayedName"));
//				info.setCardNo(object.getString("CardNo"));
//				info.setPatientName(object.getString("PatientName"));
//				info.setGender(object.getString("Gender"));
//				info.setBirthDate(object.getString("BirthDate"));
//				info.setPatientAddr(object.getString("PatientAddr"));
//				info.setPatientAge(object.getInt("PatientAge"));
//				list.add(info);
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		adapter=new HomeVisitNumInfoAdapter(list, this);
//		lv_liebiao.setAdapter(adapter);
//	}
//	
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position,
//			long id) {
//		Intent intent=new Intent(HomeVisitNumInfoActivity.this, HomeVisitPrescribeInfoActivity.class);
//		intent.putExtra("mPrescribeId", list.get(position).getPrescribeId());
//		intent.putExtra("PatientName", PatientName);
//		intent.putExtra("mResId", mResId);
//		startActivity(intent);
//	}
//	
//	private void showDialog() {
//		dialog = new ProgressDialog(context);
//		dialog.setCanceledOnTouchOutside(false);
//		dialog.setMessage("正在查询，请稍等...");
//		dialog.show();
//		
//	}
//	
//	public static  int getbackground1(int position) {
//		
//		if (position % 2 == 0) {
//			return R.drawable.btn_selector1;// listview偶数项
//		} else {
//			return R.drawable.btn_selector2;
//		}
//	}
////	public static  int getbackground1(int position) {
////		
////		if (position % 2 == 0) {
////			return 0x6699ff;// listview偶数项
////		} else {
////			return 0xffffff;
////		}
////	}
//}

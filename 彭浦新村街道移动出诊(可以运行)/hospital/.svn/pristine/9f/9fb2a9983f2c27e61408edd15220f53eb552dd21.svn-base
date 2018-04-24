package com.fc.has.views;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.companytask.CompanyTask;
import com.example.hospital.HomePageActivity;
import com.example.hospital.R;
import com.example.hospital.R.id;
import com.example.hospital.R.layout;
import com.example.service.MainService;
import com.fc.has.beans.HomeVisitPrescribeAdapter;
import com.fc.has.beans.HomeVisitPrescribeInfo;
import com.fc.helper.CustomApplication;
import com.fc.helper.IActivity;
import com.fc.unidentified.beans.ItemsInfo;
import com.fc.unidentified.beans.ItemsInfoAdapter;
import com.fc.unidentified.beans.UnideInfo;
import com.fc.unidentified.beans.UnidentifiedAdapter;
import com.fc.unidentified.views.PrescriptionActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class HomeVisitPrescribeInfoActivity extends Activity implements
		IActivity,OnClickListener {
	public static final int HOMEVISITVRESCRIBEINFO_REFRESH = 1;
	public static final int DELETEHOMEVISITVRESCRIBEINFO_REFRESH = 2;
	private UnideInfo info;
	//private String data;
	//private String message;
	private int mResId;
	private HomeVisitPrescribeAdapter adapter;
	private ListView listView;
	private Button btn_change;
	private int mPrescribeId=0 ;
	private String ItemName2;
	private String PatientName;
	private TextView tv_chufang;
	private ArrayList<ItemsInfo> itemsInfos = new ArrayList<ItemsInfo>();
	public static HomeVisitPrescribeInfoActivity _instance = null;
	private CustomApplication app;
	private String iteminfo = "";
	private Context context = this;
	private ProgressDialog dialog;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_visit_prescribe_info);
		_instance=this;
		showDialog();
		init();
		Intent intent = getIntent();
		mPrescribeId = intent.getIntExtra("mPrescribeId", -1);
		PatientName = intent.getStringExtra("PatientName");
		mResId = intent.getIntExtra("mResId",-1);
		// info=(UnideInfo) intent.getSerializableExtra("visit_info");
		initView();
		getVisitInfo();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.visit_info);
		btn_change=(Button)findViewById(R.id.btn_change);
		tv_chufang=(TextView)findViewById(R.id.btn_chufang);
		tv_chufang.setText(PatientName+"——处方信息");
		//listView.setOnItemLongClickListener(this);
		btn_change.setOnClickListener(this);
		app = (CustomApplication) getApplication();
	}

	private void getVisitInfo() {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("mPrescribeId",mPrescribeId +"");
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.HOMEVISITPRESCRIBEINFO,
				params);
		MainService.newTask(task);
	}

	@Override
	public void init() {
		MainService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString().trim())) {
		case HomeVisitPrescribeInfoActivity.HOMEVISITVRESCRIBEINFO_REFRESH:
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (params[1] != null) {
				String data = params[1].toString().trim();
				Log.i("qwj", "data=="+data);
				parseJsonList(data);
			}

		case HomeVisitPrescribeInfoActivity.DELETEHOMEVISITVRESCRIBEINFO_REFRESH:
			if (params[1] != null) {
				String message = params[1].toString().trim();
				Log.i("mess", message);
			}
		}
	}

	private void parseJsonList(String data2) {
		try {
			JSONArray array = new JSONArray(data2);
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = array.getJSONObject(i);
				ItemsInfo info = new ItemsInfo();
				info.setPrescribeId(object.getInt("PrescribeId"));
				info.setPrescribeDetailId(object.getInt("PrescribeDetailId"));
				info.setItemOrderId(object.getInt("ItemOrderId"));
				info.setItemId(object.getInt("ItemId"));
				info.setItemPropertyName(object.getString("ItemPropertyName"));
				info.setItemAmount(object.getInt("ItemAmount"));
				info.setItemOutpSpec(object.getString("ItemOutpSpec"));
				info.setItemName(object.getString("ItemName"));
				info.setItemFrequency(object.getString("ItemFrequency"));
				info.setItemDosage(object.getString("ItemDosage"));
				info.setItemUsage(object.getString("ItemUsage"));
				info.setItemPrice1(object.getDouble("ItemPrice"));
				info.setDoctorOrders(object.getString("DoctorOrders"));
				info.setItemClassName(object.getString("ItemClassName"));
				info.setIsDrug(object.getBoolean("IsDrug"));
				info.setItemDosageUnit(object.getString("ItemDosageUnit"));
				itemsInfos.add(info);
				Log.i("wocao", itemsInfos + "");

			}
			adapter = new HomeVisitPrescribeAdapter(this, itemsInfos);
			listView.setAdapter(adapter);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onClick(View v) {
		Intent intent=new Intent();
		intent.putExtra("mResId",mResId);
		intent.putExtra("ItemName2",ItemName2);
		intent.putExtra("PatientName",PatientName);
		intent.putExtra("itemsInfos",itemsInfos);
		intent.putExtra("mPrescribeId",mPrescribeId);
		
		intent.setClass(HomeVisitPrescribeInfoActivity.this, HomeVisitSearchActivity.class);
		Log.i("mPrescribeId", mPrescribeId+"");
		startActivity(intent);
	}
	
	private void showDialog() {
		dialog = new ProgressDialog(context);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("请稍等...");
		dialog.show();

	}

	
}

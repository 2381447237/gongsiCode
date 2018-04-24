package com.fc.unidentified.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.companytask.CompanyTask;
import com.example.hospital.R;
import com.example.service.MainService;
import com.fc.helper.BaseActivity;
import com.fc.helper.CustomApplication;
import com.fc.helper.IActivity;
import com.fc.unidentified.beans.DiagInfo;
import com.fc.unidentified.beans.DiagnosisAdapter;
import com.fc.unidentified.beans.ItemsInfo;

import com.fc.unidentified.beans.PrescriptionAdapter;
import com.fc.unidentified.views.PrescriptionActivity.MyReceiver;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.text.method.DigitsKeyListener;
import android.text.method.KeyListener;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DiagnosisActivity extends BaseActivity implements IActivity,
		OnClickListener {

	public static final int DIAG_INFOS = 1;
	private EditText ed_input;
	private Button btn_query, btn_query2, btn_query3, btn_stp, btn_str;
	public ListView listView;
	private DiagnosisAdapter adapter;
	public TextView tv_gun, tv_all;
	private List<DiagInfo> DiagInfo = new ArrayList<DiagInfo>();
	private Context context = this;
	private ProgressDialog dialog;
	private int mRegId;
	public static DiagnosisActivity _instance = null;
	// private MyReceiver2 receiver2 = null;
	private ItemsInfo itemsInfo;
	private List<ItemsInfo> itemsInfos = new ArrayList<ItemsInfo>();
	private CustomApplication app;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diagnosis);
		init();
		initView();
		_instance = this;
	}

	List<DiagInfo> Curr_DiagInfo = new ArrayList<DiagInfo>();
	public Boolean CheckDiagInfo(DiagInfo info) {
		return Curr_DiagInfo.contains(info);
	}
//删除诊断
	public void delDiagInfo(DiagInfo info) {
		 Log.i("qwj", "Curr_DiagInfo2=="+Curr_DiagInfo);
		 Log.i("qwj", "info2=="+info);
		for (int i = 0; i < Curr_DiagInfo.size(); i++) {
			if (Curr_DiagInfo.get(i).getDiagCode().equals(info.getDiagCode()))
				Curr_DiagInfo.remove(i);
			Log.i("qwj", "Curr_DiagInfo==" + Curr_DiagInfo);
			Log.i("qwj", "info==" + info);
		}
		// Curr_DiagInfo.remove(info);
	
		bindtext();
	}
//添加诊断
	public void addDiagInfo(DiagInfo info) {

		Curr_DiagInfo.add(info);
		Log.i("qwj", "Curr_DiagInfo=" + Curr_DiagInfo);
		Log.i("qwj", "info=" + info);
		bindtext();
	}

	public void bindtext() {
		String s = "";
		String t = "";
		for (DiagInfo info : Curr_DiagInfo) {
			s += info.getDiagName() + " ;";
			t += info.getDiagName() + "\n";
		}
		tv_gun.setText(s);
		tv_all.setText(t);

	}

	public void initView() {
		ed_input = (EditText) findViewById(R.id.ed_input);
		btn_query = (Button) findViewById(R.id.btn_query);
		btn_query2 = (Button) findViewById(R.id.btn_query2);
		btn_query3 = (Button) findViewById(R.id.btn_query3);
		btn_stp = (Button) findViewById(R.id.btn_stp);
		btn_str = (Button) findViewById(R.id.btn_str);
		listView = (ListView) findViewById(R.id.lsview_diag);
		tv_gun = (TextView) findViewById(R.id.tv_gun);
		tv_all = (TextView) findViewById(R.id.tv_all);
		tv_all.setMovementMethod(ScrollingMovementMethod.getInstance());

		Intent intent = getIntent();
		mRegId = intent.getIntExtra("mRegId", mRegId);
		btn_query.setOnClickListener(this);
		btn_stp.setOnClickListener(this);
		btn_str.setOnClickListener(this);
		btn_query2.setOnClickListener(this);
		btn_query3.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_query:
			if (!ed_input.getText().toString().trim().equals("")) {
				postDiag();
				showDialog();
				DecorView();
			} else {
				Toast.makeText(getApplicationContext(), "请输入输入码查询",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.btn_query2:
			tv_all.setVisibility(View.VISIBLE);
			btn_query2.setVisibility(View.GONE);
			btn_query3.setVisibility(View.VISIBLE);
			/*LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) listView
					.getLayoutParams(); // 取控件mGrid当前的布局参数
			linearParams.height = 650;// 当控件的高强制设成50象素
			listView.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件
*/			break;
		case R.id.btn_query3:
			tv_all.setVisibility(View.GONE);
			btn_query2.setVisibility(View.VISIBLE);
			btn_query3.setVisibility(View.GONE);
			/*LinearLayout.LayoutParams linearParams2 = (LinearLayout.LayoutParams) listView
					.getLayoutParams(); // 取控件mGrid当前的布局参数
			linearParams2.height = 900;// 当控件的高强制设成50象素
			listView.setLayoutParams(linearParams2); // 使设置好的布局参数应用到控件
*/
			break;
		case R.id.btn_stp:
			finish();
			// Toast.makeText(DiagnosisActivity.this,
			// tv_gun.getText().toString().trim(), Toast.LENGTH_LONG).show();
			break;
		case R.id.btn_str:
			if (!tv_gun.getText().toString().trim().equals("")) {

				String mDiagInfo = "";
				for (int i = 0; i < Curr_DiagInfo.size(); i++) {
					DiagInfo item = Curr_DiagInfo.get(i);
					mDiagInfo += item.getDiagCode() + ":" + item.getDiagName()
							+ "|";
				}

				Intent intent = new Intent();
				intent.putExtra("mRegId", mRegId);
				intent.putExtra("mDiagInfo", mDiagInfo);
				intent.setClass(DiagnosisActivity.this,
						PrescriptionActivity.class);
				startActivity(intent);

			} else {
				Toast.makeText(getApplicationContext(), "未选择疾病",
						Toast.LENGTH_SHORT).show();
			}

			break;
		default:
			break;
		}

	}
	//根据输入码获取诊断
	public void postDiag() {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("InputCode", ed_input.getText().toString().trim());
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.DIAGONSISINFO, params);
		MainService.newTask(task);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		MainService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.valueOf(params[0].toString().trim())) {
		case DiagnosisActivity.DIAG_INFOS:
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (params[1] != null) {
				String value = params[1].toString().trim();
				parseDiagList(value);
				if (value.equals("")) {
					Toast.makeText(DiagnosisActivity.this, "您查询的输入码有误",
							Toast.LENGTH_LONG).show();
				}

			}
			break;
		}
	}

	/*private void parseItemsList(String mstr) {
		// itemsInfos.clear();
		// List<UnideInfo> list=new ArrayList<UnideInfo>();
		try {
			// JSONArray jsonArray=new JSONArray(mtStr);
			// for(int i=0;i<itemsInfo.toString().length();i++){
			// JSONObject object = jsonArray.getJSONObject(i);
			ItemsInfo info = new ItemsInfo();
			info.setItemId(itemsInfo.getItemId());
			info.setItemName(itemsInfo.getItemName());
			info.setInputCode(itemsInfo.getInputCode());
			info.setInputCode1(itemsInfo.getInputCode1());
			info.setInputCode2(itemsInfo.getInputCode2());
			info.setItemOutpSpec(itemsInfo.getItemOutpSpec());
			info.setItemOutpUnits(itemsInfo.getItemOutpUnits());
			info.setItemBasicUnits(itemsInfo.getItemBasicUnits());
			info.setItemPrice1(itemsInfo.getItemPrice1());
			info.setItemPropertyIndicator(itemsInfo.getItemPropertyIndicator());
			info.setItemPropertyName(itemsInfo.getItemPropertyIndicator());
			info.setDrugOrItemIndicator(itemsInfo.getDrugOrItemIndicator());
			info.setDrugOrItemIndicatorName(itemsInfo
					.getDrugOrItemIndicatorName());
			info.setQuantity(itemsInfo.getQuantity());
			info.setItemClass(itemsInfo.getItemClass());
			info.setItemClassName(itemsInfo.getItemClassName());
			info.setCdname(itemsInfo.getCdname());
			info.setItemtradename(itemsInfo.getItemtradename());

			info.setNum(itemsInfo.getNum());
			info.setFreque(itemsInfo.getFreque());
			info.setUsage(itemsInfo.getUsage());
			info.setMuch(itemsInfo.getMuch());

			itemsInfos.add(info);

			// }
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
*/
	private void parseDiagList(String mtStr) {

		DiagInfo.clear();

		// List<UnideInfo> list=new ArrayList<UnideInfo>();
		try {
			JSONArray jsonArray = new JSONArray(mtStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				DiagInfo info = new DiagInfo();
				info.setDiagName(object.getString("DiagName"));
				info.setDiagCode(object.getString("DiagCode"));
				info.setInputCode(object.getString("InputCode"));
				info.setInputCode1(object.getString("InputCode1"));
				info.setInputCode2(object.getString("InputCode2"));
				DiagInfo.add(info);

				Log.i("qwj", "info.e()=" + DiagInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		adapter = new DiagnosisAdapter(this, DiagInfo);
		listView.setAdapter(adapter);

	}

	private void showDialog() {
		dialog = new ProgressDialog(context);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("正在查询，请稍等...");
		dialog.show();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			app = (CustomApplication) getApplication();
			app.setItemsinfo(null);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
}

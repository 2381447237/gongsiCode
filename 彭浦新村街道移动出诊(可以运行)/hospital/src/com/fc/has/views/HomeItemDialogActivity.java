package com.fc.has.views;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospital.R;
import com.fc.helper.BaseActivity;
import com.fc.unidentified.beans.FrequencyInfo;
import com.fc.unidentified.beans.ItemsInfo;
import com.fc.unidentified.beans.UsageInfo;
import com.fc.unidentified.views.ItemsInfosActivity;

public class HomeItemDialogActivity extends BaseActivity implements
		OnClickListener {
	private TextView tv_units, tv_spec, tv_ItemName;
	private EditText ed1_num, ed2_num;
	private LinearLayout liner;
	private Button btn_query;
	private int position;
	private Spinner spinner_frequ, spinner_usage;
	private ItemsInfo itemsInfos;
	private ArrayList<FrequencyInfo> freInfos = new ArrayList<FrequencyInfo>();
	private ArrayList<UsageInfo> usageInfos = new ArrayList<UsageInfo>();

	ArrayAdapter<FrequencyInfo> FrequencyAdapter;
	ArrayAdapter<UsageInfo> UsageAdapter;
	private String freque, usage;
	private String frequ_id;
	private String usage_id;
	private String much2;
	private String num2 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置屏幕无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置屏幕全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_items_dialog);

		Intent intent = getIntent();
		itemsInfos = (ItemsInfo) intent.getSerializableExtra("itemsInfos");
		freque = intent.getStringExtra("freque");
		usage = intent.getStringExtra("usage");
		position = intent.getIntExtra("position", -1);
		much2 = intent.getStringExtra("much2");
		num2 = intent.getStringExtra("num2");
		frequ_id = intent.getStringExtra("frequ_id");
		usage_id = intent.getStringExtra("usage_id");
		Log.i("qwj", "===much2===" + much2);
		Log.i("qwj", "===num2===" + num2);
		initView();

	}


		// tv_units.setText("");
	public void initView(){
		ed1_num=(EditText) findViewById(R.id.ed1_num);
		ed2_num=(EditText) findViewById(R.id.ed2_num);
		tv_ItemName=(TextView) findViewById(R.id.tv_ItemName);
		tv_spec=(TextView) findViewById(R.id.tv_spec);
		liner=(LinearLayout) findViewById(R.id.liner_fre);
		btn_query=(Button) findViewById(R.id.btn_query3);
		spinner_frequ=(Spinner) findViewById(R.id.spinner_frequ);
		spinner_usage=(Spinner) findViewById(R.id.spinner_usage);
		tv_units=(TextView) findViewById(R.id.tv_units);
		
		ed2_num.setFocusable(true);   
		ed2_num.setFocusableInTouchMode(true);   
		ed2_num.requestFocus();
		
		//tv_units.setText(itemsInfos.getItemDosageUnit().trim()); 
		tv_ItemName.setText(itemsInfos.getItemName().trim());
		tv_spec.setText(itemsInfos.getItemOutpSpec());
		if (itemsInfos.getItemAmount() != 0) {
			if (!itemsInfos.getIsDrug()) {
				liner.setVisibility(View.GONE);
			} else {
				tv_units.setText(itemsInfos.getItemDosageUnit());
			}
		} else {

			if (!itemsInfos.getDrugOrItemIndicatorName().equals("药品类")) {
				liner.setVisibility(View.GONE);
			} else {
				tv_units.setText(itemsInfos.getItemBasicUnits().trim());
			}
		}
		// 修改时的默认值
		if (num2 != null) {
			ed1_num.setText(much2);
			ed2_num.setText(num2);
			ed2_num.setSelection(num2.length()); 
		}
		btn_query.setOnClickListener(this);

		// 频率
		if (freque != null) {
			FrequencyList(freque);
		}
		FrequencyAdapter = new ArrayAdapter<FrequencyInfo>(this,
				android.R.layout.simple_spinner_item, freInfos);
		FrequencyAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_frequ.setAdapter(FrequencyAdapter);
		// 修改时的默认值
		if (frequ_id != null) {
			for (int i = 0; i < freInfos.size(); i++) {
				if (freInfos.get(i).getFreqDesc().equals(frequ_id)) {
					spinner_frequ.setSelection(i);
					Log.i("qwj", "===freInfos.get(i).getFreqDesc()==="
							+ freInfos.get(i).getFreqDesc());
				}
			}
		}
		Log.i("qwj", "===freque===" + frequ_id);
		if (freInfos.size() <= 3 && freInfos.size() > 1) {
			spinner_frequ.setSelection(1);
		}

		// 用法
		if (usage != null) {
			UsageList(usage);
		}
		UsageAdapter = new ArrayAdapter<UsageInfo>(this,
				android.R.layout.simple_spinner_item, usageInfos);
		UsageAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_usage.setAdapter(UsageAdapter);
		// 修改时的默认值
		if (usage_id != null) {
			for (int i = 0; i < usageInfos.size(); i++) {
				if (usageInfos.get(i).getPerformName().equals(usage_id)) {
					spinner_usage.setSelection(i);
					Log.i("qwj", "===freInfos.get(i).getFreqDesc()==="
							+ usageInfos.get(i).getPerformName());
				}
			}
		}
		if (usageInfos.size() <= 3 && usageInfos.size() > 1) {
			spinner_usage.setSelection(1);
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_query3:
			String much = ed1_num.getText().toString().trim();
			String num = ed2_num.getText().toString().trim();
			Intent intent = new Intent();
			if (num.equals("") || num.equals("0")) {
				Toast.makeText(getApplicationContext(), "请输入总量,并不能为0",
						Toast.LENGTH_LONG).show();

			} else if (itemsInfos.getItemAmount() != 0
					&& itemsInfos.getIsDrug() == true) {
				if (((FrequencyInfo) spinner_frequ.getSelectedItem())
						.getFreqId() == -1) {
					Toast.makeText(getApplicationContext(), "请选择频率",
							Toast.LENGTH_LONG).show();
				} else if (((UsageInfo) spinner_usage.getSelectedItem())
						.getPerformId() == -1) {
					Toast.makeText(getApplicationContext(), "请选择用法",
							Toast.LENGTH_LONG).show();
				} else if (much.equals("") || much.equals("0")) {
					Toast.makeText(getApplicationContext(), "请输入用量,并不能为0",
							Toast.LENGTH_LONG).show();
				}  else {

					intent.putExtra("itemsInfos", itemsInfos);
					intent.putExtra("spinner_frequ1", spinner_frequ
							.getSelectedItem().toString());
					intent.putExtra("spinner_usage1", spinner_usage
							.getSelectedItem().toString());
					intent.putExtra("much1", much);
					intent.putExtra("num1", num);
					intent.putExtra("position", position);
					intent.setAction("com.example.service.MainService");
					sendBroadcast(intent);
					// HomeItemsInfoActivity._instance.finish();
					DecorView();
					finish();
					Log.i("qwj", "==itemsInfos===" + itemsInfos);
				}

			} else if (itemsInfos.getItemAmount() != 0
					&& itemsInfos.getIsDrug() == false) {
				intent.putExtra("itemsInfos", itemsInfos);
				intent.putExtra("spinner_frequ1", "");
				intent.putExtra("spinner_usage1", "");
				intent.putExtra("much1", "");
				intent.putExtra("num1", num);
				intent.putExtra("position", position);
				intent.setAction("com.example.service.MainService");
				sendBroadcast(intent);
				// HomeItemsInfoActivity._instance.finish();
				DecorView();
				finish();
			} else if (itemsInfos.getDrugOrItemIndicatorName().equals("药品类")) {

				if (((FrequencyInfo) spinner_frequ.getSelectedItem())
						.getFreqId() == -1) {
					Toast.makeText(getApplicationContext(), "请选择频率",
							Toast.LENGTH_LONG).show();
				} else if (((UsageInfo) spinner_usage.getSelectedItem())
						.getPerformId() == -1) {
					Toast.makeText(getApplicationContext(), "请选择用法",
							Toast.LENGTH_LONG).show();
				} else if (much.equals("") || much.equals("0")) {
					Toast.makeText(getApplicationContext(), "请输入用量,并不能为0",
							Toast.LENGTH_LONG).show();
				}  else {

					intent.putExtra("itemsInfos", itemsInfos);
					intent.putExtra("spinner_frequ", spinner_frequ
							.getSelectedItem().toString());
					intent.putExtra("spinner_usage", spinner_usage
							.getSelectedItem().toString());
					intent.putExtra("much", much);
					intent.putExtra("num", num);
					intent.putExtra("position", position);
					intent.setAction("com.example.service.MainService");
					sendBroadcast(intent);
					HomeItemsInfoActivity._instance.finish();
					DecorView();
					finish();
					Log.i("qwj", "==itemsInfos===" + itemsInfos);
				}

			} else if (itemsInfos.getDrugOrItemIndicatorName().equals("非药品类")) {
				intent.putExtra("itemsInfos", itemsInfos);
				intent.putExtra("spinner_frequ", "");
				intent.putExtra("spinner_usage", "");
				intent.putExtra("much", "");
				intent.putExtra("num", num);
				intent.putExtra("position", position);
				intent.setAction("com.example.service.MainService");
				sendBroadcast(intent);
				HomeItemsInfoActivity._instance.finish();
				DecorView();
				finish();
			}
			// else {
			//
			// intent.putExtra("itemsInfos", itemsInfos);
			// intent.putExtra("spinner_frequ", "");
			// intent.putExtra("spinner_usage", "");
			// intent.putExtra("much", "");
			// intent.putExtra("num", num);
			// intent.putExtra("position", position);
			// intent.setAction("com.example.service.MainService");
			// sendBroadcast(intent);
			// HomeItemsInfoActivity._instance.finish();
			// finish();
			//
			// }

			break;

		default:
			break;
		}
	}

	private void FrequencyList(String mtStr) {
		freInfos.add(new FrequencyInfo(-1, "请选择", " -1"));
		try {
			JSONArray jsonArray = new JSONArray(mtStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				int FreqId = object.getInt("FreqId");
				String FreqDesc = object.getString("FreqDesc");
				String FreqCode = object.getString("FreqCode");
				// int position=object.getInt(i+"");
				FrequencyInfo info = new FrequencyInfo();
				info.setFreqId(FreqId);
				info.setFreqDesc(FreqDesc);
				info.setFreqCode(FreqCode);
				freInfos.add(info);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private void UsageList(String mtStr) {
		usageInfos.add(new UsageInfo(-1, "请选择"));
		try {
			JSONArray jsonArray = new JSONArray(mtStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				UsageInfo info = new UsageInfo();
				info.setPerformId(object.getInt("PerformId"));
				info.setPerformName(object.getString("PerformName"));
				usageInfos.add(info);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}

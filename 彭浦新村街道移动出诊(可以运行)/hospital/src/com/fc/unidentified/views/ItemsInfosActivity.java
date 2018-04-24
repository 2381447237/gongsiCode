package com.fc.unidentified.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.companytask.CompanyTask;
import com.example.hospital.R;
import com.example.hospital.R.layout;
import com.example.service.MainService;
import com.fc.helper.BaseActivity;
import com.fc.helper.CustomApplication;
import com.fc.helper.IActivity;
import com.fc.unidentified.beans.DiagInfo;
import com.fc.unidentified.beans.DiagnosisAdapter;
import com.fc.unidentified.beans.FrequencyInfo;
import com.fc.unidentified.beans.ItemsInfo;
import com.fc.unidentified.beans.ItemsInfoAdapter;
import com.fc.unidentified.beans.UnideInfo;
import com.fc.unidentified.beans.UnidentifiedAdapter;
import com.fc.unidentified.beans.UsageInfo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ItemsInfosActivity extends BaseActivity {
	// public static final int FREQUENCY_INFO = 1;
	// public static final int USAGE_INFO = 2;
	public ListView listView;
	private ItemsInfoAdapter adapter;
	private List<ItemsInfo> itemsInfos = new ArrayList<ItemsInfo>();
	// private List<FrequencyInfo> freInfos=new ArrayList<FrequencyInfo>();
	// private List<UsageInfo> usageInfos=new ArrayList<UsageInfo>();
	public static ItemsInfosActivity _instance = null;
	// private String freque,usage;

	private CustomApplication app;
	private String ItemName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_info);
		// init();
		initView();

		_instance = this;
	}

	public void initView() {
		// ed_input=(EditText) findViewById(R.id.ed_input2);
		// btn_query=(Button) findViewById(R.id.btn_query2);
		// btn_keep=(Button) findViewById(R.id.btn_keep);
		listView = (ListView) findViewById(R.id.lsview_drug);
		app = (CustomApplication) getApplication(); // 获取应用程序
		/*
		 * if(app.getFreque().equals("")&&app.getUsage().equals("")){
		 * Frequency(); Usage(); } else{ freque=app.getFreque();
		 * usage=app.getUsage(); } Log.i("qwj", "==us333333333=="+usage);
		 * Log.i("qwj", "==freque333333333=="+freque);
		 */
		Intent intent = getIntent();
		String value = intent.getStringExtra("value");
		ItemName = intent.getStringExtra("ItemName");
		parseItemsList(value);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (ItemName.contains(itemsInfos.get(position).getItemName()
						.toString().trim())) {
					Toast.makeText(getApplicationContext(), "该处方已存在",
							Toast.LENGTH_LONG).show();
				} else {
					Intent intent = new Intent(ItemsInfosActivity.this,
							ItemsDialogActivity.class);
					intent.putExtra("freque", app.getFreque());
					intent.putExtra("usage", app.getUsage());
					intent.putExtra("itemsInfos", itemsInfos.get(position));
					startActivity(intent);
				}
			}
		});
		// btn_query.setOnClickListener(this);
		// btn_keep.setOnClickListener(this);

	}

	private void parseItemsList(String mtStr) {
		itemsInfos.clear();
		// List<UnideInfo> list=new ArrayList<UnideInfo>();
		try {
			JSONArray jsonArray = new JSONArray(mtStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				ItemsInfo info = new ItemsInfo();
				info.setItemId(object.getInt("ItemId"));
				info.setItemName(object.getString("ItemName"));
				info.setInputCode(object.getString("InputCode"));
				info.setInputCode1(object.getString("InputCode1"));
				info.setInputCode2(object.getString("InputCode2"));
				info.setItemOutpSpec(object.getString("ItemOutpSpec"));
				info.setItemOutpUnits(object.getString("ItemOutpUnits"));
				info.setItemBasicUnits(object.getString("ItemBasicUnits"));
				info.setItemPrice1(object.getDouble("ItemPrice"));
				info.setItemPropertyIndicator(object
						.getString("ItemPropertyIndicator"));
				info.setItemPropertyName(object.getString("ItemPropertyName"));
				info.setDrugOrItemIndicator(object
						.getString("DrugOrItemIndicator"));
				info.setDrugOrItemIndicatorName(object
						.getString("DrugOrItemIndicatorName"));
				info.setQuantity(object.getString("Quantity"));
				info.setItemClass(object.getString("ItemClass"));
				info.setItemClassName(object.getString("ItemClassName"));
				info.setCdname(object.getString("Cdname"));
				info.setItemtradename(object.getString("Itemtradename"));

				itemsInfos.add(info);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		adapter = new ItemsInfoAdapter(this, itemsInfos);
		listView.setAdapter(adapter);
	}

}

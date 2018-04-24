package com.fc.recritmentmanager.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.LineLevelItem;
import com.fc.main.beans.PersonItem;
import com.fc.main.myservices.CompanyService;
import com.fc.main.myviews.GetPersonActivity;
import com.fc.recritmentmanager.bean.PersonNumberAdapter;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NumberCenterActivity extends Activity implements IActivity {
	
	LinearLayout llDuty, llPerson;
	ListView allPeople;
	List<LineLevelItem> allLevels;
	List<PersonItem> allPersons;
	PersonNumberAdapter adapter;
	/**
	 * 刷新职务列表
	 */
	public static final int REFRESH_LINELEVEL = 1;

	/**
	 * 刷新人员列表
	 */
	public static final int REFRESH_PERSON = 2;
	
	public static final int REFRESH_PERSON_TWO =3;

	Map<String, Object> data = new HashMap<String, Object>();
	List<PersonItem> allPersonsBack;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.numbercentermanager_list);
		init();
		initView();
		Map<String,Object> params=new HashMap<String, Object>();
		Map<String,String> data=new HashMap<String, String>();
		data.put("window", "数据中心");
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.NUMBERCENTER_GET_PERSON, params);
		CompanyService.newTask(task);
	}

	private void initView() {
		llDuty = (LinearLayout) findViewById(R.id.llDuty);
		llPerson = (LinearLayout) findViewById(R.id.llPerson);
		allPeople=(ListView) this.findViewById(R.id.allpeople);
		allLevels = new ArrayList<LineLevelItem>();
		allPersons = new ArrayList<PersonItem>();
		adapter=new PersonNumberAdapter(allPersons,NumberCenterActivity.this);
		allPeople.setAdapter(adapter);

		allPeople.setOnItemClickListener(new MyOnItemClickListener());
	}


	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case GetPersonActivity.REFRESH_LINELEVEL:
			if (params[1] != null) {
				String linelevelStr = params[1].toString();
				fretchLevels(linelevelStr);
				if (allLevels.size() > 0) {
					List<LineLevelItem> topItems = getTopItem();
					for (LineLevelItem top : topItems) {
						fretchTree(llDuty, top);
					}
				}
			}
			break;
		case GetPersonActivity.REFRESH_PERSON:
			if (params[1] != null && !"".equals(params[1].toString().trim())) {
				if (allPersons.size()>0) {
					allPersons.clear();
				}
				List<PersonItem> newitems = JSON.parseArray(params[1].toString().trim(),
						PersonItem.class);
				if (newitems.size()>0) {
					allPersons.addAll(newitems);
				}else{
					Toast.makeText(NumberCenterActivity.this, "没有人员可选", Toast.LENGTH_SHORT).show();
				}
			}
			adapter.notifyDataSetChanged();
			break;
		}
	}
	
	

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	private void fretchLevels(String linelevelStr) {
		try {
			JSONArray jsonArray = new JSONArray(linelevelStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				LineLevelItem item = new LineLevelItem();
				JSONObject obj = jsonArray.getJSONObject(i);

				item.setId(obj.getInt("ID"));
				item.setName(obj.getString("NAME"));
				item.setParent_id(obj.getInt("PARENT_ID"));
				allLevels.add(item);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private List<LineLevelItem> getTopItem() {
		List<LineLevelItem> topItems = new ArrayList<LineLevelItem>();
		for (LineLevelItem item : allLevels) {
			if (isTop(item)) {
				topItems.add(item);
			}
		}
		return topItems;
	}

	private boolean isTop(LineLevelItem lineLevelItem) {
		for (LineLevelItem item : allLevels) {
			if (lineLevelItem.getParent_id() == item.getId()) {
				return false;
			}
		}
		return true;
	}

	private List<LineLevelItem> getSubIems(LineLevelItem lineLevelItem) {
		List<LineLevelItem> subItems = new ArrayList<LineLevelItem>();
		for (LineLevelItem item : allLevels) {
			if (lineLevelItem.getId() == item.getParent_id()) {
				subItems.add(item);
			}
		}
		return subItems;
	}

	private void fretchTree(LinearLayout fatherLayout, final LineLevelItem item) {
		LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.activity_getperson_item, null);
		TextView lblName = (TextView) layout.findViewById(R.id.lblName);
		lblName.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		lblName.setText(item.getName());
		fatherLayout.addView(layout);

		lblName.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getInfo(""+item.getId());
			}
		});

		List<LineLevelItem> subItems = getSubIems(item);
		if (subItems.size() > 0) {
			for (LineLevelItem sub : subItems) {
				fretchTree(layout, sub);
			}
		}
	}
	
	private void getInfo(String id){
		Map<String, Object> params=new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("all", "false");
		data.put("ID", id);
		data.put("window", "数据中心");
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.NUMBERCENTER_GET_PERSONINF, params);
		CompanyService.newTask(task);
	}
	
	private class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(NumberCenterActivity.this, NumberPeopleCenterActivity.class);
			intent.putExtra("staff", allPersons.get(position).getStaff_id()+"");
			startActivity(intent);
		}
		
	}
}

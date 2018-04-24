package com.fc.main.myviews;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.fc.company.beans.CompanyTask;
import com.fc.first.views.SearchPendWorkActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.LineLevelItem;
import com.fc.main.beans.PersonItem;
import com.fc.main.beans.PersonItemsAdapter;
import com.fc.main.service.CompanyService;
import com.test.zbetuch_news.R;

public class GetPersonActivity extends Activity implements IActivity {
	LinearLayout llDuty, llPerson;
	Button btnOk, btnCancle;
	ListView allPeople;
	CheckBox cbxAll;
	GridView gvPerson;

	List<LineLevelItem> allLevels;
	List<PersonItem> allPersons;
	List<CheckBox> boxs = new ArrayList<CheckBox>();
	PersonItemsAdapter adapter;
	/**
	 * 刷新职务列表
	 */
	public static final int REFRESH_LINELEVEL = 1;

	/**
	 * 刷新人员列表
	 */
	public static final int REFRESH_PERSON = 2;

	Map<String, Object> data = new HashMap<String, Object>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_getperson);
		init();
		initView();
		initListener();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("window", "员工待办事宜");
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.GETPERSONACTIVITY_GET_LINELEVEL, params);
		CompanyService.newTask(task);
	}

	private void initView() {
		llDuty = (LinearLayout) findViewById(R.id.llDuty);
		llPerson = (LinearLayout) findViewById(R.id.llPerson);
		allPeople = (ListView) this.findViewById(R.id.allpeople);

		btnOk = (Button) findViewById(R.id.btnOk);
		btnCancle = (Button) findViewById(R.id.btnCancle);
		// cbxAll = (CheckBox) findViewById(R.id.cbxAll);
		// gvPerson = (GridView) findViewById(R.id.gvPerson);

		allLevels = new ArrayList<LineLevelItem>();
		allPersons = new ArrayList<PersonItem>();
		boxs = new ArrayList<CheckBox>();
		adapter = new PersonItemsAdapter(allPersons, GetPersonActivity.this);
		allPeople.setAdapter(adapter);

	}

	private void initListener() {
		btnOk.setOnClickListener(new MyOnClickListener());
		btnCancle.setOnClickListener(new MyOnClickListener());
		// cbxAll.setOnCheckedChangeListener(new
		// MyOnItemCheckedChangeListener());
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
			if (params[1] != null) {
				String personStr = params[1].toString().trim();
				int position = Integer.parseInt(personStr);
				allPersons.remove(position);
			}
			adapter = new PersonItemsAdapter(allPersons, GetPersonActivity.this);
			allPeople.setAdapter(adapter);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100 && resultCode == 100) {
			List<PersonItem> newPersonItems = (List<PersonItem>) data
					.getSerializableExtra("persons");
			if (newPersonItems != null && newPersonItems.size() > 0) {
				if (allPersons.size() > 0 && allPersons != null) {
					for (int i = 0; i < allPersons.size(); i++) {
						for (int j = 0; j < newPersonItems.size(); j++) {
							if (allPersons.get(i).getStaff_id() == newPersonItems
									.get(j).getStaff_id()) {
								newPersonItems.remove(j);
							}
						}
					}
				}
				allPersons.addAll(newPersonItems);
			}
			adapter = new PersonItemsAdapter(allPersons, GetPersonActivity.this);
			allPeople.setAdapter(adapter);
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
				Intent intent = new Intent(GetPersonActivity.this,
						AllPeopleActivity.class);
				intent.putExtra("id", "" + item.getId());
				startActivityForResult(intent, 100);
				// startActivity(intent);
				// Map<String, String> params = new HashMap<String, String>();
				// params.put("ID", "" + item.getId());
				// data.put("data", params);
				// CompanyTask task = new CompanyTask(
				// CompanyTask.GETPERSONACTIVITY_GET_PERSONBYLEVEL, data);
				// CompanyService.newTask(task);
			}
		});

		List<LineLevelItem> subItems = getSubIems(item);
		if (subItems.size() > 0) {
			for (LineLevelItem sub : subItems) {
				fretchTree(layout, sub);
			}
		}
	}

	private void fretchPersons(String personString) {
		allPersons.clear();
		List<PersonItem> newitems = JSON.parseArray(personString,
				PersonItem.class);
		if (newitems != null && newitems.size() > 0) {
			allPersons.addAll(newitems);
		}
	}

	// private void fretchBoxs() {
	// // llPerson.removeAllViews();
	// boxs.clear();
	// if (allPersons == null || allPersons.size() == 0) {
	// Toast.makeText(this, "没有可选人员", Toast.LENGTH_SHORT).show();
	// } else {
	// for (PersonItem person : allPersons) {
	// CheckBox box = (CheckBox) getLayoutInflater().from(
	// GetPersonActivity.this).inflate(
	// R.layout.activity_getperson_checkbox, null);
	// // CheckBox box = new CheckBox(this);
	// box.setText(person.getName());
	// // llPerson.addView(box);
	// boxs.add(box);
	// }
	//
	// }
	// adapter = new CheckBoxAdapter(this, boxs);
	// gvPerson.setAdapter(adapter);
	// }

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnOk:
				Intent intent = new Intent(GetPersonActivity.this,
						SearchPendWorkActivity.class);
				intent.putExtra("persons", (Serializable) allPersons);
				setResult(100, intent);
				GetPersonActivity.this.finish();
				break;

			case R.id.btnCancle:
				GetPersonActivity.this.finish();
				break;
			}
		}

	}

	// private class MyOnItemCheckedChangeListener implements
	// OnCheckedChangeListener{
	//
	// @Override
	// public void onCheckedChanged(CompoundButton buttonView,
	// boolean isChecked) {
	// for(CheckBox box :boxs){
	// box.setChecked(isChecked);
	// }
	// adapter = new CheckBoxAdapter(GetPersonActivity.this, boxs);
	// gvPerson.setAdapter(adapter);
	// }
	//
	// }
}

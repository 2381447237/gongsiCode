package com.fc.main.myviews;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.CheckBoxAdapter;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PersonItem;
import com.fc.main.myservices.CompanyService;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class AllPeopleActivity extends Activity implements IActivity{
	LinearLayout llPerson;
	Button btnOk, btnCancle;
	CheckBox cbxAll;
	GridView gvPerson;
	List<PersonItem> allPersons;
	List<CheckBox> boxs;
	CheckBoxAdapter adapter;
	Intent intent;
	
	RadioGroup radioGroup;
	RadioButton curt_rb,curts_bt;
	Map<String, String> data;
	/**
	 * 刷新人员列表
	 */
	public static final int REFRESH_PERSON = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_item);
		init();
		initView();
		initListener();
		intent=getIntent();
		
		curt_rb.setChecked(true);
	}
	
	private void initView(){
		llPerson = (LinearLayout) findViewById(R.id.llPerson);
		btnOk = (Button) findViewById(R.id.btn_Ok);
		btnCancle = (Button) findViewById(R.id.btn_Cancle);
		radioGroup=(RadioGroup) this.findViewById(R.id.person_rg);
		curt_rb=(RadioButton) this.findViewById(R.id.current_rb);
		curts_bt=(RadioButton) this.findViewById(R.id.currents_rb);
		
		
		
		cbxAll = (CheckBox) findViewById(R.id.cbxAll);
		gvPerson = (GridView) findViewById(R.id.gvPerson);	
		allPersons = new ArrayList<PersonItem>();
		boxs = new ArrayList<CheckBox>();
	}
	
	private void initListener() {
		btnOk.setOnClickListener(new MyOnClickListener());
		btnCancle.setOnClickListener(new MyOnClickListener());
		cbxAll.setOnCheckedChangeListener(new MyOnItemCheckedChangeListener());
		radioGroup.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		CompanyService.addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.valueOf(params[0].toString())) {
		case AllPeopleActivity.REFRESH_PERSON:
			if (params[1] != null && !"".equals(params[1].toString().trim())) {
				String personStr = params[1].toString();
				fretchPersons(personStr);
				fretchBoxs();
			}
			break;
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
	
	private void fretchBoxs() {
		// llPerson.removeAllViews();
		boxs.clear();
		if (allPersons == null || allPersons.size() == 0) {
			Toast.makeText(this, "没有可选人员", Toast.LENGTH_SHORT).show();
		} else {
			for (PersonItem person : allPersons) {
				CheckBox box = (CheckBox) getLayoutInflater().from(
						AllPeopleActivity.this).inflate(
						R.layout.activity_getperson_checkbox, null);
				// CheckBox box = new CheckBox(this);
				box.setText(person.getName());
				// llPerson.addView(box);
				boxs.add(box);
			}

		}
		adapter = new CheckBoxAdapter(this, boxs);
		gvPerson.setAdapter(adapter);
	}
	
	
	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_Ok:
				List<PersonItem> selectItems = new ArrayList<PersonItem>();
				if (boxs != null && boxs.size() > 0) {
					for (int i = 0; i < boxs.size(); i++) {
						if (boxs.get(i).isChecked()) {
							selectItems.add(allPersons.get(i));
						}
					}
				}
				intent.putExtra("persons",(Serializable)selectItems);
				setResult(100, intent);
				AllPeopleActivity.this.finish();
				break;

			case R.id.btn_Cancle:
				AllPeopleActivity.this.finish();
				break;
			}
		}

	}
	
	private class MyOnItemCheckedChangeListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			for(CheckBox box :boxs){
				box.setChecked(isChecked);
			}
			adapter = new CheckBoxAdapter(AllPeopleActivity.this, boxs);
			gvPerson.setAdapter(adapter);
		}
		
	}
	
	private class MyOnCheckedChangeListener implements android.widget.RadioGroup.OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.current_rb:
				if (cbxAll.isChecked()) {
					cbxAll.setChecked(false);
				}
				data = new HashMap<String, String>();
				data.put("all", "false");
				getInfo();
				break;
				
			case R.id.currents_rb:
				if (cbxAll.isChecked()) {
					cbxAll.setChecked(false);
				}
				data = new HashMap<String, String>();
				data.put("all", "true");
				getInfo();
				break;

			default:
				break;
			}
			
		}
		
	}
	
	private void getInfo(){
		Map<String, Object> params=new HashMap<String, Object>();
		data.put("ID", intent.getStringExtra("id"));
		if ("search".equals(getIntent().getAction())) {
			data.put("window", "员工待办事宜");
		}
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.GETPERSONACTIVITY_GET_PERSONBYLEVEL, params);
		CompanyService.newTask(task);
	}
	

}

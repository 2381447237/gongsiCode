package com.fc.person.views;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.fc.main.beans.IActivity;
import com.fc.main.myservices.PersonService;
import com.fc.person.beans.PersonTask;
import com.fc.person.beans.SchoolInfo;
import com.fc.zbetuch_sm.R;

public class ModifySchoolInfoDetailActivity extends Activity implements IActivity {
	
	EditText txtSchool,txtEducation,txtSpecialty,txtStartDate,txtEndDate;
	Button btnOk,btnCancle;
	
	private int flag;
	
	private String sfz="";
	private SchoolInfo schoolInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modifyschoolinfo_detail);
		
		init();
		initViews();
		initListener();
		
		Intent intent = getIntent();
		flag = intent.getIntExtra("flag", 0);
		switch(flag){
		case 1:
			sfz = intent.getStringExtra("sfz");
			break;	
		case 2:
			schoolInfo = (SchoolInfo) intent.getSerializableExtra("schoolinfo");
			break;
		}
		
		initData();
		
	}
	
	@Override
	public void init() {
		PersonService.addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		
	}
	
	private void initData(){
		switch (flag) {
		//表示新建
		case 1:
			Date date = new Date();
			SimpleDateFormat foramt = new SimpleDateFormat("yyyy-MM-dd");
			txtStartDate.setText(foramt.format(date));
			txtEndDate.setText(foramt.format(date));			
			break;
		case 2:
			txtSchool.setText(schoolInfo.getSchool().trim());
			txtEducation.setText(schoolInfo.getEducation().trim());
			txtSpecialty.setText(schoolInfo.getSpecialty().trim());
			txtStartDate.setText(schoolInfo.getStart_date().trim().split("T")[0].trim());
			txtEndDate.setText(schoolInfo.getEnd_date().trim().split("T")[0].trim());			
			break;

		}
	}
	
	private void initViews(){
		txtSchool = (EditText) findViewById(R.id.txtSchool);
		txtEducation = (EditText) findViewById(R.id.txtEducation);
		txtSpecialty = (EditText) findViewById(R.id.txtSpecialty);
		txtStartDate = (EditText) findViewById(R.id.txtStartDate);
		txtEndDate = (EditText) findViewById(R.id.txtEndDate);
		btnOk = (Button) findViewById(R.id.btnOk);
		btnCancle = (Button) findViewById(R.id.btnCancle);		
		txtStartDate.setInputType(InputType.TYPE_NULL);
		txtEndDate.setInputType(InputType.TYPE_NULL);
	}
	
	private void initListener(){
		btnOk.setOnClickListener(new MyOnClickListener());
		btnCancle.setOnClickListener(new MyOnClickListener());
		txtStartDate.setOnClickListener(new MyOnClickListener());
		txtEndDate.setOnClickListener(new MyOnClickListener());
		
	}
	
	/**
	 * 显示时间对话框
	 * @param txt
	 */
	private void showDateDialog(final EditText txt){
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String value = txt.getText().toString().trim();
		Calendar day = Calendar.getInstance();
		try {
			day.setTime(format.parse(value));
			new DatePickerDialog(this, new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					Date selectDate = new Date(year-1900, monthOfYear, dayOfMonth);
					txt.setText(format.format(selectDate));
				}
			}, day.get(Calendar.YEAR)
			, day.get(Calendar.MONTH),
			day.get(Calendar.DATE)).show();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnOk:
				JSONObject obj = new JSONObject();
				try {
					if(flag==1){
						obj.put("ID", "" +0);
						obj.put("SFZ", sfz);
						
					}else if(flag==2){
						if(schoolInfo!=null){
							obj.put("ID",""+ schoolInfo.getId());
							obj.put("SFZ", schoolInfo.getSfz());
							obj.put("CREATE_DATE", schoolInfo.getCreate_date().trim());
							obj.put("CREATE_STAFF", schoolInfo.getCreate_staff().trim());
							obj.put("UPDATE_DATE", schoolInfo.getUpdate_date().trim());
							obj.put("UPDATE_STAFF", schoolInfo.getUpdate_staff().trim());
							obj.put("Type", schoolInfo.getType().trim());
						}
					}
					obj.put("SCHOOL", txtSchool.getText().toString().trim());
					obj.put("EDUCATION", txtEducation.getText().toString().trim());
					obj.put("SPECIALTY", txtSpecialty.getText().toString().trim());
					obj.put("START_DATE", txtStartDate.getText().toString().trim());
					obj.put("END_DATE", txtEndDate.getText().toString().trim());
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Map<String, Object> params = new HashMap<String, Object>();
				Map<String, String> data = new HashMap<String, String>();
				data.put("json", obj.toString());
				params.put("data", data);
				PersonTask task = new PersonTask(
						PersonTask.MODIFYSCHOOLINFOACTIVITY_UPDATE_SCHOOLINFO,
						params);
				PersonService.newTask(task);
				ModifySchoolInfoDetailActivity.this.finish();
				break;
			case R.id.btnCancle:
				ModifySchoolInfoDetailActivity.this.finish();
				break;
			case R.id.txtStartDate:
				showDateDialog(txtStartDate);
				break;
			case R.id.txtEndDate:
				showDateDialog(txtEndDate);
				break;
			
			}
		}
		
	}

}

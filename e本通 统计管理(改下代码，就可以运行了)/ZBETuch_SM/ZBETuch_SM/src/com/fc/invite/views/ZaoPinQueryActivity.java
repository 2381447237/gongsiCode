package com.fc.invite.views;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.fc.main.beans.IActivity;
import com.fc.main.beans.MainTask;
import com.fc.main.beans.SpinnerItem;
import com.fc.main.myservices.MainService;
import com.fc.main.tools.MainTools;
import com.fc.zbetuch_sm.R;

public class ZaoPinQueryActivity extends Activity implements IActivity {
	EditText txtComName,txtJobNo,txtJobName,txtStartSalary,txtEndSalary,txtAge;
	Button btnCheck;
	Spinner cboComProperty,cboTypeOfWork,cboCaltureLevel,cboIndustryBigStyle,cboIndustrySmallStyle,
	cboJobBigStyle,cboJobSmallStyle,cboJobKind,cboWorkTime,cboWorkArea1,cboWorkArea2,cboWorkArea3;
	RadioGroup radiogroup;
	RadioButton rdono,rdotoday,rdothreeday,rdoweek,rdomonth;
	CheckBox cboIsDirectInterview,cboIsNewGraduates,cboIsDisabledPerson,cboIsAssurance;
	ProgressDialog progressDialog;
	
	/**
	 * 刷新工作性质
	 */
	public static final int REFRESH_CBOCOMPROPERTY=1;
	/**
	 * 刷新常见工种
	 */
	public static final int REFRESH_CBOTYPEOFWORK =2;
	/**
	 * 刷新文化程度
	 */
	public static final int REFRESH_CBOCALTURELEVEL=3;
	
	/**
	 * 刷新行业分类大类
	 */
	public static final int REFRESH_CBOINDUSTRYBIGSTYLE=4;
	
	/**
	 * 刷新行业分类小类
	 */
	public static final int REFRESH_CBOINDUSTRYSMALLSTYLE=5;
	
	/**
	 * 刷新职业分类大类
	 */
	public static final int REFRESH_CBOJOBBIGSTYLE=6;
	
	/**
	 * 刷新职业分类小类
	 */
	public static final int REFRESH_CBOJOBSMALLSTYLE=7;
	
	/**
	 * 刷新工作性质
	 */
	public static final int REFRESH_CBOCBOJOBKIND=8;
	/**
	 * 刷新工作班时
	 */
	public static final int REFRESH_CBOWORKTIME=9;
	/**
	 * 刷新工作地区
	 */
	public static final int REFRESH_CBOWORKAREA=10;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zaopin_query);
		init();
		initControl();
		initSpinner();
		initListener();
		showDialog();
	}
	
	/**
	 * 初始化控件
	 */
	private void initControl(){
		txtComName = (EditText) findViewById(R.id.txtComName);
		txtJobNo = (EditText) findViewById(R.id.txtJobNo);
		txtJobName = (EditText) findViewById(R.id.txtJobName);
		txtStartSalary = (EditText) findViewById(R.id.txtStartSalary);
		txtEndSalary = (EditText) findViewById(R.id.txtEndSalary);
		txtAge = (EditText) findViewById(R.id.txtAge);
		btnCheck = (Button) findViewById(R.id.btnCheck);
		cboComProperty = (Spinner) findViewById(R.id.cboComProperty);
		cboTypeOfWork = (Spinner) findViewById(R.id.cboTypeOfWork);
		cboCaltureLevel = (Spinner) findViewById(R.id.cboCaltureLevel);
		cboIndustryBigStyle = (Spinner) findViewById(R.id.cboIndustryBigStyle);
		cboIndustrySmallStyle = (Spinner) findViewById(R.id.cboIndustrySmallStyle);
		cboJobBigStyle = (Spinner) findViewById(R.id.cboJobBigStyle);
		cboJobSmallStyle = (Spinner) findViewById(R.id.cboJobSmallStyle);
		cboJobKind = (Spinner) findViewById(R.id.cboJobKind);
		cboWorkTime = (Spinner) findViewById(R.id.cboWorkTime);
		cboWorkArea1 = (Spinner) findViewById(R.id.cboWorkArea1);
		cboWorkArea2 = (Spinner) findViewById(R.id.cboWorkArea2);
		cboWorkArea3 = (Spinner) findViewById(R.id.cboWorkArea3);
		
		radiogroup = (RadioGroup) findViewById(R.id.rdogroup);
		rdono = (RadioButton) findViewById(R.id.rdono);
		rdotoday = (RadioButton) findViewById(R.id.rdotoday);
		rdothreeday = (RadioButton) findViewById(R.id.rdothreeday);
		rdoweek = (RadioButton) findViewById(R.id.rdoweek);
		rdomonth = (RadioButton) findViewById(R.id.rdomonth);
		rdono.setChecked(true);
		
		cboIsDirectInterview = (CheckBox) findViewById(R.id.cboIsDirectInterview);
		cboIsNewGraduates = (CheckBox) findViewById(R.id.cboIsNewGraduates);
		cboIsDisabledPerson = (CheckBox) findViewById(R.id.cboIsDisabledPerson);
		cboIsAssurance = (CheckBox) findViewById(R.id.cboIsAssurance);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item1,getResources()
				.getStringArray(R.array.cboWorkType));
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cboTypeOfWork.setAdapter(adapter);
		
	}

	/**
	 * 加载下拉框数据
	 */
	private void initSpinner(){
		MainTask task = new MainTask(MainTask.ZAOPINQUERYACTIVITY_GETCBOCOMPROPERTY, null);
		MainService.newTask(task);
		//添加工种类型，不从数据库取，写死
//		task = new MainTask(MainTask.ZAOPINQUERYACTIVITY_GET_CBOTYPEOFWORK, null);
//		MainService.newTask(task);
		task = new MainTask(MainTask.ZAOPINQUERYACTIVITY_GET_CBOEDU, null);
		MainService.newTask(task);
		task = new MainTask(MainTask.ZAOPINQUERYACTIVITY_GET_CBOZYFL, null);
		MainService.newTask(task);
		task = new MainTask(MainTask.ZAOPINQUERYACTIVITY_GET_CBOINDUSTRYCLASS, null);
		MainService.newTask(task);
		task = new MainTask(MainTask.ZAOPINQUERYACTIVITY_GET_CBOGZXZ, null);
		MainService.newTask(task);
		task = new MainTask(MainTask.ZAOPINQUERYACTIVITY_GET_CBOGZBS, null);
		MainService.newTask(task);
		task = new MainTask(MainTask.ZAOPINQUERYACTIVITY_GET_CBOAREAINFO, null);
		MainService.newTask(task);		
	}
	
	/**
	 * 初始化监听器
	 */
	private void initListener(){
		cboIndustryBigStyle.setOnItemSelectedListener(new MyOnItemSelectedListener());
		cboJobBigStyle.setOnItemSelectedListener(new MyOnItemSelectedListener());
		cboTypeOfWork.setOnItemSelectedListener(new MyOnItemSelectedListener());
		btnCheck.setOnClickListener(new MyOnClickListener());
	}
	
	@Override
	public void init() {
		MainService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		String value ="";
		if(params[1]!=null){
			value = params[1].toString();
		}
		switch (Integer.valueOf(params[0].toString())) {
		case ZaoPinQueryActivity.REFRESH_CBOCOMPROPERTY:
			MainTools.fetchSpinner(this, cboComProperty, value, "compropertyid", "compropertyname",R.layout.simple_spinner_item1);
			break;
		case ZaoPinQueryActivity.REFRESH_CBOTYPEOFWORK:			
			break;
		case ZaoPinQueryActivity.REFRESH_CBOCALTURELEVEL:
			MainTools.fetchSpinner(this, cboCaltureLevel, value, "id", "edu_name",R.layout.simple_spinner_item1);
			break;
		case ZaoPinQueryActivity.REFRESH_CBOINDUSTRYBIGSTYLE:
			MainTools.fetchSpinner(this, cboIndustryBigStyle, value, "id", "name","code",R.layout.simple_spinner_item1);
			break;
		case ZaoPinQueryActivity.REFRESH_CBOINDUSTRYSMALLSTYLE:
			MainTools.fetchSpinner(this, cboIndustrySmallStyle, value, "id", "name",R.layout.simple_spinner_item1);
			break;
		case ZaoPinQueryActivity.REFRESH_CBOJOBBIGSTYLE:
			MainTools.fetchSpinner(this, cboJobBigStyle, value, "id", "name","code",R.layout.simple_spinner_item1);
			break;
		case ZaoPinQueryActivity.REFRESH_CBOJOBSMALLSTYLE:
			MainTools.fetchSpinner(this, cboJobSmallStyle, value, "id", "name",R.layout.simple_spinner_item1);
			break;
		case ZaoPinQueryActivity.REFRESH_CBOCBOJOBKIND:
			MainTools.fetchSpinner(this, cboJobKind, value, "id", "name",R.layout.simple_spinner_item1);
			break;
		case ZaoPinQueryActivity.REFRESH_CBOWORKTIME:
			MainTools.fetchSpinner(this, cboWorkTime, value, "id", "name",R.layout.simple_spinner_item1);
			break;
		case ZaoPinQueryActivity.REFRESH_CBOWORKAREA:
			MainTools.fetchSpinner(this, cboWorkArea1, value, "areaid", "areaname",R.layout.simple_spinner_item1);
			MainTools.fetchSpinner(this, cboWorkArea2, value, "areaid", "areaname",R.layout.simple_spinner_item1);
			MainTools.fetchSpinner(this, cboWorkArea3, value, "areaid", "areaname",R.layout.simple_spinner_item1);
			if(progressDialog!=null && progressDialog.isShowing()){
				progressDialog.dismiss();
				progressDialog = null;
			}
			break;
		
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		MainService.allActivity.remove(this);
	}
	
	
	private class MyOnItemSelectedListener implements OnItemSelectedListener{

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch(arg0.getId()){
			case R.id.cboIndustryBigStyle:
				SpinnerItem item = (SpinnerItem) cboIndustryBigStyle.getSelectedItem();
				if(item.getId()!=-1){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("parentid", item.getCode());
					MainTask task1 = new MainTask(MainTask.ZAOPINQUERYACTIVITY_GET_CBOINDUSTRYSMALL, params);
					MainService.newTask(task1);
				}				
				break;
			case R.id.cboJobBigStyle:
				SpinnerItem item2 = (SpinnerItem)cboJobBigStyle.getSelectedItem();
				if(item2.getId()!=-1){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("parentid", item2.getCode());
					System.out.println("parent_id"+ item2.getId());
					MainTask task2 = new MainTask(MainTask.ZAOPINQUERYACTIVITY_GET_CBOZYFLCHILD, params);
					MainService.newTask(task2);
				}
				break;
			case R.id.cboTypeOfWork:
				if(cboTypeOfWork.getSelectedItemPosition()!=0){
					String typeofworkStr = cboTypeOfWork.getSelectedItem().toString();
					txtJobName.setText(typeofworkStr);
				}
				break;
			}
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			
		}
		
	}
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btnCheck:
				Intent intent = new Intent(ZaoPinQueryActivity.this,InviteJobListActivity.class);
				intent.putExtra("flag", InviteJobListActivity.GET_ITEMS_BY_PARAMS);
				intent.putExtra("ComName", txtComName.getText().toString().trim());
				intent.putExtra("ComPropertyId",((SpinnerItem) cboComProperty.getSelectedItem()).getId());
				intent.putExtra("JobNo", txtJobNo.getText().toString().trim());
				intent.putExtra("JobName", txtJobName.getText().toString().trim());
				String startsalary=txtStartSalary.getText().toString().trim();
				if(startsalary.equals("")){
					startsalary="0";
				}				
				intent.putExtra("StartSalary", startsalary);
				String endsalary=txtEndSalary.getText().toString().trim();
				if(endsalary.equals("")){
					endsalary="0";
				}				
				intent.putExtra("EndSalary",endsalary);
				String age = txtAge.getText().toString().trim();
				if(age.equals("")){
					age="-1";
				}				
				intent.putExtra("Age",age);
				intent.putExtra("IndustryClassId",((SpinnerItem) cboIndustryBigStyle.getSelectedItem()).getId());
				int industryclassidchild=-1;
				if(cboIndustrySmallStyle.getAdapter()!=null){
					industryclassidchild = ((SpinnerItem)cboIndustrySmallStyle.getSelectedItem()).getId();
				}
				intent.putExtra("IndustryClassChildId", industryclassidchild);
				intent.putExtra("ZyflId", ((SpinnerItem)cboJobBigStyle.getSelectedItem()).getId());
				int zyflidchild=-1;
				if(cboJobSmallStyle.getAdapter()!=null){
					zyflidchild = ((SpinnerItem)cboJobSmallStyle.getSelectedItem()).getId();
				}
				intent.putExtra("ZyflChildId", zyflidchild);
				intent.putExtra("GZXZId", ((SpinnerItem)cboJobKind.getSelectedItem()).getId());
				intent.putExtra("GZBSId", ((SpinnerItem)cboWorkTime.getSelectedItem()).getId());
				
				intent.putExtra("AreaId1", ((SpinnerItem)cboWorkArea1.getSelectedItem()).getId());
				intent.putExtra("AreaId2", ((SpinnerItem)cboWorkArea2.getSelectedItem()).getId());
				intent.putExtra("AreaId3", ((SpinnerItem)cboWorkArea3.getSelectedItem()).getId());
				
				
				String modifystartdate="";
				String modifyenddate="";
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				
				Calendar today = Calendar.getInstance();
				today.add(Calendar.DATE, 1);
				Date tomorrow= today.getTime();
				
				modifyenddate = format.format(tomorrow);
				int buttonid = radiogroup.getCheckedRadioButtonId();
				switch (buttonid) {
				case R.id.rdono:
					modifystartdate="2010-01-01";
					modifyenddate="2030-01-01";
					break;
				case R.id.rdotoday:
					modifystartdate = format.format(new Date());					
					break;
				case R.id.rdothreeday:
					today = Calendar.getInstance();
					today.add(Calendar.DATE, -3);
					modifystartdate = format.format(today.getTime());
					break;
				case R.id.rdoweek:
					today = Calendar.getInstance();
					today.add(Calendar.DATE, -7);
					modifystartdate = format.format(today.getTime());
					break;
				case R.id.rdomonth:
					today=Calendar.getInstance();
					today.add(Calendar.MONTH, -1);
					modifystartdate = format.format(today.getTime());
					break;				
				}
				intent.putExtra("ModifyStartDate", modifystartdate);
				intent.putExtra("ModifyEndDate", modifyenddate);
				
				intent.putExtra("IsDirectInterview", cboIsDirectInterview.isChecked());
				intent.putExtra("IsNewGraduates", cboIsNewGraduates.isChecked());
				intent.putExtra("IsDisabledPerson", cboIsDisabledPerson.isChecked());
				intent.putExtra("IsAssurance", cboIsAssurance.isChecked());
				intent.putExtra("EduLevel",((SpinnerItem)cboCaltureLevel.getSelectedItem()).getId());
				startActivity(intent);
			break;
			}			
		}
		
	}
	
	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

}

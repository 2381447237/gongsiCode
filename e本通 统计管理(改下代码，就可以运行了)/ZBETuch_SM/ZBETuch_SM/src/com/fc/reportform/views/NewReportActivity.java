package com.fc.reportform.views;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.choosefile.views.FileManager;
import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PersonItem;
import com.fc.main.beans.SpinnerItem;
import com.fc.main.myservices.CompanyService;
import com.fc.main.myservices.MainService;
import com.fc.main.myviews.GetPersonActivity;
import com.fc.main.tools.HttpUtil;
import com.fc.main.tools.MainTools;
import com.fc.meetguanli.views.GuanLiMainActivity;
import com.fc.meetingpost.beans.UploadMeetingAdapter;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;



public class NewReportActivity extends Activity implements IActivity{

	private EditText et_repname;
	private EditText et_repdate;
	private Spinner et_reptime_type;
	private EditText et_repdoc;
	private Button btn_tijiao;
	private String staffName;
	private int staffId;
	int mYear,mMonth,mDay,mHour,mMinute;
	private EditText et_sjr;
	private List<PersonItem> persons;
	private String staffIds;
	private Spinner et_reptype,report_time_year,report_time_month;
	private Button btn_sjr;
	private Button btn_fujian;
	private List<File> files=new ArrayList<File>();
	private ListView upLoadListView;
	private UploadMeetingAdapter reportAdapter;
	private String str;
	private String str2;
	private int[] ids;
	private ArrayList<String> alltypes;
	private ArrayAdapter<String> aspnCountries;
	
	private ArrayList<String> allyear;
	private ArrayAdapter<String> yearAdapter;
	private ArrayList<String> allMonth;
	private ArrayAdapter<String> monthAdapter;

	private static final String[] mTypes = { "请选择" ,"季报" ,"月报", "周报"};


	public static final int SET_REPORT_DATA=0;

	public static final int REFRESH_TYPE=1;
	
	private ProgressDialog loadDialog;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 100 && data != null) {
			persons = (List<PersonItem>) data.getSerializableExtra("persons");
			String names = "";
			staffIds="";
			if (persons != null && persons.size()>0) {
				for (PersonItem item : persons) {
					names += item.getName() + ",";
					staffIds+=item.getStaff_id()+",";
				}
			}
			names =names.trim().length()==0?"": names.substring(0, names.length() - 1);
			staffIds =staffIds.trim().length()==0?"": staffIds.substring(0,staffIds.length()-1);
			et_sjr.setText(names);
		}

		if (requestCode==200&&resultCode==500) {
			if (files.size()>0) {
				files.clear();
			}
			List<File> newFile=(List<File>) data.getSerializableExtra("file");
			if (newFile.size()>0) {
				files.addAll(newFile);
			}
			reportAdapter.notifyDataSetChanged();
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newreport);
		init();
		initView();
		initListener();
		et_sjr.requestFocus();

		CompanyTask task=new CompanyTask(CompanyTask.TYPE_NAME, null);
		CompanyService.newTask(task);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		CompanyService.addActivity(this);
	}

	private void initView() {
		// TODO Auto-generated method stub
		et_sjr = (EditText) findViewById(R.id.report_sjr);
		btn_sjr = (Button) findViewById(R.id.reportsjr_btn);
		et_repname = (EditText) findViewById(R.id.report_name);
		report_time_year=(Spinner) findViewById(R.id.report_time_year);
		report_time_month=(Spinner) findViewById(R.id.report_time_month);
		et_reptime_type = (Spinner) findViewById(R.id.report_time_type);
		et_reptype = (Spinner) findViewById(R.id.report_type);
		et_repdoc = (EditText) findViewById(R.id.report_neirong);
		btn_tijiao = (Button) findViewById(R.id.reporttj_btn);
		btn_fujian = (Button) findViewById(R.id.reportfujian_btn);
		upLoadListView=(ListView) this.findViewById(R.id.file_list);
		reportAdapter=new UploadMeetingAdapter(NewReportActivity.this, files);
		upLoadListView.setAdapter(reportAdapter);
		staffName = MainService.STAFFNAME;
		staffId = Integer.valueOf(MainService.STAFFID);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		Date curDate = new Date(System.currentTimeMillis());
		str = formatter.format(curDate);
		str2=month.format(curDate);
		allyear=new ArrayList<String>();
		for (int i = 5; i >= 0; i--) {
			allyear.add(String.valueOf(Integer.parseInt(str)-i));
		}
		yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allyear); 
		yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		report_time_year.setAdapter(yearAdapter);
		allMonth=new ArrayList<String>();
		for (int i = 1; i < 13; i++) {
			if (i<10) {
				allMonth.add(String.valueOf("0"+(i)));
			}else{
				allMonth.add(String.valueOf(i));
			}
		}
		monthAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allMonth); 
		monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		report_time_month.setAdapter(monthAdapter);
		

		int mTypesLength = mTypes.length;
		alltypes = new ArrayList<String>();
		for(int i=0;i<mTypesLength;i++){
			alltypes.add(mTypes[i]);
		}
		aspnCountries = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, alltypes); 
		aspnCountries.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		et_reptime_type.setAdapter(aspnCountries);
		
		initYear(str);
		initMonth(str2);
	}
	
	private void initYear(String str){
		for (int i = 0; i < yearAdapter.getCount(); i++) {
			System.out.println(yearAdapter.getItem(i));
			if (str.equals(yearAdapter.getItem(i))) {
				report_time_year.setSelection(i);
				break;
			}
		}
	}
	
	private void initMonth(String str){
		for (int i = 0; i < monthAdapter.getCount(); i++) {
			if (str.equals(monthAdapter.getItem(i))) {
				report_time_month.setSelection(i);
				break;
			}
		}
	}

	private void initListener() {
		// TODO Auto-generated method stub
		btn_tijiao.setOnClickListener(new MyOnClickListener());
		btn_sjr.setOnClickListener(new MyOnClickListener());
		btn_fujian.setOnClickListener(new MyOnClickListener());
	}

	private class MyOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.reporttj_btn:
				showMesage();
				break;
			case R.id.reportfujian_btn:
				Intent fileManagerIntent = new Intent(NewReportActivity.this,
						FileManager.class);
				fileManagerIntent.setAction("NewReportActivity");
				if (files!=null&&files.size()>0) {
					fileManagerIntent.putExtra("files", (Serializable)files);
				}
				startActivityForResult(fileManagerIntent,200); 
				break;
			case R.id.reportsjr_btn:
				Intent intent = new Intent(NewReportActivity.this,
						GetPersonActivity.class);
				intent.setAction("reportport");
				if(persons==null||persons.size()==0)
				{
					startActivityForResult(intent, 100);
				}
				else if (persons != null && persons.size()>0) {
					intent.putExtra("persons",(Serializable)persons);
					startActivityForResult(intent, 100);
				}
				break;
			}
		}
	}



	// 隐藏手机键盘
	private void hideIM(View edt) {
		try {
			InputMethodManager im = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			IBinder windowToken = edt.getWindowToken();
			if (windowToken != null) {
				im.hideSoftInputFromWindow(windowToken, 0);
			}
		} catch (Exception e) {
		}
	}


	protected void addData() {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String time=sdf.format(new Date());
		String[] arr = staffIds.split(",");
		ids = new int[arr.length];
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, String> params = new HashMap<String, String>();
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonObject=null;

		JSONArray jsarr = new JSONArray();
		JSONObject jsObj=null;
		for(int i=0;i<ids.length;i++){
			ids[i] = Integer.parseInt(arr[i]);
			try {
				jsObj = new JSONObject();
				jsonObject = new JSONObject();
				jsonObject.put("ID", 0);
				jsonObject.put("TITLE",et_repname.getText().toString().trim());
				jsonObject.put("TYPE",-1);
				jsonObject.put("MARK",et_repdoc.getText().toString().trim());
				jsonObject.put("REPORT_TIME",report_time_year.getSelectedItem().toString()+"-"+report_time_month.getSelectedItem().toString()+"-01");
				jsonObject.put("TYPE2", et_reptime_type.getSelectedItem());		
				jsonObject.put("CREATE_STAFF",staffId);
				jsonObject.put("CREATE_DATE",time);
				jsonObject.put("UPDATE_STAFF",staffId);
				jsonObject.put("UPDATE_DATE",time);
				jsonObject.put("RecordCount",0);
				jsonObject.put("CREATE_STAFF_NAME",staffName);
				jsonObject.put("TYPE_NAME",et_reptype.getSelectedItem().toString().trim());
				jsonObject.put("TYPE", ((SpinnerItem)et_reptype.getSelectedItem()).getId());
				jsObj.put("ID", -1);
				jsObj.put("MASTER_ID", -1);
				jsObj.put("CHECK_STAFF", -1);
				//			jsObj.put("CHECK_TIME", "");
				jsObj.put("REPORT_STAFF", ids[i]);
				jsObj.put("RecordCount", 0);
				jsarr.put(jsObj);
				jsonObject.put("Checks",jsarr);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		jsonarray.put(jsonObject);
		params.put("data", jsonarray.toString().trim());
		data.put("data", params);
		CompanyTask task = new CompanyTask(
				CompanyTask.NEWREPORTACTIVITY_SET_MANAGE_REPORT, data);
		CompanyService.newTask(task);

	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case SET_REPORT_DATA:
			if (!"".equals(params[1].toString().trim())&&params[1].toString().trim()!=null) {
				if (Integer.parseInt(params[1].toString().trim())>=0) {
					HttpUtil.uploadFile(files, "/Json/Set_Manage_Report_File.aspx?master_id="+params[1].toString().trim());
					if (loadDialog!=null&&loadDialog.isShowing()) {
						loadDialog.dismiss();
					}
					Toast.makeText(NewReportActivity.this, "发布成功！", Toast.LENGTH_SHORT).show();
					NewReportActivity.this.finish();
				}else{
					if (loadDialog!=null&&loadDialog.isShowing()) {
						loadDialog.dismiss();
					}
					Toast.makeText(NewReportActivity.this, "发布失败！", Toast.LENGTH_SHORT).show();
				}
			}
			break;

		case REFRESH_TYPE:
			if (!"".equals(params[1].toString().trim())&&params[1].toString().trim()!=null) {
				MainTools.fetchSpinner(this, et_reptype, params[1].toString().trim(), "ID", "NAME", "RecordCount");
			}
			break;

		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	private void showMesage(){
		Builder builder=new AlertDialog.Builder(NewReportActivity.this);
		builder.setTitle("温馨提示").setMessage("确定发布报表吗?");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (!isEmpty()) {
					addData();
					showLoadDialog();
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create();
		builder.show();
	}

	private boolean isEmpty(){
		if ("".equals(et_sjr.getText().toString().trim())) {
			Toast.makeText(NewReportActivity.this, "报表收件人不能为空！", Toast.LENGTH_SHORT).show();
			return true;
		}else if ("".equals(et_repname.getText().toString().trim())) {
			Toast.makeText(NewReportActivity.this, "报表名称不能为空！", Toast.LENGTH_SHORT).show();
			return true;
		}else if ("请选择".equals(et_reptype.getSelectedItem().toString().trim())) {
			Toast.makeText(NewReportActivity.this, "报表部门不能为空！", Toast.LENGTH_SHORT).show();
			return true;
		}
		else if ("请选择".equals(et_reptime_type.getSelectedItem().toString().trim())) {
			Toast.makeText(NewReportActivity.this, "报表类型不能为空！", Toast.LENGTH_SHORT).show();
			return true;
		}
		return false;
	}
	
	private void showLoadDialog(){
		loadDialog=new ProgressDialog(this);
		loadDialog.setTitle("温馨提示");
		loadDialog.setMessage("报表正在上传中...");
		loadDialog.setCanceledOnTouchOutside(false);
		loadDialog.show();
	}

}

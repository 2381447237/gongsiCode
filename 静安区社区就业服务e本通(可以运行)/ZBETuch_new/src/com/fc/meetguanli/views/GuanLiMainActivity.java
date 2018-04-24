package com.fc.meetguanli.views;

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

import android.R.integer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fc.company.beans.CompanyTask;
import com.fc.first.beans.GetUsrInformation;
import com.fc.first.views.Add_PendworkActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PersonItem;
import com.fc.main.dao.FirstDao;
import com.fc.main.myviews.GetPersonActivity;
import com.fc.main.service.CompanyService;
import com.fc.main.service.MainService;
import com.fc.person.views.NewFuWuXuanXiangActivity;
import com.test.zbetuch_news.R;

public class GuanLiMainActivity extends Activity implements IActivity {

	private EditText et_neirong;
	private EditText et_address;
	private EditText et_time;
	private EditText et_date;
	private EditText et_name;
	private EditText et_sjr;
	private Button bt_tijiao;
	private Button bt_fujian;
	private Button bt_sjr;
	private String str;
	private List<PersonItem> persons;
	private String staffIds;
	private String staffName;
	private int staffId;
	int mYear, mMonth, mDay, mHour, mMinute;
	private static final int SET_GUANLI_DATA = 0;
	int[] ids;
	private String str2;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 100 && data != null) {
			persons = (List<PersonItem>) data.getSerializableExtra("persons");
			String names = "";
			staffIds = "";
			if (persons != null && persons.size() > 0) {

				for (PersonItem item : persons) {
					names += item.getName() + ",";
					staffIds += item.getStaff_id() + ",";
				}

			}
			names = names.trim().length() == 0 ? "" : names.substring(0,
					names.length() - 1);
			staffIds = staffIds.trim().length() == 0 ? "" : staffIds.substring(
					0, staffIds.length() - 1);
			et_sjr.setText(names);

		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_meeting_guanli);

		init();
		initView();
		initListener();

	}

	/**
	 * 页面控件初始化
	 */
	private void initView() {
		// TODO Auto-generated method stub
		et_sjr = (EditText) findViewById(R.id.meet_sjr);
		et_name = (EditText) findViewById(R.id.meet_name);
		et_date = (EditText) findViewById(R.id.meet_data);
		et_time = (EditText) findViewById(R.id.meet_time);
		et_address = (EditText) findViewById(R.id.meet_address);
		et_neirong = (EditText) findViewById(R.id.meet_neirong);
		bt_sjr = (Button) findViewById(R.id.meetsjr_btn);
		bt_fujian = (Button) findViewById(R.id.meetfujian_btn);
		bt_tijiao = (Button) findViewById(R.id.meettj_btn);
		staffName = MainService.STAFFNAME;
		staffId = Integer.valueOf(MainService.STAFFID);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());
		str = formatter.format(curDate);
		et_date.setText(str);
		formatter = new SimpleDateFormat("HH:mm:ss");
		curDate = new Date(System.currentTimeMillis());
		str2 = formatter.format(curDate);
	}

	/**
	 * 页面监听器初始化
	 */
	private void initListener() {
		// TODO Auto-generated method stub
		bt_sjr.setOnClickListener(new MyOnClickListener());
		bt_fujian.setOnClickListener(new MyOnClickListener());
		bt_tijiao.setOnClickListener(new MyOnClickListener());
		et_date.setOnFocusChangeListener(new MyOnFocusChangeListener());
		et_time.setOnFocusChangeListener(new MyOnFocusChangeListener());
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.meetsjr_btn:
				Intent intent = new Intent(GuanLiMainActivity.this,
						GetPersonActivity.class);
				startActivityForResult(intent, 100);
				break;
			case R.id.meetfujian_btn:

				break;
			case R.id.meettj_btn:
				addData();
				Toast.makeText(GuanLiMainActivity.this, "OK", Toast.LENGTH_LONG)
						.show();
				break;
			case R.id.meet_data:
				showDialog(1);
				break;
			case R.id.meet_time:
				showDialog(2);
				break;

			}
		}

	}

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			mHour = hourOfDay;
			mMinute = minute;
			String hh;
			String mm;
			if (hourOfDay <= 9) {
				hh = "0" + mHour;
			} else {
				hh = String.valueOf(mHour);
			}
			if (minute <= 9) {
				mm = "0" + mMinute;
			} else {
				mm = String.valueOf(mMinute);
			}
			et_time.setText(hh + ":" + mm + ":00");
		}
	};

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			String mm;
			String dd;
			if (monthOfYear <= 9) {
				mMonth = monthOfYear + 1;
				mm = "0" + mMonth;
			} else {
				mMonth = monthOfYear + 1;
				mm = String.valueOf(mMonth);
			}
			if (dayOfMonth <= 9) {
				mDay = dayOfMonth;
				dd = "0" + mDay;
			} else {
				mDay = dayOfMonth;
				dd = String.valueOf(mDay);
			}
			et_date.setText(String.valueOf(mYear) + "-" + mm + "-" + dd);
		}
	};

	protected Dialog onCreateDialog(int id) {
		Calendar calendar = Calendar.getInstance();
		switch (id) {
		case 1:
			return new DatePickerDialog(this, mDateSetListener,
					calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DATE));
		case 2:
			return new TimePickerDialog(this, mTimeSetListener, 01, 00, true);
		}
		return null;
	}

	public void addData() {
		// TODO Auto-generated method stub
		String[] arr = staffIds.split(",");
		ids = new int[arr.length];
		System.out.println("==============================" + ids.length);
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, String> params = new HashMap<String, String>();
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonObject = null;

		JSONArray jsarr = new JSONArray();
		JSONObject jsObj = null;
		for (int i = 0; i < ids.length; i++) {
			ids[i] = Integer.parseInt(arr[i]);
			System.out.println("++++++++++++++++++++++++++++" + ids[i]);
			try {
				jsObj = new JSONObject();
				jsonObject = new JSONObject();
				jsonObject.put("ID", 0);
				jsonObject.put("TITLE", et_name.getText().toString().trim());
				jsonObject.put("DOC", et_neirong.getText().toString().trim());
				jsonObject.put("MEETING_TIME", et_date.getText().toString()
						.trim()
						+ "T" + et_time.getText().toString().trim());
				jsonObject.put("MEETING_ADD", et_address.getText().toString()
						.trim());
				jsonObject.put("CREATE_STAFF", staffId);
				jsonObject.put("CREATE_DATE", str + "T" + str2);
				jsonObject.put("RecordCount", 0);
				jsonObject.put("CREATE_STAFF_NAME", staffName);
				jsonObject.put("METTING_STAFFS", null);
				jsObj.put("ID", -1);
				jsObj.put("MASTER_ID", -1);
				jsObj.put("CHECK_STAFF", -1);
				// jsObj.put("CHECK_TIME", "");
				jsObj.put("METTING_STAFF", ids[i]);
				jsObj.put("RecordCount", 0);
				jsarr.put(jsObj);
				jsonObject.put("Checks", jsarr);
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		jsonarray.put(jsonObject);
		params.put("data", jsonarray.toString().trim());
		data.put("data", params);
		System.out.println("````````````" + data);
		CompanyTask task = new CompanyTask(
				CompanyTask.GUANLIACTIVITY_SET_MEET_DATA, data);
		CompanyService.newTask(task);
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

	private class MyOnFocusChangeListener implements OnFocusChangeListener {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			switch (v.getId()) {
			case R.id.meet_time:
				if (hasFocus == true) {
					hideIM(v);
					showDialog(2);
				}
				break;
			case R.id.meet_data:
				if (hasFocus == true) {
					hideIM(v);
					showDialog(1);
				}
				break;
			}
		}

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);

	}

}

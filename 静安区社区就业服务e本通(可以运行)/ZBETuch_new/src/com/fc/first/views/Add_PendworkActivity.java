package com.fc.first.views;

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

import com.fc.company.beans.CompanyTask;
import com.fc.first.beans.PendingWorkInformation;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PersonItem;
import com.fc.main.service.CompanyService;
import com.fc.main.service.MainService;
import com.fc.main.tools.HttpUrls_;
import com.test.zbetuch_news.R;

import android.R.bool;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class Add_PendworkActivity extends Activity implements IActivity {
	EditText et_pendworkstarttimeadd, et_pendworkendtimeadd,
			et_pendworktitleadd, et_pendworkdocadd, et_pendworkstartdateadd,
			et_pendworkenddateadd;
	Button btn_pendworksave, bt_pendworkback;
	ArrayList<PendingWorkInformation> pendworkInfo_list = new ArrayList<PendingWorkInformation>();
	String type = "未完成";
	int mYear, mMonth, mDay, timeFlag, dateFlag, mHour, mMinute;

	List<PersonItem> persons;

	public static final int ADD_PENDWORK = 1;

	// 类型
	private int flag = 0;

	public static final int FLAG_ADDONEPENDWORK = 100;
	public static final int FLAG_ADDMANYPENDWORK = 101;

	private boolean isButtonUserful = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add__pendwork);
		init();
		initView();
		initListener();

		Intent intent = getIntent();
		flag = intent.getIntExtra("flag", 0);
		switch (flag) {
		case Add_PendworkActivity.FLAG_ADDMANYPENDWORK:
			persons = (List<PersonItem>) intent.getSerializableExtra("persons");
			break;
		}

	}

	private boolean checkFrm() {
		if (et_pendworktitleadd.getText().toString().trim().equals("")) {
			Toast.makeText(this, "标题不能为空！", Toast.LENGTH_SHORT).show();
			et_pendworktitleadd.requestFocus();
			return false;
		}
		if (et_pendworkdocadd.getText().toString().trim().equals("")) {
			Toast.makeText(this, "内容不能为空！", Toast.LENGTH_SHORT).show();
			et_pendworkdocadd.requestFocus();
			return false;
		}
		return true;
	}

	private void initListener() {
		btn_pendworksave.setOnClickListener(new MyOnClickListener());
		bt_pendworkback.setOnClickListener(new MyOnClickListener());
		et_pendworkstarttimeadd.setOnClickListener(new MyOnClickListener());
		et_pendworkendtimeadd.setOnClickListener(new MyOnClickListener());
		et_pendworkstartdateadd.setOnClickListener(new MyOnClickListener());
		et_pendworkenddateadd.setOnClickListener(new MyOnClickListener());
		et_pendworkstarttimeadd
				.setOnFocusChangeListener(new MyOnFocusChangeListener());
		et_pendworkendtimeadd
				.setOnFocusChangeListener(new MyOnFocusChangeListener());
		et_pendworkstartdateadd
				.setOnFocusChangeListener(new MyOnFocusChangeListener());
		et_pendworkenddateadd
				.setOnFocusChangeListener(new MyOnFocusChangeListener());

		btn_pendworksave.setOnClickListener(new MyOnClickListener());

	}

	private void initView() {
		et_pendworkstarttimeadd = (EditText) findViewById(R.id.et_pendworkstarttimeadd);
		et_pendworkendtimeadd = (EditText) findViewById(R.id.et_pendworkendtimeadd);
		et_pendworkstartdateadd = (EditText) findViewById(R.id.et_pendworkstartdateadd);
		et_pendworkenddateadd = (EditText) findViewById(R.id.et_pendworkenddateadd);
		et_pendworktitleadd = (EditText) findViewById(R.id.et_pendworktitleadd);
		et_pendworkdocadd = (EditText) findViewById(R.id.et_pendworkdocadd);
		btn_pendworksave = (Button) findViewById(R.id.btn_pendworksave);
		bt_pendworkback = (Button) findViewById(R.id.btn_pendworkback);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date(System.currentTimeMillis());
		String str = formatter.format(curDate);
		et_pendworkstartdateadd.setText(str);
		et_pendworkenddateadd.setText(str);
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
			if (timeFlag == 2) {
				et_pendworkstarttimeadd.setText(hh + ":" + mm);
			} else if (timeFlag == 3) {
				et_pendworkendtimeadd.setText(hh + ":" + mm);
			}
		}
	};
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			if (dateFlag == 0) {
				et_pendworkstartdateadd.setText(new StringBuilder()
						.append(year)
						.append("-")
						.append(

						(monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1)
								: (monthOfYear + 1)).append("-").append(

						(dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth));
			} else if (dateFlag == 1) {
				et_pendworkenddateadd.setText(new StringBuilder()
						.append(year)
						.append("-")
						.append(

						(monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1)
								: (monthOfYear + 1)).append("-").append(

						(dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth));
			}
		}
	};

	protected Dialog onCreateDialog(int id) {
		Calendar calendar = Calendar.getInstance();
		switch (id) {
		case 0:
			return new DatePickerDialog(this, mDateSetListener,
					calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DATE));
		case 1:
			return new DatePickerDialog(this, mDateSetListener,
					calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
					calendar.get(Calendar.DATE));
		case 2:
			return new TimePickerDialog(this, mTimeSetListener, 01, 00, true);
		case 3:
			return new TimePickerDialog(this, mTimeSetListener, 01, 00, true);
		}
		return null;
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
			case R.id.et_pendworkstarttimeadd:
				if (hasFocus == true) {
					timeFlag = 2;
					hideIM(v);
					showDialog(2);
				}
				break;
			case R.id.et_pendworkendtimeadd:
				if (hasFocus == true) {
					timeFlag = 3;
					hideIM(v);
					showDialog(3);
				}
				break;
			case R.id.et_pendworkstartdateadd:
				if (hasFocus == true) {
					dateFlag = 0;
					hideIM(v);
					showDialog(0);
				}
				break;
			case R.id.et_pendworkenddateadd:
				if (hasFocus == true) {
					dateFlag = 1;
					hideIM(v);
					showDialog(1);
				}
				break;
			}
		}

	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_pendworksave:
				if (isButtonUserful && checkFrm()) {
					addPendWork();
					isButtonUserful = false;
				}
				break;
			case R.id.btn_pendworkback:
				finish();
				break;
			case R.id.et_pendworkstarttimeadd:
				timeFlag = 2;
				showDialog(2);
				break;
			case R.id.et_pendworkendtimeadd:
				timeFlag = 3;
				showDialog(3);
				break;
			case R.id.et_pendworkstartdateadd:
				dateFlag = 0;
				showDialog(0);
				break;
			case R.id.et_pendworkenddateadd:
				dateFlag = 1;
				showDialog(1);
				break;
			}
		}
	}

	public void addPendWork() {
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, String> params = new HashMap<String, String>();
		JSONArray jsonarray = new JSONArray();
		JSONObject jsonObject = null;
		switch (flag) {
		case Add_PendworkActivity.FLAG_ADDONEPENDWORK:
			
			jsonObject = new JSONObject();

			try {
				jsonObject.put("ID", "0");
				jsonObject.put("START_TIME", et_pendworkstartdateadd.getText()
						.toString().trim()
						+ " "
						+ et_pendworkstarttimeadd.getText().toString().trim()
						+ ":00");
				jsonObject.put("END_TIME", et_pendworkenddateadd.getText()
						.toString().trim()
						+ " "
						+ et_pendworkendtimeadd.getText().toString().trim()
						+ ":00");
				jsonObject.put("TITLE", et_pendworktitleadd.getText()
						.toString().trim());
				jsonObject.put("DOC", et_pendworkdocadd.getText().toString()
						.trim());
				jsonObject.put("WORK_STAFF", MainService.STAFFID);
				jsonObject.put("TYPE", type.trim());
				jsonarray.put(jsonObject);
				params.put("json", jsonarray.toString().trim());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		case Add_PendworkActivity.FLAG_ADDMANYPENDWORK:
			
			if (persons != null && persons.size() > 0) {
				for (PersonItem item : persons) {
					jsonObject = new JSONObject();

					try {
						jsonObject.put("ID", "0");
						jsonObject.put("START_TIME", et_pendworkstartdateadd
								.getText().toString().trim()
								+ " "
								+ et_pendworkstarttimeadd.getText().toString()
										.trim() + ":00");
						jsonObject.put("END_TIME", et_pendworkenddateadd
								.getText().toString().trim()
								+ " "
								+ et_pendworkendtimeadd.getText().toString()
										.trim() + ":00");
						jsonObject.put("TITLE", et_pendworktitleadd.getText()
								.toString().trim());
						jsonObject.put("DOC", et_pendworkdocadd.getText()
								.toString().trim());
						jsonObject.put("WORK_STAFF", item.getStaff_id());
						jsonObject.put("TYPE", type.trim());
						jsonarray.put(jsonObject);
						params.put("json", jsonarray.toString().trim());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}
			break;
		}
		
		data.put("data", params);
		CompanyTask task = new CompanyTask(
				CompanyTask.ADDPENDWORKACTIVITY_ADD_PENDWORK, data);
		CompanyService.newTask(task);
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case Add_PendworkActivity.ADD_PENDWORK:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				if (value.equalsIgnoreCase("true")) {
					isButtonUserful = true;
					finish();
				}
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}
}

package com.fc.person.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.fc.first.beans.CommitteeInformation;
import com.fc.first.beans.LocationInformation;
import com.fc.main.beans.IActivity;
import com.fc.main.service.PersonService;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.PersonTask;
import com.fc.person.beans.PersonalBaseInformation;
import com.fc.person.beans.RegionInformation;
import com.fc.person.beans.StreetInformation;
import com.test.zbetuch_news.R;

public class PersoninfoActivity2 extends Activity implements IActivity {
	private Activity mContext = this;
	private EditText et_cardnum, et_personName, et_agelow, et_ageup;
	private EditText ed, et_ceshi;
	private Button btn_scan, btn_query, btn_query2;
	private Button btn[];
	private Spinner spinner_country, spinner_street, spinner_juweihui,
			spinner_sex, spinner_PersonMark, spinner_type,
			spinner_current_intent, spinner_current_situation;
	private CheckBox cb_resources;
	private PopupWindow popupwindow;
	private View keyview;
	private KeyboardView keyboardView;
	private View mykeyview;

	ProgressDialog progressDialog;

	private ArrayList<LocationInformation> locationlist = new ArrayList<LocationInformation>();
	private ArrayList<RegionInformation> regionList = new ArrayList<RegionInformation>();
	private ArrayList<StreetInformation> streetList = new ArrayList<StreetInformation>();
	private ArrayList<CommitteeInformation> juweihuiList = new ArrayList<CommitteeInformation>();
	// 性别下拉菜单
	private String spinnersex[];

	ArrayAdapter<StreetInformation> streetAdapter;
	ArrayAdapter<CommitteeInformation> juweihuiAdapter;

	public static final int REFRESH_REGION_SPINNER = 1;
	public static final int REFRESH_STREET_SPINNER = 2;
	public static final int REFRESH_JUWEIHUI_SPINNER = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personquery);
		Intent it = getIntent();
		locationlist = (ArrayList<LocationInformation>) it
				.getSerializableExtra("locationlist");

		initView();

		init();
		initSpinnerData();
		initListener();

	}

	@Override
	protected void onResume() {
		super.onResume();
		initSpinnerCountry();
	}

	@Override
	public void init() {
		PersonService.addActivity(this);
		Intent intent = new Intent("com.fc.person.service.PersonService");
		startService(intent);

	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case PersoninfoActivity2.REFRESH_REGION_SPINNER:
			String regionStr = "";
			if (params[1] != null) {
				regionStr = params[1].toString();
			}
			if (regionStr != null) {
				tranceStrToRegionList(regionStr);
			}
			ArrayAdapter<RegionInformation> countryAdapter = new ArrayAdapter<RegionInformation>(
					this, android.R.layout.simple_spinner_item, regionList);
			countryAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_country.setAdapter(countryAdapter);
			if (spinner_country.getAdapter().getCount() > 1) {
				spinner_country.setSelection(7);
			}
			break;
		case PersoninfoActivity2.REFRESH_STREET_SPINNER:
			String streetStr = "";
			if (params[1] != null) {
				streetStr = params[1].toString();
			}
			if (streetStr != null) {
				tranceStrToStreetList(streetStr);
			}
			Log.i("aaa", "streetStr=="+streetStr);
			streetAdapter = new ArrayAdapter<StreetInformation>(this,
					android.R.layout.simple_spinner_item, streetList);
			streetAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_street.setAdapter(streetAdapter);
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			if (streetList.size() <= 3 && streetList.size() > 1) {
				spinner_street.setSelection(1);
			}

			break;
		case PersoninfoActivity2.REFRESH_JUWEIHUI_SPINNER:
			String juweihuiStr = "";
			if (params[1] != null) {
				juweihuiStr = params[1].toString();
			}
			if (juweihuiStr != null) {
				tranceStrToJuweihuiList(juweihuiStr);
			}
			juweihuiAdapter = new ArrayAdapter<CommitteeInformation>(this,
					android.R.layout.simple_spinner_item, juweihuiList);
			juweihuiAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_juweihui.setAdapter(juweihuiAdapter);

			if (juweihuiList.size() <= 3 && juweihuiList.size() > 1) {
				spinner_juweihui.setSelection(1);
			}
			break;

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		regionList = new ArrayList<RegionInformation>();
		streetList = new ArrayList<StreetInformation>();
		juweihuiList = new ArrayList<CommitteeInformation>();
		et_cardnum = (EditText) findViewById(R.id.et_identity);
		et_cardnum.setInputType(InputType.TYPE_NULL);

		et_personName = (EditText) findViewById(R.id.et_personmingzi);
		et_agelow = (EditText) findViewById(R.id.et_agelow);
		et_ageup = (EditText) findViewById(R.id.et_ageup);
		btn_query = (Button) findViewById(R.id.bt_query);
		btn_query2 = (Button) findViewById(R.id.bt_query2);
		btn_scan = (Button) findViewById(R.id.bt_scanning);
		spinner_sex = (Spinner) findViewById(R.id.spinner_sex);
		spinner_country = (Spinner) findViewById(R.id.spinner_country);
		spinner_street = (Spinner) findViewById(R.id.spinner_street);
		spinner_juweihui = (Spinner) findViewById(R.id.spinner_jiuweihui);
		spinner_PersonMark = (Spinner) findViewById(R.id.spinner_PersonMark);
		spinner_type = (Spinner) findViewById(R.id.spinner_type);
		spinner_current_intent = (Spinner) findViewById(R.id.spinner_current_intent);
		spinner_current_situation = (Spinner) findViewById(R.id.spinner_current_situation);
		cb_resources = (CheckBox) findViewById(R.id.cb_resources);
	}

	/**
	 * 设置下拉框工具类
	 * 
	 * @param spinner
	 * @param dataid
	 */
	private void setSpinner(Spinner spinner, int dataid) {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, dataid, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	/**
	 * 初始化摸底情况下拉框
	 * 
	 * @param value
	 */
	private void initModiSpinner(String value) {
		if (value.trim().equals("登记失业")) {
			setSpinner(spinner_current_situation, R.array.personmodishiye);
		} else if (value.trim().equals("未登记失业")) {
			setSpinner(spinner_current_situation, R.array.personmodiwuye);
		} else if (value.trim().equals("学龄前儿童")) {
			setSpinner(spinner_current_situation, R.array.personmodiwuye);
		} else if (value.trim().equals("请选择")) {
			setSpinner(spinner_current_situation, R.array.personmodiwuye);
		}
	}

	/**
	 * 初始化性别和标识下拉框
	 */
	private void initSpinnerData() {
		// 初始化性别下拉框
		spinnersex = getResources().getStringArray(R.array.spinner_sex);
		ArrayAdapter<String> adapter_sex = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnersex);
		adapter_sex
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_sex.setAdapter(adapter_sex);

		// 初始化标识下拉框
		ArrayAdapter<CharSequence> adapter_mark = ArrayAdapter
				.createFromResource(this, R.array.personmark,
						android.R.layout.simple_spinner_item);
		adapter_mark
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_PersonMark.setAdapter(adapter_mark);

		// 状态
		setSpinner(spinner_type, R.array.personstatus);
		// 当前意向
		setSpinner(spinner_current_intent, R.array.personintention);

	}

	/**
	 * 转化jsonStr为RegionList
	 * 
	 * @param regionStr
	 */
	private void tranceStrToRegionList(String regionStr) {
		regionList.clear();
		regionList.add(new RegionInformation("-1", "请选择", " -1"));
		try {
			if (regionStr != null && regionStr.length() > 0) {
				JSONArray jsonarray = new JSONArray(regionStr);
				int len = jsonarray.length();
				for (int i = 0; i < len; i++) {
					JSONObject object = jsonarray.getJSONObject(i);
					String region_id = object.getString("ID");
					String region_name = object.getString("NAME");
					String region_city = object.getString("CITYID");

					RegionInformation regionInfo = new RegionInformation();
					regionInfo.setRegioId(region_id);
					regionInfo.setRegionName(region_name);
					regionInfo.setCityId(region_city);
					regionList.add(regionInfo);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 转化jsonStr为StreetList
	 * 
	 * @param streetStr
	 */
	private void tranceStrToStreetList(String streetStr) {
		streetList.clear();
		streetList.add(new StreetInformation("-1", "请选择", " -1"));
		try {
			JSONArray jsonarray = new JSONArray(streetStr);
			int len = jsonarray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonarray.getJSONObject(i);
				String street_id = object.getString("ID");
				String street_type = object.getString("NAME");
				String street_region = object.getString("REGIONID");

				StreetInformation streetInfo = new StreetInformation();
				streetInfo.setStreetId(street_id);
				streetInfo.setStreetName(street_type);
				streetInfo.setRegionId(street_region);
				streetList.add(streetInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 转化jsonStr为居委会List
	 * 
	 * @param juweihuiStr
	 */
	private void tranceStrToJuweihuiList(String juweihuiStr) {
		juweihuiList.clear();
		juweihuiList.add(new CommitteeInformation("-1", "请选择", " -1"));

		CommitteeInformation bumingInfo = null;

		try {
			JSONArray jsonarray = new JSONArray(juweihuiStr);
			int len = jsonarray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonarray.getJSONObject(i);
				String committee_id = object.getString("ID");
				String committee_type = object.getString("NAME");
				String committee_street = object.getString("STREETID");

				if (committee_type.trim().equals("不明")) {
					bumingInfo = new CommitteeInformation();
					bumingInfo.setCommitteeId(committee_id);
					bumingInfo.setCommitteeName(committee_type);
					bumingInfo.setStreetId(committee_street);
				} else {
					CommitteeInformation committeeInfo = new CommitteeInformation();
					committeeInfo.setCommitteeId(committee_id);
					committeeInfo.setCommitteeName(committee_type);
					committeeInfo.setStreetId(committee_street);
					juweihuiList.add(committeeInfo);
				}
			}
			if (bumingInfo != null) {
				juweihuiList.add(bumingInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化区县下拉框
	 */
	private void initSpinnerCountry() {
		PersonTask task = new PersonTask(PersonTask.PERSONINFO_GETREGION, null);
		PersonService.newTask(task);
		showDialog();
	}

	/**
	 * 加载事件监听器
	 */
	private void initListener() {
		btn_query.setOnClickListener(new MyButtonClickListener());
		btn_query2.setOnClickListener(new MyButtonClickListener());
		btn_scan.setOnClickListener(new MyButtonClickListener());
		et_cardnum.setOnTouchListener(new MyOnTouchListener());
		spinner_country
				.setOnItemSelectedListener(new MySpinnerItemSeletedListener());
		spinner_street
				.setOnItemSelectedListener(new MySpinnerItemSeletedListener());
		spinner_type
				.setOnItemSelectedListener(new MySpinnerItemSeletedListener());

	}

	/**
	 * 按钮点击事件
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.bt_query:
				// 查询
				String personSFZ = et_cardnum.getText().toString().trim();
				if (personSFZ.equals("")) {
					Toast.makeText(PersoninfoActivity2.this,
							"对不起，您查询的身份证号码不能为空！", Toast.LENGTH_SHORT).show();
				} else if (personSFZ.length() < 18) {
					Toast.makeText(PersoninfoActivity2.this,
							"对不起，您输入的身份证号码不正确！请重新输入。", Toast.LENGTH_SHORT)
							.show();
				} else {
					Intent ientent = new Intent(PersoninfoActivity2.this,
							PersoninfoMainActivity.class);
					ientent.putExtra("sfz", personSFZ);
					ientent.putExtra("sfzCheck", true);
					ientent.putExtra("locationlist", locationlist);
					ientent.putExtra("mysfz", personSFZ);
					startActivityForResult(ientent, 300);
				}
				break;
			case R.id.bt_query2:
				// 判断年龄段 及传入参数
				String ageLow = et_agelow.getText().toString().trim();
				String ageUp = et_ageup.getText().toString().trim();
				// String r = "^[0-9]*[1-9][0-9]*$"　;

				if (!ageLow.equals("")
						&& !ageUp.equals("")
						&& ((Integer.parseInt(ageLow) > Integer.parseInt(ageUp)) || (ageLow
								.length() > 2 || ageUp.length() >= 3))) {
					Toast.makeText(PersoninfoActivity2.this,
							"对不起，您输入的年龄段不正确，请重新输入！", Toast.LENGTH_SHORT).show();
				} else {

					String personName = et_personName.getText().toString()
							.trim();
					Intent intentquery2 = new Intent(PersoninfoActivity2.this,
							PersonqueryListActivity2.class);
					String regionCode = "";
					if (spinner_country.getAdapter() != null
							&& !((RegionInformation) spinner_country
									.getSelectedItem()).getRegioId().trim()
									.equals("-1")) {
						regionCode = ((RegionInformation) spinner_country
								.getSelectedItem()).getRegioId();
					}

					intentquery2.putExtra("regionCode", regionCode);

					String streetCode = "";
					if (spinner_street.getAdapter() != null
							&& !((StreetInformation) spinner_street
									.getSelectedItem()).getStreetId().trim()
									.equals("-1")) {
						streetCode = ((StreetInformation) spinner_street
								.getSelectedItem()).getStreetId();
					}
					intentquery2.putExtra("streetCode", streetCode);

					String juweihuiCode = "";
					if (spinner_juweihui.getAdapter() != null
							&& !((CommitteeInformation) spinner_juweihui
									.getSelectedItem()).getCommitteeId().trim()
									.equals("-1")) {
						juweihuiCode = ((CommitteeInformation) spinner_juweihui
								.getSelectedItem()).getCommitteeId();
					}

					intentquery2.putExtra("juweihuiCode", juweihuiCode);
					intentquery2.putExtra("agelow", ageLow);
					intentquery2.putExtra("ageup", ageUp);

					String personsex = "";
					if (!spinner_sex.getSelectedItem().toString().trim()
							.equals("全部")) {
						personsex = spinner_sex.getSelectedItem().toString();
					}
					intentquery2.putExtra("personsex", personsex);

					intentquery2.putExtra("personname", personName);
					intentquery2.putExtra("locationlist", locationlist);

					String personMark = spinner_PersonMark
							.getSelectedItemPosition() == 0 ? ""
							: spinner_PersonMark.getSelectedItem().toString()
									.trim();
					intentquery2.putExtra("personmark", personMark);

					String type = spinner_type.getSelectedItemPosition() == 0 ? ""
							: spinner_type.getSelectedItem().toString().trim();
					intentquery2.putExtra("TYPE", type);

					String current_situation = spinner_current_situation
							.getSelectedItemPosition() == 0 ? ""
							: spinner_current_situation.getSelectedItem()
									.toString().trim();
					intentquery2.putExtra("Current_situation",
							current_situation);

					String current_intent = spinner_current_intent
							.getSelectedItemPosition() == 0 ? ""
							: spinner_current_intent.getSelectedItem()
									.toString().trim();
					intentquery2.putExtra("Current_intent", current_intent);

					boolean resource = !cb_resources.isChecked();
					intentquery2.putExtra("Resources", "" + resource);

					intentquery2.putExtra("flag",
							PersonqueryListActivity2.FLAG_FROM_PERSONINFO);
					startActivityForResult(intentquery2, 200);
					// startActivity(intentquery2);
				}
				break;
			case R.id.bt_scanning:
				// 扫描
				Intent intent = new Intent();
				intent.setClass(PersoninfoActivity2.this, CaptureActivity.class);
				startActivityForResult(intent, 100);
				break;
			}
		}

	}

	private class MySpinnerItemSeletedListener implements
			OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View view, int arg2,
				long arg3) {
			switch (arg0.getId()) {
			case R.id.spinner_country:
				RegionInformation country = (RegionInformation) spinner_country
						.getSelectedItem();
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("regionId", country.getRegioId());
				PersonTask task = new PersonTask(
						PersonTask.PERSONINFO_GETSTREET, param);
				PersonService.newTask(task);
				break;
			case R.id.spinner_street:
				StreetInformation street = (StreetInformation) spinner_street
						.getSelectedItem();
				Map<String, Object> param2 = new HashMap<String, Object>();
				param2.put("juweihuiId", street.getStreetId());
				PersonTask task2 = new PersonTask(
						PersonTask.PERSONINFO_GETJUWEIHUI, param2);
				PersonService.newTask(task2);
				break;
			case R.id.spinner_type:
				initModiSpinner(spinner_type.getSelectedItem().toString()
						.trim());
				break;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

	// private void showPopupWindow(View v) {
	// if (popupwindow == null) {
	// // 自定义键盘
	// MyKeyBoard();
	// popupwindow = new PopupWindow(mykeyview, LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// }
	// // 使其获得焦点
	// popupwindow.setFocusable(true);
	// // 设置允许在外点击消失
	// popupwindow.setOutsideTouchable(true);
	//
	// // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
	// popupwindow.setBackgroundDrawable(new BitmapDrawable());
	//
	// popupwindow.showAsDropDown(v, 0, 0);
	// et_cardnum.setFocusable(true);
	// et_cardnum.setClickable(true);
	// }

	/**
	 * 自定义键盘，用Button实现 ，调用keyboardView出错，一直木有解决！（待定）
	 */
	// public void MyKeyBoard() {
	// LayoutInflater layoutInflater = LayoutInflater
	// .from(PersoninfoActivity2.this);
	// mykeyview = layoutInflater.inflate(R.layout.mykeyboard, null);
	// btn = new Button[] { (Button) mykeyview.findViewById(R.id.button1),
	// (Button) mykeyview.findViewById(R.id.button2),
	// (Button) mykeyview.findViewById(R.id.button3),
	// (Button) mykeyview.findViewById(R.id.button4),
	// (Button) mykeyview.findViewById(R.id.button5),
	// (Button) mykeyview.findViewById(R.id.button6),
	// (Button) mykeyview.findViewById(R.id.button7),
	// (Button) mykeyview.findViewById(R.id.button8),
	// (Button) mykeyview.findViewById(R.id.button9),
	// (Button) mykeyview.findViewById(R.id.button10),
	// (Button) mykeyview.findViewById(R.id.button11),
	// (Button) mykeyview.findViewById(R.id.button12), };
	// // 数字键1-9
	// for (int i = 0; i < 9; i++) {
	// final int j = i;
	// btn[j].setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// et_cardnum.selectAll();
	// int index = et_cardnum.getSelectionEnd();
	// Editable editable = et_cardnum.getText();
	// editable.insert(index, String.valueOf(j + 1));
	// }
	// });
	// }
	//
	// // 删除键
	// ((Button) mykeyview.findViewById(R.id.button12))
	// .setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// et_cardnum.selectAll();
	// int index = et_cardnum.getSelectionEnd();
	// Editable editable = et_cardnum.getText();
	// if (index == 0) {
	// return;
	// } else {
	// editable.delete(index - 1, index);
	// }
	// }
	// });
	// // 0键
	// mykeyview.findViewById(R.id.button11).setOnClickListener(
	// new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// et_cardnum.selectAll();
	// int index = et_cardnum.getSelectionEnd();
	// Editable editable = et_cardnum.getText();
	// editable.insert(index, "0");
	// }
	// });
	// // X键
	// ((Button) mykeyview.findViewById(R.id.button10))
	// .setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// et_cardnum.selectAll();
	// int index = et_cardnum.getSelectionEnd();
	// Editable editable = et_cardnum.getText();
	// editable.insert(index, "X");
	//
	// }
	// });
	// }

	private class MyOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {
			case R.id.et_identity:
				ed = et_cardnum;
				et_cardnum.setFocusable(true);
				et_cardnum.setEnabled(true);
				et_cardnum.setCursorVisible(true);
				// showPopupWindow(v);
				popupwindow = MainTools.showPopupWindow(mContext, popupwindow,
						et_cardnum);
				popupwindow.showAsDropDown(et_cardnum, 0, 0);
				break;
			}
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100) {// 扫描返回的结果
			if (resultCode == RESULT_OK) {
				String getValue = data.getStringExtra("extra");
				Log.i("121212121212", "21212121212");
				et_cardnum.setText(getValue);
			}
		} else if (requestCode == 200) {// 人员信息列表返回的结果
			if (resultCode == RESULT_OK) {
				Toast.makeText(PersoninfoActivity2.this, "对不起，您的操作超时，请重新登录！",
						Toast.LENGTH_SHORT).show();
			}
		} else if (requestCode == 300) {// 人员基本信息返回的结果
			if (resultCode == RESULT_OK) {
				ArrayList<PersonalBaseInformation> personbaseinfo = (ArrayList<PersonalBaseInformation>) data
						.getExtras().getSerializable("personinfojson");
				String personLevel = data.getExtras().getString("personLevel");
				if (personbaseinfo != null && personbaseinfo.size() == 0) {
					Toast.makeText(PersoninfoActivity2.this, "对不起，查无此人！",
							Toast.LENGTH_SHORT).show();
				}
			}

		}
	}

	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.show();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// finish();
	}
}

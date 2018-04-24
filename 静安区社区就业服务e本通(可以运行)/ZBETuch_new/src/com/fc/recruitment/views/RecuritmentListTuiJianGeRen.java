package com.fc.recruitment.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.company.beans.CompanyTask;
import com.fc.company.beans.JobItem;
import com.fc.company.beans.JobListdAdapter;
import com.fc.first.beans.Center;
import com.fc.gradeate.beans.GradeateInfo;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.CompanyService;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.PersonalBaseInformation;
import com.fc.person.views.CaptureActivity;
import com.fc.recruitment.beans.GeRenTuiJianSFZAdapter;
import com.fc.recruitment.beans.TianjiaListdAdapter;
import com.fc.recruitment.beans.TuiJianListItem;
import com.test.zbetuch_news.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class RecuritmentListTuiJianGeRen extends Activity implements IActivity {

	private int index = 0;
	private JobItem jobInfo;
	private EditText sfz_edt, tempEditText;
	private Button btn_scanf, btn_query, btn_tijian, btn_exit;
	private static String smart_id;
	// private List<TuiJianListItem> tuiJianListItems = new
	// ArrayList<TuiJianListItem>();
	private PopupWindow popupwindow;
	private Map<String, String> data;
	// private GeRenTuiJianSFZAdapter tianjiaListdadapter ;

	private Center center = new Center();
	// 个人的基本信息
	private static ArrayList<PersonalBaseInformation> personinfoJson = new ArrayList<PersonalBaseInformation>();

	private EditText personname, personsex, personborn, personnational,
			personnative, persontype, personcardnum, personintention,
			personedution, personstatus, personstreet, personjuweihui,
			personregisteraddress, personphonenum, personaddress;

	private LinearLayout genRenInfo;

	public static final int SET_GENRENTUIJIAN = 0;

	public static final int SET_TUIJIANGEREN_QUEDING = 1;

	public static final int SET_TUIJIANGEREN_EXIT = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recuritmentlist_tuijiangeren);
		jobInfo = (JobItem) getIntent().getSerializableExtra("info");
		smart_id = getIntent().getStringExtra("master_id");
		init();
		initView();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		CompanyService.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		if (params[1].toString().trim() != null
				&& !"".equals(params[1].toString().trim())) {
			switch (Integer.parseInt(params[0].toString().trim())) {
			case RecuritmentListTuiJianGeRen.SET_GENRENTUIJIAN:
				String string = params[1].toString().trim();
				if (string != null && string.length() > 0) {
					List<PersonalBaseInformation> personalBaseInformations = ParsePersonJson(string);
					if (personalBaseInformations.size() > 0) {
						genRenInfo.setVisibility(View.VISIBLE);
						btn_tijian.setEnabled(true);
					} else {
						genRenInfo.setVisibility(View.GONE);
					}
					initPersonInfo();
				} else {
					Toast.makeText(RecuritmentListTuiJianGeRen.this, "查无此人信息",
							Toast.LENGTH_SHORT).show();
				}
				break;

			case RecuritmentListTuiJianGeRen.SET_TUIJIANGEREN_QUEDING:
				if ("true".equals(params[1].toString().trim())) {
					Toast.makeText(RecuritmentListTuiJianGeRen.this, " 推荐成功",
							Toast.LENGTH_SHORT).show();
				} else if ("False".equals(params[1].toString().trim())) {
					Toast.makeText(RecuritmentListTuiJianGeRen.this,
							"已推荐\n无需重复推荐", Toast.LENGTH_SHORT).show();
				}
				break;

			default:
				break;
			}
		}
	}

	private void initView() {
		sfz_edt = (EditText) this.findViewById(R.id.et_sfz);
		sfz_edt.setInputType(InputType.TYPE_NULL);
		sfz_edt.setOnTouchListener(new MyOnTouchListener());

		genRenInfo = (LinearLayout) this.findViewById(R.id.genrenInfo);

		btn_scanf = (Button) this.findViewById(R.id.btn_scanf);
		btn_scanf.setOnClickListener(new MyOnClickListener());
		btn_query = (Button) this.findViewById(R.id.btn_query);
		btn_query.setOnClickListener(new MyOnClickListener());

		personname = (EditText) this.findViewById(R.id.textView_addpersonname);
		personsex = (EditText) this.findViewById(R.id.textView_addpersonsex);
		personborn = (EditText) this.findViewById(R.id.textView_addpersonborn);
		persontype = (EditText) this.findViewById(R.id.textView_addpersontype);
		personnational = (EditText) this
				.findViewById(R.id.textView_addpersonnational);
		personnative = (EditText) this
				.findViewById(R.id.textView_addpersonnative);
		personcardnum = (EditText) this
				.findViewById(R.id.textView_addpersoncardnum);
		personintention = (EditText) this
				.findViewById(R.id.textView_addpersonintention);
		personedution = (EditText) this
				.findViewById(R.id.textView_addpersonedution);
		personstatus = (EditText) this
				.findViewById(R.id.textView_addpersonstatus);
		personstreet = (EditText) this
				.findViewById(R.id.textView_addpersonstreet);
		personjuweihui = (EditText) this
				.findViewById(R.id.textView_addpersonjuweihui);
		personregisteraddress = (EditText) this
				.findViewById(R.id.textView_addpersonregisteraddress);
		personphonenum = (EditText) this
				.findViewById(R.id.textView_addpersonphonenum);
		personaddress = (EditText) this
				.findViewById(R.id.textView_addpersonaddress);

		btn_tijian = (Button) this.findViewById(R.id.btnQueDing);
		btn_tijian.setOnClickListener(new MyOnClickListener());
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_scanf:
				// 扫描
				Intent intent = new Intent();
				intent.setClass(RecuritmentListTuiJianGeRen.this,
						CaptureActivity.class);
				startActivityForResult(intent, 100);
				break;

			case R.id.btn_query:
				// 查询
				String personSFZ = sfz_edt.getText().toString().trim();
				if (personSFZ.equals("")) {
					Toast.makeText(RecuritmentListTuiJianGeRen.this,
							"对不起，您查询的身份证号码不能为空！", Toast.LENGTH_SHORT).show();
				} else if (personSFZ.length() < 18) {
					Toast.makeText(RecuritmentListTuiJianGeRen.this,
							"对不起，您输入的身份证号码不正确！请重新输入。", Toast.LENGTH_SHORT)
							.show();
				} else {
					index = 0;
					Map<String, Object> params = new HashMap<String, Object>();
					data = new HashMap<String, String>();
					data.put("sfz", sfz_edt.getText().toString().trim());
					data.put("page", "" + index);
					data.put("rows", "15");
					params.put("data", data);
					
					
					CompanyTask task = new CompanyTask(
							CompanyTask.RECRUITMENT_RECURITMENTLISTDETAILACITYITY_GERENTUIJIAN,
							params);
					CompanyService.newTask(task);
				}

				break;

			case R.id.btnQueDing:
				// 查询
				String SFZ = sfz_edt.getText().toString().trim();
				if (SFZ.equals("")) {
					Toast.makeText(RecuritmentListTuiJianGeRen.this,
							"对不起，您查询的身份证号码不能为空！", Toast.LENGTH_SHORT).show();
				} else if (SFZ.length() < 18) {
					Toast.makeText(RecuritmentListTuiJianGeRen.this,
							"对不起，您输入的身份证号码不正确！请重新输入。", Toast.LENGTH_SHORT)
							.show();
				} else {
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, String> data = new HashMap<String, String>();
					data.put("master_id", "" + smart_id);
					data.put("code", jobInfo.getJobno());
					data.put("sfz", sfz_edt.getText().toString().trim());
					data.put("page", "" + index);
					data.put("rows", "15");
					params.put("data", data);
					
					CompanyTask task = new CompanyTask(
							CompanyTask.RECRUITMENT_RECURITMENTLISTDETAILACITYITY_GERENTUIJIAN_QUEDING,
							params);
					CompanyService.newTask(task);
				}
				break;

			default:
				break;
			}
		}

	}

	private class MyOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {
			case R.id.et_sfz:
				tempEditText = sfz_edt;
				sfz_edt.setFocusable(true);
				sfz_edt.setEnabled(true);
				sfz_edt.setCursorVisible(true);
				// showPopupWindow(v);
				popupwindow = MainTools.showPopupWindow(
						RecuritmentListTuiJianGeRen.this, popupwindow, sfz_edt);
				popupwindow.showAsDropDown(sfz_edt, 0, 0);
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
				sfz_edt.setText(getValue);
			}
		}
	}

	private List<TuiJianListItem> parse(String str) {
		List<TuiJianListItem> listItems = new ArrayList<TuiJianListItem>();
		try {
			JSONArray jsonArray = new JSONArray(str);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				TuiJianListItem item = new TuiJianListItem();
				item.setID(jsonObject.getInt("ID"));
				item.setCREATE_DATE(jsonObject.getString("CREATE_DATE"));
				item.setCREATE_STAFF(jsonObject.getInt("CREATE_STAFF"));
				item.setJOB_CODE(jsonObject.getString("JOB_CODE"));
				item.setMASTER_ID(jsonObject.getInt("MASTER_ID"));
				item.setRecordCount(jsonObject.getInt("RecordCount"));
				item.setSFZ(jsonObject.getString("SFZ"));
				listItems.add(item);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listItems;

	}

	/**
	 * 将查询到的json字符串转为毕业生对象
	 * 
	 * @param value
	 * @return
	 */
	public ArrayList<PersonalBaseInformation> ParsePersonJson(String json) {
		JSONArray jsonarray;
		try {
			jsonarray = new JSONArray(json);
			int len = jsonarray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonarray.getJSONObject(i);
				PersonalBaseInformation personbaseInfo = new PersonalBaseInformation();
				String personIdCard = object.getString("SFZ");
				String personName = object.getString("NAME");
				String personSex = object.getString("SEX");
				String personNational = object.getString("NATIONS");
				String personMobilePhone = object.getString("CONTACT_NUMBER");
				String personNative = object.getString("NATIVE");
				String personLevelMsg = object.getString("LevelMsg");
				String personBorn = object.getString("BIRTH_DATE");
				String personBeizhu = object.getString("Remark");
				String type = object.getString("TYPE");
				String road = object.getString("ROAD");
				String nong = object.getString("LANE");
				String number = object.getString("NO");
				String room = object.getString("ROOM");
				String nowroad = object.getString("NOW_ROAD");
				String nownong = object.getString("NOW_LANE");
				String nownumber = object.getString("NOW_NO");
				String nowroom = object.getString("NOW_ROOM");
				String status = object.getString("Current_situation");
				String intention = object.getString("Current_intent");
				// 转化时间格式
				String substring = personBorn.substring(0,
						personBorn.indexOf("T"));
				String personEducation = object.getString("CULTURAL_CODE");
				JSONObject jsonCenter = object.getJSONObject("Center");
				String cardtype = jsonCenter.getString("Q证件类型");
				String cardnum = jsonCenter.getString("Q证件号码");
				String personstreet = jsonCenter.getString("Q户口所属街道");
				String personjuwei = jsonCenter.getString("Q居委会");
				String address = jsonCenter.getString("Q户口地址");
				center.setQcardtype(cardtype);
				center.setQaddress(nowroad + nownong + nownumber + nowroom);
				center.setQcardnum(cardnum);
				center.setQstreet(personstreet);
				center.setQjuweihui(personjuwei);
				center.setQhujidizhi(road + nong + number + room);
				personbaseInfo.setCenter(center);
				personbaseInfo.setPersonName(personName);
				personbaseInfo.setPersonCardId(personIdCard);
				personbaseInfo.setPersonBorn(substring);
				personbaseInfo.setPersonSex(personSex);
				personbaseInfo.setPersonNational(personNational);
				personbaseInfo.setPersonType(type);
				personbaseInfo.setPersonCurrentStatus(status);
				personbaseInfo.setPersonIntention(intention);
				personbaseInfo.setPersonEducation(personEducation);
				personbaseInfo.setPersonMobilePhone(personMobilePhone);
				personbaseInfo.setPersonNativePlace(personNative);
				personbaseInfo.setPersonLevelmsg(personLevelMsg);
				personbaseInfo.setPersonNational(personNational);
				personbaseInfo.setPersonBeizhu(personBeizhu);
				personbaseInfo.setPersonRoad(road);
				personbaseInfo.setPersonNong(nong);
				personbaseInfo.setPersonNumber(number);
				personbaseInfo.setPersonRoom(room);
				personbaseInfo.setPersonNowRoad(nowroad);
				personbaseInfo.setPersonNowNong(nownong);
				personbaseInfo.setPersonNowNumber(nownumber);
				personbaseInfo.setPersonNowRoom(nowroom);

				String time = jsonCenter.getString("CREATE_DATE");
				String[] times = time.split("T");
				personbaseInfo.setUpdateTime(times[0]
						+ " "
						+ times[1].substring(
								0,
								times[1].indexOf('.') > 0 ? times[1]
										.indexOf('.') : times[1].length()));

				personbaseInfo.setCompare_result(jsonCenter
						.getString("COMPARE_RESULT"));
				personinfoJson.add(personbaseInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return personinfoJson;
	}

	/**
	 * 初始化个人信息
	 */
	private void initPersonInfo() {
		for (int i = 0; i < personinfoJson.size(); i++) {
			personname.setText(personinfoJson.get(i).getPersonName());
			personsex.setText(personinfoJson.get(i).getPersonSex());
			personborn.setText(personinfoJson.get(i).getPersonBorn());
			personnational.setText(personinfoJson.get(i).getPersonNational()
					.trim());
			personaddress.setText(personinfoJson.get(i).getCenter()
					.getQaddress());
			personnative.setText(personinfoJson.get(i).getPersonNativePlace());
			personcardnum.setText(personinfoJson.get(i).getPersonCardId());
			personedution.setText(personinfoJson.get(i).getPersonEducation());

			persontype.setText(personinfoJson.get(i).getPersonType());

			personphonenum
					.setText(personinfoJson.get(i).getPersonMobilePhone());
			personstatus
					.setText(personinfoJson.get(i).getPersonCurrentStatus());
			personintention.setText(personinfoJson.get(i).getPersonIntention());
			personstreet
					.setText(personinfoJson.get(i).getCenter().getQstreet());
			personjuweihui.setText(personinfoJson.get(i).getCenter()
					.getQjuweihui());
			personregisteraddress.setText(personinfoJson.get(i).getCenter()
					.getQhujidizhi());
		}
	}
}

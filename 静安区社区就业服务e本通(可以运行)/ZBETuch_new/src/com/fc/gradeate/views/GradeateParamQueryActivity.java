package com.fc.gradeate.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.company.beans.CompanyTask;
import com.fc.first.beans.CommitteeInformation;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.SpinnerItem;
import com.fc.main.service.CompanyService;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.StreetInformation;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class GradeateParamQueryActivity extends Activity implements IActivity {
	private Activity mContext = this;
	private Spinner sp_jiedao, sp_juwei, sp_sex, sp_wenhua, sp_teshu, sp_qiye,
			sp_hangye, sp_gangwei, sp_xinzi, sp_jiben, sp_juti, sp_zhidao,
			sp_jiuye;
	private Button btn_query;
	private EditText ed_startage, ed_endage;
	public static final int REFRESH_COMPROPERTY = 1;
	public static final int REFRESH_INDUSTRYCATAGORY = 2;
	public static final int REFRESH_STREETLIST_QUERY = 3;
	public static final int REFRESH_JUWEIHUILIST_QUERY = 4;
	ArrayAdapter<StreetInformation> streetAdapter;
	ArrayAdapter<CommitteeInformation> juweihuiAdapter;
	private ArrayList<CommitteeInformation> juweihuiList = new ArrayList<CommitteeInformation>();
	private ArrayList<StreetInformation> streetList = new ArrayList<StreetInformation>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gradeate_paramquery);
		init();
		initListener();

	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case GradeateParamQueryActivity.REFRESH_COMPROPERTY:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				if (value.length() > 0) {
					MainTools.fetchSpinner(mContext, sp_qiye, value,
							"compropertyid", "compropertyname");
				}
			}
			break;
		case GradeateParamQueryActivity.REFRESH_INDUSTRYCATAGORY:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				if (value.length() > 0) {
					MainTools.fetchSpinner(mContext, sp_hangye, value, "id",
							"name");
				}
			}
			break;
		case GradeateParamQueryActivity.REFRESH_STREETLIST_QUERY:
			String streetStr = "";
			if (params[1] != null) {
				streetStr = params[1].toString();
			}
			if (streetStr != null) {
				tranceStrToStreetList(streetStr);
			}
			streetAdapter.notifyDataSetChanged();
			break;
		case GradeateParamQueryActivity.REFRESH_JUWEIHUILIST_QUERY:
			String juweihuiStr = "";
			if (params[1] != null) {
				juweihuiStr = params[1].toString();
			}
			if (juweihuiStr != null) {
				tranceStrToJuweihuiList(juweihuiStr);
			}
			juweihuiAdapter.notifyDataSetChanged();
			sp_juwei.setSelection(0);
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
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

		try {
			JSONArray jsonarray = new JSONArray(juweihuiStr);
			int len = jsonarray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonarray.getJSONObject(i);
				String committee_id = object.getString("ID");
				String committee_type = object.getString("NAME");
				String committee_street = object.getString("STREETID");

				CommitteeInformation committeeInfo = new CommitteeInformation();
				committeeInfo.setCommitteeId(committee_id);
				committeeInfo.setCommitteeName(committee_type);
				committeeInfo.setStreetId(committee_street);
				juweihuiList.add(committeeInfo);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 初始化下拉框
	 */
	private void initSpinner() {
		streetList.add(new StreetInformation("-1", "请选择", " -1"));
		juweihuiList.add(new CommitteeInformation("-1", "请选择", " -1"));

		streetAdapter = new ArrayAdapter<StreetInformation>(this,
				android.R.layout.simple_spinner_item, streetList);
		streetAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_jiedao.setAdapter(streetAdapter);

		juweihuiAdapter = new ArrayAdapter<CommitteeInformation>(this,
				android.R.layout.simple_spinner_item, juweihuiList);
		juweihuiAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_juwei.setAdapter(juweihuiAdapter);

		String regionId = "310108";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("regionId", regionId);
		CompanyTask task3 = new CompanyTask(
				CompanyTask.GRADEATE_GradeateParamQueryActivity_GET_STREETLIST,
				params);
		CompanyService.newTask(task3);
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
		sp_jiedao = (Spinner) findViewById(R.id.sp_jiedao);
		sp_juwei = (Spinner) findViewById(R.id.sp_juwei);
		sp_sex = (Spinner) findViewById(R.id.sp_sex);
		sp_wenhua = (Spinner) findViewById(R.id.sp_wenhua);
		sp_teshu = (Spinner) findViewById(R.id.sp_teshu);
		sp_qiye = (Spinner) findViewById(R.id.sp_qiye);
		sp_hangye = (Spinner) findViewById(R.id.sp_hangye);
		sp_gangwei = (Spinner) findViewById(R.id.sp_gangwei);
		sp_xinzi = (Spinner) findViewById(R.id.sp_xinzi);
		sp_jiben = (Spinner) findViewById(R.id.sp_jiben);
		sp_juti = (Spinner) findViewById(R.id.sp_juti);
		sp_zhidao = (Spinner) findViewById(R.id.sp_zhidao);
		sp_jiuye = (Spinner) findViewById(R.id.sp_jiuye);
		btn_query = (Button) findViewById(R.id.btn_query);
		ed_startage = (EditText) findViewById(R.id.ed_startage);
		ed_endage = (EditText) findViewById(R.id.ed_endage);
		CompanyTask task1 = new CompanyTask(
				CompanyTask.GRADEATE_GradeateParamQueryActivity_GET_GANGWEILEIBIE,
				null);
		CompanyService.newTask(task1);
		CompanyTask task2 = new CompanyTask(
				CompanyTask.GRADEATE_GradeateParamQueryActivity_GET_HANGYELEIBIE,
				null);
		CompanyService.newTask(task2);
		initSpinner();
	}

	private void initListener() {
		sp_jiben.setOnItemSelectedListener(new MyOnItemSelectedListener());
		sp_jiedao.setOnItemSelectedListener(new MyOnItemSelectedListener());
		btn_query.setOnClickListener(new MyOnClickListener());
	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_query:

				Map<String, String> data = new HashMap<String, String>();

				data.put("STREET_ID", ((StreetInformation) sp_jiedao
						.getSelectedItem()).getStreetId());
				data.put("COMMITTEE_ID", ((CommitteeInformation) sp_juwei
						.getSelectedItem()).getCommitteeId());
				dataSelectSpinner(data, "SEX", sp_sex);
				dataSelectSpinner(data, "EDU", sp_wenhua);
				dataSelectEditText(data, "START_AGE", ed_startage);
				dataSelectEditText(data, "END_AGE", ed_endage);
				dataSelectSpinner(data, "MARK", sp_teshu);
				if(sp_qiye.getSelectedItem()==null){
					data.put("COMPROPERTY_ID","-1");
				}else{
					data.put("COMPROPERTY_ID",
							"" + ((SpinnerItem) sp_qiye.getSelectedItem()).getId());
				}
				if(sp_hangye.getSelectedItem()==null){
					data.put("INDUSTARY_CATEGORY_ID","-1");
				}else{
				data.put(
						"INDUSTARY_CATEGORY_ID",
						""
								+ ((SpinnerItem) sp_hangye.getSelectedItem())
										.getId());
				}
				dataSelectSpinner(data, "JOB_CATEGORY", sp_gangwei);
				dataSelectSpinner(data, "SALARY", sp_xinzi);
				dataSelectSpinner(data, "BASE_SITUATION", sp_jiben);
				dataSelectSpinner(data, "DETAIL_SITUATION", sp_juti);

				if (sp_zhidao.getSelectedItem().toString().trim().equals("是")) {
					data.put("CHECK_GUIDE", "true");
				} else if (sp_zhidao.getSelectedItem().toString().trim()
						.equals("否")) {
					data.put("CHECK_GUIDE", "false");
				} else {
					data.put("CHECK_GUIDE", "");
				}

				if (sp_jiuye.getSelectedItem().toString().trim().equals("是")) {
					data.put("CHECK_RECOMMEND", "true");
				} else if (sp_jiuye.getSelectedItem().toString().trim()
						.equals("否")) {
					data.put("CHECK_RECOMMEND", "false");
				} else {
					data.put("CHECK_RECOMMEND", "");
				}
				data.put("year", getIntent().getStringExtra("year"));
				IActivity activity = (IActivity) CompanyService
						.getActivityByName("GradeateListActivity");
				if (activity != null) {
					activity.refresh(GradeateListActivity.REFRESH_BY_PARAM,
							data);
				}
				
				Log.e("2017/10/17","查询data=="+data);
				
				mContext.finish();
			}
		}
	}

	/**
	 * 判断数据里是否有值，没有值的话存取空字符串
	 * 
	 * @param temp
	 *            存取的map对象
	 * @param idName
	 *            map的键值
	 * @param spName
	 *            spinner的id
	 */
	private void dataSelectSpinner(Map<String, String> temp, String idName,
			Spinner spName) {
		if (spName.getSelectedItem().toString().trim().equals("请选择")) {
			temp.put(idName, "");
		} else {
			temp.put(idName, spName.getSelectedItem().toString().trim());
		}
	}

	/**
	 * 判断数据里是否有值，没有值的话存取为-1
	 * 
	 * @param temp
	 *            存取的map对象
	 * @param idName
	 *            map的键值
	 * @param edName
	 *            EditText的id
	 */
	private void dataSelectEditText(Map<String, String> temp, String idName,
			EditText edName) {
		if (edName.getText().toString().trim().equals("")) {
			temp.put(idName, "-1");
		} else {
			temp.put(idName, edName.getText().toString().trim());
		}
	}

	private class MyOnItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			switch (parent.getId()) {
			case R.id.sp_jiben:
				String value = sp_jiben.getSelectedItem().toString().trim();
				if (value.trim().equals("请选择")) {
					MainTools.setSpinner(mContext, sp_juti,
							R.array.gradeate_empty);
				} else if (value.trim().equals("已就业")) {
					MainTools.setSpinner(mContext, sp_juti,
							R.array.gradeate_detailSituation_yjy);
				} else if (value.trim().equals("未就业")) {
					MainTools.setSpinner(mContext, sp_juti,
							R.array.gradeate_detailSituation_wjy);
				} else if (value.trim().equals("暂不要求就业")) {
					MainTools.setSpinner(mContext, sp_juti,
							R.array.gradeate_detailSituation_zbjy);
				} else if (value.trim().equals("其他")) {
					MainTools.setSpinner(mContext, sp_juti,
							R.array.gradeate_detailSituation_qt);
				}
				break;
			case R.id.sp_jiedao:
				StreetInformation street = (StreetInformation) sp_jiedao
						.getSelectedItem();
				if (!street.getStreetId().trim().equals("-1")) {
					Map<String, Object> param2 = new HashMap<String, Object>();
					param2.put("streetId", street.getStreetId());
					CompanyTask task = new CompanyTask(
							CompanyTask.GRADEATE_GradeateParamQueryActivity_GET_JUWEIHUILIST,
							param2);
					CompanyService.newTask(task);
				} else {
					juweihuiList.clear();
					juweihuiList.add(new CommitteeInformation("-1", "请选择",
							" -1"));
					juweihuiAdapter.notifyDataSetChanged();
				}
				break;
			default:
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}

	}
}

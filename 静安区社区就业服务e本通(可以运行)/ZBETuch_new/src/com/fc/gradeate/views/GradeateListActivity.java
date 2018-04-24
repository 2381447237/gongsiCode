package com.fc.gradeate.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fc.company.beans.CompanyTask;
import com.fc.first.beans.CommitteeInformation;
import com.fc.gradeate.beans.GradeateAdapter;
import com.fc.gradeate.beans.GradeateInfo;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.CompanyService;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.StreetInformation;
import com.test.zbetuch_news.R;

public class GradeateListActivity extends Activity implements IActivity,
		OnPullDownListener {

	private TextView lblNum;
	private EditText txtIdCard, txtNameText;
	private Button btnQuery, btnNew, btnParamQuery;
	private PullDownView pdvGradeate;
	private ListView lvGradeate;
	private PopupWindow popupwindow;
	private Spinner yearSpinner;
	private View mykeyview;
	private Button btn[];
	private List<GradeateInfo> gradeateList = new ArrayList<GradeateInfo>();
	private GradeateAdapter adapter;
	private Activity mContext = this;
	private Map<String, String> data;
	private int index = 0;
	private static String streetStr = "";
	private static GradeateInfo infos = null;

	public static final int REFRESH_GRADEATELIST = 1;
	public static final int REFRESH_FRM = 2;
	public static final int REFRESH_BY_PARAM = 3;
	public static final int REFRESH_STREETLIST = 4;
	private long time = 0;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gradeate_list);
		init();
		initView();
		initValue();
		initListener();
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case GradeateListActivity.REFRESH_GRADEATELIST:
			if (params[1] != null) {
				String returnValue = params[1].toString().trim();
				List<GradeateInfo> newList = parseStrToGradeateList(returnValue);
				if (newList != null && newList.size() > 0) {
					gradeateList.addAll(newList);
				}
				adapter.notifyDataSetChanged();
				pdvGradeate.notifyDidMore();

				// 修改显示的总人数
				if (gradeateList != null && gradeateList.size() > 0) {
					lblNum.setText("共有" + gradeateList.get(0).getRecordCount()
							+ "人");
				} else {
					lblNum.setText("共有0人");
					Toast.makeText(mContext, "查无此人", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case REFRESH_FRM:
			if (params[1] != null && !"".equals(params[1].toString().trim())) {
				infos = (GradeateInfo) params[1];
			}
			if (gradeateList.size() > 0) {
				for (GradeateInfo gradeateInfo : gradeateList) {
					if (gradeateInfo.getSfz().equals(infos.getSfz())) {
						gradeateInfo.setAddress(infos.getAddress());
						gradeateInfo.setCommitteeId(infos.getCommitteeId());
						gradeateInfo.setContactNumber(infos.getContactNumber());
						gradeateInfo.setCreatDate(infos.getCreatDate());
						gradeateInfo.setCreatStaff(infos.getCreatStaff());
						gradeateInfo.setEdu(infos.getEdu());
						gradeateInfo.setId(infos.getId());
						gradeateInfo.setMark(infos.getMark());
						gradeateInfo.setName(infos.getName());
						gradeateInfo.setNations(infos.getNations());
						gradeateInfo.setNowAddress(infos.getNowAddress());
						gradeateInfo.setPoliticalStatus(infos
								.getPoliticalStatus());
						gradeateInfo.setRecordCount(infos.getRecordCount());
						gradeateInfo.setSchool(infos.getSchool());
						gradeateInfo.setSex(infos.getSex());
						gradeateInfo.setSfz(infos.getSfz());
						gradeateInfo.setSpecialty(infos.getSpecialty());
						gradeateInfo.setStreetId(infos.getStreetId());
						gradeateInfo.setSurvey(infos.getSurvey());
						gradeateInfo.setUpdateDate(infos.getUpdateDate());
						gradeateInfo.setUpdateStaff(infos.getUpdateStaff());
					}
				}
			}
			adapter.notifyDataSetChanged();
			pdvGradeate.notifyDidMore();
			// btnQuery.performClick();
			break;
		case GradeateListActivity.REFRESH_BY_PARAM:
			if (params[1] != null) {
				data = (Map<String, String>) params[1];
				index = 0;
				gradeateList.clear();
				adapter.notifyDataSetChanged();
				getPageList(index);
			}
			break;

		case GradeateListActivity.REFRESH_STREETLIST:

			if (params[1] != null) {
				streetStr = params[1].toString();
			}
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void onMore() {
		if (data == null) {
			pdvGradeate.notifyDidMore();
			return;
		}
		index++;
		getPageList(index);
	}

	/**
	 * 将jsonStr转为毕业生集合
	 * 
	 * @param str
	 * @return
	 */
	private List<GradeateInfo> parseStrToGradeateList(String str) {
		List<GradeateInfo> list = new ArrayList<GradeateInfo>();

		try {
			if (str.length() > 0) {
				JSONArray jsonArray = new JSONArray(str);
				if (jsonArray != null && jsonArray.length() > 0) {
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject object = jsonArray.optJSONObject(i);
						GradeateInfo info = new GradeateInfo();
						info.setId(object.getInt("ID"));
						info.setName(object.getString("NAME").trim());
						info.setSfz(object.getString("SFZ").trim());
						info.setSex(object.getString("SEX").trim());
						info.setNations(object.getString("NATIONS").trim());
						info.setPoliticalStatus(object.getString(
								"POLITICAL_STATUS").trim());
						info.setEdu(object.getString("EDU").trim());
						info.setSchool(object.getString("SCHOOL").trim());
						info.setSpecialty(object.getString("SPECIALTY").trim());
						info.setStreetId(object.getInt("STREET_ID"));
						info.setCommitteeId(object.getInt("COMMITTEE_ID"));
						info.setAddress(object.getString("ADDRESS").trim());
						info.setNowAddress(object.getString("NOW_ADDRESS")
								.trim());
						info.setContactNumber(object
								.getString("CONTACT_NUMBER").trim());
						info.setMark(object.getString("MARK").trim());
						info.setSurvey(object.getString("SURVEY").trim());
						info.setCreatDate(object.getString("CREAT_DATE").trim());
						info.setCreatStaff(object.getInt("CREAT_STAFF"));
						info.setUpdateDate(object.getString("UPDATE_DATE")
								.trim());
						info.setUpdateStaff(object.getInt("UPDATE_STAFF"));
						info.setRecordCount(object.getInt("RecordCount"));
						list.add(info);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	// private void showPopupWindow(View v) {
	// if (popupwindow == null) {
	// // 自定义键盘
	// MyKeyBoard(txtIdCard);
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
	// txtIdCard.setFocusable(true);
	// txtIdCard.setClickable(true);
	// }

	// public void MyKeyBoard(final EditText et_cardnum) {
	// LayoutInflater layoutInflater = LayoutInflater.from(mContext);
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

	/**
	 * 页面控件初始化
	 */
	private void initView() {
		txtIdCard = (EditText) findViewById(R.id.txtIdCard);
		txtNameText = (EditText) findViewById(R.id.txt_name);
		btnQuery = (Button) findViewById(R.id.btnQuery);
		btnNew = (Button) findViewById(R.id.btnNew);
		pdvGradeate = (PullDownView) findViewById(R.id.pdvGradeate);
		lblNum = (TextView) findViewById(R.id.lblNum);
		yearSpinner = (Spinner) findViewById(R.id.year_sp);
		btnParamQuery = (Button) findViewById(R.id.btnParamQuery);

		lvGradeate = pdvGradeate.getListView();

		txtIdCard.setInputType(InputType.TYPE_NULL);

		adapter = new GradeateAdapter(mContext, gradeateList);
		lvGradeate.setAdapter(adapter);

		pdvGradeate.setShowFooter();
		pdvGradeate.setHideHeader();

		pdvGradeate.notifyDidMore();

	}

	private void initValue() {
		String year = sdf.format(new Date());
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 5; i >= 0; i--) {
			list.add((Integer.parseInt(year) - i));
		}
		ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(mContext,
				android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		yearSpinner.setAdapter(adapter);
		yearSpinner.setSelection(list.size() - 1);
	}

	/**
	 * 页面监听器初始化
	 */
	private void initListener() {
		txtIdCard.setOnTouchListener(new MyOnTouchListener());
		pdvGradeate.setOnPullDownListener(this);
		lvGradeate.setOnItemClickListener(new MyOnItemClickListener());
		btnQuery.setOnClickListener(new MyOnClickListener());
		btnNew.setOnClickListener(new MyOnClickListener());
		btnParamQuery.setOnClickListener(new MyOnClickListener());
	}

	/**
	 * 查询某页数据
	 * 
	 * @param index
	 */
	private void getPageList(int index) {
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("page", "" + index);
		data.put("rows", "15");
		params.put("data", data);
		
	    Log.e("2017/10/20","查询条件=="+data);
		
		CompanyTask task = new CompanyTask(
				CompanyTask.GRADEATELISTACTIVITY_GET_GRADEATELIST, params);
		CompanyService.newTask(task);
	}

	private class MyOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {
			case R.id.txtIdCard:
				txtIdCard.setFocusable(true);
				txtIdCard.setEnabled(true);
				txtIdCard.setCursorVisible(true); // 允许光标可见
				txtIdCard.requestFocus();
				// showPopupWindow(v);
				popupwindow = MainTools.showPopupWindow(mContext, popupwindow,
						txtIdCard);
				popupwindow.showAsDropDown(txtIdCard, 0, 0);
				break;
			}
			return false;
		}
	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {

			infos = gradeateList.get(position - 1);
			Intent intent = new Intent(mContext, GradeateMainActivity.class);
			intent.putExtra("info", infos);
			intent.putExtra("streetStr", streetStr);
			startActivity(intent);
			GradeatePersonInfoActivity.gradeateId = infos.getId();
		}

	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnQuery:
				long currrnt_time = System.currentTimeMillis();
				System.out
						.println("currrnt_time-time=" + (currrnt_time - time));
				if (currrnt_time - time < 2000) {
					Toast.makeText(GradeateListActivity.this, "数据查询中...",
							Toast.LENGTH_SHORT).show();
					time = currrnt_time;
					return;
				}
				if (txtIdCard.getText().toString().trim().length() == 18
						|| txtIdCard.getText().toString().trim().length() == 0) {
					index = 0;
					gradeateList.clear();
					adapter.notifyDataSetChanged();
					data = new HashMap<String, String>();
					data.put("sfz", txtIdCard.getText().toString().trim());
					data.put("name", txtNameText.getText().toString().trim());
					data.put("year", yearSpinner.getSelectedItem().toString());
					getPageList(index);
				} else {
					Toast.makeText(mContext, "身份证号输入不正确！ 请重新输入......",
							Toast.LENGTH_LONG).show();
					return;
				}

				if ("".equals(streetStr)) {
					getStreetInfo();
				}
				break;
			case R.id.btnNew:
				GradeatePersonInfoActivity.gradeateId = 0;
				Intent intent = new Intent(mContext, GradeateMainActivity.class);
				startActivity(intent);
				break;
			case R.id.btnParamQuery:
				if ("".equals(streetStr)) {
					getStreetInfo();
				}
				Intent paramQueryIntent = new Intent(mContext,
						GradeateParamQueryActivity.class);
				paramQueryIntent.putExtra("streetStr", streetStr);
				paramQueryIntent.putExtra("year", yearSpinner.getSelectedItem()
						.toString());
				startActivity(paramQueryIntent);
				break;
			}
		}

	}

	private void getStreetInfo() {
		String regionId = "310108";// 闸北区id
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("regionId", regionId);
		CompanyTask task = new CompanyTask(
				CompanyTask.GRADENTPERSONINFOACTIVITY_GET_STREETLIST, params);
		CompanyService.newTask(task);
	}
}

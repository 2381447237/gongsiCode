package com.fc.gradeate.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.fc.company.beans.CompanyTask;
import com.fc.first.beans.CommitteeInformation;
import com.fc.gradeate.beans.GradeateInfo;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.StreetInformation;
import com.fc.zbetuch_sm.R;

public class GradeatePersonInfoActivity extends Activity implements IActivity {

	private Activity mContext = this;
	private EditText txtName, txtIdCard, txtSchool, txtSpeciality,
			txtAddressDetail, txtLiveAddress, txtPhone, txtPhone2;
	private Spinner cboSex, cboNation, cboPolityStyle, cboEducationLevel,
			cboStreet, cboJuWei;
	private LinearLayout llEspecial;
	private Button btnSave;
	private PopupWindow popupwindow;
	private View mykeyview;
	private Button btn[];
	private ArrayList<StreetInformation> streetList = new ArrayList<StreetInformation>();
	private ArrayList<CommitteeInformation> juweihuiList = new ArrayList<CommitteeInformation>();

	/**
	 * 毕业生Id
	 */
	public static int gradeateId = 0;

	private GradeateInfo info = null;
	private int streetId = 0;
	public static int commiteeId = 0;

	public static final int REFRESH_STREETLIST = 1;
	public static final int REFRESH_JUWEIHUILIST = 2;
	public static final int REFRESH_PERSONINFO = 4;
	public static final int SAVE_GRADEATEPERSON = 5;
	public static final int REFRESH_GRADEATEPERSONINFO = 6;
	public static Map<Integer, String> map=new HashMap<Integer, String>();
	ArrayAdapter<StreetInformation> streetAdapter;
	ArrayAdapter<CommitteeInformation> juweihuiAdapter;
	public static Map<String, String> maps=new HashMap<String, String>();
	StreetInformation street;
	String streetStr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gradeate_personinfo);
		Intent intent = getIntent();
		info = (GradeateInfo) intent.getSerializableExtra("info");
		if (info != null) {
			streetId = info.getStreetId();
			commiteeId = info.getCommitteeId();
		}
		streetStr=intent.getStringExtra("streetStr");
		System.out.println("streetStr===="+streetStr);
		init();
		initView();
		initListener();
		if (info != null) {
			fretchFrmByGradeateInfo(info);
		}
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);

	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case GradeatePersonInfoActivity.REFRESH_STREETLIST:
//			String streetStr = "";
//			if (params[1] != null) {
//				streetStr = params[1].toString();
//			}
//			if (streetStr != null) {
//				tranceStrToStreetList(streetStr);
//			}
//
//			streetAdapter.notifyDataSetChanged();
//			setStreetSelect(cboStreet, streetId);
			break;
		case GradeatePersonInfoActivity.REFRESH_JUWEIHUILIST:
			String juweihuiStr = "";
			if (params[1] != null) {
				juweihuiStr = params[1].toString();
				maps.put(street.getStreetId(), juweihuiStr);
			}
			if (juweihuiStr != null) {
				tranceStrToJuweihuiList(juweihuiStr);
			}

			juweihuiAdapter.notifyDataSetChanged();

			setCommitteeSelect(cboJuWei, commiteeId);
			break;
		case GradeatePersonInfoActivity.REFRESH_PERSONINFO:
			if (params[1] != null) {
				String personInfoString = params[1].toString().trim();
				fretchFrmByPersonInfo(personInfoString);
			}
			break;
		case GradeatePersonInfoActivity.SAVE_GRADEATEPERSON:

			if (params[1] != null && params[1].toString().trim().length() > 0) {
				gradeateId = Integer.valueOf(params[1].toString());
				setIdCardAndNameNotUseful();
				Toast.makeText(mContext, "保存成功！", Toast.LENGTH_SHORT).show();
//				IActivity activity = (IActivity) CompanyService
//						.getActivityByName("GradeateListActivity");
//				if(activity!=null){
//					activity.refresh(GradeateListActivity.REFRESH_FRM);
//				}
				Map<String, Object> param=new HashMap<String, Object>();
				param.put("infos", info);
				CompanyTask task=new CompanyTask(CompanyTask.GRADEATE_EDITWORKMARKACTIVITY, param);
				CompanyService.newTask(task);
				
			} else {
				Toast.makeText(mContext, "保存失败！", Toast.LENGTH_SHORT).show();
			}
			break;
		case GradeatePersonInfoActivity.REFRESH_GRADEATEPERSONINFO:
			if (params[1] != null) {
				String value = params[1].toString().trim();
				if (value.trim().length() != 0 && !value.trim().equals("[]")) {
					info = parseStrToGradeateInfo(value);
					if (info != null) {
						streetId = info.getStreetId();
						commiteeId = info.getCommitteeId();
						gradeateId = info.getId();
						fretchFrmByGradeateInfo(info);
//						refreshOtherFrm();
					}
				} else {
					getPersonInfo();
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

	/**
	 * 刷新其他页面
	 */
	private void refreshOtherFrm() {
		IActivity activity = null;
		activity = (IActivity) CompanyService
				.getActivityByName("GradeateApirationActivity");
		if (activity != null) {
			activity.refresh(GradeateApirationActivity.REFRESH_FRM);
		}
		activity = (IActivity) CompanyService
				.getActivityByName("GradeateWorkMarkActivity");
		if (activity != null) {
			activity.refresh(GradeateWorkMarkActivity.REFRESH_FRM);
		}
		activity = (IActivity) CompanyService
				.getActivityByName("GradeateWorkGrideActivity");
		if (activity != null) {
			activity.refresh(GradeateWorkGrideActivity.REFRESH_FRM);
		}
	}

	/**
	 * 将查询到的json字符串转为毕业生对象
	 * 
	 * @param value
	 * @return
	 */
	private GradeateInfo parseStrToGradeateInfo(String value) {
		GradeateInfo info = null;
		if (value != null && value.trim().length() > 0) {
			try {
				JSONArray jsonArray = new JSONArray(value);
				if (jsonArray != null && jsonArray.length() > 0) {
					JSONObject object = jsonArray.optJSONObject(0);
					info = new GradeateInfo();
					info.setId(object.getInt("ID"));
					info.setName(object.getString("NAME").trim());
					info.setSfz(object.getString("SFZ").trim());
					info.setSex(object.getString("SEX").trim());
					info.setNations(object.getString("NATIONS").trim());
					info.setPoliticalStatus(object
							.getString("POLITICAL_STATUS").trim());
					info.setEdu(object.getString("EDU").trim());
					info.setSchool(object.getString("SCHOOL").trim());
					info.setSpecialty(object.getString("SPECIALTY").trim());
					info.setStreetId(object.getInt("STREET_ID"));
					info.setCommitteeId(object.getInt("COMMITTEE_ID"));
					info.setAddress(object.getString("ADDRESS").trim());
					info.setNowAddress(object.getString("NOW_ADDRESS").trim());
					info.setContactNumber(object.getString("CONTACT_NUMBER")
							.trim());
					info.setMark(object.getString("MARK").trim());
					info.setSurvey(object.getString("SURVEY").trim());
					info.setCreatDate(object.getString("CREAT_DATE").trim());
					info.setCreatStaff(object.getInt("CREAT_STAFF"));
					info.setUpdateDate(object.getString("UPDATE_DATE").trim());
					info.setUpdateStaff(object.getInt("UPDATE_STAFF"));
					info.setRecordCount(object.getInt("RecordCount"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return info;
	}

	/**
	 * 根据毕业生信息填充页面
	 * 
	 * @param info
	 */
	private void fretchFrmByGradeateInfo(GradeateInfo info) {
		txtIdCard.setText(info.getSfz().trim());
		txtName.setText(info.getName());
		setSpinnerSelect(cboSex, info.getSex().trim());
		setSpinnerSelect(cboNation, info.getNations().trim());
		setSpinnerSelect(cboPolityStyle, info.getPoliticalStatus().trim());
		setSpinnerSelect(cboEducationLevel, info.getEdu().trim());
		setStreetSelect(cboStreet, streetId);
		txtSchool.setText(info.getSchool().trim());
		txtSpeciality.setText(info.getSpecialty().trim());
		txtAddressDetail.setText(info.getAddress().trim());
		txtLiveAddress.setText(info.getNowAddress().trim());
		String phoneNum = info.getContactNumber().trim();
		String[] nums = phoneNum.split(",");		
		txtPhone.setText(nums[0].trim());
		if(nums.length>1){
			txtPhone2.setText(nums[1].trim());
		}
		String[] marks = info.getMark().trim().split(",");
		for (String str : marks) {
			for (int i = 0; i < llEspecial.getChildCount(); i++) {
				CheckBox box = (CheckBox) llEspecial.getChildAt(i);
				if (box.getTag().toString().trim().equals(str.trim())) {
					box.setChecked(true);
				}
			}
		}
		setIdCardAndNameNotUseful();
		
	}
	
	/**
	 * 设置身份证和姓名不能修改
	 */
	private void setIdCardAndNameNotUseful(){
		txtIdCard.setEnabled(false);
		txtIdCard.setBackgroundResource(R.drawable.txtbgnouse);
		txtName.setEnabled(false);
		txtName.setBackgroundResource(R.drawable.txtbgnouse);
		cboStreet.setEnabled(false);
		cboJuWei.setEnabled(false);
	}

	/**
	 * 根据查询到的个人基本信息填充页面
	 */
	private void fretchFrmByPersonInfo(String personinfoStr) {

		if (personinfoStr != null && personinfoStr.trim().length() > 0
				&& !personinfoStr.trim().equals("[null]")) {
			try {
				JSONArray jsonArray = new JSONArray(personinfoStr);
				if (jsonArray != null && jsonArray.length() > 0) {
					JSONObject object = jsonArray.optJSONObject(0);
					txtName.setText(object.getString("NAME").trim());
					setSpinnerSelect(cboSex, object.getString("SEX").trim());
					txtPhone.setText(object.getString("CONTACT_NUMBER").trim());
					streetId = object.getInt("STREET_ID");
					setStreetSelect(cboStreet, streetId);
					commiteeId = object.getInt("COMMITTEE_ID");
					txtAddressDetail.setText(object.getString("ROAD").trim()
							+ object.getString("LANE").trim()
							+ object.getString("NO").trim()
							+ object.getString("ROOM").trim());
					txtLiveAddress.setText(object.getString("NOW_ROAD").trim()
							+ object.getString("NOW_LANE").trim()
							+ object.getString("NOW_NO").trim()
							+ object.getString("NOW_ROOM").trim());
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 根据名称设定下拉框的选中项
	 * 
	 * @param spinner
	 * @param value
	 */
	private void setSpinnerSelect(Spinner spinner, String value) {
		for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
			if (spinner.getAdapter().getItem(i).toString().trim()
					.equals(value.trim())) {
				spinner.setSelection(i);
				break;
			}
		}
	}

	/**
	 * 设置选中的街道
	 * 
	 * @param spinner
	 * @param streetId
	 */
	private void setStreetSelect(Spinner spinner, int streetId) {
		for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
			if (((StreetInformation) spinner.getAdapter().getItem(i))
					.getStreetId().trim().equals("" + streetId)) {
				spinner.setSelection(i);
				break;
			}
		}
	}

	/**
	 * 设置选中的居委
	 * 
	 * @param spinner
	 * @param committeeId
	 */
	private void setCommitteeSelect(Spinner spinner, int committeeId) {
		for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
			if (((CommitteeInformation) spinner.getAdapter().getItem(i))
					.getCommitteeId().trim().equals("" + committeeId)) {
				spinner.setSelection(i);
				break;
			}
		}
	}

	/**
	 * 页面控件初始化
	 */
	private void initView() {
		txtName = (EditText) findViewById(R.id.txtName);
		txtIdCard = (EditText) findViewById(R.id.txtIdCard);
		txtSchool = (EditText) findViewById(R.id.txtSchool);
		txtSpeciality = (EditText) findViewById(R.id.txtSpeciality);
		txtAddressDetail = (EditText) findViewById(R.id.txtAddressDetail);
		txtLiveAddress = (EditText) findViewById(R.id.txtLiveAddress);
		txtPhone = (EditText) findViewById(R.id.txtPhone);
		txtPhone2 = (EditText) findViewById(R.id.txtPhone2);
		cboSex = (Spinner) findViewById(R.id.cboSex);
		cboNation = (Spinner) findViewById(R.id.cboNation);
		cboPolityStyle = (Spinner) findViewById(R.id.cboPolityStyle);
		cboEducationLevel = (Spinner) findViewById(R.id.cboEducationLevel);
		cboStreet = (Spinner) findViewById(R.id.cboStreet);
		cboJuWei = (Spinner) findViewById(R.id.cboJuWei);
		llEspecial = (LinearLayout) findViewById(R.id.llEspecial);
		btnSave = (Button) findViewById(R.id.btnSave);

		txtIdCard.setInputType(InputType.TYPE_NULL);

		initSpinner();
	}

	/**
	 * 页面监听器初始化
	 */
	private void initListener() {
		txtIdCard.setOnTouchListener(new MyOnTouchListener());
		cboStreet
				.setOnItemSelectedListener(new MySpinnerItemSelectedListener());
		btnSave.setOnClickListener(new MyOnClickListener());
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
	 * 初始化下拉框
	 */
	private void initSpinner() {
		setSpinner(cboSex, R.array.gradeate_sex);
		setSpinner(cboNation, R.array.gradeate_nation);
		setSpinner(cboPolityStyle, R.array.gradeate_polityStyle);
		setSpinner(cboEducationLevel, R.array.gradeate_educationLevel);

		streetList.add(new StreetInformation("-1", "请选择", " -1"));
		juweihuiList.add(new CommitteeInformation("-1", "请选择", " -1"));

		streetAdapter = new ArrayAdapter<StreetInformation>(this,
				android.R.layout.simple_spinner_item, streetList);
		streetAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cboStreet.setAdapter(streetAdapter);

		juweihuiAdapter = new ArrayAdapter<CommitteeInformation>(this,
				android.R.layout.simple_spinner_item, juweihuiList);
		juweihuiAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cboJuWei.setAdapter(juweihuiAdapter);
		
		if (streetStr != null) {
			tranceStrToStreetList(streetStr);
		}

		streetAdapter.notifyDataSetChanged();
		setStreetSelect(cboStreet, streetId);
		
//		String regionId = "310108";// 闸北区id
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("regionId", regionId);
//		CompanyTask task = new CompanyTask(
//				CompanyTask.GRADENTPERSONINFOACTIVITY_GET_STREETLIST, params);
//		CompanyService.newTask(task);
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

	private void showPopupWindow(View v) {
		if (popupwindow == null) {
			// 自定义键盘
			MyKeyBoard(txtIdCard);
			popupwindow = new PopupWindow(mykeyview, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
		}
		// 使其获得焦点
		popupwindow.setFocusable(true);
		// 设置允许在外点击消失
		popupwindow.setOutsideTouchable(true);

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupwindow.setBackgroundDrawable(new BitmapDrawable());

		popupwindow.showAsDropDown(v, 0, 0);
		txtIdCard.setFocusable(true);
		txtIdCard.setClickable(true);
		popupwindow.setOnDismissListener(new MyOnDismissListener());
	}

	/**
	 * 自定义键盘，用Button实现 ，调用keyboardView出错，一直木有解决！（待定）
	 */
	public void MyKeyBoard(final EditText et_cardnum) {
		LayoutInflater layoutInflater = LayoutInflater.from(mContext);
		mykeyview = layoutInflater.inflate(R.layout.mykeyboard, null);
		btn = new Button[] { (Button) mykeyview.findViewById(R.id.button1),
				(Button) mykeyview.findViewById(R.id.button2),
				(Button) mykeyview.findViewById(R.id.button3),
				(Button) mykeyview.findViewById(R.id.button4),
				(Button) mykeyview.findViewById(R.id.button5),
				(Button) mykeyview.findViewById(R.id.button6),
				(Button) mykeyview.findViewById(R.id.button7),
				(Button) mykeyview.findViewById(R.id.button8),
				(Button) mykeyview.findViewById(R.id.button9),
				(Button) mykeyview.findViewById(R.id.button10),
				(Button) mykeyview.findViewById(R.id.button11),
				(Button) mykeyview.findViewById(R.id.button12), };
		// 数字键1-9
		for (int i = 0; i < 9; i++) {
			final int j = i;
			btn[j].setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					et_cardnum.selectAll();
					int index = et_cardnum.getSelectionEnd();
					Editable editable = et_cardnum.getText();
					editable.insert(index, String.valueOf(j + 1));
				}
			});
		}

		// 删除键
		((Button) mykeyview.findViewById(R.id.button12))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_cardnum.selectAll();
						int index = et_cardnum.getSelectionEnd();
						Editable editable = et_cardnum.getText();
						if (index == 0) {
							return;
						} else {
							editable.delete(index - 1, index);
						}
					}
				});
		// 0键
		mykeyview.findViewById(R.id.button11).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_cardnum.selectAll();
						int index = et_cardnum.getSelectionEnd();
						Editable editable = et_cardnum.getText();
						editable.insert(index, "0");
					}
				});
		// X键
		((Button) mykeyview.findViewById(R.id.button10))
				.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						et_cardnum.selectAll();
						int index = et_cardnum.getSelectionEnd();
						Editable editable = et_cardnum.getText();
						editable.insert(index, "X");

					}
				});
	}

	/**
	 * 页面验证
	 * 
	 * @return
	 */
	private boolean checkFrm() {
		if (txtIdCard.getText().toString().trim().length() != 18) {
			Toast.makeText(mContext, "身份证号必须为18位！", Toast.LENGTH_SHORT).show();
			txtIdCard.requestFocus();
			return false;
		}
		if (txtName.getText().toString().trim().length() == 0) {
			Toast.makeText(mContext, "姓名不能为空！", Toast.LENGTH_SHORT).show();
			txtName.requestFocus();
			return false;
		}
		if (cboSex.getSelectedItem().toString().trim().equals("请选择")) {
			Toast.makeText(mContext, "请选择性别！", Toast.LENGTH_SHORT).show();
			cboSex.requestFocus();
			return false;
		}
		if (cboNation.getSelectedItem().toString().trim().equals("请选择")) {
			Toast.makeText(mContext, "请选择民族！", Toast.LENGTH_SHORT).show();
			cboNation.requestFocus();
			return false;
		}
		if (cboPolityStyle.getSelectedItem().toString().trim().equals("请选择")) {
			Toast.makeText(mContext, "请选择政治面貌！", Toast.LENGTH_SHORT).show();
			cboPolityStyle.requestFocus();
			return false;
		}
		if (cboEducationLevel.getSelectedItem().toString().trim().equals("请选择")) {
			Toast.makeText(mContext, "请选择文化程度！", Toast.LENGTH_SHORT).show();
			cboEducationLevel.requestFocus();
			return false;
		}
		if (txtSchool.getText().toString().trim().length() == 0) {
			Toast.makeText(mContext, "请输入就读学校！", Toast.LENGTH_SHORT).show();
			txtSchool.requestFocus();
			return false;
		}
		if (txtSpeciality.getText().toString().trim().length() == 0) {
			Toast.makeText(mContext, "请选择所学专业！", Toast.LENGTH_SHORT).show();
			txtSpeciality.requestFocus();
			return false;
		}
		if (cboStreet.getSelectedItem().toString().trim().equals("请选择")) {
			Toast.makeText(mContext, "请选择街镇！", Toast.LENGTH_SHORT).show();
			cboStreet.requestFocus();
			return false;
		}
		if (cboJuWei.getSelectedItem().toString().trim().equals("请选择")) {
			Toast.makeText(mContext, "请选择居委会！", Toast.LENGTH_SHORT).show();
			cboJuWei.requestFocus();
			return false;
		}
		if (txtAddressDetail.getText().toString().trim().length() == 0) {
			Toast.makeText(mContext, "请输入详细地址！", Toast.LENGTH_SHORT).show();
			txtAddressDetail.requestFocus();
			return false;
		}
		if (txtLiveAddress.getText().toString().trim().length() == 0) {
			Toast.makeText(mContext, "请输入居住地址！", Toast.LENGTH_SHORT).show();
			txtLiveAddress.requestFocus();
			return false;
		}
		if (!(txtPhone.getText().toString().trim().length() == 8 || txtPhone
				.getText().toString().trim().length() == 11)) {
			Toast.makeText(mContext, "电话号码必须是8位或11位", Toast.LENGTH_SHORT)
					.show();
			txtPhone.requestFocus();
			return false;
		}
		if (!(txtPhone2.getText().toString().trim().length() == 8
				|| txtPhone2.getText().toString().trim().length() == 11 || txtPhone2
				.getText().toString().trim().length() == 0)) {
			Toast.makeText(mContext, "电话号码必须是8位或11位", Toast.LENGTH_SHORT)
					.show();
			txtPhone2.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * 从个人信息表查询个人信息
	 */
	private void getPersonInfo() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sfz", txtIdCard.getText().toString().trim());
		CompanyTask task = new CompanyTask(
				CompanyTask.GRADENTPERSONINFOACTIVITY_GET_PERSONINFO, params);
		CompanyService.newTask(task);
	}

	/**
	 * 查询毕业生个人信息
	 */
	private void getGradeatePersonInfo() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("sfz", txtIdCard.getText().toString().trim());
		data.put("page", "0");
		data.put("rows", "2");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.GRADEATEPERSONINFOACTIVITY_GET_PERSONINFO, params);
		CompanyService.newTask(task);
	}

	/**
	 * 保存毕业生信息
	 */
	private void saveGradeatePerson() {
		Map<String, String> data = new HashMap<String, String>();
		data.put("ID", "" + gradeateId);
		data.put("NAME", txtName.getText().toString().trim());
		data.put("SFZ", txtIdCard.getText().toString().trim());
		data.put("SEX", cboSex.getSelectedItem().toString().trim());
		data.put("NATIONS", cboNation.getSelectedItem().toString().trim());
		data.put("POLITICAL_STATUS", cboPolityStyle.getSelectedItem()
				.toString().trim());
		data.put("EDU", cboEducationLevel.getSelectedItem().toString().trim());
		data.put("SCHOOL", txtSchool.getText().toString().trim());
		data.put("SPECIALTY", txtSpeciality.getText().toString().trim());
		data.put("STREET_ID",
				((StreetInformation) cboStreet.getSelectedItem()).getStreetId());
		data.put("COMMITTEE_ID", ((CommitteeInformation) cboJuWei
				.getSelectedItem()).getCommitteeId());
		data.put("ADDRESS", txtAddressDetail.getText().toString().trim());
		data.put("NOW_ADDRESS", txtLiveAddress.getText().toString().trim());
		String phoneNum=txtPhone.getText().toString().trim();
		if(txtPhone2.getText().toString().trim().length()!=0){
			phoneNum+=","+txtPhone2.getText().toString().trim();
		}
		data.put("CONTACT_NUMBER", phoneNum);

		String mark = "";
		for (int i = 0; i < llEspecial.getChildCount(); i++) {
			CheckBox box = (CheckBox) llEspecial.getChildAt(i);
			if (box.isChecked()) {
				mark += box.getTag() + ",";
			}
		}
		if (mark.length() > 0) {
			mark = mark.substring(0, mark.length() - 1);
		}
		data.put("MARK", mark);
		data.put("SURVEY", "");

		info.setAddress(txtAddressDetail.getText().toString().trim());
		info.setCommitteeId(Integer.parseInt(((CommitteeInformation) cboJuWei
				.getSelectedItem()).getCommitteeId()));
		info.setContactNumber(phoneNum);
		info.setCreatDate(info.getCreatDate());
		info.setCreatStaff(info.getCreatStaff());
		info.setEdu(cboEducationLevel.getSelectedItem().toString().trim());
		info.setId(gradeateId);
		info.setMark(mark);
		info.setName(txtName.getText().toString().trim());
		info.setNations(cboNation.getSelectedItem().toString().trim());
		info.setNowAddress(txtLiveAddress.getText().toString().trim());
		info.setPoliticalStatus(cboPolityStyle.getSelectedItem()
				.toString().trim());
		info.setRecordCount(info.getRecordCount());
		info.setSchool(txtSchool.getText().toString().trim());
		info.setSex(cboSex.getSelectedItem().toString().trim());
		info.setSfz(txtIdCard.getText().toString().trim());
		info.setSpecialty(txtSpeciality.getText().toString().trim());
		info.setStreetId(Integer.parseInt(((StreetInformation) cboStreet.getSelectedItem()).getStreetId()));
		info.setSurvey("");
		info.setUpdateDate(info.getUpdateDate());
		info.setUpdateStaff(info.getUpdateStaff());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.GRADENTPERSONINFOACTIVITY_SAVE_GRADEATEPERSON,
				params);
		CompanyService.newTask(task);
	}

	private class MyOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (v.getId()) {
			case R.id.txtIdCard:
				txtIdCard.setFocusable(true);
				txtIdCard.setEnabled(true);
				txtIdCard.setCursorVisible(true);
				txtIdCard.requestFocus();
				// showPopupWindow(v);
				popupwindow = MainTools.showPopupWindow(mContext, popupwindow,
						txtIdCard, new MyOnDismissListener());
				popupwindow.showAsDropDown(txtIdCard, 0, 0);
				break;
			}
			return false;
		}
	}

	private class MyOnDismissListener implements OnDismissListener {

		@Override
		public void onDismiss() {
			// 输入身份证后，只有在新建的时候会调用个人信息
			if (txtIdCard.getText().toString().trim().length() != 18) {
				Toast.makeText(mContext, "身份证号必须为18位！", Toast.LENGTH_SHORT)
						.show();
				txtIdCard.requestFocus();
				return;
			}
			if (GradeatePersonInfoActivity.gradeateId == 0) {
				getGradeatePersonInfo();
			}
		}

	}

	private class MySpinnerItemSelectedListener implements
			OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch (arg0.getId()) {
			case R.id.cboStreet:
				street = (StreetInformation) cboStreet
						.getSelectedItem();
				if (!street.getStreetId().trim().equals("-1")) {
					if(maps.get(street.getStreetId())!=null&&!"".equals(maps.get(street.getStreetId()))){
						System.out.println("maps is a ");
						Map<String, Object> param2 = new HashMap<String, Object>();
						param2.put("streetId", maps.get(street.getStreetId()));
						CompanyTask task = new CompanyTask(
								CompanyTask.GRADENTPERSONINFOACTIVITY_GET_JUWEIHUILIST1,
								param2);
						CompanyService.newTask(task);
					} else{
						Map<String, Object> param2 = new HashMap<String, Object>();
						param2.put("streetId", street.getStreetId());
						CompanyTask task = new CompanyTask(
								CompanyTask.GRADENTPERSONINFOACTIVITY_GET_JUWEIHUILIST,
								param2);
						CompanyService.newTask(task);
					}
					
				}
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}

	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnSave:
				if (checkFrm()) {
					AlertDialog.Builder dialog = new AlertDialog.Builder(
							mContext);
					dialog.setTitle("保存信息提示")
							.setMessage("您确定保存此毕业生信息吗？")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setCancelable(true)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();
											saveGradeatePerson();
										}
									}).setNegativeButton("取消", null).show();
				}
				break;
			}
		}

	}

}

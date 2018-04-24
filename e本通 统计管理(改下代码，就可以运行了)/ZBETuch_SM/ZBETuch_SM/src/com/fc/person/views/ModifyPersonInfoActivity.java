package com.fc.person.views;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.fc.first.beans.Center;
import com.fc.first.beans.CommitteeInformation;
import com.fc.first.views.FirstPageActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PersonInfo;
import com.fc.main.myservices.PersonService;
import com.fc.person.beans.PersonMark;
import com.fc.person.beans.PersonTask;
import com.fc.person.beans.PersonUpdataImg;
import com.fc.person.beans.PersonalBaseInformation;
import com.fc.person.beans.StreetInformation;
import com.fc.zbetuch_sm.R;

/**
 * 修改人员的基本信息
 * 
 * @author hxl
 * 
 */
public class ModifyPersonInfoActivity extends Activity implements IActivity {
	private EditText tv_personNative, tv_personaname, tv_personborn, tv_phone,
			tv_cardnum, tv_road, tv_nong, tv_number, tv_room, tv_hujiroad,
			tv_hujinong, tv_hujinumber, tv_hujiroom, tv_remark;
	private Spinner tv_personsex, tv_personnational, tv_tye, tv_education,
			tv_status, tv_intention, tv_personstreet, tv_juweihui;
	private CheckBox boxjuzhu, boxhuji;
	private ImageView img_personHead, img_student, img_qihang, img_lingjiuye,
			img_jiuyekunnan, img_jiedu, img_xingjie;
	private Button btn_paizhao, btn_shangchuan, btn_save, btn_modifymark,btn_image;
	private Button btn_btn_modify_mark;

	public static final String SDCARD_ROOT_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath();// 路径
	public static final String SAVE_PATH_IN_SDCARD = "/personBaseInfo.data/"; // 图片及其他数据保存文件夹
	public static final String IMAGE_CAPTURE_NAME = "personInfo.png"; // 照片名称

	// 存放个人图片信息
	private PersonUpdataImg person;
	// 传过来的身份证
	private String personSFZ;
	// 传过来的个人信息
	private String personInfoJson;
	// 个人信息集合
	private ArrayList<PersonalBaseInformation> personInfoJsonList = new ArrayList<PersonalBaseInformation>();
	// 启动系统自带相机的响应码
	private static final int REQUEST_CODE_TAKE_PICTURE = 1;
	//选择图片
	private static final int SELECT_PICTURE=2;
	// 街道信息
	private List<StreetInformation> streetList = new ArrayList<StreetInformation>();
	// 个人基本信息
	private PersonalBaseInformation personbaseInfo = new PersonalBaseInformation();
	// 居委会信息
	private ArrayList<CommitteeInformation> juweihuiList = new ArrayList<CommitteeInformation>();
	// 个人标识信息
	private ArrayList<PersonMark> personmarkList = new ArrayList<PersonMark>();
	// 存放标识信息的集合
	private HashMap<String, ImageView> HM_mark;

	public static final int REFRESH_SPINNERSTREET = 1;
	public static final int REFRESH_SPINNERJUWEIHUI = 2;
	public static final int REFRESH_PERSONIMAGE = 3;
	public static final int REFRESH_PERSONMARK = 4;
	public static final int UPDATE_PERSONIMG = 5;
	public static final int UPDATE_PERSONINFO = 6;
    public static final int RESULT_OK_INFO=7;
    public static final int REFRESH_INFO=8;
    
    private HorizontalScrollView horizontalScrollView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置界面无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addperson_modify_c);

		// 获得从别的activity传过来的数据
		Intent itemIntent = this.getIntent();
		Bundle bundlePersoninfo = itemIntent.getExtras();
		personSFZ = bundlePersoninfo.getString("sfz");
		personInfoJson = bundlePersoninfo.getString("personbasejson");

		init();
		initView();
		initPersonMark();
		initSpinnerData();
		tranceStrToPersonInfo(personInfoJson);
		setListener();
		initPersonInfo();

		// 获取个人照片
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sfz", personSFZ);
		PersonTask task1 = new PersonTask(
				PersonTask.MODIFYPERSONINFOACTIVITY_GET_PERSONIMAGE, params);
		PersonService.newTask(task1);

		// 获取个人标识
		Map<String, Object> params2 = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("sfz", personSFZ);
		params2.put("data", data);
		PersonTask task2 = new PersonTask(
				PersonTask.MODIFYPERSONINFOACTIVITY_GET_PERSONMARK, params2);
		PersonService.newTask(task2);
		
		
		Map<String, Object> params3=new HashMap<String, Object>();
		Map<String, String> data3=new HashMap<String, String>();
		data3.put("sfz", personSFZ.trim());
		params3.put("data", data3);
		PersonTask task3=new PersonTask(PersonTask.MODIFYPERSONMARK_TYPE_INFO, params3);
		PersonService.newTask(task3);

	}
	
	/**
	 * 设置街道选中值
	 * @param spinner
	 * @param streetId
	 */
	private void setStreetValue(Spinner spinner,String streetId){
		for(int i=0;i<spinner.getAdapter().getCount();i++){
			if (((StreetInformation) spinner.getAdapter().getItem(i)).getStreetId().trim()
					.equals(streetId.trim())) {
				spinner.setSelection(i);
				break;
			}
		}
	}
	
	/**
	 * 设置居委会选中值
	 * @param spinner
	 * @param committeeId
	 */
	private void setCommitteeValue(Spinner spinner,String committeeId){
		for(int i=0;i<spinner.getAdapter().getCount();i++){
			if (((CommitteeInformation) spinner.getAdapter().getItem(i)).getCommitteeId().trim()
					.equals(committeeId.trim())) {
				spinner.setSelection(i);
				break;
			}
		}
	}
	
	

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		// 刷新街道下拉框
		case ModifyPersonInfoActivity.REFRESH_SPINNERSTREET:
			String streetStr = "";
			if (params[1] != null) {
				streetStr = params[1].toString();
			}
			if (streetStr != null) {
				tranceStrToStreetList(streetStr);
			}
			ArrayAdapter<StreetInformation> streetAdapter = new ArrayAdapter<StreetInformation>(
					this, android.R.layout.simple_spinner_item, streetList);
			streetAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			tv_personstreet.setAdapter(streetAdapter);
//			setSpinnerSelection(tv_personstreet, personbaseInfo.getCenter()
//					.getQstreet().trim());
			setStreetValue(tv_personstreet, personbaseInfo.getStreet_id().trim());
			break;
		// 刷新居委会下拉框
		case ModifyPersonInfoActivity.REFRESH_SPINNERJUWEIHUI:
			String juweihuiStr = "";
			if (params[1] != null) {
				juweihuiStr = params[1].toString();
			}
			if (juweihuiStr != null) {
				tranceStrToJuweihuiList(juweihuiStr);
			}
			ArrayAdapter<CommitteeInformation> juweihuiAdapter = new ArrayAdapter<CommitteeInformation>(
					this, android.R.layout.simple_spinner_item, juweihuiList);
			juweihuiAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			tv_juweihui.setAdapter(juweihuiAdapter);
//			setSpinnerSelection(tv_juweihui, personbaseInfo.getCenter()
//					.getQjuweihui().trim());
			setCommitteeValue(tv_juweihui, personbaseInfo.getCommittee_id().trim());
			break;
		// 刷新照片
		case ModifyPersonInfoActivity.REFRESH_PERSONIMAGE:
			if (params[1] != null) {
				byte[] imgdata = (byte[]) params[1];
				if (imgdata.length > 0) {
					Bitmap map = BitmapFactory.decodeByteArray(imgdata, 0,
							imgdata.length);
					img_personHead.setImageBitmap(map);
				}
			} else {
				img_personHead.setImageResource(R.drawable.personhead);
			}
			break;
		// 刷新个人标识
		case ModifyPersonInfoActivity.REFRESH_PERSONMARK:
			if (params[1] != null) {
				String str = params[1].toString();
				tranceStrToMarkList(str);
				showPersonMark();
			}
			break;
		// 更新个人图片
		case ModifyPersonInfoActivity.UPDATE_PERSONIMG:
			if (params[1] != null) {
				String value = params[1].toString();
				if (value.trim().equalsIgnoreCase("true")) {
					Toast.makeText(ModifyPersonInfoActivity.this, "上传成功!",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(ModifyPersonInfoActivity.this, "上传失败！",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
		// 更新个人基本信息
		case ModifyPersonInfoActivity.UPDATE_PERSONINFO:
			if (params[1] != null) {
				String value = params[1].toString();
				if (value.trim().equalsIgnoreCase("true")) {
					Toast.makeText(ModifyPersonInfoActivity.this, "上传成功!",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(ModifyPersonInfoActivity.this, "上传失败！",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;
			
		case RESULT_OK_INFO:
			if (!"".equals(params[1].toString().trim())&&params[1].toString().trim()!=null) {
				List<PersonInfo> newList=parseToJson(params[1].toString().trim());
				System.out.println("horizontalScrollView.getChildCount()==="+horizontalScrollView.getChildCount());
				if (horizontalScrollView.getChildCount()>0) {
					horizontalScrollView.removeAllViews();
				}
				if (newList.size()>0&&newList!=null) {
					RadioGroup group=(RadioGroup) LayoutInflater.from(ModifyPersonInfoActivity.this).inflate(R.layout.radio_group, null);
					horizontalScrollView.addView(group);
					for (PersonInfo typeInfo : newList) {
						RadioButton button=(RadioButton) LayoutInflater.from(ModifyPersonInfoActivity.this).inflate(R.layout.radio_item, null);
//						RadioButton button=new RadioButton(ModifyPersonInfoActivity.this);
						button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,30));
						button.setGravity(Gravity.CENTER);
						button.setTextSize(20);
						button.setText(typeInfo.getType_Name());
						group.addView(button);
					}
				}
			}
			break;
			
		case REFRESH_INFO:
			
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == REQUEST_CODE_TAKE_PICTURE) {
				try {
					Bitmap bitmap = BitmapFactory.decodeFile(Environment
							.getExternalStorageDirectory()
							+ File.separator
							+ "1.jpg");
					img_personHead.setImageBitmap(bitmap);
					// 读取图片信息为字节数组
					ByteArrayOutputStream oStream = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 80, oStream);
					// 将数据存放入集合
					person = new PersonUpdataImg();
					person.setPersonSfz(personSFZ);
					person.setPersonheadImg(oStream.toByteArray());
					oStream.close();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if (requestCode == SELECT_PICTURE) {
				try {
					Uri uri = data.getData();
					String[] proj = { MediaStore.Images.Media.DATA };
					Cursor actualimagecursor = managedQuery(uri, proj, null,
							null, null);
					int actual_image_column_index = actualimagecursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					actualimagecursor.moveToFirst();
					String photoPath = actualimagecursor
							.getString(actual_image_column_index);
					Bitmap bitmap = BitmapFactory.decodeFile(photoPath);
					
					img_personHead.setImageBitmap(bitmap);
					// 读取图片信息为字节数组
					ByteArrayOutputStream oStream = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.JPEG, 80, oStream);
					// 将数据存放入集合
					person = new PersonUpdataImg();
					person.setPersonSfz(personSFZ);
					person.setPersonheadImg(oStream.toByteArray());
					oStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
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

	
	
	
	/**
	 * 按返回键关闭当前页面
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent modifyit = new Intent(ModifyPersonInfoActivity.this,
					PersoninfoMainActivity.class);
			modifyit.putExtra("mysfz", personSFZ);
			startActivity(modifyit);
			ModifyPersonInfoActivity.this.finish();
		}
		return false;
	}

	/**
	 * 设置监听
	 */
	private void setListener() {

		tv_tye.setOnItemSelectedListener(new MyOnItemSelectedListener());
		tv_personstreet
				.setOnItemSelectedListener(new MyOnItemSelectedListener());
		btn_paizhao.setOnClickListener(new MyOnClickListener());
		btn_shangchuan.setOnClickListener(new MyOnClickListener());
		btn_save.setOnClickListener(new MyOnClickListener());
		btn_modifymark.setOnClickListener(new MyOnClickListener());
		boxjuzhu.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
		boxhuji.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
		btn_image.setOnClickListener(new MyOnClickListener());
		btn_btn_modify_mark.setOnClickListener(new MyOnClickListener());
	}

	/**
	 * 更新个人基本信息
	 * 
	 * @param title
	 * @param msg
	 */
	private void showpersonupdateDialog(String title, String msg) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title).setMessage(msg)
				.setIcon(android.R.drawable.ic_dialog_info).setCancelable(true)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						JSONArray array = getPersonInfoJson();
						Map<String, Object> params = new HashMap<String, Object>();
						Map<String, String> data = new HashMap<String, String>();
						data.put("jsonString", array.toString());
						params.put("data", data);
						PersonTask task = new PersonTask(
								PersonTask.MODIFYPERSONINFOACTIVITY_UPDATE_PERSONINFO,
								params);
						PersonService.newTask(task);

					}
				}).setNegativeButton("取消", null).show();
	}

	/**
	 * 取得个人基本信息
	 */
	public JSONArray getPersonInfoJson() {
		JSONArray array = new JSONArray();
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("NAME", tv_personaname.getText().toString().trim());
			jsonObject.put("SEX", tv_personsex.getSelectedItem().toString()
					.trim());
			jsonObject.put("BIRTH_DATE", tv_personborn.getText().toString()
					.trim());
			jsonObject.put("NATIONS", tv_personnational.getSelectedItem()
					.toString().trim());
			jsonObject.put("NATIVE", tv_personNative.getText().toString());
			jsonObject.put("TYPE", tv_tye.getSelectedItem().toString().trim());
			jsonObject.put("CULTURAL_CODE", tv_education.getSelectedItem()
					.toString().trim());
			jsonObject.put("SFZ", tv_cardnum.getText().toString().trim());
			jsonObject.put("CONTACT_NUMBER", tv_phone.getText().toString()
					.trim());
			jsonObject.put("GPS", FirstPageActivity.GetGpsInfo());
			jsonObject.put("ROAD", tv_hujiroad.getText().toString().trim());
			jsonObject.put("LANE", tv_hujinong.getText().toString().trim());
			jsonObject.put("NO", tv_hujinumber.getText().toString().trim());
			jsonObject.put("ROOM", tv_hujiroom.getText().toString().trim());
			jsonObject.put("NOW_ROAD", tv_road.getText().toString().trim());
			jsonObject.put("NOW_LANE", tv_nong.getText().toString().trim());
			jsonObject.put("NOW_NO", tv_number.getText().toString().trim());
			jsonObject.put("NOW_ROOM", tv_room.getText().toString().trim());
			jsonObject.put("Remark", tv_remark.getText().toString().trim());
			jsonObject.put("Current_situation", tv_status.getSelectedItem()
					.toString().trim());
			jsonObject.put("Current_intent", tv_intention.getSelectedItem()
					.toString().trim());
			JSONObject jsoncenter = new JSONObject();
			jsoncenter.put("Q户口所属街道", tv_personstreet.getSelectedItem()
					.toString().trim());
			jsoncenter.put("Q居委会", tv_juweihui.getSelectedItem().toString()
					.trim());
			jsoncenter.put("Q户口地址", personbaseInfo.getCenter().getQaddress()
					.trim());
			jsonObject.put("Center", jsoncenter);
			array.put(jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return array;

	}

	/**
	 * 修改头像信息提示框
	 * 
	 * @param msg
	 */
	private void showConfirmDialog(String title, String msg) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title).setMessage(msg)
				.setIcon(android.R.drawable.ic_dialog_info).setCancelable(true)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						new Thread() {
							public void run() {
								try {
									if (person != null) {
										// 将集合转为json
										JSONArray array = new JSONArray();
										JSONObject jsonObject = new JSONObject();
										jsonObject.put("json", person);
										array.put(jsonObject);

										Map<String, Object> params = new HashMap<String, Object>();
										Map<String, String> data = new HashMap<String, String>();
										data.put("jsonString", array.toString());
										params.put("data", data);

										PersonTask task = new PersonTask(
												PersonTask.MODIFYPERSONINFOACTIVITY_UPDATE_PERSONIMAGE,
												params);
										PersonService.newTask(task);
									} else {
										Toast.makeText(
												ModifyPersonInfoActivity.this,
												"请先拍照再上传！", Toast.LENGTH_SHORT)
												.show();
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

							};
						}.start();
					}
				}).setNegativeButton("取消", null).show();
	}

	/**
	 * 将标识信息保存进集合，便于操作
	 */
	private void initPersonMark() {
		HM_mark = new HashMap<String, ImageView>();
		HM_mark.put("应届毕业生", img_student);
		HM_mark.put("启航人员", img_qihang);
		HM_mark.put("就业困难人员", img_jiuyekunnan);
		HM_mark.put("零就业家庭", img_lingjiuye);
		HM_mark.put("戒毒人员", img_jiedu);
		HM_mark.put("刑解人员", img_xingjie);
	}

	/**
	 * 显示标识信息
	 */
	private void showPersonMark() {
		for (ImageView view : HM_mark.values()) {
			view.setVisibility(View.GONE);
		}

		for (int j = 0; j < personmarkList.size(); j++) {
			HM_mark.get(personmarkList.get(j).getPersonMarkName())
					.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 将字符串转换为标识列表
	 * 
	 * @param str
	 */
	private void tranceStrToMarkList(String str) {
		personmarkList.clear();
		try {
			JSONArray jsonarray = new JSONArray(str);
			int len = jsonarray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonarray.getJSONObject(i);
				String personMarkId = object.getString("ID");
				String personSFZ = object.getString("SFZ");
				String personMarkName = object.getString("MARK");
				String personMarkCreatdate = object.getString("CREATE_DATE");
				String subdate = personMarkCreatdate.substring(0,
						personMarkCreatdate.indexOf("T"));
				String personMarkSource = object.getString("SOURCE");
				PersonMark personmark = new PersonMark();
				personmark.setPersonMarkId(personMarkId);
				personmark.setPersonSFZ(personSFZ);
				personmark.setPersonMarkName(personMarkName);
				personmark.setPersonMarkCreatdate(subdate);
				personmark.setPersonMarkSoure(personMarkSource);
				personmarkList.add(personmark);
			}
		} catch (Exception e) {
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
		juweihuiList.add(new CommitteeInformation("0", "请选择", " 0"));

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
	 * 根据值，设置下拉框的选中项
	 * 
	 * @param spinner
	 * @param value
	 */
	private void setSpinnerSelection(Spinner spinner, String value) {
		for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
			if (spinner.getAdapter().getItem(i).toString().trim()
					.equals(value.trim())) {
				spinner.setSelection(i);
				break;
			}
		}
	}
	
	private void setContainSpinnerSelection(Spinner spinner,String value){
		for(int i=0;i<spinner.getAdapter().getCount();i++){
			if(spinner.getAdapter().getItem(i).toString().trim().contains(value.trim())){
				spinner.setSelection(i);
				break;
			}
		}
	}

	/**
	 * 初始化个人信息
	 */
	private void initPersonInfo() {
		tv_personaname.setText(personbaseInfo.getPersonName());
		tv_personborn.setText(personbaseInfo.getPersonBorn());
		tv_cardnum.setText(personbaseInfo.getPersonCardId());
		tv_personNative.setText(personbaseInfo.getPersonNativePlace());
		setSpinnerSelection(tv_education, personbaseInfo.getPersonEducation()
				.trim());
		setSpinnerSelection(tv_personsex, personbaseInfo.getPersonSex().trim());
//		setSpinnerSelection(tv_personnational, personbaseInfo
//				.getPersonNational().trim());
		setContainSpinnerSelection(tv_personnational, personbaseInfo
				.getPersonNational().trim());
		setSpinnerSelection(tv_tye, personbaseInfo.getPersonType().trim());
		setSpinnerSelection(tv_intention, personbaseInfo.getPersonIntention()
				.trim());
		tv_phone.setText(personbaseInfo.getPersonMobilePhone());
		tv_hujiroad.setText(personbaseInfo.getPersonRoad());
		tv_road.setText(personbaseInfo.getPersonNowRoad());
		tv_hujinong.setText(personbaseInfo.getPersonNong());
		tv_nong.setText(personbaseInfo.getPersonNowNong());
		tv_hujinumber.setText(personbaseInfo.getPersonNumber());
		tv_number.setText(personbaseInfo.getPersonNowNumber());
		tv_hujiroom.setText(personbaseInfo.getPersonRoom());
		tv_room.setText(personbaseInfo.getPersonNowRoom());
		tv_remark.setText(personbaseInfo.getPersonBeizhu() == null ? ""
				: personbaseInfo.getPersonBeizhu());
	}

	/**
	 * 将传来的个人信息转换为对象
	 * 
	 * @param str
	 */
	private void tranceStrToPersonInfo(String str) {
		if (str != null) {
			try {
				JSONArray jsonarray = new JSONArray(str);
				if (jsonarray.length() > 0) {
					JSONObject object = jsonarray.getJSONObject(0);
					String personIdCard = object.getString("SFZ");
					String personName = object.getString("NAME");
					String personNational = object.getString("NATIONS");
					String personSex = object.getString("SEX");
					String personMobilePhone = object
							.getString("CONTACT_NUMBER");
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
					// 转化时间格式
					String substring = personBorn.substring(0,
							personBorn.indexOf("T"));

					String personEducation = object.getString("CULTURAL_CODE");

					String status = object.getString("Current_situation");
					String intention = object.getString("Current_intent");
					JSONObject jsonCenter = object.getJSONObject("Center");
					// String personNational1 = jsonCenter.getString("Q民族");
					String cardtype = jsonCenter.getString("Q证件类型");
					String cardnum = jsonCenter.getString("Q证件号码");
					String personstreet = jsonCenter.getString("Q户口所属街道");
					String personjuwei = jsonCenter.getString("Q居委会");
					String status1 = jsonCenter.getString("Q目前摸底状况");
					String intention1 = jsonCenter.getString("Q当前意向");
					String address = jsonCenter.getString("Q户口地址");
					Center center = new Center();
					center.setQcardtype(cardtype);
					center.setQaddress(nowroad + nownong + nownumber + nowroom);
					center.setQcurretnStatus(status1);
					center.setQcurrentintention(intention1);
					center.setQcardnum(cardnum);
					center.setQstreet(personstreet);
					center.setQjuweihui(personjuwei);
					center.setQhujidizhi(road + nong + number + room);
					// center.setQnational(personNational1);
					personbaseInfo.setCenter(center);
					personbaseInfo.setPersonName(personName);
					personbaseInfo.setPersonCardId(personIdCard);
					personbaseInfo.setPersonBorn(substring);
					personbaseInfo.setPersonSex(personSex);
					personbaseInfo.setPersonType(type);
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
					personbaseInfo.setPersonIntention(intention);
					personbaseInfo.setPersonCurrentStatus(status);
					String time = jsonCenter.getString("CREATE_DATE");
					String[] times = time.split("T");
					personbaseInfo.setUpdateTime(times[0]
							+ " "
							+ times[1].substring(
									0,
									times[1].indexOf('.') > 0 ? times[1]
											.indexOf('.') : times[1].length()));
					personbaseInfo.setStreet_id(object.getString("STREET_ID"));
					personbaseInfo.setCommittee_id(object.getString("COMMITTEE_ID"));
					personInfoJsonList.add(personbaseInfo);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 转化jsonStr为StreetList
	 * 
	 * @param streetStr
	 */
	private void tranceStrToStreetList(String streetStr) {
		streetList.clear();
		streetList.add(new StreetInformation("0", "请选择", " 0"));
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
	 * 初始化控件
	 */
	private void initView() {
		img_personHead = (ImageView) findViewById(R.id.imgHead);
		btn_paizhao = (Button) findViewById(R.id.button_personpaizhao);
		btn_shangchuan = (Button) findViewById(R.id.button_tijiao);
		btn_save = (Button) findViewById(R.id.button_save);
		btn_modifymark = (Button) findViewById(R.id.btn_modifymark);
		tv_personaname = (EditText) findViewById(R.id.addpersonname);
		tv_personNative = (EditText) findViewById(R.id.addpersonnative);
		tv_tye = (Spinner) findViewById(R.id.addpersontype);
		tv_personsex = (Spinner) findViewById(R.id.addpersonsex);
		tv_personborn = (EditText) findViewById(R.id.addpersonborn);
		tv_personborn.setInputType(InputType.TYPE_NULL);
		tv_personnational = (Spinner) findViewById(R.id.addpersonnational);
		tv_education = (Spinner) findViewById(R.id.addpersonedution);
		tv_phone = (EditText) findViewById(R.id.addpersonphonenum);
		tv_intention = (Spinner) findViewById(R.id.addpersonintention);
		tv_cardnum = (EditText) findViewById(R.id.addpersoncardnum);
		tv_remark = (EditText) findViewById(R.id.addpersonbeizhu);
		tv_personstreet = (Spinner) findViewById(R.id.addpersonstreet);
		tv_juweihui = (Spinner) findViewById(R.id.addpersonjuweihui);
		tv_status = (Spinner) findViewById(R.id.addpersonstatus);
		tv_road = (EditText) findViewById(R.id.et_lu);
		tv_hujiroad = (EditText) findViewById(R.id.et_hujilu);
		tv_nong = (EditText) findViewById(R.id.et_nong);
		tv_hujinong = (EditText) findViewById(R.id.et_hujinong);
		tv_number = (EditText) findViewById(R.id.et_hao);
		tv_hujinumber = (EditText) findViewById(R.id.et_hujihao);
		tv_room = (EditText) findViewById(R.id.et_shi);
		tv_hujiroom = (EditText) findViewById(R.id.et_hujishi);
		boxjuzhu = (CheckBox) findViewById(R.id.checkboxjuzhu);
		boxhuji = (CheckBox) findViewById(R.id.checkboxhuji);
		img_student = (ImageView) findViewById(R.id.img_student);
		img_jiedu = (ImageView) findViewById(R.id.img_jiedurenyuan);
		img_jiuyekunnan = (ImageView) findViewById(R.id.img_jiuyekunnan);
		img_lingjiuye = (ImageView) findViewById(R.id.imglingjiuye);
		img_qihang = (ImageView) findViewById(R.id.img_qihangrenyuan);
		img_xingjie = (ImageView) findViewById(R.id.imgxingjie);
		btn_image = (Button) findViewById(R.id.button_image);
		btn_btn_modify_mark=(Button) findViewById(R.id.btn_modify_mark);
		
		horizontalScrollView=(HorizontalScrollView) this.findViewById(R.id.my_horizon);
	}

	/**
	 * 界面非空验证
	 * 
	 * @return
	 */
	private boolean checkFrm() {
		if (tv_personnational.getSelectedItemPosition() == 0) {
			Toast.makeText(this, "请选择民族！", Toast.LENGTH_SHORT).show();
			tv_personnational.requestFocus();
			return false;
		}

		if (tv_tye.getSelectedItemPosition() == 0) {
			Toast.makeText(this, "请选择状态！", Toast.LENGTH_SHORT).show();
			tv_tye.requestFocus();
			return false;
		}

		if (tv_intention.getSelectedItemPosition() == 0) {
			Toast.makeText(this, "请选择当前意向！", Toast.LENGTH_SHORT).show();
			tv_intention.requestFocus();
			return false;
		}

		if (tv_status.getSelectedItemPosition() == 0) {
			Toast.makeText(this, "请选择摸底情况！", Toast.LENGTH_SHORT).show();
			tv_status.requestFocus();
			return false;
		}

		if (tv_personstreet.getSelectedItemPosition() == 0) {
			Toast.makeText(this, "请选择街道！", Toast.LENGTH_SHORT).show();
			tv_personstreet.requestFocus();
			return false;
		}

		if (tv_juweihui.getSelectedItemPosition() == 0) {
			Toast.makeText(this, "请选择居委会！", Toast.LENGTH_SHORT).show();
			tv_juweihui.requestFocus();
			return false;
		}

		return true;
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
			setSpinner(tv_status, R.array.personmodishiye);
		} else if (value.trim().equals("未登记失业")) {
			setSpinner(tv_status, R.array.personmodiwuye);
		} else if (value.trim().equals("学龄前儿童")) {
			setSpinner(tv_status, R.array.personmodiwuye);
		}
	}

	/**
	 * 初始化下拉框
	 */
	private void initSpinnerData() {
		// 性别
		setSpinner(tv_personsex, R.array.spinnersex);
		// 民族
		setSpinner(tv_personnational, R.array.person_national);
		// 文化程度
		setSpinner(tv_education, R.array.spinner_wenhua);
		// 街道
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", "310108");
		PersonTask task = new PersonTask(
				PersonTask.MODIFYPERSONINFOACTIVITY_GET_STREET, param);
		PersonService.newTask(task);

		// 状态
		setSpinner(tv_tye, R.array.personstatus);
		// 当前意向
		setSpinner(tv_intention, R.array.personintention);

	}

	private class MyOnItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch (arg0.getId()) {
			case R.id.addpersontype:
				initModiSpinner(tv_tye.getSelectedItem().toString().trim());
				setSpinnerSelection(tv_status, personbaseInfo
						.getPersonCurrentStatus().trim());
				break;
			case R.id.addpersonstreet:
				StreetInformation street = (StreetInformation) tv_personstreet
						.getSelectedItem();
				Map<String, Object> param2 = new HashMap<String, Object>();
				param2.put("id", street.getStreetId());
				PersonTask task2 = new PersonTask(
						PersonTask.MODIFYPERSONINFOACTIVITY_GET_JUWEIHUI,
						param2);
				PersonService.newTask(task2);
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
			case R.id.button_personpaizhao:
				// 跳转到系统自带的相机界面 需要在配置文件设置相应的权限
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
						Environment.getExternalStorageDirectory(), "1.jpg")));
				startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
				break;
			case R.id.button_tijiao:
				showConfirmDialog("上传头像确认提示", "您确定要把头像，上传到服务器吗？");
				break;
			case R.id.button_save:
				// 个人信息保存，不含图片
				if (checkFrm()) {
					showpersonupdateDialog("保存信息提示", "您确定保存修改的信息吗？");
				}
				break;
			case R.id.btn_modifymark:
				// 修改标识
				Intent markintent = new Intent(ModifyPersonInfoActivity.this,
						ModifyPersonMarkActivity.class);
				markintent.putExtra("personmarklist", personmarkList);
				markintent.putExtra("sfz", personSFZ);
				markintent.putExtra("personinfojson", personInfoJsonList);
				startActivity(markintent);
				break;
			case R.id.button_image:
				Intent intent_select_pic = new Intent();
				/* 开启Pictures画面Type设定为image */
				intent_select_pic.setType("image/*");
				/* 使用Intent.ACTION_GET_CONTENT这个Action */
				intent_select_pic.setAction(Intent.ACTION_GET_CONTENT);
				/* 取得相片后返回本画面 */
				startActivityForResult(intent_select_pic, SELECT_PICTURE);
				break;
				
			case R.id.btn_modify_mark:
				Intent intent2=new Intent(ModifyPersonInfoActivity.this, ModifyStatusMarkActivity.class);
				intent2.putExtra("sfz", personSFZ);
				startActivity(intent2);				
				break;
			}
		}

	}

	private class MyOnCheckedChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			switch (buttonView.getId()) {
			case R.id.checkboxhuji:
				// 同下
				if (boxhuji.isChecked()) {
					boxjuzhu.setChecked(false);
					tv_hujiroad.setText(tv_road.getText().toString().trim());
					tv_hujinong.setText(tv_nong.getText().toString().trim());
					tv_hujinumber
							.setText(tv_number.getText().toString().trim());
					tv_hujiroom.setText(tv_room.getText().toString().trim());
				}
				break;
			case R.id.checkboxjuzhu:
				// 同上
				if (boxjuzhu.isChecked()) {
					boxhuji.setChecked(false);
					tv_road.setText(tv_hujiroad.getText().toString().trim());
					tv_nong.setText(tv_hujinong.getText().toString().trim());
					tv_number
							.setText(tv_hujinumber.getText().toString().trim());
					tv_room.setText(tv_hujiroom.getText().toString().trim());
				}
				break;

			}
		}

	}
	
	private List<PersonInfo> parseToJson(String string){
		List<PersonInfo> infos=new ArrayList<PersonInfo>();
		try {
			JSONArray jsonArray=new JSONArray(string);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject=(JSONObject) jsonArray.get(i);
				PersonInfo info=new PersonInfo();
				info.setID(jsonObject.getInt("ID"));
				info.setSFZ(jsonObject.getString("SFZ"));
				info.setTYPE(jsonObject.getInt("TYPE"));
				info.setCREATE_DATE(jsonObject.getString("CREATE_DATE"));
				info.setCREATE_STAFF(jsonObject.getInt("CREATE_STAFF"));
				info.setUPDATE_DATE(jsonObject.getString("UPDATE_DATE"));
				info.setUPDATE_STAFF(jsonObject.getInt("UPDATE_STAFF"));
				info.setRecordCount(jsonObject.getInt("RecordCount"));
				info.setType_Name(jsonObject.getString("Type_Name"));
				infos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return infos;

	}
	
	

}

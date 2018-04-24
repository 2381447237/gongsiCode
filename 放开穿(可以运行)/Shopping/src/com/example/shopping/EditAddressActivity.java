package com.example.shopping;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;

import com.example.adapters.MyAddressmanageAdapter;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.DetailedAddressInfo;
import com.example.infoclass.EditAddress;
import com.example.wheel.OnWheelChangedListener;
import com.example.wheel.WheelView;
import com.example.wheel.adapters.ArrayWheelAdapter;
import com.exmaple.infoclass.ProvinceCityDistrictContent;
import com.exmaple.infoclass.ProvinceCityDistrictContent.TblCityDictEntity;
import com.exmaple.infoclass.ProvinceCityDistrictContent.TblCityDictEntity.TblDistrictDictEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class EditAddressActivity extends Activity implements OnClickListener,
		OnWheelChangedListener {

	private ImageView iv_back;
	private WheelView mViewProvince, mViewCity, mViewDistrict;
	private Gson gson;
	private String addressUrl = HttpUrl.HttpURL+"/Json/Get_Province_City_District_Info.aspx";
	private String editUrl = HttpUrl.HttpURL+"/Json/Set_User_Address_Info.aspx";
	private String detailedUrl = HttpUrl.HttpURL+"/Json/Get_User_Address_Info.aspx";
	private List<String> mProvinceDatas = new ArrayList<String>();
	private List<String> mCityDatas = new ArrayList<String>();
	private List<String> mAreaDatas = new ArrayList<String>();
	private List<ProvinceCityDistrictContent> mProvinceList = new ArrayList<ProvinceCityDistrictContent>();

	private List<Integer> mProvinceIds = new ArrayList<Integer>();
	private List<Integer> mCityIds = new ArrayList<Integer>();
	private List<Integer> mAreaIds = new ArrayList<Integer>();

	private String mCurrentProviceName = "", mCurrentCityName = "",
			mCurrentDistrictName = "";

	private int mCurrentProviceId, mCurrentCityId, mCurrentDistrictId;

	private String[] pDatas;

	private Integer[] pIds;

	private Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
	private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();

	private Map<Integer, Integer[]> mDistrictIdsMap = new HashMap<Integer, Integer[]>();
	private Map<Integer, Integer[]> mCitisIdsMap = new HashMap<Integer, Integer[]>();

	private TextView tv_confirm, tv_cancel, tv_receiver, tv_phonenumber,
			tv_postcode, tv_province, tv_detailedaddress, tv_chooseprovince;

	private EditText et_receiver, et_phonenumber, et_postcode,
			et_detailedaddress;

	private String receiverStr, phonenumberStr, zipCodeStr, provinceNameStr,
			cityNameStr, districtNameStr, detailAddrStr, chooseprovinceStr;
	private LinearLayout ll_myWheelView, ll_editaddress_keep;
	private RelativeLayout rl_mask, rl_title;
	private ScrollView sv_edit;

	private String userID;

	public static boolean isEdit = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_editaddress);

		iv_back = (ImageView) findViewById(R.id.img_editaddress_back);
		iv_back.setOnClickListener(this);

		rl_mask = (RelativeLayout) findViewById(R.id.rl_mask);
		rl_mask.setOnClickListener(this);
		rl_title = (RelativeLayout) findViewById(R.id.rl_editaddress_title);

		Intent intent = getIntent();

		EditAddress ea = (EditAddress) intent
				.getSerializableExtra("editAddress");
		receiverStr = ea.Consigner;
		phonenumberStr = ea.PhoneNumber;
		zipCodeStr = ea.ZipCode;
		provinceNameStr = ea.ProvinceName;
		cityNameStr = ea.CityName;
		districtNameStr = ea.DistrictName;
		detailAddrStr = ea.DetailAddr;

		et_receiver = (EditText) findViewById(R.id.et_editaddress_receiver);
		if (!TextUtils.isEmpty(receiverStr)) {
			et_receiver.setText(receiverStr);
		} else {
			et_receiver.setText("");
		}
		et_receiver.requestFocus();
		// 将EditText的光标放置在文字的后面
		et_receiver.setOnClickListener(this);

		et_phonenumber = (EditText) findViewById(R.id.et_editaddress_phonenumber);
		if (!TextUtils.isEmpty(phonenumberStr)) {
			et_phonenumber.setText(phonenumberStr);
		} else {
			et_phonenumber.setText("");
		}
		et_phonenumber.setOnClickListener(this);

		et_postcode = (EditText) findViewById(R.id.et_editaddress_postcode);
		if (!TextUtils.isEmpty(zipCodeStr)) {
			et_postcode.setText(zipCodeStr);
		} else {
			et_postcode.setText("");
		}
		et_postcode.setOnClickListener(this);

		tv_chooseprovince = (TextView) findViewById(R.id.tv_editaddress_chooseprovince);
		if (!TextUtils.isEmpty(provinceNameStr)
				|| !TextUtils.isEmpty(cityNameStr)
				|| !TextUtils.isEmpty(districtNameStr)) {
			tv_chooseprovince.setText(provinceNameStr + "-" + cityNameStr + "-"
					+ districtNameStr);
		} else {
			tv_chooseprovince.setText("");
		}
		tv_chooseprovince.setOnClickListener(this);

		et_detailedaddress = (EditText) findViewById(R.id.et_editaddress_detailedaddress);
		if (!TextUtils.isEmpty(detailAddrStr)) {
			et_detailedaddress.setText(detailAddrStr);
		} else {
			et_detailedaddress.setText("");
		}
		et_detailedaddress.setOnClickListener(this);

		ll_myWheelView = (LinearLayout) findViewById(R.id.myWheelView);
		tv_cancel = (TextView) findViewById(R.id.tv_cancel);
		tv_cancel.setOnClickListener(this);
		ll_editaddress_keep = (LinearLayout) findViewById(R.id.ll_editaddress_keep);
		ll_editaddress_keep.setOnClickListener(this);
		tv_receiver = (TextView) findViewById(R.id.tv_editaddress_receiver);
		tv_phonenumber = (TextView) findViewById(R.id.tv_editaddress_phonenumber);
		tv_postcode = (TextView) findViewById(R.id.tv_editaddress_postcode);
		tv_postcode.setOnClickListener(this);
		tv_province = (TextView) findViewById(R.id.tv_editaddress_province);
		tv_detailedaddress = (TextView) findViewById(R.id.tv_editaddress_detailedaddress);
		sv_edit = (ScrollView) findViewById(R.id.sv_editaddress);
		sv_edit.setOverScrollMode(sv_edit.OVER_SCROLL_NEVER);
		getUserId();

		setUpViews();
		setUpListener();
		getJSONOkhttpPost();
	}

	private void getUserId() {

		SharedPreferences preferences = this.getSharedPreferences("user",
				Context.MODE_PRIVATE);
		userID = preferences.getString("userID", "");

	}

	@Override
	protected void onResume() {

		super.onResume();

		getDistrictId();

	}

	// http://www.youli.pw:85/Json/Get_User_Address_Info.aspx?AcctID=80&AddrID=373
	private void getDistrictId() {

		OkHttpUtils
				.post()
				.url(detailedUrl)
				.addParams("AcctID", userID)
				.addParams("AddrID",
						String.valueOf(MyAddressmanageAdapter.editAddressId))
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(String str) {

						Gson gson = new Gson();

						DetailedAddressInfo daInfo = gson.fromJson(str,
								DetailedAddressInfo.class);

						mCurrentDistrictId = daInfo.DistrictID;
					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.img_editaddress_back:

			showMiddleDialog();

			break;

		case R.id.tv_editaddress_chooseprovince:

			rl_mask.setBackgroundColor(Color.argb(0xff, 0x99, 0x99, 0x99));
			rl_title.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));
			iv_back.setClickable(false);
			ll_myWheelView.setVisibility(View.VISIBLE);
			et_receiver.setEnabled(false);
			et_phonenumber.setEnabled(false);
			et_postcode.setEnabled(false);
			et_detailedaddress.setEnabled(false);
			ll_editaddress_keep.setClickable(false);

			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(rl_mask.getWindowToken(), 0);

			break;

		case R.id.ll_editaddress_keep:

			receiverStr = et_receiver.getText().toString().trim();
			phonenumberStr = et_phonenumber.getText().toString().trim();
			zipCodeStr = et_postcode.getText().toString().trim();
			detailAddrStr = et_detailedaddress.getText().toString().trim();
			chooseprovinceStr = tv_chooseprovince.getText().toString().trim();
			if (TextUtils.isEmpty(receiverStr)
					|| TextUtils.isEmpty(phonenumberStr)
					|| TextUtils.isEmpty(zipCodeStr)
					|| TextUtils.isEmpty(chooseprovinceStr)
					|| TextUtils.isEmpty(detailAddrStr)) {

				Toast.makeText(EditAddressActivity.this, "请完善收货信息", 0).show();

			} else {

				if (!isPhonenumber() || !isPostcode()) {
					Toast.makeText(EditAddressActivity.this, "手机号码或邮政编码输入有误", 0)
							.show();
				} else {
					editInformation();
					Toast.makeText(EditAddressActivity.this, "保存成功", 0).show();
					finish();
					isEdit = true;
				}
			}
			break;

		case R.id.tv_cancel:

			ll_myWheelView.setVisibility(View.GONE);
			rl_mask.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));
			rl_title.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));
			iv_back.setClickable(true);
			et_receiver.setEnabled(true);
			et_phonenumber.setEnabled(true);
			et_postcode.setEnabled(true);
			et_detailedaddress.setEnabled(true);
			ll_editaddress_keep.setClickable(true);

			break;

		case R.id.tv_confirm:

			rl_mask.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));
			rl_title.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));
			tv_chooseprovince.setText(mCurrentProviceName + "-"
					+ mCurrentCityName + "-" + mCurrentDistrictName);
			chooseprovinceStr = mCurrentProviceName + "-" + mCurrentCityName
					+ "-" + mCurrentDistrictName;
			ll_myWheelView.setVisibility(View.GONE);
			iv_back.setClickable(true);
			et_receiver.setEnabled(true);
			et_phonenumber.setEnabled(true);
			et_postcode.setEnabled(true);
			et_detailedaddress.setEnabled(true);
			ll_editaddress_keep.setClickable(true);
			break;

		default:
			break;
		}

	}

	private void editInformation() {

		OkHttpUtils
				.post()
				.url(editUrl)
				.addParams("AcctID", userID)
				.addParams("Consigner", receiverStr)
				.addParams("PhoneNumber", phonenumberStr)
				.addParams("ZipCode", zipCodeStr)
				.addParams("Address", detailAddrStr)
				.addParams("DistrictID", String.valueOf(mCurrentDistrictId))
				.addParams("AddrID",
						String.valueOf(MyAddressmanageAdapter.editAddressId))
				.addParams("IsDefault", String.valueOf(0)).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(String arg0) {

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});
	}

	// 判断手机号码的格式是否正确
	private boolean isPhonenumber() {

		Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");

		Matcher m = p.matcher(phonenumberStr);

		return m.matches();
	}

	// 判断邮政编码的格式是否正确
	private boolean isPostcode() {

		Pattern p = Pattern.compile("^[1-9][0-9]{5}$");

		Matcher m = p.matcher(zipCodeStr);

		return m.matches();

	}

	private void getJSONOkhttpPost() {

		OkHttpUtils.post().url(addressUrl).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						runOnUiThread(new Runnable() {

							@Override
							public void run() {

								Type listType = new TypeToken<LinkedList<ProvinceCityDistrictContent>>() {
								}.getType();

								gson = new Gson();

								LinkedList<ProvinceCityDistrictContent> contents = gson
										.fromJson(str, listType);

								for (Iterator iterator = contents.iterator(); iterator
										.hasNext();) {

									ProvinceCityDistrictContent content = (ProvinceCityDistrictContent) iterator
											.next();

									mProvinceDatas.add(content.ProvinceName);// 省名
									mProvinceIds.add(content.ProvinceID);// 省ID

									mProvinceList.add(content);

									List<TblCityDictEntity> tcdeList = new ArrayList<ProvinceCityDistrictContent.TblCityDictEntity>();

									tcdeList = content.tbl_City_Dict;

									for (int i = 0; i < content.tbl_City_Dict
											.size(); i++) {

										mCityDatas.add(tcdeList.get(i).CityName);// 市名
										mCityIds.add(tcdeList.get(i).CityID);// 市ID

										List<TblDistrictDictEntity> tddeList = new ArrayList<ProvinceCityDistrictContent.TblCityDictEntity.TblDistrictDictEntity>();

										tddeList = tcdeList.get(i).tbl_District_Dict;

										for (int j = 0; j < tcdeList.get(i).tbl_District_Dict
												.size(); j++) {

											mAreaDatas.add(tddeList.get(j).DistrictName);
											mAreaIds.add(tddeList.get(j).DistrictID);

										}
									}

								}
							}
						});
						if (mProvinceDatas != null && !mProvinceDatas.isEmpty()
								&& mCityDatas != null && !mCityDatas.isEmpty()
								&& mAreaDatas != null && !mAreaDatas.isEmpty()) {
							mViewProvince
									.setViewAdapter(new ArrayWheelAdapter<String>(
											EditAddressActivity.this,
											mProvinceDatas));
							// 设置可见条目数量
							mViewProvince.setVisibleItems(5);
							mViewCity.setVisibleItems(5);
							mViewDistrict.setVisibleItems(5);

							mViewCity
									.setViewAdapter(new ArrayWheelAdapter<String>(
											EditAddressActivity.this,
											mCityDatas));
							mViewCity.setCurrentItem(0);
							mViewDistrict
									.setViewAdapter(new ArrayWheelAdapter<String>(
											EditAddressActivity.this,
											mAreaDatas));
							mViewDistrict.setCurrentItem(0);
						}
						initProvinceDatas();
					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	private void initProvinceDatas() {

		if (mProvinceList != null && !mProvinceList.isEmpty()) {
			mCurrentProviceName = mProvinceList.get(0).ProvinceName;
			mCurrentProviceId = mProvinceList.get(0).ProvinceID;

			List<TblCityDictEntity> mCityList = mProvinceList.get(0).tbl_City_Dict;

			if (mCityList != null && !mCityList.isEmpty()) {

				mCurrentCityName = mCityList.get(0).CityName;
				mCurrentCityId = mCityList.get(0).CityID;

				List<TblDistrictDictEntity> mDistrictList = mCityList.get(0).tbl_District_Dict;

				mCurrentDistrictName = mDistrictList.get(0).DistrictName;
				mCurrentDistrictId = mDistrictList.get(0).DistrictID;
			}
		}
		pDatas = new String[mProvinceList.size()];
		for (int i = 0; i < mProvinceList.size(); i++) {

			// 遍历所有省的数据
			pDatas[i] = mProvinceList.get(i).ProvinceName;
			List<TblCityDictEntity> mCityList = mProvinceList.get(i).tbl_City_Dict;
			String[] cityNames = new String[mCityList.size()];
			for (int j = 0; j < mCityList.size(); j++) {
				// 遍历省下面的所有市的数据
				cityNames[j] = mCityList.get(j).CityName;
				List<TblDistrictDictEntity> districtList = mCityList.get(j).tbl_District_Dict;
				String[] distrinctNameArray = new String[districtList.size()];
				TblDistrictDictEntity[] distrinctArray = new TblDistrictDictEntity[districtList
						.size()];
				for (int k = 0; k < districtList.size(); k++) {
					// 遍历市下面所有区/县的数据
					TblDistrictDictEntity districtModel = new TblDistrictDictEntity(
							districtList.get(k).DistrictName, 0); // 这里有可能出错
					distrinctArray[k] = districtModel;
					distrinctNameArray[k] = districtModel.DistrictName;
				}
				// 市-区/县的数据，保存到mDistrictDatasMap
				mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
			}
			// 省-市的数据，保存到mCitisDatasMap
			mCitisDatasMap.put(mProvinceList.get(i).ProvinceName, cityNames);
		}
		pIds = new Integer[mProvinceList.size()];
		for (int i = 0; i < mProvinceList.size(); i++) {
			pIds[i] = mProvinceList.get(i).ProvinceID;
			List<TblCityDictEntity> mCityList = mProvinceList.get(i).tbl_City_Dict;
			Integer[] cityIds = new Integer[mCityList.size()];
			for (int j = 0; j < mCityList.size(); j++) {

				cityIds[j] = mCityList.get(j).CityID;
				List<TblDistrictDictEntity> districtList = mCityList.get(j).tbl_District_Dict;
				Integer[] distrinctIdArray = new Integer[districtList.size()];
				TblDistrictDictEntity[] distrinctArray = new TblDistrictDictEntity[districtList
						.size()];

				for (int k = 0; k < districtList.size(); k++) {
					TblDistrictDictEntity districtModel = new TblDistrictDictEntity(
							null, districtList.get(k).DistrictID);// 这里有可能出错
					distrinctArray[k] = districtModel;
					distrinctIdArray[k] = districtModel.DistrictID;
				}
				mDistrictIdsMap.put(cityIds[j], distrinctIdArray);
			}
			mCitisIdsMap.put(mProvinceList.get(i).ProvinceID, cityIds);
		}
	}

	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district);
		tv_confirm = (TextView) findViewById(R.id.tv_confirm);
	}

	private void setUpListener() {

		// 添加change事件
		mViewProvince.addChangingListener(this);
		// 添加change事件
		mViewCity.addChangingListener(this);
		// 添加change事件
		mViewDistrict.addChangingListener(this);

		tv_confirm.setOnClickListener(this);
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {

		if (wheel == mViewProvince) {

			updateCities();

		} else if (wheel == mViewCity) {

			updateAreas();
		} else if (wheel == mViewDistrict) {

			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];

			mCurrentDistrictId = mDistrictIdsMap.get(mCurrentCityId)[newValue];
		}

	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {

		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);
		if (areas == null) {
			areas = new String[] { "" };
		}

		mCurrentCityId = mCitisIdsMap.get(mCurrentProviceId)[pCurrent];
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(
				EditAddressActivity.this, mAreaDatas));
		mViewDistrict.setCurrentItem(0);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {

		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = pDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}

		mCurrentProviceId = pIds[pCurrent];

		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(
				EditAddressActivity.this, mCityDatas));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	private void showMiddleDialog() {

		final AlertDialog dialog = new AlertDialog.Builder(
				EditAddressActivity.this).create();

		View view = LayoutInflater.from(EditAddressActivity.this).inflate(
				R.layout.newadd_dialog_layout, null);

		dialog.setView(view);
		dialog.show();

		Window window = dialog.getWindow();
		window.setContentView(R.layout.newadd_dialog_layout);
		TextView tv_dialog = (TextView) window
				.findViewById(R.id.newadd_tv_dialog);
		tv_dialog.setText("您确定要放弃编辑吗？");
		Button btnOk = (Button) window.findViewById(R.id.newadd_btn_ok);
		Button btnUndo = (Button) window.findViewById(R.id.newadd_btn_undo);

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				EditAddressActivity.this.finish();
			}
		});

		btnUndo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.dismiss();

			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			showMiddleDialog();

		}

		return super.onKeyDown(keyCode, event);
	}

}

package com.example.shopping;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kankan.wheel.widget.adapters.WheelViewAdapter;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.example.httpurl.HttpUrl;
import com.example.infoclass.MyAddressManage;
import com.example.wheel.OnWheelChangedListener;
import com.example.wheel.WheelView;
import com.example.wheel.adapters.ArrayWheelAdapter;
import com.exmaple.infoclass.ProvinceCityDistrictContent;
import com.exmaple.infoclass.ProvinceCityDistrictContent.TblCityDictEntity;
import com.exmaple.infoclass.ProvinceCityDistrictContent.TblCityDictEntity.TblDistrictDictEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ta.utdid2.android.utils.StringUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class NewaddActivity extends Activity implements OnClickListener,
		OnWheelChangedListener {

	private ImageView iv_newadd_back;
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private Gson gson;
	private String addressUrl = HttpUrl.HttpURL+"/Json/Get_Province_City_District_Info.aspx";
	private String submitUrl = HttpUrl.HttpURL+"/Json/Set_User_Address_Info.aspx";
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

	private TextView tv_newadd_confirm, tv_newadd_cancel, tv_newadd_receiver,
			tv_newadd_phonenumber, tv_newadd_postcode, tv_newadd_province,
			tv_newadd_detailedaddress, tv_newadd_chooseprovince;
	private EditText et_newadd_receiver, et_newadd_phonenumber,
			et_newadd_postcode, et_newadd_detailedaddress;
	private LinearLayout ll_myWheelView, ll_newadd_keep;
	private RelativeLayout rl_newadd_mask, rl_newadd_title;
	private ScrollView sv_newadd;
	private String receiverStr, phonenumberStr, postcodeStr, chooseprovinceStr,
			detailedaddressStr;
	
	public static  boolean isNewaddAddress=false;
	
	private String userID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newadd);

		getUserId();
		
		iv_newadd_back = (ImageView) findViewById(R.id.img_newadd_back);
		iv_newadd_back.setOnClickListener(this);
		rl_newadd_mask = (RelativeLayout) findViewById(R.id.rl_newadd_mask);
		rl_newadd_mask.setOnClickListener(this);
		rl_newadd_title = (RelativeLayout) findViewById(R.id.rl_newadd_title);
		tv_newadd_chooseprovince = (TextView) findViewById(R.id.tv_newadd_chooseprovince);
		tv_newadd_chooseprovince.setOnClickListener(this);

		et_newadd_receiver = (EditText) findViewById(R.id.et_newadd_receiver);

		et_newadd_phonenumber = (EditText) findViewById(R.id.et_newadd_phonenumber);

		et_newadd_postcode = (EditText) findViewById(R.id.et_newadd_postcode);

		et_newadd_detailedaddress = (EditText) findViewById(R.id.et_newadd_detailedaddress);

		ll_myWheelView = (LinearLayout) findViewById(R.id.myWheelView);
		tv_newadd_cancel = (TextView) findViewById(R.id.tv_newadd_cancel);
		tv_newadd_cancel.setOnClickListener(this);
		ll_newadd_keep = (LinearLayout) findViewById(R.id.ll_newadd_keep);
		ll_newadd_keep.setOnClickListener(this);
		tv_newadd_receiver = (TextView) findViewById(R.id.tv_newadd_receiver);
		tv_newadd_phonenumber = (TextView) findViewById(R.id.tv_newadd_phonenumber);
		tv_newadd_postcode = (TextView) findViewById(R.id.tv_newadd_postcode);
		tv_newadd_postcode.setOnClickListener(this);
		tv_newadd_province = (TextView) findViewById(R.id.tv_newadd_province);
		tv_newadd_detailedaddress = (TextView) findViewById(R.id.tv_newadd_detailedaddress);
		sv_newadd = (ScrollView) findViewById(R.id.sv_newadd);
		sv_newadd.setOverScrollMode(sv_newadd.OVER_SCROLL_NEVER);

		setUpViews();
		setUpListener();
		getJSONOkhttpPost();

	}

	private void getUserId(){
		
		SharedPreferences preferences=this.getSharedPreferences("user",Context.MODE_PRIVATE);
		userID=preferences.getString("userID","");
		
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

										mCityDatas.add(tcdeList.get(i).CityName);
										mCityIds.add(tcdeList.get(i).CityID);
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

						mViewProvince
								.setViewAdapter(new ArrayWheelAdapter<String>(
										NewaddActivity.this, mProvinceDatas));
						// 设置可见条目数量
						mViewProvince.setVisibleItems(5);
						mViewCity.setVisibleItems(5);
						mViewDistrict.setVisibleItems(5);

						mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(
								NewaddActivity.this, mCityDatas));
						mViewCity.setCurrentItem(0);
						mViewDistrict
								.setViewAdapter(new ArrayWheelAdapter<String>(
										NewaddActivity.this, mAreaDatas));
						mViewDistrict.setCurrentItem(0);
						initProvinceDatas();
					}

					@Override
					public void onError(Call arg0, Exception arg1) {

					}
				});

	}

	private void initProvinceDatas() {
		// 这段经典啊,就是公式
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
							districtList.get(k).DistrictName, 0);
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
							null, districtList.get(k).DistrictID);
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
		tv_newadd_confirm = (TextView) findViewById(R.id.tv_newadd_confirm);
	}

	private void setUpListener() {

		// 添加change事件
		mViewProvince.addChangingListener(this);
		// 添加change事件
		mViewCity.addChangingListener(this);
		// 添加change事件
		mViewDistrict.addChangingListener(this);

		tv_newadd_confirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.img_newadd_back:
			// Intent intent;
			// if (AddressManageActivity.data.size()==0) {
			// intent = new Intent(this, MyOrderActivity.class);
			// } else {
			// intent = new Intent(this, AddressManageActivity.class);
			// }
			// startActivity(intent);

			receiverStr = et_newadd_receiver.getText().toString().trim();
			phonenumberStr = et_newadd_phonenumber.getText().toString().trim();
			postcodeStr = et_newadd_postcode.getText().toString().trim();
			detailedaddressStr = et_newadd_detailedaddress.getText().toString()
					.trim();

			if (!TextUtils.isEmpty(receiverStr)
					|| !TextUtils.isEmpty(phonenumberStr)
					|| !TextUtils.isEmpty(postcodeStr)
					|| !TextUtils.isEmpty(chooseprovinceStr)
					|| !TextUtils.isEmpty(detailedaddressStr)) {
				showDialog();
			} else {
				
				finish();
			}

			break;

		case R.id.ll_newadd_keep:

			receiverStr = et_newadd_receiver.getText().toString().trim();
			phonenumberStr = et_newadd_phonenumber.getText().toString().trim();
			postcodeStr = et_newadd_postcode.getText().toString().trim();
			detailedaddressStr = et_newadd_detailedaddress.getText().toString()
					.trim();

			if (TextUtils.isEmpty(receiverStr)
					|| TextUtils.isEmpty(phonenumberStr)
					|| TextUtils.isEmpty(postcodeStr)
					|| TextUtils.isEmpty(chooseprovinceStr)
					|| TextUtils.isEmpty(detailedaddressStr)) {

				Toast.makeText(NewaddActivity.this, "请完善收货信息", 0).show();

			} else {

				if (!isPhonenumber() || !isPostcode()) {
					Toast.makeText(NewaddActivity.this, "手机号码或邮政编码输入有误", 0)
							.show();
				} else {

					submitInformation();

					Toast.makeText(NewaddActivity.this, "保存成功", 0).show();
					    isNewaddAddress=true;
						
					}
				
			}

			break;

		case R.id.tv_newadd_chooseprovince:
			rl_newadd_mask.setBackgroundColor(Color
					.argb(0xff, 0x99, 0x99, 0x99));
			rl_newadd_title.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));
			iv_newadd_back.setClickable(false);
			ll_myWheelView.setVisibility(View.VISIBLE);
			et_newadd_receiver.setEnabled(false);
			et_newadd_phonenumber.setEnabled(false);
			et_newadd_postcode.setEnabled(false);
			et_newadd_detailedaddress.setEnabled(false);
			ll_newadd_keep.setClickable(false);
		
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(rl_newadd_mask.getWindowToken(), 0);

			break;

		case R.id.tv_newadd_cancel:
			ll_myWheelView.setVisibility(View.GONE);
			rl_newadd_mask.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));
			rl_newadd_title.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));
			iv_newadd_back.setClickable(true);
			et_newadd_receiver.setEnabled(true);
			et_newadd_phonenumber.setEnabled(true);
			et_newadd_postcode.setEnabled(true);
			et_newadd_detailedaddress.setEnabled(true);
			ll_newadd_keep.setClickable(true);
			
			break;

		case R.id.tv_newadd_confirm:
			rl_newadd_mask.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));
			rl_newadd_title.setBackgroundColor(Color.rgb(0xff, 0xff, 0xff));
			tv_newadd_chooseprovince.setText(mCurrentProviceName + "-"
					+ mCurrentCityName + "-" + mCurrentDistrictName);
			chooseprovinceStr = mCurrentProviceName + "-" + mCurrentCityName
					+ "-" + mCurrentDistrictName;
			ll_myWheelView.setVisibility(View.GONE);
			iv_newadd_back.setClickable(true);
			et_newadd_receiver.setEnabled(true);
			et_newadd_phonenumber.setEnabled(true);
			et_newadd_postcode.setEnabled(true);
			et_newadd_detailedaddress.setEnabled(true);
			ll_newadd_keep.setClickable(true);
			
			break;

		case R.id.tv_newadd_postcode:
			//Toast.makeText(NewaddActivity.this, "我是邮政编码", 0).show();

			break;
		default:
			break;
		}

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

		Matcher m = p.matcher(postcodeStr);

		return m.matches();

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
				NewaddActivity.this, mAreaDatas));
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
				NewaddActivity.this, mCityDatas));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	// 提交数据
	private void submitInformation() {

		OkHttpUtils.post().url(submitUrl)
				.addParams("AcctID",userID)
				.addParams("Consigner", receiverStr)
				.addParams("PhoneNumber", phonenumberStr)
				.addParams("ZipCode", postcodeStr)
				.addParams("Address", detailedaddressStr)
				.addParams("DistrictID", Integer.toString(mCurrentDistrictId))
				.build().execute(new StringCallback() {

					@Override
					public void onResponse(String arg0) {
						finish();
						
					}

					@Override
					public void onError(Call arg0, Exception arg1) {
					}
				});

	}

	// 显示对话框
	private void showDialog() {

		final AlertDialog dialog = new AlertDialog.Builder(NewaddActivity.this)
				.create();

		View view = LayoutInflater.from(NewaddActivity.this).inflate(
				R.layout.newadd_dialog_layout, null);

		dialog.setView(view);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.newadd_dialog_layout);
		TextView tv_dialog = (TextView) window
				.findViewById(R.id.newadd_tv_dialog);
		tv_dialog.setText("您确定要舍弃吗？");
		Button btnOk = (Button) window.findViewById(R.id.newadd_btn_ok);
		Button btnUndo = (Button) window.findViewById(R.id.newadd_btn_undo);

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intentBack = new Intent();
				setResult(1, intentBack);
				
				finish();
			}
		});

		btnUndo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			receiverStr = et_newadd_receiver.getText().toString().trim();
			phonenumberStr = et_newadd_phonenumber.getText().toString().trim();
			postcodeStr = et_newadd_postcode.getText().toString().trim();
			detailedaddressStr = et_newadd_detailedaddress.getText().toString()
					.trim();
			chooseprovinceStr = mCurrentProviceName + mCurrentCityName
					+ mCurrentDistrictName;

			if (!TextUtils.isEmpty(receiverStr)
					|| !TextUtils.isEmpty(phonenumberStr)
					|| !TextUtils.isEmpty(postcodeStr)
					|| !TextUtils.isEmpty(chooseprovinceStr)
					|| !TextUtils.isEmpty(detailedaddressStr)) {

				showDialog();
			} else {

				finish();
			}

		}

		return true;
	}

	/*
	 * //设置屏幕透明度 private void setWindowParamsAttributes(Activity activity,float
	 * alpha){
	 * 
	 * WindowManager.LayoutParams lp=activity.getWindow().getAttributes();
	 * lp.alpha=alpha; activity.getWindow().setAttributes(lp);
	 * 
	 * }
	 */

}

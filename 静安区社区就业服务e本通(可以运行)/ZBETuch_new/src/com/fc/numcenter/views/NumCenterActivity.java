package com.fc.numcenter.views;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.fc.company.beans.CompanyTask;
import com.fc.first.beans.GetUsrInformation;
import com.fc.first.beans.LocationInformation;
import com.fc.first.views.FirstPageActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.fc.main.service.MainService;
import com.fc.main.service.MenuService;
import com.fc.main.tools.HttpUrls_;
import com.fc.main.views.MainPageActivity;
import com.fc.person.views.AttentionActivity;
import com.fc.person.views.ERecordActivity;
import com.fc.worktodate.views.WorkToDateListActivity;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NumCenterActivity extends ActivityGroup implements IActivity,
		OnCheckedChangeListener, OnPageChangeListener, OnClickListener {
	public static double GPSLOCATION_LAT = 0;
	public static double GPSLOCATION_LNG = 0;
	public static double GPSLOCATION_ALT = 0;

	private RadioGroup radioGroup;
	private RadioButton radioButton1, radioButton2, radioButton3, radioButton4,
			radioButton5, radioButton6, radioButton7;
	private ViewPager viewPager;

	private List<RadioButton> useRadioButtons = new ArrayList<RadioButton>();
	private List<View> views = new ArrayList<View>();

	private float mCurrentCheckRadioLeft;
	private ImageView mImageView;
	private HorizontalScrollView mHorizontalScrollView;
	private PagerAdapter pagerAdapter;
	private ImageView img_personHead;
	private TextView tv_officername, tv_jobnumberinput, tv_personal_message;
	private Button btn_mainimage, button_shouye;

	public static final int REFRESH_PERSON = 0;
	public static final int REFRESH_PERSON_NAME = 1;

	private Activity activity = this;

	private LocationManager locationManager;

	private TextView latLocationText, lngLocationText, altLocationText;

	private ArrayList<LocationInformation> locationinfoJson = new ArrayList<LocationInformation>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_num_center);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		init();
		initView();
		initListener();
		GPSLocation();

		// 得到登录人员信息
		CompanyTask task1 = new CompanyTask(CompanyTask.NUM_CENTER_NAME_IFNO,
				null);
		CompanyService.newTask(task1);

		// 刷新登录人员照片
		CompanyTask task2 = new CompanyTask(CompanyTask.NUM_CENTER_INFO, null);
		CompanyService.newTask(task2);
	}

	private void initView() {
		latLocationText = (TextView) findViewById(R.id.tv_latlocation);
		lngLocationText = (TextView) findViewById(R.id.tv_lnglocation);
		altLocationText = (TextView) findViewById(R.id.tv_altlocation);

		btn_mainimage = (Button) this.findViewById(R.id.btn_mainimage);
		button_shouye = (Button) this.findViewById(R.id.button_shouye);
		tv_personal_message = (TextView) this
				.findViewById(R.id.bt_personal_message);

		img_personHead = (ImageView) this.findViewById(R.id.imgvw_ownphoto);
		tv_officername = (TextView) this.findViewById(R.id.tv_officername);
		tv_jobnumberinput = (TextView) this
				.findViewById(R.id.tv_jobnumberinput);

		mHorizontalScrollView = (HorizontalScrollView) this
				.findViewById(R.id.mHorizontalScrollView);

		radioGroup = (RadioGroup) this.findViewById(R.id.mRadioGroup);
		radioButton1 = (RadioButton) this.findViewById(R.id.mRadioButton1);
		radioButton2 = (RadioButton) this.findViewById(R.id.mRadioButton2);
		radioButton3 = (RadioButton) this.findViewById(R.id.mRadioButton3);
		radioButton4 = (RadioButton) this.findViewById(R.id.mRadioButton4);
		radioButton5 = (RadioButton) this.findViewById(R.id.mRadioButton5);
		radioButton6 = (RadioButton) this.findViewById(R.id.mRadioButton6);
		radioButton7 = (RadioButton) this.findViewById(R.id.mRadioButton7);

		useRadioButtons.add(radioButton1);
		useRadioButtons.add(radioButton2);
		useRadioButtons.add(radioButton3);
		useRadioButtons.add(radioButton4);
		useRadioButtons.add(radioButton5);
		useRadioButtons.add(radioButton6);
		useRadioButtons.add(radioButton7);

		mImageView = (ImageView) this.findViewById(R.id.mImageView);

		viewPager = (ViewPager) this.findViewById(R.id.mViewPager);

		views.add(getLocalActivityManager().startActivity("activity01",
				new Intent(this, LoginInfoActivity.class)).getDecorView());//登录信息
		views.add(getLocalActivityManager().startActivity("activity02",
				new Intent(this, WorkInfoActivity.class)).getDecorView());//工作日志
		views.add(getLocalActivityManager().startActivity("activity03",
				new Intent(this, RedecodeActivity.class)).getDecorView());//操作记录
		views.add(getLocalActivityManager().startActivity("activity04",
				new Intent(this, AttentionInfoActivity.class)).getDecorView());//关注列表
		views.add(getLocalActivityManager().startActivity("activity05",
				new Intent(this, ERecordActivity.class)).getDecorView());//系统安装情况
		views.add(getLocalActivityManager().startActivity("activity06",
				new Intent(this, MeetingReadActivity.class)).getDecorView());//会议通知读取率
		views.add(getLocalActivityManager().startActivity("activity07",
				new Intent(this, WorkReadGroupActivity.class)).getDecorView());//工作通知读取率

		// 设置ViewPager的Adaper

		pagerAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}
		};
		// mViewPager.setOffscreenPageLimit(5);
		viewPager.setAdapter(pagerAdapter);

		useRadioButtons.get(0).setChecked(true);
		viewPager.setCurrentItem(0);
		mCurrentCheckRadioLeft = getCurrentCheckRadioLeft();

	}

	private void initListener() {
		btn_mainimage.setOnClickListener(this);
		button_shouye.setOnClickListener(this);
		radioGroup.setOnCheckedChangeListener(this);
		viewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		AnimationSet _AnimationSet = new AnimationSet(true);
		TranslateAnimation _TranslateAnimation;
		int i = 0;
		for (i = 0; i < useRadioButtons.size(); i++) {
			if (useRadioButtons.get(i).isChecked()) {
				break;
			}
		}
		_TranslateAnimation = new TranslateAnimation(mCurrentCheckRadioLeft,
				getResources().getDimension(R.dimen.rdo7) * i, 0f, 0f);
		_AnimationSet.addAnimation(_TranslateAnimation);
		_AnimationSet.setFillAfter(true);
		_AnimationSet.setFillBefore(false);
		mImageView.setAnimation(_AnimationSet);
		viewPager.setCurrentItem(i);
		// 更新当前红色的横�?
		mCurrentCheckRadioLeft = getCurrentCheckRadioLeft();
		// mHorizontalScrollView.smoothScrollTo((int)mCurrentCheckRadioLeft, 0);
		mHorizontalScrollView.smoothScrollBy((int) mCurrentCheckRadioLeft
				- (int) getResources().getDimension(R.dimen.rdo7), 0);

	}

	/**
	 * 滑动ViewPager的时�? *
	 * 
	 * @return
	 */

	private float getCurrentCheckRadioLeft() {
		// 判断哪一个RadioButton
		int i = 0;
		for (i = 0; i < useRadioButtons.size(); i++) {
			if (useRadioButtons.get(i).isChecked()) {
				break;
			}
		}
		return getResources().getDimension(R.dimen.rdo7) * i;

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		useRadioButtons.get(position).setChecked(true);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		CompanyService.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case REFRESH_PERSON:
			if (params[1] != null) {
				byte[] data = (byte[]) params[1];
				if (data.length > 0) {
					Bitmap map = BitmapFactory.decodeByteArray(data, 0,
							data.length);
					img_personHead.setImageBitmap(map);
				}
			}
			break;

		case REFRESH_PERSON_NAME:
			if (params[1] != null) {
				String staffStr = params[1].toString();
				refreshStaff(staffStr);
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 刷新登录人员信息
	 * 
	 * @param staffStr
	 */
	private void refreshStaff(String staffStr) {

		try {
			JSONObject object = new JSONObject(staffStr);

			String id = object.getString("ID");
			String name = object.getString("NAME");
			String input_code = object.getString("INPUT_CODE");
			String pwd = object.getString("PWD");
			String phone = object.getString("PHONE");
			String email = object.getString("EMAIL");
			String photo = object.getString("PHOTO");
			String create_date = object.getString("CREATE_DATE");
			String create_staff = object.getString("CREATE_STAFF");
			String update_date = object.getString("UPDATE_DATE");
			String update_staff = object.getString("UPDATE_STAFF");
			String stop = object.getString("STOP");
			String enable = object.getString("Enable");
			String dept = object.getString("DEPT");
			GetUsrInformation userinfo = new GetUsrInformation();
			userinfo.setId(id);
			userinfo.setName(name);
			userinfo.setInput_Code(input_code);
			userinfo.setPwd(pwd);
			userinfo.setPhone(phone);
			userinfo.setEmail(email);
			userinfo.setPhoto(photo);
			userinfo.setCreate_Date(create_date);
			userinfo.setCreate_Staff(create_staff);
			userinfo.setUpdate_Date(update_date);
			userinfo.setUpdate_Staff(update_staff);
			userinfo.setStop(stop);
			userinfo.setEnable(enable);
			userinfo.setDept(dept);

			tv_jobnumberinput.setText("操作员:" + userinfo.getName());
			tv_officername.setText("部门：" + userinfo.getDept());
			tv_personal_message.setText("电话:" + userinfo.getPhone());
		} catch (JSONException e1) {
			e1.printStackTrace();
		}

	}

	/*
	 * 功能：GPS定位服务
	 * 
	 * @author clf
	 * 
	 * @since 2013/5/21
	 */
	public void GPSLocation() {

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setAltitudeRequired(true);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW);

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		String provider = locationManager.getBestProvider(criteria, true);
		Location location = locationManager.getLastKnownLocation(provider);

		updateView(location);

		// 判断GPS是否可用
		// System.out.println("state="+locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
		// 一秒为 1*1000
		locationManager.requestLocationUpdates(provider, 120 * 1000, 10,
				new LocationListener() {

					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						// location为变化完的新位置，更新显示
						updateView(location);
					}

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub
						updateView(locationManager
								.getLastKnownLocation(provider));

					}

					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub
						updateView(null);

					}

				});
	}

	private void updateView(Location location) {
		// TODO Auto-generated method stub
		String latString;
		String lngString;
		String altString;
		LocationInformation locationInfo = new LocationInformation();

		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			double alt = location.getAltitude();
			double hgt = alt / 3.2808398950131;
			BigDecimal b = new BigDecimal(hgt);
			double hgt1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

			GPSLOCATION_LAT = lat;
			GPSLOCATION_LNG = lng;
			GPSLOCATION_ALT = hgt1;

			latString = "经度：" + lng;
			lngString = "纬度：" + lat;
			altString = "高度：" + hgt1 + "米";

			latLocationText.setText(latString);
			lngLocationText.setText(lngString);
			altLocationText.setText(altString);
			locationInfo.setLongitude(lng);
			locationInfo.setLatitude(lat);
			locationInfo.setHeight(hgt1);
			locationinfoJson.add(locationInfo);
			String postJson = HttpUrls_.postJson1(locationinfoJson);
			Log.i("发送的json信息。。。。", postJson);

		} else {
			lngString = "无法获取地理信息";
			latLocationText.setText(lngString);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_mainimage:
			Intent intent1 = new Intent(activity, MainPageActivity.class);
			intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent1);
			break;

		case R.id.button_shouye:
			Intent intent2 = new Intent(activity, FirstPageActivity.class);
			intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent2);
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MenuService.exitInfoMenuActivity();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}

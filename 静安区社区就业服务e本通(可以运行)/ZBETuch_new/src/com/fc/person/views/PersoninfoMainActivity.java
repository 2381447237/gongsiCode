package com.fc.person.views;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.fc.first.views.FirstPageActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.service.MenuService;
import com.fc.main.service.PersonService;
import com.fc.main.views.MainPageActivity;
import com.fc.person.beans.PersonTask;
import com.fc.person.beans.PersonalBaseInformation;
import com.test.zbetuch_news.R;

/**
 * 人员信息主界面（个人信息，家庭信息，工作经验，就业信息，个人简历。。。）
 * 
 * @author hxl zdz
 * 
 */
public class PersoninfoMainActivity extends ActivityGroup implements
		OnCheckedChangeListener, OnClickListener, IActivity {
	private HorizontalScrollView mHorizontalScrollView; // 可以横向滑动的标签
	private RadioGroup mRadioGroup;
	private RadioButton mRadioButton1;
	private RadioButton mRadioButton5;
	private RadioButton mRadioButton4;
	private RadioButton mRadioButton3;
	private RadioButton mRadioButton2;
	private RadioButton mRadioButton6;
	private RadioButton mRadioButton7;
	private ImageView mImageView;
	private Button btn_cehua, btn_shouye, btn_zhuye, btn_modify;// 侧滑按钮，首页按钮，九宫格按钮
	private TextView tv_personname, tv_personsex, tv_zt, tv_sfz;// 姓名，性别，状态，身份证
	private ImageView img_personHead;// 人员头像
	private ViewPager mViewPager;
	private List<View> mListView;
	private float mCurrentCheckRadioLeft;
	private String personSFZ;
	private String query_sfz;
	private boolean SFZCheck;
	private String personBase;
	private ArrayList<PersonalBaseInformation> personinfoJson = new ArrayList<PersonalBaseInformation>();
	private ProgressDialog progressDialog;
	private LinearLayout llxinxi;
	private LinearLayout llcontent;
	private Animation showAnim, hideAnim = null;
	private boolean flagXinxiShow = true;// 信息显示与否标记
	private String checkPersoninfo;
	private String mysfz;
	private PagerAdapter pagerAdapter;

	/**
	 * 个人详细信息是否已加载完毕
	 */
	public static boolean isDetailOver = true;
	/**
	 * 个人家庭信息是否已加载完毕
	 */
	public static boolean isFamilyOver = false;

	/**
	 * 更新个人基本信息
	 */
	public static final int REFRESH_PERSONBASE = 1;

	/**
	 * 更新个人头像
	 */
	public static final int REFRESH_PERSONIMG = 2;

	/**
	 * 得到个人权限
	 */
	public static final int REFRESH_PERSONLEVEL = 3;

	private String[] names = new String[] { "个人基本信息", "家庭信息", "个人简历", "教育信息",
			"劳动经历", "e记录", "服务记录" };
	private String[] values;
	private List<RadioButton> usefulButtons = new ArrayList<RadioButton>();

	Map<String, Object> data;

	public static void Vibrate(final Activity activity, long milliseconds) {
		Vibrator vib = (Vibrator) activity
				.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		isDetailOver = true;
		isFamilyOver = false;

		// 获得从查询界面出传递过来的数据
		Intent intentmain = this.getIntent();
		Bundle bundlemain = intentmain.getExtras();
		personSFZ = bundlemain.getString("mysfz");
		SFZCheck = bundlemain.getBoolean("sfzCheck");
		query_sfz = bundlemain.getString("mysfz");
		mysfz = bundlemain.getString("mysfz");

		showDialog(PersoninfoMainActivity.this);

		setContentView(R.layout.personmain);

		init();
		initViews();
		initListener();

		data = new HashMap<String, Object>();
		data.put("sfz", mysfz);
		PersonTask task = new PersonTask(
				PersonTask.PERSONINFOMAINACTIVITY_GETPERSONBASE, data);
		PersonService.newTask(task);

	}

	private void initViews() {
		llxinxi = (LinearLayout) findViewById(R.id.llxinxi);
		llcontent = (LinearLayout) findViewById(R.id.llcontent);
		btn_cehua = (Button) findViewById(R.id.button_cehua);
		btn_shouye = (Button) findViewById(R.id.button_shouye);
		btn_zhuye = (Button) findViewById(R.id.button_zhuye);

		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.mHorizontalScrollView);
		mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
		mRadioButton1 = (RadioButton) findViewById(R.id.mRadioButton1);
		mRadioButton2 = (RadioButton) findViewById(R.id.mRadioButton2);
		mRadioButton3 = (RadioButton) findViewById(R.id.mRadioButton3);
		mRadioButton4 = (RadioButton) findViewById(R.id.mRadioButton4);
		mRadioButton5 = (RadioButton) findViewById(R.id.mRadioButton5);
		mRadioButton6 = (RadioButton) findViewById(R.id.mRadioButton6);
		mRadioButton7 = (RadioButton) findViewById(R.id.mRadioButton7);
		mImageView = (ImageView) findViewById(R.id.mImageView);
		mViewPager = (ViewPager) findViewById(R.id.mViewPager);

		tv_personname = (TextView) findViewById(R.id.tv_personname);
		tv_personsex = (TextView) findViewById(R.id.tv_personsex);
		tv_zt = (TextView) findViewById(R.id.tv_zt);
		img_personHead = (ImageView) findViewById(R.id.imgvw_ownphoto);
		tv_sfz = (TextView) findViewById(R.id.tv_sfz);
		btn_modify = (Button) findViewById(R.id.button_personModify);

		// 设置显示隐藏动画
		showAnim = AnimationUtils.loadAnimation(this,
				R.anim.for_xinxi_show_animation);
		hideAnim = AnimationUtils.loadAnimation(this,
				R.anim.for_xinxi_hide_animation);
	}

	private void initListener() {
		btn_cehua.setOnClickListener(this);
		btn_shouye.setOnClickListener(this);
		btn_zhuye.setOnClickListener(this);
		mRadioGroup.setOnCheckedChangeListener(this);
		mViewPager.setOnPageChangeListener(new MyOnPageChangerListener());
		btn_modify.setOnClickListener(this);
	}

	/**
	 * 人员信息加载提示框
	 * 
	 * @param context
	 */
	public void showDialog(Context context) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.show();
	}

	/**
	 * 滑动Viewpager监听事件
	 * 
	 * @author Administrator
	 * 
	 */
	class MyOnPageChangerListener implements OnPageChangeListener {
		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int index) {
			usefulButtons.get(index).setChecked(true);
		}

	}

	/**
	 * 点击RadioGroup监听事件
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		AnimationSet _AnimationSet = new AnimationSet(true);
		TranslateAnimation _TranslateAnimation;
		int i = 0;
		for (i = 0; i < usefulButtons.size(); i++) {
			if (usefulButtons.get(i).isChecked()) {
				break;
			}
		}
		_TranslateAnimation = new TranslateAnimation(mCurrentCheckRadioLeft,
				getResources().getDimension(R.dimen.rdo2) * i, 0f, 0f);
		_AnimationSet.addAnimation(_TranslateAnimation);
		_AnimationSet.setFillAfter(true);
		_AnimationSet.setFillBefore(false);
		mImageView.setAnimation(_AnimationSet);
		mViewPager.setCurrentItem(i);
		// 更新当前红色的横�?
		mCurrentCheckRadioLeft = getCurrentCheckRadioLeft();
		mHorizontalScrollView.smoothScrollBy((int) mCurrentCheckRadioLeft
				- (int) getResources().getDimension(R.dimen.rdo2), 0);

	}

	/**
	 * 滑动ViewPager的时�? *
	 * 
	 * @return
	 */

	private float getCurrentCheckRadioLeft() {
		// 判断哪一个RadioButton
		int i = 0;
		for (i = 0; i < usefulButtons.size(); i++) {
			if (usefulButtons.get(i).isChecked()) {
				break;
			}
		}
		return getResources().getDimension(R.dimen.rdo2) * i;

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_cehua:
			Vibrate(PersoninfoMainActivity.this, 80);
			if (flagXinxiShow) {// 如果点之前菜单是显示的，那么菜单就隐藏
				flagXinxiShow = false;
				llxinxi.setVisibility(View.GONE);
				llcontent.startAnimation(hideAnim);

			} else {// 如果点之前是隐藏的，就显示
				flagXinxiShow = true;
				llxinxi.setVisibility(View.VISIBLE);
				llcontent.startAnimation(showAnim);
			}
			break;
		case R.id.button_shouye:
			if (isDetailOver && isFamilyOver) {
				Intent intentshouye = new Intent(PersoninfoMainActivity.this,
						FirstPageActivity.class);
				intentshouye.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intentshouye);
			} else {
				Toast.makeText(PersoninfoMainActivity.this, "正在加载数据，请稍候。",
						Toast.LENGTH_SHORT).show();
			}

			// PersoninfoMainActivity.this.finish();
			break;
		case R.id.button_zhuye:
			if (isDetailOver && isFamilyOver) {
				Intent intentzhuye = new Intent(PersoninfoMainActivity.this,
						MainPageActivity.class);
				intentzhuye.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intentzhuye);
			} else {
				Toast.makeText(PersoninfoMainActivity.this, "正在加载数据，请稍候。",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.button_personModify:
			showConfirmDialog("修改信息提示", "您确定修改此人的信息？");
		}

	}

	private void showConfirmDialog(String title, String msg) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(
				PersoninfoMainActivity.this);
		dialog.setTitle(title).setMessage(msg)
				.setIcon(android.R.drawable.ic_dialog_info).setCancelable(true)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					
						switchModifyActivity();

					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		AlertDialog alert = dialog.create();
		alert.show();
	}

	protected void switchModifyActivity() {
		switch (mRadioGroup.getCheckedRadioButtonId()) {
		// 修改个人信息
		case R.id.mRadioButton1:
			Intent intentmodify = new Intent(PersoninfoMainActivity.this,
					ModifyPersonInfoActivity.class);
			intentmodify.putExtra("personbasejson", personBase);
			intentmodify.putExtra("sfz", mysfz);
			startActivity(intentmodify);
			break;
		// 修改个人简历
		case R.id.mRadioButton3:
			Intent intentresume = new Intent(PersoninfoMainActivity.this,
					ModifyPersonResumeActivity.class);
			intentresume.putExtra("sfz", mysfz);
			startActivity(intentresume);
			break;
		// 修改教育经历
		case R.id.mRadioButton5:
			Intent intentSchool = new Intent(PersoninfoMainActivity.this,
					ModifySchoolInfoActivity.class);
			intentSchool.putExtra("sfz", mysfz);
			startActivity(intentSchool);
			break;
		// 修改工作经历
		case R.id.mRadioButton6:
			Intent intent = new Intent(PersoninfoMainActivity.this,
					ModifyWorkInfoActivity.class);
			intent.putExtra("mysfz", mysfz);
			startActivity(intent);
			break;
		}
	}

	// 提示框消失处理
	public void DismissDialog() {
		try {
			if (PersoninfoMainActivity.this != null
					&& !PersoninfoMainActivity.this.isFinishing()
					&& progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PersonService.allActivity.remove(this);
		DismissDialog();
	}

	@Override
	public void init() {
		PersonService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case PersoninfoMainActivity.REFRESH_PERSONBASE:
			DismissDialog();
			if (params[1] != null) {
				personBase = params[1].toString().trim();
				if (personBase.equals("[null]")) {
					Toast.makeText(this, "对不起，查无此人！", Toast.LENGTH_LONG).show();
					finish();
				} else {
					try {
						JSONArray jsonArray = new JSONArray(personBase);
						JSONObject jsonObject = jsonArray.getJSONObject(0);
						String personLevel = jsonObject.getString("LevelMsg");
						if (personLevel.equals("null")) {
							tv_personname.setText(jsonObject.getString("NAME"));
							tv_personsex.setText(jsonObject.getString("SEX"));
							tv_zt.setText(jsonObject.getString("TYPE"));
							tv_sfz.setText(jsonObject.getString("SFZ"));

							data = new HashMap<String, Object>();
							data.put("sfz", mysfz);
							PersonTask task = new PersonTask(
									PersonTask.PERSONINFOMAINACTIVITY_GETPERSONIMG,
									data);
							PersonService.newTask(task);

							data = new HashMap<String, Object>();
							data.put("names", names);
							PersonTask task2 = new PersonTask(
									PersonTask.PERSONINFOMAINACTIVITY_GETPERSONLEVEL,
									data);
							PersonService.newTask(task2);

						} else {
							Toast.makeText(this, personLevel, Toast.LENGTH_LONG)
									.show();
							finish();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
			break;
		case PersoninfoMainActivity.REFRESH_PERSONIMG:
			if (params[1] != null) {
				byte[] data = (byte[]) params[1];
				if (data.length > 0) {
//					Bitmap map = BitmapFactory.decodeByteArray(data, 0,data.length);
//					img_personHead.setImageBitmap(map);
					
					InputStream is=new  ByteArrayInputStream(data);
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 2;//图片大小，设置越大，图片越不清晰，占用空间越小
					Bitmap bmp=BitmapFactory.decodeStream(is,null,options);
					img_personHead.setImageBitmap(bmp);
				}
			}
			break;
		case PersoninfoMainActivity.REFRESH_PERSONLEVEL:
			if (params[1] != null && !"".equals(params[1].toString().trim())) {
				values = (String[]) params[1];
				
				
				
				refreshSubFrm();
			}
			break;
		}

	}

	/**
	 * 根据权限显示子页面
	 */
	
	private void refreshSubFrm() {
		// { "个人基本信息", "家庭信息", "个人简历", "教育信息",
		// "劳动经历", "e记录","服务选项卡" };

		mListView = new ArrayList<View>();

		// 个人基本信息
		if (values[0].trim().equalsIgnoreCase("true")) {
			mListView.add(getLocalActivityManager().startActivity(
					"activity01",
					new Intent(this, AddPersonInfoActivity.class).putExtra(
							"sfz", mysfz)).getDecorView());
			usefulButtons.add(mRadioButton1);
		} else {
			mRadioButton1.setVisibility(View.GONE);
		}

		// 家庭信息
		if (values[1].trim().equalsIgnoreCase("true")) {
			mListView.add(getLocalActivityManager().startActivity(
					"activity02",
					new Intent(this, FamilyActivity2.class).putExtra("mysfz",
							mysfz)).getDecorView());
			usefulButtons.add(mRadioButton2);
		} else {
			mRadioButton2.setVisibility(View.GONE);
		}

		// 个人简历
		if (values[2].trim().equalsIgnoreCase("true")) {
			mListView.add(getLocalActivityManager().startActivity(
					"activity03",
					new Intent(this, PersonResumeActivity.class).putExtra(
							"sfz", mysfz)).getDecorView());
			usefulButtons.add(mRadioButton3);
		} else {
			mRadioButton3.setVisibility(View.GONE);
		}

		// 服务记录
		if (values[3].trim().equalsIgnoreCase("true")) {
			mListView.add(getLocalActivityManager().startActivity(
					"activity04",
					new Intent(this, FuXuanXiangCardActivity.class).putExtra(
							"sfz", mysfz)).getDecorView());
			usefulButtons.add(mRadioButton4);
		} else {
			mRadioButton4.setVisibility(View.GONE);
		}

		// 教育信息
		if (values[4].trim().equalsIgnoreCase("true")) {
			mListView.add(getLocalActivityManager().startActivity(
					"activity05",
					new Intent(this, SchoolInfoActivity.class).putExtra(
							"mysfz", mysfz)).getDecorView());
			usefulButtons.add(mRadioButton5);
		} else {
			mRadioButton5.setVisibility(View.GONE);
		}

		// 劳动经历（就业信息）
		if (values[5].trim().equalsIgnoreCase("true")) {
			mListView.add(getLocalActivityManager().startActivity(
					"activity06",
					new Intent(this, WorkInfoActivity.class).putExtra("mysfz",
							mysfz)).getDecorView());
			usefulButtons.add(mRadioButton6);
		} else {
			mRadioButton6.setVisibility(View.GONE);
		}

		// e记录
		if ("false".equalsIgnoreCase("true")) {
			mListView.add(getLocalActivityManager().startActivity("activity07",
					new Intent(this, ERecordActivity.class)).getDecorView());
			usefulButtons.add(mRadioButton7);
		} else {
			mRadioButton7.setVisibility(View.GONE);
		}

		// 设置ViewPager的Adaper

		pagerAdapter = new PagerAdapter() {
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return mListView.size();
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(mListView.get(position));
				return mListView.get(position);
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(mListView.get(position));
			}
		};
		// mViewPager.setOffscreenPageLimit(5);
		mViewPager.setAdapter(pagerAdapter);

		usefulButtons.get(0).setChecked(true);
		mViewPager.setCurrentItem(0);
		mCurrentCheckRadioLeft = getCurrentCheckRadioLeft();

		// 发送广播
		Intent itpersonmain = new Intent(
				"android.intent.action.MY_MianBROADCAST");
		itpersonmain.putExtra("sfz", personSFZ);
		itpersonmain.putExtra("sfzCheck", SFZCheck);
		itpersonmain.putExtra("querySfz", query_sfz);
		itpersonmain.putExtra("checkperson", checkPersoninfo);
		itpersonmain.putExtra("mysfz", mysfz);
		itpersonmain.putExtra("personjson", personBase);
		sendBroadcast(itpersonmain);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			MenuService.exitMenuActivity();
			finish();
			return true;
		} else
			return super.onKeyDown(keyCode, event);
	}

}
package com.fc.person.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fc.first.beans.Center;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PersonInfo;
import com.fc.main.myservices.MenuService;
import com.fc.main.myservices.PersonService;
import com.fc.main.tools.HttpUrls_;
import com.fc.person.beans.PersonMark;
import com.fc.person.beans.PersonTask;
import com.fc.person.beans.PersonalBaseInformation;
import com.fc.zbetuch_sm.R;

/**
 * 增加人员的基本信息
 * 
 * @author hxl
 * 
 */
public class AddPersonInfoActivity extends Activity implements IActivity, OnClickListener {
	private EditText tv_personaname, tv_personsex, tv_personborn,
			tv_personnational, tv_personNative, tv_tye, tv_cardnum,
			tv_education, tv_status, tv_intention, tv_phone, tv_personaddress,
			tv_personstreet, tv_juweihui, tv_hukoudizhi;
	private TextView tv_personMark, tv_markname, tv_marksource, tv_marktime,
			tv_personbeizhu, tv_updateTime,txtComareResult;
	private ImageView img_student, img_qihang, img_lingjiuye, img_jiuyekunnan,
			img_jiedu, img_xingjie;
	private View markview;
	private HorizontalScrollView horizontalScrollView;
	private String personSFZ;
	private String personmarkName;
	private String query_sfz;
	private String personjson;
	private Bitmap personImage;
	private boolean SFZCheck;
	private boolean markShow = true;
	private Center center = new Center();
	private static ArrayList<PersonalBaseInformation> personinfoJson = new ArrayList<PersonalBaseInformation>();
	private ArrayList<PersonMark> personmark;
	boolean hasShootPic = false;
	public static final String SDCARD_ROOT_PATH = android.os.Environment
			.getExternalStorageDirectory().getAbsolutePath();// 路径
	public static final String SAVE_PATH_IN_SDCARD = "/personBaseInfo.data/"; // 图片及其他数据保存文件夹
	public static final String IMAGE_CAPTURE_NAME = "personInfo.png"; // 照片名称
	private ProgressDialog progressDialog;
	private PopupWindow popupWindow;
	private MyReceiver myReceiver = new MyReceiver();
	
	private String sfz="";
	private String name="";
	Button btnAction;
	
	public static final int ADD_ACTION=1;
	
	public static final int RESULT_OK_INFO=2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置界面无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_addperson_b);

		init();
		initView();
		setListener();
		String sfzString=getIntent().getStringExtra("sfz");

		// 广播接收器的注册
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.MY_MianBROADCAST");
		registerReceiver(myReceiver, filter);
		
		Map<String, Object> params3=new HashMap<String, Object>();
		Map<String, String> data3=new HashMap<String, String>();
		data3.put("sfz", sfzString.trim());
		params3.put("data", data3);
		PersonTask task3=new PersonTask(PersonTask.FIRST_FIRST_INFOS, params3);
		PersonService.newTask(task3);
	}

	/**
	 * 人员的头像线程，获取头像
	 */
	Runnable personimg_thread = new Runnable() {
		public void run() {
			// 判断是否是身份证查询被点击
			if (SFZCheck == true) {
				personImage = HttpUrls_.getPersonImage(
						AddPersonInfoActivity.this, personSFZ);
			} else {
				personImage = HttpUrls_.getPersonImage(
						AddPersonInfoActivity.this, query_sfz);
			}
			Message msg_img = new Message();
			msg_img.what = 0x70;
			msg_img.obj = personImage;
			handler_baseinfo.obtainMessage(0x70, personImage);
			handler_baseinfo.sendMessage(msg_img);
		}
	};
	/**
	 * 线程获取数据后的处理 基本信息 头像 标识
	 */
	private Handler handler_baseinfo = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x60:
				Log.i("ddsssaaaaaa", personmark.toString());
				if (personmark.size() == 0) {
				} else {
					// 得到人员标识
					for (int i = 0; i < personmark.size(); i++) {
						personmarkName = personmark.get(i).getPersonMarkName();
						Log.i("fffff", personmarkName);
					}
					showPersonMark(personmark);
				}

				break;
			}
		}

	};

	/**
	 * 初始化个人信息
	 */
	private void initPersonInfo() {
		for (int i = 0; i < personinfoJson.size(); i++) {
			tv_personaname.setText(personinfoJson.get(i).getPersonName());
			name=personinfoJson.get(i).getPersonName();
			tv_personsex.setText(personinfoJson.get(i).getPersonSex());
			tv_personborn.setText(personinfoJson.get(i).getPersonBorn());
			tv_personnational.setText(personinfoJson.get(i).getPersonNational().trim());
			tv_personaddress.setText(personinfoJson.get(i).getCenter()
					.getQaddress());
			tv_personNative.setText(personinfoJson.get(i)
					.getPersonNativePlace());
			tv_cardnum.setText(personinfoJson.get(i).getPersonCardId());
			sfz = personinfoJson.get(i).getPersonCardId();
			tv_education.setText(personinfoJson.get(i).getPersonEducation());
			tv_tye.setText(personinfoJson.get(i).getPersonType());

			tv_phone.setText(personinfoJson.get(i).getPersonMobilePhone());
			tv_status.setText(personinfoJson.get(i).getPersonCurrentStatus());
			tv_intention.setText(personinfoJson.get(i).getPersonIntention());
			tv_personstreet.setText(personinfoJson.get(i).getCenter()
					.getQstreet());
			tv_juweihui.setText(personinfoJson.get(i).getCenter()
					.getQjuweihui());
			tv_hukoudizhi.setText(personinfoJson.get(i).getCenter()
					.getQhujidizhi());

			String updateTime = personinfoJson.get(i).getUpdateTime();
			tv_updateTime.setText(updateTime.substring(0, 16));
			
			String comaredResult="";
			if(personinfoJson.get(i).getCompare_result()!=null && personinfoJson.get(i).getCompare_result().trim().equals("缺失")){
				comaredResult="已过期";
			}
			txtComareResult.setText(comaredResult);
			
			
			String beizhu = personinfoJson.get(i).getPersonBeizhu();
			Log.i("beizhu./.", beizhu+"11111111111");
			if (beizhu.equals("null")) {
				tv_personbeizhu.setText("");
			} else {
				tv_personbeizhu.setText(beizhu);
			}
		}
	}

	/**
	 * 设置监听
	 */
	private void setListener() {
		// btn_modify.setOnClickListener(this);
		tv_personMark.setOnClickListener(this);
		btnAction.setOnClickListener(new MyOnClickListener());
	}

	/**
	 * 初始化控件
	 */
	private void initView() {

		tv_personMark = (TextView) findViewById(R.id.tv_mark);
		tv_personaname = (EditText) findViewById(R.id.addpersonname);
		tv_personsex = (EditText) findViewById(R.id.addpersonsex);
		tv_personborn = (EditText) findViewById(R.id.addpersonborn);
		tv_personnational = (EditText) findViewById(R.id.addpersonnational);
		tv_tye = (EditText) findViewById(R.id.addpersontype);
		tv_personNative = (EditText) findViewById(R.id.addpersonnative);
		tv_education = (EditText) findViewById(R.id.addpersonedution);
		tv_personaddress = (EditText) findViewById(R.id.addpersonaddress);
		tv_phone = (EditText) findViewById(R.id.addpersonphonenum);
		tv_intention = (EditText) findViewById(R.id.addpersonintention);
		tv_cardnum = (EditText) findViewById(R.id.addpersoncardnum);
		tv_personstreet = (EditText) findViewById(R.id.addpersonstreet);
		tv_juweihui = (EditText) findViewById(R.id.addpersonjuweihui);
		tv_status = (EditText) findViewById(R.id.addpersonstatus);
		tv_personbeizhu = (TextView) findViewById(R.id.tv_beizhu);
		tv_hukoudizhi = (EditText) findViewById(R.id.addpersonregisteraddress);
		img_student = (ImageView) findViewById(R.id.img_student);
		img_jiedu = (ImageView) findViewById(R.id.img_jiedurenyuan);
		img_jiuyekunnan = (ImageView) findViewById(R.id.img_jiuyekunnan);
		img_lingjiuye = (ImageView) findViewById(R.id.imglingjiuye);
		img_qihang = (ImageView) findViewById(R.id.img_qihangrenyuan);
		img_xingjie = (ImageView) findViewById(R.id.imgxingjie);
		tv_updateTime = (TextView) findViewById(R.id.tv_updateTime);
		
		btnAction = (Button) findViewById(R.id.btnAction);
		
		txtComareResult = (TextView) findViewById(R.id.txtComareResult);
		
		horizontalScrollView=(HorizontalScrollView) this.findViewById(R.id.my_horizon);
	}

	/**
	 * 处理点击事件
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_mark:
			// 人员标识显示与隐藏
			if (personmark != null) {
				if (markShow == true) {
					dismissPersonMark(personmark);
					markShow = false;
				} else {
					showPersonMark(personmark);
					markShow = true;
				}
			}
			break;
		}

	}

	/**
	 * 人员标识线程，获取人员标识
	 */
	Runnable thread_mark = new Runnable() {

		@Override
		public void run() {
			try {
				// 判断是否是身份证查询被点击
				if (SFZCheck == true) {
					personmark = HttpUrls_.getPersonMarkJson(
							AddPersonInfoActivity.this, personSFZ);
				} else {
					personmark = HttpUrls_.getPersonMarkJson(
							AddPersonInfoActivity.this, query_sfz);
				}
				Message msg_mark = new Message();
				msg_mark.what = 0x60;
				msg_mark.obj = personmark;
				handler_baseinfo.obtainMessage(0x60, personmark);
				handler_baseinfo.sendMessage(msg_mark);
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	};
	HashMap<String, ImageView> HM_mark;

	/**
	 * 人员标识显示window
	 * 
	 * @param v
	 */
	private void showPopupWindow(View parent, ArrayList<PersonMark> personmarks) {
		if (popupWindow == null) {
			LayoutInflater mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
			markview = mLayoutInflater.inflate(R.layout.personmark, null);
			tv_markname = (TextView) markview.findViewById(R.id.markname);
			tv_marksource = (TextView) markview.findViewById(R.id.marksource);
			tv_marktime = (TextView) markview.findViewById(R.id.marktime);
			popupWindow = new PopupWindow(markview, LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
		}
		PersonMark mark = Map_MarkName.get((ImageView) parent);// Map_MarkName.get(parent);
		tv_markname.setText(mark.getPersonMarkName());
		tv_marksource.setText(mark.getPersonMarkSoure());
		tv_marktime.setText(mark.getPersonMarkCreatdate());
		// 使其聚集
		popupWindow.setFocusable(true);
		// 设置允许在外点击消失
		popupWindow.setOutsideTouchable(true);

		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		popupWindow.setWindowLayoutMode(LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		popupWindow.showAsDropDown(parent, -250, 10);
	}

	// 提示框消失处理
	public void DismissDialog() {
		if (AddPersonInfoActivity.this != null
				&& !AddPersonInfoActivity.this.isFinishing()
				&& progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	Map<ImageView, PersonMark> Map_MarkName = null;

	private void showPersonMark(final ArrayList<PersonMark> personmarks) {
		// 把图片与相应的文字以键值对的方式放入Map中 高效
		HM_mark = new HashMap<String, ImageView>();
		HM_mark.put("应届毕业生", img_student);
		HM_mark.put("启航人员", img_qihang);
		HM_mark.put("就业困难人员", img_jiuyekunnan);
		HM_mark.put("零就业家庭", img_lingjiuye);
		HM_mark.put("戒毒人员", img_jiedu);
		HM_mark.put("刑解人员", img_xingjie);

		Map_MarkName = new HashMap<ImageView, PersonMark>();
		for (int j = 0; j < personmarks.size(); j++) {

			// 遍历控件，设置为不显示
			ImageView markimg = HM_mark.get(personmarks.get(j)
					.getPersonMarkName());
			markimg.setVisibility(View.VISIBLE);
			// 放入控件键、实体类
			Map_MarkName.put(markimg, personmarks.get(j));

			markimg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showPopupWindow(v, personmarks);
				}
			});
		}
	}

	private void dismissPersonMark(ArrayList<PersonMark> personmarks) {
		// 把图片与相应的文字以键值对的方式放入Map中 高效
		HM_mark = new HashMap<String, ImageView>();
		HM_mark.put("应届毕业生", img_student);
		HM_mark.put("启航人员", img_qihang);
		HM_mark.put("就业困难人员", img_jiuyekunnan);
		HM_mark.put("零就业家庭", img_lingjiuye);
		HM_mark.put("戒毒人员", img_jiedu);
		HM_mark.put("刑解人员", img_xingjie);

		for (int j = 0; j < personmarks.size(); j++) {

			HM_mark.get(personmarks.get(j).getPersonMarkName()).setVisibility(
					View.GONE);
		}
	}

	public class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(
					"android.intent.action.MY_MianBROADCAST")) {
				Bundle bundleperson = intent.getExtras();
				SFZCheck = bundleperson.getBoolean("sfzCheck");
				personSFZ = bundleperson.getString("sfz");
				query_sfz = bundleperson.getString("querySfz");
				personjson = bundleperson.getString("personjson");
				if(personjson==null){
					Toast.makeText(AddPersonInfoActivity.this, "获取信息失败",Toast.LENGTH_SHORT).show();
				}else{
					 personinfoJson = ParsePersonJson(personjson);
					initPersonInfo();
				}
				new Thread(thread_mark).start();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 解除广播
		unregisterReceiver(myReceiver);
		PersonService.allActivity.remove(this);
	}
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
				String personBeizhu =object.getString("Remark");
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
				center.setQaddress(nowroad+nownong+nownumber+nowroom);
				center.setQcardnum(cardnum);
				center.setQstreet(personstreet);
				center.setQjuweihui(personjuwei);
				center.setQhujidizhi(road+nong+number+room);
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
				String [] times = time.split("T");					
				personbaseInfo.setUpdateTime(times[0]+" "+times[1].substring(0, times[1].indexOf('.')>0?times[1].indexOf('.'):times[1].length()));
				
				personbaseInfo.setCompare_result(jsonCenter.getString("COMPARE_RESULT"));
				Log.i("######$$$$$$$$$",
						personbaseInfo.toString());
				personinfoJson.add(personbaseInfo);
			}
		}catch (JSONException e) {
			e.printStackTrace();
		}
		return personinfoJson;
	}
	

	@Override
	public void init() {
		PersonService.addActivity(this);
		MenuService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch(Integer.valueOf(params[0].toString().trim())){
		case AddPersonInfoActivity.ADD_ACTION:
				if(params[1]!=null){
					String value = params[1].toString().trim();
					if(value.trim().equalsIgnoreCase("true")){
						Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
					}else {
						Toast.makeText(this, "已经添加过", Toast.LENGTH_SHORT).show();
					}
				}
			break;
			
		case RESULT_OK_INFO:
			if (!"".equals(params[1].toString().trim())&&params[1].toString().trim()!=null) {
				List<PersonInfo> newList=parseToJson(params[1].toString().trim());
				if (newList.size()>0&&newList!=null) {
					RadioGroup group=(RadioGroup) LayoutInflater.from(AddPersonInfoActivity.this).inflate(R.layout.radio_group, null);
					horizontalScrollView.addView(group);
					for (PersonInfo typeInfo : newList) {
						RadioButton button=(RadioButton) LayoutInflater.from(AddPersonInfoActivity.this).inflate(R.layout.radio_items, null);
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
		}
	}
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btnAction:
				Map<String, Object> data = new HashMap<String, Object>();
				Map<String, String> params = new HashMap<String, String>();
				params.put("sfz", sfz);
				params.put("type", "0");
				params.put("name", name);
				data.put("data", params);
				PersonTask task = new PersonTask(PersonTask.ADDPERSONINFOACTIVITY_UPDATE_ACTION, data);
				PersonService.newTask(task);
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
            return getParent().onKeyDown(keyCode, event);
        }else{
            return super.onKeyDown(keyCode, event);
        }
	}
}

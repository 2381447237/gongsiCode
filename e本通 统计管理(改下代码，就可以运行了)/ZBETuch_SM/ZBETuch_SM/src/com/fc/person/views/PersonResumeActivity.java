package com.fc.person.views;

import java.util.ArrayList;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fc.invite.views.InviteJobListActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.MenuService;
import com.fc.main.tools.HttpUrls_;
import com.fc.person.beans.PersonResume;
import com.fc.zbetuch_sm.R;

/**
 * 个人简历显示
 * @author Administrator
 *
 */
public class PersonResumeActivity extends Activity implements IActivity{
	private TextView tv_language1,tv_language2,tv_computerskills,tv_computerzhengshhu,
				tv_chendu1,tv_chendu2,tv_languagezhengshu,tv_otherzhengshu,
				tv_selfpingjia,tv_gzxz,tv_xinzi,tv_gzbs,tv_address1,tv_address2,tv_address3,
				tv_fugangwei1,tv_zigangwei1,tv_fugangwei2,tv_zigangwei2,tv_othergangwei,tv_workyear;
	private Button btnSearch;
	private MyReceiver myReceiver = new MyReceiver();
	private ArrayList<PersonResume> personResumeJson;
	@SuppressWarnings("unused")
	private String computerSkills, computerzhengshu, language1, chendu1,
			language2, chendu2, languagezhengshu, otherzhengshu, ziwopingjia,
			xinzi, workxingz, workshijian, workdidian1, workdidian2,
			workdidian3, fugangwei1, fugangwei2, zigangwei1, zigangwei2,
			othergangwei, checktuijian;
	private  ArrayList<PersonResume> list_resume;
	public String personSFZ;
	private String[] xinziStrings={};
	private String sfz="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resume_a);
		init();
		Intent intent = getIntent();
		sfz = intent.getStringExtra("sfz");
		initviews();
		initListener();
		
		// 广播接收器的注册
		IntentFilter filter = new IntentFilter();
		filter.addAction("android.intent.action.MY_MianBROADCAST");
		registerReceiver(myReceiver, filter);
		
	}

	private void initviews() {
		tv_address1 = (TextView)findViewById(R.id.tv_address1);
		tv_address2 = (TextView)findViewById(R.id.tv_address2);
		tv_address3 = (TextView)findViewById(R.id.tv_address3);
		tv_chendu1 = (TextView)findViewById(R.id.tv_chendu1);
		tv_chendu2 = (TextView)findViewById(R.id.tv_chendu2);
		tv_computerskills = (TextView)findViewById(R.id.tv_computerskills);
		tv_computerzhengshhu = (TextView)findViewById(R.id.tv_commputerzhengshu);
		tv_fugangwei1 = (TextView)findViewById(R.id.tv_fugangwei1);
		tv_fugangwei2 = (TextView)findViewById(R.id.tv_fugangwei2);
		tv_zigangwei1 = (TextView)findViewById(R.id.tv_zigangwei1);
		tv_zigangwei2 = (TextView)findViewById(R.id.tv_zigangwei2);
		tv_gzbs = (TextView)findViewById(R.id.tv_gzbs);
		tv_gzxz = (TextView)findViewById(R.id.tv_gzxz);
		tv_language1 = (TextView)findViewById(R.id.tv_language1);
		tv_language2 = (TextView)findViewById(R.id.tv_language2);
		tv_languagezhengshu = (TextView)findViewById(R.id.tv_languagezhengshu);
		tv_otherzhengshu = (TextView)findViewById(R.id.tv_otherzhengshu);
		tv_othergangwei = (TextView)findViewById(R.id.tv_othergangwei);
		tv_selfpingjia = (TextView)findViewById(R.id.tv_selfpingjia);
		tv_xinzi =(TextView)findViewById(R.id.tv_xinzi);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		tv_workyear = (TextView)findViewById(R.id.tv_gznx);
	}
	
	private void initListener(){
		btnSearch.setOnClickListener(new MyOnClickListener());
	}

	public Handler handler_resume = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x1110:
				Log.i("resume.json/....", personResumeJson.toString());
				if (personResumeJson.size()==0) {
					//Toast.makeText(PersonResumeActivity.this, "没有个人简历信息。。。", Toast.LENGTH_SHORT).show();
				}else{
					initinfo();
				}
				break;

			default:
				break;
			}
		}
		
	};
	Runnable thread_Resume = new Runnable() {
		
		@Override
		public void run() {
		 personResumeJson = HttpUrls_.getPersonResumeJson(PersonResumeActivity.this, personSFZ);
			Message msg_ressume = new Message();
			msg_ressume.what = 0x1110;
			msg_ressume.obj = personResumeJson;
			handler_resume.obtainMessage(0x1110, personResumeJson);
			handler_resume.sendMessage(msg_ressume);
		}
	};
	public class MyReceiver extends BroadcastReceiver {



		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(
					"android.intent.action.MY_MianBROADCAST")) {
				Bundle bundleperson = intent.getExtras();
				personSFZ = bundleperson.getString("mysfz");
				
				new Thread(thread_Resume).start();
			}

		}

	}
	protected void initinfo() {
		for (int i = 0; i < personResumeJson.size(); i++) {
			String personWorkaddress1 = personResumeJson.get(i).getPersonWorkaddress1();
			if(personWorkaddress1.equals("null")){
				tv_address1.setText("无");
			}else{
				tv_address1.setText(personWorkaddress1);
			}
			String personWorkaddress2 = personResumeJson.get(i).getPersonWorkaddress2();
			if(personWorkaddress2.equals("null")){
				tv_address2.setText("无");
			}else{
				tv_address2.setText(personWorkaddress2);
			}
			String personWorkaddress3 = personResumeJson.get(i).getPersonWorkaddress3();
			if(personWorkaddress3.equals("null")){
				tv_address3.setText("无");
			}else{
				tv_address3.setText(personWorkaddress3);
			}
			String personShunian1 = personResumeJson.get(i).getPersonShunian1();
			if(personShunian1.equals("null")){
				tv_chendu1.setText("无");
			}else{
				tv_chendu1.setText(personShunian1);
			}
			String personShunian2 = personResumeJson.get(i).getPersonShunian2();
			if(personShunian2.equals("null")){
				tv_chendu2.setText("无");
			}else{
				tv_chendu2.setText(personShunian2);
			}
			String personComputerSkills = personResumeJson.get(i).getPersonComputerSkills();
			if(personComputerSkills.equals("null")){
				tv_computerskills.setText("无");
			}else{
				tv_computerskills.setText(personComputerSkills);
			}
			
			tv_computerzhengshhu.setText(personResumeJson.get(i).getPersonComputerZhengshu());
			String personFuGangwei1 = personResumeJson.get(i).getPersonFuGangwei1();
			if(personFuGangwei1.equals("null")){
				tv_fugangwei1.setText("无");
			}else{
				tv_fugangwei1.setText(personFuGangwei1);
			}
			String personFuGangwei2 = personResumeJson.get(i).getPersonFuGangwei2();
			if(personFuGangwei2.equals("null")){
				tv_fugangwei2.setText("无");
			}else{
				tv_fugangwei2.setText(personFuGangwei2);
			}
			String personWorktime = personResumeJson.get(i).getPersonWorktime();
			if(personWorktime.equals("null")){
				tv_gzbs.setText("无");
			}else{
				tv_gzbs.setText(personWorktime);
			}
			String personNatureEmployment = personResumeJson.get(i).getPersonNatureEmployment();
			if(personNatureEmployment.equals("null")){
				tv_gzxz.setText("无");
			}else{
				tv_gzxz.setText(personNatureEmployment);
			}
			String personForeignLanguages1 = personResumeJson.get(i).getPersonForeignLanguages1();
			if(personForeignLanguages1.equals("null")){
				tv_language1.setText("无");
			}else{
				tv_language1.setText(personForeignLanguages1);
			}
			String personForeignLanguages2 = personResumeJson.get(i).getPersonForeignLanguages2();
			if(personForeignLanguages2.equals("null")){
				tv_language2.setText("无");
			}else{
				tv_language2.setText(personForeignLanguages1);
			}
			tv_languagezhengshu.setText(personResumeJson.get(i).getPersonForeignZhengshu());
			tv_othergangwei.setText(personResumeJson.get(i).getPersonGanwei());
			tv_selfpingjia.setText(personResumeJson.get(i).getPersonAssessment());
			String personExpectedSalarylow = personResumeJson.get(i).getPersonExpectedSalarylow().trim().replaceAll(" ", "");
			String sublow = personExpectedSalarylow.substring(0, personExpectedSalarylow.indexOf("."));
			String personExpectedSalaryup = personResumeJson.get(i).getPersonExpectedSalaryUp().trim().replaceAll(" ", "");
			String subup = personExpectedSalaryup.substring(0, personExpectedSalaryup.indexOf("."));
			if(subup.equals("-1")){
				tv_xinzi.setText(sublow+"以上");
			}else{
				tv_xinzi.setText(sublow+"一"+subup);
			}
			
			tv_otherzhengshu.setText(personResumeJson.get(i).getPersonQitazhengshu());
			String personZigangwei1 = personResumeJson.get(i).getPersonZigangwei1();
			if(personZigangwei1.equals("null")){
				tv_zigangwei1.setText("无");
			}else{
				tv_zigangwei1.setText(personZigangwei1);
			}
			String personZigangwei2 = personResumeJson.get(i).getPersonZigangwei2();
			if(personZigangwei2.equals("null")){
				tv_zigangwei2.setText("无");
			}else{
				tv_zigangwei2.setText(personZigangwei2);
			}
			String personworkyears = personResumeJson.get(i).getPersonworkyears();
			if(personworkyears.equals("-1.0")){
				tv_workyear.setText("无");
			}else{
				tv_workyear.setText(personworkyears);
			}
		}
		
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	
		unregisterReceiver(myReceiver);
	}
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.btnSearch:
				Intent intent = new Intent(PersonResumeActivity.this,InviteJobListActivity.class);
				intent.putExtra("flag", InviteJobListActivity.GET_ITEM_BY_SFZ);
				intent.putExtra("sfz", sfz);
				startActivity(intent);
				break;
			}
			 
		}
		
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		MenuService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		
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

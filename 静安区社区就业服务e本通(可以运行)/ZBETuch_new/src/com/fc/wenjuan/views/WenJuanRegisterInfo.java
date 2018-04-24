package com.fc.wenjuan.views;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import okhttp3.Call;

import com.fc.first.views.FirstPageActivity;
import com.fc.main.beans.IActivity;
import com.fc.main.service.PersonService;
import com.fc.main.tools.HttpUrls_;
import com.fc.wenjuan.beans.FamilyInfo;
import com.fc.wenjuan.beans.WenJuanPersonInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.zbetuch_news.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WenJuanRegisterInfo extends Activity implements OnClickListener{

	private Button backBtn,nextBtn;
	
	private WenJuanPersonInfo info;
	private int position;
	private LinearLayout sixteenLl;
	private EditText nameEt,areaEt,streetEt,villageEt,addressEt,personEt,manEt,womanEt,sixteenEt,contactsEt,interviewerEt,phoneEt;
	private String nameStr,areaStr,streetStr,villageStr,addressStr,personStr,manStr,womanStr,sixteenStr,contactsStr,interviewerStr,phoneStr;
	private TextView addressTv,contactsTv,interviewerTv,phoneTv;
	public static String registerInfoUrl="/Json/Set_Home.aspx";
	private String familyListUrl="/Json/Get_Home.aspx";
	private int myHOMEID;
	private IActivity activity = null;
	private List<FamilyInfo> listInfo=new ArrayList<FamilyInfo>();
	private FamilyInfo fInfo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wenjuan_register_info);
		
		info = (WenJuanPersonInfo) getIntent().getSerializableExtra("info");
		fInfo=(FamilyInfo) getIntent().getSerializableExtra("familyList");
		
		//getIntent().getBooleanExtra("ISJYSTATUS", false);
		position = getIntent().getIntExtra("position", 0);
		backBtn=(Button) findViewById(R.id.awri_btn_back);
		backBtn.setOnClickListener(this);
		nextBtn=(Button) findViewById(R.id.awri_btn_next);
		nextBtn.setOnClickListener(this);
		
		nameEt=(EditText) findViewById(R.id.awri_et_name);//姓名
		
		areaEt=(EditText) findViewById(R.id.awri_et_area);//区
		
		streetEt=(EditText) findViewById(R.id.awri_et_street);//街道
		
		villageEt=(EditText) findViewById(R.id.awri_et_village);//居委会
		addressTv=(TextView) findViewById(R.id.awri_tv_address);
		addressEt=(EditText) findViewById(R.id.awri_et_address);//具体地址
		
		personEt=(EditText) findViewById(R.id.awri_et_person);//实际人数
	    personEt.requestFocus();
		manEt=(EditText) findViewById(R.id.awri_et_man);//男
		
		womanEt=(EditText) findViewById(R.id.awri_et_woman);//女
		
		sixteenEt=(EditText) findViewById(R.id.awri_et_16);//16周岁以上
		sixteenLl=(LinearLayout) findViewById(R.id.awri_ll_sixteen);
		contactsEt=(EditText) findViewById(R.id.awri_et_contacts);//访员签名（调查员）
		contactsEt.setText(FirstPageActivity.DiaoChaYuan);
		contactsTv=(TextView) findViewById(R.id.awri_tv_contacts);
		interviewerEt=(EditText) findViewById(R.id.awri_et_interviewer);//联系人（申请人）
		interviewerTv=(TextView) findViewById(R.id.awri_tv_interviewer);
		phoneEt=(EditText) findViewById(R.id.awri_et_phone);//联系电话
		phoneTv=(TextView) findViewById(R.id.awri_tv_phone);
		if(getIntent().getIntExtra("QUESTIONMASTERID", 0)==5){
			addressTv.setText("本户地址:");
			contactsTv.setText("调查员:");
			interviewerTv.setText("申请人:");
			phoneTv.setText("本户电话:");
			sixteenLl.setVisibility(View.GONE);
		}else if(getIntent().getIntExtra("QUESTIONMASTERID", 0)==6){
			addressTv.setText("访问地址:");
			contactsTv.setText("访员签名:");
			interviewerTv.setText("联系人:");
			phoneTv.setText("联系电话:");
			sixteenLl.setVisibility(View.VISIBLE);
		}
		
		if(fInfo!=null){
			nameEt.setText(fInfo.getNAME());
			areaEt.setText(fInfo.getXSQ());
			villageEt.setText(fInfo.getSQJWC());
			streetEt.setText(fInfo.getXZJD());
			addressEt.setText(fInfo.getADRESS());
			phoneEt.setText(fInfo.getLXDF());
			
			if(TextUtils.isEmpty(fInfo.getSQR())){			
				interviewerEt.requestFocus();
			}else{
				
				if(TextUtils.equals(fInfo.getSQR(),"null")){
					interviewerEt.setText("");
				}else{
					interviewerEt.setText(fInfo.getSQR());
					
				}
				
			}	
			
			if(TextUtils.isEmpty(fInfo.getLXR())){
				contactsEt.requestFocus();
			}else{
				
				if(TextUtils.equals(fInfo.getLXR(),"null")){
					contactsEt.setText("");
				}else{
				contactsEt.setText(fInfo.getLXR());
			}			
			}
			
			if(fInfo.getZS()==0&&fInfo.getNV()==0&&fInfo.getNAN()==0&&fInfo.getJZNUM()==0){
				
				sixteenEt.setText("");		
				womanEt.setText("");		
				manEt.setText("");		
				personEt.setText("");
				personEt.requestFocus();
			}else{
			
//			if(fInfo.getZS()==0){
//				sixteenEt.setText("");
//				sixteenEt.requestFocus();
//			}else{
				sixteenEt.setText(fInfo.getZS()+"");		
//			}
//			if(fInfo.getNV()==0){
//				womanEt.setText("");
//				womanEt.requestFocus();
//			}else{
				womanEt.setText(fInfo.getNV()+"");		
		//	}
//			if(fInfo.getNAN()==0){
//				manEt.setText("");
//				manEt.requestFocus();
//			}else{
				manEt.setText(fInfo.getNAN()+"");		
		//	}
//			if(fInfo.getJZNUM()==0){
//				personEt.setText("");
//				personEt.requestFocus();
//			}else{
				personEt.setText(fInfo.getJZNUM()+"");	
				personEt.clearFocus();
			//}
			}
		}else{
			
			nameEt.setText(info.getNAME());
			interviewerEt.setText(info.getNAME());
			areaEt.setText(info.getQX());
			villageEt.setText(info.getJW());
			streetEt.setText(info.getJD());
			addressEt.setText(info.getLXDZ());
			phoneEt.setText(info.getPHONE());
			getHuzhuInfo();
		}
		
	}

	private void getHuzhuInfo() {
		// http://192.168.11.11:89/Json/Get_Home.aspx?TYPE=1&QA_MASTER=5&SFZ=310108197604155814
		OkHttpUtils.post().url(HttpUrls_.HttpURL+familyListUrl).addParams("TYPE","1").addParams("QA_MASTER", getIntent().getIntExtra("QUESTIONMASTERID", 0)+"")
		.addParams("SFZ",info.getSFZ()).build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String infoStr) {
				
				if(!TextUtils.equals(infoStr, "[]")){

					runOnUiThread(new  Runnable() {
						public void run() {
							
							Gson gson=new Gson();
							Type listType=new TypeToken<LinkedList<FamilyInfo>>(){}.getType();
							
							LinkedList<FamilyInfo> fi=gson.fromJson(infoStr,listType);
							
							listInfo.clear();
							
							for (Iterator iterator = fi.iterator(); iterator
									.hasNext();) {

								FamilyInfo content = (FamilyInfo) iterator
										.next();

								listInfo.add(content);
								
							}
							
							if(!TextUtils.isEmpty(personEt.getText().toString().trim())){
								contactsEt.requestFocus();
							}			
							
							
							if(listInfo.get(0).getZS()==0&&listInfo.get(0).getNV()==0&&listInfo.get(0).getNAN()==0&&listInfo.get(0).getJZNUM()==0){
								
								sixteenEt.setText("");
								womanEt.setText("");
								manEt.setText("");
								personEt.setText("");
								personEt.requestFocus();
							}else{
//							if(listInfo.get(0).getZS()==0){
//								sixteenEt.setText("");
//								sixteenEt.requestFocus();
//							}else{
								sixteenEt.setText(listInfo.get(0).getZS()+"");		
						//	}
//							if(listInfo.get(0).getNV()==0){
//								womanEt.setText("");
//								womanEt.requestFocus();
//							}else{
								womanEt.setText(listInfo.get(0).getNV()+"");		
						//	}
//							if(listInfo.get(0).getNAN()==0){
//								manEt.setText("");
//								manEt.requestFocus();
//							}else{
								manEt.setText(listInfo.get(0).getNAN()+"");		
						//	}
//							if(listInfo.get(0).getJZNUM()==0){
//								personEt.setText("");
//								personEt.requestFocus();
//							}else{
								personEt.setText(listInfo.get(0).getJZNUM()+"");
								personEt.clearFocus();
						//	}
							}
						}
					});
					
				
				}
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				Toast.makeText(WenJuanRegisterInfo.this,"请连接网络",0).show();
			}
		});
		
	}
	
	@Override
	public void onClick(View v) {
		nameStr=nameEt.getText().toString().trim();
		areaStr=areaEt.getText().toString().trim();
		streetStr=streetEt.getText().toString().trim();
		villageStr=villageEt.getText().toString().trim();
		addressStr=addressEt.getText().toString().trim();
		personStr=personEt.getText().toString().trim();
		manStr=manEt.getText().toString().trim();
		womanStr=womanEt.getText().toString().trim();
		sixteenStr=0+sixteenEt.getText().toString().trim();
		contactsStr=contactsEt.getText().toString().trim();
		interviewerStr=interviewerEt.getText().toString().trim();
		phoneStr=phoneEt.getText().toString().trim();
		switch (v.getId()) {
		case R.id.awri_btn_back:
			
			
			if(getIntent().getIntExtra("QUESTIONMASTERID", 0)==5){
				
				if(TextUtils.isEmpty(nameStr)||
				TextUtils.isEmpty(areaStr)||
				TextUtils.isEmpty(streetStr)||
				TextUtils.isEmpty(villageStr)||
				TextUtils.isEmpty(addressStr)||
				TextUtils.isEmpty(personStr)||
				TextUtils.isEmpty(manStr)||
				TextUtils.isEmpty(womanStr)||
				TextUtils.isEmpty(contactsStr)||
				TextUtils.isEmpty(interviewerStr)||
				TextUtils.isEmpty(phoneStr)){
		
			Toast.makeText(WenJuanRegisterInfo.this,"请将资料信息填写完整", 0).show();
		
			return;
		}
				
			} else if(getIntent().getIntExtra("QUESTIONMASTERID", 0)==6){
				
				if(TextUtils.isEmpty(nameStr)||
				TextUtils.isEmpty(areaStr)||
				TextUtils.isEmpty(streetStr)||
				TextUtils.isEmpty(villageStr)||
				TextUtils.isEmpty(addressStr)||
				TextUtils.isEmpty(personStr)||
				TextUtils.isEmpty(manStr)||
				TextUtils.isEmpty(womanStr)||
				TextUtils.isEmpty(sixteenStr)||
				TextUtils.isEmpty(contactsStr)||
				TextUtils.isEmpty(interviewerStr)||
				TextUtils.isEmpty(phoneStr)){
		
			Toast.makeText(WenJuanRegisterInfo.this,"请将资料信息填写完整", 0).show();
		
			return;
		}
				
			}
			
				registerInfo();
			
			setResult(10000);
			
			finish();
			
			break;

case R.id.awri_btn_next:
			
//	if(TextUtils.isEmpty(areaStr)||
//			TextUtils.isEmpty(streetStr)||
//			TextUtils.isEmpty(villageStr)||
//			TextUtils.isEmpty(addressStr)||
//			TextUtils.isEmpty(personStr)||
//			TextUtils.isEmpty(manStr)||
//			TextUtils.isEmpty(womanStr)||
//			TextUtils.isEmpty(sixteenStr)||
//			TextUtils.isEmpty(contactsStr)||
//			TextUtils.isEmpty(phoneStr)){
//	
//		Toast.makeText(WenJuanRegisterInfo.this,"请将资料信息填写完整", 0).show();
//	
//		return;
//	}
//	
//	registerInfo2();
	
	showDialog();
	
			break;	
			
		default:
			break;
		}
		
	}

private void showDialog(){
		
		Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("温馨提示").setMessage("您确定要取消答题吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).create().show();
		
	}
	
	private void registerInfo(){
		//ZS代表16周岁 int
		
		int Type;
		int Id;
		if(fInfo==null){
			
			Type=1;
			Id=info.getID();
		}else{
			Id=fInfo.getID();
			Type=3;
			
		}
		
		OkHttpUtils.post().url(HttpUrls_.HttpURL+registerInfoUrl).addParams("TYPE",Type+"").addParams("NAME",info.getNAME())
		.addParams("SFZ",info.getSFZ()).addParams("LXR",contactsStr).addParams("LXDF",phoneStr)
		.addParams("JZNUM",personStr+"").addParams("NAN",manStr+"").addParams("NV",womanStr+"").addParams("ZS",sixteenStr+"").addParams("ID",Id+"")
		.addParams("XSQ",areaStr).addParams("XZJD",streetStr).addParams("SQJWC",villageStr).addParams("SQR",interviewerStr)
		.addParams("ADRESS",addressStr).addParams("QUESTIONMASTERID",getIntent().getIntExtra("QUESTIONMASTERID", 0)+"")
		.addHeader("cookie", HttpUrls_.staffName)
		.build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String str) {
				
				
				Log.i("2017/3/3","===成功==="+str);
				addRefresh();
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				
				Log.i("2017/3/3","===失败==="+arg0+"===异常==="+arg1);
				Toast.makeText(WenJuanRegisterInfo.this,"请连接网络",0).show();
			}
		});
	}
	
private void registerInfo2(){
		
		OkHttpUtils.post().url(HttpUrls_.HttpURL+registerInfoUrl).addParams("TYPE",1+"").addParams("NAME",info.getNAME())
		.addParams("SFZ",info.getSFZ()).addParams("LXR",contactsStr).addParams("LXDF",phoneStr)
		.addParams("JZNUM",personStr+"").addParams("NAN",manStr+"").addParams("NV",womanStr+"").addParams("ZS",sixteenStr+"").addParams("ID",info.getID()+"")
		.addParams("XSQ",areaStr).addParams("XZJD",streetStr).addParams("SQJWC",villageStr).addParams("SQR",interviewerStr)
		.addParams("ADRESS",addressStr).addParams("QUESTIONMASTERID",getIntent().getIntExtra("QUESTIONMASTERID", 0)+"")
		.addHeader("cookie", HttpUrls_.staffName)
		.build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String str) {
				
				Log.i("2017/3/3","===成功==="+str);
				
				runOnUiThread(new  Runnable() {
					public void run() {
						
						Gson gson=new Gson();
						
						WenJuanPersonInfo wjpIf=gson.fromJson(str,WenJuanPersonInfo.class);
						myHOMEID=wjpIf.getID();
						
						Log.i("2017/3/7","=myHOMEID="+myHOMEID);
						
						//if (rb) {
						
						
						
						Intent showIntent = new Intent(WenJuanRegisterInfo.this,
								WenJuanDetailActivity.class);
						showIntent.putExtra("pid", info.getID());
						showIntent.putExtra("info", info);
						showIntent.putExtra("NO", info.getNO());
						showIntent.putExtra("sname", contactsStr);
						showIntent.putExtra("position", position);
						showIntent.putExtra("rb", true);
						showIntent.putExtra("QUESTIONMASTERID",getIntent().getIntExtra("QUESTIONMASTERID", 0));
						showIntent.putExtra("myHOMEID", myHOMEID);
						showIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
						
						Log.i("2017/3/7","=myHOMEID点击111="+myHOMEID);
						
						startActivity(showIntent);
						finish();
						addRefresh();
					//}
						
					}
				});
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				
				Log.i("2017/3/3","===失败==="+arg0+"===异常==="+arg1);
				Toast.makeText(WenJuanRegisterInfo.this,"请连接网络",0).show();
			}
		});
	}

private void addRefresh(){
	
	int myTypeId;
		
		if(WenJuanPersonActivity.isWeichaRg){
			myTypeId=1;
		}else{
			myTypeId=0;
		}
		
		OkHttpUtils.post().url(HttpUrls_.HttpURL+"/Json/Get_Qa_UpLoad_Personnel.aspx").addParams("type",""+myTypeId).addParams("page","0").addParams("rows","15").addParams("master_id",getIntent().getIntExtra("QUESTIONMASTERID", 1)+"").addHeader("cookie", HttpUrls_.staffName)
		.build().execute(new StringCallback() {
			
			@Override
			public void onResponse(String str) {
				WenJuanPersonActivity.myRefresh=true;
				activity = (WenJuanPersonActivity) PersonService
				.getActivityByName("WenJuanPersonActivity");
	
		if (activity != null) {
			

			
		activity.refresh(WenJuanPersonActivity.REFRESH_INFO,
				str);
			}
			}
			@Override
			public void onError(Call arg0, Exception arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(WenJuanRegisterInfo.this,"请连接网络",0).show();
			}
		
		});
		
	}

}



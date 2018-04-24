package com.example.ppu_infusion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

import com.example.Seats.beans.GetStartInfo;
import com.example.Seats.beans.ScanPersonInfo;
import com.example.Seats.views.ScanActivity;
import com.google.gson.Gson;
import com.main.tools.HttpUrls_;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PeiyaoDetailActivity extends Activity implements OnClickListener{
	
	private Button btn_back,btn_scan;
	private HorizontalScrollView hsv;
	private int STAFFID;
	private int myPatientId;
	private String myQrCode;
	private String perInfoUrl = "/json/GetQrCodeBase.aspx";
	private String myQrCodeUrl = "/json/GetQrCode.aspx";
	private String stateUrl="/json/usp_do_InfusionDispense.aspx";
	
	private TextView tv_seatno, tv_name, tv_cardno, tv_sex, tv_age, tv_birth,
	tv_orderdapename, tv_ordername, tv_diagindo;
	private LinearLayout ll_footer;
	
	private ScanPersonInfo pInfo;
	private ListView lv;
	private PeiYaoAdapter adapter;
	private List<GetStartInfo> qrCodeInfos = new ArrayList<GetStartInfo>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置屏幕全屏
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_peiyao_detail);
		
		Intent intent=getIntent();
		
		STAFFID=intent.getIntExtra("STAFFID",-1);
		
		tv_seatno=(TextView) findViewById(R.id.tv_seatno_peiyao);
		tv_name=(TextView) findViewById(R.id.tv_name_peiyao);
		tv_cardno=(TextView) findViewById(R.id.tv_cardno_peiyao);
		tv_sex=(TextView) findViewById(R.id.tv_sex_peiyao);
		tv_age=(TextView) findViewById(R.id.tv_age_peiyao);
		tv_birth=(TextView) findViewById(R.id.tv_birth_peiyao);
		tv_orderdapename=(TextView) findViewById(R.id.tv_orderdapename_peiyao);
		tv_ordername=(TextView) findViewById(R.id.tv_ordername_peiyao);
		tv_diagindo=(TextView) findViewById(R.id.tv_diagindo_peiyao);
		
		lv=(ListView) findViewById(R.id.list_peiyao);
		ll_footer=(LinearLayout) LayoutInflater.from(this).inflate(R.layout.footer_peiyao,null);
		lv.addFooterView(ll_footer);
		
		btn_back=(Button) findViewById(R.id.btn_back_peiyao);
		btn_back.setOnClickListener(this);
		btn_scan=(Button) findViewById(R.id.btn_scan_peiyao);
		btn_scan.setOnClickListener(this);
		hsv=(HorizontalScrollView) findViewById(R.id.hsv_peiyao);
		hsv.setOverScrollMode(HorizontalScrollView.OVER_SCROLL_NEVER);
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_back_peiyao:
			
			finish();
			
			break;
			
		case R.id.btn_scan_peiyao:
			
			Intent intent=new Intent(this,ScanActivity.class);
			intent.putExtra("Tag", "PeiyaoDetailActivity");
			intent.putExtra("STAFFID", STAFFID);
			startActivityForResult(intent, 9999);
			
			break;
			
		default:
			break;
		}
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode==9999&&resultCode==9999){
			myQrCode=data.getStringExtra("result");
			
			//Log.i("2017/6/21","myQrCode="+myQrCode);
			
			getPersonInfo();
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	
	// 个人信息 ：
			// http://192.168.0.83:8088/json/GetQrCodeBase.aspx?QRCODE=20170620000003
	private void getPersonInfo(){
		
		OkHttpUtils.post().url(HttpUrls_.HttpURL+perInfoUrl)
		.addParams("QRCODE",myQrCode).build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String str) {
				
				runOnUiThread(new Runnable() {
					public void run() {
						
						Gson gson=new Gson();
						
						pInfo=gson.fromJson(str,ScanPersonInfo.class);
						
						if(pInfo==null){
							
							showDialog();
							
							return;
						}
						tv_seatno.setText(pInfo.SEATNO);
						tv_name.setText(pInfo.PATIENTNAME);
						tv_cardno.setText(pInfo.CARDNO);
						if (pInfo.GENDER.endsWith("1")) {
							tv_sex.setText("男");
						} else {
							tv_sex.setText("女");
						}
						Calendar c = Calendar.getInstance();
						int year = c.get(Calendar.YEAR);
						int age = Integer.parseInt(pInfo.BIRTHDATE.substring(0, 4));
						int age2 = year - age;
						tv_age.setText(age2 + "");
						tv_birth.setText(pInfo.BIRTHDATE.substring(0,10));
						tv_orderdapename.setText(pInfo.ORDERDEPTNAME);
						tv_ordername.setText(pInfo.ORDERDOCTORNAME);
						tv_diagindo.setText(pInfo.DIAGINFO);
						myPatientId = pInfo.PATIENTID;
						
						getDrugsList();
					}
				});
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				
				Toast.makeText(PeiyaoDetailActivity.this, "请检查网络",Toast.LENGTH_SHORT).show();
			
			}
		});
		
	}
	
	// 对应的url:http://192.168.0.83:8088/json/GetQrCode.aspx?QRCODE=20170620000007
	private void getDrugsList(){
		
		OkHttpUtils.post().url(HttpUrls_.HttpURL + myQrCodeUrl)
		.addParams("QRCODE", myQrCode).build()
		.execute(new StringCallback() {

			@Override
			public void onResponse(final String str) {
				runOnUiThread(new Runnable() {
					public void run() {

						qrCodeInfos.clear();
						//infusion = str;

						try {
							JSONArray jsonArray = new JSONArray(str);

							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject object = jsonArray.getJSONObject(i);
								GetStartInfo info = new GetStartInfo();
								// info.setINFUSIONDETAILID(object.getString("INFUSIONDETAILID"));
								info.setINFUSIONID(object.getString("INFUSIONID"));
								info.setDRUGNAME(object.getString("DRUGNAME"));
								info.setDRUGSPEC(object.getString("DRUGSPEC"));
								info.setDRUGTRADENAME(object.getString("DRUGTRADENAME"));
								info.setDRUGMANUFACTURER(object.getString("DRUGMANUFACTURER"));
								info.setDRUGYBCODE(object.getString("DRUGYBCODE"));
								info.setINFUSIONGROUPNO(object.getString("INFUSIONGROUPNO"));
								info.setBAGVOLUME(object.getString("BAGVOLUME"));
								info.setREMAINDERVOLUME(object.getString("REMAINDERVOLUME"));
								info.setDRIPCNTSPERMINUTE(object.getString("DRIPCNTSPERMINUTE"));
								info.setTUBEID(object.getString("TUBEID"));
								info.setDRIPCNTSPERML(object.getString("DRIPCNTSPERML"));
								info.setDRIPSTARTTIME(object.getString("DRIPSTARTTIME"));
								info.setDRIPLASTSTARTTIME(object.getString("DRIPLASTSTARTTIME"));
								info.setDRIPFINISHTIME(object.getString("DRIPFINISHTIME"));
								info.setINFUSIONSTATUS(object.getString("INFUSIONSTATUS"));
								info.setDURATION(object.getString("DURATION"));
								info.setDOSAGE(object.getString("DOSAGE"));
								info.setDOSAGEUNIT(object.getString("DOSAGEUNIT"));
								info.setInfusionStatusName(object.getString("InfusionStatusName"));

								qrCodeInfos.add(info);

							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

						
						adapter = null;
						adapter = new PeiYaoAdapter(
								PeiyaoDetailActivity.this, qrCodeInfos);
						lv.setAdapter(adapter);
					
						getPeiYaoState();
					}
				});

			}

			@Override
			public void onError(Call arg0, Exception arg1) {
				Toast.makeText(PeiyaoDetailActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
			}});
		
	}
	
	
	//http://192.168.0.83:8088/json/usp_do_InfusionDispense.aspx?STAFFID=1&QRCODE=20170607000028
	private void getPeiYaoState(){
		
		OkHttpUtils.post().url(HttpUrls_.HttpURL+stateUrl)
		.addParams("STAFFID", STAFFID+"").addParams("QRCODE", myQrCode)
		.build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String str) {
				
				runOnUiThread(new Runnable() {
					public void run() {
						
						if(TextUtils.equals(str,"True")){
							ll_footer.setVisibility(View.VISIBLE);
						}else{
							ll_footer.setVisibility(View.GONE);
						}
						
					}
				});
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				Toast.makeText(PeiyaoDetailActivity.this, "请检查网络",Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	private void showDialog(){
		
		AlertDialog.Builder builder=new Builder(PeiyaoDetailActivity.this);
		builder.setTitle("提示").setMessage("信息不匹配！")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setCancelable(true).setPositiveButton("确定", null).show();
		
	}
	
}

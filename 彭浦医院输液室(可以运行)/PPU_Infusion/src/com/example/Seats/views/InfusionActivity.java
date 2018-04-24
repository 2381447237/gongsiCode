package com.example.Seats.views;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.Seats.beans.GetStartInfo;
import com.example.Seats.beans.InfusionAdapter;
import com.example.Seats.beans.InfusionInfo;
import com.example.Seats.beans.ScanPersonInfo;
import com.example.Seats.beans.SeatsInfo;
import com.example.Seats.beans.TubesInfo;
import com.example.companytask.CompanyTask;
import com.example.helper.IActivity;
import com.example.ppu_infusion.LoginActivity;
import com.example.ppu_infusion.R;
import com.example.services.MainService;
import com.google.gson.Gson;
import com.main.tools.HttpUrls_;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class InfusionActivity extends Activity implements IActivity,
		OnClickListener {
	public static final int INFUSIONORDERSINFO = 1;
	public static final int INFUSIONRESTART = 2;
	public static final int INFUSIONPAUSE = 3;
	public static final int INFUSIONFINISH = 4;
	public static final int DRIPSPEED = 5;
	public static final int TUBES = 6;
	public static final int GETSTART = 7;
	private int STAFFID;
	private List<InfusionInfo> infusionInfos = new ArrayList<InfusionInfo>();
	public SeatsInfo infos;
	private TextView tv_seatno, tv_name, tv_cardno, tv_sex, tv_age, tv_birth,
			tv_orderdapename, tv_ordername, tv_diagindo;
	private Button btn_start, btn_restart, btn_pause, btn_finish, btn_back;
	private String tubes, speed,infusion;
	private InfusionAdapter adapter;
	private ListView list_drug;
	private List<GetStartInfo> getStartInfos = new ArrayList<GetStartInfo>();
	private ArrayList<TubesInfo> tubesInfos = new ArrayList<TubesInfo>();
	private ProgressDialog dialog;
	private Context context = this;
	private String myQrCode;
	private String myQrCodeUrl = "/json/GetQrCode.aspx";
	private String perInfoUrl = "/json/GetQrCodeBase.aspx";
	private String seatUrl="/json/vw_SeatsInfo.aspx";
	private List<GetStartInfo> qrCodeInfos = new ArrayList<GetStartInfo>();
	public boolean isScan;
	private int myPatientId;
	private ScanPersonInfo pInfo;
    private HorizontalScrollView hsv;
	
	
	private String  refreshSTATUS,refreshTUBEID,refreshDRIPCNTSPERMINUTE;//点击按钮刷新之后的参数
	private int refreshIGNO;//点击按钮刷新之后的参数
	
	private Handler handler=new Handler(){

       public void handleMessage(android.os.Message msg) {
			
			myClick((String)msg.obj);
			
		};
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置屏幕无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置屏幕全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_infusion);
		Intent intent = getIntent();
		STAFFID = intent.getIntExtra("STAFFID", -1);
		infos = (SeatsInfo) intent.getSerializableExtra("SeatsInfo");
		myQrCode = intent.getStringExtra("result");

		//Log.i("2017/6/20","myQrCode="+myQrCode);//20170607000028
		
		init();
		initview();
		showDialog();
		postOrdersInfo();
		postTubs();
		postDripseed();
		postgetstart();
	}
	
	private void myClick(String sss){
		
		if(TextUtils.equals(sss,"start")){
		
			if (refreshSTATUS.equals("1")|| refreshSTATUS.equals("2")) {
				AlertDialog.Builder builder = new Builder(InfusionActivity.this);
				builder.setTitle("提示").setMessage("有药品正在输液或暂停输液")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setCancelable(true).setPositiveButton("确定", null).show();
			}else if (refreshSTATUS.equals("3")) {
					AlertDialog.Builder builder = new Builder(InfusionActivity.this);
					builder.setTitle("提示").setMessage("该药品已完成输液")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setCancelable(true)
							.setPositiveButton("确定", null).show(); 
			}else {
				Intent intent = new Intent();
				intent.putExtra("STAFFID", STAFFID);
				intent.putExtra("infos", infos);
				intent.putExtra("tubes", tubes);
				intent.putExtra("speed", speed);
				intent.putExtra("infusion", infusion);
				intent.setClass(InfusionActivity.this, TubleActivity.class);
				startActivityForResult(intent, 100);
			}
		
	}else if(TextUtils.equals(sss,"restart")){
		if (refreshSTATUS.equals("1")|| refreshSTATUS.equals("2")) {
			AlertDialog.Builder builder3 = new Builder(InfusionActivity.this);
			builder3.setTitle("提示")
					.setMessage("您确定继续吗?")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setCancelable(true)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,int which) {
									showDialog();
									postrestart();             //有问题
									postgetstart();
									btn_restart.setVisibility(View.GONE);
									btn_pause.setVisibility(View.VISIBLE);}
							}).setNegativeButton("取消", null).show();
		} else {
			AlertDialog.Builder builder = new Builder(InfusionActivity.this);
			builder.setTitle("提示").setMessage("没有药品在输液")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setCancelable(true).setPositiveButton("确定", null).show();}
		
	}else if(TextUtils.equals(sss,"pause")){
		
			if (refreshSTATUS.equals("1")|| refreshSTATUS.equals("2")) {
				AlertDialog.Builder builder2 = new Builder(InfusionActivity.this);
				builder2.setTitle("提示")
						.setMessage("您确定暂停吗?")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setCancelable(true)
						.setPositiveButton("确定",new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,int which) {
										showDialog();
										postpause();
										postgetstart();
										btn_restart.setVisibility(View.VISIBLE);
										btn_pause.setVisibility(View.GONE);
									}}).setNegativeButton("取消", null).show();
			} else {
				AlertDialog.Builder builder = new Builder(InfusionActivity.this);
				builder.setTitle("提示").setMessage("没有药品在输液")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setCancelable(true).setPositiveButton("确定", null).show();
			}
		
	}else if(TextUtils.equals(sss,"finish")){

			if (refreshSTATUS.equals("1")|| refreshSTATUS.equals("2")) {
				AlertDialog.Builder builder4 = new Builder(InfusionActivity.this);
				builder4.setTitle("提示").setMessage("您确定结束吗?")
						.setIcon(android.R.drawable.ic_dialog_info).setCancelable(true)
						.setPositiveButton("确定",new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,int which) {
										showDialog();
										postfinish();
										postgetstart();
									}}).setNegativeButton("取消", null).show();
			} else {
				AlertDialog.Builder builder = new Builder(InfusionActivity.this);
				builder.setTitle("提示").setMessage("没有药品在输液")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setCancelable(true).setPositiveButton("确定", null).show();
			}
		
	}
		
	};
	
	//非扫描的按钮点击事件
	//http://192.168.11.11:8088/json/vw_SeatsInfo.aspx?STAFFID=1
	private void getInfo(final String state){
		
		OkHttpUtils.post().url(HttpUrls_.HttpURL+seatUrl)
		.addParams("STAFFID", STAFFID+"").build()
		.execute(new StringCallback() {
			
			@Override
			public void onResponse(final String str) {
						
				new Thread(new  Runnable() {
							public void run() {
				
						refreshSTATUS="";
					    refreshIGNO=0;
					    refreshTUBEID="";
					    refreshDRIPCNTSPERMINUTE="";
						try {
							JSONArray jsonArray = new JSONArray(str);
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject object = jsonArray.getJSONObject(i);
								SeatsInfo info = new SeatsInfo();
								info.setSEATNO(object.getString("SEATNO"));
								info.setSEATSTATUS(object.getString("SEATSTATUS"));
								info.setINFUSIONID(object.getString("INFUSIONID"));
								info.setPATIENTID(object.getString("PATIENTID"));
								info.setCARDNO(object.getString("CARDNO"));
								info.setPATIENTNAME(object.getString("PATIENTNAME"));
								info.setGENDER(object.getString("GENDER"));
								info.setAGE(object.getString("AGE"));
								info.setBAGCNTS(object.getString("BAGCNTS"));
								info.setCOMPLETEDBAGCNTS(object.getString("COMPLETEDBAGCNTS"));
								info.setTOTALDURATION(object.getString("TOTALDURATION"));
								info.setTOTALVOLUME(object.getString("TOTALVOLUME"));
								info.setINFUSIONGROUPNO(object.getInt("INFUSIONGROUPNO"));
								// info.setINFUSIONDETAILID(object.getString("INFUSIONDETAILID"));
								info.setREMAINDERRATE(object.getString("REMAINDERRATE"));
								info.setDRIPCNTSPERMINUTE(object.getString("DRIPCNTSPERMINUTE"));
								info.setTUBEID(object.getString("TUBEID"));
								info.setDRIPCNTSPERML(object.getString("DRIPCNTSPERML"));
								info.setTUBENAME(object.getString("TUBENAME"));
								info.setINFUSIONSTATUS(object.getString("INFUSIONSTATUS"));
								info.setINFUSIONSTATUSNAME(object.getString("INFUSIONSTATUSNAME"));
								info.setREMAINDERVOLUME(object.getString("REMAINDERVOLUME"));
								info.setREMAINDERTIME(object.getString("REMAINDERTIME"));

								if(TextUtils.equals(infos.getINFUSIONID(), "-1")){
									return;
								}
								
								if (TextUtils.equals(info.getINFUSIONID(),infos.getINFUSIONID())) {	
									refreshSTATUS=info.getINFUSIONSTATUS();
									refreshIGNO=info.getINFUSIONGROUPNO();
									refreshTUBEID=info.getTUBEID();
									refreshDRIPCNTSPERMINUTE=info.getDRIPCNTSPERMINUTE();
									Message msg=Message.obtain();
									msg.obj=state;
	                               handler.sendMessage(msg);
								}
								
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}}}).start();
							
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				
				Toast.makeText(InfusionActivity.this,"请检查网络",Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	// 参数=20170113000004 徐芝花
	// 参数=20170113000002 陈国霖
	// 个人信息 ：
	// http://192.168.0.83:8088/json/GetQrCodeBase.aspx?QRCODE=20170607000028
	// http://192.168.0.83:8088/json/GetQrCodeBase.aspx?QRCODE=20170620000007
	private void getOrdersInfo() {

		OkHttpUtils.post().url(HttpUrls_.HttpURL + perInfoUrl)
				.addParams("QRCODE", myQrCode).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {

						runOnUiThread(new Runnable() {
							public void run() {

								Gson gson = new Gson();

								pInfo = gson.fromJson(str, ScanPersonInfo.class);

								if (pInfo == null) {

									showInfoDialog();

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

								getQrCode();
							}
						});

					}

					@Override
					public void onError(Call arg0, Exception arg1) {

						Toast.makeText(InfusionActivity.this, "请检查网络",Toast.LENGTH_SHORT).show();

					}});
    }

	// 对应的url:http://192.168.0.83:8088/json/GetQrCode.aspx?QRCODE=20170620000007
	private void getQrCode() {

		OkHttpUtils.post().url(HttpUrls_.HttpURL + myQrCodeUrl)
				.addParams("QRCODE", myQrCode).build()
				.execute(new StringCallback() {

					@Override
					public void onResponse(final String str) {
						runOnUiThread(new Runnable() {
							public void run() {

								qrCodeInfos.clear();
								infusion = str;

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

										if (info.getTUBEID() != null) {

											for (int k = 0; k < tubesInfos.size(); k++) {
												if (tubesInfos.get(k).getTUBEID().equals(info.getTUBEID())) {
													info.setTUBENAME(tubesInfos.get(k).getTUBENAME());
												}
											}
										}

										qrCodeInfos.add(info);

									}
								} catch (JSONException e) {
									e.printStackTrace();
								}

								if (qrCodeInfos != null) {
									isScan = true;

									if (qrCodeInfos.get(0).getInfusionStatusName().equals("正在输液")) {

										btn_restart.setVisibility(View.GONE);
										btn_pause.setVisibility(View.VISIBLE);
									} else if (qrCodeInfos.get(0).getInfusionStatusName().equals("暂停输液")) {

										btn_restart.setVisibility(View.VISIBLE);
										btn_pause.setVisibility(View.GONE);
									}

								}
								adapter = null;
								adapter = new InfusionAdapter(
										InfusionActivity.this, qrCodeInfos);
								list_drug.setAdapter(adapter);
							
							}
						});

					}

					@Override
					public void onError(Call arg0, Exception arg1) {
						Toast.makeText(InfusionActivity.this, "请检查网络",Toast.LENGTH_SHORT).show();
					}});
}

	public void initview() {
		list_drug = (ListView) findViewById(R.id.list_drug);
		tv_seatno = (TextView) findViewById(R.id.tv_seatno);
		btn_start = (Button) findViewById(R.id.btn_start);
		btn_restart = (Button) findViewById(R.id.btn_restart);
		btn_pause = (Button) findViewById(R.id.btn_pause);
		btn_finish = (Button) findViewById(R.id.btn_finish);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_cardno = (TextView) findViewById(R.id.tv_cardno);
		tv_sex = (TextView) findViewById(R.id.tv_sex);
		tv_age = (TextView) findViewById(R.id.tv_age);
		tv_birth = (TextView) findViewById(R.id.tv_birth);
		tv_orderdapename = (TextView) findViewById(R.id.tv_orderdapename);
		tv_ordername = (TextView) findViewById(R.id.tv_ordername);
		tv_diagindo = (TextView) findViewById(R.id.tv_diagindo);
		btn_back = (Button) findViewById(R.id.btn_back);
        hsv=(HorizontalScrollView) findViewById(R.id.hsv);
        hsv.setOverScrollMode(HorizontalScrollView.OVER_SCROLL_NEVER);
		if (infos != null) {
			if (infos.getINFUSIONSTATUSNAME().equals("正在输液")) {
				btn_restart.setVisibility(View.GONE);
				btn_pause.setVisibility(View.VISIBLE);
			} else if (infos.getINFUSIONSTATUSNAME().equals("暂停输液")) {
				btn_restart.setVisibility(View.VISIBLE);
				btn_pause.setVisibility(View.GONE);
			}
			tv_seatno.setText(infos.getSEATNO());
		}
		btn_start.setOnClickListener(this);
		btn_restart.setOnClickListener(this);
		btn_pause.setOnClickListener(this);
		btn_finish.setOnClickListener(this);
		btn_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_start:
			
			if (infos != null) {
					getInfo("start");
			} else if (infos == null) {

				if (qrCodeInfos.size() > 0) {

					if (qrCodeInfos.get(0).getINFUSIONSTATUS().equals("1")
							|| qrCodeInfos.get(0).getINFUSIONSTATUS().equals("2")) {
						AlertDialog.Builder builder = new Builder(InfusionActivity.this);
						builder.setTitle("提示").setMessage("有药品正在输液或暂停输液")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setCancelable(true)
								.setPositiveButton("确定", null).show();
					} else if (qrCodeInfos.get(0).getINFUSIONSTATUS().equals("3")) {
						AlertDialog.Builder builder = new Builder(InfusionActivity.this);
						builder.setTitle("提示").setMessage("该药品已完成输液")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setCancelable(true)
								.setPositiveButton("确定", null).show();
					} else {
						
						Intent intent = new Intent();
						intent.putExtra("STAFFID", STAFFID);
						intent.putExtra("infos", infos);
						intent.putExtra("tubes", tubes);
						intent.putExtra("speed", speed);
						intent.putExtra("PatientId", myPatientId);
						intent.putExtra("infusion", infusion);
						
						if(qrCodeInfos!=null){
							intent.putExtra("qrCodeTubes",qrCodeInfos.get(0).getTUBEID());
							intent.putExtra("qrCodeSpeed", qrCodeInfos.get(0).getDRIPCNTSPERMINUTE());
						}
						
						intent.setClass(InfusionActivity.this,TubleActivity.class);
						startActivityForResult(intent, 100);
					}
				}
			}

			break;
		case R.id.btn_restart:
			if (infos != null) {
				getInfo("restart");
			} else if (infos == null) {
				if (qrCodeInfos.size() > 0) {
					// for(int i=0;i<qrCodeInfos.size();i++){
					if (qrCodeInfos.get(0).getINFUSIONSTATUS().equals("1")
							|| qrCodeInfos.get(0).getINFUSIONSTATUS().equals("2")) {
						AlertDialog.Builder builder3 = new Builder(InfusionActivity.this);
						builder3.setTitle("提示")
								.setMessage("您确定继续吗?")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setCancelable(true)
								.setPositiveButton("确定",new DialogInterface.OnClickListener() {

											@Override
											public void onClick(DialogInterface dialog,int which) {
												showDialog();
												postrestart();
												postgetstart();
												btn_restart.setVisibility(View.GONE);
												btn_pause.setVisibility(View.VISIBLE);}
										}).setNegativeButton("取消", null).show();

					} else {
						AlertDialog.Builder builder = new Builder(InfusionActivity.this);
						builder.setTitle("提示").setMessage("没有药品在输液")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setCancelable(true)
								.setPositiveButton("确定", null).show();
					}

				}
				// }
			}
			break;
		case R.id.btn_pause:
			if (infos != null) {
				getInfo("pause");
			} else if (infos == null) {
				// for(int i=0;i<qrCodeInfos.size();i++){
				if (qrCodeInfos.size() > 0) {
					if (qrCodeInfos.get(0).getINFUSIONSTATUS().equals("1")
							|| qrCodeInfos.get(0).getINFUSIONSTATUS().equals("2")) {
						AlertDialog.Builder builder2 = new Builder(InfusionActivity.this);
						builder2.setTitle("提示")
								.setMessage("您确定暂停吗?")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setCancelable(true)
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(DialogInterface dialog,int which) {
												showDialog();
												postpause();
												postgetstart();
												btn_restart.setVisibility(View.VISIBLE);
												btn_pause.setVisibility(View.GONE);}
										}).setNegativeButton("取消", null).show();

					} else {
						AlertDialog.Builder builder = new Builder(InfusionActivity.this);
						builder.setTitle("提示").setMessage("没有药品在输液")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setCancelable(true).setPositiveButton("确定", null).show();
					}
				}
			}
			break;
		case R.id.btn_finish:
			if (infos != null) {
				getInfo("finish");
			} else if (infos == null) {
				if (qrCodeInfos.size() > 0) {
					if (qrCodeInfos.get(0).getINFUSIONSTATUS().equals("1")
							|| qrCodeInfos.get(0).getINFUSIONSTATUS().equals("2")) {
						AlertDialog.Builder builder4 = new Builder(InfusionActivity.this);
						builder4.setTitle("提示").setMessage("您确定结束吗?")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setCancelable(true).setPositiveButton("确定",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(DialogInterface dialog,int which) {
												showDialog();
												postfinish();
												postgetstart();
											}
										}).setNegativeButton("取消", null).show();
					} else {
						AlertDialog.Builder builder = new Builder(InfusionActivity.this);
						builder.setTitle("提示").setMessage("没有药品在输液")
								.setIcon(android.R.drawable.ic_dialog_info)
								.setCancelable(true)
								.setPositiveButton("确定", null).show();
					}
				}
			}
			break;
		case R.id.btn_back:
			finish();
			break;
		default:
			break;
		}
	}

	// 获取个人基本信息
	public void postOrdersInfo() {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("STAFFID", STAFFID + "");
		if (infos != null) {
			data.put("CARDNO", infos.getCARDNO());
		}
		//http://192.168.0.83:8088/json/vw_InfusionOrdersInfo.aspx?STAFFID=1&CARDNO=J00301979
		//http://192.168.0.83:8088/json/vw_InfusionOrdersInfo.aspx?STAFFID=1&CARDNO=H00007572
		
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.INFUSIONORDERSINFO,params);
		MainService.newTask(task);

	}

	// 继续   http://192.168.11.11:8088/json/usp_do_InfusionReStart.aspx?STAFFID=1&PATIENTID=49&INFUSIONID=1137&INFUSIONGROUPNO=1&MTUBEID=24&MDRIPCNTSPERMINUTE=100
	public void postrestart() {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("STAFFID", STAFFID + "");		
		if (infos != null) {
			data.put("PATIENTID", infos.getPATIENTID());
			// data.put("INFUSIONDETAILID", infos.getINFUSIONDETAILID());
			data.put("INFUSIONID", infos.getINFUSIONID());
			data.put("INFUSIONGROUPNO", refreshIGNO + "");
			data.put("MTUBEID", refreshTUBEID);
			data.put("MDRIPCNTSPERMINUTE", refreshDRIPCNTSPERMINUTE);			
		} else {
			data.put("PATIENTID", String.valueOf(myPatientId));
			data.put("INFUSIONID", qrCodeInfos.get(0).getINFUSIONID());
			data.put("INFUSIONGROUPNO", qrCodeInfos.get(0).getINFUSIONGROUPNO());
			data.put("MTUBEID", qrCodeInfos.get(0).getTUBEID());
			data.put("MDRIPCNTSPERMINUTE", qrCodeInfos.get(0).getDRIPCNTSPERMINUTE());
		}
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.INFUSIONRESTART, params);
		MainService.newTask(task);
	}

	// 停止当前输液
	public void postpause() {
		
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("STAFFID", STAFFID + "");
		if (infos != null) {
			data.put("PATIENTID", infos.getPATIENTID());
			data.put("INFUSIONID", infos.getINFUSIONID());
			data.put("INFUSIONGROUPNO", refreshIGNO + "");
			
		} else {
			data.put("PATIENTID", String.valueOf(myPatientId));
			data.put("INFUSIONID", qrCodeInfos.get(0).getINFUSIONID());
			data.put("INFUSIONGROUPNO", qrCodeInfos.get(0).getINFUSIONGROUPNO());
		}

		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.INFUSIONPAUSE, params);
		MainService.newTask(task);

	}

	// 完成当前输液
	public void postfinish() {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("STAFFID", STAFFID + "");
		if (infos != null) {
			data.put("PATIENTID", infos.getPATIENTID());
			// data.put("INFUSIONDETAILID", infos.getINFUSIONDETAILID());
			data.put("INFUSIONID", infos.getINFUSIONID());
			data.put("INFUSIONGROUPNO", refreshIGNO + "");
		} else {
			data.put("PATIENTID", String.valueOf(myPatientId));
			data.put("INFUSIONID", qrCodeInfos.get(0).getINFUSIONID());
			data.put("INFUSIONGROUPNO", qrCodeInfos.get(0).getINFUSIONGROUPNO());
		}
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.INFUSIONFINISH, params);
		MainService.newTask(task);

		SeatsActivity.promptlist.clear();
	}

	// 管型
	public void postTubs() {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("STAFFID", STAFFID + "");
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.TUBES, params);
		MainService.newTask(task);

	}

	// 滴数
	public void postDripseed() {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("STAFFID", STAFFID + "");
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.DRIPSPEED, params);
		MainService.newTask(task);

	}

	// 获取输液信息
	public void postgetstart() {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("STAFFID", STAFFID + "");
		if (infos != null) {
			data.put("PATIENTID", infos.getPATIENTID());
		} else {
			data.put("PATIENTID", String.valueOf(myPatientId));
		}
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.GETSTART, params);
		MainService.newTask(task);

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		MainService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		
		// TODO Auto-generated method stub
		switch (Integer.valueOf(params[0].toString().trim())) {
		
		
		
		case InfusionActivity.GETSTART:

			if (params[1] != null) {
				if (infos != null) {
					infusion = params[1].toString().trim();
					parsegetStart(infusion);
				} else {
					if (!TextUtils.isEmpty(myQrCode)) {
						// 扫描二维码获取信息
						getOrdersInfo();
					}
				}
			}
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			break;
		case InfusionActivity.INFUSIONORDERSINFO:
			
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (params[1] != null) {
				String value = params[1].toString().trim();
				infusionInfoList(value);
			}
			break;

		case InfusionActivity.INFUSIONRESTART:
			
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (params[1] != null) {
				String str;
				String value = params[1].toString().trim();
				if (value.equals("True")) {
					str = "完成";
				} else {
					str = value;
				}
				//Log.i("2017/1/16","提示信息="+str); 
				AlertDialog.Builder builder4 = new Builder(InfusionActivity.this);
				builder4.setTitle("提示").setMessage(str)
						.setIcon(android.R.drawable.ic_dialog_info).setCancelable(true)
						.setPositiveButton("确定",new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,int which) {

										postgetstart();
										btn_restart.setVisibility(View.GONE);
										btn_pause.setVisibility(View.VISIBLE);}}).show();

			}
			break;
		case InfusionActivity.INFUSIONPAUSE:
			
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (params[1] != null) {
				String str;
				String value = params[1].toString().trim();
				if (value.equals("True")) {
					str = "完成";
				} else {
					str = value;
				}
				AlertDialog.Builder builder4 = new Builder(InfusionActivity.this);
				builder4.setTitle("提示").setMessage(str)
						.setIcon(android.R.drawable.ic_dialog_info).setCancelable(true)
						.setPositiveButton("确定",new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,int which) {

										postgetstart();
										btn_restart.setVisibility(View.VISIBLE);
										btn_pause.setVisibility(View.GONE);
									}}).show();
			}
			break;
		case InfusionActivity.INFUSIONFINISH:
			
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (params[1] != null) {
				String str;
				String value = params[1].toString().trim();
				if (value.equals("True")) {
					str = "完成";
				} else {
					str = value;
				}
				AlertDialog.Builder builder4 = new Builder(InfusionActivity.this);
				builder4.setTitle("提示").setMessage(str)
						.setIcon(android.R.drawable.ic_dialog_info)
						.setCancelable(true)
						.setPositiveButton("确定",new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,int which) {

										postgetstart();

									}}).show();
			}
			break;
		case InfusionActivity.DRIPSPEED:
			// String speed = "";
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (params[1] != null) {
				speed = params[1].toString().trim();

			}

			break;
		case InfusionActivity.TUBES:
			// String tubes = "";
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (params[1] != null) {
				tubes = params[1].toString().trim();

				TubesList(tubes);
			}

			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 100) {
			if (resultCode == RESULT_OK) {
				postgetstart();

			}
		}
	}

	private void infusionInfoList(String mtStr) {
		// infusionInfos.clear();
		// List<UnideInfo> list=new ArrayList<UnideInfo>();
		try {
			JSONArray jsonArray = new JSONArray(mtStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				InfusionInfo info = new InfusionInfo();
				info.setHISORDERID(object.getString("HISORDERID"));
				info.setCARDNO(object.getString("CARDNO"));
				info.setPATIENTNAME(object.getString("PATIENTNAME"));
				info.setGENDER(object.getString("GENDER"));
				info.setBIRTHDATE(object.getString("BIRTHDATE"));
				info.setORDERDATE(object.getString("ORDERDATE"));
				info.setORDERDOCTORNAME(object.getString("ORDERDOCTORNAME"));
				info.setORDERDEPTNAME(object.getString("ORDERDEPTNAME"));
				info.setDIAGINFO(object.getString("DIAGINFO"));
				info.setHISCLINICNO(object.getString("HISCLINICNO"));

				infusionInfos.add(info);
				tv_name.setText(info.getPATIENTNAME());			
				tv_cardno.setText(info.getCARDNO());
				if (info.getGENDER().endsWith("1")) {
					tv_sex.setText("男");
				} else {
					tv_sex.setText("女");
				}
				Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int age = Integer.parseInt(info.getBIRTHDATE().substring(0, 4));
				int age2 = year - age;
				tv_age.setText(age2 + "");
				tv_birth.setText(info.getBIRTHDATE().substring(0, 10));
				tv_orderdapename.setText(info.getORDERDEPTNAME());
				tv_ordername.setText(info.getORDERDOCTORNAME());
				tv_diagindo.setText(info.getDIAGINFO());

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void TubesList(String mtStr) {
		tubesInfos.clear();
		try {
			JSONArray jsonArray = new JSONArray(mtStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				TubesInfo info = new TubesInfo();
				info.setTUBEID(object.getString("TUBEID"));
				info.setTUBENAME(object.getString("TUBENAME"));
				info.setDRIPCNTSPERML(object.getString("DRIPCNTSPERML"));
				tubesInfos.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parsegetStart(String mtStr) {
		getStartInfos.clear();
		// List<UnideInfo> list=new ArrayList<UnideInfo>();
		try {
			JSONArray jsonArray = new JSONArray(mtStr);
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

				if (info.getTUBEID() != null) {

					for (int k = 0; k < tubesInfos.size(); k++) {
						if (tubesInfos.get(k).getTUBEID().equals(info.getTUBEID())) {
							info.setTUBENAME(tubesInfos.get(k).getTUBENAME());
						}
					}
				}
				getStartInfos.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (qrCodeInfos != null) {
			isScan = false;
		}
		adapter = null;
		adapter = new InfusionAdapter(this, getStartInfos);
		list_drug.setAdapter(adapter);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		Intent intent = new Intent();
		InfusionActivity.this.setResult(RESULT_OK, intent);
		finish();
		return super.onKeyDown(keyCode, event);
	}

	private void showDialog() {
		dialog = new ProgressDialog(context);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("正在查询，请稍等...");
		dialog.show();
	}

   private void showInfoDialog(){
		
		AlertDialog.Builder builder=new Builder(this);
		builder.setTitle("提示").setMessage("信息不匹配！")
		.setIcon(android.R.drawable.ic_dialog_info)
		.setCancelable(true).setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				finish();
			}
		}).show();
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (LoginActivity.isPad(this)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}
	}
}

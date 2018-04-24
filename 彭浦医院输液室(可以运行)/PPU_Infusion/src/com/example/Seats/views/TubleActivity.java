package com.example.Seats.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.Seats.beans.DripSpeedInfo;
import com.example.Seats.beans.GetStartInfo;
import com.example.Seats.beans.SeatsInfo;
import com.example.Seats.beans.TubesInfo;
import com.example.Seats.beans.TubleAdapter;
import com.example.companytask.CompanyTask;
import com.example.helper.IActivity;
import com.example.ppu_infusion.LoginActivity;
import com.example.ppu_infusion.R;
import com.example.services.MainService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class TubleActivity extends Activity implements IActivity,
		OnClickListener {

	public static final int INFUSIONSTART = 1;
	private int STAFFID;
	// private String PatientID;
	public Spinner spinner_Tubes, spinner_dripSpeed;
	private Button btn_start;
	public ListView lsview_tuble;
	public ArrayList<TubesInfo> tubesInfos = new ArrayList<TubesInfo>();
	public ArrayList<DripSpeedInfo> speedInfos = new ArrayList<DripSpeedInfo>();
	public ArrayAdapter<TubesInfo> TubesAdapter;
	public ArrayAdapter<DripSpeedInfo> DripSpeedAdapter;
	private List<GetStartInfo> infos = new ArrayList<GetStartInfo>();
	private TubleAdapter adapter;
	private String tubes, speed, infusion,qrCodeTubes,qrCodeSpeed;
	public SeatsInfo seatsInfo;
	private String str;

	private String INFUSIONID;
	private String INFUSIONGROUPNO;
    private int myPatientId;
    public LinearLayout ll;
	
    private int jishu;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置屏幕无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置屏幕全屏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_tuble);
		Intent intent = getIntent();
		STAFFID = intent.getIntExtra("STAFFID", -1);
		// PatientID = intent.getStringExtra("PatientID");
		seatsInfo = (SeatsInfo) intent.getSerializableExtra("infos");
		
		tubes = intent.getStringExtra("tubes");
		speed = intent.getStringExtra("speed");
		infusion = intent.getStringExtra("infusion");
		
		qrCodeTubes=intent.getStringExtra("qrCodeTubes");
		qrCodeSpeed=intent.getStringExtra("qrCodeSpeed");
		
		jishu=0;
		if(seatsInfo==null){
			
			myPatientId=intent.getIntExtra("PatientId",-1);
			
		}
		
		init();

		/*
		 * postTubs(); postDripseed();
		 * 
		 * postgetstart();
		 */
		initview();
	}

	public void initview() {
		ll=(LinearLayout) findViewById(R.id.LinearLayout1);
		spinner_dripSpeed = (Spinner) findViewById(R.id.spinner_dripSpeed);
		spinner_Tubes = (Spinner) findViewById(R.id.spinner_Tubes);
		btn_start = (Button) findViewById(R.id.btn_start2);
		lsview_tuble = (ListView) findViewById(R.id.lsview_tuble);
		btn_start.setOnClickListener(this);
		// if(!infusion.equals("")||infusion!=null){
		if (infusion != null) {
			parseJsonList(infusion);
		}
		// 管型
		if (tubes != null) {
			TubesList(tubes);
		}
		TubesAdapter = new ArrayAdapter<TubesInfo>(this,
				android.R.layout.simple_spinner_item, tubesInfos);
		TubesAdapter
				.setDropDownViewResource(R.layout.mysimple_spinner_dropdown_item);
		spinner_Tubes.setAdapter(TubesAdapter);
		if (seatsInfo != null) {
			if (seatsInfo.getTUBEID() != null) {
				for (int i = 0; i < tubesInfos.size(); i++) {
					if (tubesInfos.get(i).getTUBEID()
							.equals(seatsInfo.getTUBEID())) {
						spinner_Tubes.setSelection(i);
					}
				}
			}
		} else if (seatsInfo == null) {
			if(qrCodeTubes!=null){
				
				for (int i = 0; i < tubesInfos.size(); i++) {
					if (tubesInfos.get(i).getTUBEID()
							.equals(qrCodeTubes)) {
						spinner_Tubes.setSelection(i);
					}
				}	
			}
		}
		if (tubesInfos.size() <= 3 && tubesInfos.size() > 1) {
			spinner_Tubes.setSelection(1);
		}
		// 滴数
		if (speed != null) {
			DripSpeedList(speed);
			
		}else{
		
		}

		DripSpeedAdapter = new ArrayAdapter<DripSpeedInfo>(this,
				android.R.layout.simple_spinner_item, speedInfos);
		DripSpeedAdapter
				.setDropDownViewResource(R.layout.mysimple_spinner_dropdown_item);
		spinner_dripSpeed.setAdapter(DripSpeedAdapter);
		if (seatsInfo != null) {
			if (seatsInfo.getDRIPCNTSPERMINUTE() != null) {
				for (int i = 0; i < speedInfos.size(); i++) {
					if (speedInfos.get(i).getDRIPCNTSPERMINUTE()
							.equals(seatsInfo.getDRIPCNTSPERMINUTE())) {
						spinner_dripSpeed.setSelection(i);
					}
				}
			} 

		}else{
			
			if(qrCodeSpeed!=null){
				for (int i = 0; i < speedInfos.size(); i++) {	
					
					if (speedInfos.get(i).getDRIPCNTSPERMINUTE()
							.equals(qrCodeSpeed)) {
						spinner_dripSpeed.setSelection(i);
					}
				}
			}	
		
		}
		if (speedInfos.size() <= 3 && speedInfos.size() > 1) {
			spinner_dripSpeed.setSelection(1);
		}

	}

	public void GetInfo(GetStartInfo info) {
		
		if (info.getINFUSIONSTATUS().equals("1")) {
			Toast.makeText(getApplicationContext(), "该药品正在输液",
					Toast.LENGTH_LONG).show();
		} else if (info.getINFUSIONSTATUS().equals("2")) {
			Toast.makeText(getApplicationContext(), "该药品暂停输液",
					Toast.LENGTH_LONG).show();
		} else if (info.getINFUSIONSTATUS().equals("3")) {
			
			if(jishu==0){
			
			Toast.makeText(getApplicationContext(), "该药品已完成输液",
					Toast.LENGTH_LONG).show();
			++jishu;
			}
		}
		/*
		 * else
		 * if(seatsInfo.getInfusionDetailID().equals(info.getInfusionDetailID
		 * ())){ Toast.makeText(getApplicationContext(), "该药品正在输液或暂停输液",
		 * Toast.LENGTH_LONG).show(); }
		 */
		// DetailID = info.getINFUSIONDETAILID();

		INFUSIONID = info.getINFUSIONID();
		INFUSIONGROUPNO = info.getINFUSIONGROUPNO();

	}

	/*
	 * public void postTubs() { Map<String, String> data = new HashMap<String,
	 * String>(); Map<String, Object> params = new HashMap<String, Object>();
	 * data.put("STAFFID", STAFFID + ""); params.put("data", data); CompanyTask
	 * task = new CompanyTask(CompanyTask.TUBES, params);
	 * MainServices.newTask(task);
	 * 
	 * }
	 * 
	 * public void postDripseed() { Map<String, String> data = new
	 * HashMap<String, String>(); Map<String, Object> params = new
	 * HashMap<String, Object>(); data.put("STAFFID", STAFFID + "");
	 * params.put("data", data); CompanyTask task = new
	 * CompanyTask(CompanyTask.DRIPSPEED, params); MainServices.newTask(task);
	 * 
	 * }
	 */
	// 开始输液  http://192.168.11.11:8088/json/usp_do_InfusionStart.aspx?STAFFID=1&PATIENTID=2&INFUSIONID=1119&INFUSIONGROUPNO=2&MTUBEID=32&MDRIPCNTSPERMINUTE=80
	// 开始输液  http://192.168.11.11:8088/json/usp_do_InfusionStart.aspx?STAFFID=1&INFUSIONID=1119&INFUSIONGROUPNO=2&MTUBEID=32&MDRIPCNTSPERMINUTE=80
	public void poststart() {
		
		TubesInfo tubesInfo = (TubesInfo) spinner_Tubes.getSelectedItem();
		DripSpeedInfo dripSpeedInfo = (DripSpeedInfo) spinner_dripSpeed
				.getSelectedItem();
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("STAFFID", STAFFID + "");//工作人员ID
		if(seatsInfo!=null){
		data.put("PATIENTID", seatsInfo.getPATIENTID()); //空指针   病人ID
		}else{
		data.put("PATIENTID",String.valueOf(myPatientId));
		}
				
		data.put("INFUSIONID", INFUSIONID);//关于药品http://192.168.11.11:8088/json/GetQrCode.aspx?QRCODE=20170103000009
		data.put("INFUSIONGROUPNO", INFUSIONGROUPNO);//关于药品http://192.168.11.11:8088/json/GetQrCode.aspx?QRCODE=20170103000009
		data.put("MTUBEID", tubesInfo.getTUBEID());// 管型  http://192.168.11.11:8088/json/tbl_InfusionTubes_Info.aspx?STAFFID=1
		data.put("MDRIPCNTSPERMINUTE", dripSpeedInfo.getDRIPCNTSPERMINUTE());//滴速  http://192.168.11.11:8088/json/tbl_dripSpeed_Info.aspx?STAFFID=1	
		params.put("data", data);
		//Log.i("qwj", "data==" + data);
		CompanyTask task = new CompanyTask(CompanyTask.INFUSIONSTART, params);
		MainService.newTask(task);

	}

	/*
	 * public void postgetstart() { Map<String, String> data = new
	 * HashMap<String, String>(); Map<String, Object> params = new
	 * HashMap<String, Object>(); data.put("STAFFID", STAFFID + "");
	 * data.put("PatientID", seatsInfo.getPatientID()); params.put("data",
	 * data); CompanyTask task = new CompanyTask(CompanyTask.GETSTART, params);
	 * MainServices.newTask(task);
	 * 
	 * }
	 */
	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_start2:
			if (((TubesInfo) spinner_Tubes.getSelectedItem()).getTUBEID()
					.equals("-1")) {
				Toast.makeText(getApplicationContext(), "请选择管型",
						Toast.LENGTH_LONG).show();
			} else if (((DripSpeedInfo) spinner_dripSpeed.getSelectedItem())
					.getDRIPCNTSPERMINUTE().equals("-1")) {
				Toast.makeText(getApplicationContext(), "请选择滴速",
						Toast.LENGTH_LONG).show();
			} else if (TextUtils.isEmpty(INFUSIONID)
					|| TextUtils.isEmpty(INFUSIONGROUPNO)) {
				Toast.makeText(getApplicationContext(), "请选择药品",
						Toast.LENGTH_LONG).show();
			} else {
				AlertDialog.Builder builder = new Builder(TubleActivity.this,AlertDialog.THEME_HOLO_LIGHT);
				builder.setTitle("提示")
						.setMessage("您确定开始吗?")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setCancelable(true)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										poststart();
									}
								}).setNegativeButton("取消", null).show();

				// finish();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		MainService.addActivity(this);
	}

	@SuppressLint("NewApi")
	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.valueOf(params[0].toString().trim())) {
		/*
		 * case TubleActivity.DRIPSPEED: //String speed = ""; if (params[1] !=
		 * null) { speed = params[1].toString().trim();
		 * 
		 * }
		 * 
		 * break; case TubleActivity.TUBES: //String tubes = ""; if (params[1]
		 * != null) { tubes = params[1].toString().trim(); }
		 * 
		 * 
		 * break;
		 */

		case TubleActivity.INFUSIONSTART:
			if (params[1] != null) {

				String value = params[1].toString().trim();
				if (value.equals("True")) {
					str = "完成";
				} else {
					str = value;
				}
				
				AlertDialog.Builder builder4 = new Builder(TubleActivity.this,AlertDialog.THEME_HOLO_LIGHT);
				builder4.setTitle("提示")
						.setMessage(str)
						.setIcon(android.R.drawable.ic_dialog_info)
						.setCancelable(true)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										Intent intent = new Intent();
										TubleActivity.this.setResult(RESULT_OK,
												intent);
										TubleActivity.this.finish();
									}
								}).show();
			}
			break;
		default:
			break;
		}
	}

	private void TubesList(String mtStr) {
		tubesInfos.clear();
		tubesInfos.add(new TubesInfo("-1", "请选择", "-1"));
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

	private void DripSpeedList(String mtStr) {
		speedInfos.clear();
		speedInfos.add(new DripSpeedInfo("请选择", "-1"));
		try {
			JSONArray jsonArray = new JSONArray(mtStr);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject object = jsonArray.getJSONObject(i);
				DripSpeedInfo info = new DripSpeedInfo();
				info.setSPEEDNAME(object.getString("SPEEDNAME"));
				info.setDRIPCNTSPERMINUTE(object.getString("DRIPCNTSPERMINUTE"));
				speedInfos.add(info);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void parseJsonList(String mtStr) {
		infos.clear();
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
				// info.setBAGITEMNAME(object.getString("BAGITEMNAME"));
				// info.setBAGITEMYBCODE(object.getString("BAGITEMYBCODE"));
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

				infos.add(info);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		adapter = new TubleAdapter(this, infos);
		lsview_tuble.setAdapter(adapter);
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

	
	 /** 
     * 将px值转换为dip或dp值，保证尺寸大小不变 
     *  
     * @param pxValue 
     * @param scale 
     *            （DisplayMetrics类中属性density） 
     * @return 
     */  
    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
  
    /** 
     * 将dip或dp值转换为px值，保证尺寸大小不变 
     *  
     * @param dipValue 
     * @param scale 
     *            （DisplayMetrics类中属性density） 
     * @return 
     */  
    public static int dip2px(Context context, float dipValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dipValue * scale + 0.5f);  
    }  
	
}

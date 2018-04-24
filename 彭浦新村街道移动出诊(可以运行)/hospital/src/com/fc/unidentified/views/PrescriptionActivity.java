package com.fc.unidentified.views;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.companytask.CompanyTask;
import com.example.hospital.HomePageActivity;
import com.example.hospital.R;
import com.example.hospital.R.layout;
import com.example.service.MainService;
import com.fc.has.views.HomeVisitActivity;
import com.fc.has.views.HomeVisitSearchActivity;
import com.fc.helper.BaseActivity;
import com.fc.helper.CustomApplication;
import com.fc.helper.IActivity;
import com.fc.unidentified.beans.DiagInfo;
import com.fc.unidentified.beans.DiagnosisAdapter;
import com.fc.unidentified.beans.FrequencyInfo;
import com.fc.unidentified.beans.ItemsInfo;
import com.fc.unidentified.beans.ItemsInfoAdapter;
import com.fc.unidentified.beans.PrescriptionAdapter;
import com.fc.unidentified.beans.UsageInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PrescriptionActivity extends BaseActivity implements IActivity,
		OnClickListener {
	public static final int PRES_INFO = 1;
	public static final int DIAGNOSIS = 2;
	public static final int PRESCRIBE = 3;
	private EditText ed_input;
	private Button btn_query, btn_keep;
	private TextView tv_price1, tv_price2, tv_price3;
	public ListView listView;
	private PrescriptionAdapter adapter;
	private List<ItemsInfo> itemsInfos = new ArrayList<ItemsInfo>();
	private Context context = this;
	private ProgressDialog dialog;
	private ItemsInfo itemsInfo;
	private ItemsInfo itemsInfo2;
	private String freque, usage;
	private String much, num;
	private MyReceiver receiver = null;
	private double price2, price3, price4;
	private CustomApplication app;
	private int mRegId;
	private String DiagCode, DiagName;
	private String mDiagInfo;
	private int size;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prescription);
		init();
		initView();

	}

	public void initView() {
		app = (CustomApplication) getApplication();
		ed_input = (EditText) findViewById(R.id.ed_input2);
		btn_query = (Button) findViewById(R.id.btn_query2);
		btn_keep = (Button) findViewById(R.id.btn_keep);
		listView = (ListView) findViewById(R.id.lsview_presc);
		tv_price1 = (TextView) findViewById(R.id.tv_price1);
		tv_price2 = (TextView) findViewById(R.id.tv_price2);
		tv_price3 = (TextView) findViewById(R.id.tv_price3);
		Intent intent = getIntent();
		mRegId = intent.getIntExtra("mRegId", mRegId);

		// diagInfo=(DiagInfo) intent.getSerializableExtra("DiagCode");
		mDiagInfo = intent.getStringExtra("mDiagInfo");
		// DiagName=intent.getStringExtra("DiagName");
		Log.i("qwj", "==mRegId===" + mRegId);
		Log.i("qwj", "==DiagCode===" + mDiagInfo);
		Log.i("qwj", "==DiagName===" + DiagName);
		Log.i("qwj", "==app.getStaffId()===" + app.getStaffId());
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(PrescriptionActivity.this,
						ItemsDialogActivity.class);

				intent.putExtra("freque", app.getFreque());
				intent.putExtra("usage", app.getUsage());
				intent.putExtra("position", position);
				intent.putExtra("itemsInfos", itemsInfos.get(position));
				intent.putExtra("frequ_id", itemsInfos.get(position)
						.getFreque());
				intent.putExtra("usage_id", itemsInfos.get(position).getUsage());
				intent.putExtra("much2", itemsInfos.get(position).getMuch());
				intent.putExtra("num2", itemsInfos.get(position).getNum());
				intent.putExtra("ItemName", "");
				startActivity(intent);

			}
		});

		receiver = new MyReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.example.service.MainService");
		PrescriptionActivity.this.registerReceiver(receiver, filter);

		btn_query.setOnClickListener(this);
		btn_keep.setOnClickListener(this);
		//获取最新的处方信息，并显示
		if (app.getItemsinfo() != null) {
			itemsInfos = app.getItemsinfo();
			adapter = new PrescriptionAdapter(PrescriptionActivity.this,
					itemsInfos);
			listView.setAdapter(adapter);
			sumje();

		}

	}

	public class MyReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();

			itemsInfo = (ItemsInfo) bundle.getSerializable("itemsInfos");
			freque = bundle.getString("spinner_frequ");
			usage = bundle.getString("spinner_usage");
			much = bundle.getString("much");
			num = bundle.getString("num");

			Log.i("qwj", "==itemsInfos===" + itemsInfo);
			Log.i("qwj", "==much===" + much);
			Log.i("qwj", "==num===" + num);
			Log.i("qwj", "==freque===" + freque);
			Log.i("qwj", "==usage===" + usage);

			if (itemsInfo != null) {

				int info = bundle.getInt("position");
				if (info != -1) {
					itemsInfos.remove(info);

				}
				parseItemsList(itemsInfo.toString());

			}

		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_query2:
			if (!ed_input.getText().toString().trim().equals("")) {
				postDrug();
				showDialog();
				DecorView();
			} else {
				Toast.makeText(getApplicationContext(), "请输入输入码查询",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case R.id.btn_keep:
			if (itemsInfos.size() > 0 || size > 0) {
				final AlertDialog dialog = new AlertDialog.Builder(
						PrescriptionActivity.this).create();
				View view = LayoutInflater.from(PrescriptionActivity.this)
						.inflate(R.layout.dialog_layout, null);
				dialog.setView(view);
				dialog.show();
				Window window = dialog.getWindow();
				window.setContentView(R.layout.dialog_layout);
				TextView tv_dialog = (TextView) window
						.findViewById(R.id.tv_dialog);
				tv_dialog.setText("您确定保存么？");
				Button btnOk = (Button) window.findViewById(R.id.btn_ok);
				Button btnUndo = (Button) window.findViewById(R.id.btn_undo);
				btnOk.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						app.setItemsinfo(null);
						postDiagnosis();
						postPrescribe();
						dialog.cancel();
						DiagnosisActivity._instance.finish();
//						HomePageActivity._instance.finish();
//
//						Intent intent = new Intent(PrescriptionActivity.this,
//								HomePageActivity.class);
//						startActivity(intent);
						//跳到已诊病人界面并刷新
						UnidentifiedActivity.RefreshMsg();
						HomePageActivity.has_button.performClick();
						HomeVisitActivity.RefreshMsg(0);
						finish();
					}
				});
				btnUndo.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
			} else {
				Toast.makeText(getApplicationContext(), "未存在已开的处方",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	public void showInfo(final int info) {

		final AlertDialog dialog = new AlertDialog.Builder(
				PrescriptionActivity.this).create();
		View view = LayoutInflater.from(PrescriptionActivity.this).inflate(
				R.layout.dialog_layout, null);
		dialog.setView(view);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.dialog_layout);
		TextView tv_dialog = (TextView) window.findViewById(R.id.tv_dialog);
		tv_dialog.setText("您确定删除么？");
		Button btnOk = (Button) window.findViewById(R.id.btn_ok);
		Button btnUndo = (Button) window.findViewById(R.id.btn_undo);
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				itemsInfos.remove(info);
				adapter.notifyDataSetChanged();
				dialog.cancel();
				sumje();
			}
		});
		btnUndo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.cancel();
			}
		});

	}
	//根据输入码获取收费项目
	public void postDrug() {
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("InputCode", ed_input.getText().toString().trim());
		params.put("data", data);
		CompanyTask task = new CompanyTask(CompanyTask.DRUGSELECTION, params);
		MainService.newTask(task);
	}
	//保存诊断
	public void postDiagnosis() {

		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("mRegId", mRegId + "");
		data.put("mDiagInfo", mDiagInfo);
		params.put("data", data);
		Log.i("qwj", "===data===" + data);
		CompanyTask task = new CompanyTask(CompanyTask.DIAGNOSIS, params);
		MainService.newTask(task);
	}
	//保存新下达处方
	public void postPrescribe() {

		String Iteminfo = "";
		for (int i = 0; i < itemsInfos.size(); i++) {
			ItemsInfo item = itemsInfos.get(i);
			Iteminfo += item.getItemId() + "" + ":" + item.getNum() + ":"
					+ item.getFreque() + ":" + item.getMuch() + ":"
					+ item.getUsage() + "|";
		}
		Map<String, String> data = new HashMap<String, String>();
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("mPrescribeId", "-1");
		data.put("mRegId", mRegId + "");
		data.put("mPrescribeDoctorId", app.getStaffId() + "");
		data.put("mPrescribeRemark", "备注");
		data.put("mItemInfo", Iteminfo);
		params.put("data", data);
		Log.i("qwj", "===data=33==" + data);
		CompanyTask task = new CompanyTask(CompanyTask.PRESCRIBE, params);
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
		case PrescriptionActivity.PRES_INFO:
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (params[1] != null) {
				String value = params[1].toString().trim();
				// parseItemsList(value);
				if (value.equals("")) {
					Toast.makeText(getApplicationContext(), "您查询的输入码有误",
							Toast.LENGTH_LONG).show();
				} else {
					Intent intent = new Intent();
					intent.putExtra("value", value);
					String ItemName = "";
					for (int j = 0; j < itemsInfos.size(); j++) {

						ItemsInfo item = itemsInfos.get(j);
						ItemName += item.getItemName().trim() + ",";
						Log.i("qwj", "==ItemName===" + ItemName);
					}
					intent.putExtra("ItemName", ItemName);
					Log.i("qwj", "===ItemName===" + ItemName);
					intent.setClass(PrescriptionActivity.this,
							ItemsInfosActivity.class);
					startActivity(intent);
				}
			}
			break;
		case PrescriptionActivity.DIAGNOSIS:
			if (params[1] != null) {
				String value = params[1].toString().trim();

			}
			break;

		case PrescriptionActivity.PRESCRIBE:
			if (params[1] != null) {
				String value = params[1].toString().trim();
			}
			break;
		}
	}

	private void parseItemsList(String mstr) {
		// itemsInfos.clear();
		// List<UnideInfo> list=new ArrayList<UnideInfo>();
		try {
			// JSONArray jsonArray=new JSONArray(mtStr);
			// for(int i=0;i<itemsInfo.toString().length();i++){
			// JSONObject object = jsonArray.getJSONObject(i);
			ItemsInfo info = new ItemsInfo();
			info.setItemId(itemsInfo.getItemId());
			info.setItemName(itemsInfo.getItemName());
			info.setInputCode(itemsInfo.getInputCode());
			info.setInputCode1(itemsInfo.getInputCode1());
			info.setInputCode2(itemsInfo.getInputCode2());
			info.setItemOutpSpec(itemsInfo.getItemOutpSpec());
			info.setItemOutpUnits(itemsInfo.getItemOutpUnits());
			info.setItemBasicUnits(itemsInfo.getItemBasicUnits());
			info.setItemPrice1(itemsInfo.getItemPrice1());
			info.setItemPropertyIndicator(itemsInfo.getItemPropertyIndicator());
			info.setItemPropertyName(itemsInfo.getItemPropertyIndicator());
			info.setDrugOrItemIndicator(itemsInfo.getDrugOrItemIndicator());
			info.setDrugOrItemIndicatorName(itemsInfo
					.getDrugOrItemIndicatorName());
			info.setQuantity(itemsInfo.getQuantity());
			info.setItemClass(itemsInfo.getItemClass());
			info.setItemClassName(itemsInfo.getItemClassName());
			info.setCdname(itemsInfo.getCdname());
			info.setItemtradename(itemsInfo.getItemtradename());
			// if(itemsInfo2==null||itemsInfo2.equals("")){
			info.setNum(num);
			info.setFreque(freque);
			info.setUsage(usage);
			info.setMuch(much);

			/*
			 * }else{ info.setNum(itemsInfo.getNum());
			 * info.setFreque(itemsInfo.getFreque());
			 * info.setUsage(itemsInfo.getUsage());
			 * info.setMuch(itemsInfo.getMuch()); itemsInfo2=null; }
			 */

			itemsInfos.add(info);

			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		adapter = new PrescriptionAdapter(PrescriptionActivity.this, itemsInfos);
		listView.setAdapter(adapter);
		sumje();
	}
//计算金额
	public void sumje() {
		double hj = 0.00, yp = 0.00, fy = 0.00;
		if (itemsInfos.size() == 0) {
			tv_price1.setText("0.00");
			tv_price2.setText("0.00");
			tv_price3.setText("0.00");
		}
		for (int j = 0; j < itemsInfos.size(); j++) {

			ItemsInfo item = itemsInfos.get(j);
//			double je = Double.parseDouble(item.getItemPrice())
//					* Double.parseDouble(item.getNum());
			double je = item.getItemPrice1()
					* Double.parseDouble(item.getNum());
			hj += je;
			if (item.getDrugOrItemIndicatorName().equals("药品类")) {
				yp += je;
			} else {
				fy += je;
			}
			DecimalFormat format = new DecimalFormat("0.00");
			String a = format.format(new BigDecimal(yp));
			tv_price1.setText(a);
			String b = format.format(new BigDecimal(fy));
			tv_price2.setText(b);
			String c = format.format(new BigDecimal(hj));
			tv_price3.setText(c);

		}

	}

	private void showDialog() {
		dialog = new ProgressDialog(context);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("正在查询，请稍等...");
		dialog.show();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// for (int i = 0; i < itemsInfos.size(); i++) {

			// Intent intent=new Intent();
			// itemsInfo = itemsInfos.get(i);
			// intent.putExtra("itemsInfos", itemsInfos.toArray());
			// DiagnosisActivity.list111= itemsInfos;

			// intent.putExtra("itemsInfos1122", iil);
			// intent.putExtra("size", itemsInfos.size());
			/*
			 * intent.putExtra("frequ", freque); intent.putExtra("usage",
			 * usage); intent.putExtra("much",much); intent.putExtra("num",
			 * num);
			 */
			app.setItemsinfo(itemsInfos);
			// intent.setAction("com.example.service.MainService2");
			// sendBroadcast(intent);
			// Toast.makeText(getApplicationContext(), itemsInfo.getNum(),
			// Toast.LENGTH_LONG).show();
			// }

		}

		return super.onKeyDown(keyCode, event);
	}
	
	
	@Override
	protected void onDestroy() {
		PrescriptionActivity.this.unregisterReceiver(receiver);
		super.onDestroy();
	}
}

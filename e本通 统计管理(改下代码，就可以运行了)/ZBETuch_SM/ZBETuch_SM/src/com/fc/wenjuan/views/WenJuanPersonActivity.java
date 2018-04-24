package com.fc.wenjuan.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView2;
import com.fc.main.beans.PullDownView2.OnPullDownListener;
import com.fc.main.myservices.PersonService;
import com.fc.person.beans.PersonTask;
import com.fc.wenjuan.beans.WenJuanPersonAdapter;
import com.fc.wenjuan.beans.WenJuanPersonInfo;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class WenJuanPersonActivity extends Activity implements IActivity,
		OnPullDownListener {
	private ListView personquery_listview;
	private PullDownView2 mPullDownView;
	private List<WenJuanPersonInfo> wenJuanPersonInfos = new ArrayList<WenJuanPersonInfo>();
	private WenJuanPersonAdapter personAdapter;
	private TextView lblNum;

	private int index = 0;

	private Context context = this;

	private RadioGroup rg;
	private RadioButton rb1, rb2;

	private Map<String, String> data;

	public static final int REFRESH_INFO = 0;

	public static final int REFRESH_PERSION_INFO = 1;

	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wenjuan_person);
		init();
		initviews();
		initPulldownattr();
		rb1.setChecked(true);
	}

	private void initviews() {
		mPullDownView = (PullDownView2) findViewById(R.id.pull_down_view);
		personquery_listview = mPullDownView.getListView();
		personAdapter = new WenJuanPersonAdapter(wenJuanPersonInfos, context);
		personquery_listview.setAdapter(personAdapter);
		mPullDownView.setOnPullDownListener(this);
		personquery_listview
				.setOnItemClickListener(new MyOnItemClickListener());
		lblNum = (TextView) findViewById(R.id.lblNum);

		rg = (RadioGroup) this.findViewById(R.id.my_group);
		rb1 = (RadioButton) this.findViewById(R.id.btn_weicha_rg);
		rb2 = (RadioButton) this.findViewById(R.id.btn_yicha_rg);
		rg.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
	}

	private void initPulldownattr() {
		mPullDownView.setShowFooter();
		mPullDownView.setHideHeader();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case REFRESH_INFO:
			if (dialog != null && dialog.isShowing()) {
				dialog.dismiss();
			}
			if (!"".equals(params[1].toString().trim())
					|| null != params[1].toString().trim()) {
				List<WenJuanPersonInfo> newJuanPersonInfos = parseWenjuanPersonInfo(params[1]
						.toString().trim());
				if (newJuanPersonInfos.size() > 0) {
					wenJuanPersonInfos.addAll(newJuanPersonInfos);
					lblNum.setText("共有"
							+ newJuanPersonInfos.get(0).getRecordCount() + "人");
				} else {
					lblNum.setText("共有0人");
				}
				personAdapter.notifyDataSetChanged();
				mPullDownView.notifyDidMore();

			}
			break;

		case REFRESH_PERSION_INFO:
			if ("True".equals(params[1].toString().trim())) {
				Toast.makeText(context, "提交完成!", Toast.LENGTH_LONG).show();
				int potion = (Integer) params[2];
				if (wenJuanPersonInfos.size() > 0) {
					wenJuanPersonInfos.remove(potion - 1);
				}
				personAdapter.notifyDataSetChanged();
				mPullDownView.notifyDidMore();
			}
			break;

		default:
			break;
		}
	}

	@Override
	public void onMore() {
		// TODO Auto-generated method stub
		index++;
		getPageInfo(index);
	}

	private void getPageInfo(int index) {
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("master_id", getIntent().getIntExtra("id", 1) + "");
		data.put("page", index + "");
		data.put("rows", "15");
		params.put("data", data);
		PersonTask task = new PersonTask(
				PersonTask.WENJUANACTIVITY_GET_WENJUANPERSON, params);
		PersonService.newTask(task);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			if (rb1.isChecked()) {
				Intent intent = new Intent(context, ShowPersionDetailInfo.class);
				intent.putExtra("info",
						(Serializable) wenJuanPersonInfos.get(arg2 - 1));
				intent.putExtra("rb", true);
				intent.putExtra("position", arg2);
				startActivity(intent);
			} else if (rb2.isChecked()) {
				if ("".equals(wenJuanPersonInfos.get(arg2 - 1).getMARK())) {
					Intent showIntent = new Intent(context,
							WenJuanDetailActivity.class);
					showIntent.putExtra("pid", wenJuanPersonInfos.get(arg2 - 1)
							.getID());
					showIntent.putExtra("NO", wenJuanPersonInfos.get(arg2 - 1)
							.getNO());
					if (rb1.isChecked()) {
						showIntent.putExtra("rb", true);
					} else if (rb2.isChecked()) {
						showIntent.putExtra("rb", false);
					}
					startActivity(showIntent);
				} else {
					Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("无法调查原因").setMessage(
							wenJuanPersonInfos.get(arg2 - 1).getMARK());
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							}).create().show();
				}

			}

		}

	}

	private List<WenJuanPersonInfo> parseWenjuanPersonInfo(String str) {
		List<WenJuanPersonInfo> juanPersonInfos = new ArrayList<WenJuanPersonInfo>();

		try {
			JSONArray array = new JSONArray(str);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = (JSONObject) array.get(i);
				WenJuanPersonInfo personInfo = new WenJuanPersonInfo();
				personInfo.setCREATE_DATE(jsonObject.getString("CREATE_DATE"));
				personInfo.setCREATE_STAFF(jsonObject.getInt("CREATE_STAFF"));
				personInfo.setDZ(jsonObject.getString("DZ"));
				personInfo.setEDU(jsonObject.getString("EDU"));
				personInfo.setID(jsonObject.getInt("ID"));
				personInfo.setJD(jsonObject.getString("JD"));
				personInfo.setJW(jsonObject.getString("JW"));
				personInfo.setLXDZ(jsonObject.getString("LXDZ"));
				personInfo.setMARK(jsonObject.getString("MARK"));
				personInfo.setNAME(jsonObject.getString("NAME"));
				personInfo.setNO(jsonObject.getString("NO"));
				personInfo.setPHONE(jsonObject.getString("PHONE"));
				personInfo.setPID(jsonObject.getString("PID"));
				personInfo.setQA_MASTER(jsonObject.getInt("QA_MASTER"));
				personInfo.setQX(jsonObject.getString("QX"));
				personInfo.setRECEIVED_ID(jsonObject.getInt("RECEIVED"));
				personInfo.setRecordCount(jsonObject.getInt("RecordCount"));
				personInfo.setSEX(jsonObject.getString("SEX"));
				personInfo.setSFZ(jsonObject.getString("SFZ"));
				personInfo.setZT(jsonObject.getString("ZT"));
				juanPersonInfos.add(personInfo);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return juanPersonInfos;
	}

	private class MyOnCheckedChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			index = 0;
			wenJuanPersonInfos.clear();
			personAdapter.notifyDataSetChanged();
			mPullDownView.notifyDidMore();
			lblNum.setText(" ");
			showDialog();
			if (arg1 == R.id.btn_weicha_rg) {
				data = new HashMap<String, String>();
				data.put("type", "1");
			} else if (arg1 == R.id.btn_yicha_rg) {
				data = new HashMap<String, String>();
				data.put("type", "0");
			}
			getPageInfo(index);
		}

	}

	private void showDialog() {
		dialog = new ProgressDialog(context);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("数据信息加载中...");
		dialog.show();
	}

}

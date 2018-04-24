package com.fc.person.views;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fc.main.myservices.PersonService;
import com.fc.main.tools.HttpUrls_;
import com.fc.person.beans.PersonMark;
import com.fc.person.beans.PersonMarkAdapter;
import com.fc.person.beans.PersonTask;
import com.fc.person.beans.PersonalBaseInformation;
import com.fc.zbetuch_sm.R;

public class ModifyPersonMarkActivity extends Activity {
	private Spinner sp_personmark;
	private Button btn_addmark, btn_markok, btn_markno;
	private ArrayList<PersonMark> personmark;
	private ListView listview_mark;
	private PersonMarkAdapter mark_adapter;
	private String[] array;
	private String markname;
	private static String personmarksource = "现场采集";
	private int flag = 0;
	private String personSFZ;
	private String postMarJson;
	private ProgressDialog progressDialog;
	private ArrayList<PersonalBaseInformation> personinfoJson = new ArrayList<PersonalBaseInformation>();

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_modifypersonmark);
		Bundle bundle = getIntent().getExtras();
		personmark = (ArrayList<PersonMark>) bundle
				.getSerializable("personmarklist");
		personSFZ = bundle.getString("sfz");
		personinfoJson = (ArrayList<PersonalBaseInformation>) bundle
				.getSerializable("personinfojson");
		initViews();
		initSpinnerData();
		initListener();
		mark_adapter = new PersonMarkAdapter(personmark,
				ModifyPersonMarkActivity.this);
		listview_mark.setAdapter(mark_adapter);
	}

	private void initSpinnerData() {
		array = getResources().getStringArray(R.array.personmark);
		ArrayAdapter<String> sp_markadapter = new ArrayAdapter<String>(
				ModifyPersonMarkActivity.this,
				android.R.layout.simple_spinner_item, array);
		sp_markadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_personmark.setAdapter(sp_markadapter);
	}

	private void initListener() {
		btn_addmark.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (markname.equals("请选择")) {
					Toast.makeText(ModifyPersonMarkActivity.this, "请选择标识类型。。。",
							300).show();
				} else {
					if (!judmentPersonmark(personmark, markname)) {
						if(personmark.size()==1 && personmark.get(0).getPersonMarkName()==null){
							personmark.clear();
						}
						
						PersonMark mark = new PersonMark();
						mark.setPersonMarkName(markname);
						mark.setPersonMarkSoure(personmarksource);
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm");
						String markdate = sdf.format(new Date());
						mark.setPersonMarkCreatdate(markdate);
						mark.setPersonSFZ(personSFZ);
						Log.i("shijian", markdate);
						personmark.add(mark);
						mark_adapter.notifyDataSetChanged();
					}
				}
			}
		});
		sp_personmark.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				markname = array[position];
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		btn_markok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showpersonupdateDialog("修改标识提示", "您确认修改的标识上传服务器？");
			}
		});
		btn_markno.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				Intent it = new Intent(ModifyPersonMarkActivity.this,
//						ModifyPersonInfoActivity.class);
//				it.putExtra("sfz", personSFZ);
//				it.putExtra("personbasejson", personinfoJson);
//				startActivity(it);
//				ModifyPersonMarkActivity.this.finish();
//				//onBackPressed();
				finish();

			}
		});


	}

	Runnable thread_setmark = new Runnable() {

		@Override
		public void run() {
			postMarJson = HttpUrls_.postMarJson(personmark);
			Message msg_markpost = new Message();
			msg_markpost.what = 0x330;
			msg_markpost.obj = postMarJson;
			handler_setmark.obtainMessage(0x330, postMarJson);
			handler_setmark.sendMessage(msg_markpost);
		}
	};
	private Handler handler_setmark = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.dismiss();
				progressDialog = null;
			}
			System.out.println("postMarJson====>" + postMarJson);
			if (postMarJson.equals("True")) {
				
				
				
				Toast.makeText(ModifyPersonMarkActivity.this, "上传成功！",
						Toast.LENGTH_SHORT).show();
				Map<String, Object>params2 = new HashMap<String, Object>();
				Map<String, String> data = new HashMap<String, String>();
				data.put("sfz", personSFZ);
				params2.put("data", data);
				PersonTask task2 = new PersonTask(PersonTask.MODIFYPERSONINFOACTIVITY_GET_PERSONMARK, params2);
				PersonService.newTask(task2);
				
				finish();
				
			} else {
				Toast.makeText(ModifyPersonMarkActivity.this, "上传失败！",
						Toast.LENGTH_SHORT).show();
			}
		}

	};

	private void initViews() {
		sp_personmark = (Spinner) findViewById(R.id.spinner_personmark);
		btn_addmark = (Button) findViewById(R.id.button_addmark);
		listview_mark = (ListView) findViewById(R.id.list_personmark);
		btn_markok = (Button) findViewById(R.id.button_markok);
		btn_markno = (Button) findViewById(R.id.button_markno);

	}

	private void showpersonupdateDialog(String title, String msg) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title).setMessage(msg)
				.setIcon(android.R.drawable.ic_dialog_info).setCancelable(true)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						showBaseUpdialog(ModifyPersonMarkActivity.this);
						if (personmark.size() == 0) {
							PersonMark mark = new PersonMark();
							mark.setPersonMarkId("-1");
							mark.setPersonSFZ(personSFZ);
							personmark.add(mark);
						}
						new Thread(thread_setmark).start();

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

	/**
	 * 上传提示框
	 * 
	 * @param context
	 */
	public void showBaseUpdialog(Context context) {
		progressDialog = new ProgressDialog(context);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("上传提示");
		progressDialog.setMessage("修改信息上传中，请稍后。。。");
		progressDialog.show();
	}

	public boolean judmentPersonmark(ArrayList<PersonMark> personmark,
			String markname) {
		for (int i = 0; i < personmark.size(); i++) {
			String personMarkName = personmark.get(i).getPersonMarkName();
			if (personMarkName.equals(markname)) {
				Toast.makeText(ModifyPersonMarkActivity.this,
						"对不起，不能重复添加标识,请重新选择。。。", Toast.LENGTH_SHORT).show();
				return true;
			}
		}
		return false;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("Ckaishi", "yessss");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("Czhanting", "yessss");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.i("Ctingzhi", "yessss");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.i("Cxiaohui", "yessss");
	}

}

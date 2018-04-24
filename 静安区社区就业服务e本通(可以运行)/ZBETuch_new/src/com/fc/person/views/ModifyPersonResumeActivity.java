package com.fc.person.views;

import java.util.ArrayList;
import java.util.List;

import com.fc.main.tools.HttpUrls_;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.PersonResume;
import com.fc.person.beans.Zihangye;
import com.fc.person.beans.hangye;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * 个人简历编辑修改
 * 
 * @author Administrator
 * 
 */
public class ModifyPersonResumeActivity extends Activity implements
		OnItemSelectedListener, OnClickListener {
	private Spinner sp_computerSkills, sp_language1, sp_chengdu1, sp_language2,
			sp_chengdu2, sp_xinzi, sp_workxingzhi, sp_worktime,
			sp_workaddress1, sp_workaddress2, sp_workaddress3, sp_fugangwei1,
			sp_fugangwei2, sp_zigangwei1, sp_zigangwei2;
	private EditText et_computerzhengshu, et_languagezhengshu,
			et_otherzhengshu, et_selfpingjia, et_othergangwei, et_workyear;
	private CheckBox ck_istuijian;
	private Button btn_tijiaojianli;
	private String[] chendu, languang, workxingzi, worktime, xingzi,
			workaddress;
	private ProgressDialog progressDialog;
	private List<hangye> list;
	private List<String> list_zihangye;
	private String code;
	private String personSFZ;
	private String workyear;
	private ArrayAdapter<String> adapter_zihangye;
	private String resumeJson;
	private ArrayList<PersonResume> personresumeJson;
	@SuppressWarnings("unused")
	private String computerSkills, computerzhengshu, language1, chendu1,
			language2, chendu2, languagezhengshu, otherzhengshu, ziwopingjia,
			xinzi, workxingz, workshijian, workdidian1, workdidian2,
			workdidian3, fugangwei1, fugangwei2, zigangwei1, zigangwei2,
			othergangwei, checktuijian, xinzilow, xinziup;
	private ArrayList<PersonResume> list_resume;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_personresume);
		Bundle bundle = getIntent().getExtras();
		personSFZ = bundle.getString("sfz");

		initViews();
		initData();
		new Thread(thread_getResume).start();

		initListener();

	}

	private void initPersonResume() {
		for (int i = 0; i < personresumeJson.size(); i++) {
			et_computerzhengshu.setText(personresumeJson.get(i)
					.getPersonComputerZhengshu());
			et_languagezhengshu.setText(personresumeJson.get(i)
					.getPersonForeignZhengshu());
			et_otherzhengshu.setText(personresumeJson.get(i)
					.getPersonQitazhengshu());
			et_selfpingjia.setText(personresumeJson.get(i)
					.getPersonAssessment());
			et_othergangwei.setText(personresumeJson.get(i).getPersonGanwei());
			String personComputerSkills = personresumeJson.get(i)
					.getPersonComputerSkills();
			sp_computerSkills.setSelection(MainTools.getSpinnerIndex(
					personComputerSkills, chendu));
			String personForeignLanguages1 = personresumeJson.get(i)
					.getPersonForeignLanguages1();
			sp_language1.setSelection(MainTools.getSpinnerIndex(
					personForeignLanguages1, languang));
			String personForeignLanguages2 = personresumeJson.get(i)
					.getPersonForeignLanguages2();
			sp_language2.setSelection(MainTools.getSpinnerIndex(
					personForeignLanguages2, languang));
			String personFuGangwei1 = personresumeJson.get(i)
					.getPersonFuGangwei1();
			sp_fugangwei1.setSelection(MainTools.getSpinnerhangyeIndex(
					personFuGangwei1, list));
			String personFuGangwei2 = personresumeJson.get(i)
					.getPersonFuGangwei2();
			sp_fugangwei2.setSelection(MainTools.getSpinnerhangyeIndex(
					personFuGangwei2, list));
			String personNatureEmployment = personresumeJson.get(i)
					.getPersonNatureEmployment();
			sp_workxingzhi.setSelection(MainTools.getSpinnerIndex(
					personNatureEmployment, workxingzi));
			String personShunian1 = personresumeJson.get(i).getPersonShunian1();
			sp_chengdu1.setSelection(MainTools.getSpinnerIndex(personShunian1,
					chendu));
			String personShunian2 = personresumeJson.get(i).getPersonShunian2();
			sp_chengdu2.setSelection(MainTools.getSpinnerIndex(personShunian2,
					chendu));
			String personWorkaddress1 = personresumeJson.get(i)
					.getPersonWorkaddress1();
			sp_workaddress1.setSelection(MainTools.getSpinnerIndex(
					personWorkaddress1, workaddress));
			String personWorkaddress2 = personresumeJson.get(i)
					.getPersonWorkaddress2();
			sp_workaddress2.setSelection(MainTools.getSpinnerIndex(
					personWorkaddress2, workaddress));
			String personWorkaddress3 = personresumeJson.get(i)
					.getPersonWorkaddress3();
			sp_workaddress3.setSelection(MainTools.getSpinnerIndex(
					personWorkaddress3, workaddress));
			String personWorktime = personresumeJson.get(i).getPersonWorktime();
			sp_worktime.setSelection(MainTools.getSpinnerIndex(personWorktime,
					worktime));
			String personExpectedSalarylow = personresumeJson.get(i)
					.getPersonExpectedSalarylow();
			String personExpectedSalaryUp = personresumeJson.get(i)
					.getPersonExpectedSalaryUp();
			Log.i("di././", personExpectedSalarylow);
			Log.i("gao././", personExpectedSalaryUp);
			if (personExpectedSalaryUp.equals("-1.0")) {
				String personxinzi = personExpectedSalarylow.substring(0,
						personExpectedSalarylow.indexOf(".")) + "以上";
				sp_xinzi.setSelection(MainTools.getSpinnerIndex(personxinzi,
						xingzi));
			} else {
				String personxinzi = personExpectedSalarylow.substring(0,
						personExpectedSalarylow.indexOf("."))
						+ "-"
						+ personExpectedSalaryUp.substring(
								personExpectedSalaryUp.indexOf("-") + 1,
								personExpectedSalaryUp.indexOf("."));
				Log.i("xinxzi......lll", personxinzi);
				sp_xinzi.setSelection(MainTools.getSpinnerIndex(personxinzi,
						xingzi));
			}
		}

	}

	/**
	 * 获取控件中的值
	 */
	private void getResumedata() {
		computerzhengshu = et_computerzhengshu.getText().toString().trim();
		languagezhengshu = et_languagezhengshu.getText().toString().trim();
		otherzhengshu = et_otherzhengshu.getText().toString().trim();
		ziwopingjia = et_selfpingjia.getText().toString().trim();
		othergangwei = et_othergangwei.getText().toString().trim();
		workyear = et_workyear.getText().toString().trim();
	}

	/**
	 * 设置监听事件
	 */
	private void initListener() {
		sp_fugangwei1.setOnItemSelectedListener(this);
		sp_fugangwei2.setOnItemSelectedListener(this);
		sp_computerSkills.setOnItemSelectedListener(this);
		sp_language1.setOnItemSelectedListener(this);
		sp_language2.setOnItemSelectedListener(this);
		sp_chengdu1.setOnItemSelectedListener(this);
		sp_chengdu2.setOnItemSelectedListener(this);
		sp_workaddress1.setOnItemSelectedListener(this);
		sp_workaddress2.setOnItemSelectedListener(this);
		sp_workaddress3.setOnItemSelectedListener(this);
		sp_worktime.setOnItemSelectedListener(this);
		sp_workxingzhi.setOnItemSelectedListener(this);
		sp_xinzi.setOnItemSelectedListener(this);
		sp_zigangwei1.setOnItemSelectedListener(this);
		sp_zigangwei2.setOnItemSelectedListener(this);
		btn_tijiaojianli.setOnClickListener(this);

	}

	private void initData() {
		// 能力
		chendu = getResources().getStringArray(R.array.personSkills);
		ArrayAdapter<String> adapter_chendu = new ArrayAdapter<String>(
				ModifyPersonResumeActivity.this,
				android.R.layout.simple_spinner_item, chendu);
		adapter_chendu
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_computerSkills.setAdapter(adapter_chendu);
		sp_chengdu1.setAdapter(adapter_chendu);
		sp_chengdu2.setAdapter(adapter_chendu);
		// 语种
		languang = getResources().getStringArray(R.array.personLanguage);
		ArrayAdapter<String> adapter_language = new ArrayAdapter<String>(
				ModifyPersonResumeActivity.this,
				android.R.layout.simple_spinner_item, languang);
		adapter_language
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_language1.setAdapter(adapter_language);
		sp_language2.setAdapter(adapter_language);
		// 薪资
		xingzi = getResources().getStringArray(R.array.personxinzi);
		ArrayAdapter<String> adapter_xinzi = new ArrayAdapter<String>(
				ModifyPersonResumeActivity.this,
				android.R.layout.simple_spinner_item, xingzi);
		adapter_xinzi
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_xinzi.setAdapter(adapter_xinzi);
		// 工作性质
		workxingzi = getResources()
				.getStringArray(R.array.personyonggongxingzi);
		ArrayAdapter<String> adapter_workxingzi = new ArrayAdapter<String>(
				ModifyPersonResumeActivity.this,
				android.R.layout.simple_spinner_item, workxingzi);
		adapter_workxingzi
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_workxingzhi.setAdapter(adapter_workxingzi);
		// 工作时间
		worktime = getResources().getStringArray(R.array.personworktime);
		ArrayAdapter<String> adapter_worktime = new ArrayAdapter<String>(
				ModifyPersonResumeActivity.this,
				android.R.layout.simple_spinner_item, worktime);
		adapter_worktime
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_worktime.setAdapter(adapter_worktime);
		// 工作地点
		workaddress = getResources().getStringArray(R.array.personaddress);
		ArrayAdapter<String> adapter_workaddress = new ArrayAdapter<String>(
				ModifyPersonResumeActivity.this,
				android.R.layout.simple_spinner_item, workaddress);
		adapter_workaddress
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_workaddress1.setAdapter(adapter_workaddress);
		sp_workaddress2.setAdapter(adapter_workaddress);
		sp_workaddress3.setAdapter(adapter_workaddress);
		// 岗位
		hangye hy = new hangye();
		list = hy.GetAll();
		ArrayAdapter<hangye> adapter_hangye = new ArrayAdapter<hangye>(
				ModifyPersonResumeActivity.this,
				android.R.layout.simple_spinner_item, list);
		adapter_hangye
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_fugangwei1.setAdapter(adapter_hangye);
		sp_fugangwei2.setAdapter(adapter_hangye);
	}

	private void initViews() {
		sp_computerSkills = (Spinner) findViewById(R.id.sp_computerSkills);
		sp_language1 = (Spinner) findViewById(R.id.sp_language1);
		sp_language2 = (Spinner) findViewById(R.id.sp_language2);
		sp_chengdu1 = (Spinner) findViewById(R.id.sp_chendu1);
		sp_chengdu2 = (Spinner) findViewById(R.id.sp_chendu2);
		sp_fugangwei1 = (Spinner) findViewById(R.id.sp_fugangwei1);
		sp_fugangwei2 = (Spinner) findViewById(R.id.sp_fugangwei2);
		sp_workaddress1 = (Spinner) findViewById(R.id.sp_workadress1);
		sp_workaddress2 = (Spinner) findViewById(R.id.sp_workadress2);
		sp_workaddress3 = (Spinner) findViewById(R.id.sp_workadress3);
		sp_worktime = (Spinner) findViewById(R.id.sp_worktime);
		sp_workxingzhi = (Spinner) findViewById(R.id.sp_workxingzi);
		sp_xinzi = (Spinner) findViewById(R.id.sp_xingzi);
		sp_zigangwei1 = (Spinner) findViewById(R.id.sp_zigangwei1);
		sp_zigangwei2 = (Spinner) findViewById(R.id.sp_zigangwei2);
		et_computerzhengshu = (EditText) findViewById(R.id.et_computerzhengshu);
		et_languagezhengshu = (EditText) findViewById(R.id.et_languagezhengshu);
		et_otherzhengshu = (EditText) findViewById(R.id.et_otherzhengshu);
		et_selfpingjia = (EditText) findViewById(R.id.et_ziwopingjia);
		et_othergangwei = (EditText) findViewById(R.id.et_othergangwei);
		et_workyear = (EditText) findViewById(R.id.et_workyear);
		btn_tijiaojianli = (Button) findViewById(R.id.btn_tijiaojianli);
	}

	/**
	 * 以父岗位的code获取子岗位的信息
	 * 
	 * @param position
	 *            spinner的某一项
	 */
	public void getZigangwei(int position) {
		code = list.get(position).getCode();
		Zihangye zhy = new Zihangye();
		List<Zihangye> list = zhy.findAll();
		list_zihangye = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getHangyeCode().equals(code)) {
				list_zihangye.add(list.get(i).getZihangename());
			}
		}
		adapter_zihangye = new ArrayAdapter<String>(
				ModifyPersonResumeActivity.this,
				android.R.layout.simple_spinner_item, list_zihangye);
		adapter_zihangye
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}

	/**
	 * spinner的监听事件
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i("ggggggh....", view.getId() + "");
		switch (parent.getId()) {
		case R.id.sp_computerSkills:
			computerSkills = getResources()
					.getStringArray(R.array.personSkills)[position];
			break;
		case R.id.sp_language1:
			Log.i("hhhhhhh....", "ccchhhjjjkk");
			language1 = getResources().getStringArray(R.array.personLanguage)[position];
			Log.i("yuz1....", language1);
			break;
		case R.id.sp_language2:
			language2 = getResources().getStringArray(R.array.personLanguage)[position];
			Log.i("yuz2....", language2);
			break;
		case R.id.sp_fugangwei1:
			getZigangwei(position);
			sp_zigangwei1.setAdapter(adapter_zihangye);
			fugangwei1 = list.get(position).getName();
			Log.i("fgw1....", fugangwei1);
			break;
		case R.id.sp_fugangwei2:
			getZigangwei(position);
			sp_zigangwei2.setAdapter(adapter_zihangye);
			fugangwei2 = list.get(position).getName();
			if (fugangwei2.equals("请选择 ")) {
				fugangwei2 = "";
			}
			Log.i("fgw2....", fugangwei2);
			break;
		case R.id.sp_xingzi:
			xinzi = getResources().getStringArray(R.array.personxinzi)[position];
			if (xinzi.equals("请选择")) {
				Toast.makeText(ModifyPersonResumeActivity.this, "请选择薪资",
						Toast.LENGTH_SHORT).show();
			} else if (xinzi.equals("20001以上")) {
				xinzilow = "20001";
				xinziup = "-1";
			} else {
				xinzilow = xinzi.substring(0, xinzi.indexOf("-")).trim();
				xinziup = xinzi.substring(xinzi.indexOf("-") + 1);
				Log.i("shangxian", xinziup);
			}
			break;
		case R.id.sp_chendu1:
			chendu1 = getResources().getStringArray(R.array.personSkills)[position];
			break;
		case R.id.sp_chendu2:
			chendu2 = getResources().getStringArray(R.array.personSkills)[position];
			break;
		case R.id.sp_workadress1:
			workdidian1 = getResources().getStringArray(R.array.personaddress)[position];
			break;
		case R.id.sp_workadress2:
			workdidian2 = getResources().getStringArray(R.array.personaddress)[position];
			break;
		case R.id.sp_workadress3:
			workdidian3 = getResources().getStringArray(R.array.personaddress)[position];
			break;
		case R.id.sp_worktime:
			workshijian = getResources().getStringArray(R.array.personworktime)[position];
			Log.i("yuz1....", workshijian);
			break;
		case R.id.sp_workxingzi:
			workxingz = getResources().getStringArray(
					R.array.personyonggongxingzi)[position];
			break;
		case R.id.sp_zigangwei1:
			zigangwei1 = list_zihangye.get(position);
			Log.i("yuz1....", zigangwei1);
			break;
		case R.id.sp_zigangwei2:
			zigangwei2 = list_zihangye.get(position);
			Log.i("yuz2....", zigangwei2);
			if (zigangwei1.equals("请选择")) {
				zigangwei1 = "";
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	/**
	 * 提交按钮的监听事件
	 * 
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_tijiaojianli:
			getResumedata();
			getResumeListdata();
			showpersonupdateDialog("上传信息提示", "您确定上传简历信息吗？");
			break;

		default:
			break;
		}

	}

	private void showpersonupdateDialog(String title, String msg) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle(title).setMessage(msg)
				.setIcon(android.R.drawable.ic_dialog_info).setCancelable(true)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						showBaseUpdialog(ModifyPersonResumeActivity.this);
						new Thread(thread_Resume).start();

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

	/*
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

	public Handler handler_resume = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x1110:
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
					progressDialog = null;
				}
				Log.i("fanhuixinxi./././", resumeJson);
				if (resumeJson.equals("True")) {
					Toast.makeText(ModifyPersonResumeActivity.this, "上传成功！",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(ModifyPersonResumeActivity.this,
							"上传失败！请联系服务器管理员", Toast.LENGTH_SHORT).show();
				}
				break;
			case 0x2220:
				if (personresumeJson.size() == 0) {

				} else {
					initPersonResume();
				}
			default:
				break;
			}
		}

	};
	Runnable thread_getResume = new Runnable() {

		@Override
		public void run() {
			personresumeJson = HttpUrls_.getPersonResumeJson(
					ModifyPersonResumeActivity.this, personSFZ);
			Message msg_getresume = new Message();
			msg_getresume.what = 0x2220;
			msg_getresume.obj = personresumeJson;
			handler_resume.obtainMessage(0x2220, personresumeJson);
			handler_resume.sendMessage(msg_getresume);
		}
	};
	Runnable thread_Resume = new Runnable() {

		@Override
		public void run() {
			resumeJson = HttpUrls_.postResumeJson(list_resume);
			Message msg_ressume = new Message();
			msg_ressume.what = 0x1110;
			msg_ressume.obj = resumeJson;
			handler_resume.obtainMessage(0x1110, resumeJson);
			handler_resume.sendMessage(msg_ressume);
		}
	};

	private void getResumeListdata() {
		list_resume = new ArrayList<PersonResume>();
		PersonResume personResume = new PersonResume();
		personResume.setPersonComputerSkills(computerSkills);
		personResume.setPersonComputerZhengshu(computerzhengshu);
		personResume.setPersonForeignLanguages1(language1);
		personResume.setPersonForeignLanguages2(language2);
		personResume.setPersonShunian1(chendu1);
		personResume.setPersonShunian2(chendu2);
		personResume.setPersonWorkaddress1(workdidian1);
		personResume.setPersonWorkaddress2(workdidian2);
		personResume.setPersonWorkaddress3(workdidian3);
		personResume.setPersonFuGangwei1(fugangwei1);
		personResume.setPersonFuGangwei2(fugangwei2);
		personResume.setPersonWorktime(workshijian);
		personResume.setPersonworkyears(workyear);
		personResume.setPersonExpectedSalarylow(xinzilow);
		personResume.setPersonExpectedSalaryUp(xinziup);
		personResume.setPersonGanwei(othergangwei);
		personResume.setPersonQitazhengshu(otherzhengshu);
		personResume.setPersonNatureEmployment(workxingz);
		personResume.setPersonZigangwei1(zigangwei1);
		personResume.setPersonZigangwei2(zigangwei2);
		personResume.setPersonAssessment(ziwopingjia);
		personResume.setPersonSFZ(personSFZ);
		personResume.setPersonForeignZhengshu(languagezhengshu);
		list_resume.add(personResume);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		Log.i("haizai meizai ././.", PersoninfoMainActivity.class.getName());
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent modifyit = new Intent(ModifyPersonResumeActivity.this,
					PersoninfoMainActivity.class);
			modifyit.putExtra("mysfz", personSFZ);
			startActivity(modifyit);
			ModifyPersonResumeActivity.this.finish();
		}
		return false;
	}

}

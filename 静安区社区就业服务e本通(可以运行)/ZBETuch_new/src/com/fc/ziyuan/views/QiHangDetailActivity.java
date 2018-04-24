package com.fc.ziyuan.views;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fc.main.beans.IActivity;
import com.fc.main.service.MainService;
import com.fc.main.service.PersonService;
import com.fc.person.beans.PersonTask;
import com.fc.ziyuan.bean.QiHangBeanInfo;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class QiHangDetailActivity extends Activity implements IActivity {

	private QiHangBeanInfo infos;

	private EditText idcard, name, carture, adress, street, juwei, status,
			data, current_yixiang, qh_mqzk, qh_dqyx, qh_qwyx;
	private Button shideng_btn, diaocha_btn, traintypebtn, jobtypebtn,
			xuqiubtn, qihang_result_tall, no_dc_btn;
	private EditText diaocha_data, diaocha_remark, xuqiu, jobtype, traintype,
			diaocha_person, etfwxq1, etfwxq2, etfwxq3, etgzlx1, etgzlx2,
			etgzlx3, etpxlx1, etpxlx2, etpxlx3;
	private Spinner status_spinner, current_spinner, price_onem, jobyesno;
	private LinearLayout infoLayout, lay1, lay2, fuwulay, joblay, peixunlay,
			tableRow5, tableRow6, tableRow7, tableRow8;
	private SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	ArrayAdapter<String> priceAdapter;
	ArrayAdapter<String> yesAdapter;

	public static final int REFRESH_INFO = 0;
	private boolean isSave = false;
	private Button xiangxi_btn;
	private int undo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_qihang_detail);

		infos = (QiHangBeanInfo) getIntent().getSerializableExtra("beanInfos");
		init();
		initView();
		initValue();
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
			if (!"".equals(params[1].toString().trim())
					|| params[1].toString().trim() != null) {
				if ("True".equals(params[1].toString().trim())) {
					isSave = true;
					Toast.makeText(QiHangDetailActivity.this, "上传成功！",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(QiHangDetailActivity.this, "上传失败！",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		default:
			break;
		}

	}

	private void initView() {
		tableRow5 = (LinearLayout) findViewById(R.id.tableRow5);
		idcard = (EditText) this.findViewById(R.id.qh_idcard);
		name = (EditText) this.findViewById(R.id.qh_name);
		carture = (EditText) this.findViewById(R.id.qh_carture);
		adress = (EditText) this.findViewById(R.id.qh_adress);
		street = (EditText) this.findViewById(R.id.qh_street);
		juwei = (EditText) this.findViewById(R.id.qh_juwei);
		status = (EditText) this.findViewById(R.id.qh_status);
		data = (EditText) this.findViewById(R.id.qh_data);
		current_yixiang = (EditText) this.findViewById(R.id.qh_current_yixiang);

		shideng_btn = (Button) this.findViewById(R.id.qh_shideng_btn);
		infoLayout = (LinearLayout) this.findViewById(R.id.my_detail_info);
		diaocha_data = (EditText) this.findViewById(R.id.qh_diaocha_data);
		diaocha_data.setInputType(InputType.TYPE_NULL);
		diaocha_data.setOnClickListener(new MyClickListener());
		diaocha_remark = (EditText) this.findViewById(R.id.qh_diaocha_remark);
		diaocha_person = (EditText) this.findViewById(R.id.qh_diaocha_person);
		shideng_btn.setOnClickListener(new MyClickListener());
		status_spinner = (Spinner) this.findViewById(R.id.qh_status_spinner);
		current_spinner = (Spinner) this.findViewById(R.id.qh_current_spinner);
		xiangxi_btn = (Button) findViewById(R.id.qh_xxzl_btn);
		xiangxi_btn.setOnClickListener(new MyClickListener());

		diaocha_btn = (Button) this.findViewById(R.id.qh_diaocha_btn);
		diaocha_btn.setOnClickListener(new MyClickListener());

		if ("失业".equals(infos.getJYZT())) {
			setSpinner(status_spinner, R.array.resources_shiye_status);
			setSpinner(current_spinner, R.array.resources_shiye_yixiang);
		} else if ("无业".equals(infos.getJYZT())) {
			setSpinner(status_spinner, R.array.resources_wuye_status);
			setSpinner(current_spinner, R.array.resources_wuye_yixiang);
		}
		// 期望月薪
		price_onem = (Spinner) this.findViewById(R.id.price_one_d);
		// 外区就业
		jobyesno = (Spinner) this.findViewById(R.id.jobyesno);
		priceAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getResources()
						.getStringArray(R.array.jobprice));
		priceAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		price_onem.setAdapter(priceAdapter);
		yesAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, getResources()
						.getStringArray(R.array.jobyes));
		yesAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		jobyesno.setAdapter(yesAdapter);

		traintypebtn = (Button) findViewById(R.id.traintypebtn);
		jobtypebtn = (Button) findViewById(R.id.jobtypebtn);
		xuqiubtn = (Button) findViewById(R.id.xuqiubtn);
		qihang_result_tall = (Button) findViewById(R.id.qihang_result_tall);
		jobtype = (EditText) findViewById(R.id.jobtype);
		traintype = (EditText) findViewById(R.id.traintype);
		xuqiu = (EditText) findViewById(R.id.xuqiu);
		traintypebtn.setOnClickListener(btnclick);
		jobtypebtn.setOnClickListener(btnclick);
		xuqiubtn.setOnClickListener(btnclick);
		qihang_result_tall.setOnClickListener(btnclick);

		// qh_mqzk,qh_dqyx,qh_qwyx;
		qh_mqzk = (EditText) findViewById(R.id.qh_mqzk);
		qh_dqyx = (EditText) findViewById(R.id.qh_dqyx);
		qh_qwyx = (EditText) findViewById(R.id.qh_qwyx);
		no_dc_btn = (Button) findViewById(R.id.no_dc_btn);
		no_dc_btn.setOnClickListener(btnclick);

		// lay1,lay2,fuwulay,joblay,peixunlay;
		lay1 = (LinearLayout) findViewById(R.id.lay1);
		lay2 = (LinearLayout) findViewById(R.id.lay2);
		fuwulay = (LinearLayout) findViewById(R.id.fuwulay);
		joblay = (LinearLayout) findViewById(R.id.joblay);
		peixunlay = (LinearLayout) findViewById(R.id.peixunlay);
		// etfwxq1,etfwxq2,etfwxq3,etgzlx1,etgzlx2,etgzlx3,etpxlx1,etpxlx2,etpxlx3
		etfwxq1 = (EditText) findViewById(R.id.etfwxq1);
		etfwxq2 = (EditText) findViewById(R.id.etfwxq2);
		etfwxq3 = (EditText) findViewById(R.id.etfwxq3);
		etgzlx1 = (EditText) findViewById(R.id.etgzlx1);
		etgzlx2 = (EditText) findViewById(R.id.etgzlx2);
		etgzlx3 = (EditText) findViewById(R.id.etgzlx3);
		etpxlx1 = (EditText) findViewById(R.id.etpxlx1);
		etpxlx2 = (EditText) findViewById(R.id.etpxlx2);
		etpxlx3 = (EditText) findViewById(R.id.etpxlx3);
		tableRow6 = (LinearLayout) findViewById(R.id.tableRow6);
		tableRow7 = (LinearLayout) findViewById(R.id.tableRow7);
		tableRow8 = (LinearLayout) findViewById(R.id.tableRow8);
	}

	/**
	 * 选择服务需求工作类型以及职业培训
	 */
	public OnClickListener btnclick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.no_dc_btn:
				undo = 1;
				infoLayout.setVisibility(View.VISIBLE);
				// lay1,lay2,fuwulay,joblay,peixunlay;
				lay1.setVisibility(View.GONE);
				//lay2.setVisibility(View.GONE);
				fuwulay.setVisibility(View.GONE);
				joblay.setVisibility(View.GONE);
				peixunlay.setVisibility(View.GONE);
				qihang_result_tall.setVisibility(View.GONE);

				break;
			case R.id.traintypebtn:

				Intent intent1 = new Intent(QiHangDetailActivity.this,
						AllPXTypeActivity.class);
				startActivityForResult(intent1, 102);
				break;
			case R.id.jobtypebtn:
				Intent intent = new Intent(QiHangDetailActivity.this,
						AllJobTypeActivity.class);
				startActivityForResult(intent, 101);
				break;
			case R.id.xuqiubtn:
				Intent intent2 = new Intent(QiHangDetailActivity.this,
						AllXuqiuActivity.class);
				startActivityForResult(intent2, 100);
				break;
			case R.id.qihang_result_tall:
				setSpinnerSelection(status_spinner, infos.getNEW_MQZK());
				setSpinnerSelection(current_spinner, infos.getNEW_DQYX());
				setSpinnerSelection(jobyesno, infos.getWQJY());
				setSpinnerSelection(price_onem, infos.getXWYX());
				StringBuffer sb1 = new StringBuffer();
				sb1.append("");
				if (infos.getXWCS1().trim().length() > 0
						&& !infos.getXWCS1().equals("null")) {
					sb1.append(infos.getXWCS1().replaceAll("其他 ", "") + ";");
				}
				if (infos.getXWCS2().trim().length() > 0
						&& !infos.getXWCS2().equals("null")) {
					sb1.append(infos.getXWCS2().replaceAll("其他 ", "") + ";");
				}
				if (infos.getXWCS3().trim().length() > 0
						&& !infos.getXWCS3().equals("null")) {
					sb1.append(infos.getXWCS3().replaceAll("其他 ", "") + ";");
				}
				jobtype.setText(sb1.toString());
				if(sb1.toString().contains(";"))
				{
					String[] s = sb1.toString().split(";");
					if(s.length>0)
					{
						infos.setNEW_XWCS1(s[0]);
					}
					if(s.length>1)
					{
						infos.setNEW_XWCS2(s[1]);
					}
					if(s.length>2)
					{
						infos.setNEW_XWCS3(s[2]);
					}
				}
				
				

				StringBuffer sb2 = new StringBuffer();
				sb2.append("");
				if (infos.getJYFW1().trim().length() > 0
						&& !infos.getJYFW1().equals("null")) {
					sb2.append(infos.getJYFW1().replaceAll("其他 ", "") + ";");
				}
				if (infos.getJYFW2().trim().length() > 0
						&& !infos.getJYFW2().equals("null")) {
					sb2.append(infos.getJYFW2().replaceAll("其他 ", "") + ";");
				}
				if (infos.getJYFW3().trim().length() > 0
						&& !infos.getJYFW3().equals("null")) {
					sb2.append(infos.getJYFW3().replaceAll("其他 ", "") + ";");
				}
				xuqiu.setText(sb2.toString());
				if(sb2.toString().contains(";"))
				{
					String[] s = sb2.toString().split(";");
					if(s.length>0)
					{
						infos.setNEW_JYFW1(s[0]);
					}
					if(s.length>1)
					{
						infos.setNEW_JYFW2(s[1]);
					}
					if(s.length>2)
					{
						infos.setNEW_JYFW3(s[2]);
					}
				}
				
				
				
				
				StringBuffer sb3 = new StringBuffer();
				sb3.append("");
				if (infos.getXWPX1().trim().length() > 0
						&& !infos.getXWPX1().equals("null")) {
					sb3.append(infos.getXWPX1().replaceAll("其他 ", "") + ";");
				}
				if (infos.getXWPX2().trim().length() > 0
						&& !infos.getXWPX2().equals("null")) {
					sb3.append(infos.getXWPX2().replaceAll("其他 ", "") + ";");
				}
				if (infos.getXWPX3().trim().length() > 0
						&& !infos.getXWPX3().equals("null")) {
					sb3.append(infos.getXWPX3().replaceAll("其他 ", "") + ";");
				}
				traintype.setText(sb3.toString());
				if(sb3.toString().contains(";"))
				{
					String[] s = sb3.toString().split(";");
					if(s.length>0)
					{
						infos.setNEW_XWPX1(s[0]);
					}
					if(s.length>1)
					{
						infos.setNEW_XWPX2(s[1]);
					}
					if(s.length>2)
					{
						infos.setNEW_XWPX3(s[2]);
					}
				}
				
				
				
				break;
			default:
				break;
			}

		}

	};

	private void bdgView() {
		setSpinnerSelection(status_spinner, infos.getNEW_MQZK());
		setSpinnerSelection(current_spinner, infos.getNEW_DQYX());
		setSpinnerSelection(jobyesno, infos.getNEW_WQJY());
		setSpinnerSelection(price_onem, infos.getNEW_XWYX());
		StringBuffer sb1 = new StringBuffer();
		sb1.append("");
		if (infos.getNEW_XWCS1().trim().length() > 0
				&& !infos.getNEW_XWCS1().equals("null")) {
			sb1.append(infos.getNEW_XWCS1().replaceAll("其他 ", "") + ";");
		}
		if (infos.getNEW_XWCS2().trim().length() > 0
				&& !infos.getNEW_XWCS2().equals("null")) {
			sb1.append(infos.getNEW_XWCS2().replaceAll("其他 ", "") + ";");
		}
		if (infos.getNEW_XWCS3().trim().length() > 0
				&& !infos.getNEW_XWCS3().equals("null")) {
			sb1.append(infos.getNEW_XWCS3().replaceAll("其他 ", "") + ";");
		}
		jobtype.setText(sb1.toString());

		StringBuffer sb2 = new StringBuffer();
		sb2.append("");
		if (infos.getNEW_JYFW1().trim().length() > 0
				&& !infos.getNEW_JYFW1().equals("null")) {
			sb2.append(infos.getNEW_JYFW1().replaceAll("其他 ", "") + ";");
		}
		if (infos.getNEW_JYFW2().trim().length() > 0
				&& !infos.getNEW_JYFW2().equals("null")) {
			sb2.append(infos.getNEW_JYFW2().replaceAll("其他 ", "") + ";");
		}
		if (infos.getNEW_JYFW3().trim().length() > 0
				&& !infos.getNEW_JYFW3().equals("null")) {
			sb2.append(infos.getNEW_JYFW3().replaceAll("其他 ", "") + ";");
		}
		xuqiu.setText(sb2.toString());

		StringBuffer sb3 = new StringBuffer();
		sb3.append("");
		if (infos.getNEW_XWPX1().trim().length() > 0
				&& !infos.getNEW_XWPX1().equals("null")) {
			sb3.append(infos.getNEW_XWPX1().replaceAll("其他 ", "") + ";");
		}
		if (infos.getNEW_XWPX2().trim().length() > 0
				&& !infos.getNEW_XWPX2().equals("null")) {
			sb3.append(infos.getNEW_XWPX2().replaceAll("其他 ", "") + ";");
		}
		if (infos.getNEW_XWPX3().trim().length() > 0
				&& !infos.getNEW_XWPX3().equals("null")) {
			sb3.append(infos.getNEW_XWPX3().replaceAll("其他 ", "") + ";");
		}
		traintype.setText(sb3.toString());
	}

	List<String> xuqiuname;
	List<String> jobtypename;
	List<String> typename;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		xuqiuname = new ArrayList<String>();
		jobtypename = new ArrayList<String>();
		typename = new ArrayList<String>();
		if (requestCode == 100 && resultCode == 100) {
			xuqiuname = (List<String>) data.getSerializableExtra("xuqiu");
			StringBuffer sb = new StringBuffer();

			if (xuqiuname.size() == 1) {
				infos.setNEW_JYFW1(xuqiuname.get(0));
				infos.setNEW_JYFW2("");
				infos.setNEW_JYFW3("");
			} else if (xuqiuname.size() == 2) {
				infos.setNEW_JYFW1(xuqiuname.get(0));
				infos.setNEW_JYFW2(xuqiuname.get(1));
				infos.setNEW_JYFW3("");
			} else if (xuqiuname.size() == 3) {
				infos.setNEW_JYFW1(xuqiuname.get(0));
				infos.setNEW_JYFW2(xuqiuname.get(1));
				infos.setNEW_JYFW3(xuqiuname.get(2));
			} else {
				infos.setNEW_JYFW1("");
				infos.setNEW_JYFW2("");
				infos.setNEW_JYFW3("");
			}

			for (int i = 0; i < xuqiuname.size(); i++) {
				sb.append(xuqiuname.get(i).replaceFirst("其他 ", "") + ";");
			}

			xuqiu.setText(sb.toString());
		}
		if (requestCode == 101 && resultCode == 101) {
			jobtypename = (List<String>) data.getSerializableExtra("jobtype");
			StringBuffer sb2 = new StringBuffer();
			if (jobtypename.size() == 1) {
				infos.setNEW_XWCS1(jobtypename.get(0));
				infos.setNEW_XWCS2("");
				infos.setNEW_XWCS3("");
			} else if (jobtypename.size() == 2) {
				infos.setNEW_XWCS1(jobtypename.get(0));
				infos.setNEW_XWCS2(jobtypename.get(1));
				infos.setNEW_XWCS3("");
			} else if (jobtypename.size() == 3) {
				infos.setNEW_XWCS1(jobtypename.get(0));
				infos.setNEW_XWCS2(jobtypename.get(1));
				infos.setNEW_XWCS3(jobtypename.get(2));
			} else {
				infos.setNEW_XWCS1("");
				infos.setNEW_XWCS2("");
				infos.setNEW_XWCS3("");
			}

			for (int i = 0; i < jobtypename.size(); i++) {

				sb2.append(jobtypename.get(i).replaceFirst("其他 ", "") + ";");
			}
			jobtype.setText(sb2.toString());
		}
		if (requestCode == 102 && resultCode == 102) {
			typename = (List<String>) data.getSerializableExtra("type");
			StringBuffer sb1 = new StringBuffer();

			if (typename.size() == 1) {
				infos.setNEW_XWPX1(typename.get(0));
				infos.setNEW_XWPX2("");
				infos.setNEW_XWPX3("");
			} else if (typename.size() == 2) {
				infos.setNEW_XWPX1(typename.get(0));
				infos.setNEW_XWPX2(typename.get(1));
				infos.setNEW_XWPX3("");
			} else if (typename.size() == 3) {
				infos.setNEW_XWPX1(typename.get(0));
				infos.setNEW_XWPX2(typename.get(1));
				infos.setNEW_XWPX3(typename.get(2));
			} else {
				infos.setNEW_XWPX1("");
				infos.setNEW_XWPX2("");
				infos.setNEW_XWPX3("");
			}

			for (int i = 0; i < typename.size(); i++) {

				sb1.append(typename.get(i).replaceFirst("其他 ", "") + ";");
			}
			traintype.setText(sb1.toString());

		}

	}

	private void initValue() {
		idcard.setText(infos.getSFZ().toString().trim());
		name.setText(infos.getNAME());
		carture.setText(infos.getEDU());
		adress.setText(infos.getHKDZ());
		street.setText(infos.getJD());
		juwei.setText(infos.getJW());
		status.setText(infos.getFWJD());
		data.setText(infos.getFWJD());
		current_yixiang.setText(infos.getFWJW());
		qh_mqzk.setText(infos.getNEW_MQZK() + "");
		qh_dqyx.setText(infos.getNEW_DQYX() + "");
		// etfwxq1,etfwxq2,etfwxq3,etgzlx1,etgzlx2,etgzlx3,etpxlx1,etpxlx2,etpxlx3
		// infos.get

		if (!infos.getJYFW1().equals("null")) {
			etfwxq1.setText(infos.getJYFW1());
		}
		if (!infos.getJYFW2().equals("null")) {
			etfwxq2.setText(infos.getJYFW2());
		}
		if (!infos.getJYFW3().equals("null")) {
			etfwxq3.setText(infos.getJYFW3());
		}
		if (!infos.getXWCS1().equals("null")) {
			etgzlx1.setText(infos.getXWCS1());
		}
		if (!infos.getXWCS2().equals("null")) {
			etgzlx2.setText(infos.getXWCS2());
		}
		if (!infos.getXWCS3().equals("null")) {
			etgzlx3.setText(infos.getXWCS3());
		}
		if (!infos.getXWPX1().equals("null")) {
			etpxlx1.setText(infos.getXWPX1());
		}
		if (!infos.getXWPX2().equals("null")) {
			etpxlx2.setText(infos.getXWPX2());
		}
		if (!infos.getXWPX3().equals("null")) {
			etpxlx3.setText(infos.getXWPX3());
		}

		if (!infos.getXWYX().equals("null")) {
			qh_qwyx.setText(infos.getXWYX() + "");
		}

		// infos.getUND
		// infos.getu
		if ("".equals(infos.getNEW_DQYX()) || "".equals(infos.getNEW_MQZK())
				|| null == infos.getNEW_DQYX() || null == infos.getNEW_MQZK()
				|| "null".equals(infos.getNEW_DQYX())
				|| "null".equals(infos.getNEW_MQZK())) {
			if (infos.getUNDO().equalsIgnoreCase("true")
					&& infos.getMARK().trim().length() > 0
					|| infos.getMARK().equalsIgnoreCase("null")) {
				infoLayout.setVisibility(View.VISIBLE);
				// lay1,lay2,fuwulay,joblay,peixunlay;
				lay1.setVisibility(View.GONE);
				lay2.setVisibility(View.GONE);
				fuwulay.setVisibility(View.GONE);
				joblay.setVisibility(View.GONE);
				peixunlay.setVisibility(View.GONE);
				qihang_result_tall.setVisibility(View.GONE);
				diaocha_btn.setVisibility(View.GONE);
				diaocha_remark.setText(infos.getMARK());
				shideng_btn.setVisibility(View.GONE);
				no_dc_btn.setVisibility(View.GONE);
				// qh_mqzk.setVisibility(View.GONE);
				// qh_dqyx.setVisibility(View.GONE);
				// qh_qwyx.setVisibility(View.GONE);
				tableRow5.setVisibility(View.GONE);
				tableRow6.setVisibility(View.GONE);
				tableRow7.setVisibility(View.GONE);
				tableRow8.setVisibility(View.GONE);

			} else {
				infoLayout.setVisibility(View.GONE);
				diaocha_btn.setEnabled(true);
			}

		} else {
			shideng_btn.setVisibility(View.GONE);
			no_dc_btn.setVisibility(View.GONE);
			infoLayout.setVisibility(View.VISIBLE);
			if ("null".equals(infos.getMARK().toString().trim())) {
				diaocha_remark.setText("");
			} else {
				diaocha_remark.setText(infos.getMARK().toString().trim());
			}
			setSpinnerSelection(status_spinner, infos.getNEW_MQZK());
			setSpinnerSelection(current_spinner, infos.getNEW_DQYX());
			// diaocha_btn.setEnabled(false);
			diaocha_btn.setVisibility(View.GONE);
			// xuqiubtn.setEnabled(false);
			xuqiubtn.setVisibility(View.GONE);
			// jobtypebtn.setEnabled(false);
			jobtypebtn.setVisibility(View.GONE);
			// traintypebtn.setEnabled(false);
			traintypebtn.setVisibility(View.GONE);
			qihang_result_tall.setVisibility(View.GONE);
			no_dc_btn.setVisibility(View.GONE);
			bdgView();
			// diaocha_btn.setBackgroundResource(R.drawable.button_enabled);
		}

		if ("就业".equals(infos.getJYZT())) {
			infoLayout.setVisibility(View.GONE);
			shideng_btn.setVisibility(View.GONE);
		}
		diaocha_data.setText(infos.getSURVEY_DATE().substring(0, 10));
		diaocha_person.setText(MainService.STAFFNAME);

	}

	/**
	 * 设置下拉框工具类
	 * 
	 * @param spinner
	 * @param dataid
	 */
	private void setSpinner(Spinner spinner, int dataid) {
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, dataid, android.R.layout.simple_spinner_item);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
	}

	/**
	 * 根据值，设置下拉框的选中项
	 * 
	 * @param spinner
	 * @param value
	 */
	private void setSpinnerSelection(Spinner spinner, String value) {
		for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
			if (spinner.getAdapter().getItem(i).toString().trim()
					.equals(value.trim())) {
				spinner.setSelection(i);
				break;
			}
		}
	}

	private class MyClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.qh_shideng_btn:
				undo = 0;
				infoLayout.setVisibility(View.VISIBLE);
				lay1.setVisibility(View.VISIBLE);
				lay2.setVisibility(View.VISIBLE);
				fuwulay.setVisibility(View.VISIBLE);
				joblay.setVisibility(View.VISIBLE);
				peixunlay.setVisibility(View.VISIBLE);
				qihang_result_tall.setVisibility(View.VISIBLE);
				break;

			case R.id.qh_xxzl_btn:
				Intent intent = new Intent(QiHangDetailActivity.this,
						QiHangDetailInfoActiviyt.class);
				intent.putExtra("infos", (Serializable) infos);
				startActivity(intent);
				break;

			case R.id.qh_diaocha_data:
				showDateDialog(diaocha_data);
				break;

			case R.id.qh_diaocha_btn:
				if (isSave) {
					Toast.makeText(QiHangDetailActivity.this, "数据不能重复上传！",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// if
				// ("".equals(infos.getDQYX())||"".equals(infos.getMQZK())||"".equals(infos.getMARK())||null==infos.getDQYX()||null==infos.getMQZK()||null==infos.getMARK())
				// {
				// Toast.makeText(WuYeDetailActivity.this, "数据不能上传！",
				// Toast.LENGTH_SHORT).show();
				// return ;
				// }
				Builder builder = new AlertDialog.Builder(
						QiHangDetailActivity.this);
				builder.setTitle("温馨提示").setMessage("确定保存此信息吗?");
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								saveInfo();
								dialog.dismiss();
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
				builder.create();
				builder.show();
				break;

			default:
				break;
			}
		}
	}

	private void saveInfo() {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		if (status_spinner.getSelectedItemPosition() == 0) {
			Toast.makeText(QiHangDetailActivity.this, "请选择目前情况!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (current_spinner.getSelectedItemPosition() == 0) {
			Toast.makeText(QiHangDetailActivity.this, "请选择当前意向!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (undo == 0) {
			

			if (jobyesno.getSelectedItemPosition() == 0) {
				Toast.makeText(QiHangDetailActivity.this, "请选择是否外区就业!",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (price_onem.getSelectedItemPosition() == 0) {
				Toast.makeText(QiHangDetailActivity.this, "请选择期望月薪!",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if( infos.getNEW_JYFW1().length()==0 )
			{
				Toast.makeText(QiHangDetailActivity.this, "请选择服务需求!",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if(infos.getNEW_XWCS1().length()==0)
			{
				Toast.makeText(QiHangDetailActivity.this, "请选工作类型!",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if(infos.getNEW_JYFW1().contains("职业培训")||infos.getNEW_JYFW2().contains("职业培训")||infos.getNEW_JYFW3().contains("职业培训"))
			{
				if(infos.getNEW_XWPX1().length()==0)
				{
					Toast.makeText(QiHangDetailActivity.this, "请选择培训类别!",
							Toast.LENGTH_SHORT).show();
					return;
				}
			}
			
			data.put("NEW_MQZK", status_spinner.getSelectedItem().toString()
					.trim());
			data.put("NEW_DQYX", current_spinner.getSelectedItem().toString()
					.trim());

			data.put("NEW_WQJY", jobyesno.getSelectedItem().toString().trim());
			data.put("NEW_XWYX", price_onem.getSelectedItem().toString().trim());
			// List<String> xuqiuname = new ArrayList<String>();
			// List<String> jobtypename = new ArrayList<String>();
			// List<String> typename = new ArrayList<String>();
			data.put("NEW_JYFW1", infos.getNEW_JYFW1() + "");
			data.put("NEW_JYFW2", infos.getNEW_JYFW2() + "");
			data.put("NEW_JYFW3", infos.getNEW_JYFW3() + "");
			data.put("NEW_XWCS1", infos.getNEW_XWCS1() + "");
			data.put("NEW_XWCS2", infos.getNEW_XWCS2() + "");
			data.put("NEW_XWCS3", infos.getNEW_XWCS3() + "");
			data.put("NEW_XWPX1", infos.getNEW_XWPX1() + "");
			data.put("NEW_XWPX2", infos.getNEW_XWPX2() + "");
			data.put("NEW_XWPX3", infos.getNEW_XWPX3() + "");
		} else {

			
			if (diaocha_remark.getText().toString().trim().length() == 0) {
				Toast.makeText(QiHangDetailActivity.this, "请输入无法调查原因!",
						Toast.LENGTH_SHORT).show();
				return;
			}

			data.put("NEW_MQZK", status_spinner.getSelectedItem().toString()
					.trim());
			data.put("NEW_DQYX", current_spinner.getSelectedItem().toString()
					.trim());

			data.put("NEW_WQJY", "");
			data.put("NEW_XWYX", "");
			// List<String> xuqiuname = new ArrayList<String>();
			// List<String> jobtypename = new ArrayList<String>();
			// List<String> typename = new ArrayList<String>();
			data.put("NEW_JYFW1", "");
			data.put("NEW_JYFW2", "");
			data.put("NEW_JYFW3", "");
			data.put("NEW_XWCS1", "");
			data.put("NEW_XWCS2", "");
			data.put("NEW_XWCS3", "");
			data.put("NEW_XWPX1", "");
			data.put("NEW_XWPX2", "");
			data.put("NEW_XWPX3", "");
		}

		data.put("SURVEY_DATE", diaocha_data.getText().toString().trim());

		data.put("UNDO", undo + "");
		data.put("ID", infos.getID() + "");
		data.put("MARK", diaocha_remark.getText().toString().trim() + "");
		params.put("data", data);
		PersonTask task = new PersonTask(
				PersonTask.ZHIYUANDETAILACTIVITY_SET_QH_DETAIL_INFO, params);
		PersonService.newTask(task);
	}

	/**
	 * 显示时间对话框
	 * 
	 * @param txt
	 */
	private void showDateDialog(final EditText txt) {
		final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String value = txt.getText().toString().trim();
		Calendar day = Calendar.getInstance();
		try {
			day.setTime(format.parse(value));
			new DatePickerDialog(this, new OnDateSetListener() {

				@Override
				public void onDateSet(DatePicker view, int year,
						int monthOfYear, int dayOfMonth) {
					try {
						Date today = format.parse(format.format(new Date()));
						Date selectDate = new Date(year - 1900, monthOfYear,
								dayOfMonth);
						if (selectDate.compareTo(today) <= 0) {
							txt.setText(format.format(selectDate));
						} else {
							Toast.makeText(QiHangDetailActivity.this,
									"时间必须是当年且小于当前时间！", Toast.LENGTH_SHORT)
									.show();
							// txt.setText(format.format(today));
						}

					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}, day.get(Calendar.YEAR), day.get(Calendar.MONTH),
					day.get(Calendar.DATE)).show();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}

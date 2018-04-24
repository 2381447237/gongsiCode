package com.fc.wenjuan.views;

import com.fc.person.views.PersoninfoMainActivity;
import com.fc.wenjuan.beans.WenJuanPersonInfo;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class GXShowPersionDetailInfo extends Activity implements
		OnClickListener {
	private TextView name,sex,sfz,mz,csrq,dz,edu,status,mdzk,school,sxzy,ksxx,jsxx,jd,jw,dqyx;
	private Button disscus, no_disscus;
	private Button persion_info, history_list;

	private WenJuanPersonInfo info;
	private boolean rb;
	private int position;
	private TextView fin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_gxwenjuan_persion_info);
		info = (WenJuanPersonInfo) getIntent().getSerializableExtra("info");
		rb = getIntent().getBooleanExtra("rb", true);
		position = getIntent().getIntExtra("position", 0);
		initView();
		initValue();
	}

	private void initView() {
		//name,sex,sfz,mz,csrq,dz,edu,status,mdzk,school,sxzy,ksxx,jsxx,jd,jw;
		//qx_tv = (TextView) this.findViewById(R.id.qx);
	//	pid_tv = (TextView) this.findViewById(R.id.pid);
	//	number_tv = (TextView) this.findViewById(R.id.id);
		name = (TextView) this.findViewById(R.id.name);
		sex = (TextView) this.findViewById(R.id.sex);
		edu = (TextView) this.findViewById(R.id.edu);
		sfz = (TextView) this.findViewById(R.id.sfz);
		mz=(TextView) this.findViewById(R.id.mz);
		csrq=(TextView) this.findViewById(R.id.csrq);
		dz=(TextView) this.findViewById(R.id.dz);
		mdzk=(TextView) this.findViewById(R.id.mdzk);
		school=(TextView) this.findViewById(R.id.school);
		sxzy=(TextView) this.findViewById(R.id.sxzy);
		ksxx=(TextView) this.findViewById(R.id.ksxx);
		jsxx=(TextView) findViewById(R.id.jsxx);
		jd = (TextView) this.findViewById(R.id.jd);
		jw = (TextView) this.findViewById(R.id.jw);
	//	lxdz_tv = (TextView) this.findViewById(R.id.lxdz);
	//	phone = (TextView) this.findViewById(R.id.lxdh);
		status = (TextView) this.findViewById(R.id.status);
		//dzszqx_tv = (TextView) this.findViewById(R.id.hjqx);

		disscus = (Button) this.findViewById(R.id.disscus);
		no_disscus = (Button) this.findViewById(R.id.no_disscus);
		persion_info = (Button) this.findViewById(R.id.persion_info);
		history_list = (Button) this.findViewById(R.id.history_list);
		dqyx=(TextView) this.findViewById(R.id.dqyx);
		persion_info.setOnClickListener(this);
		history_list.setOnClickListener(this);
		disscus.setOnClickListener(this);
		no_disscus.setOnClickListener(this);
	}

	private void initValue() {
		//name,sex,sfz,mz,csrq,dz,edu,status,mdzk,school,sxzy,ksxx,jsxx,jd,jw;
		//qx_tv.setText(info.getQX());
		//pid_tv.setText(info.getPID());
	//	number_tv.setText(info.getNO());
		mz.setText(info.getMZ());
		name.setText(info.getNAME());
		sex.setText(info.getSEX());
		sfz.setText(info.getSFZ());
		edu.setText(info.getEDU());
		jd.setText(info.getJD());
		jw.setText(info.getJW());
		csrq.setText(info.getCSRQ());
		dz.setText(info.getDZ());
		status.setText(info.getJYZT());
		mdzk.setText(info.getMDQK());
		school.setText(info.getXXMC());
		sxzy.setText(info.getSXZY());
		ksxx.setText(info.getKSXX());
		jsxx.setText(info.getJSXX());
		dqyx.setText(info.getDQYX());
		//lxdz_tv.setText(info.getLXDZ());
		//phone.setText(info.getPHONE());
	//	zt_tv.setText(info.getZT());
	//	dzszqx_tv.setText(info.getQX());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.disscus:
			if (rb) {
				Intent showIntent = new Intent(GXShowPersionDetailInfo.this,
						GXWenJuanDetailActivity.class);
				showIntent.putExtra("pid", info.getID());
				showIntent.putExtra("NO", info.getNO());
				showIntent.putExtra("sex", info.getSEX());
				showIntent.putExtra("csrq", info.getCSRQ());
				showIntent.putExtra("jsxx", info.getJSXX());
				showIntent.putExtra("edu", info.getEDU());
				showIntent.putExtra("school", info.getXXMC());
				showIntent.putExtra("sxzy", info.getSXZY());
				showIntent.putExtra("position", position);
				showIntent.putExtra("rb", true);
				startActivity(showIntent);
			}
			break;

		case R.id.no_disscus:
			if (rb) {
				Intent intent = new Intent(GXShowPersionDetailInfo.this,
						GXShowWenJuanMarkActivity.class);
				intent.putExtra("pid", String.valueOf(info.getID()));
				intent.putExtra("position", position);
				startActivity(intent);
			}
			break;

		case R.id.persion_info:
			Intent itemIntent = new Intent(GXShowPersionDetailInfo.this,
					PersoninfoMainActivity.class);
			itemIntent.putExtra("mysfz", info.getSFZ());
			startActivity(itemIntent);
			break;

		case R.id.history_list:
			Intent historyIntent = new Intent(GXShowPersionDetailInfo.this,
					ShowPersionHistoryActivity.class);
			historyIntent.putExtra("sfz", info.getSFZ());
			startActivity(historyIntent);
			break;
		default:
			break;
		}
		GXShowPersionDetailInfo.this.finish();
	}

}

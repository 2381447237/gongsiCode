package com.fc.wenjuan.views;

import com.fc.person.views.PersoninfoMainActivity;
import com.fc.person.views.PersonqueryListActivity;
import com.fc.wenjuan.beans.WenJuanPersonInfo;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowPersionDetailInfo extends Activity implements OnClickListener {
	private TextView qx_tv, pid_tv, number_tv, name_tv, sex_tv, sfz_tv, edu_tv,
			zt_tv, jd_tv, jw_tv, lxdz_tv, phone, dzszqx_tv;
	private Button disscus, no_disscus;
	private Button persion_info, history_list;

	private WenJuanPersonInfo info;
	private boolean rb;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wenjuan_persion_info);
		info = (WenJuanPersonInfo) getIntent().getSerializableExtra("info");
		rb = getIntent().getBooleanExtra("rb", true);
		position = getIntent().getIntExtra("position", 0);
		initView();
		initValue();
	}

	private void initView() {
		qx_tv = (TextView) this.findViewById(R.id.qx);
		pid_tv = (TextView) this.findViewById(R.id.pid);
		number_tv = (TextView) this.findViewById(R.id.id);
		name_tv = (TextView) this.findViewById(R.id.name);
		sex_tv = (TextView) this.findViewById(R.id.sex);
		edu_tv = (TextView) this.findViewById(R.id.edu);
		sfz_tv = (TextView) this.findViewById(R.id.sfz);
		jd_tv = (TextView) this.findViewById(R.id.jd);
		jw_tv = (TextView) this.findViewById(R.id.jw);
		lxdz_tv = (TextView) this.findViewById(R.id.lxdz);
		phone = (TextView) this.findViewById(R.id.lxdh);
		zt_tv = (TextView) this.findViewById(R.id.status);
		dzszqx_tv = (TextView) this.findViewById(R.id.hjqx);

		disscus = (Button) this.findViewById(R.id.disscus);
		no_disscus = (Button) this.findViewById(R.id.no_disscus);
		persion_info = (Button) this.findViewById(R.id.persion_info);
		history_list = (Button) this.findViewById(R.id.history_list);

		persion_info.setOnClickListener(this);
		history_list.setOnClickListener(this);
		disscus.setOnClickListener(this);
		no_disscus.setOnClickListener(this);
	}

	private void initValue() {
		qx_tv.setText(info.getQX());
		pid_tv.setText(info.getPID());
		number_tv.setText(info.getNO());
		name_tv.setText(info.getNAME());
		sex_tv.setText(info.getSEX());
		sfz_tv.setText(info.getSFZ());
		edu_tv.setText(info.getEDU());
		jd_tv.setText(info.getJD());
		jw_tv.setText(info.getJW());
		lxdz_tv.setText(info.getLXDZ());
		phone.setText(info.getPHONE());
		zt_tv.setText(info.getZT());
		dzszqx_tv.setText(info.getQX());
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.disscus:
			if (rb) {
				Intent showIntent = new Intent(ShowPersionDetailInfo.this,
						WenJuanDetailActivity.class);
				showIntent.putExtra("pid", info.getID());
				showIntent.putExtra("NO", info.getNO());
				showIntent.putExtra("position", position);
				showIntent.putExtra("rb", true);
				startActivity(showIntent);
			}
			break;

		case R.id.no_disscus:
			if (rb) {
				Intent intent = new Intent(ShowPersionDetailInfo.this,
						ShowWenJuanMarkActivity.class);
				intent.putExtra("pid", String.valueOf(info.getID()));
				intent.putExtra("position", position);
				startActivity(intent);
			}
			break;

		case R.id.persion_info:
			Intent itemIntent = new Intent(ShowPersionDetailInfo.this,
					PersoninfoMainActivity.class);
			itemIntent.putExtra("mysfz", info.getSFZ());
			startActivity(itemIntent);
			break;

		case R.id.history_list:
			Intent historyIntent = new Intent(ShowPersionDetailInfo.this,
					ShowPersionHistoryActivity.class);
			historyIntent.putExtra("sfz", info.getSFZ());
			startActivity(historyIntent);
			break;
		default:
			break;
		}
		ShowPersionDetailInfo.this.finish();
	}

}

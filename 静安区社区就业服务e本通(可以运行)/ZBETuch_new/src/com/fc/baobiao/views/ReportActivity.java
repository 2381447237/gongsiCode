package com.fc.baobiao.views;

import java.net.URLEncoder;
import java.util.ArrayList;

import com.fc.company.beans.CompanyTask;
import com.fc.first.beans.LocationInformation;
import com.fc.main.beans.GridAdapter;
import com.fc.main.beans.IActivity;
import com.fc.main.service.CompanyService;
import com.fc.main.service.MainService;
import com.fc.main.tools.HttpUrls_;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class ReportActivity extends Activity implements IActivity {
	private TextView lblUpdateTime;
	private GridView dgvReport;// 网格view
	private GridAdapter adapter;// 网格适配器
	private int imgIds[] = new int[] { R.drawable.street1,
			R.drawable.committee1, R.drawable.age1, R.drawable.sex1,
			R.drawable.degree1, R.drawable.street2, R.drawable.committee2,
			R.drawable.age2, R.drawable.sex2, R.drawable.degree2 };

	public static final int REFRESH_UPDATETIME = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report);
		init();
		initView();
		initListener();
		CompanyTask task = new CompanyTask(
				CompanyTask.REPORTACTIVITY_GET_UPDATETIME, null);
		CompanyService.newTask(task);
	}

	private void initView() {
		dgvReport = (GridView) findViewById(R.id.dgvReport);
		adapter = new GridAdapter(imgIds, this);
		dgvReport.setAdapter(adapter);
		lblUpdateTime = (TextView) findViewById(R.id.lblUpdateTime);
	}

	private void initListener() {
		dgvReport.setOnItemClickListener(new MyOnItemClickListener());
	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString().trim())) {
		case ReportActivity.REFRESH_UPDATETIME:
			if (params[1] != null) {
				String time = params[1].toString().trim();
				String[] times = time.split(" ");
				lblUpdateTime.setText("报表日期：" + times[0]);
			}
			break;
		}
	}

	private class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			switch (position) {
			case 0:
				Intent intent0 = new Intent(ReportActivity.this,
						ReportShowActivity.class);
				intent0.putExtra(
						"url",
						HttpUrls_.HttpURL + "/Chart/Chart_STREET.aspx?staff="
								+ MainService.STAFFID + "&type="
								+ URLEncoder.encode("登记失业"));
				startActivity(intent0);

				break;
			case 1:
				Intent intent1 = new Intent(ReportActivity.this,
						ReportShowActivity.class);
				intent1.putExtra(
						"url",
						HttpUrls_.HttpURL
								+ "/Chart/Chart_committee.aspx?staff="
								+ MainService.STAFFID + "&type="
								+ URLEncoder.encode("登记失业"));
				startActivity(intent1);
				break;
			case 2:
				Intent intent2 = new Intent(ReportActivity.this,
						ReportShowActivity.class);
				intent2.putExtra("url", HttpUrls_.HttpURL
						+ "/Chart/Chart_age.aspx?staff=" + MainService.STAFFID
						+ "&type=" + URLEncoder.encode("登记失业"));
				startActivity(intent2);
				break;
			case 3:
				Intent intent3 = new Intent(ReportActivity.this,
						ReportShowActivity.class);
				intent3.putExtra("url", HttpUrls_.HttpURL
						+ "/Chart/Chart_sex.aspx?staff=" + MainService.STAFFID
						+ "&type=" + URLEncoder.encode("登记失业"));
				startActivity(intent3);
				break;
			case 4:
				Intent intent4 = new Intent(ReportActivity.this,
						ReportShowActivity.class);
				intent4.putExtra("url", HttpUrls_.HttpURL
						+ "/Chart/Chart_edu.aspx?staff=" + MainService.STAFFID
						+ "&type=" + URLEncoder.encode("登记失业"));
				startActivity(intent4);
				break;
			case 5:
				Intent intent5 = new Intent(ReportActivity.this,
						ReportShowActivity.class);
				intent5.putExtra(
						"url",
						HttpUrls_.HttpURL + "/Chart/Chart_STREET.aspx?staff="
								+ MainService.STAFFID + "&type="
								+ URLEncoder.encode("未登记失业"));
				startActivity(intent5);
				break;
			case 6:
				Intent intent6 = new Intent(ReportActivity.this,
						ReportShowActivity.class);
				intent6.putExtra(
						"url",
						HttpUrls_.HttpURL
								+ "/Chart/Chart_committee.aspx?staff="
								+ MainService.STAFFID + "&type="
								+ URLEncoder.encode("未登记失业"));
				startActivity(intent6);
				break;
			case 7:
				Intent intent7 = new Intent(ReportActivity.this,
						ReportShowActivity.class);
				intent7.putExtra("url", HttpUrls_.HttpURL
						+ "/Chart/Chart_age.aspx?staff=" + MainService.STAFFID
						+ "&type=" + URLEncoder.encode("未登记失业"));
				startActivity(intent7);
				break;
			case 8:
				Intent intent8 = new Intent(ReportActivity.this,
						ReportShowActivity.class);
				intent8.putExtra("url", HttpUrls_.HttpURL
						+ "/Chart/Chart_sex.aspx?staff=" + MainService.STAFFID
						+ "&type=" + URLEncoder.encode("未登记失业"));
				startActivity(intent8);
				break;
			case 9:
				Intent intent9 = new Intent(ReportActivity.this,
						ReportShowActivity.class);
				intent9.putExtra("url", HttpUrls_.HttpURL
						+ "/Chart/Chart_edu.aspx?staff=" + MainService.STAFFID
						+ "&type=" + URLEncoder.encode("未登记失业"));
				startActivity(intent9);
				break;

			}

		}

	}

}

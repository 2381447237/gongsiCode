package com.fc.main.views;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.fc.main.beans.GridAdapter;
import com.fc.meetingpost.views.MeetingListActivity;
import com.fc.person.views.AttentionActivity;
import com.fc.person.views.PersoninfoActivity;
import com.fc.personpolicy.views.InsertIdCardActivity;
import com.fc.policy.views.MainPolicyActivity;
import com.fc.recritmentmanager.views.NumberCenterActivity;
import com.fc.recruitment.views.RecuritmentListActivity;
import com.fc.reportform.views.ReportFormListActivity;
import com.fc.resources.views.ResourcesMainActivity;
import com.fc.summary.views.WorkSummaryActivity;
import com.fc.work.views.WorkTongZhiActivity;
import com.fc.workstatus.views.WorkStatusActivity;
import com.fc.worktodate.views.WorkToDateListActivity;
import com.fc.baobiao.views.QueryFirstActivity;
import com.fc.baobiao.views.ReportActivity;
import com.fc.company.views.CompanyQueryActivity;
import com.fc.download.views.FileDownloadActivity;
import com.fc.first.beans.LocationInformation;
import com.fc.first.views.FirstPageActivity;
import com.fc.first.views.GetNewsListActivity;
import com.fc.gradeate.views.GradeateListActivity;
import com.fc.invite.views.ZaoPinQueryActivity;
import com.fc.zbetuch_sm.R;

/**
 * 九宫格主界面的展示
 * 
 * @author hxl
 * 
 */
public class MainActivity extends Activity implements OnItemClickListener {
	private Activity mContext = this;
	private GridView gridview;// 网格view
	private GridAdapter adapter;// 网格适配器
	private ArrayList<LocationInformation> locationlist;
	private int imgIds[] = new int[] { R.drawable.baobiaotongji,
			R.drawable.meetingpost, R.drawable.workpost,
			R.drawable.reportform_read, R.drawable.workstatus,
			R.drawable.shiyetongji, R.drawable.shujuzhongxin,
			R.drawable.mainjy, R.drawable.personpolicy };//初始九宫格图片 mainempty(空图片)

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置屏幕无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Intent it = MainActivity.this.getIntent();
		locationlist = (ArrayList<LocationInformation>) it
				.getSerializableExtra("locationlist");
		gridview = (GridView) findViewById(R.id.gridView1);
		adapter = new GridAdapter(imgIds, this);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(this);

		Intent intent = new Intent("com.fc.main.myservice.MainService");
		startService(intent);
	}

	/**
	 * 点击事件的处理
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			// 报表统计
			Intent reportiIntent = new Intent(MainActivity.this,
					ReportActivity.class);
			startActivity(reportiIntent);
			break;
		case 1:
			// 会议通知
			Intent meetingPostIntent = new Intent(mContext,MeetingListActivity.class);
			startActivity(meetingPostIntent);

			
			break;

		case 2:
			//工作通知
			Intent intent=new Intent(mContext, WorkTongZhiActivity.class);
			startActivity(intent);


			break;
		case 3:
			//报表查阅
			Intent reportFormIntent = new Intent(mContext,ReportFormListActivity.class);
			startActivity(reportFormIntent);
			
			break;
		case 4:
			//工作动态
			Intent workstatusintent=new Intent(mContext, WorkStatusActivity.class);
			startActivity(workstatusintent);
			
			break;
		case 5:
			// 失业统计
			Intent resourceIntent = new Intent(mContext,
					ResourcesMainActivity.class);
			startActivity(resourceIntent);

			break;

		case 6:
			//数据中心
			Intent numberCenterIntent = new Intent(mContext,NumberCenterActivity.class);
			startActivity(numberCenterIntent);
			break;
		case 7:
			// 就业政策
			Intent intent0 = new Intent(mContext, InsertIdCardActivity.class);
			intent0.putExtra("type", "就业");
			startActivity(intent0);
			break;
		case 8:
			// 政策服务
			Intent zhenceIntent = new Intent(MainActivity.this,
					InsertIdCardActivity.class);
			zhenceIntent.putExtra("type", "创业");
			startActivity(zhenceIntent);
			break;
		default:
			break;

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		// MainService.closeAllServices(this);
	}

	/**
	 * 重写back键监听事件，清除以前所有界面，返回到首页
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intent = new Intent(MainActivity.this,
					FirstPageActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}
		return false;
	}
}

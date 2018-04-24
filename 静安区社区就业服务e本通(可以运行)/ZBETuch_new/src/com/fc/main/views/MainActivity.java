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
import com.fc.numcenter.views.NumCenterActivity;
import com.fc.person.views.AttentionActivity;
import com.fc.person.views.PersoninfoActivity2;
import com.fc.personpolicy.views.InsertIdCardActivity;
import com.fc.policy.views.MainPolicyActivity;
import com.fc.recruitment.views.RecuritmentListActivity;
import com.fc.resources.views.ResourcesMainActivity;
import com.fc.summary.views.WorkSummaryActivity;
import com.fc.worktodate.views.WorkToDateListActivity;
import com.fc.ziyuan.views.ZhiyuandiaochaActivity;
import com.fc.baobiao.views.QueryFirstActivity;
import com.fc.baobiao.views.ReportActivity;
import com.fc.company.views.CompanyQueryActivity;
import com.fc.download.views.FileDownloadActivity;
import com.fc.first.beans.LocationInformation;
import com.fc.first.views.FirstPageActivity;
import com.fc.first.views.GetNewsListActivity;
import com.fc.gradeate.views.GradeateListActivity;
import com.fc.invite.views.ZaoPinQueryActivity;
import com.test.zbetuch_news.R;

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
	private int imgIds[] = new int[] { R.drawable.gerenxinxi,
			R.drawable.zhaopinxinxi, R.drawable.baobiaotongji,
			R.drawable.graduate, R.drawable.ziyuandiaocha,
			R.drawable.xianchangmianshi, R.drawable.shiyetongji,
			R.drawable.worktodate, R.drawable.gerenshujuzhongxin };// ,R.drawable.wangdiandiaocha,R.drawable.personpolicy};//初始九宫格图片

	@SuppressWarnings("unchecked")
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

		Intent intent = new Intent("com.fc.main.service.MainService");
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
			// 个人信息
			Intent gerenxinxiIntent = new Intent(MainActivity.this,
					PersoninfoActivity2.class);
			gerenxinxiIntent.putExtra("locationlist", locationlist);
			startActivity(gerenxinxiIntent);
			break;
		case 1:

			// 招聘信息
			Intent zaopinIntent = new Intent(MainActivity.this,
					ZaoPinQueryActivity.class);
			startActivity(zaopinIntent);
			break;

		case 2:
			// 统计报表
			Intent reportiIntent = new Intent(MainActivity.this,
					ReportActivity.class);
			startActivity(reportiIntent);
			break;

		case 3:

			// 应届毕业生
			Intent intentGradeate = new Intent(mContext,
					GradeateListActivity.class);
			startActivity(intentGradeate);

			break;
		case 4:
			// 资源调查
			Intent ziyuandiaochaIntent = new Intent(mContext,
					ZhiyuandiaochaActivity.class);
			startActivity(ziyuandiaochaIntent);
			break;
		case 5:

			// 现场面试
			Intent recuritmentIntent = new Intent(mContext,
					RecuritmentListActivity.class);
			startActivity(recuritmentIntent);
			break;
		case 6:

			// 失业统计
			Intent resourceIntent = new Intent(MainActivity.this,
					ResourcesMainActivity.class);
			startActivity(resourceIntent);
			break;
		case 7:
			// 工作日志
			Intent rizhiIntent = new Intent(MainActivity.this,
					WorkToDateListActivity.class);
			startActivity(rizhiIntent);

			break;
		case 8:
			// 个人数据中心
			Intent numcenterIntent = new Intent(mContext,
					NumCenterActivity.class);
			startActivity(numcenterIntent);

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

package com.fc.main.views;

import java.util.ArrayList;

import com.fc.baobiao.views.ReportActivity;
import com.fc.changepass.views.SetPwdActivity;
import com.fc.company.views.CompanyQueryActivity;
import com.fc.first.beans.LocationInformation;
import com.fc.first.views.FirstPageActivity;
import com.fc.first.views.GetNewsListActivity;
import com.fc.first.views.MsgBoardDetitle_MoreActivity;
import com.fc.first.views.PendWorkActivity_;
import com.fc.first.views.SearchPendWorkActivity;
import com.fc.gradeate.views.GradeateListActivity;
import com.fc.invite.views.InviteJobListActivity;
import com.fc.invite.views.ZaoPinQueryActivity;
import com.fc.main.beans.GridAdapter;
import com.fc.main.myviews.GetPersonActivity;
import com.fc.person.views.AttentionActivity;
import com.fc.person.views.PersoninfoActivity;
import com.fc.personpolicy.views.InsertIdCardActivity;
import com.fc.personpolicy.views.PersonPolicyMainActivity;
import com.fc.recruitment.views.RecuritmentListActivity;
import com.fc.test.views.TestMainActivity;
import com.fc.worktodate.views.AddWorkToDateActivity;
import com.fc.worktodate.views.WorkToDateListActivity;
import com.fc.zbetuch_sm.R;
import com.fc.ziyuan.views.ZhiyuandiaochaActivity;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class MainPageOne extends Activity implements OnItemClickListener {
	private GridView gridview;// 网格view
	private GridAdapter adapter;// 网格适配器
	private ArrayList<LocationInformation> locationlist;
	// R.drawable.recentpoint
	private int imgIds[] = new int[] { R.drawable.worktodate,
			R.drawable.recentpoint, R.drawable.xianchangmianshi,
			R.drawable.gerenxinxi, R.drawable.zhaopinxinxi,
			R.drawable.jobinformation, R.drawable.qiyexinxi,
			R.drawable.graduate, R.drawable.ziyuandiaocha };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Intent it = MainPageOne.this.getIntent();
		locationlist = (ArrayList<LocationInformation>) it
				.getSerializableExtra("locationlist");
		gridview = (GridView) findViewById(R.id.gridView1);
		adapter = new GridAdapter(imgIds, this);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			// 工作日志
			Intent rizhiIntent = new Intent(MainPageOne.this,
					WorkToDateListActivity.class);
			startActivity(rizhiIntent);


			break;
		case 1:
			// 近期热点
			Intent it6 = new Intent(MainPageOne.this, GetNewsListActivity.class);
			startActivity(it6);


			break;
		case 2:
			// 现场面试
			Intent recuritmentIntent = new Intent(MainPageOne.this,
					RecuritmentListActivity.class);
			startActivity(recuritmentIntent);


			break;
		case 3:
			// 个人信息
			Intent gerenxinxiIntent = new Intent(MainPageOne.this,
					PersoninfoActivity.class);
			gerenxinxiIntent.putExtra("locationlist", locationlist);
			startActivity(gerenxinxiIntent);


			break;
		case 4:

			// 招聘信息
			Intent zaopinIntent = new Intent(MainPageOne.this,
					ZaoPinQueryActivity.class);
			startActivity(zaopinIntent);
			break;
		case 5:
			// 岗位信息
			Intent it3 = new Intent(MainPageOne.this,
					InviteJobListActivity.class);
			startActivity(it3);


			break;
		case 6:
			// 企业信息
			Intent intent7 = new Intent(MainPageOne.this,
					CompanyQueryActivity.class);
			startActivity(intent7);
			break;
		case 7:
			// 应届毕业生
			Intent intentGradeate = new Intent(MainPageOne.this,
					GradeateListActivity.class);
			startActivity(intentGradeate);

			break;
		case 8:
			//资源调查
			Intent ziyuandiaochaIntent=new Intent(MainPageOne.this, ZhiyuandiaochaActivity.class);
			startActivity(ziyuandiaochaIntent);
			break;

		default:
			break;
		}

	}

	/**
	 * 重写back键监听事件，清除以前所有界面，返回到首页
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			Intent intent = new Intent(MainPageOne.this,
					FirstPageActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

		return false;
	}
}

package com.fc.main.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.fc.first.views.FirstPageActivity;
import com.fc.main.beans.GridAdapter;
import com.fc.queue.views.QueueDefaultActicity;
import com.fc.state.views.GuanLiMakeActivity;
import com.fc.state.views.TongXunLuActivity;
import com.fc.wenjuan.views.GXWenJuanActivity;
import com.fc.wenjuan.views.ShowWenJuanActivity;
import com.fc.wenjuan.views.WenJuanPersonActivity;
import com.fc.zbetuch_sm.R;

public class MainPageThreeActivity extends Activity implements OnItemClickListener{
	private GridView gridview;// 网格view
	private GridAdapter adapter;// 网格适配器
	private Activity mContext = this;

	private int imgIds[] = new int[] { R.drawable.shiyongguifan,
			R.drawable.tongxulu, R.drawable.quenue,
			R.drawable.wenjuandiaocha, R.drawable.gxwjdc, R.drawable.mainempty,
			R.drawable.mainempty, R.drawable.mainempty, R.drawable.mainempty
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		gridview = (GridView) findViewById(R.id.gridView1);
		adapter = new GridAdapter(imgIds, this);
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(this);
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			//管理规定
			Intent makeIntent=new Intent(mContext, GuanLiMakeActivity.class);
			startActivity(makeIntent);
			break;
			
		case 1:
			//通讯录
			Intent intent1=new Intent(mContext, TongXunLuActivity.class);
			startActivity(intent1);
			break;
			
		case 2:

			
			//排队信息
			Intent intent2=new Intent(mContext, QueueDefaultActicity.class);
			startActivity(intent2);
			
			break;
			
		case 3:
			//问卷调查
			Intent intent3=new Intent(mContext, ShowWenJuanActivity.class);
			startActivity(intent3);
			break;
		case 4:
			// 高校就业
			Intent intent4=new Intent(mContext,GXWenJuanActivity.class);
			startActivity(intent4);
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
			Intent intent = new Intent(MainPageThreeActivity.this,
					FirstPageActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

		return false;
	}

}

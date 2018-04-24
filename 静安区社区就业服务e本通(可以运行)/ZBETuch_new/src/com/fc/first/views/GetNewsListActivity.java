package com.fc.first.views;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fc.first.beans.GetNews;
import com.fc.first.beans.GetNewsAdapter;
import com.fc.main.tools.HttpUrls_;
import com.test.zbetuch_news.R;

public class GetNewsListActivity extends Activity {
	private ArrayList<GetNews> newslist = new ArrayList<GetNews>();
	private GetNewsAdapter getnewsadapter;
	private Handler firstpageHandler;
	ListView getnews_list ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_getnews_list);
		getnews_list = (ListView) findViewById(R.id.listView);
		new Thread(getnewsinfo_thread).start();
		initListener();
		firstpageHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0x250:
					Log.i("getnews_size", newslist.size() + " ");
					getnewsadapter = new GetNewsAdapter(
							GetNewsListActivity.this, newslist);
					getnews_list = (ListView) findViewById(R.id.listView);
					getnews_list.setAdapter(getnewsadapter);
					getnewsadapter.notifyDataSetChanged();
					break;
				default:
					break;
				}
			}
		};
	}

	Runnable getnewsinfo_thread = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				newslist = HttpUrls_.getNewsJson(GetNewsListActivity.this, 0,
						15);
				Log.i("getnewsinfo_thread", newslist.toString());
				Message msg_newsinfo = new Message();
				msg_newsinfo.what = 0x250;
				msg_newsinfo.obj = newslist;
				firstpageHandler.obtainMessage(0x250, newslist);
				firstpageHandler.sendMessage(msg_newsinfo);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	private void initListener() {
		
		getnews_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// 布局加载器
				LayoutInflater inflater = LayoutInflater
						.from(GetNewsListActivity.this);
				// 加载数据
				GetNews list_position_data = newslist.get(position);
				// 加载要加载的布局
				View inflater_view = inflater.inflate(
						R.layout.activity_getnews, null);
				Builder builder = new AlertDialog.Builder(
						GetNewsListActivity.this);
				builder.setView(inflater_view);
				System.out.println(newslist.get(position));
				// 标题
				TextView tv_getnews_title = (TextView) inflater_view
						.findViewById(R.id.tv_getnews_title);
				// 显示日志内容
				TextView tv_getnews_doc = (TextView) inflater_view
						.findViewById(R.id.tv_getnews_doc);
				// 当前时间
				TextView tv_getnews_currentlytime = (TextView) inflater_view
						.findViewById(R.id.tv_getnews_currentlytime);
				// pendwork_title标题
				String pendwork_title = list_position_data.getTitle();
				// 转换时间
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				// 获取系统当前时间
				String nowTime = format.format(new Date());
				// 内容
				String pendwork_content = list_position_data.getDoc();
				// 设值
				tv_getnews_doc.setText(pendwork_content);
				tv_getnews_title.setText(pendwork_title);
				tv_getnews_currentlytime.setText(nowTime);
				builder.show();
			}
		});

	}
}

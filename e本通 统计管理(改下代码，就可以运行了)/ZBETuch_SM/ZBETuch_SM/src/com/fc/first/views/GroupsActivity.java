package com.fc.first.views;



import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONException;

import com.fc.first.beans.PendWorkAdapter;
import com.fc.first.beans.PendingWorkInformation;
import com.fc.main.tools.HttpUrls_;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class GroupsActivity extends Activity {
	private ArrayList<PendingWorkInformation> pendworklist = new ArrayList<PendingWorkInformation>();
	private PendWorkAdapter pendworkadapter;
	String typecd = "已完成";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.groups_activity);
		new Thread(pendwork_thread).start();
		initListener();
		
	}
	private Handler firstpageHandler = new Handler() {
		public void handleMessage(Message msg){
			switch(msg.what){	
			case 0x240:
				pendworkadapter = new PendWorkAdapter(GroupsActivity.this, pendworklist);
				ListView pendwork_list = (ListView)findViewById(R.id.listView1);
				pendwork_list.setAdapter(pendworkadapter);
				pendworkadapter.notifyDataSetChanged();
				break;
			default:
				break;

			}
		}
	};

	Runnable pendwork_thread = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				pendworklist = HttpUrls_.getPendingWorkJson(GroupsActivity.this, 0, 15, typecd);
				Log.i("pendwork_thread", pendworklist.toString());
				Message msg_pendworkinfo = new Message();
				msg_pendworkinfo.what = 0x240;
				msg_pendworkinfo.obj = pendworklist;
				firstpageHandler.obtainMessage(0x240, pendworklist);
				firstpageHandler.sendMessage(msg_pendworkinfo);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
 
	private void initListener(){
		ListView pendwork_list = (ListView)findViewById(R.id.listView1);
		pendwork_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				LayoutInflater inflater = LayoutInflater.from(GroupsActivity.this);
				//加载数据
				PendingWorkInformation  list_position_data=  pendworklist.get(position);
				//加载要加载的布局
				View inflater_view = inflater.inflate(R.layout.activity_pendwork, null);
				Builder builder = new AlertDialog.Builder(GroupsActivity.this);
				builder.setView(inflater_view);
				System.out.println(pendworklist.get(position));
				//标题
				TextView tv_pendwork_title = (TextView) inflater_view.findViewById(R.id.tv_pendwork_title);
				//开始时间-----至结束时间
				TextView tv_pendwork_starttime = (TextView) inflater_view.findViewById(R.id.tv_pendwork_starttime);
				TextView tv_pendwork_endtime = (TextView) inflater_view.findViewById(R.id.tv_pendwork_endtime);
				//显示日志内容
				TextView tv_pendwork_doc = (TextView) inflater_view.findViewById(R.id.tv_pendwork_doc);
				//当前时间
				TextView tv_pendwork_currentlytime = (TextView) inflater_view.findViewById(R.id.tv_pendwork_currentlytime);
				//pendwork_title标题
				String pendwork_title = list_position_data.getTitle();
				//开始时间
				String  start_time = list_position_data.getStart_Time();
				String  end_time = list_position_data.getEnd_Time(); 

				//转换时间
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//获取系统当前时间
				String nowTime=format.format(new Date());
				start_time = start_time.replaceFirst("T", " ");
				end_time = end_time.replace("T", " ");
				//转换开始时间
				//内容
				String pendwork_content = list_position_data.getDoc();
				//设值
				tv_pendwork_doc.setText(pendwork_content);
				tv_pendwork_title.setText(pendwork_title);
				tv_pendwork_currentlytime.setText(nowTime);
				tv_pendwork_starttime.setText(start_time);
				tv_pendwork_endtime.setText(end_time);

				builder.show();
			}
		});
	}
}

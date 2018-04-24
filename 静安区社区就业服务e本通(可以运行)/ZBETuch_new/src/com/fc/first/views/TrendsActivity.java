package com.fc.first.views;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fc.first.beans.PendWorkAdapter;
import com.fc.first.beans.PendingWorkInformation;
import com.fc.main.tools.HttpUrls_;
import com.fc.main.tools.HttpUtil;
import com.test.zbetuch_news.R;

public class TrendsActivity extends Activity {
	private ArrayList<PendingWorkInformation> pendworklist = new ArrayList<PendingWorkInformation>();
	PendingWorkInformation pendworkInfo = new PendingWorkInformation();
	private PendWorkAdapter pendworkadapter;
	String pendworkid;
	String typecd = "未完成";
	String type = "已完成";

	private int index;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.trends_activity);
		new Thread(pendwork_thread).start();
		initListener();

	}

	private Handler firstpageHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0x230:
				pendworkadapter = new PendWorkAdapter(TrendsActivity.this,
						pendworklist);
				ListView pendwork_list = (ListView) findViewById(R.id.listView1);
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
				pendworklist = HttpUrls_.getPendingWorkJson(
						TrendsActivity.this, 0, 15, typecd);
				Log.i("pendwork_thread", pendworklist.toString());
				Message msg_pendworkinfo = new Message();
				msg_pendworkinfo.what = 0x230;
				msg_pendworkinfo.obj = pendworklist;
				firstpageHandler.obtainMessage(0x230, pendworklist);
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

	private void initListener() {
		ListView pendwork_list = (ListView) findViewById(R.id.listView1);
		pendwork_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				LayoutInflater inflater = LayoutInflater
						.from(TrendsActivity.this);
				// 加载数据
				PendingWorkInformation list_position_data = pendworklist
						.get(position);
				// 加载要加载的布局
				View inflater_view = inflater.inflate(
						R.layout.activity_pendwork, null);
				Builder builder = new AlertDialog.Builder(TrendsActivity.this);
				builder.setView(inflater_view);
				System.out.println(pendworklist.get(position));
				// 标题
				TextView tv_pendwork_title = (TextView) inflater_view
						.findViewById(R.id.tv_pendwork_title);
				// 开始时间-----至结束时间
				TextView tv_pendwork_starttime = (TextView) inflater_view
						.findViewById(R.id.tv_pendwork_starttime);
				TextView tv_pendwork_endtime = (TextView) inflater_view
						.findViewById(R.id.tv_pendwork_endtime);
				// 显示日志内容
				TextView tv_pendwork_doc = (TextView) inflater_view
						.findViewById(R.id.tv_pendwork_doc);
				// 当前时间
				TextView tv_pendwork_currentlytime = (TextView) inflater_view
						.findViewById(R.id.tv_pendwork_currentlytime);
				// pendwork_title标题
				String pendwork_title = list_position_data.getTitle();
				// 开始时间
				String start_time = list_position_data.getStart_Time();
				String end_time = list_position_data.getEnd_Time();

				// 转换时间
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				// 获取系统当前时间
				String nowTime = format.format(new Date());
				start_time = start_time.replaceFirst("T", " ");
				end_time = end_time.replace("T", " ");
				// 转换开始时间
				// 内容
				String pendwork_content = list_position_data.getDoc();
				// 设值
				tv_pendwork_doc.setText(pendwork_content);
				tv_pendwork_title.setText(pendwork_title);
				tv_pendwork_currentlytime.setText(nowTime);
				tv_pendwork_starttime.setText(start_time);
				tv_pendwork_endtime.setText(end_time);

				builder.show();
			}
		});

		pendwork_list.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				index = position;
				showDialog_pendwork(TrendsActivity.this);
				return true;
			}
		});
	}

	public void showDialog_pendwork(Context context) {
		new AlertDialog.Builder(this)
				.setTitle("单选框")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setSingleChoiceItems(new String[] { "标记为已完成", "修改代办事宜" }, 0,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								switch (which) {
								case 0:
									new Thread() {
										@Override
										public void run() {
											try {
												// TODO Auto-generated method
												// stub
												PendingWorkInformation pendworkInfo = pendworklist
														.get(index);
												pendworkInfo.setType("已完成");
												JSONObject jsonObject = new JSONObject();
												jsonObject.put("ID",
														pendworkInfo.getId());
												jsonObject
														.put("START_TIME",
																pendworkInfo
																		.getStart_Time());
												jsonObject.put("END_TIME",
														pendworkInfo
																.getEnd_Time());
												jsonObject
														.put("TITLE",
																pendworkInfo
																		.getTitle());
												jsonObject.put("DOC",
														pendworkInfo.getDoc());
												jsonObject
														.put("WORK_STAFF",
																pendworkInfo
																		.getWork_Staff());
												jsonObject
														.put("CREATE_STAFF",
																pendworkInfo
																		.getCreate_Staff());
												jsonObject
														.put("CREATE_TIME",
																pendworkInfo
																		.getCreate_Time());
												jsonObject.put("TYPE",
														pendworkInfo.getType());
												jsonObject
														.put("UPDATE_TIME",
																pendworkInfo
																		.getUpdate_time());
												jsonObject.put("Max",
														pendworkInfo.getMax());
												JSONArray array = new JSONArray();
												array.put(jsonObject);

												Map<String, String> data = new HashMap<String, String>();
												data.put("json",
														array.toString());

												String value = HttpUtil
														.postRequest(
																"/Json/Set_PendingWork.aspx",
																data);
												System.out.println("value===>"
														+ value);

											} catch (ClientProtocolException e) {
												e.printStackTrace();
											} catch (IOException e) {
												e.printStackTrace();
											} catch (JSONException e) {
												e.printStackTrace();
											}
										}
									}.start();

									// pendworkInfo.setType(type);
									// pendworklist.add(pendworkInfo);
									// String postJson =
									// HttpUrls_.PendWorkPostJson(pendworklist);
									// Log.i("flag_title", postJson.toString());
									break;
								case 1:
									// Toast.makeText(TrendsActivity.this,"暂不可用",
									// Toast.LENGTH_SHORT);
									Log.i("aaaaaaaaaaaaaaaaaaaaaaaa",
											"cccccccccccccccccccccccccccccccccccc");
									break;
								default:
									break;
								}
								dialog.dismiss();
							}
						}).setNegativeButton("取消", null).show();
	}
}

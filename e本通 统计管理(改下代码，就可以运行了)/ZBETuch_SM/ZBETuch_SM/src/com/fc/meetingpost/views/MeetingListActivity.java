package com.fc.meetingpost.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView2;
import com.fc.main.beans.PullDownView2.OnPullDownListener;
import com.fc.main.myservices.CompanyService;
import com.fc.meetguanli.views.GuanLiMainActivity;
import com.fc.meetingpost.beans.MeetingAdapter;
import com.fc.meetingpost.beans.MeetingInfo;
import com.fc.meetingpost.beans.MeetingReadInfo;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MeetingListActivity extends Activity implements 
IActivity,OnPullDownListener{
	private Activity mContext = this;
	private ImageView ibtn_now,ibtn_history;
	private TextView tv_totalandread;
	private int index = 0;
	private String state = "true";
	private PullDownView2 mPullDownView;
	private ListView lvShow;
	private MeetingAdapter meetingAdapter;
	public static final int GET_MEETING_MASTER = 1;
	public static final int GET_MEETING_READ = 2;
	private List<MeetingInfo> meetingInfos = new ArrayList<MeetingInfo>();
	private List<MeetingReadInfo> meetingreadInfos = new ArrayList<MeetingReadInfo>();
	private Map<String, String> data = new HashMap<String, String>();
	
	private ProgressDialog dialog;
	private View new_meet;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_meetinglist);
		showDialog();
		init();
		initView();
		initPulldownattr();
		getPageList(index,state);
		
		getReadNum();
		
//		CompanyTask task = new CompanyTask(CompanyTask.MEETINGPOST_MEETINGLISTACTIVITY_GET_METTING_READ, params);
//		CompanyService.newTask(task);
	}

	private void initView(){
		tv_totalandread = (TextView)findViewById(R.id.tv_totalandread);
		ibtn_now = (ImageView)findViewById(R.id.ibtn_now);
		ibtn_now.setOnClickListener(new myOnClickListener());
		ibtn_history = (ImageView)findViewById(R.id.ibtn_history);
		ibtn_history.setOnClickListener(new myOnClickListener ());
		mPullDownView = (PullDownView2) findViewById(R.id.pdvShow);
		lvShow = mPullDownView.getListView();
		meetingAdapter = new MeetingAdapter(this, meetingInfos);
		lvShow.setAdapter(meetingAdapter);
		lvShow.setOnItemClickListener(new myOnItemClickListener());
		
		mPullDownView.setOnPullDownListener(this);
		new_meet = findViewById(R.id.new_meet);
		new_meet.setOnClickListener(new myOnClickListener());
	}

	private class myOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			
			index=0;
			switch (v.getId()) {
			case R.id.ibtn_now:
				state = "true";
				showDialog();
				meetingInfos.clear();
				meetingAdapter.notifyDataSetChanged();
				mPullDownView.notifyDidMore();
				ibtn_now.setImageDrawable(getResources().getDrawable(R.drawable.meetnow_white));
				ibtn_history.setImageDrawable(getResources().getDrawable(R.drawable.meethistory_gery));
				getPageList(index, state);
				break;
			case R.id.ibtn_history:
				state = "false";
				showDialog();
				meetingInfos.clear();
				meetingAdapter.notifyDataSetChanged();
				mPullDownView.notifyDidMore();
				ibtn_history.setImageDrawable(getResources().getDrawable(R.drawable.meethistory_white));
				ibtn_now.setImageDrawable(getResources().getDrawable(R.drawable.meetnow_gery));
				getPageList(index, state);
				break;
			case R.id.new_meet:
				Intent huiyitongzhiguaunliIntent = new Intent(MeetingListActivity.this,GuanLiMainActivity.class);
				startActivity(huiyitongzhiguaunliIntent);
				break;
			}
		}

	}

	public class myOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Intent intent = new Intent(mContext,MeetingListDetailActivity.class);
			intent.putExtra("meetInfo", meetingInfos.get(position-1));
			startActivity(intent);
		}

	}

	/**
	 * 初始化pulldown属性
	 */
	private void initPulldownattr() {
		// 设置可以自动获取更多 滑到最后一个自动获取 改成false将禁用自动获取更多
		// mPullDownView.enableAutoFetchMore(true, 0);
		// 隐藏 并禁用尾部
		// mPullDownView.setHideFooter();
		// 显示并启用自动获取更多
		mPullDownView.setShowFooter();
		// 隐藏并且禁用头部刷新
		mPullDownView.setHideHeader();
		// 显示并且可以使用头部刷新
		// mPullDownView.setShowHeader();
	}
	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.valueOf(params[0].toString())) {
		case MeetingListActivity.GET_MEETING_MASTER:
			if (dialog!=null) {
				dialog.dismiss();				
			}
			if(params[1].toString().trim() != null &&!"".equals(params[1].toString().trim())){
				String value = params[1].toString();
				List<MeetingInfo> newlist = parseJsonToMeetList(value);
				if (newlist.size()>0&&newlist!=null) {
					meetingInfos.addAll(newlist);
					meetingAdapter.notifyDataSetChanged();
					mPullDownView.notifyDidMore();
				}
			}
			break;
		case MeetingListActivity.GET_MEETING_READ:
			if(params[1].toString().trim() != null &&!"".equals(params[1].toString().trim())){
				String value = params[1].toString();
				List<MeetingReadInfo> newlist = parseJsonToMeetReadList(value);
				if (newlist.size()>0 && newlist!=null) {
					meetingreadInfos.addAll(newlist);
					tv_totalandread.setText("本月"+meetingreadInfos.get(0).getValue1()
							+"条,已读" +meetingreadInfos.get(0).getValue2() + "条"+"读取率:"+meetingreadInfos.get(0).getValue3()+"%");
				} else{
					tv_totalandread.setText("本月"+0+"条,已读" +0 + "条  "+"读取率:"+0.0+"%");
				}
				break;
			}
		}
	}

	/**
	 * 
	 * @param 会议通知JSON转化为MeetingInfo集合
	 * @return
	 */
	private List<MeetingInfo> parseJsonToMeetList(String meetStr){
		List<MeetingInfo> list = new ArrayList<MeetingInfo>();		
		JSONArray jsonarray;
		try {
			jsonarray = new JSONArray(meetStr);
			int len = jsonarray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonarray.getJSONObject(i);
				MeetingInfo info = new MeetingInfo();
				info.setId(Integer.valueOf(object.getString("ID")));
				info.setTitle(object.getString("TITLE"));
				info.setDoc(object.getString("DOC"));
				info.setMeetingTime(object.getString("MEETING_TIME"));
				info.setMeetingAdd(object.getString("MEETING_ADD"));
				info.setCreateStaff(Integer.valueOf(object.getString("CREATE_STAFF")));
				info.setCreateDate(object.getString("CREATE_DATE"));
				info.setRecordCount(Integer.valueOf(object.getString("RecordCount")));
				info.setCreateStaffName(object.getString("CREATE_STAFF_NAME"));
				info.setMeetingStaff(object.getString("METTING_STAFFS"));
				list.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;		
	}

	private List<MeetingReadInfo> parseJsonToMeetReadList(String meetStr){
		List<MeetingReadInfo> list = new ArrayList<MeetingReadInfo>();		
		JSONArray jsonarray;
		try {
			jsonarray = new JSONArray(meetStr);
			int len = jsonarray.length();
			for (int i = 0; i < len; i++) {
				JSONObject object = jsonarray.getJSONObject(i);
				MeetingReadInfo info = new MeetingReadInfo();
				info.setValue1(object.getInt("VALUE1"));
				info.setValue2(object.getInt("VALUE2"));
				info.setValue3(object.getDouble("RATE"));
				list.add(info);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return list;		
	}
	
	@Override
	public void onMore() {
		index++;
		mPullDownView.notifyDidMore();
		getPageList(index,state);
	}

	/**
	 * 查询某页数据
	 * @param index
	 */
	private void getPageList(int index,String State) {
		Map<String, Object> params = new HashMap<String, Object>();
		data.put("State", State);
		data.put("page",""+index);
		data.put("rows","15");	
		params.put("data", data);
		CompanyTask task = new CompanyTask(
				CompanyTask.MEETINGPOST_MEETINGLISTACTIVITY_GET_MEETING_MASTER, params);
		CompanyService.newTask(task);
	}
	
	 private void getReadNum(){
		Map<String, Object> params=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
		params.put("data", data);
		CompanyTask task=new CompanyTask(CompanyTask.MEETINGPOST_MEETINGLISTACTIVITY_GET_METTING_READ, params);
		CompanyService.newTask(task);
	}
	 
	 private void showDialog(){
		 dialog=new ProgressDialog(mContext);
		 dialog.setTitle("温馨提示");
		 dialog.setMessage("数据信息正在加载中...");
		 dialog.setCanceledOnTouchOutside(false);
		 dialog.show();
	 }

}

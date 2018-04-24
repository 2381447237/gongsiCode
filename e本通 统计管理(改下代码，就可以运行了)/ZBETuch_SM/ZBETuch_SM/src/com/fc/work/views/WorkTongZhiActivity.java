package com.fc.work.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView2;
import com.fc.main.beans.PullDownView2.OnPullDownListener;
import com.fc.main.myservices.PersonService;
import com.fc.person.beans.PersonTask;
import com.fc.work.beans.WorkTongZhiAdapter;
import com.fc.work.beans.WorkTongzhiBean;
import com.fc.workstatus.views.NewWorkStatusActivity;
import com.fc.workstatus.views.WorkStatusActivity;
import com.fc.zbetuch_sm.R;

public class WorkTongZhiActivity extends Activity implements IActivity,OnPullDownListener{
	private TextView totalWorkNum;
	private PullDownView2 workPullDownView;
	private ListView workListView;
	private WorkTongZhiAdapter workTongZhiAdapter;
	private List<WorkTongzhiBean> workList=new ArrayList<WorkTongzhiBean>();
	private Activity mContext=this;

	private int index=0;
	private ImageButton newWorktz;

	//刷新工作通知
	public static final int REFRESH_WORKINFO=0;
	public static final int REFRESH_GET_READ_NUM=1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_worktongzhi);
		init();
		initView();
		initPullDown();

		getPageList(index);
		
		getReadNum();
	}

	@Override
	public void onMore() {
		// TODO Auto-generated method stub
		index++;
		getPageList(index);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);
	}

	private void initPullDown(){
		// 设置可以自动获取更多 滑到最后一个自动获取 改成false将禁用自动获取更多
		// mPullDownView.enableAutoFetchMore(true, 0);
		// 隐藏 并禁用尾部
		// mPullDownView.setHideFooter();
		// 显示并启用自动获取更多
		workPullDownView.setShowFooter();
		// 隐藏并且禁用头部刷新
		workPullDownView.setHideHeader();
		// 显示并且可以使用头部刷新
		// mPullDownView.setShowHeader();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case WorkTongZhiActivity.REFRESH_WORKINFO:
			if (!"".equals(params[1].toString().trim())) {
				List<WorkTongzhiBean> newList=parseJsonToList(params[1].toString().trim());
				if (newList.size()>0) {
					workList.addAll(newList);
				}
			}
			workTongZhiAdapter.notifyDataSetChanged();
			workPullDownView.notifyDidMore();
			break;
		case WorkTongZhiActivity.REFRESH_GET_READ_NUM:
			if (params[1].toString().trim()!=null&&!"".equals(params[1].toString().trim())) {
				parseJosoToNum(params[1].toString().trim());
			}
			break;

		default:
			break;
		}
	}


	private void initView(){
		totalWorkNum=(TextView) this.findViewById(R.id.tv_totalworkread);
		workPullDownView=(PullDownView2) this.findViewById(R.id.mWorkPullDownView);
		workListView=workPullDownView.getListView();

		workTongZhiAdapter=new WorkTongZhiAdapter(workList, mContext);
		workListView.setAdapter(workTongZhiAdapter);
		workListView.setOnItemClickListener(new MyOnItemClickListener());
		newWorktz = (ImageButton) findViewById(R.id.new_worktz);
		newWorktz.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.new_worktz:
					Intent newReportIntent = new Intent(WorkTongZhiActivity.this,NewWorkTongzhiActivity.class);
					startActivity(newReportIntent);
					break;
				}
			}
		});

		workPullDownView.setOnPullDownListener(this);
	}


	private void getPageList(int index){
		Map<String, Object> params=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
		data.put("page", index+"");
		data.put("rows", "15");
		params.put("data", data);
		PersonTask task=new PersonTask(PersonTask.WORKTONGZHIINFOACTIVITY_GET_WORKTONGZHILIST, params);
		PersonService.newTask(task);
	}

	private class MyOnItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(WorkTongZhiActivity.this,WorkTongzhiDetail.class);
			intent.putExtra("WorkTongzhiBean", (Serializable) workList.get(position-1));
			startActivity(intent);

		}

	}
	
	private List<WorkTongzhiBean> parseJsonToList(String str){
		List<WorkTongzhiBean > list=new ArrayList<WorkTongzhiBean>();
		JSONArray jsonArray;
		try {
			jsonArray = new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
			     WorkTongzhiBean bean=new WorkTongzhiBean();
			     JSONObject jsonObject=(JSONObject) jsonArray.get(i);
			     bean.setID(jsonObject.getInt("ID"));
			     bean.setTITLE(jsonObject.getString("TITLE"));
			     bean.setDOC(jsonObject.getString("DOC"));
			     bean.setNOTICE_TIME(jsonObject.getString("NOTICE_TIME"));
			     bean.setCREATE_STAFF(jsonObject.getInt("CREATE_STAFF"));
			     bean.setCREATE_DATE(jsonObject.getString("CREATE_DATE"));
			     bean.setUPDATE_STAFF(jsonObject.getInt("UPDATE_STAFF"));
			     bean.setUPDATE_DATE(jsonObject.getString("UPDATE_DATE"));
			     bean.setRecordCount(jsonObject.getInt("RecordCount"));
			     bean.setCREATE_STAFF_NAME(jsonObject.getString("CREATE_STAFF_NAME"));
			     list.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;		
	}
	
	private void getReadNum(){
		Map<String, Object> params=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
		params.put("data", data);
		PersonTask task=new PersonTask(PersonTask.WORKTONGZHIINFOACTIVITYDETAIL_GET_READNUM, params);
		PersonService.newTask(task);
	}
	
	private void parseJosoToNum(String str){
		int totalNum=0;
		int weiduNum=0;
		double rate=0.00;
		try {
			JSONArray jsonArray=new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject=(JSONObject) jsonArray.get(i);
				totalNum=jsonObject.getInt("VALUE1");
				weiduNum=jsonObject.getInt("VALUE2");
				rate=jsonObject.getDouble("RATE");
			}
			totalWorkNum.setText("本月"+totalNum+"条已读"+weiduNum+"条"+"读取率:"+rate+"%");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

package com.fc.recritmentmanager.views;

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
import com.fc.main.myservices.MainService;
import com.fc.main.myservices.MenuService;
import com.fc.recritmentmanager.bean.RedecodeAdapter;
import com.fc.recritmentmanager.bean.RedecodeInfo;
import com.fc.zbetuch_sm.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

public class RedecodeActivity extends Activity implements IActivity ,OnPullDownListener{
	private PullDownView2 myPullDownView;
	private ListView myListView;
	private List<RedecodeInfo> redecodeInfos=new ArrayList<RedecodeInfo>();
	private RedecodeAdapter adapter;
	private int index=0;
	public static final int ERECORD_INFO=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_redecode);
		init();
		initView();
		initPullDownView();
		getPageList(index);
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
		CompanyService.addActivity(this);
		MenuService.addInfoActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case ERECORD_INFO:
			if (!"".equals(params[1].toString().trim())||null!=params[1].toString().trim()) {
				List<RedecodeInfo> newInfos=parseJsonToInfo(params[1].toString().trim());
				if (newInfos.size()>0) {
					redecodeInfos.addAll(newInfos);
				}
				adapter.notifyDataSetChanged();
				myPullDownView.notifyDidMore();
			}
			break;

		default:
			break;
		}
	}
	
	private void initView(){
		myPullDownView=(PullDownView2) this.findViewById(R.id.pull_down_view);
		myListView=myPullDownView.getListView();
		adapter=new RedecodeAdapter(redecodeInfos, RedecodeActivity.this);
		myListView.setAdapter(adapter);
		myPullDownView.setOnPullDownListener(this);
	}
	private void initPullDownView(){
		// 设置可以自动获取更多 滑到最后一个自动获取 改成false将禁用自动获取更多
		// mWorkstatusPullDownView.enableAutoFetchMore(true, 0);
		// 隐藏 并禁用尾部
		// mWorkstatusPullDownView.setHideFooter();
		// 显示并启用自动获取更多
		myPullDownView.setShowFooter();
		// 隐藏并且禁用头部刷新
		myPullDownView.setHideHeader();
		// 显示并且可以使用头部刷新
		// mWorkstatusPullDownView.setShowHeader();
	}

	
	private void getPageList(int index){
		Map<String, Object> params=new HashMap<String, Object>();
		Map<String, String> data=new HashMap<String, String>();
		data.put("rows", "15");
		data.put("page", index+"");
		data.put("staff", getIntent().getStringExtra("staff"));
		params.put("data", data);
		CompanyTask task=new CompanyTask(CompanyTask.WORKS_INFO , params);
		CompanyService.newTask(task);
	}
	
	private List<RedecodeInfo> parseJsonToInfo(String str){
		List<RedecodeInfo> infos=new ArrayList<RedecodeInfo>();
		try {
			JSONArray jsonArray=new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject=(JSONObject) jsonArray.get(i);
				RedecodeInfo info=new RedecodeInfo();
				info.setID(jsonObject.getInt("ID"));
				info.setRecordCount(jsonObject.getInt("RecordCount"));
				info.setSTAFF(jsonObject.getInt("STAFF"));
				info.setGPS(jsonObject.getString("GPS"));
				info.setCREATE_TIME(jsonObject.getString("CREATE_TIME"));
				info.setDETAIL(jsonObject.getString("DETAIL"));
				info.setSFZ(jsonObject.getString("SFZ"));
				info.setStaff_Name(jsonObject.getString("Staff_Name"));
				infos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return infos;
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
            return getParent().onKeyDown(keyCode, event);
        }else{
            return super.onKeyDown(keyCode, event);
        }
	}
}

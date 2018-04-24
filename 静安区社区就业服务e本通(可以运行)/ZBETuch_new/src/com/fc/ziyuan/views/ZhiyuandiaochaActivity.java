package com.fc.ziyuan.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView;
import com.fc.main.beans.PullDownView.OnPullDownListener;
import com.fc.main.service.PersonService;
import com.fc.main.tools.HttpUrls_;
import com.fc.person.beans.PersonTask;
import com.fc.person.beans.StreetInformation;
import com.fc.person.beans.TypeInquiry;
import com.fc.ziyuan.bean.ResourcesAdapter;
import com.fc.ziyuan.bean.ResourcesInfo;

import com.test.zbetuch_news.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ZhiyuandiaochaActivity extends Activity implements IActivity,
		OnPullDownListener {

	private ListView myListView;
	private Spinner diaochaleixing_Spinner;
	private Spinner Spinner_jiedao;
	private Button but_diaocha;
	private int index = 0;
	ArrayAdapter<StreetInformation> streetAdapter;
	ArrayAdapter<TypeInquiry> inquiryAdapter;
	public static final int REFRESH_INFO = 0;

	private List<ResourcesInfo> resourcesInfos = new ArrayList<ResourcesInfo>();
	private ResourcesAdapter adapter;

	private ProgressDialog progressDialog;
	private PullDownView myPullDownView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ziyuandiaocha_list);
		init();
		initView();
		showDialog();
		initPullDownView();
		getPageList(index);
	}
	private void initPullDownView() {
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	@Override
	public void refresh(Object... params) {
		switch (Integer.parseInt(params[0].toString().trim())) {
		case REFRESH_INFO:
			if (progressDialog != null || progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			if (params[1].toString().trim() != null
					&& !"".equals(params[1].toString().trim())) {
				List<ResourcesInfo> newList = parseJsonToInfo(params[1]
						.toString().trim());
				if (newList.size() > 0) {
					//resourcesInfos.clear();
					resourcesInfos.addAll(newList);
				}

				adapter.notifyDataSetChanged();
				myPullDownView.notifyDidMore();
			}

			break;

		default:
			break;
		}

	}

	private void initView() {
		myPullDownView = (PullDownView) this
				.findViewById(R.id.my_detail_pulldownview2);
		myListView =  myPullDownView.getListView();
		adapter = new ResourcesAdapter(ZhiyuandiaochaActivity.this,
				resourcesInfos);
		myListView.setAdapter(adapter);


		myPullDownView.setOnPullDownListener(this);
		myListView.setOnItemClickListener(new MyItemClickListener());
		
		
		// 调查Spinner
		diaochaleixing_Spinner = (Spinner) findViewById(R.id.diaochaleixing);
		inquiryAdapter = new ArrayAdapter<TypeInquiry>(this,
				android.R.layout.simple_spinner_item,
				new TypeInquiry().findAll());
		inquiryAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		diaochaleixing_Spinner.setAdapter(inquiryAdapter);

		Spinner_jiedao = (Spinner) findViewById(R.id.Spinner_jiedao);
		streetAdapter = new ArrayAdapter<StreetInformation>(this,
				android.R.layout.simple_spinner_item,
				new StreetInformation().GetStreetList());
		streetAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Spinner_jiedao.setAdapter(streetAdapter);

		but_diaocha = (Button) findViewById(R.id.but_diaocha);
	//	but_diaocha.setOnClickListener(btn_diaocha);
		but_diaocha.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				showDialog();
				getData();
				
			}
		});

	}
	private void getData(){
		
		String streeId = streetId();
		String type = diaochaleixing_Spinner.getSelectedItem().toString();
		
		if(streeId != null && streeId.trim().length() > 0&&type != null && type.trim().length() > 0
				&& !type.trim().equals("请选择")){
		
		OkHttpUtils.post().url(HttpUrls_.HttpURL+"/Json/GetResourceSurvey.aspx").addParams("page","0").addParams("STREET", streeId).addParams("TYPE", type).
		addParams("rows","15").addHeader("cookie", HttpUrls_.staffName).build().execute(new StringCallback() {
			
			@Override
			public void onResponse(String str) {
				if (progressDialog != null || progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				resourcesInfos.clear();
				index=0;
				List<ResourcesInfo> newList = parseJsonToInfo(str);
				if (newList.size() > 0) {
					resourcesInfos.addAll(newList);
				}

				adapter.notifyDataSetChanged();
				myPullDownView.notifyDidMore();
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				if (progressDialog != null || progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				Toast.makeText(ZhiyuandiaochaActivity.this,"请检查网络",0).show();
			}
		});
		}else if(streeId != null && streeId.trim().length() > 0){
			OkHttpUtils.post().url(HttpUrls_.HttpURL+"/Json/GetResourceSurvey.aspx").addParams("page","0").addParams("STREET", streeId).
			addParams("rows","15").addHeader("cookie", HttpUrls_.staffName).build().execute(new StringCallback() {
				
				@Override
				public void onResponse(String str) {
					if (progressDialog != null || progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					resourcesInfos.clear();
					index=0;
					List<ResourcesInfo> newList = parseJsonToInfo(str);
					if (newList.size() > 0) {
						resourcesInfos.addAll(newList);
					}

					adapter.notifyDataSetChanged();
					myPullDownView.notifyDidMore();
				}
				
				@Override
				public void onError(Call arg0, Exception arg1) {
					if (progressDialog != null || progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					Toast.makeText(ZhiyuandiaochaActivity.this,"请检查网络",0).show();
				}
			});
		}else if(type != null && type.trim().length() > 0
				&& !type.trim().equals("请选择")){
			
			OkHttpUtils.post().url(HttpUrls_.HttpURL+"/Json/GetResourceSurvey.aspx").addParams("page","0").addParams("TYPE", type).
			addParams("rows","15").addHeader("cookie", HttpUrls_.staffName).build().execute(new StringCallback() {
				
				@Override
				public void onResponse(String str) {
					if (progressDialog != null || progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					resourcesInfos.clear();
					index=0;
					List<ResourcesInfo> newList = parseJsonToInfo(str);
					if (newList.size() > 0) {
						resourcesInfos.addAll(newList);
					}

					adapter.notifyDataSetChanged();
					myPullDownView.notifyDidMore();
				}
				
				@Override
				public void onError(Call arg0, Exception arg1) {
					if (progressDialog != null || progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					Toast.makeText(ZhiyuandiaochaActivity.this,"请检查网络",0).show();
				}
			});
			
		}else {
			
			OkHttpUtils.post().url(HttpUrls_.HttpURL+"/Json/GetResourceSurvey.aspx").addParams("page","0").
			addParams("rows","15").addHeader("cookie", HttpUrls_.staffName).build().execute(new StringCallback() {
				
				@Override
				public void onResponse(String str) {
					if (progressDialog != null || progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					resourcesInfos.clear();
					index=0;
					List<ResourcesInfo> newList = parseJsonToInfo(str);
					if (newList.size() > 0) {
						resourcesInfos.addAll(newList);
					}

					adapter.notifyDataSetChanged();
					myPullDownView.notifyDidMore();
				}
				
				@Override
				public void onError(Call arg0, Exception arg1) {
					if (progressDialog != null || progressDialog.isShowing()) {
						progressDialog.dismiss();
					}
					Toast.makeText(ZhiyuandiaochaActivity.this,"请检查网络",0).show();
				}
			});
			
		}
		
	}
	
	/**
	 * 查询
	 */
	private OnClickListener btn_diaocha = new OnClickListener() {

		@Override
		public void onClick(View v) {
			showDialog();
			resourcesInfos.clear();
			index=0;
			getPageList(0);
//			data.put("page", "" + index);
//			data.put("rows", "15");
//			params.put("data", data);
//			PersonTask task = new PersonTask(
//					PersonTask.ZHIYUANDIAOCHAACTIVITY_GETZHIYUANDIAOCHAINFO,
//					params);
//			PersonService.newTask(task);
			
			// diaochaleixing_Spinner.getSelectedItem().toString();

		}
	};

	private String streetId() {
		List<StreetInformation> list = new StreetInformation().GetStreetList();
		StreetInformation sf = null;
		for (StreetInformation sif : list) {
			if (sif.getStreetName().equals(
					Spinner_jiedao.getSelectedItem().toString())) {
				sf = sif;
			}
		}
		if (sf.getStreetName().equals("请选择")) {
			return null;
		}
		return sf.getStreetId();
	}

	private void getPageList(int index) {
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		String type = diaochaleixing_Spinner.getSelectedItem().toString();
		String streeId = streetId();
		if (type != null && type.trim().length() > 0
				&& !type.trim().equals("请选择")) {
			data.put("TYPE", type);
		}
		if (streeId != null && streeId.trim().length() > 0) {
			data.put("STREET", streeId);
		}
		
		
//		Map<String, Object> params = new HashMap<String, Object>();
//		Map<String, String> data = new HashMap<String, String>();
		data.put("page", "" + index);
		data.put("rows", "15");
		params.put("data", data);
		PersonTask task = new PersonTask(
				PersonTask.ZHIYUANDIAOCHAACTIVITY_GETZHIYUANDIAOCHAINFO, params);
		PersonService.newTask(task);
	}

	private List<ResourcesInfo> parseJsonToInfo(String str) {
		List<ResourcesInfo> infos = new ArrayList<ResourcesInfo>();
		try {
			JSONArray jsonArray = new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				ResourcesInfo info = new ResourcesInfo();
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				info.setID(jsonObject.getInt("ID"));
				info.setTYPE(jsonObject.getString("TYPE"));
				info.setCREATE_DATE(jsonObject.getString("CREATE_DATE"));
				info.setCREATE_STAFF(jsonObject.getInt("CREATE_STAFF"));
				info.setMASTER_ID(jsonObject.getInt("MASTER_ID"));
				info.setSET_TIME(jsonObject.getString("SET_TIME"));
				info.setXUCHA(jsonObject.getInt("XUCHA"));
				info.setYICHA(jsonObject.getInt("YICHA"));
				info.setName(jsonObject.getString("NAME"));
				infos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return infos;
	}

	private class MyItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int location,
				long position) {

			// TODO Auto-generated method stub
			ResourcesInfo resourcesInfo = resourcesInfos.get(location-1);
			if ("启航".equals(resourcesInfo.getTYPE())) {
				Intent qihangIntent = new Intent(ZhiyuandiaochaActivity.this,
						QiHangListActivity.class);
				qihangIntent.putExtra("infos",
						(Serializable) resourcesInfos.get(location-1));
				startActivity(qihangIntent);
			} else {
				Intent intent = new Intent(ZhiyuandiaochaActivity.this,
						ZhiYuanDetailListActivity.class);
				intent.putExtra("infos",
						(Serializable) resourcesInfos.get(location-1));
				startActivity(intent);
			}

		}

	}

	public void showDialog() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setIcon(android.R.drawable.ic_dialog_info);
		progressDialog.setTitle("加载提示");
		progressDialog.setMessage("信息加载中，请稍后。。。");
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();
	}

}

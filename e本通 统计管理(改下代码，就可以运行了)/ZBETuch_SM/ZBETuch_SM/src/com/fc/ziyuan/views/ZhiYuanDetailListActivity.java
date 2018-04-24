package com.fc.ziyuan.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.main.beans.IActivity;
import com.fc.main.beans.PullDownView2;
import com.fc.main.beans.PullDownView2.OnPullDownListener;
import com.fc.main.myservices.PersonService;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.PersonTask;
import com.fc.zbetuch_sm.R;
import com.fc.ziyuan.bean.ResourcesDetailAdapter;
import com.fc.ziyuan.bean.ResourcesDetailInfo;
import com.fc.ziyuan.bean.ResourcesInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class ZhiYuanDetailListActivity extends Activity implements IActivity,OnPullDownListener{
	private TextView type_Name,lblNum;
	private EditText idcardText;
	private Button btnQuery,btnYicha,btnWeiCha;
	private PullDownView2 myPullDownView;
	private ListView myListView;
	private ResourcesInfo resourcesInfo;
	private ResourcesDetailAdapter detailAdapter;
	private List<ResourcesDetailInfo> detailInfos=new ArrayList<ResourcesDetailInfo>();
	private int index=0;
	private PopupWindow popupwindow;
	private RadioGroup rg;
	private RadioButton rb1,rb2;
	private Map<String, String> data;
	private ProgressDialog progressDialog;
	
	public static final int REFRESH_INFO=0;
	public static final int REFRESH_SFZ_INFO=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		data=new HashMap<String, String>();
		progressDialog=new ProgressDialog(this);
		setContentView(R.layout.activity_zhiyuan_detail_list);
		resourcesInfo=(ResourcesInfo) getIntent().getSerializableExtra("infos");
		init();
		initView();
		initPullDownView();
		rb1.setChecked(true);
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);
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
		case REFRESH_INFO:
			if (progressDialog!=null||progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			if (!"".equals(params[1].toString().trim())||params[1].toString().trim()!=null){
				List<ResourcesDetailInfo> newInfos=parseJsonToList(params[1].toString().trim());
				if (newInfos.size()>0) {
					detailInfos.addAll(newInfos);
				}
				detailAdapter.notifyDataSetChanged();
				myPullDownView.notifyDidMore();
				if (detailInfos.size()>0) {
					StringBuffer stringBuffer=null;
					if (rb1.isChecked()) {
						stringBuffer=new StringBuffer();
						stringBuffer.append("未查");
					}else if (rb2.isChecked()) {
						stringBuffer=new StringBuffer();
						stringBuffer.append("已查");
					}
					stringBuffer.append("共"+detailInfos.get(0).getRecordCount()+"条数据");
					lblNum.setText(stringBuffer.toString());
				} else {
					lblNum.setText("总共0条数据");
				}
			}
			break;
			
		case REFRESH_SFZ_INFO:
			if (progressDialog!=null||progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			if (!"".equals(params[1].toString().trim())||params[1].toString().trim()!=null){
				detailInfos.clear();
				List<ResourcesDetailInfo> newInfos=parseJsonToList(params[1].toString().trim());
				if (newInfos.size()>0) {
					detailInfos.addAll(newInfos);
				}
				detailAdapter.notifyDataSetChanged();
				myPullDownView.notifyDidMore();
				if (detailInfos.size()>0) {
					lblNum.setText("共"+detailInfos.get(0).getRecordCount()+"条数据");
				} else {
					lblNum.setText("共0条数据");
				}
				
			}
			break;
			

		default:
			break;
		}
	}
	
	private void initView(){
		type_Name=(TextView) this.findViewById(R.id.type_name);
		lblNum=(TextView) this.findViewById(R.id.lblNum);
		idcardText=(EditText) this.findViewById(R.id.shiye_IdCard);
		idcardText.setOnTouchListener(new MyOnTouchListener());
		idcardText.setInputType(InputType.TYPE_NULL);
		btnQuery=(Button) this.findViewById(R.id.btnQuery_shiye);
		btnQuery.setOnClickListener(new MyOnClickListener());
		btnYicha=(Button) this.findViewById(R.id.btn_yicha23);
		btnWeiCha=(Button) this.findViewById(R.id.btn_weicha23);
		
		rg=(RadioGroup) this.findViewById(R.id.my_group);
		rb1=(RadioButton) this.findViewById(R.id.btn_weicha3);
		rb2=(RadioButton) this.findViewById(R.id.btn_yicha3);
		rg.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
		
		myPullDownView=(PullDownView2) this.findViewById(R.id.my_detail_pulldownview);
		myListView=myPullDownView.getListView();
		detailAdapter=new ResourcesDetailAdapter(ZhiYuanDetailListActivity.this, detailInfos);
		myListView.setAdapter(detailAdapter);
		myPullDownView.setOnPullDownListener(this);
		myListView.setOnItemClickListener(new MyOnitemClickListener());
		
		
		if ("无业".equals(resourcesInfo.getTYPE())) {
			type_Name.setText("无业调查");
		} else if ("失业".equals(resourcesInfo.getTYPE())) {
			type_Name.setText("失业调查");
		}
	}
	
	
	private class MyOnitemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			if ("无业".equals(resourcesInfo.getTYPE())) {
				intent.setClass(ZhiYuanDetailListActivity.this, WuYeDetailActivity.class);
			} else if ("失业".equals(resourcesInfo.getTYPE())){
				intent.setClass(ZhiYuanDetailListActivity.this, ShiYeDetailActivity.class);
			} 
			intent.putExtra("infos",(Serializable) detailInfos.get(position-1));
			startActivity(intent);
		}
		
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
		data.put("page", ""+index);
		data.put("rows", "15");
		data.put("Master_id",""+resourcesInfo.getMASTER_ID());
		data.put("typeName", resourcesInfo.getTYPE());
		params.put("data", data);
		PersonTask task=new PersonTask(PersonTask.ZHIYUANDETAILACTIVITY_GET_INFOS, params);
		PersonService.newTask(task);
		
	}
	
	private List<ResourcesDetailInfo> parseJsonToList(String str){
		List<ResourcesDetailInfo> infos=new ArrayList<ResourcesDetailInfo>();
		try {
			JSONArray jsonArray=new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				ResourcesDetailInfo info=new ResourcesDetailInfo();
				JSONObject jsonObject=(JSONObject) jsonArray.get(i);
				info.setID(jsonObject.getInt("ID"));
				info.setMASTER_ID(jsonObject.getInt("MASTER_ID"));
				info.setJD(jsonObject.getString("JD"));
				info.setJW(jsonObject.getString("JW"));
				info.setNAME(jsonObject.getString("NAME"));
				info.setSEX(jsonObject.getString("SEX"));
				info.setSFZ(jsonObject.getString("SFZ"));
				info.setMZ(jsonObject.getString("MZ"));
				info.setMARK(jsonObject.getString("MARK"));
				info.setCSRQ(jsonObject.getString("CSRQ"));
				info.setEDU(jsonObject.getString("EDU"));
				info.setHKDZ(jsonObject.getString("HKDZ"));
				info.setMQZK(jsonObject.getString("MQZK"));
				info.setMDRQ(jsonObject.getString("MDRQ"));
				info.setDQYX(jsonObject.getString("DQYX"));
				if ("失业".equals(resourcesInfo.getTYPE())) {
					info.setZJSYDJRQ(jsonObject.getString("ZJSYDJRQ"));
					info.setSYDJYXQ(jsonObject.getString("SYDJYXQ"));
				}
				info.setCJR(jsonObject.getString("CJR"));
				info.setNEW_MQZK(jsonObject.getString("NEW_MQZK"));
				info.setNEW_DQYX(jsonObject.getString("NEW_DQYX"));
				info.setCREATE_DATE(jsonObject.getString("CREATE_DATE"));
				info.setUPDATE_DATE(jsonObject.getString("UPDATE_DATE"));
				info.setUPDATE_STAFF(jsonObject.getInt("UPDATE_STAFF"));
				info.setSURVEY_DATE(jsonObject.getString("SURVEY_DATE"));
				info.setRecordCount(jsonObject.getInt("RecordCount"));
				info.setCard_type(jsonObject.getString("card_type"));
				info.setType(jsonObject.getString("type"));
				info.setIs_Update(jsonObject.getBoolean("Is_Update"));
				infos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return infos;
	}


	@Override
	public void onMore() {
		// TODO Auto-generated method stub
		index++;
		getPageList(index);
	}

	private class MyOnTouchListener implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.shiye_IdCard:
				idcardText.setFocusable(true);
				idcardText.setEnabled(true);
				idcardText.setCursorVisible(true); //允许光标可见
				idcardText.requestFocus();
				//showPopupWindow(v);
				popupwindow = MainTools.showPopupWindow(ZhiYuanDetailListActivity.this, popupwindow, idcardText);
				popupwindow.showAsDropDown(idcardText, 0, 0);
				break;
			}
			return false;
		}
		
	}
	
	private class MyOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if ("".equals(idcardText.getText().toString().trim())||idcardText.getText().toString().trim()==null) {
				Toast.makeText(ZhiYuanDetailListActivity.this, "身份证号不能为空", Toast.LENGTH_LONG).show();
				return ;
			}
			if (idcardText.getText().toString().trim().length()!=18) {
				Toast.makeText(ZhiYuanDetailListActivity.this, "请输入18位身份证号", Toast.LENGTH_LONG).show();
				return ;
			}
			Map<String, Object> params=new HashMap<String, Object>();
			Map<String, String> data=new HashMap<String, String>();
			data.put("page", ""+index);
			data.put("rows", "15");
			data.put("Master_id",""+resourcesInfo.getMASTER_ID());
			data.put("typeName", resourcesInfo.getTYPE());
			data.put("sfz", idcardText.getText().toString().trim());
			params.put("data", data);
			PersonTask task=new PersonTask(PersonTask.ZHIYUANDETAILACTIVITY_GET_SFZ_IFNO, params);
			PersonService.newTask(task);
		}
		
	}
	
	private class MyOnCheckedChangeListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			index=0;
			detailInfos.clear();
			detailAdapter.notifyDataSetChanged();
			myPullDownView.notifyDidMore();
			lblNum.setText(" ");
			if (arg1==R.id.btn_weicha) {
				showDialog();
				data=new HashMap<String, String>();
				data.put("type", "1");
			}else if (arg1==R.id.btn_yicha) {
				showDialog();
				data=new HashMap<String, String>();
				data.put("type", "0");
			}
			getPageList(index);
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

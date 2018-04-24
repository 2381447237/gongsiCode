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
import com.fc.ziyuan.bean.QiHangAdapter;
import com.fc.ziyuan.bean.QiHangBeanInfo;
import com.fc.ziyuan.bean.ResourcesInfo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class QiHangListActivity extends Activity implements IActivity,OnPullDownListener{
	private TextView type_Name,lblNum;
	private EditText idcardText;
	private Button btnQuery,btnYicha,btnWeiCha;
	private PullDownView2 myPullDownView;
	private ListView myListView;
	
	
	private PopupWindow popupwindow;
	private RadioGroup rg;
	private RadioButton rb1,rb2;
	private Map<String, String> data;
	
	private ProgressDialog progressDialog;
	private QiHangAdapter qiHangAdapter;
	private List<QiHangBeanInfo> beanInfos=new ArrayList<QiHangBeanInfo>();
	
	private QiHangBeanInfo hangBeanInfo;
	
	private ResourcesInfo resourcesInfo;
	
	public static final int REFRESH_INFO=0;
	public static final int REFRESH_SFZ_INFO=1;
	
	private int index;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_qihang_list);
		resourcesInfo=(ResourcesInfo) getIntent().getSerializableExtra("infos");
		init();
		initView();
		initPullDownView();
		
		rb1.setChecked(true);
	}
	
	private void getPageList(int index){
		Map<String, Object> params=new HashMap<String, Object>();
		data.put("page", ""+index);
		data.put("rows", "15");
		data.put("Master_id",""+resourcesInfo.getMASTER_ID());
		params.put("data", data);
		PersonTask task=new PersonTask(PersonTask.QIHANGDIAOCHAOACTIVITY_GET_INFOS, params);
		PersonService.newTask(task);
	}
	
	@Override
	public void onMore() {
		// TODO Auto-generated method stub
		index++;
		getPageList(index);
	}
	
	
	
	private void initView(){
		type_Name=(TextView) this.findViewById(R.id.type_name);
		lblNum=(TextView) this.findViewById(R.id.lblNum);
		idcardText=(EditText) this.findViewById(R.id.shiye_IdCard);
		idcardText.setOnTouchListener(new MyOnTouchListener());
		idcardText.setInputType(InputType.TYPE_NULL);
		btnQuery=(Button) this.findViewById(R.id.btnQuery_shiye);
		btnQuery.setOnClickListener(new MyOnClickListener());
		btnYicha=(Button) this.findViewById(R.id.btn_yicha);
		btnWeiCha=(Button) this.findViewById(R.id.btn_weicha);
		
		rg=(RadioGroup) this.findViewById(R.id.my_group);
		rb1=(RadioButton) this.findViewById(R.id.btn_weicha_rg);
		rb2=(RadioButton) this.findViewById(R.id.btn_yicha_rg);
		rg.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
		
		myPullDownView=(PullDownView2) this.findViewById(R.id.my_detail_pulldownview);
		myListView=myPullDownView.getListView();
		qiHangAdapter=new QiHangAdapter(this, beanInfos);
		myListView.setAdapter(qiHangAdapter);
		myPullDownView.setOnPullDownListener(this);
		myListView.setOnItemClickListener(new MyOnitemClickListener());
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);
	}
	
	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case REFRESH_INFO:
			if (progressDialog!=null&&progressDialog.isShowing()) {
				progressDialog.dismiss();
			}
			if (params[1].toString().trim()!=null&&!"".equals(params[1].toString().trim())) {
				List<QiHangBeanInfo> newHangBeanInfos=parseToJsonQiHangInfo(params[1].toString().trim());
				if (newHangBeanInfos.size()>0) {
					beanInfos.addAll(newHangBeanInfos);
				}
				qiHangAdapter.notifyDataSetChanged();
				myPullDownView.notifyDidMore();
				
				if (beanInfos.size()>0) {
					StringBuffer buffer=null;
					if (rb1.isChecked()) {
						buffer=new StringBuffer();
						buffer.append("未查");
					}else if (rb2.isChecked()) {
						buffer=new StringBuffer();
						buffer.append("已查");
					}
					buffer.append("共"+beanInfos.get(0).getRecordCount()+"条数据");
					lblNum.setText(buffer.toString().trim());
				} else {
					lblNum.setText("共0条数据");
				}
			}
			break;
			
		case REFRESH_SFZ_INFO:
			if (params[1].toString().trim()!=null&&!"".equals(params[1].toString().trim())) {
				List<QiHangBeanInfo> newHangBeanInfos=parseToJsonQiHangInfo(params[1].toString().trim());
				beanInfos.clear();
				if (newHangBeanInfos.size()>0) {
					beanInfos.addAll(newHangBeanInfos);
				}
				qiHangAdapter.notifyDataSetChanged();
				myPullDownView.notifyDidMore();
				
				if (beanInfos.size()>0) {
					lblNum.setText("共"+beanInfos.get(0).getRecordCount()+"条数据");
				} else {
					lblNum.setText("共0条数据");
				}
			}
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
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
				popupwindow = MainTools.showPopupWindow(QiHangListActivity.this, popupwindow, idcardText);
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
				Toast.makeText(QiHangListActivity.this, "身份证号不能为空", Toast.LENGTH_LONG).show();
				return ;
			}
			if (idcardText.getText().toString().trim().length()!=18) {
				Toast.makeText(QiHangListActivity.this, "请输入18位身份证号", Toast.LENGTH_LONG).show();
				return ;
			}
			Map<String, Object> params=new HashMap<String, Object>();
			Map<String, String> data=new HashMap<String, String>();
			data.put("page", ""+index);
			data.put("rows", "15");
			data.put("Master_id",""+resourcesInfo.getMASTER_ID());
//			data.put("typeName", resourcesInfo.getTYPE());
			data.put("sfz", idcardText.getText().toString().trim());
			params.put("data", data);
			PersonTask task=new PersonTask(PersonTask.QIHANGACTIVITY_GET_SFZ_IFNO, params);
			PersonService.newTask(task);
		}
		
	}

	private class MyOnitemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int location,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent=new Intent(QiHangListActivity.this, QiHangDetailActivity.class);
			intent.putExtra("beanInfos",(Serializable) beanInfos.get(location-1));
			startActivity(intent);
		}
	}
	
	private class MyOnCheckedChangeListener implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			index=0;
			beanInfos.clear();
			qiHangAdapter.notifyDataSetChanged();
			myPullDownView.notifyDidMore();
			lblNum.setText(" ");
			if (arg1==R.id.btn_weicha_rg) {
				showDialog();
				data=new HashMap<String, String>();
				data.put("type", "1");
			}else if (arg1==R.id.btn_yicha_rg) {
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
	
	private List<QiHangBeanInfo> parseToJsonQiHangInfo(String str){
		List<QiHangBeanInfo> beanInfos=new ArrayList<QiHangBeanInfo>();
		try {
			JSONArray jsonArray=new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				QiHangBeanInfo beanInfo=new QiHangBeanInfo();
				JSONObject object=(JSONObject) jsonArray.get(i);
				beanInfo.setBYRQ(object.getString("BYRQ"));
				beanInfo.setCREATE_DATE(object.getString("CREATE_DATE"));
				beanInfo.setCSRQ(object.getString("CSRQ"));
				beanInfo.setCYJX(object.getString("CYJX"));
				beanInfo.setDLZP(object.getString("DLZP"));
				beanInfo.setEDU(object.getString("EDU"));
				beanInfo.setFWDQ(object.getString("FWDQ"));
				beanInfo.setFWDZ(object.getString("FWDZ"));
				beanInfo.setFWJD(object.getString("FWJD"));
				beanInfo.setFWJW(object.getString("FWJW"));
				beanInfo.setHKDZ(object.getString("HKDZ"));
				beanInfo.setHKQX(object.getString("HKQX"));
				beanInfo.setID(object.getInt("ID"));
				beanInfo.setJD(object.getString("JD"));
				beanInfo.setJW(object.getString("JW"));
				beanInfo.setJYFW1(object.getString("JYFW1"));
				beanInfo.setJYFW2(object.getString("JYFW2"));
				beanInfo.setJYFW3(object.getString("JYFW3"));
				beanInfo.setJYLX(object.getString("JYLX"));
				beanInfo.setJYZT(object.getString("JYZT"));
				beanInfo.setLDSC(object.getString("LDSC"));
				beanInfo.setLXDZ(object.getString("LXDZ"));
				beanInfo.setMARK(object.getString("MARK"));
				beanInfo.setMZ(object.getString("MZ"));
				beanInfo.setNAME(object.getString("NAME"));
				beanInfo.setNEW_DQYX(object.getString("NEW_DQYX"));
				beanInfo.setNEW_MQZK(object.getString("NEW_MQZK"));
				beanInfo.setPHONE(object.getString("PHONE"));
				beanInfo.setSEX(object.getString("SEX"));
				beanInfo.setSFZ(object.getString("SFZ"));
				beanInfo.setSTLD_END(object.getString("STLD_END"));
				beanInfo.setSTLD_START(object.getString("STLD_START"));
				beanInfo.setSURVEY_DATE(object.getString("SURVEY_DATE"));
				beanInfo.setSY_NO(object.getString("SY_NO"));
				beanInfo.setUPDATE_DATE(object.getString("UPDATE_DATE"));
				beanInfo.setUPDATE_STAFF(object.getString("UPDATE_STAFF"));
				beanInfo.setWQJY(object.getString("WQJY"));
				beanInfo.setXWCS1(object.getString("XWCS1"));
				beanInfo.setXWCS2(object.getString("XWCS2"));
				beanInfo.setXWCS3(object.getString("XWCS3"));
				beanInfo.setXWPX1(object.getString("XWPX1"));
				beanInfo.setXWPX2(object.getString("XWPX2"));
				beanInfo.setXWPX3(object.getString("XWPX3"));
				beanInfo.setXWYX(object.getString("XWYX"));
				beanInfo.setZJZD(object.getString("ZJZD"));
				beanInfo.setZYJX(object.getString("ZYJX"));
				beanInfo.setZYPX(object.getString("ZYPX"));
				beanInfo.setZYZD(object.getString("ZYZD"));
				beanInfo.setRecordCount(object.getInt("RecordCount"));
				beanInfos.add(beanInfo);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beanInfos;
	}

}

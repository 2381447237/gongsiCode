package com.fc.meetdoc.views;

import java.util.HashMap;
import java.util.Map;

import com.fc.meetdoc.R;
import com.fc.meetdoc.entity.MyAdapter;
import com.fc.meetdoc.face.IActivity;
import com.fc.meetdoc.service.MainService;
import com.fc.meetdoc.tools.MainTools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class SelectIPforSend extends Activity implements IActivity,OnClickListener{
	private ListView listView_select;
	private Button button_select_all,button_select_no,button_select_ok;
	private String all_ip[]=null;//取出share中所有ip
	private Intent intent=null;
	/**
	 * 存listview项是否checked
	 */
	public static Map<Integer,Boolean> setmap=new HashMap<Integer,Boolean>();//存listview是否checked值
	private MyAdapter myadapter=null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.some_ipl);
    	
    	init();
    	
    }
	@Override
	public void init() {
		// TODO Auto-generated method stub
		MainService.addActivity(this);
		all_ip=MainTools.getAllIP(this);
		
		listView_select=(ListView) findViewById(R.id.listView_select);
		button_select_all=(Button) findViewById(R.id.button_select_all);
		button_select_no=(Button) findViewById(R.id.button_select_no);
		button_select_ok=(Button) findViewById(R.id.button_select_ok);
		button_select_all.setOnClickListener(this);
		button_select_no.setOnClickListener(this);
		button_select_ok.setOnClickListener(this);
		
		myadapter=new MyAdapter(this, all_ip);
		checkedstate();
		changeUI();
		
		//listview点击事件
		listView_select.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				//记录listview项的选中状态 ，true为选中，false为未选中
				setmap.put(position,!setmap.get(position));
				//点击选中ip
				myadapter.changelist(position);
				//更新 ui
				changeUI();
			}
		});
		
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.button_select_ok:			
			if(MyAdapter.setmsg!=null&&MyAdapter.setmsg.size()>0){
				setResult(5);
				if(MyAdapter.setmsg!=null&&MyAdapter.setmsg.size()>0){
	                for(String s:MyAdapter.setmsg){
	                	
	                		Log.e("setmsg","setmsg="+ s);	
	                }
				}
				
				 finish();
			}
			else{
				Toast.makeText(this, "请选择要分享的IP！", Toast.LENGTH_SHORT).show();
			}
			
//			String ak[]=null;
//			if(MyAdapter.setmsg!=null&&MyAdapter.setmsg.size()>0){
//                for(String s:MyAdapter.setmsg){               	
//                		Log.e("s","s="+s);
//                }
//			}
           
			break;
		case R.id.button_select_all:
			
			MyAdapter.setmsg.clear();
			if(all_ip!=null&&all_ip.length>0){
		    	for(int i=0;i<all_ip.length;i++){
					setmap.put(i,true);
					
					//点击选中ip
					myadapter.changelist(i);
				}
		    	}

			//更新 ui
			changeUI();
			
//			if(MyAdapter.setmsg!=null&&MyAdapter.setmsg.size()>0){
//                for(String s:MyAdapter.setmsg){               	
//                		Log.e("setmsg","setmsg="+ s);	
//                }
//			}
			
			break;
		case R.id.button_select_no:
			MyAdapter.setmsg.clear();
			if(all_ip!=null&&all_ip.length>0){
		    	for(int i=0;i<all_ip.length;i++){
					setmap.put(i,false);
					
					//点击选中ip
					myadapter.changelist(i);
				}
		    	}

			//更新 ui
			changeUI();
			
			
			break;
		}
		
	}
	/**
	 * 预设勾选框为不选状态
	 */
    public void checkedstate(){
    	if(all_ip!=null&&all_ip.length>0){
    	for(int i=0;i<all_ip.length;i++){
			setmap.put(i,false);
		}
    	}
    	
		MyAdapter.setmsg.clear();
    }
    /**
     * 更新本页ui
     */
    public void changeUI(){
    	if(all_ip!=null&&all_ip.length>0){
    	myadapter.notifyDataSetChanged();
    	listView_select.setAdapter(myadapter);
    	}else{
    		// 吐司显示
    	    Toast.makeText(SelectIPforSend.this, "无IP显示", 1).show();
    	}
    }
}

package com.fc.wenjuan.views;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import okhttp3.Call;
import com.fc.main.tools.HttpUrls_;
import com.fc.person.views.PersoninfoMainActivity;
import com.fc.person.views.PersonqueryListActivity2;
import com.fc.wenjuan.beans.FamilyInfo;
import com.fc.wenjuan.beans.WenJuanPersonInfo;
import com.fc.wenjuan.beans.WenJuanPersonListAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.zbetuch_news.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowPersionDetailInfo extends Activity implements OnClickListener {
	private TextView qx_tv, pid_tv, number_tv, name_tv, sex_tv, sfz_tv, edu_tv,
			zt_tv, jd_tv, jw_tv, lxdz_tv, phone, dzszqx_tv,title_name_tv;
	public Button disscus, no_disscus,population,family;
	private Button persion_info, history_list;
	public LinearLayout lv_title;
	public WenJuanPersonInfo info;
	private boolean rb;
	private int position;

	public ListView lv;
	private WenJuanPersonListAdapter adapter;
	private List<FamilyInfo> listInfo=new ArrayList<FamilyInfo>();
	public static String  familyListUrl="/Json/Get_Home.aspx";
	public static String answerUrl="/Json/Get_Tb_Home_Answer_Info.aspx";
	private ProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wenjuan_persion_info);
		info = (WenJuanPersonInfo) getIntent().getSerializableExtra("info");
		
		rb = getIntent().getBooleanExtra("rb", true);
		position = getIntent().getIntExtra("position", 0);
		initView();
		initValue();
	//	Log.i("2017/3/9","=ShowPersionDetailInforb="+rb);
		
		if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
			getFamilyList();
			
		}else{
			lv_title.setVisibility(View.GONE);
        	lv.setVisibility(View.GONE);
        	disscus.setText("点击调查");
		}
		
	}

	private void showDialog() {
		dialog = new ProgressDialog(ShowPersionDetailInfo.this);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("数据信息加载中...");
		dialog.show();
	}
	
	private void getFamilyList(){
		showDialog();
		// http://192.168.11.11:89/Json/Get_Home.aspx?TYPE=1&QA_MASTER=5&SFZ=310108196301092815
		OkHttpUtils.post().url(HttpUrls_.HttpURL+familyListUrl).addParams("TYPE","1").addParams("SFZ",info.getSFZ()).addParams("QA_MASTER",getIntent().getIntExtra("QUESTIONMASTERID", 0)+"").build().execute(new StringCallback() {
			
			@Override
			public void onResponse(final String infoStr) {
				
				//Log.i("2017/3/6","===2017/3/6成功==="+infoStr);
				Log.i("2017/3/15","===2017/3/15成功==="+infoStr);
				if(TextUtils.equals(infoStr,"[]")){
					no_disscus.setEnabled(true);
					lv_title.setVisibility(View.GONE);
		        	lv.setVisibility(View.GONE);
		        //	disscus.setText("点击调查");
		        	 dialog.dismiss();
		        	 disscus.setText("点击调查");
					return;
				}else{
					disscus.setText("点击调查");
					no_disscus.setEnabled(false);
					lv_title.setVisibility(View.VISIBLE);
		        	lv.setVisibility(View.VISIBLE);
				}
				
				runOnUiThread(new Runnable() {
					public void run() {
						
						Gson gson=new Gson();
						Type listType=new TypeToken<LinkedList<FamilyInfo>>(){}.getType();
						
						LinkedList<FamilyInfo> fi=gson.fromJson(infoStr,listType);
						
						listInfo.clear();
						
						for (Iterator iterator = fi.iterator(); iterator
								.hasNext();) {

							FamilyInfo content = (FamilyInfo) iterator
									.next();

							listInfo.add(content);
							
						}

						if(adapter==null){
							adapter=new WenJuanPersonListAdapter(listInfo, ShowPersionDetailInfo.this);
							lv.setAdapter(adapter);					
						}else{
							adapter.notifyDataSetChanged();
						}
					//	disscus.setText("家庭成员点击调查");
				        setListViewHeightBasedOnChildren(lv); 
				        dialog.dismiss();
				        
				        setButtonText();
				        
					}
				});
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				
				//Log.i("2017/3/6","===2017/3/6失败==="+arg0+"===2017/3/6异常==="+arg1);
				dialog.dismiss();
				Toast.makeText(ShowPersionDetailInfo.this,"请连接网络",0).show();
			}
		});
		
	}
	
	
	private void setButtonText(){
		
OkHttpUtils.post().url(HttpUrls_.HttpURL+answerUrl).addParams("HOMEID", listInfo.get(0).getID()+"").build().execute(new StringCallback() {
			
			@Override
			public void onResponse(String str) {
				
				Log.i("2017/3/7", "===成功==="+str);
				
				if(TextUtils.equals(str, "false")){
					disscus.setText("点击调查");
					return;
				}else{
					disscus.setText("新增成员调查");
					
				}
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				Log.i("2017/3/7", "===失败==="+arg0+"===异常==="+arg1);
				//finish();
				Toast.makeText(ShowPersionDetailInfo.this,"请连接网络",0).show();
			}
		});
		
		
	}
	
	private void initView() {
		qx_tv = (TextView) this.findViewById(R.id.qx);
		pid_tv = (TextView) this.findViewById(R.id.pid);
		number_tv = (TextView) this.findViewById(R.id.id);
		name_tv = (TextView) this.findViewById(R.id.name);
		sex_tv = (TextView) this.findViewById(R.id.sex);
		edu_tv = (TextView) this.findViewById(R.id.edu);
		sfz_tv = (TextView) this.findViewById(R.id.sfz);
		jd_tv = (TextView) this.findViewById(R.id.jd);
		jw_tv = (TextView) this.findViewById(R.id.jw);
		lxdz_tv = (TextView) this.findViewById(R.id.lxdz);
		phone = (TextView) this.findViewById(R.id.lxdh);
		zt_tv = (TextView) this.findViewById(R.id.status);
		dzszqx_tv = (TextView) this.findViewById(R.id.hjqx);
		lv_title=(LinearLayout) findViewById(R.id.awpi_ll_title);
		title_name_tv=(TextView) findViewById(R.id.awpi_tv_name);
		
		if(getIntent().getIntExtra("QUESTIONMASTERID", 0)==5){
			title_name_tv.setVisibility(View.VISIBLE);
		}else{
			title_name_tv.setVisibility(View.GONE);
		}
		
		lv=(ListView) findViewById(R.id.awpi_lv); 
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
			
				toWenJuanDetail(position);
				
				
			}
		});
		disscus = (Button) this.findViewById(R.id.disscus);
		no_disscus = (Button) this.findViewById(R.id.no_disscus);
		population=(Button) findViewById(R.id.population);
		family=(Button) findViewById(R.id.family);
		
		if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
			population.setVisibility(View.VISIBLE);
			family.setVisibility(View.GONE);
		//	family.setVisibility(View.VISIBLE);
			if(rb){
				
				disscus.setVisibility(View.VISIBLE);
				family.setVisibility(View.GONE);
			}else{
				
				//disscus.setVisibility(View.GONE);
				//family.setVisibility(View.VISIBLE);
				family.setVisibility(View.GONE);
			}
			
		}else{
			population.setVisibility(View.GONE);
			family.setVisibility(View.GONE);
			
                  if(rb){
				disscus.setVisibility(View.VISIBLE);
			}else{		
				//disscus.setVisibility(View.GONE);
			
				
			}
		}
		
		if(WenJuanPersonActivity.isWeichaRg){
			no_disscus.setVisibility(View.VISIBLE);
		}else{
			no_disscus.setVisibility(View.GONE);
		}
		persion_info = (Button) this.findViewById(R.id.persion_info);
		history_list = (Button) this.findViewById(R.id.history_list);

		persion_info.setOnClickListener(this);
		history_list.setOnClickListener(this);
		disscus.setOnClickListener(this);
		no_disscus.setOnClickListener(this);
		population.setOnClickListener(this);
		family.setOnClickListener(this);
	}

	 public void setListViewHeightBasedOnChildren(ListView listView) {   
	        // 获取ListView对应的Adapter   
	        ListAdapter listAdapter = listView.getAdapter();   
	        if (listAdapter == null) {   
	            return;   
	        }   
	   
	        int totalHeight = 0;   
	        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
	            // listAdapter.getCount()返回数据项的数目   
	            View listItem = listAdapter.getView(i, null, listView);   
	            // 计算子项View 的宽高   
	            listItem.measure(0, 0);    
	            // 统计所有子项的总高度   
	            totalHeight += listItem.getMeasuredHeight();    
	        }   
	   
	        ViewGroup.LayoutParams params = listView.getLayoutParams();   
	        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
	        // listView.getDividerHeight()获取子项间分隔符占用的高度   
	        // params.height最后得到整个ListView完整显示需要的高度   
	        listView.setLayoutParams(params);   
	    }   
	
	private void initValue() {
		if(info!=null){
		qx_tv.setText(info.getQX());
		pid_tv.setText(info.getPID());
		number_tv.setText(info.getNO());
		name_tv.setText(info.getNAME());
		sex_tv.setText(info.getSEX());
		sfz_tv.setText(info.getSFZ());
		edu_tv.setText(info.getEDU());
		jd_tv.setText(info.getJD());
		jw_tv.setText(info.getJW());
		lxdz_tv.setText(info.getLXDZ());
		phone.setText(info.getPHONE());
		zt_tv.setText(info.getZT());
		dzszqx_tv.setText(info.getQX());
	}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.disscus:
//			if (rb) {
//				Intent showIntent = new Intent(ShowPersionDetailInfo.this,
//						WenJuanDetailActivity.class);
			
			
			if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
				
				if(listInfo.size()>0){
					
				Intent showIntent = new Intent(ShowPersionDetailInfo.this,
						WenJuanDetailActivity.class);
				showIntent.putExtra("info", info);
				showIntent.putExtra("pid", info.getID());
				showIntent.putExtra("NO", info.getNO());
				if(listInfo.get(0).getZT()!=1){
					
					showIntent.putExtra("sname", listInfo.get(0).getSQR());
					showIntent.putExtra("myHOMEID", listInfo.get(0).getID());
				}else{
					
					showIntent.putExtra("familyInfo",(Serializable)listInfo.get(0));
				}
				showIntent.putExtra("position", position);
			    showIntent.putExtra("zt", listInfo.get(0).getZT());
				showIntent.putExtra("rb", true);
				showIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
				showIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
				startActivityForResult(showIntent,111111);
				}else{
					
					Intent showIntent = new Intent(ShowPersionDetailInfo.this,
							WenJuanDetailActivity.class);
					showIntent.putExtra("info", info);
					showIntent.putExtra("pid", info.getID());
					showIntent.putExtra("NO", info.getNO());
					showIntent.putExtra("sname", info.getNAME());
					showIntent.putExtra("position", position);
				//	showIntent.putExtra("rb", true);
					showIntent.putExtra("rb", true);
					showIntent.putExtra("zt", 1);
					showIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
					showIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
					startActivityForResult(showIntent,111111);
					
				}
			}else{
				Intent showIntent = new Intent(ShowPersionDetailInfo.this,
						WenJuanDetailActivity.class);
				showIntent.putExtra("info", info);
				showIntent.putExtra("pid", info.getID());
				showIntent.putExtra("NO", info.getNO());
				showIntent.putExtra("sname", info.getNAME());
				showIntent.putExtra("position", position);
				//showIntent.putExtra("rb", true);
				showIntent.putExtra("rb", rb);
				showIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
				showIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
				startActivity(showIntent);
			}
				
//			}
			break;

		case R.id.no_disscus:
	//		if (rb) {
				Intent intent = new Intent(ShowPersionDetailInfo.this,
						ShowWenJuanMarkActivity.class);
				intent.putExtra("pid", String.valueOf(info.getID()));
				intent.putExtra("position", position);
				startActivity(intent);
	//		}
			break;

		case R.id.persion_info:
			Intent itemIntent = new Intent(ShowPersionDetailInfo.this,
					PersoninfoMainActivity.class);
			itemIntent.putExtra("mysfz", info.getSFZ());
			startActivity(itemIntent);
			break;

		case R.id.history_list:
			Intent historyIntent = new Intent(ShowPersionDetailInfo.this,
					ShowPersionHistoryActivity.class);
			historyIntent.putExtra("sfz", info.getSFZ());
			historyIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
			
			startActivity(historyIntent);
			break;
			
		case R.id.population://登记人数
			
			Intent showIntent = new Intent(ShowPersionDetailInfo.this,
					WenJuanRegisterInfo.class);
			showIntent.putExtra("info", info);
			showIntent.putExtra("pid", info.getID());
			showIntent.putExtra("NO", info.getNO());
			showIntent.putExtra("sname", info.getNAME());
			showIntent.putExtra("position", position);
		//	showIntent.putExtra("rb", true);
			showIntent.putExtra("rb", rb);
			showIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
			showIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
			if(listInfo.size()>0){
			showIntent.putExtra("familyList", listInfo.get(0));
			}
			startActivityForResult(showIntent,111111);
			
			break;
			
case R.id.family://家庭成员调查
			
	if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
		
		if(listInfo.size()>0){
			
		Intent familyIntent = new Intent(ShowPersionDetailInfo.this,
				WenJuanDetailActivity.class);
		familyIntent.putExtra("info", info);
		familyIntent.putExtra("pid", info.getID());
		familyIntent.putExtra("NO", info.getNO());
		//showIntent.putExtra("sname", info.getNAME());
		if(listInfo.get(0).getZT()!=1){
			
			familyIntent.putExtra("sname", listInfo.get(0).getSQR());
			familyIntent.putExtra("myHOMEID", listInfo.get(0).getID());
		}else{
			
			familyIntent.putExtra("familyInfo",(Serializable)listInfo.get(0));
		}
		familyIntent.putExtra("position", position);
		familyIntent.putExtra("zt", listInfo.get(0).getZT());
		familyIntent.putExtra("rb", true);
		familyIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
		familyIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
		startActivityForResult(familyIntent,111111);
		}else{
			
//			Intent showIntent = new Intent(ShowPersionDetailInfo.this,
//					WenJuanRegisterInfo.class);
			Intent familyIntent = new Intent(ShowPersionDetailInfo.this,
					WenJuanDetailActivity.class);
			familyIntent.putExtra("info", info);
			familyIntent.putExtra("pid", info.getID());
			familyIntent.putExtra("NO", info.getNO());
			familyIntent.putExtra("sname", info.getNAME());
			familyIntent.putExtra("position", position);
		//	showIntent.putExtra("rb", true);
			familyIntent.putExtra("rb", true);
			familyIntent.putExtra("zt", 1);
			familyIntent.putExtra("QUESTIONMASTERID", getIntent().getIntExtra("QUESTIONMASTERID", 0));
			familyIntent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
			startActivityForResult(familyIntent,111111);
			
		}
	}
			break;	
			
		default:
			break;
		}
		
		if(!getIntent().getBooleanExtra("ISJYSTATUS", false)){
		ShowPersionDetailInfo.this.finish();
		}
	}

	private void toWenJuanDetail(final int position){
		
	//	http://192.168.11.11:89/Json/Get_Tb_Home_Answer_Info.aspx?HOMEID=89	
		OkHttpUtils.post().url(HttpUrls_.HttpURL+answerUrl).addParams("HOMEID", listInfo.get(position).getID()+"").build().execute(new StringCallback() {
			
			@Override
			public void onResponse(String str) {
				
				Log.i("2017/3/7", "===成功==="+str);
				
				Intent intent=new Intent(ShowPersionDetailInfo.this,WenJuanDetailActivity.class);
				if(TextUtils.equals(str, "false")){
					intent.putExtra("rb", true);
					
					Toast.makeText(ShowPersionDetailInfo.this,"请您先答问卷", 0).show();
					//disscus.setText("点击调查");===
					return;
				}else{
					//disscus.setText("新增成员调查");
					intent.putExtra("rb", false);
				}
				intent.putExtra("info", info);
				intent.putExtra("pid", info.getID());
				intent.putExtra("NO", info.getNO());
				intent.putExtra("sname", listInfo.get(position).getSQR());
				intent.putExtra("position", position);
				
				intent.putExtra("QUESTIONMASTERID",getIntent().getIntExtra("QUESTIONMASTERID", 0));
				intent.putExtra("myHOMEID", listInfo.get(position).getID());
				intent.putExtra("ISJYSTATUS", getIntent().getBooleanExtra("ISJYSTATUS", false));
				startActivity(intent);
				//finish();
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				Log.i("2017/3/7", "===失败==="+arg0+"===异常==="+arg1);
				//finish();
				Toast.makeText(ShowPersionDetailInfo.this,"请连接网络",0).show();
			}
		});
			
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		//if(resultCode==10000){
			if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
				getFamilyList();
				
				Log.i("2017/3/6","===getFamilyList()===");
				
			}else{
				lv_title.setVisibility(View.GONE);
	        	lv.setVisibility(View.GONE);
				
			}
		//}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(getIntent().getBooleanExtra("ISJYSTATUS", false)){
			
		if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
			
			AlertDialog.Builder builder=new Builder(ShowPersionDetailInfo.this);
			builder.setTitle("关闭提示").setMessage("您确定要退出调查问卷吗?")
			.setIcon(android.R.drawable.ic_dialog_info)
			.setCancelable(true)
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					finish();
				}
			}).setNegativeButton("取消",null).show();
				
		
		
		}
		return false;
		}else{
			//return true;
			//return false;
	return super.onKeyDown(keyCode, event);
		}
	}
	
	
	
}

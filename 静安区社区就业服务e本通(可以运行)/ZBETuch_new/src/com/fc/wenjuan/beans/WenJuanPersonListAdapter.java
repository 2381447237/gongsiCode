package com.fc.wenjuan.beans;

import java.util.List;

import okhttp3.Call;

import com.fc.main.beans.IActivity;
import com.fc.main.service.PersonService;
import com.fc.main.tools.HttpUrls_;
import com.fc.main.tools.MainTools;
import com.fc.wenjuan.views.ShowPersionDetailInfo;
import com.fc.wenjuan.views.WenJuanDetailActivity;
import com.fc.wenjuan.views.WenJuanPersonActivity;
import com.fc.wenjuan.views.WenJuanRegisterInfo;
import com.test.zbetuch_news.R;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WenJuanPersonListAdapter extends BaseAdapter {

	private List<FamilyInfo> infos;
	private ShowPersionDetailInfo context;
	private IActivity activity = null;
	public WenJuanPersonListAdapter(List<FamilyInfo> infos, ShowPersionDetailInfo context) {
		super();
		this.infos = infos;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return infos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HoldItem holdItem = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_person_list_info, null);
			holdItem = new HoldItem();
			holdItem.tv_name = (TextView) view.findViewById(R.id.tv_name_person);
			//holdItem.tv_sex = (TextView) view.findViewById(R.id.tv_sex_person);
			holdItem.tv_lxdz = (TextView) view.findViewById(R.id.tv_lxdz_person);
			holdItem.tv_jd = (TextView) view.findViewById(R.id.tv_jd_person);
			holdItem.tv_jw = (TextView) view.findViewById(R.id.tv_jw_person);
			holdItem.tv_num=(TextView) view.findViewById(R.id.tv_jtcysj_person);
			holdItem.tv_delete=(TextView) view.findViewById(R.id.tv_delete_person);
			view.setTag(holdItem);
		} else {
			holdItem = (HoldItem) view.getTag();
		}
		view.setBackgroundResource(MainTools.getbackground1(position));
		FamilyInfo info = infos.get(position);
		
		if(context.getIntent().getIntExtra("QUESTIONMASTERID", 0)==5){
			holdItem.tv_name.setVisibility(View.VISIBLE);
		holdItem.tv_name.setText(info.getSQR());
		}else{
			holdItem.tv_name.setVisibility(View.GONE);
		}
		//holdItem.tv_sex.setText(info.getSEX());
		holdItem.tv_lxdz.setText(info.getADRESS());
		holdItem.tv_jd.setText(info.getXZJD());
		holdItem.tv_jw.setText(info.getSQJWC());
		holdItem.tv_num.setText(info.getJZNUM()+"");
		
//		if(context.rb){
//			holdItem.tv_delete.setVisibility(View.VISIBLE);
//		}else{
//			holdItem.tv_delete.setVisibility(View.GONE);
//		}
		
		holdItem.tv_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				showDialog(position);		
				
			}
		});
		return view;
	}

	class HoldItem {
		TextView tv_id, tv_name, tv_sex, tv_jd, tv_jw, tv_lxdz,tv_num,tv_delete;
	}

	
	private void showDialog(final int position){
		
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("温馨提示").setMessage("您确定要删除吗？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						deleteFamilyInfo(position);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).create().show();
		
	}
	
	private void showDialog2(final int position) {

		final AlertDialog dialog = new AlertDialog.Builder(context).create();

		View view = LayoutInflater.from(context).inflate(
				R.layout.newadd_dialog_layout, null);
		dialog.setView(view);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.newadd_dialog_layout);
		TextView tv_dialog = (TextView) window
				.findViewById(R.id.newadd_tv_dialog);
		tv_dialog.setText("您要确定是删除吗？");
		Button btnOk = (Button) window.findViewById(R.id.newadd_btn_ok);
		Button btnUndo = (Button) window.findViewById(R.id.newadd_btn_undo);

		btnUndo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				
			}
		});

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog.dismiss();
				deleteFamilyInfo(position);
				
			}
		});
	}
	
	private void deleteFamilyInfo(final int position){
		
		OkHttpUtils.post().url(HttpUrls_.HttpURL+WenJuanRegisterInfo.registerInfoUrl).addParams("TYPE","2").addParams("ID",infos.get(position).getID()+"").addHeader("cookie", HttpUrls_.staffName).build().execute(new StringCallback() {
			
			@Override
			public void onResponse(String str) {
				
				Log.i("2017/3/6","==删除成功=="+str);
				
					
					infos.remove(position);
					notifyDataSetChanged();
					context.setListViewHeightBasedOnChildren(context.lv);
					Toast.makeText(context,"删除成功", 0).show();
					deleteRefresh();
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
			
				Toast.makeText(context,"请连接网络",0).show();
			}
		});
		
	}
	
	private void deleteRefresh(){
		
	int myTypeId;
		
		if(WenJuanPersonActivity.isWeichaRg){
			myTypeId=1;
		}else{
			myTypeId=0;
		}
		
		OkHttpUtils.post().url(HttpUrls_.HttpURL+"/Json/Get_Qa_UpLoad_Personnel.aspx").addParams("type",""+myTypeId).addParams("page","0").addParams("rows","15").addParams("master_id",context.getIntent().getIntExtra("QUESTIONMASTERID", 1)+"").addHeader("cookie", HttpUrls_.staffName)
		.build().execute(new StringCallback() {
			
			@Override
			public void onResponse(String str) {
				
				WenJuanPersonActivity.myRefresh=true;
				
				activity = (WenJuanPersonActivity) PersonService
				.getActivityByName("WenJuanPersonActivity");
	
		if (activity != null) {
			
		activity.refresh(WenJuanPersonActivity.REFRESH_INFO,
				str);
			}
		
		refreshBtn();
		
			}
			@Override
			public void onError(Call arg0, Exception arg1) {
				Toast.makeText(context,"请连接网络",0).show();
			}
		
		});
		
	}
	
	private void refreshBtn(){
		
		OkHttpUtils.post().url(HttpUrls_.HttpURL+context.familyListUrl)
		.addParams("TYPE","1").addParams("SFZ",context.info.getSFZ()).addParams("QA_MASTER",context.getIntent().getIntExtra("QUESTIONMASTERID", 1)+"")
		.build().execute(new StringCallback() {
			
			@Override
			public void onResponse(String infoStr) {
				Log.i("2017/3/16","===成功==="+infoStr);
				
				if(TextUtils.equals(infoStr,"[]")){
					context.no_disscus.setEnabled(true);
					context.lv_title.setVisibility(View.GONE);
					context.lv.setVisibility(View.GONE);
					context.disscus.setText("点击调查");
		        	 
		        	
				}else{
					context.no_disscus.setEnabled(false);
					
					context.lv_title.setVisibility(View.VISIBLE);
					context.lv.setVisibility(View.VISIBLE);
					context.disscus.setText("新增成员调查");
				}
				
			}
			
			@Override
			public void onError(Call arg0, Exception arg1) {
				Toast.makeText(context,"请连接网络",0).show();
				
			}
		});
	}
	
}

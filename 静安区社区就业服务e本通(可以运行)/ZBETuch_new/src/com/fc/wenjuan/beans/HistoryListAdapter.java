package com.fc.wenjuan.beans;

import java.util.List;

import okhttp3.Call;

import com.fc.main.beans.IActivity;
import com.fc.main.service.PersonService;
import com.fc.main.tools.HttpUrls_;
import com.fc.main.tools.MainTools;
import com.fc.wenjuan.views.ShowPersionDetailInfo;
import com.fc.wenjuan.views.ShowPersionHistoryList;
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

public class HistoryListAdapter extends BaseAdapter {

	private List<FamilyInfo> infos;
	private ShowPersionHistoryList context;
	public HistoryListAdapter(List<FamilyInfo> infos, ShowPersionHistoryList context) {
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
					R.layout.item_person_list_sphl, null);
			holdItem = new HoldItem();
			holdItem.tv_name = (TextView) view.findViewById(R.id.tv_name_person_sphl);
			holdItem.tv_lxdz = (TextView) view.findViewById(R.id.tv_lxdz_person_sphl);
			holdItem.tv_jd = (TextView) view.findViewById(R.id.tv_jd_person_sphl);
			holdItem.tv_jw = (TextView) view.findViewById(R.id.tv_jw_person_sphl);
			view.setTag(holdItem);
		} else {
			holdItem = (HoldItem) view.getTag();
		}
		view.setBackgroundResource(MainTools.getbackground1(position));
		FamilyInfo info = infos.get(position);
		holdItem.tv_name.setText(info.getSQR());
		if(context.info.getQA_MASTER()==5){
			holdItem.tv_name.setVisibility(View.VISIBLE);
		}else if(context.info.getQA_MASTER()==6){
			holdItem.tv_name.setVisibility(View.GONE);
		}
		holdItem.tv_lxdz.setText(info.getADRESS());
		holdItem.tv_jd.setText(info.getXZJD());
		holdItem.tv_jw.setText(info.getSQJWC());
		
		
		return view;
	}

	class HoldItem {
		TextView tv_name, tv_sex, tv_jd, tv_jw, tv_lxdz;
	}

}

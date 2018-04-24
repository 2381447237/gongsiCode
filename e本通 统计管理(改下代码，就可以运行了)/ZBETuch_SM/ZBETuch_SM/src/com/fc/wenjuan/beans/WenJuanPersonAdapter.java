package com.fc.wenjuan.beans;

import java.util.List;

import com.fc.main.tools.MainTools;
import com.test.zbetuch_news.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WenJuanPersonAdapter extends BaseAdapter {

	private List<WenJuanPersonInfo> infos;
	private Context context;

	public WenJuanPersonAdapter(List<WenJuanPersonInfo> infos, Context context) {
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
	public View getView(int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		HoldItem holdItem = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(
					R.layout.item_gxperson_info, null);
			holdItem = new HoldItem();
			holdItem.tv_name = (TextView) view.findViewById(R.id.tv_name);
			holdItem.tv_sex = (TextView) view.findViewById(R.id.tv_sex);
			holdItem.tv_lxdz = (TextView) view.findViewById(R.id.tv_lxdz);
			holdItem.tv_jd = (TextView) view.findViewById(R.id.tv_jd);
			holdItem.tv_jw = (TextView) view.findViewById(R.id.tv_jw);
			view.setTag(holdItem);
		} else {
			holdItem = (HoldItem) view.getTag();
		}
		view.setBackgroundResource(MainTools.getbackground1(position));
		WenJuanPersonInfo info = infos.get(position);
		holdItem.tv_name.setText(info.getNAME());
		holdItem.tv_sex.setText(info.getSEX());
		holdItem.tv_lxdz.setText(info.getDZ());
		holdItem.tv_jd.setText(info.getJD());
		holdItem.tv_jw.setText(info.getJW());
		return view;
	}

	class HoldItem {
		TextView tv_id, tv_name, tv_sex, tv_jd, tv_jw, tv_lxdz;
	}

}

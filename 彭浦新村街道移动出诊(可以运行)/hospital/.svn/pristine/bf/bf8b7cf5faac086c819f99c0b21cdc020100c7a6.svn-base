package com.fc.has.beans;

import java.util.ArrayList;

import com.example.hospital.R;
import com.fc.unidentified.beans.ItemsInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeVisitPrescribeAdapter extends BaseAdapter {

	private ArrayList<ItemsInfo> list;
	private Context context;
	public HomeVisitPrescribeAdapter(Context context,ArrayList<ItemsInfo> list) {
		super();
		this.context=context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(convertView==null){
			convertView=LayoutInflater.from(context).inflate(R.layout.activity_delhomevisitprescribe, null);
			holder=new ViewHolder();
			holder.tv_pre11=(TextView)convertView.findViewById(R.id.tv_pre11);
			holder.tv_pre22=(TextView)convertView.findViewById(R.id.tv_pre22);
			holder.tv_pre33=(TextView)convertView.findViewById(R.id.tv_pre33);
			holder.tv_pre44=(TextView)convertView.findViewById(R.id.tv_pre44);
			holder.tv_pre55=(TextView)convertView.findViewById(R.id.tv_pre55);
			holder.tv_pre66=(TextView)convertView.findViewById(R.id.tv_pre66);
			holder.liner_pre2=(LinearLayout) convertView.findViewById(R.id.liner_pre2);
			holder.liner_fr=(LinearLayout) convertView.findViewById(R.id.liner_fr);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder)convertView.getTag();
		}
		ItemsInfo info=list.get(position);
		holder.tv_pre11.setText(info.getItemClassName());
		holder.tv_pre22.setText(info.getItemName());
		holder.tv_pre33.setText(info.getItemAmount()+"");
		holder.tv_pre44.setText(info.getItemOutpSpec());
		holder.tv_pre55.setText(info.getItemPrice1()+"");
		holder.tv_pre66.setText(info.getDoctorOrders());
		if(!info.getItemOutpSpec().equals("")){
			holder.liner_pre2.setVisibility(View.VISIBLE);
			holder.liner_fr.setVisibility(View.VISIBLE);
		}
		else{
			holder.liner_pre2.setVisibility(View.GONE);
			holder.liner_fr.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHolder{
		TextView tv_pre11,tv_pre22,tv_pre33,tv_pre44,tv_pre55,tv_pre66;
		LinearLayout liner_pre2,liner_fr;
	}
}

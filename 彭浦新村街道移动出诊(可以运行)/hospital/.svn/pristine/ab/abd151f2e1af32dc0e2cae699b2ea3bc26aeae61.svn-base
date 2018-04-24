package com.fc.has.beans;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.example.hospital.R;
import com.fc.has.views.HomeVisitNumInfoActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeVisitNumInfoAdapter extends BaseAdapter {
	private ArrayList<HomeVisitNumInfo> list;
	private Context context;
	public HomeVisitNumInfoAdapter(ArrayList<HomeVisitNumInfo> list,Context context) {
		super();
		this.list = list;
		this.context=context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		viewHolder holder;
		if(convertView==null){
			holder=new viewHolder();
//			if(position%2==0){
				
				convertView=LayoutInflater.from(context).inflate(R.layout.visitnuminfo, null);
				holder.tv_numinfo=(TextView)convertView.findViewById(R.id.tv_numinfo);
				holder.tv_totalcost=(TextView)convertView.findViewById(R.id.tv_totalcost);
//			}else{
//				convertView=LayoutInflater.from(context).inflate(R.layout.visitnuminfo2, null);
//				holder.tv_numinfo1=(TextView)convertView.findViewById(R.id.tv_numinfo1);
//				holder.tv_totalcost1=(TextView)convertView.findViewById(R.id.tv_totalcost1);
//			}
			convertView.setTag(holder);
		}else{
			holder=(viewHolder)convertView.getTag();
		}
		
//		HomeVisitNumInfo numInfo=list.get(position);
//		holder.tv_numinfo.setText("处方时间："+numInfo.getPrescribeDate().substring(0, 10)+" "+numInfo.getPrescribeDate().substring(11, 19));
//		holder.tv_totalcost.setText("金额总计："+numInfo.getTotalCosts()+"元");
//		if(position%2==0){
//			holder.tv_numinfo.setText("处方时间："+numInfo.getPrescribeDate().substring(0, 10)+" "+numInfo.getPrescribeDate().substring(11, 19));
//			holder.tv_totalcost.setText("金额总计："+numInfo.getTotalCosts()+"元");
//		}else{
//			holder.tv_numinfo1.setText("处方时间："+numInfo.getPrescribeDate().substring(0, 10)+" "+numInfo.getPrescribeDate().substring(11, 19));
//			holder.tv_totalcost1.setText("金额总计："+numInfo.getTotalCosts()+"元");
//		}
		convertView.setBackgroundResource(HomeVisitNumInfoActivity.getbackground1(position));
		HomeVisitNumInfo numInfo=list.get(position);
		holder.tv_numinfo.setText("处方时间："+numInfo.getPrescribeDate().substring(0, 10)+" "+numInfo.getPrescribeDate().substring(11, 19));
		holder.tv_totalcost.setText("金额总计："+numInfo.getTotalCosts()+"元");
		return convertView;
	}
	
	private class viewHolder{
		TextView tv_numinfo,tv_totalcost;
		TextView tv_numinfo1,tv_totalcost1;
	}

}

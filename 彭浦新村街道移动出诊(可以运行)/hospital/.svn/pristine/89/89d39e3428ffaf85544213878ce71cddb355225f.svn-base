package com.fc.has.beans;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.hospital.R;
import com.fc.has.views.HomeVisitSearchActivity;
import com.fc.unidentified.beans.ItemsInfo;
import com.fc.unidentified.views.PrescriptionActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeVisitPrescribeActivityAdapter extends BaseAdapter {
	private HomeVisitSearchActivity context;
	private ArrayList<ItemsInfo> itemsInfos;
	DecimalFormat df;
	public HomeVisitPrescribeActivityAdapter(HomeVisitSearchActivity context,
			ArrayList<ItemsInfo> itemsInfos){
		super();
		this.context=context;
		this.itemsInfos=itemsInfos;
		 df=new DecimalFormat(".##");
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemsInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return itemsInfos.get(position);
	}
	

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ItemHodler itemHodler;
		if(convertView == null){
			convertView=LayoutInflater.from(context).inflate(R.layout.activity_prescription_adapter, null);
			itemHodler = new ItemHodler();
			itemHodler.tv_pre1=(TextView) convertView.findViewById(R.id.tv_pre1);
			itemHodler.tv_pre2=(TextView) convertView.findViewById(R.id.tv_pre2);
			itemHodler.tv_pre3=(TextView) convertView.findViewById(R.id.tv_pre3);
			itemHodler.tv_pre4=(TextView) convertView.findViewById(R.id.tv_pre4);
			itemHodler.tv_pre5=(TextView) convertView.findViewById(R.id.tv_pre5);
			itemHodler.tv_pre6=(TextView) convertView.findViewById(R.id.tv_pre6);
			itemHodler.tv_pre7=(TextView) convertView.findViewById(R.id.tv_pre7);
			itemHodler.tv_pre8=(TextView) convertView.findViewById(R.id.tv_pre8);
			itemHodler.img_del=(ImageView) convertView.findViewById(R.id.img_del);
			itemHodler.liner_pre2=(LinearLayout) convertView.findViewById(R.id.liner_pre2);
			itemHodler.liner_fr=(LinearLayout) convertView.findViewById(R.id.liner_fr);
			convertView.setTag(itemHodler);
		}else{
			itemHodler = (ItemHodler)convertView.getTag();
		}
		ItemsInfo info=itemsInfos.get(position);
		if(info.getItemAmount()!=0){
			
			itemHodler.tv_pre1.setText(info.getItemName());
			itemHodler.tv_pre2.setText(info.getItemOutpSpec());
			itemHodler.tv_pre3.setText(info.getItemClassName());
			itemHodler.tv_pre4.setText(info.getItemAmount()+"");
			itemHodler.tv_pre5.setText(info.getItemPrice1()+"");
			if(info.getIsDrug()){
				itemHodler.tv_pre6.setText(info.getItemFrequency());
				itemHodler.tv_pre7.setText("每次"+info.getItemDosage()+info.getItemDosageUnit());
				itemHodler.tv_pre8.setText(info.getItemUsage());
				itemHodler.liner_pre2.setVisibility(View.VISIBLE);
				itemHodler.liner_fr.setVisibility(View.VISIBLE);
			}else{
				itemHodler.liner_pre2.setVisibility(View.GONE);
				itemHodler.liner_fr.setVisibility(View.GONE);
			}
			
		}else{
			itemHodler.tv_pre1.setText(info.getItemName());
			itemHodler.tv_pre2.setText(info.getItemOutpSpec());
			itemHodler.tv_pre3.setText(info.getItemClassName());
			itemHodler.tv_pre4.setText(info.getNum());
			itemHodler.tv_pre5.setText(df.format(info.getItemPrice1())+"");
			itemHodler.tv_pre6.setText(info.getFreque());
			itemHodler.tv_pre8.setText(info.getUsage());
			if(!info.getMuch().equals("")){
			itemHodler.tv_pre7.setText("每次"+info.getMuch()+info.getItemOutpUnits());
			}else{
				itemHodler.tv_pre7.setText("");
			}
			if(info.getDrugOrItemIndicatorName().equals("药品类")){
				itemHodler.liner_pre2.setVisibility(View.VISIBLE);
				itemHodler.liner_fr.setVisibility(View.VISIBLE);
			}
			else{
				itemHodler.liner_pre2.setVisibility(View.GONE);
				itemHodler.liner_fr.setVisibility(View.GONE);
			}
		}
		
		itemHodler.img_del.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				  context.showInfo(position);  
			}
		});
		
		return convertView;
	}

	class ItemHodler {
		TextView tv_pre1,tv_pre2,tv_pre3,tv_pre4,
		tv_pre5,tv_pre6,tv_pre7,tv_pre8;
		ImageView img_del;
		LinearLayout liner_pre2,liner_fr;
	}

}

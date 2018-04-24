package com.example.ppu_infusion;

import java.util.List;
import com.example.Seats.beans.GetStartInfo;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PeiYaoAdapter extends BaseAdapter{

	private PeiyaoDetailActivity context;
	private List<GetStartInfo> getStartInfos;
	
	public PeiYaoAdapter(PeiyaoDetailActivity context,List<GetStartInfo> getStartInfos){
		super();
		this.context=context;
		this.getStartInfos=getStartInfos;
	}
	@Override
	public int getCount() {
		return getStartInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return getStartInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ItemHodler hodler;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_infusion, null);
			hodler = new ItemHodler();
			hodler.tv_infus =  (TextView) convertView.findViewById(R.id.tv_infus);
			hodler.tv_spec=(TextView) convertView.findViewById(R.id.tv_spec);
			hodler.tv_tub =  (TextView) convertView.findViewById(R.id.tv_tub);
			hodler.tv_seep =  (TextView) convertView.findViewById(R.id.tv_seep);
			hodler.lin_infu=(LinearLayout) convertView.findViewById(R.id.lin_infu);
			hodler.img_start=(ImageView) convertView.findViewById(R.id.img_start);
			convertView.setTag(hodler);
		} else {
			hodler = (ItemHodler) convertView.getTag();
		}
		final GetStartInfo info = getStartInfos.get(position);
		Resources resources=context.getResources();
		

		
		 if(info.getINFUSIONSTATUS().equals("3")){
			 
				 hodler.tv_infus.setText(info.getDRUGNAME()+"  "+info.getDOSAGE()+info.getDOSAGEUNIT());
	
			hodler.tv_infus.setTextColor(resources.getColor(R.color.item_hong));
			
			hodler.tv_spec.setTextColor(resources.getColor(R.color.item_hong));
			hodler.tv_tub.setVisibility(View.GONE);
			hodler.tv_seep.setVisibility(View.GONE);
			hodler.img_start.setBackgroundResource(R.drawable.item_hong);
		}
	    else if (info.getINFUSIONSTATUS().equals("2")) {
	    	
	 				 hodler.tv_infus.setText(info.getDRUGNAME()+"  "+info.getDOSAGE()+info.getDOSAGEUNIT());
	 			
			hodler.tv_tub.setText("管型："+info.getTUBENAME());
			
			hodler.tv_spec.setTextColor(resources.getColor(R.color.item_huang));
			
			//hodler.tv_tub.setText("管型："+info.getDRUGNAME());
			
			hodler.tv_seep.setText("滴速："+info.getDRIPCNTSPERMINUTE());
			hodler.img_start.setBackgroundResource(R.drawable.item_huang);
			hodler.tv_infus.setTextColor(resources.getColor(R.color.item_huang));
			hodler.tv_tub.setTextColor(resources.getColor(R.color.item_huang));
			hodler.tv_seep.setTextColor(resources.getColor(R.color.item_huang));
		}
	    else if (info.getINFUSIONSTATUS().equals("1")) {
	    	 
	 				 hodler.tv_infus.setText(info.getDRUGNAME()+"  "+info.getDOSAGE()+info.getDOSAGEUNIT());
	 		
			hodler.tv_tub.setText("管型："+info.getTUBENAME());
			
			hodler.tv_spec.setTextColor(resources.getColor(R.color.item_lv));
		//	hodler.tv_tub.setText("管型："+info.getDRUGNAME());
			hodler.tv_seep.setText("滴速："+info.getDRIPCNTSPERMINUTE());
			hodler.img_start.setBackgroundResource(R.drawable.item_lv);
			hodler.tv_infus.setTextColor(resources.getColor(R.color.item_lv));
			hodler.tv_tub.setTextColor(resources.getColor(R.color.item_lv));
			hodler.tv_seep.setTextColor(resources.getColor(R.color.item_lv));
		}
		else if (info.getINFUSIONSTATUS().equals("0")){
			 
						 hodler.tv_infus.setText(info.getDRUGNAME()+"  "+info.getDOSAGE()+info.getDOSAGEUNIT());
				
			hodler.tv_spec.setTextColor(resources.getColor(R.color.item_lan));
			hodler.tv_tub.setVisibility(View.GONE);
			hodler.tv_seep.setVisibility(View.GONE);
			hodler.img_start.setBackgroundResource(R.drawable.item_lan);
			hodler.tv_infus.setTextColor(resources.getColor(R.color.item_lan));
		}
		
		return convertView;
	}
	
	class ItemHodler {
		TextView tv_infus,tv_tub,tv_seep,tv_spec;
		LinearLayout lin_infu;
		ImageView img_start;
	}
	
}

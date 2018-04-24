package com.example.Seats.beans;

import java.util.List;

import com.example.Seats.views.InfusionActivity;
import com.example.ppu_infusion.R;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InfusionAdapter extends BaseAdapter{
	private InfusionActivity context;
	private List<GetStartInfo> getStartInfos;
	
	public InfusionAdapter(InfusionActivity context,List<GetStartInfo> getStartInfos){
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
		
		if(context.isScan==true){
//			hodler.img_start.setVisibility(View.GONE);
		}else{
//			hodler.img_start.setVisibility(View.VISIBLE);
	}
		
		 if(info.getINFUSIONSTATUS().equals("3")){
			 if(context.infos!=null){
				 mySetText(hodler.tv_infus,info.getDRUGNAME());
			//hodler.tv_infus.setText(info.getDRUGNAME());
			 }else{
				 hodler.tv_infus.setText(info.getDRUGNAME()+"  "+info.getDOSAGE()+info.getDOSAGEUNIT());
			 }
			hodler.tv_infus.setTextColor(resources.getColor(R.color.item_hong));
			if(context.infos!=null){
			//hodler.tv_spec.setText(info.getDRUGSPEC());
			}else{
			//hodler.tv_spec.setText(info.getDOSAGE()+info.getDOSAGEUNIT());
			}
			hodler.tv_spec.setTextColor(resources.getColor(R.color.item_hong));
			hodler.tv_tub.setVisibility(View.GONE);
			hodler.tv_seep.setVisibility(View.GONE);
			hodler.img_start.setBackgroundResource(R.drawable.item_hong);
		}
	    else if (info.getINFUSIONSTATUS().equals("2")) {
	    	 if(context.infos!=null){
	    		 mySetText(hodler.tv_infus,info.getDRUGNAME());
	 			//hodler.tv_infus.setText(info.getDRUGNAME());
	 			 }else{
	 				 hodler.tv_infus.setText(info.getDRUGNAME()+"  "+info.getDOSAGE()+info.getDOSAGEUNIT());
	 			 }
			hodler.tv_tub.setText("管型："+info.getTUBENAME());
			if(context.infos!=null){
			//hodler.tv_spec.setText(info.getDRUGSPEC());
			}else{
			//hodler.tv_spec.setText(info.getDOSAGE()+info.getDOSAGEUNIT());
			}
			hodler.tv_spec.setTextColor(resources.getColor(R.color.item_huang));
			
			//hodler.tv_tub.setText("管型："+info.getDRUGNAME());
			
			hodler.tv_seep.setText("滴速："+info.getDRIPCNTSPERMINUTE());
			hodler.img_start.setBackgroundResource(R.drawable.item_huang);
			hodler.tv_infus.setTextColor(resources.getColor(R.color.item_huang));
			hodler.tv_tub.setTextColor(resources.getColor(R.color.item_huang));
			hodler.tv_seep.setTextColor(resources.getColor(R.color.item_huang));
		}
	    else if (info.getINFUSIONSTATUS().equals("1")) {
	    	 if(context.infos!=null){
	    		 mySetText(hodler.tv_infus,info.getDRUGNAME());
	 			//hodler.tv_infus.setText(info.getDRUGNAME());
	 			 }else{
	 				 hodler.tv_infus.setText(info.getDRUGNAME()+"  "+info.getDOSAGE()+info.getDOSAGEUNIT());
	 			 }
			hodler.tv_tub.setText("管型："+info.getTUBENAME());
			if(context.infos!=null){
			//hodler.tv_spec.setText(info.getDRUGSPEC());
			}else{
			//hodler.tv_spec.setText(info.getDOSAGE()+info.getDOSAGEUNIT());
			}
			hodler.tv_spec.setTextColor(resources.getColor(R.color.item_lv));
		//	hodler.tv_tub.setText("管型："+info.getDRUGNAME());
			hodler.tv_seep.setText("滴速："+info.getDRIPCNTSPERMINUTE());
			hodler.img_start.setBackgroundResource(R.drawable.item_lv);
			hodler.tv_infus.setTextColor(resources.getColor(R.color.item_lv));
			hodler.tv_tub.setTextColor(resources.getColor(R.color.item_lv));
			hodler.tv_seep.setTextColor(resources.getColor(R.color.item_lv));
		}
		else if (info.getINFUSIONSTATUS().equals("0")){
			 if(context.infos!=null){
				 mySetText(hodler.tv_infus,info.getDRUGNAME());
					//hodler.tv_infus.setText(info.getDRUGNAME());
					 }else{
						 hodler.tv_infus.setText(info.getDRUGNAME()+"  "+info.getDOSAGE()+info.getDOSAGEUNIT());
					 }
			if(context.infos!=null){
			//hodler.tv_spec.setText(info.getDRUGSPEC());
			}else{
			//hodler.tv_spec.setText(info.getDOSAGE()+info.getDOSAGEUNIT());
			}
			hodler.tv_spec.setTextColor(resources.getColor(R.color.item_lan));
			hodler.tv_tub.setVisibility(View.GONE);
			hodler.tv_seep.setVisibility(View.GONE);
			hodler.img_start.setBackgroundResource(R.drawable.item_lan);
			hodler.tv_infus.setTextColor(resources.getColor(R.color.item_lan));
		}
		
		return convertView;
	}

	private void mySetText(TextView tv,String a){
		
//		int cnt=0;
//		int offset=0;
//		while((offset=a.indexOf("、",offset))!=-1){
//			offset=offset+"、".length();
//			cnt++;
//		}
//		
//		String[] b=null;
//		for(int i=0;i<=cnt;i++){
//			b=a.split("、");
//			if(cnt==0){
//				tv.setText(b[0]);
//			}else if(cnt==1){
//			tv.setText(b[0]+"\n"+b[1]);
//			}else if(cnt==2){
//				tv.setText(b[0]+"\n"+b[1]+"\n"+b[2]);
//			}else if(cnt==3){
//				tv.setText(b[0]+"\n"+b[1]+"\n"+b[2]+"\n"+b[3]);
//			}else if(cnt==4){
//				tv.setText(b[0]+"\n"+b[1]+"\n"+b[2]+"\n"+b[3]+"\n"+b[4]);
//			}
//			
//		}
		
		String [] names=a.split("\\、");
		for(int i=0;i<names.length;i++){
			
			if(names.length==1){
			tv.setText(names[0]);
		}else if(names.length==2){
		tv.setText(names[0]+"\n"+names[1]);
		}else if(names.length==3){
			tv.setText(names[0]+"\n"+names[1]+"\n"+names[2]);
		}else if(names.length==4){
			tv.setText(names[0]+"\n"+names[1]+"\n"+names[2]+"\n"+names[3]);
		}else if(names.length==5){
			tv.setText(names[0]+"\n"+names[1]+"\n"+names[2]+"\n"+names[3]+"\n"+names[4]);
		}
			
		}
		
	}
	
	class ItemHodler {
		TextView tv_infus,tv_tub,tv_seep,tv_spec;
		LinearLayout lin_infu;
		ImageView img_start;
	}

}

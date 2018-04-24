package com.fc.unidentified.beans;

import java.util.HashMap;
import java.util.List;

import com.example.hospital.R;
import com.fc.unidentified.views.DiagnosisActivity;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class DiagnosisAdapter extends BaseAdapter {
	private DiagnosisActivity context;
	private List<DiagInfo> DiagInfos;
	public DiagnosisAdapter(DiagnosisActivity context,
			List<DiagInfo> DiagInfo) {
		super();
		this.context = context;
		this.DiagInfos = DiagInfo;
		/*isSelected = new HashMap<Integer, Boolean>(); 
		initDate();*/
	}
	/*// ��ʼ��isSelected������ 
    private void initDate() { 
        for (int i = 0; i < DiagInfos.size(); i++) { 
        	isSelected.put(i, false); 
        } 
    } */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return DiagInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return DiagInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ItemHodler itemHodler;
		
		final DiagInfo info=DiagInfos.get(position);
		
		if(convertView == null){
			convertView=LayoutInflater.from(context).inflate(R.layout.activity_diagnosis_adapter, null);
			
			itemHodler = new ItemHodler();
			itemHodler.tv_text=(TextView) convertView.findViewById(R.id.tv_text);
			itemHodler.btn_up= (Button) convertView.findViewById(R.id.btn_up);
			
			convertView.setTag(itemHodler);

		}else{
			itemHodler = (ItemHodler)convertView.getTag();
		}
		itemHodler.s=info;
		if(	context.CheckDiagInfo(info))
		{
			itemHodler.btn_up.setText("ɾ��");
			itemHodler.btn_up.setBackgroundResource(R.drawable.btn_orange);
		}
		else
		{
			itemHodler.btn_up.setText("����");
			itemHodler.btn_up.setBackgroundResource(R.drawable.login);
		}
		
		
		
		
		Log.i("qwj", "DiagInfo=="+info.getDiagName());

		//itemHodler.btn_up.setTag(info);
		
//		itemHodler.btn_up.setText(position%2==0?"ɾ��":"����"  );
//		if(itemHodler.btn_up.getText()=="ɾ��")
//			itemHodler.btn_up.setBackgroundResource(R.drawable.btn_orange);
//		else
//			itemHodler.btn_up.setBackgroundResource(R.drawable.login);
		
		
		itemHodler.tv_text.setText(info.getDiagName());

		itemHodler.btn_up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
            
                    if(itemHodler.btn_up.getText().toString().equals("ɾ��")){
                    	
          				itemHodler.btn_up.setText("����");
          				itemHodler.s=DiagInfos.get(position);
        				itemHodler.btn_up.setBackgroundResource(R.drawable.login);
        				 Log.i("qwj", "position=="+DiagInfos.get(position));
                    	context.delDiagInfo(itemHodler.s);
                    	
          				
                    }else{
                    	
            				itemHodler.btn_up.setText("ɾ��");
            				itemHodler.s=DiagInfos.get(position);
            				itemHodler.btn_up.setBackgroundResource(R.drawable.btn_orange);
            				 Log.i("qwj", "position2=="+position);
            				context.addDiagInfo(itemHodler.s);
            				
                    }
				
				
			}
		});
		
		return convertView;
	}
	
	class ItemHodler {
		TextView tv_text;
		Button btn_up;
		DiagInfo s;
	}
	
}

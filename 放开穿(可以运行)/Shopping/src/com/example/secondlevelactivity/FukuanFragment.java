package com.example.secondlevelactivity;

import java.util.ArrayList;
import java.util.List;

import com.example.adapters.PrePayAdapter;
import com.example.infoclass.PrePay;
import com.example.shopping.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FukuanFragment extends Fragment{

	private View contentView;
	private List<PrePay> data=new ArrayList<PrePay>();
	private PrePayAdapter adapter;
	private ListView lv_pp;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,Bundle savedInstanceState) {
		
		contentView=inflater.inflate(R.layout.fragment_os_fukuan,container,false);
		
		lv_pp=(ListView) contentView.findViewById(R.id.lv_os_prefukuan);
		
		someData();
		
		adapter=new PrePayAdapter(data, getActivity());
		
		lv_pp.setAdapter(adapter);
		
		return contentView;
	}
	
	private void someData(){
		
		data.clear();
		
		for(int i=0;i<2;i++){
			
			data.add(new PrePay(0,0,0,"1000.00",null,null));
			
		}
		
	}
	
}

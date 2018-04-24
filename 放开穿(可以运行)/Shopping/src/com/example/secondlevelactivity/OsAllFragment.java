package com.example.secondlevelactivity;

import java.util.ArrayList;
import java.util.List;

import com.example.adapters.MyOSAllAdapter;
import com.example.infoclass.MyOSAllContent;
import com.example.shopping.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class OsAllFragment extends Fragment implements OnItemClickListener{

	private View contentView;
	private ListView lv_os_all;
	private MyOSAllAdapter adapter;
	private List<MyOSAllContent> data=new ArrayList<MyOSAllContent>();
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,Bundle savedInstanceState) {
		
		contentView=inflater.inflate(R.layout.fragment_os_all, container,false);
		
		lv_os_all=(ListView) contentView.findViewById(R.id.lv_os_all);
		lv_os_all.setOnItemClickListener(this);
		
		someData();
		
		adapter=new MyOSAllAdapter(data, getActivity());
		
		lv_os_all.setAdapter(adapter);
		
		return contentView;
	}
	
	private void someData(){
		
		data.clear();
		
		for(int i=0;i<2;i++){
			
			data.add(new MyOSAllContent(0,0,0,"500","交易关闭"));
			
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	
		switch (parent.getId()) {
		case R.id.lv_os_all:
			
			Toast.makeText(getActivity(), "第"+position+"条",0).show();
			
			break;

		default:
			break;
		}
		
	}
	
}

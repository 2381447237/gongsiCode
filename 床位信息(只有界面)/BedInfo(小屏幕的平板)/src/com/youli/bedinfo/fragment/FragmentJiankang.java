package com.youli.bedinfo.fragment;

import com.youli.bedinfo.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentJiankang extends Fragment{

	private View contentView;
	
	private TextView jiankang_info4;
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container, Bundle savedInstanceState) {
		
		contentView=inflater.inflate(R.layout.fragment_jiankang,container,false);
		
		jiankang_info4=(TextView) contentView.findViewById(R.id.jiankang_info4);
		
		jiankang_info4.setFocusable(true);
		jiankang_info4.requestFocus();
		
		return contentView;
	}
	
}

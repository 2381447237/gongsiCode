package com.example.secondlevelactivity;

import com.example.shopping.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PinglunFragment extends Fragment{
	
	private View contentView;
	
@Override
public View onCreateView(LayoutInflater inflater,
		ViewGroup container,Bundle savedInstanceState) {
	
	contentView=inflater.inflate(R.layout.fragment_os_pingjia,container,false);
	
	return contentView;
}
}

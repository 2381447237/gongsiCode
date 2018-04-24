package com.fc.main.beans;

import java.util.ArrayList;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class MainPageAdapter extends PagerAdapter {
	private ArrayList<View> view_list;
	
	public MainPageAdapter(ArrayList<View> view_list) {
		super();
		this.view_list = view_list;
	}

	@Override
	public int getCount() {
		
		return view_list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
	
		return arg0 == arg1;
	}

//	@Override
//	public void destroyItem(ViewGroup container, int position, Object object) {
//		// TODO Auto-generated method stub
//		super.destroyItem(container, position, object);
//		 ((ViewPager)container).removeView(view_list.get(position));
//	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		 View view = (View)object;
	        ((ViewPager) container).removeView(view);
	        view = null;
	}
	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public void finishUpdate(ViewGroup container) {
		// TODO Auto-generated method stub
		super.finishUpdate(container);
	}


	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		((ViewPager)container).addView(view_list.get(position));
		return view_list.get(position);
	}
	
}

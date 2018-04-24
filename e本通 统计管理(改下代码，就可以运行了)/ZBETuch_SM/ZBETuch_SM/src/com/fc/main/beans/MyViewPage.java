package com.fc.main.beans;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class MyViewPage extends PagerAdapter {
	private ArrayList<View> view_list;
	
	public MyViewPage(ArrayList<View> view_list) {
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

	@Override
	public Object instantiateItem(View container, int position) {
		/* if(((ViewPager) container).getChildCount()==9)  
         {  
             ((ViewPager) container).removeView(view_list.get(position%9));  
         }  
         ((ViewPager) container).addView(view_list.get(position%9), 0);  

         return view_list.get(position%9);  */
		((ViewPager) container).addView(view_list.get(position), 0);
		return view_list.get(position);
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		((ViewPager)container).removeView(view_list.get(position));
	}

	@Override
	public void startUpdate(View container) {

	}

	@Override
	public CharSequence getPageTitle(int position) {
		return super.getPageTitle(position);
	}

	
}

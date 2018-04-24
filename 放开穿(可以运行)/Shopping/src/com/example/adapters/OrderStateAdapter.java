package com.example.adapters;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class OrderStateAdapter extends FragmentPagerAdapter{

	private ArrayList<Fragment> mFragmentList;
	
	public OrderStateAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
		super(fm);
		mFragmentList=fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
	
		return mFragmentList!=null?mFragmentList.get(arg0):null;
	}

	@Override
	public int getCount() {
		
		return mFragmentList!=null?mFragmentList.size():0;
	}

}

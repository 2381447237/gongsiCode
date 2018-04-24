package com.example.adapters;


import java.util.ArrayList;
import com.example.infoclass.Shop;
import com.example.layout.BidirSlidingLayout;
import com.example.secondlevelactivity.DetailActivity;
import com.example.shopping.R;
import com.example.shopping.ShopFragment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class Myadapter extends PagerAdapter{

	ImageView[] mImageViews;
	Context context;
	ArrayList<String> list;
	String ItemName;
	Shop shop;
	
	@Override
	public int getCount() {
		return list.size();
	}

	public Myadapter(ImageView[] mImageViews,ArrayList<String> list,Shop shop ,Context context){
		this.mImageViews=mImageViews;
		this.context=context;
		this.list=list;
		this.shop=shop;
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		//((ViewPager)container).removeView(mImageViews[position % mImageViews.length]);
	}

	/**
	 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		try {    
//			final View view=mImageViews[position % mImageViews.length];
//			
//			view.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					
//					if(BidirSlidingLayout.isLeftDisplay==false&&BidirSlidingLayout.isRightDisplay==false){
//						Intent intent=new Intent(context, DetailActivity.class);
//						intent.putExtra("shop",shop);
//						context.startActivity(intent);
//					}else{
//						view.setFocusable(false);
//					//	view.setClickable(false);
//						BidirSlidingLayout.isLeftDisplay=false;
//						BidirSlidingLayout.isRightDisplay=false;
//						ShopFragment.zhiding.setVisibility(View.VISIBLE);
//					}
//				}
//			});		
			
            ((ViewPager)container).addView(mImageViews[position % mImageViews.length], 0);  
        }catch(Exception e){  
        }  
        return mImageViews[position % mImageViews.length];  
	}
	
}

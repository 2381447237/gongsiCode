package com.example.cusview;

import com.example.layout.BidirSlidingLayout;
import com.example.shopping.R;
import com.example.shopping.ShopFragment;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Toast;

public class CusviewViewPager extends android.support.v4.view.ViewPager {

	public static boolean isSlidingViewPager = false;

	 // 滑动距离及坐标 归还父控件焦点
	private float xDistance,yDistance,xLast,yLast,xDown;
	
	public CusviewViewPager(Context context) {
		super(context);
	}

	public CusviewViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		getParent().requestDisallowInterceptTouchEvent(true);
		
        switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			
			xDistance=yDistance=0f;
			xLast=ev.getX();
			yLast=ev.getY();
			xDown=ev.getX();
			
			break;
			
		case MotionEvent.ACTION_MOVE:
			
			final float curX=ev.getX();
			final float curY=ev.getY();
			
			xDistance+=Math.abs(curX-xLast);
			yDistance+=Math.abs(curY-yLast);
			
			xLast=curX;
			yLast=curY;
			
			if(xDistance<yDistance){
				getParent().requestDisallowInterceptTouchEvent(false);
			}else{
				getParent().requestDisallowInterceptTouchEvent(true);
			}
			
			break;
			
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			
			 CusviewViewPager.isSlidingViewPager = false;
			 BidirSlidingLayout.isSlidingMenu = false;
			
			break;

			
			
		default:
			break;
		}
		
		return super.dispatchTouchEvent(ev);
	}
	
//	//分发事件处理
//		@Override
//		public boolean dispatchTouchEvent(MotionEvent ev) {
//			switch (ev.getAction()) {
//			case MotionEvent.ACTION_DOWN://按下去
//				//当按下去时，滑动事件交给轮播图来处理（ViewPager）
//				getParent().requestDisallowInterceptTouchEvent(true);//请求不允许拦截事件，为true，表示不拦截，为假，表示拦截
//				mDownX = (int) ev.getX();
//				mDownY = (int)ev.getY(); //按下去的Y轴方向的值
//				break;
//			case MotionEvent.ACTION_MOVE://滑动，移动
//				int moveX=(int) ev.getX();//滑动后的Y轴的值
//				int moveY=(int)ev.getY();//滑动后的Y轴的值 
//				
//				//1. 判断是X轴滑动还是Y轴的滑动 ,假如y轴的偏移量大于x轴的偏移量，则y轴的移动
//				if(Math.abs((moveY-mDownY))-Math.abs((moveX-mDownX))>0){
//					//y轴移动，轮播图不处理滑动事件
//					getParent().requestDisallowInterceptTouchEvent(false);//表示拦截滑动事件，轮播图不处理滑动事件
//				}
//				//2. 判断是X轴滑动还是Y轴的滑动 ,假如y轴的偏移量小于x轴的偏移量，则x轴的移动
//				else{
//					//2.1 假如 moveX-mDownX <0 ，表示从右往左移动 ，假如当前的轮播图片是最后一张，则轮播图不处理滑动事件
//					if(moveX-mDownX<0&&getCurrentItem()==getAdapter().getCount()-1){
//						getParent().requestDisallowInterceptTouchEvent(true);//表示拦截滑动事件，轮播图不处理滑动事件
//					}
//					
//					//2.2 假如 moveX-mDownX <0 ，表示从右往左移动 ，假如当前的轮播图片不是最后一张，则轮播图不处理滑动事件
//					else if(moveX-mDownX<0&&getCurrentItem()<getAdapter().getCount()-1){
//						getParent().requestDisallowInterceptTouchEvent(true);//表示不拦截滑动事件，由轮播图处理滑动事件
//					}
//					//2.3 假如 moveX-mDownX >0 ，表示从左往右移动 ，假如当前的轮播图片是第一张图片，则轮播图不处理滑动事件
//					else if(moveX-mDownX>0&&getCurrentItem()==0){
//						getParent().requestDisallowInterceptTouchEvent(true);//表示拦截滑动事件，轮播图不处理滑动事件
//					}
//					//2.4 假如 moveX-mDownX >0 ，表示从左往右移动 ，假如当前的轮播图片不是第一张图片，则轮播图处理滑动事件
//					else if(moveX-mDownX>0&&getCurrentItem()>0){
//						getParent().requestDisallowInterceptTouchEvent(true);//表示不拦截滑动事件，由轮播图处理滑动事件
//					}
//				}
//				
//				break;
//			case MotionEvent.ACTION_UP://抬起
//				
//				CusviewViewPager.isSlidingViewPager = false;
//				BidirSlidingLayout.isSlidingMenu = false;
//				
//				break;
//
//			default:
//				break;
//			}
//			return super.dispatchTouchEvent(ev);
//		}
		
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN://按下去
//			//当按下去时，滑动事件交给轮播图来处理（ViewPager）
//			getParent().requestDisallowInterceptTouchEvent(true);
//			//请求不允许拦截事件，为true，表示不拦截，为false，表示拦截
//			mDownX=(int) ev.getX();
//			mDownY=(int) ev.getY();
//
//			break;
//
//		case MotionEvent.ACTION_MOVE://滑动，移动
//
//			int moveX=(int) ev.getX();
//			int moveY=(int) ev.getY();
//			
//			if(Math.abs((moveY-mDownY))-Math.abs((moveX-mDownX))>0){
//				getParent().requestDisallowInterceptTouchEvent(false);
//			}else{
//				if(moveX-mDownX<0&&getCurrentItem()==getAdapter().getCount()-1){
//					getParent().requestDisallowInterceptTouchEvent(false);
//				}else if(moveX-mDownX<0&&getCurrentItem()<getAdapter().getCount()-1){
//					getParent().requestDisallowInterceptTouchEvent(true);
//				}else if(moveX-mDownX>0&&getCurrentItem()==0){
//					getParent().requestDisallowInterceptTouchEvent(false);
//				}else if(moveX-mDownX>0&&getCurrentItem()>0){
//					getParent().requestDisallowInterceptTouchEvent(true);
//				}
//			}
//
//			break;
//
//		case MotionEvent.ACTION_UP:
//
//			CusviewViewPager.isSlidingViewPager = false;
//			BidirSlidingLayout.isSlidingMenu = false;
//
//			break;
//		default:
//			break;
//		}
//
//		return super.dispatchTouchEvent(ev);
//	}

//	@Override
//	protected boolean canScroll(View v, boolean checkV, int dx, int x,
//			int y) {
//		
//		if(v!=this&&v instanceof ViewPager){
//			return true;
//		}
//		
//		return super.canScroll(v,checkV,dx,x,y);
//	}
	
	// @Override
	// public boolean dispatchTouchEvent(MotionEvent ev) {
	//
	// getParent().requestDisallowInterceptTouchEvent(true);
	//
	// switch (ev.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	//
	// xDistance=yDistance=0f;
	//
	// xLast=ev.getX();
	// yLast=ev.getY();
	// xDown=ev.getX();
	// mLeft=ev.getX();
	//
	// break;
	//
	// case MotionEvent.ACTION_MOVE:
	//
	// final float curX=ev.getX();
	// final float curY=ev.getY();
	//
	// xDistance +=Math.abs(curX-xLast);
	// yDistance +=Math.abs(curY-yLast);
	//
	// xLast=curX;
	// yLast=curY;
	//
	// if(mLeft<100||xDistance<yDistance){
	// getParent().requestDisallowInterceptTouchEvent(false);
	// }else{
	// if(getCurrentItem()==0){
	// if(curX<xDown){
	// getParent().requestDisallowInterceptTouchEvent(true);
	// }else{
	// getParent().requestDisallowInterceptTouchEvent(false);
	// }
	// }else if(getCurrentItem()==(getAdapter().getCount()-1)){
	//
	// if(curX>xDown){
	// getParent().requestDisallowInterceptTouchEvent(true);
	// }else{
	// getParent().requestDisallowInterceptTouchEvent(false);
	// }
	//
	// }else{
	// getParent().requestDisallowInterceptTouchEvent(true);
	// }
	// }
	//
	// break;
	//
	// case MotionEvent.ACTION_UP:
	//
	// CusviewViewPager.isSlidingViewPager = false;
	// BidirSlidingLayout.isSlidingMenu = false;
	//
	// break;
	// default:
	// break;
	// }
	//
	// return super.dispatchTouchEvent(ev);
	// }

	// @Override
	// public boolean dispatchTouchEvent(MotionEvent ev) {
	//
	// getParent().requestDisallowInterceptTouchEvent(true);
	//
	// switch (ev.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	//
	// xDistance=yDistance=0f;
	//
	// xLast=ev.getX();
	// yLast=ev.getY();
	// mLeft=ev.getX();
	//
	// break;
	//
	// case MotionEvent.ACTION_MOVE:
	//
	// final float curX=ev.getX();
	// final float curY=ev.getY();
	//
	// xDistance +=Math.abs(curX-xLast);
	// yDistance +=Math.abs(curY-yLast);
	//
	// xLast=curX;
	// yLast=curY;
	//
	// if(mLeft<100||xDistance<yDistance){
	// getParent().requestDisallowInterceptTouchEvent(false);
	// }else{
	// getParent().requestDisallowInterceptTouchEvent(true);
	// }
	//
	// break;
	//
	// case MotionEvent.ACTION_UP:
	//
	// CusviewViewPager.isSlidingViewPager = false;
	// BidirSlidingLayout.isSlidingMenu = false;
	//
	// break;
	// default:
	// break;
	// }
	//
	// return super.dispatchTouchEvent(ev);
	// }

	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// isSlidingViewPager=true;
	// return super.onTouchEvent(event);
	// //return true;
	// }

	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	//
	// //isSlidingViewPager=true;
	// //return super.onInterceptTouchEvent(ev);
	// return true;
	// //viewPager点击没反应，其他的效果完美
	// //return false;
	// //viewPager不能滑动了
	//
	// }
	
}

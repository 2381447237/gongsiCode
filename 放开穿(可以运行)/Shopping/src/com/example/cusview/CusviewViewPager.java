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

	 // �������뼰���� �黹���ؼ�����
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
	
//	//�ַ��¼�����
//		@Override
//		public boolean dispatchTouchEvent(MotionEvent ev) {
//			switch (ev.getAction()) {
//			case MotionEvent.ACTION_DOWN://����ȥ
//				//������ȥʱ�������¼������ֲ�ͼ������ViewPager��
//				getParent().requestDisallowInterceptTouchEvent(true);//�������������¼���Ϊtrue����ʾ�����أ�Ϊ�٣���ʾ����
//				mDownX = (int) ev.getX();
//				mDownY = (int)ev.getY(); //����ȥ��Y�᷽���ֵ
//				break;
//			case MotionEvent.ACTION_MOVE://�������ƶ�
//				int moveX=(int) ev.getX();//�������Y���ֵ
//				int moveY=(int)ev.getY();//�������Y���ֵ 
//				
//				//1. �ж���X�Ử������Y��Ļ��� ,����y���ƫ��������x���ƫ��������y����ƶ�
//				if(Math.abs((moveY-mDownY))-Math.abs((moveX-mDownX))>0){
//					//y���ƶ����ֲ�ͼ���������¼�
//					getParent().requestDisallowInterceptTouchEvent(false);//��ʾ���ػ����¼����ֲ�ͼ���������¼�
//				}
//				//2. �ж���X�Ử������Y��Ļ��� ,����y���ƫ����С��x���ƫ��������x����ƶ�
//				else{
//					//2.1 ���� moveX-mDownX <0 ����ʾ���������ƶ� �����統ǰ���ֲ�ͼƬ�����һ�ţ����ֲ�ͼ���������¼�
//					if(moveX-mDownX<0&&getCurrentItem()==getAdapter().getCount()-1){
//						getParent().requestDisallowInterceptTouchEvent(true);//��ʾ���ػ����¼����ֲ�ͼ���������¼�
//					}
//					
//					//2.2 ���� moveX-mDownX <0 ����ʾ���������ƶ� �����統ǰ���ֲ�ͼƬ�������һ�ţ����ֲ�ͼ���������¼�
//					else if(moveX-mDownX<0&&getCurrentItem()<getAdapter().getCount()-1){
//						getParent().requestDisallowInterceptTouchEvent(true);//��ʾ�����ػ����¼������ֲ�ͼ�������¼�
//					}
//					//2.3 ���� moveX-mDownX >0 ����ʾ���������ƶ� �����統ǰ���ֲ�ͼƬ�ǵ�һ��ͼƬ�����ֲ�ͼ���������¼�
//					else if(moveX-mDownX>0&&getCurrentItem()==0){
//						getParent().requestDisallowInterceptTouchEvent(true);//��ʾ���ػ����¼����ֲ�ͼ���������¼�
//					}
//					//2.4 ���� moveX-mDownX >0 ����ʾ���������ƶ� �����統ǰ���ֲ�ͼƬ���ǵ�һ��ͼƬ�����ֲ�ͼ�������¼�
//					else if(moveX-mDownX>0&&getCurrentItem()>0){
//						getParent().requestDisallowInterceptTouchEvent(true);//��ʾ�����ػ����¼������ֲ�ͼ�������¼�
//					}
//				}
//				
//				break;
//			case MotionEvent.ACTION_UP://̧��
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
//		case MotionEvent.ACTION_DOWN://����ȥ
//			//������ȥʱ�������¼������ֲ�ͼ������ViewPager��
//			getParent().requestDisallowInterceptTouchEvent(true);
//			//�������������¼���Ϊtrue����ʾ�����أ�Ϊfalse����ʾ����
//			mDownX=(int) ev.getX();
//			mDownY=(int) ev.getY();
//
//			break;
//
//		case MotionEvent.ACTION_MOVE://�������ƶ�
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
	// //viewPager���û��Ӧ��������Ч������
	// //return false;
	// //viewPager���ܻ�����
	//
	// }
	
}

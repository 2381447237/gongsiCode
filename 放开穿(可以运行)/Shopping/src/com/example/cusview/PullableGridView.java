package com.example.cusview;

import com.example.adapters.Myadapter;
import com.example.layout.BidirSlidingLayout;
import com.lidroid.xutils.view.annotation.event.OnTouch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class PullableGridView extends GridView implements Pullable {

//	private int startX;
//	private int startY;
//	private int lastX;
//	private int lastY;

	public PullableGridView(Context context) {
		super(context);
	}

	public PullableGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullableGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {

		if (getCount() == 0) {
			// û��item��ʱ��Ҳ��������ˢ��
			if (CusviewViewPager.isSlidingViewPager == true) {
				return false;
			}

			if (BidirSlidingLayout.isSlidingMenu == true) {
				return false;
			}

			return true;

		} else if (getFirstVisiblePosition() == 0
				&& getChildAt(0).getTop() >= 0) {
			// ����ListView�Ķ�����
			if (CusviewViewPager.isSlidingViewPager == true) {
				return false;
			}
			if (BidirSlidingLayout.isSlidingMenu == true) {
				return false;
			}
			return true;

		} else
			return false;
	}

	 @Override
	 public boolean canPullUp() {
	 if (getCount() == 0) {
	 // û��item��ʱ��Ҳ������������
	 return true;
	 } else if (getLastVisiblePosition() == (getCount() - 1)) {
	 // �����ײ���
	 if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) !=
	 null
	 && getChildAt(
	 getLastVisiblePosition()
	 - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
	 return true;
	 }
	 return false;
	 }

	// �������������ôд����ôд�Ļ���gridview�����������Ŀ�ͻ���ʾ��ȫ��
//	@Override
//	public boolean canPullUp() {
//
//		if (getCount() == 0) {
//			// û��item��ʱ��Ҳ������������
//			return true;
//		} else if (getLastVisiblePosition() == (getCount() - 1)) {
//
//			// �����ײ���
//			if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
//					&& getChildAt(
//							getLastVisiblePosition()
//									- getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
//				if (CusviewViewPager.isSlidingViewPager == true) {
//					return false;
//				}
//			if (BidirSlidingLayout.isSlidingMenu == true) {
//				return false;
//			}
//			return true;
//		}
//		return false;
//	}

	// �������뼰���� �黹���ؼ�����
	 private float xDistance,yDistance,xLast,yLast,xDown;
	 
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
			
			xDistance +=Math.abs(curX-xLast);
			yDistance +=Math.abs(curY-yLast);
			xLast=curX;
			yLast=curY;
			
			if(xDistance<yDistance){
				getParent().requestDisallowInterceptTouchEvent(true);
			}else{
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			
			break;
		
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;

		default:
			break;
		}
		 
		return super.dispatchTouchEvent(ev);
	}
	 
	// �Լ�д�������������û��ʲô�ð�====================================
//	@Override
//	public boolean onInterceptTouchEvent(MotionEvent ev) {
//
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//
//			startX = (int) ev.getX();
//			startY = (int) ev.getY();
//
//			break;
//
//		case MotionEvent.ACTION_MOVE:
//
//			lastX = (int) ev.getX();
//			lastY = (int) ev.getY();
//
//			int dx = lastX - startX;
//			int dy = lastY - startY;
//
//			if (dx > 0 && Math.abs(dx) > Math.abs(dy)) {// �һ�
//
//				return false;
//			}
//
//			if (dx < 0 && Math.abs(dx) > Math.abs(dy)) {// ��
//
//				return false;
//			}
//
//			// if(dy>0&&Math.abs(dy)>Math.abs(dx)){//�»�
//			//
//			// Log.i("2016/7/25","���»�=====================");
//			//
//			// }
//			//
//			// if(dy<0&&Math.abs(dy)>Math.abs(dx)){//�ϻ�
//			//
//			// Log.i("2016/7/25","���ϻ�=====================");
//			//
//			// }
//
//			break;
//
//		default:
//			break;
//		}
//
//		return super.onInterceptTouchEvent(ev);
//	}

	// @Override
	// public boolean dispatchTouchEvent(MotionEvent ev) {
	//
	// switch (ev.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	//
	// startX = (int) ev.getX();
	// startY = (int) ev.getY();
	//
	// break;
	//
	// case MotionEvent.ACTION_MOVE:
	//
	// lastX = (int) ev.getX();
	// lastY = (int) ev.getY();
	//
	// int dx = lastX - startX;// ����ƫ����
	// int dy = lastY - startY;// ����ƫ����
	//
	// if (dx > 0 && Math.abs(dx) > Math.abs(dy)) {// �һ�
	// }
	//
	// if (dx < 0 && Math.abs(dx) > Math.abs(dy)) {// ��
	//
	// }
	//
	// if (dy > 0 && Math.abs(dy) > Math.abs(dx)) {// �»�
	// }
	//
	// if (dy < 0 && Math.abs(dy) > Math.abs(dx)) {// �ϻ�
	// }
	//
	// break;
	//
	// case MotionEvent.ACTION_UP:
	// break;
	//
	// default:
	// break;
	// }
	//
	// return super.dispatchTouchEvent(ev);
	// }

}

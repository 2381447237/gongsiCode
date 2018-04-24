package com.fc.main.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class LongPressView extends View {

	private int mLastMotionX,mLastMotionY;
	private boolean isMoved;
	private boolean isReleased;
	private int mCounter;
	private Runnable mLongPressRunnable;
	private static final int TOUCH_SLOP=20;


	public LongPressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mLongPressRunnable=new Runnable() {
			
			@Override
			public void run() {
				mCounter--;
				if(mCounter>0||isReleased||isMoved) 
					return;
				performLongClick();
			}
		};
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		 int x = (int) event.getX();  
	        int y = (int) event.getY(); 
	        switch(event.getAction()) {  
	        case MotionEvent.ACTION_DOWN:  
	            mLastMotionX = x;  
	            mLastMotionY = y;  
	            mCounter++;  
	            isReleased = false;  
	            isMoved = false;  
	            postDelayed(mLongPressRunnable, 2000);  
	            break;  
	        case MotionEvent.ACTION_MOVE:  
	            if(isMoved) break;  
	            if(Math.abs(mLastMotionX-x) > TOUCH_SLOP   
	                    || Math.abs(mLastMotionY-y) > TOUCH_SLOP) {  
	                //�ƶ�������ֵ�����ʾ�ƶ���  
	                isMoved = true;  
	            }  
	            break;  
	        case MotionEvent.ACTION_UP:  
	            //�ͷ���  
	            isReleased = true;  
	            break;  
	        }  
	        return true;  
	}
	
}

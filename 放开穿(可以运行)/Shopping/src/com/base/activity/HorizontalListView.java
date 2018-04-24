package com.base.activity;

//import java.util.LinkedList;
//import java.util.Queue;
//import android.content.Context;
//import android.database.DataSetObserver;
//import android.graphics.Rect;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.GestureDetector;
//import android.view.GestureDetector.OnGestureListener;
//import android.view.View.MeasureSpec;
//import android.view.ViewGroup.LayoutParams;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListAdapter;
//import android.widget.Scroller;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.AdapterView.OnItemLongClickListener;
//import android.widget.AdapterView.OnItemSelectedListener;
//
//public class HorizontalListView extends AdapterView<ListAdapter> {
//
//	public boolean mAlwaysOverrideTouch = true;
//	protected ListAdapter mAdapter;
//	private int mLeftViewIndex = -1;
//	private int mRightViewIndex = 0;
//	protected int mCurrentX;
//	protected int mNextX;
//	private int mMaxX = Integer.MAX_VALUE;
//	private int mDisplayOffset = 0;
//	protected Scroller mScroller;
//	private GestureDetector mGesture;
//	private Queue<View> mRemovedViewQueue = new LinkedList<View>();
//	private OnItemSelectedListener mOnItemSelected;
//	private OnItemClickListener mOnItemClicked;
//	private OnItemLongClickListener mOnItemLongClicked;
//	private boolean mDataChanged = false;
//	
//
//	public HorizontalListView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		initView();
//	}
//	
//	private synchronized void initView() {
//		mLeftViewIndex = -1;
//		mRightViewIndex = 0;
//		mDisplayOffset = 0;
//		mCurrentX = 0;
//		mNextX = 0;
//		mMaxX = Integer.MAX_VALUE;
//		mScroller = new Scroller(getContext());
//		mGesture = new GestureDetector(getContext(), mOnGesture);
//	}
//	
//	@Override
//	public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
//		mOnItemSelected = listener;
//	}
//	
//	@Override
//	public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
//		mOnItemClicked = listener;
//	}
//	
//	@Override
//	public void setOnItemLongClickListener(AdapterView.OnItemLongClickListener listener) {
//		mOnItemLongClicked = listener;
//	}
//
//	private DataSetObserver mDataObserver = new DataSetObserver() {
//
//		@Override
//		public void onChanged() {
//			synchronized(HorizontalListView.this){
//				mDataChanged = true;
//			}
//			invalidate();
//			requestLayout();
//		}
//
//		@Override
//		public void onInvalidated() {
//			reset();
//			invalidate();
//			requestLayout();
//		}
//		
//	};
//
//	@Override
//	public ListAdapter getAdapter() {
//		return mAdapter;
//	}
//
//	@Override
//	public View getSelectedView() {
//		//TODO: implement
//		return null;
//	}
//
//	@Override
//	public void setAdapter(ListAdapter adapter) {
//		if(mAdapter != null) {
//			mAdapter.unregisterDataSetObserver(mDataObserver);
//		}
//		mAdapter = adapter;
//		mAdapter.registerDataSetObserver(mDataObserver);
//		reset();
//	}
//	
//	private synchronized void reset(){
//		initView();
//		removeAllViewsInLayout();
//        requestLayout();
//	}
//
//	@Override
//	public void setSelection(int position) {
//		//TODO: implement
//	}
//	
//	private void addAndMeasureChild(final View child, int viewPos) {
//		LayoutParams params = child.getLayoutParams();
//		if(params == null) {
//			params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
//		}
//
//		addViewInLayout(child, viewPos, params, true);
//		child.measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.AT_MOST),
//				MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.AT_MOST));
//	}
//	
//	
//
//	@Override
//	protected synchronized void onLayout(boolean changed, int left, int top, int right, int bottom) {
//		super.onLayout(changed, left, top, right, bottom);
//
//		if(mAdapter == null){
//			return;
//		}
//		
//		if(mDataChanged){
//			int oldCurrentX = mCurrentX;
//			initView();
//			removeAllViewsInLayout();
//			mNextX = oldCurrentX;
//			mDataChanged = false;
//		}
//
//		if(mScroller.computeScrollOffset()){
//			int scrollx = mScroller.getCurrX();
//			mNextX = scrollx;
//		}
//		
//		if(mNextX <= 0){
//			mNextX = 0;
//			mScroller.forceFinished(true);
//		}
//		if(mNextX >= mMaxX) {
//			mNextX = mMaxX;
//			mScroller.forceFinished(true);
//		}
//		
//		int dx = mCurrentX - mNextX;
//		
//		removeNonVisibleItems(dx);
//		fillList(dx);
//		positionItems(dx);
//		
//		mCurrentX = mNextX;
//		
//		if(!mScroller.isFinished()){
//			post(new Runnable(){
//				@Override
//				public void run() {
//					requestLayout();
//				}
//			});
//			
//		}
//	}
//	
//	private void fillList(final int dx) {
//		int edge = 0;
//		View child = getChildAt(getChildCount()-1);
//		if(child != null) {
//			edge = child.getRight();
//		}
//		fillListRight(edge, dx);
//		
//		edge = 0;
//		child = getChildAt(0);
//		if(child != null) {
//			edge = child.getLeft();
//		}
//		fillListLeft(edge, dx);
//		
//		
//	}
//	
//	private void fillListRight(int rightEdge, final int dx) {
//		while(rightEdge + dx < getWidth() && mRightViewIndex < mAdapter.getCount()) {
//			
//			View child = mAdapter.getView(mRightViewIndex, mRemovedViewQueue.poll(), this);
//			addAndMeasureChild(child, -1);
//			rightEdge += child.getMeasuredWidth();
//			
//			if(mRightViewIndex == mAdapter.getCount()-1) {
//				mMaxX = mCurrentX + rightEdge - getWidth();
//			}
//			
//			if (mMaxX < 0) {
//				mMaxX = 0;
//			}
//			mRightViewIndex++;
//		}
//		
//	}
//	
//	private void fillListLeft(int leftEdge, final int dx) {
//		while(leftEdge + dx > 0 && mLeftViewIndex >= 0) {
//			View child = mAdapter.getView(mLeftViewIndex, mRemovedViewQueue.poll(), this);
//			addAndMeasureChild(child, 0);
//			leftEdge -= child.getMeasuredWidth();
//			mLeftViewIndex--;
//			mDisplayOffset -= child.getMeasuredWidth();
//		}
//	}
//	
//	private void removeNonVisibleItems(final int dx) {
//		View child = getChildAt(0);
//		while(child != null && child.getRight() + dx <= 0) {
//			mDisplayOffset += child.getMeasuredWidth();
//			mRemovedViewQueue.offer(child);
//			removeViewInLayout(child);
//			mLeftViewIndex++;
//			child = getChildAt(0);
//			
//		}
//		
//		child = getChildAt(getChildCount()-1);
//		while(child != null && child.getLeft() + dx >= getWidth()) {
//			mRemovedViewQueue.offer(child);
//			removeViewInLayout(child);
//			mRightViewIndex--;
//			child = getChildAt(getChildCount()-1);
//		}
//	}
//	
//	private void positionItems(final int dx) {
//		if(getChildCount() > 0){
//			mDisplayOffset += dx;
//			int left = mDisplayOffset;
//			for(int i=0;i<getChildCount();i++){
//				View child = getChildAt(i);
//				int childWidth = child.getMeasuredWidth();
//				child.layout(left, 0, left + childWidth, child.getMeasuredHeight());
//				left += childWidth + child.getPaddingRight();
//			}
//		}
//	}
//	
//	public synchronized void scrollTo(int x) {
//		mScroller.startScroll(mNextX, 0, x - mNextX, 0);
//		requestLayout();
//	}
//	
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		boolean handled = super.dispatchTouchEvent(ev);
//		handled |= mGesture.onTouchEvent(ev);
//		return handled;
//	}
//	
//	protected boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//				float velocityY) {
//		synchronized(HorizontalListView.this){
//			mScroller.fling(mNextX, 0, (int)-velocityX, 0, 0, mMaxX, 0, 0);
//		}
//		requestLayout();
//		
//		return true;
//	}
//	
//	protected boolean onDown(MotionEvent e) {
//		mScroller.forceFinished(true);
//		return true;
//	}
//	
//	private OnGestureListener mOnGesture = new GestureDetector.SimpleOnGestureListener() {
//
//		@Override
//		public boolean onDown(MotionEvent e) {
//			return HorizontalListView.this.onDown(e);
//		}
//
//		@Override
//		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//				float velocityY) {
//			return HorizontalListView.this.onFling(e1, e2, velocityX, velocityY);
//		}
//
//		@Override
//		public boolean onScroll(MotionEvent e1, MotionEvent e2,
//				float distanceX, float distanceY) {
//			
//			synchronized(HorizontalListView.this){
//				mNextX += (int)distanceX;
//			}
//			requestLayout();
//			
//			return true;
//		}
//
//		@Override
//		public boolean onSingleTapConfirmed(MotionEvent e) {
//			for(int i=0;i<getChildCount();i++){
//				View child = getChildAt(i);
//				if (isEventWithinView(e, child)) {
//					if(mOnItemClicked != null){
//						mOnItemClicked.onItemClick(HorizontalListView.this, child, mLeftViewIndex + 1 + i, mAdapter.getItemId( mLeftViewIndex + 1 + i ));
//					}
//					if(mOnItemSelected != null){
//						mOnItemSelected.onItemSelected(HorizontalListView.this, child, mLeftViewIndex + 1 + i, mAdapter.getItemId( mLeftViewIndex + 1 + i ));
//					}
//					break;
//				}
//				
//			}
//			return true;
//		}
//		
//		@Override
//		public void onLongPress(MotionEvent e) {
//			int childCount = getChildCount();
//			for (int i = 0; i < childCount; i++) {
//				View child = getChildAt(i);
//				if (isEventWithinView(e, child)) {
//					if (mOnItemLongClicked != null) {
//						mOnItemLongClicked.onItemLongClick(HorizontalListView.this, child, mLeftViewIndex + 1 + i, mAdapter.getItemId(mLeftViewIndex + 1 + i));
//					}
//					break;
//				}
//
//			}
//		}
//
//		private boolean isEventWithinView(MotionEvent e, View child) {
//            Rect viewRect = new Rect();
//            int[] childPosition = new int[2];
//            child.getLocationOnScreen(childPosition);
//            int left = childPosition[0];
//            int right = left + child.getWidth();
//            int top = childPosition[1];
//            int bottom = top + child.getHeight();
//            viewRect.set(left, top, right, bottom);
//            return viewRect.contains((int) e.getRawX(), (int) e.getRawY());
//        }
//	};
//
//	
//
//}
import java.util.HashMap;  
import java.util.Map;  

import com.example.adapters.SameStyleAdapter;
  
import android.content.Context;  
import android.graphics.Color;  
import android.util.AttributeSet;  
import android.util.DisplayMetrics;  
import android.util.Log;  
import android.view.MotionEvent;  
import android.view.View;  
import android.view.View.OnClickListener;  
import android.view.WindowManager;  
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;  
import android.widget.LinearLayout;  
  
public class HorizontalListView extends HorizontalScrollView implements  
        OnClickListener  
{  
  
    /** 
     * ͼƬ����ʱ�Ļص��ӿ� 
     *  
     * @author zhy 
     *  
     */  
    public interface CurrentImageChangeListener  
    {  
        void onCurrentImgChanged(int position, View viewIndicator);  
    }  
  
    /** 
     * ��Ŀ���ʱ�Ļص� 
     *  
     * @author zhy 
     *  
     */  
    public interface OnItemClickListener  
    {  
        void onClick(View view, int pos);  
    }  
  
    private CurrentImageChangeListener mListener;  
  
    private OnItemClickListener mOnClickListener;  
  
    private static final String TAG = "MyHorizontalScrollView";  
  
    /** 
     * HorizontalListView�е�LinearLayout 
     */  
    private LinearLayout mContainer;  
  
    /** 
     * ��Ԫ�صĿ�� 
     */  
    private int mChildWidth;  
    /** 
     * ��Ԫ�صĸ߶� 
     */  
    private int mChildHeight;  
    /** 
     * ��ǰ���һ��ͼƬ��index 
     */  
    private int mCurrentIndex;  
    /** 
     * ��ǰ��һ��ͼƬ���±� 
     */  
    private int mFristIndex;  
    /** 
     * ��ǰ��һ��View 
     */  
    private View mFirstView;  
    /** 
     * ���������� 
     */  
    private SameStyleAdapter mAdapter;  
    /** 
     * ÿ��Ļ�����ʾ�ĸ��� 
     */  
    private int mCountOneScreen;  
    /** 
     * ��Ļ�Ŀ�� 
     */  
    private int mScreenWitdh;  
  
  
    /** 
     * ����View��λ�õļ�ֵ�� 
     */  
    private Map<View, Integer> mViewPos = new HashMap<View, Integer>();  
  
    public HorizontalListView(Context context, AttributeSet attrs)  
    {  
        super(context, attrs);  
        // �����Ļ���  
        WindowManager wm = (WindowManager) context  
                .getSystemService(Context.WINDOW_SERVICE);  
        DisplayMetrics outMetrics = new DisplayMetrics();  
        wm.getDefaultDisplay().getMetrics(outMetrics);  
        mScreenWitdh = outMetrics.widthPixels;  
    }  
  
    @Override  
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
    {  
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);  
        mContainer = (LinearLayout) getChildAt(0);  
    }  
  
    /** 
     * ������һ��ͼƬ 
     */  
    protected void loadNextImg()  
    {  
        // ����߽�ֵ����  
        if (mCurrentIndex == mAdapter.getCount() - 1)  
        {  
            return;  
        }  
        //�Ƴ���һ��ͼƬ���ҽ�ˮƽ����λ����0  
        scrollTo(0, 0);  
        mViewPos.remove(mContainer.getChildAt(0));  
        mContainer.removeViewAt(0);  
          
        //��ȡ��һ��ͼƬ����������onclick�¼����Ҽ���������  
        View view = mAdapter.getView(++mCurrentIndex, null, mContainer);  
        view.setOnClickListener(this);  
        mContainer.addView(view);  
        mViewPos.put(view, mCurrentIndex);  
          
        //��ǰ��һ��ͼƬС��  
        mFristIndex++;  
        //��������˹��������򴥷�  
        if (mListener != null)  
        {  
            notifyCurrentImgChanged();  
        }  
  
    }  
    /** 
     * ����ǰһ��ͼƬ 
     */  
    protected void loadPreImg()  
    {  
        //�����ǰ�Ѿ��ǵ�һ�ţ��򷵻�  
        if (mFristIndex == 0)  
            return;  
        //��õ�ǰӦ����ʾΪ��һ��ͼƬ���±�  
        int index = mCurrentIndex - mCountOneScreen;  
        if (index >= 0)  
        {  
//          mContainer = (LinearLayout) getChildAt(0);  
            //�Ƴ����һ��  
            int oldViewPos = mContainer.getChildCount() - 1;  
            mViewPos.remove(mContainer.getChildAt(oldViewPos));  
            mContainer.removeViewAt(oldViewPos);  
              
            //����View�����һ��λ��  
            View view = mAdapter.getView(index, null, mContainer);  
            mViewPos.put(view, index);  
            mContainer.addView(view, 0);  
            view.setOnClickListener(this);  
            //ˮƽ����λ�������ƶ�view�Ŀ�ȸ�����  
            scrollTo(mChildWidth, 0);  
            //��ǰλ��--����ǰ��һ����ʾ���±�--  
            mCurrentIndex--;  
            mFristIndex--;  
            //�ص�  
            if (mListener != null)  
            {  
                notifyCurrentImgChanged();  
  
            }  
        }  
    }  
  
    /** 
     * ����ʱ�Ļص� 
     */  
    public void notifyCurrentImgChanged()  
    {  
        //��������еı���ɫ�����ʱ������Ϊ��ɫ  
        for (int i = 0; i < mContainer.getChildCount(); i++)  
        {  
            mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);  
        }  
          
        mListener.onCurrentImgChanged(mFristIndex, mContainer.getChildAt(0));  
  
    }  
  
    /** 
     * ��ʼ�����ݣ��������������� 
     *  
     * @param mAdapter 
     */  
    public void initDatas(SameStyleAdapter mAdapter)  
    {  
        this.mAdapter = mAdapter;  
        mContainer = (LinearLayout) getChildAt(0);  
        // ����������е�һ��View  
        final View view = mAdapter.getView(0, null, mContainer);  
        mContainer.addView(view);  
  
        // ǿ�Ƽ��㵱ǰView�Ŀ�͸�  
        if (mChildWidth == 0 && mChildHeight == 0)  
        {  
            int w = View.MeasureSpec.makeMeasureSpec(0,  
                    View.MeasureSpec.UNSPECIFIED);  
            int h = View.MeasureSpec.makeMeasureSpec(0,  
                    View.MeasureSpec.UNSPECIFIED);  
            view.measure(w, h);  
            mChildHeight = view.getMeasuredHeight();  
            mChildWidth = view.getMeasuredWidth();  
            Log.e(TAG, view.getMeasuredWidth() + "," + view.getMeasuredHeight());  
            mChildHeight = view.getMeasuredHeight();  
            // ����ÿ�μ��ض��ٸ�View  
            mCountOneScreen = mScreenWitdh / mChildWidth+2;  
  
            Log.e(TAG, "mCountOneScreen = " + mCountOneScreen  
                    + " ,mChildWidth = " + mChildWidth);  
              
  
        }  
        //��ʼ����һ��Ļ��Ԫ��  
        initFirstScreenChildren(mCountOneScreen);  
    }  
  
    /** 
     * ���ص�һ����View 
     *  
     * @param mCountOneScreen 
     */  
    public void initFirstScreenChildren(int mCountOneScreen)  
    {  
        mContainer = (LinearLayout) getChildAt(0);  
        mContainer.removeAllViews();  
        mViewPos.clear();  
        if(mCountOneScreen>mAdapter.getCount()){
        	mCountOneScreen=mAdapter.getCount();
        }
        for (int i = 0; i < mCountOneScreen; i++)  
        {  
            View view = mAdapter.getView(i, null, mContainer);  
            view.setOnClickListener(this);  
            mContainer.addView(view);  
            mViewPos.put(view, i);  
            mCurrentIndex = i;  
        }  
  
        if (mListener != null)  
        {  
            notifyCurrentImgChanged();  
        }  
  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent ev)  
    {  
        switch (ev.getAction())  
        {  
        case MotionEvent.ACTION_MOVE:  
//          Log.e(TAG, getScrollX() + "");  
  
            int scrollX = getScrollX();  
            // �����ǰscrollXΪview�Ŀ�ȣ�������һ�ţ��Ƴ���һ��  
            if (scrollX >= mChildWidth)  
            {  
                loadNextImg();  
            }  
            // �����ǰscrollX = 0�� ��ǰ����һ�ţ��Ƴ����һ��  
            if (scrollX == 0)  
            {  
                loadPreImg();  
            }  
            break;  
        }  
        return super.onTouchEvent(ev);  
    }  
  
    @Override  
    public void onClick(View v)  
    {  
        if (mOnClickListener != null)  
        {  
            for (int i = 0; i < mContainer.getChildCount(); i++)  
            {  
                mContainer.getChildAt(i).setBackgroundColor(Color.WHITE);  
            }  
            mOnClickListener.onClick(v, mViewPos.get(v));  
        }  
    }  
  
    public void setOnItemClickListener(OnItemClickListener mOnClickListener)  
    {  
        this.mOnClickListener = mOnClickListener;  
    }  
  
    public void setCurrentImageChangeListener(  
            CurrentImageChangeListener mListener)  
    {  
        this.mListener = mListener;  
    }

  
} 

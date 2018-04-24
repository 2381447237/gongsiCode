package com.example.zxing.view;

import com.example.zxing.lib.core.ViewFinderView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
/**
**
* �Զ���ɨ����ʽ
* Created by zhouweilong on 2016/11/2.
*/
public class CustomViewFinderView extends ViewFinderView {

   public CustomViewFinderView(Context context) {
       super(context);
       init();
   }

   public CustomViewFinderView(Context context, AttributeSet attrs) {
       super(context, attrs);
       init();
   }

   public void init() {
       //������
       setSquareViewFinder(true);
       //�Ƿ��ƶ�ɨ����
       setIsMoveScanLine(true);
   }

   @Override
   public void onDraw(Canvas canvas) {
       super.onDraw(canvas);
   }

}

package com.base.activity;

import com.example.shopping.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class RangeSeekBar extends View {

	private float lineWidth = 5.0f;
	private float textSize = 25.0f;

	private int inRangeColor = 0xff0099ff;
	private int outRangeColor = 0xff777777;
	private int textColor = 0xff0099ff;

	private int textMarginBottom = 10;

	private int lowerCenterX;
	private int upperCenterX;

	private int bmpWidth;
	private int bmpHeight;

	private Bitmap lowerBmp;
	private Bitmap upperBmp;

	private Paint inRangePaint;
	private Paint outRangePaint;
	private Paint bmpPaint;
	private Paint textPaint;

	private boolean isLowerMoving = false;
	private boolean isUpperMoving = false;

	private OnRangeChangedListener onRangeChangedListener;

	private int paddingLeft = 50;
	private int paddingRight = 50;
	private int paddingTop = 50;
	private int paddingBottom = 10;

	private int lineHeight;
	private int lineLength = 250;
	private int lineStart = paddingLeft;
	private int lineEnd = lineLength + paddingLeft;

	private float smallValue = 0.0f;
	private float bigValue = 1000.0f;

	private float smallRange = smallValue;
	private float bigRange = bigValue;

	private int textHeight;

	public RangeSeekBar(Context context) {
		super(context);
		init();
	}

	public RangeSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public RangeSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		lowerBmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.bluepoint);
		upperBmp = BitmapFactory.decodeResource(getResources(),
				R.drawable.bluepoint);

		bmpWidth = upperBmp.getWidth();
		bmpHeight = upperBmp.getHeight();

		lowerCenterX = lineStart;
		upperCenterX = lineEnd;

		lineHeight = getHeight() - paddingBottom - lowerBmp.getHeight() / 2;
		textHeight = lineHeight + lowerBmp.getHeight() / 2 + 10;

	}

	private void initPaint() {
		// ���Ʒ�Χ�ڵ�����
		inRangePaint = new Paint();
		inRangePaint.setAntiAlias(true);
		inRangePaint.setStrokeWidth(lineWidth);
		inRangePaint.setColor(inRangeColor);

		// ���Ʒ�Χ�������
		outRangePaint = new Paint();
		outRangePaint.setAntiAlias(true);
		outRangePaint.setStrokeWidth(lineWidth);
		outRangePaint.setColor(outRangeColor);

		// ��ͼƬ����
		bmpPaint = new Paint();

		// ����Χ����
		textPaint = new Paint();
		textPaint.setColor(textColor);
		textPaint.setTextSize(textSize);
		textPaint.setAntiAlias(true);
		textPaint.setStrokeWidth(lineWidth);
	}

	private int measureWidth(int measureSpec) {
		int result = 0;

		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			result = specSize;
		} else {
			result = paddingLeft + paddingRight + bmpWidth * 2;

			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}

		return result;
	}

	private int measureHeight(int measureHeight) {
		int result = 0;

		int specMode = MeasureSpec.getMode(measureHeight);
		int specSize = MeasureSpec.getSize(measureHeight);

		if (specMode == MeasureSpec.EXACTLY) {
			result = bmpHeight * 2;
		} else {
			result = bmpHeight + paddingTop;

			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}

		return result;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		widthMeasureSpec = measureWidth(widthMeasureSpec);
		heightMeasureSpec = measureHeight(heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		bmpWidth = upperBmp.getWidth();
		bmpHeight = upperBmp.getHeight();

		lineHeight = getHeight() - paddingBottom - lowerBmp.getHeight() / 2;
		textHeight = lineHeight - bmpHeight / 2 - textMarginBottom;

		// ����
		Paint linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setStrokeWidth(lineWidth);

		// ���ƴ���ͼƬ����֮���߶�
		linePaint.setColor(inRangeColor);
		canvas.drawLine(lowerCenterX, lineHeight, upperCenterX, lineHeight,
				linePaint);

		// ���ƴ���ͼƬ�������˵��߶�
		linePaint.setColor(outRangeColor);
		canvas.drawLine(lineStart, lineHeight, lowerCenterX, lineHeight,
				linePaint);
		canvas.drawLine(upperCenterX, lineHeight, lineEnd, lineHeight,
				linePaint);

		// ��ͼƬ����
		Paint bmpPaint = new Paint();
		canvas.drawBitmap(lowerBmp, lowerCenterX - bmpWidth / 2, lineHeight
				- bmpHeight / 2, bmpPaint);
		canvas.drawBitmap(lowerBmp, upperCenterX - bmpWidth / 2, lineHeight
				- bmpHeight / 2, bmpPaint);

		// ����Χ����
		Paint textPaint = new Paint();
		textPaint.setColor(textColor);
		textPaint.setTextSize(textSize);
		textPaint.setAntiAlias(true);
		textPaint.setStrokeWidth(lineWidth);
		canvas.drawText(String.format("��%.0f", smallRange), lowerCenterX
				- bmpWidth / 2, textHeight, textPaint);
		canvas.drawText(String.format("��%.0f", bigRange), upperCenterX
				- bmpWidth / 2, textHeight, textPaint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		float xPos = event.getX();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// ������µ�λ���ڴ�ֱ����û����ͼƬ�Ӵ����򲻻Ử������
			float yPos = event.getY();
			if (Math.abs(yPos - lineHeight) > bmpHeight / 2) {
				return false;
			}

			// ��ʾ��ǰ���µĻ�������ߵĻ���
			if (Math.abs(xPos - lowerCenterX) < bmpWidth / 2) {
				isLowerMoving = true;
			}

			// //��ʾ��ǰ���µĻ������ұߵĻ���
			if (Math.abs(xPos - upperCenterX) < bmpWidth / 2) {
				isUpperMoving = true;
			}

			// ������߻�����������ʱ����߻��黬������Ӧ��λ��
			if (xPos >= lineStart && xPos <= lowerCenterX - bmpWidth / 2) {
				lowerCenterX = (int) xPos;
				updateRange();
				postInvalidate();
			}

			// �����ұ߻�����ұ�����ʱ�� �ұ߻��黬������Ӧ��λ��
			if (xPos <= lineEnd && xPos >= upperCenterX + bmpWidth / 2) {
				upperCenterX = (int) xPos;
				updateRange();
				postInvalidate();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			// ������߻���ʱ
			if (isLowerMoving) {
				if (xPos >= lineStart && xPos < upperCenterX - bmpWidth) {
					lowerCenterX = (int) xPos;
					updateRange();
					postInvalidate();
				}
			}

			// �����ұ߻���ʱ
			if (isUpperMoving) {
				if (xPos > lowerCenterX + bmpWidth && xPos < lineEnd) {
					upperCenterX = (int) xPos;
					updateRange();
					postInvalidate();
				}
			}

			break;
		case MotionEvent.ACTION_UP:
			// �޸Ļ���Ļ���״̬Ϊ���ٻ���
			isLowerMoving = false;
			isUpperMoving = false;
			if (null != onRangeChangedListener) {
				onRangeChangedListener.onRangeChanged(smallRange, bigRange);
			}
			break;
		default:
			break;
		}

		return true;
	}

	// ����ָ�������Ӧ�ķ�Χֵ
	private float computRange(float range) {
		return (range - lineStart) * (bigValue - smallValue) / lineLength
				+ smallValue;
	}

	// ��������Ĺ����У����»����Ϸ��ķ�Χ��ʶ
	private void updateRange() {
		smallRange = computRange(lowerCenterX);
		bigRange = computRange(upperCenterX);

		// if (null != onRangeChangedListener) {
		// onRangeChangedListener.onRangeChanged(smallRange, bigRange);
		// }
	}

	// ���û���λ��
//	public void setProgress(float lowerRange,float uppercenterx){
//		
//		this.lowerCenterX = (int) ((lowerRange-smallValue)/(bigValue-smallValue)+lineStart);
//		this.upperCenterX=	(int) ((uppercenterx-smallValue)/(bigValue-smallValue)+lineStart);
//		postInvalidate();
//	}
	
	// ע�Ử�鷶Χֵ�ı��¼��ļ���
	public void setOnRangeChangedListener(
			OnRangeChangedListener onRangeChangedListener) {
		this.onRangeChangedListener = onRangeChangedListener;
	}

	// �����ӿڣ��û��ص��ӿڷ�Χֵ�ĸı�
	public interface OnRangeChangedListener {

		public void onRangeChanged(float lowerRange, float upperRange);

	}

}

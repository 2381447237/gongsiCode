package com.base.activity;

import com.example.shopping.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.graphics.Shader;

/**
 * http://blog.csdn.net/lmj623565791/article/details/41967509
 * 
 * @author zhy
 * 
 */
public class RoundImageView extends ImageView {
	 private ScaleType mScaleType;
	/**
	 * 图片的类型，圆形or圆角
	 */
	private int type;
	public static final int TYPE_CIRCLE = 0;
	public static final int TYPE_ROUND = 1;
	/**
	 * 圆角大小的默认�??
	 */
	private static final int BODER_RADIUS_DEFAULT = 10;
	/**
	 * 圆角的大�?
	 */
	private int mBorderRadius;

	/**
	 * 绘图的Paint
	 */
	private Paint mBitmapPaint;
	/**
	 * 圆角的半�?
	 */
	private int mRadius;
	/**
	 * 3x3 矩阵，主要用于缩小放�?
	 */
	private Matrix mMatrix;
	/**
	 * 渲染图像，使用图像为绘制图形�?�?
	 */
	private BitmapShader mBitmapShader;
	/**
	 * view的宽�?
	 */
	private int mWidth;
	private RectF mRoundRect;

	public RoundImageView(Context context, AttributeSet attrs) {

		super(context, attrs);
		mMatrix = new Matrix();
		mBitmapPaint = new Paint();
		mBitmapPaint.setAntiAlias(true);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.RoundImageView);

		mBorderRadius = a.getDimensionPixelSize(
				R.styleable.RoundImageView_borderRadius, (int) TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
								BODER_RADIUS_DEFAULT, getResources()
										.getDisplayMetrics()));// 默认�?10dp
		type = a.getInt(R.styleable.RoundImageView_type, TYPE_CIRCLE);// 默认为Circle

		a.recycle();
	}

	public RoundImageView(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		/**
		 * 如果类型是圆形，则强制改变view的宽高一致，以小值为�?
		 */
		if (type == TYPE_CIRCLE) {
			mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
			mRadius = mWidth / 2;
			setMeasuredDimension(mWidth, mWidth);
		}

	}

	/**
	 * 初始化BitmapShader
	 */
	private void setUpShader() {
		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}

		Bitmap bmp = drawableToBitamp(drawable);
		// 将bmp作为�?色器，就是在指定区域内绘制bmp
		mBitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);
		float scale = 1.0f;
		if (type == TYPE_CIRCLE) {
			// 拿到bitmap宽或高的小�??
			int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
			scale = mWidth * 1.0f / bSize;

		} else if (type == TYPE_ROUND) {
			Log.e("TAG",
					"b'w = " + bmp.getWidth() + " , " + "b'h = "
							+ bmp.getHeight());
			if (!(bmp.getWidth() == getWidth() && bmp.getHeight() == getHeight())) {
				// 如果图片的宽或�?�高与view的宽高不匹配，计算出�?要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；�?以我们这里取大�?�；
				scale = Math.max(getWidth() * 1.0f / bmp.getWidth(),
						getHeight() * 1.0f / bmp.getHeight());
			}

		}
		// shader的变换矩阵，我们这里主要用于放大或�?�缩�?
		mMatrix.setScale(scale, scale);
		// 设置变换矩阵
		mBitmapShader.setLocalMatrix(mMatrix);
		// 设置shader
		mBitmapPaint.setShader(mBitmapShader);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Log.e("TAG", "onDraw");
		if (getDrawable() == null) {
			return;
		}
		setUpShader();

		if (type == TYPE_ROUND) {
			canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius,
					mBitmapPaint);
		} else {
			canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
			// drawSomeThing(canvas);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		// 圆角图片的范�?
		if (type == TYPE_ROUND)
			mRoundRect = new RectF(0, 0, w, h);
	}

	/**
	 * drawable转bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	private Bitmap drawableToBitamp(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		}
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();
		Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		drawable.draw(canvas);
		return bitmap;
	}

	private static final String STATE_INSTANCE = "state_instance";
	private static final String STATE_TYPE = "state_type";
	private static final String STATE_BORDER_RADIUS = "state_border_radius";

	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(STATE_INSTANCE, super.onSaveInstanceState());
		bundle.putInt(STATE_TYPE, type);
		bundle.putInt(STATE_BORDER_RADIUS, mBorderRadius);
		return bundle;
	}

	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			super.onRestoreInstanceState(((Bundle) state)
					.getParcelable(STATE_INSTANCE));
			this.type = bundle.getInt(STATE_TYPE);
			this.mBorderRadius = bundle.getInt(STATE_BORDER_RADIUS);
		} else {
			super.onRestoreInstanceState(state);
		}

	}

	public void setBorderRadius(int borderRadius) {
		int pxVal = dp2px(borderRadius);
		if (this.mBorderRadius != pxVal) {
			this.mBorderRadius = pxVal;
			invalidate();
		}
	}

	public void setType(int type) {
		if (this.type != type) {
			this.type = type;
			if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE) {
				this.type = TYPE_CIRCLE;
			}
			requestLayout();
		}

	}

	public int dp2px(int dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, getResources().getDisplayMetrics());
	}
	
	public void setScaleType(ScaleType scaleType) {
        if (scaleType == null) {
            throw new NullPointerException();
        }

        if (mScaleType != scaleType) {
            mScaleType = scaleType;

            setWillNotCacheDrawing(mScaleType == ScaleType.CENTER);            

            requestLayout();
            invalidate();
        }
    }
}

// public class RoundImageView extends ImageView {
//
// public RoundImageView(Context context) {
// super(context);
// // TODO Auto-generated constructor stub
// }
//
// public RoundImageView(Context context, AttributeSet attrs) {
// super(context, attrs);
// }
//
// public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
// super(context, attrs, defStyle);
// }
//
// @Override
// protected void onDraw(Canvas canvas) {
//
// Drawable drawable = getDrawable();
//
// if (drawable == null) {
// return;
// }
//
// if (getWidth() == 0 || getHeight() == 0) {
// return;
// }
//
// Bitmap b = ((BitmapDrawable) drawable).getBitmap();
//
// if (null == b) {
// return;
// }
//
// Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
//
// int w = getWidth(), h = getHeight();
//
// Bitmap roundBitmap = getCroppedBitmap(bitmap, w);
// canvas.drawBitmap(roundBitmap, 0, 0, null);
//
// }
//
// public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
// Bitmap sbmp;
// if (bmp.getWidth() != radius || bmp.getHeight() != radius)
// sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
// else
// sbmp = bmp;
// Bitmap output = Bitmap.createBitmap(sbmp.getWidth(), sbmp.getHeight(),
// Config.ARGB_8888);
// Canvas canvas = new Canvas(output);
//
// final int color = 0xffa19774;
// final Paint paint = new Paint();
// final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());
//
// paint.setAntiAlias(true);
// paint.setFilterBitmap(true);
// paint.setDither(true);
// canvas.drawARGB(0, 0, 0, 0);
// paint.setColor(Color.parseColor("#BAB399"));
// canvas.drawCircle(sbmp.getWidth() / 2 + 0.7f,
// sbmp.getHeight() / 2 + 0.7f, sbmp.getWidth() / 2 + 0.1f, paint);
// paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
// canvas.drawBitmap(sbmp, rect, rect, paint);
//
// return output;
// }
// }
// public class RoundImageView extends ImageView {
//
// private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;
//
// private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
// private static final int COLORDRAWABLE_DIMENSION = 1;
//
// private static final int DEFAULT_BORDER_WIDTH = 0;
// private static final int DEFAULT_BORDER_COLOR = Color.WHITE;
//
// private final RectF mDrawableRect = new RectF();
// private final RectF mBorderRect = new RectF();
//
// private final Matrix mShaderMatrix = new Matrix();
// private final Paint mBitmapPaint = new Paint();
// private final Paint mBorderPaint = new Paint();
//
// private int mBorderColor = DEFAULT_BORDER_COLOR;
// private int mBorderWidth = DEFAULT_BORDER_WIDTH;
//
// private Bitmap mBitmap;
// private BitmapShader mBitmapShader;
// private int mBitmapWidth;
// private int mBitmapHeight;
//
// private float mDrawableRadius;
// private float mBorderRadius;
//
// private boolean mReady;
// private boolean mSetupPending;
//
// public RoundImageView(Context context) {
// super(context);
// }
//
// public RoundImageView(Context context, AttributeSet attrs) {
// this(context, attrs, 0);
// }
//
// public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
// super(context, attrs, defStyle);
// super.setScaleType(SCALE_TYPE);
//
// TypedArray a = context.obtainStyledAttributes(attrs,
// R.styleable.RoundImageView, defStyle, 0);
//
// mBorderWidth =
// a.getDimensionPixelSize(R.styleable.RoundImageView_border_width,
// DEFAULT_BORDER_WIDTH);
// mBorderColor = a.getColor(R.styleable.RoundImageView_border_color,
// DEFAULT_BORDER_COLOR);
//
// a.recycle();
//
// mReady = true;
//
// if (mSetupPending) {
// setup();
// mSetupPending = false;
// }
// }
//
// @Override
// public ScaleType getScaleType() {
// return SCALE_TYPE;
// }
//
// @Override
// public void setScaleType(ScaleType scaleType) {
// if (scaleType != SCALE_TYPE) {
// throw new
// IllegalArgumentException(String.format("ScaleType %s not supported.",
// scaleType));
// }
// }
//
// @Override
// protected void onDraw(Canvas canvas) {
// if (getDrawable() == null) {
// return;
// }
//
// canvas.drawCircle(getWidth() / 2, getHeight() / 2, mDrawableRadius,
// mBitmapPaint);
// canvas.drawCircle(getWidth() / 2, getHeight() / 2, mBorderRadius,
// mBorderPaint);
// }
//
// @Override
// protected void onSizeChanged(int w, int h, int oldw, int oldh) {
// super.onSizeChanged(w, h, oldw, oldh);
// setup();
// }
//
// public int getBorderColor() {
// return mBorderColor;
// }
//
// public void setBorderColor(int borderColor) {
// if (borderColor == mBorderColor) {
// return;
// }
//
// mBorderColor = borderColor;
// mBorderPaint.setColor(mBorderColor);
// invalidate();
// }
//
// public int getBorderWidth() {
// return mBorderWidth;
// }
//
// public void setBorderWidth(int borderWidth) {
// if (borderWidth == mBorderWidth) {
// return;
// }
//
// mBorderWidth = borderWidth;
// setup();
// }
//
// @Override
// public void setImageBitmap(Bitmap bm) {
// super.setImageBitmap(bm);
// mBitmap = bm;
// setup();
// }
//
// @Override
// public void setImageDrawable(Drawable drawable) {
// super.setImageDrawable(drawable);
// mBitmap = getBitmapFromDrawable(drawable);
// setup();
// }
//
// @Override
// public void setImageResource(int resId) {
// super.setImageResource(resId);
// mBitmap = getBitmapFromDrawable(getDrawable());
// setup();
// }
//
// private Bitmap getBitmapFromDrawable(Drawable drawable) {
// if (drawable == null) {
// return null;
// }
//
// if (drawable instanceof BitmapDrawable) {
// return ((BitmapDrawable) drawable).getBitmap();
// }
//
// try {
// Bitmap bitmap;
//
// if (drawable instanceof ColorDrawable) {
// bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION,
// COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
// } else {
// bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
// drawable.getIntrinsicHeight(), BITMAP_CONFIG);
// }
//
// Canvas canvas = new Canvas(bitmap);
// drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
// drawable.draw(canvas);
// return bitmap;
// } catch (OutOfMemoryError e) {
// return null;
// }
// }
//
// private void setup() {
// if (!mReady) {
// mSetupPending = true;
// return;
// }
//
// if (mBitmap == null) {
// return;
// }
//
// mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP,
// Shader.TileMode.CLAMP);
//
// mBitmapPaint.setAntiAlias(true);
// mBitmapPaint.setShader(mBitmapShader);
//
// mBorderPaint.setStyle(Paint.Style.STROKE);
// mBorderPaint.setAntiAlias(true);
// mBorderPaint.setColor(mBorderColor);
// mBorderPaint.setStrokeWidth(mBorderWidth);
//
// mBitmapHeight = mBitmap.getHeight();
// mBitmapWidth = mBitmap.getWidth();
//
// mBorderRect.set(0, 0, getWidth(), getHeight());
// mBorderRadius = Math.min((mBorderRect.height() - mBorderWidth) / 2,
// (mBorderRect.width() - mBorderWidth) / 2);
//
// mDrawableRect.set(mBorderWidth, mBorderWidth, mBorderRect.width() -
// mBorderWidth, mBorderRect.height() - mBorderWidth);
// mDrawableRadius = Math.min(mDrawableRect.height() / 2, mDrawableRect.width()
// / 2);
//
// updateShaderMatrix();
// invalidate();
// }
//
// private void updateShaderMatrix() {
// float scale;
// float dx = 0;
// float dy = 0;
//
// mShaderMatrix.set(null);
//
// if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() *
// mBitmapHeight) {
// scale = mDrawableRect.height() / (float) mBitmapHeight;
// dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
// } else {
// scale = mDrawableRect.width() / (float) mBitmapWidth;
// dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
// }
//
// mShaderMatrix.setScale(scale, scale);
// mShaderMatrix.postTranslate((int) (dx + 0.5f) + mBorderWidth, (int) (dy +
// 0.5f) + mBorderWidth);
//
// mBitmapShader.setLocalMatrix(mShaderMatrix);
// }
//
// }
package com.youli.bedinfo.custom;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomFontTextView extends TextView{

	public CustomFontTextView(Context context) {
		super(context);
		init(context);
	}

	public CustomFontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	public CustomFontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}


	private void init(Context context){
		AssetManager mag=context.getAssets();
		
		Typeface font=Typeface.createFromAsset(mag, "aaa.ttf");
		setTypeface(font);
		
	}
	
}

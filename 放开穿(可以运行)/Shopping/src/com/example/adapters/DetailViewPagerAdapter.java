package com.example.adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import com.example.httpurl.HttpUrl;
import com.example.secondlevelactivity.DetailActivity;
import com.loveplusplus.demo.image.ImagePagerActivity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class DetailViewPagerAdapter extends PagerAdapter {

	ImageView[] mImageViews;
	ArrayList<String> list;
	Context context;

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	public DetailViewPagerAdapter(ImageView[] mImageViews, Context context,
			ArrayList<String> list) {
		this.mImageViews = mImageViews;
		this.list = list;
		this.context = context;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// ((ViewPager)container).removeView(mImageViews[position %
		// mImageViews.length]);
	}

	/**
	 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
	 */
	@Override
	public Object instantiateItem(View container, final int position) {
		try {
			View view = mImageViews[position % mImageViews.length];
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String[] array = new String[list.size()];
					list.toArray(array);
					for (int i = 0; i < array.length; i++) {
						array[i] = HttpUrl.HttpURL + array[i];
					}
					imageBrower(position % mImageViews.length, array);
				}
			});
			((ViewPager) container).addView(mImageViews[position
					% mImageViews.length], 0);
		} catch (Exception e) {
		}

		if (mImageViews.length == 0) {
			return 0;
		} else {

			return mImageViews[position % mImageViews.length];
		}
	}

	private void imageBrower(int position, String[] urls) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		// 鍥剧墖url,涓轰簡婕旂ず杩欓噷浣跨敤甯搁噺锛屼竴鑸粠鏁版嵁搴撲腑鎴栫綉缁滀腑鑾峰彇
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		context.startActivity(intent);
	}

}

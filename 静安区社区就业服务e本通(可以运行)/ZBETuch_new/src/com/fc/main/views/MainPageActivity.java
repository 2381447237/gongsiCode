package com.fc.main.views;

import java.util.ArrayList;

import com.fc.first.beans.LocationInformation;
import com.fc.main.beans.MainPageAdapter;
import com.test.zbetuch_news.R;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;

/**
 * 可滑动的主界面
 * 
 * @author hxl
 * 
 */
public class MainPageActivity extends ActivityGroup implements
		OnPageChangeListener {

	private ViewPager viewPager;// 滑动界面
	private ArrayList<View> view_list;// 滑动view集合
	private ViewGroup main, group;// 主View
	private ImageView[] img;// 提示图片数组
	private ImageView iv;
	private ArrayList<LocationInformation> locationInfolist;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 设置屏幕无标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Intent it = MainPageActivity.this.getIntent();
		locationInfolist = (ArrayList<LocationInformation>) it
				.getSerializableExtra("locationlist");
		initPagVeiew();
	}

	/**
	 * 初始化整体滑动界面 2个滑动界面
	 */
	private void initPagVeiew() {
		main = (ViewGroup) getLayoutInflater().inflate(
				R.layout.activity_mianpage, null);
		group = (ViewGroup) main.findViewById(R.id.viewGroup);
		viewPager = (ViewPager) main.findViewById(R.id.viewPager);

		view_list = new ArrayList<View>();
		Intent mainit = new Intent(this, MainActivity.class);
		mainit.putExtra("locationlist", locationInfolist);
		View view1 = getLocalActivityManager().startActivity("0", mainit)
				.getDecorView();
		View view2 = getLocalActivityManager().startActivity("1",
				new Intent(this, MainPageOne.class)).getDecorView();
		View view3 = getLocalActivityManager().startActivity("2",
				new Intent(this, MainPageTwoActivity.class)).getDecorView();
		View view4 = getLocalActivityManager().startActivity("3",
				new Intent(this, MainPageThreeActivity.class)).getDecorView();
		view_list.add(0, view1);
		view_list.add(1, view2);
		view_list.add(2, view3);
		view_list.add(3, view4);
		img = new ImageView[view_list.size()];
		for (int i = 0; i < view_list.size(); i++) {
			iv = new ImageView(this);
			iv.setLayoutParams(new LayoutParams(20, 20));
			iv.setPadding(20, 0, 20, 0);
			img[i] = iv;
			if (i == 0) {
				img[i].setBackgroundResource(R.drawable.radio_sel);
			} else {
				img[i].setBackgroundResource(R.drawable.radio);
			}
			group.addView(img[i]);
		}
		setContentView(main);
		viewPager.setAdapter(new MainPageAdapter(view_list));
		viewPager.setOnPageChangeListener(this);
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	/**
	 * 当某个view被选择后，下面提示图片标记为选中。
	 */
	@Override
	public void onPageSelected(int arg0) {
		for (int i = 0; i < img.length; i++) {
			img[arg0].setBackgroundResource(R.drawable.radio_sel);
			if (arg0 != i) {
				img[i].setBackgroundResource(R.drawable.radio);
			}
		}

	}
}

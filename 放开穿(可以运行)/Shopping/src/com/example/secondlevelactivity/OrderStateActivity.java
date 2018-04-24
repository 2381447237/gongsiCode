package com.example.secondlevelactivity;

import java.util.ArrayList;

import com.example.adapters.Myadapter;
import com.example.adapters.OrderStateAdapter;
import com.example.shopping.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class OrderStateActivity extends FragmentActivity implements OnClickListener{

	private ImageView iv_back;
	private ViewPager mViewPager;
	private RadioGroup mRadioGroup;
	private ArrayList<Fragment> mFragments;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_orderstate);
		
		initData();
		initView();
		
	}

	private void initData(){
		
		mFragments=new ArrayList<Fragment>();
		mFragments.add(new OsAllFragment());
		mFragments.add(new FukuanFragment());
		mFragments.add(new ShouhuoFragment());
		mFragments.add(new PinglunFragment());
		
	}
	
	private void initView(){
		iv_back=(ImageView) findViewById(R.id.img_os_back);
		iv_back.setOnClickListener(this);
		mViewPager=(ViewPager) findViewById(R.id.vp_os);
		mRadioGroup=(RadioGroup) findViewById(R.id.rg_os);
		OrderStateAdapter adapter=new OrderStateAdapter(getSupportFragmentManager(),mFragments);
		mViewPager.setAdapter(adapter);
		
		Intent intent=getIntent();
		
		int state=intent.getIntExtra("state",0);
		
		switch (state) {
		case 10000:
			
			mRadioGroup.check(R.id.rb_os_all);
			mViewPager.setCurrentItem(0, true);
			break;

		case 20000:
			
			mRadioGroup.check(R.id.rb_os_fukuan);
			mViewPager.setCurrentItem(1,true);
			
			break;
			
		case 30000:
			mRadioGroup.check(R.id.rb_os_shouhuo);
			mViewPager.setCurrentItem(2,true);
			
			break;
			
		case 40000:
			mRadioGroup.check(R.id.rb_os_pingjia);
			mViewPager.setCurrentItem(3,true);
			break;
		default:
			break;
		}
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				
				switch (arg0) {
				case 0:
					mRadioGroup.check(R.id.rb_os_all);
					break;
				case 1:
					mRadioGroup.check(R.id.rb_os_fukuan);
					break;
				case 2:
					mRadioGroup.check(R.id.rb_os_shouhuo);
					break;
				case 3:
					mRadioGroup.check(R.id.rb_os_pingjia);
					break;

				default:
					break;
				}
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				switch (checkedId) {
				case R.id.rb_os_all:
					mViewPager.setCurrentItem(0, true);
					break;
				case R.id.rb_os_fukuan:
					mViewPager.setCurrentItem(1, true);
					break;
				case R.id.rb_os_shouhuo:
					mViewPager.setCurrentItem(2, true);
					break;
				case R.id.rb_os_pingjia:
					mViewPager.setCurrentItem(3, true);
					break;

				default:
					break;
				}
				
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.img_os_back:
			
			finish();
			
			break;

		default:
			break;
		}
		
	}
	
}

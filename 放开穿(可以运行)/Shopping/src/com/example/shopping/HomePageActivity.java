package com.example.shopping;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;

/**
 * 项目的主Activity，所有的Fragment都嵌入在这里。
 * 
 * @author guolin
 */
public class HomePageActivity extends Activity implements OnClickListener {

	/**
	 * 用于展示消息的Fragment
	 */
	private ClassifyFragment classifyFragment;

	/**
	 * 用于展示联系人的Fragment
	 */
	private ShopFragment shopFragment;

	/**
	 * 用于展示动态的Fragment
	 */
	private FavoriteFragment favoriteFragment;

	/**
	 * 用于展示设置的Fragment
	 */
	private ShoppingCartFragment shoppingCartFragment;
	private MineInfomationFragment mineInfomationFragment;

	/**
	 * 消息界面布局
	 */
	private View classifyLayout;

	/**
	 * 联系人界面布局
	 */
	public static View shopLayout;

	/**
	 * 动态界面布局
	 */
	public View shoppingCartLayout;

	/**
	 * 设置界面布局
	 */
	private View favoriteLayout;
	private View mineInfomationLayout;

	/**
	 * 在Tab布局上显示消息图标的控件
	 */
	private ImageView classifyImage;

	/**
	 * 在Tab布局上显示联系人图标的控件
	 */
	private ImageView shopImage;

	/**
	 * 在Tab布局上显示动态图标的控件
	 */
	private ImageView shoppingCartImage;

	/**
	 * 在Tab布局上显示设置图标的控件
	 */
	public static ImageView favoriteImage;
	private ImageView mineInfomationImage;

	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// 初始化布局元素
		initViews();
		fragmentManager = getFragmentManager();
		
		// 第一次启动时选中第0个tab
		setTabSelection(0);
		
//		Intent intent=getIntent();
//		
//		if(TextUtils.equals("detailActivity", intent.getStringExtra("jump"))){
//			setTabSelection(2);
//		}else{
//			
//			// 第一次启动时选中第0个tab
//			setTabSelection(0);
//			
//		}
		
	}

	/**
	 * 在这里获取到每个需要用到的控件的实例，并给它们设置好必要的点击事件。
	 */
	private void initViews() {
		classifyLayout = findViewById(R.id.classifyLayout);
		shopLayout = findViewById(R.id.shopLayout);
		shoppingCartLayout = findViewById(R.id.shoppingCartLayout);
		favoriteLayout = findViewById(R.id.favoriteLayout);
		mineInfomationLayout = findViewById(R.id.mineInfomationLayout);
		favoriteImage = (ImageView) findViewById(R.id.favorite_image);
		shoppingCartImage = (ImageView) findViewById(R.id.shoppingcart_image);
		shopImage = (ImageView) findViewById(R.id.shop_image);
		mineInfomationImage = (ImageView) findViewById(R.id.mine_image);
		classifyImage = (ImageView) findViewById(R.id.classify_image);
		classifyLayout.setOnClickListener(this);
		shopLayout.setOnClickListener(this);
		shoppingCartLayout.setOnClickListener(this);
		favoriteLayout.setOnClickListener(this);
		mineInfomationLayout.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.classifyLayout:
			// 当点击了消息tab时，选中第1个tab
			setTabSelection(0);
			break;
		case R.id.shopLayout:
			// 当点击了联系人tab时，选中第2个tab
			setTabSelection(1);
			break;
		case R.id.shoppingCartLayout:
			// 当点击了动态tab时，选中第3个tab
			setTabSelection(2);
			break;
		case R.id.favoriteLayout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(3);
			break;
		case R.id.mineInfomationLayout:
			// 当点击了设置tab时，选中第4个tab
			setTabSelection(4);
			break;
		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 * 
	 */
	private void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			classifyImage.setImageResource(R.drawable.fenglei2);
			if (classifyFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				classifyFragment = new ClassifyFragment();
				transaction.add(R.id.content, classifyFragment);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(classifyFragment);
			}
			break;
		case 1:
			// 当点击了联系人tab时，改变控件的图片和文字颜色
			shopImage.setImageResource(R.drawable.dianpu2);
			if (shopFragment == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				shopFragment = new ShopFragment();
				transaction.add(R.id.content, shopFragment);

			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(shopFragment);
			}
			break;
		case 2:
			// 当点击了动态tab时，改变控件的图片和文字颜色
			shoppingCartImage.setImageResource(R.drawable.gouwuche2);
			if (shoppingCartFragment == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				shoppingCartFragment = new ShoppingCartFragment();

				transaction.add(R.id.content, shoppingCartFragment);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(shoppingCartFragment);
			}
			break;
		case 3:
			// 当点击了设置tab时，改变控件的图片和文字颜色
			favoriteImage.setImageResource(R.drawable.shoucangjia2);
			if (favoriteFragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				favoriteFragment = new FavoriteFragment();
				transaction.add(R.id.content, favoriteFragment);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(favoriteFragment);
			}
			break;
		case 4:
			// 当点击了设置tab时，改变控件的图片和文字颜色
			mineInfomationImage.setImageResource(R.drawable.wode2);
			if (mineInfomationFragment == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				mineInfomationFragment = new MineInfomationFragment();
				transaction.add(R.id.content, mineInfomationFragment);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(mineInfomationFragment);
			}
			break;
		}
		transaction.commitAllowingStateLoss();
		// transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		classifyImage.setImageResource(R.drawable.fenglei1);
		shopImage.setImageResource(R.drawable.dianpu1);
		shoppingCartImage.setImageResource(R.drawable.gouwuche1);
		favoriteImage.setImageResource(R.drawable.shoucangjia1);
		mineInfomationImage.setImageResource(R.drawable.wode1);
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (classifyFragment != null) {
			transaction.hide(classifyFragment);
		}
		if (shopFragment != null) {
			transaction.hide(shopFragment);
		}
		if (shoppingCartFragment != null) {
			transaction.hide(shoppingCartFragment);
		}
		if (favoriteFragment != null) {
			transaction.hide(favoriteFragment);
		}
		if (mineInfomationFragment != null) {
			transaction.hide(mineInfomationFragment);
		}
	}

	private long time = 0;

//	@Override
//	public void onBackPressed() {
//		if (System.currentTimeMillis() - time > 2000) {
//			time = System.currentTimeMillis();
//			Toast.makeText(this, "再按一次退出放开穿", 0).show();
//		} else {
//			android.os.Process.killProcess(android.os.Process.myPid());
//		}
//	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK){
			
			if (System.currentTimeMillis() - time > 2000) {
			time = System.currentTimeMillis();
			Toast.makeText(this, "再按一次退出放开穿", 0).show();
		} else {
			android.os.Process.killProcess(android.os.Process.myPid());
			return super.onKeyDown(keyCode, event);
		}
			
		}
		
		return false;
	}
	
}

package com.base.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.infoclass.Classify;
import com.example.shopping.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

public class ShopApplication extends Application {
	private ArrayList<Classify> classifyinfos;
	public static Context context;
	private int Category_Id;
	private String CateGoryName;
	private Bitmap  bitmap;
    public static Context getAppContext() {
        return ShopApplication.context;
    }

//    public static SharedPreferences preferences;
//    public static String userID;
//    public static String phone;
    public static VollyUtils utils;
    public static int REGIST_FLAG;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		ShopApplication.context = getApplicationContext();
		setClassifyinfos(null);
		setCategory_Id(-1);
		setCateGoryName("");
		setBitmap(null);
		DisplayImageOptions defaultOptions = new DisplayImageOptions
				.Builder()
				.showImageForEmptyUri(R.drawable.empty_photo) 
				.showImageOnFail(R.drawable.empty_photo) 
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration
				.Builder(getApplicationContext())
				.defaultDisplayImageOptions(defaultOptions)
				.discCacheSize(50 * 1024 * 1024)//
				.discCacheFileCount(100)//缂撳瓨涓�鐧惧紶鍥剧墖
				.writeDebugLogs()
				.build();
		ImageLoader.getInstance().init(config);
//		preferences=getSharedPreferences("user", Context.MODE_PRIVATE);
//		userID=preferences.getString("userID", "");
//		phone=preferences.getString("phone", "");
		utils=new VollyUtils();
	}

	public ArrayList<Classify> getClassifyinfos() {
		return classifyinfos;
	}

	public void setClassifyinfos(ArrayList<Classify> classifyinfos) {
		this.classifyinfos = classifyinfos;
	}


	public int getCategory_Id() {
		return Category_Id;
	}


	public void setCategory_Id(int category_Id) {
		Category_Id = category_Id;
	}


	public String getCateGoryName() {
		return CateGoryName;
	}


	public void setCateGoryName(String cateGoryName) {
		CateGoryName = cateGoryName;
	}


	public Bitmap getBitmap() {
		return bitmap;
	}


	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	
	
}

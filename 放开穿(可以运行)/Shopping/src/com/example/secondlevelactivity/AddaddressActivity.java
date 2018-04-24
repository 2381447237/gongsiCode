package com.example.secondlevelactivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import org.json.JSONException;
import org.xmlpull.v1.XmlSerializer;

import com.base.activity.BaseActivity;
import com.base.activity.ShopApplication;
import com.base.activity.VollyUtils.VolleyListener;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.CityModel;
import com.example.infoclass.DistrictModel;
import com.example.infoclass.ProvinceModel;
import com.example.infoclass.Shopcart;
import com.example.shopping.R;
import com.example.shopping.R.layout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.PopupWindow.OnDismissListener;

public class AddaddressActivity extends BaseActivity implements
		OnClickListener, VolleyListener,OnWheelChangedListener {
	private EditText edit_name, edit_phone, edit_address;
	private TextView tv_city;
	private Button btn_save;
	private View layout_address;
	private View province,city,distrct;
	private PopupWindow mPopupWindow;
	private List<ProvinceModel> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addaddress);
		initView();
//		saveJson();
	}

	private void initView() {
		edit_name = (EditText) findViewById(R.id.edit_name);
		edit_phone = (EditText) findViewById(R.id.edit_phone);
		tv_city = (TextView) findViewById(R.id.tv_city);
		edit_address = (EditText) findViewById(R.id.edit_address);
		btn_save = (Button) findViewById(R.id.btn_save);
		layout_address = findViewById(R.id.layout_address);
		btn_save.setOnClickListener(this);
		layout_address.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_save:
			if (edit_name.getText().toString().trim().equals("")
					|| edit_phone.getText().toString().trim().equals("")
					|| edit_address.getText().toString().trim().equals("")
					|| tv_city.getText().equals("")) {
				Toast.makeText(AddaddressActivity.this, "输入不能为空", 1000).show();
			} else if (edit_phone.getText().toString().trim().length() < 11) {
				Toast.makeText(AddaddressActivity.this, "手机号有误", 1000).show();
			} else {
			}
			break;

		case R.id.layout_address:
//			saveJson();
//			showMakeGradeMarkedWindow(v);
			Intent intent=new Intent(AddaddressActivity.this, AActivity.class);
			startActivity(intent);
			break;
		}
	}

	private void showMakeGradeMarkedWindow(View v) {
		WindowManager wm = (WindowManager) AddaddressActivity.this
				.getSystemService(Context.WINDOW_SERVICE);
		View view = LayoutInflater.from(AddaddressActivity.this).inflate(
				R.layout.activity_province, null);
		 WheelView mViewProvince;
		 WheelView mViewCity;
		 WheelView mViewDistrict;
		mViewProvince = (WheelView)view. findViewById(R.id.id_province);
		mViewCity = (WheelView)view. findViewById(R.id.id_city);
		mViewDistrict = (WheelView)view. findViewById(R.id.id_district);
		// 添加change事件
    	mViewProvince.addChangingListener(this);
    	// 添加change事件
    	mViewCity.addChangingListener(this);
    	// 添加change事件
    	mViewDistrict.addChangingListener(this);
    	province=mViewProvince;
    	city=mViewCity;
    	distrct=mViewDistrict;
    	setUpData();
    	// 添加onclick事件
		mPopupWindow = new PopupWindow(view, wm.getDefaultDisplay().getWidth(),
				450);
		mPopupWindow.setFocusable(true);
		ColorDrawable dw = new ColorDrawable(0xDCDCDC);
		mPopupWindow.setBackgroundDrawable(dw);
		mPopupWindow.setAnimationStyle(R.style.take_photo_anim);
		mPopupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		
		// 设置背景颜色变暗
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.7f;
		getWindow().setAttributes(lp);
		mPopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1f;
				getWindow().setAttributes(lp);
			}
		});
	}
	
	private void saveJson() {
		try {
			ShopApplication.utils
					.init(this)
					.setListener(this)
					.getJson(
							HttpUrl.HttpURL+"/Json/Get_Province_City_District_Info.aspx"
									);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getJson(String response) {
		Gson gson=new Gson();
		list=new ArrayList<ProvinceModel>();
		list=gson.fromJson(response, new TypeToken<ArrayList<ProvinceModel>>() {
		}.getType());
//		initProvinceDatas(list);
		AssetManager asset = getAssets();
		File f = new File(asset+"province_data.xml");
		OutputStream out;
		try {
			out = new FileOutputStream(f);
			XmlSerializer serializer = Xml.newSerializer();
			serializer.setOutput(out,"UTF-8");
			serializer.startDocument("UTF-8", true);
			serializer.startTag(null, "root");
//        for(ProvinceModel p:list){
			for(int i=0;i<list.size();i++){
				serializer.startTag(null, "province");
				serializer.attribute(null, "name", list.get(i).ProvinceName);
				for(int j=0;j<list.get(i).tbl_City_Dict.size();j++){
					serializer.startTag(null, "city");
//					serializer.text(list.get(i).tbl_City_Dict.get(j).CityName);
					serializer.attribute(null, "name", list.get(i).tbl_City_Dict.get(j).CityName);
					for(int k=0;k<list.get(i).tbl_City_Dict.get(j).tbl_District_Dict.size();k++){
						serializer.startTag(null, "district");
						serializer.attribute(null, "name", list.get(i).tbl_City_Dict.get(j).tbl_District_Dict.get(k).DistrictName);
	//					serializer.attribute(null, "zipcode", list.get(i).tbl_City_Dict.get(j).tbl_District_Dict.get(k).DistrictName);
						serializer.endTag(null, "district");
					}
					serializer.endTag(null, "city");
					
				}
				
				
				
				serializer.endTag(null, "province");
				
			}
//        }
			serializer.endTag(null, "root");
			serializer.endDocument();
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void getFiel() {
		// TODO Auto-generated method stub

	}
	
	
    	
	
	private void setUpData() {
	//	initProvinceDatas();
		((WheelView) province).setViewAdapter(new ArrayWheelAdapter<String>(AddaddressActivity.this, mProvinceDatas));
		// 设置可见条目数量
		((WheelView) province).setVisibleItems(7);
		((WheelView) city).setVisibleItems(7);
		((WheelView) distrct).setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == ((WheelView) province)) {
			updateCities();
		} else if (wheel == ((WheelView) city)) {
			updateAreas();
		} else if (wheel == ((WheelView) distrct)) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = ((WheelView) city).getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		((WheelView) distrct).setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		((WheelView) distrct).setCurrentItem(0);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = ((WheelView) province).getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		((WheelView) city).setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		((WheelView) city).setCurrentItem(0);
		updateAreas();
	}
	
	/**
	 * 所有省
	 */
	protected String[] mProvinceDatas;
	/**
	 * key - 省 value - 市
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
	
	/**
	 * key - 区 values - 邮编
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>(); 

	/**
	 * 当前省的名称
	 */
	protected String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	protected String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	protected String mCurrentDistrictName ="";
	
	/**
	 * 当前区的邮政编码
	 */
	protected String mCurrentZipCode ="";
	
	/**
	 * 解析省市区的XML数据
	 */
	
    protected void initProvinceDatas(List<ProvinceModel> list)
	{
        try {
			// 获取解析出来的数据
			//*/ 初始化默认选中的省、市、区
			if (list!= null && !list.isEmpty()) {
				mCurrentProviceName = list.get(0).ProvinceName;
				List<CityModel> cityList = list.get(0).tbl_City_Dict;
				if (cityList!= null && !cityList.isEmpty()) {
					mCurrentCityName = cityList.get(0).CityName;
					List<DistrictModel> districtList = cityList.get(0).tbl_District_Dict;
					mCurrentDistrictName = districtList.get(0).DistrictName;
		//			mCurrentZipCode = districtList.get(0).getZipcode();
				}
			}
			//*/
			mProvinceDatas = new String[list.size()];
        	for (int i=0; i< list.size(); i++) {
        		// 遍历所有省的数据
        		mProvinceDatas[i] = list.get(i).ProvinceName;
        		List<CityModel> cityList = list.get(i).tbl_City_Dict;
        		String[] cityNames = new String[cityList.size()];
        		for (int j=0; j< cityList.size(); j++) {
        			// 遍历省下面的所有市的数据
        			cityNames[j] = cityList.get(j).CityName;
        			List<DistrictModel> districtList = cityList.get(j).tbl_District_Dict;
        			String[] distrinctNameArray = new String[districtList.size()];
        			DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
        			for (int k=0; k<districtList.size(); k++) {
        				// 遍历市下面所有区/县的数据
    //    				DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
//           				DistrictModel districtModel = new DistrictModel(districtList.get(k).DistrictName);
        				// 区/县对于的邮编，保存到mZipcodeDatasMap
    //    				mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
//        				distrinctArray[k] = districtModel;
//        				distrinctNameArray[k] = districtModel.DistrictName;
        			}
        			// 市-区/县的数据，保存到mDistrictDatasMap
        			mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
        		}
        		// 省-市的数据，保存到mCitisDatasMap
        		mCitisDatasMap.put(list.get(i).ProvinceName, cityNames);
        	}
        } catch (Throwable e) {  
            e.printStackTrace();  
        } finally {
        	
        } 
	}

}

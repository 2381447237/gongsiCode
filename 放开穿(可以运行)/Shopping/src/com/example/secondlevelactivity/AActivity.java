package com.example.secondlevelactivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.json.JSONException;
import com.base.activity.BaseActivity;
import com.base.activity.ShopApplication;
import com.base.activity.VollyUtils.VolleyListener;
import com.example.httpurl.HttpUrl;
import com.example.infoclass.CityModel;
import com.example.infoclass.DistrictModel;
import com.example.infoclass.ProvinceModel;
import com.example.shopping.R;
import com.example.shopping.R.id;
import com.example.shopping.R.layout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;
import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AActivity extends BaseActivity implements OnClickListener, OnWheelChangedListener,VolleyListener {
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private Button mBtnConfirm;
	private List<ProvinceModel> list;
	private TextView tv_select;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_province);
		setUpViews();
		setUpListener();
		saveJson();
//		setUpData();
	}
	
	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district);
		tv_select=(TextView)findViewById(R.id.tv_select);
		mBtnConfirm=(Button)findViewById(R.id.tv_sure);
	}
	
	private void setUpListener() {
    	// 添加change事件
    	mViewProvince.addChangingListener(this);
    	// 添加change事件
    	mViewCity.addChangingListener(this);
    	// 添加change事件
    	mViewDistrict.addChangingListener(this);
    	mBtnConfirm.setOnClickListener(this);
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
		
//		tv_select.setText(response);
//		initProvinceDatas(list);
		setUpData();
	}

	@Override
	public void getFiel() {

	}
	
	
	
	private void setUpData() {
		initProvinceDatas(list);
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(AActivity.this, mProvinceDatas));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mViewDistrict.setCurrentItem(0);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_sure:
			showSelectedResult();
			break;
		}
	}

	private void showSelectedResult() {
		Toast.makeText(AActivity.this, "当前选中:"+mCurrentProviceName+","+mCurrentCityName+","
				+mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();
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
   					mCurrentZipCode = districtList.get(0).DistrictID;
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
              				DistrictModel districtModel = new DistrictModel(districtList.get(k).DistrictName,districtList.get(k).DistrictID);
           				// 区/县对于的邮编，保存到mZipcodeDatasMap
           				mZipcodeDatasMap.put(districtList.get(k).DistrictName, districtList.get(k).DistrictID);
           				distrinctArray[k] = districtModel;
           				distrinctNameArray[k] = districtModel.DistrictName;
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

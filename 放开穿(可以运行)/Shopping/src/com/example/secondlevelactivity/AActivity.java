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
    	// ���change�¼�
    	mViewProvince.addChangingListener(this);
    	// ���change�¼�
    	mViewCity.addChangingListener(this);
    	// ���change�¼�
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
		// ���ÿɼ���Ŀ����
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
	 * ���ݵ�ǰ���У�������WheelView����Ϣ
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
	 * ���ݵ�ǰ��ʡ��������WheelView����Ϣ
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
		Toast.makeText(AActivity.this, "��ǰѡ��:"+mCurrentProviceName+","+mCurrentCityName+","
				+mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();
	}
	/**
	 * ����ʡ
	 */
	protected String[] mProvinceDatas;
	/**
	 * key - ʡ value - ��
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - �� values - ��
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
	
	/**
	 * key - �� values - �ʱ�
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>(); 

	/**
	 * ��ǰʡ������
	 */
	protected String mCurrentProviceName;
	/**
	 * ��ǰ�е�����
	 */
	protected String mCurrentCityName;
	/**
	 * ��ǰ��������
	 */
	protected String mCurrentDistrictName ="";
	
	/**
	 * ��ǰ������������
	 */
	protected String mCurrentZipCode ="";
	
	/**
	 * ����ʡ������XML����
	 */
    
    protected void initProvinceDatas(List<ProvinceModel> list)
   	{
           try {
   			// ��ȡ��������������
   			//*/ ��ʼ��Ĭ��ѡ�е�ʡ���С���
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
           		// ��������ʡ������
           		mProvinceDatas[i] = list.get(i).ProvinceName;
           		List<CityModel> cityList = list.get(i).tbl_City_Dict;
           		String[] cityNames = new String[cityList.size()];
           		for (int j=0; j< cityList.size(); j++) {
           			// ����ʡ����������е�����
           			cityNames[j] = cityList.get(j).CityName;
           			List<DistrictModel> districtList = cityList.get(j).tbl_District_Dict;
           			String[] distrinctNameArray = new String[districtList.size()];
           			DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
           			for (int k=0; k<districtList.size(); k++) {
           				// ����������������/�ص�����
       //    				DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
              				DistrictModel districtModel = new DistrictModel(districtList.get(k).DistrictName,districtList.get(k).DistrictID);
           				// ��/�ض��ڵ��ʱ࣬���浽mZipcodeDatasMap
           				mZipcodeDatasMap.put(districtList.get(k).DistrictName, districtList.get(k).DistrictID);
           				distrinctArray[k] = districtModel;
           				distrinctNameArray[k] = districtModel.DistrictName;
           			}
           			// ��-��/�ص����ݣ����浽mDistrictDatasMap
           			mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
           		}
           		// ʡ-�е����ݣ����浽mCitisDatasMap
           		mCitisDatasMap.put(list.get(i).ProvinceName, cityNames);
           	}
           } catch (Throwable e) {  
               e.printStackTrace();  
           } finally {
           	
           } 
   	}

}

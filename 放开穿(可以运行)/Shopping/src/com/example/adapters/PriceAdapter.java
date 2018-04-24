package com.example.adapters;

import com.base.activity.RangeSeekBar;
import com.example.shopping.R;
import com.example.shopping.ShopFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

public class PriceAdapter extends BaseAdapter{

	private ShopFragment context;
	private String[] price;
	//private float small=0.f,big=0.f;
	public static int UrlStartMoney;
	public static int UrlEndMoney;
	private int minMoney;
	private int maxMoney;
	
	public PriceAdapter(ShopFragment context, String[] price) {
		super();
		this.context = context;
		this.price = price;
	}

	@Override
	public int getCount() {
		return price.length;
	}

	@Override
	public Object getItem(int position) {
		return price[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
				convertView = LayoutInflater.from(context.getActivity()).inflate(
						R.layout.seekbar, null);
				holder.seekBar = (RangeSeekBar) convertView
						.findViewById(R.id.rangeSeekBar);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		minMoney=UrlStartMoney;
		maxMoney=UrlEndMoney;
		Log.i("最小值是:",minMoney+"");
		Log.i("最大值是:",maxMoney+"");
       // holder.seekBar.setProgress(minMoney, maxMoney);
		holder.seekBar.setOnRangeChangedListener(new com.base.activity.RangeSeekBar.OnRangeChangedListener() {  
            
            @Override  
            public void onRangeChanged(float lowerRange, float upperRange) {  
            	
            	context.showDialog(context.getActivity());
            	
            	context.gridView.setSelection(0);
            	
            	UrlStartMoney=(int) lowerRange;
            	UrlEndMoney=(int)upperRange;
            	context.setStartMoney(lowerRange,upperRange);
            	
            	
//            	small=lowerRange;
//            	big=upperRange;

            }  
        });  
		//holder.seekBar.setProgress(small,big);
		return convertView;
	}

	public class ViewHolder {
		RangeSeekBar seekBar;
	}

}

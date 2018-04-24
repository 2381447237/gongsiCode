package com.example.adapters;

import java.util.ArrayList;
import java.util.List;

import com.example.adapters.StraightAdapter.ViewHolder;
import com.example.infoclass.MyModel;
import com.example.shopping.R;
import com.example.shopping.ShopFragment;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class ModelAdapter extends BaseAdapter {

	private ShopFragment context;
	private String[] size;
	GradientDrawable drawable;
	public static List<MyModel> myModelList = new ArrayList<MyModel>();
	public static String AllProOptNameModel = "";

	public ModelAdapter(ShopFragment context, String[] size) {
		super();
		this.context = context;
		this.size = size;
	}

	@Override
	public int getCount() {
		return size.length;
	}

	@Override
	public Object getItem(int position) {
		return size[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context.getActivity()).inflate(
					R.layout.size,null);
			holder.btn_size = (Button) convertView.findViewById(R.id.btn_size);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE); // »­¿ò
		drawable.setStroke(2, Color.GRAY);
		holder.btn_size.setText(size[position]);
		holder.btn_size.setBackgroundDrawable(drawable);
		holder.btn_size.setTextColor(0xffdfdfdf);
		
		if(myModelList.get(position).isChecked()){
			holder.btn_size.setTextColor(0xff000000);
			holder.btn_size.setBackgroundColor(0xffdfdfdf);
			
		}else{
			holder.btn_size.setTextColor(0xffdfdfdf);
			holder.btn_size.setBackgroundDrawable(drawable);		
		}
		
		holder.btn_size.setText(myModelList.get(position).getModelSize());
		
		holder.btn_size.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				context.showDialog(context.getActivity());
				
				context.gridView.setSelection(0);
				
				myModelList.get(position).setChecked(!myModelList.get(position).isChecked());
				
				if(ShopFragment.isChangeCategory_Id==true){
					AllProOptNameModel="";
					ShopFragment.isChangeCategory_Id=false;
				}
				
				if (holder.btn_size.getCurrentTextColor() == 0xffdfdfdf) {
					holder.btn_size.setTextColor(0xff000000);
					holder.btn_size.setBackgroundColor(0xffdfdfdf);
				} else {
					holder.btn_size.setTextColor(0xffdfdfdf);
					holder.btn_size.setBackgroundDrawable(drawable);
				}
				
				AllProOptNameModel="";
				
				for(MyModel mm:myModelList){
					
					if(mm.isChecked())
						
						AllProOptNameModel+=mm.getModelSize()+",";
					
				}
				
				context.addSizeInfo(holder.btn_size.getText() + "");
			}
		});
		
		return convertView;
	}

	public class ViewHolder {

		Button btn_size;

	}
}

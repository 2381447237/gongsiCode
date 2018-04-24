package com.example.adapters;

import java.util.List;

import com.example.infoclass.PrePay;
import com.example.shopping.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PrePayAdapter extends BaseAdapter{

	private List<PrePay> data;
	private Context context;
	private LayoutInflater inflater;
	
	public PrePayAdapter(List<PrePay> data, Context context) {
		super();
		this.data = data;
		this.context = context;
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final ViewHolder holder;
		
		if(convertView==null){
			
			holder=new ViewHolder();
			
			convertView=inflater.inflate(R.layout.item_os_prefukuan,null);
			
			holder.iv1=(ImageView) convertView.findViewById(R.id.iv_os_fukuan_content1);
			holder.iv2=(ImageView) convertView.findViewById(R.id.iv_os_fukuan_content2);
			holder.iv3=(ImageView) convertView.findViewById(R.id.iv_os_fukuan_content3);
			
			holder.tvPrice=(TextView) convertView.findViewById(R.id.tv_os_fukuan_price);
			holder.btnCancel=(Button) convertView.findViewById(R.id.btn_os_fukuan_cancal);
			holder.btnPay=(Button) convertView.findViewById(R.id.btn_os_fukuan_pay);
			
			convertView.setTag(holder);
		}else{
			
			holder=(ViewHolder) convertView.getTag();
			
		}
		
		PrePay pp=data.get(position);
		
		holder.iv1.setImageResource(R.drawable.empty_photo);
		holder.iv2.setImageResource(R.drawable.empty_photo);
		holder.iv3.setImageResource(R.drawable.empty_photo);
		
		holder.tvPrice.setText(pp.tvPrice);
		holder.btnCancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Toast.makeText(context,"第"+position+"个"+holder.btnCancel.getText().toString(),0).show();
				
			}
		});
		
		holder.btnPay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context,"第"+position+"个"+holder.btnPay.getText().toString(),0).show();
			}
		});
		
		return convertView;
	}

	class ViewHolder{
		
		ImageView iv1,iv2,iv3;
		TextView tvPrice;
		Button btnCancel,btnPay;
	}
	
}

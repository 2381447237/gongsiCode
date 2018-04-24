package com.example.adapters;

import java.util.List;

import com.example.infoclass.RvSize;
import com.example.shopping.R;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class RvSzieAdapter extends RecyclerView.Adapter<RvSzieAdapter.MyViewHolder> {

	private Context context;
	private List<RvSize> data;
	private LayoutInflater inflater;
	
	//设置Item点击的回调接口
	
	public interface OnMyItemClickListener{
		
		void onItemClick(View view,int position);
		
	}
	
	private OnMyItemClickListener mOnMyItemClickListener;
	
	public void setmOnMyItemClickListener(
			OnMyItemClickListener mOnMyItemClickListener) {
		this.mOnMyItemClickListener = mOnMyItemClickListener;
	}

	public RvSzieAdapter(Context context, List<RvSize> data) {
		super();
		this.context = context;
		this.data = data;
		inflater=LayoutInflater.from(context);
	}

	@Override
	public int getItemCount() {
		return data.size();
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position) {

		RvSize r=data.get(position);
		
		holder.tv.setText(r.size);
		
		if(r.isCheck){
			holder.tv.setTextColor(Color.rgb(0xff,0x00,0x00));
		}else{
			holder.tv.setTextColor(Color.rgb(0x00,0x00,0x00));
		}
		
		//如果设置了回调，则设置点击事件
		if(mOnMyItemClickListener!=null){
			
			holder.itemView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					mOnMyItemClickListener.onItemClick(holder.itemView, position);
					
				}
			});
			
		}
		
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		
		MyViewHolder holder=new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_rv_size,parent,false));
		
		return holder;
	}

	class MyViewHolder extends ViewHolder {

		TextView tv;

		public MyViewHolder(View view) {
			super(view);

			tv=(TextView) view.findViewById(R.id.item_rv_tv);
		}
		
	}

}

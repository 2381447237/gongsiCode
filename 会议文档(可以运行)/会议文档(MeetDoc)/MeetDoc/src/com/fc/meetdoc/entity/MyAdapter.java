package com.fc.meetdoc.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import com.fc.meetdoc.R;
import com.fc.meetdoc.views.SelectIPforSend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
    private String arr[];
    private Context context;
    public static List<String>setmsg= new ArrayList<String>();//存挑选出来的ip
    public MyAdapter(Context context,String arr[]){
    	this.arr=arr;
    	this.context=context;
    }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return arr[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inf=LayoutInflater.from(context);
		View view=inf.inflate(R.layout.select_list, null);
		TextView textView_select_list=(TextView)view.findViewById(R.id.textView_select_list);
		ImageView imageView_select_list=(ImageView)view.findViewById(R.id.imageView_select_list);
		if(arr!=null&&arr.length>0){
		textView_select_list.setText(arr[position]);
		}
		if(SelectIPforSend.setmap.get(position)){
			imageView_select_list.setImageResource(R.drawable.select2);
		}else{
			imageView_select_list.setImageResource(R.drawable.select1);
		}
		return view;
	}
	/**
	 * listview选中执行添加操作
	 */
	public void changelist(int position){
		if(SelectIPforSend.setmap.get(position)){
			if(setmsg!=null&&setmsg.size()>0){
//				Iterator<String>it=setmsg.iterator();
//				while(it.hasNext()){
//					String ss=(String)it.next();
//					if(!ss.equals(arr[position])){
//						setmsg.add(arr[position]);
//					}
//				}
				for(int i=0;i<setmsg.size();i++){
					if(setmsg.get(i).equals(arr[position])){
						return;
					}
					
				}
				setmsg.add(arr[position]);
			}else{
				setmsg.add(arr[position]);
			}
			
			}
		else{
		if(setmsg!=null&&setmsg.size()>0){
			Iterator<String>it=setmsg.iterator();
			while(it.hasNext()){
				String ss=(String)it.next();
				if(ss.equals(arr[position])){
					it.remove();
				}
			}
			
		}
		}
	}
}

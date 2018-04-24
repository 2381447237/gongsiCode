package com.fc.person.beans;

import java.util.List;

import com.fc.main.beans.PersonInfo;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.PersonMarkAdapter.MarkHodler;
import com.fc.zbetuch_sm.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PersonAdapter extends BaseAdapter{
	private List<PersonInfo> infos;
	private Context  mContext;

	public PersonAdapter(List<PersonInfo> infos, Context mContext) {
		super();
		this.infos = infos;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return infos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return infos.get(arg0).getID();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		MarkHodler markhodler =null;
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.person_item, null);
			markhodler = new MarkHodler();
			markhodler.tv_bianhao = (TextView)convertView.findViewById(R.id.tv_markbianhao);
			markhodler.tv_markname =(TextView)convertView.findViewById(R.id.tv_markname);
			markhodler.tv_markbeizhu =(TextView)convertView.findViewById(R.id.tv_markbeizhu);
			markhodler.btn_markButton=(Button) convertView.findViewById(R.id.btn_markbeizhu);
			convertView.setTag(markhodler);
		}else{
			markhodler = (MarkHodler) convertView.getTag();
		}
		convertView.setBackgroundResource(MainTools.getbackground1(position));
		PersonInfo personInfo = infos.get(position);
		markhodler.tv_bianhao.setText(position+1+"");
		markhodler.tv_markname.setText(personInfo.getType_Name().trim().replaceAll(" ", ""));
		markhodler.tv_markbeizhu.setText(personInfo.getUPDATE_DATE().replaceAll("T", " ").substring(0, 10));
		markhodler.btn_markButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				infos.remove(position);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
	class MarkHodler{
	 TextView tv_bianhao,tv_markname;
	 TextView tv_markbeizhu;
	 Button btn_markButton;
	}
}

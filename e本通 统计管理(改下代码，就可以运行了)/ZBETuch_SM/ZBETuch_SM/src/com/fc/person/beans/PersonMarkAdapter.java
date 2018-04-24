package com.fc.person.beans;

import java.util.ArrayList;

import com.fc.main.tools.MainTools;
import com.fc.zbetuch_sm.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class PersonMarkAdapter extends BaseAdapter {
	private ArrayList<PersonMark> personmark_list;
	private Context context;
	public PersonMarkAdapter(ArrayList<PersonMark> personmark_list,
			Context context) {
		super();
		this.personmark_list = personmark_list;
		this.context = context;
	}

	@Override
	public int getCount() {
		return personmark_list.size();
	}

	@Override
	public Object getItem(int position) {
		return personmark_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		MarkHodler markhodler =null;
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.modif, null);
			markhodler = new MarkHodler();
			markhodler.tv_bianhao = (TextView)convertView.findViewById(R.id.tv_markbianhao);
			markhodler.tv_markname =(TextView)convertView.findViewById(R.id.tv_markname);
			markhodler.tv_markbeizhu =(Button)convertView.findViewById(R.id.tv_markbeizhu);
			convertView.setTag(markhodler);
		}else{
			markhodler = (MarkHodler) convertView.getTag();
		}
		convertView.setBackgroundResource(MainTools.getbackground1(position));
		PersonMark personMark = personmark_list.get(position);
		markhodler.tv_bianhao.setText(position+1+"");
		markhodler.tv_markname.setText(personMark.getPersonMarkName().trim().replaceAll(" ", ""));
		markhodler.tv_markbeizhu.setFocusable(false);
		markhodler.tv_markbeizhu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				personmark_list.remove(position);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
	class MarkHodler{
	 TextView tv_bianhao,tv_markname;
	 Button tv_markbeizhu;
	}
}

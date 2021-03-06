package com.example.hospitalapp.nonetwork.adapter;

import java.util.List;

import com.example.hospitalapp.AnswerNonetWorkActivity;
import com.example.hospitalapp.CusviewRadioGroup;
import com.example.hospitalapp.R;
import com.example.hospitalapp.nonetwork.entity.AnswerNonetWorkContent;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AnswerNonetWorkAdapter extends BaseAdapter {

	private List<AnswerNonetWorkContent> data;
	private AnswerNonetWorkActivity context;
	private LayoutInflater inflater;

	private boolean isManyIcon = false;

	public AnswerNonetWorkAdapter(List<AnswerNonetWorkContent> data,
			AnswerNonetWorkActivity context) {
		super();
		this.data = data;
		this.context = context;
		inflater = LayoutInflater.from(context);
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
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();

			convertView = inflater
					.inflate(R.layout.answer_item_nonetwork, null);

			holder.ll = (LinearLayout) convertView
					.findViewById(R.id.answer_item_ll);

			holder.view = convertView.findViewById(R.id.answer_item_view);

			convertView.setTag(holder);
		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		AnswerNonetWorkContent ac = data.get(position);

		// if (qc.PARENTID == 0 && qc.ISANSWER == false) {
		// // 大标题
		// return TYPE_1;
		// } else if (qc.ISANSWER == true && qc.TITLE.indexOf("多选") != -1) {
		// // 小标题多选
		// return TYPE_2;
		// } else if (qc.ISANSWER == true && qc.TITLE.indexOf("多选") == -1) {
		// // 小标题单选
		// return TYPE_3;
		// } else if (qc.PARENTID != 0 && qc.ISANSWER == false) {
		// // 这个是服务器的答案，不要管
		// return TYPE_4;
		// }

		holder.ll.removeAllViews();

		// 文字选项
		TextView optionTv = new TextView(context);
		TextView optionTvEnd = new TextView(context);
		
		EditText etAdd=new EditText(context);
		// 图标选项
		if (ac.PARENTID != 0 && ac.ISANSWER == false) {

			ImageView optionIv = new ImageView(context);
			isManyIcon = false;
			isManySelect(ac.ID);
			if (isManyIcon == true) {
				optionIv.setImageResource(R.drawable.manynocheck);
			}else{
			optionIv.setImageResource(R.drawable.nocheck);
			}
			for (int i = 0; i < context.answerlist.size(); i++) {
				if (context.answerlist.get(i).ID == ac.ID) {
					isManyIcon = false;
					isManySelect(ac.ID);
					if (isManyIcon == true) {
						optionIv.setImageResource(R.drawable.manyischeck);
					} else {
						optionIv.setImageResource(R.drawable.ischeck);
					}
					optionTv.setTextColor(Color.rgb(0xff, 0x00, 0x00));
					optionTvEnd.setTextColor(Color.rgb(0xff, 0x00, 0x00));
				}
			}
			optionIv.setPadding(55, 5, 0, 0);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			holder.ll.addView(optionIv, lp);
		}

		// 大标题
		if (ac.PARENTID == 0 && ac.ISANSWER == false) {
			etAdd.setVisibility(View.GONE);
			optionTv.setTextSize(30);
			optionTv.setText("    " + ac.TITLE);
			holder.view.setVisibility(View.VISIBLE);
		} else
		// 问题
		if (ac.ISANSWER == true) {
			optionTv.setTextSize(23);
			optionTv.setText("        " + ac.TITLE);
			holder.view.setVisibility(View.VISIBLE);

			etAdd.setVisibility(View.GONE);
			for (int i = 0; i < context.answerlist.size(); i++) {

				if (context.answerlist.get(i).ID == ac.ID
						&& !TextUtils
								.isEmpty(context.answerlist.get(i).AnswerStr)) {
					etAdd.setVisibility(View.VISIBLE);
					etAdd.setTextSize(20);
					etAdd.setTextColor(Color.rgb(0xff,0x00, 0x00));
					etAdd.setText(context.answerlist.get(i).AnswerStr);
					etAdd.setBackgroundResource(R.drawable.bg_edittext);
					etAdd.setPadding(0,0,10,0);
				}

			}
		} else
		// 选项
		if (ac.PARENTID != 0 && ac.ISANSWER == false) {
			etAdd.setVisibility(View.GONE);
			optionTv.setTextSize(20);
			optionTv.setPadding(0, 0, 0, 0);
			optionTv.setText("   " + ac.TITLE + "     ");
			if (!TextUtils.isEmpty(ac.TITLE_END)
					&& !TextUtils.equals("null", ac.TITLE_END)) {
				optionTvEnd.setTextSize(20);
				optionTvEnd.setPadding(0, 0, 0, 0);
				optionTvEnd.setText("   " + ac.TITLE_END + "     ");
			}
			holder.view.setVisibility(View.GONE);
		}
		RelativeLayout.LayoutParams lpTv = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		lpTv.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		holder.ll.addView(optionTv, lpTv);
		holder.ll.addView(etAdd,lpTv);

		if (ac.PARENTID != 0 && ac.ISANSWER == false && ac.ISINPUT == true) {

			EditText optionEt = new EditText(context);
			// optionEt.setSingleLine(); 设置只显示一行
			optionEt.setText("          ");
			optionEt.setBackgroundResource(R.drawable.bg_edittext);
			for (int i = 0; i < context.answerlist.size(); i++) {

				if (context.answerlist.get(i).ID == ac.ID
						&& !TextUtils
								.isEmpty(context.answerlist.get(i).AnswerStr)) {
					optionEt.setText(context.answerlist.get(i).AnswerStr);
					optionEt.setBackgroundResource(R.drawable.bg_edittext);
				}

			}
			RelativeLayout.LayoutParams lpEt = new RelativeLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
			lpEt.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
			holder.ll.addView(optionEt, lpEt);

		}
		RelativeLayout.LayoutParams lpTvEnd = new RelativeLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		lpTvEnd.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		holder.ll.addView(optionTvEnd, lpTvEnd);

		return convertView;
	}

	private void isManySelect(int id) {

		for (int i = 0; i < data.size(); i++) {

			if (data.get(i).ID == id) {
				isMany(data.get(i).PARENTID);
			}

		}

	}

	private void isMany(int pid) {

		for (int i = 0; i < data.size(); i++) {
			if (data.get(i).ISANSWER == true
					&& data.get(i).TITLE.indexOf("多选") != -1) {
				if(data.get(i).ID==pid){
					isManyIcon=true;
				}
			}
		}

	}

	class ViewHolder {

		LinearLayout ll;
		View view;
	}

}

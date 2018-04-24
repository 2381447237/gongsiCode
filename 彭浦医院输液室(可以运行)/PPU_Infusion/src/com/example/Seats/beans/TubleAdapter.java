package com.example.Seats.beans;

import java.util.HashMap;
import java.util.List;


import com.example.Seats.views.TubleActivity;
import com.example.ppu_infusion.LoginActivity;
import com.example.ppu_infusion.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.RadioButton;


public class TubleAdapter extends BaseAdapter {
	private TubleActivity context;
	private List<GetStartInfo> getStartInfos;
	HashMap<String, Boolean> states = new HashMap<String, Boolean>();

	public TubleAdapter(TubleActivity context, List<GetStartInfo> getStartInfos) {
		super();
		this.context = context;
		this.getStartInfos = getStartInfos;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getStartInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return getStartInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ItemHodler hodler;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.adapter_tuble, null);
			hodler = new ItemHodler();
			hodler.tadio = (RadioButton) convertView.findViewById(R.id.radio);
			convertView.setTag(hodler);
		} else {
			hodler = (ItemHodler) convertView.getTag();
		}
		final GetStartInfo info = getStartInfos.get(position);
		hodler.tadio.setText("    "+info.getDRUGNAME());

		final RadioButton radio = (RadioButton) convertView
				.findViewById(R.id.radio);
		hodler.tadio = radio;

		if (context.seatsInfo == null) {
			// 扫描二维码进来的
			hodler.tadio.setEnabled(false);
			hodler.tadio.setChecked(true);
			for (String key : states.keySet()) {
				states.put(key, true);
			}
			states.put(String.valueOf(position), radio.isChecked());
			TubleAdapter.this.notifyDataSetChanged();
			context.GetInfo(info);
			hodler.tadio.setVisibility(View.GONE);

			getScanHeight();

			return convertView;
		} else {
			// 非扫描二维码进来的
			hodler.tadio.setChecked(false);
			hodler.tadio.setEnabled(true);
			hodler.tadio.setVisibility(View.VISIBLE);

			getNoScanHeight();

		}

		hodler.tadio.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				// 重置，确保最多只有一项被选中
				for (String key : states.keySet()) {
					states.put(key, false);

				}
				states.put(String.valueOf(position), radio.isChecked());
				TubleAdapter.this.notifyDataSetChanged();
				context.GetInfo(info);
			}
		});

		boolean res = false;
		if (states.get(String.valueOf(position)) == null
				|| states.get(String.valueOf(position)) == false) {
			res = false;
			states.put(String.valueOf(position), false);
		} else
			res = true;

		hodler.tadio.setChecked(res);
		
		if(hodler.tadio.isChecked()){
			
			if (context.seatsInfo != null) {
				//自动添加管型
			if (context.seatsInfo.getTUBEID() != null) {
				for (int i = 0; i < context.tubesInfos.size(); i++) {
					if (context.tubesInfos.get(i).getTUBEID()
							.equals(getStartInfos.get(position).getTUBEID())) {
						context.spinner_Tubes.setSelection(i);
						context.TubesAdapter.notifyDataSetChanged();
					}
				}
			}
		
			//自动添加滴速 
				if (context.seatsInfo.getDRIPCNTSPERMINUTE() != null) {
					for (int i = 0; i < context.speedInfos.size(); i++) {
						if (context.speedInfos.get(i).getDRIPCNTSPERMINUTE().equals(getStartInfos.get(position).getDRIPCNTSPERMINUTE())) {
							context.spinner_dripSpeed.setSelection(i);
							context.DripSpeedAdapter.notifyDataSetChanged();
						}
					}
				}
				}
		}
		
		return convertView;
	}

	class ItemHodler {
		RadioButton tadio;
	}

	@SuppressWarnings("static-access")
	// 扫描之后的高度
	private void getScanHeight() {

		FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) context.ll
				.getLayoutParams();

		if (LoginActivity.isPad(context)) {

			lp.height = context.dip2px(context, 100);

		} else {

			lp.height = context.dip2px(context, 90);

		}

		context.ll.setLayoutParams(lp);

	}

	@SuppressWarnings("static-access")
	// 非扫描的高度
	private void getNoScanHeight() {

		FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) context.ll
				.getLayoutParams();

		if (LoginActivity.isPad(context)) {

			lp.height = context.dip2px(context, 400);

		} else {

			lp.height = context.dip2px(context, 250);

		}

		context.ll.setLayoutParams(lp);

	}

}

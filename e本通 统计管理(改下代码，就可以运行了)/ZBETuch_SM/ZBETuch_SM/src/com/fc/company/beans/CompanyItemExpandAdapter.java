package com.fc.company.beans;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.fc.invite.views.InviteJobListActivity;
import com.fc.zbetuch_sm.R;

public class CompanyItemExpandAdapter extends BaseExpandableListAdapter {

	public CompanyItemExpandAdapter(Context context, List<CompanyItem> items) {
		super();
		this.context = context;
		this.items = items;

	}

	Context context;
	List<CompanyItem> items;

	@Override
	public Object getChild(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return items.get(arg1);
	}

	@Override
	public long getChildId(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return items.get(arg1).getComid();
	}

	@Override
	public View getChildView(final int arg0, int arg1, boolean arg2, View arg3,
			ViewGroup arg4) {

		if (arg3 == null) {
			arg3 = LayoutInflater.from(context).inflate(
					R.layout.activity_company_expandable_sub_item, null);
			// arg3.getBackground().setAlpha(150);
		}
		TextView lblCompanyCode = (TextView) arg3.findViewById(R.id.txtComCode);
		TextView txtComContactor = (TextView) arg3
				.findViewById(R.id.txtComContactor);
		TextView txtComTelePhone = (TextView) arg3
				.findViewById(R.id.txtComTelePhone);
		TextView txtComAddress = (TextView) arg3
				.findViewById(R.id.txtComAddress);
		TextView txtComComIntroduction = (TextView) arg3
				.findViewById(R.id.txtComComIntroduction);
		lblCompanyCode.setText(items.get(arg0).getComcode());
		txtComContactor.setText(items.get(arg0).getContactor());
		txtComTelePhone.setText(items.get(arg0).getTelephone());
		txtComAddress.setText(items.get(arg0).getAddress());
		txtComComIntroduction.setText(items.get(arg0).getComintroduction());
		Button comShowButton = (Button) arg3.findViewById(R.id.button_showjobs);
		comShowButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, InviteJobListActivity.class);
				intent.putExtra("flag",
						InviteJobListActivity.GET_ITEMS_BY_COMPANY);
				intent.putExtra("ComId", items.get(arg0).getComid());
				context.startActivity(intent);
			}
		});

		return arg3;

	}

	@Override
	public int getChildrenCount(int arg0) {
		// TODO Auto-generated method stub
		// return items.size();
		return 1;
	}

	@Override
	public Object getGroup(int arg0) {
		// TODO Auto-generated method stub
		return items.get(arg0);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public long getGroupId(int arg0) {
		// TODO Auto-generated method stub
		return items.get(arg0).getComid();
	}

	@Override
	public View getGroupView(int arg0, boolean arg1, View arg2, ViewGroup arg3) {

		if (arg2 == null) {
			arg2 = LayoutInflater.from(context).inflate(
					R.layout.activity_company_expandable_group_item, null);

			int i = arg2.getHeight();
			// arg2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
			// 30));

		}
		TextView lblCompanyName = (TextView) arg2.findViewById(R.id.txtComName);
		lblCompanyName.setText(items.get(arg0).getComname());

		// arg2.setBackgroundResource(MainTools.getbackground1(items.indexOf(items.get(arg0))));
		arg2.setBackgroundResource(R.drawable.companyitemselector);

		return arg2;

	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

}

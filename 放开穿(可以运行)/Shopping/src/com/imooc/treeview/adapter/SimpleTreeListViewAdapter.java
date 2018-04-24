package com.imooc.treeview.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shopping.R;
import com.imooc.treeview.utils.Node;
import com.imooc.treeview.utils.TreeHelper;
import com.imooc.treeview.utils.adapter.TreeListViewAdapter;

public class SimpleTreeListViewAdapter<T> extends TreeListViewAdapter<T>
{
	public SimpleTreeListViewAdapter(ListView tree, Context context,
			List<T> datas, int defaultExpandLevel)
			throws IllegalArgumentException, IllegalAccessException
	{
		super(tree, context, datas, defaultExpandLevel);
	}

	boolean index;
	@Override
	public View getConvertView(Node node, final int position, View convertView,
			ViewGroup parent)
	{
		ViewHolder holder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.tree_item, parent, false);
			holder = new ViewHolder();
			holder.mIcon = (ImageView) convertView
					.findViewById(R.id.image_jia);
			
			holder.mText = (TextView) convertView
					.findViewById(R.id.parentGroupTV);
			convertView.setTag(holder);
		} else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		if (node.getIcon() == -1)
		{
			holder.mIcon.setVisibility(View.INVISIBLE);
		} else
		{
			holder.mIcon.setVisibility(View.VISIBLE);
			holder.mIcon.setImageResource(node.getIcon());
		}
		holder.mIcon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					expandOrCollapse(position);
			}
		});

		holder.mText.setText(node.getName());

		return convertView;
	}

	private class ViewHolder
	{
		ImageView mIcon,mIcon_jian;
		TextView mText;
	}

	/**
	 * 鍔ㄦ�佹彃鍏ヨ妭鐐�
	 * 
	 * @param position
	 * @param string
	 */
	public void addExtraNode(int position, String string)
	{
		Node node = mVisibleNodes.get(position);
		int indexOf = mAllNodes.indexOf(node);
		// Node
		Node extraNode = new Node(-1, node.getId(), string);
		extraNode.setParent(node);
		node.getChildren().add(extraNode);
		mAllNodes.add(indexOf + 1, extraNode);

		mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes);
		notifyDataSetChanged();

	}

}

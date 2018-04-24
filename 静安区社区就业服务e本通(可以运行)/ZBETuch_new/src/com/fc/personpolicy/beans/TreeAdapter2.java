package com.fc.personpolicy.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.test.zbetuch_news.R;
import com.test.zbetuch_news.R.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 树数据源构造器
 * 
 * @author LongShao
 * 
 */
public class TreeAdapter2 extends BaseAdapter {

	private Context con;
	private LayoutInflater lif;
	private List<Node> allsCache = new ArrayList<Node>();
	private List<Node> alls = new ArrayList<Node>();
	private TreeAdapter2 oThis = this;
	private boolean hasCheckBox = true;// 是否拥有复选框
	private int expandedIcon = -1;
	private int collapsedIcon = -1;
	private int currentLevel = 0;

	/**
	 * TreeAdapter构造函数
	 * 
	 * @param context
	 * @param rootNode
	 *            根节点
	 */
	public TreeAdapter2(Context context, Node rootNode) {
		this.con = context;
		this.lif = (LayoutInflater) con
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		addNode(rootNode);
	}

	private void addNode(Node node) {
		alls.add(node);
		allsCache.add(node);
		if (node.isLeaf())
			return;
		for (int i = 0; i < node.getChildren().size(); i++) {
			addNode(node.getChildren().get(i));
		}
	}

	// 复选框联动
	private void checkNode(Node node, boolean isChecked) {
		node.setChecked(isChecked);
		for (int i = 0; i < node.getChildren().size(); i++) {
			checkNode(node.getChildren().get(i), isChecked);
		}
	}

	/**
	 * 获得选中节点
	 * 
	 * @return
	 */
	public List<Node> getSeletedNodes() {
		List<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < allsCache.size(); i++) {
			Node n = allsCache.get(i);
			if (n.isChecked()) {
				nodes.add(n);
			}
		}
		return nodes;
	}

	// 控制节点的展开和折叠
	private void filterNode() {
		alls.clear();
		for (int i = 0; i < allsCache.size(); i++) {
			Node n = allsCache.get(i);
			if (!n.isParentCollapsed() || n.isRoot()) {
				alls.add(n);
			}
		}
	}

	/**
	 * 设置是否拥有复选框
	 * 
	 * @param hasCheckBox
	 */
	public void setCheckBox(boolean hasCheckBox) {
		this.hasCheckBox = hasCheckBox;
	}

	/**
	 * 设置展开和折叠状态图标
	 * 
	 * @param expandedIcon
	 *            展开时图标
	 * @param collapsedIcon
	 *            折叠时图标
	 */
	public void setExpandedCollapsedIcon(int expandedIcon, int collapsedIcon) {
		this.expandedIcon = expandedIcon;
		this.collapsedIcon = collapsedIcon;
	}

	/**
	 * 设置展开级别
	 * 
	 * @param level
	 */
	public void setExpandLevel(int level) {
		alls.clear();
		currentLevel = level;
		for (int i = 0; i < allsCache.size(); i++) {
			Node n = allsCache.get(i);
			if (n.getLevel() == level) {
				n.setExpanded(false);
				alls.add(n);
			}
		}
		this.notifyDataSetChanged();
	}

	/**
	 * 控制节点的展开和收缩
	 * 
	 * @param position
	 */
	public void ExpandOrCollapse(int position) {
		Node n = alls.get(position);
		if (n != null) {
			if (!n.isLeaf()) {
				if (n.getLevel() == currentLevel) {
					filterChild(n);
				} else if (n.getLevel() > currentLevel) {
					changeTop(n);
				}

				this.notifyDataSetChanged();
			}
		}
	}

	/**
	 * 将二级菜单转为一级菜单
	 */
	public void changeTop(Node node) {
		alls.clear();
		currentLevel = node.getLevel();
		List<Node> brotherNodes = node.getParent().getChildren();
		for (Node node2 : brotherNodes) {
			if (node2 == node) {
				node2.setExpanded(true);
				alls.add(node2);
				alls.addAll(node2.getChildren());
			} else {
				node2.setExpanded(false);
				alls.add(node2);
			}
		}
	}

	/**
	 * 打开关闭下级菜单
	 * 
	 * @param node
	 */
	public void filterChild(Node node) {
		node.setExpanded(!node.isExpanded());
		alls.clear();
		List<Node> brotherNodes = new ArrayList<Node>();
		if (node.getParent() != null) {
			brotherNodes = node.getParent().getChildren();
		} else {
			brotherNodes.add(node);
		}
		for (Node node2 : brotherNodes) {
			if (node2 == node) {
				alls.add(node2);
				if (node2.isExpanded()) {
					alls.addAll(node2.getChildren());
				}
			} else {
				node2.setExpanded(false);
				alls.add(node2);
			}

		}
	}

	/**
	 * 返回上级菜单
	 */
	public void back() {

		for (Node node : allsCache) {
			node.setExpanded(false);
		}
		Node firstnode = alls.get(0);
		List<Node> fatherNodes = new ArrayList<Node>();
		Node fatherNode = firstnode.getParent();
		if (fatherNode.getTag() == null) {
			return;
		}

		if (fatherNode != null) {
			currentLevel = fatherNode.getLevel();
			alls.clear();
			if (fatherNode.getParent() != null) {
				fatherNodes.addAll(fatherNode.getParent().getChildren());
			} else {
				fatherNodes.add(fatherNode);
			}

			for (Node father : fatherNodes) {
				if (father == fatherNode) {
					father.setExpanded(true);
					alls.add(father);
					alls.addAll(father.getChildren());
				} else {
					father.setExpanded(false);
					alls.add(father);
				}
			}

			this.notifyDataSetChanged();
		}
	}

	/**
	 * 点击目录刷新菜单列表
	 * 
	 * @param node
	 */
	public void filterByPath(Node node) {
		alls.clear();
		for (Node nodeItem : allsCache) {
			nodeItem.setExpanded(false);
		}
		currentLevel = node.getLevel() + 1;
		List<Node> childList = node.getChildren();
		alls.addAll(childList);
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return alls.size();
	}

	@Override
	public Object getItem(int position) {
		return alls.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// 得到当前节点
		Node n = alls.get(position);

		view = LayoutInflater.from(con).inflate(R.layout.treeitem, null);

		CheckBox chbSelect = (CheckBox) view.findViewById(R.id.chbSelect);
		chbSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Node n = (Node) v.getTag();
				checkNode(n, ((CheckBox) v).isChecked());
				oThis.notifyDataSetChanged();
			}

		});

		ImageView ivIcon = (ImageView) view.findViewById(R.id.ivIcon);
		TextView tvText = (TextView) view.findViewById(R.id.tvText);
		ImageView ivExEc = (ImageView) view.findViewById(R.id.ivExEc);

		if (n != null) {
			chbSelect.setTag(n);
			chbSelect.setChecked(n.isChecked());

			// 是否显示复选框
			if (n.hasCheckBox() && hasCheckBox) {
				chbSelect.setVisibility(View.VISIBLE);
			} else {
				chbSelect.setVisibility(View.GONE);
			}

			// 是否显示图标
			if (n.getIcon() == -1) {
				ivIcon.setVisibility(View.GONE);
			} else {
				ivIcon.setVisibility(View.VISIBLE);
				ivIcon.setImageResource(n.getIcon());
			}

			// 显示文本
			tvText.setText(n.getText());

			if (n.isLeaf()) {
				// 是叶节点 不显示展开和折叠状态图标
				ivExEc.setVisibility(View.GONE);
			} else {
				// 单击时控制子节点展开和折叠,状态图标改变
				ivExEc.setVisibility(View.VISIBLE);
				if (n.isExpanded()) {
					if (expandedIcon != -1)
						ivExEc.setImageResource(expandedIcon);
				} else {
					if (collapsedIcon != -1)
						ivExEc.setImageResource(collapsedIcon);
				}
			}

			// 控制缩进
			// view.setPadding(100 * n.getLevel(), 3, 3, 3);
			view.setPadding(50 * (n.getLevel() - currentLevel), 3, 3, 3);

			PolicyLevelItem item = (PolicyLevelItem) n.getTag();
			if (item != null && item.getStop().trim().equalsIgnoreCase("true")) {
				Bitmap bitmap = BitmapFactory.decodeResource(
						con.getResources(), R.drawable.hui);
				BitmapDrawable drawable = new BitmapDrawable(bitmap);
				drawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
				drawable.setDither(true);
				view.setBackgroundDrawable(drawable);
				// view.setBackgroundResource(R.drawable.hui);
			} else {
				if (n.getLevel() == currentLevel) {
					Bitmap bitmap = BitmapFactory.decodeResource(
							con.getResources(), R.drawable.treelevel1);
					BitmapDrawable drawable = new BitmapDrawable(bitmap);
					drawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
					drawable.setDither(true);
					view.setBackgroundDrawable(drawable);

					// view.setBackgroundResource(R.drawable.treefirst);
				} else if (n.getLevel() > currentLevel) {
					Bitmap bitmap = BitmapFactory.decodeResource(
							con.getResources(), R.drawable.treelevel2);
					BitmapDrawable drawable = new BitmapDrawable(bitmap);
					drawable.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
					drawable.setDither(true);
					view.setBackgroundDrawable(drawable);
					// view.setBackgroundResource(R.drawable.treesecond);
					view.setAnimation(AnimationUtils.loadAnimation(con,
							R.anim.list_anim));
				}

				view.setOnTouchListener(new MyOnTouchListener());

			}

		}

		return view;
	}

	private Resources getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnabled(int position) {
		Node n = alls.get(position);
		PolicyLevelItem item = (PolicyLevelItem) n.getTag();
		if (item != null && item.getStop().trim().equalsIgnoreCase("true")) {
			return false;
		}
		return true;
	}

	private Drawable back;
	private View view;
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 100) {
				view.setBackgroundDrawable(back);
			}
		}

	};

	private class MyOnTouchListener implements OnTouchListener {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			back = v.getBackground();
			view = v;
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.setBackgroundColor(con.getResources().getColor(R.color.org));
				new Timer().schedule(new TimerTask() {

					@Override
					public void run() {
						handler.sendEmptyMessage(100);
					}
				}, 100);
			}
			return false;
		}

	}

}

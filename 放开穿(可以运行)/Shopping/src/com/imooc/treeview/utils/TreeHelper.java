package com.imooc.treeview.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import com.example.shopping.R;
import com.imooc.treeview.utils.annotation.TreeNodeId;
import com.imooc.treeview.utils.annotation.TreeNodeLabel;
import com.imooc.treeview.utils.annotation.TreeNodePid;

public class TreeHelper
{
	/**
	 * 灏嗙敤鎴风殑鏁版嵁杞寲涓烘爲褰㈡暟鎹�
	 * 
	 * @param datas
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static <T> List<Node> convertDatas2Nodes(List<T> datas)
			throws IllegalArgumentException, IllegalAccessException
	{
		List<Node> nodes = new ArrayList<Node>();
		Node node = null;
		for (T t : datas)
		{
			int id = -1;
			int pid = -1;
			String label = null;

			node = new Node();
			Class clazz = t.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields)
			{
				if (field.getAnnotation(TreeNodeId.class) != null)
				{
					field.setAccessible(true);
					id = field.getInt(t);
				}
				if (field.getAnnotation(TreeNodePid.class) != null)
				{
					field.setAccessible(true);
					pid = field.getInt(t);
				}
				if (field.getAnnotation(TreeNodeLabel.class) != null)
				{
					field.setAccessible(true);
					label = (String) field.get(t);
				}
			}
			node = new Node(id, pid, label);
			nodes.add(node);
		}// for end
		
		Log.e("TAG", nodes+"");

		/**
		 * 璁剧疆Node闂寸殑鑺傜偣鍏崇郴
		 */
		for (int i = 0; i < nodes.size(); i++)
		{
			Node n = nodes.get(i);

			for (int j = i + 1; j < nodes.size(); j++)
			{
				Node m = nodes.get(j);

				if (m.getpId() == n.getId())
				{
					n.getChildren().add(m);
					m.setParent(n);
				} else if (m.getId() == n.getpId())
				{
					m.getChildren().add(n);
					n.setParent(m);
				}
			}
		}

		for (Node n : nodes)
		{
			setNodeIcon(n);
		}
		return nodes;
	}

	public static <T> List<Node> getSortedNodes(List<T> datas,
			int defaultExpandLevel) throws IllegalArgumentException,
			IllegalAccessException
	{
		List<Node> result = new ArrayList<Node>();
		List<Node> nodes = convertDatas2Nodes(datas);
		// 鑾峰緱鏍戠殑鏍圭粨鐐�
		List<Node> rootNodes = getRootNodes(nodes);

		for (Node node : rootNodes)
		{
			addNode(result, node, defaultExpandLevel, 1);
		}

		Log.e("TAG", result.size() + "");
		return result;
	}

	/**
	 * 鎶婁竴涓妭鐐圭殑鎵�鏈夊瀛愯妭鐐归兘鏀惧叆result
	 * 
	 * @param result
	 * @param node
	 * @param defaultExpandLevel
	 * @param i
	 */
	private static void addNode(List<Node> result, Node node,
			int defaultExpandLevel, int currentLevel)
	{
		result.add(node);
		if (defaultExpandLevel >= currentLevel)
		{
			node.setExpand(true);
		}
		if (node.isLeaf())
			return;

		for (int i = 0; i < node.getChildren().size(); i++)
		{
			addNode(result, node.getChildren().get(i), defaultExpandLevel,
					currentLevel + 1);
		}

	}

	/**
	 * 杩囨护鍑哄彲瑙佺殑鑺傜偣
	 * 
	 * @param nodes
	 * @return
	 */
	public static List<Node> filterVisibleNodes(List<Node> nodes)
	{
		List<Node> result = new ArrayList<Node>();

		for (Node node : nodes)
		{
			if (node.isRoot() || node.isParentExpand())
			{
				setNodeIcon(node);
				result.add(node);
			}
		}
		return result;
	}

	/**
	 * 浠庢墍鏈夎妭鐐逛腑杩囨护鍑烘牴鑺傜偣
	 * 
	 * @param nodes
	 * @return
	 */
	private static List<Node> getRootNodes(List<Node> nodes)
	{
		List<Node> root = new ArrayList<Node>();
		for (Node node : nodes)
		{
			if (node.isRoot())
			{
				root.add(node);
			}
		}
		return root;
	}

	/**
	 * 涓篘ode璁剧疆鍥炬爣
	 * 
	 * @param n
	 */
	private static void setNodeIcon(Node n)
	{
		if (n.getChildren().size() > 0 && n.isExpand())
		{
			n.setIcon(R.drawable.jian);
		} else if (n.getChildren().size() > 0 && !n.isExpand())
		{
			n.setIcon(R.drawable.jia);
		} else
		{
			n.setIcon(-1);
		}
	}

}

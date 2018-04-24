package com.example.adapters;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.infoclass.MyColorCheck;
import com.example.infoclass.MyDownJackets;
import com.example.infoclass.MyDress;
import com.example.infoclass.MyLongPant;
import com.example.infoclass.MyMaterialTexture;
import com.example.infoclass.MyModel;
import com.example.infoclass.MySize;
import com.example.infoclass.MyStraight;
import com.example.infoclass.MyZipper;
import com.example.infoclass.ShopRight;
import com.example.shopping.R;
import com.example.shopping.ShopFragment;
import com.example.thirdlevelactivity.SizeActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class RightMenuAdapter extends BaseAdapter {
	private ArrayList<ShopRight> rightlist;
	private ShopFragment context;
	private ColorAdapter colorAdapter;// 颜色适配器
	private SizeAdapter sizeAdapter;// 尺寸适配器
	private PriceAdapter priceAdapter;// 价格适配器
	private OtherSizeAdapter otherSizeAdapter;
	private LongPantAdapter longPantAdapter;// 裤长适配器
	private StraightAdapter straightAdapter;// 直筒适配器
	private ZipperAdapter zipperAdapter;// 拉链适配器
	private DownJacketsAdapter downJacketsAdapter;// 羽绒服适配器
	private MaterialTextureAdapter materialTextureAdapter;// 材质适配器
	private ModelAdapter modelAdapter;// 款型适配器
	private DressAdapter dressAdapter;// 连衣裙适配器
	boolean flag = true;

	public RightMenuAdapter(ShopFragment context, ArrayList<ShopRight> rightlist) {
		this.rightlist = rightlist;
		this.context = context;
	}

	@Override
	public int getCount() {
		return rightlist.size();
	}

	@Override
	public Object getItem(int position) {
		return rightlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context.getActivity()).inflate(
					R.layout.rightmenu, null);
			holder.btn_size = (TextView) (convertView)
					.findViewById(R.id.tv_size);
			holder.img_cha = (ImageView) (convertView)
					.findViewById(R.id.img_cha);
			holder.gridView = (GridView) (convertView)
					.findViewById(R.id.gridview_size);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final ShopRight shopRight = rightlist.get(position);
		holder.btn_size.setText(shopRight.title);
		if (position == 0) {
			holder.gridView.setNumColumns(3);
			String[] size = shopRight.category.split(",");
			for (int i = 0; i < size.length; i++) {
				SizeAdapter.mySizeList.add(new MySize(size[i], false));
			}
			sizeAdapter = new SizeAdapter(context, size);
			holder.gridView.setAdapter(sizeAdapter);
		} else if (position == 1) {
			String[] str = { "1" };
			priceAdapter = new PriceAdapter(context, str);
			holder.gridView.setNumColumns(1);
			holder.gridView.setAdapter(priceAdapter);
		} else if (position == 2) {
			holder.gridView.setNumColumns(3);
			String[] color = shopRight.category.split(",");
			for (int i = 0; i < color.length; i++) {
				ColorAdapter.myColorList.add(new MyColorCheck(
						R.drawable.btn_color_src_m + "", color[i],
						R.drawable.rightsel, false));
			}
			colorAdapter = new ColorAdapter(context, color);
			holder.gridView.setAdapter(colorAdapter);
		} else if (shopRight.title.equals("裤长")) {
			holder.gridView.setNumColumns(3);
			String[] size = shopRight.category.split(",");
			for (int i = 0; i < size.length; i++) {
				LongPantAdapter.myLongPantList.add(new MyLongPant(size[i],
						false));
			}
			longPantAdapter = new LongPantAdapter(context, size);
			holder.gridView.setAdapter(longPantAdapter);
		} else if (shopRight.title.equals("直筒")) {
			holder.gridView.setNumColumns(3);
			String[] size = shopRight.category.split(",");
			for (int i = 0; i < size.length; i++) {
				StraightAdapter.myStraightList.add(new MyStraight(size[i],
						false));
			}
			straightAdapter = new StraightAdapter(context, size);
			holder.gridView.setAdapter(straightAdapter);
		} else if (shopRight.title.equals("拉链")) {
			holder.gridView.setNumColumns(3);
			String[] size = shopRight.category.split(",");
			for (int i = 0; i < size.length; i++) {
				ZipperAdapter.myZipperList.add(new MyZipper(size[i], false));
			}
			zipperAdapter = new ZipperAdapter(context, size);
			holder.gridView.setAdapter(zipperAdapter);
		} else if (shopRight.title.equals("羽绒服")) {
			holder.gridView.setNumColumns(3);
			String[] size = shopRight.category.split(",");
			for (int i = 0; i < size.length; i++) {
				DownJacketsAdapter.myDownJacketsList.add(new MyDownJackets(
						size[i], false));
			}
			downJacketsAdapter = new DownJacketsAdapter(context, size);
			holder.gridView.setAdapter(downJacketsAdapter);
		} else if (shopRight.title.equals("材质")) {
			holder.gridView.setNumColumns(3);
			String[] size = shopRight.category.split(",");
			for (int i = 0; i < size.length; i++) {
				MaterialTextureAdapter.myMaterialTextureList
						.add(new MyMaterialTexture(size[i], false));
			}
			materialTextureAdapter = new MaterialTextureAdapter(context, size);
			holder.gridView.setAdapter(materialTextureAdapter);
		} else if (shopRight.title.equals("款型")) {
			holder.gridView.setNumColumns(3);
			String[] size = shopRight.category.split(",");
			for (int i = 0; i < size.length; i++) {
				ModelAdapter.myModelList.add(new MyModel(size[i], false));
			}
			modelAdapter = new ModelAdapter(context, size);
			holder.gridView.setAdapter(modelAdapter);
		} else if (shopRight.title.equals("连衣裙")) {
			holder.gridView.setNumColumns(3);
			String[] size = shopRight.category.split(",");
			for (int i = 0; i < size.length; i++) {
				DressAdapter.myDressList.add(new MyDress(size[i], false));
			}
			dressAdapter = new DressAdapter(context, size);
			holder.gridView.setAdapter(dressAdapter);
		} else {
			if (!shopRight.category.equals("")) {
				holder.gridView.setNumColumns(3);
				String[] size = shopRight.category.split(",");
				otherSizeAdapter = new OtherSizeAdapter(context, size);
				holder.gridView.setAdapter(otherSizeAdapter);
			} else {
				holder.gridView.setNumColumns(3);
				String[] str = {};
				otherSizeAdapter = new OtherSizeAdapter(context, str);
				holder.gridView.setAdapter(otherSizeAdapter);
			}
		}

		holder.img_cha.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				context.showDialog(context.getActivity());
				
				if (shopRight.title.equals("尺寸")) {
					//Toast.makeText(context.getActivity(), "点击了尺寸x", 1000).show();
					for (MySize s : SizeAdapter.mySizeList) {
						if (s.isSelected()) {
							s.setSelected(false);
						}
						SizeAdapter.AllSizeName = "";
						ShopFragment.rightMenuAdapter.notifyDataSetChanged();					
					}
				} else if (shopRight.title.equals("价格")) {
//					Toast.makeText(context.getActivity(), "点击了价格x", 1000)
//							.show();
				
					PriceAdapter.UrlStartMoney=0;
					PriceAdapter.UrlEndMoney=1000;
					context.setStartMoney(0,0);
					
				} else if (shopRight.title.equals("颜色")) {
//					Toast.makeText(context.getActivity(), "点击了颜色x", 1000)
//							.show();
					for (MyColorCheck mcc : ColorAdapter.myColorList) {
						if (mcc.isChecked()) {
							mcc.setChecked(false);
						}
						ColorAdapter.AllColorName = "";
						ShopFragment.rightMenuAdapter.notifyDataSetChanged();
					}
				} else if (shopRight.title.equals("裤长")) {
//					Toast.makeText(context.getActivity(), "点击了裤长x", 1000)
//							.show();
					for (MyLongPant mlp : LongPantAdapter.myLongPantList) {
						if (mlp.isChecked()) {
							mlp.setChecked(false);
						}
						LongPantAdapter.AllProOptName = "";
						ShopFragment.rightMenuAdapter.notifyDataSetChanged();
					}
				} else if (shopRight.title.equals("直筒")) {
//					Toast.makeText(context.getActivity(), "点击了直筒x", 1000)
//							.show();
					for (MyStraight ms : StraightAdapter.myStraightList) {
						if (ms.isChecked()) {
							ms.setChecked(false);
						}
						StraightAdapter.AllProOptNameStraight = "";
						ShopFragment.rightMenuAdapter.notifyDataSetChanged();
					}
				} else if (shopRight.title.equals("拉链")) {
//					Toast.makeText(context.getActivity(), "点击了拉链x", 1000)
//							.show();
					for (MyZipper mz : ZipperAdapter.myZipperList) {
						if (mz.isChecked()) {
							mz.setChecked(false);
						}
						ZipperAdapter.AllProOptNameZipper = "";
						ShopFragment.rightMenuAdapter.notifyDataSetChanged();
					}
				} else if (shopRight.title.equals("羽绒服")) {
//					Toast.makeText(context.getActivity(), "点击了羽绒服x", 1000)
//							.show();
					for (MyDownJackets mdj : DownJacketsAdapter.myDownJacketsList) {
						if (mdj.isChecked()) {
							mdj.setChecked(false);
						}
						DownJacketsAdapter.AllProOptNameDownJackets = "";
						ShopFragment.rightMenuAdapter.notifyDataSetChanged();
					}
				} else if (shopRight.title.equals("材质")) {
//					Toast.makeText(context.getActivity(), "点击了材质x", 1000)
//							.show();
					for (MyMaterialTexture mmt : MaterialTextureAdapter.myMaterialTextureList) {
						if (mmt.isChecked()) {
							mmt.setChecked(false);
						}
						MaterialTextureAdapter.AllProOptNameMaterialTexture = "";
						ShopFragment.rightMenuAdapter.notifyDataSetChanged();
					}
				} else if (shopRight.title.equals("款型")) {
//					Toast.makeText(context.getActivity(), "点击了款型x", 1000)
//							.show();
					for (MyModel mm : ModelAdapter.myModelList) {
						if (mm.isChecked()) {
							mm.setChecked(false);
						}
						ModelAdapter.AllProOptNameModel = "";
						ShopFragment.rightMenuAdapter.notifyDataSetChanged();
					}
				} else if (shopRight.title.equals("连衣裙")) {
//					Toast.makeText(context.getActivity(), "点击了连衣裙x", 1000)
//							.show();
					for (MyDress md : DressAdapter.myDressList) {
						if (md.isChecked()) {
							md.setChecked(false);
						}
						DressAdapter.AllProOptNameDress = "";
						ShopFragment.rightMenuAdapter.notifyDataSetChanged();
					}
				}
				context.addSizeInfo("");
				
			}

		});

		return convertView;
	}

	public class ViewHolder {
		TextView btn_size;
		ImageView img_cha;
		GridView gridView;
	}
}

package com.fc.person.beans;

import java.util.ArrayList;
import java.util.List;

public class hangye {

	private String hangyeName;
	private String hangyeCode;

	public List<hangye> GetAll()
	{
		List<hangye> list = new ArrayList<hangye>();
		list.add(new hangye("请选择","0"));
		list.add(new hangye("企业管理人员","105000"));
		list.add(new hangye("科研人员","201000"));
		list.add(new hangye("工程技术人员","202000"));
		list.add(new hangye("农业技术人员","203000"));
		list.add(new hangye("飞机船舶技术人员","204000"));
		list.add(new hangye("卫生专业技术人员","205000"));
		list.add(new hangye("经济业务人员","206000"));
		list.add(new hangye("金融业务人员","207000"));
		list.add(new hangye("法律专业人员","208000"));
		list.add(new hangye("教学人员","209000"));
		list.add(new hangye("文学艺术工作者","210000"));
		list.add(new hangye("体育工作者","211000"));
		list.add(new hangye("新闻出版文化工作者","212000"));
		list.add(new hangye("行政办公人员","301000"));
		list.add(new hangye("安全保卫和消防人员","302000"));
		list.add(new hangye("邮政电信业务人员","303000"));
		list.add(new hangye("购销人员","401000"));
		list.add(new hangye("仓储人员","402000"));
		list.add(new hangye("餐饮服务人员","403000"));
		list.add(new hangye("饭店、旅游娱乐服务员","404000"));
		list.add(new hangye("运输服务人员","405000"));
		list.add(new hangye("医疗卫生辅助服务人员","406000"));
		list.add(new hangye("社会服务人员","407000"));
		list.add(new hangye("种植业生产人员","501000"));
		list.add(new hangye("林业及动植物保护人员","502000"));
		list.add(new hangye("畜牧业生产人员","503000"));
		list.add(new hangye("渔业生产人员","504000"));
		list.add(new hangye("水利设施管理养护人员","505000"));
		list.add(new hangye("农林机械操作和能源开发人员","509000"));
		list.add(new hangye("勘测及矿物开采工","601000"));
		list.add(new hangye("金属冶炼轧制工","602000"));
		list.add(new hangye("化工产品生产工","603000"));
		list.add(new hangye("机械制造加工工","604000"));
		list.add(new hangye("机电产品装配工","605000"));
		list.add(new hangye("机械设备修理工","606000"));
		list.add(new hangye("电力设备装运检修工","607000"));
		list.add(new hangye("电子元器件制造装调工","608000"));
		list.add(new hangye("橡胶塑料制品生产工","609000"));
		list.add(new hangye("纺织针织印染工","610000"));
		list.add(new hangye("裁剪缝纫毛皮革制作工","611000"));
		list.add(new hangye("粮油食品饮料生产工","612000"));
		list.add(new hangye("烟草制品加工工","613000"));
		list.add(new hangye("药品生产制造工","614000"));
		list.add(new hangye("木材人造板生产制作工","615000"));
		list.add(new hangye("制浆造纸纸制品生产工","616000"));
		list.add(new hangye("建筑材料生产加工工","617000"));
		list.add(new hangye("玻璃陶瓷搪瓷生产工","618000"));
		list.add(new hangye("广播影视品制作播放人员","619000"));
		list.add(new hangye("制版印刷人员","620000"));
		list.add(new hangye("工艺、美术品制作人员","621000"));
		list.add(new hangye("文体用品乐器制作人员","622000"));
		list.add(new hangye("建筑和工程施工人员","623000"));
		list.add(new hangye("驾驶员和运输工人","624000"));
		list.add(new hangye("环境监测废物处理人员","625000"));
		list.add(new hangye("检验、计量人员","626000"));
		list.add(new hangye("体力工人","627000"));
		return list;
	}
	public hangye(String name, String code) {
		super();
		this.hangyeName = name;
		this.hangyeCode = code;
	}
	public String getName() {
		return hangyeName;
	}
	public void setName(String name) {
		this.hangyeName = name;
	}
	public String getCode() {
		return hangyeCode;
	}
	public void setCode(String code) {
		this.hangyeCode = code;
	}
	public hangye() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return hangyeName;
	}
	
	
}


package com.fc.person.beans;

import java.util.ArrayList;
import java.util.List;

public class Zihangye {
	private String Zihangename;
	private String ZihangyeCode;
	private String hangyeCode;
	public Zihangye() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Zihangye(String zihangename, String zihangyeCode, String hangyeCode) {
		super();
		Zihangename = zihangename;
		ZihangyeCode = zihangyeCode;
		this.hangyeCode = hangyeCode;
	}
	public String getZihangename() {
		return Zihangename;
	}
	public void setZihangename(String zihangename) {
		Zihangename = zihangename;
	}
	public String getZihangyeCode() {
		return ZihangyeCode;
	}
	public void setZihangyeCode(String zihangyeCode) {
		ZihangyeCode = zihangyeCode;
	}
	public String getHangyeCode() {
		return hangyeCode;
	}
	public void setHangyeCode(String hangyeCode) {
		this.hangyeCode = hangyeCode;
	}
	@Override
	public String toString() {
		return Zihangename;
	}
	
	public  List<Zihangye> findAll(){
		List<Zihangye> list_zihangye = new ArrayList<Zihangye>();
		list_zihangye.add(new Zihangye("请选择","01","0"));
		list_zihangye.add(new Zihangye("部门经理","1050100","105000"));
		list_zihangye.add(new Zihangye("其他企业管理人员","1059900","105000"));
		list_zihangye.add(new Zihangye("科研人员","2019900","201000"));
		list_zihangye.add(new Zihangye("地质勘探工程技术人员","2020100","202000"));
		list_zihangye.add(new Zihangye("测绘工程技术人员","2020200","202000"));
		list_zihangye.add(new Zihangye("矿山工程技术人员","2020300","202000"));
		list_zihangye.add(new Zihangye("石油工程技术人员","2020400","202000"));
		list_zihangye.add(new Zihangye("冶金工程技术人员","2020500","202000"));
		list_zihangye.add(new Zihangye("化工工程技术人员","2020600","202000"));
		list_zihangye.add(new Zihangye("机械工程技术人员","2020700","202000"));
		list_zihangye.add(new Zihangye("兵器航空航天工程技术人员","2020800","202000"));
		list_zihangye.add(new Zihangye("电子工程技术人员","2021100","202000"));
		list_zihangye.add(new Zihangye("通信工程技术人员","2021200","202000"));
		list_zihangye.add(new Zihangye("计算机工程技术人员","2021300","202000"));
		list_zihangye.add(new Zihangye("电气工程技术人员","2021400","202000"));
		list_zihangye.add(new Zihangye("电力工程技术人员","2021500","202000"));
		list_zihangye.add(new Zihangye("邮政工程技术人员","2021600","202000"));
		list_zihangye.add(new Zihangye("广播影视工程技术人员","2021700","202000"));
		list_zihangye.add(new Zihangye("交通工程技术人员","2021800","202000"));
		list_zihangye.add(new Zihangye("民用航空工程技术人员","2021900","202000"));
		list_zihangye.add(new Zihangye("铁路工程技术人员","2022000","202000"));
		list_zihangye.add(new Zihangye("建筑工程技术人员","2022100","202000"));
		list_zihangye.add(new Zihangye("建材工程技术人员","2022200","202000"));
		list_zihangye.add(new Zihangye("家具设计、林业工程技术人员","2022300","202000"));
		list_zihangye.add(new Zihangye("水利工程技术人员","2022400","202000"));
		list_zihangye.add(new Zihangye("海洋工程技术人员","2022500","202000"));
		list_zihangye.add(new Zihangye("水产工程技术人员","2022600","202000"));
		list_zihangye.add(new Zihangye("纺织工程技术人员","2022700","202000"));
		list_zihangye.add(new Zihangye("食品工程技术人员","2022800","202000"));
		list_zihangye.add(new Zihangye("气象工程技术人员","2022900","202000"));
		list_zihangye.add(new Zihangye("地震工程技术人员","2023000","202000"));
		list_zihangye.add(new Zihangye("环境保护工程技术人员","2023100","202000"));
		list_zihangye.add(new Zihangye("安全工程技术人员","2023200","202000"));
		list_zihangye.add(new Zihangye("标准化、计量、质量工程技术人员","2023300","202000"));
		list_zihangye.add(new Zihangye("工业管理工程技术人员","2023400","202000"));
		list_zihangye.add(new Zihangye("生物工程技术人员","2023500","202000"));
		list_zihangye.add(new Zihangye("其他工程技术人员","2029900","202000"));
		list_zihangye.add(new Zihangye("土壤肥料技术人员","2030100","203000"));
		list_zihangye.add(new Zihangye("植物保护技术人员","2030200","203000"));
		list_zihangye.add(new Zihangye("园艺技术人员","2030300","203000"));
		list_zihangye.add(new Zihangye("作物遗传育种栽培技术人员","2030400","203000"));
		list_zihangye.add(new Zihangye("兽医兽药技术人员","2030500","203000"));
		list_zihangye.add(new Zihangye("畜牧与草业技术人员","2030600","203000"));
		list_zihangye.add(new Zihangye("其他农业技术人员","2039900","203000"));
		list_zihangye.add(new Zihangye("船舶指挥引航人员","2040200","204000"));
		list_zihangye.add(new Zihangye("其他飞机船舶技术人员","2049900","204000"));
		list_zihangye.add(new Zihangye("西医","2050100","205000"));
		list_zihangye.add(new Zihangye("中医","2050200","205000"));
		list_zihangye.add(new Zihangye("公共卫生医师","2050500","205000"));
		list_zihangye.add(new Zihangye("药剂师","2050600","205000"));
		list_zihangye.add(new Zihangye("医疗技术人员","2050700","205000"));
		list_zihangye.add(new Zihangye("护士","2050800","205000"));
		list_zihangye.add(new Zihangye("其他卫生专业技术人员","2050900","205000"));
		list_zihangye.add(new Zihangye("经济计划人员","2060100","206000"));
		list_zihangye.add(new Zihangye("统计师","2060200","206000"));
		list_zihangye.add(new Zihangye("财会人员","2060300","206000"));
		list_zihangye.add(new Zihangye("审计师","2060400","206000"));
		list_zihangye.add(new Zihangye("国际商务人员","2060500","206000"));
		list_zihangye.add(new Zihangye("房地产业务人员","2060600","206000"));
		list_zihangye.add(new Zihangye("其他经济业务人员","2069900","206000"));
		list_zihangye.add(new Zihangye("银行业务人员","2070100","207000"));
		list_zihangye.add(new Zihangye("保险业务人员","2070200","207000"));
		list_zihangye.add(new Zihangye("证券业务人员","2070300","207000"));
		list_zihangye.add(new Zihangye("其他金融业务人员","2079900","207000"));
		list_zihangye.add(new Zihangye("律师","2080300","208000"));
		list_zihangye.add(new Zihangye("其他法律专业人员","2089900","208000"));
		list_zihangye.add(new Zihangye("高等教育教师","2090100","209000"));
		list_zihangye.add(new Zihangye("中等职业教育教师","2090200","209000"));
		list_zihangye.add(new Zihangye("中学教师","2090300","209000"));
		list_zihangye.add(new Zihangye("小学教师","2090400","209000"));
		list_zihangye.add(new Zihangye("幼儿教师","2090500","209000"));
		list_zihangye.add(new Zihangye("特殊教育教师","2090600","209000"));
		list_zihangye.add(new Zihangye("其他教学人员","2099900","209000"));
		list_zihangye.add(new Zihangye("文艺创作和评论人员","2100100","210000"));
		list_zihangye.add(new Zihangye("编导和音乐指挥人员","2100200","210000"));
		list_zihangye.add(new Zihangye("演员","2100300","210000"));
		list_zihangye.add(new Zihangye("乐器演奏员","2100400","210000"));
		list_zihangye.add(new Zihangye("影视制作及舞台专业人员","2100500","210000"));
		list_zihangye.add(new Zihangye("美术专业人员","2100600","210000"));
		list_zihangye.add(new Zihangye("工美装饰服装广告设计人员","2100700","210000"));
		list_zihangye.add(new Zihangye("装璜美术设计人员","2100704","210000"));
		list_zihangye.add(new Zihangye("服装设计师","2100705","210000"));
		list_zihangye.add(new Zihangye("室内装饰设计人员","2100706","210000"));
		list_zihangye.add(new Zihangye("广告设计人员","2100708","210000"));
		list_zihangye.add(new Zihangye("其他文学艺术工作者","2109900","210000"));
		list_zihangye.add(new Zihangye("体育工作者","2110100","211000"));
		list_zihangye.add(new Zihangye("记者","2120100","212000"));
		list_zihangye.add(new Zihangye("编辑","2120200","212000"));
		list_zihangye.add(new Zihangye("播音员及节目主持人","2120400","212000"));
		list_zihangye.add(new Zihangye("翻译","2120500","212000"));
		list_zihangye.add(new Zihangye("图书资料档案业务人员","2120600","212000"));
		list_zihangye.add(new Zihangye("其他新闻出版、文化工作者","2129900","212000"));
		list_zihangye.add(new Zihangye("行政业务人员","3010100","301000"));
		list_zihangye.add(new Zihangye("秘书、打字员","3010200","301000"));
		list_zihangye.add(new Zihangye("其他行政办公人员","3019900","301000"));
		list_zihangye.add(new Zihangye("治安保卫人员","3020200","302000"));
		list_zihangye.add(new Zihangye("其他安全保卫消防人员","3029900","302000"));
		list_zihangye.add(new Zihangye("邮政业务人员","3030100","303000"));
		list_zihangye.add(new Zihangye("电信业务人员、话务员","3030200","303000"));
		list_zihangye.add(new Zihangye("电信通信传输业务人员","3030300","303000"));
		list_zihangye.add(new Zihangye("其他邮政电信业务人员","3039900","303000"));
		list_zihangye.add(new Zihangye("营业人员、收银员","4010100","401000"));
		list_zihangye.add(new Zihangye("推销展销人员","4010200","401000"));
		list_zihangye.add(new Zihangye("采购人员","4010300","401000"));
		list_zihangye.add(new Zihangye("废旧物资回收利用人员","4010500","401000"));
		list_zihangye.add(new Zihangye("商品监督和市场管理员","4010700","401000"));
		list_zihangye.add(new Zihangye("医药商品购销员","4013000","401000"));
		list_zihangye.add(new Zihangye("其他购销人员","4019900","401000"));
		list_zihangye.add(new Zihangye("保管人员","4020100","402000"));
		list_zihangye.add(new Zihangye("储运人员","4020200","402000"));
		list_zihangye.add(new Zihangye("其他仓储人员","4029900","402000"));
		list_zihangye.add(new Zihangye("中餐烹饪人员","4030100","403000"));
		list_zihangye.add(new Zihangye("西餐烹饪人员","4030200","403000"));
		list_zihangye.add(new Zihangye("调酒和茶艺人员","4030300","403000"));
		list_zihangye.add(new Zihangye("餐厅服务员、厨工","4030500","403000"));
		list_zihangye.add(new Zihangye("其他餐饮服务人员","4039900","403000"));
		list_zihangye.add(new Zihangye("饭店服务人员","4040100","404000"));
		list_zihangye.add(new Zihangye("旅游游览场所服务员","4040200","404000"));
		list_zihangye.add(new Zihangye("健身娱乐场所服务员","4040300","404000"));
		list_zihangye.add(new Zihangye("其他旅游健身娱乐服务人员","4049900","404000"));
		list_zihangye.add(new Zihangye("公路运输服务人员","4050100","405000"));
		list_zihangye.add(new Zihangye("铁路运输服务人员","4050200","405000"));
		list_zihangye.add(new Zihangye("航空运输服务人员","4050300","405000"));
		list_zihangye.add(new Zihangye("水上运输服务人员","4050400","405000"));
		list_zihangye.add(new Zihangye("其他运输服务人员","4059900","405000"));
		list_zihangye.add(new Zihangye("护理人员","4060100","406000"));
		list_zihangye.add(new Zihangye("其他医疗卫生辅助服务人员","4069900","406000"));
		list_zihangye.add(new Zihangye("社会中介服务人员","4070100","407000"));
		list_zihangye.add(new Zihangye("物业管理人员","4070200","407000"));
		list_zihangye.add(new Zihangye("供水供热人员、锅炉工","4070300","407000"));
		list_zihangye.add(new Zihangye("美发美容人员","4070400","407000"));
		list_zihangye.add(new Zihangye("摄影服务人员","4070500","407000"));
		list_zihangye.add(new Zihangye("验光配镜人员","4070600","407000"));
		list_zihangye.add(new Zihangye("洗染、织补人员","4070700","407000"));
		list_zihangye.add(new Zihangye("浴池服务人员","4070800","407000"));
		list_zihangye.add(new Zihangye("家用机电产品维修人员","4071000","407000"));
		list_zihangye.add(new Zihangye("办公设备维修人员","4071100","407000"));
		list_zihangye.add(new Zihangye("保育、家庭服务员","4071200","407000"));
		list_zihangye.add(new Zihangye("清洁工","4071300","407000"));
		list_zihangye.add(new Zihangye("家庭教师","4073000","407000"));
		list_zihangye.add(new Zihangye("电梯工","4073100","407000"));
		list_zihangye.add(new Zihangye("其他社会服务人员","4079900","407000"));
		list_zihangye.add(new Zihangye("大田作物生产人员","5010100","501000"));
		list_zihangye.add(new Zihangye("农业实验人员","5010200","501000"));
		list_zihangye.add(new Zihangye("园艺作物生产人员","5010300","501000"));
		list_zihangye.add(new Zihangye("中药材生产人员","5010500","501000"));
		list_zihangye.add(new Zihangye("农副林特产品加工工","5010600","501000"));
		list_zihangye.add(new Zihangye("其他种植业生产人员","5019900","501000"));
		list_zihangye.add(new Zihangye("木材采运人员","5020400","502000"));
		list_zihangye.add(new Zihangye("其他林业及野生动植物保护人员","5029900","502000"));
		list_zihangye.add(new Zihangye("畜牧业生产人员","5039900","503000"));
		list_zihangye.add(new Zihangye("水产养殖人员","5040100","504000"));
		list_zihangye.add(new Zihangye("水产捕捞及有关人员","5040200","504000"));
		list_zihangye.add(new Zihangye("水产品加工工","5040300","504000"));
		list_zihangye.add(new Zihangye("其他渔业生产人员","5049900","504000"));
		list_zihangye.add(new Zihangye("水利设施管理养护人员","5059900","505000"));
		list_zihangye.add(new Zihangye("农林机械操作和能源开发人员","5090100","509000"));
		list_zihangye.add(new Zihangye("地质勘查人员","6010100","601000"));
		list_zihangye.add(new Zihangye("测绘人员","6010200","601000"));
		list_zihangye.add(new Zihangye("矿物开采工","6010300","601000"));
		list_zihangye.add(new Zihangye("矿物处理工","6010400","601000"));
		list_zihangye.add(new Zihangye("钻井人员","6010500","601000"));
		list_zihangye.add(new Zihangye("石油、天然气开采人员","6010600","601000"));
		list_zihangye.add(new Zihangye("盐业生产人员","6010700","601000"));
		list_zihangye.add(new Zihangye("其他勘测及矿物开采工","6019900","601000"));
		list_zihangye.add(new Zihangye("金属冶炼工","6020100","602000"));
		list_zihangye.add(new Zihangye("金属轧制工","6020800","602000"));
		list_zihangye.add(new Zihangye("其他金属冶炼、轧制工","6029900","602000"));
		list_zihangye.add(new Zihangye("化工产品生产工","6039900","603000"));
		list_zihangye.add(new Zihangye("机械冷加工工","6040100","604000"));
		list_zihangye.add(new Zihangye("车工","6040101","604000"));
		list_zihangye.add(new Zihangye("铣工","6040102","604000"));
		list_zihangye.add(new Zihangye("刨插工","6040103","604000"));
		list_zihangye.add(new Zihangye("磨工","6040104","604000"));
		list_zihangye.add(new Zihangye("镗工","6040105","604000"));
		list_zihangye.add(new Zihangye("钻床工","6040106","604000"));
		list_zihangye.add(new Zihangye("加工中心操作工","6040108","604000"));
		list_zihangye.add(new Zihangye("制齿工","6040109","604000"));
		list_zihangye.add(new Zihangye("机械热加工工","6040200","604000"));
		list_zihangye.add(new Zihangye("铸造工","6040201","604000"));
		list_zihangye.add(new Zihangye("锻造工","6040202","604000"));
		list_zihangye.add(new Zihangye("冲压工","6040203","604000"));
		list_zihangye.add(new Zihangye("焊工","6040205","604000"));
		list_zihangye.add(new Zihangye("金属热处理工","6040206","604000"));
		list_zihangye.add(new Zihangye("特种加工设备操作工","6040300","604000"));
		list_zihangye.add(new Zihangye("冷作钣金加工工","6040400","604000"));
		list_zihangye.add(new Zihangye("工件表面处理加工工","6040500","604000"));
		list_zihangye.add(new Zihangye("磨料磨具制造加工人员","6040600","604000"));
		list_zihangye.add(new Zihangye("其他机械制造加工工","6049900","604000"));
		list_zihangye.add(new Zihangye("机械设备装配工","6050200","605000"));
		list_zihangye.add(new Zihangye("电气电子设备装配工","6050400","605000"));
		list_zihangye.add(new Zihangye("仪器仪表装配工","6050600","605000"));
		list_zihangye.add(new Zihangye("运输车辆装配工","6050700","605000"));
		list_zihangye.add(new Zihangye("其他机电产品装配工","6059900","605000"));
		list_zihangye.add(new Zihangye("机械设备维修工","6060100","606000"));
		list_zihangye.add(new Zihangye("仪器仪表修理工","6060200","606000"));
		list_zihangye.add(new Zihangye("其他机械设备修理工","6069900","606000"));
		list_zihangye.add(new Zihangye("电力设备安装工","6070100","607000"));
		list_zihangye.add(new Zihangye("专业电力设备检修工","6070400","607000"));
		list_zihangye.add(new Zihangye("普通电力设备检修工、电工","6070600","607000"));
		list_zihangye.add(new Zihangye("其他电力设备装运、检修及供电工","6079900","607000"));
		list_zihangye.add(new Zihangye("电子元件、器件制造工","6080100","608000"));
		list_zihangye.add(new Zihangye("电子设备装配调试工","6080400","608000"));
		list_zihangye.add(new Zihangye("电子产品维修工","6080500","608000"));
		list_zihangye.add(new Zihangye("其他电子元器件与电子设备制造、装调维修工","6089900","608000"));
		list_zihangye.add(new Zihangye("橡胶制品生产工","6090100","609000"));
		list_zihangye.add(new Zihangye("塑料制品加工工","6090200","609000"));
		list_zihangye.add(new Zihangye("其他橡胶和塑料制品生产工","6099900","609000"));
		list_zihangye.add(new Zihangye("纤维预处理人员","6100100","610000"));
		list_zihangye.add(new Zihangye("纺纱人员","6100200","610000"));
		list_zihangye.add(new Zihangye("织造人员","6100300","610000"));
		list_zihangye.add(new Zihangye("针织人员","6100400","610000"));
		list_zihangye.add(new Zihangye("印染人员","6100500","610000"));
		list_zihangye.add(new Zihangye("纺织、针织、印染工","6109900","610000"));
		list_zihangye.add(new Zihangye("裁剪缝纫工","6110100","611000"));
		list_zihangye.add(new Zihangye("鞋帽制作工","6110200","611000"));
		list_zihangye.add(new Zihangye("皮革、毛皮加工工","6110300","611000"));
		list_zihangye.add(new Zihangye("缝纫制品再加工人员","6110400","611000"));
		list_zihangye.add(new Zihangye("其他裁剪缝纫和毛皮革制作工","6119900","611000"));
		list_zihangye.add(new Zihangye("粮油食品饮料生产加工及饲料加工工","6129900","612000"));
		list_zihangye.add(new Zihangye("烟草制品加工工","6139900","613000"));
		list_zihangye.add(new Zihangye("药品生产制造工","6149900","614000"));
		list_zihangye.add(new Zihangye("木材加工制作工","6150100","615000"));
		list_zihangye.add(new Zihangye("其他木材加工、人造板生产及木制品制作工","6159900","615000"));
		list_zihangye.add(new Zihangye("纸制品制作工","6160300","616000"));
		list_zihangye.add(new Zihangye("其他制浆、造纸和纸制品生产加工工","6169900","616000"));
		list_zihangye.add(new Zihangye("建筑材料生产加工工","6179900","617000"));
		list_zihangye.add(new Zihangye("玻璃陶瓷搪瓷生产加工工","6189900","618000"));
		list_zihangye.add(new Zihangye("广播影视设备操作人员","6190300","619000"));
		list_zihangye.add(new Zihangye("其他广播影视品制作播放及文物保护人员","6199900","619000"));
		list_zihangye.add(new Zihangye("制版印刷人员","6209900","620000"));
		list_zihangye.add(new Zihangye("工艺、美术品制作人员","6219900","621000"));
		list_zihangye.add(new Zihangye("文体用品乐器制作人员","6229900","622000"));
		list_zihangye.add(new Zihangye("土石方施工人员","6230100","623000"));
		list_zihangye.add(new Zihangye("砌筑工","6230200","623000"));
		list_zihangye.add(new Zihangye("混凝土工","6230300","623000"));
		list_zihangye.add(new Zihangye("钢筋工","6230400","623000"));
		list_zihangye.add(new Zihangye("架子工","6230500","623000"));
		list_zihangye.add(new Zihangye("工程防水工","6230600","623000"));
		list_zihangye.add(new Zihangye("装饰、装修、油漆工","6230700","623000"));
		list_zihangye.add(new Zihangye("筑路、养护、维修人员","6230900","623000"));
		list_zihangye.add(new Zihangye("机械电气工程设备安装工、管工","6231000","623000"));
		list_zihangye.add(new Zihangye("电工","6231100","623000"));
		list_zihangye.add(new Zihangye("木工","6233000","623000"));
		list_zihangye.add(new Zihangye("其他工程施工人员","6239900","623000"));
		list_zihangye.add(new Zihangye("机动车驾驶员","6240100","624000"));
		list_zihangye.add(new Zihangye("铁路、地铁运输设备操作及有关人员","6240200","624000"));
		list_zihangye.add(new Zihangye("船舶水手","6240400","624000"));
		list_zihangye.add(new Zihangye("起重装卸机械操作工","6240500","624000"));
		list_zihangye.add(new Zihangye("其他运输设备操作人员","6249900","624000"));
		list_zihangye.add(new Zihangye("环境监测废物处理人员","6259900","625000"));
		list_zihangye.add(new Zihangye("检验、计量人员","6269900","626000"));
		list_zihangye.add(new Zihangye("体力工人","6279900","627000"));
		return list_zihangye;
		
	}
	
}

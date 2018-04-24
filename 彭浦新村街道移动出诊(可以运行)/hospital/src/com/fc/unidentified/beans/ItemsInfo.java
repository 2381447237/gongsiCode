package com.fc.unidentified.beans;

import java.io.Serializable;


public class ItemsInfo implements Serializable  {
	private int ItemAmount;
	private double ItemPrice;
	private int PrescribeId;
	private int PatientId;
	private int PrescribeDoctorId;
	private String PrescribeDoctorName;
	private String PrescribeDate;
	private double TotalCosts;
	private boolean IsPayed;
	private String IsPayedName;
	private int PrescribeDetailId;
	private int ItemOrderId;
	private String ItemOutpUnit;
	private String id;
	private int ItemId;
	private String ItemName;
	private String InputCode;
	private String InputCode1;
	private String InputCode2;
	private String ItemOutpSpec;
	private String ItemOutpUnits;
	private String ItemBasicUnits;
	//private String ItemPrice;
	private String ItemPropertyIndicator;
	private String ItemPropertyName;
	private String DrugOrItemIndicator;
	private String DrugOrItemIndicatorName;
	private String Quantity;
	private String ItemClass;
	private String ItemClassName;
	private String cdname;
	private String itemtradename;
	private String DoctorOrders;
	private String ItemFrequency;
	private String ItemDosage;
	
	private String ItemDosageUnit;
	private String ItemUsage;
	private String num;//药品金额数量
	private String freque;//频率
	private String usage;//用法
	private String much;//用量
	private boolean IsDrug;
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
	
	
	public String getItemDosageUnit() {
		return ItemDosageUnit;
	}
	public void setItemDosageUnit(String itemDosageUnit) {
		ItemDosageUnit = itemDosageUnit;
	}
	public ItemsInfo(int itemAmount, double itemPrice, int prescribeId,
			int patientId, int prescribeDoctorId, String prescribeDoctorName,
			String prescribeDate, double totalCosts, boolean isPayed,
			String isPayedName, int prescribeDetailId, int itemOrderId,
			String itemOutpUnit, String id, int itemId, String itemName,
			String inputCode, String inputCode1, String inputCode2,
			String itemOutpSpec, String itemOutpUnits, String itemBasicUnits,
			String itemPropertyIndicator, String itemPropertyName,
			String drugOrItemIndicator, String drugOrItemIndicatorName,
			String quantity, String itemClass, String itemClassName,
			String cdname, String itemtradename, String doctorOrders,
			String itemFrequency, String itemDosage, String itemDosageUnit,
			String itemUsage, String num, String freque, String usage,
			String much, boolean isDrug) {
		super();
		ItemAmount = itemAmount;
		ItemPrice = itemPrice;
		PrescribeId = prescribeId;
		PatientId = patientId;
		PrescribeDoctorId = prescribeDoctorId;
		PrescribeDoctorName = prescribeDoctorName;
		PrescribeDate = prescribeDate;
		TotalCosts = totalCosts;
		IsPayed = isPayed;
		IsPayedName = isPayedName;
		PrescribeDetailId = prescribeDetailId;
		ItemOrderId = itemOrderId;
		ItemOutpUnit = itemOutpUnit;
		this.id = id;
		ItemId = itemId;
		ItemName = itemName;
		InputCode = inputCode;
		InputCode1 = inputCode1;
		InputCode2 = inputCode2;
		ItemOutpSpec = itemOutpSpec;
		ItemOutpUnits = itemOutpUnits;
		ItemBasicUnits = itemBasicUnits;
		ItemPropertyIndicator = itemPropertyIndicator;
		ItemPropertyName = itemPropertyName;
		DrugOrItemIndicator = drugOrItemIndicator;
		DrugOrItemIndicatorName = drugOrItemIndicatorName;
		Quantity = quantity;
		ItemClass = itemClass;
		ItemClassName = itemClassName;
		this.cdname = cdname;
		this.itemtradename = itemtradename;
		DoctorOrders = doctorOrders;
		ItemFrequency = itemFrequency;
		ItemDosage = itemDosage;
		ItemDosageUnit = itemDosageUnit;
		ItemUsage = itemUsage;
		this.num = num;
		this.freque = freque;
		this.usage = usage;
		this.much = much;
		IsDrug = isDrug;
	}
	public boolean getIsDrug() {
		return IsDrug;
	}
	public void setIsDrug(boolean isDrug) {
		IsDrug = isDrug;
	}
	public double getItemPrice1() {
		return ItemPrice;
	}
	public void setItemPrice1(double itemPrice1) {
		this.ItemPrice = itemPrice1;
	}
	public int getItemAmount() {
		return ItemAmount;
	}
	public void setItemAmount(int itemAmount) {
		ItemAmount = itemAmount;
	}
	public int getPrescribeId() {
		return PrescribeId;
	}
	public void setPrescribeId(int prescribeId) {
		PrescribeId = prescribeId;
	}
	public int getPatientId() {
		return PatientId;
	}
	public void setPatientId(int patientId) {
		PatientId = patientId;
	}
	public int getPrescribeDoctorId() {
		return PrescribeDoctorId;
	}
	public void setPrescribeDoctorId(int prescribeDoctorId) {
		PrescribeDoctorId = prescribeDoctorId;
	}
	public String getPrescribeDoctorName() {
		return PrescribeDoctorName;
	}
	public void setPrescribeDoctorName(String prescribeDoctorName) {
		PrescribeDoctorName = prescribeDoctorName;
	}
	public String getPrescribeDate() {
		return PrescribeDate;
	}
	public void setPrescribeDate(String prescribeDate) {
		PrescribeDate = prescribeDate;
	}
	public double getTotalCosts() {
		return TotalCosts;
	}
	public void setTotalCosts(double totalCosts) {
		TotalCosts = totalCosts;
	}
	public boolean isIsPayed() {
		return IsPayed;
	}
	public void setIsPayed(boolean isPayed) {
		IsPayed = isPayed;
	}
	public String getIsPayedName() {
		return IsPayedName;
	}
	public void setIsPayedName(String isPayedName) {
		IsPayedName = isPayedName;
	}
	public int getPrescribeDetailId() {
		return PrescribeDetailId;
	}
	public void setPrescribeDetailId(int prescribeDetailId) {
		PrescribeDetailId = prescribeDetailId;
	}
	public int getItemOrderId() {
		return ItemOrderId;
	}
	public void setItemOrderId(int itemOrderId) {
		ItemOrderId = itemOrderId;
	}
	public String getItemOutpUnit() {
		return ItemOutpUnit;
	}
	public void setItemOutpUnit(String itemOutpUnit) {
		ItemOutpUnit = itemOutpUnit;
	}
	
	public String getFreque() {
		return freque;
	}
	public void setFreque(String freque) {
		this.freque = freque;
	}
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	public String getMuch() {
		return much;
	}
	public void setMuch(String much) {
		this.much = much;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getItemId() {
		return ItemId;
	}
	public void setItemId(int itemId) {
		ItemId = itemId;
	}
	
	public String getItemName() {
		return ItemName;
	}
	public void setItemName(String itemName) {
		ItemName = itemName;
	}
	public String getInputCode() {
		return InputCode;
	}
	public void setInputCode(String inputCode) {
		InputCode = inputCode;
	}
	public String getInputCode1() {
		return InputCode1;
	}
	public void setInputCode1(String inputCode1) {
		InputCode1 = inputCode1;
	}
	public String getInputCode2() {
		return InputCode2;
	}
	public void setInputCode2(String inputCode2) {
		InputCode2 = inputCode2;
	}
	public String getItemOutpSpec() {
		return ItemOutpSpec;
	}
	public void setItemOutpSpec(String itemOutpSpec) {
		ItemOutpSpec = itemOutpSpec;
	}
	public String getItemOutpUnits() {
		return ItemOutpUnits;
	}
	public void setItemOutpUnits(String itemOutpUnits) {
		ItemOutpUnits = itemOutpUnits;
	}
	public String getItemBasicUnits() {
		return ItemBasicUnits;
	}
	public void setItemBasicUnits(String itemBasicUnits) {
		ItemBasicUnits = itemBasicUnits;
	}
//	public String getItemPrice() {
//		return ItemPrice;
//	}
//	public void setItemPrice(String itemPrice) {
//		ItemPrice = itemPrice;
//	}
	public String getItemPropertyIndicator() {
		return ItemPropertyIndicator;
	}
	public void setItemPropertyIndicator(String itemPropertyIndicator) {
		ItemPropertyIndicator = itemPropertyIndicator;
	}
	public String getItemPropertyName() {
		return ItemPropertyName;
	}
	public void setItemPropertyName(String itemPropertyName) {
		ItemPropertyName = itemPropertyName;
	}
	public String getDrugOrItemIndicator() {
		return DrugOrItemIndicator;
	}
	public void setDrugOrItemIndicator(String drugOrItemIndicator) {
		DrugOrItemIndicator = drugOrItemIndicator;
	}
	public String getDrugOrItemIndicatorName() {
		return DrugOrItemIndicatorName;
	}
	public void setDrugOrItemIndicatorName(String drugOrItemIndicatorName) {
		DrugOrItemIndicatorName = drugOrItemIndicatorName;
	}
	public String getQuantity() {
		return Quantity;
	}
	public void setQuantity(String quantity) {
		Quantity = quantity;
	}
	public String getItemClass() {
		return ItemClass;
	}
	public void setItemClass(String itemClass) {
		ItemClass = itemClass;
	}
	public String getItemClassName() {
		return ItemClassName;
	}
	public void setItemClassName(String itemClassName) {
		ItemClassName = itemClassName;
	}
	public String getCdname() {
		return cdname;
	}
	public void setCdname(String cdname) {
		this.cdname = cdname;
	}
	public String getItemtradename() {
		return itemtradename;
	}
	public void setItemtradename(String itemtradename) {
		this.itemtradename = itemtradename;
	}

	
	public double getItemPrice() {
		return ItemPrice;
	}
	public void setItemPrice(double itemPrice) {
		ItemPrice = itemPrice;
	}
	public String getItemFrequency() {
		return ItemFrequency;
	}
	public void setItemFrequency(String itemFrequency) {
		ItemFrequency = itemFrequency;
	}
	public String getItemDosage() {
		return ItemDosage;
	}
	public void setItemDosage(String itemDosage) {
		ItemDosage = itemDosage;
	}
	public String getItemUsage() {
		return ItemUsage;
	}
	public void setItemUsage(String itemUsage) {
		ItemUsage = itemUsage;
	}
	public String getDoctorOrders() {
		return DoctorOrders;
	}
	public void setDoctorOrders(String doctorOrders) {
		DoctorOrders = doctorOrders;
	}
	public ItemsInfo(String id,int ItemId,String ItemName,String InputCode,String InputCode1
			,String InputCode2,String ItemOutpSpec,String ItemOutpUnits
			,String ItemBasicUnits,String ItemPrice,String ItemPropertyIndicator 
			,String ItemPropertyName,String DrugOrItemIndicator,String DrugOrItemIndicatorName
			, String Quantity,String ItemClass, String ItemClassName
			,String cdname,String itemtradename
			,String num,String freque,String usage,String much,String DoctorOrders){
		this.id=id;
		this.ItemId=ItemId;
		this.ItemName=ItemName;
		this.InputCode=InputCode;
		this.InputCode1=InputCode1;
		this.InputCode2=InputCode2;
		this.ItemOutpSpec=ItemOutpSpec;
		this.ItemOutpUnits=ItemOutpUnits;
		this.ItemBasicUnits=ItemBasicUnits;
//		this.ItemPrice=ItemPrice;
		this.ItemPropertyIndicator=ItemPropertyIndicator;
		this.ItemPropertyName=ItemPropertyName;
		this.DrugOrItemIndicator=DrugOrItemIndicator;
		this.DrugOrItemIndicatorName=DrugOrItemIndicatorName;
		this.Quantity=Quantity;
		this.ItemClass=ItemClass;
		this.ItemClassName=ItemClassName;
		this.cdname=cdname;
		this.itemtradename=itemtradename;
		this.num=num;
		this.freque=freque;
		this.usage=usage;
		this.much=much;
		this.DoctorOrders=DoctorOrders;
	}
	
	
	public ItemsInfo(int itemAmount, double itemPrice, int prescribeId,
			int patientId, int prescribeDoctorId, String prescribeDoctorName,
			String prescribeDate, double totalCosts, boolean isPayed,
			String isPayedName, int prescribeDetailId, int itemOrderId,
			String itemOutpUnit, String id, int itemId, String itemName,
			String inputCode, String inputCode1, String inputCode2,
			String itemOutpSpec, String itemOutpUnits, String itemBasicUnits,
			String itemPropertyIndicator, String itemPropertyName,
			String drugOrItemIndicator, String drugOrItemIndicatorName,
			String quantity, String itemClass, String itemClassName,
			String cdname, String itemtradename, String doctorOrders,
			String itemFrequency, String itemDosage, String itemUsage,
			String num, String freque, String usage, String much, boolean isDrug) {
		super();
		ItemAmount = itemAmount;
		ItemPrice = itemPrice;
		PrescribeId = prescribeId;
		PatientId = patientId;
		PrescribeDoctorId = prescribeDoctorId;
		PrescribeDoctorName = prescribeDoctorName;
		PrescribeDate = prescribeDate;
		TotalCosts = totalCosts;
		IsPayed = isPayed;
		IsPayedName = isPayedName;
		PrescribeDetailId = prescribeDetailId;
		ItemOrderId = itemOrderId;
		ItemOutpUnit = itemOutpUnit;
		this.id = id;
		ItemId = itemId;
		ItemName = itemName;
		InputCode = inputCode;
		InputCode1 = inputCode1;
		InputCode2 = inputCode2;
		ItemOutpSpec = itemOutpSpec;
		ItemOutpUnits = itemOutpUnits;
		ItemBasicUnits = itemBasicUnits;
		ItemPropertyIndicator = itemPropertyIndicator;
		ItemPropertyName = itemPropertyName;
		DrugOrItemIndicator = drugOrItemIndicator;
		DrugOrItemIndicatorName = drugOrItemIndicatorName;
		Quantity = quantity;
		ItemClass = itemClass;
		ItemClassName = itemClassName;
		this.cdname = cdname;
		this.itemtradename = itemtradename;
		DoctorOrders = doctorOrders;
		ItemFrequency = itemFrequency;
		ItemDosage = itemDosage;
		ItemUsage = itemUsage;
		this.num = num;
		this.freque = freque;
		this.usage = usage;
		this.much = much;
		IsDrug = isDrug;
	}
	public ItemsInfo(int itemAmount, double itemPrice, int prescribeId,
			int patientId, int prescribeDoctorId, String prescribeDoctorName,
			String prescribeDate, double totalCosts, boolean isPayed,
			String isPayedName, int prescribeDetailId, int itemOrderId,
			String itemOutpUnit, String id, int itemId, String itemName,
			String inputCode, String inputCode1, String inputCode2,
			String itemOutpSpec, String itemOutpUnits, String itemBasicUnits,
			String itemPrice2, String itemPropertyIndicator,
			String itemPropertyName, String drugOrItemIndicator,
			String drugOrItemIndicatorName, String quantity, String itemClass,
			String itemClassName, String cdname, String itemtradename,
			String doctorOrders, String num, String freque, String usage,
			String much) {
		super();
		ItemAmount = itemAmount;
		this.ItemPrice = itemPrice;
		PrescribeId = prescribeId;
		PatientId = patientId;
		PrescribeDoctorId = prescribeDoctorId;
		PrescribeDoctorName = prescribeDoctorName;
		PrescribeDate = prescribeDate;
		TotalCosts = totalCosts;
		IsPayed = isPayed;
		IsPayedName = isPayedName;
		PrescribeDetailId = prescribeDetailId;
		ItemOrderId = itemOrderId;
		ItemOutpUnit = itemOutpUnit;
		this.id = id;
		ItemId = itemId;
		ItemName = itemName;
		InputCode = inputCode;
		InputCode1 = inputCode1;
		InputCode2 = inputCode2;
		ItemOutpSpec = itemOutpSpec;
		ItemOutpUnits = itemOutpUnits;
		ItemBasicUnits = itemBasicUnits;
//		ItemPrice = itemPrice2;
		ItemPropertyIndicator = itemPropertyIndicator;
		ItemPropertyName = itemPropertyName;
		DrugOrItemIndicator = drugOrItemIndicator;
		DrugOrItemIndicatorName = drugOrItemIndicatorName;
		Quantity = quantity;
		ItemClass = itemClass;
		ItemClassName = itemClassName;
		this.cdname = cdname;
		this.itemtradename = itemtradename;
		DoctorOrders = doctorOrders;
		this.num = num;
		this.freque = freque;
		this.usage = usage;
		this.much = much;
	}
	public ItemsInfo(){
		super();
	}
}

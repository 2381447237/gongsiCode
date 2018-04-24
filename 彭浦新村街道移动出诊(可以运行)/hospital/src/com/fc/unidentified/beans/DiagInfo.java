package com.fc.unidentified.beans;

import java.io.Serializable;

public class DiagInfo implements Serializable{
	
	private String DiagName;
	//private String id;
	private String DiagCode;
	private String InputCode;
	private String InputCode1;
	private String InputCode2;

	public String getDiagName() {
		return DiagName;
	}
	
	 @Override
	 public boolean equals(Object o) {
		 DiagInfo d =((DiagInfo)o);
		 
		 return d.DiagCode.equals(this.DiagCode);
	
	 };
	public void setDiagName(String diagName) {
		DiagName = diagName;
	}
	
	/*public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}*/

	public String getDiagCode() {
		return DiagCode;
	}

	public void setDiagCode(String diagCode) {
		DiagCode = diagCode;
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

	public DiagInfo (String DiagName,String DiagCode
			,String InputCode,String InputCode1,String InputCode2){
		super();
		this.DiagName=DiagName;
		//this.id=id;
		this.DiagCode=DiagCode;
		this.InputCode=InputCode;
		this.InputCode1=InputCode1;
		this.InputCode2=InputCode2;
	}
	
	public DiagInfo(){
		super();//sf
	}
	@Override
	public String toString() {
		return "DiagInfo [DiagName=" + DiagName +", DiagCode=" + DiagCode 
				 +", InputCode=" + InputCode +", InputCode1=" + InputCode1 +", InputCode2=" + InputCode2+"]";
	}
}

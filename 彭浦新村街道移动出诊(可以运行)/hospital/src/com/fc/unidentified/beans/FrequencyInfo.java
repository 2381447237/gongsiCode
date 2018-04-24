package com.fc.unidentified.beans;

import java.io.Serializable;

public class FrequencyInfo implements Serializable{
	private int FreqId;
	private String FreqDesc;
	private String FreqCode;
	
	

	public FrequencyInfo(){
		super();
	}

	public FrequencyInfo(int FreqId,String FreqDesc,String FreqCode){
		super();
		this.FreqId=FreqId;
		this.FreqDesc=FreqDesc;
		this.FreqCode=FreqCode;
	}
	public int getFreqId() {
		return FreqId;
	}
	public void setFreqId(int freqId) {
		FreqId = freqId;
	}
	public String getFreqDesc() {
		return FreqDesc;
	}
	public void setFreqDesc(String freqDesc) {
		FreqDesc = freqDesc;
	}
	public String getFreqCode() {
		return FreqCode;
	}
	public void setFreqCode(String freqCode) {
		FreqCode = freqCode;
	}
	
	
	
	@Override
	public String toString() {
		return FreqDesc;
	}
}

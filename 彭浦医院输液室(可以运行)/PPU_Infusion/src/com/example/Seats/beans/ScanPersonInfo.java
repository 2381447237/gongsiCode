package com.example.Seats.beans;

//http://192.168.11.11:8088/json/GetQrCodeBase.aspx?QRCODE=20170103000008

//{"NAME_CARD":null,"HISORDERID":0,"CARDNO":"H0208773X","PATIENTNAME":"�ž���","GENDER":"1","BIRTHDATE":"1944-06-29T00:00:00","ORDERDATE":"2017-01-03T00:00:00",
//"ORDERDOCTORNAME":"����","ORDERDEPTNAME":"�ڿ�","DIAGINFO":"��Ѫѹ����","HISCLINICNO":null,"RecordCount":0,"SEATNO":"008","PATIENTID":2}

public class ScanPersonInfo {

 public String	NAME_CARD;
 public int	HISORDERID;
 public String	CARDNO;
 public String	PATIENTNAME;
 public String	GENDER;
 public String	BIRTHDATE;
 public String	ORDERDATE;
 public String	ORDERDOCTORNAME;
 public String	ORDERDEPTNAME;
 public String	DIAGINFO;
 public String	HISCLINICNO;
 public int	RecordCount;
 public String SEATNO;
 public int PATIENTID;
}

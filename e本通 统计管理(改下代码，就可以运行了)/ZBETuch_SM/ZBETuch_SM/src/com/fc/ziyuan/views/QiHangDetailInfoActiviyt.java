package com.fc.ziyuan.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fc.zbetuch_sm.R;
import com.fc.ziyuan.bean.QiHangBeanInfo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class QiHangDetailInfoActiviyt extends Activity{
	
	private QiHangBeanInfo qiHangBeanInfo;
	
	private EditText name,sex,sfz,born,tel,hkqx,mz,jg,age,whcd,hkjd,hkjw,fwdqx,fwdxxdz,fwdjd,
	fwdjw,jyzt,byrq,ldsc,dlzp,stldstartime,stldendtime,jylx,jysydjbh,jyfw1,jyfw2,jyfw3,
	xwcsgzlx1,xwcsgzlx2,xwcsgzlx3,zyzd,zyjx,zypx,cyzx,cyjx,
	wdjyyy,qwyx,mqzk,dqyx,zjzd,xwjspx1,xwjspx2,xwjspx3;
	private TextView mark;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
	private int bornYear=0;
	private int nowYear=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.qihang_detail_info);
		qiHangBeanInfo=(QiHangBeanInfo) getIntent().getSerializableExtra("infos");
		bornYear=Integer.parseInt(qiHangBeanInfo.getCSRQ().substring(0, 4));
		nowYear=Integer.parseInt(sdf.format(new Date()));
		initView();
		initValue();
	}
	
	private void initView(){
		name=(EditText) this.findViewById(R.id.name);
		sex=(EditText) this.findViewById(R.id.sex);
		sfz=(EditText) this.findViewById(R.id.sfz);
		born=(EditText) this.findViewById(R.id.addpersonborn);
		tel=(EditText) this.findViewById(R.id.tel);
		hkqx=(EditText) this.findViewById(R.id.hkqx);
		mz=(EditText) this.findViewById(R.id.addpersonnational);
		jg=(EditText) this.findViewById(R.id.addpersonnative);
		age=(EditText) this.findViewById(R.id.age);
		whcd=(EditText) this.findViewById(R.id.addpersonintention);
		hkjd=(EditText) this.findViewById(R.id.hkjd);
		hkjw=(EditText) this.findViewById(R.id.hkjw);
		fwdqx=(EditText) this.findViewById(R.id.fwdqx);
		fwdxxdz=(EditText) this.findViewById(R.id.fwdxxdz);
		fwdjd=(EditText) this.findViewById(R.id.fwdjd);
		fwdjw=(EditText) this.findViewById(R.id.fwdjw);
		jyzt=(EditText) this.findViewById(R.id.jyzt);
		byrq=(EditText) this.findViewById(R.id.byrq);
		ldsc=(EditText) this.findViewById(R.id.sfffldsc);
		dlzp=(EditText) this.findViewById(R.id.dlzp);
		stldstartime=(EditText) this.findViewById(R.id.stldjlksrq);
		stldendtime=(EditText) this.findViewById(R.id.stldjljsrq);
		jylx=(EditText) this.findViewById(R.id.jylx);
		jysydjbh=(EditText) this.findViewById(R.id.jysydjzbh);
		jyfw1=(EditText) this.findViewById(R.id.jyfw1);
		jyfw2=(EditText) this.findViewById(R.id.jyfw2);
		jyfw3=(EditText) this.findViewById(R.id.jyfw3);
		xwcsgzlx1=(EditText) this.findViewById(R.id.xwgzlx1);
		xwcsgzlx2=(EditText) this.findViewById(R.id.xwgzlx2);
		xwcsgzlx3=(EditText) this.findViewById(R.id.xwgzlx3);
		xwjspx1=(EditText) this.findViewById(R.id.px1);
		xwjspx2=(EditText) this.findViewById(R.id.px2);
		xwjspx3=(EditText) this.findViewById(R.id.px3);
		zyzd=(EditText) this.findViewById(R.id.zyzd);
		zyjx=(EditText) this.findViewById(R.id.zyjx);
		zypx=(EditText) this.findViewById(R.id.zypx);
		cyzx=(EditText) this.findViewById(R.id.cyzx);
		cyjx=(EditText) this.findViewById(R.id.cyjx);
		wdjyyy=(EditText) this.findViewById(R.id.wqjyyy);
		qwyx=(EditText) this.findViewById(R.id.qwyx);
		mqzk=(EditText) this.findViewById(R.id.mqzk);
		dqyx=(EditText) this.findViewById(R.id.dqyx);
		zjzd=(EditText) this.findViewById(R.id.zjzd);
		mark=(TextView) this.findViewById(R.id.mark);
	}
	
	private void initValue(){
		name.setText(qiHangBeanInfo.getNAME());
		sex.setText(qiHangBeanInfo.getSEX());
		sfz.setText(qiHangBeanInfo.getSFZ());
		born.setText(qiHangBeanInfo.getCSRQ().substring(0, 10));
		tel.setText(qiHangBeanInfo.getPHONE());
		hkqx.setText(qiHangBeanInfo.getHKQX());
		mz.setText(qiHangBeanInfo.getMZ());
		jg.setText(qiHangBeanInfo.getHKDZ());
		age.setText((nowYear-bornYear)+"岁");
		whcd.setText(qiHangBeanInfo.getEDU());
		hkjd.setText(qiHangBeanInfo.getJD());
		hkjw.setText(qiHangBeanInfo.getJW());
		fwdqx.setText(qiHangBeanInfo.getFWDQ());
		fwdxxdz.setText(qiHangBeanInfo.getFWDQ());
		fwdjd.setText(qiHangBeanInfo.getFWJD());
		fwdjw.setText(qiHangBeanInfo.getFWJW());
		jyzt.setText(qiHangBeanInfo.getJYZT());
		byrq.setText(qiHangBeanInfo.getBYRQ().substring(0, 10));
		ldsc.setText(qiHangBeanInfo.getLDSC());
		dlzp.setText(qiHangBeanInfo.getDLZP());
		stldstartime.setText(qiHangBeanInfo.getSTLD_START());
		stldendtime.setText(qiHangBeanInfo.getSTLD_END());
		jylx.setText(qiHangBeanInfo.getJYLX());
		jysydjbh.setText(qiHangBeanInfo.getSY_NO());
		jyfw1.setText(qiHangBeanInfo.getJYFW1());
		jyfw2.setText(qiHangBeanInfo.getJYFW2());
		jyfw3.setText(qiHangBeanInfo.getJYFW3());
		xwcsgzlx1.setText(qiHangBeanInfo.getXWCS1());
		xwcsgzlx2.setText(qiHangBeanInfo.getXWCS2());
		xwcsgzlx3.setText(qiHangBeanInfo.getXWCS3());
		xwjspx1.setText(qiHangBeanInfo.getXWPX1());
		xwjspx2.setText(qiHangBeanInfo.getXWPX2());
		xwjspx3.setText(qiHangBeanInfo.getXWPX3());
		zyzd.setText(qiHangBeanInfo.getZYZD());
		zyjx.setText(qiHangBeanInfo.getZYJX());
		zypx.setText(qiHangBeanInfo.getZYPX());
		cyzx.setText(qiHangBeanInfo.getCYZX());
		cyjx.setText(qiHangBeanInfo.getCYZX());
		wdjyyy.setText(qiHangBeanInfo.getWQJY());
		qwyx.setText(qiHangBeanInfo.getXWYX());
		mqzk.setText(qiHangBeanInfo.getNEW_MQZK());
		dqyx.setText(qiHangBeanInfo.getNEW_DQYX()+"");
		zjzd.setText(qiHangBeanInfo.getZJZD());
		mark.setText(qiHangBeanInfo.getMARK());
	}

}

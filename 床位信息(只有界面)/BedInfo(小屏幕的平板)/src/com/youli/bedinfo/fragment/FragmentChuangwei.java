package com.youli.bedinfo.fragment;

import com.youli.bedinfo.R;
import com.youli.bedinfo.custom.AutoSplitTextView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.res.Resources.Theme;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Telephony.Sms.Conversations;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentChuangwei extends Fragment implements OnClickListener {

	private View contentView;

	private TextView chuangwei_tv3, tv_info10, tv_info1;

	private RelativeLayout chuangwei_rl2, chuangwei_rl3, chuangwei_rl4;

	private int doctorType = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		contentView = inflater.inflate(R.layout.fragment_chuangwei, container,
				false);

		tv_info10 = (TextView) contentView.findViewById(R.id.tv_info10);
		tv_info1 = (TextView) contentView.findViewById(R.id.tv_info1);

		tv_info10.setFocusable(true);
		tv_info10.requestFocus();

		chuangwei_rl2 = (RelativeLayout) contentView
				.findViewById(R.id.chuangwei_rl2);
		chuangwei_rl2.setOnClickListener(this);
		chuangwei_rl3 = (RelativeLayout) contentView
				.findViewById(R.id.chuangwei_rl3);
		chuangwei_rl3.setOnClickListener(this);
		chuangwei_rl4 = (RelativeLayout) contentView
				.findViewById(R.id.chuangwei_rl4);
		chuangwei_rl4.setOnClickListener(this);

		return contentView;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.chuangwei_rl2:
			doctorType = 1;
			showDoctor();

			break;
		case R.id.chuangwei_rl3:
			doctorType = 2;
			showDoctor();

			break;
		case R.id.chuangwei_rl4:
			doctorType = 3;
			showDoctor();

			break;

		default:
			break;
		}

	}

	// ����ҽ���ĶԻ���
	@SuppressLint("NewApi")
	private void showDoctor() {
		//AlertDialog.THEME_HOLO_LIGHT,AlertDialog.THEME_TRADITIONAL
		AlertDialog dialog = new AlertDialog.Builder(getActivity(),AlertDialog.THEME_TRADITIONAL).create();

		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.doctor_introduce, null);

		dialog.setView(view);
		dialog.show();
		Window window = dialog.getWindow();
		window.setContentView(R.layout.doctor_introduce);

		AutoSplitTextView content = (AutoSplitTextView) window
				.findViewById(R.id.doctor_introduce_content);
		TextView name = (TextView) window
				.findViewById(R.id.doctor_introduce_name);
		TextView job = (TextView) window
				.findViewById(R.id.doctor_introduce_job);

		switch (doctorType) {
		case 1:
            name.setText("������");
            job.setText("���/����ҽ�� ");
			content.setText("\r\r\r\r\r\r\r��������ƽҽԺ���ѧ�ƴ�ͷ��,��ҵ�ڹ�����ְ��ҽѧԺ,�����ڱ����վ�466ҽԺ,��������ٴ�������40��,�����⡢�����⡢����ȳ��������෢���ж��ص���������Ʒ���.����ḻ,������տ,������Чȷ��,������졢׼ȷ,�Ǻ�����ʡ�������ר��֮һ,������߳������,�ܵ�ҽ�Ƹ�����ʿ�ĺ���,�ڹ��Ҽ�ʡ�ڼ����﷢��ҽѧ����40��ƪ��" +
					"��������ƽҽԺ���ѧ�ƴ�ͷ��,��ҵ�ڹ�����ְ��ҽѧԺ,�����ڱ����վ�466ҽԺ,��������ٴ�������40��,�����⡢�����⡢����ȳ��������෢���ж��ص���������Ʒ���.����ḻ,������տ,������Чȷ��,������졢׼ȷ,�Ǻ�����ʡ�������ר��֮һ,������߳������,�ܵ�ҽ�Ƹ�����ʿ�ĺ���,�ڹ��Ҽ�ʡ�ڼ����﷢��ҽѧ����40��ƪ��");
            
			break;

		case 2:
			name.setText("������");
			job.setText("�����/����ҽ��");
			content.setText("\r\r\r\r\r\r\r��������ƽҽԺ��ϯ�߼�����ʦ,ʡ����������֪��Ȩ��,��ҵ�ڹ�����ҽ�ƴ�ѧ�ٴ�ҽѧϵ.��ҵ��������ҽԺ����������40����,�������ո�������������������м໤����,�߱��ḻ�������ٴ�����.����þּ����м��Լ����Ҽ�����ɹ���,���Ⱥ󷢱�ҽѧ����20��ƪ��");
			break;
		case 3:
			name.setText("��ï");
			job.setText("��λҽ��");
			content.setText("\r\r\r\r\r\r\r�������Կ���ѧ���Ա,ʡ���Ǻ��ѧ�о����Ա.");
			break;
			
		default:
			break;
		}

	}

}

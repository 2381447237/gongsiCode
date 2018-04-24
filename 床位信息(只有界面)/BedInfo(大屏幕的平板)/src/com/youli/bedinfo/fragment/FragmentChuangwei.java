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

	// 介绍医生的对话框
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
            name.setText("李鹏飞");
            job.setText("外科/主任医生 ");
			content.setText("\r\r\r\r\r\r\r黑龙江和平医院外科学科带头人,毕业于哈尔滨职工医学院,进修于北京空军466医院,从事外科临床工作近40年,对普外、泌尿外、骨外等常见病、多发病有独特的诊断与治疗方法.经验丰富,技术精湛,手术疗效确切,技法娴熟、准确,是黑龙江省外科资深专家之一,被广大患者充分信赖,受到医疗各界人士的好评,在国家及省内级刊物发表医学论文40余篇。" +
					"黑龙江和平医院外科学科带头人,毕业于哈尔滨职工医学院,进修于北京空军466医院,从事外科临床工作近40年,对普外、泌尿外、骨外等常见病、多发病有独特的诊断与治疗方法.经验丰富,技术精湛,手术疗效确切,技法娴熟、准确,是黑龙江省外科资深专家之一,被广大患者充分信赖,受到医疗各界人士的好评,在国家及省内级刊物发表医学论文40余篇。");
            
			break;

		case 2:
			name.setText("张宇哲");
			job.setText("麻醉科/主治医生");
			content.setText("\r\r\r\r\r\r\r黑龙江和平医院首席高级麻醉师,省内麻醉领域知名权威,毕业于哈尔滨医科大学临床医学系.毕业后在三甲医院从事麻醉工作40余年,熟练掌握各种麻醉操作技术及术中监护技术,具备丰富的麻醉临床经验.曾获得局级、市级以及国家级多项成果奖,并先后发表医学论文20多篇。");
			break;
		case 3:
			name.setText("李茂");
			job.setText("床位医生");
			content.setText("\r\r\r\r\r\r\r听力语言康复学会会员,省耳鼻喉科学研究组成员.");
			break;
			
		default:
			break;
		}

	}

}

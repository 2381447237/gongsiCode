package com.fc.invite.views;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fc.company.beans.CompanyTask;
import com.fc.main.beans.IActivity;
import com.fc.main.myservices.CompanyService;
import com.fc.person.views.PersonqueryListActivity;
import com.fc.zbetuch_sm.R;

public class InviteJobDetailActivity extends Activity implements IActivity {
	TextView lblComName, lblJobName, lblJobNo, lblComPropertyName, lblComScope,
			lblComIntroduction, lblSalary, lblWelfare, lblProbSalary,
			lblProbMonth, lblRecruitNums, lblAge, lblEduName, lblEduLimitName,
			lblAreaName, lblGZBSName, lblGZXZName, lblLanguageName,
			lblLanguageProficiencyName, lblProfessionalRequirementName,
			lblProfessionalLevelName, lblQualifiedCertId,
			lblQualifiedLevelName, lblResponsibilities, lblQualifications,
			lblOthers, lblIsOther, lblTimeInfo, lblLinkMan,lblDispatched;
	Button btnCheck;
	ScrollView srRight;

	public static final int REFRESHFRM = 1;
	public static final int REFRESH_LINKMAN = 2;

	private String jobNo;

	private String jobId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite_job_detail);
		initView();
		init();
		initListener();
		Intent intent = getIntent();
		jobNo = intent.getStringExtra("id");

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", jobNo);
		CompanyTask task = new CompanyTask(CompanyTask.INVITEJOB_DETAIL, params);
		CompanyService.newTask(task);

		Map<String, Object> params2 = new HashMap<String, Object>();
		params2.put("id", jobNo);
		CompanyTask task2 = new CompanyTask(
				CompanyTask.INVITEJOS_DETAIL_LINKMAN, params2);
		CompanyService.newTask(task2);

	}

	@Override
	public void init() {
		CompanyService.addActivity(this);
	}

	@Override
	public void refresh(Object... params) {
		String value = "";
		if (params[1] != null) {
			value = params[1].toString();
		}
		switch (Integer.valueOf(params[0].toString())) {
		case InviteJobDetailActivity.REFRESHFRM:
			refreshView(value);
			srRight.post(new Runnable() { 
			       public void run() { 
			    	   srRight.fullScroll(ScrollView.FOCUS_DOWN); 
			       } 
			});
			break;
		case InviteJobDetailActivity.REFRESH_LINKMAN:
			refreshLinkMan(value);
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		CompanyService.allActivity.remove(this);
	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		lblComName = (TextView) findViewById(R.id.lblComName);
		lblJobName = (TextView) findViewById(R.id.lblJobName);
		lblJobNo = (TextView) findViewById(R.id.lblJobNo);
		lblComPropertyName = (TextView) findViewById(R.id.lblComPropertyName);
		lblComScope = (TextView) findViewById(R.id.lblComScope);
		lblComIntroduction = (TextView) findViewById(R.id.lblComIntroduction);
		lblSalary = (TextView) findViewById(R.id.lblSalary);
		lblWelfare = (TextView) findViewById(R.id.lblWelfare);
		lblProbSalary = (TextView) findViewById(R.id.lblProbSalary);
		lblProbMonth = (TextView) findViewById(R.id.lblProbMonth);
		lblRecruitNums = (TextView) findViewById(R.id.lblRecruitNums);
		lblAge = (TextView) findViewById(R.id.lblAge);
		lblEduName = (TextView) findViewById(R.id.lblEduName);
		// lblEduLimitName = (TextView) findViewById(R.id.lblEduLimitName);
		lblAreaName = (TextView) findViewById(R.id.lblAreaName);
		lblGZBSName = (TextView) findViewById(R.id.lblGZBSName);
		lblGZXZName = (TextView) findViewById(R.id.lblGZXZName);
		lblLanguageName = (TextView) findViewById(R.id.lblLanguageName);
		lblLanguageProficiencyName = (TextView) findViewById(R.id.lblLanguageProficiencyName);
		lblProfessionalRequirementName = (TextView) findViewById(R.id.lblProfessionalRequirementName);
		lblProfessionalLevelName = (TextView) findViewById(R.id.lblProfessionalLevelName);
		lblQualifiedCertId = (TextView) findViewById(R.id.lblQualifiedCertId);
		lblQualifiedLevelName = (TextView) findViewById(R.id.lblQualifiedLevelName);
		lblResponsibilities = (TextView) findViewById(R.id.lblResponsibilities);
		lblQualifications = (TextView) findViewById(R.id.lblQualifications);
		lblOthers = (TextView) findViewById(R.id.lblOthers);
		lblIsOther = (TextView) findViewById(R.id.lblIsOther);
		lblTimeInfo = (TextView) findViewById(R.id.lblTimeInfo);
		lblLinkMan = (TextView) findViewById(R.id.lblLinkMan);
		btnCheck = (Button) findViewById(R.id.btnCheck);
		srRight = (ScrollView) findViewById(R.id.svRight);
		
		lblDispatched = (TextView) findViewById(R.id.lblDispatched);
	}

	/**
	 * 根据数据刷新页面
	 * 
	 * @param value
	 */
	private void refreshView(String value) {
		try {
			JSONArray jsonArray = new JSONArray(value);
			if (jsonArray.length() > 0) {

				JSONObject item = jsonArray.optJSONObject(0);

				jobId = item.getString("jobid");
				lblComName.setText(item.getString("comname"));
				lblJobName.setText(item.getString("jobname"));
				lblJobNo.setText(item.getString("jobno"));
				lblComPropertyName.setText(item.getString("compropertyname"));
				lblComScope.setText(item.getString("comscope"));
				lblComIntroduction.setText(item.getString("comintroduction"));
				lblSalary.setText(changeNum(item.getString("startsalary"))
						+ "-" + changeNum(item.getString("endsalary")));
				lblWelfare.setText(item.getString("welfare"));
				
				String probSalaryString="";
				double probstartsalary = Double.parseDouble(item
						.getString("probstartsalary"));
				double probendsalary = Double.parseDouble(item.getString("probendsalary"));
				if(probstartsalary!=-1 ||  probendsalary!=-1){
					probSalaryString = changeNum(item
							.getString("probstartsalary"))
							+ "-"
							+ changeNum(item.getString("probendsalary"));
				}
								
				lblProbSalary.setText(probSalaryString);
				
				String probmonthStr ="";
				int probmonth = Integer.parseInt(item.getString("probmonth"));
				if(probmonth!=-1){
					probmonthStr=item.getString("probmonth");
				}
				lblProbMonth.setText(probmonthStr);
				
				String recruitNums = "";
				int allnums = item.getInt("recruitnums");
				int maleNums = item.getInt("recruitmalenums");
				int femaleNums = item.getInt("recruitfemalenums");
				recruitNums = "共" + allnums + "人   "
						+ (maleNums > 0 ? "男" + maleNums + "人" : "") + "   "
						+ (femaleNums > 0 ? "女" + femaleNums + "人" : "");
				lblRecruitNums.setText(recruitNums);
				lblAge.setText(item.getString("startage") + "-"
						+ item.getString("endage") + "   "
						+ item.getString("agelimitname"));
				lblEduName.setText(item.getString("eduname") + "("
						+ item.getString("edulimitname") + ")");
				// lblEduLimitName.setText(item.getString("edulimitname"));

				String area = item.getString("areaname_1") + " "
						+ item.getString("areadesc_1");
				int areaid = item.getInt("areaid_2");
				if (areaid != -1) {
					area += "  " + item.getString("areaname_2") + "  "
							+ item.getString("areadesc_2");
				}
				areaid = item.getInt("areaid_3");
				if (areaid != -1) {
					area += "  " + item.getString("areaname_3") + "  "
							+ item.getString("areadesc_3");
				}
				lblAreaName.setText(area);

				lblGZBSName.setText(item.getString("gzbsname"));
				lblGZXZName.setText(item.getString("gzxzname"));
				lblLanguageName.setText(item.getString("languagename"));
				lblLanguageProficiencyName.setText(item
						.getString("languageproficiencyname"));
				lblProfessionalRequirementName.setText(item
						.getString("professionalrequirementname"));
				lblProfessionalLevelName.setText(item
						.getString("professionallevelname"));

				lblQualifiedCertId.setText(item.getString("qualifiedcertid"));
				lblQualifiedLevelName.setText(item
						.getString("qualifiedlevelname"));
//				lblResponsibilities.setText(item.getString("responsibilities").trim()+"\r\n");
//				lblQualifications.setText(item.getString("qualifications").trim()+"\r\n");
				lblResponsibilities.setText(ToDBC(item.getString("responsibilities").trim())+"\r\n");
				lblQualifications.setText(ToDBC(item.getString("qualifications").trim())+"\r\n");
				lblOthers.setText(item.getString("others"));

				String isOther = "";
				boolean isnewgraduates = item.getBoolean("isnewgraduates");

				boolean isdisabledperson = item.getBoolean("isdisabledperson");
				if (isnewgraduates || isdisabledperson) {
					isOther += "";
					if (isnewgraduates) {
						isOther += " 应届毕业生";
					}
					if (isdisabledperson) {
						isOther += " " + item.getString("cjlxname");
					}
				}
				lblIsOther.setText(isOther);

				lblTimeInfo.setText("发布日期："
						+ item.getString("publishdate").split("T")[0].trim()
						+ "  " + "更新日期："
						+ item.getString("modifydate").split("T")[0].trim()
						+ "   " + "终止日期："
						+ item.getString("enddate").split("T")[0].trim());
				//劳务派遣
				String isdispatched = item.getString("isdispatched").trim().equalsIgnoreCase("true")?"是":"否";
				lblDispatched.setText(isdispatched);
				
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 刷新联系人方式
	 * 
	 * @param value
	 */
	private void refreshLinkMan(String value) {
		try {
			JSONArray jsonArray = new JSONArray(value);
			if (jsonArray.length() > 0) {
				JSONObject item = jsonArray.optJSONObject(0);
				lblLinkMan.setText("联系人：" + item.getString("name") + "  "
						+ "地址：" + item.getString("address") + "  " + "电话："
						+ item.getString("phone") + "  " + "邮政编码："
						+ item.getString("zip"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void initListener() {
		btnCheck.setOnClickListener(new MyOnClickListener());
	}

	private class MyOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnCheck:
				Intent intent = new Intent(InviteJobDetailActivity.this,
						PersonqueryListActivity.class);
				intent.putExtra("flag",
						PersonqueryListActivity.FLAG_FROM_COMPANY);
				intent.putExtra("JobId", jobId);
				startActivity(intent);
				break;
			}
		}
	}

	/**
	 * 去掉传来double后的点
	 * 
	 * @param innum
	 * @return
	 */
	private String changeNum(String innum) {
		DecimalFormat format = new DecimalFormat("0");
		return format.format(Double.parseDouble(innum));
	}
	
	public static String ToDBC(String input) {
		   char[] c = input.toCharArray();
		   for (int i = 0; i< c.length; i++) {
		       if (c[i] == 12288) {
		         c[i] = (char) 32;
		         continue;
		       }if (c[i]> 65280&& c[i]< 65375)
		          c[i] = (char) (c[i] - 65248);
		       }
		   return new String(c);
		}

}

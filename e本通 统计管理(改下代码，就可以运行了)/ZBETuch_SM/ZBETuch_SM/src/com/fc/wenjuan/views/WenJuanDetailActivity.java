package com.fc.wenjuan.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.main.beans.IActivity;
import com.fc.main.myservices.PersonService;
import com.fc.main.tools.HttpUrls_;
import com.fc.main.tools.MainTools;
import com.fc.person.beans.PersonTask;
import com.fc.wenjuan.beans.AnswerInfo;
import com.fc.wenjuan.beans.WenJuanInfo;
import com.fc.wenjuan.beans.WenJuanType;
import com.fc.wenjuan.beans.YiChaWenJuan;
import com.test.zbetuch_news.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class WenJuanDetailActivity extends Activity implements IActivity {

	private Button btn_star, btn_upload, btn_next_question, btn_up_question,
			btn_all;
	private LinearLayout llDuty;

	private List<WenJuanInfo> juanInfos = new ArrayList<WenJuanInfo>();

	private List<WenJuanInfo> answerInfo = new ArrayList<WenJuanInfo>();

	private List<WenJuanInfo> allanswerEditInfo = new ArrayList<WenJuanInfo>();

	// 保存所选的答案和题号
	private List<AnswerInfo> answerInfos2 = new ArrayList<AnswerInfo>();

	private Activity context = this;

	public static final int REFRESH_INFO = 0;

	public static final int REFRESH_INFO_HISTORY_LIST = 1;

	private List<RadioButton> radioButtons = new ArrayList<RadioButton>();
	// 保存小题的编辑框
	private List<EditText> editTexts = new ArrayList<EditText>();
	// 保存大题的编辑框
	private List<EditText> questionEditTexts = new ArrayList<EditText>();

	private List<WenJuanInfo> questionInfos;

	private int index = 0;

	private int tempQuestionIndex = 0;// 用于标识上一题的题号
	// private String tempQuestion=null;//用于标识大题是否有小题

	// 用于保存上一步回答的题目
	private List<Integer> tempList = new ArrayList<Integer>();

	private WenJuanType typeMap;

	private View view;

	private WenJuanInfo currentInfo;// 用于表示当前题

	private boolean rb;

	private List<YiChaWenJuan> allchaWenJuans = new ArrayList<YiChaWenJuan>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wenjuan_list);
		rb = getIntent().getBooleanExtra("rb", true);
		init();
		initView();

		if (rb) {
			btn_star.setVisibility(View.VISIBLE);
		} else {
			btn_all.setVisibility(View.VISIBLE);
		}

		if ("historyList".equals(getIntent().getAction())) {
			Map<String, Object> params = new HashMap<String, Object>();
			Map<String, String> data = new HashMap<String, String>();
			data.put("qa_master", getIntent().getIntExtra("pid", 0) + "");
			params.put("data", data);
			PersonTask task = new PersonTask(
					PersonTask.WENJUAN_HISTORY_LIST_ACTIVITY_GET_WENJUANINFO,
					params);
			PersonService.newTask(task);
		} else {
			typeMap = MainTools.map.get("wenjuaninfo");
			addTitle();
			if (!rb) {
				Map<String, Object> params = new HashMap<String, Object>();
				Map<String, String> data = new HashMap<String, String>();
				data.put("Personnel_id", getIntent().getIntExtra("pid", 0) + "");
				params.put("data", data);
				PersonTask task = new PersonTask(
						PersonTask.WENJUANLISTACTIVITY_GET_INFO_LIST_ANSWER,
						params);
				PersonService.newTask(task);
			}
		}

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		PersonService.addActivity(this);
	}

	private void initView() {
		llDuty = (LinearLayout) this.findViewById(R.id.llDuty);
		btn_star = (Button) this.findViewById(R.id.btn_star_question);
		btn_upload = (Button) this.findViewById(R.id.btn_up_wenjuan);
		btn_next_question = (Button) this.findViewById(R.id.btn_next_question);
		btn_up_question = (Button) this.findViewById(R.id.btn_up_question);
		btn_all = (Button) this.findViewById(R.id.btn_all);

		btn_star.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				List<WenJuanInfo> newJuanInfos = typeMap.getJuanInfos();
				if (newJuanInfos.size() > 0) {
					juanInfos.clear();
					radioButtons.clear();
					editTexts.clear();
					questionEditTexts.clear();
					index = 0;
					juanInfos.addAll(newJuanInfos);
				}
				if (juanInfos.size() > 0) {
					questionInfos = getQuestionByParent();
					if (questionInfos.size() > 0) {
						fretchTree(llDuty, questionInfos.get(index), "");
					}
				}

				btn_next_question.setVisibility(View.VISIBLE);
				btn_up_question.setVisibility(View.VISIBLE);
				btn_all.setVisibility(View.GONE);
				btn_star.setVisibility(View.GONE);
				btn_upload.setVisibility(View.GONE);
			}
		});

		btn_upload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// if(makeEdit(allanswerEditInfo)){
				// return ;
				// }
				Builder builder = new AlertDialog.Builder(context);
				builder.setTitle("温馨提示")
						.setMessage("确定要提交吗?")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										getAnswerInfo();
										String answerString = parseAnswerInfo();
										System.out
												.println("answerString======="
														+ answerString);
										if (answerInfos2.size() > 0) {
											answerInfos2.clear();
										}

										if (questionInfos.size() > 0) {
											llDuty.removeAllViews();
											if (tempList.size() > 0) {
												tempList.clear();
											}
											llDuty.addView(view);
											for (WenJuanInfo answerInfo : questionInfos) {
												fretchTree(llDuty, answerInfo,
														"all");
											}
										}
										btn_star.setVisibility(View.GONE);
										btn_upload.setEnabled(false);

										Map<String, Object> params = new HashMap<String, Object>();
										Map<String, String> data = new HashMap<String, String>();
										data.put("Personnel_id", getIntent()
												.getIntExtra("pid", 0) + "");
										data.put("mark", "");
										data.put("position", getIntent()
												.getIntExtra("position", 0)
												+ "");
										data.put("data", answerString);
										params.put("data", data);
										PersonTask task = new PersonTask(
												PersonTask.UPLOADWENJUAN_SET_WENJUAN,
												params);
										PersonService.newTask(task);
										WenJuanDetailActivity.this.finish();
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										dialog.dismiss();
									}
								}).create().show();

			}
		});

		btn_next_question.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// if ("all".equals(tempQuestion)) {
				// return;
				// }

				WenJuanInfo info = questionInfos.get(index);

				List<WenJuanInfo> tempSmallWenJuan = getAnswerByParentId(info);

				int questionNo = info.getID();
				if (questionEditTexts.size() > 0) {
					for (EditText editText : questionEditTexts) {
						if (editText.getId() == questionNo
								&& "".equals(editText.getText().toString()
										.trim())) {
							Toast.makeText(context, "答案不能为空!",
									Toast.LENGTH_SHORT).show();
							return;
						}
					}
				}

				if (tempSmallWenJuan.size() > 0
						&& !checkRadioIsChecked(answerInfo, questionNo)) {
					Toast.makeText(context, "请选择合适的答案", Toast.LENGTH_LONG)
							.show();
					return;
				}

				if (makeEdit(answerInfo))
					return;

				if (index >= questionInfos.size() - 1) {
					Toast.makeText(context, "已经是最后一题了", Toast.LENGTH_LONG)
							.show();
					btn_all.setVisibility(View.VISIBLE);
					return;
				}
				System.out.println("index1=====" + index);
				List<WenJuanInfo> tempAnswer = new ArrayList<WenJuanInfo>();
				for (WenJuanInfo answer : answerInfo) {
					if (answer.getPARENT_ID() == questionNo) {
						tempAnswer.add(answer);
					}
				}
				tempQuestionIndex = index;
				tempList.add(tempQuestionIndex);
				String jumpCode = null;
				for (RadioButton radioButton : radioButtons) {
					if (radioButton.isChecked()) {
						for (WenJuanInfo temp : tempAnswer) {
							if (radioButton.getId() == temp.getID()) {
								jumpCode = temp.getJUMP_CODE();
							}
						}
					}
				}
				System.out.println("index2======" + index);
				System.out.println("jumpCode=====" + jumpCode);
				int tempIndex = 0;
				if (jumpCode != null) {
					for (WenJuanInfo wenJuanInfo : questionInfos) {
						if (jumpCode.equals(wenJuanInfo.getCODE())) {
							System.out.println("=========="
									+ wenJuanInfo.getCODE());
							index = questionInfos.indexOf(wenJuanInfo);
							tempIndex = 0;
							break;
						} else {
							tempIndex++;
						}
					}
				} else {
					tempIndex++;
				}
				if (tempIndex > 0) {
					index++;
				}
				System.out.println("tempIndex======" + tempIndex);
				System.out.println("index=======" + index);
				tempAnswer.clear();
				currentInfo = questionInfos.get(index);
				fretchTree(llDuty, currentInfo, "");
			}
		});

		btn_up_question.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// if ("all".equals(tempQuestion)) {
				// return;
				// }
				if (index == 0) {
					Toast.makeText(context, "已经是第一题了", Toast.LENGTH_LONG)
							.show();
					return;
				}

				// 去掉答题的文本
				System.out.println("=====" + currentInfo.getID());
				for (EditText editText : questionEditTexts) {
					if (currentInfo.getID() == editText.getId()) {
						editText.setText("");
					}
				}
				// 去掉小题的文本
				List<WenJuanInfo> list = getAnswerByParentId(currentInfo);
				for (WenJuanInfo wenJuanInfo : list) {
					for (EditText editText : editTexts) {
						if (editText.getId() == wenJuanInfo.getID()) {
							editText.setText("");
						}
					}
				}
				// 去掉单选按钮
				for (WenJuanInfo wenJuanInfo : list) {
					for (RadioButton radioButton : radioButtons) {
						if (radioButton.getId() == wenJuanInfo.getID()) {
							radioButton.setChecked(false);
						}
					}
				}

				// if ("next".equals(tempQuestion)) {
				if (tempList.size() > 0) {
					List<Integer> temp = new ArrayList<Integer>();
					temp.addAll(tempList.subList(tempList.size() - 1,
							tempList.size()));
					tempQuestionIndex = temp.get(0);
					tempList.removeAll(temp);
					temp.clear();
				}
				index = tempQuestionIndex;
				// } else if("up".equals(tempQuestion)){
				// index--;
				// }
				// tempQuestion="up";
				currentInfo = questionInfos.get(index);
				fretchTree(llDuty, currentInfo, "");
				if (index < questionInfos.size()) {
					btn_all.setVisibility(View.GONE);
				}
			}
		});

		btn_all.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// tempQuestion="all";
				if (!rb) {
					List<WenJuanInfo> newJuanInfos = typeMap.getJuanInfos();
					if (newJuanInfos.size() > 0) {
						juanInfos.clear();
						radioButtons.clear();
						editTexts.clear();
						questionEditTexts.clear();
						index = 0;
						juanInfos.addAll(newJuanInfos);
					}
					questionInfos = getQuestionByParent();

					btn_star.setVisibility(View.GONE);
					btn_upload.setVisibility(View.GONE);
				} else {
					btn_star.setVisibility(View.VISIBLE);
					btn_star.setText("重新答题");
					btn_upload.setVisibility(View.VISIBLE);
				}

				if (questionInfos.size() > 0) {
					llDuty.removeAllViews();
					if (tempList.size() > 0) {
						tempList.clear();
					}
					llDuty.addView(view);
					for (WenJuanInfo answerInfo : questionInfos) {
						fretchTree(llDuty, answerInfo, "all");
					}
				}

				btn_next_question.setVisibility(View.GONE);
				btn_up_question.setVisibility(View.GONE);
				btn_all.setVisibility(View.GONE);

			}
		});
	}

	private void getAnswerInfo() {
		if (radioButtons.size() > 0) {
			for (RadioButton radioButton : radioButtons) {
				if (radioButton.isChecked()) {
					AnswerInfo answerInfo = new AnswerInfo();

					for (WenJuanInfo wenJuanInfo : juanInfos) {
						if (wenJuanInfo.getID() == radioButton.getId()) {
							answerInfo.setAnswerId(wenJuanInfo.getID());
							answerInfo.setAnswerNo(wenJuanInfo.getNO());
							answerInfo.setAnswerText("");
						}
					}
					if (editTexts.size() > 0) {
						for (EditText editText : editTexts) {
							if (radioButton.getId() == editText.getId()) {
								answerInfo.setAnswerText(editText.getText()
										.toString().trim());
							}
						}
					}
					answerInfos2.add(answerInfo);
				}
			}
		}

		if (questionEditTexts.size() > 0) {
			List<WenJuanInfo> questionInfos = getQuestionByParent();
			for (EditText editText : questionEditTexts) {
				for (WenJuanInfo wenJuanInfo : questionInfos) {
					if (editText.getId() == wenJuanInfo.getID()
							&& !"".equals(editText.getText().toString().trim())
							&& null != editText.getText().toString().trim()) {
						AnswerInfo answerInfo = new AnswerInfo();
						answerInfo.setAnswerId(wenJuanInfo.getID());
						answerInfo.setAnswerNo(wenJuanInfo.getNO());
						answerInfo.setAnswerText(editText.getText().toString()
								.trim());
						answerInfos2.add(answerInfo);
					}
				}
			}
		}

	}

	private String parseAnswerInfo() {
		JSONArray array = new JSONArray();
		if (answerInfos2.size() > 0) {
			for (AnswerInfo answerInfo : answerInfos2) {
				JSONObject object = new JSONObject();
				try {
					object.put("DETIL_ID", answerInfo.getAnswerId());
					object.put("INPUT_VALUE", answerInfo.getAnswerText());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				array.put(object);
			}
		}

		return array.toString();
	}

	@Override
	public void refresh(Object... params) {
		// TODO Auto-generated method stub
		switch (Integer.parseInt(params[0].toString().trim())) {
		case REFRESH_INFO:
			// List<WenJuanInfo>
			// newJuanInfos=parseInfo(params[1].toString().trim());
			// if (newJuanInfos.size()>0) {
			// juanInfos.addAll(newJuanInfos);
			// }
			// if (juanInfos.size()>0) {
			// questionInfos=getQuestionByParent();
			// if (questionInfos.size()>0) {
			// fretchTree(llDuty,questionInfos.get(index),"");
			// }
			// }
			if ("historyList".equals(getIntent().getAction())) {
				addTitle();
			}
			List<YiChaWenJuan> chaWenJuans = parseAnswerInfo(params[1]
					.toString().trim());
			if (chaWenJuans != null && chaWenJuans.size() > 0) {
				allchaWenJuans.addAll(chaWenJuans);
			}

			break;

		case REFRESH_INFO_HISTORY_LIST:
			if (!"".equals(params[1].toString().trim())
					|| null != params[1].toString().trim()) {
				List<WenJuanType> newWenJuanType = getWenJuanTypes(params[1]
						.toString().trim());
				if (newWenJuanType.size() > 0) {
					MainTools.map.put("wenjuaninfohistory",
							(WenJuanType) newWenJuanType.get(0));
					typeMap = MainTools.map.get("wenjuaninfohistory");
					Map<String, Object> params1 = new HashMap<String, Object>();
					Map<String, String> data = new HashMap<String, String>();
					data.put("Personnel_id",
							getIntent().getIntExtra("answerId", 0) + "");
					params1.put("data", data);
					PersonTask task = new PersonTask(
							PersonTask.WENJUANLISTACTIVITY_GET_INFO_LIST_ANSWER,
							params1);
					PersonService.newTask(task);
				}
			}
			break;

		default:
			break;
		}
	}

	private void fretchTree(LinearLayout layout, WenJuanInfo info, String isAll) {
		if ("".equals(isAll)) {
			llDuty.removeAllViews();
		}

		LinearLayout alllinearLayout = new LinearLayout(context);
		LinearLayout.LayoutParams allparam = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		alllinearLayout.setLayoutParams(allparam);
		alllinearLayout.setOrientation(LinearLayout.VERTICAL);

		LinearLayout qlinearLayout = new LinearLayout(context);
		qlinearLayout.setLayoutParams(allparam);
		qlinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		TextView textView = new TextView(context);
		textView.setLayoutParams(allparam);
		textView.setText(info.getTITLE_L());
		textView.setTextSize(30);
		qlinearLayout.addView(textView, allparam);
		if (info.isINPUT()) {
			EditText editText = new EditText(context);
			LinearLayout.LayoutParams edit = new LinearLayout.LayoutParams(100,
					50);
			editText.setLayoutParams(edit);
			if ("int".equals(info.getINPUT_TYPE())) {
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			}
			if (questionEditTexts.size() > 0) {
				List<EditText> tempEditTexts = new ArrayList<EditText>();
				for (EditText editText2 : questionEditTexts) {
					if (editText2.getId() == info.getID()) {
						editText.setText(editText2.getText());
						tempEditTexts.add(editText2);
					}
				}
				questionEditTexts.removeAll(tempEditTexts);
				tempEditTexts.clear();
			}

			editText.setId(info.getID());
			if (allchaWenJuans.size() > 0) {
				for (YiChaWenJuan yiChaWenJuan : allchaWenJuans) {
					if (yiChaWenJuan.getDETIL_ID() == editText.getId()) {
						editText.setText(yiChaWenJuan.getINPUT_VALUE());
					}
				}
			}

			if ("all".equals(isAll)) {
				editText.setEnabled(false);
			}
			questionEditTexts.add(editText);
			qlinearLayout.addView(editText, edit);
			if (!"".equals(info.getTITLE_R())) {
				TextView rTextView = new TextView(context);
				rTextView.setLayoutParams(allparam);
				rTextView.setTextSize(30);
				rTextView.setText(info.getTITLE_R());
				qlinearLayout.addView(rTextView, allparam);
			}
		}

		alllinearLayout.addView(qlinearLayout, allparam);

		LinearLayout aLinearLayout = new LinearLayout(context);
		aLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		aLinearLayout.setLayoutParams(allparam);

		RadioGroup radioGroup = new RadioGroup(context);
		radioGroup.setLayoutParams(allparam);

		LinearLayout xuanxiangLinearLayout = new LinearLayout(context);
		xuanxiangLinearLayout.setLayoutParams(allparam);
		xuanxiangLinearLayout.setPadding(30, 0, 0, 0);
		xuanxiangLinearLayout.setOrientation(LinearLayout.VERTICAL);

		answerInfo = getAnswerByParentId(info);
		if (allanswerEditInfo.size() > 0) {
			if (allanswerEditInfo.containsAll(answerInfo)) {
				allanswerEditInfo.removeAll(answerInfo);
			}
		}
		allanswerEditInfo.addAll(answerInfo);

		for (WenJuanInfo wenJuanInfo : answerInfo) {
			fretchTreeByQuestion(radioGroup, wenJuanInfo,
					xuanxiangLinearLayout, isAll);
		}

		aLinearLayout.addView(radioGroup, allparam);
		aLinearLayout.addView(xuanxiangLinearLayout, allparam);

		alllinearLayout.addView(aLinearLayout, allparam);

		layout.addView(alllinearLayout, allparam);
	}

	private void fretchTreeByQuestion(RadioGroup group,
			WenJuanInfo wenJuanInfo, LinearLayout xuanxiangLinearLayout,
			String isAll) {
		LinearLayout linearLayout = new LinearLayout(context);
		LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, 45);
		linearLayout.setLayoutParams(param1);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);

		RadioButton radioButton = new RadioButton(context);
		LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, 45);
		radioButton.setLayoutParams(param2);
		radioButton.setId(wenJuanInfo.getID());
		group.addView(radioButton);
		if (radioButtons.size() > 0) {
			List<RadioButton> tempRadioButtons = new ArrayList<RadioButton>();
			for (RadioButton radioButton2 : radioButtons) {
				if (radioButton2.getId() == wenJuanInfo.getID()
						&& radioButton2.isChecked()) {
					radioButton.setChecked(true);
					tempRadioButtons.add(radioButton2);
				}
			}
			radioButtons.removeAll(tempRadioButtons);
			tempRadioButtons.clear();
		}

		if (allchaWenJuans.size() > 0) {
			for (YiChaWenJuan yiChaWenJuan : allchaWenJuans) {
				if (yiChaWenJuan.getDETIL_ID() == radioButton.getId()) {
					radioButton.setChecked(true);
				}
			}
		}

		if ("all".equals(isAll)) {
			radioButton.setEnabled(false);
		}
		radioButtons.add(radioButton);
		if (wenJuanInfo.isINPUT()) {
			LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, 45);
			TextView textView = new TextView(context);
			textView.setGravity(Gravity.CENTER);
			textView.setLayoutParams(text);
			textView.setTextSize(18);
			textView.setText(wenJuanInfo.getTITLE_L());
			linearLayout.addView(textView, text);
			EditText editText = new EditText(context);
			editText.setId(wenJuanInfo.getID());
			if ("int".equals(wenJuanInfo.getINPUT_TYPE())) {
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
			}
			if (editTexts.size() > 0) {
				List<EditText> tempEditTexts = new ArrayList<EditText>();
				for (EditText editText2 : editTexts) {
					if (editText2.getId() == wenJuanInfo.getID()) {
						editText.setText(editText2.getText());
						tempEditTexts.add(editText2);
					}
				}
				editTexts.removeAll(tempEditTexts);
				tempEditTexts.clear();
			}

			if (allchaWenJuans.size() > 0) {
				for (YiChaWenJuan yiChaWenJuan : allchaWenJuans) {
					if (yiChaWenJuan.getDETIL_ID() == editText.getId()) {
						editText.setText(yiChaWenJuan.getINPUT_VALUE());
					}
				}
			}

			if ("all".equals(isAll)) {
				editText.setEnabled(false);
			}
			editTexts.add(editText);
			LinearLayout.LayoutParams edit = new LinearLayout.LayoutParams(100,
					45);
			editText.setLayoutParams(edit);
			linearLayout.addView(editText, edit);

			if (!"".equals(wenJuanInfo.getTITLE_R())) {
				LinearLayout.LayoutParams rParams = new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT, 45);
				TextView rTextView = new TextView(context);
				textView.setLayoutParams(rParams);
				rTextView.setTextSize(18);
				rTextView.setText(wenJuanInfo.getTITLE_R());
				linearLayout.addView(rTextView, rParams);
			}
		} else {
			TextView textView = new TextView(context);
			LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, 45);
			textView.setGravity(Gravity.CENTER);
			radioButton.setId(wenJuanInfo.getID());
			textView.setText(wenJuanInfo.getTITLE_L());
			textView.setTextSize(18);
			linearLayout.addView(textView, textparams);
		}
		xuanxiangLinearLayout.addView(linearLayout, param1);
	}

	private List<WenJuanInfo> getQuestionByParent() {
		List<WenJuanInfo> questionInfos = new ArrayList<WenJuanInfo>();
		for (WenJuanInfo wenJuanInfo : juanInfos) {
			if (wenJuanInfo.getPARENT_ID() == 0) {
				questionInfos.add(wenJuanInfo);
			}
		}
		return questionInfos;
	}

	private List<WenJuanInfo> getAnswerByParentId(WenJuanInfo info) {
		List<WenJuanInfo> anwserInfos = new ArrayList<WenJuanInfo>();
		for (WenJuanInfo wenJuanInfo : juanInfos) {
			if (wenJuanInfo.getPARENT_ID() == info.getID()) {
				anwserInfos.add(wenJuanInfo);
			}
		}
		return anwserInfos;
	}

	private List<WenJuanInfo> parseInfo(String str) {
		List<WenJuanInfo> newInfos = new ArrayList<WenJuanInfo>();
		try {
			JSONArray array = new JSONArray(str);
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = (JSONObject) array.get(i);
				WenJuanInfo info = new WenJuanInfo();
				info.setID(object.getInt("ID"));
				info.setCODE(object.getString("CODE"));
				info.setINPUT(object.getBoolean("INPUT"));
				info.setINPUT_TYPE(object.getString("INPUT_TYPE"));
				info.setJUMP_CODE(object.getString("JUMP_CODE"));
				info.setNO(object.getDouble("NO"));
				info.setPARENT_ID(object.getInt("PARENT_ID"));
				info.setRecordCount(object.getInt("RecordCount"));
				info.setTITLE_L(object.getString("TITLE_L"));
				info.setTITLE_R(object.getString("TITLE_R"));
				newInfos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newInfos;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		PersonService.allActivity.remove(this);
	}

	private void addTitle() {
		view = LayoutInflater.from(context).inflate(
				R.layout.item_wenjuan_title, null);
		TextView tv_no = (TextView) view.findViewById(R.id.tv_number);
		TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
		TextView tv_title = (TextView) view.findViewById(R.id.tv_title_);
		WebView webView = (WebView) view.findViewById(R.id.my_webview);
		tv_no.setText(Html.fromHtml("<u>" + getIntent().getStringExtra("NO")
				+ "</u>"));
		tv_title.setText(typeMap.getTITLE());
		tv_time.setText(typeMap.getCREATE_TIME().substring(0, 10));
		webView.loadUrl(HttpUrls_.HttpURL + "/json/" + typeMap.getTEXT());
		llDuty.addView(view);
	}

	private boolean makeEdit(List<WenJuanInfo> infos) {
		if (editTexts.size() > 0) {
			for (RadioButton radioButton : radioButtons) {
				if (radioButton.isChecked()) {
					for (EditText editText : editTexts) {
						for (WenJuanInfo wenJuanInfo : infos) {
							if (radioButton.getId() == wenJuanInfo.getID()
									&& editText.getId() == wenJuanInfo.getID()
									&& "".equals(editText.getText().toString()
											.trim())) {
								Toast.makeText(context, "答案不能为空!",
										Toast.LENGTH_SHORT).show();
								return true;
							}
						}
					}
				}

			}
		}
		return false;
	}

	private boolean checkRadioIsChecked(List<WenJuanInfo> infos, int questionId) {
		for (RadioButton radioButton : radioButtons) {
			for (WenJuanInfo wenJuanInfo : infos) {
				if (radioButton.getId() == wenJuanInfo.getID()
						&& wenJuanInfo.getPARENT_ID() == questionId
						&& radioButton.isChecked()) {
					return true;
				}
			}
		}
		return false;
	}

	private List<YiChaWenJuan> parseAnswerInfo(String str) {
		List<YiChaWenJuan> newyiChaWenJuans = new ArrayList<YiChaWenJuan>();
		try {
			JSONArray array = new JSONArray(str);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = (JSONObject) array.get(i);
				YiChaWenJuan chaWenJuan = new YiChaWenJuan();
				chaWenJuan.setID(jsonObject.getInt("ID"));
				chaWenJuan.setDETIL_ID(jsonObject.getInt("DETIL_ID"));
				chaWenJuan.setINPUT_VALUE(jsonObject.getString("INPUT_VALUE"));
				chaWenJuan.setPERSONNEL_ID(jsonObject.getInt("PERSONNEL_ID"));
				chaWenJuan.setRecordCount(jsonObject.getInt("RecordCount"));
				newyiChaWenJuans.add(chaWenJuan);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newyiChaWenJuans;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (rb) {
				showMessage("温馨提示", "确定要放弃答题吗?");
			} else {
				showMessage("温馨提示", "确定要退出吗?");
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void showMessage(String title, String message) {
		Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title).setMessage(message)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						WenJuanDetailActivity.this.finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).create().show();
	}

	private List<WenJuanType> getWenJuanTypes(String str) {
		List<WenJuanType> juanTypes = new ArrayList<WenJuanType>();
		try {
			JSONArray jsonArray = new JSONArray(str);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				WenJuanType juanType = new WenJuanType();
				juanType.setID(jsonObject.getInt("ID"));
				juanType.setCREATE_TIME(jsonObject.getString("CREATE_TIME"));
				juanType.setNO(jsonObject.getString("NO"));
				juanType.setRecordCount(jsonObject.getInt("RecordCount"));
				juanType.setTEXT(jsonObject.getString("TEXT"));
				juanType.setTITLE(jsonObject.getString("TITLE"));
				String details = jsonObject.getString("Detils").toString();
				juanType.setJuanInfos(parseInfos(details));
				juanTypes.add(juanType);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return juanTypes;
	}

	private List<WenJuanInfo> parseInfos(String str) {
		List<WenJuanInfo> newInfos = new ArrayList<WenJuanInfo>();
		try {
			JSONArray array = new JSONArray(str);
			for (int i = 0; i < array.length(); i++) {
				JSONObject object = (JSONObject) array.get(i);
				WenJuanInfo info = new WenJuanInfo();
				info.setID(object.getInt("ID"));
				info.setCODE(object.getString("CODE"));
				info.setINPUT(object.getBoolean("INPUT"));
				info.setINPUT_TYPE(object.getString("INPUT_TYPE"));
				info.setJUMP_CODE(object.getString("JUMP_CODE"));
				info.setNO(object.getDouble("NO"));
				info.setPARENT_ID(object.getInt("PARENT_ID"));
				info.setRecordCount(object.getInt("RecordCount"));
				info.setTITLE_L(object.getString("TITLE_L"));
				info.setTITLE_R(object.getString("TITLE_R"));
				info.setREMOVE_CODE(object.getString("REMOVE_CODE"));
				newInfos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newInfos;
	}
}

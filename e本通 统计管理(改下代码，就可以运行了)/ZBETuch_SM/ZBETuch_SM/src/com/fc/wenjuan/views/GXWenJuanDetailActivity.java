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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class GXWenJuanDetailActivity extends Activity implements IActivity {

	// 需要移除的选项
	private String remove_code = "";
	// 标记下需要移除的题索引，比对是否在移除后
	private int remove_index = -1;

	private Button btn_star, btn_upload, btn_next_question, btn_up_question,
			btn_all;
	// private int last_id = -1;
	// private String curr_id = "";
	Map<Integer, String> return_values = new HashMap<Integer, String>();

	private LinearLayout llDuty;

	private List<WenJuanInfo> juanInfos = new ArrayList<WenJuanInfo>();
	// 当前控件
	private List<Object> CurrCol = new ArrayList<Object>();

	private List<WenJuanInfo> answerInfo = new ArrayList<WenJuanInfo>();

	private List<WenJuanInfo> allanswerEditInfo = new ArrayList<WenJuanInfo>();
	private AlertDialog dialog2;
	private ProgressDialog dialog;
	// 保存所选的答案和题号
	private List<AnswerInfo> answerInfos2 = new ArrayList<AnswerInfo>();

	private Activity context = this;

	public static final int REFRESH_INFO = 0;
	public static final int REFRESH_INFO_HISTORY_LIST = 1;
	public static final int Service_retuen_value = 2;
	public static final int GetReceiv_Special = 3;

	private List<RadioButton> radioButtons = new ArrayList<RadioButton>();
	private List<CheckBox> this_CheckBoxs = new ArrayList<CheckBox>();
	// 保存小题的编辑框
	private List<EditText> editTexts = new ArrayList<EditText>();
	// 保存大题的编辑框
	private List<EditText> questionEditTexts = new ArrayList<EditText>();

	// private List<EditText>
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
	private String sex;
	private String csrq2;
	private String csrq;
	private String jsxx2;
	private String jsxx;
	private String edu;
	private String school;
	private String sxzy;
	private String bindinfo;
	private List<YiChaWenJuan> allchaWenJuans = new ArrayList<YiChaWenJuan>();
	
	//提交
	private EditText et_phone;
	private Button button_true;
	private Button button_false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_wenjuan_list);
		rb = getIntent().getBooleanExtra("rb", true);

		init();
		initView();
		// 得到个人信息中传过来的值
		if (rb) {
			sex = getIntent().getStringExtra("sex");
			edu = getIntent().getStringExtra("edu");
			csrq2 = getIntent().getStringExtra("csrq");
			csrq = csrq2.substring(0, 4);
			jsxx2 = getIntent().getStringExtra("jsxx");
			jsxx = jsxx2.substring(0, 4);
			school = getIntent().getStringExtra("school");
			sxzy = getIntent().getStringExtra("sxzy");

		}
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
					PersonTask.GXWENJUAN_HISTORY_LIST_ACTIVITY_GET_WENJUANINFO,
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
						PersonTask.GXWENJUANLISTACTIVITY_GET_INFO_LIST_ANSWER,
						params);
				PersonService.newTask(task);
			}

		}

		if (rb) {
			// 获取已做问卷
			getReceiv_Special();
		}

	}

	void getReceiv_Special() {
		Log.i("qwj", "Personnel_id:" + getIntent().getIntExtra("pid", 0));

		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, String> data = new HashMap<String, String>();
		data.put("Personnel_id", getIntent().getIntExtra("pid", 0) + "");
		params.put("data", data);
		PersonTask task = new PersonTask(
				PersonTask.GXWENJUAN_GetReceiv_Special, params);
		PersonService.newTask(task);

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
					Log.i("qwj", "da33--tefb---99=="+radioButtons);
				}
				if (juanInfos.size() > 0) {
					questionInfos = getQuestionByParent();
					if (questionInfos.size() > 0) {
						fretchTree(llDuty, questionInfos.get(index), "");
					}
				}
				if (btn_star.getText().equals("重新答题")) {

					return_values.clear();
					Map<String, Object> params = new HashMap<String, Object>();
					Map<String, String> data = new HashMap<String, String>();
					params.put("index", "" + 0);

					data.put("Personnel_id", getIntent().getIntExtra("pid", 0)
							+ "");
					// 下一题不传mark，完全做完时传
					data.put("del", "True");
					data.put("Receiv_id", "all");

					data.put("position", getIntent().getIntExtra("position", 0)
							+ "");
					data.put("mark", "");
					data.put("data", "");
					params.put("data", data);
					Log.i("qwj", "datefb---99=="+data);
					PersonTask task = new PersonTask(
							PersonTask.UPLOADWENJUAN_SET_WENJUAN_special,
							params);
					// task.setJsonString(JsonString)
					PersonService.newTask(task);

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
				dialog2=new AlertDialog.Builder(context).create();
				View view2=LayoutInflater.from(context).inflate(R.layout.activity_wenjuan_phone, null);
				dialog2.setView(view2);
				dialog2.show();
				Window window=dialog2.getWindow();
				window.setContentView(R.layout.activity_wenjuan_phone);
				Builder builder = new AlertDialog.Builder(context);
				et_phone=(EditText)window.findViewById(R.id.et_phone);
				button_true=(Button) window.findViewById(R.id.btn_true2);
				button_false=(Button) window.findViewById(R.id.btn_false2);
				button_true.setOnClickListener(new MyClickListener());
				button_false.setOnClickListener(new MyClickListener());
				// LayoutInflater layoutInflater = LayoutInflater.from(context);
				 // View myLoginView = layoutInflater.inflate(R.layout.activity_wenjuan_phone, null);
				 // LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				// final  LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.activity_wenjuan_phone, null);
				
				/* builder.setTitle("温馨提示")
						.setMessage("确定要提交吗?")
						//.setView(layout)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										
										
										
										WenJuanInfo info = questionInfos
												.get(index);//
										Log.i("qwj", "" + index);
										Log.i("qwj", "id:" + info.getID()
												+ ",title" + info.getTITLE_L());

										// 查找父id
										List<WenJuanInfo> tempSmallWenJuan = getAnswerByParentId(info);// 选项

										int questionNo = info.getID();
										if (questionEditTexts.size() > 0) {
											for (EditText editText : questionEditTexts) {
												if (editText.getId() == questionNo
														&& "".equals(editText
																.getText()
																.toString()
																.trim())) {
													Toast.makeText(context,
															"答案不能为空!",
															Toast.LENGTH_SHORT)
															.show();
													return;
												}
											}
										}

										if (tempSmallWenJuan.size() > 0
												&& !checkRadioIsChecked(
														answerInfo, questionNo)) {
											Toast.makeText(context, "请选择合适的答案",
													Toast.LENGTH_LONG).show();
											return;
										}

										if (makeEdit(answerInfo))
											return;

										System.out.println("index1====="
												+ index);
										List<WenJuanInfo> tempAnswer = new ArrayList<WenJuanInfo>();

										System.out.println("index1====="
												+ index);
										tempAnswer = new ArrayList<WenJuanInfo>();

										showDialog();
										List<AnswerInfo> list = getAnswerInfo2(
												questionNo, info);
										Log.i("qwj", "questionNo:" + questionNo
												+ ",size:" + list.size());

										String answerString = parseAnswerInfo(list);

										// Log.i("qwj", "answerString:"
										// + answerString);

										Map<String, Object> params = new HashMap<String, Object>();
										Map<String, String> data = new HashMap<String, String>();
										params.put("index", index);
										// 修改答案
										if (return_values.containsKey(index)) {
											String del_id = return_values
													.get(index);
											String del_id2 = return_values.get(index);
											String[] del_id=del_id2.split(",");
											data.put("Receiv_id", "" + del_id[0]);
											//data.put("Receiv_id", "" + del_id);

											Log.i("qwj", "Receiv_id1:" + del_id);
										} else {
											data.put("Receiv_id", "");
										}

										// 下一题不传mark，完全做完时传
										data.put("mark", "完成");

										data.put("Personnel_id", getIntent()
												.getIntExtra("pid", 0) + "");

										Log.i("qwj",
												"Personnel_id1:"
														+ getIntent()
																.getIntExtra(
																		"pid",
																		0));

										data.put("position", getIntent()
												.getIntExtra("position", 0)
												+ "");

										data.put("data", answerString);
										params.put("data", data);
										PersonTask task = new PersonTask(
												PersonTask.UPLOADWENJUAN_SET_WENJUAN_special,
												params);
										// task.setJsonString(JsonString)
										PersonService.newTask(task);
										btn_next_question
												.setVisibility(view.GONE);

										btn_star.setVisibility(View.GONE);
										btn_upload.setEnabled(false);

										GXWenJuanDetailActivity.this.finish();
										GXWenJuanPersonActivity._instance
												.finish();
										Intent showIntent = new Intent(
												GXWenJuanDetailActivity.this,
												GXWenJuanPersonActivity.class);
										startActivity(showIntent);
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
*/
			}
		});

		btn_next_question.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// if ("all".equals(tempQuestion)) {
				// return;
				// }
				// System.out.println("index>>>>>>>" + index);

				WenJuanInfo info = questionInfos.get(index);//

				Log.i("qwj", "info.gefrre" + info.getBindInfo());

				Log.i("qwj", "" + index);
				Log.i("qwj",
						"id:" + info.getID() + ",title" + info.getTITLE_L());

				// 查找父id
				List<WenJuanInfo> tempSmallWenJuan = getAnswerByParentId(info);// 选项

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
						if (editText.getId() == 4 && questionNo == 1) {
							editText.setText(csrq);
							break;
						}
						if (editText.getId() == 5 && questionNo == 4) {
							editText.setText(jsxx);
						}

						if (editText.getId() == 11 && questionNo == 6) {
							editText.setText(school);
						}
						if (editText.getId() == 13 && questionNo == 12) {
							editText.setText(sxzy);
						}
						Log.i("qwj",
								"===hb=editText.getId()======"
										+ editText.getId());
						Log.i("qwj", "questionNo======" + questionNo);
					}
				}

				if (tempSmallWenJuan.size() > 0
						&& !checkRadioIsChecked(answerInfo, questionNo)) {
					Toast.makeText(context, "请选择合适的答案", Toast.LENGTH_LONG)
							.show();
					Log.i("qwj",
							"===checkRadioIsChecked(answerInfo, questionNo)=="
									+ checkRadioIsChecked(answerInfo,
											questionNo));
					return;
				}
				/*
				 * WenJuanInfo info3 = answerInfo.get(indexs);// Log.i("qwj",
				 * "csacaxasxa" + indexs); Log.i("qwj", "id:" + info3.getID() +
				 * ",title" + info3.getTITLE_L());
				 */
				// for (WenJuanInfo info2 : answerInfo) {

				// Log.i("qwj", "acccccccccggjc=="+info2.getTITLE_L());
				// String str = info2.getTITLE_L();
				// String str2 = str.substring(str.length() - 2);
				// if(editTexts.size()>0){

				if (makeEdit(answerInfo))
					return;
				if (makeEdit_checkBox(answerInfo))
					return;

				if (index >= questionInfos.size() - 1) {
					Toast.makeText(context, "已经是最后一题了", Toast.LENGTH_LONG)
							.show();
					btn_all.setVisibility(View.VISIBLE);
					return;
				}
				System.out.println("index1=====" + index);
				List<WenJuanInfo> tempAnswer = new ArrayList<WenJuanInfo>();

				System.out.println("index1=====" + index);
				tempAnswer = new ArrayList<WenJuanInfo>();

				// WenJuanInfo _answer=null;
				// for (WenJuanInfo answer : answerInfo) {
				// if (answer.getPARENT_ID() == questionNo) {
				// tempAnswer.add(answer);
				// _answer = answer;
				// break;
				// }
				// }
				// 上传本题

				// 获取答案实体

				// start 提交
				showDialog();
				List<AnswerInfo> list = getAnswerInfo2(questionNo, info);
				Log.i("qwj",
						"questionNo:" + questionNo + ",size:" + list.size());

				String answerString = parseAnswerInfo(list);

				Log.i("qwj", "answerString:" + answerString);

				Map<String, Object> params = new HashMap<String, Object>();
				Map<String, String> data = new HashMap<String, String>();
				params.put("index", index);
				// 修改答案
				if (return_values.containsKey(index)) {
					String del_id2 = return_values.get(index);
					String[] del_id=del_id2.split(",");
					data.put("Receiv_id", "" + del_id[0]);

					Log.i("qwj", "Receiv_id1---------:" + del_id[0]);
					Log.i("qwj", "return_values.get(index)=======:" + return_values.get(index));
				} else {
					data.put("Receiv_id", "");
				}

				// 下一题不传mark，完全做完时传 start
				data.put("mark", "");

				data.put("Personnel_id", getIntent().getIntExtra("pid", 0) + "");

				Log.i("qwj",
						"Personnel_id1:" + getIntent().getIntExtra("pid", 0));

				data.put("position", getIntent().getIntExtra("position", 0)
						+ "");

				data.put("data", answerString);
				params.put("data", data);
				PersonTask task = new PersonTask(
						PersonTask.UPLOADWENJUAN_SET_WENJUAN_special, params);
				// task.setJsonString(JsonString)
				PersonService.newTask(task);
				btn_next_question.setVisibility(view.GONE);
				Log.i("qwj", "data=======:" + data);
				
				// end提交

				// start 记录移除码
				if (info.getPARENT_ID() == 0) {
					for (int i = 0; i < juanInfos.size(); i++) {
						if (juanInfos.get(i).getREMOVE_CODE() != "null"
								&& juanInfos.get(i).getREMOVE_CODE().length() > 0
								&& juanInfos.get(i).getPARENT_ID() == info
										.getID()
								&& list.get(0).getAnswerId() == juanInfos
										.get(i).getID()) {
							remove_code = juanInfos.get(i).getREMOVE_CODE();
							remove_index = index;
							break;
						}
					}
				}

				// end记录移除码

				// YiChaWenJuan，从已查中移除
				if (info.getMultiSelect() > 0) {
					List<YiChaWenJuan> remove = new ArrayList<YiChaWenJuan>();
					for (WenJuanInfo wenJuanInfo : juanInfos) {
						if (wenJuanInfo.getPARENT_ID() == info.getID()) {
							for (YiChaWenJuan _yicha : allchaWenJuans) {
								if (_yicha.getDETIL_ID() == wenJuanInfo.getID()) {
									remove.add(_yicha);
								}
							}
						}
					}
					for (int i = 0; i < remove.size(); i++) {
						allchaWenJuans.remove(remove.get(i));

					}
					for (int i = 0; i < list.size(); i++) {
						YiChaWenJuan _tmp = new YiChaWenJuan();
						_tmp.setDETIL_ID(list.get(i).getAnswerId());
						_tmp.setINPUT_VALUE(list.get(i).getAnswerText());
						allchaWenJuans.add(_tmp);

					}

				}
				// YiChaWenJuan，从已查中移除

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
				for (WenJuanInfo answer : answerInfo) {
					if (answer.getPARENT_ID() == questionNo) {
						tempAnswer.add(answer);
					}
				}

				// for (RadioButton radioButton : radioButtons) {
				// if (radioButton.isChecked()) {
				// for (WenJuanInfo temp : tempAnswer) {
				// if (radioButton.getId() == temp.getID()) {
				// if (temp != null
				// && !temp.getREMOVE_CODE()
				// .equals("null")
				// && !temp.getREMOVE_CODE().equals("")) {
				// // 获取要移除的字符串
				// Until.remove = temp.getREMOVE_CODE();
				// // 解析成数组
				// Until.removes = Until.remove.split(",");
				// // 加载全部题目
				// questionInfos = getQuestionByParent();
				// for (int i = 0; i < Until.removes.length; i++) {
				// // 移除要跳过的题目
				// String s = Until.removes[i];
				// // 移除要跳过的题目
				// if (s.equals("9")) {
				// questionInfos.remove(Integer
				// .parseInt(Until.removes[i]) - 1);
				// Log.i("qwj", "==questionInfos=="
				// + Until.removes[i]);
				// } else {
				// for (int j = 0; j < Until.removes[i]
				// .length() - 1; j++) {
				// questionInfos.remove(Integer
				// .parseInt(Until.removes[j]) - 1);
				// Log.i("qwj",
				// "questionInfosgfgfh=="
				// + Until.removes[j]);
				// }
				// }
				// }
				// }
				//
				// }
				// }
				// }
				// }

				// 跳过移除题目 start

				// 跳过移除题目 end

				tempAnswer.clear();
				currentInfo = questionInfos.get(index);
				if (remove_code != "null" && remove_code != null) {
					// Log.i("qwj","600-currentInfo:" +currentInfo.getNO());
					// Log.i("qwj","601-currentInfo:"
					// +String.valueOf(currentInfo.getNO()));
					int _v = (int) currentInfo.getNO();
					// Log.i("qwj","603-currentInfo:" +_v);
					String check = String.valueOf(_v);
					while (CheckRemoveCode(remove_code, check)) {
						index++;
						currentInfo = questionInfos.get(index);
						_v = (int) currentInfo.getNO();
						check = String.valueOf(_v);

					}
				}

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

				// ////////获取到该题的服务器端id，并从服务器上删除
				if (return_values.containsKey(index)) {
					if (return_values.get(index).length() > 0) {
						showDialog();
					//	String del_id = return_values.get(index);
						String del_id2 = return_values.get(index);
						String[] del_id=del_id2.split(",");
						Log.i("qwj", "501-del_id:" + del_id);

						Map<String, Object> params = new HashMap<String, Object>();
						Map<String, String> data = new HashMap<String, String>();
						params.put("index", "" + index);

						data.put("Personnel_id",
								getIntent().getIntExtra("pid", 0) + "");
						// 下一题不传mark，完全做完时传
						data.put("del", "True");
						data.put("Receiv_id", "" + del_id[0]);

						data.put("position",
								getIntent().getIntExtra("position", 0) + "");
						data.put("mark", "");
						data.put("data", "");
						params.put("data", data);
						PersonTask task = new PersonTask(
								PersonTask.UPLOADWENJUAN_SET_WENJUAN_special,
								params);
						// task.setJsonString(JsonString)
						PersonService.newTask(task);

						Log.i("qwj", "up_question2");
						btn_up_question.setVisibility(view.GONE);

						// 已做问卷列表中移除
						WenJuanInfo info = questionInfos.get(index);//
						List<YiChaWenJuan> remove = new ArrayList<YiChaWenJuan>();
						for (WenJuanInfo wenJuanInfo : juanInfos) {
							if (wenJuanInfo.getPARENT_ID() == info.getID()) {
								for (YiChaWenJuan _yicha : allchaWenJuans) {
									if (_yicha.getDETIL_ID() == wenJuanInfo.getID()) {
										remove.add(_yicha);
									}
								}
							}
						}
						for (int i = 0; i < remove.size(); i++) {
							allchaWenJuans.remove(remove.get(i));

						}
					}
				}
				Log.i("qwj", "currentInfo:" + currentInfo.getID());

				// /////////////////////////
				// 去掉答题的文本
				System.out.println("=====" + currentInfo.getID());
				/*
				 * for(EditText edt:editTexts){ if (currentInfo.getID() ==
				 * edt.getId()) { edt.setText(""); Log.i("qwj",
				 * "222===currentInfo.getID()===="+currentInfo.getID());
				 * Log.i("qwj",
				 * "222===edt.setText===="+edt.getText().toString().trim()); } }
				 */
				/*for (EditText editText : editTexts) {
					Log.i("qwj", "editTextID:" + editText.getId());
					if (currentInfo.getPARENT_ID() == editText.getId()) {
						editText.setText("");
					}

				
				}*/

				for (EditText editText : questionEditTexts) {
					Log.i("qwj", "editTextID:" + editText.getId());
					if (currentInfo.getID() == editText.getId()) {
						editText.setText("");
					}

					/*
					 * if(currentInfo.getID()==5&&"".equals(editText.getText().
					 * toString().trim())){ editText.setText(csrq); }
					 * if(currentInfo
					 * .getID()==6&&"".equals(editText.getText().toString
					 * ().trim())){ editText.setText(jsxx); }
					 * if(currentInfo.getID
					 * ()==12&&"".equals(editText.getText().toString().trim())){
					 * editText.setText(school); }
					 * if(currentInfo.getID()==14&&""
					 * .equals(editText.getText().toString().trim())){
					 * editText.setText(sxzy); } Log.i("qwj", "currentInfo===="
					 * + currentInfo.getID()); Log.i("qwj",
					 * "editText.getText().toString()===:" +
					 * editText.getText().toString());
					 */
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
					// 去掉多选按钮
					for (CheckBox checkBox : this_CheckBoxs) {
						if (checkBox.getId() == wenJuanInfo.getID()) {
							checkBox.setChecked(false);
							// Log.i("qwj", "checkBox.getId()=====:" + checkBox.getId());
							// Log.i("qwj", "wenJuanInfo.getID()=====:" + wenJuanInfo.getID());
							// Log.i("qwj", "checkBox.setChecked(false)=====:" + checkBox.isChecked());
						}
					}
				}

				// if ("next".equals(tempQuestion)) {
				// if (tempList.size() > 0) {
				// List<Integer> temp = new ArrayList<Integer>();
				// temp.addAll(tempList.subList(tempList.size() - 1,
				// tempList.size()));
				// tempQuestionIndex = temp.get(0);
				// tempList.removeAll(temp);
				// temp.clear();
				// }
				// index = tempQuestionIndex;
				// } else if("up".equals(tempQuestion)){
				// index--;
				// }
				// tempQuestion="up";

				index--;

				currentInfo = questionInfos.get(index);
				if (remove_code != "null" && remove_code != null
						&& remove_code.length() > 0) {
					int _v = (int) currentInfo.getNO();
					// Log.i("qwj","603-currentInfo:" +_v);
					String check = String.valueOf(_v);

					// String[] remove_codes = remove_code.split(",")

					while (CheckRemoveCode(remove_code, check)) {
						index--;
						currentInfo = questionInfos.get(index);
						_v = (int) currentInfo.getNO();
						check = String.valueOf(_v);
					}
				}

				// currentInfo = questionInfos.get(index);
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
	/**
	 * 做完题目，点击提交
	 */
	private class MyClickListener implements OnClickListener{
		 public void onClick(View v){
				switch (v.getId()) {
				case R.id.btn_true2:
					String ed_text=et_phone.getText().toString();
					if(ed_text.length()!=0&&ed_text.length()==8||ed_text.length()==11){
						WenJuanInfo info = questionInfos
								.get(index);//
						Log.i("qwj", "" + index);
						Log.i("qwj", "id:" + info.getID()
								+ ",title" + info.getTITLE_L());

						// 查找父id
						List<WenJuanInfo> tempSmallWenJuan = getAnswerByParentId(info);// 选项

						int questionNo = info.getID();
						if (questionEditTexts.size() > 0) {
							for (EditText editText : questionEditTexts) {
								if (editText.getId() == questionNo
										&& "".equals(editText
												.getText()
												.toString()
												.trim())) {
									Toast.makeText(context,
											"答案不能为空!",
											Toast.LENGTH_SHORT)
											.show();
									return;
								}
							}
						}

						if (tempSmallWenJuan.size() > 0
								&& !checkRadioIsChecked(
										answerInfo, questionNo)) {
							Toast.makeText(context, "请选择合适的答案",
									Toast.LENGTH_LONG).show();
							return;
						}

						if (makeEdit(answerInfo))
							return;

						System.out.println("index1====="
								+ index);
						List<WenJuanInfo> tempAnswer = new ArrayList<WenJuanInfo>();

						System.out.println("index1====="
								+ index);
						tempAnswer = new ArrayList<WenJuanInfo>();

						showDialog();
						List<AnswerInfo> list = getAnswerInfo2(
								questionNo, info);
						Log.i("qwj", "questionNo:" + questionNo
								+ ",size:" + list.size());

						String answerString = parseAnswerInfo(list);

						// Log.i("qwj", "answerString:"
						// + answerString);

						Map<String, Object> params = new HashMap<String, Object>();
						Map<String, String> data = new HashMap<String, String>();
						params.put("index", index);
						// 修改答案
						if (return_values.containsKey(index)) {
							String del_id = return_values
									.get(index);
							String del_id2 = return_values.get(index);
							String[] del_id1=del_id2.split(",");
							data.put("Receiv_id", "" + del_id1[0]);
							//data.put("Receiv_id", "" + del_id);

							Log.i("qwj", "Receiv_id1:" + del_id1);
						} else {
							data.put("Receiv_id", "");
						}

						// 下一题不传mark，完全做完时传
						data.put("mark", "完成");

						data.put("Personnel_id", getIntent()
								.getIntExtra("pid", 0) + "");

						Log.i("qwj",
								"Personnel_id1:"
										+ getIntent()
												.getIntExtra(
														"pid",
														0));

						data.put("position", getIntent()
								.getIntExtra("position", 0)
								+ "");

						data.put("data", answerString);
						data.put("mark", "调查完成");
						data.put("phone", et_phone.getText().toString().trim());
						params.put("data", data);
						PersonTask task = new PersonTask(
								PersonTask.UPLOADWENJUAN_SET_WENJUAN_special,
								params);
						Log.i("qwj", "data======"+data);
						// task.setJsonString(JsonString)
						PersonService.newTask(task);
						btn_next_question
								.setVisibility(view.GONE);

						btn_star.setVisibility(View.GONE);
						btn_upload.setEnabled(false);

						GXWenJuanDetailActivity.this.finish();
						GXWenJuanPersonActivity._instance
								.finish();
						Intent showIntent = new Intent(
								GXWenJuanDetailActivity.this,
								GXWenJuanPersonActivity.class);
						startActivity(showIntent);
						//隐藏软键盘
						InputMethodManager imm = (InputMethodManager) et_phone
				                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				        imm.hideSoftInputFromWindow(et_phone
				                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
						Toast.makeText(context, "提交完成", Toast.LENGTH_LONG).show();
					}
					else{
						InputMethodManager imm = (InputMethodManager) et_phone
				                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				        imm.hideSoftInputFromWindow(et_phone
				                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
						Toast.makeText(context, "请输入联系方式，并且只能输入8位或11为数字", Toast.LENGTH_LONG).show();
					}
					break;
				case R.id.btn_false2:
				     InputMethodManager imm = (InputMethodManager) et_phone
		                .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		        imm.hideSoftInputFromWindow(et_phone
		                .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					
					Toast.makeText(context, "尚未提交", Toast.LENGTH_LONG).show();
					dialog2.cancel();
					break;

				default:
					break;
				}
			}
	 }


	private Boolean CheckRemoveCode(String RemoveCode, String Check_string) {
		String[] remove_codes = RemoveCode.split(",");
		for (int i = 0; i < remove_codes.length; i++) {
			if (remove_codes[i].equals(Check_string)) {

				return true;
			}

		}

		return false;
	}

	// /获取单条数据
	private List<AnswerInfo> getAnswerInfo2(int id, WenJuanInfo answer) {

		List<AnswerInfo> list = new ArrayList<AnswerInfo>();
		AnswerInfo answerInfo = null;
		List<Integer> check_selects = new ArrayList<Integer>();

		if (CurrCol.size() > 0) {

			for (Object col : CurrCol) {
				if (col instanceof CheckBox) {
					CheckBox c_box = (CheckBox) col;
					if (c_box.isChecked()) {
						answerInfo = new AnswerInfo();
						check_selects.add(c_box.getId());
						answerInfo.setAnswerId(c_box.getId());// answer.getID());
						answerInfo.setAnswerNo(answer.getNO());
						answerInfo.setAnswerText("");
						list.add(answerInfo);
						//Log.i("qwj", "11111====answerInfo=====" + answerInfo);
					}
				} else if (col instanceof RadioButton) {

					RadioButton radioButton = (RadioButton) col;

					if (radioButton.isChecked()) {
						answerInfo = new AnswerInfo();
						answerInfo.setAnswerId(radioButton.getId());// answer.getID());
						answerInfo.setAnswerNo(answer.getNO());
						answerInfo.setAnswerText("");
						list.add(answerInfo);
						// Log.i("qwj", "22222====answerInfo====="+answerInfo);
					}
				} else if (col instanceof EditText) {
					if (answerInfo == null) {
						answerInfo = new AnswerInfo();
						answerInfo.setAnswerId(answer.getID());
						answerInfo.setAnswerNo(answer.getNO());
						// Log.i("qwj",
						// "5555====answer.getID()====="+answer.getID());
					}
					EditText editText = (EditText) col;

					
					// 如果是父标题的文本，加入集合
					if (answer.getPARENT_ID() == 0
							&& answer.getMultiSelect() < 1
							&& answer.getID() == answer.getID()) {
						answerInfo.setAnswerText(editText.getText().toString()
								.trim());
						list.add(answerInfo);
						continue;
					}

					for (int i = 0; i < check_selects.size(); i++) {
						if (check_selects.get(i) == editText.getId()) {
							answerInfo.setAnswerText(editText.getText()
									.toString().trim());
							continue;
						}

					}

				}
			}
		}

		return list;
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

	private String parseAnswerInfo(List<AnswerInfo> answerInfos) {
		JSONArray array = new JSONArray();
		if (answerInfos.size() > 0) {
			for (AnswerInfo answerInfo : answerInfos) {
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
							PersonTask.GXWENJUANLISTACTIVITY_GET_INFO_LIST_ANSWER,
							params1);
					PersonService.newTask(task);
				}
			}
			break;
		case Service_retuen_value:
			try {

				Log.i("qwj", "Service_retuen_value:1");
				// last_id = curr_id;
				String curr_id = params[1].toString();
				Log.i("qwj", "Service_retuen_value:2");
				// 获取到传到服务器的index 和对应值：自增id
				int index = Integer.parseInt(params[2].toString());
				
				
		
				
				return_values.put(index, curr_id);

				Log.i("qwj", "curr_id:" + curr_id);

				if (dialog != null && dialog.isShowing()) {
					dialog.cancel();
				}
				btn_next_question.setVisibility(view.VISIBLE);
				btn_up_question.setVisibility(view.VISIBLE);
			} catch (Exception e) {

			}
			break;

		case GetReceiv_Special:

			try {

				// Log.i("qwj", "json:" + params[1].toString());
				List<YiChaWenJuan> yicha = parseAnswerInfo(params[1].toString()
						.trim());
				if (yicha != null && yicha.size() > 0) {
					Log.i("qwj", "jsonsize:" + yicha.size());

					allchaWenJuans.addAll(yicha);

					int detil_id = yicha.get(yicha.size() - 1).getDETIL_ID();
					int Receiv_id = yicha.get(yicha.size() - 1).getID();

					List<WenJuanInfo> newJuanInfos = typeMap.getJuanInfos();
					if (newJuanInfos.size() > 0) {
						juanInfos.clear();
						radioButtons.clear();
						editTexts.clear();
						questionEditTexts.clear();
						int index = 0;

						juanInfos.addAll(newJuanInfos);
					}

					questionInfos = getQuestionByParent();

					for (WenJuanInfo wenJuanInfo : juanInfos) {
						if (wenJuanInfo.getID() == detil_id) {
							if (wenJuanInfo.getPARENT_ID() != 0) {
								detil_id = wenJuanInfo.getPARENT_ID();
								Log.i("qwj", "wenJuanInfo.getPARENT_ID()=="
										+ wenJuanInfo.getPARENT_ID());
							}
							break;
						}
					}

					for (WenJuanInfo wenJuanInfo : questionInfos) {
						if (wenJuanInfo.getID() == detil_id) {
							index = questionInfos.indexOf(wenJuanInfo);
							currentInfo = wenJuanInfo;
							break;

						}
					}
					Log.i("qwj", "index:" + index);

					// start,记录remove_code,remove_index
					for (int j = 0; j < juanInfos.size(); j++) {
						if (juanInfos.get(j).getREMOVE_CODE() != "null"
								&& juanInfos.get(j).getREMOVE_CODE().length() > 0) {
							for (int i = 0; i < yicha.size(); i++) {
								if (yicha.get(i).getDETIL_ID() == juanInfos
										.get(j).getID()) {
									remove_code = juanInfos.get(j)
											.getREMOVE_CODE();

									Log.i("qwj", "1036-remove_code:"
											+ remove_code);
									break;
								}

							}
						}
					}

					// end,记录remove_code,remove_index

					// return_values.put(index, ""+Receiv_id);
					// 显示当前题
					fretchTree(llDuty, questionInfos.get(index), "");

					// 记录服务器已存集合----
					if (index > -1) {
						int _index = 0;
						for (int i = 0; i < questionInfos.size(); i++) {
							tempList.add(i);
							String Receiv_id_s = "";

							for (WenJuanInfo wenJuanInfo : juanInfos) {
								if (wenJuanInfo.getPARENT_ID() == questionInfos
										.get(i).getID()
										|| wenJuanInfo.getID() == questionInfos
												.get(i).getID()) {
									for (YiChaWenJuan _yicha : yicha) {
										if (_yicha.getDETIL_ID() == wenJuanInfo
												.getID()) {
											Receiv_id_s += "" + _yicha.getID()
													+ ",";
										}
									}
								}
							}
							if (Receiv_id_s.length() > 0) {
								return_values.put(_index, Receiv_id_s);

							} else {
								return_values.put(_index, "");
							}
							_index++;
						}
						Log.i("qwj",
								"980-Receiv_id_s:" + return_values.get(index));
					}

					// 记录服务器已存集合----
					btn_next_question.setVisibility(View.VISIBLE);
					btn_up_question.setVisibility(View.VISIBLE);
					btn_all.setVisibility(View.GONE);
					btn_star.setVisibility(View.GONE);
					btn_upload.setVisibility(View.GONE);
				}

			} catch (Exception e) {
				Log.i("qwj", "969:" + e.getMessage());
			}

			break;
		default:
			break;
		}
	}

	private void showDialog() {
		if (dialog == null)
			dialog = new ProgressDialog(context);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("正在提交数据...");
		dialog.show();
	}

	// /数据填充
	private void fretchTree(LinearLayout layout, WenJuanInfo info, String isAll) {
		if ("".equals(isAll)) {
			llDuty.removeAllViews();
		}
		CurrCol.clear();
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

		if (rb) {
			bindinfo = info.getBindInfo();
		}
		if (info.isINPUT()) {
			// bindinfo = info.getBindInfo();

			EditText editText = new EditText(context);
			// editText.setOnKeyListener(new MyOnKeyListener(editText));
			CurrCol.add(editText);
			LinearLayout.LayoutParams edit;
			edit = new LinearLayout.LayoutParams(280, 70);
			edit.setMargins(0, 5, 0, 0);
			editText.setTextSize(28);
			editText.setLayoutParams(edit);

			if ("int".equals(info.getINPUT_TYPE())) {
				editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				// 把个人信息中已存在的显示出来
				if ("出生日期".equals(bindinfo)
						&& "".equals(editText.getText().toString().trim())) {
					editText.setText(csrq);
				}
				if ("终止日期".equals(bindinfo)
						&& "".equals(editText.getText().toString().trim())) {
					editText.setText(jsxx);
				}
				Log.i("qwj", "info.getBindInfo()=" + bindinfo);

			} else {
				if ("学校".equals(bindinfo)
						&& "".equals(editText.getText().toString().trim())) {
					editText.setText(school);
				}
				if ("专业".equals(bindinfo)
						&& "".equals(editText.getText().toString().trim())) {
					editText.setText(sxzy);
				}
				Log.i("qwj", "info.getBindInfo()=" + info.getBindInfo());
				// EditText的布局
				LinearLayout edlinearLayout = new LinearLayout(context);
				qlinearLayout.setLayoutParams(allparam);
				qlinearLayout.setOrientation(LinearLayout.VERTICAL);
				EditText ed = new EditText(context);
				edit = new LinearLayout.LayoutParams(1200, 400);
				editText.setLayoutParams(edit);
				editText.setTextSize(28);
				edlinearLayout.addView(ed, allparam);
				editText.setGravity(editText.getTop());

			}

			if (questionEditTexts.size() > 0) {
				List<EditText> tempEditTexts = new ArrayList<EditText>();
				for (EditText editText2 : questionEditTexts) {
					if (editText2.getId() == info.getID()) {
						editText.setText(editText2.getText());
						tempEditTexts.add(editText2);
					}

					Log.i("qwe", "editText2.getId()==" + editText2.getId());
					Log.i("qwe", "info.getID()==" + info.getID());
					Log.i("qwe", "editText2.getText()==" + editText2.getText());
					Log.i("qwe", "editText.getText()==" + editText.getText());
				}
				questionEditTexts.removeAll(tempEditTexts);
				Log.i("qwj", "questionEditTexts==" + questionEditTexts);
				Log.i("qwj", "tempEditTexts==" + tempEditTexts);
				tempEditTexts.clear();
			}

			editText.setId(info.getID());
			Log.i("qwe", "========editText.getId()==" + editText.getId());
			Log.i("qwe", "========info.getID()==" + info.getID());
			if (allchaWenJuans.size() > 0) {
				for (YiChaWenJuan yiChaWenJuan : allchaWenJuans) {
					if (yiChaWenJuan.getDETIL_ID() == editText.getId()) {
						editText.setText(yiChaWenJuan.getINPUT_VALUE());

						Log.i("qwj", "yiChaWenJuan.getINPUT_VALUE()=="
								+ yiChaWenJuan.getINPUT_VALUE());
					}
				}
			}

			if ("all".equals(isAll)) {
				editText.setEnabled(false);
			}
			questionEditTexts.add(editText);
			Log.i("qwe", "========editText==" + editText);
			qlinearLayout.addView(editText, edit);
			if (!"".equals(info.getTITLE_R())) {
				TextView rTextView = new TextView(context);
				rTextView.setLayoutParams(allparam);
				rTextView.setTextSize(30);
				rTextView.setText(info.getTITLE_R());
				qlinearLayout.addView(rTextView, allparam);
				Log.i("qwj", "info.getTITLE_R()=" + allparam);
			}
		}
		alllinearLayout.addView(qlinearLayout, allparam);

		LinearLayout aLinearLayout = new LinearLayout(context);
		aLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		aLinearLayout.setLayoutParams(allparam);

		RadioGroup radioGroup = new RadioGroup(context);
		radioGroup.setPadding(60, 0, 0, 0);
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
		Log.i("qwe", "allanswerEditInfo=" + allanswerEditInfo);
		if (info.getMultiSelect() > 1) {

			List<CheckBox> CheckBoxGroup = new ArrayList<CheckBox>();

			for (WenJuanInfo wenJuanInfo : answerInfo) {
				fretchTreeByQuestionMultiSelect(CheckBoxGroup, radioGroup,
						wenJuanInfo, xuanxiangLinearLayout, isAll,
						info.getMultiSelect());
			}

		} else {
			for (WenJuanInfo wenJuanInfo : answerInfo) {
				fretchTreeByQuestion(radioGroup, wenJuanInfo,
						xuanxiangLinearLayout, isAll);
			}
		}

		aLinearLayout.addView(radioGroup, allparam);
		aLinearLayout.addView(xuanxiangLinearLayout, allparam);

		alllinearLayout.addView(aLinearLayout, allparam);

		layout.addView(alllinearLayout, allparam);
	}

	private void fretchTreeByQuestionMultiSelect(List<CheckBox> CheckBoxGroup,
			RadioGroup group, WenJuanInfo wenJuanInfo,
			LinearLayout xuanxiangLinearLayout, String isAll, int MultiSelect) {
		LinearLayout linearLayout = new LinearLayout(context);
		LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, 55);
		linearLayout.setLayoutParams(param1);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);

		CheckBox CheckButton = new CheckBox(context);
		CurrCol.add(CheckButton);
		CheckButton.setOnCheckedChangeListener(new MyOnCheckedChangeListener(
				CheckBoxGroup, MultiSelect));
		LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, 55);
		CheckButton.setLayoutParams(param2);
		CheckButton.setId(wenJuanInfo.getID());
		group.addView(CheckButton);
		if (this_CheckBoxs.size() > 0) {
			List<CheckBox> tempRadioButtons = new ArrayList<CheckBox>();
			for (CheckBox _CheckBox : this_CheckBoxs) {
				if (_CheckBox.getId() == wenJuanInfo.getID()
						&& _CheckBox.isChecked()) {
					_CheckBox.setChecked(true);
					tempRadioButtons.add(_CheckBox);
				}
			}
			radioButtons.removeAll(tempRadioButtons);
			tempRadioButtons.clear();

		}

		if (allchaWenJuans.size() > 0) {
			for (YiChaWenJuan yiChaWenJuan : allchaWenJuans) {

				if (yiChaWenJuan.getDETIL_ID() == CheckButton.getId()) {
					
					CheckButton.setChecked(true);

				}
			}
		}

		if ("all".equals(isAll)) {
			CheckButton.setEnabled(false);
		}

		
		for(int i=0;i<this_CheckBoxs.size();i++)
		{
			if(this_CheckBoxs.get(i).getId()==CheckButton.getId())
			{
				this_CheckBoxs.remove(i);
				break;
			}
		}
		this_CheckBoxs.add(CheckButton);
		if (wenJuanInfo.isINPUT()) {
			LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, 45);
			TextView textView = new TextView(context);
			textView.setGravity(Gravity.CENTER);
			textView.setLayoutParams(text);
			textView.setTextSize(28);
			textView.setText(wenJuanInfo.getTITLE_L());
			linearLayout.addView(textView, text);
			EditText editText = new EditText(context);
			editText.setTextSize(24);
			editText.setOnKeyListener(new MyOnKeyListener(editText));
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

			CurrCol.add(editText);
			editTexts.add(editText);
			LinearLayout.LayoutParams edit = new LinearLayout.LayoutParams(460,
					60);
			//edit.setMargins(0, 3, 0, 0);

			editText.setLayoutParams(edit);
			linearLayout.addView(editText, edit);

			if (!"".equals(wenJuanInfo.getTITLE_R())) {
				LinearLayout.LayoutParams rParams = new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT, 45);
				TextView rTextView = new TextView(context);
				textView.setLayoutParams(rParams);
				rTextView.setTextSize(28);
				rTextView.setText(wenJuanInfo.getTITLE_R());
				linearLayout.addView(rTextView, rParams);
			}
		} else {
			TextView textView = new TextView(context);
			LinearLayout.LayoutParams textparams = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, 45);
			textView.setGravity(Gravity.CENTER);
			CheckButton.setId(wenJuanInfo.getID());
			textView.setText(wenJuanInfo.getTITLE_L());
			textView.setTextSize(28);
			linearLayout.addView(textView, textparams);
		}
		xuanxiangLinearLayout.addView(linearLayout, param1);
	}

	private void fretchTreeByQuestion(RadioGroup group,
			WenJuanInfo wenJuanInfo, LinearLayout xuanxiangLinearLayout,
			String isAll) {
		LinearLayout linearLayout = new LinearLayout(context);
		LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, 55);
		linearLayout.setLayoutParams(param1);
		linearLayout.setOrientation(LinearLayout.HORIZONTAL);

		RadioButton radioButton = new RadioButton(context);
		LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT, 55);
		
		radioButton.setLayoutParams(param2);
		radioButton.setId(wenJuanInfo.getID());
		group.addView(radioButton);
		String str = wenJuanInfo.getTITLE_L();
		//Log.i("qwj", "wenJuanInfo.getTITLE_L()" + bindinfo);
		if ("性别".equals(bindinfo)) {
			if (str.contains(sex)) {
				radioButton.setChecked(true);
			}

			Log.i("qwj", "str.contains(sex)" + str.contains(sex));
			// Toast.makeText(this, sex, Toast.LENGTH_LONG).show();
		}
		if ("学历".equals(bindinfo)) {
			if (str.contains(edu)) {
				radioButton.setChecked(true);
			}
			if (str.contains("硕士") && edu.equals("研究生")) {
				radioButton.setChecked(true);
			}

			Log.i("qwj", "str.contains(edu)" + str.contains(edu));
			// Toast.makeText(this, edu, Toast.LENGTH_LONG).show();
		}
		if (radioButtons.size() > 0) {
			List<RadioButton> tempRadioButtons = new ArrayList<RadioButton>();
			for (RadioButton radioButton2 : radioButtons) {
				if (radioButton2.getId() == wenJuanInfo.getID()
						&& radioButton2.isChecked()) {
					radioButton.setChecked(true);
					tempRadioButtons.add(radioButton2);
					Log.i("qwj", "radioButton2.getId()=" + radioButton2.getId());
				}

			}
			radioButtons.removeAll(tempRadioButtons);
			tempRadioButtons.clear();
		}

		if (allchaWenJuans.size() > 0) {
			for (YiChaWenJuan yiChaWenJuan : allchaWenJuans) {

				if (yiChaWenJuan.getDETIL_ID() == radioButton.getId()) {
					radioButton.setChecked(true);

					// Log.i("qwj",
					// "yiChaWenJuan.getDETIL_ID()="+yiChaWenJuan.getDETIL_ID());
				}

			}
		}

		if ("all".equals(isAll)) {
			radioButton.setEnabled(false);
		}
		CurrCol.add(radioButton);
		radioButtons.add(radioButton);
		if (wenJuanInfo.isINPUT()) {
			LinearLayout.LayoutParams text = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.WRAP_CONTENT, 45);
			TextView textView = new TextView(context);
			textView.setGravity(Gravity.CENTER);
			textView.setLayoutParams(text);
			textView.setTextSize(28);
			textView.setText(wenJuanInfo.getTITLE_L());
			linearLayout.addView(textView, text);
			EditText editText = new EditText(context);
			editText.setTextSize(24);
			editText.setOnKeyListener(new MyOnKeyListener(editText));
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

			CurrCol.add(editText);
			editTexts.add(editText);
			LinearLayout.LayoutParams edit = new LinearLayout.LayoutParams(460,
					60);
			editText.setLayoutParams(edit);
			linearLayout.addView(editText, edit);

			if (!"".equals(wenJuanInfo.getTITLE_R())) {
				LinearLayout.LayoutParams rParams = new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT, 45);
				TextView rTextView = new TextView(context);
				textView.setLayoutParams(rParams);
				rTextView.setTextSize(28);
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
			textView.setTextSize(28);
			linearLayout.addView(textView, textparams);
		}
		xuanxiangLinearLayout.addView(linearLayout, param1);
	}

	private List<WenJuanInfo> getQuestionByParent() {
		questionInfos = new ArrayList<WenJuanInfo>();
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
				info.setREMOVE_CODE(object.getString("REMOVE_CODE"));
				info.setRecordCount(object.getInt("RecordCount"));
				info.setTITLE_L(object.getString("TITLE_L"));
				info.setTITLE_R(object.getString("TITLE_R"));
				info.setMultiSelect(object.getInt("MultiSelect"));
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
		tv_no.setText(Html.fromHtml("<u>" + typeMap.getNO() + "</u>"));
		tv_title.setText(typeMap.getTITLE());
		tv_time.setText(typeMap.getCREATE_TIME().substring(0, 10));
		webView.loadUrl(HttpUrls_.HttpURL
				+ "/json/Get_Qa_Master_Text_Special.aspx?master_id=1");
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

	private boolean makeEdit_checkBox(List<WenJuanInfo> infos) {
		if (editTexts.size() > 0) {
			for (CheckBox radioButton : this_CheckBoxs) {
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
		for (CheckBox _CheckBox : this_CheckBoxs) {
			for (WenJuanInfo wenJuanInfo : infos) {
				if (_CheckBox.getId() == wenJuanInfo.getID()
						&& wenJuanInfo.getPARENT_ID() == questionId
						&& _CheckBox.isChecked()) {
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
						GXWenJuanDetailActivity.this.finish();
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
				info.setREMOVE_CODE(object.getString("REMOVE_CODE"));
				info.setTITLE_L(object.getString("TITLE_L"));
				info.setTITLE_R(object.getString("TITLE_R"));
				info.setMultiSelect(object.getInt("MULTISELECT"));
				info.setBindInfo(object.getString("BindInfo"));
				newInfos.add(info);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newInfos;
	}

	public class MyOnKeyListener implements OnKeyListener {
		public MyOnKeyListener(EditText EditText) {
			_EditText = EditText;

		}

		EditText _EditText;

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {

			/*
			 * if(event.getKeyCode() == 13) return true;
			 */

			if (event.getKeyCode() == 67)
				return false;

			Log.i("qwj", "_EditText:" + _EditText.getText());

			// 去掉回车\r\n
			_EditText.setText(_EditText.getText().toString().replace("\r", "")
					.replace("\n", ""));
			// 让输入光标在尾部
			_EditText.setSelection(_EditText.getText().toString().length());

			if (_EditText.getText().toString().length() < 10) {
				LinearLayout.LayoutParams edit = new LinearLayout.LayoutParams(
						460, 60);
				_EditText.setLayoutParams(edit);
			} else {
				LinearLayout.LayoutParams edit = new LinearLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, 60);
				_EditText.setLayoutParams(edit);
			}
			// TODO Auto-generated method stub

			return false;
		}

	}

	public class MyOnCheckedChangeListener implements OnCheckedChangeListener {
		int _MultiSelect;
		List<CheckBox> _group;

		public MyOnCheckedChangeListener(List<CheckBox> group, int MultiSelect) {

			_group = group;
			_MultiSelect = MultiSelect;
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {

			if (isChecked) {
				if (_group.size() > _MultiSelect - 1) {
					buttonView.setChecked(false);
					Toast.makeText(context, "最多选" + _MultiSelect + "项",
							Toast.LENGTH_SHORT).show();
				} else {
					_group.add((CheckBox) buttonView);
				}
			} else {
				CheckBox _check_box = (CheckBox) buttonView;
				if (_group.contains(_check_box)) {
					_group.remove(_check_box);
				}
			}
			// TODO Auto-generated method stub

		}

	}

}

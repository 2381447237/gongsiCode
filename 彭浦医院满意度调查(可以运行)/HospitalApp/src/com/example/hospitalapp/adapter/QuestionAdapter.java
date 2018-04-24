package com.example.hospitalapp.adapter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.example.hospitalapp.R;
import com.example.hospitalapp.nonetwork.entity.FormatDetailedQuestionModel;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


public class QuestionAdapter extends BaseExpandableListAdapter {

    private List<FormatDetailedQuestionModel.ParentModel> mList;
    private LayoutInflater inflater;
    private Map<Integer, FormatDetailedQuestionModel.ParentModel> tempBoxMap = new ConcurrentHashMap<>();
    private Map<Integer, FormatDetailedQuestionModel.ParentModel> tempRadioMap = new ConcurrentHashMap<>();
    private OnNotifyDataListener listener;
    private String tempJumpCode;
    private int currentChildIndex;
    private int currentParentIndex;
    Context context;
    public QuestionAdapter(Context context, List<FormatDetailedQuestionModel.ParentModel> list) {
        this.mList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return mList.size();
        //return  mList==null?0:mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mList.get(groupPosition).getAnswers().size();
      //  return mList.get(groupPosition).getAnswers()==null?0:mList.get(groupPosition).getAnswers().size();
    }

    @Override
    public FormatDetailedQuestionModel.ParentModel getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public FormatDetailedQuestionModel.AnswerModel getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).getAnswers().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final ParentViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.answer_list_parent_view_item, null);
            holder = new ParentViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tv_answer_list_parent);
            holder.pet=(EditText) convertView.findViewById(R.id.et_answer_list_parent);
            convertView.setTag(holder);
        } else {
            holder = (ParentViewHolder) convertView.getTag();
        }
        FormatDetailedQuestionModel.ParentModel parentModel = mList.get(groupPosition);
      
        if (parentModel.isAnswerTitle()) {
            holder.title.setTextSize(26);
            holder.title.setText("        "+mList.get(groupPosition).getParentTitle());//八个空格
           
        } else{
            holder.title.setTextSize(30);
           holder.title.setText("    "+mList.get(groupPosition).getParentTitle());//四个空格
           
        }
        
        holder.pet.setTag(parentModel);//我加的
        
        if(TextUtils.equals(parentModel.getParentType(),"string")){
        	
        	ViewGroup.LayoutParams lp=holder.pet.getLayoutParams();
			lp.width=150;
			holder.pet.setGravity(Gravity.CENTER);
			holder.pet.setLayoutParams(lp);
			holder.pet.setVisibility(View.VISIBLE);
        	
        	if(parentModel.getParentTitle().indexOf("宝贵意见") !=-1){
        		ViewGroup.LayoutParams lp2=holder.pet.getLayoutParams();
				lp2.width=ViewGroup.LayoutParams.MATCH_PARENT;
				holder.pet.setLayoutParams(lp2);
				holder.pet.setGravity(0);
				holder.pet.setVisibility(View.VISIBLE);
        	}
        	
        	
        }else{
        
        	holder.pet.setVisibility(View.GONE);
        }
        
        holder.pet.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable editable) {
				FormatDetailedQuestionModel.ParentModel model = (FormatDetailedQuestionModel.ParentModel) holder.pet
						.getTag();
				model.setpSuggestionText(holder.pet.getText().toString());

				// 屏蔽|符号
				if (editable.length() > 0) {
					int pos = editable.length() - 1;
					char c = editable.charAt(pos);
					if (c == '|') {
						editable.delete(pos, pos + 1);
					}
				}
			}
		});
		holder.pet.setText(TextUtils.isEmpty(parentModel.getpSuggestionText()) ? ""
						: parentModel.getpSuggestionText());
		
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
                             final ViewGroup parent) {
        final ChildViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.answer_list_child_view_item, null);
            holder = new ChildViewHolder();
            holder.rb = (RadioButton) convertView.findViewById(R.id.rb_answer_child_view_1);
            holder.cb = (CheckBox) convertView.findViewById(R.id.cb_answer_child_view_1);
            holder.tv = (TextView) convertView.findViewById(R.id.tv_answer_detail_child_1);
            holder.et = (EditText) convertView.findViewById(R.id.et_answer_detail_child_1);
           // holder.view=convertView.findViewById(R.id.child_view);
            convertView.setTag(holder);
        } else {
            holder = (ChildViewHolder) convertView.getTag();
        }
        final FormatDetailedQuestionModel.AnswerModel answerModel = getChild(groupPosition, childPosition);
        holder.tv.setTag(answerModel);
        if(!TextUtils.isEmpty(answerModel.getTitleend())&&!TextUtils.equals("null",answerModel.getTitleend())){
        	holder.tv.setTextSize(21);
        holder.tv.setText("  "+answerModel.getTitleend());
        }else{
     	   holder.tv.setText(null);
        }
        holder.rb.setTextSize(21);
        holder.rb.setText("  "+answerModel.getAnswerDetail()+"    ");
        holder.cb.setTextSize(21);
        holder.cb.setText("  "+answerModel.getAnswerDetail()+"    ");

        holder.et.setTextSize(20);
        
        if (getGroup(groupPosition).isAnswerType()) {  	
            holder.rb.setVisibility(View.GONE);
            holder.cb.setVisibility(View.VISIBLE);
        } else {
            holder.rb.setVisibility(View.VISIBLE);
            holder.cb.setVisibility(View.GONE);
        }
        if (answerModel.isInput()) {
            holder.et.setVisibility(View.VISIBLE);
        } else {
            holder.et.setVisibility(View.GONE);
        }
        if ("int".equals(answerModel.getInputType())) {
            holder.et.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            holder.et.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        holder.rb.setChecked(answerModel.getChecked());
        holder.cb.setChecked(answerModel.getChecked());
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jumpCode = mList.get(groupPosition).getAnswers().get(childPosition).getJumpCode();
                if (answerModel.getChecked()) {
                    answerModel.setChecked(false);
                    checked(jumpCode, true);
                } else {
                    answerModel.setChecked(true);
                    int codeIndex = -1;
                    if (!TextUtils.isEmpty(jumpCode)) {
                        for (int y = groupPosition; y < mList.size(); y++) {
                            String code = mList.get(y).getCode();
                            if (jumpCode.equals(code)) {
                                codeIndex = y;
                                break;
                            }
                        }

                        for (int x = groupPosition + 1; x < codeIndex; x++) {
                            tempBoxMap.put(mList.get(x).getDataIndex(), mList.get(x));
                        }
                        if (tempBoxMap != null && tempBoxMap.size() > 0) {
                            for (int i : tempBoxMap.keySet()) {
                                mList.remove(tempBoxMap.get(i));
                            }
                        }
                    }
                }
                notifyDataSetChanged();
            }

        });

        holder.rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String jumpCode = mList.get(groupPosition).getAnswers().get(childPosition).getJumpCode();
                if (answerModel.getChecked()) {
                    answerModel.setChecked(false);
                    checked(jumpCode, false);
                } else {
                    answerModel.setChecked(true);
                    List<FormatDetailedQuestionModel.AnswerModel> answers = getGroup(groupPosition).getAnswers();
                    for (int i = 0; i < answers.size(); i++) {
                        if (i != childPosition) {
                            answers.get(i).setChecked(false);
                        }
                    }
                    int codeIndex = -1;
                    if (!TextUtils.isEmpty(jumpCode)) {
                        for (int y = groupPosition; y < mList.size(); y++) {
                            String code = mList.get(y).getCode();
                            if (jumpCode.equals(code)) {
                                codeIndex = y;
                                break;
                            }
                        }
                        for (int x = groupPosition + 1; x < codeIndex; x++) {
                            tempRadioMap.put(mList.get(x).getDataIndex(), mList.get(x));
                        }
                        if (tempRadioMap != null && tempRadioMap.size() > 0) {
                            for (int i : tempRadioMap.keySet()) {
                               mList.remove(tempRadioMap.get(i));
                            }
                        }
                        mList.get(groupPosition).setSameGrop(true);
                    } else {
                            if (mList.get(groupPosition).isSameGrop() && tempRadioMap != null && tempRadioMap.size() > 0) {
                                mList.get(groupPosition).setSameGrop(false);
                                for (int i : tempRadioMap.keySet()) {
                                    mList.add(i, tempRadioMap.get(i));
                                    tempRadioMap.remove(i);
                                }
                                Collections.sort(mList, new Comparator<FormatDetailedQuestionModel.ParentModel>() {
                                    @Override
                                    public int compare(FormatDetailedQuestionModel.ParentModel parentModel, FormatDetailedQuestionModel.ParentModel t1) {

                                        return parentModel.getDataIndex() - t1.getDataIndex();
                                    }
                                });
                            }
                        }
                    }
                currentChildIndex = childPosition;
                currentParentIndex = groupPosition;
                tempJumpCode = jumpCode;
                notifyDataSetChanged();
            }
        });
        holder.et.setTag(answerModel);
        holder.et.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                FormatDetailedQuestionModel.AnswerModel model = (FormatDetailedQuestionModel.AnswerModel) holder.et.getTag();
                model.setSuggestionText(holder.et.getText().toString());
                
              //屏蔽|符号
                if(editable.length()>0){
                	
                	int pos=editable.length()-1;
                	char c=editable.charAt(pos);
                	if(c=='|'){
                		editable.delete(pos, pos+1);
                	}
                	
                }
            }
        });
        holder.et.setText(TextUtils.isEmpty(answerModel.getSuggestionText()) ? "" : answerModel.getSuggestionText());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if(listener!=null){
        listener.notifyList();
        }
    }

    public interface OnNotifyDataListener {
        void notifyList();
    }

    public void setOnNotifyDataListener(OnNotifyDataListener listener) {
        this.listener = listener;
    }

    public void checked(String jumpCode, boolean isCheckBox) {
        if (isCheckBox){
            if (!TextUtils.isEmpty(jumpCode)) {
                if (tempBoxMap != null && tempBoxMap.size() > 0) {
                    for (int i : tempBoxMap.keySet()) {
                        mList.add(i, tempBoxMap.get(i));
                        tempBoxMap.remove(i);
                    }
                    Collections.sort(mList, new Comparator<FormatDetailedQuestionModel.ParentModel>() {
                        @Override
                        public int compare(FormatDetailedQuestionModel.ParentModel parentModel, FormatDetailedQuestionModel.ParentModel t1) {

                            return parentModel.getDataIndex() - t1.getDataIndex();
                        }
                    });
                }
            }else {
                if (tempRadioMap != null && tempRadioMap.size() > 0) {
                    for (int i : tempRadioMap.keySet()) {
                        mList.add(i, tempRadioMap.get(i));
                        tempRadioMap.remove(i);
                    }
                    Collections.sort(mList, new Comparator<FormatDetailedQuestionModel.ParentModel>() {
                        @Override
                        public int compare(FormatDetailedQuestionModel.ParentModel parentModel, FormatDetailedQuestionModel.ParentModel t1) {

                            return parentModel.getDataIndex() - t1.getDataIndex();
                        }
                    });
                }
            }
        }

    }

    static class ParentViewHolder {
        TextView title;
        EditText pet;
    }

    static class ChildViewHolder {
        RadioButton rb;
        CheckBox cb;
        TextView tv;
        EditText et;
        View view;
    }

    abstract class SimpleTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
    }

}

package com.example.hospitalapp.utils;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.example.hospitalapp.DetailedQuestion;
import com.example.hospitalapp.nonetwork.entity.DetailedQuestionModel;
import com.example.hospitalapp.nonetwork.entity.FormatDetailedQuestionModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zl on 2016/10/23.
 */

public abstract class Json2ModelAsync extends AsyncTask<Void, Void, List<DetailedQuestionModel>>{

    private Context context;
    private int index = -1;
    private String json;
    public Json2ModelAsync(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List<DetailedQuestionModel> doInBackground(Void... params) {
        //String json = IOStreamUtil.getJson(context);
    	
    	if(!TextUtils.isEmpty(DetailedQuestion.myJson)){
    		json=DetailedQuestion.myJson;
    		
    		//Log.i("2016-10-27","MyJson="+json);
    		
    	}
    	
        Gson gson = new Gson();
        List<DetailedQuestionModel> list = new ArrayList<DetailedQuestionModel>();
        Type type = new TypeToken<ArrayList<DetailedQuestionModel>>() {}.getType();
        list = gson.fromJson(json, type);
       // Log.i("2016-10-27","list="+list);
        return list;
    }

    @Override
    protected void onPostExecute(List<DetailedQuestionModel> result) {
        if (result != null && result.size() > 0 ) {
            ArrayList<FormatDetailedQuestionModel.ParentModel> parentList = new ArrayList<FormatDetailedQuestionModel.ParentModel>();
            for (DetailedQuestionModel answerDetailModel : result) {
                if (answerDetailModel.getPARENTID() == 0 && answerDetailModel.isISANSWER() == false) {
                    index ++;
                    FormatDetailedQuestionModel.ParentModel parentModel = new FormatDetailedQuestionModel.ParentModel();
                    parentModel.setCode(answerDetailModel.getCODE());
                    parentModel.setParentTitle(answerDetailModel.getTITLE());
                    parentModel.setAnswerTitle(false);
                    List<FormatDetailedQuestionModel.AnswerModel> answerModels = new ArrayList<FormatDetailedQuestionModel.AnswerModel>();
                    parentModel.setAnswers(answerModels);
                    parentModel.setDataIndex(index);
                    parentModel.setTitle(true);
                    parentModel.setMasterId(answerDetailModel.getMASTERID());
                    parentList.add(parentModel);
                }
                FormatDetailedQuestionModel.ParentModel childModel = null;
                if (answerDetailModel.isISANSWER() == true && answerDetailModel.getTITLE().indexOf("多选") != -1) {
                    index++;
                    childModel = new FormatDetailedQuestionModel.ParentModel();
                    childModel.setParentTitle(answerDetailModel.getTITLE());
                    childModel.setCode(answerDetailModel.getCODE());
                    childModel.setAnswerType(true);
                    childModel.setAnswerTitle(true);
                    childModel.setTitle(false);
                    childModel.setMasterId(answerDetailModel.getMASTERID());
                    List<FormatDetailedQuestionModel.AnswerModel> answerModels = new ArrayList<FormatDetailedQuestionModel.AnswerModel>();
                    childModel.setAnswers(answerModels);
                    childModel.setDataIndex(index);
                    parentList.add(childModel);
                }

                if (answerDetailModel.isISANSWER() == true && answerDetailModel.getTITLE().indexOf("多选") == -1) {
                    index++;
                    childModel = new FormatDetailedQuestionModel.ParentModel();
                    childModel.setParentTitle(answerDetailModel.getTITLE());
                    childModel.setCode(answerDetailModel.getCODE());
                    childModel.setAnswerType(false);
                    childModel.setAnswerTitle(true);
                    childModel.setTitle(false);
                    childModel.setMasterId(answerDetailModel.getMASTERID());
                    List<FormatDetailedQuestionModel.AnswerModel> answerModels = new ArrayList<FormatDetailedQuestionModel.AnswerModel>();
                    childModel.setAnswers(answerModels);
                    childModel.setDataIndex(index);
                    
                    childModel.setParentType(answerDetailModel.getINPUTTYPE());//我加的
                    childModel.setpId(answerDetailModel.getID());//我加的
                    parentList.add(childModel);
                }

                if (answerDetailModel.getPARENTID() != 0 && answerDetailModel.isISANSWER() == false) {
                    FormatDetailedQuestionModel.AnswerModel answerModel = new FormatDetailedQuestionModel.AnswerModel();
                    answerModel.setInput(answerDetailModel.isISINPUT());
                    answerModel.setAnswerDetail(answerDetailModel.getTITLE());
                    answerModel.setInputType(answerDetailModel.getINPUTTYPE());
                    answerModel.setJumpCode(answerDetailModel.getJUMP_CODE());
                    answerModel.setId(answerDetailModel.getID());
                    answerModel.setTitleend(answerDetailModel.getTITLE_END());
                    parentList.get(index).getAnswers().add(answerModel);
                }
            }
            onPostExecute(parentList);
        }
    }

    public abstract void onPostExecute(ArrayList<FormatDetailedQuestionModel.ParentModel> parentList);
}

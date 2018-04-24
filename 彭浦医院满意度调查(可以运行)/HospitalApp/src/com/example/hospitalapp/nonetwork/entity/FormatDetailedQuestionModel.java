package com.example.hospitalapp.nonetwork.entity;

import java.io.Serializable;
import java.util.List;

public class FormatDetailedQuestionModel implements Serializable {

	public static class ParentModel implements Serializable {
		private String parentTitle;
		private String code;
		private List<AnswerModel> answers;
		private int dataIndex;
		private int masterId;
		private boolean isTitle;

		private boolean answerType;
		private boolean isAnswerTitle;
		private boolean select;
		private boolean isSameGrop;
		
		private String parentType;//我加的
		private boolean isPinput;//我加的
		private String pSuggestionText;//我加的
		private int pId;//我加的
		
		public int getpId() {
			return pId;
		}

		public void setpId(int pId) {
			this.pId = pId;
		}

		public String getpSuggestionText() {
			return pSuggestionText;
		}

		public void setpSuggestionText(String pSuggestionText) {
			this.pSuggestionText = pSuggestionText;
		}

		public boolean isPinput() {
			return isPinput;
		}

		public void setPinput(boolean isPinput) {
			this.isPinput = isPinput;
		}

		public String getParentType() {
			return parentType;
		}

		public void setParentType(String parentType) {
			this.parentType = parentType;
		}

		public String getParentTitle() {
			return parentTitle;
		}

		public void setParentTitle(String parentTitle) {
			this.parentTitle = parentTitle;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public List<AnswerModel> getAnswers() {
			return answers;
		}

		public void setAnswers(List<AnswerModel> answers) {
			this.answers = answers;
		}

		public boolean isAnswerType() {
			return answerType;
		}

		public void setAnswerType(boolean answerType) {
			this.answerType = answerType;
		}

		public boolean isAnswerTitle() {
			return isAnswerTitle;
		}

		public void setAnswerTitle(boolean answerTitle) {
			isAnswerTitle = answerTitle;
		}

		public int getDataIndex() {
			return dataIndex;
		}

		public void setDataIndex(int dataIndex) {
			this.dataIndex = dataIndex;
		}

		public int getMasterId() {
			return masterId;
		}

		public void setMasterId(int masterId) {
			this.masterId = masterId;
		}

		public boolean isTitle() {
			return isTitle;
		}

		public void setTitle(boolean title) {
			isTitle = title;
		}

		public boolean isSelect() {
			return select;
		}

		public void setSelect(boolean select) {
			this.select = select;
		}

		public boolean isSameGrop() {
			return isSameGrop;
		}

		public void setSameGrop(boolean sameGrop) {
			isSameGrop = sameGrop;
		}
	}

	public static class AnswerModel implements Serializable {
		private int id;
		private String answerDetail;
		private boolean isInput;
		private String inputType;
		private String jumpCode;
		private boolean checked;
		private String suggestionText;
		private String titleend;
		
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getAnswerDetail() {
			return answerDetail;
		}

		public void setAnswerDetail(String answerDetail) {
			this.answerDetail = answerDetail;
		}

		public boolean isInput() {
			return isInput;
		}

		public void setInput(boolean isInput) {
			this.isInput = isInput;
		}

		public String getInputType() {
			return inputType;
		}

		public void setInputType(String inputType) {
			this.inputType = inputType;
		}

		public String getJumpCode() {
			return jumpCode;
		}

		public void setJumpCode(String jumpCode) {
			this.jumpCode = jumpCode;
		}

		public boolean getChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}

		public String getSuggestionText() {
			return suggestionText;
		}

		public void setSuggestionText(String suggestionText) {
			this.suggestionText = suggestionText;
		}

		public String getTitleend() {
			return titleend;
		}

		public void setTitleend(String titleend) {
			this.titleend = titleend;
		}
		
	}

}

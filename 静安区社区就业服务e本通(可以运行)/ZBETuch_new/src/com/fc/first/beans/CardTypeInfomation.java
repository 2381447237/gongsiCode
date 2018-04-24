package com.fc.first.beans;

//qwj32----32323232

public class CardTypeInfomation {
	private String cardtypeId;
	private String cardtypeName;

	public CardTypeInfomation() {
		super();
	}

	public CardTypeInfomation(String cardtypeId, String cardtypeName) {
		super();
		this.cardtypeId = cardtypeId;
		this.cardtypeName = cardtypeName;
	}

	public String getCardtypeId() {
		return cardtypeId;
	}

	public void setCardtypeId(String cardtypeId) {
		this.cardtypeId = cardtypeId;
	}

	public String getCardtypeNasme() {
		return cardtypeName;
	}

	public void setCardtypeNasme(String cardtypeNasme) {
		this.cardtypeName = cardtypeNasme;
	}

	@Override
	public String toString() {
		return "CardTypeInfomation [cardtypeId=" + cardtypeId
				+ ", cardtypeName=" + cardtypeName + "]";
	}

}

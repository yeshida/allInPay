package com.allinpay.usdk.dev.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class CardInfo implements Parcelable{

	public final static byte CARD_MAG    = 0x01;
	public final static byte CARD_INSERT = 0x02;
	public final static byte CARD_CLSS   = 0x04;
	public final static byte CARD_UNKNOW = (byte) 0x99;
	
	private byte cardType;
	private String track1;
	private String track2;
	private String track3;
	private String cardNo;
	private String expDate;
	
	public byte getCardType() {
		return cardType;
	}

	public void setCardType(byte cardType) {
		this.cardType = cardType;
	}

	public String getTrack1() {
		return track1;
	}

	public void setTrack1(String track1) {
		this.track1 = track1;
	}

	public String getTrack2() {
		return track2;
	}

	public void setTrack2(String track2) {
		this.track2 = track2;
	}

	public String getTrack3() {
		return track3;
	}

	public void setTrack3(String track3) {
		this.track3 = track3;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte(cardType);
		dest.writeString(track1);
		dest.writeString(track2);
		dest.writeString(track3);
		dest.writeString(cardNo);
		dest.writeString(expDate);
	}
	
	public static final Creator<CardInfo> CREATOR = new Creator<CardInfo>() {

		@Override
		public CardInfo createFromParcel(Parcel source) {
			CardInfo info = new CardInfo();
			info.setCardType(source.readByte());
			info.setTrack1(source.readString());
			info.setTrack2(source.readString());
			info.setTrack3(source.readString());
			info.setCardNo(source.readString());
			info.setExpDate(source.readString());
			return info;
		}

		@Override
		public CardInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CardInfo[size];
		}
	
	};
	
}

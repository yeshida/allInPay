package com.allinpay.usdk.dev.aidl.emv;

import android.os.Parcel;
import android.os.Parcelable;

public class EmvCapk implements Parcelable {

	// 应用注册服务ID
	private String RID;
	// 密钥索引
	private int KeyID;
	// HASH算法标志,SM算法时，值为07
	private int HashInd;
	// RSA算法标志，SM算法时，值为04
	private int arithInd;
	// 模，SM算法时，为公钥值
	private String modul;
	// 指数，SM算法时，为0，不检查此数据项
	private String Exponent;
	// 有效期(YYMMDD)
	private String expDate;
	// 密钥校验和，SM算法时，为0，不检查此数据项
	private String checkSum;

	public String getRID() {
		return RID;
	}

	public void setRID(String rID) {
		RID = rID;
	}

	public int getKeyID() {
		return KeyID;
	}

	public void setKeyID(int keyID) {
		KeyID = keyID;
	}

	public int getHashInd() {
		return HashInd;
	}

	public void setHashInd(int hashInd) {
		HashInd = hashInd;
	}

	public int getArithInd() {
		return arithInd;
	}

	public void setArithInd(int arithInd) {
		this.arithInd = arithInd;
	}

	public String getModul() {
		return modul;
	}

	public void setModul(String modul) {
		this.modul = modul;
	}

	public String getExponent() {
		return Exponent;
	}

	public void setExponent(String exponent) {
		Exponent = exponent;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(RID);
		dest.writeInt(KeyID);
		dest.writeInt(HashInd);
		dest.writeInt(arithInd);
		dest.writeString(modul);
		dest.writeString(Exponent);
		dest.writeString(expDate);
		dest.writeString(checkSum);

	}

	public static final Creator<EmvCapk> CREATOR = new Creator<EmvCapk>() {

		@Override
		public EmvCapk createFromParcel(Parcel source) {
			EmvCapk info = new EmvCapk();
			info.setRID(source.readString());
			info.setKeyID(source.readInt());
			info.setHashInd(source.readInt());
			info.setArithInd(source.readInt());
			info.setModul(source.readString());
			info.setExponent(source.readString());
			info.setExpDate(source.readString());
			info.setCheckSum(source.readString());
			return info;
		}

		@Override
		public EmvCapk[] newArray(int size) {
			return new EmvCapk[size];
		}
	
	};
}

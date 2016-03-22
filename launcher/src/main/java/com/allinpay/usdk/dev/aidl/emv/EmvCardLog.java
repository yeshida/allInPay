package com.allinpay.usdk.dev.aidl.emv;

import android.os.Parcel;
import android.os.Parcelable;

public class EmvCardLog implements Parcelable {

	// 交易金额存在标识
	private boolean isAmtExist;
	// 交易金额（右靠左补0）
	private String amt;
	// 其它金额存在标识
	private boolean isOtherAmtExist;
	// 其它金额（右靠左补0）
	private String otherAmt;
	// 存在交易日期（YYMMDD）
	private boolean isDateExist;
	// 交易日期
	private String transDate;
	// 交易时间存在标识
	private boolean isTimeExist;
	// 交易时间（hhmmss）
	private String transTime;
	// 国家代码存在标识
	private boolean isCntCodeExist;
	// 国家代码(9F1A)
	private String cntCode;
	// 货币代码存在标识
	private boolean isCurExist;
	// 货币代码(5F2A)
	private String curCode;
	// 交易计数器存在标识
	private boolean isAtcExist;
	// 交易计数器(9F36)
	private String atc;
	// 交易类型存在标识
	private boolean is9Cexist;
	// 交易类型(9C)
	private String serveType;
	// 商户名称存在标识
	private boolean isMerchNameExist;
	// 商户名称(9F4E)
	private String merchName;
	// 本结构中未定义的其它数据元按照TLV 列表的格式保存在tlv中
	private String tlv;

	public boolean isAmtExist() {
		return isAmtExist;
	}

	public void setAmtExist(boolean isAmtExist) {
		this.isAmtExist = isAmtExist;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public boolean isOtherAmtExist() {
		return isOtherAmtExist;
	}

	public void setOtherAmtExist(boolean isOtherAmtExist) {
		this.isOtherAmtExist = isOtherAmtExist;
	}

	public String getOtherAmt() {
		return otherAmt;
	}

	public void setOtherAmt(String otherAmt) {
		this.otherAmt = otherAmt;
	}

	public boolean isDateExist() {
		return isDateExist;
	}

	public void setDateExist(boolean isDateExist) {
		this.isDateExist = isDateExist;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public boolean isTimeExist() {
		return isTimeExist;
	}

	public void setTimeExist(boolean isTimeExist) {
		this.isTimeExist = isTimeExist;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public boolean isCntCodeExist() {
		return isCntCodeExist;
	}

	public void setCntCodeExist(boolean isCntCodeExist) {
		this.isCntCodeExist = isCntCodeExist;
	}

	public String getCntCode() {
		return cntCode;
	}

	public void setCntCode(String cntCode) {
		this.cntCode = cntCode;
	}

	public boolean isCurExist() {
		return isCurExist;
	}

	public void setCurExist(boolean isCurExist) {
		this.isCurExist = isCurExist;
	}

	public String getCurCode() {
		return curCode;
	}

	public void setCurCode(String curCode) {
		this.curCode = curCode;
	}

	public boolean isAtcExist() {
		return isAtcExist;
	}

	public void setAtcExist(boolean isAtcExist) {
		this.isAtcExist = isAtcExist;
	}

	public String getAtc() {
		return atc;
	}

	public void setAtc(String atc) {
		this.atc = atc;
	}

	public boolean isIs9Cexist() {
		return is9Cexist;
	}

	public void setIs9Cexist(boolean is9Cexist) {
		this.is9Cexist = is9Cexist;
	}

	public String getServeType() {
		return serveType;
	}

	public void setServeType(String serveType) {
		this.serveType = serveType;
	}

	public boolean isMerchNameExist() {
		return isMerchNameExist;
	}

	public void setMerchNameExist(boolean isMerchNameExist) {
		this.isMerchNameExist = isMerchNameExist;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public String getTlv() {
		return tlv;
	}

	public void setTlv(String tlv) {
		this.tlv = tlv;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeByte((byte)(isAmtExist ? 1 : 0));
		dest.writeString(amt);
		dest.writeByte((byte)(isOtherAmtExist ? 1 : 0));
		dest.writeString(otherAmt);
		dest.writeByte((byte)(isDateExist ? 1 : 0));
		dest.writeString(transDate);
		dest.writeByte((byte)(isTimeExist ? 1 : 0));
		dest.writeString(transTime);
		dest.writeByte((byte)(isCntCodeExist ? 1 : 0));
		dest.writeString(cntCode);
		dest.writeByte((byte)(isCurExist ? 1 : 0));
		dest.writeString(curCode);
		dest.writeByte((byte)(isAtcExist ? 1 : 0));
		dest.writeString(atc);
		dest.writeByte((byte)(is9Cexist ? 1 : 0));
		dest.writeString(serveType);
		dest.writeByte((byte)(isMerchNameExist ? 1 : 0));
		dest.writeString(merchName);
		dest.writeString(tlv);
	}
	
	public static final Creator<EmvCardLog> CREATOR = new Creator<EmvCardLog>() {

		@Override
		public EmvCardLog createFromParcel(Parcel source) {
			EmvCardLog log = new EmvCardLog();
			log.setAmtExist((byte) source.readByte() != 0);
			log.setAmt(source.readString());
			log.setOtherAmtExist((byte) source.readByte() != 0);
			log.setOtherAmt(source.readString());
			log.setDateExist((byte) source.readByte() != 0);
			log.setTransDate(source.readString());
			log.setTimeExist((byte) source.readByte() != 0);
			log.setTransTime(source.readString());
			log.setCntCodeExist((byte) source.readByte() != 0);
			log.setCntCode(source.readString());
			log.setCurExist((byte) source.readByte() != 0);
			log.setCurCode(source.readString());
			log.setAtcExist((byte) source.readByte() != 0);
			log.setAtc(source.readString());
			log.setIs9Cexist((byte) source.readByte() != 0);
			log.setServeType(source.readString());
			log.setMerchNameExist((byte) source.readByte() != 0);
			log.setMerchName(source.readString());
			log.setTlv(source.readString());

			return log;
		}

		@Override
		public EmvCardLog[] newArray(int size) {
			return new EmvCardLog[size];
		}
	
	};
}

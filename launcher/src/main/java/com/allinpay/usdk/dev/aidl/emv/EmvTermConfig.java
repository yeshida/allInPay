package com.allinpay.usdk.dev.aidl.emv;

import android.os.Parcel;
import android.os.Parcelable;

public class EmvTermConfig implements Parcelable {

	// 参考货币代码和交易代码转换系数
	private long referCurrCon;
	// 商户名
	private String merchName;
	// 商户类别码(没要求可不设置)
	private String merchCateCode;
	// 商户标志(商户号)
	private String merchId;
	// 终端标志(POS号)
	private String termId;
	// 终端类型
	private int terminalType;
	// 终端性能
	private String capability;
	// 终端扩展性能
	private String exCapability;
	// 交易货币代码指数
	private int transCurrExp;
	// 参考货币指数
	private int referCurrExp;
	// 参考货币代码
	private String referCurrCode;
	// 终端国家代码
	private String countryCode;
	// 当前交易类型
	private String transCurrCode;
	// 商户强制联机(1 表示总是联机交易)
	private int transType;
	// 商户强制联机(1 表示总是联机交易)
	private int forceOnline;
	// 密码检测前是否读重试次数
	private int getDataPIN;
	// 是否支持PSE选择方式
	private int surportPSESel;
	// 是否基于卡片AIP进行风险管理,0-用卡片AIP,1-用终端AIP,默认为0
	private int useTermAIPFlg;
	// 终端是否强制进行风险管理，byte1-bit4为1：强制进行风险管理；byte1-bit4为0：不进行风险管理。默认两个字节全为0
	private byte[] termAIP;
	// whether bypass all other pin when one pin has been bypassed 1-Yes, 0-No
	private int bypassAllFlg;
	// 0-不支持，1－支持，默认支持
	private int bypassPin;
	// 0-online data capture, 1-batch capture
	private int batchCapture;
	// 0-不支持Advice，1-支持Advice
	private int adviceFlg;
	// 脚本模式
	private int scriptMethod;
	// 1-ForceAccept
	private int forceAccept;
	// 
	private String reserve;
	// TSI存在? 1-存在 电子现金终端支持指示器 （EC Terminal Support Indicator）
	private int ECTSIFlg;
	// 电子现金终端支持指示器 = 1,支持
	private int ECTSIVal;
	// 电子现金终端交易限额
	private int ECTTLFlg;
	// 
	private long ECTTLVal;

	public long getReferCurrCon() {
		return referCurrCon;
	}

	public void setReferCurrCon(long referCurrCon) {
		this.referCurrCon = referCurrCon;
	}

	public String getMerchName() {
		return merchName;
	}

	public void setMerchName(String merchName) {
		this.merchName = merchName;
	}

	public String getMerchCateCode() {
		return merchCateCode;
	}

	public void setMerchCateCode(String merchCateCode) {
		this.merchCateCode = merchCateCode;
	}

	public String getMerchId() {
		return merchId;
	}

	public void setMerchId(String merchId) {
		this.merchId = merchId;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public int getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(int terminalType) {
		this.terminalType = terminalType;
	}

	public String getCapability() {
		return capability;
	}

	public void setCapability(String capability) {
		this.capability = capability;
	}

	public String getExCapability() {
		return exCapability;
	}

	public void setExCapability(String exCapability) {
		this.exCapability = exCapability;
	}

	public int getTransCurrExp() {
		return transCurrExp;
	}

	public void setTransCurrExp(int transCurrExp) {
		this.transCurrExp = transCurrExp;
	}

	public int getReferCurrExp() {
		return referCurrExp;
	}

	public void setReferCurrExp(int referCurrExp) {
		this.referCurrExp = referCurrExp;
	}

	public String getReferCurrCode() {
		return referCurrCode;
	}

	public void setReferCurrCode(String referCurrCode) {
		this.referCurrCode = referCurrCode;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getTransCurrCode() {
		return transCurrCode;
	}

	public void setTransCurrCode(String transCurrCode) {
		this.transCurrCode = transCurrCode;
	}

	public int getTransType() {
		return transType;
	}

	public void setTransType(int transType) {
		this.transType = transType;
	}

	public int getForceOnline() {
		return forceOnline;
	}

	public void setForceOnline(int forceOnline) {
		this.forceOnline = forceOnline;
	}

	public int getGetDataPIN() {
		return getDataPIN;
	}

	public void setGetDataPIN(int getDataPIN) {
		this.getDataPIN = getDataPIN;
	}

	public int getSurportPSESel() {
		return surportPSESel;
	}

	public void setSurportPSESel(int surportPSESel) {
		this.surportPSESel = surportPSESel;
	}

	public int getUseTermAIPFlg() {
		return useTermAIPFlg;
	}

	public void setUseTermAIPFlg(int useTermAIPFlg) {
		this.useTermAIPFlg = useTermAIPFlg;
	}

	public byte[] getTermAIP() {
		return termAIP;
	}

	public void setTermAIP(byte[] termAIP) {
		this.termAIP = termAIP;
	}

	public int getBypassAllFlg() {
		return bypassAllFlg;
	}

	public void setBypassAllFlg(int bypassAllFlg) {
		this.bypassAllFlg = bypassAllFlg;
	}

	public int getBypassPin() {
		return bypassPin;
	}

	public void setBypassPin(int bypassPin) {
		this.bypassPin = bypassPin;
	}

	public int getBatchCapture() {
		return batchCapture;
	}

	public void setBatchCapture(int batchCapture) {
		this.batchCapture = batchCapture;
	}

	public int getAdviceFlg() {
		return adviceFlg;
	}

	public void setAdviceFlg(int adviceFlg) {
		this.adviceFlg = adviceFlg;
	}

	public int getScriptMethod() {
		return scriptMethod;
	}

	public void setScriptMethod(int scriptMethod) {
		this.scriptMethod = scriptMethod;
	}

	public int getForceAccept() {
		return forceAccept;
	}

	public void setForceAccept(int forceAccept) {
		this.forceAccept = forceAccept;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public int getECTSIFlg() {
		return ECTSIFlg;
	}

	public void setECTSIFlg(int eCTSIFlg) {
		ECTSIFlg = eCTSIFlg;
	}

	public int getECTSIVal() {
		return ECTSIVal;
	}

	public void setECTSIVal(int eCTSIVal) {
		ECTSIVal = eCTSIVal;
	}

	public int getECTTLFlg() {
		return ECTTLFlg;
	}

	public void setECTTLFlg(int eCTTLFlg) {
		ECTTLFlg = eCTTLFlg;
	}

	public long getECTTLVal() {
		return ECTTLVal;
	}

	public void setECTTLVal(long eCTTLVal) {
		ECTTLVal = eCTTLVal;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		
		dest.writeLong(referCurrCon);
		dest.writeString(merchName);
		dest.writeString(merchCateCode);
		dest.writeString(merchId);
		dest.writeString(termId);
		dest.writeInt(terminalType);
		dest.writeString(capability);
		dest.writeString(exCapability);
		dest.writeInt(transCurrExp);
		dest.writeInt(referCurrExp);
		dest.writeString(referCurrCode);
		dest.writeString(countryCode);
		dest.writeString(transCurrCode);
		dest.writeInt(transType);
		dest.writeInt(forceOnline);
		dest.writeInt(getDataPIN);
		dest.writeInt(surportPSESel);
		dest.writeInt(useTermAIPFlg);
		if (termAIP != null) {
			int termAipLen = termAIP.length;
			dest.writeInt(termAipLen);
			dest.writeByteArray(termAIP);
		} else {
			dest.writeInt(0);
		}
		dest.writeInt(bypassAllFlg);
		dest.writeInt(bypassPin);
		dest.writeInt(batchCapture);
		dest.writeInt(adviceFlg);
		dest.writeInt(scriptMethod);
		dest.writeInt(forceAccept);
		dest.writeString(reserve);
		dest.writeInt(ECTSIFlg);
		dest.writeInt(ECTSIVal);
		dest.writeInt(ECTTLFlg);
		dest.writeLong(ECTTLVal);
		
	}
	
	public static final Creator<EmvTermConfig> CREATOR = new Creator<EmvTermConfig>() {

		@Override
		public EmvTermConfig createFromParcel(Parcel source) {
			
			EmvTermConfig cfg = new EmvTermConfig();
			cfg.setReferCurrCon(source.readLong());
			cfg.setMerchName(source.readString());
			cfg.setMerchCateCode(source.readString());
			cfg.setMerchId(source.readString());
			cfg.setTermId(source.readString());
			cfg.setTerminalType(source.readInt());
			cfg.setCapability(source.readString());
			cfg.setExCapability(source.readString());
			cfg.setTransCurrExp(source.readInt());
			cfg.setReferCurrExp(source.readInt());
			cfg.setReferCurrCode(source.readString());
			cfg.setCountryCode(source.readString());
			cfg.setTransCurrCode(source.readString());
			cfg.setTransType(source.readInt());
			cfg.setForceOnline(source.readInt());
			cfg.setGetDataPIN(source.readInt());
			cfg.setSurportPSESel(source.readInt());
			cfg.setUseTermAIPFlg(source.readInt());
			int termAipLen = source.readInt();
			if (termAipLen != 0) {
				byte[] termAip = new byte[termAipLen];
				source.readByteArray(termAip);
				cfg.setTermAIP(termAip);
			}
			cfg.setBypassAllFlg(source.readInt());
			cfg.setBypassPin(source.readInt());
			cfg.setBatchCapture(source.readInt());
			cfg.setAdviceFlg(source.readInt());
			cfg.setScriptMethod(source.readInt());
			cfg.setForceAccept(source.readInt());
			cfg.setReserve(source.readString());
			cfg.setECTSIFlg(source.readInt());
			cfg.setECTSIVal(source.readInt());
			cfg.setECTTLFlg(source.readInt());
			cfg.setECTTLVal(source.readLong());
			return cfg;
		}

		@Override
		public EmvTermConfig[] newArray(int size) {
			return new EmvTermConfig[size];
		}
		
	};
}

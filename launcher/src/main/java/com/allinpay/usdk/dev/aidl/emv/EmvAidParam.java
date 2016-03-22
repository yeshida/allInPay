package com.allinpay.usdk.dev.aidl.emv;

import android.os.Parcel;
import android.os.Parcelable;

public class EmvAidParam implements Parcelable {

	// aid
	private String AID;
	// 选择标志(PART_MATCH 部分匹配FULL_MATCH 全匹配)
	private int selFlag;
	// 终端联机PIN支持能力
	private int onlinePin;
	// 电子现金终端交易限额(9F7F)
	private long ECTTLVal;
	// 读卡器非接触CVM限额(DF21)
	private long RdCVMLmt;
	// 读卡器非接触交易限额(DF20)
	private long RdClssTxnLmt;
	// 读卡器非接触脱机最低限额(DF19)
	private long RdClssFLmt;
	// TTL存在? 1-存在 电子现金终端交易限额（EC Terminal Transaction Limit）(9F7B)
	private int ECTTLFlg;
	// 是否存在读卡器非接触脱机最低限额
	private int RdClssFLmtFlg;
	// 是否存在读卡器非接触交易限额
	private int RdClssTxnLmtFlg;
	// 是否存在读卡器非接触CVM限额
	private int RdCVMLmtFlg;

	// 目标百分比数
	private int targetPer;
	// 最大目标百分比数
	private int maxTargetPer;
	// 是否检查最低限额
	private int floorlimitCheck;
	// 是否进行随机交易选择
	private int randTransSel;
	// 是否进行频度检测
	private int velocityCheck;
	// 最低限额
	private long floorLimit;
	// 阀值
	private long threshold;
	// 终端行为代码(拒绝)
	private String tacDenial;
	// 终端行为代码(联机)
	private String tacOnline;
	// 终端行为代码(缺省)
	private String tacDefualt;
	// 收单行标志
	private String acquierId;
	// 终端缺省DDOL
	private String dDOL;
	// 终端缺省TDOL
	private String tDOL;
	// 应用版本
	private String version;
	// 风险管理数据
	private String riskmanData;

	public String getAID() {
		return AID;
	}

	public void setAID(String aID) {
		AID = aID;
	}

	public int getSelFlag() {
		return selFlag;
	}

	public void setSelFlag(int selFlag) {
		this.selFlag = selFlag;
	}

	public int getOnlinePin() {
		return onlinePin;
	}

	public void setOnlinePin(int onlinePin) {
		this.onlinePin = onlinePin;
	}

	public long getECTTLVal() {
		return ECTTLVal;
	}

	public void setECTTLVal(long eCTTLVal) {
		ECTTLVal = eCTTLVal;
	}

	public long getRdCVMLmt() {
		return RdCVMLmt;
	}

	public void setRdCVMLmt(long rdCVMLmt) {
		RdCVMLmt = rdCVMLmt;
	}

	public long getRdClssTxnLmt() {
		return RdClssTxnLmt;
	}

	public void setRdClssTxnLmt(long rdClssTxnLmt) {
		RdClssTxnLmt = rdClssTxnLmt;
	}

	public long getRdClssFLmt() {
		return RdClssFLmt;
	}

	public void setRdClssFLmt(long rdClssFLmt) {
		RdClssFLmt = rdClssFLmt;
	}

	public int getECTTLFlg() {
		return ECTTLFlg;
	}

	public void setECTTLFlg(int eCTTLFlg) {
		ECTTLFlg = eCTTLFlg;
	}

	public int getRdClssFLmtFlg() {
		return RdClssFLmtFlg;
	}

	public void setRdClssFLmtFlg(int rdClssFLmtFlg) {
		RdClssFLmtFlg = rdClssFLmtFlg;
	}

	public int getRdClssTxnLmtFlg() {
		return RdClssTxnLmtFlg;
	}

	public void setRdClssTxnLmtFlg(int rdClssTxnLmtFlg) {
		RdClssTxnLmtFlg = rdClssTxnLmtFlg;
	}

	public int getRdCVMLmtFlg() {
		return RdCVMLmtFlg;
	}

	public void setRdCVMLmtFlg(int rdCVMLmtFlg) {
		RdCVMLmtFlg = rdCVMLmtFlg;
	}


	public int getTargetPer() {
		return targetPer;
	}

	public void setTargetPer(int targetPer) {
		this.targetPer = targetPer;
	}

	public int getMaxTargetPer() {
		return maxTargetPer;
	}

	public void setMaxTargetPer(int maxTargetPer) {
		this.maxTargetPer = maxTargetPer;
	}

	public int getFloorlimitCheck() {
		return floorlimitCheck;
	}

	public void setFloorlimitCheck(int floorlimitCheck) {
		this.floorlimitCheck = floorlimitCheck;
	}

	public int getRandTransSel() {
		return randTransSel;
	}

	public void setRandTransSel(int randTransSel) {
		this.randTransSel = randTransSel;
	}

	public int getVelocityCheck() {
		return velocityCheck;
	}

	public void setVelocityCheck(int velocityCheck) {
		this.velocityCheck = velocityCheck;
	}

	public long getFloorLimit() {
		return floorLimit;
	}

	public void setFloorLimit(long floorLimit) {
		this.floorLimit = floorLimit;
	}

	public long getThreshold() {
		return threshold;
	}

	public void setThreshold(long threshold) {
		this.threshold = threshold;
	}

	public String getTacDenial() {
		return tacDenial;
	}

	public void setTacDenial(String tacDenial) {
		this.tacDenial = tacDenial;
	}

	public String getTacOnline() {
		return tacOnline;
	}

	public void setTacOnline(String tacOnline) {
		this.tacOnline = tacOnline;
	}

	public String getTacDefualt() {
		return tacDefualt;
	}

	public void setTacDefualt(String tacDefualt) {
		this.tacDefualt = tacDefualt;
	}

	public String getAcquierId() {
		return acquierId;
	}

	public void setAcquierId(String acquierId) {
		this.acquierId = acquierId;
	}

	public String getdDOL() {
		return dDOL;
	}

	public void setdDOL(String dDOL) {
		this.dDOL = dDOL;
	}

	public String gettDOL() {
		return tDOL;
	}

	public void settDOL(String tDOL) {
		this.tDOL = tDOL;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getRiskmanData() {
		return riskmanData;
	}

	public void setRiskmanData(String riskmanData) {
		this.riskmanData = riskmanData;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(AID);
		dest.writeInt(selFlag);
		dest.writeInt(onlinePin);
		dest.writeLong(ECTTLVal);
		dest.writeLong(RdCVMLmt);
		dest.writeLong(RdClssTxnLmt);
		dest.writeLong(RdClssFLmt);
		dest.writeInt(ECTTLFlg);
		dest.writeInt(RdClssFLmtFlg);
		dest.writeInt(RdClssTxnLmtFlg);
		dest.writeInt(RdCVMLmtFlg);

		dest.writeInt(targetPer);
		dest.writeInt(maxTargetPer);
		dest.writeInt(floorlimitCheck);
		dest.writeInt(randTransSel);
		dest.writeInt(velocityCheck);
		dest.writeLong(floorLimit);
		dest.writeLong(threshold);
		dest.writeString(tacDenial);
		dest.writeString(tacOnline);
		dest.writeString(tacDefualt);
		dest.writeString(acquierId);
		dest.writeString(dDOL);
		dest.writeString(tDOL);
		dest.writeString(version);
		dest.writeString(riskmanData);
	}

	public static final Creator<EmvAidParam> CREATOR = new Creator<EmvAidParam>() {

		@Override
		public EmvAidParam createFromParcel(Parcel source) {
			EmvAidParam param = new EmvAidParam();
			param.setAID(source.readString());
			param.setSelFlag(source.readInt());
			param.setOnlinePin(source.readInt());
			param.setECTTLVal(source.readLong());
			param.setRdCVMLmt(source.readLong());
			param.setRdClssTxnLmt(source.readLong());
			param.setRdClssFLmt(source.readLong());
			param.setECTTLFlg(source.readInt());
			param.setRdClssFLmtFlg(source.readInt());
			param.setRdClssTxnLmtFlg(source.readInt());
			param.setRdCVMLmtFlg(source.readInt());
			
			param.setTargetPer(source.readInt());
			param.setMaxTargetPer(source.readInt());
			param.setFloorlimitCheck(source.readInt());
			param.setRandTransSel(source.readInt());
			param.setVelocityCheck(source.readInt());
			param.setFloorLimit(source.readLong());
			param.setThreshold(source.readLong());
			param.setTacDenial(source.readString());
			param.setTacOnline(source.readString());
			param.setTacDefualt(source.readString());
			param.setAcquierId(source.readString());
			param.setdDOL(source.readString());
			param.settDOL(source.readString());
			param.setVersion(source.readString());
			param.setRiskmanData(source.readString());
			return param;
		}

		@Override
		public EmvAidParam[] newArray(int size) {
			return new EmvAidParam[size];
		}
	};

}

package com.allinpay.usdk.dev.aidl.emv;

import android.os.Parcel;
import android.os.Parcelable;

import com.allinpay.usdk.dev.aidl.emv.EmvConstant.FLOW;

public class EmvTransData implements Parcelable {
	// 交易金额 12字节的ASCII码
	private String amount;
	// 交易类型，银联规范要求
	private byte tag9Value;
	// 交易日期，YYYYMMDD, 8字节ASCII码，例如：“20150701”
	private String transDate;
	// 交易时间，HHMMSS,6字节ASCII码，例如：“154920”
	private String transTime;
	// 交易序号, 6字节ASCII码  
	private String transNo;
	//是否支持国密： true:支持， false：不支持
	private boolean isSupportSM;	
	// 是否执行脱机数据认证  false:不执行 ，true:执行  插卡的EMV流程使用，完成流程设为true，简易流程设置为false 
	private boolean isCardAuth;
	// 是否强制联机, true:是， false:否, 非接交易有效，设置为1则联机完成交易，0则走正常的电子现金交易流程
	private boolean isForceOnline;
	// 是否支持电子现金交易, true:支持， false：不支持
	private boolean isSupportEC;
	//是否执行CVM   非指定账户圈存、现金圈存交易需设置为0，其他交易设置为1
	private boolean isSupportCvm;
	//交易流程
	private FLOW flow;
	// 通道类型
	private EmvChannel channel;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public byte getTag9Value() {
		return tag9Value;
	}

	public void setTag9Value(byte tag9Value) {
		this.tag9Value = tag9Value;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getTransNo() {
		return transNo;
	}

	public void setTransNo(String transNo) {
		this.transNo = transNo;
	}

	public boolean isSupportSM() {
		return isSupportSM;
	}

	public void setSupportSM(boolean isSupportSM) {
		this.isSupportSM = isSupportSM;
	}

	public boolean isCardAuth() {
		return isCardAuth;
	}

	public void setCardAuth(boolean isCardAuth) {
		this.isCardAuth = isCardAuth;
	}

	public boolean isForceOnline() {
		return isForceOnline;
	}

	public void setForceOnline(boolean isForceOnline) {
		this.isForceOnline = isForceOnline;
	}

	public boolean isSupportEC() {
		return isSupportEC;
	}

	public void setSupportEC(boolean isSupportEC) {
		this.isSupportEC = isSupportEC;
	}

	public boolean isSupportCvm() {
		return isSupportCvm;
	}

	public void setSupportCvm(boolean isSupportCvm) {
		this.isSupportCvm = isSupportCvm;
	}

	public static Creator<EmvTransData> getCreator() {
		return CREATOR;
	}

	public FLOW getFlow() {
		return flow;
	}

	public void setFlow(FLOW flow) {
		this.flow = flow;
	}

	public EmvChannel getChannel() {
		return channel;
	}

	public void setChannel(EmvChannel channel) {
		this.channel = channel;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeString(amount);
		dest.writeByte(tag9Value);
		dest.writeString(transDate);
		dest.writeString(transTime);
		dest.writeString(transNo);
		dest.writeInt(isSupportSM==true?1:0);
		dest.writeInt(isCardAuth==true?1:0);
		dest.writeInt(isForceOnline==true?1:0);
		dest.writeInt(isSupportEC==true?1:0);
		dest.writeInt(isSupportCvm==true?1:0);
		switch (flow) {
		case COMPLETE:
			dest.writeInt(0);
			break;
		case SIMPLE:
			dest.writeInt(1);
			break;
		case QPBOC:
			dest.writeInt(2);
			break;

		default:
			break;
		}
		
		switch (channel) {
		case ICC:
			dest.writeInt(0);
			break;
		case PICC:
			dest.writeInt(1);
		default:
			break;
		}
	}

	public static final Creator<EmvTransData> CREATOR = new Creator<EmvTransData>() {

		@Override
		public EmvTransData createFromParcel(Parcel source) {
			EmvTransData data = new EmvTransData();
			data.setAmount(source.readString());
			data.setTag9Value(source.readByte());
			data.setTransDate(source.readString());
			data.setTransTime(source.readString());
			data.setTransNo(source.readString());
			data.setSupportSM(source.readInt()==1?true:false);
			data.setCardAuth(source.readInt()==1?true:false);
			data.setForceOnline(source.readInt()==1?true:false);
			data.setSupportEC(source.readInt()==1?true:false);
			data.setSupportCvm(source.readInt()==1?true:false);
			int ret = source.readInt();
			switch (ret) {
			case 0:
				data.setFlow(FLOW.COMPLETE);
				break;
			case 1:
				data.setFlow(FLOW.SIMPLE);
				break;
			case 2:
				data.setFlow(FLOW.QPBOC);
				break;
			default:
				break;
			}
			
			ret = source.readInt();
			switch (ret) {
			case 0:
				data.setChannel(EmvChannel.ICC);
				break;
			case 1:
				data.setChannel(EmvChannel.PICC);
			default:
				break;
			}
			return data;
		}

		@Override
		public EmvTransData[] newArray(int size) {
			return new EmvTransData[size];
		}
		
	};
}

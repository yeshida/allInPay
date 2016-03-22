package com.allinpay.usdk.dev.aidl.emv;

import android.os.Parcel;
import android.os.Parcelable;

public enum EmvTransResult implements Parcelable{
	/**
	 * 联机批准
	 */
	ONLINE_APPROVED,
	/**
	 * 联机拒绝
	 */
	ONLINE_DENIED,
	/**
	 * 脱机批准
	 */
	OFFLINE_APPROVED,
	/**
	 * 脱机拒绝
	 */
	OFFLINE_DENIED,
	/**
	 * 主机批准，卡片拒绝
	 */
	ONLINE_CARD_DENIED,
	/**
	 * 异常
	 */
	ABORT_TERMINATED,
	/**
	 * 申请联机
	 */
	ARQC,
	/**
	 * 简化流程结束
	 */
	SIMPLE_FLOW_END
	
	;
	public static final Creator<EmvTransResult> CREATOR = new Creator<EmvTransResult>() {

		@Override
		public EmvTransResult createFromParcel(Parcel source) {
			 return EmvTransResult.values()[source.readInt()];  
		}

		@Override
		public EmvTransResult[] newArray(int size) {
			return new EmvTransResult[size];  
		}
	};
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		arg0.writeInt(ordinal());  
	}
}

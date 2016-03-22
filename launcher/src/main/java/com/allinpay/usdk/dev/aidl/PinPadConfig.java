package com.allinpay.usdk.dev.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class PinPadConfig implements Parcelable{
	
	private int type;
	private int mKeyIndex;
	private int timeout;
	private String supportPinLen;
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getmKeyIndex() {
		return mKeyIndex;
	}

	public void setmKeyIndex(int mKeyIndex) {
		this.mKeyIndex = mKeyIndex;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getSupportPinLen() {
		return supportPinLen;
	}

	public void setSupportPinLen(String supportPinLen) {
		this.supportPinLen = supportPinLen;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(type);
		dest.writeInt(mKeyIndex);
		dest.writeInt(timeout);
		dest.writeString(supportPinLen);
	}
	
	public static final Creator<PinPadConfig> CREATOR = new Creator<PinPadConfig>() {

		@Override
		public PinPadConfig createFromParcel(Parcel source) {
			PinPadConfig cfg = new PinPadConfig();
			cfg.setType(source.readInt());
			cfg.setmKeyIndex(source.readInt());
			cfg.setTimeout(source.readInt());
			cfg.setSupportPinLen(source.readString());
			return cfg;
		}

		@Override
		public PinPadConfig[] newArray(int size) {
			return new PinPadConfig[size];
		}
	
	};
	
}

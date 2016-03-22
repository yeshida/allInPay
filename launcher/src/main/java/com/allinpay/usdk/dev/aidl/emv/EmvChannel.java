package com.allinpay.usdk.dev.aidl.emv;

import android.os.Parcel;
import android.os.Parcelable;

public enum EmvChannel implements Parcelable{
	/**
	 * 接触式
	 */
	ICC,
	/**
	 * 非接触式
	 */
	PICC
	;
	public static final Creator<EmvChannel> CREATOR = new Creator<EmvChannel>() {

		@Override
		public EmvChannel createFromParcel(Parcel source) {
			 return EmvChannel.values()[source.readInt()];  
		}

		@Override
		public EmvChannel[] newArray(int size) {
			return new EmvChannel[size];  
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

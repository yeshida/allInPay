package com.allinpay.usdk.dev.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class KeyInfo implements Parcelable{

	public final static int KEYTYPE_MKEY     = 0x01;
	public final static int KEYTYPE_PINKEY   = 0x02;
	public final static int KEYTYPE_MACKEY   = 0x03;
	public final static int KEYTYPE_TRACKKEY = 0x04;
	public final static int KEYTYPE_DESKEY   = 0x05;
	
	
	public final static byte MODE_ISO9564_0 = 0x00;
	public final static byte MODE_ISO9564_1 = 0x01;
	public final static byte MODE_ISO9564_2 = 0x02;
	public final static byte MODE_HK_EPS= 0x03;
	
	public final static int ECB = 0x00;
	public final static int CBC = 0x01;
	
	public final static int DES_DES  = 0x00;
	public final static int DES_3DES = 0x01;
	public final static int DES_ENCRYPT = 0x00;
	public final static int DES_DECRYPT = 0x01;
	
	private int keyType;
	private boolean isEncrypted;
	private int mKeyIndex;
	private int wKeyIndex;
	private byte[] keyData;
	private int keyDataLen;
	private byte[] kcvValue;
	private int kcvValueLen;
	
	
	public int getKeyType() {
		return keyType;
	}

	public void setKeyType(int keyType) {
		this.keyType = keyType;
	}

	public boolean isEncrypted() {
		return isEncrypted;
	}

	public void setEncrypted(boolean isEncrypted) {
		this.isEncrypted = isEncrypted;
	}

	public int getmKeyIndex() {
		return mKeyIndex;
	}

	public void setmKeyIndex(int mKeyIndex) {
		this.mKeyIndex = mKeyIndex;
	}

	public int getwKeyIndex() {
		return wKeyIndex;
	}

	public void setwKeyIndex(int wKeyIndex) {
		this.wKeyIndex = wKeyIndex;
	}

	public byte[] getKeyData() {
		return keyData;
	}

	public void setKeyData(byte[] keyData) {
		this.keyData = keyData;
	}

	public int getKeyDataLen() {
		return keyDataLen;
	}

	public void setKeyDataLen(int keyDataLen) {
		this.keyDataLen = keyDataLen;
	}

	public byte[] getKcvValue() {
		return kcvValue;
	}

	public void setKcvValue(byte[] kcvValue) {
		this.kcvValue = kcvValue;
	}

	public int getKcvValueLen() {
		return kcvValueLen;
	}

	public void setKcvValueLen(int kcvValueLen) {
		this.kcvValueLen = kcvValueLen;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(keyType);
		dest.writeByte((byte)(isEncrypted?1:0));
		dest.writeInt(mKeyIndex);
		dest.writeInt(wKeyIndex);
		if (keyData!=null) {
			keyDataLen = keyData.length;
			dest.writeInt(keyDataLen);
			dest.writeByteArray(keyData);
		}else{
			dest.writeInt(0);
		}
		if (kcvValue!=null) {
			kcvValueLen = kcvValue.length;
			dest.writeInt(kcvValueLen);
			dest.writeByteArray(kcvValue);
		}else {
			dest.writeInt(0);
		}
	}
	
	public static final Creator<KeyInfo> CREATOR = new Creator<KeyInfo>() {

		@Override
		public KeyInfo createFromParcel(Parcel source) {
			
			KeyInfo cfg = new KeyInfo();
			cfg.setKeyType(source.readInt());
			cfg.setEncrypted((byte)source.readByte()!=0);
			cfg.setmKeyIndex(source.readInt());
			cfg.setwKeyIndex(source.readInt());
			
			int keyDataLen = source.readInt();
			cfg.setKeyDataLen(keyDataLen);
			if (keyDataLen != 0) {
				byte[] keyData = new byte[keyDataLen];
				source.readByteArray(keyData);
				cfg.setKeyData(keyData);
			}
			
			int kcvValueLen = source.readInt();
			cfg.setKcvValueLen(kcvValueLen);
			if (kcvValueLen != 0) {
				byte[] kcvValue = new byte[kcvValueLen];
				source.readByteArray(kcvValue);
				cfg.setKcvValue(kcvValue);
			}
			
			return cfg;
		}

		@Override
		public KeyInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new KeyInfo[size];
		}
	
	};
	
}

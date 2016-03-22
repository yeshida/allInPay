package com.allinpay.usdk.dev.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class BasicInfo implements Parcelable {

	/**
	 * 成功提示音
	 */
	public static final int BEEP_SUCC = 0x00;
	
	/**
	 * 错误提示音
	 */
	public static final int BEEP_ERR = 0x01;
	
	/**
	 * 故障提示音
	 */
	public static final int BEEP_FAULT = 0x02;
	
	/**
	 * 提醒提示音
	 */
	public static final int BEEP_PROMPT = 0x03;
	
	/**
	 * 不亮灯
	 */
	public static final byte LED_NULL = 0x00;
	
	/**
	 * 亮红灯
	 */
	public static final byte LED_RED = 0x01;
	
	/**
	 * 亮绿灯
	 */
	public static final byte LED_GREEN = 0x02;
	
	/**
	 * 亮黄灯
	 */
	public static final byte LED_YELLOW = 0x04;
	
	/**
	 * 亮蓝灯
	 */
	public static final byte LED_BLUE = 0x08;

	/**
	 * 亮全灯
	 */
	public static final byte LED_ALL = 0x0F;

	/**
	 * 终端序列号
	 */
	private String sn;
	
	/**
	 * 通联SN(硬件设备序列号)
	 */
	private String aipSn;
	
	/**
	 * 终端机型
	 */
	private String model;
	
	/**
	 * 终端提供商
	 */
	private String vendor;
	
	/**
	 * 入网许可证
	 */
	private String certification;

	/**
	 * 设备服务版本
	 */
	private int versionCode;

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(sn);
		
		dest.writeString(aipSn);
		
		dest.writeString(model);
		dest.writeString(vendor);
		dest.writeString(certification);
		dest.writeInt(versionCode);
	}

	public static final Creator<BasicInfo> CREATOR = new Creator<BasicInfo>() {

		@Override
		public BasicInfo createFromParcel(Parcel source) {
			BasicInfo info = new BasicInfo();
			info.setSn(source.readString());
			info.setAipSn(source.readString());
			info.setModel(source.readString());
			info.setVendor(source.readString());
			info.setCertification(source.readString());
			info.setVersionCode(source.readInt());
			return info;
		}

		@Override
		public BasicInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new BasicInfo[size];
		}

	};

	public String getAipSn() {
		return aipSn;
	}

	public void setAipSn(String aipSn) {
		this.aipSn = aipSn;
	}

	@Override
	public String toString() {
		return "BasicInfo{" +
				"sn='" + sn + '\'' +
				", aipSn='" + aipSn + '\'' +
				", model='" + model + '\'' +
				", vendor='" + vendor + '\'' +
				", certification='" + certification + '\'' +
				", versionCode=" + versionCode +
				'}';
	}
}

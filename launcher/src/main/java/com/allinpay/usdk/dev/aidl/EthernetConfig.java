package com.allinpay.usdk.dev.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class EthernetConfig implements Parcelable{

	public final static int MODE_DHCP     = 0x01;
	public final static int MODE_STATIC   = 0x02;
	
	private int mode;
	private String ip;
	private String netmask;
	private String gateway;
	private String dns1;
	private String dns2;
	
	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getDns1() {
		return dns1;
	}

	public void setDns1(String dns1) {
		this.dns1 = dns1;
	}

	public String getDns2() {
		return dns2;
	}

	public void setDns2(String dns2) {
		this.dns2 = dns2;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(mode);
		dest.writeString(ip);
		dest.writeString(netmask);
		dest.writeString(gateway);
		dest.writeString(dns1);
		dest.writeString(dns2);
	}
	
	public static final Creator<EthernetConfig> CREATOR = new Creator<EthernetConfig>() {

		@Override
		public EthernetConfig createFromParcel(Parcel source) {
			EthernetConfig cfg = new EthernetConfig();
			cfg.setMode(source.readInt());
			cfg.setIp(source.readString());
			cfg.setNetmask(source.readString());
			cfg.setGateway(source.readString());
			cfg.setDns1(source.readString());
			cfg.setDns2(source.readString());
			return cfg;
		}

		@Override
		public EthernetConfig[] newArray(int size) {
			// TODO Auto-generated method stub
			return new EthernetConfig[size];
		}
	
	};
	
}
